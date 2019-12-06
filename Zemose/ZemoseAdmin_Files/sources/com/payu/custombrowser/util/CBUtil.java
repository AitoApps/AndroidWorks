package com.payu.custombrowser.util;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationCompat.Style;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import com.bumptech.glide.load.Key;
import com.payu.custombrowser.R;
import com.payu.magicretry.Helpers.MRConstant;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.utils.AnalyticsConstant;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CBUtil {
    public static final String CB_JS_PREFERENCE = "com.payu.custombrowser.payucustombrowser.js";
    public static final String CB_PREFERENCE = "com.payu.custombrowser.payucustombrowser";
    private static SharedPreferences a;

    public static String getSystemCurrentTime() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getLogMessage(Context context, String key, String value, String bank, String sdkMerchantKey, String trnxID, String pageType) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(MRConstant.PAYU_ID, getCookie("PAYUID", context));
            jSONObject.put("txnid", trnxID);
            jSONObject.put(MRConstant.MERCHANT_KEY, sdkMerchantKey);
            jSONObject.put(MRConstant.PAGE_TYPE, pageType == null ? "" : pageType);
            jSONObject.put("event_key", key);
            jSONObject.put("event_value", URLEncoder.encode(value, Key.STRING_CHARSET_NAME));
            jSONObject.put("bank", bank == null ? "" : bank);
            jSONObject.put("package_name", context.getPackageName());
            jSONObject.put(AnalyticsConstant.TS, getSystemCurrentTime());
            return jSONObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public static String decodeContents(FileInputStream fileInputStream) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            try {
                int read = fileInputStream.read();
                if (read == -1) {
                    break;
                }
                if (i % 2 == 0) {
                    sb.append((char) (read - ((i % 5) + 1)));
                } else {
                    sb.append((char) (read + (i % 5) + 1));
                }
                i++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fileInputStream.close();
        return sb.toString();
    }

    public static void setAlpha(float alpha, View view) {
        if (VERSION.SDK_INT < 11) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(alpha, alpha);
            alphaAnimation.setDuration(10);
            alphaAnimation.setFillAfter(true);
            view.startAnimation(alphaAnimation);
            return;
        }
        view.setAlpha(alpha);
    }

    public static String updateLastUrl(String lastUrl) {
        try {
            if (lastUrl.contains(CBConstant.CB_DELIMITER)) {
                StringTokenizer stringTokenizer = new StringTokenizer(lastUrl, CBConstant.CB_DELIMITER);
                String nextToken = stringTokenizer.nextToken();
                String nextToken2 = stringTokenizer.nextToken();
                if (nextToken.length() > 128) {
                    nextToken = nextToken.substring(0, 125);
                }
                if (nextToken2.length() > 128) {
                    nextToken2 = nextToken2.substring(0, 125);
                }
                StringBuilder sb = new StringBuilder();
                sb.append(nextToken);
                sb.append(CBConstant.CB_DELIMITER);
                sb.append(nextToken2);
                return sb.toString();
            } else if (lastUrl.length() > 128) {
                return lastUrl.substring(0, 127);
            } else {
                return lastUrl;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void setVariableReflection(String className, String value, String varName) {
        if (value != null) {
            try {
                if (!value.trim().equals("")) {
                    Field declaredField = Class.forName(className).getDeclaredField(varName);
                    declaredField.setAccessible(true);
                    declaredField.set(null, value);
                    declaredField.setAccessible(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String filterSMS(JSONObject mBankJS, String msgBody, Context context) {
        String[] split;
        String str = null;
        if (msgBody != null) {
            try {
                if (Pattern.compile(mBankJS.getString(context.getString(R.string.cb_detect_otp)), 2).matcher(msgBody).find()) {
                    for (String str2 : mBankJS.getString(context.getString(R.string.cb_find_new_otp)).split("::")) {
                        if (!TextUtils.isEmpty(str2)) {
                            Matcher matcher = Pattern.compile(str2, 2).matcher(msgBody);
                            if (matcher.find()) {
                                str = matcher.group().trim().replaceAll("\\.", "");
                                if (!TextUtils.isEmpty(str)) {
                                    break;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public HttpsURLConnection getHttpsConn(String strURL, String postData) {
        try {
            return getHttpsConn(strURL, postData, -1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HttpsURLConnection getHttpsConn(String strURL, String postData, int timeout, String cookiesList) {
        try {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(strURL).openConnection();
            httpsURLConnection.setRequestMethod("POST");
            if (timeout != -1) {
                httpsURLConnection.setConnectTimeout(timeout);
            }
            httpsURLConnection.setRequestProperty("Content-Type", CBConstant.HTTP_URLENCODED);
            if (postData != null) {
                httpsURLConnection.setRequestProperty("Content-Length", String.valueOf(postData.length()));
            }
            if (cookiesList != null) {
                httpsURLConnection.setRequestProperty("Cookie", cookiesList);
            }
            httpsURLConnection.setSSLSocketFactory(new g());
            httpsURLConnection.setDoOutput(true);
            if (postData != null) {
                httpsURLConnection.getOutputStream().write(postData.getBytes());
            }
            return httpsURLConnection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HttpsURLConnection getHttpsConn(String strURL, String postData, int timeout) {
        return getHttpsConn(strURL, postData, timeout, null);
    }

    public static HttpsURLConnection getHttpsConn(String strURL) {
        try {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(strURL).openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setSSLSocketFactory(new g());
            httpsURLConnection.setRequestProperty("Accept-Charset", Key.STRING_CHARSET_NAME);
            return httpsURLConnection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static StringBuffer getStringBufferFromInputStream(InputStream responseInputStream) {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            byte[] bArr = new byte[1024];
            while (true) {
                int read = responseInputStream.read(bArr);
                if (read == -1) {
                    return stringBuffer;
                }
                stringBuffer.append(new String(bArr, 0, read));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected();
    }

    public static List<String> updateRetryData(String data2, Context context) {
        a(data2, context);
        return processAndAddWhiteListedUrls(data2);
    }

    private static void a(String str, Context context) {
        if (str == null) {
            f.a(context, CBConstant.SP_RETRY_FILE_NAME, CBConstant.SP_RETRY_WHITELISTED_URLS, "");
        } else {
            f.a(context, CBConstant.SP_RETRY_FILE_NAME, CBConstant.SP_RETRY_WHITELISTED_URLS, str);
        }
        c.a("#### PAYU", "DATA UPDATED IN SHARED PREFERENCES");
    }

    public void clearCookie() {
        CookieManager instance = CookieManager.getInstance();
        if (VERSION.SDK_INT >= 21) {
            instance.removeSessionCookies(null);
        } else {
            instance.removeSessionCookie();
        }
    }

    public static List<String> processAndAddWhiteListedUrls(String data2) {
        if (data2 != null && !data2.equalsIgnoreCase("")) {
            String[] split = data2.split("\\|");
            for (String str : split) {
                StringBuilder sb = new StringBuilder();
                sb.append("Split Url: ");
                sb.append(str);
                c.a("#### PAYU", sb.toString());
            }
            if (split != null && split.length > 0) {
                return Arrays.asList(split);
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Whitelisted URLs from JS: ");
            sb2.append(data2);
            c.a("#### PAYU", sb2.toString());
        }
        return new ArrayList();
    }

    public boolean getBooleanSharedPreference(String key, Context context) {
        a = context.getSharedPreferences(CB_PREFERENCE, 0);
        return a.getBoolean(key, false);
    }

    public boolean getBooleanSharedPreferenceDefaultTrue(String key, Context context) {
        a = context.getSharedPreferences(CB_PREFERENCE, 0);
        return a.getBoolean(key, true);
    }

    public void setBooleanSharedPreference(String key, boolean value, Context context) {
        Editor edit = context.getSharedPreferences(CB_PREFERENCE, 0).edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public String getDeviceDensity(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        StringBuilder sb = new StringBuilder();
        sb.append(displayMetrics.densityDpi);
        sb.append("");
        return sb.toString();
    }

    public String getNetworkStatus(Context context) {
        if (context != null) {
            try {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    if (activeNetworkInfo.isConnected()) {
                        if (activeNetworkInfo.getType() == 1) {
                            return "WIFI";
                        }
                        if (activeNetworkInfo.getType() == 0) {
                            switch (activeNetworkInfo.getSubtype()) {
                                case 1:
                                    return "GPRS";
                                case 2:
                                    return "EDGE";
                                case 3:
                                case 5:
                                case 6:
                                case 8:
                                case 9:
                                case 10:
                                    return "HSPA";
                                case 4:
                                    return "CDMA";
                                case 7:
                                case 11:
                                    return "2G";
                                case 12:
                                case 14:
                                case 15:
                                    return "3G";
                                case 13:
                                    return "4G";
                                default:
                                    return "?";
                            }
                        }
                    }
                }
                return "Not connected";
            } catch (Exception e) {
                return "?";
            }
        }
        return "?";
    }

    public NetworkInfo getNetWorkInfo(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService("connectivity");
        int i = 0;
        NetworkInfo networkInfo = null;
        if (VERSION.SDK_INT >= 21) {
            Network[] allNetworks = connectivityManager.getAllNetworks();
            int length = allNetworks.length;
            while (i < length) {
                NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(allNetworks[i]);
                if (networkInfo2.getState().equals(State.CONNECTED)) {
                    networkInfo = networkInfo2;
                }
                i++;
            }
        } else {
            NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
            if (allNetworkInfo != null) {
                int length2 = allNetworkInfo.length;
                while (i < length2) {
                    NetworkInfo networkInfo3 = allNetworkInfo[i];
                    if (networkInfo3.getState() == State.CONNECTED) {
                        networkInfo = networkInfo3;
                    }
                    i++;
                }
            }
        }
        return networkInfo;
    }

    public int getNetworkStrength(Context mContext) {
        NetworkInfo netWorkInfo = getNetWorkInfo(mContext);
        if (netWorkInfo == null) {
            return 0;
        }
        if (netWorkInfo.getTypeName().equalsIgnoreCase("MOBILE")) {
            return a(mContext, netWorkInfo);
        }
        if (netWorkInfo.getTypeName().equalsIgnoreCase(PayUmoneyConstants.IS_WIFI) && a(mContext, "android.permission.ACCESS_WIFI_STATE")) {
            try {
                WifiInfo connectionInfo = ((WifiManager) mContext.getSystemService(PayUmoneyConstants.IS_WIFI)).getConnectionInfo();
                if (connectionInfo != null) {
                    return WifiManager.calculateSignalLevel(connectionInfo.getRssi(), 5);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private boolean a(Context context, String str) {
        return context.checkCallingOrSelfPermission(str) == 0;
    }

    public boolean hasRequestedPermission(Context context, String permission) {
        String[] strArr;
        try {
            for (String str : context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions) {
                StringBuilder sb = new StringBuilder();
                sb.append("hasPermission: ");
                sb.append(str);
                c.b("CBUtil", sb.toString());
                if (permission.equalsIgnoreCase(str)) {
                    return true;
                }
            }
        } catch (NameNotFoundException e) {
            e.getMessage();
        }
        return false;
    }

    private int a(Context context, NetworkInfo networkInfo) {
        int i = 0;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (VERSION.SDK_INT >= 18) {
                int i2 = 0;
                for (CellInfo cellInfo : telephonyManager.getAllCellInfo()) {
                    if (cellInfo.isRegistered()) {
                        if (cellInfo instanceof CellInfoGsm) {
                            i2 = ((CellInfoGsm) cellInfo).getCellSignalStrength().getDbm();
                        } else if (cellInfo instanceof CellInfoCdma) {
                            i2 = ((CellInfoCdma) cellInfo).getCellSignalStrength().getDbm();
                        } else if (cellInfo instanceof CellInfoLte) {
                            i2 = ((CellInfoLte) cellInfo).getCellSignalStrength().getDbm();
                        } else if (cellInfo instanceof CellInfoWcdma) {
                            i2 = ((CellInfoWcdma) cellInfo).getCellSignalStrength().getDbm();
                        }
                    }
                }
                i = i2;
            }
            return i;
        } catch (Exception e) {
            return 0;
        }
    }

    public void setStringSharedPreferenceLastURL(Context context, String key, String url) {
        String str;
        String stringSharedPreference = getStringSharedPreference(context, key);
        if (stringSharedPreference.equalsIgnoreCase("")) {
            str = url;
        } else if (!stringSharedPreference.contains(CBConstant.CB_DELIMITER)) {
            StringBuilder sb = new StringBuilder();
            sb.append(stringSharedPreference);
            sb.append(CBConstant.CB_DELIMITER);
            sb.append(url);
            str = sb.toString();
        } else {
            StringTokenizer stringTokenizer = new StringTokenizer(stringSharedPreference, CBConstant.CB_DELIMITER);
            stringTokenizer.nextToken();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(stringTokenizer.nextToken());
            sb2.append(CBConstant.CB_DELIMITER);
            sb2.append(url);
            str = sb2.toString();
        }
        storeInSharedPreferences(context, key, str);
    }

    public String getStringSharedPreference(Context context, String key) {
        return context.getSharedPreferences(CB_PREFERENCE, 0).getString(key, "");
    }

    public String getJSStringSharedPreference(Context context, String key, boolean isPersistent) {
        if (isPersistent) {
            return context.getSharedPreferences(CB_PREFERENCE, 0).getString(key, CBConstant.UNDEFINED);
        }
        return context.getSharedPreferences(CB_JS_PREFERENCE, 0).getString(key, CBConstant.UNDEFINED);
    }

    public void removeJSStringSharedPreference(Context context, String key) {
        Editor edit = context.getSharedPreferences(CB_JS_PREFERENCE, 0).edit();
        edit.remove(key);
        edit.apply();
    }

    public void clearJSStringSharedPreference(Context context) {
        Editor edit = context.getSharedPreferences(CB_JS_PREFERENCE, 0).edit();
        edit.clear();
        edit.apply();
    }

    public void setStringSharedPreference(Context context, String key, String value) {
        Editor edit = context.getSharedPreferences(CB_PREFERENCE, 0).edit();
        edit.putString(key, value);
        edit.commit();
    }

    public void setJSStringSharedPreference(Context context, String key, String value) {
        Editor edit = context.getSharedPreferences(CB_JS_PREFERENCE, 0).edit();
        edit.putString(key, value);
        edit.commit();
    }

    public void deleteSharedPrefKey(Context context, String key) {
        try {
            Editor edit = context.getSharedPreferences(CB_PREFERENCE, 0).edit();
            edit.remove(key);
            edit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void storeInSharedPreferences(Context context, String key, String value) {
        Editor edit = context.getSharedPreferences(CB_PREFERENCE, 0).edit();
        edit.putString(key, value);
        edit.apply();
    }

    public void removeFromSharedPreferences(Context context, String key) {
        Editor edit = context.getSharedPreferences(CB_PREFERENCE, 0).edit();
        edit.remove(key);
        edit.apply();
    }

    public Drawable getDrawableCB(Context context, int resID) {
        if (VERSION.SDK_INT < 21) {
            return context.getResources().getDrawable(resID);
        }
        return context.getResources().getDrawable(resID, context.getTheme());
    }

    public void cancelTimer(Timer timer) {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    public String readFileInputStream(Context mContext, String fileName, int contextMode) {
        String str = "";
        try {
            if (!new File(mContext.getFilesDir(), fileName).exists()) {
                mContext.openFileOutput(fileName, contextMode);
            }
            FileInputStream openFileInput = mContext.openFileInput(fileName);
            while (true) {
                int read = openFileInput.read();
                if (read == -1) {
                    break;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(Character.toString((char) read));
                str = sb.toString();
            }
            openFileInput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return str;
    }

    public void writeFileOutputStream(InputStream inputStream, Context context, String fileName, int contextMode) {
        try {
            GZIPInputStream gZIPInputStream = new GZIPInputStream(inputStream);
            byte[] bArr = new byte[1024];
            FileOutputStream openFileOutput = context.openFileOutput(fileName, contextMode);
            while (true) {
                int read = gZIPInputStream.read(bArr);
                if (read > 0) {
                    openFileOutput.write(bArr, 0, read);
                } else {
                    gZIPInputStream.close();
                    openFileOutput.close();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void resetPayuID() {
        clearCookie();
    }

    public String getCookieList(Context appContext, String domainName) {
        String str = "";
        try {
            CookieManager instance = CookieManager.getInstance();
            if (VERSION.SDK_INT < 21) {
                CookieSyncManager.createInstance(appContext);
                CookieSyncManager.getInstance().sync();
            }
            String cookie = instance.getCookie(domainName);
            if (cookie != null) {
                String[] split = cookie.split(";");
                int length = split.length;
                String str2 = str;
                int i = 0;
                while (i < length) {
                    try {
                        String[] split2 = split[i].split("=");
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        sb.append(split2[0]);
                        sb.append("=");
                        sb.append(split2[1]);
                        sb.append(";");
                        str2 = sb.toString();
                        i++;
                    } catch (Exception e) {
                        e = e;
                        str = str2;
                        e.printStackTrace();
                        return str;
                    }
                }
                str = str2;
            }
            if (str.length() > 0) {
                return str.substring(0, str.length() - 1);
            }
            return str;
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            return str;
        }
    }

    public static String getCookie(String cookieName, Context context) {
        String[] split;
        String str = "";
        String str2 = "https://secure.payu.in";
        try {
            CookieManager instance = CookieManager.getInstance();
            if (VERSION.SDK_INT < 21) {
                CookieSyncManager.createInstance(context);
                CookieSyncManager.getInstance().sync();
            }
            String cookie = instance.getCookie(str2);
            if (cookie != null) {
                for (String str3 : cookie.split(";")) {
                    if (str3.contains(cookieName)) {
                        str = str3.split("=")[1];
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    @Deprecated
    public String getDataFromPostData(String postData, String key) {
        for (String split : postData.split("&")) {
            String[] split2 = split.split("=");
            if (split2.length >= 2 && split2[0].equalsIgnoreCase(key)) {
                return split2[1];
            }
        }
        return "";
    }

    public HashMap<String, String> getDataFromPostData(String postData) {
        HashMap<String, String> hashMap = new HashMap<>();
        if (postData != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(postData, "&");
            while (stringTokenizer.hasMoreTokens()) {
                String[] split = stringTokenizer.nextToken().split("=");
                if (!(split == null || split.length <= 0 || split[0] == null)) {
                    hashMap.put(split[0], split.length > 1 ? split[1] : "");
                }
            }
        }
        return hashMap;
    }

    public PaymentOption getPaymentOptionFromPostData(String postData) {
        PaymentOption[] values;
        if (!TextUtils.isEmpty(postData)) {
            String dataFromPostData = getDataFromPostData(postData, PayUmoneyConstants.BANK_CODE);
            if (!TextUtils.isEmpty(dataFromPostData)) {
                for (PaymentOption paymentOption : PaymentOption.values()) {
                    if (paymentOption.getPaymentName().equalsIgnoreCase(dataFromPostData)) {
                        return paymentOption;
                    }
                }
            }
        }
        return null;
    }

    public void showNotification(Context context, Intent intent, String title, String txt, int smallIcon, boolean autoCancel, Style style, int color, int ID) {
        Builder builder = new Builder(context);
        builder.setContentTitle(title).setContentText(txt).setSmallIcon(smallIcon).setPriority(1).setDefaults(2);
        if (autoCancel) {
            builder.setAutoCancel(autoCancel);
        }
        if (style != null) {
            builder.setStyle(style);
        }
        if (color != -1) {
            builder.setColor(color);
        }
        builder.setContentIntent(PendingIntent.getActivity(context, 0, intent, 134217728));
        ((NotificationManager) context.getSystemService("notification")).notify(ID, builder.build());
    }

    public SnoozeConfigMap storeSnoozeConfigInSharedPref(Context context, String snoozeConfig) {
        SnoozeConfigMap snoozeConfigMap = new SnoozeConfigMap();
        try {
            JSONObject jSONObject = new JSONObject(snoozeConfig);
            f.b(context, CBConstant.SNOOZE_SHARED_PREF);
            SnoozeConfigMap a2 = a(context, jSONObject.getJSONArray(CBConstant.DEFAULT_VALUE), snoozeConfigMap);
            jSONObject.remove(CBConstant.DEFAULT_VALUE);
            Iterator keys = jSONObject.keys();
            if (keys.hasNext()) {
                return a(context, jSONObject.getJSONArray((String) keys.next()), a2);
            }
            return a2;
        } catch (JSONException e) {
            e.printStackTrace();
            return snoozeConfigMap;
        }
    }

    private SnoozeConfigMap a(Context context, JSONArray jSONArray, SnoozeConfigMap snoozeConfigMap) {
        try {
            int length = jSONArray.length();
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                String obj = jSONObject.get("url").toString();
                String obj2 = jSONObject.get(CBConstant.PROGRESS_PERCENT).toString();
                String obj3 = jSONObject.get(CBConstant.TIME_OUT).toString();
                if (jSONObject.has(CBConstant.DISABLE_SP_FOR)) {
                    i = a(jSONObject.getJSONObject(CBConstant.DISABLE_SP_FOR));
                }
                StringTokenizer stringTokenizer = new StringTokenizer(obj, CBConstant.CB_DELIMITER);
                while (stringTokenizer.hasMoreTokens()) {
                    String nextToken = stringTokenizer.nextToken();
                    String str = CBConstant.SNOOZE_SHARED_PREF;
                    String trim = nextToken.contentEquals(CBConstant.DEFAULT_PAYMENT_URLS) ? CBConstant.DEFAULT_PAYMENT_URLS : nextToken.trim();
                    StringBuilder sb = new StringBuilder();
                    sb.append(obj2.trim());
                    sb.append(CBConstant.CB_DELIMITER);
                    sb.append(obj3.trim());
                    sb.append(CBConstant.CB_DELIMITER);
                    sb.append(i);
                    f.a(context, str, trim, sb.toString());
                    String trim2 = nextToken.contentEquals(CBConstant.DEFAULT_PAYMENT_URLS) ? CBConstant.DEFAULT_PAYMENT_URLS : nextToken.trim();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(obj2.trim());
                    sb2.append(CBConstant.CB_DELIMITER);
                    sb2.append(obj3.trim());
                    sb2.append(CBConstant.CB_DELIMITER);
                    sb2.append(i);
                    snoozeConfigMap.put(trim2, sb2.toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return snoozeConfigMap;
    }

    public SnoozeConfigMap convertToSnoozeConfigMap(Map<String, ?> snoozeMap) {
        SnoozeConfigMap snoozeConfigMap = new SnoozeConfigMap();
        for (Entry entry : snoozeMap.entrySet()) {
            snoozeConfigMap.put(entry.getKey(), entry.getValue());
        }
        return snoozeConfigMap;
    }

    public Set<String> getSurePayErrorCodes() {
        HashSet hashSet = new HashSet();
        hashSet.add("-7");
        hashSet.add("-8");
        hashSet.add("-15");
        return hashSet;
    }

    private int a(JSONObject jSONObject) {
        try {
            if (jSONObject.has(CBConstant.WARN) && jSONObject.getBoolean(CBConstant.WARN)) {
                if (jSONObject.has(CBConstant.FAIL) && jSONObject.getBoolean(CBConstant.FAIL)) {
                    return 3;
                }
            }
            if (jSONObject.has(CBConstant.FAIL) && jSONObject.getBoolean(CBConstant.FAIL)) {
                return 2;
            }
            if (!jSONObject.has(CBConstant.WARN) || !jSONObject.getBoolean(CBConstant.WARN)) {
                return 0;
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getSurePayDisableStatus(SnoozeConfigMap snoozeConfigMap, String webViewUrl) {
        if (snoozeConfigMap == null || webViewUrl == null) {
            return 0;
        }
        for (Object next : snoozeConfigMap.keySet()) {
            if (webViewUrl.startsWith(next.toString())) {
                return snoozeConfigMap.getPercentageAndTimeout(next.toString())[2];
            }
        }
        return snoozeConfigMap.getPercentageAndTimeout(CBConstant.DEFAULT_PAYMENT_URLS)[2];
    }

    public String getValueOfJSONKey(String data2, String key) throws JSONException {
        JSONObject jSONObject = new JSONObject(data2);
        if (jSONObject.has(key)) {
            return jSONObject.get(key).toString();
        }
        throw new JSONException("Key not found");
    }

    public static void launchPlayStore(Context context) {
        launchPlayStore(context, null, null);
    }

    public static void launchPlayStore(Context context, String url, String webViewVersion) {
        String str;
        String packageNameFromPlayStoreLink = getPackageNameFromPlayStoreLink(url);
        if (packageNameFromPlayStoreLink == null) {
            str = "";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("details?id=");
            sb.append(packageNameFromPlayStoreLink);
            str = sb.toString();
        }
        setWebViewVersionInSP(context, webViewVersion);
        try {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(CBConstant.PLAY_STORE_MARKET_URI);
            sb2.append(str);
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb2.toString())));
        } catch (ActivityNotFoundException e) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("https://play.google.com/store/apps/");
            sb3.append(str);
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb3.toString())));
        }
    }

    public static boolean isPlayStoreUrl(String url) {
        return url.startsWith(CBConstant.PLAY_STORE_URL) || url.startsWith(CBConstant.PLAY_STORE_MARKET_URI);
    }

    public static String getPackageNameFromPlayStoreLink(String url) {
        Matcher matcher = Pattern.compile("((?<=[?&]id=)[^&]+)").matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String getWebViewVersion(WebView view) {
        Matcher matcher = Pattern.compile("(Chrome\\/(.*?)\\s)").matcher(view.getSettings().getUserAgentString());
        if (matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }

    public static void setWebViewVersionInSP(Context context, String webViewVersion) {
        if (webViewVersion != null) {
            f.a(context, CB_PREFERENCE, CBConstant.WEBVIEW_VERSION, webViewVersion);
        }
    }

    public static String getWebViewVersionFromSP(Context context) {
        return f.b(context, CB_PREFERENCE, CBConstant.WEBVIEW_VERSION, "");
    }

    public static String getBase64DecodedString(String data2) {
        return new String(Base64.decode(data2, 0));
    }

    public static String getImei(Context applicationContex) {
        try {
            return ((TelephonyManager) applicationContex.getSystemService("phone")).getDeviceId();
        } catch (Exception e) {
            return CBConstant.DEFAULT_VALUE;
        }
    }

    public static String getUdid(Context applicationContex) {
        try {
            return Secure.getString(applicationContex.getContentResolver(), "android_id");
        } catch (Exception e) {
            return CBConstant.DEFAULT_VALUE;
        }
    }

    public static boolean isSPModuleAvailable() {
        try {
            CBUtil.class.getClassLoader().loadClass("com.payu.samsungpay.SamsungWrapper");
            return true;
        } catch (ClassNotFoundException e) {
            Log.e("CBUtil", "Please import com.payu.samsungpay to make Payment by Samsung Pay");
            return false;
        }
    }

    public static boolean isPaymentModuleAvailable(PaymentOption paymentOption) {
        try {
            CBUtil.class.getClassLoader().loadClass(paymentOption.getPackageName());
            return true;
        } catch (ClassNotFoundException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Please import com.payu.");
            sb.append(paymentOption.toString().toLowerCase());
            sb.append(" to make Payment by ");
            sb.append(paymentOption.toString());
            Log.e("CBUtil", sb.toString());
            return false;
        }
    }

    public static boolean isCustomTabSupported(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.example.com"));
        ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 0);
        if (resolveActivity != null) {
            String str = resolveActivity.activityInfo.packageName;
        }
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo resolveInfo : queryIntentActivities) {
            Intent intent2 = new Intent();
            intent2.setAction("android.support.customtabs.action.CustomTabsService");
            intent2.setPackage(resolveInfo.activityInfo.packageName);
            if (packageManager.resolveService(intent2, 0) != null) {
                arrayList.add(resolveInfo.activityInfo.packageName);
            }
        }
        if (!arrayList.isEmpty() && arrayList.contains("com.android.chrome")) {
            return true;
        }
        return false;
    }
}
