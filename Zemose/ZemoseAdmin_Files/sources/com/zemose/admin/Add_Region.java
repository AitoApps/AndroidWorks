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

public class Add_Region extends AppCompatActivity {
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ImageView back;
    ConnectionDetecter cd;
    ProgressDialog pd;
    EditText region;
    Button update;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_add__region);
        this.back = (ImageView) findViewById(R.id.back);
        this.region = (EditText) findViewById(R.id.region);
        this.update = (Button) findViewById(R.id.update);
        this.cd = new ConnectionDetecter(this);
        this.pd = new ProgressDialog(this);
        if (Temp_Variable.regionedit == 1) {
            this.region.setText(Temp_Variable.regionname);
        }
        this.update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!Add_Region.this.cd.isConnectingToInternet()) {
                    Toast.makeText(Add_Region.this.getApplicationContext(), Temp_Variable.nointernet, 0).show();
                } else if (Add_Region.this.region.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(Add_Region.this.getApplicationContext(), "Please enter region name", 0).show();
                    Add_Region.this.region.requestFocus();
                } else {
                    Add_Region.this.addregion();
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
                jo.put("countryid", Region_Management.txt_countryid);
                jo.put("countrycode", Region_Management.txt_countrycode);
                jo.put("regioname", this.region.getText().toString());
                jo.put("regionid", Temp_Variable.regionid);
            } catch (JSONException e) {
            }
            String str = "";
            if (Temp_Variable.regionedit == 0) {
                url = "appadmin/addregion";
            } else {
                url = "appadmin/updateregion";
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Builder builder = new Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append(url);
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Add_Region.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Add_Region.this.pd.dismiss();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Add_Region.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Add_Region.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Toasty.info(Add_Region.this.getApplicationContext(), "Please contact admin", 0).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("Update")) {
                                    Toasty.info(Add_Region.this.getApplicationContext(), "Updated", 0).show();
                                    Add_Region.this.finish();
                                } else {
                                    Toasty.info(Add_Region.this.getApplicationContext(), "Please try later", 0).show();
                                }
                            } catch (Exception e) {
                                Toasty.info(Add_Region.this.getApplicationContext(), "Please try later", 0).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }
}
