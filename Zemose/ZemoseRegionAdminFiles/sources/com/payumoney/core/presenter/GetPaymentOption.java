package com.payumoney.core.presenter;

import android.content.Context;
import com.payumoney.core.PayUmoneySdkInitializer.PaymentParam;
import com.payumoney.core.SdkSession;
import com.payumoney.core.listener.OnPaymentOptionReceivedListener;

public class GetPaymentOption {
    public GetPaymentOption(OnPaymentOptionReceivedListener listener, Context applicationContext, PaymentParam paymentParam, String tag) {
        SdkSession.getInstance(applicationContext).createPayment(paymentParam.getParams(), listener, tag);
    }
}
