package com.hellokhd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.Status;

public class SMSBoradCastReciver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            if (((Status) extras.get(SmsRetriever.EXTRA_STATUS)).getStatusCode() == 0) {
                String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                if (message.contains("HelloKHD App Verification")) {
                    String str = ":";
                    try {
                        OTP_Verification.otp.setText(message.substring(message.indexOf(str) + 2, message.indexOf(str) + 6));
                        OTP_Verification.otp.setSelection(OTP_Verification.otp.getText().toString().length());
                    } catch (Exception e) {
                    }
                }
              //  Log.w("Messagesss", message);
            }
        }
    }
}
