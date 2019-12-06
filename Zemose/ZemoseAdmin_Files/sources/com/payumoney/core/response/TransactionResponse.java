package com.payumoney.core.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Keep;
import android.text.TextUtils;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.entity.Amount;
import com.payumoney.core.entity.CitrusUser;
import com.payumoney.core.utils.AnalyticsConstant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

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
    private boolean COD;
    private Amount adjustedAmount;
    private String authIdCode;
    private String autoLoadMessage;
    private Amount balanceAmount;
    private String binCardType;
    private CitrusUser citrusUser;
    private String country;
    private String couponCode;
    private Map<String, String> customParamsMap;
    private String dpRuleName;
    private String dpRuleType;
    private String impsMmid;
    private String impsMobileNumber;
    private String issuerCode;
    private String jsonResponse;
    private String maskedCardNumber;
    private String message;
    private Amount originalAmount;
    private PaymentMode paymentMode;
    private String responseCode;
    private String signature;
    private Amount transactionAmount;
    private TransactionDetails transactionDetails;
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

    public static class TransactionDetails implements Parcelable {
        public static final Creator<TransactionDetails> CREATOR = new Creator<TransactionDetails>() {
            public TransactionDetails createFromParcel(Parcel source) {
                return new TransactionDetails(source);
            }

            public TransactionDetails[] newArray(int size) {
                return new TransactionDetails[size];
            }
        };
        private String issuerRefNo;
        private String pgTxnNo;
        private String transactionDateTime;
        private String transactionGateway;
        private String transactionId;
        private String txRefNo;

        TransactionDetails(String transactionId2) {
            this.transactionId = null;
            this.txRefNo = null;
            this.pgTxnNo = null;
            this.issuerRefNo = null;
            this.transactionGateway = null;
            this.transactionDateTime = null;
            this.transactionId = transactionId2;
        }

        public TransactionDetails(String transactionId2, String txRefNo2, String pgTxnNo2, String issuerRefNo2, String transactionGateway2, String transactionDateTime2) {
            this.transactionId = null;
            this.txRefNo = null;
            this.pgTxnNo = null;
            this.issuerRefNo = null;
            this.transactionGateway = null;
            this.transactionDateTime = null;
            this.transactionId = transactionId2;
            this.txRefNo = txRefNo2;
            this.pgTxnNo = pgTxnNo2;
            this.issuerRefNo = issuerRefNo2;
            this.transactionGateway = transactionGateway2;
            this.transactionDateTime = transactionDateTime2;
        }

        private TransactionDetails(Parcel in) {
            this.transactionId = null;
            this.txRefNo = null;
            this.pgTxnNo = null;
            this.issuerRefNo = null;
            this.transactionGateway = null;
            this.transactionDateTime = null;
            this.transactionId = in.readString();
            this.txRefNo = in.readString();
            this.pgTxnNo = in.readString();
            this.issuerRefNo = in.readString();
            this.transactionGateway = in.readString();
            this.transactionDateTime = in.readString();
        }

        public static TransactionDetails fromJSONObject(JSONObject response) {
            if (response == null) {
                return null;
            }
            TransactionDetails transactionDetails = new TransactionDetails(response.optString("TxId"), response.optString("TxRefNo"), response.optString("pgTxnNo"), response.optString("issuerRefNo"), response.optString("TxGateway"), response.optString("txnDateTime"));
            return transactionDetails;
        }

        public String getTransactionId() {
            return this.transactionId;
        }

        public String getTxRefNo() {
            return this.txRefNo;
        }

        public String getPgTxnNo() {
            return this.pgTxnNo;
        }

        public String getIssuerRefNo() {
            return this.issuerRefNo;
        }

        public String getTransactionGateway() {
            return this.transactionGateway;
        }

        public String getTransactionDateTime() {
            return this.transactionDateTime;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("TransactionDetails{transactionId='");
            sb.append(this.transactionId);
            sb.append('\'');
            sb.append(", txRefNo='");
            sb.append(this.txRefNo);
            sb.append('\'');
            sb.append(", pgTxnNo='");
            sb.append(this.pgTxnNo);
            sb.append('\'');
            sb.append(", issuerRefNo='");
            sb.append(this.issuerRefNo);
            sb.append('\'');
            sb.append(", transactionGateway='");
            sb.append(this.transactionGateway);
            sb.append('\'');
            sb.append(", transactionDateTime='");
            sb.append(this.transactionDateTime);
            sb.append('\'');
            sb.append('}');
            return sb.toString();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.transactionId);
            dest.writeString(this.txRefNo);
            dest.writeString(this.pgTxnNo);
            dest.writeString(this.issuerRefNo);
            dest.writeString(this.transactionGateway);
            dest.writeString(this.transactionDateTime);
        }
    }

    public enum TransactionStatus {
        SUCCESSFUL,
        FAILED,
        CANCELLED,
        PG_REJECTED,
        PG_FORWARD_REQUESTED,
        UNKNOWN,
        FORWARDED;

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
            return transactionStatus2;
        }
    }

    private TransactionResponse() {
        this.balanceAmount = null;
        this.transactionAmount = null;
        this.message = null;
        this.autoLoadMessage = null;
        this.responseCode = null;
        this.transactionStatus = null;
        this.transactionDetails = null;
        this.citrusUser = null;
        this.paymentMode = null;
        this.issuerCode = null;
        this.impsMobileNumber = null;
        this.impsMmid = null;
        this.authIdCode = null;
        this.signature = null;
        this.maskedCardNumber = null;
        this.COD = false;
        this.customParamsMap = null;
        this.jsonResponse = null;
        this.originalAmount = null;
        this.adjustedAmount = null;
        this.dpRuleName = null;
        this.couponCode = null;
        this.dpRuleType = null;
        this.country = null;
        this.binCardType = null;
    }

    TransactionResponse(TransactionStatus transactionStatus2, String message2, String transactionId) {
        this.balanceAmount = null;
        this.transactionAmount = null;
        this.message = null;
        this.autoLoadMessage = null;
        this.responseCode = null;
        this.transactionStatus = null;
        this.transactionDetails = null;
        this.citrusUser = null;
        this.paymentMode = null;
        this.issuerCode = null;
        this.impsMobileNumber = null;
        this.impsMmid = null;
        this.authIdCode = null;
        this.signature = null;
        this.maskedCardNumber = null;
        this.COD = false;
        this.customParamsMap = null;
        this.jsonResponse = null;
        this.originalAmount = null;
        this.adjustedAmount = null;
        this.dpRuleName = null;
        this.couponCode = null;
        this.dpRuleType = null;
        this.country = null;
        this.binCardType = null;
        this.transactionStatus = transactionStatus2;
        this.message = message2;
        this.transactionDetails = new TransactionDetails(transactionId);
    }

    private TransactionResponse(Amount transactionAmount2, String message2, String responseCode2, TransactionStatus transactionStatus2, TransactionDetails transactionDetails2, CitrusUser citrusUser2, PaymentMode paymentMode2, String issuerCode2, String impsMobileNumber2, String impsMmid2, String authIdCode2, String signature2, boolean COD2, String maskedCardNumber2, Map<String, String> customParamsMap2) {
        this.balanceAmount = null;
        this.transactionAmount = null;
        this.message = null;
        this.autoLoadMessage = null;
        this.responseCode = null;
        this.transactionStatus = null;
        this.transactionDetails = null;
        this.citrusUser = null;
        this.paymentMode = null;
        this.issuerCode = null;
        this.impsMobileNumber = null;
        this.impsMmid = null;
        this.authIdCode = null;
        this.signature = null;
        this.maskedCardNumber = null;
        this.COD = false;
        this.customParamsMap = null;
        this.jsonResponse = null;
        this.originalAmount = null;
        this.adjustedAmount = null;
        this.dpRuleName = null;
        this.couponCode = null;
        this.dpRuleType = null;
        this.country = null;
        this.binCardType = null;
        this.transactionAmount = transactionAmount2;
        this.message = message2;
        this.responseCode = responseCode2;
        this.transactionStatus = transactionStatus2;
        this.transactionDetails = transactionDetails2;
        this.citrusUser = citrusUser2;
        this.paymentMode = paymentMode2;
        this.issuerCode = issuerCode2;
        this.impsMobileNumber = impsMobileNumber2;
        this.impsMmid = impsMmid2;
        this.authIdCode = authIdCode2;
        this.signature = signature2;
        this.COD = COD2;
        this.maskedCardNumber = maskedCardNumber2;
        this.customParamsMap = customParamsMap2;
    }

    private TransactionResponse(Amount transactionAmount2, String message2, String responseCode2, TransactionStatus transactionStatus2, TransactionDetails transactionDetails2, CitrusUser citrusUser2, PaymentMode paymentMode2, String issuerCode2, String impsMobileNumber2, String impsMmid2, String authIdCode2, String signature2, boolean COD2, String maskedCardNumber2, Map<String, String> customParamsMap2, Amount originalAmount2, Amount adjustedAmount2, String dpRuleName2, String couponCode2, String dpRuleType2) {
        this.balanceAmount = null;
        this.transactionAmount = null;
        this.message = null;
        this.autoLoadMessage = null;
        this.responseCode = null;
        this.transactionStatus = null;
        this.transactionDetails = null;
        this.citrusUser = null;
        this.paymentMode = null;
        this.issuerCode = null;
        this.impsMobileNumber = null;
        this.impsMmid = null;
        this.authIdCode = null;
        this.signature = null;
        this.maskedCardNumber = null;
        this.COD = false;
        this.customParamsMap = null;
        this.jsonResponse = null;
        this.originalAmount = null;
        this.adjustedAmount = null;
        this.dpRuleName = null;
        this.couponCode = null;
        this.dpRuleType = null;
        this.country = null;
        this.binCardType = null;
        this.transactionAmount = transactionAmount2;
        this.message = message2;
        this.responseCode = responseCode2;
        this.transactionStatus = transactionStatus2;
        this.transactionDetails = transactionDetails2;
        this.citrusUser = citrusUser2;
        this.paymentMode = paymentMode2;
        this.issuerCode = issuerCode2;
        this.impsMobileNumber = impsMobileNumber2;
        this.impsMmid = impsMmid2;
        this.authIdCode = authIdCode2;
        this.signature = signature2;
        this.maskedCardNumber = maskedCardNumber2;
        this.COD = COD2;
        this.customParamsMap = customParamsMap2;
        this.originalAmount = originalAmount2;
        this.adjustedAmount = adjustedAmount2;
        this.dpRuleName = dpRuleName2;
        this.couponCode = couponCode2;
        this.dpRuleType = dpRuleType2;
    }

    private TransactionResponse(Parcel in) {
        PaymentMode paymentMode2 = null;
        this.balanceAmount = null;
        this.transactionAmount = null;
        this.message = null;
        this.autoLoadMessage = null;
        this.responseCode = null;
        this.transactionStatus = null;
        this.transactionDetails = null;
        this.citrusUser = null;
        this.paymentMode = null;
        this.issuerCode = null;
        this.impsMobileNumber = null;
        this.impsMmid = null;
        this.authIdCode = null;
        this.signature = null;
        this.maskedCardNumber = null;
        boolean z = false;
        this.COD = false;
        this.customParamsMap = null;
        this.jsonResponse = null;
        this.originalAmount = null;
        this.adjustedAmount = null;
        this.dpRuleName = null;
        this.couponCode = null;
        this.dpRuleType = null;
        this.country = null;
        this.binCardType = null;
        this.balanceAmount = (Amount) in.readParcelable(Amount.class.getClassLoader());
        this.transactionAmount = (Amount) in.readParcelable(Amount.class.getClassLoader());
        this.message = in.readString();
        this.responseCode = in.readString();
        int readInt = in.readInt();
        this.transactionStatus = readInt == -1 ? null : TransactionStatus.values()[readInt];
        this.transactionDetails = (TransactionDetails) in.readParcelable(TransactionDetails.class.getClassLoader());
        this.citrusUser = (CitrusUser) in.readParcelable(CitrusUser.class.getClassLoader());
        int readInt2 = in.readInt();
        if (readInt2 != -1) {
            paymentMode2 = PaymentMode.values()[readInt2];
        }
        this.paymentMode = paymentMode2;
        this.issuerCode = in.readString();
        this.impsMobileNumber = in.readString();
        this.impsMmid = in.readString();
        this.authIdCode = in.readString();
        this.signature = in.readString();
        this.maskedCardNumber = in.readString();
        if (in.readByte() != 0) {
            z = true;
        }
        this.COD = z;
        this.customParamsMap = in.readHashMap(String.class.getClassLoader());
        this.jsonResponse = in.readString();
        this.country = in.readString();
        this.binCardType = in.readString();
    }

    public static TransactionResponse fromJSON(String response, Map<String, String> customParamsOriginalMap) {
        try {
            return fromJSONObject(new JSONObject(response), customParamsOriginalMap);
        } catch (JSONException e) {
            e.printStackTrace();
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setJsonResponse(response);
            return transactionResponse;
        }
    }

    public static TransactionResponse fromJSON(String response) {
        return fromJSON(response, null);
    }

    public static TransactionResponse fromJSONObject(JSONObject jsonObject, Map<String, String> customParamsOriginalMap) {
        String str;
        Map map;
        JSONObject jSONObject = jsonObject;
        HashMap hashMap = null;
        if (jSONObject == null) {
            return null;
        }
        if (jSONObject.optString("Error", null) != null) {
            return new TransactionResponse(TransactionStatus.FAILED, jSONObject.optString("Reason", "Transaction Failed"), null);
        }
        PaymentMode paymentMode2 = PaymentMode.getPaymentMode(jSONObject.optString("paymentMode"));
        TransactionStatus transactionStatus2 = TransactionStatus.getTransactionStatus(jSONObject.optString("TxStatus"));
        String optString = jSONObject.optString("currency");
        String optString2 = jSONObject.optString(PayUmoneyConstants.AMOUNT);
        String optString3 = jSONObject.optString("pgRespCode");
        if (transactionStatus2 == TransactionStatus.CANCELLED) {
            str = "Transaction Cancelled.";
        } else if (jSONObject.has("TxMsg")) {
            str = jSONObject.optString("TxMsg");
        } else {
            str = jSONObject.optString(AnalyticsConstant.ERROR_MESSAGE);
        }
        String optString4 = jSONObject.optString("isCOD");
        String optString5 = jSONObject.optString("signature");
        String optString6 = jSONObject.optString("issuerCode");
        String optString7 = jSONObject.optString("impsMmid");
        String optString8 = jSONObject.optString("impsMobileNumber");
        String optString9 = jSONObject.optString("authIdCode");
        String optString10 = jSONObject.optString("maskedcardNumber");
        if (customParamsOriginalMap != null) {
            Iterator it = customParamsOriginalMap.keySet().iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                if (hashMap == null) {
                    hashMap = new HashMap();
                }
                Iterator it2 = it;
                hashMap.put(str2, jSONObject.optString(str2));
                it = it2;
            }
            map = hashMap;
        } else {
            map = null;
        }
        TransactionResponse transactionResponse = new TransactionResponse(new Amount(optString2, optString), str, optString3, transactionStatus2, TransactionDetails.fromJSONObject(jsonObject), CitrusUser.fromJSONObject(jsonObject), paymentMode2, optString6, optString8, optString7, optString9, optString5, "true".equalsIgnoreCase(optString4), optString10, map);
        transactionResponse.setJsonResponse(jsonObject.toString());
        return transactionResponse;
    }

    public String getAutoLoadMessage() {
        return this.autoLoadMessage;
    }

    /* access modifiers changed from: protected */
    public void setAutoLoadMessage(String autoLoadMessage2) {
        this.autoLoadMessage = autoLoadMessage2;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country2) {
        this.country = country2;
    }

    public String getBinCardType() {
        return this.binCardType;
    }

    public void setBinCardType(String binCardType2) {
        this.binCardType = binCardType2;
    }

    public TransactionStatus getTransactionStatus() {
        return this.transactionStatus;
    }

    public String getTransactionId() {
        TransactionDetails transactionDetails2 = this.transactionDetails;
        if (transactionDetails2 != null) {
            return transactionDetails2.getTransactionId();
        }
        return null;
    }

    public CitrusUser getCitrusUser() {
        return this.citrusUser;
    }

    public Amount getTransactionAmount() {
        return this.transactionAmount;
    }

    public Amount getBalanceAmount() {
        return this.balanceAmount;
    }

    public String getMessage() {
        return this.message;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public PaymentMode getPaymentMode() {
        return this.paymentMode;
    }

    public TransactionDetails getTransactionDetails() {
        return this.transactionDetails;
    }

    public boolean isCOD() {
        return this.COD;
    }

    public String getImpsMobileNumber() {
        return this.impsMobileNumber;
    }

    public String getIssuerCode() {
        return this.issuerCode;
    }

    public String getImpsMmid() {
        return this.impsMmid;
    }

    public String getAuthIdCode() {
        return this.authIdCode;
    }

    public String getSignature() {
        return this.signature;
    }

    public String getMaskedCardNumber() {
        return this.maskedCardNumber;
    }

    public Amount getOriginalAmount() {
        return this.originalAmount;
    }

    public Amount getAdjustedAmount() {
        return this.adjustedAmount;
    }

    public String getDpRuleName() {
        return this.dpRuleName;
    }

    public String getCouponCode() {
        return this.couponCode;
    }

    public String getDpRuleType() {
        return this.dpRuleType;
    }

    public Map<String, String> getCustomParamsMap() {
        return this.customParamsMap;
    }

    public String getJsonResponse() {
        return this.jsonResponse;
    }

    public void setJsonResponse(String jsonResponse2) {
        this.jsonResponse = jsonResponse2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CitrusTransactionResponse{transactionAmount='");
        Amount amount = this.transactionAmount;
        sb.append(amount != null ? amount.toString() : "");
        sb.append('\'');
        sb.append("balanceAmount='");
        Amount amount2 = this.balanceAmount;
        sb.append(amount2 != null ? amount2.toString() : "");
        sb.append('\'');
        sb.append(", message='");
        sb.append(this.message);
        sb.append('\'');
        sb.append(", responseCode='");
        sb.append(this.responseCode);
        sb.append('\'');
        sb.append(", transactionStatus=");
        sb.append(this.transactionStatus);
        sb.append(", transactionDetails=");
        sb.append(this.transactionDetails);
        sb.append(", citrusUser=");
        sb.append(this.citrusUser);
        sb.append(", paymentMode=");
        sb.append(this.paymentMode);
        sb.append(", issuerCode='");
        sb.append(this.issuerCode);
        sb.append('\'');
        sb.append(", impsMobileNumber='");
        sb.append(this.impsMobileNumber);
        sb.append('\'');
        sb.append(", impsMmid='");
        sb.append(this.impsMmid);
        sb.append('\'');
        sb.append(", authIdCode='");
        sb.append(this.authIdCode);
        sb.append('\'');
        sb.append(", signature='");
        sb.append(this.signature);
        sb.append('\'');
        sb.append(", maskedCardNumber='");
        sb.append(this.maskedCardNumber);
        sb.append('\'');
        sb.append(", COD=");
        sb.append(this.COD);
        sb.append(", customParamsMap=");
        sb.append(this.customParamsMap);
        sb.append(", jsonResponse='");
        sb.append(this.jsonResponse);
        sb.append('\'');
        sb.append(", originalAmount=");
        sb.append(this.originalAmount);
        sb.append(", adjustedAmount=");
        sb.append(this.adjustedAmount);
        sb.append(", dpRuleName='");
        sb.append(this.dpRuleName);
        sb.append('\'');
        sb.append(", couponCode='");
        sb.append(this.couponCode);
        sb.append('\'');
        sb.append(", dpRuleType='");
        sb.append(this.dpRuleType);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.balanceAmount, 0);
        dest.writeParcelable(this.transactionAmount, 0);
        dest.writeString(this.message);
        dest.writeString(this.responseCode);
        TransactionStatus transactionStatus2 = this.transactionStatus;
        int i = -1;
        dest.writeInt(transactionStatus2 == null ? -1 : transactionStatus2.ordinal());
        dest.writeParcelable(this.transactionDetails, 0);
        dest.writeParcelable(this.citrusUser, 0);
        PaymentMode paymentMode2 = this.paymentMode;
        if (paymentMode2 != null) {
            i = paymentMode2.ordinal();
        }
        dest.writeInt(i);
        dest.writeString(this.issuerCode);
        dest.writeString(this.impsMobileNumber);
        dest.writeString(this.impsMmid);
        dest.writeString(this.authIdCode);
        dest.writeString(this.signature);
        dest.writeString(this.maskedCardNumber);
        dest.writeByte(this.COD ? (byte) 1 : 0);
        dest.writeMap(this.customParamsMap);
        dest.writeString(this.jsonResponse);
        dest.writeString(this.country);
        dest.writeString(this.binCardType);
    }
}
