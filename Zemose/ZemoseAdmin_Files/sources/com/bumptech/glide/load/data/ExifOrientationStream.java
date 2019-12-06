package com.bumptech.glide.load.data;

import android.support.annotation.NonNull;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ExifOrientationStream extends FilterInputStream {
    private static final byte[] EXIF_SEGMENT = {-1, -31, 0, 28, 69, 120, 105, 102, 0, 0, 77, 77, 0, 0, 0, 0, 0, 8, 0, 1, 1, 18, 0, 2, 0, 0, 0, 1, 0};
    private static final int ORIENTATION_POSITION = (SEGMENT_LENGTH + 2);
    private static final int SEGMENT_LENGTH = EXIF_SEGMENT.length;
    private static final int SEGMENT_START_POSITION = 2;
    private final byte orientation;
    private int position;

    public ExifOrientationStream(InputStream in, int orientation2) {
        super(in);
        if (orientation2 < -1 || orientation2 > 8) {
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot add invalid orientation: ");
            sb.append(orientation2);
            throw new IllegalArgumentException(sb.toString());
        }
        this.orientation = (byte) orientation2;
    }

    public boolean markSupported() {
        return false;
    }

    public void mark(int readLimit) {
        throw new UnsupportedOperationException();
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001f  */
    public int read() throws IOException {
        int result;
        int i = this.position;
        if (i >= 2) {
            int i2 = ORIENTATION_POSITION;
            if (i <= i2) {
                if (i == i2) {
                    result = this.orientation;
                } else {
                    result = EXIF_SEGMENT[i - 2] & 255;
                }
                if (result != -1) {
                    this.position++;
                }
                return result;
            }
        }
        result = super.read();
        if (result != -1) {
        }
        return result;
    }

    public int read(@NonNull byte[] buffer, int byteOffset, int byteCount) throws IOException {
        int read;
        int i = this.position;
        int i2 = ORIENTATION_POSITION;
        if (i > i2) {
            read = super.read(buffer, byteOffset, byteCount);
        } else if (i == i2) {
            buffer[byteOffset] = this.orientation;
            read = 1;
        } else if (i < 2) {
            read = super.read(buffer, byteOffset, 2 - i);
        } else {
            read = Math.min(i2 - i, byteCount);
            System.arraycopy(EXIF_SEGMENT, this.position - 2, buffer, byteOffset, read);
        }
        if (read > 0) {
            this.position += read;
        }
        return read;
    }

    public long skip(long byteCount) throws IOException {
        long skipped = super.skip(byteCount);
        if (skipped > 0) {
            this.position = (int) (((long) this.position) + skipped);
        }
        return skipped;
    }

    public void reset() throws IOException {
        throw new UnsupportedOperationException();
    }
}
