package com.sanji_shops;

import adapter.OrderGroup__ListAdapter;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;
import data.OrderGroup_Feeditem;
import es.dmoral.toasty.Toasty;
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

public class Order_Group extends AppCompatActivity {
    ConnectionDetecter cd;
    public DatabaseHandler db;
    Typeface face;
    Typeface face1;
    public List<OrderGroup_Feeditem> feedItems;
    View footerView;
    ImageView heart;
    public SwipeRefreshLayout layout;
    public int limit = 0;
    public OrderGroup__ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    public HeaderAndFooterRecyclerView recylerview;
    TextView text;
    public UserDatabaseHandler udb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_order_group);
        try {
            face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
            face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
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
            listAdapter = new OrderGroup__ListAdapter(this, feedItems);
            recylerview.setLayoutManager(new GridLayoutManager(this, 1));
            recylerview.setAdapter(listAdapter);
            footerView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.footerview, recylerview.getFooterContainer(), false);
            recylerview.addFooterView(footerView);
            footerView.setVisibility(View.INVISIBLE);
            text.setText("Order Confirmation");
            text.setTypeface(face);
            layout.setOnRefreshListener(new OnRefreshListener() {
                public void onRefresh() {
                    layout.setRefreshing(true);
                    nointernet.setVisibility(View.GONE);
                    limit = 0;
                    new loadstatus2().execute(new String[0]);
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
        if (footerView.getVisibility() == 0) {
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
                String link=Temp.weblink+"getordergroup_shop.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+":%"+udb.get_shopid(), "UTF-8");
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
                        int k = (got.length - 1) / 4;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            OrderGroup_Feeditem item = new OrderGroup_Feeditem();
                            m=m+1;
                            item.setgroupid(got[m]);
                            m=m+1;
                            item.settotal(got[m]);
                            m=m+1;
                            try {
                                Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                c1.setTime(sdf.parse(got[m]));
                                item.setorderdate(getFormattedDate(c1.getTimeInMillis()));
                            } catch (Exception e) {
                                item.setorderdate(got[m]);
                            }
                            m=m+1;
                            item.setdelicharge(got[m]);
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
            limit += 30;
            footerView.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getordergroup_shop.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+":%"+udb.get_shopid(), "UTF-8");
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
                            OrderGroup_Feeditem item = new OrderGroup_Feeditem();
                            m=m+1;
                            item.setgroupid(got[m]);
                            m=m+1;
                            item.settotal(got[m]);
                            m=m+1;
                            try {
                                Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                c1.setTime(sdf.parse(got[m]));
                                item.setorderdate(getFormattedDate(c1.getTimeInMillis()));
                            } catch (Exception e) {
                                item.setorderdate(got[m]);
                            }
                            m=m+1;
                            item.setdelicharge(got[m]);
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
                String link=Temp.weblink+"getordergroup_shop.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+":%"+udb.get_shopid(), "UTF-8");
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
                            OrderGroup_Feeditem item = new OrderGroup_Feeditem();
                            m=m+1;
                            item.setgroupid(got[m]);
                            m=m+1;
                            item.settotal(got[m]);
                            m=m+1;
                            try {
                                Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                c1.setTime(sdf.parse(got[m]));
                                item.setorderdate(getFormattedDate(c1.getTimeInMillis()));
                            } catch (Exception e) {
                                item.setorderdate(got[m]);
                            }
                            m=m+1;
                            item.setdelicharge(got[m]);
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
