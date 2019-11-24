package com.hellokhd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
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

import adapter.ShopsList_Adapter;
import adapter.VideoList_Adapter;
import data.NewsList_FeedItem;
import data.ShopsList_FeedItem;
import data.Videolist_FeedItem;
import es.dmoral.toasty.Toasty;

public class Video_List extends AppCompatActivity {
    ImageView back,nointernet,heart,nodata;
    HeaderAndFooterRecyclerView recyclerview;
    public VideoList_Adapter adapter;
    public List<Videolist_FeedItem> feeditem;
    Typeface face;
    TextView text;
    View footerView;
    int limit=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video__list);
        back=findViewById(R.id.back);
        nointernet=findViewById(R.id.nointernet);
        heart=findViewById(R.id.heart);
        nodata=findViewById(R.id.nodata);
        recyclerview=findViewById(R.id.recyclerview);
        face=Typeface.createFromAsset(getAssets(), "proxibold.otf");
        feeditem = new ArrayList();
        adapter = new VideoList_Adapter(this, feeditem);
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
        new loadstages().execute();
    }


    public class loadstages extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            recyclerview.setVisibility(View.INVISIBLE);
            heart.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getvideolist_user.php";
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
                                    Videolist_FeedItem item = new Videolist_FeedItem();
                                    m=m+1;
                                    item.setSn(got[m]);
                                    m=m+1;
                                    item.setTitle(got[m]);
                                    m=m+1;
                                    item.setVideoid(got[m]);
                                    m=m+1;
                                    item.setDuration(got[m]);
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

    public void watchYoutubeVideo(String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
           startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
           startActivity(webIntent);
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
                String link=Temp.weblink+"getvideolist_user.php";
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
                                    Videolist_FeedItem item = new Videolist_FeedItem();
                                    m=m+1;
                                    item.setSn(got[m]);
                                    m=m+1;
                                    item.setTitle(got[m]);
                                    m=m+1;
                                    item.setVideoid(got[m]);
                                    m=m+1;
                                    item.setDuration(got[m]);
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

