package com.sanji_admin;

import adapter.Shoplist_ListAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import data.Shoplist_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Shop_management extends AppCompatActivity {
    ImageView addshop;
    ImageView back;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face;
    public List<Shoplist_FeedItem> feedItems;
    boolean flag = false;
    RelativeLayout footerview;
    ImageView heart;
    public int limit = 0;
    ListView list;
    public Shoplist_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_shop_management);
        heart = (ImageView) findViewById(R.id.heart);
        addshop = (ImageView) findViewById(R.id.addshop);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
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
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        text.setTypeface(face);
        Glide.with(this).load(R.drawable.loading).into(heart);
        feedItems = new ArrayList();
        listAdapter = new Shoplist_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        list.setOnScrollListener(new OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount == totalItemCount - firstVisibleItem && flag) {
                    flag = false;
                    if (!cd.isConnectingToInternet()) {
                        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    } else if (footerview.getVisibility() != 0) {
                        limit += 30;
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
        nointernet.setOnClickListener(new OnClickListener() {
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
        addshop.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.isshopedit = 0;
                startActivity(new Intent(getApplicationContext(), Add_Shops.class));
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
            Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
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

    public void changeitem(int position, String sn1, String shopname1, String ownername1, String mobile1, String mobile2, String place1, String latitude1, String longtitude1, String imgsig1, String status1, String shopdays, String delicharge, String delidisc, String trust, String delikm, String minorderamt, String website, String instagram, String facebook, String pinterest, String youtube) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
            Shoplist_FeedItem item = new Shoplist_FeedItem();
            item.setsn(sn1);
            item.setshopname(shopname1);
            item.setownername(ownername1);
            item.setmobile1(mobile1);
            item.setmobile2(mobile2);
            item.setplace(place1);
            item.setlatitude(latitude1);
            item.setlongtitude(longtitude1);
            item.setimgsig(imgsig1);
            item.setstatus(status1);
            item.setshopdays(shopdays);
            item.setdelicharge(delicharge);
            item.setdelidisc(delidisc);
            item.settrust(trust);
            item.setdelikm(delikm);
            item.setMinorderamt(minorderamt);
            item.setWebsite(website);
            item.setInstagram(instagram);
            item.setFacebook(facebook);
            item.setPinterest(pinterest);
            item.setYoutube(youtube);
            feedItems.add(position, item);
            listAdapter.notifyDataSetChanged();

        } catch (Exception e12) {

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
                String link=Temp.weblink+"getshoplist_admin.php";
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
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 21;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Shoplist_FeedItem item = new Shoplist_FeedItem();
                        m=m+1;
                        item.setsn(got[m]);
                        m=m+1;
                        item.setshopname(got[m]);
                        m=m+1;
                        item.setownername(got[m]);
                        m=m+1;
                        item.setmobile1(got[m]);
                        m=m+1;
                        item.setmobile2(got[m]);
                        m=m+1;
                        item.setplace(got[m]);
                        m=m+1;
                        item.setlatitude(got[m]);
                        m=m+1;
                        item.setlongtitude(got[m]);
                        m=m+1;
                        item.setimgsig(got[m]);
                        m=m+1;
                        item.setstatus(got[m]);
                        m=m+1;
                        item.setshopdays(got[m]);
                        m=m+1;
                        item.setdelicharge(got[m]);
                        m=m+1;
                        item.setdelidisc(got[m]);
                        m=m+1;
                        item.settrust(got[m]);
                        m=m+1;
                        item.setdelikm(got[m]);
                        m=m+1;
                        item.setMinorderamt(got[m]);
                        m=m+1;
                        item.setWebsite(got[m]);
                        m=m+1;
                        item.setInstagram(got[m]);
                        m=m+1;
                        item.setFacebook(got[m]);
                        m=m+1;
                        item.setPinterest(got[m]);
                        m=m+1;
                        item.setYoutube(got[m]);
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
            } catch (Exception a) {
                Toasty.info(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
    }

    public class loadstatus1 extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            footerview.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getshoplist_admin.php";
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
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 21;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Shoplist_FeedItem item = new Shoplist_FeedItem();
                        m=m+1;
                        item.setsn(got[m]);
                        m=m+1;
                        item.setshopname(got[m]);
                        m=m+1;
                        item.setownername(got[m]);
                        m=m+1;
                        item.setmobile1(got[m]);
                        m=m+1;
                        item.setmobile2(got[m]);
                        m=m+1;
                        item.setplace(got[m]);
                        m=m+1;
                        item.setlatitude(got[m]);
                        m=m+1;
                        item.setlongtitude(got[m]);
                        m=m+1;
                        item.setimgsig(got[m]);
                        m=m+1;
                        item.setstatus(got[m]);
                        m=m+1;
                        item.setshopdays(got[m]);
                        m=m+1;
                        item.setdelicharge(got[m]);
                        m=m+1;
                        item.setdelidisc(got[m]);
                        m=m+1;
                        item.settrust(got[m]);
                        m=m+1;
                        item.setdelikm(got[m]);
                        m=m+1;
                        item.setMinorderamt(got[m]);
                        m=m+1;
                        item.setWebsite(got[m]);
                        m=m+1;
                        item.setInstagram(got[m]);
                        m=m+1;
                        item.setFacebook(got[m]);
                        m=m+1;
                        item.setPinterest(got[m]);
                        m=m+1;
                        item.setYoutube(got[m]);
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
