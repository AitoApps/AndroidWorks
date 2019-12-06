package com.fortune_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import adapter.LuckyDrawBatches_ListAdapter;
import adapter.LuckyDrawPrizes_ListAdapter;
import data.LuckyDrawBatches_FeedItem;
import data.LuckyDrawPrizes_FeedItem;
import es.dmoral.toasty.Toasty;

public class LuckyDrawPrice extends AppCompatActivity {
    ImageView addnew;
    ImageView back;
    ConnectionDetecter cd;
    Typeface face;
    public List<LuckyDrawPrizes_FeedItem> feedItems;
    boolean flag = false;
    RelativeLayout footerview;
    ImageView heart;
    public int limit = 0;
    ListView list;
    public LuckyDrawPrizes_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_draw_prize);
        heart = (ImageView) findViewById(R.id.heart);
        addnew = (ImageView) findViewById(R.id.addnew);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        list = (ListView) findViewById(R.id.list);
        nodata = (ImageView) findViewById(R.id.nodata);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        footerview = (RelativeLayout) getLayoutInflater().inflate(R.layout.footerview, null);
        list.addFooterView(footerview);
        footerview.setVisibility(View.GONE);
        text = (TextView) findViewById(R.id.text);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        text.setText("LuckyDraw Prizes");
        text.setTypeface(face);
        Glide.with(this).load(R.drawable.loading).into(heart);
        feedItems = new ArrayList();
        listAdapter = new LuckyDrawPrizes_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);

        nointernet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (cd.isConnectingToInternet()) {
                    nointernet.setVisibility(View.GONE);
                    limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                nointernet.setVisibility(View.VISIBLE);
                Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        addnew.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Temp.luckydrawtypeedit=0;
                startActivity(new Intent(getApplicationContext(),Add_Lucky_Draw_Types.class));

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            limit = 0;
            new loadstatus().execute(new String[0]);
            return;
        }
        nointernet.setVisibility(View.VISIBLE);
        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void removeitem(int position) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }


    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            nointernet.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
            limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getluckdraw_prizelist.php";
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
        public void onPostExecute(String result) {
            try {
                if (result.contains(":%ok")) {
                    feedItems.clear();
                    String[] got = result.trim().split(":%");
                    int k = (got.length - 1) / 3;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        LuckyDrawPrizes_FeedItem item = new LuckyDrawPrizes_FeedItem();
                        m=m+1;
                        item.setSn(got[m]);
                        m=m+1;
                        item.setWeeks(got[m]);
                        m=m+1;
                        item.setImgsig(got[m]);
                        feedItems.add(item);
                    }
                    heart.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                nodata.setVisibility(View.VISIBLE);
                heart.setVisibility(View.GONE);
                footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
}