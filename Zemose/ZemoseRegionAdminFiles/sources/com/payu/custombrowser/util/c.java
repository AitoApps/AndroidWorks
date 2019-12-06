package com.payu.custombrowser.util;

import android.util.Log;

public class c {
    private static int a = 7;

    public static synchronized void a(String str, String str2) {
        synchronized (c.class) {
            if (a <= 2) {
                Log.v(str, str2);
            }
        }
    }

    public static synchronized void b(String str, String str2) {
        synchronized (c.class) {
            if (a <= 4) {
                Log.d(str, str2);
            }
        }
    }
}
