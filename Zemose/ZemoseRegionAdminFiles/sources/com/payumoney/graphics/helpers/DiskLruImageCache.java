package com.payumoney.graphics.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.jakewharton.disklrucache.DiskLruCache;
import com.jakewharton.disklrucache.DiskLruCache.Editor;
import com.jakewharton.disklrucache.DiskLruCache.Snapshot;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DiskLruImageCache implements ImageCache {
    private static final int APP_VERSION = 1;
    private static int IO_BUFFER_SIZE = 8192;
    private static final int VALUE_COUNT = 1;
    private Context ctx;
    private int diskCacheSize = 0;
    private CompressFormat mCompressFormat = CompressFormat.JPEG;
    private int mCompressQuality = 70;
    private DiskLruCache mDiskCache;
    private String uniqueName = null;

    public DiskLruImageCache(Context context, String uniqueName2, int diskCacheSize2, CompressFormat compressFormat, int quality) {
        try {
            File diskCacheDir = getDiskCacheDir(context, uniqueName2);
            this.ctx = context;
            this.uniqueName = uniqueName2;
            this.diskCacheSize = diskCacheSize2;
            this.mDiskCache = DiskLruCache.open(diskCacheDir, 1, 1, (long) diskCacheSize2);
            this.mCompressFormat = compressFormat;
            this.mCompressQuality = quality;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean writeBitmapToFile(Bitmap bitmap, Editor editor) throws IOException {
        OutputStream out = null;
        try {
            OutputStream out2 = new BufferedOutputStream(editor.newOutputStream(0), IO_BUFFER_SIZE);
            boolean compress = bitmap.compress(this.mCompressFormat, this.mCompressQuality, out2);
            out2.close();
            return compress;
        } catch (Throwable th) {
            if (out != null) {
                out.close();
            }
            throw th;
        }
    }

    private File getDiskCacheDir(Context context, String uniqueName2) {
        String cachePath = context.getCacheDir().getPath();
        StringBuilder sb = new StringBuilder();
        sb.append(cachePath);
        sb.append(File.separator);
        sb.append(uniqueName2);
        return new File(sb.toString());
    }

    public void putBitmap(String key, Bitmap data2) {
        Editor editor = null;
        try {
            Editor editor2 = this.mDiskCache.edit(String.valueOf(key.hashCode()));
            if (editor2 != null) {
                if (writeBitmapToFile(data2, editor2)) {
                    this.mDiskCache.flush();
                    editor2.commit();
                } else {
                    editor2.abort();
                }
            }
        } catch (IOException e) {
            if (editor != null) {
                try {
                    editor.abort();
                } catch (IOException e2) {
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0031, code lost:
        if (r1 != null) goto L_0x0033;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0033, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003e, code lost:
        if (r1 == null) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0041, code lost:
        return r0;
     */
    public Bitmap getBitmap(String key) {
        Bitmap bitmap = null;
        Snapshot snapshot = null;
        try {
            snapshot = this.mDiskCache.get(String.valueOf(key.hashCode()));
            if (snapshot == null) {
                if (snapshot != null) {
                    snapshot.close();
                }
                return null;
            }
            InputStream in = snapshot.getInputStream(0);
            if (in != null) {
                bitmap = BitmapFactory.decodeStream(new BufferedInputStream(in, IO_BUFFER_SIZE));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            if (snapshot != null) {
                snapshot.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001c, code lost:
        if (r1 == null) goto L_0x001f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001f, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000f, code lost:
        if (r1 != null) goto L_0x0011;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0011, code lost:
        r1.close();
     */
    public boolean containsKey(String key) {
        boolean contained = false;
        Snapshot snapshot = null;
        try {
            snapshot = this.mDiskCache.get(key);
            contained = snapshot != null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            if (snapshot != null) {
                snapshot.close();
            }
            throw th;
        }
    }

    public void clearCache() {
        try {
            this.mDiskCache.delete();
            try {
                this.mDiskCache = DiskLruCache.open(getDiskCacheDir(this.ctx, this.uniqueName), 1, 1, (long) this.diskCacheSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public File getCacheFolder() {
        return this.mDiskCache.getDirectory();
    }
}
