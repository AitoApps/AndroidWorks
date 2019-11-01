package com.sanji_shops;

import adapter.Product_List_Price_UpdateListAdapter;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import data.Productlist_priceUpdate_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Price_Update extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    Typeface face;
    public List<Productlist_priceUpdate_FeedItem> feedItems;
    ImageView heart;
    public int limit = 0;
    public Product_List_Price_UpdateListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    RecyclerView recylerview;
    EditText search;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Price_Update.nointernet.setVisibility(View.GONE);
            Price_Update.nodata.setVisibility(View.GONE);
            Price_Update.recylerview.setVisibility(View.GONE);
            Price_Update.heart.setVisibility(View.VISIBLE);
            Price_Update.limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getproductlist_forpriceup_shops.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                sb2.append(URLEncoder.encode(Price_Update.udb.get_shopid(), "UTF-8"));
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
                Log.w("Resulss", result);
                if (result.contains(":%ok")) {
                    Price_Update.feedItems.clear();
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 5;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Productlist_priceUpdate_FeedItem item = new Productlist_priceUpdate_FeedItem();
                        int m2 = m + 1;
                        item.setSn(got[m2]);
                        int m3 = m2 + 1;
                        item.setItemname(got[m3]);
                        int m4 = m3 + 1;
                        item.setOfferprice(got[m4]);
                        int m5 = m4 + 1;
                        item.setOrginalprice(got[m5]);
                        m = m5 + 1;
                        item.setImgsig1(got[m]);
                        Price_Update.feedItems.add(item);
                    }
                    Price_Update.heart.setVisibility(View.GONE);
                    Price_Update.recylerview.setVisibility(View.VISIBLE);
                    Price_Update.listAdapter.notifyDataSetChanged();
                    return;
                }
                Price_Update.nodata.setVisibility(View.VISIBLE);
                Price_Update.heart.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_price__update);
        heart = (ImageView) findViewById(R.id.heart);
        search = (EditText) findViewById(R.id.search);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Price_Update.onBackPressed();
            }
        });
        recylerview = (RecyclerView) findViewById(R.id.recylerview);
        nodata = (ImageView) findViewById(R.id.nodata);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        text = (TextView) findViewById(R.id.text);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        text.setText("Product List");
        text.setTypeface(face);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        search.setTypeface(face);
        feedItems = new ArrayList();
        listAdapter = new Product_List_Price_UpdateListAdapter(this, feedItems);
        recylerview.setLayoutManager(new GridLayoutManager(this, 3));
        recylerview.setAdapter(listAdapter);
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (Price_Update.cd.isConnectingToInternet()) {
                    Price_Update.nointernet.setVisibility(View.GONE);
                    Price_Update.limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                Price_Update.nointernet.setVisibility(View.VISIBLE);
                Toasty.info(Price_Update.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                Price_Update.filter(editable.toString());
            }
        });
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            limit = 0;
            new loadstatus().execute(new String[0]);
            return;
        }
        nointernet.setVisibility(View.VISIBLE);
        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
    }

    public void changeitem(int position, String sn1, String itemname1, String offerprice1, String orginalprice1, String imgsig11) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
            Productlist_priceUpdate_FeedItem item = new Productlist_priceUpdate_FeedItem();
            item.setSn(sn1);
            item.setItemname(itemname1);
            item.setOfferprice(offerprice1);
            item.setOrginalprice(orginalprice1);
            item.setImgsig1(imgsig11);
            feedItems.add(position, item);
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
    public void filter(String text2) {
        List<Productlist_priceUpdate_FeedItem> temp = new ArrayList<>();
        for (Productlist_priceUpdate_FeedItem d : feedItems) {
            if (text2.equalsIgnoreCase("")) {
                temp.add(d);
            } else if (d.getItemname().toLowerCase().contains(text2.toLowerCase())) {
                temp.add(d);
            }
        }
        listAdapter.updateList(temp);
    }
}
