package com.google.android.gms.internal.firebase_messaging;

import java.io.PrintStream;

public final class zzc {
    private static final zzd zzb;
    private static final int zzc;

    static final class zza extends zzd {
        zza() {
        }

        public final void zza(Throwable th, Throwable th2) {
        }
    }

    public static void zza(Throwable th, Throwable th2) {
        zzb.zza(th, th2);
    }

    private static Integer zza() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x006a  */
    static {
        zzd zzd;
        Integer num;
        int i = 1;
        try {
            num = zza();
            if (num != null) {
                try {
                    if (num.intValue() >= 19) {
                        zzd = new zzh();
                        zzb = zzd;
                        if (num != null) {
                            i = num.intValue();
                        }
                        zzc = i;
                    }
                } catch (Throwable th) {
                    th = th;
                    PrintStream printStream = System.err;
                    String name = zza.class.getName();
                    StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 132);
                    sb.append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ");
                    sb.append(name);
                    sb.append("will be used. The error is: ");
                    printStream.println(sb.toString());
                    th.printStackTrace(System.err);
                    zzd = new zza();
                    zzb = zzd;
                    if (num != null) {
                    }
                    zzc = i;
                }
            }
            if (!Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic")) {
                zzd = new zzg();
            } else {
                zzd = new zza();
            }
        } catch (Throwable th2) {
            th = th2;
            num = null;
            PrintStream printStream2 = System.err;
            String name2 = zza.class.getName();
            StringBuilder sb2 = new StringBuilder(String.valueOf(name2).length() + 132);
            sb2.append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ");
            sb2.append(name2);
            sb2.append("will be used. The error is: ");
            printStream2.println(sb2.toString());
            th.printStackTrace(System.err);
            zzd = new zza();
            zzb = zzd;
            if (num != null) {
            }
            zzc = i;
        }
        zzb = zzd;
        if (num != null) {
        }
        zzc = i;
    }
}
