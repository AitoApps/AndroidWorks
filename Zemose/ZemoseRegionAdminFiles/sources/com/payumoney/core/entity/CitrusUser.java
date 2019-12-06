package com.payumoney.core.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.utils.AnalyticsConstant;
import org.json.JSONException;
import org.json.JSONObject;

public final class CitrusUser implements Parcelable {
    public static final Creator<CitrusUser> CREATOR = new Creator<CitrusUser>() {
        public CitrusUser createFromParcel(Parcel source) {
            return new CitrusUser(source);
        }

        public CitrusUser[] newArray(int size) {
            return new CitrusUser[size];
        }
    };
    public static final CitrusUser DEFAULT_USER;
    private Address address;
    private String emailId;
    private boolean emailVerified;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private boolean mobileVerified;
    private String uuid;

    public static class Address implements Parcelable {
        public static final Creator<Address> CREATOR = new Creator<Address>() {
            public Address createFromParcel(Parcel source) {
                return new Address(source);
            }

            public Address[] newArray(int size) {
                return new Address[size];
            }
        };
        public static Address DEFAULT_ADDRESS;
        /* access modifiers changed from: private */
        public String city;
        /* access modifiers changed from: private */
        public String country;
        /* access modifiers changed from: private */
        public String state;
        /* access modifiers changed from: private */
        public String street1;
        /* access modifiers changed from: private */
        public String street2;
        /* access modifiers changed from: private */
        public String zip;

        static {
            Address address = new Address("Street1", "Street2", "Pune", "Maharashtra", "India", "411045");
            DEFAULT_ADDRESS = address;
        }

        public Address(String street12, String street22, String city2, String state2, String country2, String zip2) {
            this.street1 = "";
            this.street2 = "";
            this.city = "";
            this.state = "";
            this.country = "";
            this.zip = "";
            this.street1 = CitrusUser.normalizeString(street12);
            this.street2 = CitrusUser.normalizeString(street22);
            this.city = CitrusUser.normalizeString(city2);
            this.state = CitrusUser.normalizeString(state2);
            this.country = CitrusUser.normalizeString(country2);
            this.zip = CitrusUser.normalizeString(zip2);
        }

        private Address(Parcel in) {
            this.street1 = "";
            this.street2 = "";
            this.city = "";
            this.state = "";
            this.country = "";
            this.zip = "";
            this.street1 = in.readString();
            this.street2 = in.readString();
            this.city = in.readString();
            this.state = in.readString();
            this.country = in.readString();
            this.zip = in.readString();
        }

        public static Address fromJSONObject(JSONObject response) {
            if (response == null) {
                return null;
            }
            String optString = response.optString("addressCountry", response.optString("country", ""));
            String optString2 = response.optString("addressState", response.optString("state", ""));
            Address address = new Address(response.optString("addressStreet1", response.optString("street1", "")), response.optString("addressStreet2", response.optString("street2", "")), response.optString("addressCity", response.optString("city", "")), optString2, optString, response.optString("addressZip", response.optString("zip", "")));
            return address;
        }

        public String getStreet1() {
            return this.street1;
        }

        public String getStreet2() {
            return this.street2;
        }

        public String getCity() {
            return this.city;
        }

        public String getState() {
            return this.state;
        }

        public String getCountry() {
            return this.country;
        }

        public String getZip() {
            return this.zip;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Address{street1='");
            sb.append(this.street1);
            sb.append('\'');
            sb.append(", street2='");
            sb.append(this.street2);
            sb.append('\'');
            sb.append(", city='");
            sb.append(this.city);
            sb.append('\'');
            sb.append(", state='");
            sb.append(this.state);
            sb.append('\'');
            sb.append(", country='");
            sb.append(this.country);
            sb.append('\'');
            sb.append(", zip='");
            sb.append(this.zip);
            sb.append('\'');
            sb.append('}');
            return sb.toString();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.street1);
            dest.writeString(this.street2);
            dest.writeString(this.city);
            dest.writeString(this.state);
            dest.writeString(this.country);
            dest.writeString(this.zip);
        }
    }

