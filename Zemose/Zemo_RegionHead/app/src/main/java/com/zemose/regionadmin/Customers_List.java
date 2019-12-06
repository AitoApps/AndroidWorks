package com.zemose.regionadmin;

import adapter.Customer_ListAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.payumoney.core.PayUmoneyConstants;
import data.Customerlist_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

import static com.facebook.FacebookSdk.getApplicationContext;

public class Customers_List extends AppCompatActivity {
    public static String mobile = "";
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ImageView back;
    ConnectionDetecter cd;

    public List<Customerlist_FeedItem> feedItems;
    Button get;
    public int limit = 0;

    public Customer_ListAdapter listAdapter;
    ImageView loading;
    EditText mobilenumber;
    ImageView nodata;
    ProgressDialog pd;
    RecyclerView recylerview;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_customers__list);
        StrictMode.setThreadPolicy(new Builder().permitAll().build());
        this.pd = new ProgressDialog(this);
        this.cd = new ConnectionDetecter(this);
        this.back = (ImageView) findViewById(R.id.back);
        this.mobilenumber = (EditText) findViewById(R.id.mobilenumber);
        this.get = (Button) findViewById(R.id.get);
        this.loading = (ImageView) findViewById(R.id.loading);
        this.cd = new ConnectionDetecter(this);
        this.recylerview = (RecyclerView) findViewById(R.id.recylerview);
        this.nodata = (ImageView) findViewById(R.id.nodata);
        this.feedItems = new ArrayList();
        this.listAdapter = new Customer_ListAdapter(this, this.feedItems);
        this.recylerview.setLayoutManager(new GridLayoutManager(this, 1));
        this.recylerview.setAdapter(this.listAdapter);
        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(this.loading);
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Customers_List.this.onBackPressed();
            }
        });
        if (this.cd.isConnectingToInternet()) {
            this.limit = 0;
            get_customers();
        } else {
            Toasty.info(getApplicationContext(), Temp_Variable.nointernet, Toast.LENGTH_SHORT).show();
        }
        this.get.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Customers_List.this.mobilenumber.getText().toString().equalsIgnoreCase("")) {
                    Customers_List.this.get_customers();
                    return;
                }
                Customers_List customers_List = Customers_List.this;
                customers_List.get_suppliers_bymobilename(customers_List.mobilenumber.getText().toString());
            }
        });
    }

    public void call(String mobile1) {
        try {
            mobile = mobile1;
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

    public void get_customers() {
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
            sb.append("appadmin/getcustomer_list");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Customers_List.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Customers_List.this.nodata.setVisibility(View.VISIBLE);
                            Customers_List.this.loading.setVisibility(View.GONE);
                            Customers_List.this.recylerview.setVisibility(View.GONE);
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Customers_List.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Customers_List.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Customers_List.this.nodata.setVisibility(View.VISIBLE);
                                    Customers_List.this.loading.setVisibility(View.GONE);
                                    Customers_List.this.recylerview.setVisibility(View.GONE);
                                    Toasty.info(Customers_List.this.getApplicationContext(), "Please contact admin", Toast.LENGTH_SHORT).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("got")) {
                                    Customers_List.this.feedItems.clear();
                                    JSONArray jsonarray = jo.getJSONArray("data");
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        Customerlist_FeedItem h = new Customerlist_FeedItem();
                                        h.setcustid(jsonobject.getString("customerId"));
                                        h.setcustname(jsonobject.getString("customerName"));
                                        h.setcustmobile(jsonobject.getString("contactOne"));
                                        Customers_List.this.feedItems.add(h);
                                    }
                                    Customers_List.this.loading.setVisibility(View.GONE);
                                    Customers_List.this.recylerview.setVisibility(View.VISIBLE);
                                    Customers_List.this.listAdapter.notifyDataSetChanged();
                                } else {
                                    Customers_List.this.nodata.setVisibility(View.VISIBLE);
                                    Customers_List.this.loading.setVisibility(View.GONE);
                                    Customers_List.this.recylerview.setVisibility(View.GONE);
                                    Toasty.info(Customers_List.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Customers_List.this.nodata.setVisibility(View.VISIBLE);
                                Customers_List.this.loading.setVisibility(View.GONE);
                                Customers_List.this.recylerview.setVisibility(View.GONE);
                                Toasty.info(Customers_List.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }

    public void get_suppliers_bymobilename(String mobile2) {
        try {
            this.nodata.setVisibility(View.GONE);
            this.recylerview.setVisibility(View.GONE);
            this.loading.setVisibility(View.VISIBLE);
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("search", mobile2);
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Request.Builder builder = new Request.Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/getcustomer_list_filter");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Customers_List.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Customers_List.this.nodata.setVisibility(View.VISIBLE);
                            Customers_List.this.loading.setVisibility(View.GONE);
                            Customers_List.this.recylerview.setVisibility(View.GONE);
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Customers_List.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Customers_List.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Customers_List.this.nodata.setVisibility(View.VISIBLE);
                                    Customers_List.this.loading.setVisibility(View.GONE);
                                    Customers_List.this.recylerview.setVisibility(View.GONE);
                                    Toasty.info(Customers_List.this.getApplicationContext(), "Please contact admin", Toast.LENGTH_SHORT).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("got")) {
                                    Customers_List.this.feedItems.clear();
                                    JSONArray jsonarray = jo.getJSONArray("data");
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        Customerlist_FeedItem h = new Customerlist_FeedItem();
                                        h.setcustid(jsonobject.getString("customerId"));
                                        h.setcustname(jsonobject.getString("customerName"));
                                        h.setcustmobile(jsonobject.getString("contactOne"));
                                        Customers_List.this.feedItems.add(h);
                                    }
                                    Customers_List.this.loading.setVisibility(View.GONE);
                                    Customers_List.this.recylerview.setVisibility(View.VISIBLE);
                                    Customers_List.this.listAdapter.notifyDataSetChanged();
                                } else {
                                    Customers_List.this.nodata.setVisibility(View.VISIBLE);
                                    Customers_List.this.loading.setVisibility(View.GONE);
                                    Customers_List.this.recylerview.setVisibility(View.GONE);
                                    Toasty.info(Customers_List.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Customers_List.this.nodata.setVisibility(View.VISIBLE);
                                Customers_List.this.loading.setVisibility(View.GONE);
                                Customers_List.this.recylerview.setVisibility(View.GONE);
                                Toasty.info(Customers_List.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }
}
