package com.payumoney.core.request;

public class LoginParamsRequest {
    private String a;
    private String b;

    public String getUserName() {
        return this.a;
    }

    public void setUserName(String userName) {
        this.a = userName;
    }

    public String getPassword() {
        return this.b;
    }

    public void setPassword(String password) {
        this.b = password;
    }
}
