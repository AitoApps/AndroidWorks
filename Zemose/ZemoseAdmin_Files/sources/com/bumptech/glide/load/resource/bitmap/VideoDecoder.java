package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build.VERSION;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Option.CacheKeyUpdater;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class VideoDecoder<T> implements ResourceDecoder<T, Bitmap> {
    private static final MediaMetadataRetrieverFactory DEFAULT_FACTORY = new MediaMetadataRetrieverFactory();
    public static final long DEFAULT_FRAME = -1;
    @VisibleForTesting
    static final int DEFAULT_FRAME_OPTION = 2;
    public static final Option<Integer> FRAME_OPTION = Option.disk("com.bumptech.glide.load.resource.bitmap.VideoBitmapDecode.FrameOption", Integer.valueOf(2), new CacheKeyUpdater<Integer>() {
        private final ByteBuffer buffer = ByteBuffer.allocate(4);

        public void update(@NonNull byte[] keyBytes, @NonNull Integer value, @NonNull MessageDigest messageDigest) {
            if (value != null) {
                messageDigest.update(keyBytes);
                synchronized (this.buffer) {
                    this.buffer.position(0);
                    messageDigest.update(this.buffer.putInt(value.intValue()).array());
                }
            }
        }
    });
    private static final String TAG = "VideoDecoder";
    public static final Option<Long> TARGET_FRAME = Option.disk("com.bumptech.glide.load.resource.bitmap.VideoBitmapDecode.TargetFrame", Long.valueOf(-1), new CacheKeyUpdater<Long>() {
        private final ByteBuffer buffer = ByteBuffer.allocate(8);

        public void update(@NonNull byte[] keyBytes, @NonNull Long value, @NonNull MessageDigest messageDigest) {
            messageDigest.update(keyBytes);
            synchronized (this.buffer) {
                this.buffer.position(0);
                messageDigest.update(this.buffer.putLong(value.longValue()).array());
            }
        }
    });
    private final BitmapPool bitmapPool;
    private final MediaMetadataRetrieverFactory factory;
    private final MediaMetadataRetrieverInitializer<T> initializer;

    private static final class AssetFileDescriptorInitializer implements MediaMetadataRetrieverInitializer<AssetFileDescriptor> {
        private AssetFileDescriptorInitializer() {
        }

        public void initialize(MediaMetadataRetriever retriever, AssetFileDescriptor data2) {
            retriever.setDataSource(data2.getFileDescriptor(), data2.getStartOffset(), data2.getLength());
        }
    }

    @VisibleForTesting
    static class MediaMetadataRetrieverFactory {
        MediaMetadataRetrieverFactory() {
        }

        public MediaMetadataRetriever build() {
            return new MediaMetadataRetriever();
        }
    }

    @VisibleForTesting
    interface MediaMetadataRetrieverInitializer<T> {
        void initialize(MediaMetadataRetriever mediaMetadataRetriever, T t);
    }

    static final class ParcelFileDescriptorInitializer implements MediaMetadataRetrieverInitializer<ParcelFileDescriptor> {
        ParcelFileDescriptorInitializer() {
        }

        public void initialize(MediaMetadataRetriever retriever, ParcelFileDescriptor data2) {
            retriever.setDataSource(data2.getFileDescriptor());
        }
    }

    public static ResourceDecoder<AssetFileDescriptor, Bitmap> asset(BitmapPool bitmapPool2) {
        return new VideoDecoder(bitmapPool2, new AssetFileDescriptorInitializer());
    }

    public static ResourceDecoder<ParcelFileDescriptor, Bitmap> parcel(BitmapPool bitmapPool2) {
        return new VideoDecoder(bitmapPool2, new ParcelFileDescriptorInitializer());
    }

    VideoDecoder(BitmapPool bitmapPool2, MediaMetadataRetrieverInitializer<T> initializer2) {
        this(bitmapPool2, initializer2, DEFAULT_FACTORY);
    }

    @VisibleForTesting
    VideoDecoder(BitmapPool bitmapPool2, MediaMetadataRetrieverInitializer<T> initializer2, MediaMetadataRetrieverFactory factory2) {
        this.bitmapPool = bitmapPool2;
        this.initializer = initializer2;
        this.factory = factory2;
    }

    public boolean handles(@NonNull T t, @NonNull Options options) {
        return true;
    }

    public Resource<Bitmap> decode(@NonNull T resource, int outWidth, int outHeight, @NonNull Options options) throws IOException {
        Integer frameOption;
        DownsampleStrategy downsampleStrategy;
        Options options2 = options;
        long frameTimeMicros = ((Long) options2.get(TARGET_FRAME)).longValue();
        if (frameTimeMicros >= 0 || frameTimeMicros == -1) {
            Integer frameOption2 = (Integer) options2.get(FRAME_OPTION);
            if (frameOption2 == null) {
                frameOption = Integer.valueOf(2);
            } else {
                frameOption = frameOption2;
            }
            DownsampleStrategy downsampleStrategy2 = (DownsampleStrategy) options2.get(DownsampleStrategy.OPTION);
            if (downsampleStrategy2 == null) {
                downsampleStrategy = DownsampleStrategy.DEFAULT;
            } else {
                downsampleStrategy = downsampleStrategy2;
            }
            MediaMetadataRetriever mediaMetadataRetriever = this.factory.build();
            try {
                try {
                    this.initializer.initialize(mediaMetadataRetriever, resource);
                    Bitmap result = decodeFrame(mediaMetadataRetriever, frameTimeMicros, frameOption.intValue(), outWidth, outHeight, downsampleStrategy);
                    mediaMetadataRetriever.release();
                    return BitmapResource.obtain(result, this.bitmapPool);
                } catch (RuntimeException e) {
                    e = e;
                    try {
                        throw new IOException(e);
                    } catch (Throwable th) {
                        e = th;
                        mediaMetadataRetriever.release();
                        throw e;
                    }
                }
            } catch (RuntimeException e2) {
                e = e2;
                T t = resource;
                throw new IOException(e);
            } catch (Throwable th2) {
                e = th2;
                T t2 = resource;
                mediaMetadataRetriever.release();
                throw e;
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Requested frame must be non-negative, or DEFAULT_FRAME, given: ");
            sb.append(frameTimeMicros);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    @Nullable
    private static Bitmap decodeFrame(MediaMetadataRetriever mediaMetadataRetriever, long frameTimeMicros, int frameOption, int outWidth, int outHeight, DownsampleStrategy strategy) {
        Bitmap result = null;
        if (!(VERSION.SDK_INT < 27 || outWidth == Integer.MIN_VALUE || outHeight == Integer.MIN_VALUE || strategy == DownsampleStrategy.NONE)) {
            result = decodeScaledFrame(mediaMetadataRetriever, frameTimeMicros, frameOption, outWidth, outHeight, strategy);
        }
        if (result == null) {
            return decodeOriginalFrame(mediaMetadataRetriever, frameTimeMicros, frameOption);
        }
        return result;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0064  */
    @TargetApi(27)
    private static Bitmap decodeScaledFrame(MediaMetadataRetriever mediaMetadataRetriever, long frameTimeMicros, int frameOption, int outWidth, int outHeight, DownsampleStrategy strategy) {
        int originalHeight;
        MediaMetadataRetriever mediaMetadataRetriever2 = mediaMetadataRetriever;
        try {
            int originalWidth = Integer.parseInt(mediaMetadataRetriever.extractMetadata(18));
            int originalHeight2 = Integer.parseInt(mediaMetadataRetriever.extractMetadata(19));
            int orientation = Integer.parseInt(mediaMetadataRetriever.extractMetadata(24));
            if (orientation == 90 || orientation == 270) {
                int temp = originalWidth;
                originalWidth = originalHeight2;
                originalHeight = temp;
            } else {
                originalHeight = originalHeight2;
            }
            try {
                float scaleFactor = strategy.getScaleFactor(originalWidth, originalHeight, outWidth, outHeight);
                return mediaMetadataRetriever.getScaledFrameAtTime(frameTimeMicros, frameOption, Math.round(((float) originalWidth) * scaleFactor), Math.round(((float) originalHeight) * scaleFactor));
            } catch (Throwable th) {
                t = th;
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Exception trying to decode frame on oreo+", t);
                }
                return null;
            }
        } catch (Throwable th2) {
            t = th2;
            int i = outWidth;
            int i2 = outHeight;
            DownsampleStrategy downsampleStrategy = strategy;
            if (Log.isLoggable(TAG, 3)) {
            }
            return null;
        }
    }

    private static Bitmap decodeOriginalFrame(MediaMetadataRetriever mediaMetadataRetriever, long frameTimeMicros, int frameOption) {
        return mediaMetadataRetriever.getFrameAtTime(frameTimeMicros, frameOption);
    }
}
