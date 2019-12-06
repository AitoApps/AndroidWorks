package com.payumoney.core.presenter;

import android.content.Context;
import com.payumoney.core.SdkSession;
import com.payumoney.core.listener.OnUserLoginListener;
import com.payumoney.core.utils.SdkHelper;

public class ValidateAccount {
    public ValidateAccount(Context applicationContext, String username, String password, OnUserLoginListener onUserLoginListener, String tag) {
        if (username == null || username.trim().equalsIgnoreCase("") || password == null || password.trim().equalsIgnoreCase("")) {
            onUserLoginListener.missingParam("Invalid params", tag);
            return;
        }
        if (SdkHelper.isValidateUsername(username)) {
            SdkSession.getInstance(applicationContext).create(username, password, onUserLoginListener, null, tag);
        } else {
            onUserLoginListener.missingParam("Invalid params", tag);
        }
    }
}
