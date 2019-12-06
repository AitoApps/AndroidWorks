package android.support.v4.graphics;

import android.content.Context;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.provider.FontsContractCompat.FontInfo;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RequiresApi(21)
@RestrictTo({Scope.LIBRARY_GROUP})
class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
    private static final String TAG = "TypefaceCompatApi21Impl";

    TypefaceCompatApi21Impl() {
    }

    private File getFile(ParcelFileDescriptor fd) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("/proc/self/fd/");
            sb.append(fd.getFd());
            String path = Os.readlink(sb.toString());
            if (OsConstants.S_ISREG(Os.stat(path).st_mode)) {
                return new File(path);
            }
            return null;
        } catch (ErrnoException e) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0062, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r7.addSuppressed(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x006f, code lost:
        r4 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0070, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x007d, code lost:
        if (r5 != null) goto L_0x0080;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0084, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        r5.addSuppressed(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x008a, code lost:
        r3.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x006f A[ExcHandler: all (th java.lang.Throwable), Splitter:B:6:0x001a] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x007d  */
    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, @NonNull FontInfo[] fonts, int style) {
        ParcelFileDescriptor pfd;
        Throwable th;
        FileInputStream fis;
        Throwable th2;
        Throwable th3;
        if (fonts.length < 1) {
            return null;
        }
        FontInfo bestFont = findBestInfo(fonts, style);
        try {
            pfd = context.getContentResolver().openFileDescriptor(bestFont.getUri(), "r", cancellationSignal);
            try {
                File file = getFile(pfd);
                if (file != null) {
                    if (file.canRead()) {
                        Typeface createFromFile = Typeface.createFromFile(file);
                        if (pfd != null) {
                            pfd.close();
                        }
                        return createFromFile;
                    }
                }
                fis = new FileInputStream(pfd.getFileDescriptor());
                try {
                    Typeface createFromInputStream = super.createFromInputStream(context, fis);
                    fis.close();
                    if (pfd != null) {
                        pfd.close();
                    }
                    return createFromInputStream;
                } catch (Throwable th4) {
                    Throwable th5 = th4;
                    th2 = r6;
                    th3 = th5;
                }
            } catch (Throwable th6) {
                Throwable th7 = th6;
                Throwable th8 = r4;
                th = th7;
            }
        } catch (IOException e) {
            return null;
        }
        throw th;
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
    }
}
