package com.fortune_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import io.github.lizhangqu.coreprogress.ProgressHelper;
import io.github.lizhangqu.coreprogress.ProgressUIListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Add_Agents extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    public Dialog dialog;
    Typeface face;
    Typeface face1;
    EditText name,mobile,agentid;
    public float ogheight;
    public ProgressBar pb1;
    ProgressDialog pd;
    public TextView persentage;
    ProgressBar prb1;
    public Button stop;
    TextView text;
    Button update;
    Call call;
    boolean requestgoing=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__agents);
        name=findViewById(R.id.name);
        mobile=findViewById(R.id.mobile);
        agentid=findViewById(R.id.agentid);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "proximanormal.ttf");
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        prb1 = (ProgressBar) findViewById(R.id.pb1);
        name.setTypeface(face);
        mobile.setTypeface(face);
        agentid.setTypeface(face);
        update = (Button) findViewById(R.id.update);
        text = (TextView) findViewById(R.id.text);
        text.setTypeface(face);
        update.setTypeface(face);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    if (name.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter name", Toast.LENGTH_SHORT).show();
                        name.requestFocus();
                    } else if (mobile.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter mobile", Toast.LENGTH_SHORT).show();
                        mobile.requestFocus();
                    }
                    else if (agentid.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter agent id", Toast.LENGTH_SHORT).show();
                        agentid.requestFocus();
                    }
                    else if (cd.isConnectingToInternet()) {
                        uploadingprogress();
                    } else {
                        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }
        });
        if (Temp.agentedit== 1) {
            name.setText(Temp.agent_name);
            mobile.setText(Temp.agent_mobile);
            agentid.setText(Temp.agent_agentid);
        }
    }

    public void uploadingprogress() {
        try {
            dialog = new Dialog(this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialogupload);
            pb1 = (ProgressBar) dialog.findViewById(R.id.pb1);
            persentage = (TextView) dialog.findViewById(R.id.persentage);
            stop = (Button) dialog.findViewById(R.id.stop);
            uploadfiletoserver();
            stop.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        requestgoing=false;
                        call.cancel();
                        dialog.dismiss();
                    } catch (Exception e) {
                    }
                }
            });
            dialog.show();
        } catch (Exception e) {
        }
    }

    public void uploadfiletoserver()
    {
        requestgoing=true;
        pb1.setVisibility(View.VISIBLE);
        dialog.setCancelable(false);
        persentage.setVisibility(View.VISIBLE);
        update.setEnabled(false);

        MediaType contentType=MediaType.parse("text/plain; charset=utf-8");
        OkHttpClient client;
        OkHttpClient.Builder client1 = new OkHttpClient.Builder();
        client1.connectTimeout(5, TimeUnit.MINUTES);
        client1.readTimeout(5,TimeUnit.MINUTES);
        client1.writeTimeout(5,TimeUnit.MINUTES);

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);
        bodyBuilder.addFormDataPart("isedit", null, RequestBody.create(contentType, Temp.agentedit+""));
        bodyBuilder.addFormDataPart("editsn", null,RequestBody.create(contentType, Temp.agent_sn));
        bodyBuilder.addFormDataPart("name", null,RequestBody.create(contentType, name.getText().toString()));
        bodyBuilder.addFormDataPart("mobile", null,RequestBody.create(contentType,mobile.getText().toString()));
        bodyBuilder.addFormDataPart("agentid", null,RequestBody.create(contentType,agentid.getText().toString()));

        MultipartBody body = bodyBuilder.build();

        RequestBody requestBody = ProgressHelper.withProgress(body, new ProgressUIListener() {

            //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
            @Override
            public void onUIProgressStart(long totalBytes) {
                super.onUIProgressStart(totalBytes);

            }

            @Override
            public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                persentage.setText((int) (100 * percent)+"%");
                //progress.setText("numBytes:" + numBytes + " bytes" + "\ntotalBytes:" + totalBytes + " bytes" + "\npercent:" + percent * 100 + " %" + "\nspeed:" + speed * 1000 / 1024 / 1024 + "  MB/秒");
            }
            @Override
            public void onUIProgressFinish() {
                super.onUIProgressFinish();

            }

        });
        Request request = new Request.Builder()
                .url(Temp.weblink+"addluckdrawbatchs.php")
                .post(requestBody)
                .build();
        client = client1.build();
        call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb1.setVisibility(View.GONE);
                        persentage.setVisibility(View.GONE);
                        update.setEnabled(true);
                        dialog.dismiss();
                        pd.dismiss();
                        if(requestgoing==true)
                        {
                            Toast.makeText(getApplicationContext(),"Please try later",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
            @Override
            public void onResponse(Call call, final Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            String result=response.body().string();


                            pb1.setVisibility(View.GONE);
                            persentage.setVisibility(View.GONE);
                            update.setEnabled(true);
                            dialog.dismiss();
                            if (result.contains("ok")) {
                                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                finish();
                                return;
                            }
                            else if(result.contains("exist"))
                            {
                                Toast.makeText(getApplicationContext(), "Sorry agent id already exist", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (Exception a)
                        {

                        }
                    }
                });
            }
        });

    }
}
