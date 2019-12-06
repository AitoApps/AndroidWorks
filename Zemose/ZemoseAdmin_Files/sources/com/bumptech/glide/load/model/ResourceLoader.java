package com.bumptech.glide.load.model;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader.LoadData;
import java.io.InputStream;

public class ResourceLoader<Data> implements ModelLoader<Integer, Data> {
    private static final String TAG = "ResourceLoader";
    private final Resources resources;
    private final ModelLoader<Uri, Data> uriLoader;

    public static final class AssetFileDescriptorFactory implements ModelLoaderFactory<Integer, AssetFileDescriptor> {
        private final Resources resources;

        public AssetFileDescriptorFactory(Resources resources2) {
            this.resources = resources2;
        }

        public ModelLoader<Integer, AssetFileDescriptor> build(MultiModelLoaderFactory multiFactory) {
            return new ResourceLoader(this.resources, multiFactory.build(Uri.class, AssetFileDescriptor.class));
        }

        public void teardown() {
        }
    }

    public static class FileDescriptorFactory implements ModelLoaderFactory<Integer, ParcelFileDescriptor> {
        private final Resources resources;

        public FileDescriptorFactory(Resources resources2) {
            this.resources = resources2;
        }

        @NonNull
        public ModelLoader<Integer, ParcelFileDescriptor> build(MultiModelLoaderFactory multiFactory) {
            return new ResourceLoader(this.resources, multiFactory.build(Uri.class, ParcelFileDescriptor.class));
        }

        public void teardown() {
        }
    }

    public static class StreamFactory implements ModelLoaderFactory<Integer, InputStream> {
        private final Resources resources;

        public StreamFactory(Resources resources2) {
            this.resources = resources2;
        }

        @NonNull
        public ModelLoader<Integer, InputStream> build(MultiModelLoaderFactory multiFactory) {
            return new ResourceLoader(this.resources, multiFactory.build(Uri.class, InputStream.class));
        }

        public void teardown() {
        }
    }

    public static class UriFactory implements ModelLoaderFactory<Integer, Uri> {
        private final Resources resources;

        public UriFactory(Resources resources2) {
            this.resources = resources2;
        }

        @NonNull
        public ModelLoader<Integer, Uri> build(MultiModelLoaderFactory multiFactory) {
            return new ResourceLoader(this.resources, UnitModelLoader.getInstance());
        }

        public void teardown() {
        }
    }

    public ResourceLoader(Resources resources2, ModelLoader<Uri, Data> uriLoader2) {
        this.resources = resources2;
        this.uriLoader = uriLoader2;
    }

    public LoadData<Data> buildLoadData(@NonNull Integer model, int width, int height, @NonNull Options options) {
        Uri uri = getResourceUri(model);
        if (uri == null) {
            return null;
        }
        return this.uriLoader.buildLoadData(uri, width, height, options);
    }

    @Nullable
    private Uri getResourceUri(Integer model) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("android.resource://");
            sb.append(this.resources.getResourcePackageName(model.intValue()));
            sb.append('/');
            sb.append(this.resources.getResourceTypeName(model.intValue()));
            sb.append('/');
            sb.append(this.resources.getResourceEntryName(model.intValue()));
            return Uri.parse(sb.toString());
        } catch (NotFoundException e) {
            if (Log.isLoggable(TAG, 5)) {
                String str = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Received invalid resource id: ");
                sb2.append(model);
                Log.w(str, sb2.toString(), e);
            }
            return null;
        }
    }

    public boolean handles(@NonNull Integer model) {
        return true;
    }
}
