package com.payu.custombrowser;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.payu.custombrowser.a.b;
import com.payu.custombrowser.bean.CustomBrowserConfig;
import com.payu.custombrowser.bean.ReviewOrderData;
import com.payu.custombrowser.util.CBConstant;
import com.payu.custombrowser.util.CBUtil;
import com.payu.custombrowser.util.c;
import com.payu.magicretry.Helpers.MRConstant;
import com.payu.magicretry.MagicRetryFragment;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.concurrent.Executor;
import org.json.JSONObject;

public class a extends Fragment implements CBConstant {
    public static final boolean DEBUG = false;
    public static ArrayAdapter drawerAdapter;
    BroadcastReceiver A = null;
    String B;
    boolean C;
    String D;
    String E;
    String F;
    Boolean G = Boolean.FALSE;
    int H;
    Bundle I;
    boolean J;
    FrameLayout K;
    View L;
    View M;
    CBUtil N;
    View O;
    View P;
    b Q;
    CountDownTimer R;
    boolean S;
    boolean T;
    Set<String> U;
    Set<String> V;
    Executor W;
    RelativeLayout X;
    TextView Y;
    TextView Z;
    private boolean a;
    c aa;
    protected boolean autoApprove;
    protected boolean autoSelectOtp;
    private boolean b;
    protected String backupOfOTP;
    protected boolean backwardJourneyStarted = false;
    /* access modifiers changed from: private */
    public int c = 0;
    protected boolean catchAllJSEnabled = false;
    protected CustomBrowserConfig customBrowserConfig;
    final String e = CBConstant.PRODUCTION_URL;
    Activity f;
    protected boolean firstTouch = false;
    protected boolean forwardJourneyForChromeLoaderIsComplete = false;
    BroadcastReceiver g;
    JSONObject h;
    protected String hostName;
    JSONObject i;
    protected boolean isOTPFilled = false;
    protected boolean isSurePayValueLoaded = false;
    protected boolean isTxnNBType;
    protected boolean isWebviewReloading;
    int j;
    AlertDialog k;
    com.payu.custombrowser.a.a l;
    protected String listOfTxtFld;
    String m;
    protected Handler mHandler = new Handler();
    protected Runnable mResetCounter = new Runnable() {
        public void run() {
            a.this.c = 0;
        }
    };
    protected String merchantKey;
    boolean n = false;
    ArrayList<String> o = new ArrayList<>();
    protected String otp;
    protected boolean otpTriggered = false;
    MagicRetryFragment p;
    protected String pageType = "";
    protected boolean payuChromeLoaderDisabled = false;
    protected String phpSessionId;
    boolean q;
    Drawable r;
    protected ArrayList<ReviewOrderData> reviewOrderDetailList;
    WebView s;
    public int snoozeMode = 1;
    protected String surePayS2SPayUId;
    protected String surePayS2Surl;
    int t;
    protected String timeOfArrival;
    protected String timeOfDeparture;
    protected Timer timerProgress;
    protected String txnId;
    protected String txnType;
    int u;
    int v;
    protected b viewOnClickListener;
    com.payu.custombrowser.widgets.a w;
    int x;
    ProgressBar y;
    int z;

    /* renamed from: com.payu.custombrowser.a$a reason: collision with other inner class name */
    public class C0001a implements OnTouchListener {
        float a;
        boolean b = true;
        int c = 0;

