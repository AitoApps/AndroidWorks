package com.payumoney.core.listener;

import com.payumoney.core.response.BinDetail;
import java.util.HashMap;

public interface OnMultipleCardBinDetailsListener extends APICallbackListener {
    void onMultipleCardBinDetailsReceived(HashMap<String, BinDetail> hashMap, String str);

    void onSuccess(String str, String str2);
}
