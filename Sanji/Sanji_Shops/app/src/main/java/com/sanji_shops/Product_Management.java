package com.sanji_shops;

import adapter.Productlist_ListAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
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

public class Product_Management extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    Dialog dialog2;
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
    EditText search;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Product_Management.nointernet.setVisibility(View.GONE);
            Product_Management.nodata.setVisibility(View.GONE);
            Product_Management.list.setVisibility(View.GONE);
            Product_Management.heart.setVisibility(View.VISIBLE);
            Product_Management.limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getproductlist_new_shops.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Product_Management.limit);
                sb3.append(":%");
                sb3.append(Product_Management.udb.get_shopid());
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
                    Product_Management.feedItems.clear();
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
                        Product_Management.feedItems.add(item);
                    }
                    Product_Management.heart.setVisibility(View.GONE);
                    Product_Management.list.setVisibility(View.VISIBLE);
                    Product_Management.listAdapter.notifyDataSetChanged();
                    return;
                }
                Product_Management.nodata.setVisibility(View.VISIBLE);
                Product_Management.heart.setVisibility(View.GONE);
                Product_Management.footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }

    public class loadstatus1 extends AsyncTask<String, Void, String> {
        public loadstatus1() {
        }
        public void onPreExecute() {
            Product_Management.footerview.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getproductlist_shops.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Product_Management.limit);
                sb3.append(":%");
                sb3.append(Product_Management.udb.get_shopid());
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
                        Product_Management.feedItems.add(item);
                    }
                    Product_Management.listAdapter.notifyDataSetChanged();
                    Product_Management.footerview.setVisibility(View.GONE);
                    return;
                }
                Product_Management.heart.setVisibility(View.GONE);
                Product_Management.footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_product__management);
        heart = (ImageView) findViewById(R.id.heart);
        products = (ImageView) findViewById(R.id.products);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        search = (EditText) findViewById(R.id.search);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Product_Management.onBackPressed();
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
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (Product_Management.cd.isConnectingToInternet()) {
                    Product_Management.nointernet.setVisibility(View.GONE);
                    Product_Management.limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                Product_Management.nointernet.setVisibility(View.VISIBLE);
                Toasty.info(Product_Management.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        products.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Product_Management.add_newproduct();
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                Product_Management.filter(editable.toString());
            }
        });
    }
    public void filter(String text2) {
        List<Productlist_FeedItem> temp = new ArrayList<>();
        for (Productlist_FeedItem d : feedItems) {
            if (text2.equalsIgnoreCase("")) {
                temp.add(d);
            } else if (d.getitemname().toLowerCase().contains(text2.toLowerCase())) {
                temp.add(d);
            }
        }
        listAdapter.updateList(temp);
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

    public void add_newproduct() {
        dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(1);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.setContentView(R.layout.custom_pickproduct);
        dialog2.setCancelable(true);
        TextView pickproduct = (TextView) dialog2.findViewById(R.id.pickproduct);
        RelativeLayout lytpick = (RelativeLayout) dialog2.findViewById(R.id.lytpick);
        RelativeLayout lytaddnewprod = (RelativeLayout) dialog2.findViewById(R.id.lytaddnewprod);
        ((TextView) dialog2.findViewById(R.id.addnewprod)).setTypeface(face);
        pickproduct.setTypeface(face);
        lytaddnewprod.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.isproductedit = 0;
                Temp.edit_productid = "0";
                Product_Management.startActivity(new Intent(Product_Management.getApplicationContext(), Add_Product.class));
                Product_Management.dialog2.dismiss();
            }
        });
        lytpick.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Product_Management.startActivity(new Intent(Product_Management.getApplicationContext(), Search_Product_List.class));
                Product_Management.dialog2.dismiss();
            }
        });
        dialog2.show();
    }
}
