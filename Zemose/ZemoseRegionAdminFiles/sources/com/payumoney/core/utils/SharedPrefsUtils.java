package com.payumoney.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.payumoney.core.PayUmoneyConstants;

public class SharedPrefsUtils {

    public static class Keys {
        public static final String ACCESS_TOKEN = "access_token";
        public static final String ADDED_ON = "LAST_LOGIN";
        public static final String AVATAR = "AVATAR";
        public static final String DISPLAY_NAME = "display_name";
        public static final String EMAIL = "email";
        public static final String MAX_WALLET_BALANCE = "maxLimit";
        public static final String MERCHANT_NAME = "merchant_name";
        public static final String MIN_WALLET_BALANCE = "minLimit";
        public static final String MY_BILLS_BADGE_COUNT = "my_bills_badge_count";
        public static final String P2P_PENDING_AMOUNT = "p2p_pending_amount";
        public static final String P2P_PENDING_COUNT = "p2p_pending_count";
        public static final String PHONE = "phone";
        public static final String REFRESH_TOKEN = "refresh_token";
        public static final String USER_ID = "userId";
        public static final String USER_TYPE = "userType";
        public static final String WALLET_BALANCE = "wallet_balance";
    }

    private SharedPrefsUtils() {
    }

    public static String getStringPreference(Context context, String key) {
        if (context == null) {
            return null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(PayUmoneyConstants.SP_SP_NAME, 0);
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, null);
        }
        return null;
    }

    public static String getStringSharedPreference(Context context, String fileName, String key) {
        return context.getSharedPreferences(fileName, 0).getString(key, "");
    }

    public static void setStringSharedPreference(Context context, String fileName, String key, String value) {
        Editor edit = context.getSharedPreferences(fileName, 0).edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static void deleteKey(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PayUmoneyConstants.SP_SP_NAME, 0);
        if (context != null && sharedPreferences != null && !TextUtils.isEmpty(key)) {
            Editor edit = sharedPreferences.edit();
            edit.remove(key);
            edit.apply();
        }
    }

    public static void deleteSharedPrefKey(Context context, String fileName, String key) {
        try {
            Editor edit = context.getSharedPreferences(fileName, 0).edit();
            edit.remove(key);
            edit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getBooleanPreference(Context context, String key) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(PayUmoneyConstants.SP_SP_NAME, 0);
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, false);
        }
        return false;
    }

    public static float getFloatPreference(Context context, String key) {
        if (context == null) {
            return 0.0f;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(PayUmoneyConstants.SP_SP_NAME, 0);
        if (sharedPreferences != null) {
            return sharedPreferences.getFloat(key, 0.0f);
        }
        return 0.0f;
    }

    public static float getLongPreference(Context context, String key) {
        long j = 0;
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PayUmoneyConstants.SP_SP_NAME, 0);
            if (sharedPreferences != null) {
                j = sharedPreferences.getLong(key, 0);
            }
        }
        return (float) j;
    }

    public static int getIntPreference(Context context, String key) {
        if (context == null) {
            return 0;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(PayUmoneyConstants.SP_SP_NAME, 0);
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, 0);
        }
        return 0;
    }

    public static boolean setStringPreference(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PayUmoneyConstants.SP_SP_NAME, 0);
        if (context == null || sharedPreferences == null || TextUtils.isEmpty(key)) {
            return false;
        }
        Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        return edit.commit();
    }

    public static boolean setBooleanPreference(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PayUmoneyConstants.SP_SP_NAME, 0);
        if (context == null || sharedPreferences == null || TextUtils.isEmpty(key)) {
            return false;
        }
        Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        return edit.commit();
    }

    public static boolean setFloatPreference(Context context, String key, float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PayUmoneyConstants.SP_SP_NAME, 0);
        if (context == null || sharedPreferences == null || TextUtils.isEmpty(key)) {
            return false;
        }
        Editor edit = sharedPreferences.edit();
        edit.putFloat(key, value);
        return edit.commit();
    }

    public static boolean setIntPreference(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PayUmoneyConstants.SP_SP_NAME, 0);
        if (context == null || sharedPreferences == null || TextUtils.isEmpty(key)) {
            return false;
        }
        Editor edit = sharedPreferences.edit();
        edit.putInt(key, value);
        return edit.commit();
    }

    public static boolean setLongPreference(Context context, String key, long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PayUmoneyConstants.SP_SP_NAME, 0);
        if (context == null || sharedPreferences == null || TextUtils.isEmpty(key)) {
            return false;
        }
        Editor edit = sharedPreferences.edit();
        edit.putLong(key, value);
        return edit.commit();
    }

    public static boolean removePreferenceByKey(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PayUmoneyConstants.SP_SP_NAME, 0);
        if (context == null || sharedPreferences == null || TextUtils.isEmpty(key)) {
            return false;
        }
        Editor edit = sharedPreferences.edit();
        edit.remove(key);
        edit.apply();
        return edit.commit();
    }

    public static boolean removeAllPreference(Context context) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PayUmoneyConstants.SP_SP_NAME, 0);
            if (sharedPreferences != null) {
                Editor edit = sharedPreferences.edit();
                edit.clear();
                edit.apply();
                return edit.commit();
            }
        }
        return false;
    }

    public static boolean hasKey(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PayUmoneyConstants.SP_SP_NAME, 0);
        if (context == null || sharedPreferences == null || TextUtils.isEmpty(key)) {
            return false;
        }
        return sharedPreferences.contains(key);
    }
}
