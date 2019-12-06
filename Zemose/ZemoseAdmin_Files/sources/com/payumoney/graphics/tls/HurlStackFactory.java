package com.payumoney.graphics.tls;

import com.android.volley.toolbox.HurlStack;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HurlStackFactory {
    public static HurlStack getHurlStack() {
        return new HurlStack() {
            /* access modifiers changed from: protected */
            public HttpURLConnection createConnection(URL url) throws IOException {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
                try {
                    httpsURLConnection.setSSLSocketFactory(new TLSSocketFactory());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return httpsURLConnection;
            }
        };
    }
}
