package com.dailybill_admin;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Add_Town_List extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    Typeface face;
    ProgressDialog pd;
    TextView text;
    EditText townid;
    EditText townname;
    public String txttown="";
    public String txttownid="";
    Button update;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_add__town__list);
        back = (ImageView) findViewById(R.id.back);
        text = (TextView) findViewById(R.id.text);
        townname = (EditText) findViewById(R.id.townname);
        townid = (EditText) findViewById(R.id.townid);
        update = (Button) findViewById(R.id.update);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        face = Typeface.createFromAsset(getAssets(), "heading.otf");
        text.setTypeface(face);
        if (Temp.townedit == 1) {
            townname.setText(Temp.townname);
            townid.setText(Temp.townid);
        }
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (cd.isConnectingToInternet()) {
                    String str = "";
                    if (townname.getText().toString().equalsIgnoreCase(str)) {
                        Toast.makeText(getApplicationContext(), "Please enter town name", Toast.LENGTH_SHORT).show();
                        townname.requestFocus();
                    } else if (townid.getText().toString().equalsIgnoreCase(str)) {
                        Toast.makeText(getApplicationContext(), "Please enter town id", Toast.LENGTH_SHORT).show();
                        townid.requestFocus();
                    } else {
                        txttown = townname.getText().toString();
                        txttownid = townid.getText().toString();
                        new updatearea().execute(new String[0]);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public class updatearea extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please Wait.....");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"addnewtown.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txttown+":%"+txttownid+":%"+Temp.townedit+":%"+Temp.townsn, "UTF-8");
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
                if (result.contains("ok")) {
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                String str = "Sorry ! This town already exist";
                if (result.contains("exist")) {
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                } else if (result.contains("userexi")) {
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }
        }
    }
}
