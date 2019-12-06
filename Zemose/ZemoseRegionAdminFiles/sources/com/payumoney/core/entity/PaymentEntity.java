package com.payumoney.core.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;

public class PaymentEntity implements Parcelable {
    public static final Creator<PaymentEntity> CREATOR = new Creator<PaymentEntity>() {
        public PaymentEntity createFromParcel(Parcel in) {
            return new PaymentEntity(in);
        }

        public PaymentEntity[] newArray(int size) {
            return new PaymentEntity[size];
        }
    };
    private String cidCode;
    private String code;
    private List<EmiTenure> emiTenures;
    private double emiThresholdAmount;
    private String pgID;
    private String shortTitle;
    private String title;
    private int upStatus;

    public PaymentEntity() {
    }

    protected PaymentEntity(Parcel in) {
        this.upStatus = in.readInt();
        this.code = in.readString();
        this.title = in.readString();
        this.shortTitle = in.readString();
        this.pgID = in.readString();
        this.cidCode = in.readString();
        this.emiTenures = new ArrayList();
        in.readList(this.emiTenures, EmiTenure.class.getClassLoader());
        this.emiThresholdAmount = in.readDouble();
    }

    public int getUpStatus() {
        return this.upStatus;
    }

    public void setUpStatus(int upStatus2) {
        this.upStatus = upStatus2;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code2) {
        this.code = code2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public String getPgID() {
        return this.pgID;
    }

    public void setPgID(String pgID2) {
        this.pgID = pgID2;
    }

    public String getCidCode() {
        return this.cidCode;
    }

    public void setCidCode(String cidCode2) {
        this.cidCode = cidCode2;
    }

    public String getShortTitle() {
        return this.shortTitle;
    }

    public void setShortTitle(String shortTitle2) {
        this.shortTitle = shortTitle2;
    }

    public List<EmiTenure> getEmiTenures() {
        return this.emiTenures;
    }

    public void setEmiTenures(List<EmiTenure> emiTenures2) {
        this.emiTenures = emiTenures2;
    }

    public double getEmiThresholdAmount() {
        return this.emiThresholdAmount;
    }

    public void setEmiThresholdAmount(double emiThresholdAmount2) {
        this.emiThresholdAmount = emiThresholdAmount2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.upStatus);
        dest.writeString(this.code);
        dest.writeString(this.title);
        dest.writeString(this.shortTitle);
        dest.writeString(this.pgID);
        dest.writeString(this.cidCode);
        dest.writeList(this.emiTenures);
        dest.writeDouble(this.emiThresholdAmount);
    }

    public boolean equals(Object o) {
        return getCode().equalsIgnoreCase(((PaymentEntity) o).getCode());
    }

    public int hashCode() {
        return Integer.parseInt(getCode()) + 5;
    }
}
