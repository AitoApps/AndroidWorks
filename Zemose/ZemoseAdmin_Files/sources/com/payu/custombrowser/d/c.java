package com.payu.custombrowser.d;

import android.app.Activity;
import android.content.Context;
import com.payu.custombrowser.PayUCustomBrowserCallback;
import com.payu.custombrowser.bean.CustomBrowserConfig;
import com.payu.custombrowser.bean.CustomBrowserResultData;
import com.payu.custombrowser.bean.b;
import com.payu.custombrowser.util.CBConstant;
import com.payu.custombrowser.util.PaymentOption;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class c extends a {
    private ClassLoader b;
    private Constructor c;
    private Class d;
    private Object e;
    private InvocationHandler f = new InvocationHandler() {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equalsIgnoreCase(CBConstant.SAMSUNGPAY_SUCCESS)) {
                PayUCustomBrowserCallback payUCustomBrowserCallback = c.this.a;
                StringBuilder sb = new StringBuilder();
                sb.append(args[0]);
                sb.append("");
                payUCustomBrowserCallback.onPaymentSuccess(sb.toString(), null);
            } else if (method.getName().equalsIgnoreCase(CBConstant.SAMSUNGPAY_FAILURE)) {
                PayUCustomBrowserCallback payUCustomBrowserCallback2 = c.this.a;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(args[0]);
                sb2.append("");
                payUCustomBrowserCallback2.onPaymentFailure(sb2.toString(), null);
            } else if (method.getName().equalsIgnoreCase(CBConstant.SAMSUNGPAY_INIT_SUCCESS)) {
                CustomBrowserResultData customBrowserResultData = new CustomBrowserResultData();
                customBrowserResultData.setPaymentOption(PaymentOption.SAMSUNGPAY);
                customBrowserResultData.setSamsungPayVpa(args[0]);
                customBrowserResultData.setPaymentOptionAvailable(true);
                c.this.a.isPaymentOptionAvailable(customBrowserResultData);
                b.SINGLETON.setPaymentOption(PaymentOption.SAMSUNGPAY);
            } else if (method.getName().equalsIgnoreCase(CBConstant.SAMSUNGPAY_INIT_FAILURE)) {
                CustomBrowserResultData customBrowserResultData2 = new CustomBrowserResultData();
                customBrowserResultData2.setPaymentOption(PaymentOption.SAMSUNGPAY);
                customBrowserResultData2.setPaymentOptionAvailable(false);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(args[1]);
                sb3.append("");
                customBrowserResultData2.setErrorMessage(sb3.toString());
                c.this.a.isPaymentOptionAvailable(customBrowserResultData2);
                b.SINGLETON.removePaymentOption(PaymentOption.SAMSUNGPAY);
            }
            return null;
        }
    };

    public void a(Context context, String str, String str2, String str3) {
        if (!(b.SINGLETON == null || b.SINGLETON.getPayuCustomBrowserCallback() == null)) {
            this.a = b.SINGLETON.getPayuCustomBrowserCallback();
        }
        try {
            this.b = c.class.getClassLoader();
            this.d = this.b.loadClass("com.payu.samsungpay.SamsungWrapper");
            this.c = this.d.getDeclaredConstructor(new Class[]{InvocationHandler.class});
            this.c.setAccessible(true);
            this.e = this.c.newInstance(new Object[]{this.f});
            this.e.getClass().getMethod("checkSamsungPayAvailability", new Class[]{String.class, String.class, String.class, Context.class}).invoke(this.e, new Object[]{str, str2, str3, context});
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(Activity activity, CustomBrowserConfig customBrowserConfig) {
        try {
            if (!(b.SINGLETON == null || b.SINGLETON.getPayuCustomBrowserCallback() == null)) {
                this.a = b.SINGLETON.getPayuCustomBrowserCallback();
            }
            this.e.getClass().getMethod("makePayment", new Class[]{Activity.class, String.class}).invoke(this.e, new Object[]{activity, customBrowserConfig.getPayuPostData()});
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
