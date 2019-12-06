package com.bumptech.glide.util.pool;

import android.support.annotation.NonNull;
import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SimplePool;
import android.support.v4.util.Pools.SynchronizedPool;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public final class FactoryPools {
    private static final int DEFAULT_POOL_SIZE = 20;
    private static final Resetter<Object> EMPTY_RESETTER = new Resetter<Object>() {
        public void reset(@NonNull Object object) {
        }
    };
    private static final String TAG = "FactoryPools";

    public interface Factory<T> {
        T create();
    }

    private static final class FactoryPool<T> implements Pool<T> {
        private final Factory<T> factory;
        private final Pool<T> pool;
        private final Resetter<T> resetter;

        FactoryPool(@NonNull Pool<T> pool2, @NonNull Factory<T> factory2, @NonNull Resetter<T> resetter2) {
            this.pool = pool2;
            this.factory = factory2;
            this.resetter = resetter2;
        }

        public T acquire() {
            T result = this.pool.acquire();
            if (result == null) {
                result = this.factory.create();
                if (Log.isLoggable(FactoryPools.TAG, 2)) {
                    String str = FactoryPools.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Created new ");
                    sb.append(result.getClass());
                    Log.v(str, sb.toString());
                }
            }
            if (result instanceof Poolable) {
                ((Poolable) result).getVerifier().setRecycled(false);
            }
            return result;
        }

        public boolean release(@NonNull T instance) {
            if (instance instanceof Poolable) {
                ((Poolable) instance).getVerifier().setRecycled(true);
            }
            this.resetter.reset(instance);
            return this.pool.release(instance);
        }
    }

    public interface Poolable {
        @NonNull
        StateVerifier getVerifier();
    }

    public interface Resetter<T> {
        void reset(@NonNull T t);
    }

    private FactoryPools() {
    }

    @NonNull
    public static <T extends Poolable> Pool<T> simple(int size, @NonNull Factory<T> factory) {
        return build(new SimplePool(size), factory);
    }

    @NonNull
    public static <T extends Poolable> Pool<T> threadSafe(int size, @NonNull Factory<T> factory) {
        return build(new SynchronizedPool(size), factory);
    }

    @NonNull
    public static <T> Pool<List<T>> threadSafeList() {
        return threadSafeList(20);
    }

    @NonNull
    public static <T> Pool<List<T>> threadSafeList(int size) {
        return build(new SynchronizedPool(size), new Factory<List<T>>() {
            @NonNull
            public List<T> create() {
                return new ArrayList();
            }
        }, new Resetter<List<T>>() {
            public void reset(@NonNull List<T> object) {
                object.clear();
            }
        });
    }

    @NonNull
    private static <T extends Poolable> Pool<T> build(@NonNull Pool<T> pool, @NonNull Factory<T> factory) {
        return build(pool, factory, emptyResetter());
    }

    @NonNull
    private static <T> Pool<T> build(@NonNull Pool<T> pool, @NonNull Factory<T> factory, @NonNull Resetter<T> resetter) {
        return new FactoryPool(pool, factory, resetter);
    }

    @NonNull
    private static <T> Resetter<T> emptyResetter() {
        return EMPTY_RESETTER;
    }
}
