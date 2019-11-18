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

public class Add_Bus extends AppCompatActivity {
    ImageView back;
    TextView text;
    EditText title,station,fromstation,tostation,time;
    TextView txttitle,txtstation,txtfromstation,txttostation,txttime;
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
        setContentView(R.layout.activity_add__bus);
        back = (ImageView) findViewById(R.id.back);
        text = (TextView) findViewById(R.id.text);
        update = (Button) findViewById(R.id.update);
        cd = new ConnectionDetecter(this);
        prb1 = (ProgressBar) findViewById(R.id.pb1);
        pd = new ProgressDialog(this);
        title=findViewById(R.id.title);
        station=findViewById(R.id.station);
        fromstation=findViewById(R.id.fromstation);
        tostation=findViewById(R.id.tostation);
        time=findViewById(R.id.time);
        txttitle=findViewById(R.id.txttitle);
        txtstation=findViewById(R.id.txtstation);
        txtfromstation=findViewById(R.id.txtfromstation);
        txttostation=findViewById(R.id.txttostation);
        txttime=findViewById(R.id.txttime);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        text.setTypeface(face);
        title.setTypeface(face);
        station.setTypeface(face);
        fromstation.setTypeface(face);
        tostation.setTypeface(face);
        time.setTypeface(face);
        txttitle.setTypeface(face);
        txtstation.setTypeface(face);
        txtfromstation.setTypeface(face);
        txttostation.setTypeface(face);
        txttime.setTypeface(face);
        update.setTypeface(face);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(Temp.is_train_bus.equalsIgnoreCase("1"))
        {
            text.setText("Add / Edit Bus");
            txtstation.setText("Bus Stop");
            txtfromstation.setText("From Stop");
            txttostation.setText("To Stop");
        }
        else if(Temp.is_train_bus.equalsIgnoreCase("2"))
        {
            text.setText("Add / Edit Train");
            txtstation.setText("Station");
            txtfromstation.setText("From Station");
            txttostation.setText("To Station");
        }



        if (Temp.bus_trainedit== 1) {

            title.setText(Temp.bus_busname);
            station.setText(Temp.bus_station);
            fromstation.setText(Temp.bus_fromstation);
            tostation.setText(Temp.bus_tostation);
            time.setText(Temp.bus_times);
        }
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!cd.isConnectingToInternet()) {
                    Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                } else if (title.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Title", Toast.LENGTH_SHORT).show();
                    title.requestFocus();
                }
                else if (station.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Station Name", Toast.LENGTH_SHORT).show();
                    station.requestFocus();
                }
                else if (fromstation.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter From", Toast.LENGTH_SHORT).show();
                    fromstation.requestFocus();
                }
                else if (tostation.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter To", Toast.LENGTH_SHORT).show();
                    tostation.requestFocus();
                }
                else if (time.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Time", Toast.LENGTH_SHORT).show();
                    time.requestFocus();
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


        bodyBuilder.addFormDataPart("isedit", null, RequestBody.create(contentType, Temp.bus_trainedit+""));
        bodyBuilder.addFormDataPart("editsn", null,RequestBody.create(contentType, Temp.bus_sn));
        bodyBuilder.addFormDataPart("types", null,RequestBody.create(contentType, Temp.is_train_bus));
        bodyBuilder.addFormDataPart("title", null,RequestBody.create(contentType, title.getText().toString()));
        bodyBuilder.addFormDataPart("station", null,RequestBody.create(contentType, station.getText().toString()));
        bodyBuilder.addFormDataPart("fromstation", null,RequestBody.create(contentType, fromstation.getText().toString()));
        bodyBuilder.addFormDataPart("tostation", null,RequestBody.create(contentType, tostation.getText().toString()));
        bodyBuilder.addFormDataPart("time", null,RequestBody.create(contentType, time.getText().toString()));

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
                .url(Temp.weblink+"add_trainorbus.php")
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
