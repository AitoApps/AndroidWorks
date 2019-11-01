package com.sanji;

import adapter.Order_History_listadapter;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.bumptech.glide.Glide;
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

public class Fragment_MyOrder extends Fragment {
    ConnectionDetecter cd;
    Context context;
    public DatabaseHandler db;
    Typeface face;
    Typeface face1;

    public List<Order_History_Feed> feedItems;
    View footerView;
    ImageView heart;

    public SwipeRefreshLayout layout;
    public int limit = 0;

    public Order_History_listadapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    public HeaderAndFooterRecyclerView recylerview;
    public UserDatabaseHandler udb;

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Fragment_MyOrder.nodata.setVisibility(View.GONE);
            Fragment_MyOrder.recylerview.setVisibility(View.GONE);
            Fragment_MyOrder.heart.setVisibility(View.VISIBLE);
            Fragment_MyOrder.limit = 0;
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
                sb3.append(Fragment_MyOrder.udb.get_userid());
                sb3.append(":%");
                sb3.append(Fragment_MyOrder.limit);
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
                        Fragment_MyOrder.feedItems.clear();
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
                                item.setorderdate(Fragment_MyOrder.getFormattedDate(c1.getTimeInMillis()));
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
                            Fragment_MyOrder.feedItems.add(item);
                        }
                    } catch (Exception e2) {
                    }
                    Fragment_MyOrder.nodata.setVisibility(View.GONE);
                    Fragment_MyOrder.heart.setVisibility(View.GONE);
                    Fragment_MyOrder.recylerview.setVisibility(View.VISIBLE);
                    Fragment_MyOrder.listAdapter.notifyDataSetChanged();
                    return;
                }
                Fragment_MyOrder.nodata.setVisibility(View.VISIBLE);
                Fragment_MyOrder.recylerview.setVisibility(View.GONE);
                Fragment_MyOrder.heart.setVisibility(View.GONE);
            } catch (Exception e3) {
            }
        }
    }

    public class loadstatus1 extends AsyncTask<String, Void, String> {
        public loadstatus1() {
        }
        public void onPreExecute() {
            Fragment_MyOrder.limit += 30;
            Fragment_MyOrder.footerView.setVisibility(View.VISIBLE);
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
                sb3.append(Fragment_MyOrder.udb.get_userid());
                sb3.append(":%");
                sb3.append(Fragment_MyOrder.limit);
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
                                item.setorderdate(Fragment_MyOrder.getFormattedDate(c1.getTimeInMillis()));
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
                            Fragment_MyOrder.feedItems.add(item);
                        }
                    } catch (Exception e2) {
                    }
                    Fragment_MyOrder.footerView.setVisibility(View.INVISIBLE)
                    Fragment_MyOrder.listAdapter.notifyDataSetChanged();
                    return;
                }
                Fragment_MyOrder.footerView.setVisibility(View.INVISIBLE)
                Fragment_MyOrder.heart.setVisibility(View.GONE);
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
                sb3.append(Fragment_MyOrder.udb.get_userid());
                sb3.append(":%");
                sb3.append(Fragment_MyOrder.limit);
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
                Fragment_MyOrder.layout.setRefreshing(false);
                if (result.contains("%:ok")) {
                    try {
                        Fragment_MyOrder.feedItems.clear();
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
                                item.setorderdate(Fragment_MyOrder.getFormattedDate(c1.getTimeInMillis()));
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
                            Fragment_MyOrder.feedItems.add(item);
                        }
                    } catch (Exception e2) {
                    }
                    Fragment_MyOrder.listAdapter.notifyDataSetChanged();
                    return;
                }
                Fragment_MyOrder.heart.setVisibility(View.GONE);
            } catch (Exception e3) {
            }
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orderhistory, container, false);
        context = getContext();
        db = new DatabaseHandler(context);
        cd = new ConnectionDetecter(context);
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");
        db = new DatabaseHandler(context);
        cd = new ConnectionDetecter(context);
        udb = new UserDatabaseHandler(context);
        pd = new ProgressDialog(context);
        nodata = (ImageView) view.findViewById(R.id.nodata);
        recylerview = (HeaderAndFooterRecyclerView) view.findViewById(R.id.recylerview);
        heart = (ImageView) view.findViewById(R.id.heart);
        nointernet = (ImageView) view.findViewById(R.id.nointernet);
        layout = (SwipeRefreshLayout) view.findViewById(R.id.layout);
        layout.setEnabled(true);
        feedItems = new ArrayList();
        listAdapter = new Order_History_listadapter(getActivity(), feedItems);
        recylerview.setLayoutManager(new GridLayoutManager(context, 1));
        recylerview.setAdapter(listAdapter);
        footerView = LayoutInflater.from(getContext()).inflate(R.layout.footerview, recylerview.getFooterContainer(), false);
        recylerview.addFooterView(footerView);
        footerView.setVisibility(View.INVISIBLE)
        layout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                Fragment_MyOrder.layout.setRefreshing(true);
                Fragment_MyOrder.nointernet.setVisibility(View.GONE);
                Fragment_MyOrder fragment_MyOrder = Fragment_MyOrder.this;
                fragment_MyOrder.limit = 0;
                new loadstatus2().execute(new String[0]);
            }
        });
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (Fragment_MyOrder.cd.isConnectingToInternet()) {
                    Fragment_MyOrder.nointernet.setVisibility(View.GONE);
                    Fragment_MyOrder fragment_MyOrder = Fragment_MyOrder.this;
                    fragment_MyOrder.limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                Fragment_MyOrder.nointernet.setVisibility(View.VISIBLE);
                Toasty.info(Fragment_MyOrder.context, Temp.nointernet, 0).show();
            }
        });
        Glide.with((Fragment) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            limit = 0;
            new loadstatus().execute(new String[0]);
        } else {
            nointernet.setVisibility(View.VISIBLE);
            Toasty.info(context, Temp.nointernet, 0).show();
        }
        return view;
    }

    public void loadmore() {
        if (footerView.getVisibility() == 0) {
            return;
        }
        if (cd.isConnectingToInternet()) {
            new loadstatus1().execute(new String[0]);
        } else {
            Toasty.info(context, Temp.nointernet, 0).show();
        }
    }
    public void call(String mob) {
        String str = "android.permission.CALL_PHONE";
        try {
            if (ContextCompat.checkSelfPermission(context, str) != 0) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{str}, 1);
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
        Builder builder = new Builder(context);
        StringBuilder sb = new StringBuilder();
        sb.append(mob);
        sb.append(" ");
        sb.append(message);
        builder.setMessage(sb.toString()).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Fragment_MyOrder.call(mob);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
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
