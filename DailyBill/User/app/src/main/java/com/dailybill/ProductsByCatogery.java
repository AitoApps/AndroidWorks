package com.dailybill;

import adapter.Productlist_byCat_ListAdapter;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import data.Productlist_ByCatogery_FeedItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ProductsByCatogery extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    RelativeLayout content;
    public DatabaseHandler db;
    Typeface face;
    Typeface face1;
    public List<Productlist_ByCatogery_FeedItem> feedItems;
    ImageView heart;
    public int limit = 0;
    public Productlist_byCat_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    public RecyclerView recylerview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_products_by_catogery);
        try {
            face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
            face1 = Typeface.createFromAsset(getAssets(), "proximanormal.ttf");
            db = new DatabaseHandler(this);
            cd = new ConnectionDetecter(this);
            pd = new ProgressDialog(this);
            nodata = (ImageView) findViewById(R.id.nodata);
            content = (RelativeLayout) findViewById(R.id.content);
            recylerview = (RecyclerView) findViewById(R.id.recylerview);
            heart = (ImageView) findViewById(R.id.heart);
            back = (ImageView) findViewById(R.id.back);
            nointernet = (ImageView) findViewById(R.id.nointernet);
            feedItems = new ArrayList();
            listAdapter = new Productlist_byCat_ListAdapter(this, feedItems);
            recylerview.setLayoutManager(new GridLayoutManager(this, 3));
            recylerview.setAdapter(listAdapter);
            back.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    onBackPressed();
                }
            });
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
            Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
            if (cd.isConnectingToInternet()) {
                nointernet.setVisibility(View.GONE);
                limit = 0;
                new loadstatus().execute(new String[0]);
            } else {
                nointernet.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception a) {
            Toast.makeText(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_SHORT).show();
        }
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            limit = 0;
            new loadstatus().execute(new String[0]);
            return;
        }
        nointernet.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
    }
    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            nodata.setVisibility(View.GONE);
            recylerview.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
            limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"get_items_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(db.gettownid()+":%"+Temp.catogerid, "UTF-8");
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
                    try {
                        feedItems.clear();
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 6;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Productlist_ByCatogery_FeedItem item = new Productlist_ByCatogery_FeedItem();
                            m=m+1;
                            item.setSn(got[m]);
                            m=m+1;
                            item.setItemname(got[m]);
                            m=m+1;
                            item.setItemkeywords(got[m]);
                            m=m+1;
                            item.setUnit(got[m]);
                            m=m+1;
                            item.setUnitprice(got[m]);
                            m = m+ 1;
                            item.setImgisg(got[m]);
                            feedItems.add(item);
                        }
                    } catch (Exception e) {
                    }
                    nodata.setVisibility(View.GONE);
                    heart.setVisibility(View.GONE);
                    recylerview.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                nodata.setVisibility(View.VISIBLE);
                recylerview.setVisibility(View.GONE);
                heart.setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }
}
