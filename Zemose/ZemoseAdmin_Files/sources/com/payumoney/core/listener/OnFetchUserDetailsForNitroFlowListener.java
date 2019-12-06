package com.payumoney.core.listener;

import com.payumoney.core.response.UserDetail;

public interface OnFetchUserDetailsForNitroFlowListener extends APICallbackListener {
    void onSuccess(String str, String str2);

    void onUserDetailsReceivedForNitroFlow(UserDetail userDetail, String str);
}
