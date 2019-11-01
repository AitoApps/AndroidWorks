package com.sanji_admin;

import adapter.PendingPayment_ListAdapter;
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
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import data.Pendingpayment_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Pending_Payments extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    Dialog dialog1;
    Typeface face;
    Typeface face1;
    public List<Pendingpayment_FeedItem> feedItems;
    boolean flag = false;
    RelativeLayout footerview;
    ImageView heart;
    public int limit = 0;
    ListView list;
    public PendingPayment_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    int pos = 0;
    String t_amount = "";
    String t_amount1 = "";
    String t_shopid = "";
    String t_shopmobile = "";
    String t_shopname = "";
    String t_sn = "";
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Pending_Payments.nointernet.setVisibility(View.GONE);
            Pending_Payments.nodata.setVisibility(View.GONE);
            Pending_Payments.list.setVisibility(View.GONE);
            Pending_Payments.heart.setVisibility(View.VISIBLE);
            Pending_Payments.limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getpendingpayments.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Pending_Payments.limit);
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
                    Pending_Payments.feedItems.clear();
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 5;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Pendingpayment_FeedItem item = new Pendingpayment_FeedItem();
                        int m2 = m + 1;
                        item.setsn(got[m2]);
                        int m3 = m2 + 1;
                        item.setshopid(got[m3]);
                        int m4 = m3 + 1;
                        item.setamount(got[m4]);
                        int m5 = m4 + 1;
                        item.setshopname(got[m5]);
                        m = m5 + 1;
                        item.setshopmobile(got[m]);
                        Pending_Payments.feedItems.add(item);
                    }
                    Pending_Payments.heart.setVisibility(View.GONE);
                    Pending_Payments.list.setVisibility(View.VISIBLE);
                    Pending_Payments.listAdapter.notifyDataSetChanged();
                    return;
                }
                Pending_Payments.nodata.setVisibility(View.VISIBLE);
                Pending_Payments.heart.setVisibility(View.GONE);
                Pending_Payments.footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }

    public class loadstatus1 extends AsyncTask<String, Void, String> {
        public loadstatus1() {
        }
        public void onPreExecute() {
            Pending_Payments.footerview.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getpendingpayments.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Pending_Payments.limit);
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
                    int k = (got.length - 1) / 5;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Pendingpayment_FeedItem item = new Pendingpayment_FeedItem();
                        int m2 = m + 1;
                        item.setsn(got[m2]);
                        int m3 = m2 + 1;
                        item.setshopid(got[m3]);
                        int m4 = m3 + 1;
                        item.setamount(got[m4]);
                        int m5 = m4 + 1;
                        item.setshopname(got[m5]);
                        m = m5 + 1;
                        item.setshopmobile(got[m]);
                        Pending_Payments.feedItems.add(item);
                    }
                    Pending_Payments.listAdapter.notifyDataSetChanged();
                    Pending_Payments.footerview.setVisibility(View.GONE);
                    return;
                }
                Pending_Payments.heart.setVisibility(View.GONE);
                Pending_Payments.footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }

    public class updatepayment extends AsyncTask<String, Void, String> {
        public updatepayment() {
        }
        public void onPreExecute() {
            Pending_Payments.pd.setMessage("Please wait...");
            Pending_Payments.pd.setCancelable(false);
            Pending_Payments.pd.show();
            Pending_Payments.timerDelayRemoveDialog(50000, Pending_Payments.pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("update_payment.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Pending_Payments.t_shopid);
                sb3.append(":%");
                sb3.append(Pending_Payments.t_amount);
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
            if (Pending_Payments.pd != null || Pending_Payments.pd.isShowing()) {
                Pending_Payments.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(Pending_Payments.getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Pending_Payments.changeitem();
                    return;
                }
                Toasty.info(Pending_Payments.getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_pending__payments);
        heart = (ImageView) findViewById(R.id.heart);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Pending_Payments.onBackPressed();
            }
        });
        list = (ListView) findViewById(R.id.list);
        nodata = (ImageView) findViewById(R.id.nodata);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        footerview = (RelativeLayout) getLayoutInflater().inflate(R.layout.footerview, null);
        list.addFooterView(footerview);
        footerview.setVisibility(View.GONE);
        text = (TextView) findViewById(R.id.text);
        face = Typeface.createFromAsset(getAssets(), "font/Rachana.ttf");
        face1 = Typeface.createFromAsset(getAssets(), "font/heading.otf");
        text.setTypeface(face1);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        feedItems = new ArrayList();
        listAdapter = new PendingPayment_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        list.setOnScrollListener(new OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount == totalItemCount - firstVisibleItem && Pending_Payments.flag) {
                    Pending_Payments.flag = false;
                    if (!Pending_Payments.cd.isConnectingToInternet()) {
                        Toasty.info(Pending_Payments.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    } else if (Pending_Payments.footerview.getVisibility() != 0) {
                        Pending_Payments.limit += 30;
                        new loadstatus1().execute(new String[0]);
                    }
                }
            }

            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                if (arg1 == 2) {
                    Pending_Payments.flag = true;
                }
            }
        });
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (Pending_Payments.cd.isConnectingToInternet()) {
                    Pending_Payments.nointernet.setVisibility(View.GONE);
                    Pending_Payments.limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                Pending_Payments.nointernet.setVisibility(View.VISIBLE);
                Toasty.info(Pending_Payments.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
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

    public void showpayamount(int posi, String shopid, String sn1, String amount1, String shopname1, String shopmobile1) {
        dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.custom_payamount);
        dialog1.setCancelable(true);
        TextView textView = (TextView) dialog1.findViewById(R.id.txtamount);
        Button confirm = (Button) dialog1.findViewById(R.id.confirm);
        final EditText amount = (EditText) dialog1.findViewById(R.id.amount);
        t_shopid = shopid;
        pos = posi;
        t_sn = sn1;
        t_amount1 = amount1;
        t_shopname = shopname1;
        t_shopmobile = shopmobile1;
        confirm.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (amount.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(Pending_Payments.getApplicationContext(), "Please enter amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                Pending_Payments.t_amount = amount.getText().toString();
                Pending_Payments pending_Payments = Pending_Payments.this;
                StringBuilder sb = new StringBuilder();
                sb.append(Integer.parseInt(Pending_Payments.t_amount1) - Integer.parseInt(Pending_Payments.t_amount));
                sb.append("");
                pending_Payments.t_amount1 = sb.toString();
                Pending_Payments.showalert_payment("Are you sure want to update ?");
                Pending_Payments.dialog1.dismiss();
            }
        });
        dialog1.show();
    }

    public void showalert_payment(String message) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (Pending_Payments.cd.isConnectingToInternet()) {
                    new updatepayment().execute(new String[0]);
                } else {
                    Toasty.info(Pending_Payments.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
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

    public void changeitem() {
        try {
            feedItems.remove(pos);
            listAdapter.notifyDataSetChanged();
            Pendingpayment_FeedItem item = new Pendingpayment_FeedItem();
            item.setsn(t_sn);
            item.setshopid(t_shopid);
            item.setamount(t_amount1);
            item.setshopname(t_shopname);
            item.setshopmobile(t_shopmobile);
            feedItems.add(pos, item);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }
}
