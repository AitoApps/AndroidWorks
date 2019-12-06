package com.payumoney.core.analytics;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySDK;
import com.payumoney.core.SdkSession;
import com.payumoney.core.listener.OnClevertapAnalyticsListener;
import com.payumoney.core.utils.AnalyticsConstant;
import com.payumoney.core.utils.JsonUtils;
import com.payumoney.core.utils.SdkHelper;
import com.payumoney.core.utils.SharedPrefsUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AnalyticsDataManager implements OnClevertapAnalyticsListener {
    private static AnalyticsDataManager a;
    private long b = 5000;
    /* access modifiers changed from: private */
    public final Context c;
    /* access modifiers changed from: private */
    public String d;
    /* access modifiers changed from: private */
    public volatile boolean e = false;
    private Timer f;
    /* access modifiers changed from: private */
    public volatile boolean g;
    /* access modifiers changed from: private */
    public String h = "analytics_buffer_key";

    private AnalyticsDataManager(final Context context, String filename) {
        this.c = context;
        this.d = filename;
        final UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable ex) {
                do {
                } while (AnalyticsDataManager.this.e);
                AnalyticsDataManager.this.a();
                try {
                    FileOutputStream openFileOutput = AnalyticsDataManager.this.c.openFileOutput(AnalyticsDataManager.this.d, 0);
                    if (SharedPrefsUtils.getStringSharedPreference(AnalyticsDataManager.this.c, AnalyticsDataManager.this.d, AnalyticsDataManager.this.h).length() > 0) {
                        JSONArray jSONArray = new JSONArray();
                        JSONArray jSONArray2 = new JSONArray(SharedPrefsUtils.getStringSharedPreference(AnalyticsDataManager.this.c, AnalyticsDataManager.this.d, AnalyticsDataManager.this.h).toString());
                        for (int i = 0; i < jSONArray2.length(); i++) {
                            jSONArray.put(jSONArray.length(), jSONArray2.getJSONObject(i));
                        }
                        openFileOutput.write(jSONArray.toString().getBytes());
                        SharedPrefsUtils.deleteSharedPrefKey(context, AnalyticsDataManager.this.d, AnalyticsDataManager.this.h);
                    }
                    openFileOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                AnalyticsDataManager.this.b();
                defaultUncaughtExceptionHandler.uncaughtException(thread, ex);
            }
        });
    }

    /* access modifiers changed from: private */
    public synchronized void a() {
        while (this.e) {
        }
        this.e = true;
    }

    /* access modifiers changed from: private */
    public void b() {
        this.e = false;
    }

    public static AnalyticsDataManager getInstance(Context context, String fileName) {
        if (a == null) {
            synchronized (AnalyticsDataManager.class) {
                if (a == null) {
                    a = new AnalyticsDataManager(context, fileName);
                }
            }
        }
        return a;
    }

    public void logEvent(String eventName, HashMap<String, Object> data2) {
        HashMap<String, Object> hashMap;
        String str;
        if (data2 != null) {
            hashMap = data2;
        } else {
            hashMap = new HashMap<>();
        }
        if (!eventName.equalsIgnoreCase(AnalyticsConstant.SDK_INIT)) {
            hashMap.put(AnalyticsConstant.PAYMENTID, SdkSession.paymentId);
        }
        if (!eventName.equalsIgnoreCase(AnalyticsConstant.LOGIN_SUCCEEDED) && !eventName.equalsIgnoreCase(AnalyticsConstant.LOGIN_FAILED)) {
            hashMap.put(AnalyticsConstant.IS_USER_LOGGED_IN, Boolean.valueOf(PayUmoneySDK.getInstance().isUserLoggedIn()));
        }
        HashMap addDeviceAnalyticsAttributes = SdkHelper.addDeviceAnalyticsAttributes(this.c, hashMap);
        addDeviceAnalyticsAttributes.put(AnalyticsConstant.PLATFORM, PayUmoneyConstants.OS_NAME_VALUE);
        addDeviceAnalyticsAttributes.put(AnalyticsConstant.MID, PayUmoneySDK.getInstance().getPaymentParam().getParams().get("merchantId"));
        addDeviceAnalyticsAttributes.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        HashMap hashMap2 = new HashMap();
        hashMap2.put(AnalyticsConstant.TYPE, NotificationCompat.CATEGORY_EVENT);
        hashMap2.put(AnalyticsConstant.TS, Long.valueOf(System.currentTimeMillis() / 1000));
        hashMap2.put(AnalyticsConstant.EVT_NAME, eventName);
        String str2 = AnalyticsConstant.IDENTITY;
        if (!((String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get("email")).isEmpty()) {
            str = (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get("email");
        } else {
            str = (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get("phone");
        }
        hashMap2.put(str2, str);
        hashMap2.put(AnalyticsConstant.EVT_DATA, addDeviceAnalyticsAttributes);
        log(JsonUtils.mapToJson(hashMap2).toString());
    }

    public void log(final String msg) {
        if (this.e) {
            a(msg);
            return;
        }
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            public void run() {
                JSONArray jSONArray;
                do {
                } while (AnalyticsDataManager.this.e);
                AnalyticsDataManager.this.a();
                try {
                    JSONObject jSONObject = new JSONObject(msg);
                    String readFileInputStream = AnalyticsDataManager.this.readFileInputStream(AnalyticsDataManager.this.c, AnalyticsDataManager.this.d, 0);
                    if (readFileInputStream != null) {
                        if (!readFileInputStream.equalsIgnoreCase("")) {
                            jSONArray = new JSONArray(readFileInputStream);
                            FileOutputStream openFileOutput = AnalyticsDataManager.this.c.openFileOutput(AnalyticsDataManager.this.d, 0);
                            jSONArray.put(jSONArray.length(), jSONObject);
                            openFileOutput.write(jSONArray.toString().getBytes());
                            openFileOutput.close();
                            AnalyticsDataManager.this.b();
                            AnalyticsDataManager.this.c();
                        }
                    }
                    jSONArray = new JSONArray();
                    FileOutputStream openFileOutput2 = AnalyticsDataManager.this.c.openFileOutput(AnalyticsDataManager.this.d, 0);
                    jSONArray.put(jSONArray.length(), jSONObject);
                    openFileOutput2.write(jSONArray.toString().getBytes());
                    openFileOutput2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    AnalyticsDataManager.this.a(msg);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                } catch (Exception e3) {
                    e3.printStackTrace();
                } catch (Throwable th) {
                    AnalyticsDataManager.this.b();
                    AnalyticsDataManager.this.c();
                    throw th;
                }
                AnalyticsDataManager.this.b();
                AnalyticsDataManager.this.c();
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(final String str) {
        new Thread(new Runnable() {
            public void run() {
                JSONArray jSONArray;
                while (AnalyticsDataManager.this.g) {
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                String stringSharedPreference = SharedPrefsUtils.getStringSharedPreference(AnalyticsDataManager.this.c, AnalyticsDataManager.this.d, AnalyticsDataManager.this.h);
                if (stringSharedPreference == null || stringSharedPreference.equalsIgnoreCase("")) {
                    jSONArray = new JSONArray();
                } else {
                    jSONArray = new JSONArray(stringSharedPreference);
                }
                jSONArray.put(new JSONObject(str));
                SharedPrefsUtils.setStringSharedPreference(AnalyticsDataManager.this.c, AnalyticsDataManager.this.d, AnalyticsDataManager.this.h, jSONArray.toString());
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public void c() {
        Timer timer = this.f;
        if (timer != null) {
            timer.cancel();
        }
        this.f = new Timer();
        this.f.schedule(new TimerTask() {
            public void run() {
                AnalyticsDataManager.this.d();
            }
        }, this.b);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0058 A[Catch:{ Exception -> 0x00c5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0080 A[Catch:{ Exception -> 0x00c5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00ad A[Catch:{ Exception -> 0x00c5 }] */
    public void d() {
        JSONArray jSONArray;
        JSONArray jSONArray2;
        do {
        } while (this.e);
        a();
        if (SdkHelper.checkNetwork(this.c)) {
            Context context = this.c;
            if (context != null) {
                String str = "";
                try {
                    String readFileInputStream = readFileInputStream(context, this.d, 0);
                    if (readFileInputStream != null) {
                        try {
                            if (!readFileInputStream.equalsIgnoreCase("")) {
                                jSONArray2 = new JSONArray(readFileInputStream);
                                if (SharedPrefsUtils.getStringSharedPreference(this.c, this.d, this.h).length() > 1) {
                                    this.g = true;
                                    jSONArray2 = a(jSONArray2, new JSONArray(SharedPrefsUtils.getStringSharedPreference(this.c, this.d, this.h)));
                                }
                                if (jSONArray2.length() <= 0) {
                                    JSONObject jSONObject = new JSONObject();
                                    try {
                                        jSONObject.put("d", jSONArray2);
                                    } catch (JSONException e2) {
                                        e2.printStackTrace();
                                    }
                                    new CleverTapAnalytics().sendEvent(AnalyticsConstant.CLEVERTAP_URL, jSONObject.toString(), this);
                                    return;
                                }
                                if (this.f != null) {
                                    this.f.cancel();
                                }
                                b();
                                return;
                            }
                        } catch (Exception e3) {
                            e3.printStackTrace();
                            e();
                            return;
                        }
                    }
                    jSONArray2 = new JSONArray();
                    if (SharedPrefsUtils.getStringSharedPreference(this.c, this.d, this.h).length() > 1) {
                    }
                    if (jSONArray2.length() <= 0) {
                    }
                } finally {
                    try {
                        if (!str.equalsIgnoreCase("")) {
                            jSONArray = new JSONArray(str);
                        } else {
                            jSONArray = new JSONArray();
                        }
                        if (SharedPrefsUtils.getStringSharedPreference(this.c, this.d, this.h).length() > 1) {
                            this.g = true;
                            jSONArray = a(jSONArray, new JSONArray(SharedPrefsUtils.getStringSharedPreference(this.c, this.d, this.h)));
                        }
                        if (jSONArray.length() <= 0) {
                            if (this.f != null) {
                                this.f.cancel();
                            }
                            b();
                        } else {
                            JSONObject jSONObject2 = new JSONObject();
                            try {
                                jSONObject2.put("d", jSONArray);
                            } catch (JSONException e4) {
                                e4.printStackTrace();
                            }
                            new CleverTapAnalytics().sendEvent(AnalyticsConstant.CLEVERTAP_URL, jSONObject2.toString(), this);
                        }
                    } catch (Exception e5) {
                        e5.printStackTrace();
                        e();
                    }
                }
            }
        }
    }

    public void pushAllPendingEvents() {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            public void run() {
                AnalyticsDataManager.this.d();
            }
        });
    }

    private void e() {
        b();
        if (SharedPrefsUtils.getStringSharedPreference(this.c, this.d, this.h).length() > 1) {
            c();
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
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        return str;
    }

    private JSONArray a(JSONArray jSONArray, JSONArray jSONArray2) {
        FileOutputStream fileOutputStream = null;
        try {
            JSONArray jSONArray3 = new JSONArray(jSONArray.toString());
            for (int i = 0; i < jSONArray2.length(); i++) {
                jSONArray3.put(jSONArray2.getJSONObject(i));
            }
            FileOutputStream openFileOutput = this.c.openFileOutput(this.d, 0);
            openFileOutput.write(jSONArray3.toString().getBytes());
            SharedPrefsUtils.deleteSharedPrefKey(this.c, this.d, this.h);
            if (openFileOutput != null) {
                try {
                    openFileOutput.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            this.g = false;
            return jSONArray3;
        } catch (Exception e3) {
            e3.printStackTrace();
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            this.g = false;
            return jSONArray;
        } catch (Throwable th) {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            this.g = false;
            throw th;
        }
    }

    public void OnClevertapEventsLoggedSuccessful() {
        this.c.deleteFile(this.d);
        e();
    }

    public void OnClevertapEventsLoggedFailed() {
    }
}
