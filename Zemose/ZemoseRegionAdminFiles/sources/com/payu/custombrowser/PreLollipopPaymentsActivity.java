package com.payu.custombrowser;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsIntent.Builder;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import com.bumptech.glide.load.Key;
import com.payu.custombrowser.bean.b;
import com.payu.custombrowser.util.CBConstant;
import com.payu.custombrowser.util.CBUtil;
import com.payu.custombrowser.util.e;
import com.payu.custombrowser.util.e.j;
import com.payu.custombrowser.util.e.k;
import com.payu.magicretry.Helpers.MRConstant;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.utils.AnalyticsConstant;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import org.json.JSONObject;

public class PreLollipopPaymentsActivity extends AppCompatActivity {
    String a = "com.android.chrome";
    boolean b;
    CustomTabsClient c;
    Builder d;
    CustomTabsServiceConnection e;
    com.payu.custombrowser.a.a f;
    /* access modifiers changed from: private */
    public String htmlData;
    /* access modifiers changed from: private */
    public boolean isCustomTabsLaunched = false;
    private String merchantKey;
    private String merchantResponse = null;
    private String payUResponse = null;
    /* access modifiers changed from: private */
    public JSONObject postData;
    /* access modifiers changed from: private */
    public String postDataValue;
    private String response = null;
    private a s;
    /* access modifiers changed from: private */
    public String s2sRetryUrl;
    private String txnId;
    /* access modifiers changed from: private */
    public String url;

    private class a extends e {
        a() {
            super(8080);
        }

        public k a(String str, j jVar, Map<String, String> map, Map<String, String> map2, Map<String, String> map3) {
            String str2;
            if (str.endsWith("/htmldata")) {
                str2 = PreLollipopPaymentsActivity.this.htmlData;
            } else if (TextUtils.isEmpty(PreLollipopPaymentsActivity.this.url) || PreLollipopPaymentsActivity.this.postData == null) {
                str2 = null;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("<html><head><link rel=\"icon\" type=\"image/png\" href=\"data:image/png;base64,iVBORw0KGgo=\"></head><body> <SCRIPT TYPE=\"text/JavaScript\">var f = document.createElement(\"form\");\nf.setAttribute('method',\"post\");\nf.setAttribute('name',\"dynamic\");\nf.setAttribute('action',\"");
                sb.append(PreLollipopPaymentsActivity.this.url);
                sb.append("\");\nvar json = ");
                sb.append(PreLollipopPaymentsActivity.this.postData.toString());
                sb.append(";var objVal = Object.keys(json);for(var count=0 ; count<objVal.length;count++){var i = document.createElement(\"input\");i.setAttribute('type',\"hidden\");i.name = objVal[count];i.value = json[objVal[count]];f.appendChild(i);}var button = document.createElement(\"input\");button.setAttribute('type',\"submit\");button.setAttribute('style',\"display: none;\");f.appendChild(button);document.getElementsByTagName('body')[0].appendChild(f);</SCRIPT><SCRIPT TYPE=\"text/JavaScript\">document.forms[\"dynamic\"].submit();</SCRIPT></body></html>");
                str2 = sb.toString();
            }
            return new k(str2);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.f = com.payu.custombrowser.a.a.a(getApplicationContext(), "local_cache_analytics");
        if (getIntent().getBundleExtra("data") != null) {
            this.url = getIntent().getBundleExtra("data").getString("url");
            this.txnId = getIntent().getBundleExtra("data").getString(CBConstant.TXNID);
            this.merchantKey = getIntent().getBundleExtra("data").getString("key");
            this.htmlData = getIntent().getBundleExtra("data").getString("html");
            this.postDataValue = getIntent().getBundleExtra("data").getString("postdata");
            this.s2sRetryUrl = getIntent().getBundleExtra("data").getString(CBConstant.S2S_RETRY_URL);
            this.s = new a();
            try {
                this.s.a();
            } catch (IOException e2) {
            }
            this.e = new CustomTabsServiceConnection() {
                public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                    PreLollipopPaymentsActivity preLollipopPaymentsActivity = PreLollipopPaymentsActivity.this;
                    preLollipopPaymentsActivity.c = client;
                    preLollipopPaymentsActivity.c.warmup(1);
                    PreLollipopPaymentsActivity.this.d = new Builder();
                    PreLollipopPaymentsActivity.this.d.enableUrlBarHiding();
                    PreLollipopPaymentsActivity.this.d.setShowTitle(false);
                    CustomTabsIntent build = PreLollipopPaymentsActivity.this.d.build();
                    if (PreLollipopPaymentsActivity.this.a != null) {
                        build.intent.setPackage(PreLollipopPaymentsActivity.this.a);
                    }
                    if (!TextUtils.isEmpty(PreLollipopPaymentsActivity.this.htmlData)) {
                        PreLollipopPaymentsActivity.this.a("cb_status", "custom_tabs_load_html");
                        build.launchUrl(PreLollipopPaymentsActivity.this, Uri.parse("http://127.0.0.1:8080/htmldata"));
                        PreLollipopPaymentsActivity.this.isCustomTabsLaunched = true;
                    } else if (!TextUtils.isEmpty(PreLollipopPaymentsActivity.this.s2sRetryUrl)) {
                        PreLollipopPaymentsActivity.this.a("cb_status", "custom_tabs_load_html");
                        PreLollipopPaymentsActivity preLollipopPaymentsActivity2 = PreLollipopPaymentsActivity.this;
                        build.launchUrl(preLollipopPaymentsActivity2, Uri.parse(preLollipopPaymentsActivity2.s2sRetryUrl));
                        PreLollipopPaymentsActivity.this.isCustomTabsLaunched = true;
                    } else if (TextUtils.isEmpty(PreLollipopPaymentsActivity.this.url) || TextUtils.isEmpty(PreLollipopPaymentsActivity.this.postDataValue)) {
                        PreLollipopPaymentsActivity.this.isCustomTabsLaunched = false;
                        if (b.SINGLETON.getPayuCustomBrowserCallback() != null) {
                            b.SINGLETON.getPayuCustomBrowserCallback().onCBErrorReceived(104, CBConstant.POST_DATA_OR_HTML_DATA_NOT_PRESENT);
                        }
                        PreLollipopPaymentsActivity.this.a();
                    } else {
                        CBUtil cBUtil = new CBUtil();
                        PreLollipopPaymentsActivity preLollipopPaymentsActivity3 = PreLollipopPaymentsActivity.this;
                        preLollipopPaymentsActivity3.postData = new JSONObject(cBUtil.getDataFromPostData(preLollipopPaymentsActivity3.postDataValue));
                        build.launchUrl(PreLollipopPaymentsActivity.this, Uri.parse("http://127.0.0.1:8080"));
                        PreLollipopPaymentsActivity.this.isCustomTabsLaunched = true;
                    }
                }

                public void onServiceDisconnected(ComponentName name) {
                }
            };
            this.b = CustomTabsClient.bindCustomTabsService(this, this.a, this.e);
            return;
        }
        if (getIntent() != null && getIntent().getData() != null && !TextUtils.isEmpty(getIntent().getData().getQuery())) {
            String scheme = getIntent().getData().getScheme();
            this.response = getIntent().getData().getQuery();
            String[] split = this.response.split("[$][|]");
            this.merchantResponse = a(split[0]);
            if (split.length > 1) {
                this.payUResponse = a(split[1]);
            }
            if (scheme.contains(PayUmoneyConstants.SUCCESS_STRING)) {
                a("trxn_status", "success_transaction_custom_tabs");
                if (b.SINGLETON.getPayuCustomBrowserCallback() != null) {
                    b.SINGLETON.getPayuCustomBrowserCallback().onPaymentSuccess(this.payUResponse, this.merchantResponse);
                }
            } else {
                a("trxn_status", "failure_transaction_custom_tabs");
                if (b.SINGLETON.getPayuCustomBrowserCallback() != null) {
                    b.SINGLETON.getPayuCustomBrowserCallback().onPaymentFailure(this.payUResponse, this.merchantResponse);
                }
            }
        } else if (b.SINGLETON.getPayuCustomBrowserCallback() != null) {
            b.SINGLETON.getPayuCustomBrowserCallback().onCBErrorReceived(102, CBConstant.RESPONSE_NOT_PRESENT);
        }
        a();
    }

