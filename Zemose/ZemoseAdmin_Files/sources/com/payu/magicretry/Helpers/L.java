package com.payu.magicretry.Helpers;

import android.util.Log;

public class L {
    public static final int DEBUG = 4;
    private static final String DEFAULT_LOG_TAG = "### PAYU ####";
    private static final String DEFAULT_TIMESTAMP_TAG = "PAYU-TIMESTAMP";
    public static final int ERROR = 6;
    public static final int INFO = 3;
    public static final int NONE = 7;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    private static int sLogLevel = 7;

    public static synchronized void t(String message) {
        synchronized (L.class) {
            if (sLogLevel <= 2) {
                Log.v(DEFAULT_TIMESTAMP_TAG, message);
            }
        }
    }

    public static synchronized void v(String tag, String message) {
        synchronized (L.class) {
            if (sLogLevel <= 2) {
                Log.v(tag, message);
            }
        }
    }

    public static synchronized void v(String message) {
        synchronized (L.class) {
            if (sLogLevel <= 2) {
                Log.v(DEFAULT_LOG_TAG, message);
            }
        }
    }

    public static synchronized void v(int message) {
        synchronized (L.class) {
            if (sLogLevel <= 2) {
                String str = DEFAULT_LOG_TAG;
                StringBuilder sb = new StringBuilder();
                sb.append(message);
                sb.append("");
                v(str, sb.toString());
            }
        }
    }

    public static synchronized void v(String tag, int message) {
        synchronized (L.class) {
            if (sLogLevel <= 2) {
                StringBuilder sb = new StringBuilder();
                sb.append(message);
                sb.append("");
                v(tag, sb.toString());
            }
        }
    }

    public static synchronized void d(String tag, String message) {
        synchronized (L.class) {
            if (sLogLevel <= 4) {
                Log.d(tag, message);
            }
        }
    }

    public static synchronized void w(String tag, String message) {
        synchronized (L.class) {
            if (sLogLevel <= 5) {
                Log.w(tag, message);
            }
        }
    }

    public static synchronized void i(String tag, String message) {
        synchronized (L.class) {
            if (sLogLevel <= 3) {
                Log.i(tag, message);
            }
        }
    }

    public static synchronized void e(String tag, String message) {
        synchronized (L.class) {
            if (sLogLevel <= 6) {
                Log.e(tag, message);
            }
        }
    }
}
