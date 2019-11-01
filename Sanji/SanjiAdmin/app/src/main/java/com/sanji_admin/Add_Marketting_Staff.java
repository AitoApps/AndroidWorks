package com.sanji_admin;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Add_Marketting_Staff extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face;
    Typeface face1;
    EditText mobile;
    ProgressDialog pd;
    EditText staffname;
    TextView text;
    String txt_mobile = "",txt_name = "";
    TextView txtmobile;
    TextView txtstaffname;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    Button update;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_add__marketting__staff);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        update = (Button) findViewById(R.id.update);
        text = (TextView) findViewById(R.id.text);
        staffname = (EditText) findViewById(R.id.staffname);
        mobile = (EditText) findViewById(R.id.mobile);
        txtstaffname = (TextView) findViewById(R.id.txtstaffname);
        txtmobile = (TextView) findViewById(R.id.txtmobile);
        text.setTypeface(face);
        update.setTypeface(face);
        txtstaffname.setTypeface(face1);
        txtmobile.setTypeface(face1);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (staffname.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter Staff Name", Toast.LENGTH_SHORT).show();
                        staffname.requestFocus();
                    } else if (mobile.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter mobile number", Toast.LENGTH_SHORT).show();
                    } else if (cd.isConnectingToInternet()) {
                        txt_name = staffname.getText().toString();
                        txt_mobile = mobile.getText().toString();
                        new updatestaff().execute(new String[0]);
                    } else {
                        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }
        });
        if (Temp.ismarketedit == 1) {
            staffname.setText(Temp.edit_mkt_name);
            mobile.setText(Temp.edit_mkt_mobile);
        }
    }
    public class updatestaff extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please Wait.....");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("addnewmktstaff.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.ismarketedit);
                sb3.append(":%");
                sb3.append(Temp.edit_mkt_id);
                sb3.append(":%");
                sb3.append(txt_name);
                sb3.append(":%");
                sb3.append(txt_mobile);
                sb3.append(":%");
                sb3.append(Temp.fr_sn);
                sb2.append(URLEncoder.encode(sb3.toString(), "UTF-8"));
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
                pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                    finish();
                } else if (result.contains("exist")) {
                    Toasty.info(getApplicationContext(), "Sorry ! This Mobile Number already exist", Toast.LENGTH_SHORT).show();
                } else if (result.contains("userexi")) {
                    Toasty.info(getApplicationContext(), "Sorry ! This Mobile Number already exist", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }
        }
    }
}
