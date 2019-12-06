package com.payu.magicretry.analytics;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import com.payu.custombrowser.util.CBConstant;
import com.payu.magicretry.MagicRetryFragment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MRAnalytics {
    private static MRAnalytics INSTANCE = null;
    private static final String PRODUCTION_URL = "https://a.payu.in/MobileAnalytics";
    private static final String TEST_URL = "http://10.50.23.170:6543/MobileAnalytics";
    private static final long TIMER_DELAY = 5000;
    /* access modifiers changed from: private */
    public String ANALYTICS_URL;
    /* access modifiers changed from: private */
    public String fileName;
    /* access modifiers changed from: private */
    public ArrayList<String> mBuffer;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public boolean mIsLocked;
    private Timer mTimer;

    public MRAnalytics(final Context context, String filename) {
        this.ANALYTICS_URL = MagicRetryFragment.DEBUG ? TEST_URL : PRODUCTION_URL;
        this.mIsLocked = false;
        this.mContext = context;
        this.fileName = filename;
        this.mBuffer = new ArrayList<>();
        final UncaughtExceptionHandler defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable ex) {
                do {
                } while (MRAnalytics.this.mIsLocked);
                MRAnalytics.this.setLock();
                try {
                    FileOutputStream fileOutputStream = context.openFileOutput(MRAnalytics.this.fileName, 0);
                    int c = MRAnalytics.this.mBuffer.size();
                    if (c > 0) {
                        JSONArray jsonArray = new JSONArray();
                        for (int i = 0; i < c; i++) {
                            jsonArray.put(jsonArray.length(), new JSONObject((String) MRAnalytics.this.mBuffer.get(i)));
                        }
                        fileOutputStream.write(jsonArray.toString().getBytes());
                        MRAnalytics.this.mBuffer = new ArrayList();
                    }
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                MRAnalytics.this.releaseLock();
                defaultUEH.uncaughtException(thread, ex);
            }
        });
    }

    public void log(final String msg) {
        resetTimer();
        if (this.mIsLocked) {
            try {
                this.mBuffer.add(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new AsyncTask<Void, Void, Void>() {
                /* access modifiers changed from: protected */
                public Void doInBackground(Void... voids) {
                    JSONArray jsonArray;
                    MRAnalytics.this.setLock();
                    try {
                        JSONObject newobject = new JSONObject(msg);
                        String temp = "";
                        if (!new File(MRAnalytics.this.mContext.getFilesDir(), MRAnalytics.this.fileName).exists()) {
                            MRAnalytics.this.mContext.openFileOutput(MRAnalytics.this.fileName, 0);
                        }
                        FileInputStream fileInputStream = MRAnalytics.this.mContext.openFileInput(MRAnalytics.this.fileName);
                        while (true) {
                            int read = fileInputStream.read();
                            int c = read;
                            if (read == -1) {
                                break;
                            }
                            StringBuilder sb = new StringBuilder();
                            sb.append(temp);
                            sb.append(Character.toString((char) c));
                            temp = sb.toString();
                        }
                        if (temp.equalsIgnoreCase("")) {
                            jsonArray = new JSONArray();
                        } else {
                            jsonArray = new JSONArray(temp);
                        }
                        fileInputStream.close();
                        FileOutputStream fileOutputStream = MRAnalytics.this.mContext.openFileOutput(MRAnalytics.this.fileName, 0);
                        jsonArray.put(jsonArray.length(), newobject);
                        fileOutputStream.write(jsonArray.toString().getBytes());
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        MRAnalytics.this.mBuffer.add(msg);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                        MRAnalytics.this.mBuffer.add(msg);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        MRAnalytics.this.mBuffer.add(msg);
                    }
                    MRAnalytics.this.releaseLock();
                    return null;
                }
            }.execute(new Void[]{null, null, null});
        }
    }

    /* access modifiers changed from: private */
    public void resetTimer() {
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
        }
        this.mTimer = new Timer();
        this.mTimer.schedule(new TimerTask() {
            /* JADX WARNING: Code restructure failed: missing block: B:105:0x0374, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:108:?, code lost:
                r0.printStackTrace();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:112:0x0389, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:113:0x038a, code lost:
                r2 = r0;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:115:0x0391, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:116:0x0392, code lost:
                r2 = r0;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:118:0x0399, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:119:0x039a, code lost:
                r2 = r0;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:121:0x03a1, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:122:0x03a2, code lost:
                r2 = r0;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:124:0x03a9, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:125:0x03aa, code lost:
                r2 = r0;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:160:0x04d9, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:161:0x04da, code lost:
                r3 = r0;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:162:0x04dd, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:163:0x04de, code lost:
                r3 = r0;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:164:0x04e1, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:165:0x04e2, code lost:
                r3 = r0;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:166:0x04e5, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:167:0x04e6, code lost:
                r3 = r0;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:168:0x04e9, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:169:0x04ea, code lost:
                r3 = r0;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:170:0x04ed, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:171:0x04ee, code lost:
                r3 = r0;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:58:0x01e0, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
                r0.printStackTrace();
             */
            /* JADX WARNING: Exception block dominator not found, dom blocks: [B:49:0x0189, B:101:0x0335] */
            /* JADX WARNING: Failed to process nested try/catch */
            /* JADX WARNING: Removed duplicated region for block: B:112:0x0389 A[ExcHandler: JSONException (r0v23 'e' org.json.JSONException A[CUSTOM_DECLARE]), Splitter:B:49:0x0189] */
            /* JADX WARNING: Removed duplicated region for block: B:115:0x0391 A[ExcHandler: IOException (r0v22 'e' java.io.IOException A[CUSTOM_DECLARE]), Splitter:B:49:0x0189] */
            /* JADX WARNING: Removed duplicated region for block: B:118:0x0399 A[ExcHandler: UnsupportedEncodingException (r0v21 'e' java.io.UnsupportedEncodingException A[CUSTOM_DECLARE]), Splitter:B:49:0x0189] */
            /* JADX WARNING: Removed duplicated region for block: B:121:0x03a1 A[ExcHandler: ProtocolException (r0v20 'e' java.net.ProtocolException A[CUSTOM_DECLARE]), Splitter:B:49:0x0189] */
            /* JADX WARNING: Removed duplicated region for block: B:124:0x03a9 A[ExcHandler: MalformedURLException (r0v19 'e' java.net.MalformedURLException A[CUSTOM_DECLARE]), Splitter:B:49:0x0189] */
            /* JADX WARNING: Removed duplicated region for block: B:132:0x03d6  */
            /* JADX WARNING: Removed duplicated region for block: B:153:0x0439 A[Catch:{ MalformedURLException -> 0x0577, ProtocolException -> 0x056d, UnsupportedEncodingException -> 0x0563, IOException -> 0x0559, JSONException -> 0x054f, Exception -> 0x0545 }] */
            /* JADX WARNING: Removed duplicated region for block: B:162:0x04dd A[ExcHandler: JSONException (r0v11 'e' org.json.JSONException A[CUSTOM_DECLARE]), Splitter:B:157:0x04ca] */
            /* JADX WARNING: Removed duplicated region for block: B:164:0x04e1 A[ExcHandler: IOException (r0v10 'e' java.io.IOException A[CUSTOM_DECLARE]), Splitter:B:157:0x04ca] */
            /* JADX WARNING: Removed duplicated region for block: B:166:0x04e5 A[ExcHandler: UnsupportedEncodingException (r0v9 'e' java.io.UnsupportedEncodingException A[CUSTOM_DECLARE]), Splitter:B:157:0x04ca] */
            /* JADX WARNING: Removed duplicated region for block: B:168:0x04e9 A[ExcHandler: ProtocolException (r0v8 'e' java.net.ProtocolException A[CUSTOM_DECLARE]), Splitter:B:157:0x04ca] */
            /* JADX WARNING: Removed duplicated region for block: B:170:0x04ed A[ExcHandler: MalformedURLException (r0v7 'e' java.net.MalformedURLException A[CUSTOM_DECLARE]), Splitter:B:157:0x04ca] */
            /* JADX WARNING: Removed duplicated region for block: B:182:0x0541  */
            /* JADX WARNING: Removed duplicated region for block: B:204:0x0591  */
            /* JADX WARNING: Removed duplicated region for block: B:88:0x0253 A[Catch:{ MalformedURLException -> 0x03a9, ProtocolException -> 0x03a1, UnsupportedEncodingException -> 0x0399, IOException -> 0x0391, JSONException -> 0x0389, Exception -> 0x037f }] */
            /* JADX WARNING: Removed duplicated region for block: B:95:0x0292 A[Catch:{ MalformedURLException -> 0x03a9, ProtocolException -> 0x03a1, UnsupportedEncodingException -> 0x0399, IOException -> 0x0391, JSONException -> 0x0389, Exception -> 0x037f }] */
            public void run() {
                String temp;
                Throwable th;
                MalformedURLException e;
                ProtocolException e2;
                UnsupportedEncodingException e3;
                IOException e4;
                JSONException e5;
                Exception e6;
                JSONArray tempJsonArray;
                MalformedURLException e7;
                ProtocolException e8;
                UnsupportedEncodingException e9;
                IOException e10;
                JSONException e11;
                Exception e12;
                IOException e13;
                JSONArray tempJsonArray2;
                int i;
                do {
                } while (MRAnalytics.this.mIsLocked);
                MRAnalytics.this.setLock();
                if (MRAnalytics.this.isOnline()) {
                    String temp2 = "";
                    int i2 = -1;
                    try {
                        if (!new File(MRAnalytics.this.mContext.getFilesDir(), MRAnalytics.this.fileName).exists()) {
                            MRAnalytics.this.mContext.openFileOutput(MRAnalytics.this.fileName, 0);
                        }
                        FileInputStream fileInputStream = MRAnalytics.this.mContext.openFileInput(MRAnalytics.this.fileName);
                        while (true) {
                            try {
                                int read = fileInputStream.read();
                                int c = read;
                                if (read == -1) {
                                    break;
                                }
                                StringBuilder sb = new StringBuilder();
                                sb.append(temp2);
                                sb.append(Character.toString((char) c));
                                temp2 = sb.toString();
                            } catch (IOException e14) {
                                String str = temp2;
                                e13 = e14;
                                temp = str;
                                try {
                                    e13.printStackTrace();
                                    tempJsonArray2 = new JSONArray(temp);
                                    new JSONArray();
                                    if (MRAnalytics.this.mBuffer.size() > 0) {
                                        for (int i3 = 0; i3 < MRAnalytics.this.mBuffer.size(); i3++) {
                                            tempJsonArray2.put(new JSONObject((String) MRAnalytics.this.mBuffer.get(i3)));
                                        }
                                    }
                                    if (tempJsonArray2.length() > 0) {
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append("command=EventAnalytics&data=");
                                        sb2.append(tempJsonArray2.toString());
                                        String postData = sb2.toString();
                                        byte[] postParamsByte = postData.getBytes();
                                        HttpURLConnection conn = (HttpURLConnection) new URL(MRAnalytics.this.ANALYTICS_URL).openConnection();
                                        conn.setRequestMethod("POST");
                                        conn.setRequestProperty("Content-Type", CBConstant.HTTP_URLENCODED);
                                        conn.setRequestProperty("Content-Length", String.valueOf(postData.length()));
                                        conn.setDoOutput(true);
                                        conn.getOutputStream().write(postParamsByte);
                                        int responseCode = conn.getResponseCode();
                                        InputStream responseInputStream = conn.getInputStream();
                                        StringBuffer responseStringBuffer = new StringBuffer();
                                        byte[] byteContainer = new byte[1024];
                                        while (true) {
                                            int read2 = responseInputStream.read(byteContainer);
                                            int i4 = read2;
                                            if (read2 == -1) {
                                                break;
                                            }
                                            responseStringBuffer.append(new String(byteContainer, 0, i4));
                                        }
                                        if (responseCode == 200) {
                                            if (new JSONObject(responseStringBuffer.toString()).has("status")) {
                                                MRAnalytics.this.mContext.deleteFile(MRAnalytics.this.fileName);
                                                MRAnalytics.this.mBuffer = new ArrayList();
                                            }
                                        }
                                    }
                                    if (MRAnalytics.this.mBuffer.size() > 0) {
                                    }
                                    MRAnalytics.this.releaseLock();
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            } catch (Throwable th3) {
                                String str2 = temp2;
                                th = th3;
                                temp = str2;
                                try {
                                    tempJsonArray = new JSONArray(temp);
                                    new JSONArray();
                                    if (MRAnalytics.this.mBuffer.size() > 0) {
                                        int i5 = 0;
                                        while (i5 < MRAnalytics.this.mBuffer.size()) {
                                            try {
                                                tempJsonArray.put(new JSONObject((String) MRAnalytics.this.mBuffer.get(i5)));
                                                i5++;
                                            } catch (MalformedURLException e15) {
                                                String str3 = temp;
                                                e = e15;
                                                e.printStackTrace();
                                                throw th;
                                            } catch (ProtocolException e16) {
                                                String str4 = temp;
                                                e2 = e16;
                                                e2.printStackTrace();
                                                throw th;
                                            } catch (UnsupportedEncodingException e17) {
                                                String str5 = temp;
                                                e3 = e17;
                                                e3.printStackTrace();
                                                throw th;
                                            } catch (IOException e18) {
                                                String str6 = temp;
                                                e4 = e18;
                                                e4.printStackTrace();
                                                throw th;
                                            } catch (JSONException e19) {
                                                String str7 = temp;
                                                e5 = e19;
                                                e5.printStackTrace();
                                                throw th;
                                            } catch (Exception e20) {
                                                String str8 = temp;
                                                e6 = e20;
                                                e6.printStackTrace();
                                                throw th;
                                            }
                                        }
                                    }
                                    if (tempJsonArray.length() <= 0) {
                                        StringBuilder sb3 = new StringBuilder();
                                        sb3.append("command=EventAnalytics&data=");
                                        sb3.append(tempJsonArray.toString());
                                        String postData2 = sb3.toString();
                                        byte[] postParamsByte2 = postData2.getBytes();
                                        HttpURLConnection conn2 = (HttpURLConnection) new URL(MRAnalytics.this.ANALYTICS_URL).openConnection();
                                        conn2.setRequestMethod("POST");
                                        conn2.setRequestProperty("Content-Type", CBConstant.HTTP_URLENCODED);
                                        conn2.setRequestProperty("Content-Length", String.valueOf(postData2.length()));
                                        conn2.setDoOutput(true);
                                        conn2.getOutputStream().write(postParamsByte2);
                                        int responseCode2 = conn2.getResponseCode();
                                        InputStream responseInputStream2 = conn2.getInputStream();
                                        StringBuffer responseStringBuffer2 = new StringBuffer();
                                        byte[] byteContainer2 = new byte[1024];
                                        while (true) {
                                            int read3 = responseInputStream2.read(byteContainer2);
                                            int i6 = read3;
                                            String temp3 = temp;
                                            if (read3 == -1) {
                                                break;
                                            }
                                            try {
                                                responseStringBuffer2.append(new String(byteContainer2, 0, i6));
                                                temp = temp3;
                                            } catch (Exception e21) {
                                                e21.printStackTrace();
                                            } catch (MalformedURLException e22) {
                                            } catch (ProtocolException e23) {
                                            } catch (UnsupportedEncodingException e24) {
                                            } catch (IOException e25) {
                                            } catch (JSONException e26) {
                                            }
                                        }
                                        if (responseCode2 == 200) {
                                            if (new JSONObject(responseStringBuffer2.toString()).has("status")) {
                                                MRAnalytics.this.mContext.deleteFile(MRAnalytics.this.fileName);
                                                MRAnalytics.this.mBuffer = new ArrayList();
                                            }
                                        }
                                    }
                                } catch (MalformedURLException e27) {
                                    String str9 = temp;
                                    e = e27;
                                    e.printStackTrace();
                                    throw th;
                                } catch (ProtocolException e28) {
                                    String str10 = temp;
                                    e2 = e28;
                                    e2.printStackTrace();
                                    throw th;
                                } catch (UnsupportedEncodingException e29) {
                                    String str11 = temp;
                                    e3 = e29;
                                    e3.printStackTrace();
                                    throw th;
                                } catch (IOException e30) {
                                    String str12 = temp;
                                    e4 = e30;
                                    e4.printStackTrace();
                                    throw th;
                                } catch (JSONException e31) {
                                    String str13 = temp;
                                    e5 = e31;
                                    e5.printStackTrace();
                                    throw th;
                                } catch (Exception e32) {
                                    String str14 = temp;
                                    e6 = e32;
                                    e6.printStackTrace();
                                    throw th;
                                }
                                throw th;
                            }
                        }
                        fileInputStream.close();
                        try {
                            JSONArray tempJsonArray3 = new JSONArray(temp2);
                            new JSONArray();
                            if (MRAnalytics.this.mBuffer.size() > 0) {
                                int i7 = 0;
                                while (i7 < MRAnalytics.this.mBuffer.size()) {
                                    try {
                                        tempJsonArray3.put(new JSONObject((String) MRAnalytics.this.mBuffer.get(i7)));
                                        i7++;
                                    } catch (MalformedURLException e33) {
                                        String str15 = temp2;
                                        e7 = e33;
                                        e7.printStackTrace();
                                        if (MRAnalytics.this.mBuffer.size() > 0) {
                                        }
                                        MRAnalytics.this.releaseLock();
                                    } catch (ProtocolException e34) {
                                        String str16 = temp2;
                                        e8 = e34;
                                        e8.printStackTrace();
                                        if (MRAnalytics.this.mBuffer.size() > 0) {
                                        }
                                        MRAnalytics.this.releaseLock();
                                    } catch (UnsupportedEncodingException e35) {
                                        String str17 = temp2;
                                        e9 = e35;
                                        e9.printStackTrace();
                                        if (MRAnalytics.this.mBuffer.size() > 0) {
                                        }
                                        MRAnalytics.this.releaseLock();
                                    } catch (IOException e36) {
                                        String str18 = temp2;
                                        e10 = e36;
                                        e10.printStackTrace();
                                        if (MRAnalytics.this.mBuffer.size() > 0) {
                                        }
                                        MRAnalytics.this.releaseLock();
                                    } catch (JSONException e37) {
                                        String str19 = temp2;
                                        e11 = e37;
                                        e11.printStackTrace();
                                        if (MRAnalytics.this.mBuffer.size() > 0) {
                                        }
                                        MRAnalytics.this.releaseLock();
                                    } catch (Exception e38) {
                                        String str20 = temp2;
                                        e12 = e38;
                                        e12.printStackTrace();
                                        if (MRAnalytics.this.mBuffer.size() > 0) {
                                        }
                                        MRAnalytics.this.releaseLock();
                                    }
                                }
                            }
                            if (tempJsonArray3.length() > 0) {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("command=EventAnalytics&data=");
                                sb4.append(tempJsonArray3.toString());
                                String postData3 = sb4.toString();
                                byte[] postParamsByte3 = postData3.getBytes();
                                HttpURLConnection conn3 = (HttpURLConnection) new URL(MRAnalytics.this.ANALYTICS_URL).openConnection();
                                conn3.setRequestMethod("POST");
                                conn3.setRequestProperty("Content-Type", CBConstant.HTTP_URLENCODED);
                                conn3.setRequestProperty("Content-Length", String.valueOf(postData3.length()));
                                conn3.setDoOutput(true);
                                conn3.getOutputStream().write(postParamsByte3);
                                int responseCode3 = conn3.getResponseCode();
                                InputStream responseInputStream3 = conn3.getInputStream();
                                StringBuffer responseStringBuffer3 = new StringBuffer();
                                byte[] byteContainer3 = new byte[1024];
                                while (true) {
                                    int read4 = responseInputStream3.read(byteContainer3);
                                    i = read4;
                                    if (read4 == i2) {
                                        break;
                                    }
                                    int i8 = i;
                                    String temp4 = temp2;
                                    try {
                                        responseStringBuffer3.append(new String(byteContainer3, 0, i8));
                                        temp2 = temp4;
                                        i2 = -1;
                                    } catch (MalformedURLException e39) {
                                    } catch (ProtocolException e40) {
                                    } catch (UnsupportedEncodingException e41) {
                                    } catch (IOException e42) {
                                    } catch (JSONException e43) {
                                    } catch (Exception e44) {
                                        e12 = e44;
                                        e12.printStackTrace();
                                        if (MRAnalytics.this.mBuffer.size() > 0) {
                                        }
                                        MRAnalytics.this.releaseLock();
                                    }
                                }
                                String str21 = temp2;
                                if (responseCode3 == 200) {
                                    if (new JSONObject(responseStringBuffer3.toString()).has("status")) {
                                        MRAnalytics.this.mContext.deleteFile(MRAnalytics.this.fileName);
                                        MRAnalytics.this.mBuffer = new ArrayList();
                                    }
                                }
                            }
                        } catch (MalformedURLException e45) {
                            String str22 = temp2;
                            e7 = e45;
                            e7.printStackTrace();
                            if (MRAnalytics.this.mBuffer.size() > 0) {
                            }
                            MRAnalytics.this.releaseLock();
                        } catch (ProtocolException e46) {
                            String str23 = temp2;
                            e8 = e46;
                            e8.printStackTrace();
                            if (MRAnalytics.this.mBuffer.size() > 0) {
                            }
                            MRAnalytics.this.releaseLock();
                        } catch (UnsupportedEncodingException e47) {
                            String str24 = temp2;
                            e9 = e47;
                            e9.printStackTrace();
                            if (MRAnalytics.this.mBuffer.size() > 0) {
                            }
                            MRAnalytics.this.releaseLock();
                        } catch (IOException e48) {
                            String str25 = temp2;
                            e10 = e48;
                            e10.printStackTrace();
                            if (MRAnalytics.this.mBuffer.size() > 0) {
                            }
                            MRAnalytics.this.releaseLock();
                        } catch (JSONException e49) {
                            String str26 = temp2;
                            e11 = e49;
                            e11.printStackTrace();
                            if (MRAnalytics.this.mBuffer.size() > 0) {
                            }
                            MRAnalytics.this.releaseLock();
                        } catch (Exception e50) {
                            String str27 = temp2;
                            e12 = e50;
                            e12.printStackTrace();
                            if (MRAnalytics.this.mBuffer.size() > 0) {
                            }
                            MRAnalytics.this.releaseLock();
                        }
                    } catch (IOException e51) {
                        temp = temp2;
                        e13 = e51;
                        e13.printStackTrace();
                        tempJsonArray2 = new JSONArray(temp);
                        new JSONArray();
                        if (MRAnalytics.this.mBuffer.size() > 0) {
                        }
                        if (tempJsonArray2.length() > 0) {
                        }
                        if (MRAnalytics.this.mBuffer.size() > 0) {
                        }
                        MRAnalytics.this.releaseLock();
                    } catch (Throwable th4) {
                        temp = temp2;
                        th = th4;
                        tempJsonArray = new JSONArray(temp);
                        new JSONArray();
                        if (MRAnalytics.this.mBuffer.size() > 0) {
                        }
                        if (tempJsonArray.length() <= 0) {
                        }
                        throw th;
                    }
                }
                if (MRAnalytics.this.mBuffer.size() > 0) {
                    MRAnalytics.this.resetTimer();
                }
                MRAnalytics.this.releaseLock();
            }
        }, TIMER_DELAY);
    }

    /* access modifiers changed from: private */
    public synchronized void setLock() {
        this.mIsLocked = true;
    }

    /* access modifiers changed from: private */
    public synchronized void releaseLock() {
        this.mIsLocked = false;
    }

    /* access modifiers changed from: private */
    public boolean isOnline() {
        NetworkInfo netInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
