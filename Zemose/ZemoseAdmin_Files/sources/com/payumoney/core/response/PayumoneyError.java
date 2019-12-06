package com.payumoney.core.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.payumoney.core.response.PayumoneyResponse.Status;
import org.json.JSONObject;

public class PayumoneyError extends PayumoneyResponse {
    public static final Creator<PayumoneyError> CREATOR = new Creator<PayumoneyError>() {
        public PayumoneyError createFromParcel(Parcel source) {
            return new PayumoneyError(source);
        }

        public PayumoneyError[] newArray(int size) {
            return new PayumoneyError[size];
        }
    };
    private JSONObject errorData;
    private int statusCode;
    private final TransactionResponse transactionResponse;

    public PayumoneyError(String message) {
        super(message, Status.FAILED);
        this.statusCode = -1;
        this.transactionResponse = null;
    }

    public PayumoneyError(String message, Status status) {
        super(message, status);
        this.statusCode = -1;
        this.transactionResponse = null;
    }

    public PayumoneyError(String message, Status status, TransactionResponse transactionResponse2) {
        super(message, status);
        this.statusCode = -1;
        this.transactionResponse = transactionResponse2;
    }

    public PayumoneyError(String message, String rawResponse, Status status, TransactionResponse transactionResponse2) {
        super(message, rawResponse, status);
        this.statusCode = -1;
        this.transactionResponse = transactionResponse2;
    }

    protected PayumoneyError(Parcel in) {
        super(in);
        this.statusCode = -1;
        this.transactionResponse = (TransactionResponse) in.readParcelable(TransactionResponse.class.getClassLoader());
    }

    public TransactionResponse getTransactionResponse() {
        return this.transactionResponse;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.transactionResponse, 0);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode2) {
        this.statusCode = statusCode2;
    }

    public JSONObject getErrorData() {
        return this.errorData;
    }

    public void setErrorData(JSONObject errorData2) {
        this.errorData = errorData2;
    }
}
