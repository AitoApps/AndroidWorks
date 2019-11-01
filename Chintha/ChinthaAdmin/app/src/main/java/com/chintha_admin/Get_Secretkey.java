package com.chintha_admin;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Get_Secretkey extends AppCompatActivity {
    public static String txtmobile = "";
    ConnectionDetecter cd;
    Button getcode;
    Button getinfo;
    EditText mobilenumber;
    ProgressDialog pd;
    Button unblock;
    TextView vcode;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_get__secretkey);
        mobilenumber = (EditText) findViewById(R.id.mobilenumber);
        getcode = (Button) findViewById(R.id.getcode);
        vcode = (TextView) findViewById(R.id.vcode);
        getinfo = (Button) findViewById(R.id.getinfo);
        unblock = (Button) findViewById(R.id.unblock);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        getcode.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    Toast.makeText(getApplicationContext(), "no internet", Toast.LENGTH_SHORT).show();
                } else if (mobilenumber.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please enter mobile", Toast.LENGTH_SHORT).show();
                } else {
                    txtmobile = mobilenumber.getText().toString();
                    new updateage().execute(new String[0]);
                }
            }
        });
        getinfo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    Toast.makeText(getApplicationContext(), "no internet", Toast.LENGTH_SHORT).show();
                } else if (mobilenumber.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please enter mobile", Toast.LENGTH_SHORT).show();
                } else {
                    txtmobile = mobilenumber.getText().toString();
                    new getname().execute(new String[0]);
                }
            }
        });
        vcode.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    if (VERSION.SDK_INT < 11) {
                        ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setText(vcode.getText().toString());
                    } else {
                        ((android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("salmansuhailamp", vcode.getText().toString()));
                    }
                    Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
            }
        });
        unblock.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    Toast.makeText(getApplicationContext(), "no internet", Toast.LENGTH_SHORT).show();
                } else if (mobilenumber.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please enter mobile", Toast.LENGTH_SHORT).show();
                } else {
                    txtmobile = mobilenumber.getText().toString();
                    new unblock().execute(new String[0]);
                }
            }
        });
    }
    public class getname extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink+"getnamebymobile.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtmobile, "UTF-8");
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
                vcode.setText(result);
            } catch (Exception e) {
            }
        }
    }

    public class unblock extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink+"unblockfromadminapp.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtmobile, "UTF-8");
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
                vcode.setText(result);
            } catch (Exception e) {
            }
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
                String link=Tempvariable.weblink+"getsecretkey_admin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtmobile, "UTF-8");
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
                vcode.setText(result);
            } catch (Exception e) {
            }
        }
    }
}
