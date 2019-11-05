package com.mal_suthra;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Payment {
    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }
//
//    public static String getPaytm_ChekSum(String merchantMid,String orderId,String channelId,String custId,String mobileNo,String email,String txnAmount,String website,String industryTypeId,String callbackUrl)
//    {
//        try {
//            String merchantKey = "wtiRo#vaMVnnh7To";
//            TreeMap<String, String> paytmParams = new TreeMap<String, String>();
//            paytmParams.put("MID",merchantMid);
//            paytmParams.put("ORDER_ID",orderId);
//            paytmParams.put("CHANNEL_ID",channelId);
//            paytmParams.put("CUST_ID",custId);
//            paytmParams.put("MOBILE_NO",mobileNo);
//            paytmParams.put("EMAIL",email);
//            paytmParams.put("TXN_AMOUNT",txnAmount);
//            paytmParams.put("WEBSITE",website);
//            paytmParams.put("INDUSTRY_TYPE_ID",industryTypeId);
//            paytmParams.put("CALLBACK_URL", callbackUrl);
//            return CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(merchantKey, paytmParams);
//        }
//        catch (Exception a)
//        {
//            Log.w("Muhsin",Log.getStackTraceString(a));
//            return "non";
//        }
//
//    }
}
