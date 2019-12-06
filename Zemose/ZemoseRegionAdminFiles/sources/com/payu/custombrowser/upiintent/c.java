package com.payu.custombrowser.upiintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.PointerIconCompat;
import android.text.TextUtils;
import com.payu.custombrowser.PayUCustomBrowserCallback;
import com.payu.custombrowser.b.b;
import com.payu.custombrowser.util.CBConstant;
import com.payu.custombrowser.util.CBUtil;
import com.payu.custombrowser.widgets.a;
import com.payumoney.core.PayUmoneyConstants;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class c {
    /* access modifiers changed from: private */
    public WeakReference<Activity> a;
    /* access modifiers changed from: private */
    public String b;
    /* access modifiers changed from: private */
    public String c;
    /* access modifiers changed from: private */
    public PayUCustomBrowserCallback d;
    /* access modifiers changed from: private */
    public a e;
    /* access modifiers changed from: private */
    public String f;
    /* access modifiers changed from: private */
    public String g;
    /* access modifiers changed from: private */
    public String h;
    /* access modifiers changed from: private */
    public String i;
    /* access modifiers changed from: private */
    public String j;
    /* access modifiers changed from: private */
    public e k;
    private Payment l;
    /* access modifiers changed from: private */
    public Timer m;
    /* access modifiers changed from: private */
    public b n;
    private long o = 1200000;
    /* access modifiers changed from: private */
    public String p;
    /* access modifiers changed from: private */
    public boolean q;
    /* access modifiers changed from: private */
    public String r;
    /* access modifiers changed from: private */
    public String s;
    /* access modifiers changed from: private */
    public String t;
    /* access modifiers changed from: private */
    public String u;
    /* access modifiers changed from: private */
    public String v;
    /* access modifiers changed from: private */
    public List<a> w;
    /* access modifiers changed from: private */
    public com.payu.custombrowser.a.a x;

    c(Activity activity, String str, PayUCustomBrowserCallback payUCustomBrowserCallback) {
        this.a = new WeakReference<>(activity);
        this.b = str;
        this.k = new e();
        this.n = (b) activity;
        this.d = payUCustomBrowserCallback;
        this.l = this.k.a(str);
        this.q = this.l == Payment.GENERIC_INTENT;
        this.f = "https://secure.payu.in/_payment";
        this.x = com.payu.custombrowser.a.a.a(activity.getApplicationContext(), "local_cache_analytics");
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        new AsyncTask<String, Void, d>() {
            /* access modifiers changed from: protected */
            public void onPreExecute() {
                super.onPreExecute();
                if (c.this.a.get() != null && !((Activity) c.this.a.get()).isFinishing()) {
                    c cVar = c.this;
                    cVar.e = new a((Context) cVar.a.get());
                    c.this.e.setCancelable(false);
                    c.this.e.show();
                }
            }

            /* access modifiers changed from: protected */
            /* renamed from: a */
            public d doInBackground(String... strArr) {
                try {
                    CBUtil cBUtil = new CBUtil();
                    c.this.b = c.this.b.concat("&txn_s2s_flow=2");
                    HttpsURLConnection httpsConn = cBUtil.getHttpsConn(c.this.f, c.this.b);
                    if (httpsConn != null && httpsConn.getResponseCode() == 200) {
                        try {
                            StringBuffer stringBufferFromInputStream = CBUtil.getStringBufferFromInputStream(httpsConn.getInputStream());
                            if (stringBufferFromInputStream != null) {
                                JSONObject jSONObject = new JSONObject(stringBufferFromInputStream.toString());
                                c.this.i = jSONObject.optString("merchantName");
                                c.this.g = jSONObject.optString("returnUrl");
                                c.this.h = jSONObject.optString("merchantVpa");
                                c.this.j = jSONObject.optString("referenceId");
                                c.this.p = jSONObject.optString(PayUmoneyConstants.AMOUNT);
                                c.this.r = jSONObject.optString(CBConstant.TXNID);
                                c.this.u = jSONObject.optString("token");
                                c.this.t = jSONObject.optString("disableIntentSeamlessFailure");
                                c.this.s = jSONObject.optString("intentSdkCombineVerifyAndPayButton");
                                c.this.w = c.this.a(jSONObject.optJSONArray("apps"));
                                c.this.v = jSONObject.optString("vpaRegex");
                                if (!TextUtils.isEmpty(c.this.i) && !TextUtils.isEmpty(c.this.g) && !TextUtils.isEmpty(c.this.h)) {
                                    if (!TextUtils.isEmpty(c.this.j)) {
                                        d dVar = new d();
                                        dVar.a(c.this.i);
                                        dVar.c(c.this.h);
                                        dVar.d(c.this.j);
                                        dVar.b(c.this.g);
                                        dVar.e(c.this.p);
                                        dVar.i(c.this.u);
                                        dVar.b(c.this.g);
                                        if (!TextUtils.isEmpty(c.this.s) && !c.this.s.equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                                            dVar.h(c.this.s);
                                        }
                                        dVar.g(c.this.t);
                                        dVar.f(c.this.r);
                                        dVar.a(c.this.w);
                                        dVar.j(c.this.v);
                                        return dVar;
                                    }
                                }
                                return null;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return null;
            }

            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void onPostExecute(d dVar) {
                super.onPostExecute(dVar);
                if (c.this.e != null && c.this.e.isShowing() && !((Activity) c.this.a.get()).isFinishing()) {
                    c.this.e.dismiss();
                }
                if (dVar != null) {
                    c.this.n.a(dVar);
                    return;
                }
                c.this.d.onCBErrorReceived(PointerIconCompat.TYPE_HAND, "MERCHANT_INFO_NOT_PRESENT");
                ((Activity) c.this.a.get()).finish();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{""});
    }

    /* access modifiers changed from: 0000 */
    public void a(String str) {
        Intent intent = new Intent();
        intent.setPackage(str);
        String a2 = TextUtils.isEmpty(this.p) ? this.k.a(this.b, PayUmoneyConstants.AMOUNT) : this.p;
        e eVar = this.k;
        intent.setData(Uri.parse(eVar.a(this.h, this.i, a2, eVar.a(this.b, CBConstant.TXNID), this.j)));
        ((Activity) this.a.get()).startActivityForResult(intent, 101);
        b();
    }

    private void b() {
        if (this.m != null) {
            new CBUtil().cancelTimer(this.m);
        }
        this.m = new Timer();
        this.m.schedule(new TimerTask() {
            public void run() {
                if (c.this.a.get() != null && !((Activity) c.this.a.get()).isFinishing()) {
                    ((Activity) c.this.a.get()).runOnUiThread(new Runnable() {
                        public void run() {
                            c.this.a("failure", "timeout");
                        }
                    });
                }
            }
        }, this.o);
    }

    /* access modifiers changed from: 0000 */
    public void a(final String str, final String str2) {
        new AsyncTask<String, Void, String>() {
            /* access modifiers changed from: protected */
            public void onPreExecute() {
                super.onPreExecute();
                if (c.this.a.get() != null && !((Activity) c.this.a.get()).isFinishing()) {
                    c cVar = c.this;
                    cVar.e = new a((Context) cVar.a.get());
                    c.this.e.show();
                }
            }

            /* access modifiers changed from: protected */
            /* renamed from: a */
            public String doInBackground(String... strArr) {
                try {
                    CBUtil cBUtil = new CBUtil();
                    if (!c.this.q) {
                        c cVar = c.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("txnStatus=");
                        sb.append(str);
                        sb.append("&");
                        sb.append("failureReason");
                        sb.append("=");
                        sb.append(str2);
                        cVar.c = sb.toString();
                    } else {
                        String str = str.equalsIgnoreCase("cancel") ? "cancelTxn" : str.equalsIgnoreCase(CBConstant.FAIL) ? "failTxn" : "finish";
                        c cVar2 = c.this;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("token=");
                        sb2.append(c.this.u);
                        sb2.append("&");
                        sb2.append("action");
                        sb2.append("=");
                        sb2.append(str);
                        sb2.append("&");
                        sb2.append("failureReason");
                        sb2.append("=");
                        sb2.append(str2);
                        cVar2.c = sb2.toString();
                    }
                    HttpsURLConnection httpsConn = cBUtil.getHttpsConn(c.this.g, c.this.c);
                    if (httpsConn != null && httpsConn.getResponseCode() == 200) {
                        try {
                            StringBuffer stringBufferFromInputStream = CBUtil.getStringBufferFromInputStream(httpsConn.getInputStream());
                            if (stringBufferFromInputStream != null) {
                                return CBUtil.getBase64DecodedString(stringBufferFromInputStream.toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return null;
            }

            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void onPostExecute(String str) {
                super.onPostExecute(str);
                if (c.this.e != null && c.this.e.isShowing() && !((Activity) c.this.a.get()).isFinishing()) {
                    c.this.e.dismiss();
                }
                if (TextUtils.isEmpty(str)) {
                    c.this.x.a(CBUtil.getLogMessage(((Activity) c.this.a.get()).getApplicationContext(), "trxn_status", "failure_transaction", null, c.this.k.a(c.this.b, "key"), c.this.k.a(c.this.b, CBConstant.TXNID), null));
                    c.this.d.onPaymentFailure(null, null);
                } else if (c.this.b(str).equalsIgnoreCase("failure")) {
                    c.this.x.a(CBUtil.getLogMessage(((Activity) c.this.a.get()).getApplicationContext(), "trxn_status", "failure_transaction", null, c.this.k.a(c.this.b, "key"), c.this.k.a(c.this.b, CBConstant.TXNID), null));
                    c.this.d.onPaymentFailure(str, null);
                } else {
                    c.this.x.a(CBUtil.getLogMessage(((Activity) c.this.a.get()).getApplicationContext(), "trxn_status", "success_transaction", null, c.this.k.a(c.this.b, "key"), c.this.k.a(c.this.b, CBConstant.TXNID), null));
                    c.this.d.onPaymentSuccess(str, null);
                }
                if (c.this.m != null) {
                    new CBUtil().cancelTimer(c.this.m);
                }
                ((Activity) c.this.a.get()).finish();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{""});
    }

    /* access modifiers changed from: private */
    public List<a> a(JSONArray jSONArray) {
        if (jSONArray == null || jSONArray.length() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                arrayList.add(new a(jSONObject.optString("name"), null, jSONObject.optString("package")));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    public String b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("result")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("result");
                if (jSONObject2.has("Status".toLowerCase())) {
                    return jSONObject2.getString("Status".toLowerCase());
                }
            }
            return "failure";
        } catch (JSONException e2) {
            e2.printStackTrace();
            return "failure";
        }
    }
}
