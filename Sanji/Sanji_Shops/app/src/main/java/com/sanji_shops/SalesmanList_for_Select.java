package com.sanji_shops;

import adapter.Salesmanlist_Choose_ListAdapter;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import data.Salesmanlist_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SalesmanList_for_Select extends AppCompatActivity {
    ImageView back;
    Button btnsearch;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face;
    public List<Salesmanlist_FeedItem> feedItems;
    ImageView heart;
    ListView list;
    public Salesmanlist_Choose_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    EditText search;
    TextView text;
    String txtsearch = "";

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            SalesmanList_for_Select.pd.setMessage("Please wait...");
            SalesmanList_for_Select.pd.setCancelable(false);
            SalesmanList_for_Select.pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("shops_getsalesman_bysearch.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                sb2.append(URLEncoder.encode(SalesmanList_for_Select.txtsearch, "UTF-8"));
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
            try {
                if (result.contains(":%ok")) {
                    SalesmanList_for_Select.pd.dismiss();
                    SalesmanList_for_Select.feedItems.clear();
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 9;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Salesmanlist_FeedItem item = new Salesmanlist_FeedItem();
                        int m2 = m + 1;
                        item.setsn(got[m2]);
                        int m3 = m2 + 1;
                        item.setshopid(got[m3]);
                        int m4 = m3 + 1;
                        item.setname(got[m4]);
                        int m5 = m4 + 1;
                        item.setaddress(got[m5]);
                        int m6 = m5 + 1;
                        item.setidcard(got[m6]);
                        int m7 = m6 + 1;
                        item.setmobile(got[m7]);
                        int m8 = m7 + 1;
                        item.setplace(got[m8]);
                        int m9 = m8 + 1;
                        item.setimgsig(got[m9]);
                        m = m9 + 1;
                        item.setstatus(got[m]);
                        SalesmanList_for_Select.feedItems.add(item);
                    }
                    SalesmanList_for_Select.heart.setVisibility(View.GONE);
                    SalesmanList_for_Select.list.setVisibility(View.VISIBLE);
                    SalesmanList_for_Select.listAdapter.notifyDataSetChanged();
                    return;
                }
                Toasty.info(SalesmanList_for_Select.getApplicationContext(), "Sorry ! No Delivery Boys With This Number", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_salesman_list_for__select);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        search = (EditText) findViewById(R.id.search);
        btnsearch = (Button) findViewById(R.id.btnsearch);
        heart = (ImageView) findViewById(R.id.heart);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                SalesmanList_for_Select.onBackPressed();
            }
        });
        list = (ListView) findViewById(R.id.list);
        nodata = (ImageView) findViewById(R.id.nodata);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        text = (TextView) findViewById(R.id.text);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        text.setTypeface(face);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        feedItems = new ArrayList();
        listAdapter = new Salesmanlist_Choose_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        btnsearch.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SalesmanList_for_Select.search.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(SalesmanList_for_Select.getApplicationContext(), "Enter your mobile", Toast.LENGTH_SHORT).show();
                    SalesmanList_for_Select.search.requestFocus();
                    return;
                }
                SalesmanList_for_Select.txtsearch = SalesmanList_for_Select.search.getText().toString();
                if (SalesmanList_for_Select.cd.isConnectingToInternet()) {
                    new loadstatus().execute(new String[0]);
                } else {
                    Toasty.info(SalesmanList_for_Select.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void exitfun() {
        finish();
    }
}
