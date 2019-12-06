package com.zemose.admin;

import adapter.Transfered_ListAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.payumoney.core.PayUmoneyConstants;
import data.Sna_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Transfered_Report extends AppCompatActivity {
    public static String mobile = "";
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ImageView back;
    ConnectionDetecter cd;
    /* access modifiers changed from: private */
    public List<Sna_FeedItem> feedItems;
    public int limit = 0;
    /* access modifiers changed from: private */
    public Transfered_ListAdapter listAdapter;
    ImageView loading;
    ImageView nodata;
    ProgressDialog pd;
    RecyclerView recylerview;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_transfered__report);
        this.pd = new ProgressDialog(this);
        this.cd = new ConnectionDetecter(this);
        this.back = (ImageView) findViewById(R.id.back);
        this.loading = (ImageView) findViewById(R.id.loading);
        this.cd = new ConnectionDetecter(this);
        this.recylerview = (RecyclerView) findViewById(R.id.recylerview);
        this.nodata = (ImageView) findViewById(R.id.nodata);
        this.feedItems = new ArrayList();
        this.listAdapter = new Transfered_ListAdapter(this, this.feedItems);
        this.recylerview.setLayoutManager(new GridLayoutManager(this, 1));
        this.recylerview.setAdapter(this.listAdapter);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(this.loading);
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Transfered_Report.this.onBackPressed();
            }
        });
        if (this.cd.isConnectingToInternet()) {
            this.limit = 0;
            getpending_Payments();
            return;
        }
        Toasty.info(getApplicationContext(), Temp_Variable.nointernet, 0).show();
    }

    public void call(String mobile1) {
        mobile = mobile1;
        try {
            if (ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.CALL_PHONE"}, 1);
                return;
            }
            Intent callIntent = new Intent("android.intent.action.CALL");
            StringBuilder sb = new StringBuilder();
            sb.append("tel:");
            sb.append(mobile);
            callIntent.setData(Uri.parse(sb.toString()));
            startActivity(callIntent);
        } catch (Exception e) {
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == 0) {
            call(mobile);
        }
    }

    public void removeitem(int position) {
        try {
            this.feedItems.remove(position);
            this.listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void getpending_Payments() {
        try {
            this.nodata.setVisibility(8);
            this.recylerview.setVisibility(8);
            this.loading.setVisibility(0);
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("adminid", "");
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Builder builder = new Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/gettransferd_report");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Transfered_Report.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Transfered_Report.this.nodata.setVisibility(0);
                            Transfered_Report.this.loading.setVisibility(8);
                            Transfered_Report.this.recylerview.setVisibility(8);
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Transfered_Report.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Transfered_Report.this.pd.dismiss();
                                String result = response.body().string();
                                JSONObject jo = new JSONObject(result);
                                Log.w("Best of ", result);
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Transfered_Report.this.nodata.setVisibility(0);
                                    Transfered_Report.this.loading.setVisibility(8);
                                    Transfered_Report.this.recylerview.setVisibility(8);
                                    Toasty.info(Transfered_Report.this.getApplicationContext(), "Please contact admin", 0).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("got")) {
                                    Transfered_Report.this.feedItems.clear();
                                    JSONArray jsonarray = jo.getJSONArray("data");
                                    if (jsonarray.length() > 0) {
                                        for (int i = 0; i < jsonarray.length(); i++) {
                                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                                            Sna_FeedItem h = new Sna_FeedItem();
                                            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                            sdf.setCalendar(cal);
                                            cal.setTime(sdf.parse(jsonobject.getString("timeStamp")));
                                            h.setproductname(jsonobject.getString("productName"));
                                            h.setorderdate(Transfered_Report.this.getFormattedDate(cal.getTimeInMillis()));
                                            h.setproductimage(jsonobject.getString("productImage"));
                                            h.setimgsig(jsonobject.getString("productImageSign"));
                                            JSONArray jarray = jsonobject.getJSONArray("fromItems");
                                            h.setcustomername(jarray.getJSONObject(0).getString("customerName"));
                                            h.setcustomercontact(jarray.getJSONObject(0).getString("contactOne"));
                                            Transfered_Report.this.feedItems.add(h);
                                        }
                                        Transfered_Report.this.loading.setVisibility(8);
                                        Transfered_Report.this.recylerview.setVisibility(0);
                                        Transfered_Report.this.listAdapter.notifyDataSetChanged();
                                    } else {
                                        Transfered_Report.this.nodata.setVisibility(0);
                                        Transfered_Report.this.loading.setVisibility(8);
                                        Transfered_Report.this.recylerview.setVisibility(8);
                                    }
                                } else {
                                    Transfered_Report.this.nodata.setVisibility(0);
                                    Transfered_Report.this.loading.setVisibility(8);
                                    Transfered_Report.this.recylerview.setVisibility(8);
                                    Toasty.info(Transfered_Report.this.getApplicationContext(), "Please try later", 0).show();
                                }
                            } catch (Exception a) {
                                Log.w("RRR", Log.getStackTraceString(a));
                                Transfered_Report.this.nodata.setVisibility(0);
                                Transfered_Report.this.loading.setVisibility(8);
                                Transfered_Report.this.recylerview.setVisibility(8);
                                Toasty.info(Transfered_Report.this.getApplicationContext(), "Please try later", 0).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
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
