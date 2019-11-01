package com.sanji;

import adapter.Product_List_ByShopCat_ListAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;
import data.Productlist_new_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class Productist_ByCatogery_FromShops extends AppCompatActivity {
    ImageView back;
    ImageView carticon;
    RelativeLayout cartlyt;
    ConnectionDetecter cd;
    RelativeLayout content;
    public DatabaseHandler db;
    public Dialog dialog;
    Typeface face;
    Typeface face1;

    public List<Productlist_new_FeedItem> feedItems;
    View footerView;
    ImageView heart;
    String imgid;
    String imgsig;
    TextView itemcount;

    public SwipeRefreshLayout layout;
    public int limit = 0;

    public Product_List_ByShopCat_ListAdapter listAdapter;

    public MediaPlayer mediaPlayer;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    public HeaderAndFooterRecyclerView recylerview;
    TextView text;
    public UserDatabaseHandler udb;
    TextView viewcart;

    public class MainSliderAdapter extends SliderAdapter {
        public MainSliderAdapter() {
        }

        public int getItemCount() {
            return 3;
        }

        public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
            String str = "productpics/";
            if (position == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append(str);
                sb.append(Productist_ByCatogery_FromShops.imgid);
                sb.append("_1.jpg");
                viewHolder.bindImageSlide(sb.toString(), R.drawable.placeholder, R.drawable.placeholder);
            } else if (position == 1) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Temp.weblink);
                sb2.append(str);
                sb2.append(Productist_ByCatogery_FromShops.imgid);
                sb2.append("_2.jpg");
                viewHolder.bindImageSlide(sb2.toString(), R.drawable.placeholder, R.drawable.placeholder);
            } else if (position == 2) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.weblink);
                sb3.append(str);
                sb3.append(Productist_ByCatogery_FromShops.imgid);
                sb3.append("_3.jpg");
                viewHolder.bindImageSlide(sb3.toString(), R.drawable.placeholder, R.drawable.placeholder);
            }
        }
    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Productist_ByCatogery_FromShops.nodata.setVisibility(View.GONE);
            Productist_ByCatogery_FromShops.recylerview.setVisibility(View.GONE);
            Productist_ByCatogery_FromShops.heart.setVisibility(View.VISIBLE);
            Productist_ByCatogery_FromShops.limit = 0;
        }
        public String doInBackground(String... arg0) {
            String str = ":%";
            String str2 = "UTF-8";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getproduct_byshopcat.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str2));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.shopid);
                sb3.append(str);
                sb3.append(Temp.shopcat);
                sb3.append(str);
                sb3.append(Productist_ByCatogery_FromShops.limit);
                sb2.append(URLEncoder.encode(sb3.toString(), str2));
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
                Log.w("salman", result);
                if (result.contains(":%ok")) {
                    try {
                        Productist_ByCatogery_FromShops.feedItems.clear();
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 19;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Productlist_new_FeedItem item2 = new Productlist_new_FeedItem();
                            int m2 = m + 1;
                            item2.setsn(got[m2]);
                            int m3 = m2 + 1;
                            item2.setproductcat(got[m3]);
                            int m4 = m3 + 1;
                            item2.setshopid(got[m4]);
                            int m5 = m4 + 1;
                            item2.setitemname(got[m5]);
                            int m6 = m5 + 1;
                            item2.setprice(got[m6]);
                            int m7 = m6 + 1;
                            item2.setogprice(got[m7]);
                            int m8 = m7 + 1;
                            item2.setitemdiscription(got[m8]);
                            int m9 = m8 + 1;
                            item2.setMinorder(got[m9]);
                            int m10 = m9 + 1;
                            item2.setUnittype(got[m10]);
                            int m11 = m10 + 1;
                            item2.setimgsig1(got[m11]);
                            int m12 = m11 + 1;
                            item2.setshopname(got[m12]);
                            int m13 = m12 + 1;
                            item2.setshopplace(got[m13]);
                            int m14 = m13 + 1;
                            item2.setshopmobile(got[m14]);
                            int m15 = m14 + 1;
                            item2.setshoptime(got[m15]);
                            int m16 = m15 + 1;
                            item2.setlocation(got[m16]);
                            int m17 = m16 + 1;
                            item2.setDelicharge(got[m17]);
                            int m18 = m17 + 1;
                            item2.setDelidisc(got[m18]);
                            int m19 = m18 + 1;
                            item2.setMinordramt(got[m19]);
                            m = m19 + 1;
                            item2.setshopimgsig(got[m]);
                            Productist_ByCatogery_FromShops.feedItems.add(item2);
                        }
                    } catch (Exception e) {
                    }
                    Productist_ByCatogery_FromShops.nodata.setVisibility(View.GONE);
                    Productist_ByCatogery_FromShops.heart.setVisibility(View.GONE);
                    Productist_ByCatogery_FromShops.recylerview.setVisibility(View.VISIBLE);
                    Productist_ByCatogery_FromShops.listAdapter.notifyDataSetChanged();
                    return;
                }
                Productist_ByCatogery_FromShops.nodata.setVisibility(View.VISIBLE);
                Productist_ByCatogery_FromShops.recylerview.setVisibility(View.GONE);
                Productist_ByCatogery_FromShops.heart.setVisibility(View.GONE);
            } catch (Exception a) {
                Toasty.info(Productist_ByCatogery_FromShops.getApplicationContext(), Log.getStackTraceString(a), 1).show();
            }
        }
    }

    public class loadstatus1 extends AsyncTask<String, Void, String> {
        public loadstatus1() {
        }
        public void onPreExecute() {
            Productist_ByCatogery_FromShops.limit += 50;
            Productist_ByCatogery_FromShops.footerView.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            String str = ":%";
            String str2 = "UTF-8";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getproduct_byshopcat.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str2));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.shopid);
                sb3.append(str);
                sb3.append(Temp.shopcat);
                sb3.append(str);
                sb3.append(Productist_ByCatogery_FromShops.limit);
                sb2.append(URLEncoder.encode(sb3.toString(), str2));
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
                    try {
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 19;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Productlist_new_FeedItem item2 = new Productlist_new_FeedItem();
                            int m2 = m + 1;
                            item2.setsn(got[m2]);
                            int m3 = m2 + 1;
                            item2.setproductcat(got[m3]);
                            int m4 = m3 + 1;
                            item2.setshopid(got[m4]);
                            int m5 = m4 + 1;
                            item2.setitemname(got[m5]);
                            int m6 = m5 + 1;
                            item2.setprice(got[m6]);
                            int m7 = m6 + 1;
                            item2.setogprice(got[m7]);
                            int m8 = m7 + 1;
                            item2.setitemdiscription(got[m8]);
                            int m9 = m8 + 1;
                            item2.setMinorder(got[m9]);
                            int m10 = m9 + 1;
                            item2.setUnittype(got[m10]);
                            int m11 = m10 + 1;
                            item2.setimgsig1(got[m11]);
                            int m12 = m11 + 1;
                            item2.setshopname(got[m12]);
                            int m13 = m12 + 1;
                            item2.setshopplace(got[m13]);
                            int m14 = m13 + 1;
                            item2.setshopmobile(got[m14]);
                            int m15 = m14 + 1;
                            item2.setshoptime(got[m15]);
                            int m16 = m15 + 1;
                            item2.setlocation(got[m16]);
                            int m17 = m16 + 1;
                            item2.setDelicharge(got[m17]);
                            int m18 = m17 + 1;
                            item2.setDelidisc(got[m18]);
                            int m19 = m18 + 1;
                            item2.setMinordramt(got[m19]);
                            m = m19 + 1;
                            item2.setshopimgsig(got[m]);
                            Productist_ByCatogery_FromShops.feedItems.add(item2);
                        }
                    } catch (Exception e) {
                    }
                    Productist_ByCatogery_FromShops.footerView.setVisibility(View.INVISIBLE)
                    Productist_ByCatogery_FromShops.listAdapter.notifyDataSetChanged();
                    return;
                }
                Productist_ByCatogery_FromShops.footerView.setVisibility(View.INVISIBLE)
                Productist_ByCatogery_FromShops.heart.setVisibility(View.GONE);
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
            String str = ":%";
            String str2 = "UTF-8";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getproduct_byshopcat.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str2));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.shopid);
                sb3.append(str);
                sb3.append(Temp.shopcat);
                sb3.append(str);
                sb3.append(Productist_ByCatogery_FromShops.limit);
                sb2.append(URLEncoder.encode(sb3.toString(), str2));
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
                Productist_ByCatogery_FromShops.layout.setRefreshing(false);
                if (result.contains(":%ok")) {
                    try {
                        Productist_ByCatogery_FromShops.feedItems.clear();
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 19;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Productlist_new_FeedItem item2 = new Productlist_new_FeedItem();
                            int m2 = m + 1;
                            item2.setsn(got[m2]);
                            int m3 = m2 + 1;
                            item2.setproductcat(got[m3]);
                            int m4 = m3 + 1;
                            item2.setshopid(got[m4]);
                            int m5 = m4 + 1;
                            item2.setitemname(got[m5]);
                            int m6 = m5 + 1;
                            item2.setprice(got[m6]);
                            int m7 = m6 + 1;
                            item2.setogprice(got[m7]);
                            int m8 = m7 + 1;
                            item2.setitemdiscription(got[m8]);
                            int m9 = m8 + 1;
                            item2.setMinorder(got[m9]);
                            int m10 = m9 + 1;
                            item2.setUnittype(got[m10]);
                            int m11 = m10 + 1;
                            item2.setimgsig1(got[m11]);
                            int m12 = m11 + 1;
                            item2.setshopname(got[m12]);
                            int m13 = m12 + 1;
                            item2.setshopplace(got[m13]);
                            int m14 = m13 + 1;
                            item2.setshopmobile(got[m14]);
                            int m15 = m14 + 1;
                            item2.setshoptime(got[m15]);
                            int m16 = m15 + 1;
                            item2.setlocation(got[m16]);
                            int m17 = m16 + 1;
                            item2.setDelicharge(got[m17]);
                            int m18 = m17 + 1;
                            item2.setDelidisc(got[m18]);
                            int m19 = m18 + 1;
                            item2.setMinordramt(got[m19]);
                            m = m19 + 1;
                            item2.setshopimgsig(got[m]);
                            Productist_ByCatogery_FromShops.feedItems.add(item2);
                        }
                    } catch (Exception e) {
                    }
                    Productist_ByCatogery_FromShops.listAdapter.notifyDataSetChanged();
                    return;
                }
                Productist_ByCatogery_FromShops.heart.setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }

    public Productist_ByCatogery_FromShops() {
        String str = "";
        imgid = str;
        imgsig = str;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_productist__by_catogery__from_shops);
        try {
            face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
            face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
            db = new DatabaseHandler(this);
            cd = new ConnectionDetecter(this);
            udb = new UserDatabaseHandler(this);
            pd = new ProgressDialog(this);
            nodata = (ImageView) findViewById(R.id.nodata);
            itemcount = (TextView) findViewById(R.id.itemcount);
            viewcart = (TextView) findViewById(R.id.viewcart);
            cartlyt = (RelativeLayout) findViewById(R.id.cartlyt);
            carticon = (ImageView) findViewById(R.id.carticon);
            text = (TextView) findViewById(R.id.text);
            content = (RelativeLayout) findViewById(R.id.content);
            recylerview = (HeaderAndFooterRecyclerView) findViewById(R.id.recylerview);
            heart = (ImageView) findViewById(R.id.heart);
            back = (ImageView) findViewById(R.id.back);
            nointernet = (ImageView) findViewById(R.id.nointernet);
            layout = (SwipeRefreshLayout) findViewById(R.id.layout);
            layout.setEnabled(true);
            feedItems = new ArrayList();
            listAdapter = new Product_List_ByShopCat_ListAdapter(this, feedItems);
            recylerview.setLayoutManager(new GridLayoutManager(this, 2));
            recylerview.setAdapter(listAdapter);
            footerView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.footerview, recylerview.getFooterContainer(), false);
            ((TextView) footerView.findViewById(R.id.next)).setTypeface(face);
            recylerview.addFooterView(footerView);
            footerView.setVisibility(View.INVISIBLE)
            itemcount.setTypeface(face);
            viewcart.setTypeface(face);
            text.setText(Temp.catogeryname);
            text.setTypeface(face1);
            layout.setOnRefreshListener(new OnRefreshListener() {
                public void onRefresh() {
                    Productist_ByCatogery_FromShops.layout.setRefreshing(true);
                    Productist_ByCatogery_FromShops.nointernet.setVisibility(View.GONE);
                    Productist_ByCatogery_FromShops productist_ByCatogery_FromShops = Productist_ByCatogery_FromShops.this;
                    productist_ByCatogery_FromShops.limit = 0;
                    new loadstatus2().execute(new String[0]);
                }
            });
            back.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Productist_ByCatogery_FromShops.onBackPressed();
                }
            });
            nointernet.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (Productist_ByCatogery_FromShops.cd.isConnectingToInternet()) {
                        Productist_ByCatogery_FromShops.nointernet.setVisibility(View.GONE);
                        Productist_ByCatogery_FromShops productist_ByCatogery_FromShops = Productist_ByCatogery_FromShops.this;
                        productist_ByCatogery_FromShops.limit = 0;
                        new loadstatus().execute(new String[0]);
                        return;
                    }
                    Productist_ByCatogery_FromShops.nointernet.setVisibility(View.VISIBLE);
                    Toasty.info(Productist_ByCatogery_FromShops.getApplicationContext(), Temp.nointernet, 0).show();
                }
            });
            Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
            if (cd.isConnectingToInternet()) {
                nointernet.setVisibility(View.GONE);
                limit = 0;
                new loadstatus().execute(new String[0]);
            } else {
                nointernet.setVisibility(View.VISIBLE);
                Toasty.info(getApplicationContext(), Temp.nointernet, 0).show();
            }
            viewcart.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Productist_ByCatogery_FromShops.startActivity(new Intent(Productist_ByCatogery_FromShops.getApplicationContext(), Cart.class));
                }
            });
            carticon.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Productist_ByCatogery_FromShops.startActivity(new Intent(Productist_ByCatogery_FromShops.getApplicationContext(), Cart.class));
                }
            });
            cartlyt.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Productist_ByCatogery_FromShops.startActivity(new Intent(Productist_ByCatogery_FromShops.getApplicationContext(), Cart.class));
                }
            });
        } catch (Exception e) {
        }
    }
    public void onResume() {
        super.onResume();
        refreshcart();
    }

    public void refreshcart() {
        String rupee = getResources().getString(R.string.Rs);
        float gtotal = db.get_cartgrandtotal();
        if (gtotal > 0.0f) {
            TextView textView = itemcount;
            StringBuilder sb = new StringBuilder();
            sb.append(db.get_totalqty());
            sb.append(" Items | ");
            sb.append(rupee);
            sb.append(String.format("%.2f", new Object[]{Float.valueOf(gtotal)}));
            textView.setText(sb.toString());
            return;
        }
        TextView textView2 = itemcount;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(db.get_totalqty());
        sb2.append(" items | ");
        sb2.append(rupee);
        sb2.append("0.00");
        textView2.setText(sb2.toString());
    }

    public void loadmore() {
        if (footerView.getVisibility() == 0) {
            return;
        }
        if (cd.isConnectingToInternet()) {
            new loadstatus1().execute(new String[0]);
        } else {
            Toasty.info(getApplicationContext(), Temp.nointernet, 0).show();
        }
    }

    public void photoview(final String imgsigs, String imgids) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.photoviews);
        imgid = imgids;
        imgsig = imgsigs;
        Slider.init(new ImageLoadingService() {
            public void loadImage(String url, ImageView imageView) {
                Glide.with(Productist_ByCatogery_FromShops.getApplicationContext()).load(url).into(imageView);
            }

            public void loadImage(int resource, ImageView imageView) {
                Glide.with(Productist_ByCatogery_FromShops.getApplicationContext()).load(Integer.valueOf(resource)).into(imageView);
            }

            public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(placeHolder);
                requestOptions.error(errorDrawable);
                requestOptions.signature(new ObjectKey(imgsigs));
                Glide.with(Productist_ByCatogery_FromShops.getApplicationContext()).load(url).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
            }
        });
        ((Slider) dialog.findViewById(R.id.banner_slider1)).setAdapter(new MainSliderAdapter());
        dialog.show();
    }

    public void showaddcart(String cart_shopid1, String cart_productid1, String cart_productname1, String cart_price1, String cart_imgsig1, String cart_minqty1, String cart_unittype1, String cart_shopname1, String cart_delicharge1, String cart_delidisc1, String minordramt, String imgsig1) {
        String str = cart_shopid1;
        String str2 = cart_productid1;
        final String str3 = cart_productname1;
        final String str4 = cart_price1;
        final String str5 = cart_imgsig1;
        final String str6 = cart_minqty1;
        final String str7 = cart_unittype1;
        final String str8 = cart_shopname1;
        final String str9 = cart_delicharge1;
        final String str10 = cart_delidisc1;
        final String str11 = minordramt;
        final String str12 = imgsig1;
        Dialog dialog1 = new Dialog(this);
        final Dialog dialog2 = dialog1;
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.addtocart);
        TextView itemname = (TextView) dialog1.findViewById(R.id.itemname);
        EditText qty = (EditText) dialog1.findViewById(R.id.qty);
        EditText editText = qty;
        TextView unittype = (TextView) dialog1.findViewById(R.id.unittype);
        TextView price = (TextView) dialog1.findViewById(R.id.price);
        final TextView textView = price;
        Button buy = (Button) dialog1.findViewById(R.id.buy);
        ImageView adminclose = (ImageView) dialog1.findViewById(R.id.adminclose);
        String rupee = getResources().getString(R.string.Rs);
        Button buy2 = buy;
        final String str13 = rupee;
        itemname.setText(cart_productname1);
        qty.setText(cart_minqty1);
        Button buy3 = buy2;
        qty.setSelection(cart_minqty1.length());
        TextView textView2 = itemname;
        unittype.setText((CharSequence) Temp.lst_unittype.get(Integer.parseInt(cart_unittype1)));
        StringBuilder sb = new StringBuilder();
        sb.append(rupee);
        TextView textView3 = unittype;
        sb.append(String.format("%.2f", new Object[]{Float.valueOf(Float.parseFloat(cart_minqty1) * Float.parseFloat(cart_price1))}));
        price.setText(sb.toString());
        Button buy4 = buy3;
        final EditText editText2 = qty;
        ImageView adminclose2 = adminclose;
        String rupee2 = rupee;
        final String rupee3 = cart_minqty1;
        String str14 = str2;
        EditText qty2 = qty;
        final TextView textView4 = price;
        String str15 = str;
        AnonymousClass8 r10 = r0;
        final String str16 = rupee2;
        EditText editText3 = editText;
        final Dialog dialog12 = dialog1;
        final String str17 = cart_price1;
        AnonymousClass8 r0 = new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                if (!editText2.getText().toString().equalsIgnoreCase("") && Float.valueOf(editText2.getText().toString()).floatValue() >= 0.0f) {
                    float qty1 = Float.parseFloat(editText2.getText().toString()) / Float.parseFloat(rupee3);
                    TextView textView = textView4;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str16);
                    sb.append(String.format("%.2f", new Object[]{Float.valueOf(Float.parseFloat(str17) * qty1)}));
                    textView.setText(sb.toString());
                }
            }
        };
        qty2.addTextChangedListener(r10);
        adminclose2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog12.dismiss();
            }
        });
        ImageView imageView = adminclose2;
        EditText editText4 = qty2;
        Dialog dialog13 = dialog12;
        final String str18 = str14;
        final String str19 = str15;
        final EditText editText5 = editText3;
        AnonymousClass10 r7 = new OnClickListener(this) {
            final /* synthetic */ Productist_ByCatogery_FromShops this$0;

            {
                this$0 = this$0;
            }

            public void onClick(View view) {
                String str = "";
                if (!this$0.db.getcartexist(str18, str19).equalsIgnoreCase(str)) {
                    this$0.db.addcart_exitupdate(editText5.getText().toString(), textView.getText().toString().replace(str13, str), str18);
                    Toasty.info(this$0.getApplicationContext(), "Cart Updated", 0).show();
                } else {
                    this$0.db.addcart(str19, str18, str3, str4, editText5.getText().toString(), textView.getText().toString().replace(str13, str), str5, str6, str7, str8, str9, str10, "0", str11, str12);
                }
                try {
                    this$0.mediaPlayer = MediaPlayer.create(this$0.getApplicationContext(), R.raw.addtocart);
                    this$0.mediaPlayer.setVolume(0.1f, 0.1f);
                    this$0.mediaPlayer.start();
                } catch (Exception e) {
                }
                dialog2.dismiss();
                this$0.refreshcart();
            }
        };
        buy4.setOnClickListener(r7);
        dialog13.show();
    }
}