    private String a(String str) {
        String str2 = "{\"";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '=') {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append("\":\"");
                str2 = sb.toString();
            } else if (str.charAt(i) == '&') {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append("\",\"");
                str2 = sb2.toString();
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                sb3.append(str.charAt(i));
                str2 = sb3.toString();
            }
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str2);
        sb4.append("\"}");
        return sb4.toString();
    }

    public void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        a aVar = this.s;
        if (aVar != null) {
            aVar.b();
        }
        CustomTabsServiceConnection customTabsServiceConnection = this.e;
        if (customTabsServiceConnection != null) {
            unbindService(customTabsServiceConnection);
        }
        this.e = null;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.isCustomTabsLaunched) {
            a("user_input", "custom_tabs_cancelled");
            if (b.SINGLETON.getPayuCustomBrowserCallback() != null) {
                b.SINGLETON.getPayuCustomBrowserCallback().onBackApprove();
            }
            this.isCustomTabsLaunched = false;
            a();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(String str, String str2) {
        if (str2 != null) {
            try {
                if (!str2.trim().equalsIgnoreCase("")) {
                    this.f.a(a(getApplicationContext(), str, str2.toLowerCase(), null, Bank.keyAnalytics, Bank.a, ""));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public String a(Context context, String str, String str2, String str3, String str4, String str5, String str6) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("txnid", this.txnId);
            jSONObject.put(MRConstant.MERCHANT_KEY, this.merchantKey);
            jSONObject.put(MRConstant.PAGE_TYPE, str6);
            jSONObject.put("event_key", str);
            jSONObject.put("event_value", URLEncoder.encode(str2, Key.STRING_CHARSET_NAME));
            String str7 = "bank";
            if (str3 == null) {
                str3 = "";
            }
            jSONObject.put(str7, str3);
            jSONObject.put("package_name", context.getPackageName());
            jSONObject.put(AnalyticsConstant.TS, CBUtil.getSystemCurrentTime());
            return jSONObject.toString();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "{}";
        }
    }

    /* access modifiers changed from: private */
    public void a() {
        Intent intent = new Intent(this, PrePaymentsActivity.class);
        intent.addFlags(67108864);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
