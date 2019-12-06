package com.android.volley;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface Cache {

    public static class Entry {
        public List<Header> allResponseHeaders;

        /* renamed from: data reason: collision with root package name */
        public byte[] f2data;
        public String etag;
        public long lastModified;
        public Map<String, String> responseHeaders = Collections.emptyMap();
        public long serverDate;
        public long softTtl;
        public long ttl;

        public boolean isExpired() {
            return this.ttl < System.currentTimeMillis();
        }

        public boolean refreshNeeded() {
            return this.softTtl < System.currentTimeMillis();
        }
    }

    void clear();

    Entry get(String str);

    void initialize();

    void invalidate(String str, boolean z);

    void put(String str, Entry entry);

    void remove(String str);
}
