package com.payu.custombrowser.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.payu.custombrowser.util.CBConstant;

public class a implements Parcelable {
    public static final Creator<a> CREATOR = new Creator<a>() {
        /* renamed from: a */
        public a createFromParcel(Parcel parcel) {
            return new a(parcel);
        }

        /* renamed from: a */
        public a[] newArray(int i) {
            return new a[i];
        }
    };
    private String a;
    private String b;
    private String c;
    private String d;

    protected a(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
    }

    public a() {
        this.a = "GET";
        this.d = CBConstant.HTTP_URLENCODED;
    }

    public String a() {
        return this.a;
    }

    public void a(String str) {
        this.a = str;
    }

    public String b() {
        return this.b;
    }

    public void b(String str) {
        this.b = str;
    }

    public String c() {
        return this.c;
    }

    public void c(String str) {
        this.c = str;
    }

    public String d() {
        return this.d;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
    }
}
