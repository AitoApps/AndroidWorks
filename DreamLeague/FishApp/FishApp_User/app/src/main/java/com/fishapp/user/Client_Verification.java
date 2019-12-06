package com.fishapp.user;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Client_Verification extends AppCompatActivity {
    ConnectionDetecter cd;
    EditText clientid;
    ProgressDialog pd;
    TextView text;
    public String txt_verify = "";
    Button verify;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_client__verification);
        clientid = (EditText) findViewById(R.id.clientid);
        verify = (Button) findViewById(R.id.verify);
        text = (TextView) findViewById(R.id.text);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        verify.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!cd.isConnectingToInternet()) {
                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                } else if (clientid.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(getApplicationContext(), "Please enter your client id", Toast.LENGTH_SHORT).show();
                    clientid.requestFocus();
                } else {
                    txt_verify = verify.getText().toString();
                    new checkclientid().execute(new String[0]);
                }
            }
        });
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }
    public class checkclientid extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {

            try {

                String link= Temp.weblink +"check_clientverification.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txt_verify+":%"+Temp.clientcatid, "UTF-8");
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
                if (result.equalsIgnoreCase("ok")) {
                    startActivity(new Intent(getApplicationContext(), Client_List.class));
                    finish();
                } else if (result.equalsIgnoreCase("error")) {
                    Toasty.info(getApplicationContext(), "ദയവായി താങ്കള്‍ നല്‍കിയ വെരിഫിക്കേഷന്‍ കോഡ് പരിശോധിക്കുക ", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }
        }
    }
}
