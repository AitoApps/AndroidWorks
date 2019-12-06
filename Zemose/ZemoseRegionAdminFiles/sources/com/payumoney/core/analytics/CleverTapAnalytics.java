package com.payumoney.core.analytics;

import android.os.AsyncTask;
import com.payu.custombrowser.util.CBConstant;
import com.payumoney.core.BuildConfig;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.listener.OnClevertapAnalyticsListener;
import com.payumoney.core.utils.SdkHelper;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class CleverTapAnalytics {
    /* access modifiers changed from: private */
    public OnClevertapAnalyticsListener a;

    class UploadEventTask extends AsyncTask<String, Void, Void> {
        UploadEventTask() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Void doInBackground(String... strArr) {
            int i = 0;
            HttpURLConnection a2 = CleverTapAnalytics.this.a(strArr[0], strArr[1], -1, "application/json", null);
            if (a2 != null) {
                try {
                    i = a2.getResponseCode();
                } catch (IOException e) {
                    e.printStackTrace();
                    CleverTapAnalytics.this.a.OnClevertapEventsLoggedFailed();
                }
                if (i == 200) {
                    try {
                        if (new JSONObject(SdkHelper.getStringBufferFromInputStream(a2.getInputStream()).toString()).getString("status").equalsIgnoreCase(CBConstant.FAIL)) {
                            CleverTapAnalytics.this.a.OnClevertapEventsLoggedFailed();
                        } else {
                            CleverTapAnalytics.this.a.OnClevertapEventsLoggedSuccessful();
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        CleverTapAnalytics.this.a.OnClevertapEventsLoggedFailed();
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                        CleverTapAnalytics.this.a.OnClevertapEventsLoggedFailed();
                    }
                } else {
                    CleverTapAnalytics.this.a.OnClevertapEventsLoggedFailed();
                }
            }
            return null;
        }
    }

    public void sendEvent(String url, String postData, OnClevertapAnalyticsListener listener) {
        this.a = listener;
        new UploadEventTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{url, postData});
    }

    /* access modifiers changed from: private */
    public HttpURLConnection a(String str, String str2, int i, String str3, String str4) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setRequestMethod("POST");
            if (i != -1) {
                httpURLConnection.setConnectTimeout(i);
            }
            if (PayUmoneySdkInitializer.IsDebugMode().booleanValue()) {
                httpURLConnection.setRequestProperty("X-CleverTap-Account-Id", BuildConfig.ClevertapAccountId_Test);
                httpURLConnection.setRequestProperty("X-CleverTap-Passcode", BuildConfig.ClevertapPasscode_Test);
            } else {
                httpURLConnection.setRequestProperty("X-CleverTap-Account-Id", BuildConfig.ClevertapAccountId_Prod);
                httpURLConnection.setRequestProperty("X-CleverTap-Passcode", BuildConfig.ClevertapPasscode_Prod);
            }
            httpURLConnection.setRequestProperty("Content-Type", str3);
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(str2.length()));
            if (str4 != null) {
                httpURLConnection.setRequestProperty("X-Clevertap-Account_token", str4);
            }
            httpURLConnection.setDoOutput(true);
            httpURLConnection.getOutputStream().write(str2.getBytes());
            return httpURLConnection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
