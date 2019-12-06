package com.payumoney.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import com.payumoney.core.BuildConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.PayUmoneySdkInitializer.PaymentParam;
import com.payumoney.core.analytics.LogAnalytics;
import com.payumoney.core.entity.PaymentEntity;
import com.payumoney.core.entity.PayumoneyConvenienceFee;
import com.payumoney.core.response.PaymentOptionDetails;
import com.payumoney.graphics.AssetDownloadManager.Environment;
import com.payumoney.graphics.AssetsHelper.CARD;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class SdkHelper {
    public static Set<String> SBI_MAES_BIN = new HashSet();
    private static long a = 0;
    public static String pnpVersion = null;

    static {
        SBI_MAES_BIN.add("504435");
        SBI_MAES_BIN.add("504645");
        SBI_MAES_BIN.add("504775");
        SBI_MAES_BIN.add("504809");
        SBI_MAES_BIN.add("504993");
        SBI_MAES_BIN.add("600206");
        SBI_MAES_BIN.add("603845");
        SBI_MAES_BIN.add("622018");
        SBI_MAES_BIN.add("504774");
    }

    public static boolean checkNetwork(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService("connectivity");
        boolean z = false;
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
            z = true;
        }
        return z;
    }

    public static boolean setStringPreference(Context context, String key, String value) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (defaultSharedPreferences == null || TextUtils.isEmpty(key)) {
            return false;
        }
        Editor edit = defaultSharedPreferences.edit();
        edit.putString(key, value);
        return edit.commit();
    }

    public static String getStringPreference(Context context, String key) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (defaultSharedPreferences != null) {
            return defaultSharedPreferences.getString(key, null);
        }
        return null;
    }

    public static String getAndroidID(Context context) {
        if (context == null) {
            return "";
        }
        return Secure.getString(context.getContentResolver(), "android_id");
    }

    public static long getLongPreference(Context context, String key) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (defaultSharedPreferences != null) {
            return defaultSharedPreferences.getLong(key, 0);
        }
        return 0;
    }

    public static boolean setLongPreference(Context context, String key, long value) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (defaultSharedPreferences == null || TextUtils.isEmpty(key)) {
            return false;
        }
        Editor edit = defaultSharedPreferences.edit();
        edit.putLong(key, value);
        return edit.commit();
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static boolean hasNFC(Context context) {
        if (context != null) {
            return context.getPackageManager().hasSystemFeature("android.hardware.nfc");
        }
        return false;
    }

    public static boolean hasTelephony(Context context) {
        if (context != null) {
            return context.getPackageManager().hasSystemFeature("android.hardware.telephony");
        }
        return false;
    }

    public static String getDeviceCustomPropertyJsonString(Context c) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PayUmoneyConstants.WIDTH, getScreenWidth(c));
            jSONObject.put(PayUmoneyConstants.HEIGHT, getScreenHeight(c));
            jSONObject.put(PayUmoneyConstants.IS_WIFI, isConnectedWifi(c));
            jSONObject.put(PayUmoneyConstants.NFC, hasNFC(c));
            jSONObject.put(PayUmoneyConstants.TELEPHONE, hasTelephony(c));
            jSONObject.put(PayUmoneyConstants.DEVICE_ID, getAndroidID(c));
            jSONObject.put(PayUmoneyConstants.DEVICE_NAME, getDeviceName());
            jSONObject.put(PayUmoneyConstants.OS_NAME, PayUmoneyConstants.OS_NAME_VALUE);
            jSONObject.put(PayUmoneyConstants.OS_VERSION, a());
            jSONObject.put(PayUmoneyConstants.BR_VERSION, PayUmoneyConstants.BR_VERSION_VALUE);
            return jSONObject.toString();
        } catch (JSONException e) {
            return "";
        }
    }

    private static String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(VERSION.RELEASE);
        sb.append("");
        return sb.toString();
    }

    public static String getDeviceName() {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        if (str2 != null && str2.startsWith(str)) {
            return a(str2);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(a(str));
        sb.append(":");
        sb.append(str2);
        return sb.toString();
    }

    private static String a(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        char charAt = str.charAt(0);
        if (Character.isUpperCase(charAt)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toUpperCase(charAt));
        sb.append(str.substring(1));
        return sb.toString();
    }

    public static boolean isUpdateSessionRequired(Context c) {
        if (c == null || getLongPreference(c, PayUmoneyConstants.LAST_USED_SESSION_SEND_TIMESTAMP) + PayUmoneyConstants.DEFAULT_SESSION_SEND_MAX_TIME >= System.currentTimeMillis()) {
            return false;
        }
        setLongPreference(c, PayUmoneyConstants.LAST_USED_SESSION_SEND_TIMESTAMP, System.currentTimeMillis());
        return true;
    }

    public static void resetSessionUpdateTimeStamp(Context c) {
        if (c != null) {
            setLongPreference(c, PayUmoneyConstants.LAST_USED_SESSION_SEND_TIMESTAMP, 0);
        }
    }

    public static synchronized String getUserSessionId(Context c) {
        String str;
        synchronized (SdkHelper.class) {
            if (c == null) {
                String str2 = "";
                return str2;
            }
            if (getLongPreference(c, PayUmoneyConstants.LAST_USED_SESSION_TIMESTAMP) + PayUmoneyConstants.DEFAULT_SESSION_UPDATE_TIME < System.currentTimeMillis()) {
                StringBuilder sb = new StringBuilder();
                sb.append(getAndroidID(c));
                sb.append("_");
                sb.append(c.getPackageName());
                sb.append("_");
                sb.append(System.currentTimeMillis());
                str = sb.toString();
                setStringPreference(c, PayUmoneyConstants.LAST_SESSION_ID, str);
            } else {
                str = getStringPreference(c, PayUmoneyConstants.LAST_SESSION_ID);
                if (TextUtils.isEmpty(str)) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(getAndroidID(c));
                    sb2.append("_");
                    sb2.append(c.getPackageName());
                    sb2.append("_");
                    sb2.append(System.currentTimeMillis());
                    str = sb2.toString();
                    setStringPreference(c, PayUmoneyConstants.LAST_SESSION_ID, str);
                }
            }
            setLongPreference(c, PayUmoneyConstants.LAST_USED_SESSION_TIMESTAMP, System.currentTimeMillis());
            return str;
        }
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
    }

    public static boolean isConnectedWifi(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == 1;
    }

    public static boolean validateCardNumber(String ccNumber, String issuer) {
        try {
            int i = 0;
            boolean z = false;
            for (int length = ccNumber.length() - 1; length >= 0; length--) {
                int parseInt = Integer.parseInt(ccNumber.substring(length, length + 1));
                if (z) {
                    parseInt *= 2;
                    if (parseInt > 9) {
                        parseInt = (parseInt % 10) + 1;
                    }
                }
                i += parseInt;
                z = !z;
            }
            if (i % 10 == 0) {
                if (issuer != null) {
                    if (!issuer.trim().equalsIgnoreCase("")) {
                        if (issuer.contentEquals("VISA") && ccNumber.length() >= 13 && ccNumber.length() <= 19) {
                            return true;
                        }
                        if (issuer.contentEquals(PayUmoneyConstants.LASER) && ccNumber.length() >= 16 && ccNumber.length() <= 19) {
                            return true;
                        }
                        if (issuer.contentEquals(PayUmoneyConstants.MASTER) && ccNumber.length() == 16) {
                            return true;
                        }
                        if (issuer.contentEquals(PayUmoneyConstants.MAESTRO) && ccNumber.length() >= 12 && ccNumber.length() <= 19) {
                            return true;
                        }
                        if (issuer.contentEquals(PayUmoneyConstants.DINR) && (ccNumber.length() == 14 || ccNumber.length() == 16)) {
                            return true;
                        }
                        if (issuer.contentEquals("AMEX") && ccNumber.length() == 15) {
                            return true;
                        }
                        if (issuer.contentEquals("JCB") && ccNumber.length() >= 16 && ccNumber.length() <= 19) {
                            return true;
                        }
                        if (issuer.contentEquals(PayUmoneyConstants.RUPAY) && ccNumber.length() >= 16 && ccNumber.length() <= 19) {
                            return true;
                        }
                        if (!issuer.contentEquals(PayUmoneyConstants.SMAE) || ccNumber.length() != 19) {
                            return ccNumber.matches("6(?:011|5[0-9]{2})[0-9]{12}") && ccNumber.length() == 16;
                        }
                        return true;
                    }
                }
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static String getCardType(String cardType) {
        char c;
        switch (cardType.hashCode()) {
            case -1386658451:
                if (cardType.equals(PayUmoneyConstants.DINR_CLUB)) {
                    c = 3;
                    break;
                }
            case 73257:
                if (cardType.equals("JCB")) {
                    c = 11;
                    break;
                }
            case 2012639:
                if (cardType.equals("AMEX")) {
                    c = 1;
                    break;
                }
            case 2098441:
                if (cardType.equals(PayUmoneyConstants.DINR)) {
                    c = 2;
                    break;
                }
            case 2358594:
                if (cardType.equals(PayUmoneyConstants.MAESTRO)) {
                    c = 8;
                    break;
                }
            case 2359029:
                if (cardType.equals(PayUmoneyConstants.MASTER)) {
                    c = 0;
                    break;
                }
            case 2548734:
                if (cardType.equals(PayUmoneyConstants.SMAE)) {
                    c = 6;
                    break;
                }
            case 2634817:
                if (cardType.equals("VISA")) {
                    c = 10;
                    break;
                }
            case 78339941:
                if (cardType.equals(PayUmoneyConstants.RUPAY)) {
                    c = 4;
                    break;
                }
            case 169850204:
                if (cardType.equals("SMAESTRO")) {
                    c = 5;
                    break;
                }
            case 1055811561:
                if (cardType.equals(CARD.DISCOVER)) {
                    c = 9;
                    break;
                }
            case 1545480463:
                if (cardType.equals("MAESTRO")) {
                    c = 7;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return PayUmoneyConstants.MASTER_CARD;
            case 1:
                return "AMEX";
            case 2:
            case 3:
                return PayUmoneyConstants.DINERS;
            case 4:
                return "RPAY";
            case 5:
            case 6:
            case 7:
            case 8:
                return "MAESTRO";
            case 9:
                return CARD.DISCOVER;
            case 10:
                return "VISA";
            case 11:
                return "JCB";
            default:
                return "";
        }
    }

    public static HttpURLConnection getHttpsConn(String strURL, String postData, int timeout, String contentType, String token) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(strURL).openConnection();
            httpURLConnection.setRequestMethod("POST");
            if (timeout != -1) {
                httpURLConnection.setConnectTimeout(timeout);
            }
            httpURLConnection.setRequestProperty("Content-Type", contentType);
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(postData.length()));
            if (token != null) {
                httpURLConnection.setRequestProperty("X-Auth-Token", token);
            }
            httpURLConnection.setDoOutput(true);
            httpURLConnection.getOutputStream().write(postData.getBytes());
            return httpURLConnection;
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

    public static boolean isValidateUsername(String username) {
        if (isPhoneNumber(username)) {
            return isValidNumber(username);
        }
        return isValidEmail(username);
    }

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidAmount(Double amount) {
        if (amount.toString().matches("^\\d+(?:\\.\\d\\d?)?$")) {
            return true;
        }
        return false;
    }

    public static boolean isValidNumber(String phone) {
        if (phone.toString().matches("^((\\+91)?|[0]?)[6-9]\\d{9}$")) {
            return true;
        }
        return false;
    }

    public static boolean isPhoneNumber(String userName) {
        return userName.matches("^((\\+91)?|[0]?)[6-9]\\d{9}$");
    }

    public static boolean isNumber(String number) {
        return number.matches("[0-9]+");
    }

    public static boolean isCitiNetBankingSelected(PaymentEntity paymentEntity) {
        return paymentEntity.getCode().equalsIgnoreCase("CITNB");
    }

    public void deviceAnalytics(Context applicationContext, PaymentParam param) {
        LogAnalytics.logEvent(applicationContext, AnalyticsConstant.SDK_INIT, addDeviceAnalyticsAttributes(applicationContext, new HashMap()), AnalyticsConstant.CLEVERTAP);
    }

    public static HashMap<String, Object> addDeviceAnalyticsAttributes(Context applicationContext, HashMap<String, Object> eventData) {
        try {
            AnalyticsHelper analyticsHelper = new AnalyticsHelper();
            eventData.put(AnalyticsConstant.DEVICE_ID, analyticsHelper.getDeviceID(applicationContext));
            eventData.put(AnalyticsConstant.UUID, analyticsHelper.getUUID());
            eventData.put(AnalyticsConstant.D_UA, analyticsHelper.getUserAgent(applicationContext));
            eventData.put(AnalyticsConstant.U_LAT, analyticsHelper.getLatitude(applicationContext));
            eventData.put(AnalyticsConstant.U_LON, analyticsHelper.getLongitude(applicationContext));
            eventData.put(AnalyticsConstant.U_ACU, analyticsHelper.getAccuracy(applicationContext));
            eventData.put(AnalyticsConstant.APP_ID, applicationContext.getPackageName());
            eventData.put("package_name", applicationContext.getPackageName());
            eventData.put(AnalyticsConstant.APP_VERSION_NAME, appVersionName(applicationContext));
            String str = AnalyticsConstant.APP_VERSION_CODE;
            StringBuilder sb = new StringBuilder();
            sb.append(appVersionCode(applicationContext));
            sb.append("");
            eventData.put(str, sb.toString());
            eventData.put(AnalyticsConstant.D_LOCALE, analyticsHelper.getLocale());
            eventData.put(AnalyticsConstant.D_CCID, analyticsHelper.getCountryCode(applicationContext));
            eventData.put(AnalyticsConstant.D_LANG, analyticsHelper.getLanguage());
            eventData.put(AnalyticsConstant.D_NAME, Build.BRAND);
            eventData.put(AnalyticsConstant.D_MODEL, Build.MODEL);
            eventData.put(AnalyticsConstant.D_OS, PayUmoneyConstants.OS_NAME_VALUE);
            eventData.put(AnalyticsConstant.D_OSV, VERSION.RELEASE);
            eventData.put(AnalyticsConstant.SDK_BUILD, "17");
            eventData.put(AnalyticsConstant.SDK_VERSION, BuildConfig.VERSION_NAME);
            eventData.put(AnalyticsConstant.D_MFG, Build.MANUFACTURER);
            eventData.put(AnalyticsConstant.D_NW_TYPE, AnalyticsHelper.getNetworkType(applicationContext));
            String str2 = AnalyticsConstant.D_SS;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(analyticsHelper.getNetworkStrength(applicationContext));
            sb2.append("");
            eventData.put(str2, sb2.toString());
            String str3 = AnalyticsConstant.D_SCRN_SZ;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(getScreenSize(applicationContext));
            sb3.append("");
            eventData.put(str3, sb3.toString());
            String str4 = AnalyticsConstant.D_SCRN_RES;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(getScreenWidth(applicationContext));
            sb4.append(" * ");
            sb4.append(getScreenHeight(applicationContext));
            eventData.put(str4, sb4.toString());
            eventData.put(AnalyticsConstant.IP, b(applicationContext));
            eventData.put(AnalyticsConstant.APP_NAME, a(applicationContext));
            eventData.put(AnalyticsConstant.ENV, PayUmoneySdkInitializer.IsDebugMode().booleanValue() ? "DEBUG" : Environment.PRODUCTION);
            eventData.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
            eventData.put(AnalyticsConstant.D_ROOTED, Boolean.valueOf(AnalyticsHelper.isDeviceRooted()));
            eventData.put(AnalyticsConstant.D_BIOHW, Boolean.valueOf(AnalyticsHelper.isFingerPrintHardwareAvailable(applicationContext)));
            if (pnpVersion != null && !pnpVersion.isEmpty()) {
                eventData.put(AnalyticsConstant.PNP_VERSION, pnpVersion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eventData;
    }

    public static String getScreenSize(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        double sqrt = Math.sqrt(Math.pow((double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi), 2.0d) + Math.pow((double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi), 2.0d));
        StringBuilder sb = new StringBuilder();
        sb.append(sqrt);
        sb.append("");
        return sb.toString();
    }

    public static boolean isValidCvv(String cvv, String issuer) {
        if (issuer == null || issuer.trim().equalsIgnoreCase("") || issuer.contentEquals(PayUmoneyConstants.SMAE)) {
            return true;
        }
        if (cvv == null || cvv.length() < 3 || !isNumber(cvv)) {
            return false;
        }
        if (issuer.contentEquals("AMEX") && (cvv.length() == 4)) {
            return true;
        }
        if (issuer.contentEquals("AMEX") || cvv.length() != 3) {
            return false;
        }
        return true;
    }

    public static boolean isExpiryValid(String month, String year, String issuer) {
        if (issuer != null) {
            try {
                if (!issuer.trim().equalsIgnoreCase("")) {
                    if (issuer.equalsIgnoreCase(PayUmoneyConstants.SMAE)) {
                        return true;
                    }
                    if (month.length() == 2 && month.charAt(0) == '0') {
                        StringBuilder sb = new StringBuilder();
                        sb.append(month.charAt(1));
                        sb.append("");
                        month = sb.toString();
                    }
                    int parseInt = Integer.parseInt(year);
                    int parseInt2 = Integer.parseInt(month);
                    if (parseInt2 > 0) {
                        if (parseInt2 <= 12) {
                            Calendar instance = Calendar.getInstance();
                            if (parseInt < instance.get(1)) {
                                return false;
                            }
                            return parseInt != instance.get(1) || parseInt2 - 1 >= instance.get(2);
                        }
                    }
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public static double getWalletConvenienceFee(PayumoneyConvenienceFee convenienceFee) {
        if (convenienceFee != null) {
            return ((Double) ((Map) convenienceFee.getConvenienceFeeMap().get(PayUmoneyConstants.PAYUMONEY_WALLET)).get(PayUmoneyConstants.DEFAULT)).doubleValue();
        }
        return 0.0d;
    }

    @Deprecated
    public static double getCCConvenienceFee(PayumoneyConvenienceFee convenienceFee, String bankDetails, boolean isSplitPayment) {
        if (!isSplitPayment || PayUmoneyTransactionDetails.getInstance().getWalletAmount() < 1.0d) {
            return getConvenienceFee(PayUmoneyConstants.CC, convenienceFee, bankDetails);
        }
        return Math.max(getConvenienceFee(PayUmoneyConstants.CC, convenienceFee, bankDetails), getWalletConvenienceFee(convenienceFee));
    }

    public static double getCCConvenienceFee(PayumoneyConvenienceFee convenienceFee, String bankDetails, boolean isSplitPayment, String countryCode) {
        if (countryCode != null && !countryCode.isEmpty() && !countryCode.equalsIgnoreCase("IN")) {
            return a(convenienceFee, bankDetails, isSplitPayment);
        }
        if (!isSplitPayment || PayUmoneyTransactionDetails.getInstance().getWalletAmount() < 1.0d) {
            return getConvenienceFee(PayUmoneyConstants.CC, convenienceFee, bankDetails);
        }
        return Math.max(getConvenienceFee(PayUmoneyConstants.CC, convenienceFee, bankDetails), getWalletConvenienceFee(convenienceFee));
    }

    private static double a(PayumoneyConvenienceFee payumoneyConvenienceFee, String str, boolean z) {
        if (!z || PayUmoneyTransactionDetails.getInstance().getWalletAmount() < 1.0d) {
            return a(PayUmoneyConstants.CC_INT, payumoneyConvenienceFee, str);
        }
        return Math.max(a(PayUmoneyConstants.CC_INT, payumoneyConvenienceFee, str), getWalletConvenienceFee(payumoneyConvenienceFee));
    }

    private static double b(PayumoneyConvenienceFee payumoneyConvenienceFee, String str, boolean z) {
        if (!z || PayUmoneyTransactionDetails.getInstance().getWalletAmount() < 1.0d) {
            return a(PayUmoneyConstants.DC_INT, payumoneyConvenienceFee, str);
        }
        return Math.max(a(PayUmoneyConstants.DC_INT, payumoneyConvenienceFee, str), getWalletConvenienceFee(payumoneyConvenienceFee));
    }

    @Deprecated
    public static double getDCConvenienceFee(PayumoneyConvenienceFee convenienceFee, String cardType, boolean isSplitPayment) {
        if (!isSplitPayment || PayUmoneyTransactionDetails.getInstance().getWalletAmount() < 1.0d) {
            return getConvenienceFee(PayUmoneyConstants.DC, convenienceFee, cardType);
        }
        return Math.max(getConvenienceFee(PayUmoneyConstants.DC, convenienceFee, cardType), getWalletConvenienceFee(convenienceFee));
    }

    public static double getDCConvenienceFee(PayumoneyConvenienceFee convenienceFee, String cardType, boolean isSplitPayment, String countryCode) {
        if (countryCode != null && !countryCode.isEmpty() && !countryCode.equalsIgnoreCase("IN")) {
            return b(convenienceFee, cardType, isSplitPayment);
        }
        if (!isSplitPayment || PayUmoneyTransactionDetails.getInstance().getWalletAmount() < 1.0d) {
            return getConvenienceFee(PayUmoneyConstants.DC, convenienceFee, cardType);
        }
        return Math.max(getConvenienceFee(PayUmoneyConstants.DC, convenienceFee, cardType), getWalletConvenienceFee(convenienceFee));
    }

    public static double getEmiConvenienceFee(PayumoneyConvenienceFee convenienceFee, String emiBankCode) {
        return getConvenienceFee(PayUmoneyConstants.EMI, convenienceFee, emiBankCode);
    }

    public static double getNBConvenienceFee(PayumoneyConvenienceFee convenienceFee, String bankCode, boolean isSplitPayment) {
        if (!isSplitPayment || PayUmoneyTransactionDetails.getInstance().getWalletAmount() < 1.0d) {
            return getConvenienceFee(PayUmoneyConstants.NB, convenienceFee, bankCode);
        }
        return Math.max(getConvenienceFee(PayUmoneyConstants.NB, convenienceFee, bankCode), getWalletConvenienceFee(convenienceFee));
    }

    public static double getThirdPartyWalletsConvenienceFee(PayumoneyConvenienceFee convenienceFee, String bankCode) {
        return getConvenienceFee(PayUmoneyConstants.CASH, convenienceFee, bankCode);
    }

    public static double getUPIConvenienceFee(PayumoneyConvenienceFee convenienceFee, String bankCode) {
        return getConvenienceFee(PayUmoneyConstants.UPI, convenienceFee, bankCode);
    }

    private static double a(String str, PayumoneyConvenienceFee payumoneyConvenienceFee, String str2) {
        if (payumoneyConvenienceFee != null) {
            Map convenienceFeeMap = payumoneyConvenienceFee.getConvenienceFeeMap();
            if (convenienceFeeMap != null) {
                if (convenienceFeeMap.get(str) != null) {
                    if (((Map) convenienceFeeMap.get(str)).containsKey(str2)) {
                        return ((Double) ((Map) convenienceFeeMap.get(str)).get(str2)).doubleValue();
                    }
                    return ((Double) ((Map) convenienceFeeMap.get(str)).get(PayUmoneyConstants.DEFAULT)).doubleValue();
                } else if (str.equalsIgnoreCase(PayUmoneyConstants.CC_INT)) {
                    return getConvenienceFee(PayUmoneyConstants.CC, payumoneyConvenienceFee, str2);
                } else {
                    return getConvenienceFee(PayUmoneyConstants.DC, payumoneyConvenienceFee, str2);
                }
            }
        }
        return 0.0d;
    }

    public static double getConvenienceFee(String paymentMode, PayumoneyConvenienceFee convenienceFee, String cardType) {
        if (convenienceFee != null) {
            Map convenienceFeeMap = convenienceFee.getConvenienceFeeMap();
            if (convenienceFeeMap == null || convenienceFeeMap.get(paymentMode) == null) {
                return 0.0d;
            }
            if (((Map) convenienceFeeMap.get(paymentMode)).containsKey(cardType)) {
                return ((Double) ((Map) convenienceFeeMap.get(paymentMode)).get(cardType)).doubleValue();
            }
            return ((Double) ((Map) convenienceFeeMap.get(paymentMode)).get(PayUmoneyConstants.DEFAULT)).doubleValue();
        }
        return 0.0d;
    }

    private static String a(Context context) {
        ApplicationInfo applicationInfo;
        PackageManager packageManager = context.getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getApplicationContext().getPackageName(), 0);
        } catch (NameNotFoundException e) {
            applicationInfo = null;
        }
        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "???");
    }

    private static String b(Context context) {
        String str = "";
        try {
            for (NetworkInterface inetAddresses : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                for (InetAddress inetAddress : Collections.list(inetAddresses.getInetAddresses())) {
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        if (hostAddress.indexOf(58) < 0) {
                            str = hostAddress;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return str;
    }

    public static String appVersionName(Context applicationContext) {
        try {
            String str = applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0).versionName;
            if (str == null) {
                return "";
            }
            return str;
        } catch (Exception e) {
            return "";
        }
    }

    public static int appVersionCode(Context applicationContext) {
        int i = 0;
        try {
            return applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), i).versionCode;
        } catch (Exception e) {
            return i;
        }
    }

    public static boolean isBankStatusIsUp(PaymentEntity netbank, ArrayList<PaymentEntity> enableNetBankList) {
        if (enableNetBankList != null) {
            int i = 0;
            while (i < enableNetBankList.size()) {
                if (!((PaymentEntity) enableNetBankList.get(i)).getCode().equalsIgnoreCase(netbank.getCode())) {
                    i++;
                } else if (((PaymentEntity) enableNetBankList.get(i)).getUpStatus() == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public static String getCommaSeparatedBins(ArrayList<String> numbersList) {
        StringBuilder sb = new StringBuilder("");
        Iterator it = numbersList.iterator();
        while (it.hasNext()) {
            sb.append(((String) it.next()).substring(0, 6));
            sb.append(",");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    public static boolean hasPermission(Context context, String permission) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096);
            if (packageInfo.requestedPermissions != null) {
                for (String equals : packageInfo.requestedPermissions) {
                    if (equals.equals(permission)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getIssuer(String mCardNumber) {
        if (mCardNumber.startsWith("4")) {
            return "VISA";
        }
        if (mCardNumber.matches("^508[5-9][0-9][0-9]|60698[5-9]|60699[0-9]|607[0-8][0-9][0-9]|6079[0-7][0-9]|60798[0-4]|(?!608000)608[0-4][0-9][0-9]|608500|6521[5-9][0-9]|652[2-9][0-9][0-9]|6530[0-9][0-9]|6531[0-4][0-9]")) {
            return PayUmoneyConstants.RUPAY;
        }
        if (mCardNumber.matches("^((6304)|(6706)|(6771)|(6709))[\\d]+")) {
            return PayUmoneyConstants.LASER;
        }
        if (mCardNumber.matches("6(?:011|5[0-9]{2})[0-9]{12}[\\d]+")) {
            return PayUmoneyConstants.LASER;
        }
        if (mCardNumber.matches("(5[06-8]|6\\d)\\d{14}(\\d{2,3})?[\\d]+") || mCardNumber.matches("(5[06-8]|6\\d)[\\d]+") || mCardNumber.matches("((504([435|645|774|775|809|993]))|(60([0206]|[3845]))|(622[018])\\d)[\\d]+")) {
            if (mCardNumber.length() < 6 || !SBI_MAES_BIN.contains(mCardNumber.substring(0, 6))) {
                return PayUmoneyConstants.MAESTRO;
            }
            return PayUmoneyConstants.SMAE;
        } else if (mCardNumber.matches("^5[1-5][\\d]+")) {
            return PayUmoneyConstants.MASTER;
        } else {
            if (mCardNumber.matches("^3[47][\\d]+")) {
                return "AMEX";
            }
            if (mCardNumber.startsWith("36") || mCardNumber.matches("^30[0-5][\\d]+") || mCardNumber.matches("2(014|149)[\\d]+")) {
                return PayUmoneyConstants.DINR;
            }
            if (mCardNumber.matches("^35(2[89]|[3-8][0-9])[\\d]+")) {
                return "JCB";
            }
            return "";
        }
    }

    public static boolean isValidVPA(String vpa) {
        return vpa.matches("^[^@]+@[^@]+$");
    }

    public static boolean isUPIPaymentOptionAvailable(PaymentOptionDetails paymentOptionDetails) {
        if (paymentOptionDetails == null || paymentOptionDetails.getUpiList() == null || paymentOptionDetails.getUpiList().size() <= 0) {
            return false;
        }
        Iterator it = paymentOptionDetails.getUpiList().iterator();
        while (it.hasNext()) {
            if (((PaymentEntity) it.next()).getCode().equalsIgnoreCase(PayUmoneyConstants.UPI)) {
                return true;
            }
        }
        return false;
    }
}
