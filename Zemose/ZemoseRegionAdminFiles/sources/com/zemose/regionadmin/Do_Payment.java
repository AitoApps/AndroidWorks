package com.zemose.regionadmin;

import adapter.Supplier_List_ForPayment_Adapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer.PaymentParam;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.core.entity.TransactionResponse.TransactionStatus;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;
import data.Supplierlist_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

public class Do_Payment extends AppCompatActivity {
    public static String mobile = "";
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ImageView back;
    ConnectionDetecter cd;
    /* access modifiers changed from: private */
    public List<Supplierlist_FeedItem> feedItems;
    Button get;
    public int limit = 0;
    /* access modifiers changed from: private */
    public Supplier_List_ForPayment_Adapter listAdapter;
    ImageView loading;
    private PaymentParam mPaymentParams;
    EditText mobilenumber;
    ImageView nodata;
    ProgressDialog pd;
    RecyclerView recylerview;
    final UserDB udb = new UserDB(this);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_do__payment);
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
        this.listAdapter = new Supplier_List_ForPayment_Adapter(this, this.feedItems);
        this.recylerview.setLayoutManager(new GridLayoutManager(this, 1));
        this.recylerview.setAdapter(this.listAdapter);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(this.loading);
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Do_Payment.this.onBackPressed();
            }
        });
        this.get.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Do_Payment.this.mobilenumber.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(Do_Payment.this.getApplicationContext(), "Please enter mobile number", 0).show();
                    return;
                }
                Do_Payment do_Payment = Do_Payment.this;
                do_Payment.get_suppliers_bymobilename(do_Payment.mobilenumber.getText().toString());
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

    public void get_suppliers_bymobilename(String mobile2) {
        try {
            this.nodata.setVisibility(8);
            this.recylerview.setVisibility(8);
            this.loading.setVisibility(0);
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("search", mobile2);
                jo.put("regionId", Integer.parseInt(this.udb.getRegionheadId()));
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Request.Builder builder = new Request.Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/getsupplier_list_filter_regionhead");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Do_Payment.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Do_Payment.this.nodata.setVisibility(0);
                            Do_Payment.this.loading.setVisibility(8);
                            Do_Payment.this.recylerview.setVisibility(8);
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Do_Payment.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Do_Payment.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Do_Payment.this.nodata.setVisibility(0);
                                    Do_Payment.this.loading.setVisibility(8);
                                    Do_Payment.this.recylerview.setVisibility(8);
                                    Toasty.info(Do_Payment.this.getApplicationContext(), "Please contact admin", 0).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("got")) {
                                    Do_Payment.this.feedItems.clear();
                                    JSONArray jsonarray = jo.getJSONArray("data");
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        Supplierlist_FeedItem h = new Supplierlist_FeedItem();
                                        h.setsupid(jsonobject.getString("shopId"));
                                        h.setsupname(jsonobject.getString("shopName"));
                                        h.setsupmobile(jsonobject.getString("contactOne"));
                                        h.setsupplace(jsonobject.getString("locAddress"));
                                        h.setsupllocation(jsonobject.getString("location"));
                                        h.setwalletamount(jsonobject.getString("walletAmount"));
                                        h.setimgpath(jsonobject.getString("imagePath"));
                                        h.setimgsig(jsonobject.getString("imageSign"));
                                        Do_Payment.this.feedItems.add(h);
                                    }
                                    Do_Payment.this.loading.setVisibility(8);
                                    Do_Payment.this.recylerview.setVisibility(0);
                                    Do_Payment.this.listAdapter.notifyDataSetChanged();
                                } else {
                                    Do_Payment.this.nodata.setVisibility(0);
                                    Do_Payment.this.loading.setVisibility(8);
                                    Do_Payment.this.recylerview.setVisibility(8);
                                    Toasty.info(Do_Payment.this.getApplicationContext(), "Please try later", 0).show();
                                }
                            } catch (Exception e) {
                                Do_Payment.this.nodata.setVisibility(0);
                                Do_Payment.this.loading.setVisibility(8);
                                Do_Payment.this.recylerview.setVisibility(8);
                                Toasty.info(Do_Payment.this.getApplicationContext(), "Please try later", 0).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }

    public void showpayment(String shopname, String mobile2, String email1) {
        Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.wallet_amount);
        Button update = (Button) dialog1.findViewById(R.id.update);
        final EditText editText = (EditText) dialog1.findViewById(R.id.amount);
        final Dialog dialog = dialog1;
        final String str = shopname;
        final String str2 = mobile2;
        final String str3 = email1;
        AnonymousClass4 r1 = new OnClickListener() {
            public void onClick(View view) {
                if (editText.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(Do_Payment.this.getApplicationContext(), "Please enter amount", 0).show();
                    editText.requestFocus();
                    return;
                }
                dialog.dismiss();
                Do_Payment.this.launchPayUMoneyFlow(str, editText.getText().toString(), str2, str3);
            }
        };
        update.setOnClickListener(r1);
        dialog1.show();
    }

    public void launchPayUMoneyFlow(String shopname, String amt, String mobile2, String email1) {
        String email;
        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();
        payUmoneyConfig.setDoneButtonText("Finish");
        payUmoneyConfig.setPayUmoneyActivityTitle(shopname);
        payUmoneyConfig.disableExitConfirmation(false);
        PaymentParam.Builder builder = new PaymentParam.Builder();
        double amount = 0.0d;
        try {
            amount = Double.parseDouble(amt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        sb.append("");
        String txnId = sb.toString();
        String phone = mobile2;
        String productName = "Wallet TopUp";
        String firstName = shopname;
        String str = "";
        if (email1.equalsIgnoreCase("")) {
            email = "zemoseonline@gmail.com";
        } else {
            email = email1;
        }
        PayUmoneyConfig payUmoneyConfig2 = payUmoneyConfig;
        String udf4 = "";
        String udf5 = "";
        String udf8 = "";
        String udf10 = "";
        double d = amount;
        PaymentParam.Builder udf52 = builder.setAmount(String.valueOf(amount)).setTxnId(txnId).setPhone(phone).setProductName(productName).setFirstName(firstName).setEmail(email).setsUrl("http://zemose.com/sucesss").setfUrl("http://zemose.com/failure").setUdf1("").setUdf2("").setUdf3("").setUdf4(udf4).setUdf5(udf5);
        String str2 = udf4;
        String udf42 = "";
        String str3 = udf10;
        udf52.setUdf6("").setUdf7("").setUdf8(udf8).setUdf9(udf42).setUdf10(udf10).setIsDebug(false).setKey("9CH5bucb").setMerchantId("5582331");
        try {
            try {
                this.mPaymentParams = builder.build();
                this.mPaymentParams = calculateServerSideHashAndInitiatePayment1(this.mPaymentParams);
                String str4 = udf42;
                String str5 = udf5;
                try {
                    PayUmoneyFlowManager.startPayUMoneyFlow(this.mPaymentParams, this, -1, true);
                } catch (Exception e2) {
                }
            } catch (Exception e3) {
                String str6 = udf42;
                String str7 = udf5;
            }
        } catch (Exception e4) {
            String str8 = udf42;
            String str9 = udf5;
        }
    }

    private PaymentParam calculateServerSideHashAndInitiatePayment1(PaymentParam paymentParam) {
        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        StringBuilder sb = new StringBuilder();
        sb.append((String) params.get("key"));
        sb.append("|");
        stringBuilder.append(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append((String) params.get("txnid"));
        sb2.append("|");
        stringBuilder.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append((String) params.get(PayUmoneyConstants.AMOUNT));
        sb3.append("|");
        stringBuilder.append(sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append((String) params.get(PayUmoneyConstants.PRODUCT_INFO));
        sb4.append("|");
        stringBuilder.append(sb4.toString());
        StringBuilder sb5 = new StringBuilder();
        sb5.append((String) params.get(PayUmoneyConstants.FIRSTNAME));
        sb5.append("|");
        stringBuilder.append(sb5.toString());
        StringBuilder sb6 = new StringBuilder();
        sb6.append((String) params.get("email"));
        sb6.append("|");
        stringBuilder.append(sb6.toString());
        StringBuilder sb7 = new StringBuilder();
        sb7.append((String) params.get(PayUmoneyConstants.UDF1));
        sb7.append("|");
        stringBuilder.append(sb7.toString());
        StringBuilder sb8 = new StringBuilder();
        sb8.append((String) params.get(PayUmoneyConstants.UDF2));
        sb8.append("|");
        stringBuilder.append(sb8.toString());
        StringBuilder sb9 = new StringBuilder();
        sb9.append((String) params.get(PayUmoneyConstants.UDF3));
        sb9.append("|");
        stringBuilder.append(sb9.toString());
        StringBuilder sb10 = new StringBuilder();
        sb10.append((String) params.get(PayUmoneyConstants.UDF4));
        sb10.append("|");
        stringBuilder.append(sb10.toString());
        StringBuilder sb11 = new StringBuilder();
        sb11.append((String) params.get(PayUmoneyConstants.UDF5));
        sb11.append("||||||");
        stringBuilder.append(sb11.toString());
        stringBuilder.append("48hhjqw8qW");
        paymentParam.setMerchantHash(Payment.hashCal(stringBuilder.toString()));
        return paymentParam;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data2) {
        int i;
        Intent intent = data2;
        super.onActivityResult(requestCode, resultCode, data2);
        if (requestCode != PayUmoneyFlowManager.REQUEST_CODE_PAYMENT) {
            int i2 = resultCode;
        } else if (resultCode == -1 && intent != null) {
            TransactionResponse transactionResponse = (TransactionResponse) intent.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);
            ResultModel resultModel = (ResultModel) intent.getParcelableExtra("result");
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                transactionResponse.getTransactionStatus().equals(TransactionStatus.SUCCESSFUL);
                String payuResponse = transactionResponse.getPayuResponse();
                try {
                    this.pd.setMessage("Conforming your payment ! Please wait...");
                    this.pd.setCancelable(false);
                    this.pd.show();
                    try {
                        JSONObject jo1 = new JSONObject(payuResponse).getJSONObject("result");
                        JSONObject jSONObject = jo1;
                        i = 0;
                        try {
                            update_paymentsucess(jo1.getString(PayUmoneyConstants.PAYMENT_ID), jo1.getString(PayUmoneyConstants.PAYMENT_MODE), jo1.getString("txnid"), jo1.getString(PayUmoneyConstants.AMOUNT), jo1.getString(PayUmoneyConstants.BANK_CODE), jo1.getString("cardnum"), Temp_Variable.supplierid);
                        } catch (JSONException e) {
                            e = e;
                        }
                    } catch (JSONException e2) {
                        e = e2;
                        i = 0;
                        try {
                            e.printStackTrace();
                        } catch (Exception e3) {
                        }
                    }
                } catch (Exception e4) {
                    i = 0;
                    this.pd.dismiss();
                    Toasty.info(getApplicationContext(), "Please contact to Admin", i).show();
                }
            } else if (resultModel != null) {
                resultModel.getError();
            }
        }
    }

    public void update_paymentsucess(String paymentid, String mode, String txtnid, String amount, String bankcode, String cardnum, String shopId) {
        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("paymentid", paymentid);
                jo.put(PayUmoneyConstants.PAYMENT_MODE, mode);
                jo.put("txtnid", txtnid);
                jo.put(PayUmoneyConstants.AMOUNT, amount);
                jo.put(PayUmoneyConstants.BANK_CODE, bankcode);
                jo.put("cardnum", cardnum);
                jo.put("shopId", shopId);
            } catch (Exception e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Request.Builder builder = new Request.Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/paymentsucess");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Do_Payment.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Do_Payment.this.pd.dismiss();
                            Toasty.info(Do_Payment.this.getApplicationContext(), "Please try later", 0).show();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Do_Payment.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Do_Payment.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Toasty.info(Do_Payment.this.getApplicationContext(), "Please contact admin", 0).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("Update")) {
                                    Toasty.info(Do_Payment.this.getApplicationContext(), "Thank You , Your payment is Successful ! Wallet Updated", 0).show();
                                } else {
                                    Toasty.info(Do_Payment.this.getApplicationContext(), "Please try later", 0).show();
                                }
                            } catch (Exception e) {
                                Toasty.info(Do_Payment.this.getApplicationContext(), "Please try later", 0).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }
}
