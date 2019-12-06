package com.payu.custombrowser;

import android.app.AlertDialog.Builder;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.webkit.WebView;
import com.payu.custombrowser.bean.CustomBrowserResultData;
import com.payu.magicretry.MagicRetryFragment;

public class PayUCustomBrowserCallback {
    private String a;
    private String b;

    public void onPaymentFailure(String payuResult, String merchantResponse) {
    }

    public void onPaymentTerminate() {
    }

    public void onPaymentSuccess(String payuResult, String merchantResponse) {
    }

    public void onCBErrorReceived(int code, String errormsg) {
    }

    public void setCBProperties(@NonNull String postURL, @NonNull String postData) {
        setPostData(postData);
        setPostURL(postURL);
    }

    public void setCBProperties(WebView webview, Bank payUCustomBrowser) {
    }

    public void onBackButton(Builder alertDialogBuilder) {
    }

    public void onBackApprove() {
    }

    public void onBackDismiss() {
    }

    public void initializeMagicRetry(Bank payUCustomBrowser, WebView webview, MagicRetryFragment magicRetryFragment) {
    }

    public String getPostData() {
        return this.a;
    }

    public void setPostData(String postData) {
        this.a = postData;
    }

    public String getPostURL() {
        return this.b;
    }

    public void setPostURL(String postURL) {
        this.b = postURL;
    }

    public void getNavigationDrawerObject(DrawerLayout drawerLayout) {
    }

    public void isPaymentOptionAvailable(CustomBrowserResultData resultData) {
    }

    public void onVpaEntered(String vpa, PackageListDialogFragment packageListDialogFragment) {
    }
}
