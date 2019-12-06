package com.payu.magicretry;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.bumptech.glide.load.Key;
import com.payu.custombrowser.util.CBConstant;
import com.payu.magicretry.Helpers.L;
import com.payu.magicretry.Helpers.MRConstant;
import com.payu.magicretry.Helpers.SharedPreferenceUtil;
import com.payu.magicretry.Helpers.Util;
import com.payu.magicretry.analytics.MRAnalytics;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class MagicRetryFragment extends Fragment implements OnClickListener {
    public static boolean DEBUG = false;
    public static final String KEY_TXNID = "transaction_id";
    public static final String SP_IS_MR_ENABLED = "MR_ENABLED";
    public static final String SP_MR_FILE_NAME = "MR_SETTINGS";
    public static final String SP_WHITELISTED_URLS = "MR_WHITELISTED_URLS";
    private static String analyticsKey = null;
    private static String bankName = null;
    private static String cbVersion = null;
    private static boolean disableMagicRetry = false;
    private static final String projectToken = "68dbbac2c25bc048154999d13cb77a55";
    private static List<String> whiteListedUrls = new ArrayList();
    String PAYU_DOMAIN;
    String PAYU_DOMAIN_PROD = "https://secure.payu.in";
    String PAYU_DOMAIN_TEST = CBConstant.PAYU_DOMAIN_TEST;
    private ActivityCallback activityCallbackHandler;
    private Context context;
    private boolean errorWasReceived = true;
    private int firstCall;
    private boolean fromOnReceivedError = false;
    private boolean isWhiteListingEnabled = true;
    private MRAnalytics mAnalytics;
    private WebView mWebView;
    private ProgressBar magicProgress;
    private LinearLayout magicRetryLayoutParent;
    private String reloadUrl;
    private ImageView retryButton;
    private String txnID = "";
    private Map<String, String> urlListWithPostData = new HashMap();
    private LinearLayout waitingDotsLayoutParent;

    public interface ActivityCallback {
        void hideMagicRetry();

        void showMagicRetry();
    }

    public MagicRetryFragment() {
        this.PAYU_DOMAIN = DEBUG ? this.PAYU_DOMAIN_TEST : this.PAYU_DOMAIN_PROD;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getActivity().getBaseContext();
        this.txnID = getArguments().getString(KEY_TXNID);
        View view = inflater.inflate(R.layout.magicretry_fragment, container, false);
        initViewElements(view);
        List<String> whiteListedUrls2 = new ArrayList<>();
        whiteListedUrls2.add("https://secure.payu.in/_payment");
        whiteListedUrls2.add("https://secure.payu.in/_secure_payment");
        whiteListedUrls2.add("https://www.payumoney.com/txn/#/user/");
        whiteListedUrls2.add("https://mpi.onlinesbi.com/electraSECURE/vbv/MPIEntry.jsp");
        whiteListedUrls2.add("https://netsafe.hdfcbank.com/ACSWeb/com.enstage.entransact.servers.AccessControlServerSSL");
        whiteListedUrls2.add("https://www.citibank.co.in/acspage/cap_nsapi.so");
        whiteListedUrls2.add("https://acs.icicibank.com/acspage/cap");
        whiteListedUrls2.add("https://secure.payu.in/_payment");
        whiteListedUrls2.add("https://www.citibank.co.in/servlets/TransReq");
        whiteListedUrls2.add("https://netsafe.hdfcbank.com/ACSWeb/com.enstage.entransact.servers.AccessControlServerSSL");
        whiteListedUrls2.add("https://netsafe.hdfcbank.com/ACSWeb/jsp/MerchantPost.jsp");
        whiteListedUrls2.add("https://netsafe.hdfcbank.com/ACSWeb/jsp/SCode.jsp");
        whiteListedUrls2.add("https://netsafe.hdfcbank.com/ACSWeb/com.enstage.entransact.servers.AccessControlServerSSL");
        whiteListedUrls2.add("https://netsafe.hdfcbank.com/ACSWeb/jsp/payerAuthOptions.jsp");
        whiteListedUrls2.add("https://cardsecurity.enstage.com/ACSWeb/EnrollWeb/KotakBank/server/AccessControlServer");
        whiteListedUrls2.add("https://cardsecurity.enstage.com/ACSWeb/EnrollWeb/KotakBank/server/OtpServer");
        whiteListedUrls2.add("https://www.citibank.co.in/acspage/cap_nsapi.so");
        whiteListedUrls2.add("https://acs.icicibank.com/acspage/cap");
        whiteListedUrls2.add("https://secureonline.idbibank.com/ACSWeb/EnrollWeb/IDBIBank/server/AccessControlServer");
        whiteListedUrls2.add("https://vpos.amxvpos.com/vpcpay");
        if (getActivity() != null) {
            initAnalytics(getActivity());
        }
        return view;
    }

    public void initAnalytics(Activity activity) {
        this.mAnalytics = new MRAnalytics(activity.getApplicationContext(), "local_cache_analytics_mr");
    }

    private void initViewElements(View view) {
        this.magicProgress = (ProgressBar) view.findViewById(R.id.magic_reload_progress);
        this.retryButton = (ImageView) view.findViewById(R.id.retry_btn);
        this.waitingDotsLayoutParent = (LinearLayout) view.findViewById(R.id.waiting_dots_parent);
        this.magicRetryLayoutParent = (LinearLayout) view.findViewById(R.id.magic_retry_parent);
        this.magicRetryLayoutParent.setVisibility(0);
        this.waitingDotsLayoutParent.setVisibility(8);
        this.retryButton.setOnClickListener(this);
    }

    public void setWebView(WebView wv) {
        this.mWebView = wv;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.retry_btn) {
            performReload();
        }
    }

    public void onAttach(Context context2) {
        super.onAttach(context2);
        try {
            this.activityCallbackHandler = (ActivityCallback) context2;
        } catch (ClassCastException e) {
            StringBuilder sb = new StringBuilder();
            sb.append(context2.toString());
            sb.append(" must implement OnHeadlineSelectedListener");
            throw new ClassCastException(sb.toString());
        }
    }

    private void performReload() {
        StringBuilder sb = new StringBuilder();
        sb.append("PayUWebViewClient.java Reloading URL: ");
        sb.append(this.mWebView.getUrl());
        Log.v("#### PAYU", sb.toString());
        this.reloadUrl = this.mWebView.getUrl();
        if (this.urlListWithPostData.size() <= 0 || !this.urlListWithPostData.containsKey(this.mWebView.getUrl())) {
            if (Util.isNetworkAvailable(this.context)) {
                this.fromOnReceivedError = false;
                this.mWebView.reload();
                addEventAnalytics(MRConstant.MR_USER_INPUT, MRConstant.CLICK_M_RETRY);
                showMagicReloadProgressDialog();
                return;
            }
            Util.showNetworkNotAvailableError(this.context);
        } else if (Util.isNetworkAvailable(this.context)) {
            this.fromOnReceivedError = false;
            WebView webView = this.mWebView;
            webView.postUrl(webView.getUrl(), ((String) this.urlListWithPostData.get(this.mWebView.getUrl())).getBytes());
            resetPayuID();
            addEventAnalytics(MRConstant.MR_USER_INPUT, MRConstant.CLICK_M_RETRY);
            showMagicReloadProgressDialog();
        } else {
            Util.showNetworkNotAvailableError(this.context);
        }
    }

    public void setUrlListWithPostData(Map<String, String> urlListWithPostData2) {
        this.urlListWithPostData = urlListWithPostData2;
    }

    private void showMagicReloadProgressDialog() {
        this.magicRetryLayoutParent.setVisibility(8);
        this.waitingDotsLayoutParent.setVisibility(0);
        this.magicProgress.setVisibility(0);
    }

    private void hideMagicReloadProgressDialog() {
        if (isAdded()) {
            LinearLayout linearLayout = this.waitingDotsLayoutParent;
            if (linearLayout != null) {
                linearLayout.setVisibility(8);
            }
            ProgressBar progressBar = this.magicProgress;
            if (progressBar != null) {
                progressBar.setVisibility(4);
            }
            LinearLayout linearLayout2 = this.magicRetryLayoutParent;
            if (linearLayout2 != null) {
                linearLayout2.setVisibility(0);
            }
        }
    }

    public void onPageStarted(WebView view, String url) {
    }

    public void onPageFinished(WebView webView, String url) {
        if (getActivity() != null && !getActivity().isFinishing() && !this.fromOnReceivedError && this.errorWasReceived && this.reloadUrl != null) {
            this.activityCallbackHandler.hideMagicRetry();
            this.errorWasReceived = true;
        }
    }

    public void onSaveInstanceState(Bundle outState) {
    }

    public void onReceivedError(WebView mWebView2, String failingUrl) {
        try {
            addEventAnalytics(MRConstant.MR_EURL, URLEncoder.encode(failingUrl, Key.STRING_CHARSET_NAME));
            if (this.firstCall == 0) {
                addEventAnalytics(MRConstant.MR_VERION, BuildConfig.VERSION_NAME);
                this.firstCall++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!disableMagicRetry) {
            StringBuilder sb = new StringBuilder();
            sb.append("WebView URL: ");
            sb.append(mWebView2.getUrl());
            sb.append(" FAILING URL: ");
            sb.append(failingUrl);
            L.v("#### PAYU", sb.toString());
            hideItems();
            if (failingUrl == null || !isWhiteListedURL(failingUrl)) {
                this.reloadUrl = null;
                return;
            }
            this.fromOnReceivedError = true;
            ActivityCallback activityCallback = this.activityCallbackHandler;
            if (activityCallback != null) {
                activityCallback.showMagicRetry();
            }
            addEventAnalytics(MRConstant.MR_USER_INPUT, MRConstant.SHOW_M_RETRY);
            this.reloadUrl = mWebView2.getUrl();
        }
    }

    public String getCookie(String cookieName, Context context2) {
        String[] temp;
        String cookieValue = "";
        try {
            String siteName = this.PAYU_DOMAIN;
            CookieManager cookieManager = CookieManager.getInstance();
            if (VERSION.SDK_INT < 21) {
                CookieSyncManager.createInstance(context2);
                CookieSyncManager.getInstance().sync();
            }
            String cookies = cookieManager.getCookie(siteName);
            if (cookies != null) {
                for (String ar1 : cookies.split(";")) {
                    if (ar1.contains(cookieName)) {
                        cookieValue = ar1.split("=")[1];
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cookieValue;
    }

    /* access modifiers changed from: 0000 */
    public String getLogMessage(String key, String value) {
        try {
            JSONObject eventAnalytics = new JSONObject();
            eventAnalytics.put(MRConstant.PAYU_ID, getCookie("PAYUID", this.context));
            eventAnalytics.put("txnid", this.txnID == null ? "" : this.txnID);
            eventAnalytics.put(MRConstant.MERCHANT_KEY, analyticsKey);
            eventAnalytics.put(MRConstant.PAGE_TYPE, "");
            eventAnalytics.put("key", key);
            eventAnalytics.put("value", value);
            eventAnalytics.put("package_name", getActivity().getPackageName());
            eventAnalytics.put("bank", bankName == null ? "" : bankName);
            return eventAnalytics.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addEventAnalytics(String key, String value) {
        try {
            if (getActivity() != null && isAdded() && !isRemoving() && !isDetached() && this.mAnalytics != null) {
                this.mAnalytics.log(getLogMessage(key, value.toLowerCase()));
            }
        } catch (Exception e) {
        }
    }

    private boolean isWhiteListedURL(String url) {
        if (!this.isWhiteListingEnabled) {
            return true;
        }
        for (String whiteListedUrl : whiteListedUrls) {
            if (url != null && url.contains(whiteListedUrl)) {
                StringBuilder sb = new StringBuilder();
                sb.append("WHITELISTED URL FOUND.. SHOWING MAGIC RETRY: ");
                sb.append(url);
                L.v("#### PAYU", sb.toString());
                return true;
            }
        }
        return false;
    }

    private void hideItems() {
        hideMagicReloadProgressDialog();
    }

    public static void disableMagicRetry(boolean disable) {
        disableMagicRetry = disable;
    }

    public static void setWhitelistedURL(List<String> urls) {
        whiteListedUrls.clear();
        StringBuilder sb = new StringBuilder();
        sb.append("MR Cleared whitelisted urls, length: ");
        sb.append(whiteListedUrls.size());
        L.v("#### PAYU", sb.toString());
        whiteListedUrls.addAll(urls);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("MR Updated whitelisted urls, length: ");
        sb2.append(whiteListedUrls.size());
        L.v("#### PAYU", sb2.toString());
    }

    public void isWhiteListingEnabled(boolean enable) {
        this.isWhiteListingEnabled = enable;
    }

    public static void processAndAddWhiteListedUrls(String data2) {
        if (data2 != null && !data2.equalsIgnoreCase("")) {
            String[] urls = data2.split("\\|");
            for (String url : urls) {
                StringBuilder sb = new StringBuilder();
                sb.append("Split Url: ");
                sb.append(url);
                L.v("#### PAYU", sb.toString());
            }
            if (urls != null && urls.length > 0) {
                setWhitelistedURL(Arrays.asList(urls));
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Whitelisted URLs from JS: ");
            sb2.append(data2);
            L.v("#### PAYU", sb2.toString());
        }
    }

    public static void setMRData(String data2, Context context2) {
        if (data2 == null) {
            SharedPreferenceUtil.addBooleanToSharedPreference(context2, SP_MR_FILE_NAME, SP_IS_MR_ENABLED, false);
            disableMagicRetry(true);
            StringBuilder sb = new StringBuilder();
            sb.append("MR SP Setting 1) Disable MR: ");
            sb.append(disableMagicRetry);
            L.v("#### PAYU", sb.toString());
            SharedPreferenceUtil.addStringToSharedPreference(context2, SP_MR_FILE_NAME, SP_WHITELISTED_URLS, "");
            setWhitelistedURL(new ArrayList());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("MR SP Setting 2) Clear white listed urls, length: ");
            sb2.append(whiteListedUrls.size());
            L.v("#### PAYU", sb2.toString());
        } else {
            SharedPreferenceUtil.addBooleanToSharedPreference(context2, SP_MR_FILE_NAME, SP_IS_MR_ENABLED, true);
            disableMagicRetry(false);
            StringBuilder sb3 = new StringBuilder();
            sb3.append("MR SP Setting 1) Disable MR: ");
            sb3.append(disableMagicRetry);
            L.v("#### PAYU", sb3.toString());
            SharedPreferenceUtil.addStringToSharedPreference(context2, SP_MR_FILE_NAME, SP_WHITELISTED_URLS, data2);
            processAndAddWhiteListedUrls(data2);
            StringBuilder sb4 = new StringBuilder();
            sb4.append("MR SP Setting 2) Update white listed urls, length: ");
            sb4.append(whiteListedUrls.size());
            L.v("#### PAYU", sb4.toString());
        }
        L.v("#### PAYU", "MR DATA UPDATED IN SHARED PREFERENCES");
    }

    public void initMRSettingsFromSharedPreference(Context context2) {
        disableMagicRetry(!SharedPreferenceUtil.getBooleanFromSharedPreference(context2, SP_MR_FILE_NAME, SP_IS_MR_ENABLED, !disableMagicRetry));
        processAndAddWhiteListedUrls(SharedPreferenceUtil.getStringFromSharedPreference(context2, SP_MR_FILE_NAME, SP_WHITELISTED_URLS, ""));
    }

    public static boolean isUrlWhiteListed(String currentUrl) {
        for (String whiteListedUrl : whiteListedUrls) {
            if (currentUrl != null && currentUrl.contains(whiteListedUrl)) {
                return true;
            }
        }
        return false;
    }

    private void resetPayuID() {
        clearCookie();
    }

    public void clearCookie() {
        CookieManager cookieManager = CookieManager.getInstance();
        if (VERSION.SDK_INT >= 21) {
            cookieManager.removeSessionCookies(null);
        } else {
            cookieManager.removeSessionCookie();
        }
    }
}
