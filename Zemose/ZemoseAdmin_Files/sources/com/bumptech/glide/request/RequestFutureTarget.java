package com.bumptech.glide.request;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.Util;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RequestFutureTarget<R> implements FutureTarget<R>, RequestListener<R>, Runnable {
    private static final Waiter DEFAULT_WAITER = new Waiter();
    private final boolean assertBackgroundThread;
    @Nullable
    private GlideException exception;
    private final int height;
    private boolean isCancelled;
    private boolean loadFailed;
    private final Handler mainHandler;
    @Nullable
    private Request request;
    @Nullable
    private R resource;
    private boolean resultReceived;
    private final Waiter waiter;
    private final int width;

    @VisibleForTesting
    static class Waiter {
        Waiter() {
        }

        /* access modifiers changed from: 0000 */
        public void waitForTimeout(Object toWaitOn, long timeoutMillis) throws InterruptedException {
            toWaitOn.wait(timeoutMillis);
        }

        /* access modifiers changed from: 0000 */
        public void notifyAll(Object toNotify) {
            toNotify.notifyAll();
        }
    }

    public RequestFutureTarget(Handler mainHandler2, int width2, int height2) {
        this(mainHandler2, width2, height2, true, DEFAULT_WAITER);
    }

    RequestFutureTarget(Handler mainHandler2, int width2, int height2, boolean assertBackgroundThread2, Waiter waiter2) {
        this.mainHandler = mainHandler2;
        this.width = width2;
        this.height = height2;
        this.assertBackgroundThread = assertBackgroundThread2;
        this.waiter = waiter2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001a, code lost:
        return true;
     */
    public synchronized boolean cancel(boolean mayInterruptIfRunning) {
        if (isDone()) {
            return false;
        }
        this.isCancelled = true;
        this.waiter.notifyAll(this);
        if (mayInterruptIfRunning) {
            clearOnMainThread();
        }
    }

    public synchronized boolean isCancelled() {
        return this.isCancelled;
    }

    public synchronized boolean isDone() {
        return this.isCancelled || this.resultReceived || this.loadFailed;
    }

    public R get() throws InterruptedException, ExecutionException {
        try {
            return doGet(null);
        } catch (TimeoutException e) {
            throw new AssertionError(e);
        }
    }

    public R get(long time, @NonNull TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return doGet(Long.valueOf(timeUnit.toMillis(time)));
    }

    public void getSize(@NonNull SizeReadyCallback cb) {
        cb.onSizeReady(this.width, this.height);
    }

    public void removeCallback(@NonNull SizeReadyCallback cb) {
    }

    public void setRequest(@Nullable Request request2) {
        this.request = request2;
    }

    @Nullable
    public Request getRequest() {
        return this.request;
    }

    public void onLoadCleared(@Nullable Drawable placeholder) {
    }

    public void onLoadStarted(@Nullable Drawable placeholder) {
    }

    public synchronized void onLoadFailed(@Nullable Drawable errorDrawable) {
    }

    public synchronized void onResourceReady(@NonNull R r, @Nullable Transition<? super R> transition) {
    }

    private synchronized R doGet(Long timeoutMillis) throws ExecutionException, InterruptedException, TimeoutException {
        if (this.assertBackgroundThread && !isDone()) {
            Util.assertBackgroundThread();
        }
        if (this.isCancelled) {
            throw new CancellationException();
        } else if (this.loadFailed) {
            throw new ExecutionException(this.exception);
        } else if (this.resultReceived) {
            return this.resource;
        } else {
            if (timeoutMillis == null) {
                this.waiter.waitForTimeout(this, 0);
            } else if (timeoutMillis.longValue() > 0) {
                long now = System.currentTimeMillis();
                long deadline = timeoutMillis.longValue() + now;
                while (!isDone() && now < deadline) {
                    this.waiter.waitForTimeout(this, deadline - now);
                    now = System.currentTimeMillis();
                }
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            } else if (this.loadFailed) {
                throw new ExecutionException(this.exception);
            } else if (this.isCancelled) {
                throw new CancellationException();
            } else if (this.resultReceived) {
                return this.resource;
            } else {
                throw new TimeoutException();
            }
        }
    }

    public void run() {
        Request request2 = this.request;
        if (request2 != null) {
            request2.clear();
            this.request = null;
        }
    }

    private void clearOnMainThread() {
        this.mainHandler.post(this);
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public void onDestroy() {
    }

    public synchronized boolean onLoadFailed(@Nullable GlideException e, Object model, Target<R> target, boolean isFirstResource) {
        this.loadFailed = true;
        this.exception = e;
        this.waiter.notifyAll(this);
        return false;
    }

    public synchronized boolean onResourceReady(R resource2, Object model, Target<R> target, DataSource dataSource, boolean isFirstResource) {
        this.resultReceived = true;
        this.resource = resource2;
        this.waiter.notifyAll(this);
        return false;
    }
}
