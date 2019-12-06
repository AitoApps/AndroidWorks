package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.Key;
import java.security.MessageDigest;

final class DataCacheKey implements Key {
    private final Key signature;
    private final Key sourceKey;

    DataCacheKey(Key sourceKey2, Key signature2) {
        this.sourceKey = sourceKey2;
        this.signature = signature2;
    }

    /* access modifiers changed from: 0000 */
    public Key getSourceKey() {
        return this.sourceKey;
    }

    public boolean equals(Object o) {
        boolean z = false;
        if (!(o instanceof DataCacheKey)) {
            return false;
        }
        DataCacheKey other = (DataCacheKey) o;
        if (this.sourceKey.equals(other.sourceKey) && this.signature.equals(other.signature)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return (this.sourceKey.hashCode() * 31) + this.signature.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DataCacheKey{sourceKey=");
        sb.append(this.sourceKey);
        sb.append(", signature=");
        sb.append(this.signature);
        sb.append('}');
        return sb.toString();
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.sourceKey.updateDiskCacheKey(messageDigest);
        this.signature.updateDiskCacheKey(messageDigest);
    }
}
