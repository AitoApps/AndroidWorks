package com.payu.custombrowser.bean;

import com.payu.custombrowser.PayUCustomBrowserCallback;
import com.payu.custombrowser.util.PaymentOption;
import java.util.Set;

public enum b {
    SINGLETON;
    
    private Set<PaymentOption> paymentOptionSet;
    private PayUCustomBrowserCallback payuCustomBrowserCallback;

    public PayUCustomBrowserCallback getPayuCustomBrowserCallback() {
        return this.payuCustomBrowserCallback;
    }

    public void setPayuCustomBrowserCallback(PayUCustomBrowserCallback payuCustomBrowserCallback2) {
        this.payuCustomBrowserCallback = payuCustomBrowserCallback2;
    }

    public boolean isPaymentOptionAvailabilityCalled(PaymentOption paymentOptionName) {
        return this.paymentOptionSet.contains(paymentOptionName);
    }

    public void setPaymentOption(PaymentOption paymentOptionName) {
        this.paymentOptionSet.add(paymentOptionName);
    }

    public void removePaymentOption(PaymentOption paymentOption) {
        this.paymentOptionSet.remove(paymentOption);
    }
}
