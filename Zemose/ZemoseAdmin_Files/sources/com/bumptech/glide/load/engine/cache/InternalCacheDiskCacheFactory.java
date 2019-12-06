package com.bumptech.glide.load.engine.cache;

import android.content.Context;
import com.bumptech.glide.load.engine.cache.DiskCache.Factory;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory.CacheDirectoryGetter;
import java.io.File;

public final class InternalCacheDiskCacheFactory extends DiskLruCacheFactory {
    public InternalCacheDiskCacheFactory(Context context) {
        this(context, Factory.DEFAULT_DISK_CACHE_DIR, 262144000);
    }

    public InternalCacheDiskCacheFactory(Context context, long diskCacheSize) {
        this(context, Factory.DEFAULT_DISK_CACHE_DIR, diskCacheSize);
    }

    public InternalCacheDiskCacheFactory(final Context context, final String diskCacheName, long diskCacheSize) {
        super((CacheDirectoryGetter) new CacheDirectoryGetter() {
            public File getCacheDirectory() {
                File cacheDirectory = context.getCacheDir();
                if (cacheDirectory == null) {
                    return null;
                }
                String str = diskCacheName;
                if (str != null) {
                    return new File(cacheDirectory, str);
                }
                return cacheDirectory;
            }
        }, diskCacheSize);
    }
}
