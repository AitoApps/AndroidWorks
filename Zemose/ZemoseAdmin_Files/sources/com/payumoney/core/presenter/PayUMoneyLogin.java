package com.payumoney.core.presenter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import com.payumoney.core.SdkSession;
import com.payumoney.core.listener.OnUserLoginListener;
import com.payumoney.core.listener.PayULoginDialogListener;
import com.payumoney.core.listener.onUserAccountReceivedListener;
import com.payumoney.core.request.LoginParamsRequest;
import com.payumoney.core.ui.PayULoginDialog;

public class PayUMoneyLogin implements PayULoginDialogListener {
    PayULoginDialog a;
    private Context b;

    public PayUMoneyLogin(Context applicationContext) {
    }

    public PayUMoneyLogin(OnUserLoginListener listener, Context applicationContext, LoginParamsRequest loginParams, String tag) {
        if (loginParams.getPassword() == null || loginParams.getPassword().equals("")) {
            listener.missingParam("Password can not be empty", tag);
        }
        if (loginParams.getUserName() == null || loginParams.getUserName().equals("")) {
            listener.missingParam("Username can not be empty", tag);
        }
        SdkSession.getInstance(applicationContext).create(loginParams.getUserName(), loginParams.getPassword(), listener, this, tag);
    }

    public void launchPayUMoneyLoginFragment(OnUserLoginListener onUserLoginListener, FragmentManager fragmentManager, int theme, String tag) {
        if (fragmentManager == null) {
            onUserLoginListener.missingParam("fragment manager is null", tag);
            return;
        }
        this.a = PayULoginDialog.newInstance(theme);
        this.a.setLoginListener(onUserLoginListener);
        this.a.setLoginDialogListener(this);
        this.a.show(fragmentManager, tag);
    }

    public void onDialogDismiss(String tag) {
        try {
            if (this.a != null && this.a.isVisible()) {
                this.a.dismiss();
            }
        } catch (Exception e) {
        }
    }

    public void onErrorOccurred(String message, String tag) {
        this.a.updateUiOnError(message);
    }

    public void getUserLoginDetails(onUserAccountReceivedListener listener, String paymentID, String tag) {
        if (paymentID == null || paymentID.trim().equalsIgnoreCase("")) {
            listener.missingParam("Invalid Payment ID", tag);
        } else {
            SdkSession.getInstance(this.b).fetchUserPaymentData(paymentID, listener, tag);
        }
    }
}
