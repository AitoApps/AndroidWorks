package com.payu.custombrowser.upiintent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

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
    private Drawable b;
    private String c;

    private a(Parcel parcel) {
        a(parcel);
    }

    public a(String str, Drawable drawable, String str2) {
        this.a = str;
        this.b = drawable;
        this.c = str2;
    }

    public String a() {
        return this.c;
    }

    public String b() {
        return this.a;
    }

    public void a(String str) {
        this.a = str;
    }

    public Drawable c() {
        return this.b;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof a)) {
            return false;
        }
        return this.c.equalsIgnoreCase(((a) o).c);
    }

    public int hashCode() {
        Drawable drawable = this.b;
        return 217 + (drawable == null ? this.a.length() : drawable.hashCode());
    }

    public int describeContents() {
        return 0;
    }

    private void a(Parcel parcel) {
        this.a = parcel.readString();
        this.b = new BitmapDrawable((Bitmap) parcel.readParcelable(getClass().getClassLoader()));
        this.c = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        Drawable drawable = this.b;
        if (drawable != null) {
            parcel.writeParcelable(((BitmapDrawable) drawable).getBitmap(), 0);
        }
        parcel.writeString(this.c);
    }
}
