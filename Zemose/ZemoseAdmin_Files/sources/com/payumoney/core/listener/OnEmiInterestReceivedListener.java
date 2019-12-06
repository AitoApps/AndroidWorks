package com.payumoney.core.listener;

import com.payumoney.core.entity.PaymentEntity;
import java.util.ArrayList;

public interface OnEmiInterestReceivedListener {
    void onUpdatedEmiInterestFailed(String str, String str2);

    void onUpdatedEmiInterestReceived(ArrayList<PaymentEntity> arrayList, String str);
}
