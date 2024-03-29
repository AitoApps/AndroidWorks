package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.Transformation;
import java.util.Map;

class EngineKeyFactory {
    EngineKeyFactory() {
    }

    /* access modifiers changed from: 0000 */
    public EngineKey buildKey(Object model, Key signature, int width, int height, Map<Class<?>, Transformation<?>> transformations, Class<?> resourceClass, Class<?> transcodeClass, Options options) {
        EngineKey engineKey = new EngineKey(model, signature, width, height, transformations, resourceClass, transcodeClass, options);
        return engineKey;
    }
}
