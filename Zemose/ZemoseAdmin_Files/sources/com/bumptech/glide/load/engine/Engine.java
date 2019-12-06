package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools.Pool;
import android.util.Log;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskCacheAdapter;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.cache.MemoryCache.ResourceRemovedListener;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.request.ResourceCallback;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.FactoryPools.Factory;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Engine implements EngineJobListener, ResourceRemovedListener, ResourceListener {
    private static final int JOB_POOL_SIZE = 150;
    private static final String TAG = "Engine";
    private static final boolean VERBOSE_IS_LOGGABLE = Log.isLoggable(TAG, 2);
    private final ActiveResources activeResources;
    private final MemoryCache cache;
    private final DecodeJobFactory decodeJobFactory;
    private final LazyDiskCacheProvider diskCacheProvider;
    private final EngineJobFactory engineJobFactory;
    private final Jobs jobs;
    private final EngineKeyFactory keyFactory;
    private final ResourceRecycler resourceRecycler;

    @VisibleForTesting
    static class DecodeJobFactory {
        private int creationOrder;
        final DiskCacheProvider diskCacheProvider;
        final Pool<DecodeJob<?>> pool = FactoryPools.simple(Engine.JOB_POOL_SIZE, new Factory<DecodeJob<?>>() {
            public DecodeJob<?> create() {
                return new DecodeJob<>(DecodeJobFactory.this.diskCacheProvider, DecodeJobFactory.this.pool);
            }
        });

        DecodeJobFactory(DiskCacheProvider diskCacheProvider2) {
            this.diskCacheProvider = diskCacheProvider2;
        }

        /* access modifiers changed from: 0000 */
        public <R> DecodeJob<R> build(GlideContext glideContext, Object model, EngineKey loadKey, Key signature, int width, int height, Class<?> resourceClass, Class<R> transcodeClass, Priority priority, DiskCacheStrategy diskCacheStrategy, Map<Class<?>, Transformation<?>> transformations, boolean isTransformationRequired, boolean isScaleOnlyOrNoTransform, boolean onlyRetrieveFromCache, Options options, Callback<R> callback) {
            GlideContext glideContext2 = glideContext;
            Object obj = model;
            EngineKey engineKey = loadKey;
            Key key = signature;
            int i = width;
            int i2 = height;
            Class<?> cls = resourceClass;
            Class<R> cls2 = transcodeClass;
            Priority priority2 = priority;
            DiskCacheStrategy diskCacheStrategy2 = diskCacheStrategy;
            Map<Class<?>, Transformation<?>> map = transformations;
            boolean z = isTransformationRequired;
            boolean z2 = isScaleOnlyOrNoTransform;
            boolean z3 = onlyRetrieveFromCache;
            Options options2 = options;
            Callback<R> callback2 = callback;
            DecodeJob decodeJob = (DecodeJob) Preconditions.checkNotNull((DecodeJob) this.pool.acquire());
            int i3 = this.creationOrder;
            int i4 = i3;
            this.creationOrder = i3 + 1;
            return decodeJob.init(glideContext2, obj, engineKey, key, i, i2, cls, cls2, priority2, diskCacheStrategy2, map, z, z2, z3, options2, callback2, i4);
        }
    }

    @VisibleForTesting
    static class EngineJobFactory {
        final GlideExecutor animationExecutor;
        final GlideExecutor diskCacheExecutor;
        final EngineJobListener listener;
        final Pool<EngineJob<?>> pool = FactoryPools.simple(Engine.JOB_POOL_SIZE, new Factory<EngineJob<?>>() {
            public EngineJob<?> create() {
                EngineJob engineJob = new EngineJob(EngineJobFactory.this.diskCacheExecutor, EngineJobFactory.this.sourceExecutor, EngineJobFactory.this.sourceUnlimitedExecutor, EngineJobFactory.this.animationExecutor, EngineJobFactory.this.listener, EngineJobFactory.this.pool);
                return engineJob;
            }
        });
        final GlideExecutor sourceExecutor;
        final GlideExecutor sourceUnlimitedExecutor;

        EngineJobFactory(GlideExecutor diskCacheExecutor2, GlideExecutor sourceExecutor2, GlideExecutor sourceUnlimitedExecutor2, GlideExecutor animationExecutor2, EngineJobListener listener2) {
            this.diskCacheExecutor = diskCacheExecutor2;
            this.sourceExecutor = sourceExecutor2;
            this.sourceUnlimitedExecutor = sourceUnlimitedExecutor2;
            this.animationExecutor = animationExecutor2;
            this.listener = listener2;
        }

        /* access modifiers changed from: 0000 */
        @VisibleForTesting
        public void shutdown() {
            shutdownAndAwaitTermination(this.diskCacheExecutor);
            shutdownAndAwaitTermination(this.sourceExecutor);
            shutdownAndAwaitTermination(this.sourceUnlimitedExecutor);
            shutdownAndAwaitTermination(this.animationExecutor);
        }

        /* access modifiers changed from: 0000 */
        public <R> EngineJob<R> build(Key key, boolean isMemoryCacheable, boolean useUnlimitedSourceGeneratorPool, boolean useAnimationPool, boolean onlyRetrieveFromCache) {
            return ((EngineJob) Preconditions.checkNotNull((EngineJob) this.pool.acquire())).init(key, isMemoryCacheable, useUnlimitedSourceGeneratorPool, useAnimationPool, onlyRetrieveFromCache);
        }

        private static void shutdownAndAwaitTermination(ExecutorService pool2) {
            pool2.shutdown();
            try {
                if (!pool2.awaitTermination(5, TimeUnit.SECONDS)) {
                    pool2.shutdownNow();
                    if (!pool2.awaitTermination(5, TimeUnit.SECONDS)) {
                        throw new RuntimeException("Failed to shutdown");
                    }
                }
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
        }
    }

    private static class LazyDiskCacheProvider implements DiskCacheProvider {
        private volatile DiskCache diskCache;
        private final DiskCache.Factory factory;

        LazyDiskCacheProvider(DiskCache.Factory factory2) {
            this.factory = factory2;
        }

        /* access modifiers changed from: 0000 */
        @VisibleForTesting
        public synchronized void clearDiskCacheIfCreated() {
            if (this.diskCache != null) {
                this.diskCache.clear();
            }
        }

        public DiskCache getDiskCache() {
            if (this.diskCache == null) {
                synchronized (this) {
                    if (this.diskCache == null) {
                        this.diskCache = this.factory.build();
                    }
                    if (this.diskCache == null) {
                        this.diskCache = new DiskCacheAdapter();
                    }
                }
            }
            return this.diskCache;
        }
    }

    public static class LoadStatus {
        private final ResourceCallback cb;
        private final EngineJob<?> engineJob;

        LoadStatus(ResourceCallback cb2, EngineJob<?> engineJob2) {
            this.cb = cb2;
            this.engineJob = engineJob2;
        }

        public void cancel() {
            this.engineJob.removeCallback(this.cb);
        }
    }

    public Engine(MemoryCache memoryCache, DiskCache.Factory diskCacheFactory, GlideExecutor diskCacheExecutor, GlideExecutor sourceExecutor, GlideExecutor sourceUnlimitedExecutor, GlideExecutor animationExecutor, boolean isActiveResourceRetentionAllowed) {
        this(memoryCache, diskCacheFactory, diskCacheExecutor, sourceExecutor, sourceUnlimitedExecutor, animationExecutor, null, null, null, null, null, null, isActiveResourceRetentionAllowed);
    }

    @VisibleForTesting
    Engine(MemoryCache cache2, DiskCache.Factory diskCacheFactory, GlideExecutor diskCacheExecutor, GlideExecutor sourceExecutor, GlideExecutor sourceUnlimitedExecutor, GlideExecutor animationExecutor, Jobs jobs2, EngineKeyFactory keyFactory2, ActiveResources activeResources2, EngineJobFactory engineJobFactory2, DecodeJobFactory decodeJobFactory2, ResourceRecycler resourceRecycler2, boolean isActiveResourceRetentionAllowed) {
        ActiveResources activeResources3;
        EngineKeyFactory keyFactory3;
        Jobs jobs3;
        EngineJobFactory engineJobFactory3;
        DecodeJobFactory decodeJobFactory3;
        ResourceRecycler resourceRecycler3;
        this.cache = cache2;
        this.diskCacheProvider = new LazyDiskCacheProvider(diskCacheFactory);
        if (activeResources2 == null) {
            activeResources3 = new ActiveResources(isActiveResourceRetentionAllowed);
        } else {
            boolean z = isActiveResourceRetentionAllowed;
            activeResources3 = activeResources2;
        }
        this.activeResources = activeResources3;
        activeResources3.setListener(this);
        if (keyFactory2 == null) {
            keyFactory3 = new EngineKeyFactory();
        } else {
            keyFactory3 = keyFactory2;
        }
        this.keyFactory = keyFactory3;
        if (jobs2 == null) {
            jobs3 = new Jobs();
        } else {
            jobs3 = jobs2;
        }
        this.jobs = jobs3;
        if (engineJobFactory2 == null) {
            engineJobFactory3 = new EngineJobFactory(diskCacheExecutor, sourceExecutor, sourceUnlimitedExecutor, animationExecutor, this);
        } else {
            engineJobFactory3 = engineJobFactory2;
        }
        this.engineJobFactory = engineJobFactory3;
        if (decodeJobFactory2 == null) {
            decodeJobFactory3 = new DecodeJobFactory(this.diskCacheProvider);
        } else {
            decodeJobFactory3 = decodeJobFactory2;
        }
        this.decodeJobFactory = decodeJobFactory3;
        if (resourceRecycler2 == null) {
            resourceRecycler3 = new ResourceRecycler();
        } else {
            resourceRecycler3 = resourceRecycler2;
        }
        this.resourceRecycler = resourceRecycler3;
        cache2.setResourceRemovedListener(this);
    }

    public <R> LoadStatus load(GlideContext glideContext, Object model, Key signature, int width, int height, Class<?> resourceClass, Class<R> transcodeClass, Priority priority, DiskCacheStrategy diskCacheStrategy, Map<Class<?>, Transformation<?>> transformations, boolean isTransformationRequired, boolean isScaleOnlyOrNoTransform, Options options, boolean isMemoryCacheable, boolean useUnlimitedSourceExecutorPool, boolean useAnimationPool, boolean onlyRetrieveFromCache, ResourceCallback cb) {
        boolean z = isMemoryCacheable;
        ResourceCallback resourceCallback = cb;
        Util.assertMainThread();
        long startTime = VERBOSE_IS_LOGGABLE ? LogTime.getLogTime() : 0;
        EngineKey key = this.keyFactory.buildKey(model, signature, width, height, transformations, resourceClass, transcodeClass, options);
        EngineResource<?> active = loadFromActiveResources(key, z);
        if (active != null) {
            resourceCallback.onResourceReady(active, DataSource.MEMORY_CACHE);
            if (VERBOSE_IS_LOGGABLE) {
                logWithTimeAndKey("Loaded resource from active resources", startTime, key);
            }
            return null;
        }
        EngineResource<?> cached = loadFromCache(key, z);
        if (cached != null) {
            resourceCallback.onResourceReady(cached, DataSource.MEMORY_CACHE);
            if (VERBOSE_IS_LOGGABLE) {
                logWithTimeAndKey("Loaded resource from cache", startTime, key);
            }
            return null;
        }
        EngineJob<?> current = this.jobs.get(key, onlyRetrieveFromCache);
        if (current != null) {
            current.addCallback(resourceCallback);
            if (VERBOSE_IS_LOGGABLE) {
                logWithTimeAndKey("Added to existing load", startTime, key);
            }
            return new LoadStatus(resourceCallback, current);
        }
        EngineJob<R> engineJob = this.engineJobFactory.build(key, isMemoryCacheable, useUnlimitedSourceExecutorPool, useAnimationPool, onlyRetrieveFromCache);
        EngineJob engineJob2 = current;
        EngineResource engineResource = cached;
        EngineResource engineResource2 = active;
        DecodeJob<R> decodeJob = this.decodeJobFactory.build(glideContext, model, key, signature, width, height, resourceClass, transcodeClass, priority, diskCacheStrategy, transformations, isTransformationRequired, isScaleOnlyOrNoTransform, onlyRetrieveFromCache, options, engineJob);
        this.jobs.put(key, engineJob);
        engineJob.addCallback(resourceCallback);
        engineJob.start(decodeJob);
        if (VERBOSE_IS_LOGGABLE) {
            logWithTimeAndKey("Started new load", startTime, key);
        }
        return new LoadStatus(resourceCallback, engineJob);
    }

    private static void logWithTimeAndKey(String log, long startTime, Key key) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(log);
        sb.append(" in ");
        sb.append(LogTime.getElapsedMillis(startTime));
        sb.append("ms, key: ");
        sb.append(key);
        Log.v(str, sb.toString());
    }

    @Nullable
    private EngineResource<?> loadFromActiveResources(Key key, boolean isMemoryCacheable) {
        if (!isMemoryCacheable) {
            return null;
        }
        EngineResource<?> active = this.activeResources.get(key);
        if (active != null) {
            active.acquire();
        }
        return active;
    }

    private EngineResource<?> loadFromCache(Key key, boolean isMemoryCacheable) {
        if (!isMemoryCacheable) {
            return null;
        }
        EngineResource<?> cached = getEngineResourceFromCache(key);
        if (cached != null) {
            cached.acquire();
            this.activeResources.activate(key, cached);
        }
        return cached;
    }

    private EngineResource<?> getEngineResourceFromCache(Key key) {
        Resource<?> cached = this.cache.remove(key);
        if (cached == null) {
            return null;
        }
        if (cached instanceof EngineResource) {
            return (EngineResource) cached;
        }
        return new EngineResource<>(cached, true, true);
    }

    public void release(Resource<?> resource) {
        Util.assertMainThread();
        if (resource instanceof EngineResource) {
            ((EngineResource) resource).release();
            return;
        }
        throw new IllegalArgumentException("Cannot release anything but an EngineResource");
    }

    public void onEngineJobComplete(EngineJob<?> engineJob, Key key, EngineResource<?> resource) {
        Util.assertMainThread();
        if (resource != null) {
            resource.setResourceListener(key, this);
            if (resource.isCacheable()) {
                this.activeResources.activate(key, resource);
            }
        }
        this.jobs.removeIfCurrent(key, engineJob);
    }

    public void onEngineJobCancelled(EngineJob<?> engineJob, Key key) {
        Util.assertMainThread();
        this.jobs.removeIfCurrent(key, engineJob);
    }

    public void onResourceRemoved(@NonNull Resource<?> resource) {
        Util.assertMainThread();
        this.resourceRecycler.recycle(resource);
    }

    public void onResourceReleased(Key cacheKey, EngineResource<?> resource) {
        Util.assertMainThread();
        this.activeResources.deactivate(cacheKey);
        if (resource.isCacheable()) {
            this.cache.put(cacheKey, resource);
        } else {
            this.resourceRecycler.recycle(resource);
        }
    }

    public void clearDiskCache() {
        this.diskCacheProvider.getDiskCache().clear();
    }

    @VisibleForTesting
    public void shutdown() {
        this.engineJobFactory.shutdown();
        this.diskCacheProvider.clearDiskCacheIfCreated();
        this.activeResources.shutdown();
    }
}
