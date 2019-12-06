package com.bumptech.glide.load.engine.executor;

import android.os.Build.VERSION;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

final class RuntimeCompat {
    private static final String CPU_LOCATION = "/sys/devices/system/cpu/";
    private static final String CPU_NAME_REGEX = "cpu[0-9]+";
    private static final String TAG = "GlideRuntimeCompat";

    private RuntimeCompat() {
    }

    static int availableProcessors() {
        int cpus = Runtime.getRuntime().availableProcessors();
        if (VERSION.SDK_INT < 17) {
            return Math.max(getCoreCountPre17(), cpus);
        }
        return cpus;
    }

    private static int getCoreCountPre17() {
        File[] cpus = null;
        ThreadPolicy originalPolicy = StrictMode.allowThreadDiskReads();
        try {
            File cpuInfo = new File(CPU_LOCATION);
            final Pattern cpuNamePattern = Pattern.compile(CPU_NAME_REGEX);
            cpus = cpuInfo.listFiles(new FilenameFilter() {
                public boolean accept(File file, String s) {
                    return cpuNamePattern.matcher(s).matches();
                }
            });
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(originalPolicy);
            throw th;
        }
        StrictMode.setThreadPolicy(originalPolicy);
        return Math.max(1, cpus != null ? cpus.length : 0);
    }
}
