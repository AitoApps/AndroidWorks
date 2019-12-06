package com.payumoney.core.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PayumoneyResponse implements Parcelable {
    public static final Creator<PayumoneyResponse> CREATOR = new Creator<PayumoneyResponse>() {
        public PayumoneyResponse createFromParcel(Parcel source) {
            return new PayumoneyResponse(source);
        }

        public PayumoneyResponse[] newArray(int size) {
            return new PayumoneyResponse[size];
        }
    };
    protected String message = null;
    protected String rawResponse = null;
    protected Status status = null;

    public enum Status {
        SUCCESSFUL,
        FAILED,
        CANCELLED,
        PG_REJECTED
    }

    PayumoneyResponse() {
    }

    public PayumoneyResponse(String message2, Status status2) {
        this.message = message2;
        this.status = status2;
    }

    public PayumoneyResponse(String message2, String rawResponse2, Status status2) {
        this.message = message2;
        this.rawResponse = rawResponse2;
        this.status = status2;
    }

    protected PayumoneyResponse(Parcel in) {
        Status status2 = null;
        this.message = in.readString();
        int readInt = in.readInt();
        if (readInt != -1) {
            status2 = Status.values()[readInt];
        }
        this.status = status2;
        this.rawResponse = in.readString();
    }

    public String getMessage() {
        return this.message;
    }

    public Status getStatus() {
        return this.status;
    }

    public String getRawResponse() {
        return this.rawResponse;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        Status status2 = this.status;
        dest.writeInt(status2 == null ? -1 : status2.ordinal());
        dest.writeString(this.rawResponse);
    }
}
