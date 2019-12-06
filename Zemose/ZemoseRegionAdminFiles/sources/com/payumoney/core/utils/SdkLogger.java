package com.payumoney.core.utils;

import android.util.Log;

public class SdkLogger {
    private static String a = "SDK_PAYU";
    private static final int b = a.length();

    private SdkLogger() {
    }

    public static String makeLogTag(String str) {
        if (str.length() > 23 - b) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append(str.substring(0, (23 - b) - 1));
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(a);
        sb2.append(str);
        return sb2.toString();
    }

    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public static void setTAG(String TAG) {
        a = TAG;
    }

    private static String a(String str) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        sb.append("[ ");
        sb.append(Thread.currentThread().getName());
        sb.append(": ");
        sb.append(stackTraceElement.getFileName());
        sb.append(": ");
        sb.append(stackTraceElement.getLineNumber());
        sb.append(": ");
        sb.append(stackTraceElement.getMethodName());
        sb.append("() ] --> ");
        sb.append(str);
        return sb.toString();
    }

    public static void v(String msg) {
        if (Log.isLoggable(a, 2)) {
            Log.v(a, a(msg));
        }
    }

    public static void d(String msg) {
        if (Log.isLoggable(a, 3)) {
            Log.d(a, a(msg));
        }
    }

    public static void i(String msg) {
        if (Log.isLoggable(a, 4)) {
            Log.i(a, a(msg));
        }
    }

    public static void w(String msg) {
        if (Log.isLoggable(a, 5)) {
            Log.w(a, a(msg));
        }
    }

    public static void w(String msg, Exception e) {
        if (Log.isLoggable(a, 5)) {
            Log.w(a, a(msg), e);
        }
    }

    public static void e(String msg) {
        if (Log.isLoggable(a, 6)) {
            Log.e(a, a(msg));
        }
    }

    public static void e(String msg, Exception e) {
        if (Log.isLoggable(a, 6)) {
            Log.e(a, a(msg), e);
        }
    }

    public static void v(String TAG, String msg) {
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, a(msg));
        }
    }

    public static void d(String TAG, String msg) {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, a(msg));
        }
    }

    public static void i(String TAG, String msg) {
        if (Log.isLoggable(TAG, 4)) {
            Log.i(TAG, a(msg));
        }
    }

    public static void w(String TAG, String msg) {
        if (Log.isLoggable(TAG, 5)) {
            Log.w(TAG, a(msg));
        }
    }

    public static void w(String TAG, String msg, Exception e) {
        if (Log.isLoggable(TAG, 5)) {
            Log.w(TAG, a(msg), e);
        }
    }

    public static void e(String TAG, String msg) {
        if (Log.isLoggable(TAG, 6)) {
            Log.e(TAG, a(msg));
        }
    }

    public static void e(String TAG, String msg, Exception e) {
        if (Log.isLoggable(TAG, 6)) {
            Log.e(TAG, a(msg), e);
        }
    }
}
