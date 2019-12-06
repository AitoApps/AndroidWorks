package com.android.volley.toolbox;

import android.os.SystemClock;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Header;
import com.android.volley.VolleyLog;
import com.bumptech.glide.load.Key;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DiskBasedCache implements Cache {
    private static final int CACHE_MAGIC = 538247942;
    private static final int DEFAULT_DISK_USAGE_BYTES = 5242880;
    private static final float HYSTERESIS_FACTOR = 0.9f;
    private final Map<String, CacheHeader> mEntries;
    private final int mMaxCacheSizeInBytes;
    private final File mRootDirectory;
    private long mTotalSize;

    static class CacheHeader {
        final List<Header> allResponseHeaders;
        final String etag;
        final String key;
        final long lastModified;
        final long serverDate;
        long size;
        final long softTtl;
        final long ttl;

        private CacheHeader(String key2, String etag2, long serverDate2, long lastModified2, long ttl2, long softTtl2, List<Header> allResponseHeaders2) {
            this.key = key2;
            this.etag = "".equals(etag2) ? null : etag2;
            this.serverDate = serverDate2;
            this.lastModified = lastModified2;
            this.ttl = ttl2;
            this.softTtl = softTtl2;
            this.allResponseHeaders = allResponseHeaders2;
        }

        CacheHeader(String key2, Entry entry) {
            this(key2, entry.etag, entry.serverDate, entry.lastModified, entry.ttl, entry.softTtl, getAllResponseHeaders(entry));
            this.size = (long) entry.f2data.length;
        }

        private static List<Header> getAllResponseHeaders(Entry entry) {
            if (entry.allResponseHeaders != null) {
                return entry.allResponseHeaders;
            }
            return HttpHeaderParser.toAllHeaderList(entry.responseHeaders);
        }

        static CacheHeader readHeader(CountingInputStream is) throws IOException {
            if (DiskBasedCache.readInt(is) == DiskBasedCache.CACHE_MAGIC) {
                CacheHeader cacheHeader = new CacheHeader(DiskBasedCache.readString(is), DiskBasedCache.readString(is), DiskBasedCache.readLong(is), DiskBasedCache.readLong(is), DiskBasedCache.readLong(is), DiskBasedCache.readLong(is), DiskBasedCache.readHeaderList(is));
                return cacheHeader;
            }
            throw new IOException();
        }

        /* access modifiers changed from: 0000 */
        public Entry toCacheEntry(byte[] data2) {
            Entry e = new Entry();
            e.f2data = data2;
            e.etag = this.etag;
            e.serverDate = this.serverDate;
            e.lastModified = this.lastModified;
            e.ttl = this.ttl;
            e.softTtl = this.softTtl;
            e.responseHeaders = HttpHeaderParser.toHeaderMap(this.allResponseHeaders);
            e.allResponseHeaders = Collections.unmodifiableList(this.allResponseHeaders);
            return e;
        }

        /* access modifiers changed from: 0000 */
        public boolean writeHeader(OutputStream os) {
            try {
                DiskBasedCache.writeInt(os, DiskBasedCache.CACHE_MAGIC);
                DiskBasedCache.writeString(os, this.key);
                DiskBasedCache.writeString(os, this.etag == null ? "" : this.etag);
                DiskBasedCache.writeLong(os, this.serverDate);
                DiskBasedCache.writeLong(os, this.lastModified);
                DiskBasedCache.writeLong(os, this.ttl);
                DiskBasedCache.writeLong(os, this.softTtl);
                DiskBasedCache.writeHeaderList(this.allResponseHeaders, os);
                os.flush();
                return true;
            } catch (IOException e) {
                VolleyLog.d("%s", e.toString());
                return false;
            }
        }
    }

    @VisibleForTesting
    static class CountingInputStream extends FilterInputStream {
        private long bytesRead;
        private final long length;

        CountingInputStream(InputStream in, long length2) {
            super(in);
            this.length = length2;
        }

        public int read() throws IOException {
            int result = super.read();
            if (result != -1) {
                this.bytesRead++;
            }
            return result;
        }

        public int read(byte[] buffer, int offset, int count) throws IOException {
            int result = super.read(buffer, offset, count);
            if (result != -1) {
                this.bytesRead += (long) result;
            }
            return result;
        }

        /* access modifiers changed from: 0000 */
        @VisibleForTesting
        public long bytesRead() {
            return this.bytesRead;
        }

        /* access modifiers changed from: 0000 */
        public long bytesRemaining() {
            return this.length - this.bytesRead;
        }
    }

    public DiskBasedCache(File rootDirectory, int maxCacheSizeInBytes) {
        this.mEntries = new LinkedHashMap(16, 0.75f, true);
        this.mTotalSize = 0;
        this.mRootDirectory = rootDirectory;
        this.mMaxCacheSizeInBytes = maxCacheSizeInBytes;
    }

    public DiskBasedCache(File rootDirectory) {
        this(rootDirectory, DEFAULT_DISK_USAGE_BYTES);
    }

    public synchronized void clear() {
        File[] files = this.mRootDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        this.mEntries.clear();
        this.mTotalSize = 0;
        VolleyLog.d("Cache cleared.", new Object[0]);
    }

    public synchronized Entry get(String key) {
        CountingInputStream cis;
        CacheHeader entry = (CacheHeader) this.mEntries.get(key);
        if (entry == null) {
            return null;
        }
        File file = getFileForKey(key);
        try {
            cis = new CountingInputStream(new BufferedInputStream(createInputStream(file)), file.length());
            CacheHeader entryOnDisk = CacheHeader.readHeader(cis);
            if (!TextUtils.equals(key, entryOnDisk.key)) {
                VolleyLog.d("%s: key=%s, found=%s", file.getAbsolutePath(), key, entryOnDisk.key);
                removeEntry(key);
                cis.close();
                return null;
            }
            Entry cacheEntry = entry.toCacheEntry(streamToBytes(cis, cis.bytesRemaining()));
            cis.close();
            return cacheEntry;
        } catch (IOException e) {
            VolleyLog.d("%s: %s", file.getAbsolutePath(), e.toString());
            remove(key);
            return null;
        } catch (Throwable th) {
            cis.close();
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0025, code lost:
        return;
     */
    public synchronized void initialize() {
        CountingInputStream cis;
        if (this.mRootDirectory.exists()) {
            File[] files = this.mRootDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    try {
                        long entrySize = file.length();
                        cis = new CountingInputStream(new BufferedInputStream(createInputStream(file)), entrySize);
                        CacheHeader entry = CacheHeader.readHeader(cis);
                        entry.size = entrySize;
                        putEntry(entry.key, entry);
                        cis.close();
                    } catch (IOException e) {
                        file.delete();
                    } catch (Throwable th) {
                        cis.close();
                        throw th;
                    }
                }
            }
        } else if (!this.mRootDirectory.mkdirs()) {
            VolleyLog.e("Unable to create cache dir %s", this.mRootDirectory.getAbsolutePath());
        }
    }

    public synchronized void invalidate(String key, boolean fullExpire) {
        Entry entry = get(key);
        if (entry != null) {
            entry.softTtl = 0;
            if (fullExpire) {
                entry.ttl = 0;
            }
            put(key, entry);
        }
    }

    public synchronized void put(String key, Entry entry) {
        pruneIfNeeded(entry.f2data.length);
        File file = getFileForKey(key);
        try {
            BufferedOutputStream fos = new BufferedOutputStream(createOutputStream(file));
            CacheHeader e = new CacheHeader(key, entry);
            if (e.writeHeader(fos)) {
                fos.write(entry.f2data);
                fos.close();
                putEntry(key, e);
            } else {
                fos.close();
                VolleyLog.d("Failed to write header for %s", file.getAbsolutePath());
                throw new IOException();
            }
        } catch (IOException e2) {
            if (!file.delete()) {
                VolleyLog.d("Could not clean up file %s", file.getAbsolutePath());
            }
        }
    }

    public synchronized void remove(String key) {
        boolean deleted = getFileForKey(key).delete();
        removeEntry(key);
        if (!deleted) {
            VolleyLog.d("Could not delete cache entry for key=%s, filename=%s", key, getFilenameForKey(key));
        }
    }

    private String getFilenameForKey(String key) {
        int firstHalfLength = key.length() / 2;
        String localFilename = String.valueOf(key.substring(0, firstHalfLength).hashCode());
        StringBuilder sb = new StringBuilder();
        sb.append(localFilename);
        sb.append(String.valueOf(key.substring(firstHalfLength).hashCode()));
        return sb.toString();
    }

    public File getFileForKey(String key) {
        return new File(this.mRootDirectory, getFilenameForKey(key));
    }

    private void pruneIfNeeded(int neededSpace) {
        long before;
        int i = neededSpace;
        if (this.mTotalSize + ((long) i) >= ((long) this.mMaxCacheSizeInBytes)) {
            if (VolleyLog.DEBUG) {
                VolleyLog.v("Pruning old cache entries.", new Object[0]);
            }
            long before2 = this.mTotalSize;
            int prunedFiles = 0;
            long startTime = SystemClock.elapsedRealtime();
            Iterator<Map.Entry<String, CacheHeader>> iterator = this.mEntries.entrySet().iterator();
            while (true) {
                if (!iterator.hasNext()) {
                    before = before2;
                    break;
                }
                CacheHeader e = (CacheHeader) ((Map.Entry) iterator.next()).getValue();
                if (getFileForKey(e.key).delete()) {
                    before = before2;
                    this.mTotalSize -= e.size;
                } else {
                    before = before2;
                    VolleyLog.d("Could not delete cache entry for key=%s, filename=%s", e.key, getFilenameForKey(e.key));
                }
                iterator.remove();
                prunedFiles++;
                if (((float) (this.mTotalSize + ((long) i))) < ((float) this.mMaxCacheSizeInBytes) * HYSTERESIS_FACTOR) {
                    break;
                }
                before2 = before;
            }
            if (VolleyLog.DEBUG) {
                VolleyLog.v("pruned %d files, %d bytes, %d ms", Integer.valueOf(prunedFiles), Long.valueOf(this.mTotalSize - before), Long.valueOf(SystemClock.elapsedRealtime() - startTime));
            }
        }
    }

    private void putEntry(String key, CacheHeader entry) {
        if (!this.mEntries.containsKey(key)) {
            this.mTotalSize += entry.size;
        } else {
            this.mTotalSize += entry.size - ((CacheHeader) this.mEntries.get(key)).size;
        }
        this.mEntries.put(key, entry);
    }

    private void removeEntry(String key) {
        CacheHeader removed = (CacheHeader) this.mEntries.remove(key);
        if (removed != null) {
            this.mTotalSize -= removed.size;
        }
    }

    static byte[] streamToBytes(CountingInputStream cis, long length) throws IOException {
        long maxLength = cis.bytesRemaining();
        if (length < 0 || length > maxLength || ((long) ((int) length)) != length) {
            StringBuilder sb = new StringBuilder();
            sb.append("streamToBytes length=");
            sb.append(length);
            sb.append(", maxLength=");
            sb.append(maxLength);
            throw new IOException(sb.toString());
        }
        byte[] bytes = new byte[((int) length)];
        new DataInputStream(cis).readFully(bytes);
        return bytes;
    }

    /* access modifiers changed from: 0000 */
    public InputStream createInputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    /* access modifiers changed from: 0000 */
    public OutputStream createOutputStream(File file) throws FileNotFoundException {
        return new FileOutputStream(file);
    }

    private static int read(InputStream is) throws IOException {
        int b = is.read();
        if (b != -1) {
            return b;
        }
        throw new EOFException();
    }

    static void writeInt(OutputStream os, int n) throws IOException {
        os.write((n >> 0) & 255);
        os.write((n >> 8) & 255);
        os.write((n >> 16) & 255);
        os.write((n >> 24) & 255);
    }

    static int readInt(InputStream is) throws IOException {
        return 0 | (read(is) << 0) | (read(is) << 8) | (read(is) << 16) | (read(is) << 24);
    }

    static void writeLong(OutputStream os, long n) throws IOException {
        os.write((byte) ((int) (n >>> 0)));
        os.write((byte) ((int) (n >>> 8)));
        os.write((byte) ((int) (n >>> 16)));
        os.write((byte) ((int) (n >>> 24)));
        os.write((byte) ((int) (n >>> 32)));
        os.write((byte) ((int) (n >>> 40)));
        os.write((byte) ((int) (n >>> 48)));
        os.write((byte) ((int) (n >>> 56)));
    }

    static long readLong(InputStream is) throws IOException {
        return 0 | ((((long) read(is)) & 255) << 0) | ((((long) read(is)) & 255) << 8) | ((((long) read(is)) & 255) << 16) | ((((long) read(is)) & 255) << 24) | ((((long) read(is)) & 255) << 32) | ((((long) read(is)) & 255) << 40) | ((((long) read(is)) & 255) << 48) | ((((long) read(is)) & 255) << 56);
    }

    static void writeString(OutputStream os, String s) throws IOException {
        byte[] b = s.getBytes(Key.STRING_CHARSET_NAME);
        writeLong(os, (long) b.length);
        os.write(b, 0, b.length);
    }

    static String readString(CountingInputStream cis) throws IOException {
        return new String(streamToBytes(cis, readLong(cis)), Key.STRING_CHARSET_NAME);
    }

    static void writeHeaderList(List<Header> headers, OutputStream os) throws IOException {
        if (headers != null) {
            writeInt(os, headers.size());
            for (Header header : headers) {
                writeString(os, header.getName());
                writeString(os, header.getValue());
            }
            return;
        }
        writeInt(os, 0);
    }

    static List<Header> readHeaderList(CountingInputStream cis) throws IOException {
        int size = readInt(cis);
        if (size >= 0) {
            List<Header> result = size == 0 ? Collections.emptyList() : new ArrayList<>();
            for (int i = 0; i < size; i++) {
                result.add(new Header(readString(cis).intern(), readString(cis).intern()));
            }
            return result;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("readHeaderList size=");
        sb.append(size);
        throw new IOException(sb.toString());
    }
}
