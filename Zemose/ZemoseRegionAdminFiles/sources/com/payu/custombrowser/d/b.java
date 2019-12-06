package com.payu.custombrowser.d;

import android.app.Activity;
import android.content.Context;
import com.payu.custombrowser.PayUCustomBrowserCallback;
import com.payu.custombrowser.a.a;
import com.payu.custombrowser.bean.CustomBrowserConfig;
import com.payu.custombrowser.bean.CustomBrowserResultData;
import com.payu.custombrowser.upiintent.e;
import com.payu.custombrowser.util.CBConstant;
import com.payu.custombrowser.util.CBUtil;
import com.payu.custombrowser.util.PaymentOption;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class b extends a {
    private ClassLoader b;
    private Constructor c;
    private Class d;
    private Object e;
    /* access modifiers changed from: private */
    public a f;
    /* access modifiers changed from: private */
    public Activity g;
    /* access modifiers changed from: private */
    public CustomBrowserConfig h;
    /* access modifiers changed from: private */
    public e i;
    private InvocationHandler j = new InvocationHandler() {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equalsIgnoreCase(CBConstant.PAYMENT_OPTION_SUCCESS)) {
                b.this.f.a(CBUtil.getLogMessage(b.this.g.getApplicationContext(), "trxn_status", "success_transaction", null, b.this.i.a(b.this.h.getPayuPostData(), "key"), b.this.i.a(b.this.h.getPayuPostData(), CBConstant.TXNID), null));
                PayUCustomBrowserCallback payUCustomBrowserCallback = b.this.a;
                StringBuilder sb = new StringBuilder();
                sb.append(args[0]);
                sb.append("");
                payUCustomBrowserCallback.onPaymentSuccess(sb.toString(), null);
            } else if (method.getName().equalsIgnoreCase(CBConstant.PAYMENT_OPTION_FAILURE)) {
                b.this.f.a(CBUtil.getLogMessage(b.this.g.getApplicationContext(), "trxn_status", "failure_transaction", null, b.this.i.a(b.this.h.getPayuPostData(), "key"), b.this.i.a(b.this.h.getPayuPostData(), CBConstant.TXNID), null));
                PayUCustomBrowserCallback payUCustomBrowserCallback2 = b.this.a;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(args[0]);
                sb2.append("");
                payUCustomBrowserCallback2.onPaymentFailure(sb2.toString(), null);
            } else if (method.getName().equalsIgnoreCase(CBConstant.PAYMENT_OPTION_INIT_SUCCESS)) {
                CustomBrowserResultData customBrowserResultData = new CustomBrowserResultData();
                customBrowserResultData.setPaymentOption(PaymentOption.PHONEPE);
                customBrowserResultData.setPaymentOptionAvailable(true);
                b.this.a.isPaymentOptionAvailable(customBrowserResultData);
                com.payu.custombrowser.bean.b.SINGLETON.setPaymentOption(PaymentOption.PHONEPE);
            } else if (method.getName().equalsIgnoreCase(CBConstant.PAYMENT_OPTION_INIT_FAILURE)) {
                CustomBrowserResultData customBrowserResultData2 = new CustomBrowserResultData();
                customBrowserResultData2.setPaymentOption(PaymentOption.PHONEPE);
                customBrowserResultData2.setPaymentOptionAvailable(false);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(args[1]);
                sb3.append("");
                customBrowserResultData2.setErrorMessage(sb3.toString());
                b.this.a.isPaymentOptionAvailable(customBrowserResultData2);
                com.payu.custombrowser.bean.b.SINGLETON.removePaymentOption(PaymentOption.PHONEPE);
            }
            return null;
        }
    };

    public void a(Activity activity, CustomBrowserConfig customBrowserConfig) {
        try {
            if (!(com.payu.custombrowser.bean.b.SINGLETON == null || com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback() == null)) {
                this.a = com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback();
            }
            if (this.e == null) {
                a();
            }
            this.g = activity;
            this.i = new e();
            this.h = customBrowserConfig;
            this.f = a.a(activity.getApplicationContext(), "local_cache_analytics");
            boolean z = false;
            Method method = this.e.getClass().getMethod("makePayment", new Class[]{Activity.class, String.class, Boolean.class});
            Object obj = this.e;
            Object[] objArr = new Object[3];
            objArr[0] = activity;
            objArr[1] = customBrowserConfig.getPayuPostData();
            if (customBrowserConfig.getIsPhonePeUserCacheEnabled() == 0) {
                z = true;
            }
            objArr[2] = Boolean.valueOf(z);
            method.invoke(obj, objArr);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void a() {
        try {
            this.b = b.class.getClassLoader();
            this.d = this.b.loadClass("com.payu.phonepe.PhonePeWrapper");
            this.c = this.d.getDeclaredConstructor(new Class[]{InvocationHandler.class});
            this.c.setAccessible(true);
            this.e = this.c.newInstance(new Object[]{this.j});
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(Context context, String str, String str2, String str3) {
        if (!(com.payu.custombrowser.bean.b.SINGLETON == null || com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback() == null)) {
            this.a = com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback();
        }
        try {
            a();
            this.e.getClass().getMethod("checkForPaymentAvailability", new Class[]{Context.class, String.class, String.class, String.class}).invoke(this.e, new Object[]{context, str, str2, str3});
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
