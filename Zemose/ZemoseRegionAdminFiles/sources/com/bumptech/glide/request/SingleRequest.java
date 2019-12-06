package com.bumptech.glide.request;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pools.Pool;
import android.util.Log;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.Engine.LoadStatus;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.drawable.DrawableDecoderCompat;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.TransitionFactory;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Util;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.FactoryPools.Factory;
import com.bumptech.glide.util.pool.FactoryPools.Poolable;
import com.bumptech.glide.util.pool.StateVerifier;

public final class SingleRequest<R> implements Request, SizeReadyCallback, ResourceCallback, Poolable {
    private static final String GLIDE_TAG = "Glide";
    private static final boolean IS_VERBOSE_LOGGABLE = Log.isLoggable(TAG, 2);
    private static final Pool<SingleRequest<?>> POOL = FactoryPools.simple(150, new Factory<SingleRequest<?>>() {
        public SingleRequest<?> create() {
            return new SingleRequest<>();
        }
    });
    private static final String TAG = "Request";
    private TransitionFactory<? super R> animationFactory;
    private Context context;
    private Engine engine;
    private Drawable errorDrawable;
    private Drawable fallbackDrawable;
    private GlideContext glideContext;
    private int height;
    private boolean isCallingCallbacks;
    private LoadStatus loadStatus;
    @Nullable
    private Object model;
    private int overrideHeight;
    private int overrideWidth;
    private Drawable placeholderDrawable;
    private Priority priority;
    private RequestCoordinator requestCoordinator;
    private RequestListener<R> requestListener;
    private RequestOptions requestOptions;
    private Resource<R> resource;
    private long startTime;
    private final StateVerifier stateVerifier;
    private Status status;
    @Nullable
    private final String tag;
    private Target<R> target;
    @Nullable
    private RequestListener<R> targetListener;
    private Class<R> transcodeClass;
    private int width;

    private enum Status {
        PENDING,
        RUNNING,
        WAITING_FOR_SIZE,
        COMPLETE,
        FAILED,
        CANCELLED,
        CLEARED,
        PAUSED
    }

    public static <R> SingleRequest<R> obtain(Context context2, GlideContext glideContext2, Object model2, Class<R> transcodeClass2, RequestOptions requestOptions2, int overrideWidth2, int overrideHeight2, Priority priority2, Target<R> target2, RequestListener<R> targetListener2, RequestListener<R> requestListener2, RequestCoordinator requestCoordinator2, Engine engine2, TransitionFactory<? super R> animationFactory2) {
        SingleRequest<R> request = (SingleRequest) POOL.acquire();
        if (request == null) {
            request = new SingleRequest<>();
        }
        request.init(context2, glideContext2, model2, transcodeClass2, requestOptions2, overrideWidth2, overrideHeight2, priority2, target2, targetListener2, requestListener2, requestCoordinator2, engine2, animationFactory2);
        return request;
    }

    SingleRequest() {
        this.tag = IS_VERBOSE_LOGGABLE ? String.valueOf(super.hashCode()) : null;
        this.stateVerifier = StateVerifier.newInstance();
    }

    private void init(Context context2, GlideContext glideContext2, Object model2, Class<R> transcodeClass2, RequestOptions requestOptions2, int overrideWidth2, int overrideHeight2, Priority priority2, Target<R> target2, RequestListener<R> targetListener2, RequestListener<R> requestListener2, RequestCoordinator requestCoordinator2, Engine engine2, TransitionFactory<? super R> animationFactory2) {
        this.context = context2;
        this.glideContext = glideContext2;
        this.model = model2;
        this.transcodeClass = transcodeClass2;
        this.requestOptions = requestOptions2;
        this.overrideWidth = overrideWidth2;
        this.overrideHeight = overrideHeight2;
        this.priority = priority2;
        this.target = target2;
        this.targetListener = targetListener2;
        this.requestListener = requestListener2;
        this.requestCoordinator = requestCoordinator2;
        this.engine = engine2;
        this.animationFactory = animationFactory2;
        this.status = Status.PENDING;
    }

    @NonNull
    public StateVerifier getVerifier() {
        return this.stateVerifier;
    }

    public void recycle() {
        assertNotCallingCallbacks();
        this.context = null;
        this.glideContext = null;
        this.model = null;
        this.transcodeClass = null;
        this.requestOptions = null;
        this.overrideWidth = -1;
        this.overrideHeight = -1;
        this.target = null;
        this.requestListener = null;
        this.targetListener = null;
        this.requestCoordinator = null;
        this.animationFactory = null;
        this.loadStatus = null;
        this.errorDrawable = null;
        this.placeholderDrawable = null;
        this.fallbackDrawable = null;
        this.width = -1;
        this.height = -1;
        POOL.release(this);
    }

