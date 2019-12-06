package com.payumoney.core.listener;

import com.payumoney.core.response.BinDetail;

public interface OnCardBinDetailsReceived extends APICallbackListener {
    void onCardBinDetailReceived(BinDetail binDetail, String str);

    void onSuccess(String str, String str2);
}
