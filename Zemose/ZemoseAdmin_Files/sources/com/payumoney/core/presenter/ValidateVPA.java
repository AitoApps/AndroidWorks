package com.payumoney.core.presenter;

import android.content.Context;
import com.payumoney.core.PayUmoneySDK;
import com.payumoney.core.R;
import com.payumoney.core.SdkSession;
import com.payumoney.core.analytics.LogAnalytics;
import com.payumoney.core.listener.OnValidateVpaListener;
import com.payumoney.core.response.ErrorResponse;
import com.payumoney.core.utils.AnalyticsConstant;
import com.payumoney.core.utils.SdkHelper;
import java.util.HashMap;

public class ValidateVPA {
    public ValidateVPA(OnValidateVpaListener listener, Context applicationContext, String vpa, String tag) {
        if (vpa == null || vpa.isEmpty() || !SdkHelper.isValidVPA(vpa)) {
            HashMap hashMap = new HashMap();
            hashMap.put(AnalyticsConstant.IS_USER_LOGGED_IN, Boolean.valueOf(PayUmoneySDK.getInstance().isUserLoggedIn()));
            hashMap.put(AnalyticsConstant.PAYMENTID, SdkSession.paymentId);
            hashMap.put(AnalyticsConstant.VPA, vpa);
            LogAnalytics.logEvent(applicationContext, AnalyticsConstant.INVALID_VPA_ENTERED, hashMap, AnalyticsConstant.CLEVERTAP);
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(applicationContext.getResources().getString(R.string.error_incorrect_upi_id));
            listener.onFailureResponse(errorResponse, tag);
            return;
        }
        SdkSession.getInstance(applicationContext).validateVPA(listener, vpa, tag);
    }
}
