package com.suhi_chintha;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Primary_Registration extends AppCompatActivity {
    public static String mVerificationId;
    public static String txtcountrycode = "";
    public static String txtmobile = "";
    public static String txtname = "";
    public static String txtusername = "";
    public static String txtverificationkey = "";
    int PERMISSION_ALL = 1;
    String android_id = "";
    NetConnection cd;
    EditText countryode;
    final DataDb dataDb = new DataDb(this);
    final DataDB1 dataDb1 = new DataDB1(this);
    final DataDB2 dataDb2 = new DataDB2(this);
    final DataDB4 dataDb4 = new DataDB4(this);
    public Dialog dialog;
    Typeface face1;
    ImageView help;
    FirebaseAuth mAuth;
    OnVerificationStateChangedCallbacks mCallbacks;
    boolean mVerificationInProgress = false;
    EditText mobile;
    EditText name;
    ProgressDialog pd;
    Button regs;
    TextView text;
    TextView textmobile1;
    final User_DataDB userDataDB = new User_DataDB(this);
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.first_registration_actvty);
        try {
            textmobile1 = (TextView) findViewById(R.id.textmobile1);
            name = (EditText) findViewById(R.id.name);
            mobile = (EditText) findViewById(R.id.mobile);
            regs = (Button) findViewById(R.id.verify);
            help = (ImageView) findViewById(R.id.help);
            cd = new NetConnection(this);
            pd = new ProgressDialog(this);
            String[] PERMISSIONS = {android.Manifest.permission.INTERNET,
                    android.Manifest.permission.CALL_PHONE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.ACCESS_NETWORK_STATE,
                    android.Manifest.permission.ACCESS_WIFI_STATE};

            countryode = (EditText) findViewById(R.id.countryode);
            face1 = Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
            text = (TextView) findViewById(R.id.text);
            text.setText(Static_Variable.application_title);
            text.setTypeface(face1);
            textmobile1.setText("രജിസ്‌ട്രേഷന്‍ ചെയ്യുവാന്‍ സാധിക്കുന്നില്ലെങ്കില്‍ ഇവിടെ പ്രസ്സ് ചെയ്യുക ");
            textmobile1.setTypeface(face1);
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
            mAuth = FirebaseAuth.getInstance();
            help.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Developer_Details.class));
                }
            });
            textmobile1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(getApplicationContext(), Registration_Bypassing.class));
                        finish();
                    } catch (Exception e) {
                     //   Toast.makeText(getApplicationContext(), Log.getStackTraceString(e),Toast.LENGTH_LONG).show();
                    }
                }
            });
            TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            PhoneNumberUtil phoneutil = PhoneNumberUtil.getInstance();
            countryode.setText("+"+phoneutil.getCountryCodeForRegion(manager.getSimCountryIso().toUpperCase()));
            regs.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (name.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nameofyou, Toast.LENGTH_SHORT).show();
                        name.requestFocus();
                    } else if (containsDigit(name.getText().toString())) {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.number_noted, Toast.LENGTH_SHORT).show();
                        name.requestFocus();
                    } else if (name.getText().toString().length() >= 20) {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.namelength, Toast.LENGTH_SHORT).show();
                        name.requestFocus();
                    } else if (mobile.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.mobile_txt, Toast.LENGTH_SHORT).show();
                        mobile.requestFocus();
                    } else if (mobile.getText().toString().length() < 6) {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.mobile_crtion, Toast.LENGTH_SHORT).show();
                        mobile.requestFocus();
                    } else if (countryode.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.cntrycod_txt, Toast.LENGTH_SHORT).show();
                        mobile.requestFocus();
                    } else if (!countryode.getText().toString().contains("+")) {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.countrycode, Toast.LENGTH_SHORT).show();
                        mobile.requestFocus();
                    } else {
                        txtname = name.getText().toString();
                        txtcountrycode = countryode.getText().toString();
                        txtmobile = mobile.getText().toString();
                        dataDb2.add_usertmp(txtname, txtmobile, txtcountrycode);
                        if (cd.isConnectingToInternet()) {
                            pd.setMessage("Please wait...");
                            pd.setCancelable(false);
                            pd.show();
                            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Primary_Registration.this,  new OnSuccessListener<InstanceIdResult>() {
                                @Override
                                public void onSuccess(InstanceIdResult instanceIdResult) {
                                    String fcmId = instanceIdResult.getToken();
                                    dataDb2.addfcmid(fcmId);
                                    try {
                                        android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                    } catch (Exception a) {
                                        android_id = "NA";
                                    }
                                    FirebaseMessaging.getInstance().subscribeToTopic("statusupdate");
                                    newregistration();


                                }
                            }).addOnFailureListener(e->{
                                pd.dismiss();
                                Toasty.info(getApplicationContext(), "താല്\u200Dക്കാലിക പ്രശ്\u200Cനം ! ദയവായി 10 മിനിട്ടിന് ശേഷം ശ്രമിക്കുക ", Toast.LENGTH_LONG).show();

                            });
                        }
                        else
                        {
                            Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    public void newregistration() {
        try
        {
            userDataDB.drop_user();
            regs.setEnabled(false);
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("name", dataDb2.get_usrtmp())
                    .addFormDataPart("mobile", dataDb2.get_mobiletmp())
                    .addFormDataPart("countrycode", dataDb2.get_codetemp())
                    .addFormDataPart("fcmid", dataDb2.getfcmid())
                    .addFormDataPart("aid", android_id)
                    .build();
            Request request = new Request.Builder()
                    .url(Static_Variable.entypoint1 + "registrationfresh.php")
                    .post(body)
                    .build();

            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            Toasty.info(getApplicationContext(),Log.getStackTraceString(e), Toast.LENGTH_LONG).show();
                        }
                    });
                }
                @Override
                public void onResponse(Call call, final Response response) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                String result=response.body().string();
                                Toasty.success(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                                if (result.contains("404")) {
                                    pd.dismiss();

                                } else {
                                    if (result.contains("%:ok")) {
                                        pd.dismiss();
                                        String[] k = result.split("%:");
                                        userDataDB.add_user(k[0], dataDb2.get_usrtmp(), dataDb2.get_codetemp() + dataDb2.get_mobiletmp(), "0", "1");
                                        Intent intent = new Intent(getApplicationContext(), HeartOf_App.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    } else if (result.contains("Blocked")) {
                                        pd.dismiss();
                                        Toasty.info(getApplicationContext(), "Sorry ! you are blocked", Toast.LENGTH_SHORT).show();
                                    } else if (result.contains("otp")) {


                                        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                            @Override
                                            public void onVerificationCompleted(PhoneAuthCredential credential) {

                                                mVerificationInProgress = false;
                                                Toasty.info(getApplicationContext(), "Please try with another number", Toast.LENGTH_SHORT).show();
                                                pd.dismiss();

                                            }

                                            @Override
                                            public void onVerificationFailed(FirebaseException e) {



                                                mVerificationInProgress = false;
                                                Toasty.info(getApplicationContext(), "Please try with another number", Toast.LENGTH_SHORT).show();
                                                pd.dismiss();

                                                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                                                    Toasty.info(getApplicationContext(), "Please try with another number", Toast.LENGTH_SHORT).show();
                                                    pd.dismiss();

                                                } else if (e instanceof FirebaseTooManyRequestsException) {

                                                    Toasty.info(getApplicationContext(), "Please try with another number", Toast.LENGTH_SHORT).show();
                                                    pd.dismiss();

                                                }

                                            }

                                            @Override
                                            public void onCodeSent(String verificationId,
                                                                   PhoneAuthProvider.ForceResendingToken token) {

                                                pd.dismiss();
                                                mVerificationId = verificationId;
                                                Intent i = new Intent(getApplicationContext(), OTP.class);
                                                startActivity(i);
                                                finish();
                                                return;
                                            }
                                        };


                                        startPhoneNumberVerification(txtcountrycode + txtmobile);


                                    } else {
                                        pd.dismiss();
                                        Toasty.info(getApplicationContext(), Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                            catch (Exception a)
                            {
                                //  Toast.makeText(getApplicationContext(), Log.getStackTraceString(a),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        }
        catch (Exception a)
        {

        }
    }

    public static boolean containsDigit(String s) {
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    return true;
                }
            }
        }
        return false;
    }


    public void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, (AppCompatActivity) this, mCallbacks);
        mVerificationInProgress = true;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (!(context == null || permissions == null)) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
