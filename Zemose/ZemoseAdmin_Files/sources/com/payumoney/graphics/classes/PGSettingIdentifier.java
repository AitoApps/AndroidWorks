package com.payumoney.graphics.classes;

import java.util.ArrayList;
import java.util.List;

public class PGSettingIdentifier {
    private List<String> creditCard = new ArrayList();
    private List<String> debitCard = new ArrayList();
    private Boolean lazyPayEnabled;
    private Boolean mcpEnabled;
    private List<NetBanking> netBanking = new ArrayList();
    private Boolean prepaid;

    public List<String> getCreditCard() {
        return this.creditCard;
    }

    public void setCreditCard(List<String> creditCard2) {
        this.creditCard = creditCard2;
    }

    public List<String> getDebitCard() {
        return this.debitCard;
    }

    public void setDebitCard(List<String> debitCard2) {
        this.debitCard = debitCard2;
    }

    public List<NetBanking> getNetBanking() {
        return this.netBanking;
    }

    public void setNetBanking(List<NetBanking> netBanking2) {
        this.netBanking = netBanking2;
    }
}
