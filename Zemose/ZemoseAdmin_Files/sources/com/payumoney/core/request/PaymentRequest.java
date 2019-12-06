package com.payumoney.core.request;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PaymentRequest implements Parcelable {
    public static final Creator<PaymentRequest> CREATOR = new Creator<PaymentRequest>() {
        public PaymentRequest createFromParcel(Parcel in) {
            return new PaymentRequest(in);
        }

        public PaymentRequest[] newArray(int size) {
            return new PaymentRequest[size];
        }
    };
    private String bankCode;
    private String cardName;
    private String cardNumber;
    private String cardtoken;
    private double convenienceFee;
    private String countryCode;
    private String cvv;
    private String expiryMonth;
    private String expiryYear;
    private boolean isEmiPayment;
    private boolean isSplitPayment;
    private String paymentID;
    private String pg;
    private String processor;
    private boolean storeCard;
    private String storeCardId;
    private String vpa;

    public String getVpa() {
        return this.vpa;
    }

    public void setVpa(String vpa2) {
        this.vpa = vpa2;
    }

    public double getConvenienceFee() {
        return this.convenienceFee;
    }

    public void setConvenienceFee(double convenienceFee2) {
        this.convenienceFee = convenienceFee2;
    }

    public PaymentRequest() {
    }

    protected PaymentRequest(Parcel in) {
        this.paymentID = in.readString();
        this.pg = in.readString();
        this.bankCode = in.readString();
        this.cardNumber = in.readString();
        this.cvv = in.readString();
        this.expiryMonth = in.readString();
        this.expiryYear = in.readString();
        this.cardName = in.readString();
        this.cardtoken = in.readString();
        boolean z = true;
        this.storeCard = in.readByte() != 0;
        this.isSplitPayment = in.readByte() != 0;
        this.storeCardId = in.readString();
        this.convenienceFee = in.readDouble();
        this.processor = in.readString();
        this.countryCode = in.readString();
        this.vpa = in.readString();
        if (in.readByte() == 0) {
            z = false;
        }
        this.isEmiPayment = z;
    }

    public boolean isSplitPayment() {
        return this.isSplitPayment;
    }

    public void setSplitPayment(boolean splitPayment) {
        this.isSplitPayment = splitPayment;
    }

    public boolean isStoreCard() {
        return this.storeCard;
    }

    public void setStoreCard(boolean storeCard2) {
        this.storeCard = storeCard2;
    }

    public String getStoreCardId() {
        return this.storeCardId;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode2) {
        this.countryCode = countryCode2;
    }

    public void setStoreCardId(String storeCardId2) {
        this.storeCardId = storeCardId2;
    }

    public String getPaymentID() {
        return this.paymentID;
    }

    public void setPaymentID(String paymentID2) {
        this.paymentID = paymentID2;
    }

    public String getPg() {
        return this.pg;
    }

    public void setPg(String pg2) {
        this.pg = pg2;
    }

    public String getBankCode() {
        return this.bankCode;
    }

    public void setBankCode(String bankCode2) {
        this.bankCode = bankCode2;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber2) {
        this.cardNumber = cardNumber2;
    }

    public String getCvv() {
        return this.cvv;
    }

    public void setCvv(String cvv2) {
        this.cvv = cvv2;
    }

    public String getExpiryMonth() {
        return this.expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth2) {
        this.expiryMonth = expiryMonth2;
    }

    public String getExpiryYear() {
        return this.expiryYear;
    }

    public void setExpiryYear(String expiryYear2) {
        this.expiryYear = expiryYear2;
    }

    public String getCardName() {
        return this.cardName;
    }

    public void setCardName(String cardName2) {
        this.cardName = cardName2;
    }

    public String getCardtoken() {
        return this.cardtoken;
    }

    public void setCardtoken(String cardtoken2) {
        this.cardtoken = cardtoken2;
    }

    public String getProcessor() {
        return this.processor;
    }

    public void setProcessor(String processor2) {
        this.processor = processor2;
    }

    public boolean isEmiPayment() {
        return this.isEmiPayment;
    }

    public void setEmiPayment(boolean emiPayment) {
        this.isEmiPayment = emiPayment;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.paymentID);
        dest.writeString(this.pg);
        dest.writeString(this.bankCode);
        dest.writeString(this.cardNumber);
        dest.writeString(this.cvv);
        dest.writeString(this.expiryMonth);
        dest.writeString(this.expiryYear);
        dest.writeString(this.cardName);
        dest.writeString(this.cardtoken);
        dest.writeByte(this.storeCard ? (byte) 1 : 0);
        dest.writeByte(this.isSplitPayment ? (byte) 1 : 0);
        dest.writeString(this.storeCardId);
        dest.writeDouble(this.convenienceFee);
        dest.writeString(this.processor);
        dest.writeString(this.countryCode);
        dest.writeString(this.vpa);
        dest.writeByte(this.isEmiPayment ? (byte) 1 : 0);
    }
}
