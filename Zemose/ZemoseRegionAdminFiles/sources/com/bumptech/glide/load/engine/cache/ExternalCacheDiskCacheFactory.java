package com.bumptech.glide.load.engine.cache;

import android.content.Context;
import com.bumptech.glide.load.engine.cache.DiskCache.Factory;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory.CacheDirectoryGetter;
import java.io.File;

@Deprecated
public final class ExternalCacheDiskCacheFactory extends DiskLruCacheFactory {
    public ExternalCacheDiskCacheFactory(Context context) {
        this(context, Factory.DEFAULT_DISK_CACHE_DIR, Factory.DEFAULT_DISK_CACHE_SIZE);
    }

    public ExternalCacheDiskCacheFactory(Context context, int diskCacheSize) {
        this(context, Factory.DEFAULT_DISK_CACHE_DIR, diskCacheSize);
    }

    public ExternalCacheDiskCacheFactory(final Context context, final String diskCacheName, int diskCacheSize) {
        super((CacheDirectoryGetter) new CacheDirectoryGetter() {
            public File getCacheDirectory() {
                File cacheDirectory = context.getExternalCacheDir();
                if (cacheDirectory == null) {
                    return null;
                }
                String str = diskCacheName;
                if (str != null) {
                    return new File(cacheDirectory, str);
                }
                return cacheDirectory;
            }
        }, (long) diskCacheSize);
    }
}
