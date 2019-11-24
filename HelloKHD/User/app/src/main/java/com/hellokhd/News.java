package com.hellokhd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.graphics.Typeface;
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

import adapter.NewsList_Adapter;
import adapter.VideoList_Adapter;
import data.NewsList_FeedItem;
import data.Videolist_FeedItem;

public class News extends AppCompatActivity {
    ImageView back,nointernet,heart,nodata;
    HeaderAndFooterRecyclerView recyclerview;
    public NewsList_Adapter adapter;
    public List<NewsList_FeedItem> feeditem;
    int limit=0;
    View footerView;
    Typeface face;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        back=findViewById(R.id.back);
        nointernet=findViewById(R.id.nointernet);
        heart=findViewById(R.id.heart);
        nodata=findViewById(R.id.nodata);
        recyclerview=findViewById(R.id.recyclerview);
        face=Typeface.createFromAsset(getAssets(), "proxibold.otf");
        feeditem = new ArrayList();
        adapter = new NewsList_Adapter(this, feeditem);
        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerview.setAdapter(adapter);
        text=findViewById(R.id.text);
        text.setTypeface(face);
        footerView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.footerview, recyclerview.getFooterContainer(), false);
        ((TextView) footerView.findViewById(R.id.next)).setTypeface(face);
        recyclerview.addFooterView(footerView);
        footerView.setVisibility(View.INVISIBLE);

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
                String link=Temp.weblink+"getnewslist_user.php";
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
                                int k = (got.length - 1) /4;
                                int m = -1;
                                feeditem.clear();
                                for (int i = 1; i <= k; i++) {
                                    NewsList_FeedItem item = new NewsList_FeedItem();
                                    m=m+1;
                                    item.setSn(got[m]);
                                    m=m+1;
                                    item.setHeading(got[m]);
                                    m=m+1;
                                    item.setNews(got[m]);
                                    m=m+1;
                                    item.setImgsig(got[m]);
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
                String link=Temp.weblink+"getnewslist_user.php";
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
                                int k = (got.length - 1) /4;
                                int m = -1;
                                for (int i = 1; i <= k; i++) {
                                    NewsList_FeedItem item = new NewsList_FeedItem();
                                    m=m+1;
                                    item.setSn(got[m]);
                                    m=m+1;
                                    item.setHeading(got[m]);
                                    m=m+1;
                                    item.setNews(got[m]);
                                    m=m+1;
                                    item.setImgsig(got[m]);
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