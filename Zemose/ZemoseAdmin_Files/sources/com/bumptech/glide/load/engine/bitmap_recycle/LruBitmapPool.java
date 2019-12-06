package com.bumptech.glide.load.engine.bitmap_recycle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LruBitmapPool implements BitmapPool {
    private static final Config DEFAULT_CONFIG = Config.ARGB_8888;
    private static final String TAG = "LruBitmapPool";
    private final Set<Config> allowedConfigs;
    private long currentSize;
    private int evictions;
    private int hits;
    private final long initialMaxSize;
    private long maxSize;
    private int misses;
    private int puts;
    private final LruPoolStrategy strategy;
    private final BitmapTracker tracker;

    private interface BitmapTracker {
        void add(Bitmap bitmap);

        void remove(Bitmap bitmap);
    }

    private static final class NullBitmapTracker implements BitmapTracker {
        NullBitmapTracker() {
        }

        public void add(Bitmap bitmap) {
        }

        public void remove(Bitmap bitmap) {
        }
    }

    private static class ThrowingBitmapTracker implements BitmapTracker {
        private final Set<Bitmap> bitmaps = Collections.synchronizedSet(new HashSet());

        private ThrowingBitmapTracker() {
        }

        public void add(Bitmap bitmap) {
            if (!this.bitmaps.contains(bitmap)) {
                this.bitmaps.add(bitmap);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Can't add already added bitmap: ");
            sb.append(bitmap);
            sb.append(" [");
            sb.append(bitmap.getWidth());
            sb.append("x");
            sb.append(bitmap.getHeight());
            sb.append("]");
            throw new IllegalStateException(sb.toString());
        }

        public void remove(Bitmap bitmap) {
            if (this.bitmaps.contains(bitmap)) {
                this.bitmaps.remove(bitmap);
                return;
            }
            throw new IllegalStateException("Cannot remove bitmap not in tracker");
        }
    }

    LruBitmapPool(long maxSize2, LruPoolStrategy strategy2, Set<Config> allowedConfigs2) {
        this.initialMaxSize = maxSize2;
        this.maxSize = maxSize2;
        this.strategy = strategy2;
        this.allowedConfigs = allowedConfigs2;
        this.tracker = new NullBitmapTracker();
    }

    public LruBitmapPool(long maxSize2) {
        this(maxSize2, getDefaultStrategy(), getDefaultAllowedConfigs());
    }

    public LruBitmapPool(long maxSize2, Set<Config> allowedConfigs2) {
        this(maxSize2, getDefaultStrategy(), allowedConfigs2);
    }

    public long getMaxSize() {
        return this.maxSize;
    }

    public synchronized void setSizeMultiplier(float sizeMultiplier) {
        this.maxSize = (long) Math.round(((float) this.initialMaxSize) * sizeMultiplier);
        evict();
    }

    public synchronized void put(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                if (!bitmap.isRecycled()) {
                    if (bitmap.isMutable() && ((long) this.strategy.getSize(bitmap)) <= this.maxSize) {
                        if (this.allowedConfigs.contains(bitmap.getConfig())) {
                            int size = this.strategy.getSize(bitmap);
                            this.strategy.put(bitmap);
                            this.tracker.add(bitmap);
                            this.puts++;
                            this.currentSize += (long) size;
                            if (Log.isLoggable(TAG, 2)) {
                                String str = TAG;
                                StringBuilder sb = new StringBuilder();
                                sb.append("Put bitmap in pool=");
                                sb.append(this.strategy.logBitmap(bitmap));
                                Log.v(str, sb.toString());
                            }
                            dump();
                            evict();
                            return;
                        }
                    }
                    if (Log.isLoggable(TAG, 2)) {
                        String str2 = TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Reject bitmap from pool, bitmap: ");
                        sb2.append(this.strategy.logBitmap(bitmap));
                        sb2.append(", is mutable: ");
                        sb2.append(bitmap.isMutable());
                        sb2.append(", is allowed config: ");
                        sb2.append(this.allowedConfigs.contains(bitmap.getConfig()));
                        Log.v(str2, sb2.toString());
                    }
                    bitmap.recycle();
                    return;
                }
                throw new IllegalStateException("Cannot pool recycled bitmap");
            } finally {
            }
        } else {
            throw new NullPointerException("Bitmap must not be null");
        }
    }

    private void evict() {
        trimToSize(this.maxSize);
    }

    @NonNull
    public Bitmap get(int width, int height, Config config) {
        Bitmap result = getDirtyOrNull(width, height, config);
        if (result == null) {
            return createBitmap(width, height, config);
        }
        result.eraseColor(0);
        return result;
    }

    @NonNull
    public Bitmap getDirty(int width, int height, Config config) {
        Bitmap result = getDirtyOrNull(width, height, config);
        if (result == null) {
            return createBitmap(width, height, config);
        }
        return result;
    }

    @NonNull
    private static Bitmap createBitmap(int width, int height, @Nullable Config config) {
        return Bitmap.createBitmap(width, height, config != null ? config : DEFAULT_CONFIG);
    }

    @TargetApi(26)
    private static void assertNotHardwareConfig(Config config) {
        if (VERSION.SDK_INT >= 26 && config == Config.HARDWARE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot create a mutable Bitmap with config: ");
            sb.append(config);
            sb.append(". Consider setting Downsampler#ALLOW_HARDWARE_CONFIG to false in your RequestOptions and/or in GlideBuilder.setDefaultRequestOptions");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    @Nullable
    private synchronized Bitmap getDirtyOrNull(int width, int height, @Nullable Config config) {
        Bitmap result;
        assertNotHardwareConfig(config);
        result = this.strategy.get(width, height, config != null ? config : DEFAULT_CONFIG);
        if (result == null) {
            if (Log.isLoggable(TAG, 3)) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Missing bitmap=");
                sb.append(this.strategy.logBitmap(width, height, config));
                Log.d(str, sb.toString());
            }
            this.misses++;
        } else {
            this.hits++;
            this.currentSize -= (long) this.strategy.getSize(result);
            this.tracker.remove(result);
            normalize(result);
        }
        if (Log.isLoggable(TAG, 2)) {
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Get bitmap=");
            sb2.append(this.strategy.logBitmap(width, height, config));
            Log.v(str2, sb2.toString());
        }
        dump();
        return result;
    }

    private static void normalize(Bitmap bitmap) {
        bitmap.setHasAlpha(true);
        maybeSetPreMultiplied(bitmap);
    }

    @TargetApi(19)
    private static void maybeSetPreMultiplied(Bitmap bitmap) {
        if (VERSION.SDK_INT >= 19) {
            bitmap.setPremultiplied(true);
        }
    }

    public void clearMemory() {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "clearMemory");
        }
        trimToSize(0);
    }

    @SuppressLint({"InlinedApi"})
    public void trimMemory(int level) {
        if (Log.isLoggable(TAG, 3)) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("trimMemory, level=");
            sb.append(level);
            Log.d(str, sb.toString());
        }
        if (level >= 40) {
            clearMemory();
        } else if (level >= 20 || level == 15) {
            trimToSize(getMaxSize() / 2);
        }
    }

    private synchronized void trimToSize(long size) {
        while (this.currentSize > size) {
            Bitmap removed = this.strategy.removeLast();
            if (removed == null) {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Size mismatch, resetting");
                    dumpUnchecked();
                }
                this.currentSize = 0;
                return;
            }
            this.tracker.remove(removed);
            this.currentSize -= (long) this.strategy.getSize(removed);
            this.evictions++;
            if (Log.isLoggable(TAG, 3)) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Evicting bitmap=");
                sb.append(this.strategy.logBitmap(removed));
                Log.d(str, sb.toString());
            }
            dump();
            removed.recycle();
        }
    }

    private void dump() {
        if (Log.isLoggable(TAG, 2)) {
            dumpUnchecked();
        }
    }

    private void dumpUnchecked() {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Hits=");
        sb.append(this.hits);
        sb.append(", misses=");
        sb.append(this.misses);
        sb.append(", puts=");
        sb.append(this.puts);
        sb.append(", evictions=");
        sb.append(this.evictions);
        sb.append(", currentSize=");
        sb.append(this.currentSize);
        sb.append(", maxSize=");
        sb.append(this.maxSize);
        sb.append("\nStrategy=");
        sb.append(this.strategy);
        Log.v(str, sb.toString());
    }

    private static LruPoolStrategy getDefaultStrategy() {
        if (VERSION.SDK_INT >= 19) {
            return new SizeConfigStrategy();
        }
        return new AttributeStrategy();
    }

    @TargetApi(26)
    private static Set<Config> getDefaultAllowedConfigs() {
        Set<Config> configs = new HashSet<>(Arrays.asList(Config.values()));
        if (VERSION.SDK_INT >= 19) {
            configs.add(null);
        }
        if (VERSION.SDK_INT >= 26) {
            configs.remove(Config.HARDWARE);
        }
        return Collections.unmodifiableSet(configs);
    }
}
