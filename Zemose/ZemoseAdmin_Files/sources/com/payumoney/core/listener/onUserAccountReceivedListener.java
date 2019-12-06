package com.payumoney.core.listener;

import com.payumoney.core.response.UserDetail;

public interface onUserAccountReceivedListener extends APICallbackListener {
    void OnUserPaymentDetailsReceived(UserDetail userDetail, String str);

    void onSuccess(String str, String str2);
}
