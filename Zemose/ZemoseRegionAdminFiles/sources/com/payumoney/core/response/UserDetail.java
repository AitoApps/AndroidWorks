package com.payumoney.core.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.payumoney.core.entity.CardDetail;
import com.payumoney.core.entity.Wallet;
import java.util.ArrayList;

public class UserDetail extends PayUMoneyAPIResponse implements Parcelable {
    public static final Creator<UserDetail> CREATOR = new Creator<UserDetail>() {
        public UserDetail createFromParcel(Parcel in) {
            return new UserDetail(in);
        }

        public UserDetail[] newArray(int size) {
            return new UserDetail[size];
        }
    };
    private String email;
    private String phoneNumber;
    private ArrayList<CardDetail> saveCardList;
    private String userID;
    private Wallet walletDetails;

    public UserDetail() {
    }

    protected UserDetail(Parcel in) {
        this.userID = in.readString();
        this.phoneNumber = in.readString();
        this.email = in.readString();
        this.walletDetails = (Wallet) in.readParcelable(Wallet.class.getClassLoader());
        this.saveCardList = in.createTypedArrayList(CardDetail.CREATOR);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email2) {
        this.email = email2;
    }

    public Wallet getWalletDetails() {
        return this.walletDetails;
    }

    public void setWalletDetails(Wallet walletDetails2) {
        this.walletDetails = walletDetails2;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID2) {
        this.userID = userID2;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber2) {
        this.phoneNumber = phoneNumber2;
    }

    public ArrayList<CardDetail> getSaveCardList() {
        return this.saveCardList;
    }

    public void setSaveCardList(ArrayList<CardDetail> saveCardList2) {
        this.saveCardList = saveCardList2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userID);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.email);
        dest.writeParcelable(this.walletDetails, flags);
        dest.writeTypedList(this.saveCardList);
    }
}
