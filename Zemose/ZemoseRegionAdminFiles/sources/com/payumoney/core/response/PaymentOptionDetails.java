package com.payumoney.core.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.payumoney.core.entity.EmiThreshold;
import com.payumoney.core.entity.MerchantDetails;
import com.payumoney.core.entity.PaymentEntity;
import com.payumoney.core.entity.PayumoneyConvenienceFee;
import java.util.ArrayList;

public class PaymentOptionDetails extends PayUMoneyAPIResponse implements Parcelable {
    public static final Creator<PaymentOptionDetails> CREATOR = new Creator<PaymentOptionDetails>() {
        public PaymentOptionDetails createFromParcel(Parcel in) {
            return new PaymentOptionDetails(in);
        }

        public PaymentOptionDetails[] newArray(int size) {
            return new PaymentOptionDetails[size];
        }
    };
    private ArrayList<PaymentEntity> cashCardList;
    private PayumoneyConvenienceFee convenienceFee;
    private ArrayList<PaymentEntity> creditCardList;
    private ArrayList<PaymentEntity> debitCardList;
    private ArrayList<PaymentEntity> emiList;
    private ArrayList<EmiThreshold> emiThresholds;
    private MerchantDetails merchantDetails;
    private ArrayList<PaymentEntity> netBankingList;
    private ArrayList<PaymentEntity> netBankingStatusList;
    private String nitroEnabled;
    private String paymentID;
    private String publicKey;
    private ArrayList<PaymentEntity> upiList;
    private UserDetail userDetails;
    private String wallet;

    public ArrayList<PaymentEntity> getUpiList() {
        return this.upiList;
    }

    public void setUpiList(ArrayList<PaymentEntity> upiList2) {
        this.upiList = upiList2;
    }

    public boolean isNitroEnabled() {
        String str = this.nitroEnabled;
        if (str == null || !str.equalsIgnoreCase("true")) {
            return false;
        }
        return true;
    }

    public void setNitroEnabled(String nitroEnabled2) {
        this.nitroEnabled = nitroEnabled2;
    }

    public String getWallet() {
        return this.wallet;
    }

    public void setWallet(String wallet2) {
        this.wallet = wallet2;
    }

    public PaymentOptionDetails() {
    }

    public PayumoneyConvenienceFee getConvenienceFee() {
        return this.convenienceFee;
    }

    public void setConvenienceFee(PayumoneyConvenienceFee convenienceFee2) {
        this.convenienceFee = convenienceFee2;
    }

    protected PaymentOptionDetails(Parcel in) {
        this.paymentID = in.readString();
        this.userDetails = (UserDetail) in.readParcelable(UserDetail.class.getClassLoader());
        this.debitCardList = in.createTypedArrayList(PaymentEntity.CREATOR);
        this.creditCardList = in.createTypedArrayList(PaymentEntity.CREATOR);
        this.cashCardList = in.createTypedArrayList(PaymentEntity.CREATOR);
        this.netBankingList = in.createTypedArrayList(PaymentEntity.CREATOR);
        this.netBankingStatusList = in.createTypedArrayList(PaymentEntity.CREATOR);
        this.publicKey = in.readString();
        this.wallet = in.readString();
        this.nitroEnabled = in.readString();
        this.merchantDetails = (MerchantDetails) in.readParcelable(MerchantDetails.class.getClassLoader());
        this.emiList = in.createTypedArrayList(PaymentEntity.CREATOR);
        this.upiList = in.createTypedArrayList(PaymentEntity.CREATOR);
        this.emiThresholds = in.createTypedArrayList(EmiThreshold.CREATOR);
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public void setPublicKey(String publicKey2) {
        this.publicKey = publicKey2;
    }

    public String getPaymentID() {
        return this.paymentID;
    }

    public void setPaymentID(String paymentID2) {
        this.paymentID = paymentID2;
    }

    public UserDetail getUserDetails() {
        return this.userDetails;
    }

    public void setUserDetails(UserDetail userDetails2) {
        this.userDetails = userDetails2;
    }

    public ArrayList<PaymentEntity> getCashCardList() {
        return this.cashCardList;
    }

    public void setCashCardList(ArrayList<PaymentEntity> cashCardList2) {
        this.cashCardList = cashCardList2;
    }

    public ArrayList<PaymentEntity> getDebitCardList() {
        return this.debitCardList;
    }

    public void setDebitCardList(ArrayList<PaymentEntity> debitCardList2) {
        this.debitCardList = debitCardList2;
    }

    public ArrayList<PaymentEntity> getCreditCardList() {
        return this.creditCardList;
    }

    public void setCreditCardList(ArrayList<PaymentEntity> creditCardList2) {
        this.creditCardList = creditCardList2;
    }

    public ArrayList<PaymentEntity> getNetBankingList() {
        return this.netBankingList;
    }

    public void setNetBankingList(ArrayList<PaymentEntity> netBankingList2) {
        this.netBankingList = netBankingList2;
    }

    public MerchantDetails getMerchantDetails() {
        return this.merchantDetails;
    }

    public void setMerchantDetails(MerchantDetails merchantDetails2) {
        this.merchantDetails = merchantDetails2;
    }

    public ArrayList<PaymentEntity> getEmiList() {
        return this.emiList;
    }

    public void setEmiList(ArrayList<PaymentEntity> emiList2) {
        this.emiList = emiList2;
    }

    public ArrayList<EmiThreshold> getEmiThresholds() {
        return this.emiThresholds;
    }

    public void setEmiThresholds(ArrayList<EmiThreshold> emiThresholds2) {
        this.emiThresholds = emiThresholds2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.paymentID);
        dest.writeParcelable(this.userDetails, flags);
        dest.writeTypedList(this.debitCardList);
        dest.writeTypedList(this.creditCardList);
        dest.writeTypedList(this.cashCardList);
        dest.writeTypedList(this.netBankingList);
        dest.writeTypedList(this.netBankingStatusList);
        dest.writeString(this.publicKey);
        dest.writeString(this.wallet);
        dest.writeString(this.nitroEnabled);
        dest.writeParcelable(this.merchantDetails, flags);
        dest.writeTypedList(this.emiList);
        dest.writeTypedList(this.upiList);
        dest.writeTypedList(this.emiThresholds);
    }

    public boolean isWalletAvailable() {
        String str = this.wallet;
        if (str == null || !str.equalsIgnoreCase("true")) {
            return false;
        }
        return true;
    }

    public boolean isNBAvailable() {
        ArrayList<PaymentEntity> arrayList = this.netBankingList;
        if (arrayList == null || arrayList.size() == 0) {
            return false;
        }
        return true;
    }

    public ArrayList<PaymentEntity> getNetBankingStatusList() {
        return this.netBankingStatusList;
    }

    public void setNetBankingStatusList(ArrayList<PaymentEntity> netBankingStatusList2) {
        this.netBankingStatusList = netBankingStatusList2;
    }
}
