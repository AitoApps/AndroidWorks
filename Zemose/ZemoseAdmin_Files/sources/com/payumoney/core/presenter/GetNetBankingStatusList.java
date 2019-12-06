package com.payumoney.core.presenter;

import android.content.Context;
import com.payumoney.core.SdkSession;
import com.payumoney.core.listener.OnNetBankingStatusListReceivedListener;

public class GetNetBankingStatusList {
    public GetNetBankingStatusList(OnNetBankingStatusListReceivedListener listener, Context applicationContext, String tag) {
        SdkSession.getInstance(applicationContext).getNetBankingStatus(listener, tag);
    }
}
