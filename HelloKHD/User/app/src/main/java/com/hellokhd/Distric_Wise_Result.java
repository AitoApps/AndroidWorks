package com.hellokhd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import adapter.DistricSearch_Adapter;
import adapter.SchoolSearch_Adapter;
import data.DistricSearch_FeedItem;
import data.SchoolSearch_FeedItem;

public class Distric_Wise_Result extends AppCompatActivity {
    ConnectionDetecter cd;
    RecyclerView searchrecyclerview;
    ImageView nointernet,heart,nodata,back;
    TextView text;
    public DistricSearch_Adapter adapter;
    public List<DistricSearch_FeedItem> feeditem;
    Typeface face;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distric__wise__result);
        cd=new ConnectionDetecter(this);
        searchrecyclerview=findViewById(R.id.searchrecyclerview);
        nointernet=findViewById(R.id.nointernet);
        heart=findViewById(R.id.heart);
        nodata=findViewById(R.id.nodata);
        back=findViewById(R.id.back);
        text=findViewById(R.id.text);
        face= Typeface.createFromAsset(getAssets(), "proxibold.otf");
        feeditem = new ArrayList();
        adapter = new DistricSearch_Adapter(this, feeditem);
        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(heart);

        searchrecyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        searchrecyclerview.setAdapter(adapter);

        text.setTypeface(face);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        new loadsearch().execute(new String[0]);

    }

    public class loadsearch extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            searchrecyclerview.setVisibility(View.INVISIBLE);
            heart.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getdistricwiseresult_full.php";
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
        public void onPostExecute(final String result) {
            try {
                if (result.contains(":%ok")) {
                    try {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                String[] got = result.split(":%");
                                int k = (got.length - 1) /6;
                                int m = -1;
                                feeditem.clear();
                                for (int i = 1; i <= k; i++) {
                                    DistricSearch_FeedItem item2 = new DistricSearch_FeedItem();
                                    m=m+1;
                                    item2.setSn(got[m]);
                                    m=m+1;
                                    item2.setDistricname(got[m]);
                                    m=m+1;
                                    item2.setHsgeneral(got[m]);
                                    m=m+1;
                                    item2.setHssgeneral(got[m]);
                                    m=m+1;
                                    item2.setHsarabic(got[m]);
                                    m=m+1;
                                    item2.setHssanskrit(got[m]);
                                    feeditem.add(item2);
                                }
                            }
                        });
                        nodata.setVisibility(View.GONE);
                        heart.setVisibility(View.GONE);
                        searchrecyclerview.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                    }
                } else {
                    nodata.setVisibility(View.VISIBLE);
                    heart.setVisibility(View.GONE);
                }
            } catch (Exception e2) {
            }
        }
    }
}

