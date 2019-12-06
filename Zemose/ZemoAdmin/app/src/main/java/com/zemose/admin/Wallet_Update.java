package com.zemose.admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.payumoney.core.PayUmoneyConstants;
import es.dmoral.toasty.Toasty;
import java.io.IOException;
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

public class Wallet_Update extends AppCompatActivity {
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ConnectionDetecter cd;
    Button get;
    EditText mobilenumber;
    ProgressDialog pd;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_wallet__update);
        this.cd = new ConnectionDetecter(this);
        this.pd = new ProgressDialog(this);
        this.mobilenumber = (EditText) findViewById(R.id.mobilenumber);
        this.get = (Button) findViewById(R.id.get);
        this.get.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!Wallet_Update.this.cd.isConnectingToInternet()) {
                    Toasty.info(Wallet_Update.this.getApplicationContext(), "Please make sure your internet connection is active", Toast.LENGTH_SHORT).show();
                } else if (Wallet_Update.this.mobilenumber.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(Wallet_Update.this.getApplicationContext(), "Please enter mobile number", Toast.LENGTH_SHORT).show();
                    Wallet_Update.this.mobilenumber.requestFocus();
                } else {
                    Wallet_Update.this.pd.setMessage("Please wait...");
                    Wallet_Update.this.pd.setCancelable(false);
                    Wallet_Update.this.pd.show();
                    Wallet_Update wallet_Update = Wallet_Update.this;
                    wallet_Update.timerDelayRemoveDialog(30000, wallet_Update.pd);
                    Wallet_Update.this.getsupplierdetails();
                }
            }
        });
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void getsupplierdetails() {
        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("contact", this.mobilenumber.getText().toString());
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Builder builder = new Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/getsupplier_base");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Wallet_Update.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Wallet_Update.this.pd.dismiss();
                            Toasty.info(Wallet_Update.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Wallet_Update.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Wallet_Update.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Toasty.info(Wallet_Update.this.getApplicationContext(), "Please check contact number", Toast.LENGTH_SHORT).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("got")) {
                                    JSONArray jsonarray = jo.getJSONArray("data");
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        Wallet_Update.this.show_supplier(jsonobject.getString("shopName"), jsonobject.getString("shopId"));
                                    }
                                } else {
                                    Toasty.info(Wallet_Update.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
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

    public void show_supplier(String shopname1, final String shopid) {
        final Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.supplier_details);
        final EditText wallet = (EditText) dialog1.findViewById(R.id.wallet);
        Button update = (Button) dialog1.findViewById(R.id.update);
        ((TextView) dialog1.findViewById(R.id.shopname)).setText(shopname1);
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (wallet.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(Wallet_Update.this.getApplicationContext(), "Please enter waller amount", Toast.LENGTH_SHORT).show();
                    wallet.requestFocus();
                } else if (Wallet_Update.this.cd.isConnectingToInternet()) {
                    Wallet_Update.this.pd.setMessage("Updating...Please wait");
                    Wallet_Update.this.pd.setCancelable(false);
                    Wallet_Update.this.pd.show();
                    Wallet_Update wallet_Update = Wallet_Update.this;
                    wallet_Update.timerDelayRemoveDialog(30000, wallet_Update.pd);
                    Wallet_Update.this.wallet_update(shopid, wallet.getText().toString());
                    dialog1.dismiss();
                } else {
                    Toasty.info(Wallet_Update.this.getApplicationContext(), "Please make sure your internet connection is active", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog1.show();
    }

    public void wallet_update(String shopid, String amount) {
        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("shopid", shopid);
                jo.put(PayUmoneyConstants.AMOUNT, amount);
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Builder builder = new Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/updatewallet");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Wallet_Update.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Wallet_Update.this.pd.dismiss();
                            Toasty.info(Wallet_Update.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Wallet_Update.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Wallet_Update.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Toasty.info(Wallet_Update.this.getApplicationContext(), "Please check contact number", Toast.LENGTH_SHORT).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("Update")) {
                                    Wallet_Update.this.mobilenumber.setText("");
                                    Wallet_Update.this.mobilenumber.requestFocus();
                                    Toasty.info(Wallet_Update.this.getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toasty.info(Wallet_Update.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
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
}
