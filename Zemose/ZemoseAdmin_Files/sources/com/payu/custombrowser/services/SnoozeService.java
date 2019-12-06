package com.payu.custombrowser.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.payu.custombrowser.Bank;
import com.payu.custombrowser.CBActivity;
import com.payu.custombrowser.R;
import com.payu.custombrowser.bean.CustomBrowserConfig;
import com.payu.custombrowser.util.CBConstant;
import com.payu.custombrowser.util.CBUtil;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Random;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import org.json.JSONException;
import org.json.JSONObject;

public class SnoozeService extends Service {
    /* access modifiers changed from: private */
    public static int b;
    private String A = "";
    /* access modifiers changed from: private */
    public String B = "";
    /* access modifiers changed from: private */
    public String C = "";
    private String D;
    /* access modifiers changed from: private */
    public boolean E;
    /* access modifiers changed from: private */
    public boolean F = true;
    /* access modifiers changed from: private */
    public boolean G;
    /* access modifiers changed from: private */
    public boolean H;
    /* access modifiers changed from: private */
    public boolean I;
    /* access modifiers changed from: private */
    public long J;
    /* access modifiers changed from: private */
    public boolean K;
    /* access modifiers changed from: private */
    public String L = "https://info.payu.in/merchant/postservice?form=2";
    private CustomBrowserConfig M;
    /* access modifiers changed from: private */
    public String N;
    /* access modifiers changed from: private */
    public CBUtil O;
    /* access modifiers changed from: private */
    public String P;
    private HashMap<String, String> Q;
    /* access modifiers changed from: private */
    public String R = null;
    /* access modifiers changed from: private */
    public String S = null;
    /* access modifiers changed from: private */
    public Runnable T = new Runnable() {
        public void run() {
            String str;
            String str2;
            String str3;
            try {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(SnoozeService.this.L).openConnection();
                SnoozeService.this.O;
                String str4 = null;
                if (!TextUtils.isEmpty(CBUtil.getCookie("PAYUID", SnoozeService.this.getApplicationContext()))) {
                    SnoozeService.this.O;
                    str = CBUtil.getCookie("PAYUID", SnoozeService.this.getApplicationContext());
                } else if (!TextUtils.isEmpty(SnoozeService.this.R)) {
                    str = SnoozeService.this.R;
                } else {
                    str = null;
                }
                SnoozeService.this.O;
                if (!TextUtils.isEmpty(CBUtil.getCookie(CBConstant.PHPSESSID, SnoozeService.this.getApplicationContext()))) {
                    SnoozeService.this.O;
                    str4 = CBUtil.getCookie(CBConstant.PHPSESSID, SnoozeService.this.getApplicationContext());
                } else if (!TextUtils.isEmpty(SnoozeService.this.S)) {
                    str4 = SnoozeService.this.S;
                } else if (TextUtils.isEmpty(SnoozeService.this.S)) {
                    str4 = "123456";
                }
                if (!TextUtils.isEmpty(SnoozeService.this.x)) {
                    str3 = SnoozeService.this.O.getDataFromPostData(SnoozeService.this.x, "key");
                    str2 = SnoozeService.this.O.getDataFromPostData(SnoozeService.this.x, "txnid");
                } else {
                    str3 = SnoozeService.this.B;
                    str2 = SnoozeService.this.C;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("command=verifyTxnStatus&var1=");
                sb.append(str2);
                sb.append("&key=");
                sb.append(str3);
                sb.append("&priorityParam=");
                sb.append(SnoozeService.this.P);
                String sb2 = sb.toString();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setConnectTimeout(CBConstant.VERIFY_HTTP_TIMEOUT);
                httpsURLConnection.setRequestProperty("Content-Type", CBConstant.HTTP_URLENCODED);
                httpsURLConnection.setRequestProperty("Content-Length", String.valueOf(sb2.length()));
                StringBuilder sb3 = new StringBuilder();
                sb3.append("PHPSESSID=");
                sb3.append(str4);
                sb3.append("; PAYUID=");
                sb3.append(str);
                httpsURLConnection.setRequestProperty("Cookie", sb3.toString());
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.getOutputStream().write(sb2.getBytes());
                byte[] bArr = new byte[1024];
                if (httpsURLConnection.getResponseCode() != 200) {
                    SnoozeService.this.b("{\"api_status\":\"0\",\"message\":\"Some error occurred\"}");
                } else if (httpsURLConnection.getInputStream() != null) {
                    StringBuffer stringBufferFromInputStream = CBUtil.getStringBufferFromInputStream(httpsURLConnection.getInputStream());
                    if (stringBufferFromInputStream != null) {
                        new JSONObject(stringBufferFromInputStream.toString());
                        SnoozeService.this.N = stringBufferFromInputStream.toString();
                        SnoozeService.this.b(stringBufferFromInputStream.toString());
                    }
                }
            } catch (Exception e) {
                SnoozeService.this.b("{\"api_status\":\"0\",\"message\":\"Some exception occurred\"}");
                e.printStackTrace();
            }
        }
    };
    String a = CBConstant.MERCHANT_CHECKOUT_ACTIVITY;
    /* access modifiers changed from: private */
    public int c = 1800000;
    private final int d = 500;
    private final String e = "webview_status_action";
    private final String f = "snooze_broad_cast_message";
    private final String g = CBConstant.CURRENT_URL;
    private final String h = CBConstant.S2S_RETRY_URL;
    private final IBinder i = new b();
    /* access modifiers changed from: private */
    public Handler j;
    /* access modifiers changed from: private */
    public Runnable k;
    /* access modifiers changed from: private */
    public Handler l;
    private HandlerThread m;
    /* access modifiers changed from: private */
    public CountDownTimer n;
    private Looper o;
    private a p;
    /* access modifiers changed from: private */
    public long q;
    /* access modifiers changed from: private */
    public long r;
    /* access modifiers changed from: private */
    public int s = 1000;
    /* access modifiers changed from: private */
    public int t = 60000;
    /* access modifiers changed from: private */
    public long u;
    /* access modifiers changed from: private */
    public String v = "";
    /* access modifiers changed from: private */
    public String w = "";
    /* access modifiers changed from: private */
    public String x = "";
    private String y = "";
    private String z = "";

    private final class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            SnoozeService.this.F = true;
            SnoozeService snoozeService = SnoozeService.this;
            AnonymousClass1 r1 = new CountDownTimer((long) snoozeService.c, 5000) {
                public void onTick(long l) {
                    SnoozeService.this.J = (((long) SnoozeService.this.c) - l) / 1000;
                }

                public void onFinish() {
                    if (!SnoozeService.this.H && CBActivity.b == 2) {
                        SnoozeService.this.e();
                        SnoozeService.this.a("internet_not_restored_notification", "-1");
                    } else if (!SnoozeService.this.H && CBActivity.b == 1) {
                        SnoozeService.this.a("internet_not_restored_dialog_foreground", "-1");
                    }
                    if (SnoozeService.this.G && !SnoozeService.this.H) {
                        Intent intent = new Intent("webview_status_action");
                        intent.putExtra(CBConstant.SNOOZE_SERVICE_STATUS, CBConstant.SNOOZE_SERVICE_DEAD);
                        LocalBroadcastManager.getInstance(SnoozeService.this).sendBroadcast(intent);
                    }
                    SnoozeService.this.a();
                }
            };
            snoozeService.n = r1;
            SnoozeService.this.n.start();
            SnoozeService.this.l = new Handler();
            SnoozeService.this.l.postDelayed(new Runnable() {
                public void run() {
                    if (SnoozeService.this.F) {
                        if (SnoozeService.this.v.contentEquals(SnoozeService.this.w)) {
                            SnoozeService.this.G = true;
                        } else {
                            SnoozeService.this.G = false;
                            if (SnoozeService.this.F && SnoozeService.this.K && SnoozeService.this.H && SnoozeService.this.I) {
                                SnoozeService.this.c(SnoozeService.this.N);
                            } else if (SnoozeService.this.F && SnoozeService.this.H && SnoozeService.this.I) {
                                SnoozeService.this.a(SnoozeService.this.G);
                            }
                        }
                        SnoozeService.this.l.postDelayed(this, 500);
                        Intent intent = new Intent("webview_status_action");
                        SnoozeService snoozeService = SnoozeService.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("");
                        sb.append(System.currentTimeMillis());
                        snoozeService.v = sb.toString();
                        intent.putExtra("snooze_broad_cast_message", SnoozeService.this.v);
                        LocalBroadcastManager.getInstance(SnoozeService.this).sendBroadcast(intent);
                    }
                }
            }, 500);
            SnoozeService.this.c();
        }
    }

    public class b extends Binder {
        public b() {
        }

        public SnoozeService a() {
            return SnoozeService.this;
        }
    }

    /* access modifiers changed from: private */
    public void b(String str) {
        try {
            String valueOfJSONKey = this.O.getValueOfJSONKey(str, getString(R.string.cb_snooze_verify_api_status));
            if (CBActivity.b == 2) {
                if (valueOfJSONKey.contentEquals("1")) {
                    a("transaction_verified_notification", "-1");
                } else {
                    a("transaction_not_verified_notification", "-1");
                }
                c(str);
                return;
            }
            if (valueOfJSONKey.contentEquals("1")) {
                a("transaction_verified_dialog_foreground", "-1");
            } else {
                a("transaction_not_verified_dialog_foreground", "-1");
            }
            a(CBConstant.BACKWARD_JOURNEY_STATUS, str, false);
            a();
        } catch (JSONException e2) {
            e2.printStackTrace();
            if (CBActivity.b == 2) {
                a("transaction_not_verified_notification", "-1");
                c(str);
                return;
            }
            a("transaction_not_verified_dialog_foreground", "-1");
            a(CBConstant.BACKWARD_JOURNEY_STATUS, str, false);
            a();
        }
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        this.G = true;
        return this.i;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        this.O = new CBUtil();
        this.D = intent.getStringExtra(this.a);
        this.M = (CustomBrowserConfig) intent.getParcelableExtra(CBConstant.CB_CONFIG);
        this.c = this.M.getSurePayBackgroundTTL();
        this.Q = this.O.getDataFromPostData(this.M.getPayuPostData());
        b = Bank.snoozeImageDownloadTimeout > 0 ? Bank.snoozeImageDownloadTimeout : 10000;
        if (!intent.getExtras().containsKey(CBConstant.VERIFICATION_MSG_RECEIVED) || !intent.getExtras().getBoolean(CBConstant.VERIFICATION_MSG_RECEIVED)) {
            this.K = false;
            this.z = intent.getStringExtra(CBConstant.CURRENT_URL);
            this.A = intent.getStringExtra(CBConstant.S2S_RETRY_URL);
        } else {
            this.K = true;
            if (intent.getExtras().containsKey(CBConstant.VERIFY_ADDON_PARAMS)) {
                this.P = intent.getExtras().getString(CBConstant.VERIFY_ADDON_PARAMS);
            }
            this.x = this.M.getPayuPostData();
            this.y = this.M.getPostURL();
            this.B = intent.getStringExtra("merchantKey");
            this.C = intent.getStringExtra("txnid");
            this.R = intent.getStringExtra("PAYUID");
        }
        Message obtainMessage = this.p.obtainMessage();
        obtainMessage.arg1 = startId;
        this.p.sendMessage(obtainMessage);
        if (Bank.hasToStart) {
            return 3;
        }
        return 2;
    }

    public void onCreate() {
        this.m = new HandlerThread("SnoozeServiceHandlerThread", 10);
        this.m.start();
        this.o = this.m.getLooper();
        this.p = new a(this.o);
    }

    public void a() {
        this.F = false;
        CountDownTimer countDownTimer = this.n;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.n = null;
        }
        this.m.interrupt();
        stopSelf();
    }

    public void a(String str) {
        this.w = str;
    }

    /* access modifiers changed from: private */
    public void c() {
        this.j = new Handler(this.o);
        this.k = new Runnable() {
            public void run() {
                if (SnoozeService.this.F) {
                    SnoozeService.this.d();
                }
            }
        };
        this.j.postDelayed(this.k, (long) Math.min(this.s, this.t));
    }

    /* access modifiers changed from: private */
    public void d() {
        this.E = false;
        StringBuilder sb = new StringBuilder();
        sb.append(CBConstant.SNOOZE_IMAGE_DOWNLOAD_END_POINT);
        sb.append(CBConstant.SNOOZE_IMAGE_COLLECTIONS[new Random().nextInt(2)]);
        final String sb2 = sb.toString();
        final AnonymousClass3 r1 = new CountDownTimer((long) b, 1000) {
            public void onTick(long l) {
            }

            public void onFinish() {
                cancel();
                SnoozeService.this.E = true;
            }
        };
        r1.start();
        new Thread(new Runnable() {
            public void run() {
                try {
                    SnoozeService.this.O;
                    if (CBUtil.isNetworkAvailable(SnoozeService.this.getApplicationContext())) {
                        SnoozeService.this.q = System.currentTimeMillis();
                        URLConnection openConnection = new URL(sb2).openConnection();
                        openConnection.setUseCaches(false);
                        openConnection.connect();
                        openConnection.getContentLength();
                        InputStream inputStream = openConnection.getInputStream();
                        byte[] bArr = new byte[1024];
                        while (!SnoozeService.this.E && inputStream.read(bArr) != -1) {
                        }
                        if (SnoozeService.this.E) {
                            r1.cancel();
                            inputStream.close();
                            SnoozeService.this.u = (long) (SnoozeService.b + 1);
                        } else {
                            r1.cancel();
                            SnoozeService.this.r = System.currentTimeMillis();
                            inputStream.close();
                            SnoozeService.this.u = SnoozeService.this.r - SnoozeService.this.q;
                        }
                        if (SnoozeService.this.u > ((long) SnoozeService.b)) {
                            SnoozeService.this.s = SnoozeService.this.s + SnoozeService.this.s;
                            SnoozeService.this.j.postDelayed(SnoozeService.this.k, (long) Math.min(SnoozeService.this.s, SnoozeService.this.t));
                        } else if (SnoozeService.this.F) {
                            if (SnoozeService.this.K) {
                                SnoozeService.this.a("snooze_verify_api_status", "snooze_verify_api_called");
                                new Thread(SnoozeService.this.T).start();
                            } else if (CBActivity.b == 1) {
                                SnoozeService.this.a(SnoozeService.this.getString(R.string.internet_restored), SnoozeService.this.getString(R.string.resuming_your_transaction), true);
                                SnoozeService.this.a("internet_restored_dialog_foreground", "-1");
                                SnoozeService.this.a();
                            } else {
                                SnoozeService.this.a(SnoozeService.this.G);
                                SnoozeService.this.a("internet_restored_notification", "-1");
                            }
                        }
                        return;
                    }
                    SnoozeService.this.j.postDelayed(SnoozeService.this.k, (long) Math.min(SnoozeService.this.s, SnoozeService.this.t));
                } catch (MalformedURLException e) {
                    SnoozeService.this.u = -1;
                    r1.cancel();
                    e.printStackTrace();
                } catch (SSLException e2) {
                    SnoozeService.this.j.postDelayed(SnoozeService.this.k, (long) Math.min(SnoozeService.this.s, SnoozeService.this.t));
                    e2.printStackTrace();
                } catch (IOException e3) {
                    SnoozeService.this.u = -1;
                    r1.cancel();
                    e3.printStackTrace();
                } catch (Exception e4) {
                    SnoozeService.this.u = -1;
                    r1.cancel();
                }
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public void a(boolean z2) {
        boolean z3;
        Builder builder = new Builder(this, this.M.getSurePayNotificationChannelId());
        Builder defaults = builder.setContentTitle(this.M.getSurePayNotificationGoodNetworkTitle()).setContentText(this.M.getSurePayNotificationGoodNetWorkHeader()).setSmallIcon(this.M.getSurePayNotificationIcon()).setAutoCancel(true).setPriority(1).setDefaults(2);
        BigTextStyle bigTextStyle = new BigTextStyle();
        StringBuilder sb = new StringBuilder();
        sb.append(this.M.getSurePayNotificationGoodNetWorkHeader());
        sb.append("\n\n");
        sb.append(this.M.getSurePayNotificationGoodNetWorkBody());
        defaults.setStyle(bigTextStyle.bigText(sb.toString()));
        if (VERSION.SDK_INT >= 23) {
            builder.setColor(getResources().getColor(R.color.cb_blue_button, null));
        } else {
            builder.setColor(getResources().getColor(R.color.cb_blue_button));
        }
        this.H = true;
        Intent intent = new Intent();
        intent.putExtra(CBConstant.CURRENT_URL, this.z);
        intent.putExtra(CBConstant.S2S_RETRY_URL, this.A);
        intent.putExtra(CBConstant.SENDER, CBConstant.SNOOZE_SERVICE);
        if (z2) {
            this.I = true;
            intent.setFlags(536870912);
            intent.putExtra(CBConstant.CURRENT_URL, this.z);
            intent.putExtra(CBConstant.CB_CONFIG, this.M);
            intent.setClass(getApplicationContext(), CBActivity.class);
            z3 = true;
        } else {
            Intent intent2 = new Intent();
            Context applicationContext = getApplicationContext();
            String str = this.D;
            if (str == null) {
                str = "";
            }
            intent2.setClassName(applicationContext, str);
            if (intent2.resolveActivityInfo(getPackageManager(), 0) != null) {
                intent.setClassName(getApplicationContext(), this.D);
                intent.putExtra(CBConstant.POST_TYPE, "sure_pay_payment_data");
                intent.putExtra(CBConstant.POST_DATA, this.M.getPayuPostData());
                z3 = true;
            } else {
                z3 = false;
            }
            a("snooze_notification_expected_action", "merchant_checkout_page");
            this.I = false;
            a();
        }
        if (z3) {
            try {
                builder.setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, intent, 134217728));
                ((NotificationManager) getSystemService("notification")).notify(CBConstant.SNOOZE_NOTIFICATION_ID, builder.build());
                a(CBConstant.GOOD_NETWORK_NOTIFICATION_LAUNCHED, "true", true);
            } catch (ActivityNotFoundException e2) {
                e2.printStackTrace();
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("The Activity ");
            sb2.append(this.D);
            sb2.append(" is not found, Please set valid activity ");
            throw new ActivityNotFoundException(sb2.toString());
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2) {
        Intent intent = new Intent("webview_status_action");
        intent.putExtra("BROAD_CAST_FROM_SNOOZE_SERVICE", true);
        intent.putExtra("event_key", str);
        intent.putExtra("event_value", str2);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void c(String str) {
        StringBuilder sb;
        try {
            String valueOfJSONKey = this.O.getValueOfJSONKey(str, getString(R.string.cb_snooze_verify_api_status));
            StringBuilder sb2 = new StringBuilder();
            sb2.append(valueOfJSONKey);
            sb2.append("");
            a("snooze_verify_api_response_received", sb2.toString());
            Builder builder = new Builder(this, this.M.getSurePayNotificationChannelId());
            if (valueOfJSONKey.contentEquals("1")) {
                sb = new StringBuilder();
                sb.append(this.M.getSurePayNotificationTransactionVerifiedHeader());
                sb.append("\n\n");
                sb.append(this.M.getSurePayNotificationTransactionVerifiedBody());
            } else {
                sb = new StringBuilder();
                sb.append(this.M.getSurePayNotificationTransactionNotVerifiedHeader());
                sb.append("\n\n");
                sb.append(this.M.getSurePayNotificationTransactionNotVerifiedBody());
            }
            boolean z2 = true;
            builder.setContentTitle(valueOfJSONKey.contentEquals("1") ? this.M.getSurePayNotificationTransactionVerifiedTitle() : this.M.getSurePayNotificationTransactionNotVerifiedTitle()).setContentText(valueOfJSONKey.contentEquals("1") ? this.M.getSurePayNotificationTransactionVerifiedHeader() : this.M.getSurePayNotificationTransactionNotVerifiedHeader()).setSmallIcon(this.M.getSurePayNotificationIcon()).setAutoCancel(true).setPriority(1).setDefaults(2).setStyle(new BigTextStyle().bigText(sb.toString()));
            Intent intent = new Intent();
            intent.putExtra(CBConstant.CB_CONFIG, this.M);
            this.H = true;
            intent.putExtra(CBConstant.PAYU_RESPONSE, str);
            if (this.G) {
                intent.setFlags(805306368);
                this.I = true;
                intent.putExtra(CBConstant.SENDER, CBConstant.SNOOZE_SERVICE);
                intent.putExtra(CBConstant.VERIFICATION_MSG_RECEIVED, true);
                intent.setClass(getApplicationContext(), CBActivity.class);
            } else {
                Intent intent2 = new Intent();
                intent2.setClassName(getApplicationContext(), this.D == null ? "" : this.D);
                if (intent2.resolveActivityInfo(getPackageManager(), 0) != null) {
                    intent.putExtra(CBConstant.POST_DATA, str);
                    intent.setClassName(getApplicationContext(), this.D);
                    intent.putExtra(CBConstant.POST_TYPE, "verify_response_post_data");
                } else {
                    z2 = false;
                }
                a("snooze_notification_expected_action", "merchant_checkout_page");
                this.I = false;
                a();
            }
            if (z2) {
                try {
                    builder.setContentIntent(PendingIntent.getActivity(this, 0, intent, 134217728));
                    ((NotificationManager) getSystemService("notification")).notify(CBConstant.TRANSACTION_STATUS_NOTIFICATION_ID, builder.build());
                    a(CBConstant.GOOD_NETWORK_NOTIFICATION_LAUNCHED, str, false);
                } catch (ActivityNotFoundException e2) {
                    e2.printStackTrace();
                }
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("The Activity ");
                sb3.append(this.D);
                sb3.append(" is not found, Please set valid activity ");
                throw new ActivityNotFoundException(sb3.toString());
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        a("snooze_notification_expected_action", "merchant_checkout_page");
        Builder builder = new Builder(this, this.M.getSurePayNotificationChannelId());
        Builder defaults = builder.setContentTitle(this.M.getSurePayNotificationPoorNetWorkTitle()).setContentText(this.M.getSurePayNotificationPoorNetWorkHeader()).setSmallIcon(this.M.getSurePayNotificationIcon()).setAutoCancel(true).setPriority(1).setDefaults(2);
        BigTextStyle bigTextStyle = new BigTextStyle();
        StringBuilder sb = new StringBuilder();
        sb.append(this.M.getSurePayNotificationPoorNetWorkHeader());
        sb.append(this.M.getSurePayNotificationPoorNetWorkBody());
        defaults.setStyle(bigTextStyle.bigText(sb.toString()));
        if (VERSION.SDK_INT >= 23) {
            builder.setColor(getResources().getColor(R.color.cb_blue_button, null));
        } else {
            builder.setColor(getResources().getColor(R.color.cb_blue_button));
        }
        Intent intent = new Intent();
        Context applicationContext = getApplicationContext();
        String str = this.D;
        if (str == null) {
            str = "";
        }
        intent.setClassName(applicationContext, str);
        if (intent.resolveActivityInfo(getPackageManager(), 0) != null) {
            Intent intent2 = new Intent();
            intent2.setClassName(getApplicationContext(), this.D);
            intent2.putExtra(CBConstant.POST_TYPE, "sure_pay_payment_data");
            intent2.putExtra(CBConstant.POST_DATA, this.M.getPayuPostData());
            builder.setContentIntent(PendingIntent.getActivity(this, 0, intent2, 134217728));
            ((NotificationManager) getSystemService("notification")).notify(CBConstant.SNOOZE_NOTIFICATION_ID, builder.build());
            return;
        }
        try {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("The Activity ");
            sb2.append(this.D);
            sb2.append(" is not found, Please set valid activity ");
            throw new ActivityNotFoundException(sb2.toString());
        } catch (ActivityNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2, boolean z2) {
        Intent intent = new Intent("webview_status_action");
        intent.putExtra(CBConstant.BROADCAST_FROM_SERVICE_UPDATE_UI, true);
        intent.putExtra("key", str);
        intent.putExtra("value", str2);
        intent.putExtra(CBConstant.CURRENT_URL, this.z);
        intent.putExtra(CBConstant.S2S_RETRY_URL, this.A);
        intent.putExtra(CBConstant.CB_CONFIG, this.M);
        intent.putExtra(CBConstant.IS_FORWARD_JOURNEY, z2);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