    public void begin() {
        assertNotCallingCallbacks();
        this.stateVerifier.throwIfRecycled();
        this.startTime = LogTime.getLogTime();
        if (this.model == null) {
            if (Util.isValidDimensions(this.overrideWidth, this.overrideHeight)) {
                this.width = this.overrideWidth;
                this.height = this.overrideHeight;
            }
            onLoadFailed(new GlideException("Received null model"), getFallbackDrawable() == null ? 5 : 3);
        } else if (this.status == Status.RUNNING) {
            throw new IllegalArgumentException("Cannot restart a running request");
        } else if (this.status == Status.COMPLETE) {
            onResourceReady(this.resource, DataSource.MEMORY_CACHE);
        } else {
            this.status = Status.WAITING_FOR_SIZE;
            if (Util.isValidDimensions(this.overrideWidth, this.overrideHeight)) {
                onSizeReady(this.overrideWidth, this.overrideHeight);
            } else {
                this.target.getSize(this);
            }
            if ((this.status == Status.RUNNING || this.status == Status.WAITING_FOR_SIZE) && canNotifyStatusChanged()) {
                this.target.onLoadStarted(getPlaceholderDrawable());
            }
            if (IS_VERBOSE_LOGGABLE) {
                StringBuilder sb = new StringBuilder();
                sb.append("finished run method in ");
                sb.append(LogTime.getElapsedMillis(this.startTime));
                logV(sb.toString());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void cancel() {
        assertNotCallingCallbacks();
        this.stateVerifier.throwIfRecycled();
        this.target.removeCallback(this);
        this.status = Status.CANCELLED;
        LoadStatus loadStatus2 = this.loadStatus;
        if (loadStatus2 != null) {
            loadStatus2.cancel();
            this.loadStatus = null;
        }
    }

    private void assertNotCallingCallbacks() {
        if (this.isCallingCallbacks) {
            throw new IllegalStateException("You can't start or clear loads in RequestListener or Target callbacks. If you're trying to start a fallback request when a load fails, use RequestBuilder#error(RequestBuilder). Otherwise consider posting your into() or clear() calls to the main thread using a Handler instead.");
        }
    }

    public void clear() {
        Util.assertMainThread();
        assertNotCallingCallbacks();
        this.stateVerifier.throwIfRecycled();
        if (this.status != Status.CLEARED) {
            cancel();
            Resource<R> resource2 = this.resource;
            if (resource2 != null) {
                releaseResource(resource2);
            }
            if (canNotifyCleared()) {
                this.target.onLoadCleared(getPlaceholderDrawable());
            }
            this.status = Status.CLEARED;
        }
    }

    public boolean isPaused() {
        return this.status == Status.PAUSED;
    }

    public void pause() {
        clear();
        this.status = Status.PAUSED;
    }

    private void releaseResource(Resource<?> resource2) {
        this.engine.release(resource2);
        this.resource = null;
    }

    public boolean isRunning() {
        return this.status == Status.RUNNING || this.status == Status.WAITING_FOR_SIZE;
    }

    public boolean isComplete() {
        return this.status == Status.COMPLETE;
    }

    public boolean isResourceSet() {
        return isComplete();
    }

    public boolean isCancelled() {
        return this.status == Status.CANCELLED || this.status == Status.CLEARED;
    }

    public boolean isFailed() {
        return this.status == Status.FAILED;
    }

    private Drawable getErrorDrawable() {
        if (this.errorDrawable == null) {
            this.errorDrawable = this.requestOptions.getErrorPlaceholder();
            if (this.errorDrawable == null && this.requestOptions.getErrorId() > 0) {
                this.errorDrawable = loadDrawable(this.requestOptions.getErrorId());
            }
        }
        return this.errorDrawable;
    }

    private Drawable getPlaceholderDrawable() {
        if (this.placeholderDrawable == null) {
            this.placeholderDrawable = this.requestOptions.getPlaceholderDrawable();
            if (this.placeholderDrawable == null && this.requestOptions.getPlaceholderId() > 0) {
                this.placeholderDrawable = loadDrawable(this.requestOptions.getPlaceholderId());
            }
        }
        return this.placeholderDrawable;
    }

    private Drawable getFallbackDrawable() {
        if (this.fallbackDrawable == null) {
            this.fallbackDrawable = this.requestOptions.getFallbackDrawable();
            if (this.fallbackDrawable == null && this.requestOptions.getFallbackId() > 0) {
                this.fallbackDrawable = loadDrawable(this.requestOptions.getFallbackId());
            }
        }
        return this.fallbackDrawable;
    }

    private Drawable loadDrawable(@DrawableRes int resourceId) {
        return DrawableDecoderCompat.getDrawable((Context) this.glideContext, resourceId, this.requestOptions.getTheme() != null ? this.requestOptions.getTheme() : this.context.getTheme());
    }

    private void setErrorPlaceholder() {
        if (canNotifyStatusChanged()) {
            Drawable error = null;
            if (this.model == null) {
                error = getFallbackDrawable();
            }
            if (error == null) {
                error = getErrorDrawable();
            }
            if (error == null) {
                error = getPlaceholderDrawable();
            }
            this.target.onLoadFailed(error);
        }
    }

    public void onSizeReady(int width2, int height2) {
        this.stateVerifier.throwIfRecycled();
        if (IS_VERBOSE_LOGGABLE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Got onSizeReady in ");
            sb.append(LogTime.getElapsedMillis(this.startTime));
            logV(sb.toString());
        }
        if (this.status == Status.WAITING_FOR_SIZE) {
            this.status = Status.RUNNING;
            float sizeMultiplier = this.requestOptions.getSizeMultiplier();
            this.width = maybeApplySizeMultiplier(width2, sizeMultiplier);
            this.height = maybeApplySizeMultiplier(height2, sizeMultiplier);
            if (IS_VERBOSE_LOGGABLE) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("finished setup for calling load in ");
                sb2.append(LogTime.getElapsedMillis(this.startTime));
                logV(sb2.toString());
            }
            Engine engine2 = this.engine;
            float f = sizeMultiplier;
            GlideContext glideContext2 = this.glideContext;
            LoadStatus load = engine2.load(glideContext2, this.model, this.requestOptions.getSignature(), this.width, this.height, this.requestOptions.getResourceClass(), this.transcodeClass, this.priority, this.requestOptions.getDiskCacheStrategy(), this.requestOptions.getTransformations(), this.requestOptions.isTransformationRequired(), this.requestOptions.isScaleOnlyOrNoTransform(), this.requestOptions.getOptions(), this.requestOptions.isMemoryCacheable(), this.requestOptions.getUseUnlimitedSourceGeneratorsPool(), this.requestOptions.getUseAnimationPool(), this.requestOptions.getOnlyRetrieveFromCache(), this);
            this.loadStatus = load;
            if (this.status != Status.RUNNING) {
                this.loadStatus = null;
            }
            if (IS_VERBOSE_LOGGABLE) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("finished onSizeReady in ");
                sb3.append(LogTime.getElapsedMillis(this.startTime));
                logV(sb3.toString());
            }
        }
    }

    private static int maybeApplySizeMultiplier(int size, float sizeMultiplier) {
        return size == Integer.MIN_VALUE ? size : Math.round(((float) size) * sizeMultiplier);
    }

    private boolean canSetResource() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        return requestCoordinator2 == null || requestCoordinator2.canSetImage(this);
    }

