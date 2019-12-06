package com.payumoney.core.presenter;

import android.content.Context;
import com.payumoney.core.SdkSession;
import com.payumoney.core.listener.OnFetchUserDetailsForNitroFlowListener;

public class FetchUserDetailsForNitroFlow {
    public FetchUserDetailsForNitroFlow(OnFetchUserDetailsForNitroFlowListener listener, Context applicationContext, String paymentId, String email, String phone, String tag) {
        if (paymentId == null || paymentId.isEmpty() || email == null || email.isEmpty() || phone == null || phone.isEmpty()) {
            listener.missingParam("Mandatory param is missing", tag);
        } else {
            SdkSession.getInstance(applicationContext).fetchUserDetailsForNitro(listener, paymentId, email, phone, tag);
        }
    }
}
