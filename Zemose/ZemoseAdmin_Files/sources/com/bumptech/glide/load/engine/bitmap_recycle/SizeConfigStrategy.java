package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.util.Util;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

@RequiresApi(19)
public class SizeConfigStrategy implements LruPoolStrategy {
    private static final Config[] ALPHA_8_IN_CONFIGS = {Config.ALPHA_8};
    private static final Config[] ARGB_4444_IN_CONFIGS = {Config.ARGB_4444};
    private static final Config[] ARGB_8888_IN_CONFIGS;
    private static final int MAX_SIZE_MULTIPLE = 8;
    private static final Config[] RGBA_F16_IN_CONFIGS = ARGB_8888_IN_CONFIGS;
    private static final Config[] RGB_565_IN_CONFIGS = {Config.RGB_565};
    private final GroupedLinkedMap<Key, Bitmap> groupedMap = new GroupedLinkedMap<>();
    private final KeyPool keyPool = new KeyPool();
    private final Map<Config, NavigableMap<Integer, Integer>> sortedSizes = new HashMap();

    /* renamed from: com.bumptech.glide.load.engine.bitmap_recycle.SizeConfigStrategy$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$Config = new int[Config.values().length];

        static {
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Config.ARGB_8888.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Config.RGB_565.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Config.ARGB_4444.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Config.ALPHA_8.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @VisibleForTesting
    static final class Key implements Poolable {
        private Config config;
        private final KeyPool pool;
        int size;

        public Key(KeyPool pool2) {
            this.pool = pool2;
        }

        @VisibleForTesting
        Key(KeyPool pool2, int size2, Config config2) {
            this(pool2);
            init(size2, config2);
        }

        public void init(int size2, Config config2) {
            this.size = size2;
            this.config = config2;
        }

        public void offer() {
            this.pool.offer(this);
        }

        public String toString() {
            return SizeConfigStrategy.getBitmapString(this.size, this.config);
        }

        public boolean equals(Object o) {
            boolean z = false;
            if (!(o instanceof Key)) {
                return false;
            }
            Key other = (Key) o;
            if (this.size == other.size && Util.bothNullOrEqual(this.config, other.config)) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            int i = this.size * 31;
            Config config2 = this.config;
            return i + (config2 != null ? config2.hashCode() : 0);
        }
    }

    @VisibleForTesting
    static class KeyPool extends BaseKeyPool<Key> {
        KeyPool() {
        }

        public Key get(int size, Config config) {
            Key result = (Key) get();
            result.init(size, config);
            return result;
        }

        /* access modifiers changed from: protected */
        public Key create() {
            return new Key(this);
        }
    }

    static {
        Config[] result = {Config.ARGB_8888, null};
        if (VERSION.SDK_INT >= 26) {
            result = (Config[]) Arrays.copyOf(result, result.length + 1);
            result[result.length - 1] = Config.RGBA_F16;
        }
        ARGB_8888_IN_CONFIGS = result;
    }

    public void put(Bitmap bitmap) {
        Key key = this.keyPool.get(Util.getBitmapByteSize(bitmap), bitmap.getConfig());
        this.groupedMap.put(key, bitmap);
        NavigableMap<Integer, Integer> sizes = getSizesForConfig(bitmap.getConfig());
        Integer current = (Integer) sizes.get(Integer.valueOf(key.size));
        Integer valueOf = Integer.valueOf(key.size);
        int i = 1;
        if (current != null) {
            i = 1 + current.intValue();
        }
        sizes.put(valueOf, Integer.valueOf(i));
    }

    @Nullable
    public Bitmap get(int width, int height, Config config) {
        Key bestKey = findBestKey(Util.getBitmapByteSize(width, height, config), config);
        Bitmap result = (Bitmap) this.groupedMap.get(bestKey);
        if (result != null) {
            decrementBitmapOfSize(Integer.valueOf(bestKey.size), result);
            result.reconfigure(width, height, result.getConfig() != null ? result.getConfig() : Config.ARGB_8888);
        }
        return result;
    }

    private Key findBestKey(int size, Config config) {
        Key result = this.keyPool.get(size, config);
        Config[] inConfigs = getInConfigs(config);
        int length = inConfigs.length;
        int i = 0;
        while (i < length) {
            Config possibleConfig = inConfigs[i];
            Integer possibleSize = (Integer) getSizesForConfig(possibleConfig).ceilingKey(Integer.valueOf(size));
            if (possibleSize == null || possibleSize.intValue() > size * 8) {
                i++;
            } else {
                if (possibleSize.intValue() == size) {
                    if (possibleConfig == null) {
                        if (config == null) {
                            return result;
                        }
                    } else if (possibleConfig.equals(config)) {
                        return result;
                    }
                }
                this.keyPool.offer(result);
                return this.keyPool.get(possibleSize.intValue(), possibleConfig);
            }
        }
        return result;
    }

    @Nullable
    public Bitmap removeLast() {
        Bitmap removed = (Bitmap) this.groupedMap.removeLast();
        if (removed != null) {
            decrementBitmapOfSize(Integer.valueOf(Util.getBitmapByteSize(removed)), removed);
        }
        return removed;
    }

    private void decrementBitmapOfSize(Integer size, Bitmap removed) {
        NavigableMap<Integer, Integer> sizes = getSizesForConfig(removed.getConfig());
        Integer current = (Integer) sizes.get(size);
        if (current == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Tried to decrement empty size, size: ");
            sb.append(size);
            sb.append(", removed: ");
            sb.append(logBitmap(removed));
            sb.append(", this: ");
            sb.append(this);
            throw new NullPointerException(sb.toString());
        } else if (current.intValue() == 1) {
            sizes.remove(size);
        } else {
            sizes.put(size, Integer.valueOf(current.intValue() - 1));
        }
    }

    private NavigableMap<Integer, Integer> getSizesForConfig(Config config) {
        NavigableMap<Integer, Integer> sizes = (NavigableMap) this.sortedSizes.get(config);
        if (sizes != null) {
            return sizes;
        }
        NavigableMap<Integer, Integer> sizes2 = new TreeMap<>();
        this.sortedSizes.put(config, sizes2);
        return sizes2;
    }

    public String logBitmap(Bitmap bitmap) {
        return getBitmapString(Util.getBitmapByteSize(bitmap), bitmap.getConfig());
    }

    public String logBitmap(int width, int height, Config config) {
        return getBitmapString(Util.getBitmapByteSize(width, height, config), config);
    }

    public int getSize(Bitmap bitmap) {
        return Util.getBitmapByteSize(bitmap);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SizeConfigStrategy{groupedMap=");
        sb.append(this.groupedMap);
        StringBuilder sb2 = sb.append(", sortedSizes=(");
        for (Entry<Config, NavigableMap<Integer, Integer>> entry : this.sortedSizes.entrySet()) {
            sb2.append(entry.getKey());
            sb2.append('[');
            sb2.append(entry.getValue());
            sb2.append("], ");
        }
        if (!this.sortedSizes.isEmpty()) {
            sb2.replace(sb2.length() - 2, sb2.length(), "");
        }
        sb2.append(")}");
        return sb2.toString();
    }

    static String getBitmapString(int size, Config config) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(size);
        sb.append("](");
        sb.append(config);
        sb.append(")");
        return sb.toString();
    }

    private static Config[] getInConfigs(Config requested) {
        if (VERSION.SDK_INT >= 26 && Config.RGBA_F16.equals(requested)) {
            return RGBA_F16_IN_CONFIGS;
        }
        switch (AnonymousClass1.$SwitchMap$android$graphics$Bitmap$Config[requested.ordinal()]) {
            case 1:
                return ARGB_8888_IN_CONFIGS;
            case 2:
                return RGB_565_IN_CONFIGS;
            case 3:
                return ARGB_4444_IN_CONFIGS;
            case 4:
                return ALPHA_8_IN_CONFIGS;
            default:
                return new Config[]{requested};
        }
    }
}
