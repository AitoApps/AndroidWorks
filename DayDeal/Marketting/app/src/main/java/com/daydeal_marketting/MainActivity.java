package com.daydeal_marketting;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.bumptech.glide.Glide;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    TextView balance;
    TextView commision;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face;
    TextView incentive;
    SwipeRefreshLayout layout;
    ImageView loading;
    TextView paid;
    RelativeLayout reports;
    String rupee;
    Button shops;
    TextView text;
    TextView total;
    TextView txtbalance;
    TextView txtcommision;
    TextView txtincentive;
    TextView txtpaid;
    TextView txttotal;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        rupee = getResources().getString(R.string.Rs);
        shops = (Button) findViewById(R.id.shops);
        text = (TextView) findViewById(R.id.text);
        txtcommision = (TextView) findViewById(R.id.txtcommision);
        commision = (TextView) findViewById(R.id.commision);
        txtincentive = (TextView) findViewById(R.id.txtincentive);
        incentive = (TextView) findViewById(R.id.incentive);
        txttotal = (TextView) findViewById(R.id.txttotal);
        total = (TextView) findViewById(R.id.total);
        txtpaid = (TextView) findViewById(R.id.txtpaid);
        paid = (TextView) findViewById(R.id.paid);
        txtbalance = (TextView) findViewById(R.id.txtbalance);
        balance = (TextView) findViewById(R.id.balance);
        loading = (ImageView) findViewById(R.id.loading);
        reports = (RelativeLayout) findViewById(R.id.reports);
        layout = (SwipeRefreshLayout) findViewById(R.id.layout);
        layout.setEnabled(true);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(loading);
        txtcommision.setTypeface(face);
        commision.setTypeface(face);
        txtincentive.setTypeface(face);
        incentive.setTypeface(face);
        txttotal.setTypeface(face);
        total.setTypeface(face);
        txtpaid.setTypeface(face);
        paid.setTypeface(face);
        txtbalance.setTypeface(face);
        balance.setTypeface(face);
        String str = "";
        if (udb.get_staffid().equalsIgnoreCase(str)) {
            startActivity(new Intent(getApplicationContext(), Registartion.class));
            finish();
            return;
        }
        text.setText(udb.get_staffname());
        try {
            if (db.getscreenwidth().equalsIgnoreCase(str)) {
                int width = getResources().getDisplayMetrics().widthPixels;
                DatabaseHandler databaseHandler = db;
                StringBuilder sb = new StringBuilder();
                sb.append(width);
                sb.append(str);
                databaseHandler.addscreenwidth(sb.toString());
            }
        } catch (Exception e) {
        }
        layout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                layout.setRefreshing(true);
                new getprofit().execute(new String[0]);
            }
        });
        shops.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShopList.class));
            }
        });
        new getprofit().execute(new String[0]);
    }
    public class getprofit extends AsyncTask<String, Void, String> {
        public getprofit() {
        }


        public void onPreExecute() {
            loading.setVisibility(View.VISIBLE);
            reports.setVisibility(View.INVISIBLE);
        }


        public String doInBackground(String... arg0) {
            try {

                String link= Temp.weblink +"getmarkettingstaffprofit_mapp.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(udb.get_staffid(), "UTF-8");
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
                layout.setRefreshing(false);
                if (result.trim().contains(":%")) {
                    String[] res = result.trim().split(":%");
                    Float commi = Float.valueOf(Float.parseFloat(res[0]));
                    Float inceti = Float.valueOf(Float.parseFloat(res[1]));
                    Float paidamt = Float.valueOf(Float.parseFloat(res[2]));
                    Float totalamt = Float.valueOf(commi.floatValue() + inceti.floatValue());
                    Float balanceamt = Float.valueOf(totalamt.floatValue() - paidamt.floatValue());

                    commision.setText(rupee+String.format("%.2f",commi));
                    incentive.setText(rupee+String.format("%.2f", inceti));
                    paid.setText(rupee+String.format("%.2f", paidamt));
                    total.setText(rupee+String.format("%.2f", totalamt));
                    balance.setText(rupee+String.format("%.2f", balanceamt));
                    loading.setVisibility(View.INVISIBLE);
                    reports.setVisibility(View.VISIBLE);
                    return;
                }
                loading.setVisibility(View.INVISIBLE);
                reports.setVisibility(View.VISIBLE);
            } catch (Exception a) {
                Toasty.info(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
    }

}
