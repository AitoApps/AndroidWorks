package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParser.ImageType;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy.SampleSizeRounding;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public final class Downsampler {
    public static final Option<Boolean> ALLOW_HARDWARE_CONFIG = Option.memory("com.bumtpech.glide.load.resource.bitmap.Downsampler.AllowHardwareDecode");
    public static final Option<DecodeFormat> DECODE_FORMAT = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeFormat", DecodeFormat.DEFAULT);
    @Deprecated
    public static final Option<DownsampleStrategy> DOWNSAMPLE_STRATEGY = DownsampleStrategy.OPTION;
    private static final DecodeCallbacks EMPTY_CALLBACKS = new DecodeCallbacks() {
        public void onObtainBounds() {
        }

        public void onDecodeComplete(BitmapPool bitmapPool, Bitmap downsampled) {
        }
    };
    public static final Option<Boolean> FIX_BITMAP_SIZE_TO_REQUESTED_DIMENSIONS = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.FixBitmapSize", Boolean.valueOf(false));
    private static final String ICO_MIME_TYPE = "image/x-ico";
    private static final int MARK_POSITION = 10485760;
    private static final Set<String> NO_DOWNSAMPLE_PRE_N_MIME_TYPES = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{WBMP_MIME_TYPE, ICO_MIME_TYPE})));
    private static final Queue<Options> OPTIONS_QUEUE = Util.createQueue(0);
    static final String TAG = "Downsampler";
    private static final Set<ImageType> TYPES_THAT_USE_POOL_PRE_KITKAT = Collections.unmodifiableSet(EnumSet.of(ImageType.JPEG, ImageType.PNG_A, ImageType.PNG));
    private static final String WBMP_MIME_TYPE = "image/vnd.wap.wbmp";
    private final BitmapPool bitmapPool;
    private final ArrayPool byteArrayPool;
    private final DisplayMetrics displayMetrics;
    private final HardwareConfigState hardwareConfigState = HardwareConfigState.getInstance();
    private final List<ImageHeaderParser> parsers;

    public interface DecodeCallbacks {
        void onDecodeComplete(BitmapPool bitmapPool, Bitmap bitmap) throws IOException;

        void onObtainBounds();
    }

    public Downsampler(List<ImageHeaderParser> parsers2, DisplayMetrics displayMetrics2, BitmapPool bitmapPool2, ArrayPool byteArrayPool2) {
        this.parsers = parsers2;
        this.displayMetrics = (DisplayMetrics) Preconditions.checkNotNull(displayMetrics2);
        this.bitmapPool = (BitmapPool) Preconditions.checkNotNull(bitmapPool2);
        this.byteArrayPool = (ArrayPool) Preconditions.checkNotNull(byteArrayPool2);
    }

    public boolean handles(InputStream is) {
        return true;
    }

    public boolean handles(ByteBuffer byteBuffer) {
        return true;
    }

    public Resource<Bitmap> decode(InputStream is, int outWidth, int outHeight, com.bumptech.glide.load.Options options) throws IOException {
        return decode(is, outWidth, outHeight, options, EMPTY_CALLBACKS);
    }

    public Resource<Bitmap> decode(InputStream is, int requestedWidth, int requestedHeight, com.bumptech.glide.load.Options options, DecodeCallbacks callbacks) throws IOException {
        boolean isHardwareConfigAllowed;
        com.bumptech.glide.load.Options options2 = options;
        Preconditions.checkArgument(is.markSupported(), "You must provide an InputStream that supports mark()");
        byte[] bytesForOptions = (byte[]) this.byteArrayPool.get(65536, byte[].class);
        Options bitmapFactoryOptions = getDefaultOptions();
        bitmapFactoryOptions.inTempStorage = bytesForOptions;
        DecodeFormat decodeFormat = (DecodeFormat) options2.get(DECODE_FORMAT);
        DownsampleStrategy downsampleStrategy = (DownsampleStrategy) options2.get(DownsampleStrategy.OPTION);
        boolean fixBitmapToRequestedDimensions = ((Boolean) options2.get(FIX_BITMAP_SIZE_TO_REQUESTED_DIMENSIONS)).booleanValue();
        boolean isHardwareConfigAllowed2 = options2.get(ALLOW_HARDWARE_CONFIG) != null && ((Boolean) options2.get(ALLOW_HARDWARE_CONFIG)).booleanValue();
        if (decodeFormat == DecodeFormat.PREFER_ARGB_8888_DISALLOW_HARDWARE) {
            isHardwareConfigAllowed = false;
        } else {
            isHardwareConfigAllowed = isHardwareConfigAllowed2;
        }
        try {
            return BitmapResource.obtain(decodeFromWrappedStreams(is, bitmapFactoryOptions, downsampleStrategy, decodeFormat, isHardwareConfigAllowed, requestedWidth, requestedHeight, fixBitmapToRequestedDimensions, callbacks), this.bitmapPool);
        } finally {
            releaseOptions(bitmapFactoryOptions);
            this.byteArrayPool.put(bytesForOptions);
        }
    }

    private Bitmap decodeFromWrappedStreams(InputStream is, Options options, DownsampleStrategy downsampleStrategy, DecodeFormat decodeFormat, boolean isHardwareConfigAllowed, int requestedWidth, int requestedHeight, boolean fixBitmapToRequestedDimensions, DecodeCallbacks callbacks) throws IOException {
        boolean isHardwareConfigAllowed2;
        Downsampler downsampler;
        int expectedHeight;
        int expectedWidth;
        InputStream inputStream = is;
        Options options2 = options;
        DecodeCallbacks decodeCallbacks = callbacks;
        long startTime = LogTime.getLogTime();
        int[] sourceDimensions = getDimensions(inputStream, options2, decodeCallbacks, this.bitmapPool);
        boolean z = false;
        int sourceWidth = sourceDimensions[0];
        int sourceHeight = sourceDimensions[1];
        String sourceMimeType = options2.outMimeType;
        if (sourceWidth == -1 || sourceHeight == -1) {
            isHardwareConfigAllowed2 = false;
        } else {
            isHardwareConfigAllowed2 = isHardwareConfigAllowed;
        }
        int orientation = ImageHeaderParserUtils.getOrientation(this.parsers, inputStream, this.byteArrayPool);
        int degreesToRotate = TransformationUtils.getExifOrientationDegrees(orientation);
        boolean isExifOrientationRequired = TransformationUtils.isExifOrientationRequired(orientation);
        int i = requestedWidth;
        int targetWidth = i == Integer.MIN_VALUE ? sourceWidth : i;
        int i2 = requestedHeight;
        int targetHeight = i2 == Integer.MIN_VALUE ? sourceHeight : i2;
        ImageType imageType = ImageHeaderParserUtils.getType(this.parsers, inputStream, this.byteArrayPool);
        BitmapPool bitmapPool2 = this.bitmapPool;
        ImageType imageType2 = imageType;
        calculateScaling(imageType, is, callbacks, bitmapPool2, downsampleStrategy, degreesToRotate, sourceWidth, sourceHeight, targetWidth, targetHeight, options);
        int orientation2 = orientation;
        String sourceMimeType2 = sourceMimeType;
        int sourceHeight2 = sourceHeight;
        int sourceWidth2 = sourceWidth;
        DecodeCallbacks decodeCallbacks2 = decodeCallbacks;
        Options options3 = options2;
        calculateConfig(is, decodeFormat, isHardwareConfigAllowed2, isExifOrientationRequired, options, targetWidth, targetHeight);
        if (VERSION.SDK_INT >= 19) {
            z = true;
        }
        boolean isKitKatOrGreater = z;
        if (options3.inSampleSize == 1 || isKitKatOrGreater) {
            ImageType imageType3 = imageType2;
            downsampler = this;
            if (downsampler.shouldUsePool(imageType3)) {
                if (sourceWidth2 < 0 || sourceHeight2 < 0 || !fixBitmapToRequestedDimensions || !isKitKatOrGreater) {
                    float densityMultiplier = isScaling(options) ? ((float) options3.inTargetDensity) / ((float) options3.inDensity) : 1.0f;
                    int sampleSize = options3.inSampleSize;
                    int downsampledHeight = (int) Math.ceil((double) (((float) sourceHeight2) / ((float) sampleSize)));
                    expectedWidth = Math.round(((float) ((int) Math.ceil((double) (((float) sourceWidth2) / ((float) sampleSize))))) * densityMultiplier);
                    expectedHeight = Math.round(((float) downsampledHeight) * densityMultiplier);
                    boolean z2 = isKitKatOrGreater;
                    if (Log.isLoggable(TAG, 2)) {
                        String str = TAG;
                        StringBuilder sb = new StringBuilder();
                        ImageType imageType4 = imageType3;
                        sb.append("Calculated target [");
                        sb.append(expectedWidth);
                        sb.append("x");
                        sb.append(expectedHeight);
                        sb.append("] for source [");
                        sb.append(sourceWidth2);
                        sb.append("x");
                        sb.append(sourceHeight2);
                        sb.append("], sampleSize: ");
                        sb.append(sampleSize);
                        sb.append(", targetDensity: ");
                        sb.append(options3.inTargetDensity);
                        sb.append(", density: ");
                        sb.append(options3.inDensity);
                        sb.append(", density multiplier: ");
                        sb.append(densityMultiplier);
                        Log.v(str, sb.toString());
                    }
                } else {
                    boolean z3 = isKitKatOrGreater;
                    ImageType imageType5 = imageType3;
                    expectedWidth = targetWidth;
                    expectedHeight = targetHeight;
                }
                if (expectedWidth > 0 && expectedHeight > 0) {
                    setInBitmap(options3, downsampler.bitmapPool, expectedWidth, expectedHeight);
                }
            } else {
                ImageType imageType6 = imageType3;
            }
        } else {
            boolean z4 = isKitKatOrGreater;
            ImageType imageType7 = imageType2;
            downsampler = this;
        }
        Bitmap downsampled = decodeStream(is, options3, decodeCallbacks2, downsampler.bitmapPool);
        decodeCallbacks2.onDecodeComplete(downsampler.bitmapPool, downsampled);
        if (Log.isLoggable(TAG, 2)) {
            logDecode(sourceWidth2, sourceHeight2, sourceMimeType2, options, downsampled, requestedWidth, requestedHeight, startTime);
        }
        Bitmap rotated = null;
        if (downsampled != null) {
            downsampled.setDensity(downsampler.displayMetrics.densityDpi);
            rotated = TransformationUtils.rotateImageExif(downsampler.bitmapPool, downsampled, orientation2);
            if (!downsampled.equals(rotated)) {
                downsampler.bitmapPool.put(downsampled);
            }
        }
        return rotated;
    }

    private static void calculateScaling(ImageType imageType, InputStream is, DecodeCallbacks decodeCallbacks, BitmapPool bitmapPool2, DownsampleStrategy downsampleStrategy, int degreesToRotate, int sourceWidth, int sourceHeight, int targetWidth, int targetHeight, Options options) throws IOException {
        float exactScaleFactor;
        int scaleFactor;
        int powerOfTwoSampleSize;
        int powerOfTwoHeight;
        int powerOfTwoWidth;
        ImageType imageType2 = imageType;
        DownsampleStrategy downsampleStrategy2 = downsampleStrategy;
        int i = degreesToRotate;
        int i2 = sourceWidth;
        int i3 = sourceHeight;
        int i4 = targetWidth;
        int i5 = targetHeight;
        Options options2 = options;
        if (i2 <= 0 || i3 <= 0) {
            if (Log.isLoggable(TAG, 3)) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to determine dimensions for: ");
                sb.append(imageType2);
                sb.append(" with target [");
                sb.append(i4);
                sb.append("x");
                sb.append(i5);
                sb.append("]");
                Log.d(str, sb.toString());
            }
            return;
        }
        if (i == 90 || i == 270) {
            exactScaleFactor = downsampleStrategy2.getScaleFactor(i3, i2, i4, i5);
        } else {
            exactScaleFactor = downsampleStrategy2.getScaleFactor(i2, i3, i4, i5);
        }
        if (exactScaleFactor > 0.0f) {
            SampleSizeRounding rounding = downsampleStrategy2.getSampleSizeRounding(i2, i3, i4, i5);
            if (rounding != null) {
                int outWidth = round((double) (((float) i2) * exactScaleFactor));
                int outHeight = round((double) (((float) i3) * exactScaleFactor));
                int widthScaleFactor = i2 / outWidth;
                int heightScaleFactor = i3 / outHeight;
                if (rounding == SampleSizeRounding.MEMORY) {
                    scaleFactor = Math.max(widthScaleFactor, heightScaleFactor);
                } else {
                    scaleFactor = Math.min(widthScaleFactor, heightScaleFactor);
                }
                int i6 = outWidth;
                if (VERSION.SDK_INT > 23 || !NO_DOWNSAMPLE_PRE_N_MIME_TYPES.contains(options2.outMimeType)) {
                    powerOfTwoSampleSize = Math.max(1, Integer.highestOneBit(scaleFactor));
                    if (rounding == SampleSizeRounding.MEMORY && ((float) powerOfTwoSampleSize) < 1.0f / exactScaleFactor) {
                        powerOfTwoSampleSize <<= 1;
                    }
                } else {
                    powerOfTwoSampleSize = 1;
                }
                options2.inSampleSize = powerOfTwoSampleSize;
                if (imageType2 == ImageType.JPEG) {
                    int nativeScaling = Math.min(powerOfTwoSampleSize, 8);
                    SampleSizeRounding sampleSizeRounding = rounding;
                    powerOfTwoWidth = (int) Math.ceil((double) (((float) i2) / ((float) nativeScaling)));
                    int i7 = outHeight;
                    powerOfTwoHeight = (int) Math.ceil((double) (((float) i3) / ((float) nativeScaling)));
                    int secondaryScaling = powerOfTwoSampleSize / 8;
                    if (secondaryScaling > 0) {
                        powerOfTwoWidth /= secondaryScaling;
                        powerOfTwoHeight /= secondaryScaling;
                    }
                    BitmapPool bitmapPool3 = bitmapPool2;
                } else {
                    int i8 = outHeight;
                    if (imageType2 == ImageType.PNG) {
                        BitmapPool bitmapPool4 = bitmapPool2;
                    } else if (imageType2 == ImageType.PNG_A) {
                        BitmapPool bitmapPool5 = bitmapPool2;
                    } else {
                        if (imageType2 == ImageType.WEBP) {
                            InputStream inputStream = is;
                            DecodeCallbacks decodeCallbacks2 = decodeCallbacks;
                            BitmapPool bitmapPool6 = bitmapPool2;
                        } else if (imageType2 == ImageType.WEBP_A) {
                            InputStream inputStream2 = is;
                            DecodeCallbacks decodeCallbacks3 = decodeCallbacks;
                            BitmapPool bitmapPool7 = bitmapPool2;
                        } else if (i2 % powerOfTwoSampleSize == 0 && i3 % powerOfTwoSampleSize == 0) {
                            powerOfTwoWidth = i2 / powerOfTwoSampleSize;
                            powerOfTwoHeight = i3 / powerOfTwoSampleSize;
                            BitmapPool bitmapPool8 = bitmapPool2;
                        } else {
                            int[] dimensions = getDimensions(is, options2, decodeCallbacks, bitmapPool2);
                            int powerOfTwoWidth2 = dimensions[0];
                            powerOfTwoHeight = dimensions[1];
                            powerOfTwoWidth = powerOfTwoWidth2;
                        }
                        if (VERSION.SDK_INT >= 24) {
                            int powerOfTwoWidth3 = Math.round(((float) i2) / ((float) powerOfTwoSampleSize));
                            powerOfTwoHeight = Math.round(((float) i3) / ((float) powerOfTwoSampleSize));
                            powerOfTwoWidth = powerOfTwoWidth3;
                        } else {
                            powerOfTwoHeight = (int) Math.floor((double) (((float) i3) / ((float) powerOfTwoSampleSize)));
                            powerOfTwoWidth = (int) Math.floor((double) (((float) i2) / ((float) powerOfTwoSampleSize)));
                        }
                    }
                    powerOfTwoHeight = (int) Math.floor((double) (((float) i3) / ((float) powerOfTwoSampleSize)));
                    powerOfTwoWidth = (int) Math.floor((double) (((float) i2) / ((float) powerOfTwoSampleSize)));
                }
                int i9 = widthScaleFactor;
                double adjustedScaleFactor = (double) downsampleStrategy2.getScaleFactor(powerOfTwoWidth, powerOfTwoHeight, i4, i5);
                int i10 = heightScaleFactor;
                if (VERSION.SDK_INT >= 19) {
                    options2.inTargetDensity = adjustTargetDensityForError(adjustedScaleFactor);
                    options2.inDensity = getDensityMultiplier(adjustedScaleFactor);
                }
                if (isScaling(options)) {
                    options2.inScaled = true;
                } else {
                    options2.inTargetDensity = 0;
                    options2.inDensity = 0;
                }
                if (Log.isLoggable(TAG, 2)) {
                    String str2 = TAG;
                    StringBuilder sb2 = new StringBuilder();
                    int i11 = scaleFactor;
                    sb2.append("Calculate scaling, source: [");
                    sb2.append(i2);
                    sb2.append("x");
                    sb2.append(i3);
                    sb2.append("], target: [");
                    sb2.append(i4);
                    sb2.append("x");
                    sb2.append(i5);
                    sb2.append("], power of two scaled: [");
                    sb2.append(powerOfTwoWidth);
                    sb2.append("x");
                    sb2.append(powerOfTwoHeight);
                    sb2.append("], exact scale factor: ");
                    sb2.append(exactScaleFactor);
                    sb2.append(", power of 2 sample size: ");
                    sb2.append(powerOfTwoSampleSize);
                    sb2.append(", adjusted scale factor: ");
                    sb2.append(adjustedScaleFactor);
                    sb2.append(", target density: ");
                    sb2.append(options2.inTargetDensity);
                    sb2.append(", density: ");
                    sb2.append(options2.inDensity);
                    Log.v(str2, sb2.toString());
                }
                return;
            }
            throw new IllegalArgumentException("Cannot round with null rounding");
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Cannot scale with factor: ");
        sb3.append(exactScaleFactor);
        sb3.append(" from: ");
        sb3.append(downsampleStrategy2);
        sb3.append(", source: [");
        sb3.append(i2);
        sb3.append("x");
        sb3.append(i3);
        sb3.append("], target: [");
        sb3.append(i4);
        sb3.append("x");
        sb3.append(i5);
        sb3.append("]");
        throw new IllegalArgumentException(sb3.toString());
    }

    private static int adjustTargetDensityForError(double adjustedScaleFactor) {
        int densityMultiplier = getDensityMultiplier(adjustedScaleFactor);
        int targetDensity = round(((double) densityMultiplier) * adjustedScaleFactor);
        return round(((double) targetDensity) * (adjustedScaleFactor / ((double) (((float) targetDensity) / ((float) densityMultiplier)))));
    }

    private static int getDensityMultiplier(double adjustedScaleFactor) {
        return (int) Math.round((adjustedScaleFactor <= 1.0d ? adjustedScaleFactor : 1.0d / adjustedScaleFactor) * 2.147483647E9d);
    }

    private static int round(double value) {
        return (int) (0.5d + value);
    }

    private boolean shouldUsePool(ImageType imageType) {
        if (VERSION.SDK_INT >= 19) {
            return true;
        }
        return TYPES_THAT_USE_POOL_PRE_KITKAT.contains(imageType);
    }

    private void calculateConfig(InputStream is, DecodeFormat format, boolean isHardwareConfigAllowed, boolean isExifOrientationRequired, Options optionsWithScaling, int targetWidth, int targetHeight) {
        if (!this.hardwareConfigState.setHardwareConfigIfAllowed(targetWidth, targetHeight, optionsWithScaling, format, isHardwareConfigAllowed, isExifOrientationRequired)) {
            if (format == DecodeFormat.PREFER_ARGB_8888 || format == DecodeFormat.PREFER_ARGB_8888_DISALLOW_HARDWARE || VERSION.SDK_INT == 16) {
                optionsWithScaling.inPreferredConfig = Config.ARGB_8888;
                return;
            }
            boolean hasAlpha = false;
            try {
                hasAlpha = ImageHeaderParserUtils.getType(this.parsers, is, this.byteArrayPool).hasAlpha();
            } catch (IOException e) {
                if (Log.isLoggable(TAG, 3)) {
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Cannot determine whether the image has alpha or not from header, format ");
                    sb.append(format);
                    Log.d(str, sb.toString(), e);
                }
            }
            optionsWithScaling.inPreferredConfig = hasAlpha ? Config.ARGB_8888 : Config.RGB_565;
            if (optionsWithScaling.inPreferredConfig == Config.RGB_565) {
                optionsWithScaling.inDither = true;
            }
        }
    }

    private static int[] getDimensions(InputStream is, Options options, DecodeCallbacks decodeCallbacks, BitmapPool bitmapPool2) throws IOException {
        options.inJustDecodeBounds = true;
        decodeStream(is, options, decodeCallbacks, bitmapPool2);
        options.inJustDecodeBounds = false;
        return new int[]{options.outWidth, options.outHeight};
    }

    private static Bitmap decodeStream(InputStream is, Options options, DecodeCallbacks callbacks, BitmapPool bitmapPool2) throws IOException {
        IOException bitmapAssertionException;
        if (options.inJustDecodeBounds) {
            is.mark(MARK_POSITION);
        } else {
            callbacks.onObtainBounds();
        }
        int sourceWidth = options.outWidth;
        int sourceHeight = options.outHeight;
        String outMimeType = options.outMimeType;
        TransformationUtils.getBitmapDrawableLock().lock();
        try {
            Bitmap result = BitmapFactory.decodeStream(is, null, options);
            TransformationUtils.getBitmapDrawableLock().unlock();
            if (options.inJustDecodeBounds) {
                is.reset();
            }
            return result;
        } catch (IOException e) {
            throw bitmapAssertionException;
        } catch (IllegalArgumentException e2) {
            bitmapAssertionException = newIoExceptionForInBitmapAssertion(e2, sourceWidth, sourceHeight, outMimeType, options);
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to decode with inBitmap, trying again without Bitmap re-use", bitmapAssertionException);
            }
            if (options.inBitmap != null) {
                is.reset();
                bitmapPool2.put(options.inBitmap);
                options.inBitmap = null;
                Bitmap decodeStream = decodeStream(is, options, callbacks, bitmapPool2);
                TransformationUtils.getBitmapDrawableLock().unlock();
                return decodeStream;
            }
            throw bitmapAssertionException;
        } catch (Throwable th) {
            TransformationUtils.getBitmapDrawableLock().unlock();
            throw th;
        }
    }

    private static boolean isScaling(Options options) {
        return options.inTargetDensity > 0 && options.inDensity > 0 && options.inTargetDensity != options.inDensity;
    }

    private static void logDecode(int sourceWidth, int sourceHeight, String outMimeType, Options options, Bitmap result, int requestedWidth, int requestedHeight, long startTime) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Decoded ");
        sb.append(getBitmapString(result));
        sb.append(" from [");
        sb.append(sourceWidth);
        sb.append("x");
        sb.append(sourceHeight);
        sb.append("] ");
        sb.append(outMimeType);
        sb.append(" with inBitmap ");
        sb.append(getInBitmapString(options));
        sb.append(" for [");
        sb.append(requestedWidth);
        sb.append("x");
        sb.append(requestedHeight);
        sb.append("], sample size: ");
        sb.append(options.inSampleSize);
        sb.append(", density: ");
        sb.append(options.inDensity);
        sb.append(", target density: ");
        sb.append(options.inTargetDensity);
        sb.append(", thread: ");
        sb.append(Thread.currentThread().getName());
        sb.append(", duration: ");
        sb.append(LogTime.getElapsedMillis(startTime));
        Log.v(str, sb.toString());
    }

    private static String getInBitmapString(Options options) {
        return getBitmapString(options.inBitmap);
    }

    @Nullable
    @TargetApi(19)
    private static String getBitmapString(Bitmap bitmap) {
        String sizeString;
        if (bitmap == null) {
            return null;
        }
        if (VERSION.SDK_INT >= 19) {
            StringBuilder sb = new StringBuilder();
            sb.append(" (");
            sb.append(bitmap.getAllocationByteCount());
            sb.append(")");
            sizeString = sb.toString();
        } else {
            sizeString = "";
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("[");
        sb2.append(bitmap.getWidth());
        sb2.append("x");
        sb2.append(bitmap.getHeight());
        sb2.append("] ");
        sb2.append(bitmap.getConfig());
        sb2.append(sizeString);
        return sb2.toString();
    }

    private static IOException newIoExceptionForInBitmapAssertion(IllegalArgumentException e, int outWidth, int outHeight, String outMimeType, Options options) {
        StringBuilder sb = new StringBuilder();
        sb.append("Exception decoding bitmap, outWidth: ");
        sb.append(outWidth);
        sb.append(", outHeight: ");
        sb.append(outHeight);
        sb.append(", outMimeType: ");
        sb.append(outMimeType);
        sb.append(", inBitmap: ");
        sb.append(getInBitmapString(options));
        return new IOException(sb.toString(), e);
    }

    @TargetApi(26)
    private static void setInBitmap(Options options, BitmapPool bitmapPool2, int width, int height) {
        Config expectedConfig = null;
        if (VERSION.SDK_INT >= 26) {
            if (options.inPreferredConfig != Config.HARDWARE) {
                expectedConfig = options.outConfig;
            } else {
                return;
            }
        }
        if (expectedConfig == null) {
            expectedConfig = options.inPreferredConfig;
        }
        options.inBitmap = bitmapPool2.getDirty(width, height, expectedConfig);
    }

    private static synchronized Options getDefaultOptions() {
        Options decodeBitmapOptions;
        synchronized (Downsampler.class) {
            synchronized (OPTIONS_QUEUE) {
                decodeBitmapOptions = (Options) OPTIONS_QUEUE.poll();
            }
            if (decodeBitmapOptions == null) {
                decodeBitmapOptions = new Options();
                resetOptions(decodeBitmapOptions);
            }
        }
        return decodeBitmapOptions;
    }

    private static void releaseOptions(Options decodeBitmapOptions) {
        resetOptions(decodeBitmapOptions);
        synchronized (OPTIONS_QUEUE) {
            OPTIONS_QUEUE.offer(decodeBitmapOptions);
        }
    }

    private static void resetOptions(Options decodeBitmapOptions) {
        decodeBitmapOptions.inTempStorage = null;
        decodeBitmapOptions.inDither = false;
        decodeBitmapOptions.inScaled = false;
        decodeBitmapOptions.inSampleSize = 1;
        decodeBitmapOptions.inPreferredConfig = null;
        decodeBitmapOptions.inJustDecodeBounds = false;
        decodeBitmapOptions.inDensity = 0;
        decodeBitmapOptions.inTargetDensity = 0;
        decodeBitmapOptions.outWidth = 0;
        decodeBitmapOptions.outHeight = 0;
        decodeBitmapOptions.outMimeType = null;
        decodeBitmapOptions.inBitmap = null;
        decodeBitmapOptions.inMutable = true;
    }
}
