package com.bumptech.glide.load.model.stream;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoader.LoadData;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import java.io.InputStream;
import java.net.URL;

public class UrlLoader implements ModelLoader<URL, InputStream> {
    private final ModelLoader<GlideUrl, InputStream> glideUrlLoader;

    public static class StreamFactory implements ModelLoaderFactory<URL, InputStream> {
        @NonNull
        public ModelLoader<URL, InputStream> build(MultiModelLoaderFactory multiFactory) {
            return new UrlLoader(multiFactory.build(GlideUrl.class, InputStream.class));
        }

        public void teardown() {
        }
    }

    public UrlLoader(ModelLoader<GlideUrl, InputStream> glideUrlLoader2) {
        this.glideUrlLoader = glideUrlLoader2;
    }

    public LoadData<InputStream> buildLoadData(@NonNull URL model, int width, int height, @NonNull Options options) {
        return this.glideUrlLoader.buildLoadData(new GlideUrl(model), width, height, options);
    }

    public boolean handles(@NonNull URL model) {
        return true;
    }
}