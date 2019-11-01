package com.sanji_admin;

import adapter.Product_Cat_list_ListAdapter;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import data.Product_cat_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Product_Catogery extends AppCompatActivity {
    ImageView addproduct;
    ImageView back;
    ConnectionDetecter cd;
    Typeface face;
    public List<Product_cat_FeedItem> feedItems;
    ImageView heart;
    public int limit = 0;
    public Product_Cat_list_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    RecyclerView recylerview;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Product_Catogery.nointernet.setVisibility(View.GONE);
            Product_Catogery.nodata.setVisibility(View.GONE);
            Product_Catogery.recylerview.setVisibility(View.GONE);
            Product_Catogery.heart.setVisibility(View.VISIBLE);
            Product_Catogery.limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getproductcatgory_admin.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                sb2.append(URLEncoder.encode("", "UTF-8"));
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
                    Product_Catogery.feedItems.clear();
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 4;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Product_cat_FeedItem item = new Product_cat_FeedItem();
                        int m2 = m + 1;
                        item.setsn(got[m2].trim());
                        int m3 = m2 + 1;
                        item.settitle(got[m3]);
                        int m4 = m3 + 1;
                        item.setimgsig(got[m4]);
                        m = m4 + 1;
                        item.setcatorder(got[m]);
                        Product_Catogery.feedItems.add(item);
                    }
                    Product_Catogery.heart.setVisibility(View.GONE);
                    Product_Catogery.recylerview.setVisibility(View.VISIBLE);
                    Product_Catogery.listAdapter.notifyDataSetChanged();
                    return;
                }
                Product_Catogery.nodata.setVisibility(View.VISIBLE);
                Product_Catogery.heart.setVisibility(View.GONE);
                Product_Catogery.recylerview.setVisibility(View.GONE);
            } catch (Exception a) {
                Toasty.info(Product_Catogery.getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_product__catogery);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        addproduct = (ImageView) findViewById(R.id.addproduct);
        back = (ImageView) findViewById(R.id.back);
        text = (TextView) findViewById(R.id.text);
        addproduct.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.productcatedit = 0;
                Product_Catogery.startActivity(new Intent(Product_Catogery.getApplicationContext(), Add_ProductCat.class));
            }
        });
        text.setTypeface(face);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Product_Catogery.onBackPressed();
            }
        });
        heart = (ImageView) findViewById(R.id.heart);
        cd = new ConnectionDetecter(this);
        recylerview = (RecyclerView) findViewById(R.id.recylerview);
        nodata = (ImageView) findViewById(R.id.nodata);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        feedItems = new ArrayList();
        listAdapter = new Product_Cat_list_ListAdapter(this, feedItems);
        recylerview.setLayoutManager(new GridLayoutManager(this, 1));
        recylerview.setAdapter(listAdapter);
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (Product_Catogery.cd.isConnectingToInternet()) {
                    Product_Catogery.nointernet.setVisibility(View.GONE);
                    Product_Catogery.limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                Product_Catogery.nointernet.setVisibility(View.VISIBLE);
                Toasty.info(Product_Catogery.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            limit = 0;
            new loadstatus().execute(new String[0]);
            return;
        }
        nointernet.setVisibility(View.VISIBLE);
        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
    }

    public void removeitem(int position) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void changeitem(int position, String pcatid1, String cattitle1, String catimgsig1, String catorder1) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
            Product_cat_FeedItem item = new Product_cat_FeedItem();
            item.setsn(pcatid1);
            item.setcatorder(catorder1);
            item.settitle(cattitle1);
            item.setimgsig(catimgsig1);
            feedItems.add(position, item);
        } catch (Exception e) {
        }
    }
}
