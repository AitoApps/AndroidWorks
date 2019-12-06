package com.payumoney.core.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.bumptech.glide.load.Key;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.entity.Amount;
import com.payumoney.core.entity.CitrusUser;
import com.payumoney.core.response.PayumoneyResponse.Status;
import com.payumoney.core.utils.AnalyticsConstant;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentResponse extends PayumoneyResponse implements Parcelable {
    public static final Creator<PaymentResponse> CREATOR = new Creator<PaymentResponse>() {
        public PaymentResponse createFromParcel(Parcel source) {
            return new PaymentResponse(source);
        }

        public PaymentResponse[] newArray(int size) {
            return new PaymentResponse[size];
        }
    };
    protected Amount balanceAmount = null;
    protected String customer = null;
    protected String date = null;
    protected String merchantName = null;
    private JSONObject responseParams = null;
    protected Amount transactionAmount = null;
    protected String transactionId = null;
    protected TransactionResponse transactionResponse = null;
    protected CitrusUser user = null;

    PaymentResponse() {
    }

    public PaymentResponse(String message, Status status, String transactionId2, Amount transactionAmount2, Amount balanceAmount2, CitrusUser user2) {
        super(message, status);
        this.transactionId = transactionId2;
        this.transactionAmount = transactionAmount2;
        this.balanceAmount = balanceAmount2;
        this.user = user2;
    }

    public PaymentResponse(String message, Status status, String transactionId2, Amount transactionAmount2, Amount balanceAmount2, CitrusUser user2, String customer2, String merchantName2, String date2, TransactionResponse transactionResponse2) {
        super(message, status);
        this.transactionId = transactionId2;
        this.transactionAmount = transactionAmount2;
        this.balanceAmount = balanceAmount2;
        this.customer = customer2;
        this.merchantName = merchantName2;
        this.date = date2;
        this.transactionResponse = transactionResponse2;
        this.user = user2;
    }

    protected PaymentResponse(Parcel in) {
        super(in);
        this.transactionId = in.readString();
        this.transactionAmount = (Amount) in.readParcelable(Amount.class.getClassLoader());
        this.balanceAmount = (Amount) in.readParcelable(Amount.class.getClassLoader());
        this.customer = in.readString();
        this.merchantName = in.readString();
        this.date = in.readString();
        this.transactionResponse = (TransactionResponse) in.readParcelable(TransactionResponse.class.getClassLoader());
        this.user = (CitrusUser) in.readParcelable(CitrusUser.class.getClassLoader());
    }

    public static PaymentResponse fromJSON(String json) {
        PaymentResponse paymentResponse;
        Map map;
        try {
            JSONObject jSONObject = new JSONObject(json);
            String optString = jSONObject.optString("customer", jSONObject.optString("cutsomer"));
            Status valueOf = Status.valueOf(jSONObject.optString("status"));
            String optString2 = valueOf == Status.SUCCESSFUL ? "Transaction Successful" : jSONObject.optString(AnalyticsConstant.REASON);
            String optString3 = jSONObject.optString("date");
            String optString4 = jSONObject.optString("merchant");
            Amount fromJSONObject = Amount.fromJSONObject(jSONObject.optJSONObject(PayUmoneyConstants.AMOUNT));
            Amount fromJSONObject2 = Amount.fromJSONObject(jSONObject.optJSONObject("balance"));
            JSONObject optJSONObject = jSONObject.optJSONObject("customParams");
            if (optJSONObject != null) {
                map = new HashMap();
                Iterator keys = optJSONObject.keys();
                while (keys.hasNext()) {
                    String str = (String) keys.next();
                    map.put(str, optJSONObject.optString(str));
                }
            } else {
                map = null;
            }
            JSONObject optJSONObject2 = jSONObject.optJSONObject("responseParams");
            TransactionResponse fromJSONObject3 = TransactionResponse.fromJSONObject(optJSONObject2, map);
            paymentResponse = new PaymentResponse(optString2, valueOf, fromJSONObject3 != null ? fromJSONObject3.getTransactionId() : null, fromJSONObject, fromJSONObject2, fromJSONObject3.getCitrusUser(), optString, optString4, optString3, fromJSONObject3);
            try {
                paymentResponse.setResponseParams(optJSONObject2);
            } catch (JSONException e) {
                e = e;
            }
        } catch (JSONException e2) {
            e = e2;
            paymentResponse = null;
            e.printStackTrace();
            return paymentResponse;
        }
        return paymentResponse;
    }

    public TransactionResponse getTransactionResponse() {
        return this.transactionResponse;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public Amount getTransactionAmount() {
        return this.transactionAmount;
    }

    public Amount getBalanceAmount() {
        return this.balanceAmount;
    }

    public CitrusUser getUser() {
        return this.user;
    }

    public String getMerchantName() {
        return this.merchantName;
    }

    public String getDate() {
        return this.date;
    }

    public String getCustomer() {
        return this.customer;
    }

    private void setResponseParams(JSONObject responseParams2) {
        this.responseParams = responseParams2;
    }

    public String getURLEncodedParams() {
        StringBuffer stringBuffer = new StringBuffer();
        JSONObject jSONObject = this.responseParams;
        if (jSONObject != null) {
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                stringBuffer.append(str);
                stringBuffer.append("=");
                try {
                    stringBuffer.append(URLEncoder.encode(this.responseParams.optString(str), Key.STRING_CHARSET_NAME));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                stringBuffer.append("&");
            }
        }
        return stringBuffer.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentResponse{transactionId='");
        sb.append(this.transactionId);
        sb.append('\'');
        sb.append(", transactionAmount=");
        sb.append(this.transactionAmount);
        sb.append(", balanceAmount=");
        sb.append(this.balanceAmount);
        sb.append(", customer='");
        sb.append(this.customer);
        sb.append('\'');
        sb.append(", merchantName='");
        sb.append(this.merchantName);
        sb.append('\'');
        sb.append(", date='");
        sb.append(this.date);
        sb.append('\'');
        sb.append(", transactionResponse=");
        sb.append(this.transactionResponse);
        sb.append(", user=");
        sb.append(this.user);
        sb.append('}');
        return sb.toString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.transactionId);
        dest.writeParcelable(this.transactionAmount, 0);
        dest.writeParcelable(this.balanceAmount, 0);
        dest.writeString(this.customer);
        dest.writeString(this.merchantName);
        dest.writeString(this.date);
        dest.writeParcelable(this.transactionResponse, 0);
        dest.writeParcelable(this.user, 0);
    }
}
