package com.bumptech.glide.disklrucache;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class DiskLruCache implements Closeable {
    static final long ANY_SEQUENCE_NUMBER = -1;
    private static final String CLEAN = "CLEAN";
    private static final String DIRTY = "DIRTY";
    static final String JOURNAL_FILE = "journal";
    static final String JOURNAL_FILE_BACKUP = "journal.bkp";
    static final String JOURNAL_FILE_TEMP = "journal.tmp";
    static final String MAGIC = "libcore.io.DiskLruCache";
    private static final String READ = "READ";
    private static final String REMOVE = "REMOVE";
    static final String VERSION_1 = "1";
    private final int appVersion;
    private final Callable<Void> cleanupCallable;
    /* access modifiers changed from: private */
    public final File directory;
    final ThreadPoolExecutor executorService;
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;
    /* access modifiers changed from: private */
    public Writer journalWriter;
    private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap<>(0, 0.75f, true);
    private long maxSize;
    private long nextSequenceNumber = 0;
    /* access modifiers changed from: private */
    public int redundantOpCount;
    private long size = 0;
    /* access modifiers changed from: private */
    public final int valueCount;

    private static final class DiskLruCacheThreadFactory implements ThreadFactory {
        private DiskLruCacheThreadFactory() {
        }

        public synchronized Thread newThread(Runnable runnable) {
            Thread result;
            result = new Thread(runnable, "glide-disk-lru-cache-thread");
            result.setPriority(1);
            return result;
        }
    }

    public final class Editor {
        private boolean committed;
        /* access modifiers changed from: private */
        public final Entry entry;
        /* access modifiers changed from: private */
        public final boolean[] written;

        private Editor(Entry entry2) {
            this.entry = entry2;
            this.written = entry2.readable ? null : new boolean[DiskLruCache.this.valueCount];
        }

        private InputStream newInputStream(int index) throws IOException {
            synchronized (DiskLruCache.this) {
                if (this.entry.currentEditor != this) {
                    throw new IllegalStateException();
                } else if (!this.entry.readable) {
                    return null;
                } else {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(this.entry.getCleanFile(index));
                        return fileInputStream;
                    } catch (FileNotFoundException e) {
                        return null;
                    }
                }
            }
        }

        public String getString(int index) throws IOException {
            InputStream in = newInputStream(index);
            if (in != null) {
                return DiskLruCache.inputStreamToString(in);
            }
            return null;
        }

        public File getFile(int index) throws IOException {
            File dirtyFile;
            synchronized (DiskLruCache.this) {
                if (this.entry.currentEditor == this) {
                    if (!this.entry.readable) {
                        this.written[index] = true;
                    }
                    dirtyFile = this.entry.getDirtyFile(index);
                    if (!DiskLruCache.this.directory.exists()) {
                        DiskLruCache.this.directory.mkdirs();
                    }
                } else {
                    throw new IllegalStateException();
                }
            }
            return dirtyFile;
        }

        public void set(int index, String value) throws IOException {
            Writer writer = null;
            try {
                writer = new OutputStreamWriter(new FileOutputStream(getFile(index)), Util.UTF_8);
                writer.write(value);
            } finally {
                Util.closeQuietly(writer);
            }
        }

        public void commit() throws IOException {
            DiskLruCache.this.completeEdit(this, true);
            this.committed = true;
        }

        public void abort() throws IOException {
            DiskLruCache.this.completeEdit(this, false);
        }

        public void abortUnlessCommitted() {
            if (!this.committed) {
                try {
                    abort();
                } catch (IOException e) {
                }
            }
        }
    }

    private final class Entry {
        File[] cleanFiles;
        /* access modifiers changed from: private */
        public Editor currentEditor;
        File[] dirtyFiles;
        /* access modifiers changed from: private */
        public final String key;
        /* access modifiers changed from: private */
        public final long[] lengths;
        /* access modifiers changed from: private */
        public boolean readable;
        /* access modifiers changed from: private */
        public long sequenceNumber;

        private Entry(String key2) {
            this.key = key2;
            this.lengths = new long[DiskLruCache.this.valueCount];
            this.cleanFiles = new File[DiskLruCache.this.valueCount];
            this.dirtyFiles = new File[DiskLruCache.this.valueCount];
            StringBuilder fileBuilder = new StringBuilder(key2).append('.');
            int truncateTo = fileBuilder.length();
            for (int i = 0; i < DiskLruCache.this.valueCount; i++) {
                fileBuilder.append(i);
                this.cleanFiles[i] = new File(DiskLruCache.this.directory, fileBuilder.toString());
                fileBuilder.append(".tmp");
                this.dirtyFiles[i] = new File(DiskLruCache.this.directory, fileBuilder.toString());
                fileBuilder.setLength(truncateTo);
            }
        }

        public String getLengths() throws IOException {
            long[] jArr;
            StringBuilder result = new StringBuilder();
            for (long size : this.lengths) {
                result.append(' ');
                result.append(size);
            }
            return result.toString();
        }

        /* access modifiers changed from: private */
        public void setLengths(String[] strings) throws IOException {
            if (strings.length == DiskLruCache.this.valueCount) {
                int i = 0;
                while (i < strings.length) {
                    try {
                        this.lengths[i] = Long.parseLong(strings[i]);
                        i++;
                    } catch (NumberFormatException e) {
                        throw invalidLengths(strings);
                    }
                }
                return;
            }
            throw invalidLengths(strings);
        }

        private IOException invalidLengths(String[] strings) throws IOException {
            StringBuilder sb = new StringBuilder();
            sb.append("unexpected journal line: ");
            sb.append(Arrays.toString(strings));
            throw new IOException(sb.toString());
        }

        public File getCleanFile(int i) {
            return this.cleanFiles[i];
        }

        public File getDirtyFile(int i) {
            return this.dirtyFiles[i];
        }
    }

    public final class Value {
        private final File[] files;
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;

        private Value(String key2, long sequenceNumber2, File[] files2, long[] lengths2) {
            this.key = key2;
            this.sequenceNumber = sequenceNumber2;
            this.files = files2;
            this.lengths = lengths2;
        }

        public Editor edit() throws IOException {
            return DiskLruCache.this.edit(this.key, this.sequenceNumber);
        }

        public File getFile(int index) {
            return this.files[index];
        }

        public String getString(int index) throws IOException {
            return DiskLruCache.inputStreamToString(new FileInputStream(this.files[index]));
        }

        public long getLength(int index) {
            return this.lengths[index];
        }
    }

    private DiskLruCache(File directory2, int appVersion2, int valueCount2, long maxSize2) {
        File file = directory2;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), new DiskLruCacheThreadFactory());
        this.executorService = threadPoolExecutor;
        this.cleanupCallable = new Callable<Void>() {
            /* JADX WARNING: Code restructure failed: missing block: B:11:0x0029, code lost:
                return null;
             */
            public Void call() throws Exception {
                synchronized (DiskLruCache.this) {
                    if (DiskLruCache.this.journalWriter == null) {
                        return null;
                    }
                    DiskLruCache.this.trimToSize();
                    if (DiskLruCache.this.journalRebuildRequired()) {
                        DiskLruCache.this.rebuildJournal();
                        DiskLruCache.this.redundantOpCount = 0;
                    }
                }
            }
        };
        this.directory = file;
        this.appVersion = appVersion2;
        this.journalFile = new File(file, JOURNAL_FILE);
        this.journalFileTmp = new File(file, JOURNAL_FILE_TEMP);
        this.journalFileBackup = new File(file, JOURNAL_FILE_BACKUP);
        this.valueCount = valueCount2;
        this.maxSize = maxSize2;
    }

    public static DiskLruCache open(File directory2, int appVersion2, int valueCount2, long maxSize2) throws IOException {
        if (maxSize2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (valueCount2 > 0) {
            File backupFile = new File(directory2, JOURNAL_FILE_BACKUP);
            if (backupFile.exists()) {
                File journalFile2 = new File(directory2, JOURNAL_FILE);
                if (journalFile2.exists()) {
                    backupFile.delete();
                } else {
                    renameTo(backupFile, journalFile2, false);
                }
            }
            DiskLruCache diskLruCache = new DiskLruCache(directory2, appVersion2, valueCount2, maxSize2);
            if (diskLruCache.journalFile.exists()) {
                try {
                    diskLruCache.readJournal();
                    diskLruCache.processJournal();
                    return diskLruCache;
                } catch (IOException journalIsCorrupt) {
                    PrintStream printStream = System.out;
                    StringBuilder sb = new StringBuilder();
                    sb.append("DiskLruCache ");
                    sb.append(directory2);
                    sb.append(" is corrupt: ");
                    sb.append(journalIsCorrupt.getMessage());
                    sb.append(", removing");
                    printStream.println(sb.toString());
                    diskLruCache.delete();
                }
            }
            directory2.mkdirs();
            DiskLruCache diskLruCache2 = new DiskLruCache(directory2, appVersion2, valueCount2, maxSize2);
            DiskLruCache cache = diskLruCache2;
            cache.rebuildJournal();
            return cache;
        } else {
            throw new IllegalArgumentException("valueCount <= 0");
        }
    }

    private void readJournal() throws IOException {
        int lineCount;
        StrictLineReader reader = new StrictLineReader(new FileInputStream(this.journalFile), Util.US_ASCII);
        try {
            String magic = reader.readLine();
            String version = reader.readLine();
            String appVersionString = reader.readLine();
            String valueCountString = reader.readLine();
            String blank = reader.readLine();
            if (!MAGIC.equals(magic) || !"1".equals(version) || !Integer.toString(this.appVersion).equals(appVersionString) || !Integer.toString(this.valueCount).equals(valueCountString) || !"".equals(blank)) {
                StringBuilder sb = new StringBuilder();
                sb.append("unexpected journal header: [");
                sb.append(magic);
                sb.append(", ");
                sb.append(version);
                sb.append(", ");
                sb.append(valueCountString);
                sb.append(", ");
                sb.append(blank);
                sb.append("]");
                throw new IOException(sb.toString());
            }
            lineCount = 0;
            while (true) {
                readJournalLine(reader.readLine());
                lineCount++;
            }
        } catch (EOFException e) {
            this.redundantOpCount = lineCount - this.lruEntries.size();
            if (reader.hasUnterminatedLine()) {
                rebuildJournal();
            } else {
                this.journalWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFile, true), Util.US_ASCII));
            }
            Util.closeQuietly(reader);
        } catch (Throwable th) {
            Util.closeQuietly(reader);
            throw th;
        }
    }

    private void readJournalLine(String line) throws IOException {
        String key;
        int firstSpace = line.indexOf(32);
        if (firstSpace != -1) {
            int keyBegin = firstSpace + 1;
            int secondSpace = line.indexOf(32, keyBegin);
            if (secondSpace == -1) {
                key = line.substring(keyBegin);
                if (firstSpace == REMOVE.length() && line.startsWith(REMOVE)) {
                    this.lruEntries.remove(key);
                    return;
                }
            } else {
                key = line.substring(keyBegin, secondSpace);
            }
            Entry entry = (Entry) this.lruEntries.get(key);
            if (entry == null) {
                entry = new Entry(key);
                this.lruEntries.put(key, entry);
            }
            if (secondSpace != -1 && firstSpace == CLEAN.length() && line.startsWith(CLEAN)) {
                String[] parts = line.substring(secondSpace + 1).split(" ");
                entry.readable = true;
                entry.currentEditor = null;
                entry.setLengths(parts);
            } else if (secondSpace == -1 && firstSpace == DIRTY.length() && line.startsWith(DIRTY)) {
                entry.currentEditor = new Editor(entry);
            } else if (!(secondSpace == -1 && firstSpace == READ.length() && line.startsWith(READ))) {
                StringBuilder sb = new StringBuilder();
                sb.append("unexpected journal line: ");
                sb.append(line);
                throw new IOException(sb.toString());
            }
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("unexpected journal line: ");
        sb2.append(line);
        throw new IOException(sb2.toString());
    }

    private void processJournal() throws IOException {
        deleteIfExists(this.journalFileTmp);
        Iterator<Entry> i = this.lruEntries.values().iterator();
        while (i.hasNext()) {
            Entry entry = (Entry) i.next();
            if (entry.currentEditor == null) {
                for (int t = 0; t < this.valueCount; t++) {
                    this.size += entry.lengths[t];
                }
            } else {
                entry.currentEditor = null;
                for (int t2 = 0; t2 < this.valueCount; t2++) {
                    deleteIfExists(entry.getCleanFile(t2));
                    deleteIfExists(entry.getDirtyFile(t2));
                }
                i.remove();
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    public synchronized void rebuildJournal() throws IOException {
        if (this.journalWriter != null) {
            this.journalWriter.close();
        }
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFileTmp), Util.US_ASCII));
        try {
            writer.write(MAGIC);
            writer.write("\n");
            writer.write("1");
            writer.write("\n");
            writer.write(Integer.toString(this.appVersion));
            writer.write("\n");
            writer.write(Integer.toString(this.valueCount));
            writer.write("\n");
            writer.write("\n");
            for (Entry entry : this.lruEntries.values()) {
                if (entry.currentEditor != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("DIRTY ");
                    sb.append(entry.key);
                    sb.append(10);
                    writer.write(sb.toString());
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("CLEAN ");
                    sb2.append(entry.key);
                    sb2.append(entry.getLengths());
                    sb2.append(10);
                    writer.write(sb2.toString());
                }
            }
            writer.close();
            if (this.journalFile.exists()) {
                renameTo(this.journalFile, this.journalFileBackup, true);
            }
            renameTo(this.journalFileTmp, this.journalFile, false);
            this.journalFileBackup.delete();
            this.journalWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFile, true), Util.US_ASCII));
        } catch (Throwable th) {
            writer.close();
            throw th;
        }
    }

    private static void deleteIfExists(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    private static void renameTo(File from, File to, boolean deleteDestination) throws IOException {
        if (deleteDestination) {
            deleteIfExists(to);
        }
        if (!from.renameTo(to)) {
            throw new IOException();
        }
    }

    public synchronized Value get(String key) throws IOException {
        checkNotClosed();
        Entry entry = (Entry) this.lruEntries.get(key);
        if (entry == null) {
            return null;
        }
        if (!entry.readable) {
            return null;
        }
        for (File file : entry.cleanFiles) {
            if (!file.exists()) {
                return null;
            }
        }
        this.redundantOpCount++;
        this.journalWriter.append(READ);
        this.journalWriter.append(' ');
        this.journalWriter.append(key);
        this.journalWriter.append(10);
        if (journalRebuildRequired()) {
            this.executorService.submit(this.cleanupCallable);
        }
        Value value = new Value(key, entry.sequenceNumber, entry.cleanFiles, entry.lengths);
        return value;
    }

    public Editor edit(String key) throws IOException {
        return edit(key, -1);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0021, code lost:
        return null;
     */
    public synchronized Editor edit(String key, long expectedSequenceNumber) throws IOException {
        checkNotClosed();
        Entry entry = (Entry) this.lruEntries.get(key);
        if (expectedSequenceNumber == -1 || (entry != null && entry.sequenceNumber == expectedSequenceNumber)) {
            if (entry == null) {
                entry = new Entry(key);
                this.lruEntries.put(key, entry);
            } else if (entry.currentEditor != null) {
                return null;
            }
            Editor editor = new Editor(entry);
            entry.currentEditor = editor;
            this.journalWriter.append(DIRTY);
            this.journalWriter.append(' ');
            this.journalWriter.append(key);
            this.journalWriter.append(10);
            this.journalWriter.flush();
            return editor;
        }
    }

    public File getDirectory() {
        return this.directory;
    }

    public synchronized long getMaxSize() {
        return this.maxSize;
    }

    public synchronized void setMaxSize(long maxSize2) {
        this.maxSize = maxSize2;
        this.executorService.submit(this.cleanupCallable);
    }

    public synchronized long size() {
        return this.size;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0177, code lost:
        return;
     */
    public synchronized void completeEdit(Editor editor, boolean success) throws IOException {
        Entry entry = editor.entry;
        if (entry.currentEditor == editor) {
            if (success && !entry.readable) {
                int i = 0;
                while (i < this.valueCount) {
                    if (!editor.written[i]) {
                        editor.abort();
                        StringBuilder sb = new StringBuilder();
                        sb.append("Newly created entry didn't create value for index ");
                        sb.append(i);
                        throw new IllegalStateException(sb.toString());
                    } else if (!entry.getDirtyFile(i).exists()) {
                        editor.abort();
                        return;
                    } else {
                        i++;
                    }
                }
            }
            for (int i2 = 0; i2 < this.valueCount; i2++) {
                File dirty = entry.getDirtyFile(i2);
                if (!success) {
                    deleteIfExists(dirty);
                } else if (dirty.exists()) {
                    File clean = entry.getCleanFile(i2);
                    dirty.renameTo(clean);
                    long oldLength = entry.lengths[i2];
                    long newLength = clean.length();
                    entry.lengths[i2] = newLength;
                    this.size = (this.size - oldLength) + newLength;
                }
            }
            this.redundantOpCount++;
            entry.currentEditor = null;
            if (entry.readable || success) {
                entry.readable = true;
                this.journalWriter.append(CLEAN);
                this.journalWriter.append(' ');
                this.journalWriter.append(entry.key);
                this.journalWriter.append(entry.getLengths());
                this.journalWriter.append(10);
                if (success) {
                    long j = this.nextSequenceNumber;
                    this.nextSequenceNumber = 1 + j;
                    entry.sequenceNumber = j;
                }
            } else {
                this.lruEntries.remove(entry.key);
                this.journalWriter.append(REMOVE);
                this.journalWriter.append(' ');
                this.journalWriter.append(entry.key);
                this.journalWriter.append(10);
            }
            this.journalWriter.flush();
            if (this.size <= this.maxSize) {
                if (journalRebuildRequired()) {
                }
            }
            this.executorService.submit(this.cleanupCallable);
        } else {
            throw new IllegalStateException();
        }
    }

    /* access modifiers changed from: private */
    public boolean journalRebuildRequired() {
        int i = this.redundantOpCount;
        return i >= 2000 && i >= this.lruEntries.size();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0090, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0093, code lost:
        return false;
     */
    public synchronized boolean remove(String key) throws IOException {
        checkNotClosed();
        Entry entry = (Entry) this.lruEntries.get(key);
        if (entry != null) {
            if (entry.currentEditor == null) {
                for (int i = 0; i < this.valueCount; i++) {
                    File file = entry.getCleanFile(i);
                    if (file.exists()) {
                        if (!file.delete()) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("failed to delete ");
                            sb.append(file);
                            throw new IOException(sb.toString());
                        }
                    }
                    this.size -= entry.lengths[i];
                    entry.lengths[i] = 0;
                }
                this.redundantOpCount++;
                this.journalWriter.append(REMOVE);
                this.journalWriter.append(' ');
                this.journalWriter.append(key);
                this.journalWriter.append(10);
                this.lruEntries.remove(key);
                if (journalRebuildRequired()) {
                    this.executorService.submit(this.cleanupCallable);
                }
            }
        }
    }

    public synchronized boolean isClosed() {
        return this.journalWriter == null;
    }

    private void checkNotClosed() {
        if (this.journalWriter == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public synchronized void flush() throws IOException {
        checkNotClosed();
        trimToSize();
        this.journalWriter.flush();
    }

    public synchronized void close() throws IOException {
        if (this.journalWriter != null) {
            Iterator it = new ArrayList(this.lruEntries.values()).iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                if (entry.currentEditor != null) {
                    entry.currentEditor.abort();
                }
            }
            trimToSize();
            this.journalWriter.close();
            this.journalWriter = null;
        }
    }

    /* access modifiers changed from: private */
    public void trimToSize() throws IOException {
        while (this.size > this.maxSize) {
            remove((String) ((java.util.Map.Entry) this.lruEntries.entrySet().iterator().next()).getKey());
        }
    }

    public void delete() throws IOException {
        close();
        Util.deleteContents(this.directory);
    }

    /* access modifiers changed from: private */
    public static String inputStreamToString(InputStream in) throws IOException {
        return Util.readFully(new InputStreamReader(in, Util.UTF_8));
    }
}
