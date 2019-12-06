package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.data.DataFetcher.DataCallback;
import com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoader.LoadData;
import java.io.File;
import java.util.List;

class ResourceCacheGenerator implements DataFetcherGenerator, DataCallback<Object> {
    private File cacheFile;
    private final FetcherReadyCallback cb;
    private ResourceCacheKey currentKey;
    private final DecodeHelper<?> helper;
    private volatile LoadData<?> loadData;
    private int modelLoaderIndex;
    private List<ModelLoader<File, ?>> modelLoaders;
    private int resourceClassIndex = -1;
    private int sourceIdIndex;
    private Key sourceKey;

    ResourceCacheGenerator(DecodeHelper<?> helper2, FetcherReadyCallback cb2) {
        this.helper = helper2;
        this.cb = cb2;
    }

    public boolean startNext() {
        List<Key> sourceIds = this.helper.getCacheKeys();
        boolean z = false;
        if (sourceIds.isEmpty()) {
            return false;
        }
        List<Class<?>> resourceClasses = this.helper.getRegisteredResourceClasses();
        if (resourceClasses.isEmpty() && File.class.equals(this.helper.getTranscodeClass())) {
            return false;
        }
        while (true) {
            if (this.modelLoaders == null || !hasNextModelLoader()) {
                this.resourceClassIndex++;
                if (this.resourceClassIndex >= resourceClasses.size()) {
                    this.sourceIdIndex++;
                    if (this.sourceIdIndex >= sourceIds.size()) {
                        return z;
                    }
                    this.resourceClassIndex = z ? 1 : 0;
                }
                Key sourceId = (Key) sourceIds.get(this.sourceIdIndex);
                Class<?> resourceClass = (Class) resourceClasses.get(this.resourceClassIndex);
                Key key = sourceId;
                ResourceCacheKey resourceCacheKey = r5;
                ResourceCacheKey resourceCacheKey2 = new ResourceCacheKey(this.helper.getArrayPool(), key, this.helper.getSignature(), this.helper.getWidth(), this.helper.getHeight(), this.helper.getTransformation(resourceClass), resourceClass, this.helper.getOptions());
                this.currentKey = resourceCacheKey;
                this.cacheFile = this.helper.getDiskCache().get(this.currentKey);
                File file = this.cacheFile;
                if (file != null) {
                    this.sourceKey = sourceId;
                    this.modelLoaders = this.helper.getModelLoaders(file);
                    z = false;
                    this.modelLoaderIndex = 0;
                } else {
                    z = false;
                }
            } else {
                this.loadData = null;
                boolean started = false;
                while (!started && hasNextModelLoader()) {
                    List<ModelLoader<File, ?>> list = this.modelLoaders;
                    int i = this.modelLoaderIndex;
                    this.modelLoaderIndex = i + 1;
                    this.loadData = ((ModelLoader) list.get(i)).buildLoadData(this.cacheFile, this.helper.getWidth(), this.helper.getHeight(), this.helper.getOptions());
                    if (this.loadData != null && this.helper.hasLoadPath(this.loadData.fetcher.getDataClass())) {
                        started = true;
                        this.loadData.fetcher.loadData(this.helper.getPriority(), this);
                    }
                }
                return started;
            }
        }
    }

    private boolean hasNextModelLoader() {
        return this.modelLoaderIndex < this.modelLoaders.size();
    }

    public void cancel() {
        LoadData<?> local = this.loadData;
        if (local != null) {
            local.fetcher.cancel();
        }
    }

    public void onDataReady(Object data2) {
        this.cb.onDataFetcherReady(this.sourceKey, data2, this.loadData.fetcher, DataSource.RESOURCE_DISK_CACHE, this.currentKey);
    }

    public void onLoadFailed(@NonNull Exception e) {
        this.cb.onDataFetcherFailed(this.currentKey, e, this.loadData.fetcher, DataSource.RESOURCE_DISK_CACHE);
    }
}
