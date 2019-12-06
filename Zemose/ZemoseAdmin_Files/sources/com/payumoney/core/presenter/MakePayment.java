package com.payumoney.core.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySDK;
import com.payumoney.core.R;
import com.payumoney.core.SdkSession;
import com.payumoney.core.SdkWebViewActivityNew;
import com.payumoney.core.analytics.LogAnalytics;
import com.payumoney.core.listener.OnPaymentDetailsReceivedFromPayuMoney;
import com.payumoney.core.listener.OnPaymentStatusReceivedListener;
import com.payumoney.core.request.PaymentRequest;
import com.payumoney.core.response.ErrorResponse;
import com.payumoney.core.utils.AnalyticsConstant;
import com.payumoney.core.utils.PayUmoneyTransactionDetails;
import com.payumoney.core.utils.SdkHelper;
import com.payumoney.core.utils.SdkLogger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.HashMap;
import org.json.JSONObject;

public class MakePayment implements OnPaymentDetailsReceivedFromPayuMoney {
    public final int a = 2;
    OnPaymentStatusReceivedListener b;
    Activity c;
    PaymentRequest d;
    private final int e = 1;
    private final int f = 0;
    private ProgressDialog g;
    /* access modifiers changed from: private */
    public BroadcastReceiver h = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (MakePayment.this.c != null && !MakePayment.this.c.isFinishing()) {
                LocalBroadcastManager.getInstance(MakePayment.this.c).unregisterReceiver(MakePayment.this.h);
                String stringExtra = intent.getStringExtra("eventname");
                String stringExtra2 = intent.getStringExtra("payuresponse");
                String stringExtra3 = intent.getStringExtra("merchantresponse");
                String stringExtra4 = intent.getStringExtra("TAG") == null ? "TAG" : intent.getStringExtra("TAG");
                if (stringExtra.equalsIgnoreCase("onPaymentSuccess")) {
                    MakePayment.this.b.onSuccess(stringExtra2, stringExtra3, stringExtra4);
                } else if (stringExtra.equalsIgnoreCase("onPaymentFailure")) {
                    MakePayment.this.b.onFailure(stringExtra2, stringExtra3, stringExtra4);
                }
                if (stringExtra.equalsIgnoreCase("onPaymentCancelled")) {
                    MakePayment.this.b.onCancelled(stringExtra2, stringExtra4);
                }
            }
        }
    };

    public MakePayment(OnPaymentStatusReceivedListener listener, PaymentRequest request, boolean validateCardDetails, Activity activity, String tag) {
        this.b = listener;
        this.d = request;
        this.c = activity;
        HashMap hashMap = new HashMap();
        hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        if (request.getPg() != null) {
            if (request.getPg().equals(PayUmoneyConstants.PAYMENT_MODE_NB)) {
                if (request.getBankCode() == null || request.getBankCode().trim().equalsIgnoreCase("")) {
                    listener.missingParam("bank code is empty", tag);
                    return;
                } else if (request.getPaymentID() == null || request.getPaymentID().trim().equalsIgnoreCase("")) {
                    listener.missingParam("payment ID is empty", tag);
                    return;
                } else if (request.getBankCode().equalsIgnoreCase("CITNB")) {
                    listener.missingParam("CITI Netbanking not allowed", tag);
                    return;
                } else {
                    hashMap.put(AnalyticsConstant.PAYMENT_METHOD_SELECTED, "Bank");
                }
            }
            if (request.getPg().equals(PayUmoneyConstants.PAYMENT_MODE_UPI)) {
                if (request.getBankCode() == null || request.getBankCode().trim().equalsIgnoreCase("")) {
                    listener.missingParam("bank code is empty", tag);
                    return;
                } else if (request.getPaymentID() == null || request.getPaymentID().trim().equalsIgnoreCase("")) {
                    listener.missingParam("payment ID is empty", tag);
                    return;
                } else if (request.getVpa() == null || request.getVpa().trim().equalsIgnoreCase("")) {
                    listener.missingParam("vpa is empty", tag);
                    return;
                } else if (!validateCardDetails || SdkHelper.isValidVPA(request.getVpa())) {
                    hashMap.put(AnalyticsConstant.PAYMENT_METHOD_SELECTED, PayUmoneyConstants.PAYMENT_MODE_UPI);
                } else {
                    ErrorResponse errorResponse = new ErrorResponse();
                    errorResponse.setMessage(activity.getResources().getString(R.string.error_incorrect_upi_id));
                    listener.onFailureResponse(errorResponse, tag);
                    return;
                }
            }
            if (request.getPg().equals(PayUmoneyConstants.WALLET)) {
                if (Double.parseDouble((String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT)) + request.getConvenienceFee() > PayUmoneyTransactionDetails.getInstance().getWalletAmount()) {
                    listener.missingParam("Insufficient amount in wallet", tag);
                    return;
                } else if (Double.parseDouble((String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT)) < 1.0d) {
                    listener.missingParam("Amount less than 1 rupee for wallet transaction not allowed", tag);
                    return;
                } else {
                    hashMap.put(AnalyticsConstant.PAYMENT_METHOD_SELECTED, "PUMWallet");
                }
            }
            if (request.getPg().equals(PayUmoneyConstants.PAYMENT_MODE_CC) || request.getPg().equals(PayUmoneyConstants.PAYMENT_MODE_DC)) {
                if (request.getCardNumber() == null && request.getCardtoken() == null) {
                    listener.missingParam("Card detail empty", tag);
                    return;
                } else if ((request.getCardNumber() != null && request.getCountryCode() == null) || ((request.getCountryCode() != null && request.getCountryCode().isEmpty()) || (request.getCountryCode() != null && request.getCountryCode().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)))) {
                    listener.missingParam("country code is empty", tag);
                    return;
                } else if (request.getCardNumber() != null && request.getCountryCode() != null && request.isStoreCard() && !request.getCountryCode().equalsIgnoreCase("IN")) {
                    listener.missingParam("International Cards cannot be saved to user account (Err: storeCard=true; should be storeCard=false)", tag);
                    return;
                } else if (request.getBankCode() == null || request.getBankCode().isEmpty()) {
                    listener.missingParam("bank code is empty", tag);
                    return;
                } else if (!PayUmoneySDK.getInstance().isUserLoggedIn() && ((!PayUmoneySDK.getInstance().isNitroEnabled() || !PayUmoneySDK.getInstance().isUserAccountActive()) && request.isStoreCard() && PayUmoneySDK.getInstance().isUserSignUpDisabled())) {
                    listener.missingParam("Cards cannot be saved for guest users", tag);
                    return;
                } else if (!validateCardDetails || validateCardDetails(listener, request, tag)) {
                    if (validateCardDetails) {
                        if (request.getCvv() == null || request.getCvv().trim().equalsIgnoreCase("")) {
                            request.setCvv("123");
                        }
                        if (request.getCardtoken() == null) {
                            if (request.getExpiryMonth() == null || request.getExpiryMonth().trim().equalsIgnoreCase("")) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(Calendar.getInstance().get(2) + 1);
                                sb.append("");
                                request.setExpiryMonth(sb.toString());
                            }
                            if (request.getExpiryYear() == null || request.getExpiryYear().trim().equalsIgnoreCase("")) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(Calendar.getInstance().get(1) + 5);
                                sb2.append("");
                                request.setExpiryYear(sb2.toString());
                            }
                        }
                    }
                    if (request.getPaymentID() == null) {
                        listener.missingParam("payment ID is empty", tag);
                        return;
                    }
                    hashMap.put(AnalyticsConstant.PAYMENT_METHOD_SELECTED, "Cards");
                } else {
                    return;
                }
            }
            if (request.getPg().equals("EMI")) {
                if (request.getCardNumber() == null && request.getCardtoken() == null) {
                    listener.missingParam("Card detail empty", tag);
                    return;
                } else if ((request.getCardNumber() != null && request.getCountryCode() == null) || ((request.getCountryCode() != null && request.getCountryCode().isEmpty()) || (request.getCountryCode() != null && request.getCountryCode().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)))) {
                    listener.missingParam("country code is empty", tag);
                    return;
                } else if (request.getCardNumber() != null && request.getCountryCode() != null && request.isStoreCard() && !request.getCountryCode().equalsIgnoreCase("IN")) {
                    listener.missingParam("International Cards cannot be saved to user account (Err: storeCard=true; should be storeCard=false)", tag);
                    return;
                } else if (request.getBankCode() == null || request.getBankCode().isEmpty()) {
                    listener.missingParam("bank code is empty", tag);
                    return;
                } else if (!PayUmoneySDK.getInstance().isUserLoggedIn() && ((!PayUmoneySDK.getInstance().isNitroEnabled() || !PayUmoneySDK.getInstance().isUserAccountActive()) && request.isStoreCard() && PayUmoneySDK.getInstance().isUserSignUpDisabled())) {
                    listener.missingParam("Cards cannot be saved for guest users", tag);
                    return;
                } else if (!validateCardDetails || validateCardDetails(listener, request, tag)) {
                    if (validateCardDetails) {
                        if (request.getCvv() == null || request.getCvv().trim().equalsIgnoreCase("")) {
                            request.setCvv("123");
                        }
                        if (request.getCardtoken() == null) {
                            if (request.getExpiryMonth() == null || request.getExpiryMonth().trim().equalsIgnoreCase("")) {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(Calendar.getInstance().get(2) + 1);
                                sb3.append("");
                                request.setExpiryMonth(sb3.toString());
                            }
                            if (request.getExpiryYear() == null || request.getExpiryYear().trim().equalsIgnoreCase("")) {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append(Calendar.getInstance().get(1) + 5);
                                sb4.append("");
                                request.setExpiryYear(sb4.toString());
                            }
                        }
                    }
                    if (request.getPaymentID() == null) {
                        listener.missingParam("payment ID is empty", tag);
                        return;
                    }
                    hashMap.put(AnalyticsConstant.PAYMENT_METHOD_SELECTED, "EMI");
                } else {
                    return;
                }
            }
            if (request.getPg().equals(PayUmoneyConstants.PAYMENT_MODE_CASH_CARD)) {
                if (request.getBankCode() == null || request.getBankCode().trim().equalsIgnoreCase("")) {
                    listener.missingParam("bank code is empty", tag);
                    return;
                } else if (request.getPaymentID() == null || request.getPaymentID().trim().equalsIgnoreCase("")) {
                    listener.missingParam("payment ID is empty", tag);
                    return;
                } else if (request.isSplitPayment()) {
                    listener.missingParam("split payment not allowed", tag);
                    return;
                } else {
                    hashMap.put(AnalyticsConstant.PAYMENT_METHOD_SELECTED, "3PWallet");
                    HashMap hashMap2 = new HashMap();
                    hashMap2.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                    hashMap2.put(AnalyticsConstant.WALLET_SELECTED, request.getBankCode());
                    LogAnalytics.logEvent(activity.getApplicationContext(), AnalyticsConstant.THREE_P_WALLET_PAYMENT, hashMap2, AnalyticsConstant.CLEVERTAP);
                }
            }
            if (request.isSplitPayment()) {
                if (!PayUmoneySDK.isUserLoggedIn(activity.getApplicationContext())) {
                    listener.missingParam("Split payment not allowed - user is not logged in", tag);
                    return;
                } else if (PayUmoneyTransactionDetails.getInstance().getWalletAmount() < 1.0d) {
                    listener.missingParam("Wallet balance less then 1 rupee transaction not allowed ", tag);
                    return;
                } else if (PayUmoneyTransactionDetails.getInstance().getWalletAmount() >= Double.parseDouble((String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT)) + request.getConvenienceFee()) {
                    listener.missingParam("Split payment not allowed", tag);
                    return;
                } else {
                    String str = AnalyticsConstant.PAYMENT_METHOD_SELECTED;
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("PUMWallet|");
                    sb5.append(hashMap.get(AnalyticsConstant.PAYMENT_METHOD_SELECTED));
                    hashMap.put(str, sb5.toString());
                }
            }
            LogAnalytics.logEvent(activity.getApplicationContext(), AnalyticsConstant.PAYMENT_OPTION_SELECTED, hashMap, AnalyticsConstant.CLEVERTAP);
            if (activity != null && !activity.isFinishing()) {
                SdkSession.getInstance(activity.getApplicationContext()).sendToPayU(request, this, tag);
                this.g = new ProgressDialog(activity);
                this.g.setMessage("Please Wait...");
                this.g.show();
            }
            return;
        }
        listener.missingParam("invalid pg value", tag);
    }

    public static PublicKey getPublicKey(String key) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(key.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "").trim(), 0));
        SdkLogger.d(PayUmoneyConstants.TAG, new String(x509EncodedKeySpec.getEncoded()));
        return KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
    }

    public void OnPaymentDetailsReceivedFromPayuMoney(JSONObject jsonObject, String tag) {
        try {
            if (jsonObject.has(PayUmoneyConstants.TRANSACTION_DTO) && !jsonObject.getString(PayUmoneyConstants.TRANSACTION_DTO).equals(PayUmoneyConstants.NULL_STRING)) {
                if (jsonObject.getJSONObject(PayUmoneyConstants.TRANSACTION_DTO).has("hash")) {
                    if (jsonObject.getJSONObject(PayUmoneyConstants.TRANSACTION_DTO).get("hash") != null) {
                        if (!jsonObject.getJSONObject(PayUmoneyConstants.TRANSACTION_DTO).get("hash").toString().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                            JSONObject jsonObject2 = jsonObject.getJSONObject(PayUmoneyConstants.TRANSACTION_DTO).getJSONObject("hash");
                            if (this.g != null) {
                                this.g.dismiss();
                            }
                            Intent intent = new Intent(this.c.getBaseContext(), SdkWebViewActivityNew.class);
                            intent.putExtra("result", jsonObject2.toString());
                            intent.putExtra(PayUmoneyConstants.PAYMENT_MODE, this.d.getPg());
                            intent.putExtra(PayUmoneyConstants.PAYMENT_ID, this.d.getPaymentID());
                            intent.putExtra(PayUmoneyConstants.PAYMENT_REQUEST, this.d);
                            if (!this.d.getPg().equals(PayUmoneyConstants.PAYMENT_MODE_NB)) {
                                if (!this.d.getPg().equals(PayUmoneyConstants.PAYMENT_MODE_UPI)) {
                                    if (this.d.getCardName() != null) {
                                        intent.putExtra("card_name", this.d.getCardName());
                                    } else {
                                        intent.putExtra("card_name", "");
                                    }
                                    if (this.d.getCardtoken() != null) {
                                        intent.putExtra(PayUmoneyConstants.STORE_CARD_TOKEN, this.d.getCardtoken());
                                    }
                                    if (this.d.isStoreCard()) {
                                        intent.putExtra(PayUmoneyConstants.STORE_CARD, "1");
                                    } else {
                                        intent.putExtra(PayUmoneyConstants.STORE_CARD, "0");
                                    }
                                }
                            }
                            if (this.d.getBankCode() != null) {
                                intent.putExtra(PayUmoneyConstants.BANK_CODE, this.d.getBankCode());
                            }
                            intent.putExtra(PayUmoneyConstants.OTP_AUTO_READ, true);
                            intent.putExtra("merchantKey", (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get("merchantKey"));
                            try {
                                intent.putExtra(PayUmoneyConstants.PAYMENT_ID, jsonObject2.getJSONObject("payment").getString(PayUmoneyConstants.PAYMENT_ID));
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                            Activity activity = this.c;
                            getClass();
                            activity.startActivityForResult(intent, 2);
                        }
                    }
                }
                if (this.g != null) {
                    this.g.dismiss();
                }
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage(PayUmoneyConstants.ERROR_SOMETHING_WENT_WRONG);
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                    hashMap.put(AnalyticsConstant.REASON, PayUmoneyConstants.ERROR_SOMETHING_WENT_WRONG);
                    hashMap.put(AnalyticsConstant.AMOUNT, Double.valueOf(Double.parseDouble((String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT)) + this.d.getConvenienceFee()));
                    LogAnalytics.logEvent(this.c.getApplicationContext(), AnalyticsConstant.PAYMENT_FAILED, hashMap, AnalyticsConstant.CLEVERTAP);
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                this.b.onFailureResponse(errorResponse, tag);
                return;
            } else if (jsonObject.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("frmPostBack")) {
                if (this.g != null) {
                    this.g.dismiss();
                }
                if (jsonObject.has("result") && jsonObject.getJSONObject("result").has("status")) {
                    if (jsonObject.getJSONObject("result").getString("status").equalsIgnoreCase(PayUmoneyConstants.SUCCESS_STRING)) {
                        this.b.onSuccess(jsonObject.toString(), "", tag);
                    } else {
                        this.b.onFailure(jsonObject.toString(), "", tag);
                    }
                }
                LocalBroadcastManager.getInstance(this.c).registerReceiver(this.h, new IntentFilter("paymentstatus"));
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            ProgressDialog progressDialog = this.g;
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
        LocalBroadcastManager.getInstance(this.c).registerReceiver(this.h, new IntentFilter("paymentstatus"));
    }

    public void onError(String response, String tag) {
        this.b.onError(response, tag);
    }

    public void missingParam(String description, String tag) {
    }

    public void onFailureResponse(ErrorResponse response, String tag) {
        ProgressDialog progressDialog = this.g;
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        this.b.onFailureResponse(response, tag);
    }

    public boolean validateCardDetails(OnPaymentStatusReceivedListener listener, PaymentRequest request, String tag) {
        HashMap hashMap = new HashMap();
        hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        if (request.getProcessor() == null || request.getProcessor().equalsIgnoreCase("")) {
            listener.missingParam("Issuer is empty", tag);
            return false;
        }
        if (request.getCardtoken() == null) {
            if (!SdkHelper.validateCardNumber(request.getCardNumber(), request.getProcessor())) {
                listener.missingParam("Invalid Card Number", tag);
                hashMap.put(AnalyticsConstant.PAYMENT_METHOD_SELECTED, "Card");
                hashMap.put(AnalyticsConstant.REASON, "Invalid Card Number");
                LogAnalytics.logEvent(this.c.getApplicationContext(), AnalyticsConstant.INVALID_PAYMENT_INFO, hashMap, AnalyticsConstant.CLEVERTAP);
                return false;
            } else if (!SdkHelper.isExpiryValid(request.getExpiryMonth(), request.getExpiryYear(), request.getProcessor())) {
                listener.missingParam("Invalid card expiry details", tag);
                return false;
            }
        }
        if (SdkHelper.isValidCvv(request.getCvv(), request.getProcessor())) {
            return true;
        }
        listener.missingParam("Invalid CVV", tag);
        hashMap.put(AnalyticsConstant.PAYMENT_METHOD_SELECTED, "Card");
        hashMap.put(AnalyticsConstant.REASON, "Invalid CVV");
        LogAnalytics.logEvent(this.c.getApplicationContext(), AnalyticsConstant.INVALID_PAYMENT_INFO, hashMap, AnalyticsConstant.CLEVERTAP);
        return false;
    }
}
