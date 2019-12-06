package com.payumoney.core.listener;

import com.payumoney.core.response.NetBankingStatusResponse;

public interface OnNetBankingStatusListReceivedListener extends APICallbackListener {
    void OnNetBankingListReceived(NetBankingStatusResponse netBankingStatusResponse, String str);

    void onSuccess(String str, String str2);
}
