package com.payumoney.core.presenter;

import android.content.Context;
import com.payumoney.core.SdkSession;
import com.payumoney.core.listener.OnMultipleCardBinDetailsListener;
import java.util.ArrayList;

public class GetMultipleBinDetails {
    public GetMultipleBinDetails(OnMultipleCardBinDetailsListener listener, Context applicationContext, ArrayList<String> cardNumbersList, String tag) {
        if (cardNumbersList == null || cardNumbersList.size() < 1) {
            listener.missingParam("Invalid card number list", tag);
        } else {
            SdkSession.getInstance(applicationContext).fetchMultipleCardBinInfo(listener, cardNumbersList, tag);
        }
    }
}
