package com.bumptech.glide.load.engine;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools.Pool;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.request.ResourceCallback;
import com.bumptech.glide.util.Util;
import com.bumptech.glide.util.pool.FactoryPools.Poolable;
import com.bumptech.glide.util.pool.StateVerifier;
import java.util.ArrayList;
import java.util.List;

class EngineJob<R> implements Callback<R>, Poolable {
    private static final EngineResourceFactory DEFAULT_FACTORY = new EngineResourceFactory();
    private static final Handler MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper(), new MainThreadCallback());
    private static final int MSG_CANCELLED = 3;
    private static final int MSG_COMPLETE = 1;
    private static final int MSG_EXCEPTION = 2;
    private final GlideExecutor animationExecutor;
    private final List<ResourceCallback> cbs;
    private DataSource dataSource;
    private DecodeJob<R> decodeJob;
    private final GlideExecutor diskCacheExecutor;
    private EngineResource<?> engineResource;
    private final EngineResourceFactory engineResourceFactory;
    private GlideException exception;
    private boolean hasLoadFailed;
    private boolean hasResource;
    private List<ResourceCallback> ignoredCallbacks;
    private boolean isCacheable;
    private volatile boolean isCancelled;
    private Key key;
    private final EngineJobListener listener;
    private boolean onlyRetrieveFromCache;
    private final Pool<EngineJob<?>> pool;
    private Resource<?> resource;
    private final GlideExecutor sourceExecutor;
    private final GlideExecutor sourceUnlimitedExecutor;
    private final StateVerifier stateVerifier;
    private boolean useAnimationPool;
    private boolean useUnlimitedSourceGeneratorPool;

    @VisibleForTesting
    static class EngineResourceFactory {
        EngineResourceFactory() {
        }

        public <R> EngineResource<R> build(Resource<R> resource, boolean isMemoryCacheable) {
            return new EngineResource<>(resource, isMemoryCacheable, true);
        }
    }

    private static class MainThreadCallback implements Callback {
        MainThreadCallback() {
        }

        public boolean handleMessage(Message message) {
            EngineJob<?> job = (EngineJob) message.obj;
            switch (message.what) {
                case 1:
                    job.handleResultOnMainThread();
                    break;
                case 2:
                    job.handleExceptionOnMainThread();
                    break;
                case 3:
                    job.handleCancelledOnMainThread();
                    break;
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unrecognized message: ");
                    sb.append(message.what);
                    throw new IllegalStateException(sb.toString());
            }
            return true;
        }
    }

    EngineJob(GlideExecutor diskCacheExecutor2, GlideExecutor sourceExecutor2, GlideExecutor sourceUnlimitedExecutor2, GlideExecutor animationExecutor2, EngineJobListener listener2, Pool<EngineJob<?>> pool2) {
        this(diskCacheExecutor2, sourceExecutor2, sourceUnlimitedExecutor2, animationExecutor2, listener2, pool2, DEFAULT_FACTORY);
    }

    @VisibleForTesting
    EngineJob(GlideExecutor diskCacheExecutor2, GlideExecutor sourceExecutor2, GlideExecutor sourceUnlimitedExecutor2, GlideExecutor animationExecutor2, EngineJobListener listener2, Pool<EngineJob<?>> pool2, EngineResourceFactory engineResourceFactory2) {
        this.cbs = new ArrayList(2);
        this.stateVerifier = StateVerifier.newInstance();
        this.diskCacheExecutor = diskCacheExecutor2;
        this.sourceExecutor = sourceExecutor2;
        this.sourceUnlimitedExecutor = sourceUnlimitedExecutor2;
        this.animationExecutor = animationExecutor2;
        this.listener = listener2;
        this.pool = pool2;
        this.engineResourceFactory = engineResourceFactory2;
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public EngineJob<R> init(Key key2, boolean isCacheable2, boolean useUnlimitedSourceGeneratorPool2, boolean useAnimationPool2, boolean onlyRetrieveFromCache2) {
        this.key = key2;
        this.isCacheable = isCacheable2;
        this.useUnlimitedSourceGeneratorPool = useUnlimitedSourceGeneratorPool2;
        this.useAnimationPool = useAnimationPool2;
        this.onlyRetrieveFromCache = onlyRetrieveFromCache2;
        return this;
    }

    public void start(DecodeJob<R> decodeJob2) {
        GlideExecutor executor;
        this.decodeJob = decodeJob2;
        if (decodeJob2.willDecodeFromCache()) {
            executor = this.diskCacheExecutor;
        } else {
            executor = getActiveSourceExecutor();
        }
        executor.execute(decodeJob2);
    }

    /* access modifiers changed from: 0000 */
    public void addCallback(ResourceCallback cb) {
        Util.assertMainThread();
        this.stateVerifier.throwIfRecycled();
        if (this.hasResource) {
            cb.onResourceReady(this.engineResource, this.dataSource);
        } else if (this.hasLoadFailed) {
            cb.onLoadFailed(this.exception);
        } else {
            this.cbs.add(cb);
        }
    }

    /* access modifiers changed from: 0000 */
    public void removeCallback(ResourceCallback cb) {
        Util.assertMainThread();
        this.stateVerifier.throwIfRecycled();
        if (this.hasResource || this.hasLoadFailed) {
            addIgnoredCallback(cb);
            return;
        }
        this.cbs.remove(cb);
        if (this.cbs.isEmpty()) {
            cancel();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean onlyRetrieveFromCache() {
        return this.onlyRetrieveFromCache;
    }

    private GlideExecutor getActiveSourceExecutor() {
        if (this.useUnlimitedSourceGeneratorPool) {
            return this.sourceUnlimitedExecutor;
        }
        return this.useAnimationPool ? this.animationExecutor : this.sourceExecutor;
    }

    private void addIgnoredCallback(ResourceCallback cb) {
        if (this.ignoredCallbacks == null) {
            this.ignoredCallbacks = new ArrayList(2);
        }
        if (!this.ignoredCallbacks.contains(cb)) {
            this.ignoredCallbacks.add(cb);
        }
    }

    private boolean isInIgnoredCallbacks(ResourceCallback cb) {
        List<ResourceCallback> list = this.ignoredCallbacks;
        return list != null && list.contains(cb);
    }

    /* access modifiers changed from: 0000 */
    public void cancel() {
        if (!this.hasLoadFailed && !this.hasResource && !this.isCancelled) {
            this.isCancelled = true;
            this.decodeJob.cancel();
            this.listener.onEngineJobCancelled(this, this.key);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isCancelled() {
        return this.isCancelled;
    }

    /* access modifiers changed from: 0000 */
    public void handleResultOnMainThread() {
        this.stateVerifier.throwIfRecycled();
        if (this.isCancelled) {
            this.resource.recycle();
            release(false);
        } else if (this.cbs.isEmpty()) {
            throw new IllegalStateException("Received a resource without any callbacks to notify");
        } else if (!this.hasResource) {
            this.engineResource = this.engineResourceFactory.build(this.resource, this.isCacheable);
            this.hasResource = true;
            this.engineResource.acquire();
            this.listener.onEngineJobComplete(this, this.key, this.engineResource);
            int size = this.cbs.size();
            for (int i = 0; i < size; i++) {
                ResourceCallback cb = (ResourceCallback) this.cbs.get(i);
                if (!isInIgnoredCallbacks(cb)) {
                    this.engineResource.acquire();
                    cb.onResourceReady(this.engineResource, this.dataSource);
                }
            }
            this.engineResource.release();
            release(false);
        } else {
            throw new IllegalStateException("Already have resource");
        }
    }

    /* access modifiers changed from: 0000 */
    public void handleCancelledOnMainThread() {
        this.stateVerifier.throwIfRecycled();
        if (this.isCancelled) {
            this.listener.onEngineJobCancelled(this, this.key);
            release(false);
            return;
        }
        throw new IllegalStateException("Not cancelled");
    }

    private void release(boolean isRemovedFromQueue) {
        Util.assertMainThread();
        this.cbs.clear();
        this.key = null;
        this.engineResource = null;
        this.resource = null;
        List<ResourceCallback> list = this.ignoredCallbacks;
        if (list != null) {
            list.clear();
        }
        this.hasLoadFailed = false;
        this.isCancelled = false;
        this.hasResource = false;
        this.decodeJob.release(isRemovedFromQueue);
        this.decodeJob = null;
        this.exception = null;
        this.dataSource = null;
        this.pool.release(this);
    }

    public void onResourceReady(Resource<R> resource2, DataSource dataSource2) {
        this.resource = resource2;
        this.dataSource = dataSource2;
        MAIN_THREAD_HANDLER.obtainMessage(1, this).sendToTarget();
    }

    public void onLoadFailed(GlideException e) {
        this.exception = e;
        MAIN_THREAD_HANDLER.obtainMessage(2, this).sendToTarget();
    }

    public void reschedule(DecodeJob<?> job) {
        getActiveSourceExecutor().execute(job);
    }

    /* access modifiers changed from: 0000 */
    public void handleExceptionOnMainThread() {
        this.stateVerifier.throwIfRecycled();
        if (this.isCancelled) {
            release(false);
        } else if (this.cbs.isEmpty()) {
            throw new IllegalStateException("Received an exception without any callbacks to notify");
        } else if (!this.hasLoadFailed) {
            this.hasLoadFailed = true;
            this.listener.onEngineJobComplete(this, this.key, null);
            for (ResourceCallback cb : this.cbs) {
                if (!isInIgnoredCallbacks(cb)) {
                    cb.onLoadFailed(this.exception);
                }
            }
            release(false);
        } else {
            throw new IllegalStateException("Already failed once");
        }
    }

    @NonNull
    public StateVerifier getVerifier() {
        return this.stateVerifier;
    }
}
