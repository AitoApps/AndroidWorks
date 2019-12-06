package com.payumoney.core.presenter;

import android.content.Context;
import com.payumoney.core.SdkSession;
import com.payumoney.core.listener.OnCardBinDetailsReceived;

public class GetBinDetails {
    public GetBinDetails(OnCardBinDetailsReceived listener, Context applicationContext, String cardNumber, String tag) {
        if (cardNumber == null || cardNumber.length() < 6) {
            listener.missingParam("Invalid card number", tag);
        } else {
            SdkSession.getInstance(applicationContext).fetchCardBinInfo(listener, cardNumber, tag);
        }
    }
}
