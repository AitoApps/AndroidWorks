package com.bumptech.glide.load.resource;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.util.Preconditions;

public class SimpleResource<T> implements Resource<T> {

    /* renamed from: data reason: collision with root package name */
    protected final T f10data;

    public SimpleResource(@NonNull T data2) {
        this.f10data = Preconditions.checkNotNull(data2);
    }

    @NonNull
    public Class<T> getResourceClass() {
        return this.f10data.getClass();
    }

    @NonNull
    public final T get() {
        return this.f10data;
    }

    public final int getSize() {
        return 1;
    }

    public void recycle() {
    }
}
