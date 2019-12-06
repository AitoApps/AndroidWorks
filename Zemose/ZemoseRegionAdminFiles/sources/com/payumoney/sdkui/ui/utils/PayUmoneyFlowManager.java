package com.payumoney.sdkui.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Keep;
import com.payumoney.core.PayUmoneySDK;
import com.payumoney.core.PayUmoneySdkInitializer.PaymentParam;
import com.payumoney.core.utils.SdkHelper;
import com.payumoney.graphics.AssetDownloadManager;
import com.payumoney.graphics.AssetsHelper.SDK_TYPE;
import com.payumoney.sdkui.BuildConfig;
import com.payumoney.sdkui.ui.activities.PayUmoneyActivity;

@Keep
public class PayUmoneyFlowManager {
    public static final String ARG_RESULT = "result";
    public static String INTENT_EXTRA_TRANSACTION_RESPONSE = "INTENT_EXTRA_TRANSACTION_RESPONSE";
    public static final String KEY_AMOUNT = "key_amount";
    public static final String KEY_EMAIL = "key_email";
    public static final String KEY_FLOW = "key_flow";
    public static final String KEY_MOBILE = "key_mobile";
    public static final String KEY_STYLE = "key_style";
    public static final String OVERRIDE_RESULT_SCREEN = "override_result_screen";
    public static int REQUEST_CODE_PAYMENT = 10000;
    public static int REQ_CODE_SET_ORDER_ITEMS = 9999;
    public static int theme;

    public static Intent getIntentToStartPayUMoneyFlow(PaymentParam paymentParam, Activity context, int style, boolean isOverrideResultScreen) {
        SdkHelper.pnpVersion = BuildConfig.VERSION_NAME;
        PayUmoneySDK.init(context.getApplicationContext(), paymentParam);
        AssetDownloadManager.getInstance().init(context.getApplicationContext(), SDK_TYPE.PLUG_N_PLAY);
        Intent intent = new Intent(context, PayUmoneyActivity.class);
        intent.putExtra(KEY_FLOW, 3);
        intent.putExtra(KEY_STYLE, style);
        intent.putExtra(OVERRIDE_RESULT_SCREEN, isOverrideResultScreen);
        return intent;
    }

    public static void startPayUMoneyFlow(PaymentParam paymentParam, Activity context, int style, boolean isOverrideResultScreen) {
        context.startActivityForResult(getIntentToStartPayUMoneyFlow(paymentParam, context, style, isOverrideResultScreen), REQUEST_CODE_PAYMENT);
    }

    public static boolean isUserLoggedIn(Context context) {
        return PayUmoneySDK.isUserLoggedIn(context);
    }

    public static void logoutUser(Context context) {
        PayUmoneySDK.logoutUser(context);
    }
}
