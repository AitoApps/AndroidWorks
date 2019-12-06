package com.payu.custombrowser;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog.Builder;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.load.Key;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.payu.custombrowser.bean.CustomBrowserConfig;
import com.payu.custombrowser.bean.b;
import com.payu.custombrowser.services.SnoozeService;
import com.payu.custombrowser.util.CBConstant;
import com.payu.custombrowser.util.CBUtil;
import com.payu.custombrowser.util.c;
import com.payu.custombrowser.util.d;
import com.payu.custombrowser.util.g;
import com.payu.custombrowser.widgets.SnoozeLoaderView;
import com.payu.magicretry.MagicRetryFragment;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.utils.AnalyticsConstant;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public class Bank extends b {
    public static String Version;
    static String a;
    private static List<String> aw = new ArrayList();
    static String b;
    static String c;
    public static String keyAnalytics;
    /* access modifiers changed from: private */
    public SnoozeLoaderView aA;
    private View aB;
    private boolean aC;
    /* access modifiers changed from: private */
    public boolean aD;
    /* access modifiers changed from: private */
    public a aE;
    /* access modifiers changed from: private */
    public boolean aF;
    private boolean aG;
    private boolean aH;
    /* access modifiers changed from: private */
    public boolean aI;
    private CountDownTimer aJ;
    private CountDownTimer aK;
    /* access modifiers changed from: private */
    public boolean aL;
    private boolean aM;
    /* access modifiers changed from: private */
    public boolean aN;
    private boolean aO;
    private boolean aP;
    private boolean aQ;
    private AlertDialog aR;
    /* access modifiers changed from: private */
    public boolean aS;
    /* access modifiers changed from: private */
    public boolean aT;
    /* access modifiers changed from: private */
    public boolean aU;
    /* access modifiers changed from: private */
    public String aV;
    private SmsRetrieverClient aW;
    private CountDownTimer ax;
    /* access modifiers changed from: private */
    public boolean ay;
    /* access modifiers changed from: private */
    public boolean az;
    Runnable d;
    public long snoozeClickedTime;

    public class a implements OnClickListener {
        private View b;

        public a() {
        }

        public void a(View view) {
            this.b = view;
        }

        public void onClick(View v) {
            String str = "";
            if (v instanceof Button) {
                str = ((Button) v).getText().toString();
            } else if (v instanceof TextView) {
                str = ((TextView) v).getText().toString();
            }
            switch (Bank.this.b(str.toLowerCase())) {
                case 1:
                case 3:
                    Bank bank = Bank.this;
                    bank.ae = true;
                    bank.aj = Boolean.valueOf(true);
                    Bank.this.f();
                    Bank bank2 = Bank.this;
                    bank2.z = 1;
                    bank2.onHelpUnavailable();
                    if (Bank.this.L != null) {
                        Bank.this.L.setVisibility(8);
                    }
                    if (Bank.this.M != null) {
                        Bank.this.M.setVisibility(8);
                    }
                    try {
                        WebView webView = Bank.this.s;
                        StringBuilder sb = new StringBuilder();
                        sb.append("javascript:");
                        sb.append(Bank.this.i.getString(Bank.this.getString(R.string.cb_pin)));
                        webView.loadUrl(sb.toString());
                        Bank.this.m = "password_click";
                        Bank.this.a("user_input", Bank.this.m);
                        return;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }
                case 2:
                    try {
                        Bank.this.m = "regenerate_click";
                        Bank.this.a("user_input", Bank.this.m);
                        Bank.this.ah = null;
                        WebView webView2 = Bank.this.s;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("javascript:");
                        sb2.append(Bank.this.i.getString(Bank.this.getString(R.string.cb_regen_otp)));
                        webView2.loadUrl(sb2.toString());
                        Bank.this.m();
                        return;
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                        return;
                    }
                case 4:
                    final View inflate = Bank.this.f.getLayoutInflater().inflate(R.layout.wait_for_otp, null);
                    if (v instanceof Button) {
                        Bank.this.m = "enter_manually_click";
                    } else {
                        Bank.this.m = "enter_manually_ontimer_click";
                    }
                    Bank bank3 = Bank.this;
                    bank3.a("user_input", bank3.m);
                    if (Bank.this.af == 0) {
                        inflate.measure(-2, -2);
                        Bank.this.af = inflate.getMeasuredHeight();
                    }
                    Bank.this.K.removeAllViews();
                    Bank.this.K.addView(inflate);
                    if (Bank.this.K.isShown()) {
                        Bank.this.z = 2;
                    } else {
                        Bank.this.f();
                    }
                    ImageView imageView = (ImageView) inflate.findViewById(R.id.bank_logo);
                    imageView.setOnClickListener(Bank.this.viewOnClickListener);
                    if (Bank.this.r != null) {
                        imageView.setImageDrawable(Bank.this.r);
                    }
                    inflate.findViewById(R.id.waiting).setVisibility(8);
                    final Button button = (Button) inflate.findViewById(R.id.approve);
                    button.setClickable(false);
                    EditText editText = (EditText) inflate.findViewById(R.id.otp_sms);
                    if (Bank.this.aS) {
                        editText.setInputType(2);
                    } else {
                        editText.setInputType(1);
                    }
                    Bank.this.a((View) editText);
                    CBUtil.setAlpha(0.3f, button);
                    button.setVisibility(0);
                    editText.setVisibility(0);
                    inflate.findViewById(R.id.regenerate_layout).setVisibility(8);
                    inflate.findViewById(R.id.progress).setVisibility(4);
                    Bank.this.a((View) editText);
                    editText.addTextChangedListener(new TextWatcher() {
                        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        }

                        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                            if (((EditText) inflate.findViewById(R.id.otp_sms)).getText().toString().length() > 5) {
                                Bank.this.aE.a(inflate);
                                button.setOnClickListener(Bank.this.aE);
                                button.setClickable(true);
                                CBUtil.setAlpha(1.0f, button);
                                return;
                            }
                            button.setClickable(false);
                            CBUtil.setAlpha(0.3f, button);
                            button.setOnClickListener(null);
                        }

                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    Bank.this.updateHeight(inflate);
                    Bank.this.addReviewOrder(inflate);
                    return;
                case 5:
                    try {
                        Bank.this.d();
                        Bank.this.ah = null;
                        Bank.this.ak = false;
                        Bank.this.aj = Boolean.valueOf(true);
                        Bank.this.onHelpUnavailable();
                        Bank.this.f();
                        Bank.this.z = 1;
                        Bank.this.m();
                        if (((EditText) this.b.findViewById(R.id.otp_sms)).getText().toString().length() > 5) {
                            Bank.this.m = "approved_otp";
                            Bank.this.a("user_input", Bank.this.m);
                            Bank.this.a("Approve_btn_clicked_time", "-1");
                            WebView webView3 = Bank.this.s;
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("javascript:");
                            sb3.append(Bank.this.i.getString(Bank.this.getString(R.string.cb_process_otp)));
                            sb3.append("(\"");
                            sb3.append(((TextView) this.b.findViewById(R.id.otp_sms)).getText().toString());
                            sb3.append("\")");
                            webView3.loadUrl(sb3.toString());
                            ((EditText) this.b.findViewById(R.id.otp_sms)).setText("");
                            return;
                        }
                        return;
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                        return;
                    }
                case 6:
                case 7:
                    Bank bank4 = Bank.this;
                    bank4.aq = true;
                    bank4.p();
                    Bank bank5 = Bank.this;
                    bank5.m = "otp_click";
                    bank5.a("user_input", bank5.m);
                    if (VERSION.SDK_INT < 23) {
                        Bank bank6 = Bank.this;
                        bank6.ah = null;
                        bank6.m();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    static {
        Version = "7.3.0";
        Version = "7.3.0";
    }

    public Bank() {
        this.ax = null;
        this.ay = false;
        this.az = false;
        this.aC = true;
        this.aD = false;
        this.aF = true;
        this.aH = false;
        this.aL = true;
        this.aN = false;
        this.aO = false;
        this.aP = false;
        this.aQ = false;
        this.aS = true;
        this.aT = false;
        this.aE = new a();
        this.aI = false;
        this.ar = new com.payu.custombrowser.custombar.a();
        this.U = new HashSet();
        this.N = new CBUtil();
        this.W = Executors.newCachedThreadPool();
        this.V = new HashSet();
    }

    public void onPause() {
        super.onPause();
        this.aU = true;
    }

    public void onStart() {
        super.onStart();
        this.aU = false;
        if (this.aV != null) {
            Toast.makeText(this.f, this.aV, 0).show();
            this.aV = null;
        }
        if (ContextCompat.checkSelfPermission(this.f, "android.permission.RECEIVE_SMS") != 0) {
            o();
        }
    }

    private void o() {
        this.aW = SmsRetriever.getClient(this.f);
        Task startSmsRetriever = this.aW.startSmsRetriever();
        startSmsRetriever.addOnSuccessListener(new OnSuccessListener<Void>() {
            /* renamed from: a */
            public void onSuccess(Void voidR) {
                c.b("Bank", "SmsRetrieverClient started successfully");
            }
        });
        startSmsRetriever.addOnFailureListener(new OnFailureListener() {
            public void onFailure(@NonNull Exception e) {
                c.b("Bank", "SmsRetrieverClient failed to start");
                e.printStackTrace();
            }
        });
    }

    public static boolean isUrlWhiteListed(String currentUrl) {
        if ((currentUrl.contains("https://secure.payu.in") || currentUrl.contains(CBConstant.PAYU_DOMAIN_TEST)) && currentUrl.contains(CBConstant.RESPONSE_BACKWARD)) {
            return true;
        }
        for (String str : aw) {
            if (currentUrl != null && currentUrl.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public SnoozeLoaderView getSnoozeLoaderView() {
        return this.aA;
    }

    public void setSnoozeLoaderView(SnoozeLoaderView snoozeLoaderView) {
        this.aA = snoozeLoaderView;
    }

    public String getPageType() {
        return this.pageType;
    }

    public void reloadWebView(String url, String postParams) {
        this.forwardJourneyForChromeLoaderIsComplete = false;
        this.backwardJourneyStarted = false;
        this.isWebviewReloading = true;
        registerSMSBroadcast();
        this.backwardJourneyStarted = false;
        if (this.snoozeService != null) {
            this.snoozeService.a();
        }
        if (this.n) {
            dismissSnoozeWindow();
        }
        if (this.w != null) {
            this.w.dismiss();
        }
        this.w = null;
        if (VERSION.SDK_INT == 16 || VERSION.SDK_INT == 17 || VERSION.SDK_INT == 18) {
            this.s.loadUrl("about:blank");
        }
        a(true);
        resetAutoSelectOTP();
        this.N.resetPayuID();
        this.surePayS2SPayUId = null;
        if (url != null && postParams != null) {
            this.s.postUrl(url, postParams.getBytes());
        } else if (url != null) {
            this.s.loadUrl(url);
        }
    }

    public void killSnoozeService() {
        if (this.snoozeService != null) {
            this.snoozeService.a();
        }
    }

    public void reloadWebView() {
        if (this.snoozeService != null) {
            this.snoozeService.a();
        }
        if (this.n) {
            dismissSnoozeWindow();
        }
        registerSMSBroadcast();
        this.isWebviewReloading = true;
        if (this.isSnoozeEnabled) {
            s();
        }
        if (this.s.getUrl() != null) {
            a(true);
            if (19 == VERSION.SDK_INT) {
                this.s.reload();
            } else {
                reloadWVNative();
            }
        }
    }

    public void reloadWebView(String resumeUrl) {
        if (this.snoozeService != null) {
            this.snoozeService.a();
        }
        if (this.n) {
            dismissSnoozeWindow();
        }
        registerSMSBroadcast();
        this.isWebviewReloading = true;
        if (this.isSnoozeEnabled) {
            s();
        }
        if (this.s.getUrl() != null) {
            a(true);
            if (19 == VERSION.SDK_INT) {
                this.s.reload();
            } else {
                reloadWVUsingJS();
            }
        } else {
            reloadWebView(this.customBrowserConfig.getPostURL(), this.customBrowserConfig.getPayuPostData());
        }
    }

    public String getBankName() {
        if (this.D == null) {
            return "";
        }
        return this.D;
    }

    /* access modifiers changed from: private */
    public void p() {
        if (this.an || VERSION.SDK_INT < 23 || !this.C) {
            onHelpAvailable();
            if (this.aq) {
                try {
                    WebView webView = this.s;
                    StringBuilder sb = new StringBuilder();
                    sb.append("javascript:");
                    sb.append(this.i.getString(getString(R.string.cb_otp)));
                    webView.loadUrl(sb.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } else {
            this.an = true;
            if (ContextCompat.checkSelfPermission(this.f, "android.permission.RECEIVE_SMS") != 0) {
                requestPermissions(new String[]{"android.permission.RECEIVE_SMS"}, 1);
                this.ao = true;
                return;
            }
            this.am = true;
            if (this.aq) {
                try {
                    WebView webView2 = this.s;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("javascript:");
                    sb2.append(this.i.getString(getString(R.string.cb_otp)));
                    webView2.loadUrl(sb2.toString());
                } catch (JSONException e3) {
                    e3.printStackTrace();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
        }
    }

    @JavascriptInterface
    public void showCustomBrowser(final boolean showCustomBrowser) {
        this.ag = showCustomBrowser;
        if (getActivity() != null && !getActivity().isFinishing()) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (!showCustomBrowser) {
                        Bank.this.f();
                        Bank bank = Bank.this;
                        bank.z = 1;
                        try {
                            if (bank.L != null) {
                                Bank.this.L.setVisibility(8);
                            }
                            Bank.this.onHelpUnavailable();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    @JavascriptInterface
    public void setMRData(String data2) {
        if (!this.aH) {
            MagicRetryFragment.setMRData(data2, getActivity().getApplicationContext());
            a(CBUtil.updateRetryData(data2, getActivity().getApplicationContext()));
            this.aH = true;
        }
    }

    public void onOverrideURL(String url) {
        if (this.y != null) {
            this.y.setProgress(10);
        }
    }

    private void q() {
        setIsPageStoppedForcefully(true);
        if (this.at != null) {
            w();
            this.av = this.N.getSurePayDisableStatus(this.at, this.B);
            launchSnoozeWindow(2);
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=android.net.http.SslError, code=java.lang.Object, for r5v0, types: [android.net.http.SslError, java.lang.Object] */
    public void onReceivedSslError(WebView view, SslErrorHandler handler, Object error) {
        StringBuilder sb = new StringBuilder();
        sb.append(error == null ? "" : error);
        sb.append("|");
        sb.append(view.getUrl() == null ? "" : view.getUrl());
        b("SSL_ERROR", sb.toString());
        h();
    }

    public void onReceivedErrorWebClient(int errorCode, String description) {
        StringBuilder sb = new StringBuilder();
        sb.append(errorCode);
        sb.append("|");
        sb.append(description == null ? "" : description);
        sb.append("|");
        sb.append(this.s.getUrl() == null ? "" : this.s.getUrl());
        b("ERROR_RECEIVED", sb.toString());
        h();
        if (this.y != null) {
            this.y.setVisibility(8);
        }
        this.aO = true;
        try {
            if (getActivity() != null && !getActivity().isFinishing() && b.SINGLETON != null && b.SINGLETON.getPayuCustomBrowserCallback() != null) {
                if (!this.backwardJourneyStarted) {
                    q();
                } else if (this.backwardJourneyStarted && this.isTxnNBType && this.snoozeCountBackwardJourney < this.customBrowserConfig.getEnableSurePay()) {
                    dismissSnoozeWindow();
                    q();
                }
                onHelpUnavailable();
                this.K.removeAllViews();
                if (this.v != 0) {
                    f();
                    this.z = 1;
                }
                i();
                if (!this.J) {
                    b.SINGLETON.getPayuCustomBrowserCallback().onCBErrorReceived(errorCode, description);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void r() {
        try {
            WebView webView = this.s;
            StringBuilder sb = new StringBuilder();
            sb.append("javascript:");
            sb.append(this.h.getString("getMagicRetryUrls"));
            sb.append("('");
            sb.append(keyAnalytics);
            sb.append("')");
            webView.loadUrl(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onProgressChanged(int progress) {
        if (this.f != null && !this.f.isFinishing() && !isRemoving() && isAdded() && this.y != null) {
            this.y.setVisibility(0);
            if (progress != 100) {
                a(progress);
            } else if (this.y != null) {
                this.y.setProgress(100);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (Bank.this.f != null && !Bank.this.f.isFinishing() && !Bank.this.isRemoving() && Bank.this.isAdded()) {
                            Bank.this.y.setVisibility(8);
                            Bank.this.x = 0;
                        }
                    }
                }, 100);
            }
        }
    }

    @JavascriptInterface
    public void onMerchantHashReceived(final String result) {
        if (getActivity() != null && !getActivity().isFinishing() && !isRemoving() && isAdded()) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    switch (Bank.this.H) {
                        case 2:
                            try {
                                JSONObject jSONObject = new JSONObject(result);
                                Bank.this.N.storeInSharedPreferences(Bank.this.getActivity().getApplicationContext(), jSONObject.getString("card_token"), jSONObject.getString("merchant_hash"));
                                return;
                            } catch (JSONException e) {
                                e.printStackTrace();
                                return;
                            }
                        default:
                            return;
                    }
                }
            });
        }
    }

    private void s() {
        if (b.SINGLETON != null && b.SINGLETON.getPayuCustomBrowserCallback() != null && this.customBrowserConfig != null && this.N.getBooleanSharedPreferenceDefaultTrue(CBConstant.SNOOZE_ENABLED, this.f.getApplicationContext()) && this.customBrowserConfig.getEnableSurePay() > this.snoozeCount) {
            if (this.ay) {
                w();
            }
            v();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:116:0x024a A[Catch:{ UnsupportedEncodingException -> 0x029a }] */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x025c A[Catch:{ UnsupportedEncodingException -> 0x029a }] */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x0262 A[Catch:{ UnsupportedEncodingException -> 0x029a }] */
    public void onPageStartedWebclient(String url) {
        this.aN = true;
        this.az = false;
        if ((VERSION.SDK_INT == 16 || VERSION.SDK_INT == 17 || VERSION.SDK_INT == 18) && this.aO) {
            t();
        }
        this.aO = false;
        dismissSlowUserWarning();
        if (!this.T && url != null && (url.equalsIgnoreCase("https://secure.payu.in/_payment") || url.equalsIgnoreCase(CBConstant.PRODUCTION_PAYMENT_URL_SEAMLESS))) {
            this.T = true;
        }
        if (!this.aM) {
            if (this.customBrowserConfig != null && this.customBrowserConfig.getPayuPostData() == null && this.customBrowserConfig.getPostURL() == null && this.customBrowserConfig.getHtmlData() == null && this.customBrowserConfig.getSurepayS2Surl() == null) {
                if (b.SINGLETON.getPayuCustomBrowserCallback().getPostData() == null || b.SINGLETON.getPayuCustomBrowserCallback().getPostURL() == null) {
                    throw new d("POST Data or POST URL Missing or wrong POST URL or HTML Data missing");
                }
                this.customBrowserConfig.setPayuPostData(b.SINGLETON.getPayuCustomBrowserCallback().getPostData());
                this.customBrowserConfig.setPostURL(b.SINGLETON.getPayuCustomBrowserCallback().getPostURL());
                b.SINGLETON.getPayuCustomBrowserCallback().setPostURL(null);
                b.SINGLETON.getPayuCustomBrowserCallback().setPostData(null);
            }
            if (!(this.customBrowserConfig == null || this.customBrowserConfig.getPayuPostData() == null || this.isS2SHtmlSupport)) {
                this.isTxnNBType = checkIfTransactionNBType(this.customBrowserConfig.getPayuPostData());
            }
            this.aM = true;
        }
        this.aL = true;
        if (this.pageType != null && !this.pageType.equalsIgnoreCase("")) {
            a("departure", "-1");
            this.pageType = "";
        }
        if (this.f != null && !this.f.isFinishing() && !isRemoving() && isAdded()) {
            StringBuilder sb = new StringBuilder();
            sb.append("s:");
            sb.append(url);
            this.N.setStringSharedPreferenceLastURL(this.f.getApplicationContext(), "last_url", sb.toString());
            if (this.y != null) {
                this.y.setVisibility(0);
            }
            CountDownTimer countDownTimer = this.aJ;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            if (this.y != null) {
                this.y.setVisibility(0);
                this.y.setProgress(10);
            }
            this.backwardJourneyStarted = isInBackWardJourney(url);
            if (!this.forwardJourneyForChromeLoaderIsComplete || this.backwardJourneyStarted) {
                a(0, url);
            }
            this.B = (this.s.getUrl() == null || this.s.getUrl().equalsIgnoreCase("")) ? url : this.s.getUrl();
            if (b.SINGLETON != null && b.SINGLETON.getPayuCustomBrowserCallback() != null) {
                if (this.backwardJourneyStarted) {
                    if (this.isTxnNBType) {
                        this.n = false;
                    } else {
                        dismissSnoozeWindow();
                    }
                }
                if (url.contains(CBConstant.PAYMENT_OPTION_URL_PROD)) {
                    this.i = null;
                    this.r = null;
                }
                try {
                    if (this.customBrowserConfig != null) {
                        if (this.customBrowserConfig.getPayuPostData() != null) {
                            if (!this.N.getDataFromPostData(this.customBrowserConfig.getPayuPostData(), "surl").equals("")) {
                                if (url.contains(URLDecoder.decode(this.N.getDataFromPostData(this.customBrowserConfig.getPayuPostData(), "surl"), Key.STRING_CHARSET_NAME))) {
                                    this.aL = false;
                                    dismissSnoozeWindow();
                                    i();
                                    if (isRetryURL(url)) {
                                        resetAutoSelectOTP();
                                        this.backupOfOTP = null;
                                        this.ah = null;
                                        this.backwardJourneyStarted = false;
                                    }
                                    w();
                                    if (this.snoozeService == null) {
                                        this.snoozeService.a();
                                        return;
                                    }
                                    return;
                                }
                            }
                            if (!this.N.getDataFromPostData(this.customBrowserConfig.getPayuPostData(), "furl").equals("")) {
                                if (url.contains(URLDecoder.decode(this.N.getDataFromPostData(this.customBrowserConfig.getPayuPostData(), "furl"), Key.STRING_CHARSET_NAME))) {
                                    this.aL = false;
                                    dismissSnoozeWindow();
                                    i();
                                    if (isRetryURL(url)) {
                                    }
                                    w();
                                    if (this.snoozeService == null) {
                                    }
                                }
                            }
                            if (!isRetryURL(url)) {
                            }
                            this.aL = false;
                            dismissSnoozeWindow();
                            i();
                            if (isRetryURL(url)) {
                            }
                            w();
                            if (this.snoozeService == null) {
                            }
                        }
                        if (!this.isS2SHtmlSupport || !isRetryURL(url)) {
                            if (this.isSnoozeEnabled && this.customBrowserConfig.getSurePayMode() == 1 && !this.backwardJourneyStarted) {
                                this.as = this.at.getPercentageAndTimeout(url);
                                this.snoozeUrlLoadingPercentage = this.as[0];
                                this.snoozeUrlLoadingTimeout = this.as[1];
                                this.av = this.N.getSurePayDisableStatus(this.at, url);
                                s();
                                return;
                            }
                            return;
                        }
                        this.aL = false;
                        dismissSnoozeWindow();
                        i();
                        if (isRetryURL(url)) {
                        }
                        w();
                        if (this.snoozeService == null) {
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void t() {
        setIsPageStoppedForcefully(true);
        if (this.at != null && !this.J) {
            w();
            this.av = this.N.getSurePayDisableStatus(this.at, this.B);
            launchSnoozeWindow(2);
        }
    }

    public boolean isInBackWardJourney(String url) {
        try {
            if (!this.backwardJourneyStarted) {
                if ((url.startsWith("https://secure.payu.in") || url.startsWith(CBConstant.PAYU_DOMAIN_TEST)) && url.contains(CBConstant.RESPONSE_BACKWARD)) {
                    return true;
                }
                if (this.U != null) {
                    for (String contains : this.U) {
                        if (url.contains(contains)) {
                            return true;
                        }
                    }
                    return false;
                }
            }
            return this.backwardJourneyStarted;
        } catch (Exception e) {
            e.printStackTrace();
            return this.backwardJourneyStarted;
        }
    }

    public void onLoadResourse(WebView view, String url) {
        if (this.f != null && !this.f.isFinishing() && !isRemoving() && isAdded() && !url.equalsIgnoreCase(CBConstant.rupeeURL) && !url.contains(CBConstant.rupeeURL1)) {
            url.contains(CBConstant.rupeeURL2);
        }
    }

    @JavascriptInterface
    public void showReviewOrder(boolean isShowReviewOrder) {
        if (!this.J) {
            int enableReviewOrder = this.customBrowserConfig.getEnableReviewOrder();
            CustomBrowserConfig customBrowserConfig = this.customBrowserConfig;
            if (enableReviewOrder == 0 && !isShowReviewOrder) {
                CustomBrowserConfig customBrowserConfig2 = this.customBrowserConfig;
                CustomBrowserConfig customBrowserConfig3 = this.customBrowserConfig;
                customBrowserConfig2.setEnableReviewOrder(-1);
                this.Z.setVisibility(8);
                dismissReviewOrder();
            }
        }
    }

    @JavascriptInterface
    public void surePayData(String jsonData) {
        try {
            JSONObject jSONObject = new JSONObject(jsonData);
            if (jSONObject.has(CBConstant.S2SPAYUID)) {
                this.surePayS2SPayUId = jSONObject.getString(CBConstant.S2SPAYUID);
            }
            if (jSONObject.has(CBConstant.S2SREPLAYURL) && jSONObject.has(CBConstant.SNOOZE_COUNT) && jSONObject.has(CBConstant.TXN_TYPE) && jSONObject.has("merchantKey") && jSONObject.has(CBConstant.TXNID)) {
                this.surePayS2Surl = jSONObject.getString(CBConstant.S2SREPLAYURL);
                this.merchantKey = jSONObject.getString("merchantKey");
                this.txnId = jSONObject.getString(CBConstant.TXNID);
                this.txnType = jSONObject.getString(CBConstant.TXN_TYPE);
                this.isTxnNBType = this.txnType.equalsIgnoreCase(PayUmoneyConstants.PAYMENT_MODE_NB);
                this.customBrowserConfig.setEnableSurePay(Integer.parseInt(jSONObject.getString(CBConstant.SNOOZE_COUNT)));
                this.isSurePayValueLoaded = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void dismissReviewOrder() {
        if (this.f != null && !this.f.isFinishing()) {
            this.f.runOnUiThread(new Runnable() {
                public void run() {
                    if (!Bank.this.J) {
                        Bank.this.hideReviewOrderHorizontalBar();
                        Bank.this.hideReviewOrderDetails();
                    }
                }
            });
        }
    }

    public void onPageFinishWebclient(String url) {
        this.aN = false;
        if (this.f != null && !this.f.isFinishing() && !isRemoving() && isAdded()) {
            if (this.aQ) {
                a("snooze_resume_url", url);
                a(false);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("f:");
            sb.append(url);
            this.N.setStringSharedPreferenceLastURL(this.f.getApplicationContext(), "last_url", sb.toString());
            u();
            if (!(!this.aC || getArguments() == null || getArguments().getInt(CBConstant.MAIN_LAYOUT, -1) == -1)) {
                try {
                    final View findViewById = this.f.findViewById(getArguments().getInt(CBConstant.MAIN_LAYOUT));
                    findViewById.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                        private final int c = 100;
                        private final int d;
                        private final Rect e;

                        {
                            this.d = (VERSION.SDK_INT >= 21 ? 48 : 0) + 100;
                            this.e = new Rect();
                        }

                        public void onGlobalLayout() {
                            if (Bank.this.f != null && !Bank.this.f.isFinishing() && !Bank.this.isRemoving() && Bank.this.isAdded()) {
                                int applyDimension = (int) TypedValue.applyDimension(1, (float) this.d, findViewById.getResources().getDisplayMetrics());
                                findViewById.getWindowVisibleDisplayFrame(this.e);
                                if ((findViewById.getRootView().getHeight() - (this.e.bottom - this.e.top) >= applyDimension) && Bank.this.j == 0) {
                                    ((InputMethodManager) Bank.this.f.getSystemService("input_method")).toggleSoftInput(3, 0);
                                    Bank.this.j = 1;
                                }
                            }
                        }
                    });
                    this.aC = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (!this.az) {
            w();
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (!Bank.this.az && !Bank.this.aN && Bank.this.n && !Bank.this.backwardJourneyStarted) {
                    if (Bank.this.n) {
                        Bank.this.a("snooze_window_automatically_disappear_time", "-1");
                    }
                    Bank.this.dismissSnoozeWindow();
                }
            }
        }, 1000);
    }

    @JavascriptInterface
    public void setSnoozeEnabled(boolean snoozeEnabled) {
        if (!snoozeEnabled) {
            this.customBrowserConfig.setEnableSurePay(0);
        }
        this.N.setBooleanSharedPreference(CBConstant.SNOOZE_ENABLED, snoozeEnabled, this.f.getApplicationContext());
    }

    private void u() {
        CountDownTimer countDownTimer = this.aJ;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        CountDownTimer countDownTimer2 = this.aK;
        if (countDownTimer2 != null) {
            countDownTimer2.cancel();
        }
        AnonymousClass37 r1 = new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                Bank.this.dismissPayULoader();
                Bank.this.showReviewOrderHorizontalBar();
            }
        };
        this.aJ = r1.start();
    }

    @JavascriptInterface
    public void getUserId() {
        if (this.f != null && !this.f.isFinishing() && !isRemoving() && isAdded()) {
            this.f.runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        if (Bank.this.N.getStringSharedPreference(Bank.this.f.getApplicationContext(), Bank.this.D) != null && !Bank.this.N.getStringSharedPreference(Bank.this.f.getApplicationContext(), Bank.this.D).equals("")) {
                            WebView webView = Bank.this.s;
                            StringBuilder sb = new StringBuilder();
                            sb.append("javascript:");
                            sb.append(Bank.this.i.getString(Bank.this.getString(R.string.cb_populate_user_id)));
                            sb.append("(\"");
                            sb.append(Bank.this.N.getStringSharedPreference(Bank.this.f.getApplicationContext(), Bank.this.D));
                            sb.append("\")");
                            webView.loadUrl(sb.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @JavascriptInterface
    public void setUserId(String params) {
        if (this.aF) {
            if (this.f != null && !this.f.isFinishing()) {
                this.N.storeInSharedPreferences(this.f.getApplicationContext(), this.D, params);
            }
        } else if (!this.N.getStringSharedPreference(this.f.getApplicationContext(), this.D).equals("")) {
            this.N.removeFromSharedPreferences(this.f.getApplicationContext(), this.D);
        }
    }

    @JavascriptInterface
    public void nativeHelperForNB(final String fields, final String params) {
        if (this.f != null && !this.f.isFinishing() && !isRemoving() && isAdded()) {
            this.f.runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        Bank.this.dismissReviewOrder();
                        if (Bank.this.n) {
                            Bank.this.dismissSnoozeWindow();
                            Bank.this.a("snooze_window_action", "snooze_window_dismissed_by_cb");
                            Bank.this.a("snooze_window_automatically_disappear_time", "-1");
                        }
                        Bank.this.pageType = "NBLogin Page";
                        Bank.this.a("arrival", "-1");
                        Bank.this.onHelpAvailable();
                        Bank.this.a("cb_status", com.payu.custombrowser.util.a.c);
                        if (fields != null && Bank.this.f != null) {
                            Bank.this.dismissSnoozeWindow();
                            View inflate = Bank.this.f.getLayoutInflater().inflate(R.layout.nb_layout, null);
                            final Button button = (Button) inflate.findViewById(R.id.b_continue);
                            final CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.checkbox);
                            JSONObject jSONObject = new JSONObject(params);
                            String string = Bank.this.getString(R.string.cb_btn_text);
                            if (!jSONObject.has(string) || jSONObject.getString(string) == null || jSONObject.getString(string).equalsIgnoreCase("")) {
                                Bank.this.onHelpUnavailable();
                                Bank.this.K.removeAllViews();
                            } else if (fields.equals(Bank.this.getString(R.string.cb_button))) {
                                if (!jSONObject.has(Bank.this.getString(R.string.cb_checkbox))) {
                                    checkBox.setVisibility(8);
                                } else if (jSONObject.getBoolean(Bank.this.getString(R.string.cb_checkbox))) {
                                    if (Bank.this.aF) {
                                        Bank.this.a(com.payu.custombrowser.util.a.f, "y");
                                        checkBox.setChecked(true);
                                    } else {
                                        Bank.this.a(com.payu.custombrowser.util.a.f, "n");
                                        checkBox.setChecked(false);
                                    }
                                    checkBox.setOnClickListener(new OnClickListener() {
                                        public void onClick(View v) {
                                            Bank.this.aF = checkBox.isChecked();
                                            if (Bank.this.aF) {
                                                StringBuilder sb = new StringBuilder();
                                                sb.append(com.payu.custombrowser.util.a.e);
                                                sb.append("y");
                                                Bank.this.a("user_input", sb.toString());
                                                return;
                                            }
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append(com.payu.custombrowser.util.a.e);
                                            sb2.append("n");
                                            Bank.this.a("user_input", sb2.toString());
                                        }
                                    });
                                    checkBox.setVisibility(0);
                                } else {
                                    checkBox.setVisibility(8);
                                }
                                button.setText(jSONObject.getString(string));
                                button.setTransformationMethod(null);
                                button.setOnClickListener(new OnClickListener() {
                                    public void onClick(View v) {
                                        try {
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(com.payu.custombrowser.util.a.d);
                                            sb.append(button.getText());
                                            Bank.this.a("user_input", sb.toString());
                                            WebView webView = Bank.this.s;
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append("javascript:");
                                            sb2.append(Bank.this.i.getString(Bank.this.getString(R.string.cb_btn_action)));
                                            webView.loadUrl(sb2.toString());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                Bank.this.K.removeAllViews();
                                Bank.this.K.addView(inflate);
                                Bank.this.q = true;
                            } else if (fields.equals(Bank.this.getString(R.string.cb_pwd_btn))) {
                                button.setText(jSONObject.getString(string));
                                if (Bank.this.aI) {
                                    checkBox.setChecked(true);
                                } else {
                                    checkBox.setChecked(false);
                                }
                                if (checkBox.isChecked()) {
                                    try {
                                        WebView webView = Bank.this.s;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("javascript:");
                                        sb.append(Bank.this.i.getString(Bank.this.getString(R.string.cb_toggle_field)));
                                        sb.append("(\"");
                                        sb.append(true);
                                        sb.append("\")");
                                        webView.loadUrl(sb.toString());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                checkBox.setText(Bank.this.getString(R.string.cb_show_password));
                                checkBox.setVisibility(0);
                                checkBox.setOnClickListener(new OnClickListener() {
                                    public void onClick(View v) {
                                        Bank.this.aI = checkBox.isChecked();
                                        if (checkBox.isChecked()) {
                                            try {
                                                WebView webView = Bank.this.s;
                                                StringBuilder sb = new StringBuilder();
                                                sb.append("javascript:");
                                                sb.append(Bank.this.i.getString(Bank.this.getString(R.string.cb_toggle_field)));
                                                sb.append("(\"");
                                                sb.append(true);
                                                sb.append("\")");
                                                webView.loadUrl(sb.toString());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            try {
                                                WebView webView2 = Bank.this.s;
                                                StringBuilder sb2 = new StringBuilder();
                                                sb2.append("javascript:");
                                                sb2.append(Bank.this.i.getString(Bank.this.getString(R.string.cb_toggle_field)));
                                                sb2.append("(\"");
                                                sb2.append(false);
                                                sb2.append("\")");
                                                webView2.loadUrl(sb2.toString());
                                            } catch (Exception e2) {
                                                e2.printStackTrace();
                                            }
                                        }
                                    }
                                });
                                button.setOnClickListener(new OnClickListener() {
                                    public void onClick(View v) {
                                        try {
                                            WebView webView = Bank.this.s;
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("javascript:");
                                            sb.append(Bank.this.i.getString(Bank.this.getString(R.string.cb_btn_action)));
                                            webView.loadUrl(sb.toString());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                Bank.this.q = true;
                                Bank.this.K.removeAllViews();
                                Bank.this.K.addView(inflate);
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        }
        if (this.f != null && !this.f.isFinishing() && !isRemoving() && isAdded()) {
            this.f.runOnUiThread(new Runnable() {
                public void run() {
                    Bank.this.dismissPayULoader();
                }
            });
        }
    }

    @JavascriptInterface
    public void reInit() {
        if (this.f != null && !this.f.isFinishing()) {
            this.f.runOnUiThread(new Runnable() {
                public void run() {
                    Bank.this.onPageFinished();
                }
            });
        }
    }

    @JavascriptInterface
    public void bankFound(final String bank) {
        if (!this.aG) {
            checkStatusFromJS(bank);
            this.aG = true;
        }
        c(bank);
        CBUtil.setVariableReflection(CBConstant.MAGIC_RETRY_PAKAGE, bank, CBConstant.BANKNAME);
        if (this.f != null && !this.f.isFinishing()) {
            this.f.runOnUiThread(new Runnable() {
                public void run() {
                    Bank.this.e();
                }
            });
        }
        this.D = bank;
        if (!this.ap) {
            try {
                if (this.f != null && !this.f.isFinishing()) {
                    this.f.runOnUiThread(new Runnable() {
                        public void run() {
                            if (Bank.this.O != null) {
                                Bank.this.ar.b(Bank.this.O.findViewById(R.id.progress));
                            }
                        }
                    });
                }
                if (!this.az) {
                    if (this.O == null) {
                        convertToNative(CBConstant.LOADING, "{}");
                    } else if (!(this.f == null || this.O == ((ViewGroup) this.f.findViewById(R.id.help_view)).getChildAt(0))) {
                        convertToNative(CBConstant.LOADING, "{}");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!this.aD && this.i == null) {
            this.W.execute(new Runnable() {
                public void run() {
                    Bank.this.aD = true;
                    try {
                        if (Bank.this.f != null) {
                            String string = Bank.this.h.getString(bank);
                            if (!new File(Bank.this.f.getFilesDir(), string).exists()) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(CBConstant.PRODUCTION_URL);
                                sb.append(string);
                                sb.append(".js");
                                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(sb.toString()).openConnection();
                                httpsURLConnection.setRequestMethod("GET");
                                httpsURLConnection.setSSLSocketFactory(new g());
                                httpsURLConnection.setRequestProperty("Accept-Charset", Key.STRING_CHARSET_NAME);
                                if (httpsURLConnection.getResponseCode() == 200) {
                                    Bank.this.N.writeFileOutputStream(httpsURLConnection.getInputStream(), Bank.this.f, string, 0);
                                }
                            }
                        }
                        try {
                            if (Bank.this.f != null) {
                                String string2 = Bank.this.h.getString(bank);
                                Bank.this.i = new JSONObject(CBUtil.decodeContents(Bank.this.f.openFileInput(string2)));
                                if (Bank.this.f != null && !Bank.this.f.isFinishing()) {
                                    Bank.this.f.runOnUiThread(new Runnable() {
                                        public void run() {
                                            Bank.this.onPageFinished();
                                        }
                                    });
                                }
                                Bank.this.aD = false;
                            }
                        } catch (FileNotFoundException | JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        if (Bank.this.f != null) {
                            String string3 = Bank.this.h.getString(bank);
                            Bank.this.i = new JSONObject(CBUtil.decodeContents(Bank.this.f.openFileInput(string3)));
                            if (Bank.this.f != null && !Bank.this.f.isFinishing()) {
                                Bank.this.f.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Bank.this.onPageFinished();
                                    }
                                });
                            }
                            Bank.this.aD = false;
                        }
                    } catch (Throwable th) {
                        try {
                            if (Bank.this.f != null) {
                                String string4 = Bank.this.h.getString(bank);
                                Bank.this.i = new JSONObject(CBUtil.decodeContents(Bank.this.f.openFileInput(string4)));
                                if (Bank.this.f != null && !Bank.this.f.isFinishing()) {
                                    Bank.this.f.runOnUiThread(new Runnable() {
                                        public void run() {
                                            Bank.this.onPageFinished();
                                        }
                                    });
                                }
                                Bank.this.aD = false;
                            }
                        } catch (FileNotFoundException | JSONException e4) {
                            e4.printStackTrace();
                        } catch (Exception e5) {
                            e5.printStackTrace();
                        }
                        throw th;
                    }
                }
            });
        }
    }

    @JavascriptInterface
    public void fillOTPCallback(final boolean otpFilled) {
        if (this.f != null && !this.f.isFinishing()) {
            this.f.runOnUiThread(new Runnable() {
                public void run() {
                    Bank bank = Bank.this;
                    bank.isOTPFilled = otpFilled;
                    if (bank.isOTPFilled) {
                        Bank bank2 = Bank.this;
                        bank2.otp = null;
                        if (bank2.otpTriggered) {
                            Bank bank3 = Bank.this;
                            bank3.otpTriggered = false;
                            try {
                                String string = bank3.h.getString(Bank.this.getString(R.string.cb_catchAll_success_msg));
                                if (Bank.this.aU) {
                                    Bank.this.aV = string;
                                } else {
                                    Toast.makeText(Bank.this.f.getApplicationContext(), string, 0).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
    }

    @JavascriptInterface
    public void enableCatchAllJS(final boolean enableCatchAllJS) {
        if (this.f != null && !this.f.isFinishing()) {
            this.f.runOnUiThread(new Runnable() {
                public void run() {
                    Bank bank = Bank.this;
                    bank.catchAllJSEnabled = enableCatchAllJS;
                    bank.fillOTPOnBankPage(true);
                }
            });
        }
    }

    @JavascriptInterface
    public void cacheAnalytics(String event) {
        try {
            JSONObject jSONObject = new JSONObject(event);
            String obj = jSONObject.get("inputFields").toString();
            if (this.listOfTxtFld == null) {
                this.listOfTxtFld = obj;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(this.listOfTxtFld);
                sb.append(obj);
                this.listOfTxtFld = sb.toString();
            }
            this.hostName = jSONObject.get("hostName").toString();
        } catch (Exception e) {
        }
    }

    @JavascriptInterface
    public void convertToNative(final String fields, final String params) {
        if (this.n) {
            dismissSnoozeWindow();
            killSnoozeService();
            cancelTransactionNotification();
            a("snooze_window_action", "snooze_window_dismissed_by_cb");
            a("snooze_window_automatically_disappear_time", "-1");
        }
        if (this.f != null && !this.f.isFinishing()) {
            this.f.runOnUiThread(new Runnable() {
                public void run() {
                    Bank.this.dismissPayULoader();
                }
            });
        }
        if (this.pageType != null && !this.pageType.equalsIgnoreCase("")) {
            a("departure", "-1");
            this.pageType = "";
        }
        if (this.f != null && this.ag && !this.f.isFinishing()) {
            this.f.runOnUiThread(new Runnable() {
                /* JADX WARNING: Removed duplicated region for block: B:72:0x027f A[Catch:{ JSONException -> 0x034e }] */
                /* JADX WARNING: Removed duplicated region for block: B:77:0x02b6 A[Catch:{ JSONException -> 0x034e }] */
                /* JADX WARNING: Removed duplicated region for block: B:80:0x02f3 A[Catch:{ JSONException -> 0x034e }] */
                /* JADX WARNING: Removed duplicated region for block: B:86:0x0331 A[Catch:{ JSONException -> 0x034e }] */
                public void run() {
                    if (Bank.this.O != null) {
                        Bank.this.ar.b(Bank.this.O.findViewById(R.id.progress));
                    }
                    if (Bank.this.P != null) {
                        Bank.this.ar.b(Bank.this.P.findViewById(R.id.progress));
                    }
                    try {
                        if (!(Bank.this.ai == null || Bank.this.d == null)) {
                            Bank.this.N.cancelTimer(Bank.this.ai);
                        }
                        if (fields.equals(Bank.this.getString(R.string.cb_error))) {
                            Bank.this.onBankError();
                        } else if (fields.equals("parse error")) {
                            Bank.this.onBankError();
                        } else if (!fields.contentEquals(CBConstant.LOADING) || Bank.this.ae || !Bank.this.ak) {
                            boolean z = true;
                            if (fields.equals(Bank.this.getString(R.string.cb_choose))) {
                                Bank.this.a();
                                Bank.this.z = 2;
                                Bank.this.ak = true;
                                if (Bank.this.M != null) {
                                    Bank.this.M.setVisibility(0);
                                }
                                View inflate = Bank.this.f.getLayoutInflater().inflate(R.layout.choose_action, null);
                                Bank.this.addReviewOrder(inflate);
                                if (Bank.this.v == 0) {
                                    Bank.this.e();
                                    Bank.this.f();
                                }
                                Bank.this.K.setVisibility(0);
                                if (Bank.this.L != null) {
                                    Bank.this.L.setVisibility(8);
                                }
                                Bank.this.b(inflate);
                                Bank.this.onHelpAvailable();
                                inflate.measure(-2, -2);
                                Bank.this.af = inflate.getMeasuredHeight();
                                ImageView imageView = (ImageView) inflate.findViewById(R.id.bank_logo);
                                imageView.setOnClickListener(Bank.this.viewOnClickListener);
                                if (Bank.this.r != null) {
                                    imageView.setImageDrawable(Bank.this.r);
                                }
                                Bank.this.K.removeAllViews();
                                Bank.this.K.addView(inflate);
                                if (Bank.this.K.isShown()) {
                                    Bank.this.z = 2;
                                }
                                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("Select an option for Faster payment");
                                spannableStringBuilder.setSpan(new StyleSpan(1), 21, 27, 33);
                                ((TextView) inflate.findViewById(R.id.choose_text)).setText(spannableStringBuilder);
                                try {
                                    final JSONObject jSONObject = new JSONObject(params);
                                    if (jSONObject.has(Bank.this.getString(R.string.cb_otp))) {
                                        if (!jSONObject.getBoolean(Bank.this.getString(R.string.cb_otp))) {
                                        }
                                        Bank.this.pageType = "Choose Screen";
                                        if (jSONObject.has(Bank.this.getString(R.string.cb_otp)) || jSONObject.getBoolean(Bank.this.getString(R.string.cb_otp))) {
                                            inflate.findViewById(R.id.otp).setOnClickListener(Bank.this.aE);
                                            if (Bank.this.autoSelectOtp) {
                                                Bank.this.m = "auto_otp_select";
                                                Bank.this.a("user_input", Bank.this.m);
                                                inflate.findViewById(R.id.otp).performClick();
                                                Bank.this.autoSelectOtp = false;
                                            }
                                        } else {
                                            inflate.findViewById(R.id.otp).setVisibility(8);
                                            inflate.findViewById(R.id.view).setVisibility(8);
                                        }
                                        inflate.findViewById(R.id.otp).setOnClickListener(Bank.this.aE);
                                        if (jSONObject.has(Bank.this.getString(R.string.cb_pin)) || jSONObject.getBoolean(Bank.this.getString(R.string.cb_pin))) {
                                            inflate.findViewById(R.id.pin).setOnClickListener(new OnClickListener() {
                                                public void onClick(View view) {
                                                    Bank.this.ae = true;
                                                    Bank.this.aj = Boolean.valueOf(true);
                                                    Bank.this.f();
                                                    Bank.this.z = 1;
                                                    if (Bank.this.M != null) {
                                                        Bank.this.M.setVisibility(8);
                                                    }
                                                    try {
                                                        if (!jSONObject.has(Bank.this.getString(R.string.cb_register)) || !jSONObject.getBoolean(Bank.this.getString(R.string.cb_register))) {
                                                            Bank.this.m = "password_click";
                                                            Bank.this.a("user_input", Bank.this.m);
                                                            Bank.this.onHelpUnavailable();
                                                            WebView webView = Bank.this.s;
                                                            StringBuilder sb = new StringBuilder();
                                                            sb.append("javascript:");
                                                            sb.append(Bank.this.i.getString(Bank.this.getString(R.string.cb_pin)));
                                                            webView.loadUrl(sb.toString());
                                                            Bank.this.updateHeight(view);
                                                        }
                                                        view = Bank.this.f.getLayoutInflater().inflate(R.layout.register_pin, null);
                                                        Bank.this.K.removeAllViews();
                                                        Bank.this.K.addView(view);
                                                        if (Bank.this.K.isShown()) {
                                                            Bank.this.z = 2;
                                                        }
                                                        view.findViewById(R.id.pin).setOnClickListener(new OnClickListener() {
                                                            public void onClick(View view) {
                                                                try {
                                                                    Bank.this.m = "password_click";
                                                                    Bank.this.a("user_input", Bank.this.m);
                                                                    WebView webView = Bank.this.s;
                                                                    StringBuilder sb = new StringBuilder();
                                                                    sb.append("javascript:");
                                                                    sb.append(Bank.this.i.getString(Bank.this.getString(R.string.cb_pin)));
                                                                    webView.loadUrl(sb.toString());
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        });
                                                        if (jSONObject.has(Bank.this.getString(R.string.cb_otp)) && !jSONObject.getBoolean(Bank.this.getString(R.string.cb_otp))) {
                                                            view.findViewById(R.id.otp).setVisibility(8);
                                                        }
                                                        view.findViewById(R.id.otp).setOnClickListener(Bank.this.aE);
                                                        Bank.this.updateHeight(view);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        } else {
                                            inflate.findViewById(R.id.pin).setVisibility(8);
                                            inflate.findViewById(R.id.view).setVisibility(8);
                                        }
                                        if (jSONObject.has(Bank.this.getString(R.string.cb_error))) {
                                            inflate.findViewById(R.id.error_message).setVisibility(0);
                                            ((TextView) inflate.findViewById(R.id.error_message)).setText(jSONObject.getString(AnalyticsConstant.ERROR));
                                        }
                                    }
                                    if (!jSONObject.has(Bank.this.getString(R.string.cb_pin)) || !jSONObject.getBoolean(Bank.this.getString(R.string.cb_pin))) {
                                        Bank.this.pageType = "";
                                        if (jSONObject.has(Bank.this.getString(R.string.cb_otp))) {
                                        }
                                        inflate.findViewById(R.id.otp).setOnClickListener(Bank.this.aE);
                                        if (Bank.this.autoSelectOtp) {
                                        }
                                        inflate.findViewById(R.id.otp).setOnClickListener(Bank.this.aE);
                                        if (jSONObject.has(Bank.this.getString(R.string.cb_pin))) {
                                        }
                                        inflate.findViewById(R.id.pin).setOnClickListener(new OnClickListener() {
                                            public void onClick(View view) {
                                                Bank.this.ae = true;
                                                Bank.this.aj = Boolean.valueOf(true);
                                                Bank.this.f();
                                                Bank.this.z = 1;
                                                if (Bank.this.M != null) {
                                                    Bank.this.M.setVisibility(8);
                                                }
                                                try {
                                                    if (!jSONObject.has(Bank.this.getString(R.string.cb_register)) || !jSONObject.getBoolean(Bank.this.getString(R.string.cb_register))) {
                                                        Bank.this.m = "password_click";
                                                        Bank.this.a("user_input", Bank.this.m);
                                                        Bank.this.onHelpUnavailable();
                                                        WebView webView = Bank.this.s;
                                                        StringBuilder sb = new StringBuilder();
                                                        sb.append("javascript:");
                                                        sb.append(Bank.this.i.getString(Bank.this.getString(R.string.cb_pin)));
                                                        webView.loadUrl(sb.toString());
                                                        Bank.this.updateHeight(view);
                                                    }
                                                    view = Bank.this.f.getLayoutInflater().inflate(R.layout.register_pin, null);
                                                    Bank.this.K.removeAllViews();
                                                    Bank.this.K.addView(view);
                                                    if (Bank.this.K.isShown()) {
                                                        Bank.this.z = 2;
                                                    }
                                                    view.findViewById(R.id.pin).setOnClickListener(new OnClickListener() {
                                                        public void onClick(View view) {
                                                            try {
                                                                Bank.this.m = "password_click";
                                                                Bank.this.a("user_input", Bank.this.m);
                                                                WebView webView = Bank.this.s;
                                                                StringBuilder sb = new StringBuilder();
                                                                sb.append("javascript:");
                                                                sb.append(Bank.this.i.getString(Bank.this.getString(R.string.cb_pin)));
                                                                webView.loadUrl(sb.toString());
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    });
                                                    if (jSONObject.has(Bank.this.getString(R.string.cb_otp)) && !jSONObject.getBoolean(Bank.this.getString(R.string.cb_otp))) {
                                                        view.findViewById(R.id.otp).setVisibility(8);
                                                    }
                                                    view.findViewById(R.id.otp).setOnClickListener(Bank.this.aE);
                                                    Bank.this.updateHeight(view);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        if (jSONObject.has(Bank.this.getString(R.string.cb_error))) {
                                        }
                                    } else {
                                        Bank.this.pageType = "Choose Screen";
                                        if (jSONObject.has(Bank.this.getString(R.string.cb_otp))) {
                                        }
                                        inflate.findViewById(R.id.otp).setOnClickListener(Bank.this.aE);
                                        if (Bank.this.autoSelectOtp) {
                                        }
                                        inflate.findViewById(R.id.otp).setOnClickListener(Bank.this.aE);
                                        if (jSONObject.has(Bank.this.getString(R.string.cb_pin))) {
                                        }
                                        inflate.findViewById(R.id.pin).setOnClickListener(new OnClickListener() {
                                            public void onClick(View view) {
                                                Bank.this.ae = true;
                                                Bank.this.aj = Boolean.valueOf(true);
                                                Bank.this.f();
                                                Bank.this.z = 1;
                                                if (Bank.this.M != null) {
                                                    Bank.this.M.setVisibility(8);
                                                }
                                                try {
                                                    if (!jSONObject.has(Bank.this.getString(R.string.cb_register)) || !jSONObject.getBoolean(Bank.this.getString(R.string.cb_register))) {
                                                        Bank.this.m = "password_click";
                                                        Bank.this.a("user_input", Bank.this.m);
                                                        Bank.this.onHelpUnavailable();
                                                        WebView webView = Bank.this.s;
                                                        StringBuilder sb = new StringBuilder();
                                                        sb.append("javascript:");
                                                        sb.append(Bank.this.i.getString(Bank.this.getString(R.string.cb_pin)));
                                                        webView.loadUrl(sb.toString());
                                                        Bank.this.updateHeight(view);
                                                    }
                                                    view = Bank.this.f.getLayoutInflater().inflate(R.layout.register_pin, null);
                                                    Bank.this.K.removeAllViews();
                                                    Bank.this.K.addView(view);
                                                    if (Bank.this.K.isShown()) {
                                                        Bank.this.z = 2;
                                                    }
                                                    view.findViewById(R.id.pin).setOnClickListener(new OnClickListener() {
                                                        public void onClick(View view) {
                                                            try {
                                                                Bank.this.m = "password_click";
                                                                Bank.this.a("user_input", Bank.this.m);
                                                                WebView webView = Bank.this.s;
                                                                StringBuilder sb = new StringBuilder();
                                                                sb.append("javascript:");
                                                                sb.append(Bank.this.i.getString(Bank.this.getString(R.string.cb_pin)));
                                                                webView.loadUrl(sb.toString());
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    });
                                                    if (jSONObject.has(Bank.this.getString(R.string.cb_otp)) && !jSONObject.getBoolean(Bank.this.getString(R.string.cb_otp))) {
                                                        view.findViewById(R.id.otp).setVisibility(8);
                                                    }
                                                    view.findViewById(R.id.otp).setOnClickListener(Bank.this.aE);
                                                    Bank.this.updateHeight(view);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        if (jSONObject.has(Bank.this.getString(R.string.cb_error))) {
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (fields.equals(Bank.this.getString(R.string.cb_incorrect_OTP_2))) {
                                Bank.this.pageType = fields;
                                Bank.this.a();
                                Bank.this.ak = true;
                                Bank.this.onHelpAvailable();
                                View inflate2 = Bank.this.f.getLayoutInflater().inflate(R.layout.retry_otp, null);
                                Bank.this.addReviewOrder(inflate2);
                                ImageView imageView2 = (ImageView) inflate2.findViewById(R.id.bank_logo);
                                imageView2.setOnClickListener(Bank.this.viewOnClickListener);
                                if (Bank.this.r != null) {
                                    imageView2.setImageDrawable(Bank.this.r);
                                }
                                Bank.this.K.removeAllViews();
                                Bank.this.K.addView(inflate2);
                                if (Bank.this.K.isShown()) {
                                    Bank.this.z = 2;
                                } else {
                                    if (Bank.this.L != null) {
                                        Bank.this.L.setVisibility(0);
                                    }
                                    Bank.this.f();
                                }
                                if (Bank.this.ah == null) {
                                    inflate2.findViewById(R.id.regenerate_layout).setVisibility(0);
                                    inflate2.findViewById(R.id.Regenerate_layout_gone).setVisibility(8);
                                    try {
                                        JSONObject jSONObject2 = new JSONObject(params);
                                        if (!jSONObject2.has(Bank.this.getString(R.string.cb_pin)) || !jSONObject2.getBoolean(Bank.this.getString(R.string.cb_pin))) {
                                            z = false;
                                        }
                                        inflate2.findViewById(R.id.enter_manually).setOnClickListener(Bank.this.aE);
                                        if (z) {
                                            inflate2.findViewById(R.id.pin_layout_gone).setVisibility(0);
                                        } else {
                                            inflate2.findViewById(R.id.pin_layout_gone).setVisibility(8);
                                        }
                                        inflate2.findViewById(R.id.pin).setOnClickListener(Bank.this.aE);
                                    } catch (Exception e2) {
                                        e2.printStackTrace();
                                    }
                                }
                                Bank.this.updateHeight(inflate2);
                            } else if (fields.equals(Bank.this.getString(R.string.cb_retry_otp))) {
                                Bank.this.pageType = fields;
                                Bank.this.a();
                                Bank.this.ak = true;
                                Bank.this.onHelpAvailable();
                                Bank.this.c();
                                if (Bank.this.M != null) {
                                    Bank.this.M.setVisibility(0);
                                }
                                View inflate3 = Bank.this.f.getLayoutInflater().inflate(R.layout.retry_otp, null);
                                Bank.this.addReviewOrder(inflate3);
                                ImageView imageView3 = (ImageView) inflate3.findViewById(R.id.bank_logo);
                                imageView3.setOnClickListener(Bank.this.viewOnClickListener);
                                if (Bank.this.r != null) {
                                    imageView3.setImageDrawable(Bank.this.r);
                                }
                                Bank.this.K.removeAllViews();
                                Bank.this.K.addView(inflate3);
                                if (Bank.this.K.isShown()) {
                                    Bank.this.z = 2;
                                } else {
                                    if (Bank.this.L != null) {
                                        Bank.this.L.setVisibility(0);
                                    }
                                    Bank.this.f();
                                }
                                try {
                                    if (Bank.this.ah == null) {
                                        JSONObject jSONObject3 = new JSONObject(params);
                                        boolean z2 = jSONObject3.has(Bank.this.getString(R.string.cb_regenerate)) && jSONObject3.getBoolean(Bank.this.getString(R.string.cb_regenerate));
                                        if (!jSONObject3.has(Bank.this.getString(R.string.cb_pin)) || !jSONObject3.getBoolean(Bank.this.getString(R.string.cb_pin))) {
                                            z = false;
                                        }
                                        inflate3.findViewById(R.id.regenerate_layout).setVisibility(0);
                                        if (z2) {
                                            inflate3.findViewById(R.id.Regenerate_layout_gone).setVisibility(0);
                                            if (z) {
                                                inflate3.findViewById(R.id.Enter_manually_gone).setVisibility(8);
                                                inflate3.findViewById(R.id.pin_layout_gone).setVisibility(0);
                                            } else {
                                                inflate3.findViewById(R.id.Enter_manually_gone).setVisibility(0);
                                                inflate3.findViewById(R.id.pin_layout_gone).setVisibility(8);
                                            }
                                        } else {
                                            if (z) {
                                                inflate3.findViewById(R.id.pin_layout_gone).setVisibility(0);
                                            } else {
                                                inflate3.findViewById(R.id.pin_layout_gone).setVisibility(8);
                                            }
                                            inflate3.findViewById(R.id.Regenerate_layout_gone).setVisibility(8);
                                            inflate3.findViewById(R.id.Enter_manually_gone).setVisibility(0);
                                        }
                                    }
                                    inflate3.findViewById(R.id.pin).setOnClickListener(Bank.this.aE);
                                    inflate3.findViewById(R.id.enter_manually).setOnClickListener(Bank.this.aE);
                                    inflate3.findViewById(R.id.retry).setOnClickListener(Bank.this.aE);
                                    Bank.this.aE.a(inflate3);
                                    inflate3.findViewById(R.id.approve).setOnClickListener(Bank.this.aE);
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                                Bank.this.updateHeight(inflate3);
                            } else if (fields.equals(Bank.this.getString(R.string.cb_enter_pin))) {
                                Bank.this.pageType = "PIN Page";
                                Bank.this.a();
                                if (Bank.this.L != null) {
                                    Bank.this.L.setVisibility(8);
                                }
                                Bank.this.onHelpUnavailable();
                                Bank.this.ae = true;
                                Bank.this.aj = Boolean.valueOf(true);
                                Bank.this.f();
                                Bank.this.z = 1;
                                if (Bank.this.M != null) {
                                    Bank.this.M.setVisibility(8);
                                }
                                Bank.this.f();
                                Bank.this.K.removeAllViews();
                            } else if (fields.equals(Bank.this.getString(R.string.cb_enter_otp))) {
                                Bank.this.pageType = fields;
                                Bank.this.aq = false;
                                Bank.this.p();
                                Bank.this.al = params;
                                if (!Bank.this.ao) {
                                    Bank.this.a();
                                    Bank.this.a(params);
                                }
                            } else if (fields.equals(Bank.this.getString(R.string.cb_incorrect_pin))) {
                                Bank.this.pageType = "Choose Screen";
                                Bank.this.a();
                                try {
                                    JSONObject jSONObject4 = new JSONObject(params);
                                    if (jSONObject4.has(Bank.this.getString(R.string.cb_otp)) && jSONObject4.getBoolean(Bank.this.getString(R.string.cb_otp))) {
                                        Bank.this.ak = true;
                                        Bank.this.onHelpAvailable();
                                        View inflate4 = Bank.this.f.getLayoutInflater().inflate(R.layout.choose_action, null);
                                        Bank.this.addReviewOrder(inflate4);
                                        ImageView imageView4 = (ImageView) inflate4.findViewById(R.id.bank_logo);
                                        imageView4.setOnClickListener(Bank.this.viewOnClickListener);
                                        if (Bank.this.r != null) {
                                            imageView4.setImageDrawable(Bank.this.r);
                                        }
                                        TextView textView = (TextView) inflate4.findViewById(R.id.error_message);
                                        textView.setVisibility(0);
                                        textView.setText(Bank.this.f.getResources().getString(R.string.cb_incorrect_password));
                                        TextView textView2 = (TextView) inflate4.findViewById(R.id.choose_text);
                                        textView2.setVisibility(0);
                                        textView2.setText(Bank.this.f.getResources().getString(R.string.cb_retry));
                                        Bank.this.K.removeAllViews();
                                        Bank.this.K.addView(inflate4);
                                        inflate4.findViewById(R.id.otp).setOnClickListener(Bank.this.aE);
                                        inflate4.findViewById(R.id.pin).setOnClickListener(Bank.this.aE);
                                        Bank.this.updateHeight(inflate4);
                                        if (Bank.this.K.isShown()) {
                                            Bank.this.z = 2;
                                        } else {
                                            Bank.this.f();
                                        }
                                    }
                                } catch (Exception e4) {
                                    e4.printStackTrace();
                                }
                            } else if (fields.equals(Bank.this.getString(R.string.cb_register_option))) {
                                Bank.this.pageType = "Register Page";
                                Bank.this.a();
                                Bank.this.onHelpAvailable();
                                View inflate5 = Bank.this.f.getLayoutInflater().inflate(R.layout.register, null);
                                Bank.this.addReviewOrder(inflate5);
                                Bank.this.K.removeAllViews();
                                Bank.this.K.addView(inflate5);
                                ImageView imageView5 = (ImageView) inflate5.findViewById(R.id.bank_logo);
                                imageView5.setOnClickListener(Bank.this.viewOnClickListener);
                                if (Bank.this.r != null) {
                                    imageView5.setImageDrawable(Bank.this.r);
                                }
                                Bank.this.updateHeight(inflate5);
                                if (Bank.this.K.isShown()) {
                                    Bank.this.z = 2;
                                } else {
                                    Bank.this.f();
                                }
                            } else {
                                Bank.this.f();
                                Bank.this.z = 1;
                                if (Bank.this.L != null) {
                                    Bank.this.L.setVisibility(8);
                                }
                                Bank.this.onHelpUnavailable();
                            }
                        } else {
                            Bank.this.onHelpAvailable();
                            if (Bank.this.M != null) {
                                Bank.this.M.setVisibility(0);
                            }
                            if (Bank.this.O == null) {
                                Bank.this.O = Bank.this.f.getLayoutInflater().inflate(R.layout.cb_loading, null);
                            }
                            ImageView imageView6 = (ImageView) Bank.this.O.findViewById(R.id.bank_logo);
                            imageView6.setOnClickListener(Bank.this.viewOnClickListener);
                            if (Bank.this.r != null) {
                                imageView6.setImageDrawable(Bank.this.r);
                            }
                            LayoutParams layoutParams = new LayoutParams(-1, Bank.this.af);
                            View findViewById = Bank.this.O.findViewById(R.id.loading);
                            findViewById.setLayoutParams(layoutParams);
                            findViewById.requestLayout();
                            findViewById.invalidate();
                            Bank.this.ar.a(Bank.this.O.findViewById(R.id.progress));
                            Bank.this.K.removeAllViews();
                            Bank.this.K.addView(Bank.this.O);
                            if (Bank.this.K.isShown()) {
                                Bank.this.z = 2;
                            } else {
                                Bank.this.f();
                            }
                            Bank.this.updateHeight(Bank.this.O);
                            Bank.this.addReviewOrder(Bank.this.O);
                        }
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                    if (Bank.this.pageType != null && !Bank.this.pageType.equalsIgnoreCase("")) {
                        Bank.this.a("arrival", "-1");
                    }
                }
            });
        }
    }

    @JavascriptInterface
    public void showJSRequestedToast(String message) {
        if (this.aU) {
            this.aV = message;
        } else {
            Toast.makeText(this.f.getApplicationContext(), message, 0).show();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (!this.o.contains("CUSTOM_BROWSER")) {
            this.m = "CUSTOM_BROWSER";
            this.o.add("CUSTOM_BROWSER");
            a("cb_status", this.m);
        }
    }

    public void onPageFinished() {
        if (isAdded() && !isRemoving() && this.f != null) {
            this.ap = true;
            if (this.aj.booleanValue()) {
                onHelpUnavailable();
                this.aj = Boolean.valueOf(false);
            }
            if (this.O != null && this.O.isShown()) {
                this.z = 1;
                f();
                onHelpUnavailable();
            }
            this.f.getWindow().setSoftInputMode(3);
            if (this.i != null && this.ag && !this.az) {
                try {
                    WebView webView = this.s;
                    StringBuilder sb = new StringBuilder();
                    sb.append("javascript:");
                    sb.append(this.i.getString(getString(R.string.cb_init)));
                    webView.loadUrl(sb.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (this.h != null) {
                if (!this.aP) {
                    checkStatusFromJS("", 3);
                    this.aP = true;
                }
                if (this.M != null) {
                    this.M.setVisibility(8);
                }
            }
            fillOTPOnBankPage(true);
        }
    }

    public void fillOTPOnBankPage(boolean isAutoFillOTP) {
        if (!TextUtils.isEmpty(this.otp) && this.catchAllJSEnabled && !this.backwardJourneyStarted && !this.isOTPFilled) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("otp", this.otp);
                jSONObject.put("isAutoFillOTP", isAutoFillOTP);
                WebView webView = this.s;
                StringBuilder sb = new StringBuilder();
                sb.append("javascript:");
                sb.append(this.h.getString(getString(R.string.cb_fill_otp)));
                sb.append("(");
                sb.append(jSONObject);
                sb.append(")");
                webView.loadUrl(sb.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onPageStarted() {
        if (this.f != null && !this.f.isFinishing() && !isRemoving() && isAdded()) {
            if (this.q) {
                onHelpUnavailable();
                this.q = false;
            }
            if (isAdded() && !isRemoving()) {
                this.ap = false;
                if (this.h != null) {
                    try {
                        if (this.ag) {
                            WebView webView = this.s;
                            StringBuilder sb = new StringBuilder();
                            sb.append("javascript:");
                            sb.append(this.h.getString(getString(R.string.cb_detect_bank)));
                            webView.loadUrl(sb.toString());
                            r();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (this.M != null) {
                    this.M.setVisibility(8);
                }
            }
        }
    }

    @JavascriptInterface
    public void onFailure(String result) {
        this.F = result;
        l();
    }

    @JavascriptInterface
    public void onPayuFailure(String result) {
        if (this.snoozeService != null) {
            this.snoozeService.a();
        }
        if (this.f != null) {
            this.m = "failure_transaction";
            a("trxn_status", this.m);
            this.G = Boolean.valueOf(false);
            this.E = result;
        }
        cancelTransactionNotification();
        k();
    }

    @JavascriptInterface
    public void onSuccess() {
        onSuccess("");
    }

    @JavascriptInterface
    public void onPayuSuccess(String result) {
        if (this.snoozeService != null) {
            this.snoozeService.a();
        }
        this.G = Boolean.valueOf(true);
        this.m = "success_transaction";
        a("trxn_status", this.m);
        this.E = result;
        if (this.H == 2) {
            try {
                JSONObject jSONObject = new JSONObject(this.E);
                this.N.storeInSharedPreferences(this.f.getApplicationContext(), jSONObject.getString("card_token"), jSONObject.getString("merchant_hash"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        cancelTransactionNotification();
        k();
    }

    @JavascriptInterface
    public void onSuccess(String result) {
        this.F = result;
        l();
    }

    @JavascriptInterface
    public void onCancel() {
        onCancel("");
    }

    @JavascriptInterface
    public void onCancel(final String result) {
        if (this.f != null && !this.f.isFinishing()) {
            this.f.runOnUiThread(new Runnable() {
                public void run() {
                    if (Bank.this.f != null && !Bank.this.f.isFinishing() && Bank.this.isAdded()) {
                        Intent intent = new Intent();
                        intent.putExtra(Bank.this.getString(R.string.cb_result), result);
                        Bank.this.f.setResult(0, intent);
                        Bank.this.f.finish();
                    }
                }
            });
        }
    }

    /* JADX WARNING: type inference failed for: r2v6, types: [android.view.View] */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r23v0 */
    /* JADX WARNING: type inference failed for: r11v1, types: [android.view.View] */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r2v15 */
    /* JADX WARNING: type inference failed for: r24v1 */
    /* JADX WARNING: type inference failed for: r2v16 */
    /* JADX WARNING: type inference failed for: r2v19, types: [boolean] */
    /* JADX WARNING: type inference failed for: r2v20 */
    /* JADX WARNING: type inference failed for: r2v21 */
    /* JADX WARNING: type inference failed for: r2v22 */
    /* JADX WARNING: type inference failed for: r2v23 */
    /* JADX WARNING: type inference failed for: r2v24 */
    /* JADX WARNING: type inference failed for: r2v27, types: [android.view.View] */
    /* JADX WARNING: type inference failed for: r2v30 */
    /* JADX WARNING: type inference failed for: r2v32, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r2v35 */
    /* JADX WARNING: type inference failed for: r2v36 */
    /* JADX WARNING: type inference failed for: r2v37 */
    /* JADX WARNING: type inference failed for: r2v38 */
    /* JADX WARNING: type inference failed for: r2v39 */
    /* JADX WARNING: type inference failed for: r2v40 */
    /* JADX WARNING: type inference failed for: r2v41 */
    /* JADX WARNING: type inference failed for: r2v42 */
    /* JADX WARNING: type inference failed for: r2v43 */
    /* JADX WARNING: type inference failed for: r2v44 */
    /* JADX WARNING: type inference failed for: r2v45 */
    /* JADX WARNING: type inference failed for: r2v46 */
    /* JADX WARNING: type inference failed for: r2v47 */
    /* JADX WARNING: type inference failed for: r2v48 */
    /* JADX WARNING: type inference failed for: r2v49 */
    /* JADX WARNING: type inference failed for: r2v50 */
    /* JADX WARNING: type inference failed for: r2v51 */
    /* JADX WARNING: type inference failed for: r2v52 */
    /* JADX WARNING: type inference failed for: r2v53 */
    /* JADX WARNING: type inference failed for: r2v54 */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v11
  assigns: []
  uses: []
  mth insns count: 419
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x02ee  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0309  */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x0355  */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x0410  */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x0413  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x020b A[SYNTHETIC, Splitter:B:57:0x020b] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x026c A[Catch:{ Exception -> 0x02f9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x029c A[Catch:{ Exception -> 0x02f9 }] */
    /* JADX WARNING: Unknown variable types count: 14 */
    public void a(String str) {
        View view;
        ? r2;
        int i;
        int i2;
        ? r22;
        ? r23;
        ? r24;
        ? r25;
        ? r26;
        boolean z;
        ? r27;
        int i3;
        int i4;
        if (!this.aO) {
            m();
            if (this.m.equals("payment_initiated")) {
                this.m = "CUSTOM_BROWSER";
                this.o.add("CUSTOM_BROWSER");
                a("cb_status", this.m);
            }
            if (this.P != null) {
                this.ar.b(this.P.findViewById(R.id.progress));
            }
            this.ak = true;
            onHelpAvailable();
            if (this.M != null) {
                this.M.setVisibility(0);
            }
            if (this.P == null) {
                this.P = this.f.getLayoutInflater().inflate(R.layout.wait_for_otp, null);
            }
            final Button button = (Button) this.P.findViewById(R.id.approve);
            final View findViewById = this.P.findViewById(R.id.Regenerate_layout_gone);
            final View findViewById2 = this.P.findViewById(R.id.Enter_manually_gone);
            final View findViewById3 = this.P.findViewById(R.id.pin_layout_gone);
            View findViewById4 = this.P.findViewById(R.id.regenerate_layout);
            ImageView imageView = (ImageView) this.P.findViewById(R.id.bank_logo);
            TextView textView = (TextView) this.P.findViewById(R.id.timer);
            final EditText editText = (EditText) this.P.findViewById(R.id.otp_sms);
            View findViewById5 = this.P.findViewById(R.id.waiting);
            View findViewById6 = this.P.findViewById(R.id.pin);
            View findViewById7 = this.P.findViewById(R.id.retry);
            ? findViewById8 = this.P.findViewById(R.id.enter_manually_button);
            View findViewById9 = this.P.findViewById(R.id.enter_manually);
            View findViewById10 = this.P.findViewById(R.id.retry_text);
            ImageView imageView2 = imageView;
            View findViewById11 = this.P.findViewById(R.id.text_or);
            View findViewById12 = this.P.findViewById(R.id.progress);
            View findViewById13 = this.P.findViewById(R.id.otp_recieved);
            View view2 = findViewById9;
            button.setVisibility(8);
            CBUtil.setAlpha(0.3f, button);
            findViewById.setVisibility(8);
            findViewById2.setVisibility(0);
            findViewById3.setVisibility(8);
            findViewById4.setVisibility(0);
            textView.setVisibility(4);
            editText.setVisibility(8);
            findViewById5.setVisibility(0);
            findViewById6.setVisibility(0);
            findViewById7.setVisibility(0);
            findViewById10.setVisibility(8);
            findViewById12.setVisibility(0);
            findViewById13.setVisibility(8);
            findViewById8.setVisibility(8);
            View view3 = view2;
            View view4 = findViewById13;
            view3.setVisibility(0);
            TextView textView2 = textView;
            View view5 = findViewById11;
            view5.setVisibility(0);
            findViewById8.setOnClickListener(this.aE);
            view3.setOnClickListener(this.aE);
            if (this.r != null) {
                view = view3;
                imageView2.setImageDrawable(this.r);
            } else {
                view = view3;
            }
            editText.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    if (editText.getText().toString().length() > 5) {
                        Bank.this.aE.a(Bank.this.P);
                        button.setOnClickListener(Bank.this.aE);
                        button.setClickable(true);
                        CBUtil.setAlpha(1.0f, button);
                        return;
                    }
                    button.setClickable(false);
                    CBUtil.setAlpha(1.0f, button);
                    button.setOnClickListener(null);
                }

                public void afterTextChanged(Editable editable) {
                }
            });
            if (this.f != null && !this.f.isFinishing()) {
                this.ar.a(findViewById12);
            }
            this.K.removeAllViews();
            this.K.addView(this.P);
            if (this.K.isShown()) {
                this.z = 2;
            } else {
                f();
            }
            if (this.z == 1 && this.L != null) {
                this.L.setVisibility(0);
            }
            try {
                r24 = findViewById8;
                JSONObject jSONObject = new JSONObject(str);
                boolean z2 = jSONObject.has(getString(R.string.cb_regenerate)) && jSONObject.getBoolean(getString(R.string.cb_regenerate));
                View view6 = findViewById12;
                try {
                    r26 = findViewById8;
                    boolean z3 = jSONObject.has(getString(R.string.cb_skip_screen)) && jSONObject.getBoolean(getString(R.string.cb_skip_screen));
                    ? r242 = findViewById8;
                    try {
                        ? has = jSONObject.has(getString(R.string.cb_pin));
                        if (has != 0) {
                            has = getString(R.string.cb_pin);
                            if (jSONObject.getBoolean(has)) {
                                z = true;
                                if (!z3) {
                                    try {
                                        r26 = has;
                                        if (this.f == null || editText == null || editText.getVisibility() == 0) {
                                            r27 = r242;
                                        } else {
                                            findViewById10.setVisibility(0);
                                            view5.setVisibility(8);
                                            if (z2) {
                                                findViewById.setVisibility(0);
                                                findViewById3.setVisibility(8);
                                                findViewById2.setVisibility(0);
                                                i4 = 8;
                                                i3 = 0;
                                            } else {
                                                if (z) {
                                                    findViewById3.setVisibility(0);
                                                    i4 = 8;
                                                } else {
                                                    i4 = 8;
                                                    findViewById3.setVisibility(8);
                                                }
                                                findViewById.setVisibility(i4);
                                                i3 = 0;
                                                findViewById2.setVisibility(0);
                                            }
                                            findViewById4.setVisibility(i3);
                                            button.setVisibility(i4);
                                            findViewById5.setVisibility(i4);
                                            findViewById6.setOnClickListener(this.aE);
                                            findViewById7.setOnClickListener(this.aE);
                                            ? r28 = r242;
                                            r26 = r28;
                                            r28.setOnClickListener(this.aE);
                                            updateHeight(this.P);
                                            r27 = r28;
                                        }
                                    } catch (Exception e) {
                                        e = e;
                                        r25 = r242;
                                        findViewById12 = view6;
                                        r23 = r25;
                                        r22 = r23;
                                        e.printStackTrace();
                                        r2 = r22;
                                        if (VERSION.SDK_INT >= 23) {
                                        }
                                        i = 30;
                                        View view7 = view5;
                                        View view8 = findViewById7;
                                        final int i5 = i;
                                        View view9 = findViewById12;
                                        final TextView textView3 = textView2;
                                        View view10 = findViewById6;
                                        final String str2 = str;
                                        View view11 = findViewById5;
                                        final View view12 = view7;
                                        EditText editText2 = editText;
                                        final View view13 = view;
                                        View view14 = findViewById4;
                                        final ? r11 = r2;
                                        Button button2 = button;
                                        final View view15 = findViewById10;
                                        View view16 = findViewById10;
                                        final View view17 = view14;
                                        AnonymousClass18 r0 = r1;
                                        final Button button3 = button2;
                                        View view18 = view4;
                                        final View view19 = view11;
                                        final EditText editText3 = editText2;
                                        final View view20 = view10;
                                        final View view21 = view8;
                                        AnonymousClass18 r1 = new Runnable(this) {
                                            int a = i5;
                                            final /* synthetic */ Bank r;

                                            {
                                                this.r = r3;
                                            }

                                            public void run() {
                                                String str;
                                                boolean z = true;
                                                if (this.a == 0) {
                                                    try {
                                                        if (!this.r.aT && this.r.f != null && this.r.P.isShown() && this.r.f.findViewById(R.id.otp_sms) != null) {
                                                            this.r.aT = true;
                                                            textView3.setVisibility(8);
                                                            JSONObject jSONObject = new JSONObject(str2);
                                                            boolean z2 = jSONObject.has(this.r.getString(R.string.cb_regenerate)) && jSONObject.getBoolean(this.r.getString(R.string.cb_regenerate));
                                                            if (!jSONObject.has(this.r.getString(R.string.cb_pin)) || !jSONObject.getBoolean(this.r.getString(R.string.cb_pin))) {
                                                                z = false;
                                                            }
                                                            view12.setVisibility(8);
                                                            if (z2) {
                                                                findViewById.setVisibility(0);
                                                                findViewById3.setVisibility(8);
                                                                findViewById2.setVisibility(0);
                                                            } else {
                                                                if (z) {
                                                                    findViewById3.setVisibility(0);
                                                                } else {
                                                                    findViewById3.setVisibility(8);
                                                                }
                                                                findViewById.setVisibility(8);
                                                                findViewById2.setVisibility(0);
                                                            }
                                                            view13.setVisibility(8);
                                                            r11.setVisibility(0);
                                                            view15.setVisibility(0);
                                                            view17.setVisibility(0);
                                                            button3.setVisibility(8);
                                                            view19.setVisibility(8);
                                                            editText3.setVisibility(8);
                                                            view20.setOnClickListener(this.r.aE);
                                                            view21.setOnClickListener(this.r.aE);
                                                            r11.setOnClickListener(this.r.aE);
                                                            this.r.updateHeight(this.r.P);
                                                        }
                                                    } catch (Exception e2) {
                                                        e2.printStackTrace();
                                                    }
                                                } else if (!editText3.hasFocus() && editText3.getText().toString().matches("")) {
                                                    try {
                                                        this.r.aT = false;
                                                        JSONObject jSONObject2 = new JSONObject(str2);
                                                        boolean z3 = jSONObject2.has(this.r.getString(R.string.cb_regenerate)) && jSONObject2.getBoolean(this.r.getString(R.string.cb_regenerate));
                                                        if (!jSONObject2.has(this.r.getString(R.string.cb_pin)) || jSONObject2.getBoolean(this.r.getString(R.string.cb_pin))) {
                                                        }
                                                        if (this.a == i5 && z3) {
                                                            textView3.setVisibility(0);
                                                        }
                                                        if (VERSION.SDK_INT < 23 || this.r.am) {
                                                            textView3.setVisibility(0);
                                                            StringBuilder sb = new StringBuilder();
                                                            sb.append(this.a);
                                                            sb.append("");
                                                            str = sb.toString();
                                                        } else if (this.a != 1) {
                                                            StringBuilder sb2 = new StringBuilder();
                                                            sb2.append(this.a);
                                                            sb2.append("  secs remaining to regenerate OTP\n");
                                                            str = sb2.toString();
                                                        } else {
                                                            StringBuilder sb3 = new StringBuilder();
                                                            sb3.append(this.a);
                                                            sb3.append(" sec remaining to regenerate OTP\n");
                                                            str = sb3.toString();
                                                        }
                                                        textView3.setText(str);
                                                        this.a--;
                                                    } catch (Exception e3) {
                                                        e3.printStackTrace();
                                                    }
                                                }
                                            }
                                        };
                                        this.d = r0;
                                        if (this.ah != null) {
                                        }
                                        i2 = 2;
                                        updateHeight(this.P);
                                        addReviewOrder(this.P);
                                        if (!this.K.isShown()) {
                                        }
                                    }
                                } else {
                                    ? r29 = r242;
                                    if (!z2 && !z) {
                                        if (!this.am) {
                                            r27 = r29;
                                        }
                                    }
                                    editText.setText("");
                                    this.ai = new Timer();
                                    this.ai.scheduleAtFixedRate(new TimerTask() {
                                        public synchronized void run() {
                                            if (Bank.this.f != null && !Bank.this.f.isFinishing()) {
                                                Bank.this.f.runOnUiThread(Bank.this.d);
                                            }
                                        }
                                    }, 0, 1000);
                                    r27 = r29;
                                }
                                if (VERSION.SDK_INT >= 23) {
                                    findViewById12 = view6;
                                    r2 = r27;
                                } else if (!this.am) {
                                    button.setClickable(false);
                                    LinearLayout linearLayout = (LinearLayout) this.P.findViewById(R.id.linear_layout_waiting_for_otp);
                                    if (this.aS) {
                                        editText.setInputType(2);
                                    } else {
                                        editText.setInputType(1);
                                    }
                                    a((View) editText);
                                    editText.setVisibility(0);
                                    button.setVisibility(0);
                                    try {
                                        findViewById5.setVisibility(8);
                                        findViewById12 = view6;
                                        try {
                                            findViewById12.setVisibility(8);
                                            r24 = r27;
                                            findViewById4.setVisibility(0);
                                            r24 = r27;
                                            findViewById2.setVisibility(8);
                                            editText.addTextChangedListener(new TextWatcher() {
                                                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                                                }

                                                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                                                    if (editText.getText().toString().length() > 5) {
                                                        Bank.this.aE.a(Bank.this.P);
                                                        button.setOnClickListener(Bank.this.aE);
                                                        button.setClickable(true);
                                                        CBUtil.setAlpha(1.0f, button);
                                                        return;
                                                    }
                                                    button.setClickable(false);
                                                    CBUtil.setAlpha(0.3f, button);
                                                    button.setOnClickListener(null);
                                                }

                                                public void afterTextChanged(Editable editable) {
                                                }
                                            });
                                            r2 = r27;
                                        } catch (Exception e2) {
                                            e = e2;
                                            r22 = r27;
                                        }
                                    } catch (Exception e3) {
                                        e = e3;
                                        findViewById12 = view6;
                                        r22 = r27;
                                        e.printStackTrace();
                                        r2 = r22;
                                        if (VERSION.SDK_INT >= 23) {
                                        }
                                        i = 30;
                                        View view72 = view5;
                                        View view82 = findViewById7;
                                        final int i52 = i;
                                        View view92 = findViewById12;
                                        final TextView textView32 = textView2;
                                        View view102 = findViewById6;
                                        final String str22 = str;
                                        View view112 = findViewById5;
                                        final View view122 = view72;
                                        EditText editText22 = editText;
                                        final View view132 = view;
                                        View view142 = findViewById4;
                                        final ? r112 = r2;
                                        Button button22 = button;
                                        final View view152 = findViewById10;
                                        View view162 = findViewById10;
                                        final View view172 = view142;
                                        AnonymousClass18 r02 = r1;
                                        final Button button32 = button22;
                                        View view182 = view4;
                                        final View view192 = view112;
                                        final EditText editText32 = editText22;
                                        final View view202 = view102;
                                        final View view212 = view82;
                                        AnonymousClass18 r12 = new Runnable(this) {
                                            int a = i52;
                                            final /* synthetic */ Bank r;

                                            {
                                                this.r = r3;
                                            }

                                            public void run() {
                                                String str;
                                                boolean z = true;
                                                if (this.a == 0) {
                                                    try {
                                                        if (!this.r.aT && this.r.f != null && this.r.P.isShown() && this.r.f.findViewById(R.id.otp_sms) != null) {
                                                            this.r.aT = true;
                                                            textView32.setVisibility(8);
                                                            JSONObject jSONObject = new JSONObject(str22);
                                                            boolean z2 = jSONObject.has(this.r.getString(R.string.cb_regenerate)) && jSONObject.getBoolean(this.r.getString(R.string.cb_regenerate));
                                                            if (!jSONObject.has(this.r.getString(R.string.cb_pin)) || !jSONObject.getBoolean(this.r.getString(R.string.cb_pin))) {
                                                                z = false;
                                                            }
                                                            view122.setVisibility(8);
                                                            if (z2) {
                                                                findViewById.setVisibility(0);
                                                                findViewById3.setVisibility(8);
                                                                findViewById2.setVisibility(0);
                                                            } else {
                                                                if (z) {
                                                                    findViewById3.setVisibility(0);
                                                                } else {
                                                                    findViewById3.setVisibility(8);
                                                                }
                                                                findViewById.setVisibility(8);
                                                                findViewById2.setVisibility(0);
                                                            }
                                                            view132.setVisibility(8);
                                                            r112.setVisibility(0);
                                                            view152.setVisibility(0);
                                                            view172.setVisibility(0);
                                                            button32.setVisibility(8);
                                                            view192.setVisibility(8);
                                                            editText32.setVisibility(8);
                                                            view202.setOnClickListener(this.r.aE);
                                                            view212.setOnClickListener(this.r.aE);
                                                            r112.setOnClickListener(this.r.aE);
                                                            this.r.updateHeight(this.r.P);
                                                        }
                                                    } catch (Exception e2) {
                                                        e2.printStackTrace();
                                                    }
                                                } else if (!editText32.hasFocus() && editText32.getText().toString().matches("")) {
                                                    try {
                                                        this.r.aT = false;
                                                        JSONObject jSONObject2 = new JSONObject(str22);
                                                        boolean z3 = jSONObject2.has(this.r.getString(R.string.cb_regenerate)) && jSONObject2.getBoolean(this.r.getString(R.string.cb_regenerate));
                                                        if (!jSONObject2.has(this.r.getString(R.string.cb_pin)) || jSONObject2.getBoolean(this.r.getString(R.string.cb_pin))) {
                                                        }
                                                        if (this.a == i52 && z3) {
                                                            textView32.setVisibility(0);
                                                        }
                                                        if (VERSION.SDK_INT < 23 || this.r.am) {
                                                            textView32.setVisibility(0);
                                                            StringBuilder sb = new StringBuilder();
                                                            sb.append(this.a);
                                                            sb.append("");
                                                            str = sb.toString();
                                                        } else if (this.a != 1) {
                                                            StringBuilder sb2 = new StringBuilder();
                                                            sb2.append(this.a);
                                                            sb2.append("  secs remaining to regenerate OTP\n");
                                                            str = sb2.toString();
                                                        } else {
                                                            StringBuilder sb3 = new StringBuilder();
                                                            sb3.append(this.a);
                                                            sb3.append(" sec remaining to regenerate OTP\n");
                                                            str = sb3.toString();
                                                        }
                                                        textView32.setText(str);
                                                        this.a--;
                                                    } catch (Exception e3) {
                                                        e3.printStackTrace();
                                                    }
                                                }
                                            }
                                        };
                                        this.d = r02;
                                        if (this.ah != null) {
                                        }
                                        i2 = 2;
                                        updateHeight(this.P);
                                        addReviewOrder(this.P);
                                        if (!this.K.isShown()) {
                                        }
                                    }
                                } else {
                                    findViewById12 = view6;
                                    r2 = r27;
                                }
                                if (VERSION.SDK_INT >= 23 || this.am) {
                                    i = 30;
                                } else {
                                    i = 45;
                                }
                                View view722 = view5;
                                View view822 = findViewById7;
                                final int i522 = i;
                                View view922 = findViewById12;
                                final TextView textView322 = textView2;
                                View view1022 = findViewById6;
                                final String str222 = str;
                                View view1122 = findViewById5;
                                final View view1222 = view722;
                                EditText editText222 = editText;
                                final View view1322 = view;
                                View view1422 = findViewById4;
                                final ? r1122 = r2;
                                Button button222 = button;
                                final View view1522 = findViewById10;
                                View view1622 = findViewById10;
                                final View view1722 = view1422;
                                AnonymousClass18 r022 = r12;
                                final Button button322 = button222;
                                View view1822 = view4;
                                final View view1922 = view1122;
                                final EditText editText322 = editText222;
                                final View view2022 = view1022;
                                final View view2122 = view822;
                                AnonymousClass18 r122 = new Runnable(this) {
                                    int a = i522;
                                    final /* synthetic */ Bank r;

                                    {
                                        this.r = r3;
                                    }

                                    public void run() {
                                        String str;
                                        boolean z = true;
                                        if (this.a == 0) {
                                            try {
                                                if (!this.r.aT && this.r.f != null && this.r.P.isShown() && this.r.f.findViewById(R.id.otp_sms) != null) {
                                                    this.r.aT = true;
                                                    textView322.setVisibility(8);
                                                    JSONObject jSONObject = new JSONObject(str222);
                                                    boolean z2 = jSONObject.has(this.r.getString(R.string.cb_regenerate)) && jSONObject.getBoolean(this.r.getString(R.string.cb_regenerate));
                                                    if (!jSONObject.has(this.r.getString(R.string.cb_pin)) || !jSONObject.getBoolean(this.r.getString(R.string.cb_pin))) {
                                                        z = false;
                                                    }
                                                    view1222.setVisibility(8);
                                                    if (z2) {
                                                        findViewById.setVisibility(0);
                                                        findViewById3.setVisibility(8);
                                                        findViewById2.setVisibility(0);
                                                    } else {
                                                        if (z) {
                                                            findViewById3.setVisibility(0);
                                                        } else {
                                                            findViewById3.setVisibility(8);
                                                        }
                                                        findViewById.setVisibility(8);
                                                        findViewById2.setVisibility(0);
                                                    }
                                                    view1322.setVisibility(8);
                                                    r1122.setVisibility(0);
                                                    view1522.setVisibility(0);
                                                    view1722.setVisibility(0);
                                                    button322.setVisibility(8);
                                                    view1922.setVisibility(8);
                                                    editText322.setVisibility(8);
                                                    view2022.setOnClickListener(this.r.aE);
                                                    view2122.setOnClickListener(this.r.aE);
                                                    r1122.setOnClickListener(this.r.aE);
                                                    this.r.updateHeight(this.r.P);
                                                }
                                            } catch (Exception e2) {
                                                e2.printStackTrace();
                                            }
                                        } else if (!editText322.hasFocus() && editText322.getText().toString().matches("")) {
                                            try {
                                                this.r.aT = false;
                                                JSONObject jSONObject2 = new JSONObject(str222);
                                                boolean z3 = jSONObject2.has(this.r.getString(R.string.cb_regenerate)) && jSONObject2.getBoolean(this.r.getString(R.string.cb_regenerate));
                                                if (!jSONObject2.has(this.r.getString(R.string.cb_pin)) || jSONObject2.getBoolean(this.r.getString(R.string.cb_pin))) {
                                                }
                                                if (this.a == i522 && z3) {
                                                    textView322.setVisibility(0);
                                                }
                                                if (VERSION.SDK_INT < 23 || this.r.am) {
                                                    textView322.setVisibility(0);
                                                    StringBuilder sb = new StringBuilder();
                                                    sb.append(this.a);
                                                    sb.append("");
                                                    str = sb.toString();
                                                } else if (this.a != 1) {
                                                    StringBuilder sb2 = new StringBuilder();
                                                    sb2.append(this.a);
                                                    sb2.append("  secs remaining to regenerate OTP\n");
                                                    str = sb2.toString();
                                                } else {
                                                    StringBuilder sb3 = new StringBuilder();
                                                    sb3.append(this.a);
                                                    sb3.append(" sec remaining to regenerate OTP\n");
                                                    str = sb3.toString();
                                                }
                                                textView322.setText(str);
                                                this.a--;
                                            } catch (Exception e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                    }
                                };
                                this.d = r022;
                                if (this.ah != null) {
                                    EditText editText4 = editText222;
                                    if (!(editText4 == null || editText4.getVisibility() == 0)) {
                                        this.N.cancelTimer(this.ai);
                                        this.f.findViewById(R.id.timer).setVisibility(8);
                                        if (this.m.equals("payment_initiated") || this.m.equals("CUSTOM_BROWSER")) {
                                            this.m = "received_otp_direct";
                                            a("otp_received", this.m);
                                        }
                                        editText4.setText(this.ah);
                                        Button button4 = button222;
                                        button4.setText(getString(R.string.cb_approve_otp));
                                        button4.setClickable(true);
                                        if (this.autoApprove) {
                                            button4.performClick();
                                            this.m = CBConstant.AUTO_APPROVE;
                                            a("user_input", this.m);
                                        }
                                        CBUtil.setAlpha(1.0f, button4);
                                        view1822.setVisibility(0);
                                        this.ar.c(view922);
                                        view1622.setVisibility(8);
                                        view1422.setVisibility(8);
                                        button4.setVisibility(0);
                                        view1122.setVisibility(8);
                                        if (this.aS) {
                                            i2 = 2;
                                            editText4.setInputType(2);
                                        } else {
                                            i2 = 2;
                                            editText4.setInputType(1);
                                        }
                                        editText4.setVisibility(0);
                                        this.aE.a(this.P);
                                        button4.setOnClickListener(this.aE);
                                        updateHeight(this.P);
                                        addReviewOrder(this.P);
                                        if (!this.K.isShown()) {
                                            this.z = i2;
                                        } else {
                                            f();
                                        }
                                    }
                                }
                                i2 = 2;
                                updateHeight(this.P);
                                addReviewOrder(this.P);
                                if (!this.K.isShown()) {
                                }
                            }
                        }
                        z = false;
                        has = has;
                        if (!z3) {
                        }
                        if (VERSION.SDK_INT >= 23) {
                        }
                    } catch (Exception e4) {
                        e = e4;
                        findViewById12 = view6;
                        r23 = r242;
                        r22 = r23;
                        e.printStackTrace();
                        r2 = r22;
                        if (VERSION.SDK_INT >= 23) {
                        }
                        i = 30;
                        View view7222 = view5;
                        View view8222 = findViewById7;
                        final int i5222 = i;
                        View view9222 = findViewById12;
                        final TextView textView3222 = textView2;
                        View view10222 = findViewById6;
                        final String str2222 = str;
                        View view11222 = findViewById5;
                        final View view12222 = view7222;
                        EditText editText2222 = editText;
                        final View view13222 = view;
                        View view14222 = findViewById4;
                        final ? r11222 = r2;
                        Button button2222 = button;
                        final View view15222 = findViewById10;
                        View view16222 = findViewById10;
                        final View view17222 = view14222;
                        AnonymousClass18 r0222 = r122;
                        final Button button3222 = button2222;
                        View view18222 = view4;
                        final View view19222 = view11222;
                        final EditText editText3222 = editText2222;
                        final View view20222 = view10222;
                        final View view21222 = view8222;
                        AnonymousClass18 r1222 = new Runnable(this) {
                            int a = i5222;
                            final /* synthetic */ Bank r;

                            {
                                this.r = r3;
                            }

                            public void run() {
                                String str;
                                boolean z = true;
                                if (this.a == 0) {
                                    try {
                                        if (!this.r.aT && this.r.f != null && this.r.P.isShown() && this.r.f.findViewById(R.id.otp_sms) != null) {
                                            this.r.aT = true;
                                            textView3222.setVisibility(8);
                                            JSONObject jSONObject = new JSONObject(str2222);
                                            boolean z2 = jSONObject.has(this.r.getString(R.string.cb_regenerate)) && jSONObject.getBoolean(this.r.getString(R.string.cb_regenerate));
                                            if (!jSONObject.has(this.r.getString(R.string.cb_pin)) || !jSONObject.getBoolean(this.r.getString(R.string.cb_pin))) {
                                                z = false;
                                            }
                                            view12222.setVisibility(8);
                                            if (z2) {
                                                findViewById.setVisibility(0);
                                                findViewById3.setVisibility(8);
                                                findViewById2.setVisibility(0);
                                            } else {
                                                if (z) {
                                                    findViewById3.setVisibility(0);
                                                } else {
                                                    findViewById3.setVisibility(8);
                                                }
                                                findViewById.setVisibility(8);
                                                findViewById2.setVisibility(0);
                                            }
                                            view13222.setVisibility(8);
                                            r11222.setVisibility(0);
                                            view15222.setVisibility(0);
                                            view17222.setVisibility(0);
                                            button3222.setVisibility(8);
                                            view19222.setVisibility(8);
                                            editText3222.setVisibility(8);
                                            view20222.setOnClickListener(this.r.aE);
                                            view21222.setOnClickListener(this.r.aE);
                                            r11222.setOnClickListener(this.r.aE);
                                            this.r.updateHeight(this.r.P);
                                        }
                                    } catch (Exception e2) {
                                        e2.printStackTrace();
                                    }
                                } else if (!editText3222.hasFocus() && editText3222.getText().toString().matches("")) {
                                    try {
                                        this.r.aT = false;
                                        JSONObject jSONObject2 = new JSONObject(str2222);
                                        boolean z3 = jSONObject2.has(this.r.getString(R.string.cb_regenerate)) && jSONObject2.getBoolean(this.r.getString(R.string.cb_regenerate));
                                        if (!jSONObject2.has(this.r.getString(R.string.cb_pin)) || jSONObject2.getBoolean(this.r.getString(R.string.cb_pin))) {
                                        }
                                        if (this.a == i5222 && z3) {
                                            textView3222.setVisibility(0);
                                        }
                                        if (VERSION.SDK_INT < 23 || this.r.am) {
                                            textView3222.setVisibility(0);
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(this.a);
                                            sb.append("");
                                            str = sb.toString();
                                        } else if (this.a != 1) {
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append(this.a);
                                            sb2.append("  secs remaining to regenerate OTP\n");
                                            str = sb2.toString();
                                        } else {
                                            StringBuilder sb3 = new StringBuilder();
                                            sb3.append(this.a);
                                            sb3.append(" sec remaining to regenerate OTP\n");
                                            str = sb3.toString();
                                        }
                                        textView3222.setText(str);
                                        this.a--;
                                    } catch (Exception e3) {
                                        e3.printStackTrace();
                                    }
                                }
                            }
                        };
                        this.d = r0222;
                        if (this.ah != null) {
                        }
                        i2 = 2;
                        updateHeight(this.P);
                        addReviewOrder(this.P);
                        if (!this.K.isShown()) {
                        }
                    }
                } catch (Exception e5) {
                    e = e5;
                    r25 = r26;
                    findViewById12 = view6;
                    r23 = r25;
                    r22 = r23;
                    e.printStackTrace();
                    r2 = r22;
                    if (VERSION.SDK_INT >= 23) {
                    }
                    i = 30;
                    View view72222 = view5;
                    View view82222 = findViewById7;
                    final int i52222 = i;
                    View view92222 = findViewById12;
                    final TextView textView32222 = textView2;
                    View view102222 = findViewById6;
                    final String str22222 = str;
                    View view112222 = findViewById5;
                    final View view122222 = view72222;
                    EditText editText22222 = editText;
                    final View view132222 = view;
                    View view142222 = findViewById4;
                    final ? r112222 = r2;
                    Button button22222 = button;
                    final View view152222 = findViewById10;
                    View view162222 = findViewById10;
                    final View view172222 = view142222;
                    AnonymousClass18 r02222 = r1222;
                    final Button button32222 = button22222;
                    View view182222 = view4;
                    final View view192222 = view112222;
                    final EditText editText32222 = editText22222;
                    final View view202222 = view102222;
                    final View view212222 = view82222;
                    AnonymousClass18 r12222 = new Runnable(this) {
                        int a = i52222;
                        final /* synthetic */ Bank r;

                        {
                            this.r = r3;
                        }

                        public void run() {
                            String str;
                            boolean z = true;
                            if (this.a == 0) {
                                try {
                                    if (!this.r.aT && this.r.f != null && this.r.P.isShown() && this.r.f.findViewById(R.id.otp_sms) != null) {
                                        this.r.aT = true;
                                        textView32222.setVisibility(8);
                                        JSONObject jSONObject = new JSONObject(str22222);
                                        boolean z2 = jSONObject.has(this.r.getString(R.string.cb_regenerate)) && jSONObject.getBoolean(this.r.getString(R.string.cb_regenerate));
                                        if (!jSONObject.has(this.r.getString(R.string.cb_pin)) || !jSONObject.getBoolean(this.r.getString(R.string.cb_pin))) {
                                            z = false;
                                        }
                                        view122222.setVisibility(8);
                                        if (z2) {
                                            findViewById.setVisibility(0);
                                            findViewById3.setVisibility(8);
                                            findViewById2.setVisibility(0);
                                        } else {
                                            if (z) {
                                                findViewById3.setVisibility(0);
                                            } else {
                                                findViewById3.setVisibility(8);
                                            }
                                            findViewById.setVisibility(8);
                                            findViewById2.setVisibility(0);
                                        }
                                        view132222.setVisibility(8);
                                        r112222.setVisibility(0);
                                        view152222.setVisibility(0);
                                        view172222.setVisibility(0);
                                        button32222.setVisibility(8);
                                        view192222.setVisibility(8);
                                        editText32222.setVisibility(8);
                                        view202222.setOnClickListener(this.r.aE);
                                        view212222.setOnClickListener(this.r.aE);
                                        r112222.setOnClickListener(this.r.aE);
                                        this.r.updateHeight(this.r.P);
                                    }
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            } else if (!editText32222.hasFocus() && editText32222.getText().toString().matches("")) {
                                try {
                                    this.r.aT = false;
                                    JSONObject jSONObject2 = new JSONObject(str22222);
                                    boolean z3 = jSONObject2.has(this.r.getString(R.string.cb_regenerate)) && jSONObject2.getBoolean(this.r.getString(R.string.cb_regenerate));
                                    if (!jSONObject2.has(this.r.getString(R.string.cb_pin)) || jSONObject2.getBoolean(this.r.getString(R.string.cb_pin))) {
                                    }
                                    if (this.a == i52222 && z3) {
                                        textView32222.setVisibility(0);
                                    }
                                    if (VERSION.SDK_INT < 23 || this.r.am) {
                                        textView32222.setVisibility(0);
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(this.a);
                                        sb.append("");
                                        str = sb.toString();
                                    } else if (this.a != 1) {
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append(this.a);
                                        sb2.append("  secs remaining to regenerate OTP\n");
                                        str = sb2.toString();
                                    } else {
                                        StringBuilder sb3 = new StringBuilder();
                                        sb3.append(this.a);
                                        sb3.append(" sec remaining to regenerate OTP\n");
                                        str = sb3.toString();
                                    }
                                    textView32222.setText(str);
                                    this.a--;
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                            }
                        }
                    };
                    this.d = r02222;
                    if (this.ah != null) {
                    }
                    i2 = 2;
                    updateHeight(this.P);
                    addReviewOrder(this.P);
                    if (!this.K.isShown()) {
                    }
                }
            } catch (Exception e6) {
                e = e6;
                r23 = r24;
                r22 = r23;
                e.printStackTrace();
                r2 = r22;
                if (VERSION.SDK_INT >= 23) {
                }
                i = 30;
                View view722222 = view5;
                View view822222 = findViewById7;
                final int i522222 = i;
                View view922222 = findViewById12;
                final TextView textView322222 = textView2;
                View view1022222 = findViewById6;
                final String str222222 = str;
                View view1122222 = findViewById5;
                final View view1222222 = view722222;
                EditText editText222222 = editText;
                final View view1322222 = view;
                View view1422222 = findViewById4;
                final ? r1122222 = r2;
                Button button222222 = button;
                final View view1522222 = findViewById10;
                View view1622222 = findViewById10;
                final View view1722222 = view1422222;
                AnonymousClass18 r022222 = r12222;
                final Button button322222 = button222222;
                View view1822222 = view4;
                final View view1922222 = view1122222;
                final EditText editText322222 = editText222222;
                final View view2022222 = view1022222;
                final View view2122222 = view822222;
                AnonymousClass18 r122222 = new Runnable(this) {
                    int a = i522222;
                    final /* synthetic */ Bank r;

                    {
                        this.r = r3;
                    }

                    public void run() {
                        String str;
                        boolean z = true;
                        if (this.a == 0) {
                            try {
                                if (!this.r.aT && this.r.f != null && this.r.P.isShown() && this.r.f.findViewById(R.id.otp_sms) != null) {
                                    this.r.aT = true;
                                    textView322222.setVisibility(8);
                                    JSONObject jSONObject = new JSONObject(str222222);
                                    boolean z2 = jSONObject.has(this.r.getString(R.string.cb_regenerate)) && jSONObject.getBoolean(this.r.getString(R.string.cb_regenerate));
                                    if (!jSONObject.has(this.r.getString(R.string.cb_pin)) || !jSONObject.getBoolean(this.r.getString(R.string.cb_pin))) {
                                        z = false;
                                    }
                                    view1222222.setVisibility(8);
                                    if (z2) {
                                        findViewById.setVisibility(0);
                                        findViewById3.setVisibility(8);
                                        findViewById2.setVisibility(0);
                                    } else {
                                        if (z) {
                                            findViewById3.setVisibility(0);
                                        } else {
                                            findViewById3.setVisibility(8);
                                        }
                                        findViewById.setVisibility(8);
                                        findViewById2.setVisibility(0);
                                    }
                                    view1322222.setVisibility(8);
                                    r1122222.setVisibility(0);
                                    view1522222.setVisibility(0);
                                    view1722222.setVisibility(0);
                                    button322222.setVisibility(8);
                                    view1922222.setVisibility(8);
                                    editText322222.setVisibility(8);
                                    view2022222.setOnClickListener(this.r.aE);
                                    view2122222.setOnClickListener(this.r.aE);
                                    r1122222.setOnClickListener(this.r.aE);
                                    this.r.updateHeight(this.r.P);
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        } else if (!editText322222.hasFocus() && editText322222.getText().toString().matches("")) {
                            try {
                                this.r.aT = false;
                                JSONObject jSONObject2 = new JSONObject(str222222);
                                boolean z3 = jSONObject2.has(this.r.getString(R.string.cb_regenerate)) && jSONObject2.getBoolean(this.r.getString(R.string.cb_regenerate));
                                if (!jSONObject2.has(this.r.getString(R.string.cb_pin)) || jSONObject2.getBoolean(this.r.getString(R.string.cb_pin))) {
                                }
                                if (this.a == i522222 && z3) {
                                    textView322222.setVisibility(0);
                                }
                                if (VERSION.SDK_INT < 23 || this.r.am) {
                                    textView322222.setVisibility(0);
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(this.a);
                                    sb.append("");
                                    str = sb.toString();
                                } else if (this.a != 1) {
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(this.a);
                                    sb2.append("  secs remaining to regenerate OTP\n");
                                    str = sb2.toString();
                                } else {
                                    StringBuilder sb3 = new StringBuilder();
                                    sb3.append(this.a);
                                    sb3.append(" sec remaining to regenerate OTP\n");
                                    str = sb3.toString();
                                }
                                textView322222.setText(str);
                                this.a--;
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                        }
                    }
                };
                this.d = r022222;
                if (this.ah != null) {
                }
                i2 = 2;
                updateHeight(this.P);
                addReviewOrder(this.P);
                if (!this.K.isShown()) {
                }
            }
            if (VERSION.SDK_INT >= 23) {
            }
            i = 30;
            View view7222222 = view5;
            View view8222222 = findViewById7;
            final int i5222222 = i;
            View view9222222 = findViewById12;
            final TextView textView3222222 = textView2;
            View view10222222 = findViewById6;
            final String str2222222 = str;
            View view11222222 = findViewById5;
            final View view12222222 = view7222222;
            EditText editText2222222 = editText;
            final View view13222222 = view;
            View view14222222 = findViewById4;
            final ? r11222222 = r2;
            Button button2222222 = button;
            final View view15222222 = findViewById10;
            View view16222222 = findViewById10;
            final View view17222222 = view14222222;
            AnonymousClass18 r0222222 = r122222;
            final Button button3222222 = button2222222;
            View view18222222 = view4;
            final View view19222222 = view11222222;
            final EditText editText3222222 = editText2222222;
            final View view20222222 = view10222222;
            final View view21222222 = view8222222;
            AnonymousClass18 r1222222 = new Runnable(this) {
                int a = i5222222;
                final /* synthetic */ Bank r;

                {
                    this.r = r3;
                }

                public void run() {
                    String str;
                    boolean z = true;
                    if (this.a == 0) {
                        try {
                            if (!this.r.aT && this.r.f != null && this.r.P.isShown() && this.r.f.findViewById(R.id.otp_sms) != null) {
                                this.r.aT = true;
                                textView3222222.setVisibility(8);
                                JSONObject jSONObject = new JSONObject(str2222222);
                                boolean z2 = jSONObject.has(this.r.getString(R.string.cb_regenerate)) && jSONObject.getBoolean(this.r.getString(R.string.cb_regenerate));
                                if (!jSONObject.has(this.r.getString(R.string.cb_pin)) || !jSONObject.getBoolean(this.r.getString(R.string.cb_pin))) {
                                    z = false;
                                }
                                view12222222.setVisibility(8);
                                if (z2) {
                                    findViewById.setVisibility(0);
                                    findViewById3.setVisibility(8);
                                    findViewById2.setVisibility(0);
                                } else {
                                    if (z) {
                                        findViewById3.setVisibility(0);
                                    } else {
                                        findViewById3.setVisibility(8);
                                    }
                                    findViewById.setVisibility(8);
                                    findViewById2.setVisibility(0);
                                }
                                view13222222.setVisibility(8);
                                r11222222.setVisibility(0);
                                view15222222.setVisibility(0);
                                view17222222.setVisibility(0);
                                button3222222.setVisibility(8);
                                view19222222.setVisibility(8);
                                editText3222222.setVisibility(8);
                                view20222222.setOnClickListener(this.r.aE);
                                view21222222.setOnClickListener(this.r.aE);
                                r11222222.setOnClickListener(this.r.aE);
                                this.r.updateHeight(this.r.P);
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    } else if (!editText3222222.hasFocus() && editText3222222.getText().toString().matches("")) {
                        try {
                            this.r.aT = false;
                            JSONObject jSONObject2 = new JSONObject(str2222222);
                            boolean z3 = jSONObject2.has(this.r.getString(R.string.cb_regenerate)) && jSONObject2.getBoolean(this.r.getString(R.string.cb_regenerate));
                            if (!jSONObject2.has(this.r.getString(R.string.cb_pin)) || jSONObject2.getBoolean(this.r.getString(R.string.cb_pin))) {
                            }
                            if (this.a == i5222222 && z3) {
                                textView3222222.setVisibility(0);
                            }
                            if (VERSION.SDK_INT < 23 || this.r.am) {
                                textView3222222.setVisibility(0);
                                StringBuilder sb = new StringBuilder();
                                sb.append(this.a);
                                sb.append("");
                                str = sb.toString();
                            } else if (this.a != 1) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(this.a);
                                sb2.append("  secs remaining to regenerate OTP\n");
                                str = sb2.toString();
                            } else {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(this.a);
                                sb3.append(" sec remaining to regenerate OTP\n");
                                str = sb3.toString();
                            }
                            textView3222222.setText(str);
                            this.a--;
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                }
            };
            this.d = r0222222;
            if (this.ah != null) {
            }
            i2 = 2;
            updateHeight(this.P);
            addReviewOrder(this.P);
            if (!this.K.isShown()) {
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int b(String str) {
        if (str.equalsIgnoreCase(getString(R.string.cb_pin))) {
            return 3;
        }
        if (str.equalsIgnoreCase(getString(R.string.cb_password))) {
            return 1;
        }
        if (str.equalsIgnoreCase(getString(R.string.cb_enter_manually))) {
            return 4;
        }
        if (str.equalsIgnoreCase(getString(R.string.cb_approve_otp))) {
            return 5;
        }
        if (str.equalsIgnoreCase(getString(R.string.cb_otp)) || str.equalsIgnoreCase(getString(R.string.cb_use_sms_otp))) {
            return 6;
        }
        if (str.equalsIgnoreCase(getString(R.string.cb_sms_otp))) {
            return 7;
        }
        if (str.equalsIgnoreCase(getString(R.string.cb_regenerate_otp))) {
            return 2;
        }
        return 0;
    }

    private void v() {
        AnonymousClass19 r0 = new CountDownTimer((long) this.snoozeUrlLoadingTimeout, 500) {
            public void onTick(long l) {
                Bank.this.ay = true;
            }

            public void onFinish() {
                Bank.this.ay = false;
                if (Bank.this.s.getProgress() < Bank.this.snoozeUrlLoadingPercentage && !Bank.this.n && Bank.this.aL && !Bank.this.getTransactionStatusReceived()) {
                    Bank.this.launchSnoozeWindow();
                }
                Bank.this.w();
            }
        };
        this.ax = r0;
        this.ax.start();
    }

    /* access modifiers changed from: private */
    public void w() {
        CountDownTimer countDownTimer = this.ax;
        if (countDownTimer != null) {
            this.ay = false;
            countDownTimer.cancel();
            this.ax = null;
        }
    }

    public void launchSnoozeWindow() {
        launchSnoozeWindow(1);
    }

    public void launchSnoozeWindow(int mode) {
        boolean z;
        final int i = mode;
        if (this.av != 3) {
            if (i != 2 || this.av != 2) {
                if (i != 1 || this.av != 1) {
                    showCbBlankOverlay(8);
                    if (this.backwardJourneyStarted) {
                        if (this.snoozeCountBackwardJourney >= this.customBrowserConfig.getEnableSurePay() || (this.isS2SHtmlSupport && (TextUtils.isEmpty(this.surePayS2SPayUId) || TextUtils.isEmpty(this.surePayS2Surl)))) {
                            return;
                        }
                    } else if (this.snoozeCount >= this.customBrowserConfig.getEnableSurePay() || (this.isS2SHtmlSupport && TextUtils.isEmpty(this.surePayS2Surl))) {
                        return;
                    }
                    this.snoozeMode = i;
                    if (this.f != null && !this.f.isFinishing()) {
                        dismissSlowUserWarning();
                        a(8, "");
                        this.n = true;
                        a("snooze_window_status", "snooze_visible");
                        a("snooze_appear_url", this.B);
                        a("snooze_window_launch_mode", i == 1 ? CBConstant.STR_SNOOZE_MODE_WARN : CBConstant.STR_SNOOZE_MODE_FAIL);
                        a("snooze_window_appear_time", "-1");
                        this.aB = this.f.getLayoutInflater().inflate(R.layout.cb_layout_snooze, null);
                        TextView textView = (TextView) this.aB.findViewById(R.id.text_view_snooze_message);
                        TextView textView2 = (TextView) this.aB.findViewById(R.id.text_view_transaction_snoozed_message1);
                        TextView textView3 = (TextView) this.aB.findViewById(R.id.button_cancel_transaction);
                        Button button = (Button) this.aB.findViewById(R.id.button_snooze_transaction);
                        Button button2 = (Button) this.aB.findViewById(R.id.button_retry_transaction);
                        TextView textView4 = (TextView) this.aB.findViewById(R.id.text_view_cancel_snooze_window);
                        final TextView textView5 = (TextView) this.aB.findViewById(R.id.t_confirm);
                        TextView textView6 = (TextView) this.aB.findViewById(R.id.t_nconfirm);
                        TextView textView7 = (TextView) this.aB.findViewById(R.id.snooze_header_txt);
                        TextView textView8 = (TextView) this.aB.findViewById(R.id.text_view_retry_message_detail);
                        Button button3 = (Button) this.aB.findViewById(R.id.button_retry_anyway);
                        this.aA = (SnoozeLoaderView) this.aB.findViewById(R.id.snooze_loader_view);
                        this.aA.setVisibility(8);
                        textView4.setVisibility(0);
                        textView3.setVisibility(0);
                        button.setVisibility(0);
                        button2.setVisibility(0);
                        textView.setVisibility(0);
                        textView2.setVisibility(8);
                        textView8.setVisibility(0);
                        textView5.setVisibility(8);
                        textView6.setVisibility(8);
                        button3.setVisibility(8);
                        textView.setText(this.f.getString(R.string.cb_slownetwork_status));
                        textView7.setText(this.f.getString(R.string.cb_try_later));
                        textView8.setText(this.f.getString(R.string.cb_retry_restart));
                        if (!this.backwardJourneyStarted || !this.T) {
                            this.snoozeVisibleCountFwdJourney++;
                        } else {
                            textView.setText(this.f.getResources().getString(R.string.cb_slow_internet_confirmation));
                            textView2.setText(this.f.getResources().getString(R.string.cb_receive_sms));
                            textView7.setText(this.f.getResources().getString(R.string.cb_confirm_transaction));
                            button.setVisibility(8);
                            textView8.setVisibility(8);
                            button2.setVisibility(8);
                            textView3.setVisibility(8);
                            textView.setVisibility(0);
                            textView2.setVisibility(0);
                            textView5.setVisibility(0);
                            textView6.setVisibility(0);
                            button3.setVisibility(8);
                            this.snoozeVisibleCountBackwdJourney++;
                            a("snooze_backward_visible", "Y");
                        }
                        Button button4 = button3;
                        AnonymousClass20 r13 = r0;
                        final TextView textView9 = textView7;
                        TextView textView10 = textView8;
                        final TextView textView11 = textView;
                        TextView textView12 = textView7;
                        final Button button5 = button;
                        TextView textView13 = textView6;
                        final TextView textView14 = textView2;
                        TextView textView15 = textView5;
                        TextView textView16 = textView2;
                        TextView textView17 = textView4;
                        final TextView textView18 = textView13;
                        AnonymousClass20 r0 = new OnClickListener() {
                            public void onClick(View v) {
                                Bank.this.a(com.payu.custombrowser.util.a.a, "confirm_deduction_y");
                                if (Bank.this.ai != null) {
                                    Bank.this.ai.cancel();
                                    Bank.this.ai.purge();
                                }
                                Bank.this.snoozeCountBackwardJourney++;
                                Bank.this.k.setCanceledOnTouchOutside(false);
                                textView9.setText(Bank.this.f.getResources().getString(R.string.cb_confirm_transaction));
                                textView11.setText(Bank.this.f.getString(R.string.cb_transaction_status));
                                Bank.this.aA.setVisibility(0);
                                Bank.this.aA.a();
                                button5.setVisibility(8);
                                textView14.setVisibility(8);
                                textView5.setVisibility(8);
                                textView18.setVisibility(8);
                                if (Bank.this.S) {
                                    Bank bank = Bank.this;
                                    bank.startSnoozeServiceVerifyPayment(bank.f.getResources().getString(R.string.cb_verify_message_received));
                                    return;
                                }
                                Bank bank2 = Bank.this;
                                bank2.startSnoozeServiceVerifyPayment(bank2.f.getResources().getString(R.string.cb_user_input_confirm_transaction));
                            }
                        };
                        textView15.setOnClickListener(r13);
                        textView13.setOnClickListener(new OnClickListener() {
                            public void onClick(View v) {
                                Bank.this.snoozeCountBackwardJourney++;
                                Bank.this.dismissSnoozeWindow();
                                Bank.this.a(com.payu.custombrowser.util.a.a, "confirm_deduction_n");
                            }
                        });
                        textView17.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                if (Bank.this.backwardJourneyStarted) {
                                    Bank.this.snoozeCountBackwardJourney++;
                                } else {
                                    Bank.this.snoozeCount++;
                                }
                                Bank.this.a("snooze_interaction_time", "-1");
                                if (!Bank.this.backwardJourneyStarted) {
                                    Bank.this.a("snooze_window_action", "snooze_cancel_window_click");
                                }
                                if (i == 2) {
                                    Bank.this.killSnoozeService();
                                }
                                Bank.this.dismissSnoozeWindow();
                            }
                        });
                        button2.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                Bank.this.i();
                                Bank.this.c(view);
                            }
                        });
                        final TextView textView19 = textView17;
                        final TextView textView20 = textView3;
                        final Button button6 = button2;
                        final TextView textView21 = textView;
                        final TextView textView22 = textView10;
                        final TextView textView23 = textView12;
                        final TextView textView24 = textView16;
                        final Button button7 = button4;
                        AnonymousClass25 r02 = new OnClickListener() {
                            public void onClick(View view) {
                                Bank bank = Bank.this;
                                bank.isRetryNowPressed = true;
                                bank.snoozeCount++;
                                Bank.this.a("snooze_interaction_time", "-1");
                                Bank.this.f();
                                Bank bank2 = Bank.this;
                                bank2.z = 1;
                                if (bank2.L != null) {
                                    Bank.this.L.setVisibility(8);
                                }
                                Bank.this.onHelpUnavailable();
                                Bank.this.snoozeClickedTime = System.currentTimeMillis();
                                Bank bank3 = Bank.this;
                                bank3.isSnoozeBroadCastReceiverRegistered = true;
                                bank3.az = true;
                                Bank.this.s.stopLoading();
                                if (!(b.SINGLETON == null || b.SINGLETON.getPayuCustomBrowserCallback() == null)) {
                                    b.hasToStart = true;
                                    Bank.this.bindService();
                                }
                                Bank bank4 = Bank.this;
                                bank4.ah = null;
                                bank4.unregisterBroadcast(bank4.g);
                                textView19.setVisibility(8);
                                textView20.setVisibility(8);
                                button5.setVisibility(8);
                                button6.setVisibility(8);
                                textView21.setText("We have paused your transaction because the network was unable to process it now. We will notify you when the network improves.");
                                textView22.setVisibility(8);
                                textView23.setText(Bank.this.f.getResources().getString(R.string.cb_transaction_paused));
                                textView24.setVisibility(0);
                                button7.setVisibility(0);
                                Bank.this.a(8, "");
                                Bank.this.a("snooze_window_action", "snooze_click");
                                Bank bank5 = Bank.this;
                                bank5.a("snooze_load_url", bank5.s.getUrl() == null ? Bank.this.B : Bank.this.s.getUrl());
                                Bank bank6 = Bank.this;
                                bank6.slowUserCountDownTimer = null;
                                bank6.showCbBlankOverlay(0);
                            }
                        };
                        button.setOnClickListener(r02);
                        textView3.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                if (Bank.this.backwardJourneyStarted) {
                                    Bank.this.snoozeCountBackwardJourney++;
                                } else {
                                    Bank.this.snoozeCount++;
                                }
                                Bank.this.a("snooze_interaction_time", "-1");
                                Bank.this.a("snooze_window_action", "snooze_cancel_transaction_click");
                                Bank.this.showBackButtonDialog();
                            }
                        });
                        button4.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                Bank.this.i();
                                Bank.this.c(view);
                            }
                        });
                        if (this.k == null || !this.k.isShowing()) {
                            this.k = new Builder(this.f).create();
                            this.k.setView(this.aB);
                            z = false;
                            this.k.setCanceledOnTouchOutside(false);
                            this.k.setOnDismissListener(new OnDismissListener() {
                                public void onDismiss(DialogInterface dialogInterface) {
                                    Bank.this.showCbBlankOverlay(8);
                                }
                            });
                            this.k.setOnKeyListener(new OnKeyListener() {
                                public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                                    if (keyCode == 4 && event.getAction() == 0) {
                                        Bank.this.a("user_input", "payu_back_button".toLowerCase());
                                        Bank.this.showBackButtonDialog();
                                    }
                                    return true;
                                }
                            });
                        } else {
                            z = false;
                        }
                        dismissReviewOrder();
                        this.k.show();
                        if (i == 2 && !this.backwardJourneyStarted) {
                            hasToStart = z;
                            bindService();
                        }
                    }
                }
            }
        }
    }

    public void bindService() {
        LocalBroadcastManager.getInstance(this.f).unregisterReceiver(this.snoozeBroadCastReceiver);
        LocalBroadcastManager.getInstance(this.f.getApplicationContext()).registerReceiver(this.snoozeBroadCastReceiver, new IntentFilter(this.SNOOZE_GET_WEBVIEW_STATUS_INTENT_ACTION));
        Intent intent = new Intent(this.f, SnoozeService.class);
        intent.putExtra(CBConstant.CB_CONFIG, this.customBrowserConfig);
        intent.putExtra(CBConstant.CURRENT_URL, this.B);
        intent.putExtra(CBConstant.MERCHANT_CHECKOUT_ACTIVITY, this.customBrowserConfig.getMerchantCheckoutActivityPath());
        if (!TextUtils.isEmpty(this.surePayS2Surl)) {
            intent.putExtra(CBConstant.S2S_RETRY_URL, this.surePayS2Surl);
        }
        this.isSnoozeServiceBounded = true;
        this.f.bindService(intent, this.snoozeServiceConnection, 1);
        this.f.startService(intent);
    }

    public void startSnoozeServiceVerifyPayment(String verifyParam) {
        LocalBroadcastManager.getInstance(this.f).unregisterReceiver(this.snoozeBroadCastReceiver);
        LocalBroadcastManager.getInstance(this.f.getApplicationContext()).registerReceiver(this.snoozeBroadCastReceiver, new IntentFilter(this.SNOOZE_GET_WEBVIEW_STATUS_INTENT_ACTION));
        Intent intent = new Intent(this.f, SnoozeService.class);
        intent.putExtra(CBConstant.CB_CONFIG, this.customBrowserConfig);
        intent.putExtra(CBConstant.VERIFICATION_MSG_RECEIVED, true);
        intent.putExtra(CBConstant.MERCHANT_CHECKOUT_ACTIVITY, this.customBrowserConfig.getMerchantCheckoutActivityPath());
        intent.putExtra(CBConstant.VERIFY_ADDON_PARAMS, verifyParam);
        if (!TextUtils.isEmpty(this.surePayS2SPayUId)) {
            intent.putExtra("PAYUID", this.surePayS2SPayUId);
        }
        if (!TextUtils.isEmpty(this.merchantKey)) {
            intent.putExtra("merchantKey", this.merchantKey);
        }
        if (!TextUtils.isEmpty(this.txnId)) {
            intent.putExtra("txnid", this.txnId);
        }
        this.isSnoozeServiceBounded = true;
        this.f.bindService(intent, this.snoozeServiceConnection, 1);
        this.isSnoozeBroadCastReceiverRegistered = true;
        this.f.startService(intent);
    }

    public void dismissSnoozeWindow() {
        this.n = false;
        showReviewOrderHorizontalBar();
        if (this.k != null) {
            this.k.dismiss();
            this.k.cancel();
            showCbBlankOverlay(8);
        }
    }

    public void setMagicRetry(Map<String, String> urlList) {
        if (this.p != null) {
            this.p.setUrlListWithPostData(urlList);
        }
    }

    public void initMagicRetry() {
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        this.p = new MagicRetryFragment();
        Bundle bundle = new Bundle();
        if (!(b.SINGLETON == null || b.SINGLETON.getPayuCustomBrowserCallback() == null)) {
            bundle.putString(MagicRetryFragment.KEY_TXNID, this.customBrowserConfig.getTransactionID());
        }
        this.p.setArguments(bundle);
        supportFragmentManager.beginTransaction().add(R.id.magic_retry_container, this.p, "magicRetry").commit();
        toggleFragmentVisibility(0);
        this.p.isWhiteListingEnabled(true);
        this.p.setWebView(this.s);
        this.p.initMRSettingsFromSharedPreference(this.f);
        if (this.customBrowserConfig.getEnableSurePay() > 0) {
            this.s.setWebViewClient(new PayUSurePayWebViewClient(this, keyAnalytics));
        } else {
            this.s.setWebViewClient(new PayUWebViewClient(this, this.p, keyAnalytics));
        }
    }

    public void toggleFragmentVisibility(int flag) {
        if (getActivity() != null && !getActivity().isFinishing() && isAdded() && !isRemoving()) {
            FragmentTransaction beginTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            if (this.p != null && flag == 1) {
                beginTransaction.show(this.p).commitAllowingStateLoss();
            } else if (this.p != null && flag == 0) {
                beginTransaction.hide(this.p).commitAllowingStateLoss();
            }
        }
    }

    public void showMagicRetry() {
        dismissSnoozeWindow();
        toggleFragmentVisibility(1);
    }

    public void hideMagicRetry() {
        toggleFragmentVisibility(0);
    }

    public void showBackButtonDialog() {
        if (this.f != null && !this.f.isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.f, R.style.cb_dialog);
            builder.setCancelable(false);
            builder.setMessage("Do you really want to cancel the transaction ?");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Bank.this.postToPaytxn();
                    if (Bank.this.k != null && Bank.this.k.isShowing()) {
                        Bank.this.k.cancel();
                    }
                    Bank.this.killSnoozeService();
                    Bank.this.cancelTransactionNotification();
                    Bank.this.a("user_input", "back_button_ok");
                    Bank.this.dismissSnoozeWindow();
                    if (!(b.SINGLETON == null || b.SINGLETON.getPayuCustomBrowserCallback() == null)) {
                        b.SINGLETON.getPayuCustomBrowserCallback().onBackApprove();
                    }
                    Bank.this.f.finish();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Bank.this.a("user_input", "back_button_cancel");
                    dialog.dismiss();
                    if (b.SINGLETON != null && b.SINGLETON.getPayuCustomBrowserCallback() != null) {
                        b.SINGLETON.getPayuCustomBrowserCallback().onBackDismiss();
                    }
                }
            });
            if (!(b.SINGLETON == null || b.SINGLETON.getPayuCustomBrowserCallback() == null)) {
                b.SINGLETON.getPayuCustomBrowserCallback().onBackButton(builder);
            }
            this.aR = builder.create();
            this.aR.getWindow().getAttributes().type = 2003;
            this.aR = builder.show();
        }
    }

    public void setIsPageStoppedForcefully(boolean isPageStoppedForcefully) {
        this.az = isPageStoppedForcefully;
    }

    private void a(List<String> list) {
        aw.clear();
        StringBuilder sb = new StringBuilder();
        sb.append("MR Cleared whitelisted urls, length: ");
        sb.append(aw.size());
        c.a("#### PAYU", sb.toString());
        aw.addAll(list);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("MR Updated whitelisted urls, length: ");
        sb2.append(aw.size());
        c.a("#### PAYU", sb2.toString());
    }

    @JavascriptInterface
    public void setSnoozeConfig(String snoozeConfig) {
        this.at = this.N.storeSnoozeConfigInSharedPref(this.f.getApplicationContext(), snoozeConfig);
    }

    @JavascriptInterface
    public void dismissPayULoader() {
        if (this.f != null && !this.f.isFinishing() && this.w != null) {
            this.w.dismiss();
            this.w.cancel();
            if (!this.aO) {
                this.forwardJourneyForChromeLoaderIsComplete = true;
                StringBuilder sb = new StringBuilder();
                sb.append("Setting forwardJourneyForChromeLoaderIsComplete = ");
                sb.append(this.forwardJourneyForChromeLoaderIsComplete);
                c.a("stag", sb.toString());
                startSlowUserWarningTimer();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void startSlowUserWarningTimer() {
        c.a("sTag", "Attempting to start slowUserCountDownTimer");
        if (this.slowUserCountDownTimer == null) {
            c.a("sTag", "Starting slowUserCountDownTimer");
        }
    }

    /* access modifiers changed from: protected */
    public void dismissSlowUserWarningTimer() {
        if (this.slowUserCountDownTimer != null) {
            c.a("sTag", "Shutting down slowUserCountDownTimer");
            this.slowUserCountDownTimer.cancel();
        }
    }

    public void reloadWVUsingJS() {
        this.s.loadUrl("javascript:window.location.reload(true)");
    }

    public void reloadWVNative() {
        this.s.reload();
    }

    public void reloadWVUsingJSFromCache() {
        this.s.loadUrl("javascript:window.location.reload()");
    }

    /* access modifiers changed from: private */
    public void c(View view) {
        if (view.getId() == R.id.button_retry_transaction) {
            this.snoozeCount++;
            a("snooze_interaction_time", "-1");
            a("snooze_window_action", "snooze_retry_click");
        } else if (view.getId() == R.id.button_retry_anyway) {
            this.snoozeCount++;
            a("snooze_txn_paused_user_interaction_time", "-1");
            a("snooze_txn_paused_window_action", "retry_anyway_click");
        }
        setTransactionStatusReceived(false);
        if (CBUtil.isNetworkAvailable(this.f.getApplicationContext())) {
            if (this.s.getUrl() == null || this.s.getUrl().contentEquals("https://secure.payu.in/_payment") || this.s.getUrl().contentEquals(CBConstant.PRODUCTION_PAYMENT_URL_SEAMLESS) || !isUrlWhiteListed(this.s.getUrl())) {
                this.N.clearCookie();
                if ((this.customBrowserConfig.getPostURL() != null && (this.customBrowserConfig.getPostURL().contentEquals("https://secure.payu.in/_payment") || this.customBrowserConfig.getPostURL().contentEquals("https://mobiletest.payu.in/_payment"))) || (this.isS2SHtmlSupport && !TextUtils.isEmpty(this.surePayS2Surl) && !TextUtils.isEmpty(this.surePayS2SPayUId))) {
                    CBUtil cBUtil = this.N;
                    markPreviousTxnAsUserCanceled(CBUtil.getLogMessage(this.f.getApplicationContext(), "sure_pay_cancelled", this.customBrowserConfig.getTransactionID(), "", keyAnalytics, this.customBrowserConfig.getTransactionID(), ""));
                }
                if (this.customBrowserConfig.getPostURL() != null && this.customBrowserConfig.getPayuPostData() != null && this.surePayS2Surl == null) {
                    reloadWebView(this.customBrowserConfig.getPostURL(), this.customBrowserConfig.getPayuPostData());
                } else if (this.surePayS2Surl != null) {
                    reloadWebView(this.surePayS2Surl, null);
                }
            } else {
                reloadWebView();
            }
            dismissSnoozeWindow();
            this.slowUserCountDownTimer = null;
            if (view.getId() == R.id.button_retry_anyway) {
                killSnoozeService();
                ((NotificationManager) this.f.getSystemService("notification")).cancel(CBConstant.SNOOZE_NOTIFICATION_ID);
                return;
            }
            return;
        }
        Toast.makeText(this.f.getApplicationContext(), CBConstant.MSG_NO_INTERNET, 0).show();
    }

    @JavascriptInterface
    public void spResumedWindowTTL(String ttl) {
        try {
            this.au = Integer.parseInt(ttl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void a(boolean z) {
        this.aQ = z;
    }

    public void addReviewOrder(View view) {
        setReviewOrderButtonProperty((TextView) view.findViewById(R.id.t_payu_review_option));
    }

    @JavascriptInterface
    public void logPayUAnalytics(String event) {
        try {
            JSONObject jSONObject = new JSONObject(event);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                if (this.mAnalyticsMap.get(str) != null) {
                    if (!((String) this.mAnalyticsMap.get(str)).contentEquals(jSONObject.get(str).toString())) {
                    }
                }
                this.mAnalyticsMap.put(str, jSONObject.get(str).toString());
                a(str, jSONObject.get(str).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void b(String str, String str2) {
        String str3;
        int i = 0;
        while (str2.length() > 0) {
            try {
                i++;
                if (str2.length() > 128) {
                    String substring = str2.substring(0, 128);
                    str3 = str2.substring(128, str2.length());
                    str2 = substring;
                } else {
                    str3 = "";
                }
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("_");
                sb.append(i);
                a(sb.toString(), str2);
                str2 = str3;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        AlertDialog alertDialog = this.aR;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.aR = null;
        }
    }

    @JavascriptInterface
    public void isOTPKeyboardNumeric(boolean isOTPKeyboardNumeric) {
        this.aS = isOTPKeyboardNumeric;
    }

    @JavascriptInterface
    public void setJSData(String key, String value, boolean isPersistent) {
        if (isPersistent) {
            this.N.setStringSharedPreference(this.f, key, value);
        } else {
            this.N.setJSStringSharedPreference(this.f, key, value);
        }
    }

    @JavascriptInterface
    public void setJSData(String key, String value) {
        setJSData(key, value, false);
    }

    @JavascriptInterface
    public String getJSData(String key) {
        return getJSData(key, false);
    }

    @JavascriptInterface
    public String getJSData(String key, boolean isPersistent) {
        return this.N.getJSStringSharedPreference(this.f, key, isPersistent);
    }

    @JavascriptInterface
    public void removeJSData(String key, boolean isPersistent) {
        if (isPersistent) {
            this.N.removeFromSharedPreferences(this.f, key);
        } else {
            this.N.removeJSStringSharedPreference(this.f, key);
        }
    }

    @JavascriptInterface
    public void removeJSData(String key) {
        removeJSData(key, false);
    }
}
