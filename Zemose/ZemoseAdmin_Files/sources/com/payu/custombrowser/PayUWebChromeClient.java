package com.payu.custombrowser;

import android.os.Message;
import android.support.annotation.NonNull;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class PayUWebChromeClient extends WebChromeClient {
    private Bank a;
    private boolean b = false;

    public PayUWebChromeClient(@NonNull Bank bank) {
        this.a = bank;
    }

    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        return false;
    }

    public void onProgressChanged(WebView view, int newProgress) {
        Bank bank = this.a;
        if (bank != null) {
            if (!this.b && newProgress < 100) {
                this.b = true;
                bank.onPageStarted();
            } else if (newProgress == 100) {
                this.a.onPageStarted();
                this.b = false;
                this.a.onPageFinished();
            }
            this.a.onProgressChanged(newProgress);
        }
    }
}
