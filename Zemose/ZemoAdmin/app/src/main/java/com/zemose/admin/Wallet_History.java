package com.zemose.admin;

import adapter.Wallet_History_ListAdapter;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.payumoney.core.PayUmoneyConstants;
import data.Wallet_History_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Wallet_History extends AppCompatActivity {
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ImageView back;
    ConnectionDetecter cd;

    public List<Wallet_History_FeedItem> feedItems;
    public int limit = 0;

    public Wallet_History_ListAdapter listAdapter;
    ImageView loading;
    ImageView nodata;
    ProgressDialog pd;
    RecyclerView recylerview;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_wallet__history);
        StrictMode.setThreadPolicy(new Builder().permitAll().build());
        this.pd = new ProgressDialog(this);
        this.cd = new ConnectionDetecter(this);
        this.back = (ImageView) findViewById(R.id.back);
        this.loading = (ImageView) findViewById(R.id.loading);
        this.cd = new ConnectionDetecter(this);
        this.recylerview = (RecyclerView) findViewById(R.id.recylerview);
        this.nodata = (ImageView) findViewById(R.id.nodata);
        this.feedItems = new ArrayList();
        this.listAdapter = new Wallet_History_ListAdapter(this, this.feedItems);
        this.recylerview.setLayoutManager(new GridLayoutManager(this, 1));
        this.recylerview.setAdapter(this.listAdapter);
        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(this.loading);
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Wallet_History.this.onBackPressed();
            }
        });
        if (this.cd.isConnectingToInternet()) {
            this.limit = 0;
            getwallet_history();
            return;
        }
        Toasty.info(getApplicationContext(), Temp_Variable.nointernet, Toast.LENGTH_SHORT).show();
    }

    public void removeitem(int position) {
        try {
            this.feedItems.remove(position);
            this.listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void getwallet_history() {
        try {
            this.nodata.setVisibility(View.GONE);
            this.recylerview.setVisibility(View.GONE);
            this.loading.setVisibility(View.VISIBLE);
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("adminid", "");
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Request.Builder builder = new Request.Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/getwallet_history");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Wallet_History.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Wallet_History.this.nodata.setVisibility(View.VISIBLE);
                            Wallet_History.this.loading.setVisibility(View.GONE);
                            Wallet_History.this.recylerview.setVisibility(View.GONE);
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Wallet_History.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Wallet_History.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Wallet_History.this.nodata.setVisibility(View.VISIBLE);
                                    Wallet_History.this.loading.setVisibility(View.GONE);
                                    Wallet_History.this.recylerview.setVisibility(View.GONE);
                                    Toasty.info(Wallet_History.this.getApplicationContext(), "Please contact admin", Toast.LENGTH_SHORT).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("OK")) {
                                    Wallet_History.this.feedItems.clear();
                                    JSONArray jsonarray = jo.getJSONArray("data");
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        Wallet_History_FeedItem h = new Wallet_History_FeedItem();
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
                                        String date = sdf.format(new Date(Long.parseLong(jsonobject.getString("timeStamp"))));
                                        String GetHumanReadableDate = Wallet_History.GetHumanReadableDate(Long.parseLong(jsonobject.getString("timeStamp")) / 1000, "dd-MM-yyyy HH:mm:ss aa");
                                        Log.w("Vishyny", jsonobject.getString("timeStamp"));
                                        Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                        c1.setTime(sdf.parse(date));
                                        h.setshopId(jsonobject.getString("shopId"));
                                        h.setremarks(jsonobject.getString("remarks"));
                                        h.setpaymentMethod(jsonobject.getString("paymentMethod"));
                                        h.setamount(jsonobject.getString(PayUmoneyConstants.AMOUNT));
                                        h.settimeStamp(Wallet_History.this.getFormattedDate(c1.getTimeInMillis()));
                                        JSONArray jarray = jsonobject.getJSONArray("fromItems");
                                        if (jarray.length() > 0) {
                                            h.setshopname(jarray.getJSONObject(0).getString("shopName"));
                                            Wallet_History.this.feedItems.add(h);
                                        }
                                    }
                                    Wallet_History.this.loading.setVisibility(View.GONE);
                                    Wallet_History.this.recylerview.setVisibility(View.VISIBLE);
                                    Wallet_History.this.listAdapter.notifyDataSetChanged();
                                } else {
                                    Wallet_History.this.nodata.setVisibility(View.VISIBLE);
                                    Wallet_History.this.loading.setVisibility(View.GONE);
                                    Wallet_History.this.recylerview.setVisibility(View.GONE);
                                    Toasty.info(Wallet_History.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Wallet_History.this.nodata.setVisibility(View.VISIBLE);
                                Wallet_History.this.loading.setVisibility(View.GONE);
                                Wallet_History.this.recylerview.setVisibility(View.GONE);
                                Toasty.info(Wallet_History.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }

    public static String GetHumanReadableDate(long epochSec, String dateFormatStr) {
        return new SimpleDateFormat(dateFormatStr, Locale.getDefault()).format(new Date(1000 * epochSec));
    }

    public String getFormattedDate(long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);
        Calendar now = Calendar.getInstance();
        String str = "h:mm a";
        String str2 = "MMM d h:mm a";
        if (now.get(5) == smsTime.get(5)) {
            StringBuilder sb = new StringBuilder();
            sb.append(DateFormat.format("h:mm a", smsTime));
            sb.append("");
            return sb.toString();
        } else if (now.get(5) - smsTime.get(5) == 1) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Yesterday ");
            sb2.append(DateFormat.format("h:mm a", smsTime));
            return sb2.toString();
        } else if (now.get(1) == smsTime.get(1)) {
            return DateFormat.format("MMM d h:mm a", smsTime).toString();
        } else {
            return DateFormat.format("MMM dd yyyy h:mm a", smsTime).toString();
        }
    }
}
