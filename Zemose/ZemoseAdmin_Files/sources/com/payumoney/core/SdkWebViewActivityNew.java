package com.payumoney.core;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.payu.custombrowser.Bank;
import com.payu.custombrowser.CustomBrowser;
import com.payu.custombrowser.PayUCustomBrowserCallback;
import com.payu.custombrowser.PayUWebChromeClient;
import com.payu.custombrowser.PayUWebViewClient;
import com.payu.custombrowser.bean.CustomBrowserConfig;
import com.payu.custombrowser.bean.ReviewOrderBundle;
import com.payu.magicretry.MagicRetryFragment;
import com.payumoney.core.analytics.LogAnalytics;
import com.payumoney.core.listener.OnVerifyPaymentResponse;
import com.payumoney.core.listener.ReviewOrderImpl;
import com.payumoney.core.request.PaymentRequest;
import com.payumoney.core.utils.AnalyticsConstant;
import com.payumoney.core.utils.SharedPrefsUtils;
import com.payumoney.core.utils.SharedPrefsUtils.Keys;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public class SdkWebViewActivityNew extends AppCompatActivity implements OnVerifyPaymentResponse {
    String a = null;
    private boolean b;
    private Map<String, String> c;
    /* access modifiers changed from: private */
    public String d;
    /* access modifiers changed from: private */
    public String e;
    private PaymentRequest f;
    /* access modifiers changed from: private */
    public String g;
    private ProgressDialog h;
    /* access modifiers changed from: private */
    public Bank i;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        final String str;
        JSONObject jSONObject;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdk_activity_web_view_dummy);
        if (getIntent().getExtras().getString("merchantKey") == null) {
            str = "could not find";
        } else {
            str = getIntent().getExtras().getString("merchantKey");
        }
        this.h = new ProgressDialog(this);
        AnonymousClass1 r1 = new PayUCustomBrowserCallback() {
            public void onPaymentFailure(String payuResponse, String merchantResponse) {
                SdkWebViewActivityNew.this.d = merchantResponse;
                SdkWebViewActivityNew.this.g = "onPaymentFailure";
                if (PayUmoneySdkInitializer.IsDebugMode().booleanValue()) {
                    String str = PayUmoneyConstants.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failure -- payuResponse");
                    sb.append(payuResponse);
                    Log.i(str, sb.toString());
                    String str2 = PayUmoneyConstants.TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Failure -- merchantResponse");
                    sb2.append(merchantResponse);
                    Log.i(str2, sb2.toString());
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        SdkSession.getInstance(SdkWebViewActivityNew.this.getBaseContext()).verifyPaymentDetails(SdkWebViewActivityNew.this.e, SdkWebViewActivityNew.this);
                    }
                }, 1000);
            }

            public void onPaymentTerminate() {
            }

            public void onPaymentSuccess(String payuResponse, String merchantResponse) {
                SdkWebViewActivityNew.this.d = merchantResponse;
                SdkWebViewActivityNew.this.g = "onPaymentSuccess";
                if (PayUmoneySdkInitializer.IsDebugMode().booleanValue()) {
                    String str = PayUmoneyConstants.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Success -- payuResponse");
                    sb.append(payuResponse);
                    Log.i(str, sb.toString());
                    String str2 = PayUmoneyConstants.TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Success -- merchantResponse");
                    sb2.append(merchantResponse);
                    Log.i(str2, sb2.toString());
                }
                SdkSession.getInstance(SdkWebViewActivityNew.this.getBaseContext()).verifyPaymentDetails(SdkWebViewActivityNew.this.e, SdkWebViewActivityNew.this);
            }

            public void onCBErrorReceived(int code, String errormsg) {
            }

            public void setCBProperties(WebView webview, Bank payUCustomBrowser) {
                webview.setWebChromeClient(new PayUWebChromeClient(payUCustomBrowser));
                webview.setWebViewClient(new PayUWebViewClient(payUCustomBrowser, str));
                SdkWebViewActivityNew.this.i = payUCustomBrowser;
            }

            public void onBackApprove() {
                SdkWebViewActivityNew.this.cancelTransaction();
            }

            public void onBackDismiss() {
                HashMap hashMap = new HashMap();
                hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                hashMap.put(AnalyticsConstant.TXN_CANCELLED, "false");
                LogAnalytics.logEvent(SdkWebViewActivityNew.this.getApplicationContext(), AnalyticsConstant.TXN_CANCEL_ATTEMPT, hashMap, AnalyticsConstant.CLEVERTAP);
                super.onBackDismiss();
            }

            public void onBackButton(Builder alertDialogBuilder) {
                super.onBackButton(alertDialogBuilder);
                alertDialogBuilder.setMessage("Press Ok to cancel the transaction. You will have to restart the transaction");
            }

            public void initializeMagicRetry(Bank payUCustomBrowser, WebView webview, MagicRetryFragment magicRetryFragment) {
                webview.setWebViewClient(new PayUWebViewClient(payUCustomBrowser, magicRetryFragment, str));
                payUCustomBrowser.setMagicRetry(new HashMap());
            }
        };
        try {
            JSONObject jSONObject2 = new JSONObject(getIntent().getStringExtra("result"));
            this.c = new HashMap();
            Iterator keys = jSONObject2.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                this.c.put(str2, jSONObject2.getString(str2));
            }
            this.c.put("device_type", "1");
            if (getIntent().getStringExtra(PayUmoneyConstants.PAYMENT_MODE).equalsIgnoreCase(PayUmoneyConstants.PAYMENT_MODE_CASH_CARD)) {
                this.c.put("pg", PayUmoneyConstants.PAYMENT_MODE_CASH);
            } else {
                this.c.put("pg", getIntent().getStringExtra(PayUmoneyConstants.PAYMENT_MODE));
            }
            this.e = getIntent().getStringExtra(PayUmoneyConstants.PAYMENT_ID);
            this.f = (PaymentRequest) getIntent().getParcelableExtra(PayUmoneyConstants.PAYMENT_REQUEST);
            if (this.f != null) {
                if (!this.f.getPg().equalsIgnoreCase(PayUmoneyConstants.CC)) {
                    if (!this.f.getPg().equalsIgnoreCase(PayUmoneyConstants.DC)) {
                        if (this.f.getPg().equalsIgnoreCase(PayUmoneyConstants.EMI)) {
                        }
                    }
                }
                if (this.f.getCardtoken() == null) {
                    this.c.put(PayUmoneyConstants.CARD_NUMBER, this.f.getCardNumber());
                    this.c.put(PayUmoneyConstants.CARD_CVV, this.f.getCvv());
                    this.c.put(PayUmoneyConstants.CARD_EXPIRY_YEAR, this.f.getExpiryYear());
                    this.c.put(PayUmoneyConstants.CARD_EXPIRY_MONTH, this.f.getExpiryMonth());
                }
            }
            if (this.f != null && this.f.getPg().equalsIgnoreCase(PayUmoneyConstants.UPI)) {
                this.c.put(PayUmoneyConstants.PM_KEY_VPA, this.f.getVpa());
            }
            this.c.put(PayUmoneyConstants.BANK_CODE, getIntent().getStringExtra(PayUmoneyConstants.BANK_CODE));
            if (getIntent().getStringExtra(PayUmoneyConstants.STORE_CARD_TOKEN) != null) {
                if (this.f != null) {
                    this.c.put(PayUmoneyConstants.CARD_CVV, this.f.getCvv());
                }
                this.c.put(PayUmoneyConstants.STORE_CARD_TOKEN, getIntent().getStringExtra(PayUmoneyConstants.STORE_CARD_TOKEN));
            }
            if (getIntent().getStringExtra("card_name") != null) {
                this.c.put("card_name", getIntent().getStringExtra("card_name"));
            }
            if (getIntent().getStringExtra(PayUmoneyConstants.STORE_CARD) != null) {
                this.c.put(PayUmoneyConstants.STORE_CARD, getIntent().getStringExtra(PayUmoneyConstants.STORE_CARD));
            }
            String string = jSONObject2.getString("txnid");
            if (string == null) {
                string = String.valueOf(System.currentTimeMillis());
            }
            CustomBrowserConfig customBrowserConfig = new CustomBrowserConfig("123454", string);
            if (getIntent().getStringExtra(PayUmoneyConstants.PAYMENT_MODE).equals(PayUmoneyConstants.PAYMENT_MODE_NB)) {
                this.b = true;
            }
            customBrowserConfig.setViewPortWideEnable(this.b);
            SharedPreferences sharedPreferences = getSharedPreferences(PayUmoneyConstants.SP_SP_NAME, 0);
            String string2 = getIntent().getExtras().getString(PayUmoneyConstants.PAYMENT_MODE);
            Boolean valueOf = Boolean.valueOf(false);
            Boolean valueOf2 = Boolean.valueOf(false);
            if (!sharedPreferences.contains(PayUmoneyConstants.CONFIG_DTO) || !sharedPreferences.contains(PayUmoneyConstants.ONE_TAP_FEATURE) || !sharedPreferences.getBoolean(PayUmoneyConstants.ONE_TAP_FEATURE, false)) {
                jSONObject = null;
            } else {
                jSONObject = new JSONObject(sharedPreferences.getString(PayUmoneyConstants.CONFIG_DTO, "XYZ"));
            }
            if (jSONObject != null) {
                if (jSONObject.has(PayUmoneyConstants.ONE_CLICK_PAYMENT) && !jSONObject.isNull(PayUmoneyConstants.ONE_CLICK_PAYMENT)) {
                    valueOf = Boolean.valueOf(jSONObject.optBoolean(PayUmoneyConstants.ONE_CLICK_PAYMENT));
                    if (valueOf.booleanValue() && jSONObject.has(PayUmoneyConstants.ONE_TAP_FEATURE) && !jSONObject.isNull(PayUmoneyConstants.ONE_TAP_FEATURE)) {
                        valueOf2 = Boolean.valueOf(jSONObject.optBoolean(PayUmoneyConstants.ONE_TAP_FEATURE));
                    }
                }
            }
            if (jSONObject != null && jSONObject.has("userToken") && !jSONObject.isNull("userToken")) {
                this.a = jSONObject.getString("userToken");
            }
            if (string2 != null && valueOf.booleanValue()) {
                if (!string2.equals("")) {
                    if (!string2.equals(PayUmoneyConstants.PAYMENT_MODE_DC)) {
                        if (string2.equals(PayUmoneyConstants.PAYMENT_MODE_CC)) {
                        }
                    }
                    this.c.put(PayUmoneyConstants.ONE_CLICK_CHECKOUT, "1");
                    if (this.a != null && !this.a.isEmpty()) {
                        this.c.put("userToken", this.a);
                    }
                } else if (getIntent().getExtras().getString("cardHashForOneClickTxn").equals("0")) {
                    this.c.put(PayUmoneyConstants.ONE_CLICK_CHECKOUT, "1");
                    if (this.a != null && !this.a.isEmpty()) {
                        this.c.put("userToken", this.a);
                    }
                } else {
                    this.c.put(PayUmoneyConstants.CARD_MERCHANT_PARAM, getIntent().getExtras().getString("cardHashForOneClickTxn"));
                }
            }
            if (valueOf2.booleanValue()) {
                customBrowserConfig.setAutoApprove(true);
                customBrowserConfig.setAutoSelectOTP(true);
            } else {
                customBrowserConfig.setAutoApprove(false);
                customBrowserConfig.setAutoSelectOTP(false);
            }
            try {
                if (PayUmoneyConfig.getInstance().isEnableReviewOrder()) {
                    ReviewOrderBundle reviewOrderBundle = PayUmoneyConfig.getInstance().getReviewOrderBundle();
                    String reviewOrderMenuText = PayUmoneyConfig.getInstance().getReviewOrderMenuText();
                    if (TextUtils.isEmpty(reviewOrderMenuText)) {
                        reviewOrderMenuText = getString(R.string.review_order_default_text);
                    }
                    if (reviewOrderBundle != null) {
                        final ReviewOrderImpl reviewOrderImpl = PayUmoneyConfig.getInstance().getReviewOrderImpl();
                        if (reviewOrderImpl == null) {
                            customBrowserConfig.setReviewOrderDefaultViewData(reviewOrderBundle);
                            customBrowserConfig.setReviewOrderButtonText(reviewOrderMenuText);
                            customBrowserConfig.setEnableReviewOrder(0);
                        } else {
                            View inflate = LayoutInflater.from(this).inflate(R.layout.sdk_review_order_toolbar, null);
                            if (inflate != null) {
                                customBrowserConfig.setToolBarView(inflate);
                                a(inflate);
                                int reviewOrderToolbarBgColor = PayUmoneyConfig.getInstance().getReviewOrderToolbarBgColor();
                                if (reviewOrderToolbarBgColor != -1) {
                                    inflate.setBackgroundColor(reviewOrderToolbarBgColor);
                                }
                                int reviewOrderToolbarTextColor = PayUmoneyConfig.getInstance().getReviewOrderToolbarTextColor();
                                if (reviewOrderToolbarTextColor != -1) {
                                    a(inflate, reviewOrderToolbarTextColor);
                                }
                                TextView textView = (TextView) inflate.findViewById(R.id.btn_webview_review_order);
                                if (textView != null) {
                                    textView.setText(reviewOrderMenuText);
                                    textView.setOnClickListener(new OnClickListener() {
                                        public void onClick(View v) {
                                            SdkWebViewActivityNew.this.startActivity(reviewOrderImpl.getReviewOrderScreenIntent(SdkWebViewActivityNew.this));
                                        }
                                    });
                                }
                                View findViewById = inflate.findViewById(R.id.img_webview_review_order_back);
                                if (findViewById != null) {
                                    findViewById.setOnClickListener(new OnClickListener() {
                                        public void onClick(View v) {
                                            if (SdkWebViewActivityNew.this.i != null) {
                                                SdkWebViewActivityNew.this.i.showBackButtonDialog();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            customBrowserConfig.setDisableBackButtonDialog(false);
            customBrowserConfig.setStoreOneClickHash(1);
            customBrowserConfig.setMerchantSMSPermission(true);
            customBrowserConfig.setmagicRetry(true);
            customBrowserConfig.setPostURL(PayUmoneySdkInitializer.getWebviewRedirectionUrl());
            customBrowserConfig.setPayuPostData(a(this.c));
            new CustomBrowser().addCustomBrowser(this, customBrowserConfig, r1);
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        String stringPreference = SharedPrefsUtils.getStringPreference(this, Keys.MERCHANT_NAME);
        if (stringPreference == null || stringPreference.equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
            stringPreference = PayUmoneyConstants.SP_SP_NAME;
        }
        ((TextView) view.findViewById(R.id.tv_webview_review_order_title)).setText(stringPreference);
    }

    /* access modifiers changed from: protected */
    public void a(View view, int i2) {
        ((TextView) view.findViewById(R.id.tv_webview_review_order_title)).setTextColor(i2);
        ((TextView) view.findViewById(R.id.btn_webview_review_order)).setTextColor(i2);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_webview_review_order_back);
        if (imageView != null) {
            imageView.setColorFilter(i2);
        }
    }

    public void cancelTransaction() {
        a();
        this.g = "onPaymentCancelled";
        HashMap hashMap = new HashMap();
        hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        hashMap.put(AnalyticsConstant.TXN_CANCELLED, "true");
        LogAnalytics.logEvent(getApplicationContext(), AnalyticsConstant.TXN_CANCEL_ATTEMPT, hashMap, AnalyticsConstant.CLEVERTAP);
        HashMap hashMap2 = new HashMap();
        hashMap2.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        hashMap2.put(AnalyticsConstant.REASON, PayUmoneyConstants.USER_CANCELLED_TRANSACTION);
        LogAnalytics.logEvent(getApplicationContext(), AnalyticsConstant.PAYMENT_ABANDONED, hashMap2, AnalyticsConstant.CLEVERTAP);
        c();
        SdkSession.getInstance(getBaseContext()).verifyPaymentDetails(this.e, this);
    }

    private void a() {
        String stringExtra = getIntent().getStringExtra(PayUmoneyConstants.PAYMENT_ID);
        if (stringExtra != null) {
            SdkSession.getInstance(this).notifyUserCancelledTransaction(stringExtra, "1");
        }
    }

    private synchronized String a(Map<String, String> map) {
        String str;
        str = "";
        Iterator it = map.entrySet().iterator();
        boolean z = true;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (z) {
                StringBuilder sb = new StringBuilder();
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                str = str.concat(sb.toString());
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("&");
                sb2.append(entry.getKey());
                sb2.append("=");
                sb2.append(entry.getValue());
                str = str.concat(sb2.toString());
            }
            z = false;
            it.remove();
        }
        return str;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        c();
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        b();
        super.onDestroy();
    }

    private void a(String str, String str2, String str3) {
        Intent intent = new Intent("paymentstatus");
        intent.putExtra("eventname", str);
        intent.putExtra("payuresponse", str2);
        intent.putExtra("merchantresponse", str3);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /* JADX WARNING: Removed duplicated region for block: B:75:0x0199  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x019f  */
    public void onVerifyStatusResponseReceived(String response) {
        HashMap hashMap = new HashMap();
        hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        if (response == null) {
            a("onPaymentFailure", null, null);
            finish();
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(response);
            if (!jSONObject.has("status") || jSONObject.getInt("status") != 0) {
                if (jSONObject.has("status") && jSONObject.getInt("status") == -1) {
                    String optString = jSONObject.optString("result");
                    if (!TextUtils.isEmpty(optString)) {
                        hashMap.put(AnalyticsConstant.REASON, optString);
                    }
                    LogAnalytics.logEvent(getApplicationContext(), AnalyticsConstant.VERIFY_PAYMENT_API_ERROR, hashMap, AnalyticsConstant.CLEVERTAP);
                }
                if (!this.g.equalsIgnoreCase("onPaymentCancelled")) {
                    a("onPaymentCancelled", null, null);
                } else {
                    a("onPaymentFailure", null, null);
                }
                finish();
            }
            if (jSONObject.has("result")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("result");
                String optString2 = jSONObject2.optString("pg_TYPE");
                if (!TextUtils.isEmpty(optString2)) {
                    hashMap.put(AnalyticsConstant.PG_TYPE, optString2);
                }
                if (jSONObject2.has("status")) {
                    double d2 = 0.0d;
                    if (jSONObject2.getString("status").equalsIgnoreCase(PayUmoneyConstants.SUCCESS_STRING)) {
                        try {
                            if (jSONObject2.has("additionalCharges") && jSONObject2.getString("additionalCharges") != null) {
                                if (!jSONObject2.getString("additionalCharges").isEmpty()) {
                                    if (!jSONObject2.getString("additionalCharges").equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                                        d2 = Double.parseDouble(jSONObject2.getString("additionalCharges"));
                                    }
                                }
                            }
                            hashMap.put(AnalyticsConstant.AMOUNT, String.valueOf(Double.parseDouble(jSONObject2.getString(PayUmoneyConstants.AMOUNT)) + d2));
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                        LogAnalytics.logEvent(getApplicationContext(), AnalyticsConstant.PAYMENT_SUCCEEDED, hashMap, AnalyticsConstant.CLEVERTAP);
                        a("onPaymentSuccess", response, this.d);
                        finish();
                        return;
                    } else if (this.g.equalsIgnoreCase("onPaymentCancelled")) {
                        a("onPaymentCancelled", response, this.d);
                        finish();
                        return;
                    } else {
                        try {
                            if (jSONObject2.has("additionalCharges") && jSONObject2.getString("additionalCharges") != null) {
                                if (!jSONObject2.getString("additionalCharges").isEmpty()) {
                                    if (!jSONObject2.getString("additionalCharges").equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                                        d2 = Double.parseDouble(jSONObject2.getString("additionalCharges"));
                                    }
                                }
                            }
                            hashMap.put(AnalyticsConstant.AMOUNT, String.valueOf(Double.parseDouble(jSONObject2.getString(PayUmoneyConstants.AMOUNT)) + d2));
                        } catch (JSONException e3) {
                            e3.printStackTrace();
                        }
                        hashMap.put(AnalyticsConstant.REASON, jSONObject2.getString("field9"));
                        LogAnalytics.logEvent(getApplicationContext(), AnalyticsConstant.PAYMENT_FAILED, hashMap, AnalyticsConstant.CLEVERTAP);
                        a("onPaymentFailure", response, this.d);
                        finish();
                        return;
                    }
                }
            }
            if (!this.g.equalsIgnoreCase("onPaymentCancelled")) {
            }
            finish();
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }

    private void b() {
        if (!isFinishing()) {
            ProgressDialog progressDialog = this.h;
            if (progressDialog != null && progressDialog.isShowing()) {
                this.h.dismiss();
            }
        }
    }

    private void c() {
        if (!isFinishing()) {
            ProgressDialog progressDialog = this.h;
            if (progressDialog != null) {
                progressDialog.setMessage("Please Wait...");
                this.h.show();
            }
        }
    }
}
