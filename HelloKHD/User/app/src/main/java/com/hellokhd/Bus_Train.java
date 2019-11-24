package com.hellokhd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

import adapter.AmbuTrasn_Adapter;
import adapter.BusTrain_Adapter;
import data.AmbuTrans_FeedItem;
import data.BusTrain_FeedItem;

public class Bus_Train extends AppCompatActivity {
    ImageView back,nointernet,heart,nodata;
    HeaderAndFooterRecyclerView recyclerview;
    public BusTrain_Adapter adapter;
    public List<BusTrain_FeedItem> feeditem;
    TextView text;
    Typeface face;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus__train);
        back=findViewById(R.id.back);
        nointernet=findViewById(R.id.nointernet);
        heart=findViewById(R.id.heart);
        nodata=findViewById(R.id.nodata);
        recyclerview=findViewById(R.id.recyclerview);
        face= Typeface.createFromAsset(getAssets(), "proxibold.otf");
        feeditem = new ArrayList();
        adapter = new BusTrain_Adapter(this, feeditem);
        text=findViewById(R.id.text);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerview.setAdapter(adapter);
        text.setTypeface(face);
        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        text.setText(Temp.busortraintext);
        new loadstages().execute();
    }

    public class loadstages extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            recyclerview.setVisibility(View.INVISIBLE);
            heart.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getbuslist_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.busortraintype+"", "UTF-8");
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
        public void onPostExecute(final String result) {
            try {
                if (result.contains(":%ok")) {
                    try {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                String[] got = result.split(":%");
                                int k = (got.length - 1) /6;
                                int m = -1;
                                for (int i = 1; i <= k; i++) {
                                    BusTrain_FeedItem item = new BusTrain_FeedItem();
                                    m=m+1;
                                    item.setSn(got[m]);
                                    m=m+1;
                                    item.setBusname(got[m]);
                                    m=m+1;
                                    item.setStation(got[m]);
                                    m=m+1;
                                    item.setFromstation(got[m]);
                                    m=m+1;
                                    item.setTostation(got[m]);
                                    m=m+1;
                                    item.setTimes(got[m]);
                                    feeditem.add(item);
                                }
                            }
                        });
                        heart.setVisibility(View.GONE);
                        recyclerview.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                    }
                } else {
                    heart.setVisibility(View.GONE);
                }
            } catch (Exception e2) {
            }
        }
    }
}

