package com.footballstatus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import de.mateware.snacky.Snacky;

public class Registration extends AppCompatActivity {
    public static String txtname="";
    ConnectionDetecter cd;
    Typeface face;
    public EditText name;
    ProgressDialog pd;
    Button register;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        FirebaseApp.initializeApp(this);
        text = (TextView) findViewById(R.id.text);
        name = (EditText) findViewById(R.id.name);
        register = (Button) findViewById(R.id.register);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        text.setText("FootBall Status");
        text.setTypeface(face);
        name.setTypeface(face);
        register.setTypeface(face);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    register.setEnabled(true);
                    Snacky.builder().setActivty(Registration.this).setText(Temp.nointernet).setDuration(Snacky.LENGTH_SHORT).success().show();
                }
                else {
                    if (name.getText().toString().equalsIgnoreCase("")) {
                        Snacky.builder().setActivty(Registration.this).setText("Please enter your name").setDuration(Snacky.LENGTH_SHORT).error().show();
                        name.requestFocus();
                    } else {
                        register.setEnabled(false);
                        pd.setMessage("Please wait...");
                        pd.setCancelable(false);
                        pd.show();
                        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    udb.addfcmid("NA");
                                }
                                else
                                {
                                    udb.addfcmid(((InstanceIdResult) task.getResult()).getToken());
                                }
                                txtname = name.getText().toString();
                                FirebaseMessaging.getInstance().subscribeToTopic(Temp.fcmtopic);
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
                String link= Temp.weblink +"registration_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtname+":%"+udb.getfcmid(), "UTF-8");
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
                pd.dismiss();
                udb.adduser("ok");
                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            } catch (Exception a) {

            }
        }
    }
}
