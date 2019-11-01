package com.sanji_admin;

import adapter.Franchaisis_ListAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import data.Franchisis_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Franchiesis extends AppCompatActivity {
    ImageView addmarketter;
    ImageView back;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face;
    public List<Franchisis_FeedItem> feedItems;
    boolean flag = false;
    RelativeLayout footerview;
    ImageView heart;
    public int limit = 0;
    ListView list;
    public Franchaisis_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Franchiesis.nointernet.setVisibility(View.GONE);
            Franchiesis.nodata.setVisibility(View.GONE);
            Franchiesis.list.setVisibility(View.GONE);
            Franchiesis.heart.setVisibility(View.VISIBLE);
            Franchiesis.limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getfranchise_admin.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Franchiesis.limit);
                sb3.append("");
                sb2.append(URLEncoder.encode(sb3.toString(), "UTF-8"));
                String data2 = sb2.toString();
                URLConnection conn = new URL(link).openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb4 = new StringBuilder();
                while (true) {
                    String readLine = reader.readLine();
                    String line = readLine;
                    if (readLine == null) {
                        return sb4.toString();
                    }
                    sb4.append(line);
                }
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            try {
                Log.w("resukt", result);
                if (result.contains(":%ok")) {
                    Franchiesis.feedItems.clear();
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 6;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Franchisis_FeedItem item = new Franchisis_FeedItem();
                        int m2 = m + 1;
                        item.setSn(got[m2]);
                        int m3 = m2 + 1;
                        item.setName(got[m3]);
                        int m4 = m3 + 1;
                        item.setArea(got[m4]);
                        int m5 = m4 + 1;
                        item.setMobile(got[m5]);
                        int m6 = m5 + 1;
                        item.setAddress(got[m6]);
                        m = m6 + 1;
                        item.setAgreid(got[m]);
                        Franchiesis.feedItems.add(item);
                    }
                    Franchiesis.heart.setVisibility(View.GONE);
                    Franchiesis.list.setVisibility(View.VISIBLE);
                    Franchiesis.listAdapter.notifyDataSetChanged();
                    return;
                }
                Franchiesis.nodata.setVisibility(View.VISIBLE);
                Franchiesis.heart.setVisibility(View.GONE);
                Franchiesis.footerview.setVisibility(View.GONE);
            } catch (Exception a) {
                Log.w("MUHIS", Log.getStackTraceString(a));
            }
        }
    }

    public class loadstatus1 extends AsyncTask<String, Void, String> {
        public loadstatus1() {
        }
        public void onPreExecute() {
            Franchiesis.footerview.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getfranchise_admin.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Franchiesis.limit);
                sb3.append("");
                sb2.append(URLEncoder.encode(sb3.toString(), "UTF-8"));
                String data2 = sb2.toString();
                URLConnection conn = new URL(link).openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb4 = new StringBuilder();
                while (true) {
                    String readLine = reader.readLine();
                    String line = readLine;
                    if (readLine == null) {
                        return sb4.toString();
                    }
                    sb4.append(line);
                }
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            try {
                if (result.contains(":%ok")) {
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 6;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Franchisis_FeedItem item = new Franchisis_FeedItem();
                        int m2 = m + 1;
                        item.setSn(got[m2]);
                        int m3 = m2 + 1;
                        item.setName(got[m3]);
                        int m4 = m3 + 1;
                        item.setArea(got[m4]);
                        int m5 = m4 + 1;
                        item.setMobile(got[m5]);
                        int m6 = m5 + 1;
                        item.setAddress(got[m6]);
                        m = m6 + 1;
                        item.setAgreid(got[m]);
                        Franchiesis.feedItems.add(item);
                    }
                    Franchiesis.listAdapter.notifyDataSetChanged();
                    Franchiesis.footerview.setVisibility(View.GONE);
                    return;
                }
                Franchiesis.heart.setVisibility(View.GONE);
                Franchiesis.footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_franchiesis);
        heart = (ImageView) findViewById(R.id.heart);
        addmarketter = (ImageView) findViewById(R.id.addmarketter);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Franchiesis.onBackPressed();
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
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        feedItems = new ArrayList();
        listAdapter = new Franchaisis_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        list.setOnScrollListener(new OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount == totalItemCount - firstVisibleItem && Franchiesis.flag) {
                    Franchiesis.flag = false;
                    if (!Franchiesis.cd.isConnectingToInternet()) {
                        Toasty.info(Franchiesis.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    } else if (Franchiesis.footerview.getVisibility() != 0) {
                        Franchiesis.limit += 30;
                        new loadstatus1().execute(new String[0]);
                    }
                }
            }

            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                if (arg1 == 2) {
                    Franchiesis.flag = true;
                }
            }
        });
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (Franchiesis.cd.isConnectingToInternet()) {
                    Franchiesis.nointernet.setVisibility(View.GONE);
                    Franchiesis.limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                Franchiesis.nointernet.setVisibility(View.VISIBLE);
                Toasty.info(Franchiesis.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        addmarketter.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.frachedit = 0;
                Franchiesis.startActivity(new Intent(Franchiesis.getApplicationContext(), Add_Franchiesis.class));
            }
        });
    }
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
}
