package com.sanji;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class OTP_Verification extends AppCompatActivity {
    ConnectionDetecter cd;
    Typeface face;
    TextView footer;
    FirebaseAuth mAuth;
    EditText otp;
    ProgressDialog pd;
    Button register;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public class registration extends AsyncTask<String, Void, String> {
        public registration() {
        }
        public void onPreExecute() {
            OTP_Verification.register.setEnabled(false);
        }
        public String doInBackground(String... arg0) {
            String str = "UTF-8";
            String str2 = ":%";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("registration_otp.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(OTP_Verification.udb.gettempuser());
                sb3.append(str2);
                sb3.append(OTP_Verification.udb.gettempccode());
                sb3.append(OTP_Verification.udb.gettempmobile());
                sb3.append(str2);
                sb3.append(OTP_Verification.udb.getfcmid());
                sb3.append(str2);
                sb3.append(OTP_Verification.udb.gettempandrodid());
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
                OTP_Verification.register.setEnabled(true);
                OTP_Verification.pd.dismiss();
                if (result.contains(",ok")) {
                    FirebaseMessaging.getInstance().subscribeToTopic(Temp.firebasetopic);
                    String[] k = result.split(",");
                    UserDatabaseHandler userDatabaseHandler = OTP_Verification.udb;
                    String str = k[0];
                    String str2 = OTP_Verification.udb.gettempuser();
                    StringBuilder sb = new StringBuilder();
                    sb.append(OTP_Verification.udb.gettempccode());
                    sb.append(OTP_Verification.udb.gettempmobile());
                    userDatabaseHandler.adduser(str, str2, sb.toString());
                    Context applicationContext = OTP_Verification.getApplicationContext();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(OTP_Verification.udb.get_mobile());
                    sb2.append("");
                    Toasty.info(applicationContext, sb2.toString(), 1).show();
                    OTP_Verification.finish();
                    return;
                }
                if (result.contains("error")) {
                    Toasty.info(OTP_Verification.getApplicationContext(), "ക്ഷമിക്കണം !!! താങ്കള്‍ എന്റര്‍ ചെയ്ത വിവരങ്ങള്‍ തെറ്റാണ്‌ ", 0).show();
                } else {
                    Toasty.info(OTP_Verification.getApplicationContext(), Temp.tempproblem, 0).show();
                }
            } catch (Exception e) {
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_otp__verification);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        text = (TextView) findViewById(R.id.text);
        footer = (TextView) findViewById(R.id.footer);
        otp = (EditText) findViewById(R.id.otp);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        register = (Button) findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();
        text.setTypeface(face);
        footer.setTypeface(face);
        otp.setTypeface(face);
        register.setTypeface(face);
        footer.setText("താങ്കള്‍ നല്‍കിയ മൊബൈല്‍ നമ്പറിലേക്ക് ഒരു ഒ.ടി.പി വരുന്നത് വരെ ദയവായി കാത്തിരിക്കുക ");
        register.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!OTP_Verification.cd.isConnectingToInternet()) {
                    Toasty.info(OTP_Verification.getApplicationContext(), Temp.nointernet, 0).show();
                } else if (OTP_Verification.otp.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(OTP_Verification.getApplicationContext(), "Please enter OTP", 1).show();
                } else {
                    OTP_Verification.signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(Temp.mVerificationId, OTP_Verification.otp.getText().toString()));
                }
            }
        });
    }


    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        pd.setMessage("Please Wait.....");
        pd.setCancelable(false);
        pd.show();
        mAuth.signInWithCredential(credential).addOnCompleteListener((Activity) this, (OnCompleteListener<TResult>) new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    new registration().execute(new String[0]);
                    return;
                }
                boolean z = task.getException() instanceof FirebaseAuthInvalidCredentialsException;
                OTP_Verification.pd.dismiss();
                Toasty.info(OTP_Verification.getApplicationContext(), "താങ്കള്‍ നല്‍കിയ വെരിഫിക്കേഷന്‍ കോഡ് തെറ്റാണ്‌ ", 0).show();
            }
        });
    }
}
