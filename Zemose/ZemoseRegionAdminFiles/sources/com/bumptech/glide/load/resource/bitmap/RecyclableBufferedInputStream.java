package com.bumptech.glide.load.resource.bitmap;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RecyclableBufferedInputStream extends FilterInputStream {
    private volatile byte[] buf;
    private final ArrayPool byteArrayPool;
    private int count;
    private int marklimit;
    private int markpos;
    private int pos;

    static class InvalidMarkException extends IOException {
        private static final long serialVersionUID = -4338378848813561757L;

        InvalidMarkException(String detailMessage) {
            super(detailMessage);
        }
    }

    public RecyclableBufferedInputStream(@NonNull InputStream in, @NonNull ArrayPool byteArrayPool2) {
        this(in, byteArrayPool2, 65536);
    }

    @VisibleForTesting
    RecyclableBufferedInputStream(@NonNull InputStream in, @NonNull ArrayPool byteArrayPool2, int bufferSize) {
        super(in);
        this.markpos = -1;
        this.byteArrayPool = byteArrayPool2;
        this.buf = (byte[]) byteArrayPool2.get(bufferSize, byte[].class);
    }

    public synchronized int available() throws IOException {
        InputStream localIn;
        localIn = this.in;
        if (this.buf == null || localIn == null) {
            throw streamClosed();
        }
        return (this.count - this.pos) + localIn.available();
    }

    private static IOException streamClosed() throws IOException {
        throw new IOException("BufferedInputStream is closed");
    }

    public synchronized void fixMarkLimit() {
        this.marklimit = this.buf.length;
    }

    public synchronized void release() {
        if (this.buf != null) {
            this.byteArrayPool.put(this.buf);
            this.buf = null;
        }
    }

    public void close() throws IOException {
        if (this.buf != null) {
            this.byteArrayPool.put(this.buf);
            this.buf = null;
        }
        InputStream localIn = this.in;
        this.in = null;
        if (localIn != null) {
            localIn.close();
        }
    }

    private int fillbuf(InputStream localIn, byte[] localBuf) throws IOException {
        int i = this.markpos;
        if (i != -1) {
            int i2 = this.pos - i;
            int i3 = this.marklimit;
            if (i2 < i3) {
                if (i == 0 && i3 > localBuf.length && this.count == localBuf.length) {
                    int newLength = localBuf.length * 2;
                    if (newLength > i3) {
                        newLength = this.marklimit;
                    }
                    byte[] newbuf = (byte[]) this.byteArrayPool.get(newLength, byte[].class);
                    System.arraycopy(localBuf, 0, newbuf, 0, localBuf.length);
                    byte[] oldbuf = localBuf;
                    this.buf = newbuf;
                    localBuf = newbuf;
                    this.byteArrayPool.put(oldbuf);
                } else {
                    int i4 = this.markpos;
                    if (i4 > 0) {
                        System.arraycopy(localBuf, i4, localBuf, 0, localBuf.length - i4);
                    }
                }
                this.pos -= this.markpos;
                this.markpos = 0;
                this.count = 0;
                int i5 = this.pos;
                int bytesread = localIn.read(localBuf, i5, localBuf.length - i5);
                this.count = bytesread <= 0 ? this.pos : this.pos + bytesread;
                return bytesread;
            }
        }
        int result = localIn.read(localBuf);
        if (result > 0) {
            this.markpos = -1;
            this.pos = 0;
            this.count = result;
        }
        return result;
    }

    public synchronized void mark(int readlimit) {
        this.marklimit = Math.max(this.marklimit, readlimit);
        this.markpos = this.pos;
    }

    public boolean markSupported() {
        return true;
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:11:0x0019=Splitter:B:11:0x0019, B:27:0x003f=Splitter:B:27:0x003f} */
    public synchronized int read() throws IOException {
        byte[] localBuf = this.buf;
        InputStream localIn = this.in;
        if (localBuf == null || localIn == null) {
            throw streamClosed();
        } else if (this.pos >= this.count && fillbuf(localIn, localBuf) == -1) {
            return -1;
        } else {
            if (localBuf != this.buf) {
                localBuf = this.buf;
                if (localBuf == null) {
                    throw streamClosed();
                }
            }
            if (this.count - this.pos <= 0) {
                return -1;
            }
            int i = this.pos;
            this.pos = i + 1;
            return localBuf[i] & 255;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003b, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x005d, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0073, code lost:
        return r4;
     */
    public synchronized int read(@NonNull byte[] buffer, int offset, int byteCount) throws IOException {
        int copylength;
        int read;
        byte[] localBuf = this.buf;
        if (localBuf == null) {
            throw streamClosed();
        } else if (byteCount == 0) {
            return 0;
        } else {
            InputStream localIn = this.in;
            if (localIn != null) {
                if (this.pos < this.count) {
                    int copylength2 = this.count - this.pos >= byteCount ? byteCount : this.count - this.pos;
                    System.arraycopy(localBuf, this.pos, buffer, offset, copylength2);
                    this.pos += copylength2;
                    if (copylength2 != byteCount && localIn.available() != 0) {
                        offset += copylength2;
                        copylength = byteCount - copylength2;
                    }
                } else {
                    copylength = byteCount;
                }
                while (true) {
                    int i = -1;
                    if (this.markpos == -1 && copylength >= localBuf.length) {
                        read = localIn.read(buffer, offset, copylength);
                        if (read == -1) {
                            if (copylength != byteCount) {
                                i = byteCount - copylength;
                            }
                        }
                    } else if (fillbuf(localIn, localBuf) != -1) {
                        if (localBuf != this.buf) {
                            localBuf = this.buf;
                            if (localBuf == null) {
                                throw streamClosed();
                            }
                        }
                        read = this.count - this.pos >= copylength ? copylength : this.count - this.pos;
                        System.arraycopy(localBuf, this.pos, buffer, offset, read);
                        this.pos += read;
                    } else if (copylength != byteCount) {
                        i = byteCount - copylength;
                    }
                    copylength -= read;
                    if (copylength == 0) {
                        return byteCount;
                    }
                    if (localIn.available() == 0) {
                        return byteCount - copylength;
                    }
                    offset += read;
                }
            } else {
                throw streamClosed();
            }
        }
    }

    public synchronized void reset() throws IOException {
        if (this.buf == null) {
            throw new IOException("Stream is closed");
        } else if (-1 != this.markpos) {
            this.pos = this.markpos;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Mark has been invalidated, pos: ");
            sb.append(this.pos);
            sb.append(" markLimit: ");
            sb.append(this.marklimit);
            throw new InvalidMarkException(sb.toString());
        }
    }

    public synchronized long skip(long byteCount) throws IOException {
        if (byteCount < 1) {
            return 0;
        }
        byte[] localBuf = this.buf;
        if (localBuf != null) {
            InputStream localIn = this.in;
            if (localIn == null) {
                throw streamClosed();
            } else if (((long) (this.count - this.pos)) >= byteCount) {
                this.pos = (int) (((long) this.pos) + byteCount);
                return byteCount;
            } else {
                long read = ((long) this.count) - ((long) this.pos);
                this.pos = this.count;
                if (this.markpos == -1 || byteCount > ((long) this.marklimit)) {
                    return localIn.skip(byteCount - read) + read;
                } else if (fillbuf(localIn, localBuf) == -1) {
                    return read;
                } else {
                    if (((long) (this.count - this.pos)) >= byteCount - read) {
                        this.pos = (int) ((((long) this.pos) + byteCount) - read);
                        return byteCount;
                    }
                    long read2 = (((long) this.count) + read) - ((long) this.pos);
                    this.pos = this.count;
                    return read2;
                }
            }
        } else {
            throw streamClosed();
        }
    }
}
