package com.payumoney.core.entity;

public enum SdkIssuer {
    VISA,
    MASTERCARD,
    MAESTRO,
    DISCOVER,
    AMEX,
    DINER,
    UNKNOWN,
    JCB,
    LASER,
    RUPAY;

    public static SdkIssuer getIssuer(String issuer) {
        SdkIssuer[] values;
        for (SdkIssuer sdkIssuer : values()) {
            if (sdkIssuer.name().equals(issuer)) {
                return sdkIssuer;
            }
        }
        return UNKNOWN;
    }
}
