package com.zemose.admin;

import adapter.Wallet_Report_ListAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.payumoney.core.PayUmoneyConstants;
import data.Wallet_Report_FeedItem;
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

public class Supplier_Product extends AppCompatActivity {
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    RelativeLayout addproduct;
    ImageView back;
    ConnectionDetecter cd;
    /* access modifiers changed from: private */
    public List<Wallet_Report_FeedItem> feedItems;
    public int limit = 0;
    /* access modifiers changed from: private */
    public Wallet_Report_ListAdapter listAdapter;
    ImageView loading;
    ImageView nodata;
    ProgressDialog pd;
    RecyclerView recylerview;
    TextView text;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_supplier__product);
        StrictMode.setThreadPolicy(new Builder().permitAll().build());
        this.addproduct = (RelativeLayout) findViewById(R.id.addproduct);
        this.pd = new ProgressDialog(this);
        this.cd = new ConnectionDetecter(this);
        this.back = (ImageView) findViewById(R.id.back);
        this.text = (TextView) findViewById(R.id.text);
        this.loading = (ImageView) findViewById(R.id.loading);
        this.cd = new ConnectionDetecter(this);
        this.recylerview = (RecyclerView) findViewById(R.id.recylerview);
        this.nodata = (ImageView) findViewById(R.id.nodata);
        this.text.setText(Search_Suppliers.t_shopname);
        this.feedItems = new ArrayList();
        this.listAdapter = new Wallet_Report_ListAdapter(this, this.feedItems);
        this.recylerview.setLayoutManager(new GridLayoutManager(this, 1));
        this.recylerview.setAdapter(this.listAdapter);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(this.loading);
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Supplier_Product.this.onBackPressed();
            }
        });
        if (this.cd.isConnectingToInternet()) {
            this.limit = 0;
            getproducts();
        } else {
            Toasty.info(getApplicationContext(), Temp_Variable.nointernet, 0).show();
        }
        this.addproduct.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Supplier_Product.this.startActivity(new Intent(Supplier_Product.this.getApplicationContext(), Product_Search_For_Add.class));
            }
        });
    }

    public void getproducts() {
        try {
            this.nodata.setVisibility(8);
            this.recylerview.setVisibility(8);
            this.loading.setVisibility(0);
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("shopId", Search_Suppliers.t_shopid);
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Request.Builder builder = new Request.Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/getconsolereportWallet");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Supplier_Product.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Supplier_Product.this.nodata.setVisibility(0);
                            Supplier_Product.this.loading.setVisibility(8);
                            Supplier_Product.this.recylerview.setVisibility(8);
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Supplier_Product.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Supplier_Product.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Supplier_Product.this.nodata.setVisibility(0);
                                    Supplier_Product.this.loading.setVisibility(8);
                                    Supplier_Product.this.recylerview.setVisibility(8);
                                    Toasty.info(Supplier_Product.this.getApplicationContext(), "Please contact admin", 0).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("got")) {
                                    Supplier_Product.this.feedItems.clear();
                                    JSONArray jsonarray = jo.getJSONArray("data");
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        Wallet_Report_FeedItem h = new Wallet_Report_FeedItem();
                                        h.setuserid(jsonobject.getString("shopId"));
                                        h.setname(jsonobject.getString("shopName"));
                                        h.setcontact(jsonobject.getString("contactOne"));
                                        h.setamount(jsonobject.getString("walletAmount"));
                                        Supplier_Product.this.feedItems.add(h);
                                    }
                                    Supplier_Product.this.loading.setVisibility(8);
                                    Supplier_Product.this.recylerview.setVisibility(0);
                                    Supplier_Product.this.listAdapter.notifyDataSetChanged();
                                } else {
                                    Supplier_Product.this.nodata.setVisibility(0);
                                    Supplier_Product.this.loading.setVisibility(8);
                                    Supplier_Product.this.recylerview.setVisibility(8);
                                    Toasty.info(Supplier_Product.this.getApplicationContext(), "Please try later", 0).show();
                                }
                            } catch (Exception e) {
                                Supplier_Product.this.nodata.setVisibility(0);
                                Supplier_Product.this.loading.setVisibility(8);
                                Supplier_Product.this.recylerview.setVisibility(8);
                                Toasty.info(Supplier_Product.this.getApplicationContext(), "Please try later", 0).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }
}
