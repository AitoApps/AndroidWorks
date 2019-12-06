package com.payu.custombrowser.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import java.util.HashMap;
import java.util.Map;

public class f {
    public static void a(Context context, String str, String str2, String str3) {
        Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putString(str2, str3);
        edit.commit();
    }

    public static void a(Context context, String str, String str2, int i) {
        Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putInt(str2, i);
        edit.commit();
    }

    public static String b(Context context, String str, String str2, String str3) {
        return context.getSharedPreferences(str, 0).getString(str2, str3);
    }

    public static int b(Context context, String str, String str2, int i) {
        return context.getSharedPreferences(str, 0).getInt(str2, i);
    }

    public static Map<String, ?> a(Context context, String str) {
        new HashMap();
        return context.getSharedPreferences(str, 0).getAll();
    }

    public static void b(Context context, String str) {
        context.getSharedPreferences(str, 0).edit().clear().commit();
    }
}
