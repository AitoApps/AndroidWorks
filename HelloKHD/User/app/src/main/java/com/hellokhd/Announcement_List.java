package com.hellokhd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

import adapter.Anouncements_Adapter;
import adapter.VideoList_Adapter;
import data.Anouncement_FeedItem;
import data.NewsList_FeedItem;
import data.Videolist_FeedItem;

public class Announcement_List extends AppCompatActivity {
    ImageView back, nointernet, heart, nodata;
    HeaderAndFooterRecyclerView recyclerview;
    public Anouncements_Adapter adapter;
    public List<Anouncement_FeedItem> feeditem;
    int limit=0;
    View footerView;
    Typeface face;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement__list);
        back = findViewById(R.id.back);
        nointernet = findViewById(R.id.nointernet);
        heart = findViewById(R.id.heart);
        nodata = findViewById(R.id.nodata);
        recyclerview = findViewById(R.id.recyclerview);
        text=findViewById(R.id.text);
        feeditem = new ArrayList();
        adapter = new Anouncements_Adapter(this, feeditem);
        face= Typeface.createFromAsset(getAssets(), "proxibold.otf");
        recyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerview.setAdapter(adapter);

        footerView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.footerview, recyclerview.getFooterContainer(), false);
        ((TextView) footerView.findViewById(R.id.next)).setTypeface(face);
        recyclerview.addFooterView(footerView);
        footerView.setVisibility(View.INVISIBLE);
        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        text.setText("Anouncements");
        text.setTypeface(face);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        limit=0;
        new loadstages().execute();
    }


    public class loadstages extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            recyclerview.setVisibility(View.INVISIBLE);
            heart.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... arg0) {
            try {
                String link = Temp.weblink + "getanouncementlist.php";
                String data = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+"", "UTF-8");
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
                while ((line = reader.readLine()) != null) {
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
                                int k = (got.length - 1) /2;
                                int m = -1;
                                for (int i = 1; i <= k; i++) {
                                    Anouncement_FeedItem item = new Anouncement_FeedItem();
                                    m = m + 1;
                                    item.setSn(got[m]);
                                    m = m + 1;
                                    item.setAnouncment(got[m]);
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

    public void loadmore()
    {
        new loadstages1().execute();
    }
    public class loadstages1 extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            footerView.setVisibility(View.VISIBLE);
            limit=limit+30;
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getanouncementlist.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+"", "UTF-8");
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
                                int k = (got.length - 1) /2;
                                int m = -1;
                                for (int i = 1; i <= k; i++) {
                                    Anouncement_FeedItem item = new Anouncement_FeedItem();
                                    m = m + 1;
                                    item.setSn(got[m]);
                                    m = m + 1;
                                    item.setAnouncment(got[m]);
                                    feeditem.add(item);
                                }
                            }
                        });
                        footerView.setVisibility(View.INVISIBLE);
                        heart.setVisibility(View.GONE);
                        recyclerview.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                    }
                } else {
                    footerView.setVisibility(View.INVISIBLE);
                    heart.setVisibility(View.GONE);
                }
            } catch (Exception e2) {
            }
        }
    }

}
