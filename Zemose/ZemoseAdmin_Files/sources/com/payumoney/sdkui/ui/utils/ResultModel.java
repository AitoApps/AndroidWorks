package com.payumoney.sdkui.ui.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Keep;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.core.response.PayumoneyError;

@Keep
public class ResultModel implements Parcelable {
    public static final Creator<ResultModel> CREATOR = new Creator<ResultModel>() {
        public ResultModel createFromParcel(Parcel source) {
            return new ResultModel(source);
        }

        public ResultModel[] newArray(int size) {
            return new ResultModel[size];
        }
    };
    private PayumoneyError error;
    private TransactionResponse transactionResponse;

    public ResultModel(PayumoneyError error2, TransactionResponse transactionResponse2) {
        this.error = error2;
        this.transactionResponse = transactionResponse2;
    }

    public ResultModel(String paymentResponse, PayumoneyError error2, boolean isWithdraw) {
        this.error = error2;
    }

    private ResultModel(Parcel in) {
        this.transactionResponse = (TransactionResponse) in.readParcelable(TransactionResponse.class.getClassLoader());
        this.error = (PayumoneyError) in.readParcelable(PayumoneyError.class.getClassLoader());
    }

    public PayumoneyError getError() {
        return this.error;
    }

    public void setError(PayumoneyError error2) {
        this.error = error2;
    }

    public TransactionResponse getTransactionResponse() {
        return this.transactionResponse;
    }

    public void setTransactionResponse(TransactionResponse transactionResponse2) {
        this.transactionResponse = transactionResponse2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.transactionResponse, 0);
        dest.writeParcelable(this.error, 0);
    }
}
