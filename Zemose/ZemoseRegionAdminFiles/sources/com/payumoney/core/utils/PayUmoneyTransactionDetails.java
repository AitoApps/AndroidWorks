package com.payumoney.core.utils;

public class PayUmoneyTransactionDetails {
    private static PayUmoneyTransactionDetails c;
    private String a;
    private double b;

    public static PayUmoneyTransactionDetails getInstance() {
        return c;
    }

    public static void initPayUMoneyTransaction() {
        c = new PayUmoneyTransactionDetails();
    }

    public String getPublicKey() {
        return this.a;
    }

    public void setPublicKey(String publicKey) {
        this.a = publicKey;
    }

    public double getWalletAmount() {
        return this.b;
    }

    public void setWalletAmount(double walletAmount) {
        this.b = walletAmount;
    }
}
