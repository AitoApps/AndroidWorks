package com.payu.custombrowser.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.payu.custombrowser.util.CBConstant;
import com.payu.custombrowser.util.CBUtil;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Timer;
import java.util.TimerTask;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a {
    private static a b;
    /* access modifiers changed from: private */
    public String a;
    /* access modifiers changed from: private */
    public final Context c;
    /* access modifiers changed from: private */
    public volatile boolean d = false;
    private Timer e;
    /* access modifiers changed from: private */
    public CBUtil f;
    /* access modifiers changed from: private */
    public volatile boolean g;
    /* access modifiers changed from: private */
    public String h = "analytics_buffer_key";

    private a(final Context context, String str) {
        this.c = context;
        this.a = str;
        this.f = new CBUtil();
        final UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable ex) {
                do {
                } while (a.this.d);
                a.this.c();
                try {
                    FileOutputStream openFileOutput = a.this.c.openFileOutput(a.this.a, 0);
                    if (a.this.f.getStringSharedPreference(a.this.c, a.this.h).length() > 0) {
                        JSONArray jSONArray = new JSONArray();
                        JSONArray jSONArray2 = new JSONArray(a.this.f.getStringSharedPreference(a.this.c, a.this.h).toString());
                        for (int i = 0; i < jSONArray2.length(); i++) {
                            jSONArray.put(jSONArray.length(), jSONArray2.getJSONObject(i));
                        }
                        openFileOutput.write(jSONArray.toString().getBytes());
                        a.this.f.deleteSharedPrefKey(context, a.this.h);
                    }
                    openFileOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                a.this.d();
                defaultUncaughtExceptionHandler.uncaughtException(thread, ex);
            }
        });
    }

    public static a a(Context context, String str) {
        if (b == null) {
            synchronized (a.class) {
                if (b == null) {
                    b = new a(context, str);
                }
            }
        }
        return b;
    }

    public void a(final String str) {
        if (e()) {
            b();
        }
        if (this.d) {
            new Thread(new Runnable() {
                public void run() {
                    JSONArray jSONArray;
                    while (a.this.g) {
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    String stringSharedPreference = a.this.f.getStringSharedPreference(a.this.c, a.this.h);
                    if (stringSharedPreference == null || stringSharedPreference.equalsIgnoreCase("")) {
                        jSONArray = new JSONArray();
                    } else {
                        jSONArray = new JSONArray(stringSharedPreference);
                    }
                    jSONArray.put(new JSONObject(str));
                    a.this.f.setStringSharedPreference(a.this.c, a.this.h, jSONArray.toString());
                }
            }).start();
        } else {
            new Thread(new Runnable() {
                public void run() {
                    JSONArray jSONArray;
                    do {
                    } while (a.this.d);
                    a.this.c();
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        String readFileInputStream = a.this.f.readFileInputStream(a.this.c, a.this.a, 0);
                        if (readFileInputStream != null) {
                            if (!readFileInputStream.equalsIgnoreCase("")) {
                                jSONArray = new JSONArray(readFileInputStream);
                                FileOutputStream openFileOutput = a.this.c.openFileOutput(a.this.a, 0);
                                jSONArray.put(jSONArray.length(), jSONObject);
                                openFileOutput.write(jSONArray.toString().getBytes());
                                openFileOutput.close();
                                a.this.d();
                            }
                        }
                        jSONArray = new JSONArray();
                        FileOutputStream openFileOutput2 = a.this.c.openFileOutput(a.this.a, 0);
                        jSONArray.put(jSONArray.length(), jSONObject);
                        openFileOutput2.write(jSONArray.toString().getBytes());
                        openFileOutput2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    } catch (Throwable th) {
                        a.this.d();
                        throw th;
                    }
                    a.this.d();
                }
            }).start();
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        Timer timer = this.e;
        if (timer != null) {
            timer.cancel();
        }
        this.e = new Timer();
        this.e.schedule(new TimerTask() {
            /* JADX WARNING: Removed duplicated region for block: B:16:0x0066 A[Catch:{ Exception -> 0x00f6 }] */
            /* JADX WARNING: Removed duplicated region for block: B:19:0x0094 A[Catch:{ Exception -> 0x00f6 }] */
            public void run() {
                JSONArray jSONArray;
                JSONArray jSONArray2;
                do {
                } while (a.this.d);
                a.this.c();
                if (a.this.e()) {
                    String str = "";
                    try {
                        String readFileInputStream = a.this.f.readFileInputStream(a.this.c, a.this.a, 0);
                        if (readFileInputStream != null) {
                            try {
                                if (!readFileInputStream.equalsIgnoreCase("")) {
                                    jSONArray2 = new JSONArray(readFileInputStream);
                                    if (a.this.f.getStringSharedPreference(a.this.c, a.this.h).length() > 1) {
                                        a.this.g = true;
                                        jSONArray2 = a.this.a(jSONArray2, new JSONArray(a.this.f.getStringSharedPreference(a.this.c, a.this.h)));
                                    }
                                    if (jSONArray2.length() > 0) {
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("command=EventAnalytics&data=");
                                        sb.append(jSONArray2.toString());
                                        HttpsURLConnection httpsConn = a.this.f.getHttpsConn("https://info.payu.in/merchant/MobileAnalytics", sb.toString(), CBConstant.HTTP_TIMEOUT);
                                        if (!(httpsConn == null || httpsConn.getResponseCode() != 200 || httpsConn.getInputStream() == null)) {
                                            StringBuffer stringBufferFromInputStream = CBUtil.getStringBufferFromInputStream(httpsConn.getInputStream());
                                            if (stringBufferFromInputStream != null && new JSONObject(stringBufferFromInputStream.toString()).has("status")) {
                                                a.this.c.deleteFile(a.this.a);
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        jSONArray2 = new JSONArray();
                        if (a.this.f.getStringSharedPreference(a.this.c, a.this.h).length() > 1) {
                        }
                        if (jSONArray2.length() > 0) {
                        }
                    } finally {
                        try {
                            if (!str.equalsIgnoreCase("")) {
                                jSONArray = new JSONArray(str);
                            } else {
                                jSONArray = new JSONArray();
                            }
                            if (a.this.f.getStringSharedPreference(a.this.c, a.this.h).length() > 1) {
                                a.this.g = true;
                                jSONArray = a.this.a(jSONArray, new JSONArray(a.this.f.getStringSharedPreference(a.this.c, a.this.h)));
                            }
                            if (jSONArray.length() > 0) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("command=EventAnalytics&data=");
                                sb2.append(jSONArray.toString());
                                HttpsURLConnection httpsConn2 = a.this.f.getHttpsConn("https://info.payu.in/merchant/MobileAnalytics", sb2.toString(), CBConstant.HTTP_TIMEOUT);
                                if (!(httpsConn2 == null || httpsConn2.getResponseCode() != 200 || httpsConn2.getInputStream() == null)) {
                                    StringBuffer stringBufferFromInputStream2 = CBUtil.getStringBufferFromInputStream(httpsConn2.getInputStream());
                                    if (stringBufferFromInputStream2 != null && new JSONObject(stringBufferFromInputStream2.toString()).has("status")) {
                                        a.this.c.deleteFile(a.this.a);
                                    }
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
                a.this.d();
                if (a.this.f.getStringSharedPreference(a.this.c, a.this.h).length() > 1) {
                    a.this.b();
                }
            }
        }, 5000);
    }

    /* access modifiers changed from: private */
    public synchronized void c() {
        while (this.d) {
        }
        this.d = true;
    }

    /* access modifiers changed from: private */
    public void d() {
        this.d = false;
    }

    /* access modifiers changed from: private */
    public boolean e() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.c.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public Timer a() {
        return this.e;
    }

    /* access modifiers changed from: private */
    public JSONArray a(JSONArray jSONArray, JSONArray jSONArray2) {
        FileOutputStream fileOutputStream = null;
        try {
            JSONArray jSONArray3 = new JSONArray(jSONArray.toString());
            for (int i = 0; i < jSONArray2.length(); i++) {
                jSONArray3.put(jSONArray2.getJSONObject(i));
            }
            FileOutputStream openFileOutput = this.c.openFileOutput(this.a, 0);
            openFileOutput.write(jSONArray3.toString().getBytes());
            this.f.deleteSharedPrefKey(this.c, this.h);
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
}
