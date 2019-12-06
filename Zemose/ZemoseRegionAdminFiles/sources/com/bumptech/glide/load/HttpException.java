package com.bumptech.glide.load;

import android.support.annotation.Nullable;
import java.io.IOException;

public final class HttpException extends IOException {
    public static final int UNKNOWN = -1;
    private static final long serialVersionUID = 1;
    private final int statusCode;

    public HttpException(int statusCode2) {
        StringBuilder sb = new StringBuilder();
        sb.append("Http request failed with status code: ");
        sb.append(statusCode2);
        this(sb.toString(), statusCode2);
    }

    public HttpException(String message) {
        this(message, -1);
    }

    public HttpException(String message, int statusCode2) {
        this(message, statusCode2, null);
    }

    public HttpException(String message, int statusCode2, @Nullable Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode2;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
