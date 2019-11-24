package com.hellokhd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class Registartion extends AppCompatActivity {

    public static String txtcountrycode="";
    public static String txtmobile="";
    public static String txtname="";
    ConnectionDetecter cd;
    public EditText countryode;
    Typeface face;
    public EditText mobile;
    public EditText name;
    ProgressDialog pd;
    Button register;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registartion);
        text = (TextView) findViewById(R.id.text);
        name = (EditText) findViewById(R.id.name);
        mobile = (EditText) findViewById(R.id.mobile);
        countryode = (EditText) findViewById(R.id.countryode);
        register = (Button) findViewById(R.id.register);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        text.setText("Hello KHD");
        text.setTypeface(face);
        name.setTypeface(face);
        mobile.setTypeface(face);
        register.setTypeface(face);
        countryode.setTypeface(face);
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        PhoneNumberUtil phoneutil = PhoneNumberUtil.getInstance();
        countryode.setText("+"+phoneutil.getCountryCodeForRegion(manager.getSimCountryIso().toUpperCase()));
        Random rand = new Random();
        udb.addotpnumber(String.format("%04d", new Object[]{Integer.valueOf(rand.nextInt(10000))}));
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    register.setEnabled(true);
                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }  else {
                    String str = "";
                    if (name.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                        name.requestFocus();
                    } else if (mobile.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(getApplicationContext(), "Please enter your mobile", Toast.LENGTH_SHORT).show();
                        mobile.requestFocus();
                    } else if (countryode.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(getApplicationContext(), "Please enter country code", Toast.LENGTH_SHORT).show();
                        mobile.requestFocus();
                    } else if (!countryode.getText().toString().contains("+")) {
                        Toasty.info(getApplicationContext(), "Please add '+' Sign before the country code", Toast.LENGTH_SHORT).show();
                        mobile.requestFocus();
                    } else {
                        register.setEnabled(false);
                        pd.setMessage("Please wait...");
                        pd.setCancelable(false);
                        pd.show();
                        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    pd.dismiss();
                                    register.setEnabled(true);
                                    Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_LONG).show();
                                    return;
                                }
                                udb.addfcmid(((InstanceIdResult) task.getResult()).getToken());
                                txtname = name.getText().toString();
                                txtmobile = mobile.getText().toString();
                                txtcountrycode = countryode.getText().toString();

                                new registration().execute(new String[0]);
                            }
                        });
                    }
                }
            }
        });
    }
    public class registration extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            register.setEnabled(false);
        }
        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink +"sendotptouser.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtname+":%"+txtmobile+":%"+udb.getotpnumber(), "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            try {
                register.setEnabled(true);
                if (result.contains("otp")) {
                    Task<Void> task = SmsRetriever.getClient(Registartion.this).startSmsRetriever();
                    task.addOnSuccessListener(new OnSuccessListener<Void>() {
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            startActivity(new Intent(getApplicationContext(), OTP_Verification.class));
                        }
                    });
                    task.addOnFailureListener(new OnFailureListener() {
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            startActivity(new Intent(getApplicationContext(), OTP_Verification.class));
                        }
                    });
                } else {
                    pd.dismiss();
                    Toasty.info(getApplicationContext(), "Please contact Hello KHD TEAM", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception a) {
                //Toasty.info(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
    }
}
