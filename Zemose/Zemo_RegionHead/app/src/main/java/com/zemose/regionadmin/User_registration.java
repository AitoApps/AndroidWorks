package com.zemose.regionadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User_registration extends AppCompatActivity {
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    int PERMISSION_ALL = 1;
    ImageView back;
    ConnectionDetecter cd;
    Button login;
    EditText password;
    ProgressDialog pd;
    final UserDB udb = new UserDB(this);
    EditText username;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_user_registration);
        FirebaseApp.initializeApp(this);
        this.back = (ImageView) findViewById(R.id.back);
        this.username = (EditText) findViewById(R.id.username);
        this.password = (EditText) findViewById(R.id.password);
        this.login = (Button) findViewById(R.id.login);
        this.cd = new ConnectionDetecter(this);
        this.pd = new ProgressDialog(this);
        String[] PERMISSIONS = {"android.permission.INTERNET", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_NETWORK_STATE", "android.permission.ACCESS_WIFI_STATE", "android.permission.CALL_PHONE"};
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, this.PERMISSION_ALL);
        }
        this.login.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!User_registration.this.cd.isConnectingToInternet()) {
                    Toast.makeText(User_registration.this.getApplicationContext(), Temp_Variable.nointernet, Toast.LENGTH_SHORT).show();
                } else if (User_registration.this.username.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(User_registration.this.getApplicationContext(), "Please enter username", Toast.LENGTH_SHORT).show();
                    User_registration.this.username.requestFocus();
                } else if (User_registration.this.password.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(User_registration.this.getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                    User_registration.this.password.requestFocus();
                } else {
                    FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            User_registration.this.pd.setMessage("Please wait...");
                            User_registration.this.pd.setCancelable(false);
                            User_registration.this.pd.show();
                            if (!task.isSuccessful()) {
                                User_registration.this.pd.dismiss();
                                Toast.makeText(User_registration.this.getApplicationContext(), "Please try after 10 minutes", 1).show();
                                return;
                            }
                            User_registration.this.udb.addfcmid(((InstanceIdResult) task.getResult()).getToken());
                            User_registration.this.checkusername();
                        }
                    });
                }
            }
        });
    }

    public void checkusername() {
        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put(PayUmoneyConstants.USER_NAME, this.username.getText().toString());
                jo.put(PayUmoneyConstants.PASSWORD, this.password.getText().toString());
                jo.put("fcmid", this.udb.getfcmid());
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Builder builder = new Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/checkregionheadlogin");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    User_registration.this.runOnUiThread(new Runnable() {
                        public void run() {
                            User_registration.this.pd.dismiss();
                            Toast.makeText(User_registration.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    User_registration.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                User_registration.this.pd.dismiss();
                                String result = response.body().string();
                                JSONObject jo = new JSONObject(result);
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Toasty.info(User_registration.this.getApplicationContext(), "Please contact admin", Toast.LENGTH_SHORT).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("nomatch")) {
                                    Toasty.info(User_registration.this.getApplicationContext(), "Please check your username or password", Toast.LENGTH_SHORT).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("got")) {
                                    JSONArray jsonarray = jo.getJSONArray("data");
                                    if (jsonarray.length() > 0) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(0);
                                        User_registration.this.udb.addregion(jsonobject.getString("regionheadId"), jsonobject.getString("regionId"), jsonobject.getString("username"),jsonobject.getString("headname"));
                                        User_registration.this.startActivity(new Intent(User_registration.this.getApplicationContext(), MainActivity.class));
                                        User_registration.this.finish();
                                    }
                                } else {
                                    Toasty.info(User_registration.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                User_registration.this.pd.dismiss();
                                Toasty.info(User_registration.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (!(context == null || permissions == null)) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
