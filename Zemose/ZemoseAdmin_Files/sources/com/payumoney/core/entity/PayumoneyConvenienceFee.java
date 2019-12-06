package com.payumoney.core.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Map;

public class PayumoneyConvenienceFee implements Parcelable {
    public static final Creator<PayumoneyConvenienceFee> CREATOR = new Creator<PayumoneyConvenienceFee>() {
        public PayumoneyConvenienceFee createFromParcel(Parcel in) {
            return new PayumoneyConvenienceFee(in);
        }

        public PayumoneyConvenienceFee[] newArray(int size) {
            return new PayumoneyConvenienceFee[size];
        }
    };
    private Map<String, Map<String, Double>> convenienceFeeMap;

    public PayumoneyConvenienceFee() {
    }

    protected PayumoneyConvenienceFee(Parcel in) {
        this.convenienceFeeMap = in.readHashMap(null);
    }

    public void setConvenienceFeeMap(Map<String, Map<String, Double>> convenienceFeeMap2) {
        this.convenienceFeeMap = convenienceFeeMap2;
    }

    public Map<String, Map<String, Double>> getConvenienceFeeMap() {
        return this.convenienceFeeMap;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(this.convenienceFeeMap);
    }
}
