package com.payu.custombrowser;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.payu.custombrowser.util.CBConstant;
import com.payu.custombrowser.util.CBUtil;

public class PayUSurePayWebViewClient extends WebViewClient {
    private boolean a = true;
    private boolean b = false;
    private String c = "";
    private Bank d;

    public PayUSurePayWebViewClient(@NonNull Bank bank, @NonNull String merchantKey) {
        this.d = bank;
        if (Bank.keyAnalytics == null) {
            Bank.keyAnalytics = merchantKey;
        }
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        this.a = false;
        Bank bank = this.d;
        if (bank != null) {
            bank.onPageStartedWebclient(url);
        }
    }

    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (!this.b) {
            this.a = true;
        }
        if (url.equals(this.c)) {
            this.a = true;
            this.b = false;
        } else {
            this.b = false;
        }
        if (VERSION.SDK_INT >= 19 && !this.d.isSurePayValueLoaded && this.d.isS2SHtmlSupport) {
            String stringSharedPreference = new CBUtil().getStringSharedPreference(this.d.getContext(), CBConstant.SUREPAY_S2S);
            if (!TextUtils.isEmpty(stringSharedPreference)) {
                StringBuilder sb = new StringBuilder();
                sb.append("javascript:");
                sb.append(stringSharedPreference);
                sb.append("()");
                view.loadUrl(sb.toString());
            } else {
                view.evaluateJavascript(this.d.getResources().getString(R.string.surepay_js), null);
            }
        }
        Bank bank = this.d;
        if (bank != null) {
            bank.onPageFinishWebclient(url);
        }
    }

    public void onLoadResource(WebView view, String url) {
        Bank bank = this.d;
        if (bank != null) {
            bank.onLoadResourse(view, url);
        }
        super.onLoadResource(view, url);
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (failingUrl != null && this.d != null && !description.contentEquals(CBConstant.ERR_CONNECTION_RESET) && VERSION.SDK_INT < 23) {
            this.d.onReceivedErrorWebClient(errorCode, description);
        }
    }

    @TargetApi(23)
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (this.d != null && !error.getDescription().toString().contentEquals(CBConstant.ERR_CONNECTION_RESET)) {
            this.d.onReceivedErrorWebClient(error.getErrorCode(), error.getDescription().toString());
        }
    }

    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        this.d.onReceivedSslError(view, handler, error);
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        this.c = url;
        if (CBUtil.isPlayStoreUrl(url)) {
            CBUtil.launchPlayStore(this.d.getContext(), url, CBUtil.getWebViewVersion(view));
            return true;
        } else if (url.startsWith(CBConstant.DEEP_LINK_INTENT_URI)) {
            return true;
        } else {
            if (!this.a) {
                this.b = true;
            }
            this.a = false;
            Bank bank = this.d;
            if (bank != null) {
                bank.onOverrideURL(url);
            }
            return false;
        }
    }
}
