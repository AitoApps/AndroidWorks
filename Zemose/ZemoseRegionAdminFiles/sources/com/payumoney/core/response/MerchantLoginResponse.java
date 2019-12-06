package com.payumoney.core.response;

public class MerchantLoginResponse extends PayUMoneyAPIResponse {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;

    public String getAddedOn() {
        return this.e;
    }

    public void setAddedOn(String addedOn) {
        this.e = addedOn;
    }

    public String getUpdatedBy() {
        return this.f;
    }

    public void setUpdatedBy(String updatedBy) {
        this.f = updatedBy;
    }

    public String getUpdatedOn() {
        return this.g;
    }

    public void setUpdatedOn(String updatedOn) {
        this.g = updatedOn;
    }

    public String getMerchantparamsId() {
        return this.a;
    }

    public void setMerchantparamsId(String merchantparamsId) {
        this.a = merchantparamsId;
    }

    public String getMerchantId() {
        return this.b;
    }

    public void setMerchantId(String merchantId) {
        this.b = merchantId;
    }

    public String getParamKey() {
        return this.c;
    }

    public void setParamKey(String paramKey) {
        this.c = paramKey;
    }

    public String getParamsValue() {
        return this.d;
    }

    public void setParamsValue(String paramsValue) {
        this.d = paramsValue;
    }
}
