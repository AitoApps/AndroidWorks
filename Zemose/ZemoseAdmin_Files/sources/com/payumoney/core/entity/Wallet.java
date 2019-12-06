package com.payumoney.core.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Wallet implements Parcelable {
    public static final Creator<Wallet> CREATOR = new Creator<Wallet>() {
        public Wallet createFromParcel(Parcel in) {
            return new Wallet(in);
        }

        public Wallet[] newArray(int size) {
            return new Wallet[size];
        }
    };
    double amount;
    double availableAmount;
    double maxLimit;
    String message;
    double minLimit;
    double status;

    public Wallet() {
    }

    protected Wallet(Parcel in) {
        this.amount = in.readDouble();
        this.availableAmount = in.readDouble();
        this.minLimit = in.readDouble();
        this.maxLimit = in.readDouble();
        this.status = in.readDouble();
        this.message = in.readString();
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount2) {
        this.amount = amount2;
    }

    public double getAvailableAmount() {
        return this.availableAmount;
    }

    public void setAvailableAmount(double availableAmount2) {
        this.availableAmount = availableAmount2;
    }

    public double getMinLimit() {
        return this.minLimit;
    }

    public void setMinLimit(double minLimit2) {
        this.minLimit = minLimit2;
    }

    public double getMaxLimit() {
        return this.maxLimit;
    }

    public void setMaxLimit(double maxLimit2) {
        this.maxLimit = maxLimit2;
    }

    public double getStatus() {
        return this.status;
    }

    public void setStatus(double status2) {
        this.status = status2;
    }

    public String getMesssage() {
        return this.message;
    }

    public void setMesssage(String messsage) {
        this.message = messsage;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.amount);
        dest.writeDouble(this.availableAmount);
        dest.writeDouble(this.minLimit);
        dest.writeDouble(this.maxLimit);
        dest.writeDouble(this.status);
        dest.writeString(this.message);
    }
}
