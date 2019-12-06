package com.payumoney.core.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EmiThreshold implements Parcelable {
    public static final Creator<EmiThreshold> CREATOR = new Creator<EmiThreshold>() {
        public EmiThreshold createFromParcel(Parcel in) {
            return new EmiThreshold(in);
        }

        public EmiThreshold[] newArray(int size) {
            return new EmiThreshold[size];
        }
    };
    private final String emiBankCode;
    private final String emiBankTitle;
    private final double threshouldAmount;

    public EmiThreshold(String emiBankCode2, double threshouldAmount2, String emiBankTitle2) {
        this.emiBankCode = emiBankCode2;
        this.threshouldAmount = threshouldAmount2;
        this.emiBankTitle = emiBankTitle2;
    }

    protected EmiThreshold(Parcel in) {
        this.emiBankCode = in.readString();
        this.threshouldAmount = in.readDouble();
        this.emiBankTitle = in.readString();
    }

    public String getEmiBankCode() {
        return this.emiBankCode;
    }

    public String getEmiBankTitle() {
        return this.emiBankTitle;
    }

    public double getThreshouldAmount() {
        return this.threshouldAmount;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.emiBankCode);
        dest.writeDouble(this.threshouldAmount);
        dest.writeString(this.emiBankTitle);
    }
}
