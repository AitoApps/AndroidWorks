package com.bumptech.glide.load.resource.gif;

import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParser.ImageType;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class StreamGifDecoder implements ResourceDecoder<InputStream, GifDrawable> {
    private static final String TAG = "StreamGifDecoder";
    private final ArrayPool byteArrayPool;
    private final ResourceDecoder<ByteBuffer, GifDrawable> byteBufferDecoder;
    private final List<ImageHeaderParser> parsers;

    public StreamGifDecoder(List<ImageHeaderParser> parsers2, ResourceDecoder<ByteBuffer, GifDrawable> byteBufferDecoder2, ArrayPool byteArrayPool2) {
        this.parsers = parsers2;
        this.byteBufferDecoder = byteBufferDecoder2;
        this.byteArrayPool = byteArrayPool2;
    }

    public boolean handles(@NonNull InputStream source, @NonNull Options options) throws IOException {
        return !((Boolean) options.get(GifOptions.DISABLE_ANIMATION)).booleanValue() && ImageHeaderParserUtils.getType(this.parsers, source, this.byteArrayPool) == ImageType.GIF;
    }

    public Resource<GifDrawable> decode(@NonNull InputStream source, int width, int height, @NonNull Options options) throws IOException {
        byte[] data2 = inputStreamToBytes(source);
        if (data2 == null) {
            return null;
        }
        return this.byteBufferDecoder.decode(ByteBuffer.wrap(data2), width, height, options);
    }

    private static byte[] inputStreamToBytes(InputStream is) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(16384);
        try {
            byte[] data2 = new byte[16384];
            while (true) {
                int read = is.read(data2);
                int nRead = read;
                if (read != -1) {
                    buffer.write(data2, 0, nRead);
                } else {
                    buffer.flush();
                    return buffer.toByteArray();
                }
            }
        } catch (IOException e) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Error reading data from stream", e);
            }
            return null;
        }
    }
}
