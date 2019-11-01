package com.fishappadmin;

import adapter.Fishlist_ListAdapter;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import data.Fishlist_FeedItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FishList extends AppCompatActivity {
    ImageView addproduct;
    ImageView back;
    ConnectionDetecter cd;
    Typeface face;
    public List<Fishlist_FeedItem> feedItems;
    boolean flag = false;
    RelativeLayout footerview;
    ImageView heart;
    public int limit = 0;
    ListView list;
    public Fishlist_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    TextView text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_fish_list);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        addproduct = (ImageView) findViewById(R.id.addproduct);
        back = (ImageView) findViewById(R.id.back);
        text = (TextView) findViewById(R.id.text);
        addproduct.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.fishedit = 0;
                startActivity(new Intent(getApplicationContext(), Add_Fish.class));
            }
        });
        text.setTypeface(face);
        text.setText(Temp.areaname);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        heart = (ImageView) findViewById(R.id.heart);
        cd = new ConnectionDetecter(this);
        list = (ListView) findViewById(R.id.list);
        nodata = (ImageView) findViewById(R.id.nodata);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        footerview = (RelativeLayout) getLayoutInflater().inflate(R.layout.footerview, null);
        list.addFooterView(footerview);
        footerview.setVisibility(View.GONE);
        RequestManager with = Glide.with((FragmentActivity) this);
        Integer valueOf = Integer.valueOf(R.drawable.loading);
        with.load(valueOf).into(heart);
        feedItems = new ArrayList();
        listAdapter = new Fishlist_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        list.setOnScrollListener(new OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount == totalItemCount - firstVisibleItem && flag) {
                    if (cd.isConnectingToInternet()) {
                        Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    } else if (footerview.getVisibility() != View.VISIBLE) {
                        limit += 50;
                        new loadstatus1().execute(new String[0]);
                    }
                }
            }

            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                if (arg1 == 2) {
                    flag = true;
                }
            }
        });
        Glide.with((FragmentActivity) this).load(valueOf).into(heart);
        nointernet.setOnClickListener(new OnClickListener() {
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
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (cd.isConnectingToInternet()) {
                nointernet.setVisibility(View.GONE);
                limit = 0;
                new loadstatus().execute(new String[0]);
                return;
            }
            nointernet.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
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

    public void changeitem(int position, String fishsn1, String fishname1, String qty1, String unit1, String price1, String stock1, String imgsig1, String discription) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
            Fishlist_FeedItem item = new Fishlist_FeedItem();
            item.setSn(fishsn1);
            item.setFishname(fishname1);
            item.setQty(qty1);
            item.setUnit(unit1);
            item.setPrice(price1);
            item.setStock(stock1);
            item.setImgsig(imgsig1);
            item.setDiscription(discription);
            feedItems.add(position, item);
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

                String link= Temp.weblink +"getfishlist.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+":%"+Temp.areaid+":%"+Temp.clientcatsn, "UTF-8");
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
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 8;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Fishlist_FeedItem item = new Fishlist_FeedItem();
                        m=m+1;
                        item.setSn(got[m].trim());
                        m=m+1;
                        item.setFishname(got[m]);
                        m=m+1;
                        item.setQty(got[m]);
                        m=m+1;
                        item.setUnit(got[m]);
                        m=m+1;
                        item.setPrice(got[m]);
                        m=m+1;
                        item.setStock(got[m]);
                        m=m+1;
                        item.setImgsig(got[m]);
                        m=m+1;
                        item.setDiscription(got[m]);
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

    public class loadstatus1 extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            footerview.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... arg0) {
            try {

                String link= Temp.weblink +"getfishlist.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+":%"+Temp.areaid+":%"+Temp.clientcatsn, "UTF-8");
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
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 8;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Fishlist_FeedItem item = new Fishlist_FeedItem();
                        m=m+1;
                        item.setSn(got[m].trim());
                        m=m+1;
                        item.setFishname(got[m]);
                        m=m+1;
                        item.setQty(got[m]);
                        m=m+1;
                        item.setUnit(got[m]);
                        m=m+1;
                        item.setPrice(got[m]);
                        m=m+1;
                        item.setStock(got[m]);
                        m=m+1;
                        item.setImgsig(got[m]);
                        m=m+1;
                        item.setDiscription(got[m]);
                        feedItems.add(item);
                    }
                    listAdapter.notifyDataSetChanged();
                    footerview.setVisibility(View.GONE);
                    return;
                }
                heart.setVisibility(View.GONE);
                footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
}
