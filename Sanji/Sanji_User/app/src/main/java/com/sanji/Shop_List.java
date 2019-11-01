package com.sanji;

import adapter.ShopList_ListAdapter;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.bumptech.glide.Glide;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;
import data.ShoplistHome_Feed;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Shop_List extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    RelativeLayout content;
    public DatabaseHandler db;
    Typeface face;
    Typeface face1;

    public List<ShoplistHome_Feed> feedItems;
    View footerView;
    ImageView heart;

    public SwipeRefreshLayout layout;
    public int limit = 0;

    public ShopList_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    public HeaderAndFooterRecyclerView recylerview;
    TextView text;
    public UserDatabaseHandler udb;

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Shop_List.nodata.setVisibility(View.GONE);
            Shop_List.recylerview.setVisibility(View.GONE);
            Shop_List.heart.setVisibility(View.VISIBLE);
            Shop_List.limit = 0;
        }
        public String doInBackground(String... arg0) {
            String str = "UTF-8";
            String str2 = ":%";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getshops_list.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.shopcat);
                sb3.append(str2);
                sb3.append(Shop_List.db.get_latitude());
                sb3.append(str2);
                sb3.append(Shop_List.db.get_longtitude());
                sb3.append(str2);
                sb3.append(Shop_List.limit);
                sb2.append(URLEncoder.encode(sb3.toString(), str));
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
                return new String(Log.getStackTraceString(e));
            }
        }
        public void onPostExecute(String result) {
            try {
                if (result.contains("::ok")) {
                    try {
                        Shop_List.feedItems.clear();
                        String[] got = result.split("::");
                        int k = (got.length - 1) / 10;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            ShoplistHome_Feed item2 = new ShoplistHome_Feed();
                            int m2 = m + 1;
                            item2.setSn(got[m2]);
                            int m3 = m2 + 1;
                            item2.setShopname(got[m3]);
                            int m4 = m3 + 1;
                            item2.setShopimgisg(got[m4]);
                            int m5 = m4 + 1;
                            StringBuilder sb = new StringBuilder();
                            sb.append(String.format("%.2f", new Object[]{Double.valueOf(Double.parseDouble(got[m5]))}));
                            sb.append(" KM");
                            item2.setDistance(sb.toString());
                            int m6 = m5 + 1;
                            item2.setPlace(got[m6]);
                            int m7 = m6 + 1;
                            item2.setWebsite(got[m7]);
                            int m8 = m7 + 1;
                            item2.setInstagram(got[m8]);
                            int m9 = m8 + 1;
                            item2.setFacebook(got[m9]);
                            int m10 = m9 + 1;
                            item2.setPinterest(got[m10]);
                            m = m10 + 1;
                            item2.setYoutube(got[m]);
                            Shop_List.feedItems.add(item2);
                        }
                    } catch (Exception e) {
                    }
                    Shop_List.nodata.setVisibility(View.GONE);
                    Shop_List.heart.setVisibility(View.GONE);
                    Shop_List.recylerview.setVisibility(View.VISIBLE);
                    Shop_List.listAdapter.notifyDataSetChanged();
                    return;
                }
                Shop_List.nodata.setVisibility(View.VISIBLE);
                Shop_List.recylerview.setVisibility(View.GONE);
                Shop_List.heart.setVisibility(View.GONE);
            } catch (Exception a) {
                Toasty.info(Shop_List.getApplicationContext(), Log.getStackTraceString(a), 1).show();
            }
        }
    }

    public class loadstatus1 extends AsyncTask<String, Void, String> {
        public loadstatus1() {
        }
        public void onPreExecute() {
            Shop_List.limit += 30;
            Shop_List.footerView.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            String str = "UTF-8";
            String str2 = ":%";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getshops_list.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.shopcat);
                sb3.append(str2);
                sb3.append(Shop_List.db.get_latitude());
                sb3.append(str2);
                sb3.append(Shop_List.db.get_longtitude());
                sb3.append(str2);
                sb3.append(Shop_List.limit);
                sb2.append(URLEncoder.encode(sb3.toString(), str));
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
                if (result.contains("::ok")) {
                    try {
                        String[] got = result.split("::");
                        int k = (got.length - 1) / 10;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            ShoplistHome_Feed item2 = new ShoplistHome_Feed();
                            int m2 = m + 1;
                            item2.setSn(got[m2]);
                            int m3 = m2 + 1;
                            item2.setShopname(got[m3]);
                            int m4 = m3 + 1;
                            item2.setShopimgisg(got[m4]);
                            int m5 = m4 + 1;
                            StringBuilder sb = new StringBuilder();
                            sb.append(String.format("%.2f", new Object[]{Double.valueOf(Double.parseDouble(got[m5]))}));
                            sb.append(" KM");
                            item2.setDistance(sb.toString());
                            int m6 = m5 + 1;
                            item2.setPlace(got[m6]);
                            int m7 = m6 + 1;
                            item2.setWebsite(got[m7]);
                            int m8 = m7 + 1;
                            item2.setInstagram(got[m8]);
                            int m9 = m8 + 1;
                            item2.setFacebook(got[m9]);
                            int m10 = m9 + 1;
                            item2.setPinterest(got[m10]);
                            m = m10 + 1;
                            item2.setYoutube(got[m]);
                            Shop_List.feedItems.add(item2);
                        }
                    } catch (Exception e) {
                    }
                    Shop_List.footerView.setVisibility(View.INVISIBLE);
                    Shop_List.listAdapter.notifyDataSetChanged();
                    return;
                }
                Shop_List.footerView.setVisibility(View.INVISIBLE);
                Shop_List.heart.setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }

    public class loadstatus2 extends AsyncTask<String, Void, String> {
        public loadstatus2() {
        }
        public void onPreExecute() {
        }
        public String doInBackground(String... arg0) {
            String str = "UTF-8";
            String str2 = ":%";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getshops_list.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.shopcat);
                sb3.append(str2);
                sb3.append(Shop_List.db.get_latitude());
                sb3.append(str2);
                sb3.append(Shop_List.db.get_longtitude());
                sb3.append(str2);
                sb3.append(Shop_List.limit);
                sb2.append(URLEncoder.encode(sb3.toString(), str));
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
                Shop_List.layout.setRefreshing(false);
                if (result.contains("::ok")) {
                    try {
                        Shop_List.feedItems.clear();
                        String[] got = result.split("::");
                        int k = (got.length - 1) / 10;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            ShoplistHome_Feed item2 = new ShoplistHome_Feed();
                            int m2 = m + 1;
                            item2.setSn(got[m2]);
                            int m3 = m2 + 1;
                            item2.setShopname(got[m3]);
                            int m4 = m3 + 1;
                            item2.setShopimgisg(got[m4]);
                            int m5 = m4 + 1;
                            StringBuilder sb = new StringBuilder();
                            sb.append(String.format("%.2f", new Object[]{Double.valueOf(Double.parseDouble(got[m5]))}));
                            sb.append(" KM");
                            item2.setDistance(sb.toString());
                            int m6 = m5 + 1;
                            item2.setPlace(got[m6]);
                            int m7 = m6 + 1;
                            item2.setWebsite(got[m7]);
                            int m8 = m7 + 1;
                            item2.setInstagram(got[m8]);
                            int m9 = m8 + 1;
                            item2.setFacebook(got[m9]);
                            int m10 = m9 + 1;
                            item2.setPinterest(got[m10]);
                            m = m10 + 1;
                            item2.setYoutube(got[m]);
                            Shop_List.feedItems.add(item2);
                        }
                    } catch (Exception e) {
                    }
                    Shop_List.listAdapter.notifyDataSetChanged();
                    return;
                }
                Shop_List.heart.setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_shop__list);
        try {
            face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
            face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
            db = new DatabaseHandler(this);
            cd = new ConnectionDetecter(this);
            udb = new UserDatabaseHandler(this);
            pd = new ProgressDialog(this);
            nodata = (ImageView) findViewById(R.id.nodata);
            text = (TextView) findViewById(R.id.text);
            content = (RelativeLayout) findViewById(R.id.content);
            recylerview = (HeaderAndFooterRecyclerView) findViewById(R.id.recylerview);
            heart = (ImageView) findViewById(R.id.heart);
            back = (ImageView) findViewById(R.id.back);
            nointernet = (ImageView) findViewById(R.id.nointernet);
            layout = (SwipeRefreshLayout) findViewById(R.id.layout);
            layout.setEnabled(true);
            feedItems = new ArrayList();
            listAdapter = new ShopList_ListAdapter(this, feedItems);
            recylerview.setLayoutManager(new GridLayoutManager(this, 2));
            recylerview.setAdapter(listAdapter);
            footerView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.footerview, recylerview.getFooterContainer(), false);
            ((TextView) footerView.findViewById(R.id.next)).setTypeface(face);
            recylerview.addFooterView(footerView);
            footerView.setVisibility(View.INVISIBLE);
            text.setText(Temp.catogeryname);
            text.setTypeface(face1);
            layout.setOnRefreshListener(new OnRefreshListener() {
                public void onRefresh() {
                    Shop_List.layout.setRefreshing(true);
                    Shop_List.nointernet.setVisibility(View.GONE);
                    Shop_List shop_List = Shop_List.this;
                    shop_List.limit = 0;
                    new loadstatus2().execute(new String[0]);
                }
            });
            back.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Shop_List.onBackPressed();
                }
            });
            nointernet.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (Shop_List.cd.isConnectingToInternet()) {
                        Shop_List.nointernet.setVisibility(View.GONE);
                        Shop_List shop_List = Shop_List.this;
                        shop_List.limit = 0;
                        new loadstatus().execute(new String[0]);
                        return;
                    }
                    Shop_List.nointernet.setVisibility(View.VISIBLE);
                    Toasty.info(Shop_List.getApplicationContext(), Temp.nointernet, 0).show();
                }
            });
            Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
            if (cd.isConnectingToInternet()) {
                nointernet.setVisibility(View.GONE);
                limit = 0;
                new loadstatus().execute(new String[0]);
                return;
            }
            nointernet.setVisibility(View.VISIBLE);
            Toasty.info(getApplicationContext(), Temp.nointernet, 0).show();
        } catch (Exception a) {
            Toasty.info(getApplicationContext(), Log.getStackTraceString(a), 0).show();
        }
    }

    public void loadmore() {
        if (footerView.getVisibility() == View.VISIBLE) {
            return;
        }
        if (cd.isConnectingToInternet()) {
            new loadstatus1().execute(new String[0]);
        } else {
            Toasty.info(getApplicationContext(), Temp.nointernet, 0).show();
        }
    }
}
