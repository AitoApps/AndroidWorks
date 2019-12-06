package com.bumptech.glide.load.data;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.data.DataRewinder.Factory;
import com.bumptech.glide.util.Preconditions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataRewinderRegistry {
    private static final Factory<?> DEFAULT_FACTORY = new Factory<Object>() {
        @NonNull
        public DataRewinder<Object> build(@NonNull Object data2) {
            return new DefaultRewinder(data2);
        }

        @NonNull
        public Class<Object> getDataClass() {
            throw new UnsupportedOperationException("Not implemented");
        }
    };
    private final Map<Class<?>, Factory<?>> rewinders = new HashMap();

    private static final class DefaultRewinder implements DataRewinder<Object> {

        /* renamed from: data reason: collision with root package name */
        private final Object f5data;

        DefaultRewinder(@NonNull Object data2) {
            this.f5data = data2;
        }

        @NonNull
        public Object rewindAndGet() {
            return this.f5data;
        }

        public void cleanup() {
        }
    }

    public synchronized void register(@NonNull Factory<?> factory) {
        this.rewinders.put(factory.getDataClass(), factory);
    }

    @NonNull
    public synchronized <T> DataRewinder<T> build(@NonNull T data2) {
        Factory factory;
        Preconditions.checkNotNull(data2);
        factory = (Factory) this.rewinders.get(data2.getClass());
        if (factory == null) {
            Iterator it = this.rewinders.values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Factory<?> registeredFactory = (Factory) it.next();
                if (registeredFactory.getDataClass().isAssignableFrom(data2.getClass())) {
                    factory = registeredFactory;
                    break;
                }
            }
        }
        if (factory == null) {
            factory = DEFAULT_FACTORY;
        }
        return factory.build(data2);
    }
}
