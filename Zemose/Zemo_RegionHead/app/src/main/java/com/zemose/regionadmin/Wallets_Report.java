package com.zemose.regionadmin;

import adapter.Wallet_Report_ListAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class Wallets_Report extends AppCompatActivity {
    public static String mobile = "";
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ImageView back;
    ConnectionDetecter cd;

    public List<Wallet_Report_FeedItem> feedItems;
    Button get;
    public int limit = 0;

    public Wallet_Report_ListAdapter listAdapter;
    ImageView loading;
    EditText mobilenumber;
    ImageView nodata;
    ProgressDialog pd;
    int pos = 0;
    RecyclerView recylerview;
    final UserDB udb = new UserDB(this);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_wallets__report);
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
        this.listAdapter = new Wallet_Report_ListAdapter(this, this.feedItems);
        this.recylerview.setLayoutManager(new GridLayoutManager(this, 1));
        this.recylerview.setAdapter(this.listAdapter);
        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(this.loading);
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Wallets_Report.this.onBackPressed();
            }
        });
        if (this.cd.isConnectingToInternet()) {
            this.limit = 0;
            getpending_Payments();
        } else {
            Toasty.info(getApplicationContext(), Temp_Variable.nointernet, Toast.LENGTH_SHORT).show();
        }
        this.get.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Wallets_Report.this.mobilenumber.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(Wallets_Report.this.getApplicationContext(), "Please enter mobile number", Toast.LENGTH_SHORT).show();
                    Wallets_Report.this.mobilenumber.requestFocus();
                    return;
                }
                Wallets_Report.this.pd.setMessage("Please wait...");
                Wallets_Report.this.pd.setCancelable(false);
                Wallets_Report.this.pd.show();
                Wallets_Report.this.getsupplierdetails();
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

    public void changeitem(int position, String amount) {
        try {
            Wallet_Report_FeedItem h1 = (Wallet_Report_FeedItem) this.feedItems.get(position);
            Wallet_Report_FeedItem h = new Wallet_Report_FeedItem();
            h.setuserid(h1.getuserid());
            h.setname(h1.getname());
            h.setcontact(h1.getcontact());
            StringBuilder sb = new StringBuilder();
            sb.append(Float.parseFloat(h1.getamount()) * Float.parseFloat(amount));
            sb.append("");
            h.setamount(sb.toString());
            this.feedItems.remove(position);
            this.listAdapter.notifyDataSetChanged();
            this.feedItems.add(position, h);
        } catch (Exception e) {
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
            this.nodata.setVisibility(View.GONE);
            this.recylerview.setVisibility(View.GONE);
            this.loading.setVisibility(View.VISIBLE);
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("adminid", "");
                jo.put("regionId", Integer.parseInt(this.udb.getRegionheadId()));
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Request.Builder builder = new Request.Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/getconsolereportWallet_regionhead");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Wallets_Report.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Wallets_Report.this.nodata.setVisibility(View.VISIBLE);
                            Wallets_Report.this.loading.setVisibility(View.GONE);
                            Wallets_Report.this.recylerview.setVisibility(View.GONE);
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Wallets_Report.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Wallets_Report.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Wallets_Report.this.nodata.setVisibility(View.VISIBLE);
                                    Wallets_Report.this.loading.setVisibility(View.GONE);
                                    Wallets_Report.this.recylerview.setVisibility(View.GONE);
                                    Toasty.info(Wallets_Report.this.getApplicationContext(), "Please contact admin", Toast.LENGTH_SHORT).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("got")) {
                                    Wallets_Report.this.feedItems.clear();
                                    JSONArray jsonarray = jo.getJSONArray("data");
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        Wallet_Report_FeedItem h = new Wallet_Report_FeedItem();
                                        h.setuserid(jsonobject.getString("shopId"));
                                        h.setname(jsonobject.getString("shopName"));
                                        h.setcontact(jsonobject.getString("contactOne"));
                                        h.setamount(jsonobject.getString("walletAmount"));
                                        Wallets_Report.this.feedItems.add(h);
                                    }
                                    Wallets_Report.this.loading.setVisibility(View.GONE);
                                    Wallets_Report.this.recylerview.setVisibility(View.VISIBLE);
                                    Wallets_Report.this.listAdapter.notifyDataSetChanged();
                                } else {
                                    Wallets_Report.this.nodata.setVisibility(View.VISIBLE);
                                    Wallets_Report.this.loading.setVisibility(View.GONE);
                                    Wallets_Report.this.recylerview.setVisibility(View.GONE);
                                    Toasty.info(Wallets_Report.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Wallets_Report.this.nodata.setVisibility(View.VISIBLE);
                                Wallets_Report.this.loading.setVisibility(View.GONE);
                                Wallets_Report.this.recylerview.setVisibility(View.GONE);
                                Toasty.info(Wallets_Report.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }

    public void show_supplier(String shopname1, final String shopid, int position) {
        final Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.supplier_details);
        final EditText wallet = (EditText) dialog1.findViewById(R.id.wallet);
        Button update = (Button) dialog1.findViewById(R.id.update);
        ((TextView) dialog1.findViewById(R.id.shopname)).setText(shopname1);
        this.pos = position;
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (wallet.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(Wallets_Report.this.getApplicationContext(), "Please enter waller amount", Toast.LENGTH_SHORT).show();
                    wallet.requestFocus();
                } else if (Wallets_Report.this.cd.isConnectingToInternet()) {
                    Wallets_Report.this.pd.setMessage("Updating...Please wait");
                    Wallets_Report.this.pd.setCancelable(false);
                    Wallets_Report.this.pd.show();
                    Wallets_Report.this.wallet_update(shopid, wallet.getText().toString());
                    dialog1.dismiss();
                } else {
                    Toasty.info(Wallets_Report.this.getApplicationContext(), "Please make sure your internet connection is active", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog1.show();
    }

    public void wallet_update(String shopid, final String amount) {
        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("shopid", shopid);
                jo.put(PayUmoneyConstants.AMOUNT, amount);
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Request.Builder builder = new Request.Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/updatewallet");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Wallets_Report.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Wallets_Report.this.pd.dismiss();
                            Toasty.info(Wallets_Report.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Wallets_Report.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Wallets_Report.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Toasty.info(Wallets_Report.this.getApplicationContext(), "Please check contact number", Toast.LENGTH_SHORT).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("Update")) {
                                    Toasty.info(Wallets_Report.this.getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                    Wallets_Report.this.changeitem(Wallets_Report.this.pos, amount);
                                } else {
                                    Toasty.info(Wallets_Report.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }

    public void getsupplierdetails() {
        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("contact", this.mobilenumber.getText().toString());
                jo.put("regionId", Integer.parseInt(this.udb.getRegionheadId()));
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Request.Builder builder = new Request.Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/getsupplier_wallet_regionhead");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Wallets_Report.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Wallets_Report.this.pd.dismiss();
                            Toasty.info(Wallets_Report.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Wallets_Report.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Wallets_Report.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Toasty.info(Wallets_Report.this.getApplicationContext(), "Please check contact number", Toast.LENGTH_SHORT).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("got")) {
                                    JSONArray jsonarray = jo.getJSONArray("data");
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        Wallets_Report.this.show_supplier(jsonobject.getString("shopName"), jsonobject.getString("walletAmount"));
                                    }
                                } else {
                                    Toasty.info(Wallets_Report.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }

    public void show_supplier(String shopname1, String amount) {
        Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.wallet_details);
        TextView shopname = (TextView) dialog1.findViewById(R.id.shopname);
        ((TextView) dialog1.findViewById(R.id.wallet)).setText(amount);
        shopname.setText(shopname1);
        dialog1.show();
    }
}
