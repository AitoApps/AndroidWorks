package com.payumoney.graphics.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import com.android.volley.toolbox.ImageLoader.ImageCache;

public class LRUBitMapCache extends LruCache<String, Bitmap> implements ImageCache {
    public LRUBitMapCache(int maxSize) {
        super(maxSize);
    }

    public LRUBitMapCache(Context ctx) {
        this(getCacheSize(ctx));
    }

    /* access modifiers changed from: protected */
    public int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    public Bitmap getBitmap(String url) {
        return (Bitmap) get(url);
    }

    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

    public static int getCacheSize(Context ctx) {
        DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels * displayMetrics.heightPixels * 4 * 3;
    }
}
