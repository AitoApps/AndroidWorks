package com.payumoney.core.listener;

import com.payumoney.core.response.PaymentOptionDetails;

public interface OnPaymentOptionReceivedListener extends APICallbackListener {
    void onPaymentOptionReceived(PaymentOptionDetails paymentOptionDetails, String str);

    void onSuccess(String str, String str2);
}
