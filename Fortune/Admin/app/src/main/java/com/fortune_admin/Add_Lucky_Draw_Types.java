package com.fortune_admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.common.api.GoogleApiClient;
import com.wdullaer.materialdatetimepicker.date.DatePickerController;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class Add_Lucky_Draw_Types extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    ImageView back;
    ConnectionDetecter cd;
    public Dialog dialog;
    Typeface face;
    Typeface face1;
    EditText drawtype,discirption,price,startdate;
    public float ogheight;
    public ProgressBar pb1;
    ProgressDialog pd;
    public TextView persentage;
    ProgressBar prb1;
    public Button stop;
    TextView text,txtdiscirption,txtdrawtype,txtprice,txtstartdate;
    Button update;
    Call call;
    boolean requestgoing=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__lucky__draw__types);
        drawtype = findViewById(R.id.drawtype);
        discirption =findViewById(R.id.discirption);
        price=findViewById(R.id.price);
        txtprice=findViewById(R.id.txtprice);
        txtdiscirption = findViewById(R.id.txtdiscirption);
        txtdrawtype = findViewById(R.id.txtdrawtype);
        startdate=findViewById(R.id.startdate);
        txtstartdate=findViewById(R.id.txtstartdate);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "proximanormal.ttf");
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        prb1 = (ProgressBar) findViewById(R.id.pb1);
        drawtype.setTypeface(face);
        discirption.setTypeface(face);
        startdate.setTypeface(face);
        txtdrawtype.setTypeface(face1);
        txtdiscirption.setTypeface(face1);
        txtstartdate.setTypeface(face1);
        price.setTypeface(face);
        txtprice.setTypeface(face);
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
                    if (drawtype.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter batchname", Toast.LENGTH_SHORT).show();
                        drawtype.requestFocus();
                    }
                    else if (price.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter price", Toast.LENGTH_SHORT).show();
                        price.requestFocus();
                    }
                    else if (discirption.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter discription", Toast.LENGTH_SHORT).show();
                        discirption.requestFocus();
                    } else if (cd.isConnectingToInternet()) {
                        uploadingprogress();
                    } else {
                        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }
        });
        if (Temp.luckydrawtypeedit== 1) {
            drawtype.setText(Temp.ltype_batchname);
            discirption.setText(Temp.ltype_discription);
            price.setText(Temp.ltye_price);
            startdate.setText(Temp.ltype_startdate);
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
        bodyBuilder.addFormDataPart("isedit", null,RequestBody.create(contentType, Temp.luckydrawtypeedit+""));
        bodyBuilder.addFormDataPart("editsn", null,RequestBody.create(contentType, Temp.ltype_sn));
        bodyBuilder.addFormDataPart("batchname", null,RequestBody.create(contentType, drawtype.getText().toString()));
        bodyBuilder.addFormDataPart("discription", null,RequestBody.create(contentType, discirption.getText().toString()));
        bodyBuilder.addFormDataPart("price", null,RequestBody.create(contentType, price.getText().toString()));
        bodyBuilder.addFormDataPart("startdate", null,RequestBody.create(contentType, startdate.getText().toString()));

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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

       startdate.setText(dayOfMonth+"-"+monthOfYear+"-"+year);
    }
}
