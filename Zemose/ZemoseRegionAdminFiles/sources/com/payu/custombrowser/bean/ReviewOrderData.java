package com.payu.custombrowser.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ReviewOrderData implements Parcelable {
    public static final Creator<ReviewOrderData> CREATOR = new Creator<ReviewOrderData>() {
        /* renamed from: a */
        public ReviewOrderData createFromParcel(Parcel parcel) {
            return new ReviewOrderData(parcel);
        }

        /* renamed from: a */
        public ReviewOrderData[] newArray(int i) {
            return new ReviewOrderData[i];
        }
    };
    private String a;
    private String b;

    public String getKey() {
        return this.a;
    }

    public String getValue() {
        return this.b;
    }

    public ReviewOrderData(String key, String value) {
        this.a = key;
        this.b = value;
    }

    protected ReviewOrderData(Parcel in) {
        this.a = in.readString();
        this.b = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.a);
        dest.writeString(this.b);
    }

    public int describeContents() {
        return 0;
    }
}
