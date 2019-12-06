package com.payu.custombrowser.a;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import com.payu.custombrowser.util.CBUtil;
import com.payu.magicretry.Helpers.MRConstant;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b {
    /* access modifiers changed from: private */
    public long a = 0;
    /* access modifiers changed from: private */
    public final Context b;
    /* access modifiers changed from: private */
    public boolean c = false;
    /* access modifiers changed from: private */
    public ArrayList<String> d;
    /* access modifiers changed from: private */
    public Timer e;
    /* access modifiers changed from: private */
    public String f = "cb_local_cache_device";
    /* access modifiers changed from: private */
    public CBUtil g;
    /* access modifiers changed from: private */
    public boolean h;

    public class a extends AsyncTask<String, Void, String> {
        private String b;

        a(String str) {
            this.b = str;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(String str) {
            super.onPostExecute(str);
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0198, code lost:
            r8 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x019a, code lost:
            r8 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
            com.payu.custombrowser.a.b.h(r7.a);
            r8.printStackTrace();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x01a9, code lost:
            r8 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x01ab, code lost:
            r8 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x01ac, code lost:
            r8.printStackTrace();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x01b4, code lost:
            r8.printStackTrace();
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:37:0x019a A[ExcHandler: IOException (r8v5 'e' java.io.IOException A[CUSTOM_DECLARE]), Splitter:B:0:0x0000] */
        /* JADX WARNING: Removed duplicated region for block: B:40:0x01a9 A[Catch:{ Exception -> 0x0198 }, ExcHandler: ProtocolException (e java.net.ProtocolException), Splitter:B:0:0x0000] */
        /* JADX WARNING: Removed duplicated region for block: B:42:0x01ab A[Catch:{ Exception -> 0x0198 }, ExcHandler: MalformedURLException (e java.net.MalformedURLException), Splitter:B:0:0x0000] */
        /* renamed from: a */
        public String doInBackground(String... strArr) {
            try {
                if (b.this.b != null && !b.this.h) {
                    JSONArray jSONArray = new JSONArray(strArr[0]);
                    JSONArray jSONArray2 = jSONArray;
                    for (int i = 0; i < jSONArray.length(); i++) {
                        b bVar = b.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append(((JSONObject) jSONArray.get(i)).getString(MRConstant.MERCHANT_KEY));
                        sb.append("|");
                        sb.append(((JSONObject) jSONArray.get(i)).getString("txnid"));
                        if (bVar.a(sb.toString(), b.this.b)) {
                            jSONArray2 = b.this.a(jSONArray, i);
                        }
                    }
                    if (jSONArray2.length() > 0) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("command=DeviceAnalytics&data=");
                        sb2.append(jSONArray2.toString());
                        String sb3 = sb2.toString();
                        HttpsURLConnection httpsConn = b.this.g.getHttpsConn("https://info.payu.in/merchant/MobileAnalytics", sb3);
                        if (httpsConn == null) {
                            b.this.b.deleteFile(b.this.f);
                        } else if (httpsConn.getResponseCode() == 200) {
                            StringBuffer stringBufferFromInputStream = CBUtil.getStringBufferFromInputStream(httpsConn.getInputStream());
                            if (stringBufferFromInputStream != null) {
                                if (new JSONObject(stringBufferFromInputStream.toString()).has("status")) {
                                    b.this.b.deleteFile(b.this.f);
                                    for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                                        b bVar2 = b.this;
                                        StringBuilder sb4 = new StringBuilder();
                                        sb4.append(((JSONObject) jSONArray2.get(i2)).getString(MRConstant.MERCHANT_KEY));
                                        sb4.append("|");
                                        sb4.append(((JSONObject) jSONArray2.get(i2)).getString("txnid"));
                                        bVar2.a(sb4.toString(), true, b.this.b);
                                    }
                                } else {
                                    b.this.b(this.b);
                                }
                            }
                        } else {
                            b.this.b(this.b);
                        }
                    }
                }
            } catch (Exception e) {
                b.this.b(this.b);
            } catch (MalformedURLException e2) {
            } catch (ProtocolException e3) {
            } catch (IOException e4) {
            }
            return null;
        }
    }

    public b(Context context, final String str) {
        this.b = context;
        this.f = str;
        this.d = new ArrayList<>();
        this.g = new CBUtil();
        final UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable ex) {
                do {
                } while (b.this.c);
                b.this.c();
                try {
                    FileOutputStream openFileOutput = b.this.b.openFileOutput(str, 0);
                    int size = b.this.d.size();
                    for (int i = 0; i < size; i++) {
                        StringBuilder sb = new StringBuilder();
                        sb.append((String) b.this.d.get(i));
                        sb.append("\r\n");
                        openFileOutput.write(sb.toString().getBytes());
                    }
                    openFileOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.this.d();
                defaultUncaughtExceptionHandler.uncaughtException(thread, ex);
            }
        });
    }

    public void a(String str) {
        JSONArray jSONArray;
        if (this.c) {
            this.d.add(str);
        } else {
            c();
            try {
                JSONObject jSONObject = new JSONObject(str);
                String str2 = "";
                if (!new File(this.b.getFilesDir(), this.f).exists()) {
                    this.b.openFileOutput(this.f, 0);
                }
                FileInputStream openFileInput = this.b.openFileInput(this.f);
                while (true) {
                    int read = openFileInput.read();
                    if (read == -1) {
                        break;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(Character.toString((char) read));
                    str2 = sb.toString();
                }
                if (str2.equalsIgnoreCase("")) {
                    jSONArray = new JSONArray();
                } else {
                    jSONArray = new JSONArray(str2);
                }
                openFileInput.close();
                FileOutputStream openFileOutput = this.b.openFileOutput(this.f, 0);
                jSONArray.put(jSONArray.length(), jSONObject);
                openFileOutput.write(jSONArray.toString().getBytes());
                openFileOutput.close();
            } catch (IOException e2) {
                e2.printStackTrace();
                this.d.add(str);
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            d();
        }
        b();
    }

    /* access modifiers changed from: private */
    public void b() {
        Timer timer = this.e;
        if (timer != null) {
            timer.cancel();
        }
        this.e = new Timer();
        this.e.schedule(new TimerTask() {
            /* JADX WARNING: Removed duplicated region for block: B:40:0x014e  */
            public void run() {
                a aVar;
                String[] strArr;
                do {
                } while (b.this.c);
                b.this.a = 5000;
                b.this.c();
                String str = "";
                try {
                    if (!new File(b.this.b.getFilesDir(), b.this.f).exists()) {
                        b.this.b.openFileOutput(b.this.f, 0);
                    }
                    FileInputStream openFileInput = b.this.b.openFileInput(b.this.f);
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
                    int size = b.this.d.size();
                    while (size > 0) {
                        size--;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append((String) b.this.d.get(size));
                        sb2.append("\r\n");
                        str = sb2.toString();
                        if (size >= 0 && b.this.d.size() > size) {
                            b.this.d.remove(size);
                        }
                    }
                    String trim = str.trim();
                    if (trim.length() > 0) {
                        aVar = new a(trim);
                        strArr = new String[]{trim};
                        aVar.execute(strArr);
                        if (b.this.d.size() > 0) {
                            b.this.b();
                        }
                        b.this.d();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    int size2 = b.this.d.size();
                    while (size2 > 0) {
                        size2--;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(str);
                        sb3.append((String) b.this.d.get(size2));
                        sb3.append("\r\n");
                        str = sb3.toString();
                        if (size2 >= 0 && b.this.d.size() > size2) {
                            b.this.d.remove(size2);
                        }
                    }
                    String trim2 = str.trim();
                    if (trim2.length() > 0) {
                        aVar = new a(trim2);
                        strArr = new String[]{trim2};
                    }
                } catch (Throwable th) {
                    int size3 = b.this.d.size();
                    while (size3 > 0) {
                        size3--;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(str);
                        sb4.append((String) b.this.d.get(size3));
                        sb4.append("\r\n");
                        str = sb4.toString();
                        if (size3 >= 0 && b.this.d.size() > size3) {
                            b.this.d.remove(size3);
                        }
                    }
                    String trim3 = str.trim();
                    if (trim3.length() > 0) {
                        new a(trim3).execute(new String[]{trim3});
                    } else {
                        b.this.e.cancel();
                    }
                    throw th;
                }
                b.this.e.cancel();
                if (b.this.d.size() > 0) {
                }
                b.this.d();
            }
        }, this.a);
    }

    /* access modifiers changed from: private */
    public synchronized void c() {
        this.c = true;
    }

    /* access modifiers changed from: private */
    public synchronized void d() {
        this.c = false;
    }

    /* access modifiers changed from: private */
    public JSONArray a(JSONArray jSONArray, int i) throws JSONException {
        if (i < 0 || i > jSONArray.length() - 1) {
            throw new IndexOutOfBoundsException();
        }
        JSONArray jSONArray2 = new JSONArray();
        int length = jSONArray.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (i2 != i) {
                jSONArray2.put(jSONArray.get(i2));
            }
        }
        return jSONArray2;
    }

    /* access modifiers changed from: private */
    public void b(String str) {
        try {
            FileOutputStream openFileOutput = this.b.openFileOutput(this.f, 0);
            openFileOutput.write(str.getBytes());
            openFileOutput.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public Timer a() {
        this.h = true;
        return this.e;
    }

    /* access modifiers changed from: private */
    public boolean a(String str, Context context) {
        return context.getSharedPreferences("com.payu", 0).getBoolean(str, false);
    }

    /* access modifiers changed from: private */
    public void a(String str, boolean z, Context context) {
        Editor edit = context.getSharedPreferences("com.payu", 0).edit();
        edit.putBoolean(str, z);
        edit.apply();
    }
}
