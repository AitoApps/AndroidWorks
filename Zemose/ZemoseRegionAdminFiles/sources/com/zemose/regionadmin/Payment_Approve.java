package com.zemose.regionadmin;

import adapter.PaymentApprove_ListAdapter;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.payumoney.core.PayUmoneyConstants;
import data.PaymentApprove_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

public class Payment_Approve extends AppCompatActivity {
    public static String mobile = "";
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ImageView back;
    ConnectionDetecter cd;
    /* access modifiers changed from: private */
    public List<PaymentApprove_FeedItem> feedItems;
    public int limit = 0;
    /* access modifiers changed from: private */
    public PaymentApprove_ListAdapter listAdapter;
    ImageView loading;
    ImageView nodata;
    ProgressDialog pd;
    RecyclerView recylerview;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_payment__approve);
        this.pd = new ProgressDialog(this);
        this.cd = new ConnectionDetecter(this);
        this.back = (ImageView) findViewById(R.id.back);
        this.loading = (ImageView) findViewById(R.id.loading);
        this.cd = new ConnectionDetecter(this);
        this.recylerview = (RecyclerView) findViewById(R.id.recylerview);
        this.nodata = (ImageView) findViewById(R.id.nodata);
        this.feedItems = new ArrayList();
        this.listAdapter = new PaymentApprove_ListAdapter(this, this.feedItems);
        this.recylerview.setLayoutManager(new GridLayoutManager(this, 1));
        this.recylerview.setAdapter(this.listAdapter);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(this.loading);
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Payment_Approve.this.onBackPressed();
            }
        });
        if (this.cd.isConnectingToInternet()) {
            this.limit = 0;
            getpending_Payments();
            return;
        }
        Toasty.info(getApplicationContext(), Temp_Variable.nointernet, 0).show();
    }

    public void call() {
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
            call();
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
            sb.append("appadmin/getpendingpayment");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Payment_Approve.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Payment_Approve.this.nodata.setVisibility(0);
                            Payment_Approve.this.loading.setVisibility(8);
                            Payment_Approve.this.recylerview.setVisibility(8);
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Payment_Approve.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Payment_Approve.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Payment_Approve.this.nodata.setVisibility(0);
                                    Payment_Approve.this.loading.setVisibility(8);
                                    Payment_Approve.this.recylerview.setVisibility(8);
                                    Toasty.info(Payment_Approve.this.getApplicationContext(), "Please contact admin", 0).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("OK")) {
                                    Payment_Approve.this.feedItems.clear();
                                    JSONArray jsonarray = jo.getJSONArray("data");
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        PaymentApprove_FeedItem h = new PaymentApprove_FeedItem();
                                        h.setpayid(jsonobject.getString("payid"));
                                        h.setpaymentid(jsonobject.getString("paymentid"));
                                        h.setmode(jsonobject.getString(PayUmoneyConstants.PAYMENT_MODE));
                                        h.settxtnid(jsonobject.getString("txtnid"));
                                        h.setamount(jsonobject.getString(PayUmoneyConstants.AMOUNT));
                                        h.setbankcode(jsonobject.getString(PayUmoneyConstants.BANK_CODE));
                                        h.setcardnum(jsonobject.getString("cardnum"));
                                        h.setshopId(jsonobject.getString("shopId"));
                                        JSONArray jarray = jsonobject.getJSONArray("fromItems");
                                        h.setshopName(jarray.getJSONObject(0).getString("shopName"));
                                        h.setshopContact(jarray.getJSONObject(0).getString("contactOne"));
                                        Payment_Approve.this.feedItems.add(h);
                                    }
                                    Payment_Approve.this.loading.setVisibility(8);
                                    Payment_Approve.this.recylerview.setVisibility(0);
                                    Payment_Approve.this.listAdapter.notifyDataSetChanged();
                                } else {
                                    Payment_Approve.this.nodata.setVisibility(0);
                                    Payment_Approve.this.loading.setVisibility(8);
                                    Payment_Approve.this.recylerview.setVisibility(8);
                                    Toasty.info(Payment_Approve.this.getApplicationContext(), "Please try later", 0).show();
                                }
                            } catch (Exception e) {
                                Payment_Approve.this.nodata.setVisibility(0);
                                Payment_Approve.this.loading.setVisibility(8);
                                Payment_Approve.this.recylerview.setVisibility(8);
                                Toasty.info(Payment_Approve.this.getApplicationContext(), "Please try later", 0).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }
}
