package com.payumoney.core;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BaseHttpStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.load.Key;
import com.payu.custombrowser.util.CBConstant;
import com.payumoney.core.analytics.LogAnalytics;
import com.payumoney.core.entity.EmiThreshold;
import com.payumoney.core.listener.OnCardBinDetailsReceived;
import com.payumoney.core.listener.OnEmiInterestReceivedListener;
import com.payumoney.core.listener.OnFetchUserDetailsForNitroFlowListener;
import com.payumoney.core.listener.OnMerchantLoginParams;
import com.payumoney.core.listener.OnMultipleCardBinDetailsListener;
import com.payumoney.core.listener.OnNetBankingStatusListReceivedListener;
import com.payumoney.core.listener.OnOTPRequestSendListener;
import com.payumoney.core.listener.OnPaymentDetailsReceivedFromPayuMoney;
import com.payumoney.core.listener.OnPaymentOptionReceivedListener;
import com.payumoney.core.listener.OnUserLoginListener;
import com.payumoney.core.listener.OnValidateVpaListener;
import com.payumoney.core.listener.OnVerifyPaymentResponse;
import com.payumoney.core.listener.PayULoginDialogListener;
import com.payumoney.core.listener.onUserAccountReceivedListener;
import com.payumoney.core.request.PaymentRequest;
import com.payumoney.core.response.BinDetail;
import com.payumoney.core.response.ErrorResponse;
import com.payumoney.core.response.MerchantLoginResponse;
import com.payumoney.core.response.NetBankingStatusResponse;
import com.payumoney.core.response.PayUMoneyAPIResponse;
import com.payumoney.core.response.PayUMoneyLoginResponse;
import com.payumoney.core.response.PaymentOptionDetails;
import com.payumoney.core.response.UserDetail;
import com.payumoney.core.tls.HurlStackFactory;
import com.payumoney.core.utils.AnalyticsConstant;
import com.payumoney.core.utils.PayUMoneyCustomException;
import com.payumoney.core.utils.PayUmoneyTransactionDetails;
import com.payumoney.core.utils.ResponseParser;
import com.payumoney.core.utils.SdkHelper;
import com.payumoney.core.utils.SdkLogger;
import com.payumoney.core.utils.SharedPrefsUtils;
import com.payumoney.core.utils.SharedPrefsUtils.Keys;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

public class SdkSession {
    public static final String TAG = SdkSession.class.getSimpleName();
    static final Map<PaymentMode, String> a = new HashMap();
    private static SdkSession e = null;
    /* access modifiers changed from: private */
    public static String f;
    public static String paymentId = null;
    Long b;
    Long c;
    Long d;
    private boolean g;
    private boolean h;
    private boolean i;
    private String j;
    private boolean k;
    private final SessionData l;
    /* access modifiers changed from: private */
    public final Context m;
    private final Handler n;
    /* access modifiers changed from: private */
    public boolean o;
    private RequestQueue p;
    private String q;
    private String r;
    /* access modifiers changed from: private */
    public ResponseParser s;

    /* renamed from: com.payumoney.core.SdkSession$13 reason: invalid class name */
    class AnonymousClass13 implements Runnable {
        final /* synthetic */ Task a;
        final /* synthetic */ String b;

        public void run() {
            this.a.onSuccess(this.b);
        }
    }

    public enum Method {
        POST,
        GET,
        DELETE
    }

    public enum PaymentMode {
        CC,
        DC,
        NB,
        EMI,
        PAYU_MONEY,
        STORED_CARDS,
        CASH
    }

    private class SessionData {
        private String b = null;
        private String c = "0";

        public SessionData() {
            reset();
        }

        public void setrevisedCashbackReceivedStatus(String s) {
            this.c = s;
        }

        public String getToken() {
            return this.b;
        }

        public void setToken(String token) {
            this.b = token;
        }

        public void reset() {
            this.b = null;
        }
    }

    public interface Task {
        void onError(Throwable th);

        void onProgress(int i);

        void onSuccess(String str);

        void onSuccess(JSONObject jSONObject);
    }

    static {
        a.put(PaymentMode.CC, "Credit CardDetail");
        a.put(PaymentMode.DC, "Debit CardDetail");
        a.put(PaymentMode.NB, "Net Banking");
        a.put(PaymentMode.EMI, "EMI");
        a.put(PaymentMode.PAYU_MONEY, PayUmoneyConstants.SP_SP_NAME);
        a.put(PaymentMode.STORED_CARDS, "Stored Cards");
        a.put(PaymentMode.CASH, "Cash CardDetail");
    }

    private SdkSession(Context context) {
        this.g = false;
        this.h = false;
        this.i = false;
        this.j = null;
        this.k = false;
        this.l = new SessionData();
        this.b = null;
        this.c = null;
        this.d = null;
        this.o = false;
        this.q = "";
        this.r = "";
        this.s = null;
        this.s = new ResponseParser();
        this.n = new Handler(Looper.getMainLooper());
        this.m = context;
        f = null;
        this.o = false;
        String stringPreference = SharedPrefsUtils.getStringPreference(this.m, "access_token");
        if (stringPreference != null) {
            this.l.setToken(stringPreference);
        }
    }

    public void setNitroEnabled(boolean isEnabled) {
        this.g = isEnabled;
    }

    public boolean isNitroEnabled() {
        return this.g;
    }

    public void setUserAccountActive(boolean isActive) {
        this.h = isActive;
    }

    public boolean isUserAccountActive() {
        return this.h;
    }

    public boolean isUserSignUpDisabled() {
        return this.i;
    }

    public void setUserSignUpDisabled(boolean userSignUpDisabled) {
        this.i = userSignUpDisabled;
    }

    public String getRegisteredUserName() {
        return this.j;
    }

    public void setRegisteredUserName(String registeredUserName) {
        this.j = registeredUserName;
    }

    public boolean isMobileNumberRegistered() {
        return this.k;
    }

    public void setMobileNumberRegistered(boolean mobileNumberRegistered) {
        this.k = mobileNumberRegistered;
    }