    static {
        CitrusUser citrusUser = new CitrusUser("developercitrus@gmail.com", "9876543210", "Developer", "Citrus", Address.DEFAULT_ADDRESS);
        DEFAULT_USER = citrusUser;
    }

    private CitrusUser() {
        this.emailId = null;
        this.mobileNo = null;
        this.firstName = null;
        this.lastName = null;
        this.address = null;
        this.emailVerified = false;
        this.mobileVerified = false;
        this.uuid = null;
    }

    public CitrusUser(String emailId2, String mobileNo2) {
        this.emailId = null;
        this.mobileNo = null;
        this.firstName = null;
        this.lastName = null;
        this.address = null;
        this.emailVerified = false;
        this.mobileVerified = false;
        this.uuid = null;
        this.emailId = emailId2;
        this.mobileNo = mobileNo2;
    }

    public CitrusUser(String emailId2, String mobileNo2, String firstName2, String lastName2, Address address2) {
        this.emailId = null;
        this.mobileNo = null;
        this.firstName = null;
        this.lastName = null;
        this.address = null;
        this.emailVerified = false;
        this.mobileVerified = false;
        this.uuid = null;
        this.emailId = emailId2;
        this.mobileNo = mobileNo2;
        this.firstName = firstName2;
        this.lastName = lastName2;
        this.address = address2;
    }

    private CitrusUser(String emailId2, String mobileNo2, String firstName2, String lastName2, boolean emailVerified2, boolean mobileVerified2, Address address2) {
        this.emailId = null;
        this.mobileNo = null;
        this.firstName = null;
        this.lastName = null;
        this.address = null;
        this.emailVerified = false;
        this.mobileVerified = false;
        this.uuid = null;
        this.emailId = emailId2;
        this.mobileNo = mobileNo2;
        this.firstName = firstName2;
        this.lastName = lastName2;
        this.emailVerified = emailVerified2;
        this.mobileVerified = mobileVerified2;
        this.address = address2;
    }

    protected CitrusUser(Parcel in) {
        this.emailId = null;
        this.mobileNo = null;
        this.firstName = null;
        this.lastName = null;
        this.address = null;
        boolean z = false;
        this.emailVerified = false;
        this.mobileVerified = false;
        this.uuid = null;
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.emailId = in.readString();
        this.mobileNo = in.readString();
        this.address = (Address) in.readParcelable(Address.class.getClassLoader());
        this.emailVerified = in.readByte() != 0;
        if (in.readByte() != 0) {
            z = true;
        }
        this.mobileVerified = z;
    }

