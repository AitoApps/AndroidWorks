package com.sanji_admin;

import adapter.Shoplist_Expired_ListAdapter;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener;
import data.Shoplist_Expired_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Shop_Expire_Controller extends AppCompatActivity implements OnDateSetListener {
    ImageView back;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    public String exdate = "";
    Typeface face;
    public List<Shoplist_Expired_FeedItem> feedItems;
    boolean flag = false;
    RelativeLayout footerview;
    ImageView heart;
    public int limit = 0;
    ListView list;
    public Shoplist_Expired_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    int position = 0;
    public String shopid = "";
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public class datextend extends AsyncTask<String, Void, String> {
        public datextend() {
        }
        public void onPreExecute() {
            Shop_Expire_Controller.pd.setMessage("Please wait...");
            Shop_Expire_Controller.pd.setCancelable(false);
            Shop_Expire_Controller.pd.show();
            Shop_Expire_Controller.timerDelayRemoveDialog(50000, Shop_Expire_Controller.pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("shopdateextend.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Shop_Expire_Controller.shopid);
                sb3.append(":%");
                sb3.append(Shop_Expire_Controller.exdate);
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
            if (Shop_Expire_Controller.pd != null || Shop_Expire_Controller.pd.isShowing()) {
                Shop_Expire_Controller.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(Shop_Expire_Controller.getApplicationContext(), "Date Extended", Toast.LENGTH_SHORT).show();
                    Shop_Expire_Controller.removeitem(Shop_Expire_Controller.position);
                    return;
                }
                Toasty.info(Shop_Expire_Controller.getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Shop_Expire_Controller.nointernet.setVisibility(View.GONE);
            Shop_Expire_Controller.nodata.setVisibility(View.GONE);
            Shop_Expire_Controller.list.setVisibility(View.GONE);
            Shop_Expire_Controller.heart.setVisibility(View.VISIBLE);
            Shop_Expire_Controller.limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getshoplist_Expr_admin.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Shop_Expire_Controller.limit);
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
                    Shop_Expire_Controller.feedItems.clear();
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 11;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Shoplist_Expired_FeedItem item = new Shoplist_Expired_FeedItem();
                        int m2 = m + 1;
                        item.setsn(got[m2]);
                        int m3 = m2 + 1;
                        item.setshopname(got[m3]);
                        int m4 = m3 + 1;
                        item.setownername(got[m4]);
                        int m5 = m4 + 1;
                        item.setmobile1(got[m5]);
                        int m6 = m5 + 1;
                        item.setmobile2(got[m6]);
                        int m7 = m6 + 1;
                        item.setplace(got[m7]);
                        int m8 = m7 + 1;
                        item.setlatitude(got[m8]);
                        int m9 = m8 + 1;
                        item.setlongtitude(got[m9]);
                        int m10 = m9 + 1;
                        item.setimgsig(got[m10]);
                        int m11 = m10 + 1;
                        item.setstatus(got[m11]);
                        m = m11 + 1;
                        item.setexpireddt(got[m]);
                        Shop_Expire_Controller.feedItems.add(item);
                    }
                    Shop_Expire_Controller.heart.setVisibility(View.GONE);
                    Shop_Expire_Controller.list.setVisibility(View.VISIBLE);
                    Shop_Expire_Controller.listAdapter.notifyDataSetChanged();
                    return;
                }
                Shop_Expire_Controller.nodata.setVisibility(View.VISIBLE);
                Shop_Expire_Controller.heart.setVisibility(View.GONE);
                Shop_Expire_Controller.footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }

    public class loadstatus1 extends AsyncTask<String, Void, String> {
        public loadstatus1() {
        }
        public void onPreExecute() {
            Shop_Expire_Controller.footerview.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getshoplist_Expr_admin.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Shop_Expire_Controller.limit);
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
                        Shoplist_Expired_FeedItem item = new Shoplist_Expired_FeedItem();
                        int m2 = m + 1;
                        item.setsn(got[m2]);
                        int m3 = m2 + 1;
                        item.setshopname(got[m3]);
                        int m4 = m3 + 1;
                        item.setownername(got[m4]);
                        int m5 = m4 + 1;
                        item.setmobile1(got[m5]);
                        int m6 = m5 + 1;
                        item.setmobile2(got[m6]);
                        int m7 = m6 + 1;
                        item.setplace(got[m7]);
                        int m8 = m7 + 1;
                        item.setlatitude(got[m8]);
                        int m9 = m8 + 1;
                        item.setlongtitude(got[m9]);
                        int m10 = m9 + 1;
                        item.setimgsig(got[m10]);
                        int m11 = m10 + 1;
                        item.setstatus(got[m11]);
                        m = m11 + 1;
                        item.setexpireddt(got[m]);
                        Shop_Expire_Controller.feedItems.add(item);
                    }
                    Shop_Expire_Controller.listAdapter.notifyDataSetChanged();
                    Shop_Expire_Controller.footerview.setVisibility(View.GONE);
                    return;
                }
                Shop_Expire_Controller.heart.setVisibility(View.GONE);
                Shop_Expire_Controller.footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_shop__expire__controller);
        heart = (ImageView) findViewById(R.id.heart);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Shop_Expire_Controller.onBackPressed();
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
        listAdapter = new Shoplist_Expired_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        list.setOnScrollListener(new OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount == totalItemCount - firstVisibleItem && Shop_Expire_Controller.flag) {
                    Shop_Expire_Controller.flag = false;
                    if (!Shop_Expire_Controller.cd.isConnectingToInternet()) {
                        Toasty.info(Shop_Expire_Controller.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    } else if (Shop_Expire_Controller.footerview.getVisibility() != 0) {
                        Shop_Expire_Controller.limit += 30;
                        new loadstatus1().execute(new String[0]);
                    }
                }
            }

            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                if (arg1 == 2) {
                    Shop_Expire_Controller.flag = true;
                }
            }
        });
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (Shop_Expire_Controller.cd.isConnectingToInternet()) {
                    Shop_Expire_Controller.nointernet.setVisibility(View.GONE);
                    Shop_Expire_Controller.limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                Shop_Expire_Controller.nointernet.setVisibility(View.VISIBLE);
                Toasty.info(Shop_Expire_Controller.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onResume() {
        super.onResume();
        try {
            if (cd.isConnectingToInternet()) {
                nointernet.setVisibility(View.GONE);
                limit = 0;
                new loadstatus().execute(new String[0]);
                return;
            }
            nointernet.setVisibility(View.VISIBLE);
            Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void removeitem(int position2) {
        try {
            feedItems.remove(position2);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void changeitem(int position2, String sn1, String shopname1, String ownername1, String mobile1, String mobile2, String place1, String latitude1, String longtitude1, String imgsig1, String status1, String expdate1) {
        try {
            feedItems.remove(position2);
            listAdapter.notifyDataSetChanged();
            Shoplist_Expired_FeedItem item = new Shoplist_Expired_FeedItem();
            item.setsn(sn1);
            item.setshopname(shopname1);
            item.setownername(ownername1);
            item.setmobile1(mobile1);
            item.setmobile2(mobile2);
            item.setplace(place1);
            item.setlatitude(latitude1);
            item.setlongtitude(longtitude1);
            item.setimgsig(imgsig1);
            item.setstatus(status1);
            item.setexpireddt(expdate1);
            feedItems.add(position2, item);
        } catch (Exception e) {
        }
    }

    public void setdate(int pos, String shopid1) {
        try {
            position = pos;
            shopid = shopid1;
            Calendar now = Calendar.getInstance();
            DatePickerDialog.newInstance(this, now.get(1), now.get(2), now.get(5)).show(getFragmentManager(), "Select End Date");
        } catch (Exception e) {
        }
    }

    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(year);
            sb.append("-");
            sb.append(monthOfYear + 1);
            sb.append("-");
            sb.append(dayOfMonth);
            exdate = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Are you sure want to update ");
            sb2.append(exdate);
            sb2.append(" this date ?");
            showalert_dateextend(sb2.toString());
        } catch (Exception e) {
        }
    }

    public void showalert_dateextend(String message) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (Shop_Expire_Controller.cd.isConnectingToInternet()) {
                    new datextend().execute(new String[0]);
                } else {
                    Toasty.info(Shop_Expire_Controller.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
