package com.zemose.admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
import org.json.JSONException;
import org.json.JSONObject;

public class Search_Suppliers extends AppCompatActivity {
    public static String t_shopid = "";
    public static String t_shopname = "";
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ConnectionDetecter cd;
    public Dialog dialog1;
    Button get;
    EditText mobilenumber;
    ProgressDialog pd;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_search__suppliers);
        this.mobilenumber = (EditText) findViewById(R.id.mobilenumber);
        this.get = (Button) findViewById(R.id.get);
        this.cd = new ConnectionDetecter(this);
        this.pd = new ProgressDialog(this);
        this.get.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Search_Suppliers.this.cd.isConnectingToInternet()) {
                    Search_Suppliers search_Suppliers = Search_Suppliers.this;
                    search_Suppliers.get_supplierlist(search_Suppliers.mobilenumber.getText().toString());
                    return;
                }
                Toasty.info(Search_Suppliers.this.getApplicationContext(), Temp_Variable.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void get_supplierlist(String mobile) {
        try {
            this.pd.setMessage("Fetching...");
            this.pd.setCancelable(false);
            this.pd.show();
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("contact", mobile);
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Builder builder = new Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/getsupplier_dtl_by_ID");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Search_Suppliers.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Search_Suppliers.this.pd.dismiss();
                            Toasty.info(Search_Suppliers.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Search_Suppliers.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Search_Suppliers.this.pd.dismiss();
                                String result = response.body().string();
                                Log.w("EEE", result);
                                JSONObject jo = new JSONObject(result);
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Toasty.info(Search_Suppliers.this.getApplicationContext(), "Not Found", Toast.LENGTH_SHORT).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("got")) {
                                    JSONObject jsonobject = jo.getJSONArray("data").getJSONObject(0);
                                    Search_Suppliers.this.show_supplier(jsonobject.getString("shopName"), jsonobject.getString("shopId"), jsonobject.getString("contactPerson"));
                                } else {
                                    Toasty.info(Search_Suppliers.this.getApplicationContext(), "Not Found", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Search_Suppliers.this.pd.dismiss();
                                Toasty.info(Search_Suppliers.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }

    public void show_supplier(String shopname1, final String shopid, String shopcontact) {
        this.dialog1 = new Dialog(this);
        this.dialog1.requestWindowFeature(1);
        this.dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialog1.setContentView(R.layout.supplier_details_product);
        final TextView shopname = (TextView) this.dialog1.findViewById(R.id.shopname);
        TextView contact = (TextView) this.dialog1.findViewById(R.id.contact);
        Button choose = (Button) this.dialog1.findViewById(R.id.choose);
        shopname.setText(shopname1);
        contact.setText(shopcontact);
        choose.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Search_Suppliers.t_shopname = shopname.getText().toString();
                Search_Suppliers.t_shopid = shopid;
                Search_Suppliers.this.startActivity(new Intent(Search_Suppliers.this.getApplicationContext(), Supplier_Product.class));
            }
        });
        this.dialog1.show();
    }
    public void onResume() {
        super.onResume();
        Dialog dialog = this.dialog1;
        if (dialog != null && dialog.isShowing()) {
            this.dialog1.dismiss();
        }
    }
}
