package com.sanji_admin;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;

public class AndroidMultiPartEntity extends MultipartEntity {
    private final ProgressListener listener;

    public static class CountingOutputStream extends FilterOutputStream {
        private final ProgressListener listener;
        private long transferred = 0;

        public CountingOutputStream(OutputStream out, ProgressListener listener2) {
            super(out);
            listener = listener2;
        }

        public void write(byte[] b, int off, int len) throws IOException {
            out.write(b, off, len);
            transferred += (long) len;
            listener.transferred(transferred);
        }

        public void write(int b) throws IOException {
            out.write(b);
            transferred++;
            listener.transferred(transferred);
        }
    }

    public interface ProgressListener {
        void transferred(long j);
    }

    public AndroidMultiPartEntity(ProgressListener listener2) {
        listener = listener2;
    }

    public AndroidMultiPartEntity(HttpMultipartMode mode, ProgressListener listener2) {
        super(mode);
        listener = listener2;
    }

    public AndroidMultiPartEntity(HttpMultipartMode mode, String boundary, Charset charset, ProgressListener listener2) {
        super(mode, boundary, charset);
        listener = listener2;
    }

    public void writeTo(OutputStream outstream) throws IOException {
        super.writeTo(new CountingOutputStream(outstream, listener));
    }
}
