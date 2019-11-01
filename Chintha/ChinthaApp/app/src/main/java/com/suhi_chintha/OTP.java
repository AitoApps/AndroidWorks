package com.suhi_chintha;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import es.dmoral.toasty.Toasty;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OTP extends AppCompatActivity {
    public static EditText verificationcode;
    String android_id = "";
    NetConnection cd;
    final DataDB2 dataDb2 = new DataDB2(this);
    Typeface face;
    ImageView help;
    FirebaseAuth mAuth;
    ProgressDialog pd;
    Button regs;
    TextView text;
    TextView textmobile;
    final User_DataDB userDataDB = new User_DataDB(this);
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.verificationotp_actvty);
        text = (TextView) findViewById(R.id.text);
        textmobile = (TextView) findViewById(R.id.txtmobile);
        verificationcode = (EditText) findViewById(R.id.vericode);
        face = Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
        regs = (Button) findViewById(R.id.verify);
        cd = new NetConnection(this);
        pd = new ProgressDialog(this);
        text.setText(Static_Variable.title_verification);
        text.setTypeface(face);
        text.setSelected(true);
        textmobile.setText(Static_Variable.msg_verification);
        textmobile.setTypeface(face);
        help = (ImageView) findViewById(R.id.help);
        mAuth = FirebaseAuth.getInstance();
        regs.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                } else if (verificationcode.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.vericode_notget, Toast.LENGTH_LONG).show();
                } else {
                    FirebaseMessaging.getInstance().subscribeToTopic("status_updating");
                    signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(Primary_Registration.mVerificationId, verificationcode.getText().toString()));
                }
            }
        });
        help.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Developer_Details.class));
            }
        });
    }

    public void newregistration() {
        try {
            userDataDB.drop_user();
            regs.setEnabled(false);
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new Builder().setType(MultipartBody.FORM).addFormDataPart(ConditionalUserProperty.NAME, dataDb2.get_usrtmp()).addFormDataPart("mobile", dataDb2.get_mobiletmp()).addFormDataPart("countrycode", dataDb2.get_codetemp()).addFormDataPart("fcmid", dataDb2.getfcmid()).addFormDataPart("aid", android_id).build();
            Request.Builder builder = new Request.Builder();
            client.newCall(builder.url(Static_Variable.entypoint1+"registrationfresh_verify.php").post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                String result = response.body().string();
                                regs.setEnabled(true);
                                pd.dismiss();
                                if (result.contains("404")) {
                                    pd.dismiss();
                                } else if (result.contains("%:ok")) {
                                    String[] k = result.split("%:");
                                    userDataDB.add_user(k[0], dataDb2.get_usrtmp(), dataDb2.get_codetemp()+ dataDb2.get_mobiletmp(),"0","1");
                                    Intent intent = new Intent(getApplicationContext(), HeartOf_App.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                    return;
                                } else {
                                    pd.dismiss();
                                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
        }
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        pd.setMessage("Please Wait.....");
        pd.setCancelable(false);
        pd.show();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {



                            FirebaseInstanceId.getInstance().getInstanceId()
                                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {

                                            if (!task.isSuccessful()) {
                                                pd.dismiss();
                                                Toasty.info(getApplicationContext(), "താല്\u200Dക്കാലിക പ്രശ്\u200Cനം ! ദയവായി 10 മിനിട്ടിന് ശേഷം ശ്രമിക്കുക ", Toast.LENGTH_LONG).show();
                                                return;
                                            }
                                            String fcmId = task.getResult().getToken();
                                            dataDb2.addfcmid(fcmId);
                                            try
                                            {
                                                android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                            }
                                            catch (Exception a)
                                            {
                                                android_id="NA";
                                            }
                                            FirebaseMessaging.getInstance().subscribeToTopic("statusupdate");
                                            newregistration();
                                        }
                                    });





                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            }
                            pd.dismiss();
                            Toasty.info(getApplicationContext(),"വെരിഫിക്കേഷന്\u200D പ്രശ്\u200Cനം !! ദയവായി ശരിയായ വെരിഫിക്കേഷന്\u200D കോഡ് എന്റര്\u200D ചെയ്യൂ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