    private static String a(String str) {
        if (str.equals("/payuPaisa/up.php")) {
            StringBuilder sb = new StringBuilder();
            sb.append(PayUmoneySdkInitializer.getBaseUrl());
            sb.append(str);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(PayUmoneySdkInitializer.getBaseUrl());
        sb2.append(str);
        return sb2.toString();
    }

    public static synchronized SdkSession getInstance(Context context) {
        SdkSession sdkSession;
        synchronized (SdkSession.class) {
            if (e == null) {
                e = new SdkSession(context);
            }
            sdkSession = e;
        }
        return sdkSession;
    }

    public static synchronized SdkSession getInstanceForService() {
        SdkSession sdkSession;
        synchronized (SdkSession.class) {
            sdkSession = e;
        }
        return sdkSession;
    }

    public static synchronized SdkSession createNewInstance(Context context) {
        SdkSession sdkSession;
        synchronized (SdkSession.class) {
            e = null;
            e = new SdkSession(context);
            sdkSession = e;
        }
        return sdkSession;
    }

    public String getLoginMode() {
        return this.q;
    }

    public void setLoginMode(String loginMode) {
        this.q = loginMode;
    }

    public String getGuestEmail() {
        return this.r;
    }

    public void setGuestEmail(String guestEmail) {
        this.r = guestEmail;
    }

    public RequestQueue getRequestQueue(Context context) {
        if (this.p == null) {
            this.p = Volley.newRequestQueue(context.getApplicationContext(), (BaseHttpStack) HurlStackFactory.getNewHurlStack());
        }
        return this.p;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        boolean isEmpty = TextUtils.isEmpty(TAG);
        req.setTag(TAG);
        getRequestQueue(this.m).add(req);
    }

    public void cancelPendingRequests(Object tag) {
        RequestQueue requestQueue = this.p;
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

    public void cancelPendingRequests() {
        this.p.cancelAll((RequestFilter) new RequestFilter() {
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    public void fetchMechantParams(String merchantId, final OnMerchantLoginParams listener, final String tag) {
        new HashMap().put("merchantId", merchantId);
        StringBuilder sb = new StringBuilder();
        sb.append("/auth/app/op/merchant/LoginParams?merchantId=");
        sb.append(merchantId);
        postFetch(sb.toString(), null, new Task() {
            public void onSuccess(JSONObject jsonObject) {
                try {
                    PayUMoneyAPIResponse parseFetchMerchant = SdkSession.this.s.parseFetchMerchant(jsonObject);
                    if (parseFetchMerchant instanceof MerchantLoginResponse) {
                        listener.onMerchantLoginParams((MerchantLoginResponse) parseFetchMerchant, tag);
                    } else {
                        listener.onFailureResponse((ErrorResponse) parseFetchMerchant, tag);
                    }
                } catch (PayUMoneyCustomException e) {
                    listener.onError(e.getMessage(), tag);
                }
            }

            public void onSuccess(String response) {
                listener.onError(response, tag);
            }

            public void onError(Throwable throwable) {
                listener.onError(throwable.getMessage(), tag);
            }

            public void onProgress(int percent) {
            }
        }, 0);
    }

    public void validateVPA(final OnValidateVpaListener listener, final String vpa, final String tag) {
        final HashMap hashMap = new HashMap();
        hashMap.put(PayUmoneyConstants.PM_KEY_VPA, vpa);
        String str = PayUmoneyConstants.PM_VALIDATE_VPA_URL;
        final HashMap hashMap2 = hashMap;
        final OnValidateVpaListener onValidateVpaListener = listener;
        final String str2 = tag;
        final String str3 = vpa;
        AnonymousClass3 r0 = new Listener<String>() {
            public void onResponse(String response) {
                String str = PayUmoneyConstants.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("SdkSession.validateVpa.onSuccess: url=");
                sb.append(PayUmoneyConstants.PM_VALIDATE_VPA_URL);
                sb.append(", params=");
                sb.append(hashMap2);
                sb.append(", method");
                sb.append(1);
                sb.append(", response=");
                sb.append(response);
                SdkLogger.d(str, sb.toString());
                boolean z = false;
                try {
                    if (Integer.parseInt(response) == 1) {
                        z = true;
                    }
                } catch (Exception e2) {
                    ErrorResponse errorResponse = new ErrorResponse();
                    errorResponse.setMessage(SdkSession.this.m.getResources().getString(R.string.error_unable_to_validate_vpa));
                    onValidateVpaListener.onFailureResponse(errorResponse, str2);
                }
                if (!z) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(AnalyticsConstant.IS_USER_LOGGED_IN, Boolean.valueOf(PayUmoneySDK.getInstance().isUserLoggedIn()));
                    hashMap.put(AnalyticsConstant.PAYMENTID, SdkSession.paymentId);
                    hashMap.put(AnalyticsConstant.VPA, str3);
                    LogAnalytics.logEvent(SdkSession.this.m, AnalyticsConstant.INVALID_VPA_ENTERED, hashMap, AnalyticsConstant.CLEVERTAP);
                }
                onValidateVpaListener.onSuccess(z, str2);
            }
        };
        AnonymousClass5 r02 = new StringRequest(1, str, r0, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                if (PayUmoneySdkInitializer.IsDebugMode().booleanValue() && error != null) {
                    String str = PayUmoneyConstants.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Session...new JsonHttpResponseHandler() {...}.onFailure: ");
                    sb.append(error.getMessage());
                    Log.e(str, sb.toString());
                }
                String string = SdkSession.this.m.getResources().getString(R.string.error_api_failed);
                if (!(error == null || error.networkResponse == null)) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(SdkSession.this.m.getResources().getString(R.string.error_api_failed));
                    sb2.append(" with status code ");
                    sb2.append(error.networkResponse.statusCode);
                    string = sb2.toString();
                }
                HashMap hashMap = new HashMap();
                hashMap.put(AnalyticsConstant.IS_USER_LOGGED_IN, Boolean.valueOf(PayUmoneySDK.getInstance().isUserLoggedIn()));
                hashMap.put(AnalyticsConstant.PAYMENTID, SdkSession.paymentId);
                hashMap.put(AnalyticsConstant.VPA, vpa);
                hashMap.put(AnalyticsConstant.ERROR_MESSAGE, string);
                LogAnalytics.logEvent(SdkSession.this.m, AnalyticsConstant.VPA_VALIDATION_API_FAILURE, hashMap, AnalyticsConstant.CLEVERTAP);
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage(SdkSession.this.m.getResources().getString(R.string.error_unable_to_validate_vpa));
                listener.onFailureResponse(errorResponse, tag);
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() throws AuthFailureError {
                return hashMap;
            }

            public String getBodyContentType() {
                return CBConstant.HTTP_URLENCODED;
            }
        };
        r02.setShouldCache(false);
        r02.setRetryPolicy(new DefaultRetryPolicy(CBConstant.HTTP_TIMEOUT, 1, 0.0f));
        addToRequestQueue(r02);
    }

    /* access modifiers changed from: private */
    public Map<String, String> b() {
        HashMap hashMap = new HashMap();
        hashMap.put(PayUmoneyConstants.USER_SESSION_COOKIE, SdkHelper.getUserSessionId(this.m));
        hashMap.put(PayUmoneyConstants.CUSTOM_BROWSER_PROPERTY, SdkHelper.getDeviceCustomPropertyJsonString(this.m));
        hashMap.put(PayUmoneyConstants.USER_SESSION_COOKIE_PAGE_URL, SharedPrefsUtils.getStringPreference(this.m, PayUmoneyConstants.USER_SESSION_COOKIE_PAGE_URL));
        if (SdkHelper.isUpdateSessionRequired(this.m)) {
            hashMap.put(PayUmoneyConstants.USER_SESSION_UPDATE, "1");
        }
        return hashMap;
    }

    public void fetchUserPaymentData(String paymentId2, final onUserAccountReceivedListener listener, final String tag) {
        HashMap hashMap = new HashMap();
        hashMap.put(PayUmoneyConstants.PAYMENT_ID, paymentId2);
        postFetch("/payment/app/fetchPaymentUserData", hashMap, new Task() {
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject != null) {
                    try {
                        listener.onSuccess(jsonObject.toString(), tag);
                    } catch (PayUMoneyCustomException e) {
                        listener.onError(e.getMessage(), tag);
                        return;
                    }
                }
                PayUMoneyAPIResponse parseUserAccountDetail = SdkSession.this.s.parseUserAccountDetail(jsonObject);
                if (parseUserAccountDetail instanceof UserDetail) {
                    listener.OnUserPaymentDetailsReceived((UserDetail) parseUserAccountDetail, tag);
                } else {
                    listener.onFailureResponse((ErrorResponse) parseUserAccountDetail, tag);
                }
            }

            public void onSuccess(String response) {
                listener.onError(response, tag);
            }

            public void onError(Throwable throwable) {
                listener.onError(throwable.getMessage(), tag);
            }

            public void onProgress(int percent) {
            }
        }, 1);
    }

    public void reset() {
        this.l.reset();
    }

    public void postFetch(String url, Map<String, String> params, Task task, int method) {
        postFetch(url, params, null, task, method);
    }

    public void postFetch(String url, Map<String, String> params, Map<String, String> customHeader, Task task, int method) {
        if (PayUmoneySdkInitializer.IsDebugMode().booleanValue()) {
            String str = PayUmoneyConstants.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("SdkSession.postFetch: ");
            String str2 = url;
            sb.append(url);
            sb.append(" ");
            sb.append(params);
            sb.append(" ");
            sb.append(method);
            SdkLogger.d(str, sb.toString());
        } else {
            String str3 = url;
            Map<String, String> map = params;
            int i2 = method;
        }
        String a2 = a(url);
        final String str4 = url;
        final Map<String, String> map2 = params;
        final int i3 = method;
        final Task task2 = task;
        AnonymousClass7 r0 = new Listener<String>() {
            public void onResponse(String response) {
                SdkSession.this.d = Long.valueOf(System.currentTimeMillis() - SdkSession.this.b.longValue());
                StringBuilder sb = new StringBuilder();
                sb.append("URL=");
                sb.append(str4);
                sb.append("Time=");
                sb.append(SdkSession.this.d);
                SdkLogger.i("Difference ", sb.toString());
                String str = PayUmoneyConstants.TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("SdkSession.postFetch.onSuccess: ");
                sb2.append(str4);
                sb2.append(" ");
                sb2.append(map2);
                sb2.append(" ");
                sb2.append(i3);
                sb2.append(": ");
                sb2.append(response);
                SdkLogger.d(str, sb2.toString());
                try {
                    JSONObject jSONObject = new JSONObject(response);
                    if (!jSONObject.has(AnalyticsConstant.ERROR) || str4.contains("/payment/postBackParamIcp.do")) {
                        SdkSession.this.a(task2, jSONObject);
                        return;
                    }
                    onFailure(jSONObject.getString(AnalyticsConstant.ERROR), new Throwable(jSONObject.getString(AnalyticsConstant.ERROR)));
                    SdkSession.this.logout("force");
                } catch (JSONException e2) {
                    onFailure(e2.getMessage(), e2);
                }
            }

            public void onFailure(String msg, Throwable e2) {
                if (PayUmoneySdkInitializer.IsDebugMode().booleanValue()) {
                    String str = PayUmoneyConstants.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Session...new JsonHttpResponseHandler() {...}.onFailure: ");
                    sb.append(e2.getMessage());
                    sb.append(" ");
                    sb.append(msg);
                    Log.e(str, sb.toString());
                }
                if (msg.contains("401")) {
                    if (!PayUmoneyConstants.WALLET_SDK.booleanValue()) {
                        SdkSession.this.logout("force");
                        SdkSession.this.cancelPendingRequests(SdkSession.TAG);
                    } else if (!SdkSession.this.o) {
                        SdkSession.this.logout("force");
                    } else {
                        SdkSession.this.o = false;
                    }
                }
                SdkSession.this.a(task2, e2);
            }
        };
        final Task task3 = task;
        final Map<String, String> map3 = params;
        final Map<String, String> map4 = customHeader;
        AnonymousClass9 r02 = new StringRequest(method, a2, r0, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                if (PayUmoneySdkInitializer.IsDebugMode().booleanValue()) {
                    String str = PayUmoneyConstants.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Session...new JsonHttpResponseHandler() {...}.onFailure: ");
                    sb.append(error.getMessage());
                    Log.e(str, sb.toString());
                }
                if (!(error == null || error.networkResponse == null || error.networkResponse.statusCode != 401)) {
                    if (!PayUmoneyConstants.WALLET_SDK.booleanValue()) {
                        SdkSession.this.logout("force");
                    } else if (!SdkSession.this.o) {
                        SdkSession.this.logout("force");
                    } else {
                        SdkSession.this.o = false;
                    }
                }
                SdkSession.this.a(task3, (Throwable) error);
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                if (PayUmoneyConstants.WALLET_SDK.booleanValue()) {
                    map3.put(PayUmoneyConstants.CLIENT_ID, SdkSession.f);
                    map3.put(PayUmoneyConstants.IS_MOBILE, "1");
                }
                return map3;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                Map map = map4;
                if (map != null && !map.isEmpty()) {
                    hashMap.putAll(map4);
                }
                hashMap.putAll(SdkSession.this.b());
                hashMap.put("User-Agent", "PayUMoneyAndroidSDK");
                hashMap.put("x-payumoney-sdk-ver", BuildConfig.VERSION_NAME);
                if (SdkHelper.pnpVersion != null) {
                    hashMap.put("x-payumoney-pnp-ver", SdkHelper.pnpVersion);
                }
                if (SdkSession.this.getToken() != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Bearer ");
                    sb.append(SdkSession.this.getToken());
                    hashMap.put("Authorization", sb.toString());
                } else {
                    hashMap.put("Accept", "*/*;");
                }
                return hashMap;
            }

            public String getBodyContentType() {
                if (SdkSession.this.getToken() == null) {
                    return CBConstant.HTTP_URLENCODED;
                }
                return super.getBodyContentType();
            }
        };
        r02.setShouldCache(false);
        r02.setRetryPolicy(new DefaultRetryPolicy(CBConstant.HTTP_TIMEOUT, 1, 0.0f));
        addToRequestQueue(r02);
        this.b = Long.valueOf(System.currentTimeMillis());
    }

