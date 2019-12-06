package com.payumoney.core.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MerchantDetails implements Parcelable {
    public static final Creator<MerchantDetails> CREATOR = new Creator<MerchantDetails>() {
        public MerchantDetails createFromParcel(Parcel in) {
            return new MerchantDetails(in);
        }

        public MerchantDetails[] newArray(int size) {
            return new MerchantDetails[size];
        }
    };
    private String displayName;
    private String logoUrl;
    private String merchantId;

    public MerchantDetails() {
    }

    protected MerchantDetails(Parcel in) {
        this.logoUrl = in.readString();
        this.displayName = in.readString();
        this.merchantId = in.readString();
    }

    public String getLogoUrl() {
        return this.logoUrl;
    }

    public void setLogoUrl(String logoUrl2) {
        this.logoUrl = logoUrl2;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName2) {
        this.displayName = displayName2;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public void setMerchantId(String merchantId2) {
        this.merchantId = merchantId2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.logoUrl);
        parcel.writeString(this.displayName);
        parcel.writeString(this.merchantId);
    }
}
