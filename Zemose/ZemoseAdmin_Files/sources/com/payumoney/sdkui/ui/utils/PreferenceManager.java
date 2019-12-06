package com.payumoney.sdkui.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class PreferenceManager {
    private static PreferenceManager a;
    private final SharedPreferences b;

    private PreferenceManager(Context context) {
        this.b = context.getSharedPreferences("stored_values", 0);
    }

    public static synchronized void initializeInstance(Context context) {
        synchronized (PreferenceManager.class) {
            if (a == null) {
                a = new PreferenceManager(context);
            }
        }
    }

    public static synchronized PreferenceManager getInstance() {
        PreferenceManager preferenceManager;
        synchronized (PreferenceManager.class) {
            if (a != null) {
                preferenceManager = a;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(PreferenceManager.class.getSimpleName());
                sb.append(" is not initialized, call initializeInstance(..) method first.");
                throw new IllegalStateException(sb.toString());
            }
        }
        return preferenceManager;
    }

    public String getAutoLoadSubscriptionResponse() {
        return this.b.getString("auto_load_response", "");
    }

    public void setAutoLoadSubscriptionResponse(String autoLoadSubscriptionResponse) {
        this.b.edit().putString("auto_load_response", autoLoadSubscriptionResponse).apply();
    }

    public void setUserBankDetails(String accountHolderName, String accountNo, String ifscCode) {
        Editor edit = this.b.edit();
        edit.putString("citrus_withdraw_acctname", accountHolderName);
        edit.putString("citrus_withdraw_acctnumber", accountNo);
        edit.putString("citrus_withdraw_bankifsc", ifscCode);
        edit.apply();
    }

    public String getWithdraAcctName() {
        return this.b.getString("citrus_withdraw_acctname", "");
    }

    public String getWithdraAcctNumber() {
        return this.b.getString("citrus_withdraw_acctnumber", "");
    }

    public String getWithdraBankifsc() {
        return this.b.getString("citrus_withdraw_bankifsc", "");
    }

    public String getAutoLoadThresholdAmount() {
        String string = this.b.getString("autoload_threshold_amount", "");
        if (TextUtils.isEmpty(string)) {
            return "500";
        }
        return string;
    }

    public void setAutoLoadThresholdAmount(String autoLoadThresholdAmount) {
        this.b.edit().putString("autoload_threshold_amount", autoLoadThresholdAmount).apply();
    }

    public String getAutoLoadAmount() {
        String string = this.b.getString("autoload_amount", "");
        if (TextUtils.isEmpty(string)) {
            return "500";
        }
        return string;
    }

    public void setAutoLoadAmount(String autoLoadAmount) {
        this.b.edit().putString("autoload_amount", autoLoadAmount).apply();
    }

    public boolean isAutoLoadEnable() {
        return this.b.getBoolean("auto_load_enable", false);
    }

    public void setAutoLoadEnable(boolean autoLoadEnable) {
        this.b.edit().putBoolean("auto_load_enable", autoLoadEnable).apply();
    }
}