    private boolean canNotifyCleared() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        return requestCoordinator2 == null || requestCoordinator2.canNotifyCleared(this);
    }

    private boolean canNotifyStatusChanged() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        return requestCoordinator2 == null || requestCoordinator2.canNotifyStatusChanged(this);
    }

    private boolean isFirstReadyResource() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        return requestCoordinator2 == null || !requestCoordinator2.isAnyResourceSet();
    }

    private void notifyLoadSuccess() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        if (requestCoordinator2 != null) {
            requestCoordinator2.onRequestSuccess(this);
        }
    }

    private void notifyLoadFailed() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        if (requestCoordinator2 != null) {
            requestCoordinator2.onRequestFailed(this);
        }
    }

    public void onResourceReady(Resource<?> resource2, DataSource dataSource) {
        this.stateVerifier.throwIfRecycled();
        this.loadStatus = null;
        if (resource2 == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Expected to receive a Resource<R> with an object of ");
            sb.append(this.transcodeClass);
            sb.append(" inside, but instead got null.");
            onLoadFailed(new GlideException(sb.toString()));
            return;
        }
        Object received = resource2.get();
        if (received == null || !this.transcodeClass.isAssignableFrom(received.getClass())) {
            releaseResource(resource2);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Expected to receive an object of ");
            sb2.append(this.transcodeClass);
            sb2.append(" but instead got ");
            sb2.append(received != null ? received.getClass() : "");
            sb2.append("{");
            sb2.append(received);
            sb2.append("} inside Resource{");
            sb2.append(resource2);
            sb2.append("}.");
            sb2.append(received != null ? "" : " To indicate failure return a null Resource object, rather than a Resource object containing null data.");
            onLoadFailed(new GlideException(sb2.toString()));
        } else if (!canSetResource()) {
            releaseResource(resource2);
            this.status = Status.COMPLETE;
        } else {
            onResourceReady(resource2, received, dataSource);
        }
    }

    /* JADX INFO: finally extract failed */
    private void onResourceReady(Resource<R> resource2, R result, DataSource dataSource) {
        boolean isFirstResource = isFirstReadyResource();
        this.status = Status.COMPLETE;
        this.resource = resource2;
        if (this.glideContext.getLogLevel() <= 3) {
            String str = GLIDE_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Finished loading ");
            sb.append(result.getClass().getSimpleName());
            sb.append(" from ");
            sb.append(dataSource);
            sb.append(" for ");
            sb.append(this.model);
            sb.append(" with size [");
            sb.append(this.width);
            sb.append("x");
            sb.append(this.height);
            sb.append("] in ");
            sb.append(LogTime.getElapsedMillis(this.startTime));
            sb.append(" ms");
            Log.d(str, sb.toString());
        }
        this.isCallingCallbacks = true;
        try {
            if (this.requestListener != null) {
                if (this.requestListener.onResourceReady(result, this.model, this.target, dataSource, isFirstResource)) {
                    this.isCallingCallbacks = false;
                    notifyLoadSuccess();
                }
            }
            if (this.targetListener != null) {
                if (this.targetListener.onResourceReady(result, this.model, this.target, dataSource, isFirstResource)) {
                    this.isCallingCallbacks = false;
                    notifyLoadSuccess();
                }
            }
            this.target.onResourceReady(result, this.animationFactory.build(dataSource, isFirstResource));
            this.isCallingCallbacks = false;
            notifyLoadSuccess();
        } catch (Throwable th) {
            this.isCallingCallbacks = false;
            throw th;
        }
    }

    public void onLoadFailed(GlideException e) {
        onLoadFailed(e, 5);
    }

    /* JADX INFO: finally extract failed */
    private void onLoadFailed(GlideException e, int maxLogLevel) {
        this.stateVerifier.throwIfRecycled();
        int logLevel = this.glideContext.getLogLevel();
        if (logLevel <= maxLogLevel) {
            String str = GLIDE_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Load failed for ");
            sb.append(this.model);
            sb.append(" with size [");
            sb.append(this.width);
            sb.append("x");
            sb.append(this.height);
            sb.append("]");
            Log.w(str, sb.toString(), e);
            if (logLevel <= 4) {
                e.logRootCauses(GLIDE_TAG);
            }
        }
        this.loadStatus = null;
        this.status = Status.FAILED;
        this.isCallingCallbacks = true;
        try {
            if (this.requestListener != null) {
                if (this.requestListener.onLoadFailed(e, this.model, this.target, isFirstReadyResource())) {
                    this.isCallingCallbacks = false;
                    notifyLoadFailed();
                }
            }
            if (this.targetListener != null) {
                if (this.targetListener.onLoadFailed(e, this.model, this.target, isFirstReadyResource())) {
                    this.isCallingCallbacks = false;
                    notifyLoadFailed();
                }
            }
            setErrorPlaceholder();
            this.isCallingCallbacks = false;
            notifyLoadFailed();
        } catch (Throwable th) {
            this.isCallingCallbacks = false;
            throw th;
        }
    }

    public boolean isEquivalentTo(Request o) {
        boolean z = false;
        if (!(o instanceof SingleRequest)) {
            return false;
        }
        SingleRequest<?> that = (SingleRequest) o;
        if (this.overrideWidth == that.overrideWidth && this.overrideHeight == that.overrideHeight && Util.bothModelsNullEquivalentOrEquals(this.model, that.model) && this.transcodeClass.equals(that.transcodeClass) && this.requestOptions.equals(that.requestOptions) && this.priority == that.priority && (this.requestListener == null ? that.requestListener == null : that.requestListener != null)) {
            z = true;
        }
        return z;
    }

    private void logV(String message) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        sb.append(" this: ");
        sb.append(this.tag);
        Log.v(str, sb.toString());
    }
}
