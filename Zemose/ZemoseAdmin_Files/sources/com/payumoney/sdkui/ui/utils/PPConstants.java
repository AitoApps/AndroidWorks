package com.payumoney.sdkui.ui.utils;

import android.support.v4.view.ViewCompat;

public class PPConstants {
    public static final String ARG_ADD_MONEY_AMOUNT = "add_money_amount";
    public static final String ARG_BANK_LIST_TYPE = "bank_list_type";
    public static final String ARG_IS_SPLIT_PAY = "is_split_pay";
    public static final String ARG_NET_BANKING_LIST = "net_banking_list";
    public static final String ARG_TRANSACTION_TYPE = "transaction_type";
    public static final String DEFAULT_BANK_NAME = "CID000";
    public static String ERROR_INTERNAL_SERVER_ERROR = "Internal Server Error Occurred.";
    public static String ERROR_SOMETHING_WENT_WRONG = "OOPS! Something Went Wrong!";
    public static double MAX_WALLET_AMOUNT_RECHARGE = 5000.0d;
    public static final int PAYU_MONEY_FLOW = 3;
    public static final int SCREEN_ADD_CARD = 1;
    public static final int SCREEN_ADD_MONEY = 4;
    public static final int SCREEN_BANK_LIST = 3;
    public static final int SCREEN_EMI_ADD_CARD = 13;
    public static final int SCREEN_EMI_TENURES = 14;
    public static final int SCREEN_GET_CVV = 11;
    public static final int SCREEN_PAYU_MONEY = 12;
    public static final int SCREEN_RESULT = 2;
    public static final int SCREEN_SHOPPING = 0;
    public static final int SCREEN_VALIDATE_WALLET = 6;
    public static final int SCREEN_WALLET = 5;
    public static final String TERMS_COND_URL = "http://www.citruspay.com/citrusbanktnc-lite.html";
    public static final String TRANS_QUICK_PAY = "trans_quick_pay";
    public static final String ZERO_AMOUNT = "0";
    public static int actionBarItemColor = ViewCompat.MEASURED_STATE_MASK;
}
