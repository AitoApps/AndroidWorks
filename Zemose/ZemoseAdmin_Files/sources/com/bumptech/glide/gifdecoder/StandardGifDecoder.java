package com.bumptech.glide.gifdecoder;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import com.bumptech.glide.gifdecoder.GifDecoder.BitmapProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Iterator;

public class StandardGifDecoder implements GifDecoder {
    private static final int BYTES_PER_INTEGER = 4;
    @ColorInt
    private static final int COLOR_TRANSPARENT_BLACK = 0;
    private static final int INITIAL_FRAME_POINTER = -1;
    private static final int MASK_INT_LOWEST_BYTE = 255;
    private static final int MAX_STACK_SIZE = 4096;
    private static final int NULL_CODE = -1;
    private static final String TAG = StandardGifDecoder.class.getSimpleName();
    @ColorInt
    private int[] act;
    @NonNull
    private Config bitmapConfig;
    private final BitmapProvider bitmapProvider;
    private byte[] block;
    private int downsampledHeight;
    private int downsampledWidth;
    private int framePointer;
    private GifHeader header;
    @Nullable
    private Boolean isFirstFrameTransparent;
    private byte[] mainPixels;
    @ColorInt
    private int[] mainScratch;
    private GifHeaderParser parser;
    @ColorInt
    private final int[] pct;
    private byte[] pixelStack;
    private short[] prefix;
    private Bitmap previousImage;
    private ByteBuffer rawData;
    private int sampleSize;
    private boolean savePrevious;
    private int status;
    private byte[] suffix;

    public StandardGifDecoder(@NonNull BitmapProvider provider, GifHeader gifHeader, ByteBuffer rawData2) {
        this(provider, gifHeader, rawData2, 1);
    }

    public StandardGifDecoder(@NonNull BitmapProvider provider, GifHeader gifHeader, ByteBuffer rawData2, int sampleSize2) {
        this(provider);
        setData(gifHeader, rawData2, sampleSize2);
    }

    public StandardGifDecoder(@NonNull BitmapProvider provider) {
        this.pct = new int[256];
        this.bitmapConfig = Config.ARGB_8888;
        this.bitmapProvider = provider;
        this.header = new GifHeader();
    }

    public int getWidth() {
        return this.header.width;
    }

    public int getHeight() {
        return this.header.height;
    }

    @NonNull
    public ByteBuffer getData() {
        return this.rawData;
    }

    public int getStatus() {
        return this.status;
    }

    public void advance() {
        this.framePointer = (this.framePointer + 1) % this.header.frameCount;
    }

    public int getDelay(int n) {
        if (n < 0 || n >= this.header.frameCount) {
            return -1;
        }
        return ((GifFrame) this.header.frames.get(n)).delay;
    }

    public int getNextDelay() {
        if (this.header.frameCount > 0) {
            int i = this.framePointer;
            if (i >= 0) {
                return getDelay(i);
            }
        }
        return 0;
    }

    public int getFrameCount() {
        return this.header.frameCount;
    }

    public int getCurrentFrameIndex() {
        return this.framePointer;
    }

    public void resetFrameIndex() {
        this.framePointer = -1;
    }

    @Deprecated
    public int getLoopCount() {
        if (this.header.loopCount == -1) {
            return 1;
        }
        return this.header.loopCount;
    }

    public int getNetscapeLoopCount() {
        return this.header.loopCount;
    }

    public int getTotalIterationCount() {
        if (this.header.loopCount == -1) {
            return 1;
        }
        if (this.header.loopCount == 0) {
            return 0;
        }
        return this.header.loopCount + 1;
    }

