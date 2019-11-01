package com.sanji;

import adapter.Product_List_ListAdapter;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;
import data.Product_List_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class Product_List extends AppCompatActivity {
    public static String searchstring = "";
    public static String searchtype = "1";
    ImageView back;
    TextView cartcount;
    ConnectionDetecter cd;
    RelativeLayout content;
    public DatabaseHandler db;
    public Dialog dialog;
    Typeface face;
    Typeface face1;

    public List<Product_List_FeedItem> feedItems;
    View footerView;
    ImageView heart;
    String imgid;
    String imgsig;

    public SwipeRefreshLayout layout;
    public int limit = 0;

    public Product_List_ListAdapter listAdapter;
    RelativeLayout lytcart;
    private MediaPlayer mediaPlayer;
    ImageView nodata;
    ImageView nointernet;

    public List<Product_List_FeedItem> normallist;
    ProgressDialog pd;

    public List<Product_List_FeedItem> premimumlist;
    public HeaderAndFooterRecyclerView recylerview;
    TextView text;
    public UserDatabaseHandler udb;

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
                sb.append(Product_List.imgid);
                sb.append("_1.jpg");
                viewHolder.bindImageSlide(sb.toString(), R.drawable.placeholder, R.drawable.placeholder);
            } else if (position == 1) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Temp.weblink);
                sb2.append(str);
                sb2.append(Product_List.imgid);
                sb2.append("_2.jpg");
                viewHolder.bindImageSlide(sb2.toString(), R.drawable.placeholder, R.drawable.placeholder);
            } else if (position == 2) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.weblink);
                sb3.append(str);
                sb3.append(Product_List.imgid);
                sb3.append("_3.jpg");
                viewHolder.bindImageSlide(sb3.toString(), R.drawable.placeholder, R.drawable.placeholder);
            }
        }
    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Product_List.nodata.setVisibility(View.GONE);
            Product_List.recylerview.setVisibility(View.GONE);
            Product_List.heart.setVisibility(View.VISIBLE);
            Product_List.limit = 0;
        }
        public String doInBackground(String... arg0) {
            String str = "UTF-8";
            String str2 = ":%";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getproductlist_user.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Product_List.db.getproductcat());
                sb3.append(str2);
                sb3.append(Product_List.udb.getselecttownid());
                sb3.append(str2);
                sb3.append(Product_List.limit);
                sb3.append(str2);
                sb3.append(Product_List.searchtype);
                sb3.append(str2);
                sb3.append(Product_List.searchstring);
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
                if (result.contains(":%ok")) {
                    try {
                        Product_List.feedItems.clear();
                        Product_List.premimumlist.clear();
                        Product_List.normallist.clear();
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 19;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Product_List_FeedItem item = new Product_List_FeedItem();
                            int m2 = m + 1;
                            item.settype(got[m2]);
                            int type = Integer.parseInt(got[m2]);
                            int m3 = m2 + 1;
                            item.setsn(got[m3]);
                            int m4 = m3 + 1;
                            item.setdeli_type(got[m4]);
                            int m5 = m4 + 1;
                            item.setproductcat(got[m5]);
                            int m6 = m5 + 1;
                            item.setshopid(got[m6]);
                            int m7 = m6 + 1;
                            item.setitemname(got[m7]);
                            int m8 = m7 + 1;
                            item.setprice(got[m8]);
                            int m9 = m8 + 1;
                            item.setogprice(got[m9]);
                            int m10 = m9 + 1;
                            item.setmaxorder(got[m10]);
                            int m11 = m10 + 1;
                            item.setdlcharge(got[m11]);
                            int m12 = m11 + 1;
                            item.setddsic(got[m12]);
                            int m13 = m12 + 1;
                            item.setitemdiscription(got[m13]);
                            int m14 = m13 + 1;
                            item.setimgsig1(got[m14]);
                            int m15 = m14 + 1;
                            item.setshopname(got[m15]);
                            int m16 = m15 + 1;
                            item.setshopplace(got[m16]);
                            int m17 = m16 + 1;
                            item.setshopmobile(got[m17]);
                            int m18 = m17 + 1;
                            item.setshoptime(got[m18]);
                            int m19 = m18 + 1;
                            item.setlocation(got[m19]);
                            m = m19 + 1;
                            if (got[m].equalsIgnoreCase("1")) {
                                Product_List.premimumlist.add(item);
                            } else {
                                Product_List.normallist.add(item);
                            }
                            if (type != 1) {
                                if (type == 2) {
                                    Context applicationContext = Product_List.getApplicationContext();
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(Product_List.udb.getselecttownname());
                                    sb.append(" എന്ന ടൗണില്‍ ഓഫറുകള്‍ ലഭ്യമല്ലാത്തതിനാല്‍ മറ്റു ടൗണുകളിലെ ഓഫറുകളാണ് ഇവിടെ കാണിച്ചിരിക്കുന്നത്‌ ");
                                    Toasty.info(applicationContext, sb.toString(), 0).show();
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                    Collections.shuffle(Product_List.premimumlist);
                    Collections.shuffle(Product_List.normallist);
                    if (Product_List.premimumlist.size() > 0 || Product_List.normallist.size() > 0) {
                        Product_List.feedItems.addAll(Product_List.premimumlist);
                        Product_List.feedItems.addAll(Product_List.normallist);
                    }
                    Product_List.nodata.setVisibility(View.GONE);
                    Product_List.heart.setVisibility(View.GONE);
                    Product_List.recylerview.setVisibility(View.VISIBLE);
                    Product_List.listAdapter.notifyDataSetChanged();
                    return;
                }
                Product_List.nodata.setVisibility(View.VISIBLE);
                Product_List.recylerview.setVisibility(View.GONE);
                Product_List.heart.setVisibility(View.GONE);
            } catch (Exception a) {
                Toasty.info(Product_List.getApplicationContext(), Log.getStackTraceString(a), 1).show();
            }
        }
    }

    public class loadstatus1 extends AsyncTask<String, Void, String> {
        public loadstatus1() {
        }
        public void onPreExecute() {
            Product_List.limit += 30;
            Product_List.footerView.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            String str = "UTF-8";
            String str2 = ":%";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getproductlist_user.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Product_List.db.getproductcat());
                sb3.append(str2);
                sb3.append(Product_List.udb.getselecttownid());
                sb3.append(str2);
                sb3.append(Product_List.limit);
                sb3.append(str2);
                sb3.append(Product_List.searchtype);
                sb3.append(str2);
                sb3.append(Product_List.searchstring);
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
                if (result.contains(":%ok")) {
                    try {
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 19;
                        int m = -1;
                        Product_List.normallist.clear();
                        Product_List.premimumlist.clear();
                        for (int i = 1; i <= k; i++) {
                            Product_List_FeedItem item = new Product_List_FeedItem();
                            int m2 = m + 1;
                            item.settype(got[m2]);
                            int m3 = m2 + 1;
                            item.setsn(got[m3]);
                            int m4 = m3 + 1;
                            item.setdeli_type(got[m4]);
                            int m5 = m4 + 1;
                            item.setproductcat(got[m5]);
                            int m6 = m5 + 1;
                            item.setshopid(got[m6]);
                            int m7 = m6 + 1;
                            item.setitemname(got[m7]);
                            int m8 = m7 + 1;
                            item.setprice(got[m8]);
                            int m9 = m8 + 1;
                            item.setogprice(got[m9]);
                            int m10 = m9 + 1;
                            item.setmaxorder(got[m10]);
                            int m11 = m10 + 1;
                            item.setdlcharge(got[m11]);
                            int m12 = m11 + 1;
                            item.setddsic(got[m12]);
                            int m13 = m12 + 1;
                            item.setitemdiscription(got[m13]);
                            int m14 = m13 + 1;
                            item.setimgsig1(got[m14]);
                            int m15 = m14 + 1;
                            item.setshopname(got[m15]);
                            int m16 = m15 + 1;
                            item.setshopplace(got[m16]);
                            int m17 = m16 + 1;
                            item.setshopmobile(got[m17]);
                            int m18 = m17 + 1;
                            item.setshoptime(got[m18]);
                            int m19 = m18 + 1;
                            item.setlocation(got[m19]);
                            m = m19 + 1;
                            if (got[m].equalsIgnoreCase("1")) {
                                Product_List.premimumlist.add(item);
                            } else {
                                Product_List.normallist.add(item);
                            }
                        }
                    } catch (Exception e) {
                    }
                    Collections.shuffle(Product_List.normallist);
                    if (Product_List.normallist.size() > 0 || Product_List.premimumlist.size() > 0) {
                        Product_List.feedItems.addAll(Product_List.premimumlist);
                        Product_List.feedItems.addAll(Product_List.normallist);
                    }
                    Product_List.footerView.setVisibility(View.INVISIBLE)
                    Product_List.listAdapter.notifyDataSetChanged();
                    return;
                }
                Product_List.footerView.setVisibility(View.INVISIBLE)
                Product_List.heart.setVisibility(View.GONE);
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
                sb.append("getproductlist_user.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Product_List.db.getproductcat());
                sb3.append(str2);
                sb3.append(Product_List.udb.getselecttownid());
                sb3.append(str2);
                sb3.append(Product_List.limit);
                sb3.append(str2);
                sb3.append(Product_List.searchtype);
                sb3.append(str2);
                sb3.append(Product_List.searchstring);
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
                Product_List.layout.setRefreshing(false);
                if (result.contains(":%ok")) {
                    try {
                        Product_List.feedItems.clear();
                        Product_List.premimumlist.clear();
                        Product_List.normallist.clear();
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 19;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Product_List_FeedItem item = new Product_List_FeedItem();
                            int m2 = m + 1;
                            item.settype(got[m2]);
                            int m3 = m2 + 1;
                            item.setsn(got[m3]);
                            int m4 = m3 + 1;
                            item.setdeli_type(got[m4]);
                            int m5 = m4 + 1;
                            item.setproductcat(got[m5]);
                            int m6 = m5 + 1;
                            item.setshopid(got[m6]);
                            int m7 = m6 + 1;
                            item.setitemname(got[m7]);
                            int m8 = m7 + 1;
                            item.setprice(got[m8]);
                            int m9 = m8 + 1;
                            item.setogprice(got[m9]);
                            int m10 = m9 + 1;
                            item.setmaxorder(got[m10]);
                            int m11 = m10 + 1;
                            item.setdlcharge(got[m11]);
                            int m12 = m11 + 1;
                            item.setddsic(got[m12]);
                            int m13 = m12 + 1;
                            item.setitemdiscription(got[m13]);
                            int m14 = m13 + 1;
                            item.setimgsig1(got[m14]);
                            int m15 = m14 + 1;
                            item.setshopname(got[m15]);
                            int m16 = m15 + 1;
                            item.setshopplace(got[m16]);
                            int m17 = m16 + 1;
                            item.setshopmobile(got[m17]);
                            int m18 = m17 + 1;
                            item.setshoptime(got[m18]);
                            int m19 = m18 + 1;
                            item.setlocation(got[m19]);
                            m = m19 + 1;
                            if (got[m].equalsIgnoreCase("1")) {
                                Product_List.premimumlist.add(item);
                            } else {
                                Product_List.normallist.add(item);
                            }
                        }
                    } catch (Exception e) {
                    }
                    Collections.shuffle(Product_List.premimumlist);
                    Collections.shuffle(Product_List.normallist);
                    if (Product_List.premimumlist.size() > 0 || Product_List.normallist.size() > 0) {
                        Product_List.feedItems.addAll(Product_List.premimumlist);
                        Product_List.feedItems.addAll(Product_List.normallist);
                    }
                    Product_List.listAdapter.notifyDataSetChanged();
                    return;
                }
                Product_List.heart.setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }

    public Product_List() {
        String str = "";
        imgid = str;
        imgsig = str;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_product__list);
        try {
            face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
            face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
            db = new DatabaseHandler(this);
            cd = new ConnectionDetecter(this);
            udb = new UserDatabaseHandler(this);
            pd = new ProgressDialog(this);
            nodata = (ImageView) findViewById(R.id.nodata);
            lytcart = (RelativeLayout) findViewById(R.id.lytcart);
            cartcount = (TextView) findViewById(R.id.cartcount);
            text = (TextView) findViewById(R.id.text);
            content = (RelativeLayout) findViewById(R.id.content);
            recylerview = (HeaderAndFooterRecyclerView) findViewById(R.id.recylerview);
            heart = (ImageView) findViewById(R.id.heart);
            back = (ImageView) findViewById(R.id.back);
            nointernet = (ImageView) findViewById(R.id.nointernet);
            layout = (SwipeRefreshLayout) findViewById(R.id.layout);
            layout.setEnabled(true);
            feedItems = new ArrayList();
            premimumlist = new ArrayList();
            normallist = new ArrayList();
            listAdapter = new Product_List_ListAdapter(this, feedItems);
            recylerview.setLayoutManager(new GridLayoutManager(this, 1));
            recylerview.setAdapter(listAdapter);
            footerView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.footerview, recylerview.getFooterContainer(), false);
            ((TextView) footerView.findViewById(R.id.next)).setTypeface(face);
            recylerview.addFooterView(footerView);
            footerView.setVisibility(View.INVISIBLE)
            text.setText(Temp.catogeryname);
            text.setTypeface(face1);
            layout.setOnRefreshListener(new OnRefreshListener() {
                public void onRefresh() {
                    Product_List.layout.setRefreshing(true);
                    Product_List.nointernet.setVisibility(View.GONE);
                    Product_List product_List = Product_List.this;
                    product_List.limit = 0;
                    new loadstatus2().execute(new String[0]);
                }
            });
            back.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Product_List.onBackPressed();
                }
            });
            nointernet.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (Product_List.cd.isConnectingToInternet()) {
                        Product_List.nointernet.setVisibility(View.GONE);
                        Product_List product_List = Product_List.this;
                        product_List.limit = 0;
                        new loadstatus().execute(new String[0]);
                        return;
                    }
                    Product_List.nointernet.setVisibility(View.VISIBLE);
                    Toasty.info(Product_List.getApplicationContext(), Temp.nointernet, 0).show();
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
            lytcart.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Product_List.startActivity(new Intent(Product_List.getApplicationContext(), Cart.class));
                }
            });
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
            Toasty.info(getApplicationContext(), Temp.nointernet, 0).show();
        }
    }

    public void showdiscription(String txtdiscription, String deli_type, String dlcharge, String ddsic) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.home_productdiscription);
        TextView discription = (TextView) dialog.findViewById(R.id.discription);
        RelativeLayout lytdeliverytype = (RelativeLayout) dialog.findViewById(R.id.lytdeliverytype);
        TextView deliverytype = (TextView) dialog.findViewById(R.id.deliverytype);
        TextView txtdeliverycharge = (TextView) dialog.findViewById(R.id.txtdeliverycharge);
        TextView deliverycharge = (TextView) dialog.findViewById(R.id.deliverycharge);
        TextView deliverydic = (TextView) dialog.findViewById(R.id.deliverydic);
        discription.setTypeface(face1);
        deliverytype.setTypeface(face);
        deliverycharge.setTypeface(face);
        deliverydic.setTypeface(face);
        txtdeliverycharge.setTypeface(face);
        discription.setText(fromHtml(txtdiscription));
        String rupee = getResources().getString(R.string.Rs);
        if (deli_type.equalsIgnoreCase("2")) {
            lytdeliverytype.setVisibility(View.VISIBLE);
            String str = "";
            if (dlcharge.equalsIgnoreCase(str) || dlcharge.equalsIgnoreCase("0")) {
                deliverycharge.setVisibility(View.INVISIBLE)
                txtdeliverycharge.setVisibility(View.VISIBLE);
                txtdeliverycharge.setText("No Delivery Charge");
            } else {
                deliverycharge.setVisibility(View.VISIBLE);
                txtdeliverycharge.setVisibility(View.VISIBLE);
                txtdeliverycharge.setText("Delivery Charge : ");
                StringBuilder sb = new StringBuilder();
                sb.append(rupee);
                sb.append(dlcharge);
                deliverycharge.setText(sb.toString());
            }
            if (ddsic.equalsIgnoreCase(str)) {
                deliverydic.setVisibility(View.GONE);
            } else {
                deliverydic.setVisibility(View.VISIBLE);
                deliverydic.setText(ddsic);
            }
        } else {
            lytdeliverytype.setVisibility(View.GONE);
        }
        dialog.show();
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
                Glide.with(Product_List.getApplicationContext()).load(url).into(imageView);
            }

            public void loadImage(int resource, ImageView imageView) {
                Glide.with(Product_List.getApplicationContext()).load(Integer.valueOf(resource)).into(imageView);
            }

            public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(placeHolder);
                requestOptions.error(errorDrawable);
                requestOptions.signature(new ObjectKey(imgsigs));
                Glide.with(Product_List.getApplicationContext()).load(url).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
            }
        });
        ((Slider) dialog.findViewById(R.id.banner_slider1)).setAdapter(new MainSliderAdapter());
        dialog.show();
    }
    public void onResume() {
        super.onResume();
        try {
            refreshcart();
        } catch (Exception e) {
        }
    }
    public void call(String mob) {
        String str = "android.permission.CALL_PHONE";
        try {
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                ActivityCompat.requestPermissions(this, new String[]{str}, 1);
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
    }
    public void web(String web) {
        Intent pagIntent = new Intent("android.intent.action.VIEW");
        pagIntent.setData(Uri.parse(web));
        try {
            startActivity(pagIntent);
        } catch (Exception e) {
        }
    }
    public void download(String link) {
        String str = "android.intent.action.VIEW";
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("market://details?id=");
            sb.append(link);
            startActivity(new Intent(str, Uri.parse(sb.toString())));
        } catch (ActivityNotFoundException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("https://play.google.com/store/apps/details?id=");
            sb2.append(link);
            startActivity(new Intent(str, Uri.parse(sb2.toString())));
        }
    }

    public void showalertcall(String message, final String mob) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Product_List.call(mob);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showalertweb(String message, final String link) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Product_List.web(link);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showalertapp(String message, final String link) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Product_List.download(link);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public static Spanned fromHtml(String html) {
        if (VERSION.SDK_INT >= 24) {
            return Html.fromHtml(html, 0);
        }
        return Html.fromHtml(html);
    }

    public void showmap(String gps, String shopname) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("http://maps.google.com/maps?q=loc:");
            sb.append(gps);
            sb.append(" (");
            sb.append(shopname);
            sb.append(")");
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(sb.toString()));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        } catch (Exception e) {
            Toasty.error(getApplicationContext(), "ദയവായി താങ്കളുടെ ഫോണില്‍ ഗൂഗിള്‍ മാപ്പ് ഇന്‍സ്റ്റാള്‍ ചെയ്യുക ", 0).show();
        }
    }

    public void showaddcart(String cart_shopid1, String cart_productid1, String cart_productname1, String cart_price1, String cart_qty1, String cart_totalprice1, String cart_imgsig1, String cart_maxqty1) {
        try {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.addtocart);
            mediaPlayer.setVolume(0.1f, 0.1f);
            mediaPlayer.start();
        } catch (Exception e) {
        }
        refreshcart();
    }

    public void refreshcart() {
        try {
            long cartcounts = db.getcartcount();
            TextView textView = cartcount;
            StringBuilder sb = new StringBuilder();
            sb.append(cartcounts);
            sb.append("");
            textView.setText(sb.toString());
            lytcart.startAnimation(AnimationUtils.loadAnimation(this, R.anim.expand_in));
        } catch (Exception e) {
        }
    }
}
