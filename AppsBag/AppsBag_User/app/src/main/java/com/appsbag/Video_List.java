package com.appsbag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import adapter.AppList_Adapter;
import adapter.VideoList_Adapter;
import data.Applist_Feed;
import data.VideoList_Feed;

public class Video_List extends AppCompatActivity {

    ConnectionDetecter cd;
    public VideoList_Adapter adapter;
    HeaderAndFooterRecyclerView recyclerview;
    Typeface face;
    public List<VideoList_Feed> feeditem;
    ImageView heart;
    public int limit = 0;
    ImageView nodata;
    ImageView nointernet;
    TextView text;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video__list);
        heart = (ImageView) findViewById(R.id.heart);
        nodata = (ImageView) findViewById(R.id.nodata);
        cd = new ConnectionDetecter(this);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        text = (TextView) findViewById(R.id.text);
        recyclerview = findViewById(R.id.recyclerview);
        back=findViewById(R.id.back);


        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        text.setTypeface(face);
        feeditem = new ArrayList();
        adapter = new VideoList_Adapter(this, feeditem);

        recyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerview.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nointernet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (cd.isConnectingToInternet()) {
                    nointernet.setVisibility(View.GONE);
                    limit = 0;
                    new loadstatus().execute(new String[0]);
                }
                else
                {
                    nointernet.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }

            }
        });
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            limit = 0;
            new loadstatus().execute(new String[0]);
        }
        else
        {
            nointernet.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
        }


    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            heart.setVisibility(View.VISIBLE);
            limit = 0;
        }
        public String doInBackground(String... arg0) {

            try {

                String link= Temp.weblink +"getvideolist_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode("", "UTF-8");
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
                if (result.contains("%%ok")) {
                    String[] got = result.trim().split("%%");
                    int k = (got.length - 1) / 4;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        VideoList_Feed item = new VideoList_Feed();
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
                    heart.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    return;
                }
                heart.setVisibility(View.GONE);
            } catch (Exception e) {
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

}
