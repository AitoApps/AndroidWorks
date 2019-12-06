package com.zemose.admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
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

public class Registration extends AppCompatActivity {
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ConnectionDetecter cd;
    Button login;
    ProgressDialog pd;
    EditText skey;
    UserDB udb = new UserDB(this);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_registration);
        this.skey = (EditText) findViewById(R.id.skey);
        this.login = (Button) findViewById(R.id.login);
        this.pd = new ProgressDialog(this);
        this.cd = new ConnectionDetecter(this);
        this.login.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Registration.this.skey.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(Registration.this.getApplicationContext(), "Please enter secret key", 0).show();
                    Registration.this.skey.requestFocus();
                } else if (Registration.this.cd.isConnectingToInternet()) {
                    Registration.this.pd.setMessage("Please wait...");
                    Registration.this.pd.setCancelable(false);
                    Registration registration = Registration.this;
                    registration.timerDelayRemoveDialog(30000, registration.pd);
                    Registration.this.pd.show();
                    Registration.this.registration();
                } else {
                    Toasty.info(Registration.this.getApplicationContext(), "Please make sure your internet connection is active", 0).show();
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

    public void registration() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Registration.this.pd.dismiss();
                    Toast.makeText(Registration.this.getApplicationContext(), "Please update playservice", 1).show();
                    return;
                }
                Registration.this.udb.addfcmid(((InstanceIdResult) task.getResult()).getToken());
                OkHttpClient client = new OkHttpClient();
                JSONObject jo = new JSONObject();
                try {
                    jo.put("skey", Registration.this.skey.getText().toString());
                    jo.put("fcmId", Registration.this.udb.getfcmid());
                } catch (JSONException e) {
                }
                RequestBody body = RequestBody.create(Registration.this.JSON, jo.toString());
                Builder builder = new Builder();
                StringBuilder sb = new StringBuilder();
                sb.append(Temp_Variable.baseurl);
                sb.append("appadmin/adminreg");
                client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                    public void onFailure(Call call, IOException e) {
                        Registration.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Registration.this.pd.dismiss();
                                Toasty.info(Registration.this.getApplicationContext(), "Please try later", 0).show();
                            }
                        });
                    }

                    public void onResponse(Call call, final Response response) throws IOException {
                        Registration.this.runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    Registration.this.pd.dismiss();
                                    JSONObject jo = new JSONObject(response.body().string());
                                    if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                        Toasty.info(Registration.this.getApplicationContext(), "Please check your secret key", 0).show();
                                    } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("Ok")) {
                                        Toasty.info(Registration.this.getApplicationContext(), "Registration Successfull", 0).show();
                                        Registration.this.udb.adduser("OKK");
                                        Registration.this.startActivity(new Intent(Registration.this.getApplicationContext(), MainActivity.class));
                                        Registration.this.finish();
                                    } else {
                                        Toasty.info(Registration.this.getApplicationContext(), "Please try later", 0).show();
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}
