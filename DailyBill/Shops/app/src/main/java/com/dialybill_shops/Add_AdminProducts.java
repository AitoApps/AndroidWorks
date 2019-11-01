package com.dialybill_shops;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import adapter.Productlist_Admin_Adapter;
import data.Productlist_admin_FeedItem;
import es.dmoral.toasty.Toasty;

public class Add_AdminProducts extends AppCompatActivity {
    ConnectionDetecter cd;
    public DatabaseHandler db;
    Typeface face;
    Typeface face1;
    public List<Productlist_admin_FeedItem> feedItems;
    View footerView;
    ImageView heart;
    public SwipeRefreshLayout layout;
    public int limit = 0;
    public Productlist_Admin_Adapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    public HeaderAndFooterRecyclerView recylerview;
    TextView text;
    public UserDatabaseHandler udb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__admin_products);
        try {
            face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
            face1 = Typeface.createFromAsset(getAssets(), "proximanormal.ttf");
            db = new DatabaseHandler(this);
            cd = new ConnectionDetecter(this);
            udb = new UserDatabaseHandler(this);
            pd = new ProgressDialog(this);
            nodata = (ImageView) findViewById(R.id.nodata);
            text = (TextView) findViewById(R.id.text);
            recylerview = (HeaderAndFooterRecyclerView) findViewById(R.id.recylerview);
            heart = (ImageView) findViewById(R.id.heart);
            nointernet = (ImageView) findViewById(R.id.nointernet);
            layout = (SwipeRefreshLayout) findViewById(R.id.layout);
            layout.setEnabled(true);
            feedItems = new ArrayList();
            listAdapter = new Productlist_Admin_Adapter(this, feedItems);
            recylerview.setLayoutManager(new GridLayoutManager(this, 3));
            recylerview.setAdapter(listAdapter);
            footerView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.footerview, recylerview.getFooterContainer(), false);
            recylerview.addFooterView(footerView);
            footerView.setVisibility(View.INVISIBLE);
            text.setText("Admin Products");
            text.setTypeface(face);
            layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                public void onRefresh() {
                    layout.setRefreshing(true);
                    nointernet.setVisibility(View.GONE);
                    limit = 0;
                    new loadstatus2().execute(new String[0]);
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
                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            });
            Glide.with(this).load(R.drawable.loading).into(heart);
        } catch (Exception a) {
            Toasty.info(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_SHORT).show();
        }
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

    public void removeitem(int position) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void loadmore() {
        if (footerView.getVisibility() == View.VISIBLE) {
            return;
        }
        if (cd.isConnectingToInternet()) {
            new loadstatus1().execute(new String[0]);
        } else {
            Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
        }
    }

    public String getFormattedDate(long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);
        Calendar now = Calendar.getInstance();
        final String timeFormatString = "h:mm a";
        final String dateTimeFormatString = "MMM d h:mm a";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ) {
            return DateFormat.format(timeFormatString, smsTime)+"";
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1  ){
            return "Yesterday " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("MMM dd yyyy h:mm a", smsTime).toString();
        }

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
                String link=Temp.weblink+"getadminproductlistforshop.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+":%"+udb.get_shopcatogery(), "UTF-8");
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
                Log.w("Result",result);
                if (result.contains(":%ok")) {
                    try {
                        feedItems.clear();
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 4;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Productlist_admin_FeedItem item = new Productlist_admin_FeedItem();
                            m=m+1;
                            item.setSn(got[m]);
                            m=m+1;
                            item.setItemname(got[m]);
                            m=m+1;
                            item.setUnit(got[m]);
                            m=m+1;
                            item.setImgsig(got[m]);
                            feedItems.add(item);
                        }
                    } catch (Exception e2) {
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
            } catch (Exception e3) {
            }
        }
    }

    public class loadstatus1 extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            limit += 50;
            footerView.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getadminproductlistforshop.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+":%"+udb.get_shopcatogery(), "UTF-8");
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
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 4;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Productlist_admin_FeedItem item = new Productlist_admin_FeedItem();
                            m=m+1;
                            item.setSn(got[m]);
                            m=m+1;
                            item.setItemname(got[m]);
                            m=m+1;
                            item.setUnit(got[m]);
                            m=m+1;
                            item.setImgsig(got[m]);
                            feedItems.add(item);
                        }
                    } catch (Exception e2) {
                    }
                    footerView.setVisibility(View.INVISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                footerView.setVisibility(View.INVISIBLE);
                heart.setVisibility(View.GONE);
            } catch (Exception e3) {
            }
        }
    }

    public class loadstatus2 extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getadminproductlistforshop";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+":%"+udb.get_shopcatogery(), "UTF-8");
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
                layout.setRefreshing(false);
                if (result.contains(":%ok")) {
                    try {
                        feedItems.clear();
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 4;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Productlist_admin_FeedItem item = new Productlist_admin_FeedItem();
                            m=m+1;
                            item.setSn(got[m]);
                            m=m+1;
                            item.setItemname(got[m]);
                            m=m+1;
                            item.setUnit(got[m]);
                            m=m+1;
                            item.setImgsig(got[m]);
                            feedItems.add(item);
                        }
                    } catch (Exception e2) {
                    }
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                heart.setVisibility(View.GONE);
            } catch (Exception e3) {
            }
        }
    }
}

