package com.payumoney.core.listener;

import com.payumoney.core.response.ErrorResponse;

public interface OnValidateVpaListener {
    void onFailureResponse(ErrorResponse errorResponse, String str);

    void onSuccess(boolean z, String str);
}
