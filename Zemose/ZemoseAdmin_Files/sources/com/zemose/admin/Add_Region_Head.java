package com.zemose.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
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

public class Add_Region_Head extends AppCompatActivity {
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ImageView back;
    ConnectionDetecter cd;
    EditText headname;
    EditText password;
    ProgressDialog pd;
    Button update;
    EditText username;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_add__region__head);
        this.back = (ImageView) findViewById(R.id.back);
        this.headname = (EditText) findViewById(R.id.headname);
        this.username = (EditText) findViewById(R.id.username);
        this.password = (EditText) findViewById(R.id.password);
        this.update = (Button) findViewById(R.id.update);
        this.cd = new ConnectionDetecter(this);
        this.pd = new ProgressDialog(this);
        if (Temp_Variable.regionhededit == 1) {
            this.headname.setText(Temp_Variable.regionheadname);
            this.username.setText(Temp_Variable.regionheadusername);
            this.password.setText(Temp_Variable.regionheadpassword);
        }
        this.update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!Add_Region_Head.this.cd.isConnectingToInternet()) {
                    Toast.makeText(Add_Region_Head.this.getApplicationContext(), Temp_Variable.nointernet, 0).show();
                } else if (Add_Region_Head.this.headname.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(Add_Region_Head.this.getApplicationContext(), "Please enter headname", 0).show();
                    Add_Region_Head.this.headname.requestFocus();
                } else if (Add_Region_Head.this.username.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(Add_Region_Head.this.getApplicationContext(), "Please enter username", 0).show();
                    Add_Region_Head.this.username.requestFocus();
                } else if (Add_Region_Head.this.password.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(Add_Region_Head.this.getApplicationContext(), "Please enter password", 0).show();
                    Add_Region_Head.this.password.requestFocus();
                } else {
                    Add_Region_Head.this.addregion();
                }
            }
        });
    }

    public void addregion() {
        String url;
        try {
            this.pd.setMessage("Please wait..");
            this.pd.setCancelable(false);
            this.pd.show();
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("regionheadId", Temp_Variable.regionheadid);
                jo.put("regionId", Temp_Variable.regionid);
                jo.put("headname", this.headname.getText().toString());
                jo.put(PayUmoneyConstants.USER_NAME, this.username.getText().toString());
                jo.put(PayUmoneyConstants.PASSWORD, this.password.getText().toString());
            } catch (JSONException e) {
            }
            String str = "";
            if (Temp_Variable.regionhededit == 0) {
                url = "appadmin/addregionhead";
            } else {
                url = "appadmin/updateregionhead";
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Builder builder = new Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append(url);
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Add_Region_Head.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Add_Region_Head.this.pd.dismiss();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Add_Region_Head.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Add_Region_Head.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Toasty.info(Add_Region_Head.this.getApplicationContext(), "Please contact admin", 0).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("exit")) {
                                    Toasty.info(Add_Region_Head.this.getApplicationContext(), "Username already exist", 0).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("Update")) {
                                    Toasty.info(Add_Region_Head.this.getApplicationContext(), "Updated", 0).show();
                                    Add_Region_Head.this.finish();
                                } else {
                                    Toasty.info(Add_Region_Head.this.getApplicationContext(), "Please try later", 0).show();
                                }
                            } catch (Exception e) {
                                Toasty.info(Add_Region_Head.this.getApplicationContext(), "Please try later", 0).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }
}
