package com.payumoney.core.presenter;

import android.content.Context;
import com.payumoney.core.SdkSession;
import com.payumoney.core.listener.OnOTPRequestSendListener;
import com.payumoney.core.utils.SdkHelper;

public class GetOTPForLogin {
    public GetOTPForLogin(OnOTPRequestSendListener listener, Context applicationContext, String phoneNumber, String tag) {
        if (phoneNumber == null || phoneNumber.trim().equalsIgnoreCase("")) {
            listener.missingParam("Invalid params", tag);
            return;
        }
        if (SdkHelper.isValidateUsername(phoneNumber)) {
            SdkSession.getInstance(applicationContext).sendOTPsForLoginSignUP(phoneNumber, listener, tag);
        } else {
            listener.missingParam("Invalid params", tag);
        }
    }
}
