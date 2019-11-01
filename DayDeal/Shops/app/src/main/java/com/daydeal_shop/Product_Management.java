package com.daydeal_shop;

import adapter.Productlist_ListAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import data.Productlist_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Product_Management extends AppCompatActivity {
    ImageView addnew;
    ImageView back;
    ConnectionDetecter cd;
    Typeface face;
    public List<Productlist_FeedItem> feedItems;
    RelativeLayout footerview;
    ImageView heart;
    public int limit = 0;
    ListView list;
    public Productlist_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_product__management);
        heart = (ImageView) findViewById(R.id.heart);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        addnew = (ImageView) findViewById(R.id.addnew);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        list = (ListView) findViewById(R.id.list);
        nodata = (ImageView) findViewById(R.id.nodata);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        footerview = (RelativeLayout) getLayoutInflater().inflate(R.layout.footerview, null);
        list.addFooterView(footerview);
        footerview.setVisibility(View.GONE);
        text = (TextView) findViewById(R.id.text);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        text.setText("Product List");
        text.setTypeface(face);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        feedItems = new ArrayList();
        listAdapter = new Productlist_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (cd.isConnectingToInternet()) {
                    nointernet.setVisibility(View.GONE);
                    limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                nointernet.setVisibility(View.VISIBLE);
                Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        addnew.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.isproductedit = 0;
                startActivity(new Intent(getApplicationContext(), Add_Product.class));
            }
        });
    }


    public void onResume() {
        super.onResume();
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            limit = 0;
            new loadstatus().execute(new String[0]);
            return;
        }
        nointernet.setVisibility(View.VISIBLE);
        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
    }

    public void changeitem(int position, String sn1, String itemname1, String offerprice1, String orginalprice1, String discription1, String imgsig1, String status1, String itemkeyword1, String cashback1, String catid1) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
            Productlist_FeedItem item = new Productlist_FeedItem();
            item.setsn(sn1);
            item.setitemname(itemname1);
            item.setofferprice(offerprice1);
            item.setorginalprice(orginalprice1);
            item.setstatus(status1);
            item.setdiscription(discription1);
            item.setimgsig1(imgsig1);
            item.setCashback(cashback1);
            item.setItemkeyword(itemkeyword1);
            item.setcatid(catid1);
            feedItems.add(position, item);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void removeitem(int position) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }
    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            nointernet.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
            limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Temp.weblink +"getproductlist_byshopid.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+":%"+udb.get_shopid(), "UTF-8");
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
                if (result.contains(":%ok")) {
                    feedItems.clear();
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 10;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Productlist_FeedItem item = new Productlist_FeedItem();
                        m=m+1;
                        item.setsn(got[m]);
                        m=m+1;
                        item.setitemname(got[m]);
                        m=m+1;
                        item.setofferprice(got[m]);
                        m=m+1;
                        item.setorginalprice(got[m]);
                        m=m+1;
                        item.setstatus(got[m]);
                        m=m+1;
                        item.setdiscription(got[m]);
                        m=m+1;
                        item.setimgsig1(got[m]);
                        m=m+1;
                        item.setcatid(got[m]);
                        m=m+1;
                        item.setCashback(got[m]);
                        m=m+1;
                        item.setItemkeyword(got[m]);
                        feedItems.add(item);
                    }
                    heart.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                nodata.setVisibility(View.VISIBLE);
                heart.setVisibility(View.GONE);
                footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
}
