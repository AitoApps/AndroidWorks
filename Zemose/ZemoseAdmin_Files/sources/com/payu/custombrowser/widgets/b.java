package com.payu.custombrowser.widgets;

import com.payu.custombrowser.b.a;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b implements a {
    private String a;

    private b() {
    }

    public b(String str) {
        this.a = str;
    }

    public void a() {
        try {
            if (this.a != null && this.a.length() > 0) {
                com.payu.custombrowser.bean.a aVar = new com.payu.custombrowser.bean.a();
                aVar.a("POST");
                JSONArray jSONArray = new JSONArray();
                jSONArray.put(new JSONObject(this.a));
                StringBuilder sb = new StringBuilder();
                sb.append("command=EventAnalytics&data=");
                sb.append(jSONArray.toString());
                aVar.c(sb.toString());
                aVar.b("https://info.payu.in/merchant/MobileAnalytics");
                new com.payu.custombrowser.util.b(this).execute(new com.payu.custombrowser.bean.a[]{aVar});
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onCustomBrowserAsyncTaskResponse(String cbAsyncTaskResponse) {
    }
}
