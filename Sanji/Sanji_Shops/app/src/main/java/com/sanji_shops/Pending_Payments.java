package com.sanji_shops;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Pending_Payments extends AppCompatActivity {
    ImageView back;
    TextView balance;
    ConnectionDetecter cd;
    Typeface face;
    ImageView heart;
    ImageView nointernet;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Pending_Payments.balance.setVisibility(View.GONE);
            Pending_Payments.heart.setVisibility(View.VISIBLE);
            Pending_Payments.nointernet.setVisibility(View.GONE);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getshopbalance.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                sb2.append(URLEncoder.encode(Pending_Payments.udb.get_shopid(), "UTF-8"));
                String data2 = sb2.toString();
                URLConnection conn = new URL(link).openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb3 = new StringBuilder();
                while (true) {
                    String readLine = reader.readLine();
                    String line = readLine;
                    if (readLine == null) {
                        return sb3.toString();
                    }
                    sb3.append(line);
                }
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            Pending_Payments.heart.setVisibility(View.GONE);
            if (result.contains(":%ok")) {
                Pending_Payments.balance.setVisibility(View.VISIBLE);
                Pending_Payments.balance.setText(result.split(":%")[0]);
                return;
            }
            Toasty.info(Pending_Payments.getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_pending__payments);
        balance = (TextView) findViewById(R.id.balance);
        back = (ImageView) findViewById(R.id.back);
        heart = (ImageView) findViewById(R.id.heart);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        cd = new ConnectionDetecter(this);
        face = Typeface.createFromAsset(getAssets(), "font/heading.otf");
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Pending_Payments.cd.isConnectingToInternet()) {
                    Pending_Payments.nointernet.setVisibility(View.GONE);
                    new loadstatus().execute(new String[0]);
                    return;
                }
                Pending_Payments.nointernet.setVisibility(View.VISIBLE);
                Toasty.info(Pending_Payments.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onResume() {
        super.onResume();
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            new loadstatus().execute(new String[0]);
            return;
        }
        nointernet.setVisibility(View.VISIBLE);
        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
    }
}
