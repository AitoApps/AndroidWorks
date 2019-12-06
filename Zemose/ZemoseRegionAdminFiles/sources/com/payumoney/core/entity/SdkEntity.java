package com.payumoney.core.entity;

import android.os.Parcelable;

public abstract class SdkEntity implements Parcelable {
    private boolean deleted = false;
    private long id = 0;

    public long getId() {
        return this.id;
    }

    public void setId(long id2) {
        this.id = id2;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted2) {
        this.deleted = deleted2;
    }
}
