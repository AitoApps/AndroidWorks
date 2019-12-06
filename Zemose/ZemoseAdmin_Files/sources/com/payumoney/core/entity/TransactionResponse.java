package com.payumoney.core.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Keep;
import android.text.TextUtils;

@Keep
public final class TransactionResponse implements Parcelable {
    public static final Creator<TransactionResponse> CREATOR = new Creator<TransactionResponse>() {
        public TransactionResponse createFromParcel(Parcel source) {
            return new TransactionResponse(source);
        }

        public TransactionResponse[] newArray(int size) {
            return new TransactionResponse[size];
        }
    };
    private String message;
    public String payuResponse;
    private String transactionDetails;
    private TransactionStatus transactionStatus;

    public enum PaymentMode {
        NET_BANKING,
        CREDIT_CARD,
        DEBIT_CARD,
        PREPAID_CARD,
        LAZYPAY_CARD;

        public static PaymentMode getPaymentMode(String paymentMode) {
            if (TextUtils.equals(paymentMode, "NET_BANKING")) {
                return NET_BANKING;
            }
            if (TextUtils.equals(paymentMode, "CREDIT_CARD")) {
                return CREDIT_CARD;
            }
            if (TextUtils.equals(paymentMode, "DEBIT_CARD")) {
                return DEBIT_CARD;
            }
            if (TextUtils.equals(paymentMode, "PREPAID_CARD")) {
                return PREPAID_CARD;
            }
            if (TextUtils.equals(paymentMode, "LAZYPAY_CARD")) {
                return LAZYPAY_CARD;
            }
            return null;
        }
    }

    public enum TransactionStatus {
        SUCCESSFUL,
        FAILED,
        CANCELLED,
        PG_REJECTED,
        PG_FORWARD_REQUESTED,
        UNKNOWN,
        FORWARDED,
        TRANSACTION_EXPIRY;

        public static TransactionStatus getTransactionStatus(String transactionStatus) {
            TransactionStatus transactionStatus2 = UNKNOWN;
            if ("SUCCESS".equalsIgnoreCase(transactionStatus) || "SUCCESSFUL".equalsIgnoreCase(transactionStatus)) {
                return SUCCESSFUL;
            }
            if ("FAIL".equalsIgnoreCase(transactionStatus)) {
                return FAILED;
            }
            if ("CANCELLED".equalsIgnoreCase(transactionStatus) || "CANCELED".equalsIgnoreCase(transactionStatus)) {
                return CANCELLED;
            }
            if ("PG_REJECTED".equalsIgnoreCase(transactionStatus)) {
                return PG_REJECTED;
            }
            if ("PG Forward requested".equalsIgnoreCase(transactionStatus)) {
                return PG_FORWARD_REQUESTED;
            }
            if ("FORWARDED".equalsIgnoreCase(transactionStatus)) {
                return FORWARDED;
            }
            if ("FORWARDED".equalsIgnoreCase(transactionStatus)) {
                return TRANSACTION_EXPIRY;
            }
            return transactionStatus2;
        }
    }

    public void setMessage(String message2) {
        this.message = message2;
    }

    public String getPayuResponse() {
        return this.payuResponse;
    }

    public void setPayuResponse(String payuResponse2) {
        this.payuResponse = payuResponse2;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus2) {
        this.transactionStatus = transactionStatus2;
    }

    public String getTransactionDetails() {
        return this.transactionDetails;
    }

    public void setTransactionDetails(String transactionDetails2) {
        this.transactionDetails = transactionDetails2;
    }

    private TransactionResponse() {
        this.message = null;
        this.transactionStatus = null;
        this.transactionDetails = null;
    }

    public TransactionResponse(TransactionStatus transactionStatus2, String message2, String transactionDetails2) {
        this.message = null;
        this.transactionStatus = null;
        this.transactionDetails = null;
        this.transactionStatus = transactionStatus2;
        this.message = message2;
        this.transactionDetails = transactionDetails2;
    }

    private TransactionResponse(Parcel in) {
        TransactionStatus transactionStatus2 = null;
        this.message = null;
        this.transactionStatus = null;
        this.transactionDetails = null;
        this.message = in.readString();
        int readInt = in.readInt();
        if (readInt != -1) {
            transactionStatus2 = TransactionStatus.values()[readInt];
        }
        this.transactionStatus = transactionStatus2;
        this.transactionDetails = in.readString();
        this.payuResponse = in.readString();
    }

    public TransactionStatus getTransactionStatus() {
        return this.transactionStatus;
    }

    public String getMessage() {
        return this.message;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        TransactionStatus transactionStatus2 = this.transactionStatus;
        dest.writeInt(transactionStatus2 == null ? -1 : transactionStatus2.ordinal());
        dest.writeString(this.transactionDetails);
        dest.writeString(this.payuResponse);
    }
}
