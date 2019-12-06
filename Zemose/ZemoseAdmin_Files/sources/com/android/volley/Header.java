package com.android.volley;

import android.text.TextUtils;

public final class Header {
    private final String mName;
    private final String mValue;

    public Header(String name, String value) {
        this.mName = name;
        this.mValue = value;
    }

    public final String getName() {
        return this.mName;
    }

    public final String getValue() {
        return this.mValue;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Header header = (Header) o;
        if (!TextUtils.equals(this.mName, header.mName) || !TextUtils.equals(this.mValue, header.mValue)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (this.mName.hashCode() * 31) + this.mValue.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Header[name=");
        sb.append(this.mName);
        sb.append(",value=");
        sb.append(this.mValue);
        sb.append("]");
        return sb.toString();
    }
}
