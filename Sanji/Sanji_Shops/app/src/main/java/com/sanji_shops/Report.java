package com.sanji_shops;

import adapter.Report_ListAdapter;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import data.Report_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Report extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    Typeface face;
    public List<Report_FeedItem> feedItems;
    boolean flag = false;
    RelativeLayout footerview;
    ImageView heart;
    public int limit = 0;
    ListView list;
    public Report_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Report.nointernet.setVisibility(View.GONE);
            Report.nodata.setVisibility(View.GONE);
            Report.list.setVisibility(View.GONE);
            Report.heart.setVisibility(View.VISIBLE);
            Report.limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getreport_shops.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                sb2.append(URLEncoder.encode(Report.udb.get_shopid(), "UTF-8"));
                String data2 = sb2.toString();
                URLConnection conn = new URL(link).openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb3 = new StringBuilder();
                while (true) {
                    String readLine = reader.readLine();
                    String line = readLine;
                    if (readLine == null) {
                        return sb3.toString();
                    }
                    sb3.append(line);
                }
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            try {
                if (result.contains(":%ok")) {
                    Report.feedItems.clear();
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 6;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Report_FeedItem item = new Report_FeedItem();
                        int m2 = m + 1;
                        item.setvcount_sales(got[m2]);
                        int m3 = m2 + 1;
                        item.setvcount_pedning(got[m3]);
                        int m4 = m3 + 1;
                        item.setvcount_cancelled(got[m4]);
                        int m5 = m4 + 1;
                        item.setitemid(got[m5]);
                        int m6 = m5 + 1;
                        item.setitemname(got[m6]);
                        m = m6 + 1;
                        item.setimgsig(got[m]);
                        Report.feedItems.add(item);
                    }
                    Report.heart.setVisibility(View.GONE);
                    Report.list.setVisibility(View.VISIBLE);
                    Report.listAdapter.notifyDataSetChanged();
                    return;
                }
                Report.nodata.setVisibility(View.VISIBLE);
                Report.heart.setVisibility(View.GONE);
                Report.footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }

    public class loadstatus1 extends AsyncTask<String, Void, String> {
        public loadstatus1() {
        }
        public void onPreExecute() {
            Report.footerview.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getreport_shops.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                sb2.append(URLEncoder.encode(Report.udb.get_shopid(), "UTF-8"));
                String data2 = sb2.toString();
                URLConnection conn = new URL(link).openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb3 = new StringBuilder();
                while (true) {
                    String readLine = reader.readLine();
                    String line = readLine;
                    if (readLine == null) {
                        return sb3.toString();
                    }
                    sb3.append(line);
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
                        Report_FeedItem item = new Report_FeedItem();
                        int m2 = m + 1;
                        item.setvcount_sales(got[m2]);
                        int m3 = m2 + 1;
                        item.setvcount_pedning(got[m3]);
                        int m4 = m3 + 1;
                        item.setvcount_cancelled(got[m4]);
                        int m5 = m4 + 1;
                        item.setitemid(got[m5]);
                        int m6 = m5 + 1;
                        item.setitemname(got[m6]);
                        m = m6 + 1;
                        item.setimgsig(got[m]);
                    }
                    Report.listAdapter.notifyDataSetChanged();
                    Report.footerview.setVisibility(View.GONE);
                    return;
                }
                Report.heart.setVisibility(View.GONE);
                Report.footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_report);
        heart = (ImageView) findViewById(R.id.heart);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Report.onBackPressed();
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
        text.setText("Report");
        text.setTypeface(face);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        feedItems = new ArrayList();
        listAdapter = new Report_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        list.setOnScrollListener(new OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount == totalItemCount - firstVisibleItem && Report.flag) {
                    Report.flag = false;
                    if (!Report.cd.isConnectingToInternet()) {
                        Toasty.info(Report.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    } else if (Report.footerview.getVisibility() != 0) {
                        Report.limit += 30;
                        new loadstatus1().execute(new String[0]);
                    }
                }
            }

            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                if (arg1 == 2) {
                    Report.flag = true;
                }
            }
        });
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (Report.cd.isConnectingToInternet()) {
                    Report.nointernet.setVisibility(View.GONE);
                    Report.limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                Report.nointernet.setVisibility(View.VISIBLE);
                Toasty.info(Report.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            limit = 0;
            new loadstatus().execute(new String[0]);
            return;
        }
        nointernet.setVisibility(View.VISIBLE);
        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
    }
}
