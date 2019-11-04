package com.appsbag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.graphics.Typeface;
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
import data.AppCatogery_FeedItem;
import data.Applist_Feed;

public class App_List extends AppCompatActivity {
    ConnectionDetecter cd;
    public AppList_Adapter adapter;
    HeaderAndFooterRecyclerView recyclerview;
    Typeface face;
    public List<Applist_Feed> feeditem;
    ImageView heart;
    public int limit = 0;
    ImageView nodata;
    ImageView nointernet;
    TextView text;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app__list);
        back=findViewById(R.id.back);
        heart = (ImageView) findViewById(R.id.heart);
        nodata = (ImageView) findViewById(R.id.nodata);
        cd = new ConnectionDetecter(this);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        text = (TextView) findViewById(R.id.text);
        recyclerview = findViewById(R.id.recyclerview);


        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        text.setTypeface(face);
        text.setText(Temp.catogeryname);
        feeditem = new ArrayList();
        adapter = new AppList_Adapter(this, feeditem);

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
                    return;
                }
                nointernet.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
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

                String link= Temp.weblink +"getapplistbycatogery.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.catogeryid, "UTF-8");
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
                    int k = (got.length - 1) / 6;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Applist_Feed item = new Applist_Feed();
                        m=m+1;
                        item.setSn(got[m]);
                        m=m+1;
                        item.setAppname(got[m]);
                        m=m+1;
                        item.setAppurl(got[m]);
                        m=m+1;
                        item.setDisctiltle(got[m]);
                        m=m+1;
                        item.setDiscfooter(got[m]);
                        m=m+1;
                        item.setImgsig(got[m]);
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
}