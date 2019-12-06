package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;

public final class zza {
    private static final IntentFilter filter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static long zzgt;
    private static float zzgu = Float.NaN;

    @TargetApi(20)
    public static int zzg(Context context) {
        boolean z;
        if (context == null || context.getApplicationContext() == null) {
            return -1;
        }
        Intent registerReceiver = context.getApplicationContext().registerReceiver(null, filter);
        int i = 0;
        int i2 = ((registerReceiver == null ? 0 : registerReceiver.getIntExtra("plugged", 0)) & 7) != 0 ? 1 : 0;
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (powerManager == null) {
            return -1;
        }
        if (PlatformVersion.isAtLeastKitKatWatch()) {
            z = powerManager.isInteractive();
        } else {
            z = powerManager.isScreenOn();
        }
        if (z) {
            i = 1;
        }
        return (i << 1) | i2;
    }

    public static synchronized float zzh(Context context) {
        synchronized (zza.class) {
            if (SystemClock.elapsedRealtime() - zzgt >= 60000 || Float.isNaN(zzgu)) {
                Intent registerReceiver = context.getApplicationContext().registerReceiver(null, filter);
                if (registerReceiver != null) {
                    zzgu = ((float) registerReceiver.getIntExtra("level", -1)) / ((float) registerReceiver.getIntExtra("scale", -1));
                }
                zzgt = SystemClock.elapsedRealtime();
                float f = zzgu;
                return f;
            }
            float f2 = zzgu;
            return f2;
        }
    }
}
