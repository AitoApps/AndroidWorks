package com.bumptech.glide.load.data;

import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher.DataCallback;
import java.io.IOException;

public abstract class AssetPathFetcher<T> implements DataFetcher<T> {
    private static final String TAG = "AssetPathFetcher";
    private final AssetManager assetManager;
    private final String assetPath;

    /* renamed from: data reason: collision with root package name */
    private T f4data;

    /* access modifiers changed from: protected */
    public abstract void close(T t) throws IOException;

    /* access modifiers changed from: protected */
    public abstract T loadResource(AssetManager assetManager2, String str) throws IOException;

    public AssetPathFetcher(AssetManager assetManager2, String assetPath2) {
        this.assetManager = assetManager2;
        this.assetPath = assetPath2;
    }

    public void loadData(@NonNull Priority priority, @NonNull DataCallback<? super T> callback) {
        try {
            this.f4data = loadResource(this.assetManager, this.assetPath);
            callback.onDataReady(this.f4data);
        } catch (IOException e) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to load data from asset manager", e);
            }
            callback.onLoadFailed(e);
        }
    }

    public void cleanup() {
        T t = this.f4data;
        if (t != null) {
            try {
                close(t);
            } catch (IOException e) {
            }
        }
    }

    public void cancel() {
    }

    @NonNull
    public DataSource getDataSource() {
        return DataSource.LOCAL;
    }
}
