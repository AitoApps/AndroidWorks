package com.payumoney.core.presenter;

import android.content.Context;
import com.payumoney.core.SdkSession;
import com.payumoney.core.entity.EmiThreshold;
import com.payumoney.core.listener.OnEmiInterestReceivedListener;
import java.util.List;

public class GetEmiInterestsForBank {
    public GetEmiInterestsForBank(OnEmiInterestReceivedListener listener, Context applicationContext, String paymentId, double totalAmount, List<EmiThreshold> emiThresholds, String tag) {
        SdkSession.getInstance(applicationContext).getEmiInterestsForBank(paymentId, totalAmount, listener, emiThresholds, tag);
    }
}
