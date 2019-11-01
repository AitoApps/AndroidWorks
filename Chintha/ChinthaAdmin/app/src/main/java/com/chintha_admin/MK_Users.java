package com.chintha_admin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MK_Users extends AppCompatActivity {
    TextView capsulecounts;
    ConnectionDetecter cd;
    TextView hindicounts;
    TextView hindinew;
    TextView kisscounts;
    TextView mkcounts;
    ProgressDialog pd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_mk__users);
        mkcounts = (TextView) findViewById(R.id.mkcounts);
        hindicounts = (TextView) findViewById(R.id.hindicounts);
        kisscounts = (TextView) findViewById(R.id.kisscounts);
        capsulecounts = (TextView) findViewById(R.id.capsulecounts);
        hindinew = (TextView) findViewById(R.id.hindinew);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        if (cd.isConnectingToInternet()) {
            new updateage().execute(new String[0]);
        } else {
            Toast.makeText(getApplicationContext(), Tempvariable.nointernet, Toast.LENGTH_SHORT).show();
        }
    }
    public class updateage extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink5+"getcounts.php";
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
            try {
                pd.dismiss();
                String[] k = result.split(",");
                mkcounts = (TextView) findViewById(R.id.mkcounts);
                hindicounts = (TextView) findViewById(R.id.hindicounts);
                kisscounts = (TextView) findViewById(R.id.kisscounts);
                capsulecounts = (TextView) findViewById(R.id.capsulecounts);
                mkcounts.setText(k[0]);
                hindicounts.setText(k[1]);
                kisscounts.setText(k[2]);
                capsulecounts.setText(k[3]);
                hindinew.setText(k[4]);
            } catch (Exception e) {
            }
        }
    }
}
