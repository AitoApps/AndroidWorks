package com.payumoney.sdkui.ui.utils;

import android.app.Activity;
import android.app.Application;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.widget.Toast;
import java.text.MessageFormat;

public class ToastUtils {
    private static void a(Activity activity, final int i, final int i2) {
        if (activity != null) {
            final Application application = activity.getApplication();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(application, i, i2).show();
                }
            });
        }
    }

    private static void a(Activity activity, final String str, final int i) {
        if (activity != null && !TextUtils.isEmpty(str)) {
            final Application application = activity.getApplication();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(application, str, i).show();
                }
            });
        }
    }

    public static void showLong(Activity activity, int resId) {
        a(activity, resId, 1);
    }

    public static void showShort(Activity activity, int resId) {
        a(activity, resId, 0);
    }

    public static void showLong(Activity activity, String message, boolean showSnackbar) {
        if (activity != null && message != null && !TextUtils.isEmpty(message)) {
            if (showSnackbar) {
                Snackbar.make(activity.findViewById(16908290), (CharSequence) message, 0).show();
            } else {
                a(activity, message, 1);
            }
        }
    }

    public static void showShort(Activity activity, String message, boolean showSnackbar) {
        if (activity != null && message != null && !TextUtils.isEmpty(message)) {
            if (showSnackbar) {
                Snackbar.make(activity.findViewById(16908290), (CharSequence) message, -1).show();
            } else {
                a(activity, message, 0);
            }
        }
    }

    public static void showLong(Activity activity, String message, Object... args) {
        a(activity, MessageFormat.format(message, args), 1);
    }

    public static void showShort(Activity activity, String message, Object... args) {
        a(activity, MessageFormat.format(message, args), 0);
    }

    public static void showLong(Activity activity, int resId, Object... args) {
        if (activity != null) {
            showLong(activity, activity.getString(resId), args);
        }
    }

    public static void showShort(Activity activity, int resId, Object... args) {
        if (activity != null) {
            showShort(activity, activity.getString(resId), args);
        }
    }
}
