package com.payu.custombrowser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog.Builder;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.load.Key;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.Status;
import com.payu.custombrowser.a.C0001a;
import com.payu.custombrowser.b.c;
import com.payu.custombrowser.bean.CustomBrowserConfig;
import com.payu.custombrowser.services.SnoozeService;
import com.payu.custombrowser.util.CBConstant;
import com.payu.custombrowser.util.CBUtil;
import com.payu.custombrowser.util.SnoozeConfigMap;
import com.payu.custombrowser.util.f;
import com.payu.custombrowser.widgets.SnoozeLoaderView;
import com.payumoney.core.PayUmoneyConstants;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class b extends a implements c {
    private static boolean a;
    public static boolean hasToStart = false;
    public static int snoozeImageDownloadTimeout;
    protected static List<String> whiteListedUrls = new ArrayList();
    protected String SNOOZE_GET_WEBVIEW_STATUS_INTENT_ACTION = "webview_status_action";
    View ab;
    boolean ac;
    Intent ad;
    boolean ae;
    int af;
    boolean ag = true;
    String ah;
    Timer ai;
    Boolean aj = Boolean.valueOf(false);
    boolean ak;
    String al;
    boolean am = true;
    boolean an = false;
    boolean ao;
    boolean ap = false;
    boolean aq = false;
    com.payu.custombrowser.custombar.a ar;
    int[] as;
    SnoozeConfigMap at;
    int au = 0;
    int av = 0;
    /* access modifiers changed from: private */
    public String b = "snooze_broad_cast_message";
    protected boolean isRetryNowPressed = false;
    public boolean isS2SHtmlSupport = false;
    protected boolean isSnoozeBroadCastReceiverRegistered = false;
    protected boolean isSnoozeEnabled = true;
    protected boolean isSnoozeServiceBounded = false;
    protected HashMap<String, String> mAnalyticsMap;
    protected CountDownTimer slowUserCountDownTimer;
    protected AlertDialog slowUserWarningDialog;
    protected BroadcastReceiver snoozeBroadCastReceiver;
    protected int snoozeCount = 0;
    protected int snoozeCountBackwardJourney = 0;
    protected SnoozeService snoozeService;
    protected ServiceConnection snoozeServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            com.payu.custombrowser.services.SnoozeService.b bVar = (com.payu.custombrowser.services.SnoozeService.b) iBinder;
            b.this.snoozeService = bVar.a();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            b.this.snoozeService = null;
        }
    };
    protected int snoozeUrlLoadingPercentage;
    protected int snoozeUrlLoadingTimeout;
    protected int snoozeVisibleCountBackwdJourney;
    protected int snoozeVisibleCountFwdJourney;

    public class a extends C0001a {
        public a() {
            super();
        }

        public boolean onTouch(View v, MotionEvent event) {
            com.payu.custombrowser.util.c.a("sTag", "onTouch of PayUCBLifeCycleCalled");
            b.this.q();
            return super.onTouch(v, event);
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void a(String str);

    /* access modifiers changed from: 0000 */
    public abstract void b();

    /* access modifiers changed from: 0000 */
    public abstract void dismissSlowUserWarningTimer();

    /* access modifiers changed from: 0000 */
    public abstract void onPageStarted();

    /* access modifiers changed from: 0000 */
    public abstract void reloadWebView();

    /* access modifiers changed from: 0000 */
    public abstract void reloadWebView(String str);

    /* access modifiers changed from: 0000 */
    public abstract void reloadWebView(String str, String str2);

    /* access modifiers changed from: 0000 */
    public abstract void startSlowUserWarningTimer();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.N.resetPayuID();
        this.surePayS2SPayUId = null;
        this.isSnoozeEnabled = this.N.getBooleanSharedPreferenceDefaultTrue(CBConstant.SNOOZE_ENABLED, getActivity().getApplicationContext());
        a = false;
        this.at = this.N.convertToSnoozeConfigMap(f.a(this.f, CBConstant.SNOOZE_SHARED_PREF));
        this.as = this.at.getPercentageAndTimeout(CBConstant.DEFAULT_PAYMENT_URLS);
        int[] iArr = this.as;
        this.snoozeUrlLoadingPercentage = iArr[0];
        this.snoozeUrlLoadingTimeout = iArr[1];
        this.av = this.N.getSurePayDisableStatus(this.at, CBConstant.DEFAULT_PAYMENT_URLS);
        whiteListedUrls = CBUtil.processAndAddWhiteListedUrls(f.b((Context) this.f, CBConstant.SP_RETRY_FILE_NAME, CBConstant.SP_RETRY_WHITELISTED_URLS, ""));
        snoozeImageDownloadTimeout = f.b(this.f.getApplicationContext(), CBUtil.CB_PREFERENCE, CBConstant.SNOOZE_IMAGE_DOWNLOAD_TIME_OUT, 0);
        SnoozeService snoozeService2 = this.snoozeService;
        if (snoozeService2 != null) {
            snoozeService2.a();
        }
        if (this.f.getIntent().getStringExtra(CBConstant.SENDER) != null && this.f.getIntent().getStringExtra(CBConstant.SENDER).contentEquals(CBConstant.SNOOZE_SERVICE)) {
            a = true;
        }
        this.snoozeBroadCastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (context != null && b.this.f != null && !b.this.f.isFinishing()) {
                    if (intent.hasExtra("broadcaststatus")) {
                        Intent intent2 = new Intent(context.getApplicationContext(), CBActivity.class);
                        intent2.putExtra(CBConstant.SENDER, CBConstant.SNOOZE_SERVICE);
                        intent2.putExtra(CBConstant.VERIFICATION_MSG_RECEIVED, true);
                        intent2.putExtra(CBConstant.PAYU_RESPONSE, intent.getExtras().getString(CBConstant.PAYU_RESPONSE));
                        intent2.setFlags(805306368);
                        context.startActivity(intent2);
                    }
                    if (intent.hasExtra(b.this.b) && b.this.snoozeService != null) {
                        b.this.snoozeService.a(intent.getStringExtra(b.this.b));
                    }
                    if (intent.getBooleanExtra("BROAD_CAST_FROM_SNOOZE_SERVICE", false)) {
                        b.this.a(intent.getStringExtra("event_key"), intent.getStringExtra("event_value"));
                    }
                    if (intent.hasExtra(CBConstant.SNOOZE_SERVICE_STATUS)) {
                        b.this.ac = true;
                        if (CBActivity.b != 2) {
                            int i = CBActivity.b;
                        }
                        b.this.a();
                    }
                    if (intent.getBooleanExtra(CBConstant.BROADCAST_FROM_SERVICE_UPDATE_UI, false) && intent.hasExtra(CBConstant.IS_FORWARD_JOURNEY)) {
                        if (intent.getStringExtra("key").contentEquals(CBConstant.GOOD_NETWORK_NOTIFICATION_LAUNCHED)) {
                            b bVar = b.this;
                            bVar.ac = true;
                            bVar.ad = intent;
                            return;
                        }
                        b bVar2 = b.this;
                        bVar2.ac = false;
                        bVar2.a(intent);
                    }
                }
            }
        };
        if (!this.f.getClass().getSimpleName().equalsIgnoreCase("CBActivity")) {
            this.J = true;
            cbOldOnCreate();
        } else {
            cbOnCreate();
        }
        initAnalytics(Bank.keyAnalytics);
        this.ae = false;
        if (this.f != null) {
            this.N.clearCookie();
            this.surePayS2SPayUId = null;
            this.surePayS2Surl = null;
        }
        if (this.customBrowserConfig != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(this.customBrowserConfig.getEnableSurePay());
            a("snooze_enable_count", sb.toString());
            a("snooze_mode_set_merchant", this.customBrowserConfig.getSurePayMode() == 1 ? "WARN" : "FAIL");
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.f = (Activity) context;
    }

    /* access modifiers changed from: private */
    public void a() {
        if (this.k != null && this.k.isShowing()) {
            this.k.cancel();
            this.k.dismiss();
        }
        if (this.f != null && !this.f.isFinishing()) {
            View inflate = this.f.getLayoutInflater().inflate(R.layout.cb_layout_snooze, null);
            ((TextView) inflate.findViewById(R.id.snooze_header_txt)).setText(getString(R.string.cb_snooze_network_error));
            inflate.findViewById(R.id.text_view_cancel_snooze_window).setVisibility(8);
            ((TextView) inflate.findViewById(R.id.text_view_snooze_message)).setText(getString(R.string.cb_snooze_network_down_message));
            inflate.findViewById(R.id.snooze_loader_view).setVisibility(8);
            inflate.findViewById(R.id.button_snooze_transaction).setVisibility(8);
            inflate.findViewById(R.id.text_view_retry_message_detail).setVisibility(8);
            inflate.findViewById(R.id.button_retry_transaction).setVisibility(8);
            inflate.findViewById(R.id.button_cancel_transaction).setVisibility(8);
            inflate.findViewById(R.id.t_confirm).setVisibility(8);
            inflate.findViewById(R.id.t_nconfirm).setVisibility(8);
            Button button = (Button) inflate.findViewById(R.id.button_go_back_snooze);
            button.setVisibility(0);
            button.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    b.this.f.finish();
                }
            });
            this.k = new Builder(this.f, R.style.cb_snooze_dialog).create();
            this.k.setView(inflate);
            this.k.setCanceledOnTouchOutside(false);
            this.k.setOnCancelListener(new OnCancelListener() {
                public void onCancel(DialogInterface dialogInterface) {
                    b.this.f.finish();
                }
            });
            this.k.show();
        }
    }

    public void cbOldOnCreate() {
        this.I = getArguments();
        this.autoApprove = this.I.getBoolean(CBConstant.AUTO_APPROVE, false);
        this.autoSelectOtp = this.I.getBoolean(CBConstant.AUTO_SELECT_OTP, false);
        this.H = this.I.getInt(CBConstant.STORE_ONE_CLICK_HASH, 0);
        this.C = this.I.getBoolean(CBConstant.MERCHANT_SMS_PERMISSION, false);
        if (Bank.c == null || Bank.c.equalsIgnoreCase("")) {
            Bank.c = getArguments().getString(CBConstant.SDK_DETAILS);
        }
        if (Bank.a == null || Bank.a.equalsIgnoreCase("")) {
            Bank.a = getArguments().getString("txnid");
        }
        if (Bank.keyAnalytics == null || Bank.keyAnalytics.equalsIgnoreCase("")) {
            Bank.keyAnalytics = getArguments().getString(CBConstant.MERCHANT_KEY);
        }
    }

    public void cbOnCreate() {
        if (getArguments() != null && getArguments().containsKey(CBConstant.CB_CONFIG)) {
            this.customBrowserConfig = (CustomBrowserConfig) getArguments().getParcelable(CBConstant.CB_CONFIG);
            this.reviewOrderDetailList = getArguments().getParcelableArrayList(CBConstant.ORDER_DETAILS);
            int i = 0;
            this.C = this.customBrowserConfig != null && this.customBrowserConfig.getMerchantSMSPermission() == 1;
            this.autoApprove = this.customBrowserConfig != null && this.customBrowserConfig.getAutoApprove() == 1;
            this.autoSelectOtp = this.customBrowserConfig != null && this.customBrowserConfig.getAutoSelectOTP() == 1;
            if (this.customBrowserConfig != null) {
                i = this.customBrowserConfig.getStoreOneClickHash();
            }
            this.H = i;
            if (this.customBrowserConfig != null) {
                this.customBrowserConfig.getPostURL();
            }
            if (this.customBrowserConfig != null) {
                if (Bank.keyAnalytics == null || Bank.keyAnalytics.trim().equals("")) {
                    if (this.customBrowserConfig.getMerchantKey() != null || !this.customBrowserConfig.getMerchantKey().trim().equals("")) {
                        Bank.keyAnalytics = this.customBrowserConfig.getMerchantKey();
                    } else {
                        Bank.keyAnalytics = "";
                    }
                }
                if (Bank.a == null || Bank.a.trim().equals("")) {
                    if (this.customBrowserConfig.getTransactionID() == null || this.customBrowserConfig.getTransactionID().trim().equals("")) {
                        Bank.a = "123";
                    } else {
                        Bank.a = this.customBrowserConfig.getTransactionID();
                    }
                }
                if (Bank.c == null || Bank.c.trim().equals("")) {
                    if (this.customBrowserConfig.getSdkVersionName() == null || this.customBrowserConfig.getSdkVersionName().trim().equals("")) {
                        Bank.c = "";
                    } else {
                        Bank.c = this.customBrowserConfig.getSdkVersionName();
                    }
                }
                if (!TextUtils.isEmpty(this.customBrowserConfig.getSurepayS2Surl()) || !TextUtils.isEmpty(this.customBrowserConfig.getHtmlData())) {
                    this.isS2SHtmlSupport = true;
                }
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        View view2;
        super.onCreateView(inflater, container, savedInstanceState);
        if (this.J) {
            view2 = inflater.inflate(R.layout.bankold, container, false);
            view2.bringToFront();
            cbOldFlowOnCreateView();
            view = view2;
        } else {
            view2 = inflater.inflate(R.layout.bank, container, false);
            this.M = view2.findViewById(R.id.trans_overlay);
            this.s = (WebView) view2.findViewById(R.id.webview);
            this.ab = view2.findViewById(R.id.cb_blank_overlay);
            view = view2.findViewById(R.id.parent);
            this.Z = (TextView) view2.findViewById(R.id.t_payu_review_order_cb);
            this.Y = (TextView) view2.findViewById(R.id.t_payu_review_order);
            setReviewOrderButtonProperty(this.Z);
            this.X = (RelativeLayout) view2.findViewById(R.id.r_payu_review_order);
            cbOnCreateView();
            if (this.customBrowserConfig.getEnableReviewOrder() == 0) {
                if (this.customBrowserConfig.getReviewOrderCustomView() != -1) {
                    a("review_order_type", "review_order_custom");
                } else {
                    a("review_order_type", "review_order_default");
                }
            }
        }
        CBUtil.setVariableReflection(CBConstant.MAGIC_RETRY_PAKAGE, "7.3.0", CBConstant.CB_VERSION);
        this.K = (FrameLayout) view2.findViewById(R.id.help_view);
        this.L = view2.findViewById(R.id.view);
        this.y = (ProgressBar) view2.findViewById(R.id.cb_progressbar);
        o();
        this.viewOnClickListener = new com.payu.custombrowser.a.b();
        p();
        this.mAnalyticsMap = new HashMap<>();
        view.setOnTouchListener(new a());
        return view2;
    }

    private void o() {
        this.s.getSettings().setJavaScriptEnabled(true);
        this.s.addJavascriptInterface(this, "PayU");
        this.s.getSettings().setSupportMultipleWindows(true);
        this.s.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                b.this.q();
                if (b.this.M != null) {
                    b.this.M.setVisibility(8);
                }
                if (b.this.z == 2) {
                    b.this.g();
                }
                return false;
            }
        });
        this.s.getSettings().setDomStorageEnabled(true);
        this.s.getSettings().setRenderPriority(RenderPriority.HIGH);
        this.s.getSettings().setCacheMode(2);
        this.s.getSettings().setAppCacheEnabled(false);
    }

    public void cbOldFlowOnCreateView() {
        this.s = (WebView) this.f.findViewById(getArguments().getInt(CBConstant.WEBVIEW));
        if (Bank.b != null && Bank.b.equalsIgnoreCase(CBConstant.NB)) {
            this.s.getSettings().setUseWideViewPort(true);
        } else if (this.customBrowserConfig != null && this.customBrowserConfig.getViewPortWideEnable() == 1) {
            this.s.getSettings().setUseWideViewPort(true);
        }
        this.s.setFocusable(true);
        this.s.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == 1 && keyCode == 4) {
                    if (b.this.getArguments().getBoolean(CBConstant.BACK_BUTTON, true)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(b.this.f, R.style.cb_dialog);
                        builder.setCancelable(false);
                        builder.setMessage("Do you really want to cancel the transaction ?");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                b.this.postToPaytxn();
                                b.this.a("user_input", "back_button_ok");
                                dialog.dismiss();
                                b.this.onBackApproved();
                                b.this.f.finish();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                b.this.a("user_input", "back_button_cancel");
                                b.this.onBackCancelled();
                                dialog.dismiss();
                            }
                        });
                        b.this.a("user_input", "payu_back_button");
                        b.this.onBackPressed(builder);
                        builder.show();
                        return true;
                    }
                    b.this.a("user_input", "m_back_button");
                    b.this.onBackPressed(null);
                    b.this.f.onBackPressed();
                }
                return false;
            }
        });
        if (Bank.b != null && Bank.b.equalsIgnoreCase(CBConstant.NB)) {
            this.s.getSettings().setUseWideViewPort(true);
        } else if (this.I.getBoolean(CBConstant.VIEWPORTWIDE, false)) {
            this.s.getSettings().setUseWideViewPort(true);
        }
    }

    public void cbOnCreateView() {
        if (Bank.b != null && Bank.b.equalsIgnoreCase(CBConstant.NB)) {
            this.s.getSettings().setUseWideViewPort(true);
        } else if (this.customBrowserConfig != null && this.customBrowserConfig.getViewPortWideEnable() == 1) {
            this.s.getSettings().setUseWideViewPort(true);
        }
        Bank bank = (Bank) this;
        this.s.setWebChromeClient(new PayUWebChromeClient(bank));
        if (this.customBrowserConfig.getEnableSurePay() > 0) {
            this.s.setWebViewClient(new PayUSurePayWebViewClient(bank, Bank.keyAnalytics));
        } else {
            this.s.setWebViewClient(new PayUWebViewClient(bank, Bank.keyAnalytics));
        }
        if (!TextUtils.isEmpty(this.customBrowserConfig.getHtmlData())) {
            a("cb_status", "load_html");
            this.s.loadDataWithBaseURL("https://secure.payu.in/_payment", this.customBrowserConfig.getHtmlData(), "text/html", Key.STRING_CHARSET_NAME, null);
        } else if (!TextUtils.isEmpty(this.customBrowserConfig.getSurepayS2Surl())) {
            this.s.loadUrl(this.customBrowserConfig.getSurepayS2Surl());
        } else if (!(this.customBrowserConfig == null || this.customBrowserConfig.getPostURL() == null || this.customBrowserConfig.getPayuPostData() == null)) {
            this.s.postUrl(this.customBrowserConfig.getPostURL(), this.customBrowserConfig.getPayuPostData().getBytes());
        }
        if (com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback() != null) {
            com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback().setCBProperties(this.s, bank);
        }
        if (this.customBrowserConfig != null && this.customBrowserConfig.getMagicretry() == 1) {
            if (this.customBrowserConfig.getEnableSurePay() == 0) {
                initMagicRetry();
            }
            if (com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback() != null) {
                com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback().initializeMagicRetry(bank, this.s, this.p);
            }
        }
        this.mAnalyticsMap = new HashMap<>();
        String webViewVersionFromSP = CBUtil.getWebViewVersionFromSP(getContext());
        if (webViewVersionFromSP.length() > 0 && !webViewVersionFromSP.contentEquals(CBUtil.getWebViewVersion(new WebView(getContext())))) {
            a("web_view_updated_successfully", CBUtil.getWebViewVersion(new WebView(getContext())));
            CBUtil.setWebViewVersionInSP(getContext(), "");
        }
    }

    public void logOnTerminate() {
        try {
            a("last_url", CBUtil.updateLastUrl(this.N.getStringSharedPreference(this.f.getApplicationContext(), "last_url")));
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            this.N.deleteSharedPrefKey(this.f.getApplicationContext(), "last_url");
            throw th;
        }
        this.N.deleteSharedPrefKey(this.f.getApplicationContext(), "last_url");
        if (!this.o.contains("CUSTOM_BROWSER")) {
            if (this.o.contains("review_order_custom_browser")) {
                this.m = "review_order_custom_browser";
            } else {
                this.m = "NON_CUSTOM_BROWSER";
            }
            a("cb_status", this.m);
        }
        this.m = "terminate_transaction";
        a("user_input", this.m);
        if (this.w != null && !this.w.isShowing()) {
            this.w.dismiss();
        }
        if (this.g != null) {
            unregisterBroadcast(this.g);
            this.g = null;
        }
        if (this.listOfTxtFld != null && this.listOfTxtFld.length() > 1 && !this.isOTPFilled) {
            a("bank_page_otp_fields", this.listOfTxtFld);
            a("bank_page_host_name", this.hostName);
        }
        this.N.clearJSStringSharedPreference(this.f);
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.N.cancelTimer(this.timerProgress);
        if (this.k != null && this.k.isShowing()) {
            this.k.dismiss();
        }
        if (this.w != null) {
            this.w.dismiss();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacks(this.mResetCounter);
        this.N.cancelTimer(this.timerProgress);
        this.N.cancelTimer(this.ai);
        CountDownTimer countDownTimer = this.slowUserCountDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this.snoozeVisibleCountBackwdJourney + this.snoozeVisibleCountFwdJourney);
        a("snooze_count", sb.toString());
        com.payu.custombrowser.bean.b.SINGLETON.setPayuCustomBrowserCallback(null);
        if (this.k != null && this.k.isShowing()) {
            this.k.dismiss();
        }
        if (this.snoozeBroadCastReceiver != null && this.isSnoozeBroadCastReceiverRegistered && !a) {
            LocalBroadcastManager.getInstance(this.f.getApplicationContext()).unregisterReceiver(this.snoozeBroadCastReceiver);
        }
        if (this.snoozeServiceConnection != null && this.isSnoozeServiceBounded) {
            this.f.unbindService(this.snoozeServiceConnection);
        }
        SnoozeService snoozeService2 = this.snoozeService;
        if (snoozeService2 != null && a) {
            snoozeService2.a();
        }
        if (this.O != null) {
            this.ar.b(this.O.findViewById(R.id.progress));
        }
        if (this.P != null) {
            this.ar.b(this.P.findViewById(R.id.progress));
        }
        if (this.Q != null) {
            this.N.cancelTimer(this.Q.a());
        }
        if (this.l != null) {
            this.N.cancelTimer(this.l.a());
        }
        this.N.cancelTimer(this.ai);
        if (this.R != null) {
            this.R.cancel();
        }
        logOnTerminate();
        Bank.c = null;
        Bank.keyAnalytics = null;
        Bank.a = null;
        Bank.b = null;
        if (this.s != null) {
            this.s.destroy();
        }
        this.N.resetPayuID();
        this.surePayS2SPayUId = null;
    }

    public void onResume() {
        super.onResume();
        if (this.ac) {
            this.ac = false;
            cancelTransactionNotification();
            if (this.ad != null) {
                if (this.backwardJourneyStarted) {
                    try {
                        if (Integer.parseInt(new JSONObject(this.ad.getStringExtra("value")).get(getString(R.string.cb_snooze_verify_api_status)).toString()) == 1) {
                            a("transaction_verified_dialog_recent_app", "-1");
                        } else {
                            a("transaction_not_verified_dialog_recent_app", "-1");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        a("transaction_not_verified_dialog_recent_app", "-1");
                    }
                } else {
                    a("internet_restored_dialog_recent_app", "-1");
                }
                a(this.ad);
                return;
            }
            a("internet_not_restored_dialog_recent_app", "-1");
        }
    }

    /* access modifiers changed from: 0000 */
    public void m() {
        if (this.g == null && this.am) {
            this.g = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    try {
                        if (b.this.h != null) {
                            Bundle extras = intent.getExtras();
                            if (b.this.getActivity() != null && !b.this.getActivity().isFinishing()) {
                                Bundle extras2 = intent.getExtras();
                                if (extras2 != null) {
                                    String str = "";
                                    String str2 = null;
                                    if (!SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
                                        Object[] objArr = (Object[]) extras2.get("pdus");
                                        if (objArr != null) {
                                            SmsMessage[] smsMessageArr = new SmsMessage[objArr.length];
                                            String str3 = null;
                                            String str4 = str;
                                            for (int i = 0; i < smsMessageArr.length; i++) {
                                                if (VERSION.SDK_INT >= 23) {
                                                    smsMessageArr[i] = SmsMessage.createFromPdu((byte[]) objArr[i], extras.getString("format"));
                                                } else {
                                                    smsMessageArr[i] = SmsMessage.createFromPdu((byte[]) objArr[i]);
                                                }
                                                StringBuilder sb = new StringBuilder();
                                                sb.append(str4);
                                                sb.append(smsMessageArr[i].getMessageBody());
                                                str4 = sb.toString();
                                                str3 = smsMessageArr[i].getDisplayOriginatingAddress();
                                            }
                                            str = str4;
                                            str2 = str3;
                                        }
                                    } else if (((Status) extras2.get(SmsRetriever.EXTRA_STATUS)).getStatusCode() == 0) {
                                        String str5 = (String) extras2.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                                        b.this.a("otp_received", "sms_retriever");
                                        str = str5;
                                    }
                                    if (!TextUtils.isEmpty(str)) {
                                        b.this.ah = CBUtil.filterSMS(b.this.h, str, b.this.f.getApplicationContext());
                                        if (b.this.ah == null || b.this.ah.length() < 6 || b.this.ah.length() > 8) {
                                            if (b.this.T && !TextUtils.isEmpty(str2)) {
                                                b.this.S = b.this.b(str2, str);
                                            }
                                            if (b.this.S) {
                                                b.this.a(com.payu.custombrowser.util.a.b, com.payu.custombrowser.util.a.g);
                                            }
                                        } else {
                                            b.this.fillOTPOnBankPage();
                                            b.this.otp = b.this.ah;
                                            b.this.backupOfOTP = b.this.otp;
                                            b.this.otpTriggered = true;
                                            try {
                                                b.this.isOTPFilled = false;
                                                if (b.this.catchAllJSEnabled && !TextUtils.isEmpty(b.this.otp)) {
                                                    JSONObject jSONObject = new JSONObject();
                                                    jSONObject.put("otp", b.this.otp);
                                                    jSONObject.put("isAutoFillOTP", true);
                                                    WebView webView = b.this.s;
                                                    StringBuilder sb2 = new StringBuilder();
                                                    sb2.append("javascript:");
                                                    sb2.append(b.this.h.getString(b.this.getString(R.string.cb_fill_otp)));
                                                    sb2.append("(");
                                                    sb2.append(jSONObject);
                                                    sb2.append(")");
                                                    webView.loadUrl(sb2.toString());
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            b.this.fillOTP(this);
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            };
        }
        registerBroadcast(this.g, n());
    }

    public void fillOTPOnBankPage() {
        try {
            if (this.i != null && !TextUtils.isEmpty(this.ah) && this.i.has(getString(R.string.cb_fill_otp))) {
                WebView webView = this.s;
                StringBuilder sb = new StringBuilder();
                sb.append("javascript:");
                sb.append(this.i.getString(getString(R.string.cb_fill_otp)));
                sb.append("(\"");
                sb.append(this.ah);
                sb.append("\",\"url\")");
                webView.loadUrl(sb.toString());
                this.ah = null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void registerSMSBroadcast() {
        if (this.g == null) {
            m();
        } else {
            registerBroadcast(this.g, n());
        }
    }

    /* access modifiers changed from: 0000 */
    public IntentFilter n() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.setPriority(9999999);
        if (ContextCompat.checkSelfPermission(this.f, "android.permission.RECEIVE_SMS") == 0) {
            intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        } else {
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        }
        return intentFilter;
    }

    /* access modifiers changed from: 0000 */
    public String d(String str) {
        for (String split : this.customBrowserConfig.getPayuPostData().split("&")) {
            String[] split2 = split.split("=");
            if (split2.length >= 2 && split2[0].equalsIgnoreCase(str)) {
                return split2[1];
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public boolean b(String str, String str2) {
        int i;
        boolean z;
        String lowerCase = str2.toLowerCase();
        boolean z2 = false;
        if (str.contains(this.D)) {
            i = 1;
        } else {
            i = 0;
        }
        if (lowerCase.toLowerCase().contains(d(PayUmoneyConstants.AMOUNT).replace(",", ""))) {
            i++;
        }
        if (i == 2) {
            z = true;
        } else {
            z = false;
        }
        if (i == 0) {
            z = false;
        }
        if (i == 0) {
            return false;
        }
        if (lowerCase.contains("made") && lowerCase.contains("purchase")) {
            return true;
        }
        if (lowerCase.contains("account") && lowerCase.contains("debited")) {
            return true;
        }
        if (lowerCase.contains("ac") && lowerCase.contains("debited")) {
            return true;
        }
        if (lowerCase.contains("tranx") && lowerCase.contains("made")) {
            return true;
        }
        if ((lowerCase.contains("transaction") && lowerCase.contains("made")) || lowerCase.contains("spent") || lowerCase.contains("Thank you using card for")) {
            return true;
        }
        if (!lowerCase.contains("charge") || !lowerCase.contains("initiated")) {
            return z;
        }
        if (lowerCase.contains("charge") && lowerCase.contains("initiated")) {
            z2 = true;
        }
        return z2;
    }

    public void fillOTP(BroadcastReceiver mReceiver) {
        if (getActivity().findViewById(R.id.otp_sms) != null) {
            final TextView textView = (TextView) getActivity().findViewById(R.id.otp_sms);
            if (this.ag && this.ah != null) {
                this.N.cancelTimer(this.ai);
                String str = this.m;
                char c = 65535;
                int hashCode = str.hashCode();
                if (hashCode != -557081102) {
                    if (hashCode != 674270068) {
                        if (hashCode == 2084916017 && str.equals("regenerate_click")) {
                            c = 2;
                        }
                    } else if (str.equals("otp_click")) {
                        c = 1;
                    }
                } else if (str.equals("payment_initiated")) {
                    c = 0;
                }
                switch (c) {
                    case 0:
                        this.m = "received_otp_direct";
                        break;
                    case 1:
                        this.m = "received_otp_selected";
                        break;
                    case 2:
                        this.m = "received_otp_regenerate";
                        break;
                    default:
                        this.m = "otp_web";
                        break;
                }
                a("otp_received", this.m);
                textView.setText(this.ah);
                this.ah = null;
                this.ar.c(getActivity().findViewById(R.id.progress));
                Button button = (Button) getActivity().findViewById(R.id.approve);
                button.setClickable(true);
                CBUtil.setAlpha(1.0f, button);
                button.setVisibility(0);
                this.f.findViewById(R.id.timer).setVisibility(8);
                this.f.findViewById(R.id.retry_text).setVisibility(8);
                this.f.findViewById(R.id.regenerate_layout).setVisibility(8);
                this.f.findViewById(R.id.waiting).setVisibility(8);
                this.f.findViewById(R.id.otp_recieved).setVisibility(0);
                textView.setVisibility(0);
                if (this.autoApprove) {
                    button.performClick();
                    this.m = CBConstant.AUTO_APPROVE;
                    a("user_input", this.m);
                }
                button.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        try {
                            b.this.ah = null;
                            b.this.m = "approved_otp";
                            b.this.a("user_input", b.this.m);
                            b.this.a("Approve_btn_clicked_time", "-1");
                            b.this.m();
                            b.this.ak = false;
                            b.this.aj = Boolean.valueOf(true);
                            b.this.onHelpUnavailable();
                            b.this.f();
                            b.this.z = 1;
                            WebView webView = b.this.s;
                            StringBuilder sb = new StringBuilder();
                            sb.append("javascript:");
                            sb.append(b.this.i.getString(b.this.getString(R.string.cb_process_otp)));
                            sb.append("(\"");
                            sb.append(textView.getText().toString());
                            sb.append("\")");
                            webView.loadUrl(sb.toString());
                            textView.setText("");
                            b.this.c();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    private void p() {
        m();
        this.m = "payment_initiated";
        a("user_input", this.m);
        this.W.execute(new Runnable() {
            public void run() {
                final String str;
                final String str2;
                final String str3;
                final String str4;
                final String str5;
                final String str6;
                String str7 = "initialize";
                StringBuilder sb = new StringBuilder();
                sb.append(CBConstant.PRODUCTION_URL);
                sb.append(str7);
                sb.append(".js");
                HttpsURLConnection httpsConn = CBUtil.getHttpsConn(sb.toString());
                if (httpsConn != null) {
                    try {
                        if (httpsConn.getResponseCode() == 200) {
                            b.this.N.writeFileOutputStream(httpsConn.getInputStream(), b.this.f, str7, 0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            if (b.this.f != null && !b.this.f.isFinishing()) {
                                b.this.h = new JSONObject(CBUtil.decodeContents(b.this.f.openFileInput(str7)));
                                b.this.j();
                                if (!b.this.J) {
                                    b.this.checkStatusFromJS("", 1);
                                    b.this.checkStatusFromJS("", 2);
                                }
                                if (b.this.h.has("snooze_config")) {
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(b.this.h.get("snooze_config"));
                                    sb2.append("('");
                                    sb2.append(Bank.keyAnalytics);
                                    sb2.append("')");
                                    str3 = sb2.toString();
                                } else {
                                    str3 = "";
                                }
                                b.snoozeImageDownloadTimeout = Integer.parseInt(b.this.h.has("snooze_image_download_time") ? b.this.h.get("snooze_image_download_time").toString() : "0");
                                f.a(b.this.f.getApplicationContext(), CBUtil.CB_PREFERENCE, CBConstant.SNOOZE_IMAGE_DOWNLOAD_TIME_OUT, b.snoozeImageDownloadTimeout);
                                if (b.this.h.has(b.this.getString(R.string.sp_internet_restored_ttl))) {
                                    StringBuilder sb3 = new StringBuilder();
                                    sb3.append(b.this.h.get(b.this.getString(R.string.sp_internet_restored_ttl)));
                                    sb3.append("('");
                                    sb3.append(Bank.keyAnalytics);
                                    sb3.append("')");
                                    str4 = sb3.toString();
                                } else {
                                    str4 = "";
                                }
                                b.this.f.runOnUiThread(new Runnable() {
                                    public void run() {
                                        WebView webView = b.this.s;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("javascript:");
                                        sb.append(str);
                                        webView.loadUrl(sb.toString());
                                    }
                                });
                                b.this.f.runOnUiThread(new Runnable() {
                                    public void run() {
                                        WebView webView = b.this.s;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("javascript:");
                                        sb.append(str2);
                                        webView.loadUrl(sb.toString());
                                    }
                                });
                                if (b.this.h.has(CBConstant.SUREPAY_S2S)) {
                                    b.this.N.setStringSharedPreference(b.this.f, CBConstant.SUREPAY_S2S, b.this.h.getString(CBConstant.SUREPAY_S2S));
                                }
                                if (b.this.ap && b.this.f != null) {
                                    b.this.f.runOnUiThread(new Runnable() {
                                        public void run() {
                                            b.this.onPageStarted();
                                        }
                                    });
                                    return;
                                }
                                return;
                            }
                            return;
                        } catch (FileNotFoundException | JSONException e2) {
                            b.this.h();
                            e2.printStackTrace();
                            return;
                        } catch (Exception e3) {
                            b.this.h();
                            e3.printStackTrace();
                            return;
                        }
                    } catch (Throwable th) {
                        try {
                            if (b.this.f != null && !b.this.f.isFinishing()) {
                                b.this.h = new JSONObject(CBUtil.decodeContents(b.this.f.openFileInput(str7)));
                                b.this.j();
                                if (!b.this.J) {
                                    b.this.checkStatusFromJS("", 1);
                                    b.this.checkStatusFromJS("", 2);
                                }
                                if (b.this.h.has("snooze_config")) {
                                    StringBuilder sb4 = new StringBuilder();
                                    sb4.append(b.this.h.get("snooze_config"));
                                    sb4.append("('");
                                    sb4.append(Bank.keyAnalytics);
                                    sb4.append("')");
                                    str5 = sb4.toString();
                                } else {
                                    str5 = "";
                                }
                                b.snoozeImageDownloadTimeout = Integer.parseInt(b.this.h.has("snooze_image_download_time") ? b.this.h.get("snooze_image_download_time").toString() : "0");
                                f.a(b.this.f.getApplicationContext(), CBUtil.CB_PREFERENCE, CBConstant.SNOOZE_IMAGE_DOWNLOAD_TIME_OUT, b.snoozeImageDownloadTimeout);
                                if (b.this.h.has(b.this.getString(R.string.sp_internet_restored_ttl))) {
                                    StringBuilder sb5 = new StringBuilder();
                                    sb5.append(b.this.h.get(b.this.getString(R.string.sp_internet_restored_ttl)));
                                    sb5.append("('");
                                    sb5.append(Bank.keyAnalytics);
                                    sb5.append("')");
                                    str6 = sb5.toString();
                                } else {
                                    str6 = "";
                                }
                                b.this.f.runOnUiThread(new Runnable() {
                                    public void run() {
                                        WebView webView = b.this.s;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("javascript:");
                                        sb.append(str);
                                        webView.loadUrl(sb.toString());
                                    }
                                });
                                b.this.f.runOnUiThread(new Runnable() {
                                    public void run() {
                                        WebView webView = b.this.s;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("javascript:");
                                        sb.append(str2);
                                        webView.loadUrl(sb.toString());
                                    }
                                });
                                if (b.this.h.has(CBConstant.SUREPAY_S2S)) {
                                    b.this.N.setStringSharedPreference(b.this.f, CBConstant.SUREPAY_S2S, b.this.h.getString(CBConstant.SUREPAY_S2S));
                                }
                                if (b.this.ap && b.this.f != null) {
                                    b.this.f.runOnUiThread(new Runnable() {
                                        public void run() {
                                            b.this.onPageStarted();
                                        }
                                    });
                                }
                            }
                        } catch (FileNotFoundException | JSONException e4) {
                            b.this.h();
                            e4.printStackTrace();
                        } catch (Exception e5) {
                            b.this.h();
                            e5.printStackTrace();
                        }
                        throw th;
                    }
                }
                if (b.this.f != null && !b.this.f.isFinishing()) {
                    b.this.h = new JSONObject(CBUtil.decodeContents(b.this.f.openFileInput(str7)));
                    b.this.j();
                    if (!b.this.J) {
                        b.this.checkStatusFromJS("", 1);
                        b.this.checkStatusFromJS("", 2);
                    }
                    if (b.this.h.has("snooze_config")) {
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(b.this.h.get("snooze_config"));
                        sb6.append("('");
                        sb6.append(Bank.keyAnalytics);
                        sb6.append("')");
                        str = sb6.toString();
                    } else {
                        str = "";
                    }
                    b.snoozeImageDownloadTimeout = Integer.parseInt(b.this.h.has("snooze_image_download_time") ? b.this.h.get("snooze_image_download_time").toString() : "0");
                    f.a(b.this.f.getApplicationContext(), CBUtil.CB_PREFERENCE, CBConstant.SNOOZE_IMAGE_DOWNLOAD_TIME_OUT, b.snoozeImageDownloadTimeout);
                    if (b.this.h.has(b.this.getString(R.string.sp_internet_restored_ttl))) {
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append(b.this.h.get(b.this.getString(R.string.sp_internet_restored_ttl)));
                        sb7.append("('");
                        sb7.append(Bank.keyAnalytics);
                        sb7.append("')");
                        str2 = sb7.toString();
                    } else {
                        str2 = "";
                    }
                    b.this.f.runOnUiThread(new Runnable() {
                        public void run() {
                            WebView webView = b.this.s;
                            StringBuilder sb = new StringBuilder();
                            sb.append("javascript:");
                            sb.append(str);
                            webView.loadUrl(sb.toString());
                        }
                    });
                    b.this.f.runOnUiThread(new Runnable() {
                        public void run() {
                            WebView webView = b.this.s;
                            StringBuilder sb = new StringBuilder();
                            sb.append("javascript:");
                            sb.append(str2);
                            webView.loadUrl(sb.toString());
                        }
                    });
                    if (b.this.h.has(CBConstant.SUREPAY_S2S)) {
                        b.this.N.setStringSharedPreference(b.this.f, CBConstant.SUREPAY_S2S, b.this.h.getString(CBConstant.SUREPAY_S2S));
                    }
                    if (b.this.ap && b.this.f != null) {
                        b.this.f.runOnUiThread(new Runnable() {
                            public void run() {
                                b.this.onPageStarted();
                            }
                        });
                    }
                }
            }
        });
    }

    public void updateHeight(View view) {
        if (this.v == 0) {
            e();
            f();
        }
        b(view);
    }

    public void updateLoaderHeight() {
        if (this.af == 0) {
            this.s.measure(-1, -1);
            this.af = (int) (((double) this.s.getMeasuredHeight()) * 0.35d);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            this.ao = false;
            if (this.aq) {
                try {
                    WebView webView = this.s;
                    StringBuilder sb = new StringBuilder();
                    sb.append("javascript:");
                    sb.append(this.i.getString(getString(R.string.cb_otp)));
                    webView.loadUrl(sb.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (ContextCompat.checkSelfPermission(this.f, "android.permission.RECEIVE_SMS") == 0) {
                this.am = true;
                this.ah = null;
                m();
                a(this.al);
                return;
            }
            if (this.N.hasRequestedPermission(this.f, "android.permission.RECEIVE_SMS")) {
                this.am = false;
                if (this.g != null) {
                    unregisterBroadcast(this.g);
                    this.g = null;
                }
            }
            a(this.al);
        }
    }

    /* access modifiers changed from: protected */
    public void showSlowUserWarning() {
        if (this.f != null && !this.f.isFinishing() && !this.n) {
            View inflate = this.f.getLayoutInflater().inflate(R.layout.cb_layout_snooze_slow_user, null);
            ((TextView) inflate.findViewById(R.id.snooze_header_txt)).setText(R.string.cb_snooze_slow_user_warning_header);
            TextView textView = (TextView) inflate.findViewById(R.id.text_view_cancel_snooze_window);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.snooze_status_icon);
            imageView.setVisibility(0);
            imageView.setImageDrawable(getCbDrawable(this.f.getApplicationContext(), R.drawable.hourglass));
            if (this.slowUserWarningDialog == null) {
                this.slowUserWarningDialog = new AlertDialog.Builder(this.f).create();
                this.slowUserWarningDialog.setView(inflate);
                this.slowUserWarningDialog.setCanceledOnTouchOutside(true);
                this.slowUserWarningDialog.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss(DialogInterface dialogInterface) {
                    }
                });
                this.slowUserWarningDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                        if (keyCode == 4 && event.getAction() == 0) {
                            b.this.slowUserWarningDialog.dismiss();
                        }
                        return true;
                    }
                });
                textView.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        b.this.slowUserWarningDialog.dismiss();
                    }
                });
            }
            this.slowUserWarningDialog.show();
            CBActivity cBActivity = (CBActivity) this.f;
            if (CBActivity.b == 1) {
                showSlowUserWarningNotification();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void dismissSlowUserWarning() {
        AlertDialog alertDialog = this.slowUserWarningDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    /* access modifiers changed from: private */
    public void q() {
        if (this.forwardJourneyForChromeLoaderIsComplete) {
            this.firstTouch = true;
            dismissSlowUserWarningTimer();
        }
    }

    /* access modifiers changed from: protected */
    public void showSlowUserWarningNotification() {
        if (this.f != null && !this.f.isFinishing()) {
            new Intent();
        }
    }

    /* access modifiers changed from: protected */
    public void showCbBlankOverlay(int visibility) {
        View view = this.ab;
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    /* access modifiers changed from: protected */
    public void updateSnoozeDialogWithMessage(String header, String message) {
        if (this.k != null && this.k.isShowing()) {
            this.k.cancel();
            this.k.dismiss();
        }
        SnoozeService snoozeService2 = this.snoozeService;
        if (snoozeService2 != null) {
            snoozeService2.a();
        }
        b();
        if (this.f != null && !this.f.isFinishing()) {
            View inflate = this.f.getLayoutInflater().inflate(R.layout.cb_layout_snooze, null);
            ((TextView) inflate.findViewById(R.id.snooze_header_txt)).setText(header);
            inflate.findViewById(R.id.text_view_cancel_snooze_window).setVisibility(8);
            ((TextView) inflate.findViewById(R.id.text_view_snooze_message)).setText(message);
            SnoozeLoaderView snoozeLoaderView = (SnoozeLoaderView) inflate.findViewById(R.id.snooze_loader_view);
            snoozeLoaderView.setVisibility(0);
            snoozeLoaderView.a();
            inflate.findViewById(R.id.button_snooze_transaction).setVisibility(8);
            inflate.findViewById(R.id.text_view_retry_message_detail).setVisibility(8);
            inflate.findViewById(R.id.button_retry_transaction).setVisibility(8);
            inflate.findViewById(R.id.button_cancel_transaction).setVisibility(8);
            inflate.findViewById(R.id.t_confirm).setVisibility(8);
            inflate.findViewById(R.id.t_nconfirm).setVisibility(8);
            inflate.findViewById(R.id.button_go_back_snooze).setVisibility(8);
            this.k = new Builder(this.f).create();
            this.k.setView(inflate);
            this.k.setCancelable(false);
            this.k.setCanceledOnTouchOutside(false);
            hideReviewOrderHorizontalBar();
            this.k.show();
        }
    }

    /* access modifiers changed from: private */
    public void a(final Intent intent) {
        int i;
        if (this.customBrowserConfig != null) {
            i = this.customBrowserConfig.getInternetRestoredWindowTTL();
        } else {
            i = 5000;
        }
        int i2 = this.au;
        if (i2 != 0) {
            i = i2;
        }
        if (this.backwardJourneyStarted) {
            try {
                if (this.N.getValueOfJSONKey(intent.getStringExtra("value"), getString(R.string.cb_snooze_verify_api_status)).contentEquals("1")) {
                    if (this.f != null && !this.f.isFinishing()) {
                        updateSnoozeDialogWithMessage(this.f.getResources().getString(R.string.cb_transaction_verified), this.f.getResources().getString(R.string.redirect_back_to_merchant));
                    }
                } else if (this.f != null && !this.f.isFinishing()) {
                    updateSnoozeDialogWithMessage(this.f.getResources().getString(R.string.cb_transaction_state_unknown), this.f.getResources().getString(R.string.status_unknown_redirect_to_merchant));
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (this.f != null && !this.f.isFinishing()) {
                    updateSnoozeDialogWithMessage(this.f.getResources().getString(R.string.cb_transaction_state_unknown), this.f.getResources().getString(R.string.status_unknown_redirect_to_merchant));
                }
            }
        } else if (this.f != null && !this.f.isFinishing()) {
            updateSnoozeDialogWithMessage(this.f.getResources().getString(R.string.internet_restored), this.f.getResources().getString(R.string.resuming_your_transaction));
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (b.this.k != null) {
                    b.this.k.dismiss();
                    b.this.showReviewOrderHorizontalBar();
                }
                if (b.this.backwardJourneyStarted) {
                    if (b.this.snoozeService != null) {
                        b.this.snoozeService.a();
                    }
                    b.this.showTransactionStatusDialog(intent.getStringExtra("value"), false);
                    return;
                }
                if (b.this.isRetryNowPressed) {
                    b.this.isRetryNowPressed = false;
                } else {
                    b.this.snoozeCount++;
                }
                b.this.resumeTransaction(intent);
            }
        }, (long) i);
    }

    public void showTransactionStatusDialog(String payuResponse, boolean showDialogMessage) {
        final String valueOfJSONKey;
        try {
            setTransactionStatusReceived(true);
            String valueOfJSONKey2 = this.N.getValueOfJSONKey(payuResponse, getString(R.string.cb_snooze_verify_api_status));
            View inflate = this.f.getLayoutInflater().inflate(R.layout.cb_layout_snooze, null);
            Builder builder = new Builder(this.f);
            builder.setView(inflate);
            this.k = builder.create();
            if (valueOfJSONKey2.contentEquals("1")) {
                if (!Bank.isUrlWhiteListed(this.s.getUrl() != null ? this.s.getUrl() : "") || 19 == VERSION.SDK_INT) {
                    a("snooze_transaction_status_update", "post_to_surl");
                    String str = "";
                    try {
                        valueOfJSONKey = this.N.getValueOfJSONKey(payuResponse, CBConstant.RESPONSE);
                        postDataToSurl(valueOfJSONKey, new CBUtil().getDataFromPostData(this.customBrowserConfig.getPayuPostData(), "surl"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (showDialogMessage) {
                        inflate.findViewById(R.id.snooze_status_icon).setVisibility(0);
                        ((TextView) inflate.findViewById(R.id.snooze_header_txt)).setText(R.string.cb_transaction_sucess);
                        inflate.findViewById(R.id.text_view_cancel_snooze_window).setVisibility(8);
                        ((TextView) inflate.findViewById(R.id.text_view_snooze_message)).setText(getString(R.string.cb_transaction_success_msg));
                        inflate.findViewById(R.id.snooze_loader_view).setVisibility(8);
                        inflate.findViewById(R.id.button_snooze_transaction).setVisibility(8);
                        inflate.findViewById(R.id.text_view_retry_message_detail).setVisibility(8);
                        inflate.findViewById(R.id.button_retry_transaction).setVisibility(8);
                        inflate.findViewById(R.id.button_cancel_transaction).setVisibility(8);
                        inflate.findViewById(R.id.t_confirm).setVisibility(8);
                        inflate.findViewById(R.id.t_nconfirm).setVisibility(8);
                        this.k.setOnDismissListener(new OnDismissListener() {
                            public void onDismiss(DialogInterface dialogInterface) {
                                try {
                                    if (!(com.payu.custombrowser.bean.b.SINGLETON == null || com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback() == null)) {
                                        com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback().onPaymentSuccess(valueOfJSONKey, "");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                b.this.k.dismiss();
                                b.this.k.cancel();
                                b.this.f.finish();
                            }
                        });
                        this.k.setCanceledOnTouchOutside(false);
                        this.k.show();
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                if (b.this.k != null && b.this.k.isShowing()) {
                                    b.this.k.cancel();
                                    b.this.k.dismiss();
                                    b.this.f.finish();
                                }
                            }
                        }, 5000);
                    } else {
                        if (!(com.payu.custombrowser.bean.b.SINGLETON == null || com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback() == null)) {
                            com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback().onPaymentSuccess(valueOfJSONKey, "");
                        }
                        this.f.finish();
                    }
                    return;
                }
                a("snooze_transaction_status_update", "data_repost");
                a(8, "");
                reloadWebView();
            } else if (showDialogMessage) {
                inflate.findViewById(R.id.button_snooze_transaction).setVisibility(0);
                inflate.findViewById(R.id.snooze_status_icon).setVisibility(0);
                inflate.findViewById(R.id.text_view_cancel_snooze_window).setVisibility(8);
                inflate.findViewById(R.id.button_snooze_transaction).setVisibility(8);
                ((TextView) inflate.findViewById(R.id.snooze_header_txt)).setText(R.string.cb_transaction_failed_title);
                ((TextView) inflate.findViewById(R.id.text_view_snooze_message)).setText(R.string.cb_transaction_failed);
                inflate.findViewById(R.id.button_retry_transaction).setVisibility(8);
                inflate.findViewById(R.id.button_cancel_transaction).setVisibility(0);
                inflate.findViewById(R.id.button_snooze_transaction).setVisibility(8);
                inflate.findViewById(R.id.text_view_retry_message_detail).setVisibility(8);
                inflate.findViewById(R.id.text_view_transaction_snoozed_message1).setVisibility(8);
                inflate.findViewById(R.id.text_view_ac_debited_twice).setVisibility(8);
                inflate.findViewById(R.id.button_cancel_transaction).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        b.this.a("snooze_interaction_time", "-1");
                        b.this.a("snooze_window_action", "snooze_cancel_transaction_click");
                        b.this.k.dismiss();
                        b.this.k.cancel();
                        b.this.f.finish();
                    }
                });
                this.k.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss(DialogInterface dialogInterface) {
                        b.this.k.dismiss();
                        b.this.k.cancel();
                    }
                });
                this.k.setCanceledOnTouchOutside(false);
                hideReviewOrderDetails();
                hideReviewOrderHorizontalBar();
                this.k.show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (b.this.k != null && b.this.k.isShowing()) {
                            b.this.k.cancel();
                            b.this.k.dismiss();
                            b.this.f.finish();
                        }
                    }
                }, 5000);
            } else {
                this.f.finish();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void postDataToSurl(final String postData, final String surl) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(URLDecoder.decode(surl, Key.STRING_CHARSET_NAME)).openConnection();
                    String str = postData;
                    httpsURLConnection.setRequestMethod("POST");
                    httpsURLConnection.setRequestProperty("Content-Type", CBConstant.HTTP_URLENCODED);
                    httpsURLConnection.setRequestProperty("Content-Length", String.valueOf(str.length()));
                    httpsURLConnection.setDoOutput(true);
                    httpsURLConnection.getOutputStream().write(str.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void resumeTransaction(Intent intent) {
        this.customBrowserConfig = (CustomBrowserConfig) intent.getExtras().getParcelable(CBConstant.CB_CONFIG);
        if (intent.getStringExtra(CBConstant.CURRENT_URL) == null || intent.getStringExtra(CBConstant.S2S_RETRY_URL) != null) {
            if (intent.getStringExtra(CBConstant.S2S_RETRY_URL) != null) {
                reloadWebView(intent.getStringExtra(CBConstant.S2S_RETRY_URL), null);
            } else {
                reloadWebView(this.customBrowserConfig.getPostURL(), this.customBrowserConfig.getPayuPostData());
            }
        } else if (intent.getStringExtra(CBConstant.CURRENT_URL).equalsIgnoreCase(this.customBrowserConfig.getPostURL())) {
            if (this.customBrowserConfig.getPostURL().contentEquals("https://secure.payu.in/_payment") || this.customBrowserConfig.getPostURL().contentEquals("https://mobiletest.payu.in/_payment")) {
                CBUtil cBUtil = this.N;
                markPreviousTxnAsUserCanceled(CBUtil.getLogMessage(this.f.getApplicationContext(), "sure_pay_cancelled", this.customBrowserConfig.getTransactionID(), "", Bank.keyAnalytics, this.customBrowserConfig.getTransactionID(), ""));
            }
            reloadWebView(this.customBrowserConfig.getPostURL(), this.customBrowserConfig.getPayuPostData());
        } else if (Bank.isUrlWhiteListed(intent.getStringExtra(CBConstant.CURRENT_URL))) {
            reloadWebView(intent.getStringExtra(CBConstant.CURRENT_URL));
        } else {
            if (this.customBrowserConfig.getPostURL().contentEquals("https://secure.payu.in/_payment") || this.customBrowserConfig.getPostURL().contentEquals("https://mobiletest.payu.in/_payment")) {
                CBUtil cBUtil2 = this.N;
                markPreviousTxnAsUserCanceled(CBUtil.getLogMessage(this.f.getApplicationContext(), "sure_pay_cancelled", this.customBrowserConfig.getTransactionID(), "", Bank.keyAnalytics, this.customBrowserConfig.getTransactionID(), ""));
            }
            reloadWebView(this.customBrowserConfig.getPostURL(), this.customBrowserConfig.getPayuPostData());
        }
    }

    public void markPreviousTxnAsUserCanceled(String logMessage) {
        new com.payu.custombrowser.widgets.b(logMessage).a();
    }
}
