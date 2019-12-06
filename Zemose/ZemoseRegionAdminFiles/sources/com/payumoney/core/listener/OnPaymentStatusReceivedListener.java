package com.payumoney.core.listener;

public interface OnPaymentStatusReceivedListener extends APICallbackListener {
    void onCancelled(String str, String str2);

    void onFailure(String str, String str2, String str3);

    void onSuccess(String str, String str2, String str3);
}
