package com.payu.custombrowser.upiintent;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.List;

public class d implements Parcelable {
    public static final Creator<d> CREATOR = new Creator<d>() {
        /* renamed from: a */
        public d createFromParcel(Parcel parcel) {
            return new d(parcel);
        }

        /* renamed from: a */
        public d[] newArray(int i) {
            return new d[i];
        }
    };
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h = "0";
    private List<a> i;
    private String j;
    private String k;

    protected d(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.readString();
        this.f = parcel.readString();
        this.g = parcel.readString();
        this.h = parcel.readString();
        this.j = parcel.readString();
        this.k = parcel.readString();
        this.i = parcel.createTypedArrayList(a.CREATOR);
    }

    d() {
    }

    public void a(String str) {
        this.a = str;
    }

    public String a() {
        return this.b;
    }

    public void b(String str) {
        this.b = str;
    }

    public void c(String str) {
        this.c = str;
    }

    public void d(String str) {
        this.d = str;
    }

    public void e(String str) {
        this.e = str;
    }

    public void f(String str) {
        this.f = str;
    }

    public List<a> b() {
        return this.i;
    }

    public void a(List<a> list) {
        this.i = list;
    }

    public String c() {
        return this.g;
    }

    public void g(String str) {
        this.g = str;
    }

    public String d() {
        return this.h;
    }

    public void h(String str) {
        this.h = str;
    }

    public String e() {
        return this.j;
    }

    public void i(String str) {
        this.j = str;
    }

    public String f() {
        return this.k;
    }

    public void j(String str) {
        this.k = str;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeString(this.h);
        parcel.writeString(this.j);
        parcel.writeString(this.k);
        parcel.writeTypedList(this.i);
    }
}
