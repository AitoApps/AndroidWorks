package com.chintha_admin;

import adapter.RechargeListAdapterNew;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import data.Recharge_FeedItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MK_EASY_RECHARGE_NEW extends AppCompatActivity {
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    public List<Recharge_FeedItem> feedItems;
    boolean flag = false;
    RelativeLayout footerview;
    public int limit = 0;
    ListView list;
    public RechargeListAdapterNew listAdapter;
    ImageView nointernet;
    ProgressBar pb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_mk__easy__recharge__new);
        list = (ListView) findViewById(R.id.list);
        pb = (ProgressBar) findViewById(R.id.pb);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        footerview = (RelativeLayout) getLayoutInflater().inflate(R.layout.footerview, null);
        list.addFooterView(footerview);
        footerview.setVisibility(View.GONE);
        cd = new ConnectionDetecter(this);
        feedItems = new ArrayList();
        listAdapter = new RechargeListAdapterNew(this, feedItems);
        list.setAdapter(listAdapter);
        list.setOnScrollListener(new OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount == totalItemCount - firstVisibleItem && flag) {
                    flag = false;
                    if (!cd.isConnectingToInternet()) {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    } else if (footerview.getVisibility() != 0) {
                        limit += 30;
                        new loadlikes1().execute(new String[0]);
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
                    new loadlikes().execute(new String[0]);
                    return;
                }
                nointernet.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            limit = 0;
            new loadlikes().execute(new String[0]);
            return;
        }
        nointernet.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
    }

    public void removeitem(int position) {
        feedItems.remove(position);
        listAdapter.notifyDataSetChanged();
    }
    public class loadlikes extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            list.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);
            nointernet.setVisibility(View.GONE);
            limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink3+"getecrecharge.php";
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
                if (result.contains("%:ok")) {
                    feedItems.clear();
                    String[] got = result.split("%:");
                    int k = (got.length - 1) / 4;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Recharge_FeedItem item = new Recharge_FeedItem();
                        m=m+1;
                        item.setsn(got[m]);
                        m=m+1;
                        item.setuserid(got[m]);
                        m=m+1;
                        item.setoperator(got[m]);
                        m=m+1;
                        item.setcoponcode(got[m]);
                        feedItems.add(item);
                    }
                    pb.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                pb.setVisibility(View.GONE);
                footerview.setVisibility(View.GONE);
            } catch (Exception a) {
                Toast.makeText(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
    }

    public class loadlikes1 extends AsyncTask<String, Void, String> {
        public loadlikes1() {
        }
        public void onPreExecute() {
            footerview.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink3+"getecrecharge.php";
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
                if (result.contains("%:ok")) {
                    String[] got = result.split("%:");
                    int k = (got.length - 1) / 4;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Recharge_FeedItem item = new Recharge_FeedItem();
                        m=m+1;
                        item.setsn(got[m]);
                        m=m+1;
                        item.setuserid(got[m]);
                        m=m+1;
                        item.setoperator(got[m]);
                        m=m+1;
                        item.setcoponcode(got[m]);
                        feedItems.add(item);
                    }
                    footerview.setVisibility(View.GONE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                footerview.setVisibility(View.GONE);
            } catch (Exception a) {
                Toast.makeText(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
    }
}
