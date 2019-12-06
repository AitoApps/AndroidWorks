package com.payu.custombrowser.upiintent;

public enum Payment {
    TEZ("Tez", "com.google.android.apps.nbu.paisa.user", true, 19),
    GENERIC_INTENT("INTENT", "", false, 16);
    
    private boolean isWebFlowSupported;
    private int minSdk;
    private String packageName;
    private String paymentName;

    private Payment(String paymentName2, String packageName2, boolean isWebFlowSupported2, int minSdk2) {
        this.paymentName = paymentName2;
        this.packageName = packageName2;
        this.isWebFlowSupported = isWebFlowSupported2;
        this.minSdk = minSdk2;
    }

    public String getPaymentName() {
        return this.paymentName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public boolean isWebFlowSupported() {
        return this.isWebFlowSupported;
    }

    public void setWebFlowSupported(boolean webFlowSupported) {
        this.isWebFlowSupported = webFlowSupported;
    }

    public int getMinSdk() {
        return this.minSdk;
    }
}