    private String a(Map<String, String> map) {
        String str = "?";
        Iterator it = map.entrySet().iterator();
        boolean z = true;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (z) {
                StringBuilder sb = new StringBuilder();
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                str = str.concat(sb.toString());
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("&");
                sb2.append(entry.getKey());
                sb2.append("=");
                sb2.append(entry.getValue());
                str = str.concat(sb2.toString());
            }
            z = false;
            it.remove();
        }
        return str;
    }

    /* access modifiers changed from: private */
    public void a(final Task task, final Throwable th) {
        if ((th instanceof ConnectTimeoutException) || (th instanceof SocketTimeoutException)) {
            final Throwable th2 = new Throwable("time out error");
            this.n.post(new Runnable() {
                public void run() {
                    task.onError(th2);
                }
            });
            return;
        }
        this.n.post(new Runnable() {
            public void run() {
                task.onError(th);
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(final Task task, final JSONObject jSONObject) {
        this.n.post(new Runnable() {
            public void run() {
                task.onSuccess(jSONObject);
            }
        });
    }

    public boolean isLoggedIn() {
        return getToken() != null;
    }

    public static boolean isLoggedIn(Context context) {
        boolean z = false;
        try {
            if (SharedPrefsUtils.getStringPreference(context, "access_token") != null) {
                z = true;
            }
            return z;
        } catch (Exception e2) {
            return false;
        }
    }

    public void create(String username, String password, OnUserLoginListener listener, PayULoginDialogListener payULoginDialogListener, String tag) {
        HashMap hashMap = new HashMap();
        hashMap.put("grant_type", PayUmoneyConstants.PASSWORD);
        hashMap.put(PayUmoneyConstants.CLIENT_ID, "10182");
        hashMap.put(PayUmoneyConstants.USER_NAME, username);
        hashMap.put(PayUmoneyConstants.PASSWORD, password);
        HashMap hashMap2 = new HashMap();
        hashMap2.put(AnalyticsConstant.ID_VALUE, username);
        hashMap2.put(AnalyticsConstant.MERCHANT_PASSED_EMAIL, PayUmoneySDK.getInstance().getPaymentParam().getParams().get("email"));
        hashMap2.put(AnalyticsConstant.MERCHANT_PASSED_PHONE, PayUmoneySDK.getInstance().getPaymentParam().getParams().get("phone"));
        hashMap2.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        LogAnalytics.logEvent(this.m, AnalyticsConstant.LOGIN_INITIATED, hashMap2, AnalyticsConstant.CLEVERTAP);
        final String str = username;
        final String str2 = password;
        final OnUserLoginListener onUserLoginListener = listener;
        final String str3 = tag;
        final PayULoginDialogListener payULoginDialogListener2 = payULoginDialogListener;
        AnonymousClass14 r2 = new Task() {
            public void onSuccess(JSONObject jsonObject) {
                try {
                    PayUMoneyAPIResponse parseLoginResponse = new ResponseParser().parseLoginResponse(jsonObject);
                    if (!(parseLoginResponse instanceof PayUMoneyLoginResponse)) {
                        HashMap hashMap = new HashMap();
                        hashMap.put(AnalyticsConstant.ID_VALUE, str);
                        SdkSession.this.a(str2, hashMap);
                        LogAnalytics.logEvent(SdkSession.this.m, AnalyticsConstant.LOGIN_FAILED, hashMap, AnalyticsConstant.CLEVERTAP);
                        onUserLoginListener.onFailureResponse((ErrorResponse) parseLoginResponse, str3);
                        if (payULoginDialogListener2 != null) {
                            payULoginDialogListener2.onErrorOccurred(((ErrorResponse) parseLoginResponse).getMessage(), str3);
                        }
                    } else if (!jsonObject.has("access_token") || jsonObject.isNull("access_token")) {
                        HashMap hashMap2 = new HashMap();
                        hashMap2.put(AnalyticsConstant.ID_VALUE, str);
                        SdkSession.this.a(str2, hashMap2);
                        LogAnalytics.logEvent(SdkSession.this.m, AnalyticsConstant.LOGIN_FAILED, hashMap2, AnalyticsConstant.CLEVERTAP);
                        ErrorResponse errorResponse = new ErrorResponse();
                        errorResponse.setMessage(jsonObject.toString());
                        onUserLoginListener.onFailureResponse(errorResponse, str3);
                        String obj = jsonObject.get(PayUmoneyConstants.MESSAGE).toString();
                        if (obj == null) {
                            obj = PayUmoneyConstants.ERROR_SOMETHING_WENT_WRONG;
                        }
                        if (payULoginDialogListener2 != null) {
                            payULoginDialogListener2.onErrorOccurred(obj, str3);
                        }
                    } else {
                        HashMap hashMap3 = new HashMap();
                        hashMap3.put(AnalyticsConstant.ID_VALUE, str);
                        SdkSession.this.a(str2, hashMap3);
                        LogAnalytics.logEvent(SdkSession.this.m, AnalyticsConstant.LOGIN_SUCCEEDED, hashMap3, AnalyticsConstant.CLEVERTAP);
                        String string = jsonObject.getString("access_token");
                        SharedPrefsUtils.setStringPreference(SdkSession.this.m, "access_token", string);
                        SdkSession.getInstance(SdkSession.this.m).setToken(string);
                        SdkHelper.resetSessionUpdateTimeStamp(SdkSession.this.m);
                        SharedPrefsUtils.setStringPreference(SdkSession.this.m, "email", str);
                        onUserLoginListener.onSuccessfulLogin((PayUMoneyLoginResponse) parseLoginResponse, str3);
                        if (payULoginDialogListener2 != null) {
                            payULoginDialogListener2.onDialogDismiss(str3);
                        }
                    }
                } catch (Exception e2) {
                    onUserLoginListener.onError(e2.getMessage(), str3);
                    PayULoginDialogListener payULoginDialogListener = payULoginDialogListener2;
                    if (payULoginDialogListener != null) {
                        payULoginDialogListener.onErrorOccurred(e2.getMessage(), str3);
                    }
                }
            }

            public void onSuccess(String response) {
                onUserLoginListener.onError(response, str3);
            }

            public void onError(Throwable e2) {
                try {
                    JSONObject jSONObject = new JSONObject(new String(((ServerError) e2).networkResponse.f3data, Key.STRING_CHARSET_NAME));
                    if (jSONObject.has("error_description") && jSONObject.getString("error_description") != null) {
                        if (payULoginDialogListener2 != null) {
                            payULoginDialogListener2.onErrorOccurred(jSONObject.getString("error_description"), str3);
                        }
                        return;
                    }
                } catch (Exception e3) {
                }
                onUserLoginListener.onError(e2.getMessage(), str3);
                PayULoginDialogListener payULoginDialogListener = payULoginDialogListener2;
                if (payULoginDialogListener != null) {
                    payULoginDialogListener.onErrorOccurred(e2.toString(), str3);
                }
            }

            public void onProgress(int percent) {
            }
        };
        postFetch("/auth/oauth/token", hashMap, r2, 1);
    }

    /* access modifiers changed from: private */
    public void a(String str, HashMap<String, Object> hashMap) {
        if (!SdkHelper.isNumber(str) || str.length() != 6) {
            hashMap.put(AnalyticsConstant.AUTH_TYPE, "Password");
        } else {
            hashMap.put(AnalyticsConstant.AUTH_TYPE, "OTP");
        }
        hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
    }

    public void logout(String message) {
        SharedPrefsUtils.deleteKey(this.m, "access_token");
        this.l.reset();
    }

    public static void logout(Context context) {
        try {
            SharedPrefsUtils.deleteKey(context, "access_token");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void fetchPaymentStatus(String paymentID, final OnPaymentDetailsReceivedFromPayuMoney listener, final String tag) {
        HashMap hashMap = new HashMap();
        hashMap.put(PayUmoneyConstants.PAYMENT_ID, paymentID);
        postFetch("/payment/app/postPayment", hashMap, new Task() {
            public void onSuccess(JSONObject object) {
                String optString = object.optString("status");
                if (optString == null || optString.equals("-1")) {
                    ErrorResponse errorResponse = new ErrorResponse();
                    errorResponse.setMessage(object.toString());
                    listener.onFailureResponse(errorResponse, tag);
                    return;
                }
                HashMap hashMap = new HashMap();
                hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                try {
                    JSONObject jSONObject = object.getJSONObject("result");
                    double d = 0.0d;
                    if (jSONObject.getString("additionalCharges") != null) {
                        if (!jSONObject.getString("additionalCharges").isEmpty()) {
                            if (!jSONObject.getString("additionalCharges").equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                                d = Double.parseDouble(jSONObject.getString("additionalCharges"));
                            }
                        }
                    }
                    hashMap.put(AnalyticsConstant.AMOUNT, String.valueOf(Double.parseDouble(jSONObject.getString(PayUmoneyConstants.AMOUNT)) + d));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogAnalytics.logEvent(SdkSession.this.m, AnalyticsConstant.PAYMENT_SUCCEEDED, hashMap, AnalyticsConstant.CLEVERTAP);
                listener.OnPaymentDetailsReceivedFromPayuMoney(object, tag);
            }

            public void onSuccess(String response) {
                listener.onError(response.toString(), tag);
            }

            public void onError(Throwable e) {
                listener.onError(e.getMessage(), tag);
            }

            public void onProgress(int percent) {
            }
        }, 1);
    }

    public String getToken() {
        if (PayUmoneyConstants.WALLET_SDK.booleanValue()) {
            return SharedPrefsUtils.getStringPreference(this.m, "access_token");
        }
        return this.l.getToken();
    }

    public void setToken(String token) {
        this.l.setToken(token);
    }

    public void createPayment(final HashMap<String, String> params, final OnPaymentOptionReceivedListener listener, final String tag) {
        HashMap hashMap = new HashMap();
        hashMap.put("key", params.get("key"));
        hashMap.put(PayUmoneyConstants.AMOUNT, params.get(PayUmoneyConstants.AMOUNT));
        hashMap.put("txnid", params.get("txnid"));
        hashMap.put(PayUmoneyConstants.PRODUCT_INFO_STRING, params.get(PayUmoneyConstants.PRODUCT_INFO));
        hashMap.put(PayUmoneyConstants.FIRST_NAME_STRING, params.get(PayUmoneyConstants.FIRSTNAME));
        hashMap.put("email", params.get("email"));
        hashMap.put(PayUmoneyConstants.UDF1, params.get(PayUmoneyConstants.UDF1));
        hashMap.put(PayUmoneyConstants.UDF2, params.get(PayUmoneyConstants.UDF2));
        hashMap.put(PayUmoneyConstants.UDF3, params.get(PayUmoneyConstants.UDF3));
        hashMap.put(PayUmoneyConstants.UDF4, params.get(PayUmoneyConstants.UDF4));
        hashMap.put(PayUmoneyConstants.UDF5, params.get(PayUmoneyConstants.UDF5));
        hashMap.put(PayUmoneyConstants.UDF6, params.get(PayUmoneyConstants.UDF6));
        hashMap.put(PayUmoneyConstants.UDF7, params.get(PayUmoneyConstants.UDF7));
        hashMap.put(PayUmoneyConstants.UDF8, params.get(PayUmoneyConstants.UDF8));
        hashMap.put(PayUmoneyConstants.UDF9, params.get(PayUmoneyConstants.UDF9));
        hashMap.put(PayUmoneyConstants.UDF10, params.get(PayUmoneyConstants.UDF10));
        hashMap.put("hash", params.get("hash"));
        hashMap.put(PayUmoneyConstants.PAYMENT_IDENTIFIERS_STRING, "[]");
        hashMap.put("purchaseFrom", "PayUMoneyAndroidSDK");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("surl", params.get("surl"));
            jSONObject.put("furl", params.get("furl"));
            jSONObject.put("email", params.get("email"));
            jSONObject.put("phone", params.get("phone"));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        hashMap.put(PayUmoneyConstants.TRANSACTION_DETAILS, jSONObject.toString());
        hashMap.put(PayUmoneyConstants.PAYMENT_PARTS_STRING, "[]");
        hashMap.put(PayUmoneyConstants.DEVICE_ID, SdkHelper.getAndroidID(this.m));
        hashMap.put(PayUmoneyConstants.IS_MOBILE, "1");
        if (!isLoggedIn()) {
            hashMap.put(PayUmoneyConstants.GUEST_CHECKOUT, "true");
        } else {
            hashMap.put(PayUmoneyConstants.GUEST_CHECKOUT, "false");
        }
        postFetch("/payment/app/v2/addPayment", hashMap, new Task() {
            public void onSuccess(JSONObject jsonObject) {
                String str;
                try {
                    PayUMoneyAPIResponse parsePaymentOption = SdkSession.this.s.parsePaymentOption(jsonObject);
                    if (parsePaymentOption instanceof PaymentOptionDetails) {
                        SdkSession.paymentId = ((PaymentOptionDetails) parsePaymentOption).getPaymentID();
                        listener.onSuccess(jsonObject.toString(), tag);
                        HashMap hashMap = new HashMap();
                        hashMap.put(AnalyticsConstant.MERCHANT_PASSED_EMAIL, params.get("email"));
                        hashMap.put(AnalyticsConstant.MERCHANT_PASSED_PHONE, params.get("phone"));
                        hashMap.put(AnalyticsConstant.AMOUNT, params.get(PayUmoneyConstants.AMOUNT));
                        LogAnalytics.logEvent(SdkSession.this.m, AnalyticsConstant.PAYMENT_ADDED, hashMap, AnalyticsConstant.CLEVERTAP);
                        if (PayUmoneyConfig.getInstance() == null || PayUmoneyConfig.getInstance().getPayUmoneyActivityTitle() == null || PayUmoneyConfig.getInstance().getPayUmoneyActivityTitle().equalsIgnoreCase("")) {
                            str = ((PaymentOptionDetails) parsePaymentOption).getMerchantDetails().getDisplayName();
                        } else {
                            str = PayUmoneyConfig.getInstance().getPayUmoneyActivityTitle();
                        }
                        SharedPrefsUtils.setStringPreference(SdkSession.this.m, Keys.MERCHANT_NAME, str);
                        listener.onPaymentOptionReceived((PaymentOptionDetails) parsePaymentOption, tag);
                        return;
                    }
                    listener.onFailureResponse((ErrorResponse) parsePaymentOption, tag);
                } catch (PayUMoneyCustomException e) {
                    listener.onError(e.getMessage(), tag);
                }
            }

            public void onSuccess(String response) {
                listener.onSuccess(response, tag);
            }

            public void onError(Throwable e) {
                if (e.toString().contains("com.android.volley.AuthFailureError")) {
                    SdkSession.this.createPayment(params, listener, tag);
                } else {
                    listener.onError(e.getMessage(), tag);
                }
            }

            public void onProgress(int percent) {
            }
        }, 1);
    }

    public void getEmiInterestsForBank(String paymentID, double totalAmount, OnEmiInterestReceivedListener listener, List<EmiThreshold> emiThresholds, String tag) {
        HashMap hashMap = new HashMap();
        String str = paymentID;
        hashMap.put(PayUmoneyConstants.PAYMENT_ID, paymentID);
        String str2 = PayUmoneyConstants.AMOUNT;
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(totalAmount);
        hashMap.put(str2, sb.toString());
        hashMap.put("resetGlobalOffer", "false");
        final double d2 = totalAmount;
        final List<EmiThreshold> list = emiThresholds;
        final OnEmiInterestReceivedListener onEmiInterestReceivedListener = listener;
        final String str3 = tag;
        AnonymousClass17 r0 = new Task() {
            public void onSuccess(JSONObject jsonObject) {
                try {
                    ArrayList parseEmiInterestsForBankResponse = SdkSession.this.s.parseEmiInterestsForBankResponse(jsonObject, d2, list);
                    if (parseEmiInterestsForBankResponse != null) {
                        onEmiInterestReceivedListener.onUpdatedEmiInterestReceived(parseEmiInterestsForBankResponse, str3);
                    } else {
                        onEmiInterestReceivedListener.onUpdatedEmiInterestFailed(null, str3);
                    }
                } catch (PayUMoneyCustomException e2) {
                    onEmiInterestReceivedListener.onUpdatedEmiInterestFailed(e2.getMessage(), str3);
                }
            }

            public void onSuccess(String response) {
                onEmiInterestReceivedListener.onUpdatedEmiInterestFailed(null, str3);
            }

            public void onError(Throwable e2) {
                onEmiInterestReceivedListener.onUpdatedEmiInterestFailed(e2.getMessage(), str3);
            }

            public void onProgress(int percent) {
            }
        };
        StringBuilder sb2 = new StringBuilder();
        sb2.append("/payment/op/getEmiInterestForBank");
        sb2.append(a((Map<String, String>) hashMap));
        postFetch(sb2.toString(), null, r0, 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:57:0x019e  */
    public void sendToPayU(PaymentRequest request, OnPaymentDetailsReceivedFromPayuMoney listener, String tag) {
        double d2;
        CharSequence charSequence;
        HashMap hashMap = new HashMap();
        final String paymentID = request.getPaymentID();
        JSONObject jSONObject = new JSONObject();
        if (request.isSplitPayment()) {
            d2 = PayUmoneyTransactionDetails.getInstance().getWalletAmount();
        } else {
            d2 = 0.0d;
        }
        try {
            jSONObject.put("PAYU", Double.valueOf((Double.parseDouble((String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT)) + request.getConvenienceFee()) - d2));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (d2 > 0.0d) {
            String str = PayUmoneyConstants.WALLET_STRING;
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(d2);
                sb.append("");
                jSONObject.put(str, sb.toString());
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (request.getPg().equals(PayUmoneyConstants.WALLET)) {
            try {
                jSONObject.put("PAYU", 0);
                jSONObject.put(PayUmoneyConstants.WALLET_STRING, Double.parseDouble((String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT)) + request.getConvenienceFee());
            } catch (Exception e4) {
            }
        }
        hashMap.put(PayUmoneyConstants.SOURCE_AMOUNT_MAP, jSONObject.toString());
        hashMap.put("PG", request.getPg());
        if (request.getPg().equals(PayUmoneyConstants.WALLET)) {
            hashMap.put("PG", PayUmoneyConstants.WALLET_STRING);
        }
        if (request.getBankCode() != null && !request.getBankCode().equals("")) {
            hashMap.put(PayUmoneyConstants.BANK_CODE_STRING, request.getBankCode());
        }
        if (request.getCountryCode() != null && !request.getCountryCode().isEmpty() && !request.getCountryCode().equalsIgnoreCase("IN")) {
            hashMap.put(PayUmoneyConstants.IS_INTERNATIONAL, "true");
        }
        if (request.getPg().equals(PayUmoneyConstants.PAYMENT_MODE_UPI)) {
            hashMap.put(PayUmoneyConstants.PM_KEY_VPA, request.getVpa());
        }
        if (request.getStoreCardId() != null) {
            hashMap.put(PayUmoneyConstants.STORE_CARD_ID, String.valueOf(request.getStoreCardId()));
        } else if (request.getPg().equalsIgnoreCase(PayUmoneyConstants.PAYMENT_MODE_CC) || request.getPg().equalsIgnoreCase(PayUmoneyConstants.PAYMENT_MODE_DC)) {
            if (request.getCardNumber() != null) {
                String cardNumber = request.getCardNumber();
                if (!TextUtils.isEmpty(cardNumber) && cardNumber.length() > 6) {
                    String substring = cardNumber.substring(0, 6);
                    String substring2 = cardNumber.substring(cardNumber.length() - 4, cardNumber.length());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(substring);
                    sb2.append("XXXXXX");
                    sb2.append(substring2);
                    charSequence = sb2.toString();
                    if (!TextUtils.isEmpty(charSequence)) {
                        hashMap.put("cardMask", charSequence);
                    }
                }
            }
            charSequence = null;
            if (!TextUtils.isEmpty(charSequence)) {
            }
        }
        if (request.isEmiPayment()) {
            hashMap.put(PayUmoneyConstants.EMI_CONVERT, "on");
        }
        hashMap.put(PayUmoneyConstants.REVISED_CASHBAK_RECEIVED_STATUS, "0");
        hashMap.put(PayUmoneyConstants.IS_MOBILE, "1");
        hashMap.put(PayUmoneyConstants.CALLING_PLATFORM_NAME, PayUmoneyConstants.CALLING_PLATFORM_VALUE);
        if (isLoggedIn() || (isNitroEnabled() && isUserAccountActive())) {
            hashMap.put(PayUmoneyConstants.GUEST_CHECKOUT, "false");
        } else {
            hashMap.put(PayUmoneyConstants.SIGNUP_USER_FLAG, String.valueOf(!PayUmoneySDK.getInstance().isUserSignUpDisabled()));
            hashMap.put(PayUmoneyConstants.GUEST_CHECKOUT, "true");
            hashMap.put(PayUmoneyConstants.GUEST_EMAIL, PayUmoneySDK.getInstance().getPaymentParam().getParams().get("email"));
            hashMap.put(PayUmoneyConstants.GUEST_PHONE, PayUmoneySDK.getInstance().getPaymentParam().getParams().get("phone"));
        }
        SdkLogger.d("PayUMoneySdk:Params -->", hashMap.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("/payment/makePayment/");
        sb3.append(paymentID);
        sb3.append(a((Map<String, String>) hashMap));
        String sb4 = sb3.toString();
        final PaymentRequest paymentRequest = request;
        final OnPaymentDetailsReceivedFromPayuMoney onPaymentDetailsReceivedFromPayuMoney = listener;
        final String str2 = tag;
        AnonymousClass18 r1 = new Task() {
            public void onSuccess(JSONObject object) {
                try {
                    SdkLogger.d(SdkSession.TAG, object.toString());
                    if (!paymentRequest.getPg().equals(PayUmoneyConstants.POINTS)) {
                        if (!paymentRequest.getPg().equals(PayUmoneyConstants.WALLET)) {
                            SdkLogger.d("PayUMoneySdk:Success-->", object.toString());
                            if (!object.has("status") || object.getInt("status") != 0) {
                                ResponseParser responseParser = new ResponseParser();
                                onPaymentDetailsReceivedFromPayuMoney.onFailureResponse(responseParser.errorFromResponse(object), str2);
                                try {
                                    HashMap hashMap = new HashMap();
                                    hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                                    hashMap.put(AnalyticsConstant.AMOUNT, Double.valueOf(Double.parseDouble((String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT)) + paymentRequest.getConvenienceFee()));
                                    hashMap.put(AnalyticsConstant.REASON, responseParser.errorFromResponse(object).getMessage());
                                    LogAnalytics.logEvent(SdkSession.this.m, AnalyticsConstant.PAYMENT_FAILED, hashMap, AnalyticsConstant.CLEVERTAP);
                                    return;
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                    return;
                                }
                            } else {
                                JSONObject object2 = object.getJSONObject("result");
                                new JSONObject();
                                onPaymentDetailsReceivedFromPayuMoney.OnPaymentDetailsReceivedFromPayuMoney(object2, str2);
                                return;
                            }
                        }
                    }
                    if (!object.has("status") || object.getInt("status") != 0) {
                        ResponseParser responseParser2 = new ResponseParser();
                        onPaymentDetailsReceivedFromPayuMoney.onFailureResponse(responseParser2.errorFromResponse(object), str2);
                        try {
                            HashMap hashMap2 = new HashMap();
                            hashMap2.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                            hashMap2.put(AnalyticsConstant.REASON, responseParser2.errorFromResponse(object).getMessage());
                            hashMap2.put(AnalyticsConstant.AMOUNT, Double.valueOf(Double.parseDouble((String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT)) + paymentRequest.getConvenienceFee()));
                            LogAnalytics.logEvent(SdkSession.this.m, AnalyticsConstant.PAYMENT_FAILED, hashMap2, AnalyticsConstant.CLEVERTAP);
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    } else {
                        SdkSession.this.fetchPaymentStatus(paymentID, onPaymentDetailsReceivedFromPayuMoney, str2);
                    }
                } catch (Exception e4) {
                    onError(e4);
                }
            }

            public void onSuccess(String response) {
                onPaymentDetailsReceivedFromPayuMoney.onError(response.toString(), str2);
            }

            public void onError(Throwable throwable) {
                onPaymentDetailsReceivedFromPayuMoney.onError(throwable.getMessage(), str2);
            }

            public void onProgress(int percent) {
            }
        };
        postFetch(sb4, null, r1, 0);
    }

    public void sendOTPsForLoginSignUP(final String username, final OnOTPRequestSendListener listener, final String tag) {
        HashMap hashMap = new HashMap();
        if (b(username)) {
            hashMap.put("phone", username);
        } else {
            hashMap.put(PayUmoneyConstants.USER_NAME, username);
        }
        hashMap.put("merchantId", PayUmoneySDK.getInstance().getPaymentParam().getParams().get("merchantId"));
        postFetch("/auth/op/sendPaymentOTP", hashMap, new Task() {
            public void onSuccess(JSONObject jsonObject) {
                try {
                    int i = jsonObject.getInt("status");
                    HashMap hashMap = new HashMap();
                    hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                    hashMap.put(AnalyticsConstant.ID_VALUE, username);
                    if (i == 0) {
                        hashMap.put(AnalyticsConstant.OTP_TRIGGERED, "true");
                        listener.onOTPRequestSend(jsonObject.toString(), tag);
                    } else {
                        hashMap.put(AnalyticsConstant.OTP_TRIGGERED, "false");
                        ErrorResponse errorResponse = new ErrorResponse();
                        StringBuilder sb = new StringBuilder();
                        sb.append(i);
                        sb.append("");
                        errorResponse.setStatus(sb.toString());
                        if (jsonObject.get(PayUmoneyConstants.MESSAGE).toString().contentEquals("Invalid phone number")) {
                            errorResponse.setMessage("User credentials do not exist. Please pay without login (go back) or register and pay.");
                            listener.onFailureResponse(errorResponse, tag);
                        } else {
                            errorResponse.setMessage(jsonObject.getString(PayUmoneyConstants.MESSAGE));
                            listener.onFailureResponse(errorResponse, tag);
                        }
                    }
                    LogAnalytics.logEvent(SdkSession.this.m, AnalyticsConstant.LOGIN_OTP_TRIGGERED, hashMap, AnalyticsConstant.CLEVERTAP);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onSuccess(String response) {
                listener.onError(response, tag);
            }

            public void onError(Throwable throwable) {
                listener.onError(throwable.getMessage(), tag);
            }

            public void onProgress(int percent) {
            }
        }, 1);
    }

    private boolean b(String str) {
        return str.matches("[0-9]+");
    }

    public void notifyUserCancelledTransaction(String paymentId2, String userCancelled) {
        HashMap hashMap = new HashMap();
        hashMap.put(PayUmoneyConstants.PAYMENT_ID, paymentId2);
        if (userCancelled != null) {
            hashMap.put(PayUmoneyConstants.USER_CANCELLED_TRANSACTION, userCancelled);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("/payment/postBackParamIcp.do");
        sb.append(a((Map<String, String>) hashMap));
        postFetch(sb.toString(), null, new Task() {
            public void onSuccess(JSONObject jsonObject) {
                if (PayUmoneySdkInitializer.IsDebugMode().booleanValue()) {
                    SdkLogger.d(PayUmoneyConstants.TAG, "Successfully Cancelled the transaction");
                }
            }

            public void onSuccess(String response) {
                if (PayUmoneySdkInitializer.IsDebugMode().booleanValue()) {
                    SdkLogger.d(PayUmoneyConstants.TAG, "Successfully Cancelled the transaction");
                }
            }

            public void onError(Throwable throwable) {
            }

            public void onProgress(int percent) {
            }
        }, 0);
    }

    public void getNetBankingStatus(final OnNetBankingStatusListReceivedListener listener, final String tag) {
        postFetch("/payment/op/getNetBankingStatus", null, new Task() {
            public void onSuccess(JSONObject jsonObject) {
                try {
                    PayUMoneyAPIResponse parseNetBankingStatusList = SdkSession.this.s.getParseNetBankingStatusList(jsonObject);
                    if (parseNetBankingStatusList instanceof NetBankingStatusResponse) {
                        listener.OnNetBankingListReceived((NetBankingStatusResponse) parseNetBankingStatusList, tag);
                    } else {
                        listener.onFailureResponse((ErrorResponse) parseNetBankingStatusList, tag);
                    }
                } catch (PayUMoneyCustomException e) {
                    listener.onError(e.getMessage(), tag);
                } catch (Exception e2) {
                }
            }

            public void onSuccess(String response) {
                listener.onSuccess(response, tag);
            }

            public void onError(Throwable e) {
                listener.onError(e.getMessage(), tag);
            }

            public void onProgress(int percent) {
            }
        }, 0);
    }

    public void fetchCardBinInfo(final OnCardBinDetailsReceived listener, String cardNumber, final String tag) {
        HashMap hashMap = new HashMap();
        hashMap.put("bin", cardNumber.substring(0, 6));
        StringBuilder sb = new StringBuilder();
        sb.append("/payment/op/v1/getBinDetails");
        sb.append(a((Map<String, String>) hashMap));
        postFetch(sb.toString(), null, new Task() {
            public void onSuccess(JSONObject jsonObject) {
                try {
                    PayUMoneyAPIResponse parseBinDetail = SdkSession.this.s.parseBinDetail(jsonObject);
                    if (parseBinDetail instanceof BinDetail) {
                        listener.onCardBinDetailReceived((BinDetail) parseBinDetail, tag);
                    } else {
                        listener.onFailureResponse((ErrorResponse) parseBinDetail, tag);
                    }
                } catch (PayUMoneyCustomException e) {
                    listener.onError(e.getMessage(), tag);
                }
            }

            public void onSuccess(String response) {
                listener.onSuccess(response, tag);
            }

            public void onError(Throwable e) {
                listener.onError(e.getMessage(), tag);
            }

            public void onProgress(int percent) {
            }
        }, 0);
    }

    public void fetchMultipleCardBinInfo(final OnMultipleCardBinDetailsListener listener, ArrayList<String> cardNumbersList, final String tag) {
        HashMap hashMap = new HashMap();
        hashMap.put("bins", SdkHelper.getCommaSeparatedBins(cardNumbersList));
        StringBuilder sb = new StringBuilder();
        sb.append("/payment/op/v1/getMultipleBinDetails");
        sb.append(a((Map<String, String>) hashMap));
        postFetch(sb.toString(), null, new Task() {
            public void onSuccess(JSONObject jsonObject) {
                try {
                    HashMap parseMultipleBinDetail = SdkSession.this.s.parseMultipleBinDetail(jsonObject);
                    if (parseMultipleBinDetail != null) {
                        listener.onMultipleCardBinDetailsReceived(parseMultipleBinDetail, tag);
                        return;
                    }
                    ErrorResponse errorResponse = new ErrorResponse();
                    if (jsonObject.has("status")) {
                        errorResponse.setStatus(jsonObject.getString("status"));
                    }
                    if (jsonObject.has(PayUmoneyConstants.MESSAGE)) {
                        errorResponse.setMessage(jsonObject.getString(PayUmoneyConstants.MESSAGE));
                    }
                    if (jsonObject.has("errorCode")) {
                        errorResponse.setErrorCode(jsonObject.getString("errorCode"));
                    }
                    if (jsonObject.has("responseCode")) {
                        errorResponse.setErrorCode(jsonObject.getString("errorCode"));
                    }
                    listener.onFailureResponse(errorResponse, tag);
                } catch (JSONException e) {
                    listener.onError(e.getMessage(), tag);
                } catch (PayUMoneyCustomException e2) {
                    listener.onError(e2.getMessage(), tag);
                }
            }

            public void onSuccess(String response) {
                listener.onSuccess(response, tag);
            }

            public void onError(Throwable e) {
                listener.onError(e.getMessage(), tag);
            }

            public void onProgress(int percent) {
            }
        }, 0);
    }

    public void fetchUserDetailsForNitro(final OnFetchUserDetailsForNitroFlowListener listener, String paymentId2, String email, String phone, final String tag) {
        HashMap hashMap = new HashMap();
        hashMap.put(PayUmoneyConstants.PAYMENT_ID, paymentId2);
        hashMap.put("email", email);
        hashMap.put(PayUmoneyConstants.MOBILE, phone);
        StringBuilder sb = new StringBuilder();
        sb.append("/payment/op/v1/fetchUserDataFromEmailMobile");
        sb.append(a((Map<String, String>) hashMap));
        postFetch(sb.toString(), null, new Task() {
            public void onSuccess(JSONObject jsonObject) {
                try {
                    PayUMoneyAPIResponse parseUserDetailsForNitroFlow = SdkSession.this.s.parseUserDetailsForNitroFlow(jsonObject);
                    if (parseUserDetailsForNitroFlow instanceof UserDetail) {
                        listener.onSuccess(jsonObject.toString(), tag);
                        listener.onUserDetailsReceivedForNitroFlow((UserDetail) parseUserDetailsForNitroFlow, tag);
                        return;
                    }
                    listener.onFailureResponse((ErrorResponse) parseUserDetailsForNitroFlow, tag);
                } catch (PayUMoneyCustomException e) {
                    listener.onError(e.getMessage(), tag);
                }
            }

            public void onSuccess(String response) {
                listener.onSuccess(response, tag);
            }

            public void onError(Throwable e) {
                listener.onError(e.getMessage(), tag);
            }

            public void onProgress(int percent) {
            }
        }, 0);
    }

    public void verifyPaymentDetails(String paymentID, final OnVerifyPaymentResponse listener) {
        HashMap hashMap = new HashMap();
        hashMap.put(PayUmoneyConstants.PAYMENT_ID, paymentID);
        postFetch("/payment/app/checkPaymentDetails", hashMap, new Task() {
            public void onSuccess(JSONObject object) {
                listener.onVerifyStatusResponseReceived(object.toString());
            }

            public void onSuccess(String response) {
                listener.onVerifyStatusResponseReceived(response);
            }

            public void onError(Throwable throwable) {
                listener.onVerifyStatusResponseReceived(null);
            }

            public void onProgress(int percent) {
            }
        }, 1);
    }
}
