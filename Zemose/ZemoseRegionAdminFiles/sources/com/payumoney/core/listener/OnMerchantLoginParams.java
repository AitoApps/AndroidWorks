package com.payumoney.core.listener;

import com.payumoney.core.response.MerchantLoginResponse;

public interface OnMerchantLoginParams extends APICallbackListener {
    void onMerchantLoginParams(MerchantLoginResponse merchantLoginResponse, String str);
}
