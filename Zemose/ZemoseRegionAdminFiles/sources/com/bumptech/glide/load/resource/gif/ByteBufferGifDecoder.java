package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.gifdecoder.GifDecoder.BitmapProvider;
import com.bumptech.glide.gifdecoder.GifHeader;
import com.bumptech.glide.gifdecoder.GifHeaderParser;
import com.bumptech.glide.gifdecoder.StandardGifDecoder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParser.ImageType;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.UnitTransformation;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Util;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Queue;

public class ByteBufferGifDecoder implements ResourceDecoder<ByteBuffer, GifDrawable> {
    private static final GifDecoderFactory GIF_DECODER_FACTORY = new GifDecoderFactory();
    private static final GifHeaderParserPool PARSER_POOL = new GifHeaderParserPool();
    private static final String TAG = "BufferGifDecoder";
    private final Context context;
    private final GifDecoderFactory gifDecoderFactory;
    private final GifHeaderParserPool parserPool;
    private final List<ImageHeaderParser> parsers;
    private final GifBitmapProvider provider;

    @VisibleForTesting
    static class GifDecoderFactory {
        GifDecoderFactory() {
        }

        /* access modifiers changed from: 0000 */
        public GifDecoder build(BitmapProvider provider, GifHeader header, ByteBuffer data2, int sampleSize) {
            return new StandardGifDecoder(provider, header, data2, sampleSize);
        }
    }

    @VisibleForTesting
    static class GifHeaderParserPool {
        private final Queue<GifHeaderParser> pool = Util.createQueue(0);

        GifHeaderParserPool() {
        }

        /* access modifiers changed from: 0000 */
        public synchronized GifHeaderParser obtain(ByteBuffer buffer) {
            GifHeaderParser result;
            result = (GifHeaderParser) this.pool.poll();
            if (result == null) {
                result = new GifHeaderParser();
            }
            return result.setData(buffer);
        }

        /* access modifiers changed from: 0000 */
        public synchronized void release(GifHeaderParser parser) {
            parser.clear();
            this.pool.offer(parser);
        }
    }

    public ByteBufferGifDecoder(Context context2) {
        this(context2, Glide.get(context2).getRegistry().getImageHeaderParsers(), Glide.get(context2).getBitmapPool(), Glide.get(context2).getArrayPool());
    }

    public ByteBufferGifDecoder(Context context2, List<ImageHeaderParser> parsers2, BitmapPool bitmapPool, ArrayPool arrayPool) {
        this(context2, parsers2, bitmapPool, arrayPool, PARSER_POOL, GIF_DECODER_FACTORY);
    }

    @VisibleForTesting
    ByteBufferGifDecoder(Context context2, List<ImageHeaderParser> parsers2, BitmapPool bitmapPool, ArrayPool arrayPool, GifHeaderParserPool parserPool2, GifDecoderFactory gifDecoderFactory2) {
        this.context = context2.getApplicationContext();
        this.parsers = parsers2;
        this.gifDecoderFactory = gifDecoderFactory2;
        this.provider = new GifBitmapProvider(bitmapPool, arrayPool);
        this.parserPool = parserPool2;
    }

    public boolean handles(@NonNull ByteBuffer source, @NonNull Options options) throws IOException {
        return !((Boolean) options.get(GifOptions.DISABLE_ANIMATION)).booleanValue() && ImageHeaderParserUtils.getType(this.parsers, source) == ImageType.GIF;
    }

    public GifDrawableResource decode(@NonNull ByteBuffer source, int width, int height, @NonNull Options options) {
        GifHeaderParser parser = this.parserPool.obtain(source);
        try {
            return decode(source, width, height, parser, options);
        } finally {
            this.parserPool.release(parser);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ea  */
    @Nullable
    private GifDrawableResource decode(ByteBuffer byteBuffer, int width, int height, GifHeaderParser parser, Options options) {
        long startTime = LogTime.getLogTime();
        try {
            GifHeader header = parser.parseHeader();
            if (header.getNumFrames() <= 0) {
                int i = width;
            } else if (header.getStatus() != 0) {
                int i2 = width;
            } else {
                Config config = options.get(GifOptions.DECODE_FORMAT) == DecodeFormat.PREFER_RGB_565 ? Config.RGB_565 : Config.ARGB_8888;
                try {
                    int sampleSize = getSampleSize(header, width, height);
                    GifDecoder gifDecoder = this.gifDecoderFactory.build(this.provider, header, byteBuffer, sampleSize);
                    gifDecoder.setDefaultBitmapConfig(config);
                    gifDecoder.advance();
                    Bitmap firstFrame = gifDecoder.getNextFrame();
                    if (firstFrame == null) {
                        if (Log.isLoggable(TAG, 2)) {
                            String str = TAG;
                            StringBuilder sb = new StringBuilder();
                            sb.append("Decoded GIF from stream in ");
                            sb.append(LogTime.getElapsedMillis(startTime));
                            Log.v(str, sb.toString());
                        }
                        return null;
                    }
                    GifDecoder gifDecoder2 = gifDecoder;
                    int i3 = sampleSize;
                    GifDrawable gifDrawable = new GifDrawable(this.context, gifDecoder, UnitTransformation.get(), width, height, firstFrame);
                    GifDrawableResource gifDrawableResource = new GifDrawableResource(gifDrawable);
                    if (Log.isLoggable(TAG, 2)) {
                        String str2 = TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Decoded GIF from stream in ");
                        sb2.append(LogTime.getElapsedMillis(startTime));
                        Log.v(str2, sb2.toString());
                    }
                    return gifDrawableResource;
                } catch (Throwable th) {
                    th = th;
                    if (Log.isLoggable(TAG, 2)) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Decoded GIF from stream in ");
                        sb3.append(LogTime.getElapsedMillis(startTime));
                        Log.v(TAG, sb3.toString());
                    }
                    throw th;
                }
            }
            if (Log.isLoggable(TAG, 2)) {
                String str3 = TAG;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Decoded GIF from stream in ");
                sb4.append(LogTime.getElapsedMillis(startTime));
                Log.v(str3, sb4.toString());
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            int i4 = width;
            if (Log.isLoggable(TAG, 2)) {
            }
            throw th;
        }
    }

    private static int getSampleSize(GifHeader gifHeader, int targetWidth, int targetHeight) {
        int exactSampleSize = Math.min(gifHeader.getHeight() / targetHeight, gifHeader.getWidth() / targetWidth);
        int sampleSize = Math.max(1, exactSampleSize == 0 ? 0 : Integer.highestOneBit(exactSampleSize));
        if (Log.isLoggable(TAG, 2) && sampleSize > 1) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Downsampling GIF, sampleSize: ");
            sb.append(sampleSize);
            sb.append(", target dimens: [");
            sb.append(targetWidth);
            sb.append("x");
            sb.append(targetHeight);
            sb.append("], actual dimens: [");
            sb.append(gifHeader.getWidth());
            sb.append("x");
            sb.append(gifHeader.getHeight());
            sb.append("]");
            Log.v(str, sb.toString());
        }
        return sampleSize;
    }
}
