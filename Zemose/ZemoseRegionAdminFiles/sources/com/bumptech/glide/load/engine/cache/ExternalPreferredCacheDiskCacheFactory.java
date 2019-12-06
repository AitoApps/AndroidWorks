package com.bumptech.glide.load.engine.cache;

import android.content.Context;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.engine.cache.DiskCache.Factory;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory.CacheDirectoryGetter;
import java.io.File;

public final class ExternalPreferredCacheDiskCacheFactory extends DiskLruCacheFactory {
    public ExternalPreferredCacheDiskCacheFactory(Context context) {
        this(context, Factory.DEFAULT_DISK_CACHE_DIR, 262144000);
    }

    public ExternalPreferredCacheDiskCacheFactory(Context context, long diskCacheSize) {
        this(context, Factory.DEFAULT_DISK_CACHE_DIR, diskCacheSize);
    }

    public ExternalPreferredCacheDiskCacheFactory(final Context context, final String diskCacheName, long diskCacheSize) {
        super((CacheDirectoryGetter) new CacheDirectoryGetter() {
            @Nullable
            private File getInternalCacheDirectory() {
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

            public File getCacheDirectory() {
                File internalCacheDirectory = getInternalCacheDirectory();
                if (internalCacheDirectory != null && internalCacheDirectory.exists()) {
                    return internalCacheDirectory;
                }
                File cacheDirectory = context.getExternalCacheDir();
                if (cacheDirectory == null || !cacheDirectory.canWrite()) {
                    return internalCacheDirectory;
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
