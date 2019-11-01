package com.sanji;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

public class Registration extends AppCompatActivity {
    public String android_id;
    ConnectionDetecter cd;
    public EditText countryode;
    Typeface face;
    FirebaseAuth mAuth;
    OnVerificationStateChangedCallbacks mCallbacks;
    boolean mVerificationInProgress = false;
    public EditText mobile;
    public EditText name;
    ProgressDialog pd;
    Button register;
    TextView text;
    public String txtcountrycode;
    public String txtmobile;
    public String txtname;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public class registration extends AsyncTask<String, Void, String> {
        public registration() {
        }
        public void onPreExecute() {
            Registration.register.setEnabled(false);
        }
        public String doInBackground(String... arg0) {
            String str = "UTF-8";
            String str2 = ":%";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("registration_user.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Registration.txtname);
                sb3.append(str2);
                sb3.append(Registration.txtmobile);
                sb3.append(str2);
                sb3.append(Registration.udb.getfcmid());
                sb3.append(str2);
                sb3.append(Registration.android_id);
                sb2.append(URLEncoder.encode(sb3.toString(), str));
                String data2 = sb2.toString();
                URLConnection conn = new URL(link).openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb4 = new StringBuilder();
                while (true) {
                    String readLine = reader.readLine();
                    String line = readLine;
                    if (readLine == null) {
                        return sb4.toString();
                    }
                    sb4.append(line);
                }
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            try {
                Registration.register.setEnabled(true);
                Registration.mCallbacks = new OnVerificationStateChangedCallbacks() {
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        Registration.mVerificationInProgress = false;
                        Registration.pd.dismiss();
                    }

                    public void onVerificationFailed(FirebaseException e) {
                        Registration.mVerificationInProgress = false;
                        Registration.pd.dismiss();
                        String str = "Sorry ! Please try another number";
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toasty.error(Registration.getApplicationContext(), str, 0).show();
                            Registration.pd.dismiss();
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            Toasty.error(Registration.getApplicationContext(), str, 0).show();
                            Registration.pd.dismiss();
                        }
                    }

                    public void onCodeSent(String verificationId, ForceResendingToken token) {
                        Registration.pd.dismiss();
                        Temp.mVerificationId = verificationId;
                        Registration.startActivity(new Intent(Registration.getApplicationContext(), OTP_Verification.class));
                        Registration.finish();
                    }
                };
                if (result.contains(",ok")) {
                    FirebaseMessaging.getInstance().subscribeToTopic(Temp.firebasetopic);
                    Registration.pd.dismiss();
                    String[] k = result.split(",");
                    UserDatabaseHandler userDatabaseHandler = Registration.udb;
                    String str = k[0];
                    String str2 = Registration.txtname;
                    StringBuilder sb = new StringBuilder();
                    sb.append(Registration.txtcountrycode);
                    sb.append(Registration.txtmobile);
                    userDatabaseHandler.adduser(str, str2, sb.toString());
                    Registration.finish();
                    return;
                }
                if (result.contains("otp")) {
                    Registration.udb.addtempuser(Registration.txtname, Registration.txtmobile, Registration.txtcountrycode, Registration.android_id);
                    Registration registration = Registration.this;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(Registration.txtcountrycode);
                    sb2.append(Registration.txtmobile);
                    registration.startPhoneNumberVerification(sb2.toString());
                } else if (result.contains("error")) {
                    Registration.pd.dismiss();
                    Toasty.info(Registration.getApplicationContext(), "ക്ഷമിക്കണം !!! താങ്കള്‍ എന്റര്‍ ചെയ്ത വിവരങ്ങള്‍ തെറ്റാണ്‌ ", 0).show();
                } else {
                    Registration.pd.dismiss();
                    Toasty.info(Registration.getApplicationContext(), Temp.tempproblem, 0).show();
                }
            } catch (Exception a) {
                Toasty.info(Registration.getApplicationContext(), Log.getStackTraceString(a), 1).show();
            }
        }
    }

    public Registration() {
        String str = "";
        txtname = str;
        txtmobile = str;
        android_id = str;
        txtcountrycode = str;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_registration);
        text = (TextView) findViewById(R.id.text);
        name = (EditText) findViewById(R.id.name);
        mobile = (EditText) findViewById(R.id.mobile);
        countryode = (EditText) findViewById(R.id.countryode);
        register = (Button) findViewById(R.id.register);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        text.setText(Temp.apptitle);
        text.setTypeface(face);
        name.setTypeface(face);
        mobile.setTypeface(face);
        register.setTypeface(face);
        countryode.setTypeface(face);
        mAuth = FirebaseAuth.getInstance();
        TelephonyManager manager = (TelephonyManager) getSystemService("phone");
        PhoneNumberUtil phoneutil = PhoneNumberUtil.getInstance();
        EditText editText = countryode;
        StringBuilder sb = new StringBuilder();
        sb.append("+");
        sb.append(phoneutil.getCountryCodeForRegion(manager.getSimCountryIso().toUpperCase()));
        editText.setText(sb.toString());
        register.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (Registration.cd.isConnectingToInternet()) {
                    String str = "";
                    if (Registration.name.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(Registration.getApplicationContext(), "Please enter your name", 0).show();
                        Registration.name.requestFocus();
                    } else if (Registration.mobile.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(Registration.getApplicationContext(), "Please enter your mobile", 0).show();
                        Registration.mobile.requestFocus();
                    } else if (Registration.countryode.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(Registration.getApplicationContext(), "Please enter country code", 0).show();
                        Registration.mobile.requestFocus();
                    } else if (!Registration.countryode.getText().toString().contains("+")) {
                        Toasty.info(Registration.getApplicationContext(), "Please add '+' Sign before the country code", 0).show();
                        Registration.mobile.requestFocus();
                    } else {
                        Registration.pd.setMessage("Please wait...");
                        Registration.pd.setCancelable(false);
                        Registration.pd.show();
                        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Registration.pd.dismiss();
                                    Toasty.info(Registration.getApplicationContext(), "താല്‍ക്കാലിക പ്രശ്‌നം ! ദയവായി 10 മിനിട്ടിന് ശേഷം ശ്രമിക്കുക ", 1).show();
                                    return;
                                }
                                Registration.udb.addfcmid(((InstanceIdResult) task.getResult()).getToken());
                                Registration.txtname = Registration.name.getText().toString();
                                Registration.txtmobile = Registration.mobile.getText().toString();
                                Registration.txtcountrycode = Registration.countryode.getText().toString();
                                try {
                                    Registration.android_id = Secure.getString(Registration.getContentResolver(), "android_id");
                                } catch (Exception e) {
                                    Registration.android_id = "NA";
                                }
                                new registration().execute(new String[0]);
                            }
                        });
                    }
                } else {
                    Toasty.info(Registration.getApplicationContext(), Temp.nointernet, 0).show();
                }
            }
        });
    }


    public void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, (Activity) this, mCallbacks);
        mVerificationInProgress = true;
    }
}
