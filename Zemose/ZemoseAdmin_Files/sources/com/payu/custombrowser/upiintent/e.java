package com.payu.custombrowser.upiintent;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.payumoney.core.PayUmoneyConstants;
import java.util.regex.Pattern;

public class e {
    public boolean a(Context context, Payment payment) {
        switch (payment) {
            case TEZ:
                if (VERSION.SDK_INT < payment.getMinSdk()) {
                    return false;
                }
                try {
                    context.getPackageManager().getPackageInfo(payment.getPackageName(), 0);
                    return true;
                } catch (NameNotFoundException e) {
                    return false;
                }
            case GENERIC_INTENT:
                return true;
            default:
                return false;
        }
    }

    public String a(String str, String str2) {
        for (String split : str.split("&")) {
            String[] split2 = split.split("=");
            if (split2.length >= 2 && str2.equalsIgnoreCase(split2[0])) {
                return split2[1];
            }
        }
        return "";
    }

    public Payment a(String str) {
        Payment[] values;
        if (str != null) {
            String a = a(str, PayUmoneyConstants.BANK_CODE);
            for (Payment payment : Payment.values()) {
                if (payment.getPaymentName().equalsIgnoreCase(a)) {
                    return payment;
                }
            }
        }
        return null;
    }

    public String a(String str, String str2, String str3, String str4, String str5) {
        StringBuilder sb = new StringBuilder();
        sb.append("upi://pay?");
        sb.append("pa");
        sb.append("=");
        sb.append(str);
        sb.append("&");
        sb.append("pn");
        sb.append("=");
        sb.append(str2);
        sb.append("&");
        sb.append("am");
        sb.append("=");
        sb.append(str3);
        sb.append("&");
        sb.append("tr");
        sb.append("=");
        sb.append(str5);
        sb.append("&");
        sb.append("tid");
        sb.append("=");
        sb.append(str4);
        return sb.toString();
    }

    public static boolean b(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Pattern.compile(str2).matcher(str.trim()).matches();
    }
}
