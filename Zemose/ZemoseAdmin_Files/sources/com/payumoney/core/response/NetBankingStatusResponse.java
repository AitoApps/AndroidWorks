package com.payumoney.core.response;

import com.payumoney.core.entity.PaymentEntity;
import java.util.ArrayList;

public class NetBankingStatusResponse extends PayUMoneyAPIResponse {
    private ArrayList<PaymentEntity> a;

    public ArrayList<PaymentEntity> getNetBankList() {
        return this.a;
    }

    public void setNetBankList(ArrayList<PaymentEntity> netBankList) {
        this.a = netBankList;
    }
}
