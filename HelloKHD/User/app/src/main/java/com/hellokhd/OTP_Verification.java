package com.hellokhd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import es.dmoral.toasty.Toasty;

public class OTP_Verification extends AppCompatActivity {
    public static EditText otp;
    ConnectionDetecter cd;
    Typeface face;
    ProgressDialog pd;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    Button verify;
    TextView pleasewait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp__verification);
        otp = (EditText) findViewById(R.id.otp);
        verify = (Button) findViewById(R.id.verify);
        pleasewait=findViewById(R.id.pleasewait);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        verify.setTypeface(face);
        otp.setTypeface(face);
        pleasewait.setTypeface(face);
        verify.setOnClickListener(new View.OnClickListener() {
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
                String link= Temp.weblink +"registration_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode("1:%"+Registartion.txtname+":%"+Registartion.txtmobile+":%"+udb.getfcmid(), "UTF-8");
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
                    udb.adduserid(k[0],k[1]);
                    Intent i=new Intent(getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                    return;
                }
                pd.dismiss();
                Toasty.info(getApplicationContext(), "Please contact Hello KHD TEAM", Toast.LENGTH_SHORT).show();
            } catch (Exception a) {
                //Toasty.info(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
    }
}

