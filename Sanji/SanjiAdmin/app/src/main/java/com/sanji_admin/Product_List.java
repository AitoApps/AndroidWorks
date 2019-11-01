package com.sanji_admin;

import adapter.Productlist_ListAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import data.Productlist_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Product_List extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    Typeface face;
    public List<Productlist_FeedItem> feedItems;
    boolean flag = false;
    RelativeLayout footerview;
    ImageView heart;
    public int limit = 0;
    ListView list;
    public Productlist_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    ImageView products;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Product_List.nointernet.setVisibility(View.GONE);
            Product_List.nodata.setVisibility(View.GONE);
            Product_List.list.setVisibility(View.GONE);
            Product_List.heart.setVisibility(View.VISIBLE);
            Product_List.limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getproductlist_admin.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Product_List.limit);
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
                    Product_List.feedItems.clear();
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 11;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Productlist_FeedItem item = new Productlist_FeedItem();
                        int m2 = m + 1;
                        item.setsn(got[m2]);
                        int m3 = m2 + 1;
                        item.setitemname(got[m3]);
                        int m4 = m3 + 1;
                        item.setofferprice(got[m4]);
                        int m5 = m4 + 1;
                        item.setorginalprice(got[m5]);
                        int m6 = m5 + 1;
                        item.setstatus(got[m6]);
                        int m7 = m6 + 1;
                        item.setdiscription(got[m7]);
                        int m8 = m7 + 1;
                        item.setimgsig1(got[m8]);
                        int m9 = m8 + 1;
                        item.setcatid(got[m9]);
                        int m10 = m9 + 1;
                        item.setUnittype(got[m10]);
                        int m11 = m10 + 1;
                        item.setMinorder(got[m11]);
                        m = m11 + 1;
                        item.setItemkeyword(got[m]);
                        Product_List.feedItems.add(item);
                    }
                    Product_List.heart.setVisibility(View.GONE);
                    Product_List.list.setVisibility(View.VISIBLE);
                    Product_List.listAdapter.notifyDataSetChanged();
                    return;
                }
                Product_List.nodata.setVisibility(View.VISIBLE);
                Product_List.heart.setVisibility(View.GONE);
                Product_List.footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }

    public class loadstatus1 extends AsyncTask<String, Void, String> {
        public loadstatus1() {
        }
        public void onPreExecute() {
            Product_List.footerview.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getproductlist_admin.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Product_List.limit);
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
                    int k = (got.length - 1) / 11;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Productlist_FeedItem item = new Productlist_FeedItem();
                        int m2 = m + 1;
                        item.setsn(got[m2]);
                        int m3 = m2 + 1;
                        item.setitemname(got[m3]);
                        int m4 = m3 + 1;
                        item.setofferprice(got[m4]);
                        int m5 = m4 + 1;
                        item.setorginalprice(got[m5]);
                        int m6 = m5 + 1;
                        item.setstatus(got[m6]);
                        int m7 = m6 + 1;
                        item.setdiscription(got[m7]);
                        int m8 = m7 + 1;
                        item.setimgsig1(got[m8]);
                        int m9 = m8 + 1;
                        item.setcatid(got[m9]);
                        int m10 = m9 + 1;
                        item.setUnittype(got[m10]);
                        int m11 = m10 + 1;
                        item.setMinorder(got[m11]);
                        m = m11 + 1;
                        item.setItemkeyword(got[m]);
                        Product_List.feedItems.add(item);
                    }
                    Product_List.listAdapter.notifyDataSetChanged();
                    Product_List.footerview.setVisibility(View.GONE);
                    return;
                }
                Product_List.heart.setVisibility(View.GONE);
                Product_List.footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_product__list);
        heart = (ImageView) findViewById(R.id.heart);
        products = (ImageView) findViewById(R.id.products);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Product_List.onBackPressed();
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
        text.setText("Product List");
        text.setTypeface(face);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        feedItems = new ArrayList();
        listAdapter = new Productlist_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        list.setOnScrollListener(new OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount == totalItemCount - firstVisibleItem && Product_List.flag) {
                    Product_List.flag = false;
                    if (!Product_List.cd.isConnectingToInternet()) {
                        Toasty.info(Product_List.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    } else if (Product_List.footerview.getVisibility() != 0) {
                        Product_List.limit += 30;
                        new loadstatus1().execute(new String[0]);
                    }
                }
            }

            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                if (arg1 == 2) {
                    Product_List.flag = true;
                }
            }
        });
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (Product_List.cd.isConnectingToInternet()) {
                    Product_List.nointernet.setVisibility(View.GONE);
                    Product_List.limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                Product_List.nointernet.setVisibility(View.VISIBLE);
                Toasty.info(Product_List.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        products.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.isproductedit = 0;
                Product_List.startActivity(new Intent(Product_List.getApplicationContext(), Add_Product.class));
            }
        });
    }
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
}
