package com.payu.custombrowser;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.view.PointerIconCompat;
import android.text.TextUtils;
import com.payu.custombrowser.bean.CustomBrowserConfig;
import com.payu.custombrowser.bean.b;
import com.payu.custombrowser.c.a;
import com.payu.custombrowser.upiintent.Payment;
import com.payu.custombrowser.upiintent.PaymentResponseActivity;
import com.payu.custombrowser.upiintent.e;
import com.payu.custombrowser.util.CBConstant;
import com.payu.custombrowser.util.CBUtil;
import com.payu.custombrowser.util.PaymentOption;

public class CustomBrowser {
    public void checkForPaymentAvailability(Activity activity, @NonNull PaymentOption paymentOption, @NonNull PayUCustomBrowserCallback payUCustomBrowserCallback, String paymentOptionHash, String merchantKey, String user_credentials) {
        b.SINGLETON.setPayuCustomBrowserCallback(payUCustomBrowserCallback);
        switch (paymentOption) {
            case SAMSUNGPAY:
            case PHONEPE:
                if (CBUtil.isPaymentModuleAvailable(paymentOption)) {
                    new a(paymentOption).a().a(activity, paymentOptionHash, merchantKey, user_credentials);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Device not supported or com.payu.");
                sb.append(paymentOption.toString().toLowerCase());
                sb.append(CBConstant.IS_MISSING);
                payUCustomBrowserCallback.onCBErrorReceived(CBConstant.DEVICE_NOT_SUPPORTED_OR_MODULE_NOT_IMPORTED, sb.toString());
                return;
            default:
                return;
        }
    }

    public void addCustomBrowser(Activity activity, @NonNull CustomBrowserConfig cbCustomBrowserConfig, @NonNull PayUCustomBrowserCallback cbPayUCustomBrowserCallback) {
        com.payu.custombrowser.a.a a = com.payu.custombrowser.a.a.a(activity.getApplicationContext(), "local_cache_analytics");
        b.SINGLETON.setPayuCustomBrowserCallback(cbPayUCustomBrowserCallback);
        PaymentOption paymentOptionFromPostData = new CBUtil().getPaymentOptionFromPostData(cbCustomBrowserConfig.getPayuPostData());
        if (!TextUtils.isEmpty(cbCustomBrowserConfig.getReactVersion())) {
            a.a(CBUtil.getLogMessage(activity.getApplicationContext(), "react_version_name", cbCustomBrowserConfig.getReactVersion(), null, cbCustomBrowserConfig.getMerchantKey(), cbCustomBrowserConfig.getTransactionID(), null));
        }
        if (paymentOptionFromPostData != null) {
            if (b.SINGLETON.isPaymentOptionAvailabilityCalled(paymentOptionFromPostData)) {
                Context applicationContext = activity.getApplicationContext();
                String paymentOption = paymentOptionFromPostData.toString();
                StringBuilder sb = new StringBuilder();
                sb.append(paymentOptionFromPostData.toString());
                sb.append("_launched");
                a.a(CBUtil.getLogMessage(applicationContext, paymentOption, sb.toString(), null, cbCustomBrowserConfig.getMerchantKey(), cbCustomBrowserConfig.getTransactionID(), null));
                new a(paymentOptionFromPostData).a().a(activity, cbCustomBrowserConfig);
                return;
            }
            Context applicationContext2 = activity.getApplicationContext();
            String paymentOption2 = paymentOptionFromPostData.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(paymentOptionFromPostData.toString());
            sb2.append("_launch_failed");
            a.a(CBUtil.getLogMessage(applicationContext2, paymentOption2, sb2.toString(), null, cbCustomBrowserConfig.getMerchantKey(), cbCustomBrowserConfig.getTransactionID(), null));
            cbPayUCustomBrowserCallback.onCBErrorReceived(1021, CBConstant.CHECK_PAYMENT_NOT_CALLED_MSG);
        } else if (VERSION.SDK_INT < 16) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(VERSION.SDK_INT);
            sb3.append("");
            a.a(CBUtil.getLogMessage(activity.getApplicationContext(), "os_not_supported", sb3.toString(), null, cbCustomBrowserConfig.getMerchantKey(), cbCustomBrowserConfig.getTransactionID(), null));
            cbPayUCustomBrowserCallback.onCBErrorReceived(101, CBConstant.OS_NOT_SUPPORTED);
        } else if (VERSION.SDK_INT >= 19 && (VERSION.SDK_INT != 19 || cbCustomBrowserConfig.getGmsProviderUpdatedStatus() != -1)) {
            if (cbCustomBrowserConfig.getPayuPostData() != null && cbCustomBrowserConfig.getEnableSurePay() > 0 && cbCustomBrowserConfig.getPostURL() != null && (cbCustomBrowserConfig.getPostURL().contentEquals("https://secure.payu.in/_payment") || cbCustomBrowserConfig.getPostURL().contentEquals("https://mobiletest.payu.in/_payment") || cbCustomBrowserConfig.getPostURL().contentEquals(CBConstant.MOBILE_TEST_PAYMENT_URL_SEAMLESS) || cbCustomBrowserConfig.getPostURL().contentEquals(CBConstant.PRODUCTION_PAYMENT_URL_SEAMLESS))) {
                if (cbCustomBrowserConfig.getPayuPostData().trim().endsWith("&")) {
                    cbCustomBrowserConfig.setPayuPostData(cbCustomBrowserConfig.getPayuPostData().substring(0, cbCustomBrowserConfig.getPayuPostData().length() - 1));
                }
                StringBuilder sb4 = new StringBuilder();
                sb4.append(cbCustomBrowserConfig.getPayuPostData());
                sb4.append("&snooze=");
                sb4.append(cbCustomBrowserConfig.getEnableSurePay());
                cbCustomBrowserConfig.setPayuPostData(sb4.toString());
            }
            if (cbCustomBrowserConfig.getSurePayNotificationChannelId().equalsIgnoreCase(CBConstant.NOTIFICATION_CHANNEL_ID)) {
                a(activity);
            }
            e eVar = new e();
            Payment a2 = eVar.a(cbCustomBrowserConfig.getPayuPostData());
            if (a2 != null && eVar.a((Context) activity, a2)) {
                Context applicationContext3 = activity.getApplicationContext();
                String lowerCase = a2.toString().toLowerCase();
                StringBuilder sb5 = new StringBuilder();
                sb5.append(a2.getPaymentName().toLowerCase());
                sb5.append("_launched");
                a.a(CBUtil.getLogMessage(applicationContext3, lowerCase, sb5.toString(), null, cbCustomBrowserConfig.getMerchantKey(), cbCustomBrowserConfig.getTransactionID(), null));
                Intent intent = new Intent(activity, PaymentResponseActivity.class);
                intent.putExtra(CBConstant.POST_DATA, cbCustomBrowserConfig.getPayuPostData());
                intent.putExtra(CBConstant.PAYMENT_TYPE, a2);
                intent.putExtra(CBConstant.CB_CONFIG, cbCustomBrowserConfig);
                activity.startActivity(intent);
            } else if (a2 == null || a2.isWebFlowSupported()) {
                CBActivity.a = cbCustomBrowserConfig.getCbMenuAdapter();
                CBActivity.e = cbCustomBrowserConfig.getToolBarView();
                Intent intent2 = new Intent(activity, CBActivity.class);
                intent2.putExtra(CBConstant.CB_CONFIG, cbCustomBrowserConfig);
                if (!(cbCustomBrowserConfig.getReviewOrderDefaultViewData() == null || cbCustomBrowserConfig.getReviewOrderDefaultViewData().getReviewOrderDatas() == null)) {
                    intent2.putExtra(CBConstant.ORDER_DETAILS, cbCustomBrowserConfig.getReviewOrderDefaultViewData().getReviewOrderDatas());
                }
                activity.startActivity(intent2);
            } else {
                cbPayUCustomBrowserCallback.onCBErrorReceived(PointerIconCompat.TYPE_CONTEXT_MENU, "DEVICE_NOT_SUPPORTED");
            }
        } else if (CBUtil.isCustomTabSupported(activity.getApplicationContext())) {
            a.a(CBUtil.getLogMessage(activity.getApplicationContext(), "custom_tabs", "custom_tabs_launched", null, cbCustomBrowserConfig.getMerchantKey(), cbCustomBrowserConfig.getTransactionID(), null));
            Intent intent3 = new Intent(activity, PrePaymentsActivity.class);
            intent3.putExtra("url", cbCustomBrowserConfig.getPostURL());
            intent3.putExtra("html", cbCustomBrowserConfig.getHtmlData());
            intent3.putExtra("postdata", cbCustomBrowserConfig.getPayuPostData());
            intent3.putExtra(CBConstant.S2S_RETRY_URL, cbCustomBrowserConfig.getSurepayS2Surl());
            intent3.putExtra(CBConstant.TXNID, cbCustomBrowserConfig.getTransactionID());
            intent3.putExtra("key", cbCustomBrowserConfig.getMerchantKey());
            activity.startActivity(intent3);
        } else {
            a.a(CBUtil.getLogMessage(activity.getApplicationContext(), "custom_tabs", "custom_tabs_launch_failed", null, cbCustomBrowserConfig.getMerchantKey(), cbCustomBrowserConfig.getTransactionID(), null));
            cbPayUCustomBrowserCallback.onCBErrorReceived(103, CBConstant.CHROME_NOT_PRESENT);
        }
    }

    @TargetApi(26)
    private void a(Activity activity) {
        if (VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(CBConstant.NOTIFICATION_CHANNEL_ID, "No Internet Notification", 3);
            notificationChannel.setDescription("No Internet Notification");
            NotificationManager notificationManager = (NotificationManager) activity.getSystemService("notification");
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
    }
}
