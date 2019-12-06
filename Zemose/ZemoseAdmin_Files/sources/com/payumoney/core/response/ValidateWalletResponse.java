package com.payumoney.core.response;

public class ValidateWalletResponse extends PayUMoneyAPIResponse {
    private String a;
    private String b;
    private String c;
    private String d;

    public String getName() {
        return this.a;
    }

    public void setName(String name) {
        this.a = name;
    }

    public String getEmail() {
        return this.b;
    }

    public void setEmail(String email) {
        this.b = email;
    }

    public String getPhone() {
        return this.c;
    }

    public void setPhone(String phone) {
        this.c = phone;
    }

    public String getUsername() {
        return this.d;
    }

    public void setUsername(String username) {
        this.d = username;
    }
}
