package com.sanji_admin;

import adapter.ApprovelProduct_ListAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
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
import data.ApprovelProduct_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Product_verification extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face;
    public List<ApprovelProduct_FeedItem> feedItems;
    boolean flag = false;
    RelativeLayout footerview;
    ImageView heart;
    public int limit = 0;
    ListView list;
    public ApprovelProduct_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Product_verification.nointernet.setVisibility(View.GONE);
            Product_verification.nodata.setVisibility(View.GONE);
            Product_verification.list.setVisibility(View.GONE);
            Product_verification.heart.setVisibility(View.VISIBLE);
            Product_verification.limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getpendingprdctapprovel.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Product_verification.limit);
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
                    Product_verification.feedItems.clear();
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 10;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        ApprovelProduct_FeedItem item = new ApprovelProduct_FeedItem();
                        int m2 = m + 1;
                        item.setsn(got[m2]);
                        int m3 = m2 + 1;
                        item.setitemname(got[m3]);
                        int m4 = m3 + 1;
                        item.setofferprice(got[m4]);
                        int m5 = m4 + 1;
                        item.setorginalprice(got[m5]);
                        int m6 = m5 + 1;
                        item.setimgsig1(got[m6]);
                        int m7 = m6 + 1;
                        item.setshopid(got[m7]);
                        int m8 = m7 + 1;
                        item.setshopname(got[m8]);
                        int m9 = m8 + 1;
                        item.setshopmobile(got[m9]);
                        int m10 = m9 + 1;
                        item.setMinqty(got[m10]);
                        m = m10 + 1;
                        item.setUnit(got[m]);
                        Product_verification.feedItems.add(item);
                    }
                    Product_verification.heart.setVisibility(View.GONE);
                    Product_verification.list.setVisibility(View.VISIBLE);
                    Product_verification.listAdapter.notifyDataSetChanged();
                    return;
                }
                Product_verification.nodata.setVisibility(View.VISIBLE);
                Product_verification.heart.setVisibility(View.GONE);
                Product_verification.footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }

    public class loadstatus1 extends AsyncTask<String, Void, String> {
        public loadstatus1() {
        }
        public void onPreExecute() {
            Product_verification.footerview.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getpendingprdctapprovel.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Product_verification.limit);
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
                    int k = (got.length - 1) / 10;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        ApprovelProduct_FeedItem item = new ApprovelProduct_FeedItem();
                        int m2 = m + 1;
                        item.setsn(got[m2]);
                        int m3 = m2 + 1;
                        item.setitemname(got[m3]);
                        int m4 = m3 + 1;
                        item.setofferprice(got[m4]);
                        int m5 = m4 + 1;
                        item.setorginalprice(got[m5]);
                        int m6 = m5 + 1;
                        item.setimgsig1(got[m6]);
                        int m7 = m6 + 1;
                        item.setshopid(got[m7]);
                        int m8 = m7 + 1;
                        item.setshopname(got[m8]);
                        int m9 = m8 + 1;
                        item.setshopmobile(got[m9]);
                        int m10 = m9 + 1;
                        item.setMinqty(got[m10]);
                        m = m10 + 1;
                        item.setUnit(got[m]);
                        Product_verification.feedItems.add(item);
                    }
                    Product_verification.listAdapter.notifyDataSetChanged();
                    Product_verification.footerview.setVisibility(View.GONE);
                    return;
                }
                Product_verification.heart.setVisibility(View.GONE);
                Product_verification.footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_product_verification);
        heart = (ImageView) findViewById(R.id.heart);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Product_verification.onBackPressed();
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
        listAdapter = new ApprovelProduct_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        list.setOnScrollListener(new OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount == totalItemCount - firstVisibleItem && Product_verification.flag) {
                    Product_verification.flag = false;
                    if (!Product_verification.cd.isConnectingToInternet()) {
                        Toasty.info(Product_verification.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    } else if (Product_verification.footerview.getVisibility() != 0) {
                        Product_verification.limit += 30;
                        new loadstatus1().execute(new String[0]);
                    }
                }
            }

            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                if (arg1 == 2) {
                    Product_verification.flag = true;
                }
            }
        });
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (Product_verification.cd.isConnectingToInternet()) {
                    Product_verification.nointernet.setVisibility(View.GONE);
                    Product_verification.limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                Product_verification.nointernet.setVisibility(View.VISIBLE);
                Toasty.info(Product_verification.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
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

    public void call(String mob) {
        if (mob.length() > 7) {
            try {
                if (ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
                    ActivityCompat.requestPermissions(this, new String[]{"android.permission.CALL_PHONE"}, 1);
                    return;
                }
                Intent callIntent = new Intent("android.intent.action.CALL");
                StringBuilder sb = new StringBuilder();
                sb.append("tel:");
                sb.append(mob);
                callIntent.setData(Uri.parse(sb.toString()));
                startActivity(callIntent);
            } catch (Exception e) {
            }
        } else {
            Toasty.info(getApplicationContext(), "Sorry! This is not a valid phone number", Toast.LENGTH_SHORT).show();
        }
    }
}
