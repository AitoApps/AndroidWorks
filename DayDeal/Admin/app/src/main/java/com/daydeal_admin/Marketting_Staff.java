package com.daydeal_admin;

import adapter.Markettingstaff_ListAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import data.Marketting_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Marketting_Staff extends AppCompatActivity {
    ImageView addstaff;
    ImageView back;
    ConnectionDetecter cd;
    Typeface face;
    public List<Marketting_FeedItem> feedItems;
    boolean flag = false;
    RelativeLayout footerview;
    ImageView heart;
    public int limit = 0;
    ListView list;

    public Markettingstaff_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    TextView text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_marketting__staff);
        heart = (ImageView) findViewById(R.id.heart);
        addstaff = (ImageView) findViewById(R.id.addstaff);
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
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        text.setText("Marketting Staff");
        text.setTypeface(face);
        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        feedItems = new ArrayList();
        listAdapter = new Markettingstaff_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        list.setOnScrollListener(new OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount == totalItemCount - firstVisibleItem && flag) {
                    flag = false;
                    if (!cd.isConnectingToInternet()) {
                        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    } else if (footerview.getVisibility() != View.VISIBLE) {
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
        addstaff.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.markettingstaffedit = 0;
                startActivity(new Intent(getApplicationContext(), Add_Marketting_Staff.class));
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

    public void changeitem(int position, String sn1, String name1, String place1, String mobile1, String mobile2, String imgsigphoto1, String imgsigadhar1, String status) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
            Marketting_FeedItem item = new Marketting_FeedItem();
            item.setSn(sn1);
            item.setName(name1);
            item.setPlace(place1);
            item.setMobile1(mobile1);
            item.setMobile2(mobile2);
            item.setImgsigphoto(imgsigphoto1);
            item.setImgsigadhar(imgsigadhar1);
            item.setStatus(status);
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

                String link= Temp.weblink +"admin_getmarkettinglist.php";
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
                    int k = (got.length - 1) / 8;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Marketting_FeedItem item = new Marketting_FeedItem();
                        m=m+1;
                        item.setSn(got[m]);
                        m=m+1;
                        item.setName(got[m]);
                        m=m+1;
                        item.setPlace(got[m]);
                        m=m+1;
                        item.setMobile1(got[m]);
                        m=m+1;
                        item.setMobile2(got[m]);
                        m=m+1;
                        item.setImgsigphoto(got[m]);
                        m=m+1;
                        item.setImgsigadhar(got[m]);
                        m=m+1;
                        item.setStatus(got[m]);
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

                String link= Temp.weblink +"admin_getmarkettinglist.php";
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
                    int k = (got.length - 1) / 8;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Marketting_FeedItem item = new Marketting_FeedItem();
                        m=m+1;
                        item.setSn(got[m]);
                        m=m+1;
                        item.setName(got[m]);
                        m=m+1;
                        item.setPlace(got[m]);
                        m=m+1;
                        item.setMobile1(got[m]);
                        m=m+1;
                        item.setMobile2(got[m]);
                        m=m+1;
                        item.setImgsigphoto(got[m]);
                        m=m+1;
                        item.setImgsigadhar(got[m]);
                        m=m+1;
                        item.setStatus(got[m]);
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
