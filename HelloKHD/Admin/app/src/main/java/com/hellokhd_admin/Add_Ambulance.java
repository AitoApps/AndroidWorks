package com.hellokhd_admin;

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

public class Add_Ambulance extends AppCompatActivity {

    ImageView back;
    TextView text;
    EditText ambulance,contact;
    ConnectionDetecter cd;
    public Button stop;
    Button update;
    Call call;
    boolean requestgoing=true;
    public ProgressBar pb1;
    ProgressDialog pd;
    public TextView persentage;
    ProgressBar prb1;
    public Dialog dialog;
    Typeface face;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__ambulance);

        back = (ImageView) findViewById(R.id.back);
        text = (TextView) findViewById(R.id.text);
        update = (Button) findViewById(R.id.update);
        cd = new ConnectionDetecter(this);
        prb1 = (ProgressBar) findViewById(R.id.pb1);
        pd = new ProgressDialog(this);
        ambulance=findViewById(R.id.ambulance);
        contact=findViewById(R.id.contact);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        text.setTypeface(face);
        ambulance.setTypeface(face);
        contact.setTypeface(face);
        update.setTypeface(face);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(Temp.is_amb_trans.equalsIgnoreCase("1"))
        {
            text.setText("Add / Edit Ambulance");
        }
        else if(Temp.is_amb_trans.equalsIgnoreCase("2"))
        {
            text.setText("Add / Edit Transportation");
        }
        else if(Temp.is_amb_trans.equalsIgnoreCase("3"))
        {
            text.setText("Add / Edit Help Desk");
        }
        if (Temp.amb_trasnedit== 1) {
            ambulance.setText(Temp.amb_title);
            contact.setText(Temp.amb_contact);
        }
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!cd.isConnectingToInternet()) {
                    Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                } else if (ambulance.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Title", Toast.LENGTH_SHORT).show();
                    ambulance.requestFocus();
                }
                else if (contact.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Contact number", Toast.LENGTH_SHORT).show();
                    contact.requestFocus();
                }
                else {
                    uploadingprogress();
                }
            }
        });
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

        bodyBuilder.addFormDataPart("isedit", null, RequestBody.create(contentType, Temp.amb_trasnedit+""));
        bodyBuilder.addFormDataPart("editsn", null,RequestBody.create(contentType, Temp.amb_sn));
        bodyBuilder.addFormDataPart("types", null,RequestBody.create(contentType, Temp.is_amb_trans));
        bodyBuilder.addFormDataPart("title", null,RequestBody.create(contentType, ambulance.getText().toString()));
        bodyBuilder.addFormDataPart("contact", null,RequestBody.create(contentType, contact.getText().toString()));

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
                .url(Temp.weblink+"addambtrans.php")
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
                            pd.dismiss();
                            if (result.contains("ok")) {

                                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                finish();
                                return;
                            } else {
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

