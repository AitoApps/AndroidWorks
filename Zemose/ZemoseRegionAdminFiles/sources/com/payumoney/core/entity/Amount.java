package com.payumoney.core.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import java.text.DecimalFormat;
import org.json.JSONException;
import org.json.JSONObject;

public class Amount implements Parcelable, Comparable<Amount> {
    public static final Creator<Amount> CREATOR = new Creator<Amount>() {
        public Amount createFromParcel(Parcel source) {
            return new Amount(source);
        }

        public Amount[] newArray(int size) {
            return new Amount[size];
        }
    };
    private final String currency;
    private final String value;

    public Amount(String value2) {
        this.value = value2;
        this.currency = "INR";
    }

    public Amount(String value2, String currency2) {
        this.value = value2;
        this.currency = currency2;
    }

    private Amount(Parcel in) {
        this.value = in.readString();
        this.currency = in.readString();
    }

    public static Amount fromJSON(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                return fromJSONObject(new JSONObject(response));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Amount fromJSONObject(JSONObject amountObject) {
        if (amountObject != null) {
            String optString = amountObject.optString("value");
            String optString2 = amountObject.optString("currency");
            if (!TextUtils.isEmpty(optString) && !TextUtils.isEmpty(optString2)) {
                return new Amount(optString, optString2);
            }
        }
        return null;
    }

    public static String toJSON(Amount amount) {
        JSONObject jSONObject = toJSONObject(amount);
        if (jSONObject != null) {
            return jSONObject.toString();
        }
        return null;
    }

    public static JSONObject toJSONObject(Amount amount) {
        JSONObject jSONObject;
        JSONException e;
        if (amount == null) {
            return null;
        }
        try {
            jSONObject = new JSONObject();
            try {
                jSONObject.put("value", amount.value);
                jSONObject.put("currency", amount.currency);
                return jSONObject;
            } catch (JSONException e2) {
                e = e2;
            }
        } catch (JSONException e3) {
            JSONException jSONException = e3;
            jSONObject = null;
            e = jSONException;
            e.printStackTrace();
            return jSONObject;
        }
    }

    public String getValue() {
        if (!TextUtils.isEmpty(this.value)) {
            return this.value.replaceFirst("^0+(?!$)", "");
        }
        return this.value;
    }

    public String getCurrency() {
        return this.currency;
    }

    public String getValueAsFormattedDouble(String format) throws NumberFormatException {
        if (compareTo(new Amount("1.00")) < 0) {
            return new DecimalFormat("0.00").format(getValueAsDouble());
        }
        return new DecimalFormat(format).format(getValueAsDouble());
    }

    public double getValueAsDouble() throws NumberFormatException {
        if (!TextUtils.isEmpty(this.value)) {
            return Double.parseDouble(this.value);
        }
        return 0.0d;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.value);
        dest.writeString(this.currency);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Amount{value='");
        sb.append(this.value);
        sb.append('\'');
        sb.append(", currency='");
        sb.append(this.currency);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }

    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (!(o instanceof Amount)) {
            return false;
        }
        Amount amount = (Amount) o;
        if (getValueAsDouble() != amount.getValueAsDouble() || !this.currency.equalsIgnoreCase(amount.getCurrency())) {
            z = false;
        }
        return z;
    }

    public int compareTo(@NonNull Amount another) {
        if (getValueAsDouble() > another.getValueAsDouble()) {
            return 1;
        }
        if (getValueAsDouble() < another.getValueAsDouble()) {
            return -1;
        }
        return 0;
    }

    public boolean isValidAmount() {
        String[] split = this.value.split("\\.");
        boolean z = true;
        if (split.length <= 1) {
            return true;
        }
        if (split[1].length() > 2) {
            z = false;
        }
        return z;
    }
}
