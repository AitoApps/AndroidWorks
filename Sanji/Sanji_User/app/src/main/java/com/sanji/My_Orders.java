package com.sanji;

import adapter.Order_History_listadapter;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;
import data.Order_History_Feed;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class My_Orders extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    public DatabaseHandler db;
    Typeface face;
    Typeface face1;

    public List<Order_History_Feed> feedItems;
    View footerView;
    ImageView heart;
    String imgid;
    String imgsig;

    public SwipeRefreshLayout layout;
    public int limit = 0;

    public Order_History_listadapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
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
                sb.append(My_Orders.imgid);
                sb.append("_1.jpg");
                viewHolder.bindImageSlide(sb.toString(), R.drawable.placeholder, R.drawable.placeholder);
            } else if (position == 1) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Temp.weblink);
                sb2.append(str);
                sb2.append(My_Orders.imgid);
                sb2.append("_2.jpg");
                viewHolder.bindImageSlide(sb2.toString(), R.drawable.placeholder, R.drawable.placeholder);
            } else if (position == 2) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.weblink);
                sb3.append(str);
                sb3.append(My_Orders.imgid);
                sb3.append("_3.jpg");
                viewHolder.bindImageSlide(sb3.toString(), R.drawable.placeholder, R.drawable.placeholder);
            }
        }
    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            My_Orders.nodata.setVisibility(View.GONE);
            My_Orders.recylerview.setVisibility(View.GONE);
            My_Orders.heart.setVisibility(View.VISIBLE);
            My_Orders.limit = 0;
        }
        public String doInBackground(String... arg0) {
            String str = "UTF-8";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getmyorder_user.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(My_Orders.udb.get_userid());
                sb3.append(":%");
                sb3.append(My_Orders.limit);
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
            String str = "Asia/Calcutta";
            try {
                Log.w("POlice", result);
                if (result.contains("%:ok")) {
                    try {
                        My_Orders.feedItems.clear();
                        String[] got = result.split("%:");
                        int k = (got.length - 1) / 10;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Order_History_Feed item = new Order_History_Feed();
                            int m2 = m + 1;
                            try {
                                Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone(str));
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                sdf.setTimeZone(TimeZone.getTimeZone(str));
                                c1.setTime(sdf.parse(got[m2]));
                                item.setorderdate(My_Orders.getFormattedDate(c1.getTimeInMillis()));
                            } catch (Exception e) {
                                item.setorderdate(got[m2]);
                            }
                            int m3 = m2 + 1;
                            item.setgroupid(got[m3]);
                            int m4 = m3 + 1;
                            item.setstatus(got[m4]);
                            int m5 = m4 + 1;
                            item.settotalamount(got[m5]);
                            int m6 = m5 + 1;
                            item.setdlcharge(got[m6]);
                            int m7 = m6 + 1;
                            item.setsmname(got[m7]);
                            int m8 = m7 + 1;
                            item.setsmmobile(got[m8]);
                            int m9 = m8 + 1;
                            item.setShopid(got[m9]);
                            int m10 = m9 + 1;
                            item.setShopname(got[m10]);
                            m = m10 + 1;
                            item.setitemdetails(got[m]);
                            My_Orders.feedItems.add(item);
                        }
                    } catch (Exception e2) {
                    }
                    My_Orders.nodata.setVisibility(View.GONE);
                    My_Orders.heart.setVisibility(View.GONE);
                    My_Orders.recylerview.setVisibility(View.VISIBLE);
                    My_Orders.listAdapter.notifyDataSetChanged();
                    return;
                }
                My_Orders.nodata.setVisibility(View.VISIBLE);
                My_Orders.recylerview.setVisibility(View.GONE);
                My_Orders.heart.setVisibility(View.GONE);
            } catch (Exception e3) {
            }
        }
    }

    public class loadstatus1 extends AsyncTask<String, Void, String> {
        public loadstatus1() {
        }
        public void onPreExecute() {
            My_Orders.limit += 30;
            My_Orders.footerView.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            String str = "UTF-8";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getmyorder_user.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(My_Orders.udb.get_userid());
                sb3.append(":%");
                sb3.append(My_Orders.limit);
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
            String str = "Asia/Calcutta";
            try {
                if (result.contains("%:ok")) {
                    try {
                        String[] got = result.split("%:");
                        int k = (got.length - 1) / 10;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Order_History_Feed item = new Order_History_Feed();
                            int m2 = m + 1;
                            try {
                                Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone(str));
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                sdf.setTimeZone(TimeZone.getTimeZone(str));
                                c1.setTime(sdf.parse(got[m2]));
                                item.setorderdate(My_Orders.getFormattedDate(c1.getTimeInMillis()));
                            } catch (Exception e) {
                                item.setorderdate(got[m2]);
                            }
                            int m3 = m2 + 1;
                            item.setgroupid(got[m3]);
                            int m4 = m3 + 1;
                            item.setstatus(got[m4]);
                            int m5 = m4 + 1;
                            item.settotalamount(got[m5]);
                            int m6 = m5 + 1;
                            item.setdlcharge(got[m6]);
                            int m7 = m6 + 1;
                            item.setsmname(got[m7]);
                            int m8 = m7 + 1;
                            item.setsmmobile(got[m8]);
                            int m9 = m8 + 1;
                            item.setShopid(got[m9]);
                            int m10 = m9 + 1;
                            item.setShopname(got[m10]);
                            m = m10 + 1;
                            item.setitemdetails(got[m]);
                            My_Orders.feedItems.add(item);
                        }
                    } catch (Exception e2) {
                    }
                    My_Orders.footerView.setVisibility(View.INVISIBLE)
                    My_Orders.listAdapter.notifyDataSetChanged();
                    return;
                }
                My_Orders.footerView.setVisibility(View.INVISIBLE)
                My_Orders.heart.setVisibility(View.GONE);
            } catch (Exception e3) {
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
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getmyorder_user.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(My_Orders.udb.get_userid());
                sb3.append(":%");
                sb3.append(My_Orders.limit);
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
            String str = "Asia/Calcutta";
            try {
                My_Orders.layout.setRefreshing(false);
                if (result.contains("%:ok")) {
                    try {
                        My_Orders.feedItems.clear();
                        String[] got = result.split("%:");
                        int k = (got.length - 1) / 10;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Order_History_Feed item = new Order_History_Feed();
                            int m2 = m + 1;
                            try {
                                Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone(str));
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                sdf.setTimeZone(TimeZone.getTimeZone(str));
                                c1.setTime(sdf.parse(got[m2]));
                                item.setorderdate(My_Orders.getFormattedDate(c1.getTimeInMillis()));
                            } catch (Exception e) {
                                item.setorderdate(got[m2]);
                            }
                            int m3 = m2 + 1;
                            item.setgroupid(got[m3]);
                            int m4 = m3 + 1;
                            item.setstatus(got[m4]);
                            int m5 = m4 + 1;
                            item.settotalamount(got[m5]);
                            int m6 = m5 + 1;
                            item.setdlcharge(got[m6]);
                            int m7 = m6 + 1;
                            item.setsmname(got[m7]);
                            int m8 = m7 + 1;
                            item.setsmmobile(got[m8]);
                            int m9 = m8 + 1;
                            item.setShopid(got[m9]);
                            int m10 = m9 + 1;
                            item.setShopname(got[m10]);
                            m = m10 + 1;
                            item.setitemdetails(got[m]);
                            My_Orders.feedItems.add(item);
                        }
                    } catch (Exception e2) {
                    }
                    My_Orders.listAdapter.notifyDataSetChanged();
                    return;
                }
                My_Orders.heart.setVisibility(View.GONE);
            } catch (Exception e3) {
            }
        }
    }

    public My_Orders() {
        String str = "";
        imgid = str;
        imgsig = str;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_my__orders);
        try {
            back = (ImageView) findViewById(R.id.back);
            face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
            face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
            db = new DatabaseHandler(this);
            cd = new ConnectionDetecter(this);
            udb = new UserDatabaseHandler(this);
            pd = new ProgressDialog(this);
            nodata = (ImageView) findViewById(R.id.nodata);
            text = (TextView) findViewById(R.id.text);
            recylerview = (HeaderAndFooterRecyclerView) findViewById(R.id.recylerview);
            heart = (ImageView) findViewById(R.id.heart);
            nointernet = (ImageView) findViewById(R.id.nointernet);
            layout = (SwipeRefreshLayout) findViewById(R.id.layout);
            layout.setEnabled(true);
            feedItems = new ArrayList();
            listAdapter = new Order_History_listadapter(this, feedItems);
            recylerview.setLayoutManager(new GridLayoutManager(this, 1));
            recylerview.setAdapter(listAdapter);
            footerView = LayoutInflater.from(this).inflate(R.layout.footerview, recylerview.getFooterContainer(), false);
            recylerview.addFooterView(footerView);
            footerView.setVisibility(View.INVISIBLE)
            text.setText("എന്റെ ഓര്‍ഡറുകള്‍ ");
            text.setTypeface(face1);
            back.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    My_Orders.onBackPressed();
                }
            });
            layout.setOnRefreshListener(new OnRefreshListener() {
                public void onRefresh() {
                    My_Orders.layout.setRefreshing(true);
                    My_Orders.nointernet.setVisibility(View.GONE);
                    My_Orders my_Orders = My_Orders.this;
                    my_Orders.limit = 0;
                    new loadstatus2().execute(new String[0]);
                }
            });
            nointernet.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (My_Orders.cd.isConnectingToInternet()) {
                        My_Orders.nointernet.setVisibility(View.GONE);
                        My_Orders my_Orders = My_Orders.this;
                        my_Orders.limit = 0;
                        new loadstatus().execute(new String[0]);
                        return;
                    }
                    My_Orders.nointernet.setVisibility(View.VISIBLE);
                    Toasty.info(My_Orders.getApplicationContext(), Temp.nointernet, 0).show();
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
        } catch (Exception e) {
        }
    }

    public void refresh() {
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            limit = 0;
            new loadstatus().execute(new String[0]);
            return;
        }
        nointernet.setVisibility(View.VISIBLE);
        Toasty.info(getApplicationContext(), Temp.nointernet, 0).show();
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
    public void call(String mob) {
        String str = "android.permission.CALL_PHONE";
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), str) != 0) {
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

    public void showalertcall(String message, final String mob) {
        Builder builder = new Builder(this);
        StringBuilder sb = new StringBuilder();
        sb.append(mob);
        sb.append(" ");
        sb.append(message);
        builder.setMessage(sb.toString()).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                My_Orders.call(mob);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showcall(final String mobile1, final String mobile2) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.callalert);
        RelativeLayout lytcall1 = (RelativeLayout) dialog.findViewById(R.id.lytcall1);
        RelativeLayout lytcall2 = (RelativeLayout) dialog.findViewById(R.id.lytcall2);
        TextView txtcall2 = (TextView) dialog.findViewById(R.id.txtcall2);
        ((TextView) dialog.findViewById(R.id.txtcall1)).setTypeface(face);
        txtcall2.setTypeface(face);
        if (mobile2.equalsIgnoreCase("")) {
            lytcall2.setVisibility(View.GONE);
        } else {
            lytcall2.setVisibility(View.VISIBLE);
        }
        lytcall1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                My_Orders.showalertcall("എന്ന നമ്പറിലേക്ക് വിളിക്കാം അല്ലെ ", mobile1);
                dialog.dismiss();
            }
        });
        lytcall2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                My_Orders.showalertcall("എന്ന നമ്പറിലേക്ക് വിളിക്കാം അല്ലെ ", mobile2);
                dialog.dismiss();
            }
        });
        dialog.show();
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

    public void showqrcode(String vouchercode) {
        try {
            Dialog dialog3 = new Dialog(this);
            dialog3.requestWindowFeature(1);
            dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog3.setContentView(R.layout.qrcodeview);
            try {
                ((ImageView) dialog3.findViewById(R.id.qrcode)).setImageBitmap(new BarcodeEncoder().encodeBitmap(vouchercode, BarcodeFormat.QR_CODE, 240, 240));
            } catch (Exception e) {
            }
            dialog3.show();
        } catch (Exception e2) {
        }
    }

    public void photoview(final String imgsigs, String imgids) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.photoviews);
        imgid = imgids;
        imgsig = imgsigs;
        Slider.init(new ImageLoadingService() {
            public void loadImage(String url, ImageView imageView) {
                Glide.with(My_Orders.getApplicationContext()).load(url).into(imageView);
            }

            public void loadImage(int resource, ImageView imageView) {
                Glide.with(My_Orders.getApplicationContext()).load(Integer.valueOf(resource)).into(imageView);
            }

            public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(placeHolder);
                requestOptions.error(errorDrawable);
                requestOptions.signature(new ObjectKey(imgsigs));
                Glide.with(My_Orders.getApplicationContext()).load(url).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
            }
        });
        ((Slider) dialog.findViewById(R.id.banner_slider1)).setAdapter(new MainSliderAdapter());
        dialog.show();
    }

    public String getFormattedDate(long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);
        Calendar now = Calendar.getInstance();
        String str = "h:mm a";
        String str2 = "MMM d h:mm a";
        String str3 = "h:mm a";
        if (now.get(5) == smsTime.get(5)) {
            StringBuilder sb = new StringBuilder();
            sb.append(DateFormat.format(str3, smsTime));
            sb.append("");
            return sb.toString();
        } else if (now.get(5) - smsTime.get(5) == 1) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Yesterday ");
            sb2.append(DateFormat.format(str3, smsTime));
            return sb2.toString();
        } else if (now.get(1) == smsTime.get(1)) {
            return DateFormat.format("MMM d h:mm a", smsTime).toString();
        } else {
            return DateFormat.format("MMM dd yyyy h:mm a", smsTime).toString();
        }
    }
}
