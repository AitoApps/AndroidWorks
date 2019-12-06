package com.payumoney.sdkui.presenter.fragmentPresenter;

public interface IWithdrawMoneyPresenter {
    void notifySaveCashOutInfoSuccess();

    void sendOneTimePassword(String str);

    void verifyOneTimePassword(String str, String str2, String str3, String str4, String str5);
}