        public C0001a() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (a.this.q) {
                return false;
            }
            a.this.f();
            if (!this.b) {
                return false;
            }
            int actionMasked = event.getActionMasked();
            if (a.this.L.getVisibility() != 0) {
                switch (actionMasked) {
                    case 0:
                        this.a = event.getY();
                        break;
                    case 1:
                        float y = event.getY();
                        if (this.a < y && a.this.K.getVisibility() == 0 && y - this.a > 0.0f) {
                            this.c = v.getHeight();
                            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) (v.getHeight() - 30));
                            translateAnimation.setDuration(500);
                            translateAnimation.setFillBefore(false);
                            translateAnimation.setFillEnabled(true);
                            translateAnimation.setZAdjustment(1);
                            v.startAnimation(translateAnimation);
                            if (a.this.M != null) {
                                a.this.M.setVisibility(8);
                            }
                            this.b = false;
                            this.b = true;
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    if (a.this.f != null && !a.this.f.isFinishing()) {
                                        a.this.z = 1;
                                        a.this.K.setVisibility(8);
                                        a.this.L.setVisibility(0);
                                    }
                                }
                            }, 400);
                            break;
                        }
                }
            } else {
                a.this.L.setClickable(false);
                a.this.L.setOnTouchListener(null);
                TranslateAnimation translateAnimation2 = new TranslateAnimation(0.0f, 0.0f, (float) this.c, 0.0f);
                translateAnimation2.setDuration(500);
                translateAnimation2.setFillBefore(true);
                v.startAnimation(translateAnimation2);
                a.this.K.setVisibility(0);
                this.b = false;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (a.this.f != null && !a.this.f.isFinishing()) {
                            a.this.L.setVisibility(8);
                        }
                    }
                }, 20);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        C0001a aVar = C0001a.this;
                        aVar.b = true;
                        a.this.z = 2;
                        if (a.this.M != null && a.this.f != null && !a.this.f.isFinishing()) {
                            a.this.a(a.this.M, (Context) a.this.f);
                        }
                    }
                }, 500);
            }
            return true;
        }
    }

    public class b implements OnClickListener {
        public b() {
        }

        public void onClick(View v) {
            if (v.getId() == R.id.bank_logo) {
                if (a.this.c == 0) {
                    a.this.mHandler.postDelayed(a.this.mResetCounter, 3000);
                }
                a.this.c = a.this.c + 1;
                if (a.this.c == 5) {
                    a.this.mHandler.removeCallbacks(a.this.mResetCounter);
                    a.this.c = 0;
                    Toast.makeText(a.this.f, "Version Name: 7.3.0", 0).show();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkIfTransactionNBType(String postData) {
        try {
            if (this.N.getDataFromPostData(this.customBrowserConfig.getPayuPostData(), "pg").equalsIgnoreCase(CBConstant.NB)) {
                return true;
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void resetAutoSelectOTP() {
        CustomBrowserConfig customBrowserConfig2 = this.customBrowserConfig;
        boolean z2 = true;
        if (customBrowserConfig2 == null || customBrowserConfig2.getAutoSelectOTP() != 1) {
            z2 = false;
        }
        this.autoSelectOtp = z2;
    }

    public Drawable getCbDrawable(Context context, int resID) {
        if (VERSION.SDK_INT >= 21) {
            return context.getResources().getDrawable(resID, context.getTheme());
        }
        return context.getResources().getDrawable(resID);
    }

    /* access modifiers changed from: 0000 */
    public void a(final View view, Context context) {
        if (view != null) {
            view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.cb_fade_in));
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (a.this.f != null && !a.this.f.isFinishing()) {
                        view.setVisibility(0);
                    }
                }
            }, 500);
        }
    }

    public void checkStatusFromJS(String bank) {
        checkStatusFromJS(bank, 0);
    }

    public void checkStatusFromJS(final String bank, final int featureConstant) {
        try {
            if (getActivity() != null && !getActivity().isFinishing()) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        final String str;
                        String str2;
                        try {
                            JSONObject jSONObject = new JSONObject();
                            StringBuilder sb = new StringBuilder();
                            sb.append(VERSION.RELEASE);
                            sb.append("");
                            jSONObject.put("androidosversion", sb.toString());
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(Build.MANUFACTURER);
                            sb2.append("");
                            jSONObject.put("androidmanufacturer", sb2.toString().toLowerCase());
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(Build.MODEL);
                            sb3.append("");
                            jSONObject.put("model", sb3.toString().toLowerCase());
                            jSONObject.put(CBConstant.MERCHANT_KEY, Bank.keyAnalytics);
                            jSONObject.put(CBConstant.SDK_DETAILS, Bank.c);
                            jSONObject.put("cbname", "7.3.0");
                            if (featureConstant == 1) {
                                if (a.this.h.has("set_dynamic_snooze")) {
                                    StringBuilder sb4 = new StringBuilder();
                                    sb4.append(a.this.h.getString("set_dynamic_snooze"));
                                    sb4.append("(");
                                    sb4.append(jSONObject);
                                    sb4.append(")");
                                    str2 = sb4.toString();
                                } else {
                                    str2 = "";
                                }
                                WebView webView = a.this.s;
                                StringBuilder sb5 = new StringBuilder();
                                sb5.append("javascript:");
                                sb5.append(str2);
                                webView.loadUrl(sb5.toString());
                            } else if (featureConstant == 0) {
                                jSONObject.put("bankname", bank.toLowerCase());
                                WebView webView2 = a.this.s;
                                StringBuilder sb6 = new StringBuilder();
                                sb6.append("javascript:");
                                sb6.append(a.this.h.getString("checkVisibilityCBCall"));
                                sb6.append("(");
                                sb6.append(jSONObject);
                                sb6.append(")");
                                webView2.loadUrl(sb6.toString());
                            } else if (featureConstant == 2) {
                                if (a.this.h.has("checkVisibilityReviewOrderCall")) {
                                    StringBuilder sb7 = new StringBuilder();
                                    sb7.append(a.this.h.getString("checkVisibilityReviewOrderCall"));
                                    sb7.append("(");
                                    sb7.append(jSONObject);
                                    sb7.append(")");
                                    str = sb7.toString();
                                } else {
                                    str = null;
                                }
                                if (str != null) {
                                    new Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            if (a.this.f != null && !a.this.f.isFinishing()) {
                                                WebView webView = a.this.s;
                                                StringBuilder sb = new StringBuilder();
                                                sb.append("javascript:");
                                                sb.append(str);
                                                webView.loadUrl(sb.toString());
                                            }
                                        }
                                    }, 1000);
                                }
                            } else if (featureConstant == 3) {
                                WebView webView3 = a.this.s;
                                StringBuilder sb8 = new StringBuilder();
                                sb8.append("javascript:");
                                sb8.append(a.this.h.getString(a.this.getString(R.string.cb_check_visibility_cajs)));
                                sb8.append("(");
                                sb8.append(jSONObject);
                                sb8.append(")");
                                webView3.loadUrl(sb8.toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        ((InputMethodManager) this.f.getSystemService("input_method")).showSoftInput(view, 2);
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        this.f.getWindow().setSoftInputMode(3);
    }

    /* access modifiers changed from: protected */
    public void initAnalytics(String sdkMerchantKey) {
        this.l = com.payu.custombrowser.a.a.a(this.f.getApplicationContext(), "local_cache_analytics");
        a(sdkMerchantKey, this.f.getApplicationContext());
    }

    private void a(String str, Context context) {
        JSONObject jSONObject = new JSONObject();
        String str2 = MRConstant.PAYU_ID;
        try {
            CBUtil cBUtil = this.N;
            jSONObject.put(str2, CBUtil.getCookie("PAYUID", context));
            jSONObject.put("txnid", Bank.a);
            jSONObject.put(MRConstant.MERCHANT_KEY, str);
            StringBuilder sb = new StringBuilder();
            sb.append(VERSION.SDK_INT);
            sb.append("");
            jSONObject.put("device_os_version", sb.toString());
            jSONObject.put("device_resolution", this.N.getDeviceDensity(this.f));
            jSONObject.put("device_manufacturer", Build.MANUFACTURER);
            jSONObject.put("device_model", Build.MODEL);
            jSONObject.put("network_info", this.N.getNetworkStatus(this.f.getApplicationContext()));
            jSONObject.put("sdk_version_name", Bank.c);
            jSONObject.put("cb_version_name", "7.3.0");
            jSONObject.put("package_name", context.getPackageName());
            jSONObject.put("network_strength", this.N.getNetworkStrength(this.f.getApplicationContext()));
            CBUtil.setVariableReflection(CBConstant.MAGIC_RETRY_PAKAGE, str, CBConstant.ANALYTICS_KEY);
            this.Q = new b(this.f.getApplicationContext(), "cb_local_cache_device");
            this.Q.a(jSONObject.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(String str, String str2) {
        if (str2 != null) {
            try {
                if (!str2.trim().equalsIgnoreCase("")) {
                    com.payu.custombrowser.a.a aVar = this.l;
                    CBUtil cBUtil = this.N;
                    aVar.a(CBUtil.getLogMessage(this.f.getApplicationContext(), str, str2.toLowerCase(), this.D, Bank.keyAnalytics, Bank.a, this.pageType));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void c(String str) {
        if (this.r == null && str != null) {
            try {
                if (!str.equalsIgnoreCase("sbinet") && !str.equalsIgnoreCase("sbi")) {
                    if (!str.startsWith("sbi_")) {
                        if (!str.equalsIgnoreCase("icici") && !str.equalsIgnoreCase("icicinet") && !str.equalsIgnoreCase("icicicc")) {
                            if (!str.startsWith("icici_")) {
                                if (!str.equalsIgnoreCase("kotaknet") && !str.equalsIgnoreCase("kotak")) {
                                    if (!str.startsWith("kotak_")) {
                                        if (!str.equalsIgnoreCase("indus")) {
                                            if (!str.startsWith("indus_")) {
                                                if (!str.equalsIgnoreCase("hdfc") && !str.equalsIgnoreCase("hdfcnet")) {
                                                    if (!str.startsWith("hdfc_")) {
                                                        if (!str.equalsIgnoreCase("yesnet")) {
                                                            if (!str.startsWith("yes_")) {
                                                                if (!str.equalsIgnoreCase("sc")) {
                                                                    if (!str.startsWith("sc_")) {
                                                                        if (!str.equalsIgnoreCase("axisnet") && !str.equalsIgnoreCase("axis")) {
                                                                            if (!str.startsWith("axis_")) {
                                                                                if (!str.equalsIgnoreCase("amex")) {
                                                                                    if (!str.startsWith("amex_")) {
                                                                                        if (!str.equalsIgnoreCase("hdfcnet") && !str.equalsIgnoreCase("hdfc")) {
                                                                                            if (!str.startsWith("hdfc_")) {
                                                                                                if (!str.equalsIgnoreCase("ing")) {
                                                                                                    if (!str.startsWith("ing_")) {
                                                                                                        if (!str.equalsIgnoreCase("idbi")) {
                                                                                                            if (!str.startsWith("idbi_")) {
                                                                                                                if (!str.equalsIgnoreCase("citi")) {
                                                                                                                    if (!str.startsWith("citi_")) {
                                                                                                                        if (!str.equalsIgnoreCase("unionnet")) {
                                                                                                                            if (!str.startsWith("unionnet_")) {
                                                                                                                                this.r = null;
                                                                                                                                return;
                                                                                                                            }
                                                                                                                        }
                                                                                                                        this.r = this.N.getDrawableCB(this.f.getApplicationContext(), R.drawable.union_bank_logo);
                                                                                                                        return;
                                                                                                                    }
                                                                                                                }
                                                                                                                this.r = this.N.getDrawableCB(this.f.getApplicationContext(), R.drawable.citi);
                                                                                                                return;
                                                                                                            }
                                                                                                        }
                                                                                                        this.r = this.N.getDrawableCB(this.f.getApplicationContext(), R.drawable.idbi);
                                                                                                        return;
                                                                                                    }
                                                                                                }
                                                                                                this.r = this.N.getDrawableCB(this.f.getApplicationContext(), R.drawable.ing_logo);
                                                                                                return;
                                                                                            }
                                                                                        }
                                                                                        this.r = this.N.getDrawableCB(this.f, R.drawable.hdfc_bank);
                                                                                        return;
                                                                                    }
                                                                                }
                                                                                this.r = this.N.getDrawableCB(this.f.getApplicationContext(), R.drawable.cb_amex_logo);
                                                                                return;
                                                                            }
                                                                        }
                                                                        this.r = this.N.getDrawableCB(this.f.getApplicationContext(), R.drawable.axis_logo);
                                                                        return;
                                                                    }
                                                                }
                                                                this.r = this.N.getDrawableCB(this.f.getApplicationContext(), R.drawable.scblogo);
                                                                return;
                                                            }
                                                        }
                                                        this.r = this.N.getDrawableCB(this.f.getApplicationContext(), R.drawable.yesbank_logo);
                                                        return;
                                                    }
                                                }
                                                this.r = this.N.getDrawableCB(this.f.getApplicationContext(), R.drawable.hdfc_bank);
                                                return;
                                            }
                                        }
                                        this.r = this.N.getDrawableCB(this.f.getApplicationContext(), R.drawable.induslogo);
                                        return;
                                    }
                                }
                                this.r = this.N.getDrawableCB(this.f.getApplicationContext(), R.drawable.kotak);
                                return;
                            }
                        }
                        this.r = this.N.getDrawableCB(this.f.getApplicationContext(), R.drawable.icici);
                        return;
                    }
                }
                this.r = this.N.getDrawableCB(this.f.getApplicationContext(), R.drawable.sbi);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void d() {
        View currentFocus = this.f.getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) this.f.getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(View view) {
        view.measure(-2, -2);
        this.t = view.getMeasuredHeight();
        int i2 = this.v;
        if (i2 != 0) {
            this.u = i2 - this.t;
        }
    }

    /* access modifiers changed from: 0000 */
    public void e() {
        try {
            if (this.v == 0 && this.D != null) {
                this.s.measure(-1, -1);
                this.s.requestLayout();
                this.v = this.s.getMeasuredHeight();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: 0000 */
    public void f() {
        if (this.v == 0) {
            e();
        }
        if (this.v != 0) {
            this.s.getLayoutParams().height = this.v;
            this.s.requestLayout();
        }
    }

    /* access modifiers changed from: 0000 */
    public void g() {
        if (this.v != 0) {
            this.s.getLayoutParams().height = this.u;
            this.s.requestLayout();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, String str) {
        Activity activity = this.f;
        if (activity != null && !activity.isFinishing() && !isRemoving() && isAdded()) {
            if (i2 == 8 || i2 == 4) {
                com.payu.custombrowser.widgets.a aVar = this.w;
                if (aVar != null) {
                    aVar.dismiss();
                    this.w = null;
                    showReviewOrderHorizontalBar();
                }
            } else if (i2 == 0 && !this.payuChromeLoaderDisabled && !this.n) {
                if (this.w == null) {
                    this.w = new com.payu.custombrowser.widgets.a(this.f);
                }
                if (this.isWebviewReloading) {
                    this.w.a(this.f.getString(R.string.cb_resuming_transaction));
                    this.isWebviewReloading = false;
                } else {
                    this.w.a(this.f.getString(R.string.cb_please_wait));
                }
                this.w.show();
                if (!this.J) {
                    hideReviewOrderHorizontalBar();
                    hideReviewOrderDetails();
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void h() {
        Activity activity = this.f;
        if (activity != null && !activity.isFinishing() && isAdded() && !isRemoving()) {
            this.f.runOnUiThread(new Runnable() {
                public void run() {
                    a.this.a(8, "");
                    if (a.this.y != null) {
                        a.this.y.setVisibility(8);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        Activity activity = this.f;
        if (activity != null && !activity.isFinishing() && !isRemoving() && isAdded()) {
            if (this.x > i2) {
                this.y.setProgress(i2);
            }
            if (VERSION.SDK_INT >= 11) {
                ObjectAnimator ofInt = ObjectAnimator.ofInt(this.y, NotificationCompat.CATEGORY_PROGRESS, new int[]{i2});
                ofInt.setDuration(50);
                ofInt.setInterpolator(new AccelerateInterpolator());
                ofInt.start();
            } else {
                if (i2 <= 10) {
                    i2 = 10;
                }
                this.y.setProgress(i2);
            }
            this.x = i2;
        }
    }

    /* access modifiers changed from: 0000 */
    public void i() {
        f();
        this.z = 1;
        onHelpUnavailable();
    }

    public void registerBroadcast(BroadcastReceiver broadcastReceiver, IntentFilter filter) {
        Activity activity = this.f;
        if (activity != null && !activity.isFinishing()) {
            this.A = broadcastReceiver;
            this.f.registerReceiver(broadcastReceiver, filter);
        }
    }

    public void unregisterBroadcast(BroadcastReceiver broadcastReceiver) {
        if (this.A != null) {
            this.f.unregisterReceiver(broadcastReceiver);
            this.A = null;
        }
    }

    public void onHelpUnavailable() {
        Activity activity = this.f;
        if (activity != null && !activity.isFinishing()) {
            this.f.findViewById(R.id.parent).setVisibility(8);
        }
    }

    public void onBankError() {
        this.f.findViewById(R.id.parent).setVisibility(8);
    }

    public void onHelpAvailable() {
        this.a = true;
        this.f.findViewById(R.id.parent).setVisibility(0);
    }

    public boolean wasCBVisibleOnce() {
        return this.a;
    }

    public boolean isRetryURL(String url) {
        if (this.V.size() == 0) {
            return url.contains(CBConstant.PAYMENT_OPTION_URL_PROD);
        }
        for (String contains : this.V) {
            if (url.contains(contains)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void j() {
        JSONObject jSONObject = this.h;
        if (jSONObject != null) {
            try {
                if (jSONObject.has("postPaymentPgUrlList")) {
                    StringTokenizer stringTokenizer = new StringTokenizer(this.h.getString("postPaymentPgUrlList").replace(" ", ""), CBConstant.CB_DELIMITER);
                    while (stringTokenizer.hasMoreTokens()) {
                        this.U.add(stringTokenizer.nextToken());
                    }
                }
                if (this.h.has("retryList")) {
                    StringTokenizer stringTokenizer2 = new StringTokenizer(this.h.getString("retryUrlList").replace(" ", ""), CBConstant.CB_DELIMITER);
                    while (stringTokenizer2.hasMoreTokens()) {
                        this.V.add(stringTokenizer2.nextToken());
                    }
                }
            } catch (Exception e2) {
                h();
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void k() {
        AnonymousClass4 r0 = new CountDownTimer((long) this.customBrowserConfig.getMerchantResponseTimeout(), 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                if (a.this.f != null && !a.this.f.isFinishing() && a.this.isAdded() && !a.this.isRemoving()) {
                    a.this.f.runOnUiThread(new Runnable() {
                        public void run() {
                            if (a.this.f != null && !a.this.f.isFinishing() && a.this.isAdded()) {
                                a.this.l();
                            }
                        }
                    });
                }
            }
        };
        this.R = r0.start();
    }

    /* access modifiers changed from: 0000 */
    public void l() {
        CountDownTimer countDownTimer = this.R;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        Activity activity = this.f;
        if (activity != null && !activity.isFinishing() && isAdded() && !isRemoving()) {
            this.f.runOnUiThread(new Runnable() {
                public void run() {
                    if (a.this.f != null && !a.this.f.isFinishing() && a.this.isAdded()) {
                        if (a.this.J) {
                            Intent intent = new Intent();
                            intent.putExtra(a.this.getString(R.string.cb_result), a.this.F);
                            intent.putExtra(a.this.getString(R.string.cb_payu_response), a.this.E);
                            if (a.this.G.booleanValue()) {
                                if (a.this.H == 1) {
                                    new d().execute(new String[]{a.this.E});
                                }
                                a.this.f.setResult(-1, intent);
                            } else {
                                a.this.f.setResult(0, intent);
                            }
                        } else if (a.this.G.booleanValue()) {
                            if (a.this.customBrowserConfig.getStoreOneClickHash() == 1) {
                                new d().execute(new String[]{a.this.E});
                            }
                            if (com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback() != null) {
                                com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback().onPaymentSuccess(a.this.E, a.this.F);
                            } else {
                                c.a("PayuError", "No PayUCustomBrowserCallback found, please assign a callback: com.payu.custombrowser.PayUCustomBrowserCallback.setPayuCustomBrowserCallback(PayUCustomBrowserCallback payuCustomBrowserCallback)");
                            }
                        } else if (com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback() != null) {
                            com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback().onPaymentFailure(a.this.E, a.this.F);
                        } else {
                            c.a("PayuError", "No PayUCustomBrowserCallback found, please assign a callback: com.payu.custombrowser.PayUCustomBrowserCallback.setPayuCustomBrowserCallback(PayUCustomBrowserCallback payuCustomBrowserCallback)");
                        }
                        a.this.f.finish();
                    }
                }
            });
        }
    }

    public void loadUrlWebView(JSONObject mJS, String functName) {
    }

    public void onBackPressed(Builder alertDialog) {
    }

    public void onBackApproved() {
    }

    public void onBackCancelled() {
    }

    /* access modifiers changed from: protected */
    public void cancelTransactionNotification() {
        NotificationManager notificationManager = (NotificationManager) this.f.getSystemService("notification");
        notificationManager.cancel(CBConstant.TRANSACTION_STATUS_NOTIFICATION_ID);
        notificationManager.cancel(CBConstant.SNOOZE_NOTIFICATION_ID);
    }

    /* access modifiers changed from: protected */
    public void setTransactionStatusReceived(boolean transactionStatusReceived) {
        this.b = transactionStatusReceived;
    }

    /* access modifiers changed from: protected */
    public boolean getTransactionStatusReceived() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public void postToPaytxn() {
        if (this.T) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        if (a.this.N.getHttpsConn("https://secure.payu.in/paytxn", null, -1, a.this.N.getCookieList(a.this.getActivity().getApplicationContext(), "https://secure.payu.in")).getResponseCode() != 200) {
                            Log.d("PayU", "BackButtonClick - UnSuccessful post to Paytxn");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.setPriority(10);
            thread.start();
        }
    }

    public void showReviewOrderHorizontalBar() {
        com.payu.custombrowser.widgets.a aVar = this.w;
        if ((aVar == null || !aVar.isShowing()) && !this.J && this.customBrowserConfig.getEnableReviewOrder() == 0 && !this.n) {
            if (!this.o.contains("review_order_custom_browser")) {
                this.o.add("review_order_custom_browser");
            }
            this.X.setVisibility(0);
            this.X.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    a.this.a("user_input", "review_order_btn_click");
                    a.this.d();
                    a.this.showReviewOrderDetails();
                }
            });
            setReviewOrderButtonProperty(this.Y);
        }
    }

    public void setReviewOrderButtonProperty(TextView reviewOrderTextView) {
        if (this.J) {
            reviewOrderTextView.setVisibility(8);
        } else if (this.customBrowserConfig.getEnableReviewOrder() == 0) {
            if (this.customBrowserConfig.getReviewOrderButtonText() != null) {
                reviewOrderTextView.setText(this.customBrowserConfig.getReviewOrderButtonText());
            }
            if (this.customBrowserConfig.getReviewOrderButtonTextColor() != -1) {
                reviewOrderTextView.setTextColor(this.f.getResources().getColor(this.customBrowserConfig.getReviewOrderButtonTextColor()));
            }
            reviewOrderTextView.setVisibility(0);
            reviewOrderTextView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    a.this.a("user_input", "review_order_btn_click");
                    a.this.d();
                    a.this.showReviewOrderDetails();
                }
            });
        } else {
            reviewOrderTextView.setVisibility(8);
        }
    }

    public void hideReviewOrderHorizontalBar() {
        if (!this.J) {
            this.X.setVisibility(8);
        }
    }

    public void showReviewOrderDetails() {
        c cVar = this.aa;
        if ((cVar == null || !cVar.isAdded()) && getActivity() != null) {
            this.aa = c.a(this.reviewOrderDetailList, this.customBrowserConfig.getReviewOrderCustomView());
            FragmentTransaction beginTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            beginTransaction.setCustomAnimations(R.anim.slide_up_in, R.anim.slide_up_out);
            beginTransaction.add(R.id.payu_review_order, (Fragment) this.aa);
            beginTransaction.commit();
        }
    }

    public void hideReviewOrderDetails() {
        if (getActivity() != null && this.aa != null) {
            FragmentTransaction beginTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            beginTransaction.remove(this.aa);
            beginTransaction.setCustomAnimations(R.anim.slide_up_out, R.anim.slide_up_in);
            beginTransaction.commitAllowingStateLoss();
        }
    }
}
