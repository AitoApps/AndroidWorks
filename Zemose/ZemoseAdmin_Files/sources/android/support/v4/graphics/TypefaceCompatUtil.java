package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

@RestrictTo({Scope.LIBRARY_GROUP})
public class TypefaceCompatUtil {
    private static final String CACHE_FILE_PREFIX = ".font";
    private static final String TAG = "TypefaceCompatUtil";

    private TypefaceCompatUtil() {
    }

    @Nullable
    public static File getTempFile(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(CACHE_FILE_PREFIX);
        sb.append(Process.myPid());
        sb.append("-");
        sb.append(Process.myTid());
        sb.append("-");
        String prefix = sb.toString();
        int i = 0;
        while (i < 100) {
            File cacheDir = context.getCacheDir();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(prefix);
            sb2.append(i);
            File file = new File(cacheDir, sb2.toString());
            try {
                if (file.createNewFile()) {
                    return file;
                }
                i++;
            } catch (IOException e) {
            }
        }
        return null;
    }

    @Nullable
    @RequiresApi(19)
    private static ByteBuffer mmap(File file) {
        Throwable th;
        Throwable th2;
        try {
            FileInputStream fis = new FileInputStream(file);
            try {
                FileChannel channel = fis.getChannel();
                MappedByteBuffer map = channel.map(MapMode.READ_ONLY, 0, channel.size());
                fis.close();
                return map;
            } catch (Throwable th3) {
                Throwable th4 = th3;
                th = r2;
                th2 = th4;
            }
            if (th != null) {
                try {
                    fis.close();
                } catch (Throwable th5) {
                    th.addSuppressed(th5);
                }
            } else {
                fis.close();
            }
            throw th2;
            throw th2;
        } catch (IOException e) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0050, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r5.addSuppressed(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x005d, code lost:
        r3 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x005e, code lost:
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x006b, code lost:
        if (r4 != null) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0072, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        r4.addSuppressed(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0078, code lost:
        r2.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x005d A[ExcHandler: all (th java.lang.Throwable), Splitter:B:7:0x0014] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x006b  */
    @Nullable
    @RequiresApi(19)
    public static ByteBuffer mmap(Context context, CancellationSignal cancellationSignal, Uri uri) {
        Throwable th;
        FileInputStream fis;
        Throwable th2;
        Throwable th3;
        try {
            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r", cancellationSignal);
            if (pfd == null) {
                if (pfd != null) {
                    pfd.close();
                }
                return null;
            }
            try {
                fis = new FileInputStream(pfd.getFileDescriptor());
                try {
                    FileChannel channel = fis.getChannel();
                    MappedByteBuffer map = channel.map(MapMode.READ_ONLY, 0, channel.size());
                    fis.close();
                    if (pfd != null) {
                        pfd.close();
                    }
                    return map;
                } catch (Throwable th4) {
                    Throwable th5 = th4;
                    th2 = r4;
                    th3 = th5;
                }
            } catch (Throwable th6) {
                Throwable th7 = th6;
                Throwable th8 = r3;
                th = th7;
            }
            if (pfd != null) {
            }
            throw th;
            if (th2 != null) {
                fis.close();
            } else {
                fis.close();
            }
            throw th3;
            throw th3;
            throw th;
        } catch (IOException e) {
            return null;
        }
    }

    @Nullable
    @RequiresApi(19)
    public static ByteBuffer copyToDirectBuffer(Context context, Resources res, int id) {
        File tmpFile = getTempFile(context);
        ByteBuffer byteBuffer = null;
        if (tmpFile == null) {
            return null;
        }
        try {
            if (copyToFile(tmpFile, res, id)) {
                byteBuffer = mmap(tmpFile);
            }
            return byteBuffer;
        } finally {
            tmpFile.delete();
        }
    }

    public static boolean copyToFile(File file, InputStream is) {
        FileOutputStream os = null;
        ThreadPolicy old = StrictMode.allowThreadDiskWrites();
        try {
            os = new FileOutputStream(file, false);
            byte[] buffer = new byte[1024];
            while (true) {
                int read = is.read(buffer);
                int readLen = read;
                if (read != -1) {
                    os.write(buffer, 0, readLen);
                } else {
                    return true;
                }
            }
        } catch (IOException e) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Error copying resource contents to temp file: ");
            sb.append(e.getMessage());
            Log.e(str, sb.toString());
            return false;
        } finally {
            closeQuietly(os);
            StrictMode.setThreadPolicy(old);
        }
    }

    public static boolean copyToFile(File file, Resources res, int id) {
        InputStream is = null;
        try {
            is = res.openRawResource(id);
            return copyToFile(file, is);
        } finally {
            closeQuietly(is);
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
}
