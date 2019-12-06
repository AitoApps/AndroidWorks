package com.payumoney.core.listener;

import org.json.JSONObject;

public interface OnPaymentDetailsReceivedFromPayuMoney extends APICallbackListener {
    void OnPaymentDetailsReceivedFromPayuMoney(JSONObject jSONObject, String str);
}
