package com.payumoney.core.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class BinDetail extends PayUMoneyAPIResponse implements Parcelable {
    public static final Creator<BinDetail> CREATOR = new Creator<BinDetail>() {
        public BinDetail createFromParcel(Parcel in) {
            return new BinDetail(in);
        }

        public BinDetail[] newArray(int size) {
            return new BinDetail[size];
        }
    };
    private String bankCode;
    private String bankName;
    private String binOwner;
    private String cardBin;
    private String cardProgram;
    private String category;
    private String countryCode;

    public BinDetail() {
    }

    protected BinDetail(Parcel in) {
        this.cardBin = in.readString();
        this.binOwner = in.readString();
        this.category = in.readString();
        this.bankName = in.readString();
        this.cardProgram = in.readString();
        this.countryCode = in.readString();
        this.bankCode = in.readString();
    }

    public String getBankCode() {
        return this.bankCode;
    }

    public void setBankCode(String bankCode2) {
        this.bankCode = bankCode2;
    }

    public String getCardBin() {
        return this.cardBin;
    }

    public void setCardBin(String cardBin2) {
        this.cardBin = cardBin2;
    }

    public String getBinOwner() {
        return this.binOwner;
    }

    public void setBinOwner(String binOwner2) {
        this.binOwner = binOwner2;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category2) {
        this.category = category2;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName2) {
        this.bankName = bankName2;
    }

    public String getCardProgram() {
        return this.cardProgram;
    }

    public void setCardProgram(String cardProgram2) {
        this.cardProgram = cardProgram2;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode2) {
        this.countryCode = countryCode2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cardBin);
        dest.writeString(this.binOwner);
        dest.writeString(this.category);
        dest.writeString(this.bankName);
        dest.writeString(this.cardProgram);
        dest.writeString(this.countryCode);
        dest.writeString(this.bankCode);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append('|');
        sb.append(this.cardBin);
        sb.append('|');
        sb.append(this.binOwner);
        sb.append('|');
        sb.append(this.category);
        sb.append('|');
        sb.append(this.bankName);
        sb.append('|');
        sb.append(this.cardProgram);
        sb.append('|');
        sb.append(this.countryCode);
        sb.append('|');
        sb.append(this.bankCode);
        return sb.toString();
    }
}
