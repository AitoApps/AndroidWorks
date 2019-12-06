package com.payumoney.core.listener;

import com.payumoney.core.response.ErrorResponse;

public interface APICallbackListener {
    void missingParam(String str, String str2);

    void onError(String str, String str2);

    void onFailureResponse(ErrorResponse errorResponse, String str);
}
