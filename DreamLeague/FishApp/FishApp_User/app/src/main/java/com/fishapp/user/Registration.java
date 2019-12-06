package com.fishapp.user;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Registration extends AppCompatActivity {
    public static String android_id="";
    public static String txt_areaid="";
    public static String txt_areaname="";
    public static String txt_delitime="";
    public static String txt_otp="";
    public static String txtcountrycode="";
    public static String txtmobile="";
    public static String txtname="";
    Spinner area;
    ConnectionDetecter cd;
    public EditText countryode;
    Typeface face;
    List<String> lst_areaid = new ArrayList();
    List<String> lst_areaname = new ArrayList();
    List<String> lst_delitime = new ArrayList();
    public EditText mobile;
    public EditText name;
    ProgressDialog pd;
    Button register;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_registration);
        area = (Spinner) findViewById(R.id.area);
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
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        PhoneNumberUtil phoneutil = PhoneNumberUtil.getInstance();
        countryode.setText("+"+phoneutil.getCountryCodeForRegion(manager.getSimCountryIso().toUpperCase()));
        Random rand = new Random();
        udb.addotpnumber(String.format("%04d", new Object[]{Integer.valueOf(rand.nextInt(10000))}));
        register.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    register.setEnabled(true);
                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                } else if (area.getSelectedItemPosition() == 0) {
                    Toasty.info(getApplicationContext(), "Please choose your area", Toast.LENGTH_SHORT).show();
                } else {
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
                                    Toasty.info(getApplicationContext(), "താല്‍ക്കാലിക പ്രശ്‌നം ! ദയവായി 10 മിനിട്ടിന് ശേഷം ശ്രമിക്കുക ", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                udb.addfcmid(((InstanceIdResult) task.getResult()).getToken());
                                txtname = name.getText().toString();
                                txtmobile = mobile.getText().toString();
                                txtcountrycode = countryode.getText().toString();
                                try {
                                    android_id = Secure.getString(getContentResolver(), "android_id");
                                } catch (Exception e) {
                                    android_id = "NA";
                                }
                                new registration().execute(new String[0]);
                            }
                        });
                    }
                }
            }
        });
        if (cd.isConnectingToInternet()) {
            new featching_area().execute(new String[0]);
        } else {
            Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
        }
    }
    public class featching_area extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            pd.setMessage("Loading your area....");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink +"user_getarealist.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode("", "UTF-8");
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
            String str = "0";
            try {
                pd.dismiss();
                if (result.contains(":%ok")) {
                    try {
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 3;
                        int m = -1;
                        lst_areaid.clear();
                        lst_areaname.clear();
                        lst_delitime.clear();
                        lst_areaid.add(str);
                        lst_delitime.add(str);
                        lst_areaname.add("Select Your Area");
                        for (int i = 1; i <= k; i++) {
                            m=m+1;
                            lst_areaid.add(got[m].trim());
                            m=m+1;
                            lst_areaname.add(got[m].trim());
                            m = m + 1;
                            lst_delitime.add(got[m].trim());
                        }
                        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(Registration.this, android.R.layout.simple_spinner_item, lst_areaname) {
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View v = super.getView(position, convertView, parent);
                                ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                                ((TextView) v).setTextSize(16.0f);
                                ((TextView) v).setTypeface(face);
                                return v;
                            }

                            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                View v = super.getDropDownView(position, convertView, parent);
                                ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                                ((TextView) v).setTextSize(16.0f);
                                ((TextView) v).setTypeface(face);
                                return v;
                            }
                        };
                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        area.setAdapter(dataAdapter2);
                        area.setOnItemSelectedListener(new OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                                txt_areaid = (String) lst_areaid.get(arg2);
                                txt_areaname = (String) lst_areaname.get(arg2);
                                txt_delitime = (String) lst_delitime.get(arg2);
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e2) {
            }
        }
    }

    public class registration extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            register.setEnabled(false);
        }
        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink +"registration_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtname+":%"+txtmobile+":%"+udb.getfcmid()+":%"+android_id+":%"+txt_areaid+":%"+udb.getotpnumber(), "UTF-8");
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
                if (result.contains(",ok")) {
                    pd.dismiss();
                    FirebaseMessaging.getInstance().subscribeToTopic(Temp.fcmtopic);
                    pd.dismiss();
                    String[] k = result.split(",");
                    udb.adduser(k[0], txtname, txtcountrycode+txtmobile);
                    udb.addarea(txt_areaid, txt_areaname, txt_delitime);
                    Intent i=new Intent(getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                    return;
                }
                if (result.contains("otp")) {
                    if (result.contains("Sent Successfully")) {
                        Task<Void> task = SmsRetriever.getClient(Registration.this).startSmsRetriever();
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
                        Toasty.info(getApplicationContext(), "Please contact FISH APP TEAM", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception a) {
                Toasty.info(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
    }
}
