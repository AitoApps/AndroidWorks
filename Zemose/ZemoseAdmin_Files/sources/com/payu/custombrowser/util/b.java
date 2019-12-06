package com.payu.custombrowser.util;

import android.os.AsyncTask;
import com.payu.custombrowser.bean.a;
import java.io.InputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class b extends AsyncTask<a, String, String> {
    private com.payu.custombrowser.b.a a;

    private b() {
    }

    public b(com.payu.custombrowser.b.a aVar) {
        this.a = aVar;
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public String doInBackground(a... aVarArr) {
        a aVar = aVarArr[0];
        try {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(aVar.b()).openConnection();
            httpsURLConnection.setRequestMethod(aVar.a());
            httpsURLConnection.setSSLSocketFactory(new g());
            httpsURLConnection.setConnectTimeout(CBConstant.VERIFY_HTTP_TIMEOUT);
            httpsURLConnection.setRequestProperty("Content-Type", aVar.d());
            String str = "Content-Length";
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(aVar.c() != null ? aVar.c().length() : 0);
            httpsURLConnection.setRequestProperty(str, sb.toString());
            httpsURLConnection.getOutputStream().write(aVar.c().getBytes());
            InputStream inputStream = httpsURLConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            byte[] bArr = new byte[1024];
            while (true) {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    return stringBuffer.toString();
                }
                stringBuffer.append(new String(bArr, 0, read));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage().toString();
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void onPostExecute(String str) {
        super.onPostExecute(str);
        this.a.onCustomBrowserAsyncTaskResponse(str);
    }
}
