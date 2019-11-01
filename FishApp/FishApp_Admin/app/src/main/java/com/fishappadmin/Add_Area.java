package com.fishappadmin;

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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Add_Area extends AppCompatActivity {
    EditText areaname;
    ImageView back;
    ConnectionDetecter cd;
    EditText deliverytime;
    Typeface face;
    ProgressDialog pd;
    TextView text;
    Button update;
    public String  txt_areaname = "",txt_deliverytime="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_add__area);
        back = (ImageView) findViewById(R.id.back);
        text = (TextView) findViewById(R.id.text);
        areaname = (EditText) findViewById(R.id.areaname);
        deliverytime = (EditText) findViewById(R.id.deliverytime);
        update = (Button) findViewById(R.id.update);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        text.setTypeface(face);
        areaname.setTypeface(face);
        deliverytime.setTypeface(face);
        update.setTypeface(face);
        if (Temp.areaedit == 1) {
            areaname.setText(Temp.areaname);
            deliverytime.setText(Temp.deliverytime);
        }
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (cd.isConnectingToInternet()) {
                    String str = "";
                    if (areaname.getText().toString().equalsIgnoreCase(str)) {
                        Toast.makeText(getApplicationContext(), "Please enter area name", Toast.LENGTH_SHORT).show();
                        areaname.requestFocus();
                    } else if (deliverytime.getText().toString().equalsIgnoreCase(str)) {
                        Toast.makeText(getApplicationContext(), "Please enter delivery time", Toast.LENGTH_SHORT).show();
                        deliverytime.requestFocus();
                    } else {
                        txt_areaname = areaname.getText().toString();
                        txt_deliverytime = deliverytime.getText().toString();
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

                String link=Temp.weblink +"addnewarea.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txt_areaname+":%"+txt_deliverytime+":%"+Temp.areaedit+":%"+Temp.areaid, "UTF-8");
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
                String str = "Sorry ! This area already exist";
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
