package com.payu.custombrowser.bean;

import com.payu.custombrowser.util.PaymentOption;

public class CustomBrowserResultData {
    String a;
    String b;
    boolean c;
    PaymentOption d;
    String e;

    public boolean isPaymentOptionAvailable() {
        return this.c;
    }

    public void setPaymentOptionAvailable(boolean paymentOptionAvailable) {
        this.c = paymentOptionAvailable;
    }

    public String getSamsungPayVpa() {
        return this.a;
    }

    public void setSamsungPayVpa(String vpa) {
        this.a = vpa;
    }

    public String getJsonResult() {
        return this.b;
    }

    public void setJsonResult(String jsonResult) {
        this.b = jsonResult;
    }

    public PaymentOption getPaymentOption() {
        return this.d;
    }

    public void setPaymentOption(PaymentOption paymentOption) {
        this.d = paymentOption;
    }

    public String getErrorMessage() {
        return this.e;
    }

    public void setErrorMessage(String errorMessage) {
        this.e = errorMessage;
    }
}
