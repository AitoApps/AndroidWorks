package com.payu.custombrowser.d;

import android.app.Activity;
import android.content.Context;
import com.payu.custombrowser.PayUCustomBrowserCallback;
import com.payu.custombrowser.bean.CustomBrowserConfig;

public abstract class a {
    protected PayUCustomBrowserCallback a;

    public abstract void a(Activity activity, CustomBrowserConfig customBrowserConfig);

    public abstract void a(Context context, String str, String str2, String str3);
}
