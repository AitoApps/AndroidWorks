package com.payu.custombrowser.util;

public enum PaymentOption {
    SAMSUNGPAY("SAMPAY", "com.payu.samsungpay.SamsungWrapper"),
    PHONEPE("PPINTENT", "com.payu.phonepe.PhonePeWrapper");
    
    private String packageName;
    private String paymentName;

    public String getPackageName() {
        return this.packageName;
    }

    private PaymentOption(String paymentName2, String packageName2) {
        this.paymentName = paymentName2;
        this.packageName = packageName2;
    }

    public String getPaymentName() {
        return this.paymentName;
    }
}
