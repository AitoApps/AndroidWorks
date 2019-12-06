package com.payumoney.core.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EmiTenure implements Parcelable {
    public static final Creator<EmiTenure> CREATOR = new Creator<EmiTenure>() {
        public EmiTenure createFromParcel(Parcel in) {
            return new EmiTenure(in);
        }

        public EmiTenure[] newArray(int size) {
            return new EmiTenure[size];
        }
    };
    private String bank;
    private double emiBankInterest;
    private double emiInterestPaid;
    private double emiValue;
    private String pgID;
    private String tenureId;
    private String title;
    private double transactionAmount;

    public EmiTenure() {
    }

    public String getTenureId() {
        return this.tenureId;
    }

    public void setTenureId(String tenureId2) {
        this.tenureId = tenureId2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public double getEmiBankInterest() {
        return this.emiBankInterest;
    }

    public void setEmiBankInterest(double emiBankInterest2) {
        this.emiBankInterest = emiBankInterest2;
    }

    public double getEmiInterestPaid() {
        return this.emiInterestPaid;
    }

    public void setEmiInterestPaid(double emiInterestPaid2) {
        this.emiInterestPaid = emiInterestPaid2;
    }

    public String getBank() {
        return this.bank;
    }

    public void setBank(String bank2) {
        this.bank = bank2;
    }

    public String getPgID() {
        return this.pgID;
    }

    public void setPgID(String pgID2) {
        this.pgID = pgID2;
    }

    public double getTransactionAmount() {
        return this.transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount2) {
        this.transactionAmount = transactionAmount2;
    }

    public double getEmiValue() {
        return this.emiValue;
    }

    public void setEmiValue(double emiValue2) {
        this.emiValue = emiValue2;
    }

    protected EmiTenure(Parcel in) {
        this.tenureId = in.readString();
        this.title = in.readString();
        this.emiBankInterest = in.readDouble();
        this.emiInterestPaid = in.readDouble();
        this.bank = in.readString();
        this.pgID = in.readString();
        this.transactionAmount = in.readDouble();
        this.emiValue = in.readDouble();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tenureId);
        dest.writeString(this.title);
        dest.writeDouble(this.emiBankInterest);
        dest.writeDouble(this.emiInterestPaid);
        dest.writeString(this.bank);
        dest.writeString(this.pgID);
        dest.writeDouble(this.transactionAmount);
        dest.writeDouble(this.emiValue);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append('|');
        sb.append(this.title);
        sb.append('|');
        sb.append(this.emiBankInterest);
        sb.append('|');
        sb.append(this.emiValue);
        return sb.toString();
    }
}
