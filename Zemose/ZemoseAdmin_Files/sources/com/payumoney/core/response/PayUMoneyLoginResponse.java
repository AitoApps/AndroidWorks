package com.payumoney.core.response;

public class PayUMoneyLoginResponse extends PayUMoneyAPIResponse {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;

    public String getAccessToken() {
        return this.a;
    }

    public void setAccessToken(String accessToken) {
        this.a = accessToken;
    }

    public String getTokenType() {
        return this.b;
    }

    public void setTokenType(String tokenType) {
        this.b = tokenType;
    }

    public String getRefreshToken() {
        return this.c;
    }

    public void setRefreshToken(String refreshToken) {
        this.c = refreshToken;
    }

    public String getExpiresIn() {
        return this.d;
    }

    public void setExpiresIn(String expiresIn) {
        this.d = expiresIn;
    }

    public String getScope() {
        return this.e;
    }

    public void setScope(String scope) {
        this.e = scope;
    }
}