    public int getByteSize() {
        return this.rawData.limit() + this.mainPixels.length + (this.mainScratch.length * 4);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00f8, code lost:
        return null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00dd  */
    @Nullable
    public synchronized Bitmap getNextFrame() {
        if (this.header.frameCount > 0) {
            if (this.framePointer >= 0) {
                if (this.status != 1) {
                    if (this.status != 2) {
                        this.status = 0;
                        if (this.block == null) {
                            this.block = this.bitmapProvider.obtainByteArray(255);
                        }
                        GifFrame currentFrame = (GifFrame) this.header.frames.get(this.framePointer);
                        GifFrame previousFrame = null;
                        int previousIndex = this.framePointer - 1;
                        if (previousIndex >= 0) {
                            previousFrame = (GifFrame) this.header.frames.get(previousIndex);
                        }
                        this.act = currentFrame.lct != null ? currentFrame.lct : this.header.gct;
                        if (this.act == null) {
                            if (Log.isLoggable(TAG, 3)) {
                                String str = TAG;
                                StringBuilder sb = new StringBuilder();
                                sb.append("No valid color table found for frame #");
                                sb.append(this.framePointer);
                                Log.d(str, sb.toString());
                            }
                            this.status = 1;
                            return null;
                        }
                        if (currentFrame.transparency) {
                            System.arraycopy(this.act, 0, this.pct, 0, this.act.length);
                            this.act = this.pct;
                            this.act[currentFrame.transIndex] = 0;
                        }
                        return setPixels(currentFrame, previousFrame);
                    }
                }
                if (Log.isLoggable(TAG, 3)) {
                    String str2 = TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Unable to decode frame, status=");
                    sb2.append(this.status);
                    Log.d(str2, sb2.toString());
                }
            }
        }
        if (Log.isLoggable(TAG, 3)) {
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Unable to decode frame, frameCount=");
            sb3.append(this.header.frameCount);
            sb3.append(", framePointer=");
            sb3.append(this.framePointer);
            Log.d(str3, sb3.toString());
        }
        this.status = 1;
        if (this.status != 1) {
        }
        if (Log.isLoggable(TAG, 3)) {
        }
    }

    public int read(@Nullable InputStream is, int contentLength) {
        if (is != null) {
            try {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream(contentLength > 0 ? contentLength + 4096 : 16384);
                byte[] data2 = new byte[16384];
                while (true) {
                    int read = is.read(data2, 0, data2.length);
                    int nRead = read;
                    if (read == -1) {
                        break;
                    }
                    buffer.write(data2, 0, nRead);
                }
                buffer.flush();
                read(buffer.toByteArray());
            } catch (IOException e) {
                Log.w(TAG, "Error reading data from stream", e);
            }
        } else {
            this.status = 2;
        }
        if (is != null) {
            try {
                is.close();
            } catch (IOException e2) {
                Log.w(TAG, "Error closing stream", e2);
            }
        }
        return this.status;
    }

    public void clear() {
        this.header = null;
        byte[] bArr = this.mainPixels;
        if (bArr != null) {
            this.bitmapProvider.release(bArr);
        }
        int[] iArr = this.mainScratch;
        if (iArr != null) {
            this.bitmapProvider.release(iArr);
        }
        Bitmap bitmap = this.previousImage;
        if (bitmap != null) {
            this.bitmapProvider.release(bitmap);
        }
        this.previousImage = null;
        this.rawData = null;
        this.isFirstFrameTransparent = null;
        byte[] bArr2 = this.block;
        if (bArr2 != null) {
            this.bitmapProvider.release(bArr2);
        }
    }

    public synchronized void setData(@NonNull GifHeader header2, @NonNull byte[] data2) {
        setData(header2, ByteBuffer.wrap(data2));
    }

    public synchronized void setData(@NonNull GifHeader header2, @NonNull ByteBuffer buffer) {
        setData(header2, buffer, 1);
    }

    public synchronized void setData(@NonNull GifHeader header2, @NonNull ByteBuffer buffer, int sampleSize2) {
        if (sampleSize2 > 0) {
            int sampleSize3 = Integer.highestOneBit(sampleSize2);
            this.status = 0;
            this.header = header2;
            this.framePointer = -1;
            this.rawData = buffer.asReadOnlyBuffer();
            this.rawData.position(0);
            this.rawData.order(ByteOrder.LITTLE_ENDIAN);
            this.savePrevious = false;
            Iterator it = header2.frames.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (((GifFrame) it.next()).dispose == 3) {
                    this.savePrevious = true;
                    break;
                }
            }
            this.sampleSize = sampleSize3;
            this.downsampledWidth = header2.width / sampleSize3;
            this.downsampledHeight = header2.height / sampleSize3;
            this.mainPixels = this.bitmapProvider.obtainByteArray(header2.width * header2.height);
            this.mainScratch = this.bitmapProvider.obtainIntArray(this.downsampledWidth * this.downsampledHeight);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Sample size must be >=0, not: ");
            sb.append(sampleSize2);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    @NonNull
    private GifHeaderParser getHeaderParser() {
        if (this.parser == null) {
            this.parser = new GifHeaderParser();
        }
        return this.parser;
    }

    public synchronized int read(@Nullable byte[] data2) {
        this.header = getHeaderParser().setData(data2).parseHeader();
        if (data2 != null) {
            setData(this.header, data2);
        }
        return this.status;
    }

    public void setDefaultBitmapConfig(@NonNull Config config) {
        if (config == Config.ARGB_8888 || config == Config.RGB_565) {
            this.bitmapConfig = config;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unsupported format: ");
        sb.append(config);
        sb.append(", must be one of ");
        sb.append(Config.ARGB_8888);
        sb.append(" or ");
        sb.append(Config.RGB_565);
        throw new IllegalArgumentException(sb.toString());
    }

    private Bitmap setPixels(GifFrame currentFrame, GifFrame previousFrame) {
        int[] dest = this.mainScratch;
        if (previousFrame == null) {
            Bitmap bitmap = this.previousImage;
            if (bitmap != null) {
                this.bitmapProvider.release(bitmap);
            }
            this.previousImage = null;
            Arrays.fill(dest, 0);
        }
        if (previousFrame != null && previousFrame.dispose == 3 && this.previousImage == null) {
            Arrays.fill(dest, 0);
        }
        if (previousFrame != null && previousFrame.dispose > 0) {
            if (previousFrame.dispose == 2) {
                int c = 0;
                if (!currentFrame.transparency) {
                    c = this.header.bgColor;
                    if (currentFrame.lct != null && this.header.bgIndex == currentFrame.transIndex) {
                        c = 0;
                    }
                } else if (this.framePointer == 0) {
                    this.isFirstFrameTransparent = Boolean.valueOf(true);
                }
                int downsampledIH = previousFrame.ih / this.sampleSize;
                int downsampledIY = previousFrame.iy / this.sampleSize;
                int downsampledIW = previousFrame.iw / this.sampleSize;
                int downsampledIX = previousFrame.ix / this.sampleSize;
                int i = this.downsampledWidth;
                int topLeft = (downsampledIY * i) + downsampledIX;
                int bottomLeft = (i * downsampledIH) + topLeft;
                int left = topLeft;
                while (left < bottomLeft) {
                    int right = left + downsampledIW;
                    for (int pointer = left; pointer < right; pointer++) {
                        dest[pointer] = c;
                    }
                    left += this.downsampledWidth;
                }
            } else if (previousFrame.dispose == 3) {
                Bitmap bitmap2 = this.previousImage;
                if (bitmap2 != null) {
                    int i2 = this.downsampledWidth;
                    bitmap2.getPixels(dest, 0, i2, 0, 0, i2, this.downsampledHeight);
                }
            }
        }
        decodeBitmapData(currentFrame);
        if (currentFrame.interlace || this.sampleSize != 1) {
            copyCopyIntoScratchRobust(currentFrame);
        } else {
            copyIntoScratchFast(currentFrame);
        }
        if (this.savePrevious && (currentFrame.dispose == 0 || currentFrame.dispose == 1)) {
            if (this.previousImage == null) {
                this.previousImage = getNextBitmap();
            }
            Bitmap bitmap3 = this.previousImage;
            int i3 = this.downsampledWidth;
            bitmap3.setPixels(dest, 0, i3, 0, 0, i3, this.downsampledHeight);
        }
        Bitmap result = getNextBitmap();
        int i4 = this.downsampledWidth;
        result.setPixels(dest, 0, i4, 0, 0, i4, this.downsampledHeight);
        return result;
    }

    private void copyIntoScratchFast(GifFrame currentFrame) {
        GifFrame gifFrame = currentFrame;
        int[] dest = this.mainScratch;
        int downsampledIH = gifFrame.ih;
        int downsampledIY = gifFrame.iy;
        int downsampledIW = gifFrame.iw;
        int downsampledIX = gifFrame.ix;
        boolean isFirstFrame = this.framePointer == 0;
        int width = this.downsampledWidth;
        byte[] mainPixels2 = this.mainPixels;
        int[] act2 = this.act;
        int transparentColorIndex = -1;
        int i = 0;
        while (i < downsampledIH) {
            int k = (i + downsampledIY) * width;
            int dx = k + downsampledIX;
            int dlim = dx + downsampledIW;
            if (k + width < dlim) {
                dlim = k + width;
            }
            int transparentColorIndex2 = transparentColorIndex;
            int sx = gifFrame.iw * i;
            int dx2 = dx;
            while (dx2 < dlim) {
                int downsampledIH2 = downsampledIH;
                byte downsampledIH3 = mainPixels2[sx];
                int downsampledIY2 = downsampledIY;
                int downsampledIY3 = downsampledIH3 & 255;
                if (downsampledIY3 != transparentColorIndex2) {
                    int color = act2[downsampledIY3];
                    if (color != 0) {
                        dest[dx2] = color;
                    } else {
                        transparentColorIndex2 = downsampledIH3;
                    }
                }
                sx++;
                dx2++;
                downsampledIH = downsampledIH2;
                downsampledIY = downsampledIY2;
            }
            int i2 = downsampledIY;
            i++;
            transparentColorIndex = transparentColorIndex2;
            gifFrame = currentFrame;
        }
        int i3 = downsampledIY;
        this.isFirstFrameTransparent = Boolean.valueOf(this.isFirstFrameTransparent == null && isFirstFrame && transparentColorIndex != -1);
    }

    private void copyCopyIntoScratchRobust(GifFrame currentFrame) {
        boolean z;
        int downsampledIX;
        int downsampledIW;
        int downsampledIY;
        GifFrame gifFrame = currentFrame;
        int[] dest = this.mainScratch;
        int downsampledIH = gifFrame.ih / this.sampleSize;
        int downsampledIY2 = gifFrame.iy / this.sampleSize;
        int downsampledIW2 = gifFrame.iw / this.sampleSize;
        int downsampledIX2 = gifFrame.ix / this.sampleSize;
        int iline = 0;
        boolean isFirstFrame = this.framePointer == 0;
        int sampleSize2 = this.sampleSize;
        int downsampledWidth2 = this.downsampledWidth;
        int downsampledHeight2 = this.downsampledHeight;
        byte[] mainPixels2 = this.mainPixels;
        int[] act2 = this.act;
        int pass = 1;
        Boolean isFirstFrameTransparent2 = this.isFirstFrameTransparent;
        int i = 0;
        int inc = 8;
        while (i < downsampledIH) {
            int line = i;
            Boolean isFirstFrameTransparent3 = isFirstFrameTransparent2;
            if (gifFrame.interlace) {
                if (iline >= downsampledIH) {
                    pass++;
                    switch (pass) {
                        case 2:
                            iline = 4;
                            break;
                        case 3:
                            iline = 2;
                            inc = 4;
                            break;
                        case 4:
                            iline = 1;
                            inc = 2;
                            break;
                    }
                }
                line = iline;
                iline += inc;
            }
            int line2 = line + downsampledIY2;
            int downsampledIH2 = downsampledIH;
            boolean isNotDownsampling = sampleSize2 == 1;
            if (line2 < downsampledHeight2) {
                int k = line2 * downsampledWidth2;
                int dx = k + downsampledIX2;
                downsampledIY = downsampledIY2;
                int dlim = dx + downsampledIW2;
                downsampledIW = downsampledIW2;
                if (k + downsampledWidth2 < dlim) {
                    dlim = k + downsampledWidth2;
                }
                downsampledIX = downsampledIX2;
                int sx = i * sampleSize2 * gifFrame.iw;
                if (isNotDownsampling) {
                    int sx2 = sx;
                    int dx2 = dx;
                    while (dx2 < dlim) {
                        boolean isNotDownsampling2 = isNotDownsampling;
                        int averageColor = act2[mainPixels2[sx2] & 255];
                        if (averageColor != 0) {
                            dest[dx2] = averageColor;
                        } else if (isFirstFrame && isFirstFrameTransparent3 == null) {
                            isFirstFrameTransparent3 = Boolean.valueOf(true);
                        }
                        sx2 += sampleSize2;
                        dx2++;
                        isNotDownsampling = isNotDownsampling2;
                    }
                    isFirstFrameTransparent2 = isFirstFrameTransparent3;
                } else {
                    int maxPositionInSource = ((dlim - dx) * sampleSize2) + sx;
                    int sx3 = sx;
                    int dx3 = dx;
                    while (dx3 < dlim) {
                        int dlim2 = dlim;
                        int averageColor2 = averageColorsNear(sx3, maxPositionInSource, gifFrame.iw);
                        if (averageColor2 != 0) {
                            dest[dx3] = averageColor2;
                        } else if (isFirstFrame && isFirstFrameTransparent3 == null) {
                            isFirstFrameTransparent3 = Boolean.valueOf(true);
                        }
                        sx3 += sampleSize2;
                        dx3++;
                        dlim = dlim2;
                    }
                    isFirstFrameTransparent2 = isFirstFrameTransparent3;
                }
            } else {
                downsampledIY = downsampledIY2;
                downsampledIW = downsampledIW2;
                downsampledIX = downsampledIX2;
                isFirstFrameTransparent2 = isFirstFrameTransparent3;
            }
            i++;
            downsampledIH = downsampledIH2;
            downsampledIY2 = downsampledIY;
            downsampledIW2 = downsampledIW;
            downsampledIX2 = downsampledIX;
        }
        int i2 = downsampledIY2;
        int i3 = downsampledIW2;
        int i4 = downsampledIX2;
        Boolean isFirstFrameTransparent4 = isFirstFrameTransparent2;
        if (this.isFirstFrameTransparent == null) {
            if (isFirstFrameTransparent4 == null) {
                z = false;
            } else {
                z = isFirstFrameTransparent4.booleanValue();
            }
            this.isFirstFrameTransparent = Boolean.valueOf(z);
        }
    }

    @ColorInt
    private int averageColorsNear(int positionInMainPixels, int maxPositionInMainPixels, int currentFrameIw) {
        int alphaSum = 0;
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        int totalAdded = 0;
        for (int i = positionInMainPixels; i < this.sampleSize + positionInMainPixels; i++) {
            byte[] bArr = this.mainPixels;
            if (i >= bArr.length || i >= maxPositionInMainPixels) {
                break;
            }
            int currentColor = this.act[bArr[i] & 255];
            if (currentColor != 0) {
                alphaSum += (currentColor >> 24) & 255;
                redSum += (currentColor >> 16) & 255;
                greenSum += (currentColor >> 8) & 255;
                blueSum += currentColor & 255;
                totalAdded++;
            }
        }
        for (int i2 = positionInMainPixels + currentFrameIw; i2 < positionInMainPixels + currentFrameIw + this.sampleSize; i2++) {
            byte[] bArr2 = this.mainPixels;
            if (i2 >= bArr2.length || i2 >= maxPositionInMainPixels) {
                break;
            }
            int currentColor2 = this.act[bArr2[i2] & 255];
            if (currentColor2 != 0) {
                alphaSum += (currentColor2 >> 24) & 255;
                redSum += (currentColor2 >> 16) & 255;
                greenSum += (currentColor2 >> 8) & 255;
                blueSum += currentColor2 & 255;
                totalAdded++;
            }
        }
        if (totalAdded == 0) {
            return 0;
        }
        return ((alphaSum / totalAdded) << 24) | ((redSum / totalAdded) << 16) | ((greenSum / totalAdded) << 8) | (blueSum / totalAdded);
    }

    /* JADX WARNING: type inference failed for: r4v1, types: [short[]] */
    /* JADX WARNING: type inference failed for: r22v6 */
    /* JADX WARNING: type inference failed for: r14v7 */
    /* JADX WARNING: type inference failed for: r13v7 */
    /* JADX WARNING: type inference failed for: r24v6 */
    /* JADX WARNING: type inference failed for: r13v9 */
    /* JADX WARNING: type inference failed for: r13v10 */
    /* JADX WARNING: type inference failed for: r14v9 */
    /* JADX WARNING: type inference failed for: r13v11, types: [short] */
    /* JADX WARNING: type inference failed for: r13v12 */
    /* JADX WARNING: type inference failed for: r14v10 */
    /* JADX WARNING: type inference failed for: r14v11 */
    /* JADX WARNING: type inference failed for: r22v13 */
    /* JADX WARNING: type inference failed for: r14v12 */
    /* JADX WARNING: type inference failed for: r13v13 */
    /* JADX WARNING: type inference failed for: r13v14 */
    /* JADX WARNING: type inference failed for: r13v15 */
    /* JADX WARNING: type inference failed for: r13v16 */
    /* JADX WARNING: type inference failed for: r14v13 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=short, code=null, for r13v11, types: [short] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=short[], code=null, for r4v1, types: [short[]] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r22v6
  assigns: []
  uses: []
  mth insns count: 173
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 9 */
    private void decodeBitmapData(GifFrame frame) {
        int i;
        int i2;
        int pi;
        ? r14;
        StandardGifDecoder standardGifDecoder = this;
        GifFrame gifFrame = frame;
        if (gifFrame != null) {
            standardGifDecoder.rawData.position(gifFrame.bufferFrameStart);
        }
        if (gifFrame == null) {
            i2 = standardGifDecoder.header.width;
            i = standardGifDecoder.header.height;
        } else {
            i2 = gifFrame.iw;
            i = gifFrame.ih;
        }
        int npix = i2 * i;
        byte[] bArr = standardGifDecoder.mainPixels;
        if (bArr == null || bArr.length < npix) {
            standardGifDecoder.mainPixels = standardGifDecoder.bitmapProvider.obtainByteArray(npix);
        }
        byte[] mainPixels2 = standardGifDecoder.mainPixels;
        if (standardGifDecoder.prefix == null) {
            standardGifDecoder.prefix = new short[4096];
        }
        ? r4 = standardGifDecoder.prefix;
        if (standardGifDecoder.suffix == null) {
            standardGifDecoder.suffix = new byte[4096];
        }
        byte[] suffix2 = standardGifDecoder.suffix;
        if (standardGifDecoder.pixelStack == null) {
            standardGifDecoder.pixelStack = new byte[FragmentTransaction.TRANSIT_FRAGMENT_OPEN];
        }
        byte[] pixelStack2 = standardGifDecoder.pixelStack;
        int dataSize = readByte();
        int clear = 1 << dataSize;
        int endOfInformation = clear + 1;
        int available = clear + 2;
        char c = 65535;
        int codeSize = dataSize + 1;
        int codeMask = (1 << codeSize) - 1;
        int code = 0;
        while (code < clear) {
            r4[code] = 0;
            suffix2[code] = (byte) code;
            code++;
        }
        byte[] block2 = standardGifDecoder.block;
        int bi = 0;
        int i3 = 0;
        byte b = 0;
        int count = 0;
        ? r22 = 0;
        int datum = 0;
        int i4 = available;
        int codeSize2 = code;
        int bits = 0;
        int codeSize3 = codeSize;
        int oldCode = 0;
        int codeMask2 = codeMask;
        int available2 = i4;
        while (true) {
            if (bits >= npix) {
                break;
            }
            if (count == 0) {
                count = readBlock();
                if (count <= 0) {
                    standardGifDecoder.status = 3;
                    break;
                }
                bi = 0;
            }
            datum += (block2[bi] & 255) << r22;
            bi++;
            char c2 = 65535;
            count--;
            int i5 = b;
            int top = i3;
            int i6 = bits;
            int bits2 = r22 + 8;
            int pi2 = oldCode;
            ? r142 = c;
            ? code2 = codeSize2;
            int codeSize4 = codeSize3;
            while (true) {
                if (bits2 < codeSize4) {
                    codeSize3 = codeSize4;
                    codeSize2 = code2;
                    c = r142;
                    oldCode = pi2;
                    standardGifDecoder = this;
                    pi = bits2;
                    bits = i6;
                    i3 = top;
                    b = i5;
                    break;
                }
                ? r13 = datum & codeMask2;
                datum >>= codeSize4;
                bits2 -= codeSize4;
                if (r13 == clear) {
                    codeSize4 = dataSize + 1;
                    codeMask2 = (1 << codeSize4) - 1;
                    available2 = clear + 2;
                    r14 = -1;
                } else if (r13 == endOfInformation) {
                    codeSize3 = codeSize4;
                    codeSize2 = r13;
                    c = r142;
                    oldCode = pi2;
                    pi = bits2;
                    bits = i6;
                    i3 = top;
                    b = i5;
                    break;
                } else if (r142 == c2) {
                    mainPixels2[pi2] = suffix2[r13];
                    pi2++;
                    i6++;
                    r14 = r13;
                    i5 = r13;
                } else {
                    ? r24 = r13;
                    if (r13 >= available2) {
                        pixelStack2[top] = (byte) i5;
                        top++;
                        r13 = r142;
                    } else {
                        int first = i5;
                    }
                    ? r132 = r13;
                    while (r132 >= clear) {
                        pixelStack2[top] = suffix2[r132];
                        top++;
                        r132 = r4[r132];
                    }
                    int i7 = suffix2[r132] & 255;
                    mainPixels2[pi2] = (byte) i7;
                    pi2++;
                    i6++;
                    while (top > 0) {
                        top--;
                        mainPixels2[pi2] = pixelStack2[top];
                        pi2++;
                        i6++;
                    }
                    if (available2 < 4096) {
                        r4[available2] = (short) r142;
                        suffix2[available2] = (byte) i7;
                        available2++;
                        if ((available2 & codeMask2) == 0) {
                            if (available2 < 4096) {
                                codeSize4++;
                                codeMask2 += available2;
                            }
                        }
                    }
                    r14 = r24;
                    i5 = i7;
                    standardGifDecoder = this;
                    c2 = 65535;
                    r13 = r132;
                }
                r142 = r14;
                code2 = r13;
            }
            r22 = pi;
        }
        Arrays.fill(mainPixels2, oldCode, npix, 0);
    }

    private int readByte() {
        return this.rawData.get() & 255;
    }

    private int readBlock() {
        int blockSize = readByte();
        if (blockSize <= 0) {
            return blockSize;
        }
        ByteBuffer byteBuffer = this.rawData;
        byteBuffer.get(this.block, 0, Math.min(blockSize, byteBuffer.remaining()));
        return blockSize;
    }

    private Bitmap getNextBitmap() {
        Boolean bool = this.isFirstFrameTransparent;
        Bitmap result = this.bitmapProvider.obtain(this.downsampledWidth, this.downsampledHeight, (bool == null || bool.booleanValue()) ? Config.ARGB_8888 : this.bitmapConfig);
        result.setHasAlpha(true);
        return result;
    }
}
