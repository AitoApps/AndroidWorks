package com.payu.magicretry.WebClient;

import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Message;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.payu.magicretry.Helpers.L;
import com.payu.magicretry.MagicRetryFragment;

public class MagicRetryWebViewClient extends WebViewClient {
    private MagicRetryFragment magicRetryFragment;

    public MagicRetryWebViewClient() {
    }

    public MagicRetryWebViewClient(MagicRetryFragment fragment) {
        this.magicRetryFragment = fragment;
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        StringBuilder sb = new StringBuilder();
        sb.append("MagicRetryWebViewClient.java: onPageStarted: URL ");
        sb.append(url);
        L.v("#### PAYU", sb.toString());
        super.onPageStarted(view, url, favicon);
        MagicRetryFragment magicRetryFragment2 = this.magicRetryFragment;
        if (magicRetryFragment2 != null) {
            magicRetryFragment2.onPageStarted(view, url);
        }
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        StringBuilder sb = new StringBuilder();
        sb.append("MagicRetryWebViewClient.java: shouldOverrideUrlLoading: URL ");
        sb.append(url);
        L.v("#### PAYU", sb.toString());
        return super.shouldOverrideUrlLoading(view, url);
    }

    public void onPageFinished(WebView view, String url) {
        StringBuilder sb = new StringBuilder();
        sb.append("MagicRetryWebViewClient.java: onPageFinished: URL ");
        sb.append(url);
        L.v("#### PAYU", sb.toString());
        super.onPageFinished(view, url);
        MagicRetryFragment magicRetryFragment2 = this.magicRetryFragment;
        if (magicRetryFragment2 != null) {
            magicRetryFragment2.onPageFinished(view, url);
        }
    }

    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        resend.sendToTarget();
    }

    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        StringBuilder sb = new StringBuilder();
        sb.append("MagicRetryWebViewClient.java: onReceivedError: URL ");
        sb.append(view.getUrl());
        L.v("#### PAYU", sb.toString());
        if (this.magicRetryFragment != null && request.isForMainFrame()) {
            this.magicRetryFragment.onReceivedError(view, request.getUrl().toString());
        }
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        StringBuilder sb = new StringBuilder();
        sb.append("MagicRetryWebViewClient.java: onReceivedError: URL ");
        sb.append(view.getUrl());
        L.v("#### PAYU", sb.toString());
        if (VERSION.SDK_INT < 23) {
            MagicRetryFragment magicRetryFragment2 = this.magicRetryFragment;
            if (magicRetryFragment2 != null) {
                magicRetryFragment2.onReceivedError(view, failingUrl);
            }
        }
    }
}
