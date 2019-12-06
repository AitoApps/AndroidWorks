package com.payumoney.core.utils;

import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.payu.custombrowser.util.CBConstant;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.SdkSession;
import com.payumoney.core.entity.CardDetail;
import com.payumoney.core.entity.EmiTenure;
import com.payumoney.core.entity.EmiThreshold;
import com.payumoney.core.entity.MerchantDetails;
import com.payumoney.core.entity.PaymentEntity;
import com.payumoney.core.entity.PayumoneyConvenienceFee;
import com.payumoney.core.entity.Wallet;
import com.payumoney.core.response.BinDetail;
import com.payumoney.core.response.ErrorResponse;
import com.payumoney.core.response.MerchantLoginResponse;
import com.payumoney.core.response.NetBankingStatusResponse;
import com.payumoney.core.response.PayUMoneyAPIResponse;
import com.payumoney.core.response.PayUMoneyLoginResponse;
import com.payumoney.core.response.PaymentOptionDetails;
import com.payumoney.core.response.UserDetail;
import com.payumoney.core.utils.SharedPrefsUtils.Keys;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResponseParser {
    private Comparator<EmiTenure> a = new Comparator<EmiTenure>() {
        public int compare(EmiTenure lhs, EmiTenure rhs) {
            if (lhs == null || rhs == null || lhs.getTitle() == null || rhs.getTitle() == null) {
                return 0;
            }
            String lowerCase = lhs.getTitle().toLowerCase();
            String lowerCase2 = rhs.getTitle().toLowerCase();
            try {
                if (lowerCase.contains("months") && lowerCase2.contains("months")) {
                    int indexOf = lowerCase.indexOf("months");
                    int indexOf2 = lowerCase2.indexOf("months");
                    String trim = lowerCase.substring(0, indexOf).trim();
                    String trim2 = lowerCase2.substring(0, indexOf2).trim();
                    if (!trim.isEmpty()) {
                        if (!trim2.isEmpty()) {
                            return Integer.valueOf(Integer.parseInt(trim)).compareTo(Integer.valueOf(Integer.parseInt(trim2)));
                        }
                    }
                    return lowerCase.compareTo(lowerCase2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lowerCase.compareTo(lowerCase2);
        }
    };

    public PayUMoneyAPIResponse parseFetchMerchant(JSONObject response) throws PayUMoneyCustomException {
        String str = "";
        try {
            if (response.has("status")) {
                str = response.getString("status");
            }
            if (!str.equals("0")) {
                return errorFromResponse(response);
            }
            if (!response.has("result")) {
                return errorFromResponse(response);
            }
            JSONObject jSONObject = response.getJSONArray("result").getJSONObject(0);
            MerchantLoginResponse merchantLoginResponse = new MerchantLoginResponse();
            if (jSONObject.has("merchantParamsId")) {
                merchantLoginResponse.setMerchantparamsId(jSONObject.getString("merchantParamsId"));
            }
            if (jSONObject.has("merchantId")) {
                merchantLoginResponse.setMerchantId(jSONObject.getString("merchantId"));
            }
            if (jSONObject.has("paramKey")) {
                merchantLoginResponse.setParamKey(jSONObject.getString("paramKey"));
            }
            if (jSONObject.has("paramValue")) {
                merchantLoginResponse.setParamsValue(jSONObject.getString("paramValue"));
            }
            if (jSONObject.has("addedOn")) {
                merchantLoginResponse.setAddedOn(jSONObject.getString("addedOn"));
            }
            if (jSONObject.has("updatedBy")) {
                merchantLoginResponse.setUpdatedBy(jSONObject.getString("updatedBy"));
            }
            if (jSONObject.has("updatedOn")) {
                merchantLoginResponse.setUpdatedOn(jSONObject.getString("updatedOn"));
            }
            return merchantLoginResponse;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PayUMoneyCustomException(PayUmoneyConstants.ERROR_SOMETHING_WENT_WRONG);
        }
    }

    public ErrorResponse errorFromResponse(JSONObject response) throws PayUMoneyCustomException {
        ErrorResponse errorResponse = new ErrorResponse();
        try {
            if (response.has("status")) {
                errorResponse.setStatus(response.getString("status"));
            }
            if (response.has(PayUmoneyConstants.MESSAGE)) {
                errorResponse.setMessage(response.getString(PayUmoneyConstants.MESSAGE));
            }
            if (response.has("errorCode")) {
                errorResponse.setErrorCode(response.getString("errorCode"));
            }
            if (response.has("guid")) {
                errorResponse.setGuid(response.getString("guid"));
            }
            return errorResponse;
        } catch (Exception e) {
            throw new PayUMoneyCustomException(PayUmoneyConstants.ERROR_SOMETHING_WENT_WRONG);
        }
    }

    public ErrorResponse errorFromResponse(JSONObject response, String messagekey) throws PayUMoneyCustomException {
        ErrorResponse errorResponse = new ErrorResponse();
        try {
            if (response.has("status")) {
                errorResponse.setStatus(response.getString("status"));
            }
            if (response.has(messagekey)) {
                errorResponse.setMessage(response.getString(messagekey));
            }
            if (response.has("errorCode")) {
                errorResponse.setErrorCode(response.getString("errorCode"));
            }
            if (response.has("guid")) {
                errorResponse.setGuid(response.getString("guid"));
            }
            return errorResponse;
        } catch (Exception e) {
            throw new PayUMoneyCustomException(PayUmoneyConstants.ERROR_SOMETHING_WENT_WRONG);
        }
    }

    public PayUMoneyAPIResponse parseLoginResponse(JSONObject response) throws PayUMoneyCustomException {
        try {
            if (!response.has("access_token") || response.isNull("access_token")) {
                return errorFromResponse(response);
            }
            PayUMoneyLoginResponse payUMoneyLoginResponse = new PayUMoneyLoginResponse();
            payUMoneyLoginResponse.setAccessToken(response.getString("access_token"));
            if (response.has("token_type")) {
                payUMoneyLoginResponse.setTokenType(response.getString("token_type"));
            }
            if (response.has(Keys.REFRESH_TOKEN)) {
                payUMoneyLoginResponse.setRefreshToken(response.getString(Keys.REFRESH_TOKEN));
            }
            if (response.has("expires_in")) {
                payUMoneyLoginResponse.setExpiresIn(response.getString("expires_in"));
            }
            if (response.has("scope")) {
                payUMoneyLoginResponse.setScope(response.getString("scope"));
            }
            return payUMoneyLoginResponse;
        } catch (Exception e) {
            throw new PayUMoneyCustomException(PayUmoneyConstants.ERROR_SOMETHING_WENT_WRONG);
        }
    }

    public PayUMoneyAPIResponse parseValidateWalletResponse(JSONObject response) throws PayUMoneyCustomException {
        String str = "-1";
        try {
            if (response.has("status")) {
                str = response.getString("status");
            }
            if (!str.equals("0")) {
                return errorFromResponse(response, NotificationCompat.CATEGORY_MESSAGE);
            }
            if (!response.has("result")) {
                return errorFromResponse(response, NotificationCompat.CATEGORY_MESSAGE);
            }
            PayUMoneyLoginResponse payUMoneyLoginResponse = new PayUMoneyLoginResponse();
            JSONObject jSONObject = response.getJSONObject("result");
            if (!jSONObject.has(PayUmoneyConstants.CONFIG_DTO) || jSONObject.get(PayUmoneyConstants.CONFIG_DTO) == null) {
                return errorFromResponse(response, NotificationCompat.CATEGORY_MESSAGE);
            }
            payUMoneyLoginResponse.setAccessToken(jSONObject.getJSONObject(PayUmoneyConstants.CONFIG_DTO).getString("userToken"));
            return payUMoneyLoginResponse;
        } catch (Exception e) {
            throw new PayUMoneyCustomException(PayUmoneyConstants.ERROR_SOMETHING_WENT_WRONG);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0091 A[Catch:{ Exception -> 0x0ae1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0092 A[Catch:{ Exception -> 0x0ae1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00b1 A[Catch:{ Exception -> 0x0ae1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00d0 A[Catch:{ Exception -> 0x0ae1 }] */
    public PayUMoneyAPIResponse parsePaymentOption(JSONObject response) throws PayUMoneyCustomException {
        char c;
        JSONObject jSONObject = response;
        String str = "-1";
        try {
            if (jSONObject.has("status")) {
                str = jSONObject.getString("status");
            }
            if (!str.equals("0")) {
                return errorFromResponse(response);
            } else if (jSONObject.has("result")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("result");
                PaymentOptionDetails paymentOptionDetails = new PaymentOptionDetails();
                if (jSONObject2.has("merchant") && jSONObject2.get("merchant") != null) {
                    MerchantDetails merchantDetails = new MerchantDetails();
                    JSONObject jSONObject3 = jSONObject2.getJSONObject("merchant");
                    Iterator keys = jSONObject3.keys();
                    while (keys.hasNext()) {
                        String str2 = (String) keys.next();
                        int hashCode = str2.hashCode();
                        if (hashCode == -258572029) {
                            if (str2.equals("merchantId")) {
                                c = 2;
                                switch (c) {
                                    case 0:
                                        break;
                                    case 1:
                                        break;
                                    case 2:
                                        break;
                                }
                            }
                        } else if (hashCode == 3327403) {
                            if (str2.equals("logo")) {
                                c = 0;
                                switch (c) {
                                    case 0:
                                        break;
                                    case 1:
                                        break;
                                    case 2:
                                        break;
                                }
                            }
                        } else if (hashCode == 1714148973 && str2.equals("displayName")) {
                            c = 1;
                            switch (c) {
                                case 0:
                                    if (jSONObject3.has("logo") && jSONObject3.get("logo") != null) {
                                        merchantDetails.setLogoUrl(jSONObject3.get("logo").toString());
                                        break;
                                    }
                                case 1:
                                    if (jSONObject3.has("displayName") && jSONObject3.get("displayName") != null) {
                                        merchantDetails.setDisplayName(jSONObject3.get("displayName").toString());
                                        break;
                                    } else {
                                        break;
                                    }
                                    break;
                                case 2:
                                    if (jSONObject3.has("merchantId") && jSONObject3.get("merchantId") != null) {
                                        merchantDetails.setMerchantId(jSONObject3.get("merchantId").toString());
                                        break;
                                    } else {
                                        break;
                                    }
                                    break;
                            }
                        }
                        c = 65535;
                        switch (c) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                        }
                    }
                    paymentOptionDetails.setMerchantDetails(merchantDetails);
                }
                double d = 0.0d;
                try {
                    if (jSONObject2.has("payment")) {
                        JSONObject jSONObject4 = jSONObject2.getJSONObject("payment");
                        if (jSONObject4.has("orderAmount") && jSONObject4.get("orderAmount") != null) {
                            d = jSONObject4.getDouble("orderAmount");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (jSONObject2.has(PayUmoneyConstants.PAYMENT_ID)) {
                    paymentOptionDetails.setPaymentID(jSONObject2.getString(PayUmoneyConstants.PAYMENT_ID));
                    if (jSONObject2.has("user") && jSONObject2.get("user") != null) {
                        UserDetail userDetail = new UserDetail();
                        JSONObject jSONObject5 = jSONObject2.getJSONObject("user");
                        if (jSONObject5.has(Keys.USER_ID)) {
                            userDetail.setUserID(jSONObject5.getString(Keys.USER_ID));
                        }
                        if (jSONObject5.has("phone")) {
                            userDetail.setPhoneNumber(jSONObject5.getString("phone"));
                        }
                        if (jSONObject5.has(PayUmoneyConstants.WALLET) && jSONObject5.get(PayUmoneyConstants.WALLET) != null && !jSONObject5.get(PayUmoneyConstants.WALLET).toString().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                            JSONObject jSONObject6 = jSONObject5.getJSONObject(PayUmoneyConstants.WALLET);
                            Wallet wallet = new Wallet();
                            if (jSONObject6.has(PayUmoneyConstants.AMOUNT)) {
                                wallet.setAmount(jSONObject6.getDouble(PayUmoneyConstants.AMOUNT));
                                PayUmoneyTransactionDetails.getInstance().setWalletAmount(jSONObject6.getDouble(PayUmoneyConstants.AMOUNT));
                            }
                            if (jSONObject6.has("availableAmount")) {
                                wallet.setAvailableAmount(jSONObject6.getDouble("availableAmount"));
                            }
                            if (jSONObject6.has(Keys.MIN_WALLET_BALANCE)) {
                                wallet.setMinLimit(jSONObject6.getDouble(Keys.MIN_WALLET_BALANCE));
                            }
                            if (jSONObject6.has(Keys.MAX_WALLET_BALANCE)) {
                                wallet.setMaxLimit(jSONObject6.getDouble(Keys.MAX_WALLET_BALANCE));
                            }
                            if (jSONObject6.has("status")) {
                                wallet.setStatus(jSONObject6.getDouble("status"));
                            }
                            if (jSONObject6.has(PayUmoneyConstants.MESSAGE)) {
                                wallet.setMesssage(jSONObject6.getString(PayUmoneyConstants.MESSAGE));
                            }
                            userDetail.setWalletDetails(wallet);
                        }
                        if (jSONObject5.has("savedCards") && jSONObject5.get("savedCards") != null && !jSONObject5.get("savedCards").toString().trim().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                            JSONArray jSONArray = jSONObject5.getJSONArray("savedCards");
                            ArrayList arrayList = new ArrayList();
                            for (int i = 0; i < jSONArray.length(); i++) {
                                JSONObject jSONObject7 = jSONArray.getJSONObject(i);
                                CardDetail cardDetail = new CardDetail();
                                if (jSONObject7.has("cardId")) {
                                    cardDetail.setId(jSONObject7.getLong("cardId"));
                                }
                                if (jSONObject7.has("cardName")) {
                                    cardDetail.setName(jSONObject7.getString("cardName"));
                                }
                                if (jSONObject7.has("cardToken")) {
                                    cardDetail.setToken(jSONObject7.getString("cardToken"));
                                }
                                if (jSONObject7.has("cardType")) {
                                    cardDetail.setType(jSONObject7.getString("cardType"));
                                }
                                if (jSONObject7.has(PayUmoneyConstants.CARD_NUMBER)) {
                                    cardDetail.setNumber(jSONObject7.getString(PayUmoneyConstants.CARD_NUMBER));
                                }
                                if (jSONObject7.has("pg")) {
                                    cardDetail.setPg(jSONObject7.getString("pg"));
                                }
                                if (jSONObject7.has("oneclickcheckout")) {
                                    cardDetail.setOneClickCheckout(jSONObject7.getBoolean("oneclickcheckout"));
                                }
                                arrayList.add(cardDetail);
                            }
                            userDetail.setSaveCardList(arrayList);
                            paymentOptionDetails.setUserDetails(userDetail);
                        }
                    }
                    if (jSONObject2.has("convenienceCharges") && jSONObject2.getString("convenienceCharges") != null && !jSONObject2.getString("convenienceCharges").equals(PayUmoneyConstants.NULL_STRING) && !jSONObject2.getString("convenienceCharges").equals("")) {
                        JSONObject jSONObject8 = new JSONObject(jSONObject2.getString("convenienceCharges"));
                        PayumoneyConvenienceFee payumoneyConvenienceFee = new PayumoneyConvenienceFee();
                        HashMap hashMap = new HashMap();
                        Iterator keys2 = jSONObject8.keys();
                        while (keys2.hasNext()) {
                            String str3 = (String) keys2.next();
                            JSONObject jSONObject9 = jSONObject8.getJSONObject(str3);
                            HashMap hashMap2 = new HashMap();
                            Iterator keys3 = jSONObject9.keys();
                            while (keys3.hasNext()) {
                                String str4 = (String) keys3.next();
                                hashMap2.put(str4, Double.valueOf(jSONObject9.getDouble(str4)));
                            }
                            hashMap.put(str3, hashMap2);
                        }
                        payumoneyConvenienceFee.setConvenienceFeeMap(hashMap);
                        paymentOptionDetails.setConvenienceFee(payumoneyConvenienceFee);
                    }
                    if (jSONObject2.has("emiIssuerCodes") && jSONObject2.getString("emiIssuerCodes") != null && !jSONObject2.getString("emiIssuerCodes").equals(PayUmoneyConstants.NULL_STRING) && !jSONObject2.getString("emiIssuerCodes").equals("")) {
                        JSONObject jSONObject10 = new JSONObject(jSONObject2.getString("emiIssuerCodes"));
                        ArrayList arrayList2 = new ArrayList();
                        Iterator keys4 = jSONObject10.keys();
                        while (keys4.hasNext()) {
                            String str5 = (String) keys4.next();
                            JSONObject jSONObject11 = jSONObject10.getJSONObject(str5);
                            if (jSONObject11.has("minAmount")) {
                                arrayList2.add(new EmiThreshold(str5, jSONObject11.getDouble("minAmount"), jSONObject11.getString("emiBankName")));
                            }
                        }
                        if (!arrayList2.isEmpty()) {
                            paymentOptionDetails.setEmiThresholds(arrayList2);
                        }
                    }
                    if (jSONObject2.has("paymentOptions") && jSONObject2.get("paymentOptions") != null && !jSONObject2.get("paymentOptions").toString().equals(PayUmoneyConstants.NULL_STRING)) {
                        if (jSONObject2.getJSONObject("paymentOptions").has("options") && jSONObject2.getJSONObject("paymentOptions").get("options") != null) {
                            JSONObject jSONObject12 = jSONObject2.getJSONObject("paymentOptions").getJSONObject("options");
                            if (jSONObject12.has("dc") && jSONObject12.get("dc") != null && !jSONObject12.get("dc").toString().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                                if (!jSONObject12.getString("dc").equalsIgnoreCase("-1")) {
                                    ArrayList arrayList3 = new ArrayList();
                                    JSONObject jSONObject13 = new JSONObject(jSONObject12.getString("dc"));
                                    Iterator keys5 = jSONObject13.keys();
                                    while (keys5.hasNext()) {
                                        String str6 = (String) keys5.next();
                                        PaymentEntity paymentEntity = new PaymentEntity();
                                        paymentEntity.setCode(str6);
                                        JSONObject jSONObject14 = jSONObject13.getJSONObject(str6);
                                        if (jSONObject14.has("pgId")) {
                                            paymentEntity.setPgID(jSONObject14.getString("pgId"));
                                        }
                                        if (jSONObject14.has("title")) {
                                            paymentEntity.setTitle(jSONObject14.getString("title"));
                                        }
                                        arrayList3.add(paymentEntity);
                                    }
                                    paymentOptionDetails.setDebitCardList(arrayList3);
                                }
                            }
                            if (jSONObject12.has(PayUmoneyConstants.WALLET) && jSONObject12.get(PayUmoneyConstants.WALLET) != null && !jSONObject12.get(PayUmoneyConstants.WALLET).toString().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                                paymentOptionDetails.setWallet(jSONObject12.getString(PayUmoneyConstants.WALLET));
                            }
                            if (jSONObject12.has("cc") && jSONObject12.get("cc") != null && !jSONObject12.get("cc").toString().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                                if (!jSONObject12.getString("cc").equalsIgnoreCase("-1")) {
                                    ArrayList arrayList4 = new ArrayList();
                                    JSONObject jSONObject15 = new JSONObject(jSONObject12.getString("cc"));
                                    Iterator keys6 = jSONObject15.keys();
                                    while (keys6.hasNext()) {
                                        String str7 = (String) keys6.next();
                                        PaymentEntity paymentEntity2 = new PaymentEntity();
                                        paymentEntity2.setCode(str7);
                                        JSONObject jSONObject16 = jSONObject15.getJSONObject(str7);
                                        if (jSONObject16.has("pgId")) {
                                            paymentEntity2.setPgID(jSONObject16.getString("pgId"));
                                        }
                                        if (jSONObject16.has("title")) {
                                            paymentEntity2.setTitle(jSONObject16.getString("title"));
                                        }
                                        arrayList4.add(paymentEntity2);
                                    }
                                    paymentOptionDetails.setCreditCardList(arrayList4);
                                }
                            }
                            if (jSONObject12.has(CBConstant.NB) && jSONObject12.get(CBConstant.NB) != null && !jSONObject12.get(CBConstant.NB).toString().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                                ArrayList arrayList5 = new ArrayList();
                                JSONObject jSONObject17 = new JSONObject(jSONObject12.getString(CBConstant.NB));
                                Iterator keys7 = jSONObject17.keys();
                                while (keys7.hasNext()) {
                                    String str8 = (String) keys7.next();
                                    PaymentEntity paymentEntity3 = new PaymentEntity();
                                    paymentEntity3.setCode(str8);
                                    JSONObject jSONObject18 = jSONObject17.getJSONObject(str8);
                                    if (jSONObject18.has("pgId")) {
                                        paymentEntity3.setPgID(jSONObject18.getString("pgId"));
                                    }
                                    if (jSONObject18.has("title")) {
                                        paymentEntity3.setTitle(jSONObject18.getString("title"));
                                    }
                                    if (jSONObject18.has("ShortTitle") && jSONObject18.getString("ShortTitle") != null) {
                                        if (!jSONObject18.getString("ShortTitle").equalsIgnoreCase("")) {
                                            paymentEntity3.setShortTitle(jSONObject18.getString("ShortTitle"));
                                        }
                                    }
                                    arrayList5.add(paymentEntity3);
                                }
                                paymentOptionDetails.setNetBankingList(arrayList5);
                            }
                            if (jSONObject12.has("upi")) {
                                if (jSONObject12.get("upi") != null) {
                                    if (!jSONObject12.get("upi").toString().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                                        if (!jSONObject12.getString("upi").equalsIgnoreCase("-1")) {
                                            ArrayList arrayList6 = new ArrayList();
                                            JSONObject jSONObject19 = new JSONObject(jSONObject12.getString("upi"));
                                            Iterator keys8 = jSONObject19.keys();
                                            while (keys8.hasNext()) {
                                                String str9 = (String) keys8.next();
                                                PaymentEntity paymentEntity4 = new PaymentEntity();
                                                paymentEntity4.setCode(str9);
                                                JSONObject jSONObject20 = jSONObject19.getJSONObject(str9);
                                                if (jSONObject20.has("pgId")) {
                                                    paymentEntity4.setPgID(jSONObject20.getString("pgId"));
                                                }
                                                if (jSONObject20.has("title")) {
                                                    paymentEntity4.setTitle(jSONObject20.getString("title"));
                                                }
                                                arrayList6.add(paymentEntity4);
                                            }
                                            paymentOptionDetails.setUpiList(arrayList6);
                                        }
                                    }
                                }
                            }
                            if (jSONObject12.has("cashcard") && jSONObject12.get("cashcard") != null) {
                                if (!jSONObject12.get("cashcard").toString().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                                    if (!jSONObject12.get("cashcard").toString().equalsIgnoreCase("")) {
                                        if (!jSONObject12.getString("cashcard").equalsIgnoreCase("-1")) {
                                            ArrayList arrayList7 = new ArrayList();
                                            JSONObject jSONObject21 = new JSONObject(jSONObject12.getString("cashcard"));
                                            Iterator keys9 = jSONObject21.keys();
                                            while (keys9.hasNext()) {
                                                String str10 = (String) keys9.next();
                                                PaymentEntity paymentEntity5 = new PaymentEntity();
                                                paymentEntity5.setCode(str10);
                                                JSONObject jSONObject22 = jSONObject21.getJSONObject(str10);
                                                if (jSONObject22.has("pgId")) {
                                                    paymentEntity5.setPgID(jSONObject22.getString("pgId"));
                                                }
                                                if (jSONObject22.has("title")) {
                                                    paymentEntity5.setTitle(jSONObject22.getString("title"));
                                                }
                                                arrayList7.add(paymentEntity5);
                                            }
                                            paymentOptionDetails.setCashCardList(arrayList7);
                                        }
                                    }
                                }
                            }
                            if (jSONObject12.has("emi") && jSONObject12.get("emi") != null && !jSONObject12.get("emi").toString().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                                if (!jSONObject12.getString("emi").equalsIgnoreCase("-1")) {
                                    try {
                                        paymentOptionDetails.setEmiList(a(new JSONObject(jSONObject12.getString("emi")), d, paymentOptionDetails.getEmiThresholds()));
                                    } catch (Exception e2) {
                                        throw new PayUMoneyCustomException(PayUmoneyConstants.ERROR_SOMETHING_WENT_WRONG);
                                    }
                                }
                            }
                        }
                        if (jSONObject2.getJSONObject("paymentOptions").has("config")) {
                            if (jSONObject2.getJSONObject("paymentOptions").get("config") != null) {
                                JSONObject jSONObject23 = jSONObject2.getJSONObject("paymentOptions").getJSONObject("config");
                                if (jSONObject23.has("publicKey")) {
                                    paymentOptionDetails.setPublicKey(jSONObject23.getString("publicKey"));
                                    PayUmoneyTransactionDetails.getInstance().setPublicKey(jSONObject23.getString("publicKey"));
                                }
                            }
                        }
                    }
                    if (jSONObject2.has("configData")) {
                        if (jSONObject2.get("configData") != null) {
                            if (!jSONObject2.get("configData").toString().trim().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                                JSONObject jSONObject24 = jSONObject2.getJSONObject("configData");
                                if (!jSONObject24.has("nitroEnabled") || !jSONObject24.getString("nitroEnabled").equalsIgnoreCase("true")) {
                                    SdkSession.getInstanceForService().setNitroEnabled(false);
                                    paymentOptionDetails.setNitroEnabled("false");
                                } else {
                                    SdkSession.getInstanceForService().setNitroEnabled(true);
                                    paymentOptionDetails.setNitroEnabled("true");
                                }
                                if (jSONObject24.has("userSignupDisabled")) {
                                    SdkSession.getInstanceForService().setUserSignUpDisabled(jSONObject24.getBoolean("userSignupDisabled"));
                                }
                            }
                        }
                    }
                    return paymentOptionDetails;
                }
                return errorFromResponse(response);
            } else {
                return errorFromResponse(response);
            }
        } catch (Exception e3) {
            throw new PayUMoneyCustomException(PayUmoneyConstants.ERROR_SOMETHING_WENT_WRONG);
        }
    }

    public ArrayList<PaymentEntity> parseEmiInterestsForBankResponse(JSONObject response, double totalAmount, List<EmiThreshold> emiThresholds) throws PayUMoneyCustomException {
        String str = "-1";
        try {
            if (response.has("status")) {
                str = response.getString("status");
            }
            if (!str.equals("0") || !response.has("result")) {
                return null;
            }
            return a(response.getJSONObject("result"), totalAmount, emiThresholds);
        } catch (Exception e) {
            throw new PayUMoneyCustomException(PayUmoneyConstants.ERROR_SOMETHING_WENT_WRONG);
        }
    }

    private ArrayList<PaymentEntity> a(JSONObject jSONObject, double d, List<EmiThreshold> list) throws JSONException {
        JSONObject jSONObject2 = jSONObject;
        ArrayList<PaymentEntity> arrayList = new ArrayList<>();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            PaymentEntity paymentEntity = new PaymentEntity();
            paymentEntity.setCode(str);
            paymentEntity.setTitle(str);
            if (jSONObject.get(str) != null && !jSONObject.get(str).toString().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                ArrayList arrayList2 = new ArrayList();
                JSONObject jSONObject3 = new JSONObject(jSONObject.getString(str));
                Iterator keys2 = jSONObject3.keys();
                while (keys2.hasNext()) {
                    try {
                        String str2 = (String) keys2.next();
                        EmiTenure emiTenure = new EmiTenure();
                        emiTenure.setTenureId(str2);
                        JSONObject jSONObject4 = jSONObject3.getJSONObject(str2);
                        if (jSONObject4.has("emiBankInterest")) {
                            double d2 = jSONObject4.getDouble("emiBankInterest");
                            if (d2 > 0.0d) {
                                emiTenure.setEmiBankInterest(d2);
                                if (jSONObject4.has("emi_interest_paid")) {
                                    double d3 = jSONObject4.getDouble("emi_interest_paid");
                                    if (d3 > 0.0d) {
                                        emiTenure.setEmiInterestPaid(d3);
                                        if (jSONObject4.has("emi_value")) {
                                            double d4 = jSONObject4.getDouble("emi_value");
                                            if (d4 > 0.0d) {
                                                emiTenure.setEmiValue(d4);
                                            }
                                            if (jSONObject4.has("title")) {
                                                emiTenure.setTitle(jSONObject4.getString("title"));
                                            }
                                            if (jSONObject4.has("bank")) {
                                                emiTenure.setBank(jSONObject4.getString("bank"));
                                            }
                                            if (jSONObject4.has("pgId")) {
                                                emiTenure.setPgID(jSONObject4.getString("pgId"));
                                            }
                                            if (jSONObject4.has("transactionAmount")) {
                                                emiTenure.setTransactionAmount(jSONObject4.getDouble("transactionAmount"));
                                            }
                                            arrayList2.add(emiTenure);
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!arrayList2.isEmpty()) {
                    if (list != null && !list.isEmpty()) {
                        Iterator it = list.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            EmiThreshold emiThreshold = (EmiThreshold) it.next();
                            if (emiThreshold.getEmiBankCode().equals(paymentEntity.getCode())) {
                                paymentEntity.setEmiThresholdAmount(emiThreshold.getThreshouldAmount());
                                String emiBankTitle = emiThreshold.getEmiBankTitle();
                                if (!TextUtils.isEmpty(emiBankTitle)) {
                                    paymentEntity.setTitle(emiBankTitle);
                                }
                            }
                        }
                    }
                    Collections.sort(arrayList2, this.a);
                    paymentEntity.setEmiTenures(arrayList2);
                }
            }
            if (paymentEntity.getEmiTenures() != null && !paymentEntity.getEmiTenures().isEmpty() && d >= paymentEntity.getEmiThresholdAmount()) {
                arrayList.add(paymentEntity);
            }
        }
        return arrayList;
    }

    public PayUMoneyAPIResponse parseUserAccountDetail(JSONObject response) throws PayUMoneyCustomException {
        String str = "-1";
        try {
            if (response.has("status")) {
                str = response.getString("status");
            }
            if (str.equals("0")) {
                if (response.has("result")) {
                    if (response.getJSONObject("result").has("UserDataDTO")) {
                        JSONObject jSONObject = response.getJSONObject("result").getJSONObject("UserDataDTO");
                        UserDetail userDetail = new UserDetail();
                        if (jSONObject.has("savedCards")) {
                            if (jSONObject.get("savedCards") != null) {
                                if (!jSONObject.get("savedCards").toString().trim().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                                    JSONArray jSONArray = jSONObject.getJSONArray("savedCards");
                                    ArrayList arrayList = new ArrayList();
                                    for (int i = 0; i < jSONArray.length(); i++) {
                                        JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                        CardDetail cardDetail = new CardDetail();
                                        if (jSONObject2.has("cardId")) {
                                            cardDetail.setId(jSONObject2.getLong("cardId"));
                                        }
                                        if (jSONObject2.has("cardName")) {
                                            cardDetail.setName(jSONObject2.getString("cardName"));
                                        }
                                        if (jSONObject2.has("cardToken")) {
                                            cardDetail.setToken(jSONObject2.getString("cardToken"));
                                        }
                                        if (jSONObject2.has("cardType")) {
                                            cardDetail.setType(jSONObject2.getString("cardType"));
                                        }
                                        if (jSONObject2.has(PayUmoneyConstants.CARD_NUMBER)) {
                                            cardDetail.setNumber(jSONObject2.getString(PayUmoneyConstants.CARD_NUMBER));
                                        }
                                        if (jSONObject2.has("pg")) {
                                            cardDetail.setPg(jSONObject2.getString("pg"));
                                        }
                                        if (jSONObject2.has("oneclickcheckout")) {
                                            cardDetail.setOneClickCheckout(jSONObject2.getBoolean("oneclickcheckout"));
                                        }
                                        arrayList.add(cardDetail);
                                    }
                                    userDetail.setSaveCardList(arrayList);
                                }
                            }
                        }
                        if (jSONObject.has(PayUmoneyConstants.WALLET)) {
                            if (jSONObject.get(PayUmoneyConstants.WALLET) != null) {
                                if (!jSONObject.get(PayUmoneyConstants.WALLET).toString().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                                    JSONObject jSONObject3 = jSONObject.getJSONObject(PayUmoneyConstants.WALLET);
                                    Wallet wallet = new Wallet();
                                    if (jSONObject3.has(PayUmoneyConstants.AMOUNT)) {
                                        wallet.setAmount(jSONObject3.getDouble(PayUmoneyConstants.AMOUNT));
                                        PayUmoneyTransactionDetails.getInstance().setWalletAmount(jSONObject3.getDouble(PayUmoneyConstants.AMOUNT));
                                    }
                                    if (jSONObject3.has("availableAmount")) {
                                        wallet.setAvailableAmount(jSONObject3.getDouble("availableAmount"));
                                    }
                                    if (jSONObject3.has(Keys.MIN_WALLET_BALANCE)) {
                                        wallet.setMinLimit(jSONObject3.getDouble(Keys.MIN_WALLET_BALANCE));
                                    }
                                    if (jSONObject3.has(Keys.MAX_WALLET_BALANCE)) {
                                        wallet.setMaxLimit(jSONObject3.getDouble(Keys.MAX_WALLET_BALANCE));
                                    }
                                    if (jSONObject3.has("status")) {
                                        wallet.setStatus(jSONObject3.getDouble("status"));
                                    }
                                    if (jSONObject3.has(PayUmoneyConstants.MESSAGE)) {
                                        wallet.setMesssage(jSONObject3.getString(PayUmoneyConstants.MESSAGE));
                                    }
                                    userDetail.setWalletDetails(wallet);
                                }
                            }
                        }
                        return userDetail;
                    }
                }
            }
            return errorFromResponse(response);
        } catch (Exception e) {
            throw new PayUMoneyCustomException(PayUmoneyConstants.ERROR_SOMETHING_WENT_WRONG);
        }
    }

    public PayUMoneyAPIResponse getParseNetBankingStatusList(JSONObject response) throws PayUMoneyCustomException {
        String str = "-1";
        try {
            if (response.has("status")) {
                str = response.getString("status");
            }
            if (str.equals("0") && response.has("result")) {
                JSONArray jSONArray = response.getJSONArray("result");
                NetBankingStatusResponse netBankingStatusResponse = new NetBankingStatusResponse();
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = new JSONObject(jSONArray.getString(i));
                    Iterator keys = jSONObject.keys();
                    while (keys.hasNext()) {
                        String str2 = (String) keys.next();
                        PaymentEntity paymentEntity = new PaymentEntity();
                        paymentEntity.setCode(str2);
                        JSONObject jSONObject2 = jSONObject.getJSONObject(str2);
                        if (jSONObject2.has("ibibo_code")) {
                            paymentEntity.setPgID(jSONObject2.getString("ibibo_code"));
                        }
                        if (jSONObject2.has("title")) {
                            paymentEntity.setTitle(jSONObject2.getString("title"));
                        }
                        if (jSONObject2.has("up_status")) {
                            paymentEntity.setUpStatus(jSONObject2.getInt("up_status"));
                        }
                        arrayList.add(paymentEntity);
                    }
                }
                netBankingStatusResponse.setNetBankList(arrayList);
                return netBankingStatusResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorFromResponse(response);
    }

    public HashMap<String, BinDetail> parseMultipleBinDetail(JSONObject response) throws PayUMoneyCustomException {
        String str = "";
        try {
            if (response.has("status")) {
                str = response.getString("status");
            }
            if (!str.equals("0")) {
                return null;
            }
            HashMap<String, BinDetail> hashMap = new HashMap<>();
            if (!response.has("result")) {
                return null;
            }
            JSONObject jSONObject = response.getJSONObject("result");
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                BinDetail binDetail = new BinDetail();
                if (jSONObject.get(str2) != null && !jSONObject.get(str2).toString().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                    if (!jSONObject.get(str2).toString().isEmpty()) {
                        JSONObject jSONObject2 = jSONObject.getJSONObject(str2);
                        if (jSONObject2.has("cardBin")) {
                            binDetail.setCardBin(jSONObject2.getString("cardBin"));
                        }
                        if (jSONObject2.has("binOwner")) {
                            binDetail.setBinOwner(jSONObject2.getString("binOwner"));
                        }
                        if (jSONObject2.has("category")) {
                            binDetail.setCategory(jSONObject2.getString("category"));
                        }
                        if (jSONObject2.has(CBConstant.BANKNAME)) {
                            binDetail.setBankName(jSONObject2.getString(CBConstant.BANKNAME));
                        }
                        if (jSONObject2.has("cardProgram")) {
                            binDetail.setCardProgram(jSONObject2.getString("cardProgram"));
                        }
                        if (jSONObject2.has("countryCode")) {
                            binDetail.setCountryCode(jSONObject2.getString("countryCode"));
                        }
                        if (jSONObject2.has(PayUmoneyConstants.BANK_CODE_STRING)) {
                            binDetail.setBankCode(jSONObject2.getString(PayUmoneyConstants.BANK_CODE_STRING));
                        }
                        hashMap.put(str2, binDetail);
                    }
                }
                hashMap.put(str2, null);
            }
            return hashMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PayUMoneyCustomException(PayUmoneyConstants.ERROR_SOMETHING_WENT_WRONG);
        }
    }

    public PayUMoneyAPIResponse parseBinDetail(JSONObject response) throws PayUMoneyCustomException {
        String str = "";
        try {
            if (response.has("status")) {
                str = response.getString("status");
            }
            if (!str.equals("0")) {
                return errorFromResponse(response);
            }
            if (!response.has("result")) {
                return errorFromResponse(response);
            }
            JSONObject jSONObject = response.getJSONObject("result");
            BinDetail binDetail = new BinDetail();
            if (jSONObject.has("cardBin")) {
                binDetail.setCardBin(jSONObject.getString("cardBin"));
            }
            if (jSONObject.has("binOwner")) {
                binDetail.setBinOwner(jSONObject.getString("binOwner"));
            }
            if (jSONObject.has("category")) {
                binDetail.setCategory(jSONObject.getString("category"));
            }
            if (jSONObject.has(CBConstant.BANKNAME)) {
                binDetail.setBankName(jSONObject.getString(CBConstant.BANKNAME));
            }
            if (jSONObject.has("cardProgram")) {
                binDetail.setCardProgram(jSONObject.getString("cardProgram"));
            }
            if (jSONObject.has("countryCode")) {
                binDetail.setCountryCode(jSONObject.getString("countryCode"));
            }
            if (jSONObject.has(PayUmoneyConstants.BANK_CODE_STRING)) {
                binDetail.setBankCode(jSONObject.getString(PayUmoneyConstants.BANK_CODE_STRING));
            }
            return binDetail;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PayUMoneyCustomException(PayUmoneyConstants.ERROR_SOMETHING_WENT_WRONG);
        }
    }

    public PayUMoneyAPIResponse parseUserDetailsForNitroFlow(JSONObject response) throws PayUMoneyCustomException {
        String str = "";
        try {
            if (response.has("status")) {
                str = response.getString("status");
            }
            if (!str.equals("0")) {
                return errorFromResponse(response);
            }
            if (!response.has("result")) {
                return errorFromResponse(response);
            }
            JSONObject jSONObject = response.getJSONObject("result");
            if (!jSONObject.has(Keys.USER_ID) || jSONObject.getString(Keys.USER_ID) == null || !jSONObject.has("userEnabled") || jSONObject.getString("userEnabled") == null) {
                return errorFromResponse(response);
            }
            String string = jSONObject.getString(Keys.USER_ID);
            String string2 = jSONObject.getString("userEnabled");
            if (string.equals("-1") || !string2.equals("1")) {
                SdkSession.getInstanceForService().setUserAccountActive(false);
                return errorFromResponse(response);
            }
            SdkSession.getInstanceForService().setUserAccountActive(true);
            String string3 = jSONObject.getString("phone");
            String string4 = jSONObject.getString("email");
            if (string3 == null || string3.equalsIgnoreCase(PayUmoneyConstants.NULL_STRING) || string3.isEmpty()) {
                SdkSession.getInstanceForService().setRegisteredUserName(string4);
                SdkSession.getInstanceForService().setMobileNumberRegistered(false);
            } else {
                SdkSession.getInstanceForService().setRegisteredUserName(string3);
                SdkSession.getInstanceForService().setMobileNumberRegistered(true);
            }
            UserDetail userDetail = new UserDetail();
            userDetail.setUserID(jSONObject.getString(Keys.USER_ID));
            userDetail.setPhoneNumber(jSONObject.getString("phone"));
            userDetail.setEmail(jSONObject.getString("email"));
            if (jSONObject.has(PayUmoneyConstants.WALLET) && jSONObject.get(PayUmoneyConstants.WALLET) != null) {
                if (!jSONObject.get(PayUmoneyConstants.WALLET).toString().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                    if (!jSONObject.get(PayUmoneyConstants.WALLET).toString().equalsIgnoreCase("")) {
                        JSONObject jSONObject2 = jSONObject.getJSONObject(PayUmoneyConstants.WALLET);
                        Wallet wallet = new Wallet();
                        if (jSONObject2.has(PayUmoneyConstants.AMOUNT)) {
                            wallet.setAmount(jSONObject2.getDouble(PayUmoneyConstants.AMOUNT));
                            PayUmoneyTransactionDetails.getInstance().setWalletAmount(jSONObject2.getDouble(PayUmoneyConstants.AMOUNT));
                        }
                        if (jSONObject2.has("availableAmount")) {
                            wallet.setAvailableAmount(jSONObject2.getDouble("availableAmount"));
                        }
                        if (jSONObject2.has(Keys.MIN_WALLET_BALANCE)) {
                            wallet.setMinLimit(jSONObject2.getDouble(Keys.MIN_WALLET_BALANCE));
                        }
                        if (jSONObject2.has(Keys.MAX_WALLET_BALANCE)) {
                            wallet.setMaxLimit(jSONObject2.getDouble(Keys.MAX_WALLET_BALANCE));
                        }
                        if (jSONObject2.has("status")) {
                            wallet.setStatus(jSONObject2.getDouble("status"));
                        }
                        if (jSONObject2.has(PayUmoneyConstants.MESSAGE)) {
                            wallet.setMesssage(jSONObject2.getString(PayUmoneyConstants.MESSAGE));
                        }
                        userDetail.setWalletDetails(wallet);
                    }
                }
            }
            if (jSONObject.has("savedCards") && jSONObject.get("savedCards") != null && !jSONObject.get("savedCards").toString().trim().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                JSONArray jSONArray = jSONObject.getJSONArray("savedCards");
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                    CardDetail cardDetail = new CardDetail();
                    if (jSONObject3.has("cardId")) {
                        cardDetail.setId(jSONObject3.getLong("cardId"));
                    }
                    if (jSONObject3.has("cardName")) {
                        cardDetail.setName(jSONObject3.getString("cardName"));
                    }
                    if (jSONObject3.has("cardToken")) {
                        cardDetail.setToken(jSONObject3.getString("cardToken"));
                    }
                    if (jSONObject3.has("cardType")) {
                        cardDetail.setType(jSONObject3.getString("cardType"));
                    }
                    if (jSONObject3.has(PayUmoneyConstants.CARD_NUMBER)) {
                        cardDetail.setNumber(jSONObject3.getString(PayUmoneyConstants.CARD_NUMBER));
                    }
                    if (jSONObject3.has("pg")) {
                        cardDetail.setPg(jSONObject3.getString("pg"));
                    }
                    if (jSONObject3.has("oneclickcheckout")) {
                        cardDetail.setOneClickCheckout(jSONObject3.getBoolean("oneclickcheckout"));
                    }
                    arrayList.add(cardDetail);
                }
                userDetail.setSaveCardList(arrayList);
            }
            return userDetail;
        } catch (JSONException e) {
            e.printStackTrace();
            return errorFromResponse(response);
        }
    }
}
