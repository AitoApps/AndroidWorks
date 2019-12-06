package com.fishapp.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class OTP_Verification extends AppCompatActivity {
    public static EditText otp;
    ConnectionDetecter cd;
    Typeface face;
    ProgressDialog pd;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    Button verify;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_otp__verification);
        otp = (EditText) findViewById(R.id.otp);
        verify = (Button) findViewById(R.id.verify);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        verify.setTypeface(face);
        otp.setTypeface(face);
        verify.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (otp.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else if (!otp.getText().toString().equalsIgnoreCase(udb.getotpnumber())) {
                    Toast.makeText(getApplicationContext(), "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                } else if (cd.isConnectingToInternet()) {
                    new registration().execute(new String[0]);
                } else {
                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public class registration extends AsyncTask<String, Void, String> {
        public registration() {
        }
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            verify.setEnabled(false);
        }
        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink +"otpvalidation.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Registration.txtname+":%"+Registration.txtmobile+":%"+udb.getfcmid()+":%"+Registration.android_id+":%"+Registration.txt_areaid, "UTF-8");
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
                verify.setEnabled(true);
                if (result.contains(",ok")) {
                    pd.dismiss();
                    FirebaseMessaging.getInstance().subscribeToTopic(Temp.fcmtopic);
                    String[] k = result.split(",");
                    udb.adduser(k[0], Registration.txtname, Registration.txtcountrycode+Registration.txtmobile);
                    udb.addarea(Registration.txt_areaid, Registration.txt_areaname, Registration.txt_delitime);
                    Intent i=new Intent(getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                    return;
                }
                pd.dismiss();
                Toasty.info(getApplicationContext(), "Please contact FISH APP TEAM", Toast.LENGTH_SHORT).show();
            } catch (Exception a) {
                //Toasty.info(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
    }
}
