package com.payumoney.core.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CardDetail extends SdkEntity implements Parcelable {
    public static final Creator<CardDetail> CREATOR = new Creator<CardDetail>() {
        public CardDetail createFromParcel(Parcel in) {
            return new CardDetail(in);
        }

        public CardDetail[] newArray(int size) {
            return new CardDetail[size];
        }
    };
    private String mLabel = null;
    private String mMode = null;
    private String mName = null;
    private String mNumber = null;
    private String mToken = null;
    private boolean oneClickCheckout;
    private String pg;
    private boolean rewardPoint;
    private String type;

    public CardDetail() {
    }

    protected CardDetail(Parcel in) {
        this.mName = in.readString();
        this.mNumber = in.readString();
        this.mLabel = in.readString();
        this.mToken = in.readString();
        this.mMode = in.readString();
        this.pg = in.readString();
        this.type = in.readString();
        boolean z = true;
        this.oneClickCheckout = in.readByte() != 0;
        if (in.readByte() == 0) {
            z = false;
        }
        this.rewardPoint = z;
    }

    public static boolean isValidNumber(String number) {
        boolean z = false;
        if (number.replaceAll("0", "").trim().length() == 0) {
            return false;
        }
        String stringBuffer = new StringBuffer(number).reverse().toString();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < stringBuffer.length(); i3++) {
            int digit = Character.digit(stringBuffer.charAt(i3), 10);
            if (i3 % 2 == 0) {
                i += digit;
            } else {
                i2 += digit * 2;
                if (digit >= 5) {
                    i2 -= 9;
                }
            }
        }
        if ((i + i2) % 10 == 0) {
            z = true;
        }
        return z;
    }

    public static boolean isAmex(String number) {
        return number.startsWith("34") || number.startsWith("37");
    }

    public boolean isOneClickCheckout() {
        return this.oneClickCheckout;
    }

    public void setOneClickCheckout(boolean oneClickCheckout2) {
        this.oneClickCheckout = oneClickCheckout2;
    }

    public boolean isRewardPoint() {
        return this.rewardPoint;
    }

    public void setRewardPoint(boolean rewardPoint2) {
        this.rewardPoint = rewardPoint2;
    }

    public String getPg() {
        return this.pg;
    }

    public void setPg(String pg2) {
        this.pg = pg2;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getNumber() {
        return this.mNumber;
    }

    public void setNumber(String number) {
        this.mNumber = number;
    }

    public String getLabel() {
        return this.mLabel;
    }

    public void setLabel(String label) {
        this.mLabel = label;
    }

    public SdkIssuer getIssuer() {
        if (this.mNumber.startsWith("4")) {
            return SdkIssuer.VISA;
        }
        if (this.mNumber.matches("^((6304)|(6706)|(6771)|(6709))[\\d]+X+\\d+")) {
            return SdkIssuer.LASER;
        }
        if (this.mNumber.matches("(5[06-8]|6\\d)\\d{14}(\\d{2,3})?[\\d]+X+\\d+") || this.mNumber.matches("(5[06-8]|6\\d)[\\d]+X+\\d+") || this.mNumber.matches("((504([435|645|774|775|809|993]))|(60([0206]|[3845]))|(622[018])\\d)[\\d]+X+\\d+")) {
            return SdkIssuer.MAESTRO;
        }
        if (this.mNumber.matches("^5[1-5][\\d]+X+\\d+")) {
            return SdkIssuer.MASTERCARD;
        }
        if (this.mNumber.matches("^3[47][\\d]+X+\\d+")) {
            return SdkIssuer.AMEX;
        }
        if (this.mNumber.startsWith("36") || this.mNumber.startsWith("34") || this.mNumber.startsWith("37") || this.mNumber.matches("^30[0-5][\\d]+X+\\d+")) {
            return SdkIssuer.DINER;
        }
        if (this.mNumber.matches("2(014|149)[\\d]+X+\\d+")) {
            return SdkIssuer.DINER;
        }
        if (this.mNumber.matches("^35(2[89]|[3-8][0-9])[\\d]+X+\\d+")) {
            return SdkIssuer.JCB;
        }
        if (this.mNumber.matches("6(?:011|5[0-9]{2})[0-9]{12}[\\d]+X+\\d+")) {
            return SdkIssuer.DISCOVER;
        }
        return SdkIssuer.UNKNOWN;
    }

    public String getToken() {
        return this.mToken;
    }

    public void setToken(String token) {
        this.mToken = token;
    }

    public String getMode() {
        return this.mMode;
    }

    public void setMode(String mode) {
        this.mMode = mode;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mNumber);
        dest.writeString(this.mLabel);
        dest.writeString(this.mToken);
        dest.writeString(this.mMode);
        dest.writeString(this.pg);
        dest.writeString(this.type);
        dest.writeByte(this.oneClickCheckout ? (byte) 1 : 0);
        dest.writeByte(this.rewardPoint ? (byte) 1 : 0);
    }
}
