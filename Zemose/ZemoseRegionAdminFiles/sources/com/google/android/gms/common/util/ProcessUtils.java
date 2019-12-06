package com.google.android.gms.common.util;

import android.os.Process;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import javax.annotation.Nullable;

@KeepForSdk
public class ProcessUtils {
    private static String zzhd = null;
    private static int zzhe = 0;

    private ProcessUtils() {
    }

    @KeepForSdk
    @Nullable
    public static String getMyProcessName() {
        if (zzhd == null) {
            if (zzhe == 0) {
                zzhe = Process.myPid();
            }
            zzhd = zzd(zzhe);
        }
        return zzhd;
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r4v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r0v2, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r4v10 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    @Nullable
    private static String zzd(int i) {
        String str;
        ? r4;
        ? r0;
        ? r02 = 0;
        if (i <= 0) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder(25);
            sb.append("/proc/");
            sb.append(i);
            sb.append("/cmdline");
            BufferedReader zzj = zzj(sb.toString());
            try {
                String trim = zzj.readLine().trim();
                IOUtils.closeQuietly((Closeable) zzj);
                str = trim;
            } catch (IOException e) {
                r4 = zzj;
                IOUtils.closeQuietly((Closeable) r4);
                str = r02;
                return str;
            } catch (Throwable th) {
                Throwable th2 = th;
                r0 = zzj;
                th = th2;
                IOUtils.closeQuietly((Closeable) r0);
                throw th;
            }
        } catch (IOException e2) {
            r4 = 0;
            IOUtils.closeQuietly((Closeable) r4);
            str = r02;
            return str;
        } catch (Throwable th3) {
            th = th3;
            r0 = r02;
            IOUtils.closeQuietly((Closeable) r0);
            throw th;
        }
        return str;
    }

    private static BufferedReader zzj(String str) throws IOException {
        ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            return new BufferedReader(new FileReader(str));
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }
}
