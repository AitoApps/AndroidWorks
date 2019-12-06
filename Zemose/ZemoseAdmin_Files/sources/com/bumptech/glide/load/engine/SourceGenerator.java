package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.DataFetcher.DataCallback;
import com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback;
import com.bumptech.glide.load.model.ModelLoader.LoadData;
import com.bumptech.glide.util.LogTime;
import java.util.Collections;
import java.util.List;

class SourceGenerator implements DataFetcherGenerator, DataCallback<Object>, FetcherReadyCallback {
    private static final String TAG = "SourceGenerator";
    private final FetcherReadyCallback cb;
    private Object dataToCache;
    private final DecodeHelper<?> helper;
    private volatile LoadData<?> loadData;
    private int loadDataListIndex;
    private DataCacheKey originalKey;
    private DataCacheGenerator sourceCacheGenerator;

    SourceGenerator(DecodeHelper<?> helper2, FetcherReadyCallback cb2) {
        this.helper = helper2;
        this.cb = cb2;
    }

    public boolean startNext() {
        if (this.dataToCache != null) {
            Object data2 = this.dataToCache;
            this.dataToCache = null;
            cacheData(data2);
        }
        DataCacheGenerator dataCacheGenerator = this.sourceCacheGenerator;
        if (dataCacheGenerator != null && dataCacheGenerator.startNext()) {
            return true;
        }
        this.sourceCacheGenerator = null;
        this.loadData = null;
        boolean started = false;
        while (!started && hasNextModelLoader()) {
            List loadData2 = this.helper.getLoadData();
            int i = this.loadDataListIndex;
            this.loadDataListIndex = i + 1;
            this.loadData = (LoadData) loadData2.get(i);
            if (this.loadData != null && (this.helper.getDiskCacheStrategy().isDataCacheable(this.loadData.fetcher.getDataSource()) || this.helper.hasLoadPath(this.loadData.fetcher.getDataClass()))) {
                started = true;
                this.loadData.fetcher.loadData(this.helper.getPriority(), this);
            }
        }
        return started;
    }

    private boolean hasNextModelLoader() {
        return this.loadDataListIndex < this.helper.getLoadData().size();
    }

    /* JADX INFO: finally extract failed */
    private void cacheData(Object dataToCache2) {
        long startTime = LogTime.getLogTime();
        try {
            Encoder<Object> encoder = this.helper.getSourceEncoder(dataToCache2);
            DataCacheWriter<Object> writer = new DataCacheWriter<>(encoder, dataToCache2, this.helper.getOptions());
            this.originalKey = new DataCacheKey(this.loadData.sourceKey, this.helper.getSignature());
            this.helper.getDiskCache().put(this.originalKey, writer);
            if (Log.isLoggable(TAG, 2)) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Finished encoding source to cache, key: ");
                sb.append(this.originalKey);
                sb.append(", data: ");
                sb.append(dataToCache2);
                sb.append(", encoder: ");
                sb.append(encoder);
                sb.append(", duration: ");
                sb.append(LogTime.getElapsedMillis(startTime));
                Log.v(str, sb.toString());
            }
            this.loadData.fetcher.cleanup();
            this.sourceCacheGenerator = new DataCacheGenerator(Collections.singletonList(this.loadData.sourceKey), this.helper, this);
        } catch (Throwable th) {
            this.loadData.fetcher.cleanup();
            throw th;
        }
    }

    public void cancel() {
        LoadData<?> local = this.loadData;
        if (local != null) {
            local.fetcher.cancel();
        }
    }

    public void onDataReady(Object data2) {
        DiskCacheStrategy diskCacheStrategy = this.helper.getDiskCacheStrategy();
        if (data2 == null || !diskCacheStrategy.isDataCacheable(this.loadData.fetcher.getDataSource())) {
            this.cb.onDataFetcherReady(this.loadData.sourceKey, data2, this.loadData.fetcher, this.loadData.fetcher.getDataSource(), this.originalKey);
            return;
        }
        this.dataToCache = data2;
        this.cb.reschedule();
    }

    public void onLoadFailed(@NonNull Exception e) {
        this.cb.onDataFetcherFailed(this.originalKey, e, this.loadData.fetcher, this.loadData.fetcher.getDataSource());
    }

    public void reschedule() {
        throw new UnsupportedOperationException();
    }

    public void onDataFetcherReady(Key sourceKey, Object data2, DataFetcher<?> fetcher, DataSource dataSource, Key attemptedKey) {
        this.cb.onDataFetcherReady(sourceKey, data2, fetcher, this.loadData.fetcher.getDataSource(), sourceKey);
    }

    public void onDataFetcherFailed(Key sourceKey, Exception e, DataFetcher<?> fetcher, DataSource dataSource) {
        this.cb.onDataFetcherFailed(sourceKey, e, fetcher, this.loadData.fetcher.getDataSource());
    }
}
