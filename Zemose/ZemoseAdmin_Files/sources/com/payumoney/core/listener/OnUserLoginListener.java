package com.payumoney.core.listener;

import com.payumoney.core.response.PayUMoneyLoginResponse;

public interface OnUserLoginListener extends APICallbackListener {
    void onDismissLoginDialog();

    void onSuccessfulLogin(PayUMoneyLoginResponse payUMoneyLoginResponse, String str);
}
