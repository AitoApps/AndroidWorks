package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
public final class DeviceProperties {
    private static Boolean zzgl;
    private static Boolean zzgm;
    private static Boolean zzgn;
    private static Boolean zzgo;
    private static Boolean zzgp;
    private static Boolean zzgq;
    private static Boolean zzgr;
    private static Boolean zzgs;

    private DeviceProperties() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003f, code lost:
        if (zzgm.booleanValue() != false) goto L_0x0044;
     */
    @KeepForSdk
    public static boolean isTablet(Resources resources) {
        boolean z = false;
        if (resources == null) {
            return false;
        }
        if (zzgl == null) {
            if (!((resources.getConfiguration().screenLayout & 15) > 3)) {
                if (zzgm == null) {
                    Configuration configuration = resources.getConfiguration();
                    zzgm = Boolean.valueOf((configuration.screenLayout & 15) <= 3 && configuration.smallestScreenWidthDp >= 600);
                }
            }
            z = true;
            zzgl = Boolean.valueOf(z);
        }
        return zzgl.booleanValue();
    }

    @TargetApi(20)
    @KeepForSdk
    public static boolean isWearable(Context context) {
        if (zzgn == null) {
            zzgn = Boolean.valueOf(PlatformVersion.isAtLeastKitKatWatch() && context.getPackageManager().hasSystemFeature("android.hardware.type.watch"));
        }
        return zzgn.booleanValue();
    }

    @TargetApi(26)
    @KeepForSdk
    public static boolean isWearableWithoutPlayStore(Context context) {
        return isWearable(context) && (!PlatformVersion.isAtLeastN() || (isSidewinder(context) && !PlatformVersion.isAtLeastO()));
    }

    @TargetApi(21)
    @KeepForSdk
    public static boolean isSidewinder(Context context) {
        if (zzgo == null) {
            zzgo = Boolean.valueOf(PlatformVersion.isAtLeastLollipop() && context.getPackageManager().hasSystemFeature("cn.google"));
        }
        return zzgo.booleanValue();
    }

    @KeepForSdk
    public static boolean isLatchsky(Context context) {
        if (zzgp == null) {
            PackageManager packageManager = context.getPackageManager();
            zzgp = Boolean.valueOf(packageManager.hasSystemFeature("com.google.android.feature.services_updater") && packageManager.hasSystemFeature("cn.google.services"));
        }
        return zzgp.booleanValue();
    }

    public static boolean zzf(Context context) {
        if (zzgq == null) {
            zzgq = Boolean.valueOf(context.getPackageManager().hasSystemFeature("android.hardware.type.iot") || context.getPackageManager().hasSystemFeature("android.hardware.type.embedded"));
        }
        return zzgq.booleanValue();
    }

    @KeepForSdk
    public static boolean isAuto(Context context) {
        if (zzgr == null) {
            zzgr = Boolean.valueOf(PlatformVersion.isAtLeastO() && context.getPackageManager().hasSystemFeature("android.hardware.type.automotive"));
        }
        return zzgr.booleanValue();
    }

    @KeepForSdk
    public static boolean isTv(Context context) {
        if (zzgs == null) {
            PackageManager packageManager = context.getPackageManager();
            zzgs = Boolean.valueOf(packageManager.hasSystemFeature("com.google.android.tv") || packageManager.hasSystemFeature("android.hardware.type.television") || packageManager.hasSystemFeature("android.software.leanback"));
        }
        return zzgs.booleanValue();
    }

    @KeepForSdk
    public static boolean isUserBuild() {
        return "user".equals(Build.TYPE);
    }
}
