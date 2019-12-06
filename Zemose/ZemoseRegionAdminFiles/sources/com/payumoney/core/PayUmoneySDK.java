package com.payumoney.core;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import com.payumoney.core.PayUmoneySdkInitializer.PaymentParam;
import com.payumoney.core.entity.EmiThreshold;
import com.payumoney.core.listener.OnCardBinDetailsReceived;
import com.payumoney.core.listener.OnEmiInterestReceivedListener;
import com.payumoney.core.listener.OnFetchUserDetailsForNitroFlowListener;
import com.payumoney.core.listener.OnMultipleCardBinDetailsListener;
import com.payumoney.core.listener.OnNetBankingStatusListReceivedListener;
import com.payumoney.core.listener.OnOTPRequestSendListener;
import com.payumoney.core.listener.OnPaymentOptionReceivedListener;
import com.payumoney.core.listener.OnPaymentStatusReceivedListener;
import com.payumoney.core.listener.OnUserLoginListener;
import com.payumoney.core.listener.OnValidateVpaListener;
import com.payumoney.core.listener.onUserAccountReceivedListener;
import com.payumoney.core.presenter.FetchUserDetailsForNitroFlow;
import com.payumoney.core.presenter.GetBinDetails;
import com.payumoney.core.presenter.GetEmiInterestsForBank;
import com.payumoney.core.presenter.GetMultipleBinDetails;
import com.payumoney.core.presenter.GetNetBankingStatusList;
import com.payumoney.core.presenter.GetOTPForLogin;
import com.payumoney.core.presenter.GetPaymentOption;
import com.payumoney.core.presenter.MakePayment;
import com.payumoney.core.presenter.PayUMoneyLogin;
import com.payumoney.core.presenter.ValidateAccount;
import com.payumoney.core.presenter.ValidateVPA;
import com.payumoney.core.request.LoginParamsRequest;
import com.payumoney.core.request.PaymentRequest;
import com.payumoney.core.utils.PayUmoneyTransactionDetails;
import com.payumoney.core.utils.SdkHelper;
import java.util.ArrayList;
import java.util.List;

public class PayUmoneySDK {
    private static PayUmoneySDK a;
    private Context b;
    private PaymentParam c;

    public static PayUmoneySDK getInstance() {
        return a;
    }

    public static PayUmoneySDK init(Context applicationContext, PaymentParam paymentParam) {
        a = new PayUmoneySDK();
        a.a(applicationContext);
        a.setPaymentParam(paymentParam);
        SdkSession.createNewInstance(applicationContext);
        PayUmoneyTransactionDetails.initPayUMoneyTransaction();
        new SdkHelper().deviceAnalytics(applicationContext, paymentParam);
        return a;
    }

    private void a(Context context) {
        this.b = context;
    }

    public PaymentParam getPaymentParam() {
        return this.c;
    }

    public void setPaymentParam(PaymentParam paymentParam) {
        this.c = paymentParam;
    }

    public void login(OnUserLoginListener listener, LoginParamsRequest loginParamsRequest, String tag) {
        new PayUMoneyLogin(listener, this.b, loginParamsRequest, tag);
    }

    public void getNetBankingStatusList(OnNetBankingStatusListReceivedListener listener, String tag) {
        new GetNetBankingStatusList(listener, this.b, tag);
    }

    public void addPayment(OnPaymentOptionReceivedListener listener, String tag) {
        new GetPaymentOption(listener, this.b, this.c, tag);
    }

    public void validateVPA(OnValidateVpaListener listener, String vpa, String tag) {
        new ValidateVPA(listener, this.b, vpa, tag);
    }

    public void getEmiInterestForBank(OnEmiInterestReceivedListener listener, String paymentId, double amount, List<EmiThreshold> emiThresholds, String tag) {
        new GetEmiInterestsForBank(listener, this.b, paymentId, amount, emiThresholds, tag);
    }

    public void makePayment(OnPaymentStatusReceivedListener listener, PaymentRequest request, boolean validateDetails, Activity activity, String tag) {
        new MakePayment(listener, request, validateDetails, activity, tag);
    }

    public void fetchDetailsForNitroFlow(OnFetchUserDetailsForNitroFlowListener listener, String paymentId, String tag) {
        new FetchUserDetailsForNitroFlow(listener, this.b, paymentId, (String) this.c.getParams().get("email"), (String) this.c.getParams().get("phone"), tag);
    }

    public boolean isUserLoggedIn() {
        return SdkSession.getInstance(this.b).isLoggedIn();
    }

    public boolean isNitroEnabled() {
        return SdkSession.getInstance(this.b).isNitroEnabled();
    }

    public boolean isUserAccountActive() {
        return SdkSession.getInstance(this.b).isUserAccountActive();
    }

    public String getUserName() {
        return SdkSession.getInstance(this.b).getRegisteredUserName();
    }

    public boolean isMobileNumberRegistered() {
        return SdkSession.getInstance(this.b).isMobileNumberRegistered();
    }

    public void userCancelledTransaction() {
        SdkSession.getInstance(this.b).notifyUserCancelledTransaction(SdkSession.paymentId, "1");
    }

    public void logoutUser() {
        SdkSession.getInstance(this.b).logout("force");
    }

    public static boolean isUserLoggedIn(Context context) {
        return SdkSession.isLoggedIn(context);
    }

    public static void logoutUser(Context context) {
        SdkSession.logout(context);
    }

    public void requestOTPForLogin(OnOTPRequestSendListener listener, String phoneNumber, String tag) {
        new GetOTPForLogin(listener, this.b, phoneNumber, tag);
    }

    public void validateAccount(String username, String password, OnUserLoginListener onUserLoginListener, String tag) {
        new ValidateAccount(this.b, username, password, onUserLoginListener, tag);
    }

    public void launchLoginScreen(OnUserLoginListener listener, FragmentManager fragmentManager, int theme, String tag) {
        new PayUMoneyLogin(this.b).launchPayUMoneyLoginFragment(listener, fragmentManager, theme, tag);
    }

    public void getUserAccount(onUserAccountReceivedListener listener, String paymentID, String tag) {
        new PayUMoneyLogin(this.b).getUserLoginDetails(listener, paymentID, tag);
    }

    public static void setINSTANCE(PayUmoneySDK INSTANCE) {
        a = INSTANCE;
    }

    public void getCardBinDetails(OnCardBinDetailsReceived listener, String cardNumber, String tag) {
        new GetBinDetails(listener, this.b, cardNumber, tag);
    }

    public void getMultipleCardBinDetails(OnMultipleCardBinDetailsListener listener, ArrayList<String> cardNumbersList, String tag) {
        new GetMultipleBinDetails(listener, this.b, cardNumbersList, tag);
    }

    public boolean isUserSignUpDisabled() {
        return SdkSession.getInstance(this.b).isUserSignUpDisabled();
    }
}
