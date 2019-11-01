package com.dailybill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import adapter.ShopHeader_List_ListAdapter;
import data.ShoplistHeader_FeedItem;

public class NewBill extends AppCompatActivity {
    ConnectionDetecter cd;
    public DatabaseHandler db=new DatabaseHandler(this);
    public List<ShoplistHeader_FeedItem> feedItems;
    ImageView heart,nointernet,nodata;
    public ShopHeader_List_ListAdapter listAdapter;
    HeaderAndFooterRecyclerView recylerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bill);
        cd = new ConnectionDetecter(this);
        nodata=findViewById(R.id.nodata);
        heart = (ImageView) findViewById(R.id.heart);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        recylerview = (HeaderAndFooterRecyclerView)findViewById(R.id.recylerview);

        feedItems = new ArrayList();
        listAdapter = new ShopHeader_List_ListAdapter(this, feedItems);
        recylerview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recylerview.setAdapter(listAdapter);
        Glide.with(this).load(R.drawable.loading).into(heart);
        nointernet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (cd.isConnectingToInternet()) {
                    nointernet.setVisibility(View.GONE);
                    new loadstatus().execute(new String[0]);
                }
                else
                {
                    nointernet.setVisibility(View.VISIBLE);
                }

            }
        });
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            new loadstatus().execute(new String[0]);
        } else {
            nointernet.setVisibility(View.VISIBLE);
        }
    }
    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            recylerview.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getshopslist_foruser.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(db.getlatitude()+","+db.getlongtitude(), "UTF-8");
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
                Log.w("Reusltt",result);

                if (result.contains(":%ok")) {
                    try {
                        feedItems.clear();
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 3;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            ShoplistHeader_FeedItem item = new ShoplistHeader_FeedItem();
                            m=m+1;
                            item.setCatid(got[m]);
                            m=m+1;
                            item.setCatname(got[m]);
                            m=m+1;
                            item.setShops(got[m]);
                            feedItems.add(item);
                        }
                    } catch (Exception e) {
                    }
                    heart.setVisibility(View.GONE);
                    recylerview.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                recylerview.setVisibility(View.GONE);
                heart.setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }
}
