package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache.Entry;
import com.android.volley.ClientError;
import com.android.volley.Header;
import com.android.volley.Network;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.payumoney.core.PayUmoneyConstants;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class BasicNetwork implements Network {
    protected static final boolean DEBUG = VolleyLog.DEBUG;
    private static final int DEFAULT_POOL_SIZE = 4096;
    private static final int SLOW_REQUEST_THRESHOLD_MS = 3000;
    private final BaseHttpStack mBaseHttpStack;
    @Deprecated
    protected final HttpStack mHttpStack;
    protected final ByteArrayPool mPool;

    @Deprecated
    public BasicNetwork(HttpStack httpStack) {
        this(httpStack, new ByteArrayPool(4096));
    }

    @Deprecated
    public BasicNetwork(HttpStack httpStack, ByteArrayPool pool) {
        this.mHttpStack = httpStack;
        this.mBaseHttpStack = new AdaptedHttpStack(httpStack);
        this.mPool = pool;
    }

    public BasicNetwork(BaseHttpStack httpStack) {
        this(httpStack, new ByteArrayPool(4096));
    }

    public BasicNetwork(BaseHttpStack httpStack, ByteArrayPool pool) {
        this.mBaseHttpStack = httpStack;
        this.mHttpStack = httpStack;
        this.mPool = pool;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00bf, code lost:
        throw new java.io.IOException();
     */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x0179 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00f6  */
    public NetworkResponse performRequest(Request<?> request) throws VolleyError {
        HttpResponse httpResponse;
        NetworkResponse networkResponse;
        InputStream inputStream;
        byte[] responseContents;
        Request<?> request2 = request;
        long requestStart = SystemClock.elapsedRealtime();
        while (true) {
            byte[] responseContents2 = null;
            List emptyList = Collections.emptyList();
            try {
                httpResponse = this.mBaseHttpStack.executeRequest(request2, getCacheHeaders(request.getCacheEntry()));
                try {
                    int statusCode = httpResponse.getStatusCode();
                    List headers = httpResponse.getHeaders();
                    if (statusCode == 304) {
                        try {
                            Entry entry = request.getCacheEntry();
                            if (entry == null) {
                                NetworkResponse networkResponse2 = new NetworkResponse(304, (byte[]) null, true, SystemClock.elapsedRealtime() - requestStart, headers);
                                return networkResponse2;
                            }
                            List<Header> combinedHeaders = combineHeaders(headers, entry);
                            NetworkResponse networkResponse3 = new NetworkResponse(304, entry.f2data, true, SystemClock.elapsedRealtime() - requestStart, combinedHeaders);
                            return networkResponse3;
                        } catch (SocketTimeoutException e) {
                            List list = headers;
                            attemptRetryOnException("socket", request2, new TimeoutError());
                        } catch (MalformedURLException e2) {
                            e = e2;
                            List list2 = headers;
                            StringBuilder sb = new StringBuilder();
                            sb.append("Bad URL ");
                            sb.append(request.getUrl());
                            throw new RuntimeException(sb.toString(), e);
                        } catch (IOException e3) {
                            e = e3;
                            emptyList = headers;
                            if (httpResponse == null) {
                            }
                        }
                    } else {
                        try {
                            inputStream = httpResponse.getContent();
                            if (inputStream != null) {
                                responseContents = inputStreamToBytes(inputStream, httpResponse.getContentLength());
                            } else {
                                responseContents = new byte[0];
                            }
                        } catch (SocketTimeoutException e4) {
                            List list3 = headers;
                            attemptRetryOnException("socket", request2, new TimeoutError());
                        } catch (MalformedURLException e5) {
                            e = e5;
                            List list4 = headers;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Bad URL ");
                            sb2.append(request.getUrl());
                            throw new RuntimeException(sb2.toString(), e);
                        } catch (IOException e6) {
                            e = e6;
                            emptyList = headers;
                            if (httpResponse == null) {
                            }
                        }
                        try {
                            logSlowRequests(SystemClock.elapsedRealtime() - requestStart, request, responseContents, statusCode);
                            if (statusCode < 200 || statusCode > 299) {
                                List list5 = headers;
                                int i = statusCode;
                            } else {
                                InputStream inputStream2 = inputStream;
                                r13 = r13;
                                emptyList = headers;
                                int i2 = statusCode;
                                try {
                                    NetworkResponse networkResponse4 = new NetworkResponse(statusCode, responseContents, false, SystemClock.elapsedRealtime() - requestStart, emptyList);
                                    return networkResponse4;
                                } catch (SocketTimeoutException e7) {
                                    byte[] bArr = responseContents;
                                    attemptRetryOnException("socket", request2, new TimeoutError());
                                } catch (MalformedURLException e8) {
                                    e = e8;
                                    byte[] bArr2 = responseContents;
                                    StringBuilder sb22 = new StringBuilder();
                                    sb22.append("Bad URL ");
                                    sb22.append(request.getUrl());
                                    throw new RuntimeException(sb22.toString(), e);
                                } catch (IOException e9) {
                                    e = e9;
                                    responseContents2 = responseContents;
                                    if (httpResponse == null) {
                                    }
                                }
                            }
                        } catch (SocketTimeoutException e10) {
                            List list6 = headers;
                            byte[] bArr3 = responseContents;
                            attemptRetryOnException("socket", request2, new TimeoutError());
                        } catch (MalformedURLException e11) {
                            e = e11;
                            List list7 = headers;
                            byte[] bArr4 = responseContents;
                            StringBuilder sb222 = new StringBuilder();
                            sb222.append("Bad URL ");
                            sb222.append(request.getUrl());
                            throw new RuntimeException(sb222.toString(), e);
                        } catch (IOException e12) {
                            e = e12;
                            emptyList = headers;
                            responseContents2 = responseContents;
                            if (httpResponse == null) {
                            }
                        }
                    }
                } catch (SocketTimeoutException e13) {
                    attemptRetryOnException("socket", request2, new TimeoutError());
                } catch (MalformedURLException e14) {
                    e = e14;
                    StringBuilder sb2222 = new StringBuilder();
                    sb2222.append("Bad URL ");
                    sb2222.append(request.getUrl());
                    throw new RuntimeException(sb2222.toString(), e);
                } catch (IOException e15) {
                    e = e15;
                    if (httpResponse == null) {
                        int statusCode2 = httpResponse.getStatusCode();
                        VolleyLog.e("Unexpected response code %d for %s", Integer.valueOf(statusCode2), request.getUrl());
                        if (responseContents2 != null) {
                            networkResponse = new NetworkResponse(statusCode2, responseContents2, false, SystemClock.elapsedRealtime() - requestStart, emptyList);
                            if (statusCode2 == 401 || statusCode2 == 403) {
                                attemptRetryOnException("auth", request2, new AuthFailureError(networkResponse));
                            } else if (statusCode2 >= 400 && statusCode2 <= 499) {
                                throw new ClientError(networkResponse);
                            } else if (statusCode2 < 500 || statusCode2 > 599) {
                                throw new ServerError(networkResponse);
                            } else if (request.shouldRetryServerErrors()) {
                                attemptRetryOnException("server", request2, new ServerError(networkResponse));
                            } else {
                                throw new ServerError(networkResponse);
                            }
                        } else {
                            attemptRetryOnException("network", request2, new NetworkError());
                        }
                    } else {
                        throw new NoConnectionError(e);
                    }
                }
            } catch (SocketTimeoutException e16) {
                attemptRetryOnException("socket", request2, new TimeoutError());
            } catch (MalformedURLException e17) {
                e = e17;
                StringBuilder sb22222 = new StringBuilder();
                sb22222.append("Bad URL ");
                sb22222.append(request.getUrl());
                throw new RuntimeException(sb22222.toString(), e);
            } catch (IOException e18) {
                e = e18;
                httpResponse = null;
                if (httpResponse == null) {
                }
            }
        }
        throw new ServerError(networkResponse);
    }

    private void logSlowRequests(long requestLifetime, Request<?> request, byte[] responseContents, int statusCode) {
        if (DEBUG || requestLifetime > 3000) {
            String str = "HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]";
            Object[] objArr = new Object[5];
            objArr[0] = request;
            objArr[1] = Long.valueOf(requestLifetime);
            objArr[2] = responseContents != null ? Integer.valueOf(responseContents.length) : PayUmoneyConstants.NULL_STRING;
            objArr[3] = Integer.valueOf(statusCode);
            objArr[4] = Integer.valueOf(request.getRetryPolicy().getCurrentRetryCount());
            VolleyLog.d(str, objArr);
        }
    }

    private static void attemptRetryOnException(String logPrefix, Request<?> request, VolleyError exception) throws VolleyError {
        RetryPolicy retryPolicy = request.getRetryPolicy();
        int oldTimeout = request.getTimeoutMs();
        try {
            retryPolicy.retry(exception);
            request.addMarker(String.format("%s-retry [timeout=%s]", new Object[]{logPrefix, Integer.valueOf(oldTimeout)}));
        } catch (VolleyError e) {
            request.addMarker(String.format("%s-timeout-giveup [timeout=%s]", new Object[]{logPrefix, Integer.valueOf(oldTimeout)}));
            throw e;
        }
    }

    private Map<String, String> getCacheHeaders(Entry entry) {
        if (entry == null) {
            return Collections.emptyMap();
        }
        Map<String, String> headers = new HashMap<>();
        if (entry.etag != null) {
            headers.put("If-None-Match", entry.etag);
        }
        if (entry.lastModified > 0) {
            headers.put("If-Modified-Since", HttpHeaderParser.formatEpochAsRfc1123(entry.lastModified));
        }
        return headers;
    }

    /* access modifiers changed from: protected */
    public void logError(String what, String url, long start) {
        VolleyLog.v("HTTP ERROR(%s) %d ms to fetch %s", what, Long.valueOf(SystemClock.elapsedRealtime() - start), url);
    }

    private byte[] inputStreamToBytes(InputStream in, int contentLength) throws IOException, ServerError {
        PoolingByteArrayOutputStream bytes = new PoolingByteArrayOutputStream(this.mPool, contentLength);
        byte[] buffer = null;
        if (in != null) {
            try {
                buffer = this.mPool.getBuf(1024);
                while (true) {
                    int read = in.read(buffer);
                    int count = read;
                    if (read == -1) {
                        break;
                    }
                    bytes.write(buffer, 0, count);
                }
                byte[] byteArray = bytes.toByteArray();
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        VolleyLog.v("Error occurred when closing InputStream", new Object[0]);
                    }
                }
                return byteArray;
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e2) {
                        VolleyLog.v("Error occurred when closing InputStream", new Object[0]);
                    }
                }
                this.mPool.returnBuf(buffer);
                bytes.close();
            }
        } else {
            throw new ServerError();
        }
    }

    @Deprecated
    protected static Map<String, String> convertHeaders(Header[] headers) {
        Map<String, String> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < headers.length; i++) {
            result.put(headers[i].getName(), headers[i].getValue());
        }
        return result;
    }

    private static List<Header> combineHeaders(List<Header> responseHeaders, Entry entry) {
        Set<String> headerNamesFromNetworkResponse = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        if (!responseHeaders.isEmpty()) {
            for (Header header : responseHeaders) {
                headerNamesFromNetworkResponse.add(header.getName());
            }
        }
        List<Header> combinedHeaders = new ArrayList<>(responseHeaders);
        if (entry.allResponseHeaders != null) {
            if (!entry.allResponseHeaders.isEmpty()) {
                for (Header header2 : entry.allResponseHeaders) {
                    if (!headerNamesFromNetworkResponse.contains(header2.getName())) {
                        combinedHeaders.add(header2);
                    }
                }
            }
        } else if (!entry.responseHeaders.isEmpty()) {
            for (Map.Entry<String, String> header3 : entry.responseHeaders.entrySet()) {
                if (!headerNamesFromNetworkResponse.contains(header3.getKey())) {
                    combinedHeaders.add(new Header((String) header3.getKey(), (String) header3.getValue()));
                }
            }
        }
        return combinedHeaders;
    }
}