    public static CitrusUser fromJSON(String json) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            jSONObject = null;
        }
        return fromJSONObject(jSONObject);
    }

    public static CitrusUser fromJSONObject(JSONObject response) {
        return fromJSONObject(response, true);
    }

    public static CitrusUser fromJSONObject(JSONObject response, boolean flattenedJSON) {
        Address address2;
        if (response == null) {
            return null;
        }
        String optString = response.optString("email");
        String optString2 = response.optString("mobileNo", response.optString(PayUmoneyConstants.MOBILE));
        String optString3 = response.optString(PayUmoneyConstants.FIRSTNAME);
        String optString4 = response.optString("lastName");
        boolean z = response.optInt("emailVerified", 0) == 1;
        boolean z2 = response.optInt("mobileVerified", 0) == 1;
        String optString5 = response.optString(AnalyticsConstant.UUID);
        if (flattenedJSON) {
            address2 = Address.fromJSONObject(response);
        } else {
            address2 = Address.fromJSONObject(response.optJSONObject("address"));
        }
        CitrusUser citrusUser = new CitrusUser(optString, optString2, optString3, optString4, z, z2, address2);
        citrusUser.uuid = optString5;
        return citrusUser;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x001a A[Catch:{ JSONException -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x002b A[Catch:{ JSONException -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x003c A[Catch:{ JSONException -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x005d A[Catch:{ JSONException -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x005e A[Catch:{ JSONException -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x007a A[Catch:{ JSONException -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x007b A[Catch:{ JSONException -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0097 A[Catch:{ JSONException -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0098 A[Catch:{ JSONException -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00b4 A[Catch:{ JSONException -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00b5 A[Catch:{ JSONException -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00d1 A[Catch:{ JSONException -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00d2 A[Catch:{ JSONException -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00ee A[Catch:{ JSONException -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00ef A[Catch:{ JSONException -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x010f A[Catch:{ JSONException -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x012e A[Catch:{ JSONException -> 0x0157 }] */
    public static JSONObject toJSONObject(CitrusUser user, boolean flattenJson) {
        JSONObject jSONObject;
        JSONException e;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10;
        try {
            jSONObject = new JSONObject();
            if (user != null) {
                try {
                    if (!TextUtils.isEmpty(user.firstName)) {
                        str = user.firstName;
                        if (user != null) {
                            if (!TextUtils.isEmpty(user.lastName)) {
                                str2 = user.lastName;
                                if (user != null) {
                                    if (!TextUtils.isEmpty(user.emailId)) {
                                        str3 = user.emailId;
                                        if (user != null) {
                                            if (!TextUtils.isEmpty(user.mobileNo)) {
                                                str4 = user.mobileNo;
                                                if (!(user == null || user.address == null)) {
                                                    if (!TextUtils.isEmpty(user.address.street1)) {
                                                        str5 = user.address.street1;
                                                        if (!(user == null || user.address == null)) {
                                                            if (TextUtils.isEmpty(user.address.street2)) {
                                                                str6 = user.address.street2;
                                                                if (!(user == null || user.address == null)) {
                                                                    if (TextUtils.isEmpty(user.address.city)) {
                                                                        str7 = user.address.city;
                                                                        if (!(user == null || user.address == null)) {
                                                                            if (TextUtils.isEmpty(user.address.state)) {
                                                                                str8 = user.address.state;
                                                                                if (!(user == null || user.address == null)) {
                                                                                    if (TextUtils.isEmpty(user.address.country)) {
                                                                                        str9 = user.address.country;
                                                                                        if (!(user == null || user.address == null)) {
                                                                                            if (TextUtils.isEmpty(user.address.zip)) {
                                                                                                str10 = user.address.zip;
                                                                                                jSONObject.put("email", str3);
                                                                                                jSONObject.put("mobileNo", str4);
                                                                                                jSONObject.put(PayUmoneyConstants.FIRSTNAME, str);
                                                                                                jSONObject.put("lastName", str2);
                                                                                                if (!flattenJson) {
                                                                                                    jSONObject.put("street1", str5);
                                                                                                    jSONObject.put("street2", str6);
                                                                                                    jSONObject.put("city", str7);
                                                                                                    jSONObject.put("zip", str10);
                                                                                                    jSONObject.put("state", str8);
                                                                                                    jSONObject.put("country", str9);
                                                                                                } else {
                                                                                                    JSONObject jSONObject2 = new JSONObject();
                                                                                                    jSONObject2.put("street1", str5);
                                                                                                    jSONObject2.put("street2", str6);
                                                                                                    jSONObject2.put("city", str7);
                                                                                                    jSONObject2.put("zip", str10);
                                                                                                    jSONObject2.put("state", str8);
                                                                                                    jSONObject2.put("country", str9);
                                                                                                    jSONObject.put("address", jSONObject2);
                                                                                                }
                                                                                                return jSONObject;
                                                                                            }
                                                                                        }
                                                                                        str10 = "400052";
                                                                                        jSONObject.put("email", str3);
                                                                                        jSONObject.put("mobileNo", str4);
                                                                                        jSONObject.put(PayUmoneyConstants.FIRSTNAME, str);
                                                                                        jSONObject.put("lastName", str2);
                                                                                        if (!flattenJson) {
                                                                                        }
                                                                                        return jSONObject;
                                                                                    }
                                                                                }
                                                                                str9 = "India";
                                                                                if (TextUtils.isEmpty(user.address.zip)) {
                                                                                }
                                                                            }
                                                                        }
                                                                        str8 = "Maharashtra";
                                                                        if (TextUtils.isEmpty(user.address.country)) {
                                                                        }
                                                                    }
                                                                }
                                                                str7 = "Mumbai";
                                                                if (TextUtils.isEmpty(user.address.state)) {
                                                                }
                                                            }
                                                        }
                                                        str6 = "streettwo";
                                                        if (TextUtils.isEmpty(user.address.city)) {
                                                        }
                                                    }
                                                }
                                                str5 = "streetone";
                                                if (TextUtils.isEmpty(user.address.street2)) {
                                                }
                                            }
                                        }
                                        str4 = "9999999999";
                                        if (!TextUtils.isEmpty(user.address.street1)) {
                                        }
                                    }
                                }
                                str3 = "tester@gmail.com";
                                if (user != null) {
                                }
                                str4 = "9999999999";
                                if (!TextUtils.isEmpty(user.address.street1)) {
                                }
                            }
                        }
                        str2 = "Citrus";
                        if (user != null) {
                        }
                        str3 = "tester@gmail.com";
                        if (user != null) {
                        }
                        str4 = "9999999999";
                        if (!TextUtils.isEmpty(user.address.street1)) {
                        }
                    }
                } catch (JSONException e2) {
                    e = e2;
                    e.printStackTrace();
                    return jSONObject;
                }
            }
            str = "Tester";
            if (user != null) {
            }
            str2 = "Citrus";
            if (user != null) {
            }
            str3 = "tester@gmail.com";
            if (user != null) {
            }
            str4 = "9999999999";
            if (!TextUtils.isEmpty(user.address.street1)) {
            }
        } catch (JSONException e3) {
            JSONException jSONException = e3;
            jSONObject = null;
            e = jSONException;
            e.printStackTrace();
            return jSONObject;
        }
    }

    public static String toJSON(CitrusUser citrusUser) {
        JSONObject jSONObject = toJSONObject(citrusUser, true);
        return jSONObject != null ? jSONObject.toString() : "";
    }

    /* access modifiers changed from: private */
    public static String normalizeString(String input) {
        return !TextUtils.isEmpty(input) ? input.replaceAll("[\\p{Cntrl}^\r\n\t]+", "") : "";
    }

    public String getEmailId() {
        return this.emailId;
    }

    public void setEmailId(String emailId2) {
        this.emailId = emailId2;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public void setMobileNo(String mobileNo2) {
        this.mobileNo = mobileNo2;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address2) {
        this.address = address2;
    }

    public boolean isEmailVerified() {
        return this.emailVerified;
    }

    public boolean isMobileVerified() {
        return this.mobileVerified;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid2) {
        this.uuid = uuid2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CitrusUser{firstName='");
        sb.append(this.firstName);
        sb.append('\'');
        sb.append(", lastName='");
        sb.append(this.lastName);
        sb.append('\'');
        sb.append(", emailId='");
        sb.append(this.emailId);
        sb.append('\'');
        sb.append(", mobileNo='");
        sb.append(this.mobileNo);
        sb.append('\'');
        sb.append(", address=");
        sb.append(this.address);
        sb.append('}');
        return sb.toString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.emailId);
        dest.writeString(this.mobileNo);
        dest.writeParcelable(this.address, 0);
        dest.writeByte(this.emailVerified ? (byte) 1 : 0);
        dest.writeByte(this.mobileVerified ? (byte) 1 : 0);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CitrusUser citrusUser = (CitrusUser) o;
        if (!this.emailId.equals(citrusUser.emailId)) {
            return false;
        }
        return this.mobileNo.equals(citrusUser.mobileNo);
    }

    public int hashCode() {
        return (this.emailId.hashCode() * 31) + this.mobileNo.hashCode();
    }
}
