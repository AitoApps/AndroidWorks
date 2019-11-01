package com.demo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
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

public class MainActivity extends AppCompatActivity {
    AdRequest adRequest1;
    AdView adview1;
    TextView progress;
    int PERMISSION_ALL = 1;
    Call call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-8933294539595122~5710315462");
        adview1 = (AdView) findViewById(R.id.adView1);
        progress=findViewById(R.id.progress);
        adRequest1 = new AdRequest.Builder().build();
        adview1.loadAd(adRequest1);
      /*  adview1.setAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Toast.makeText(getApplicationContext(),i+"",Toast.LENGTH_SHORT).show();
                adview1.loadAd(adRequest1);
            }
        });*/

        String[] PERMISSIONS = {android.Manifest.permission.INTERNET,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.CALL_PHONE};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        else
        {
            uploadfiletoserver();
        }


        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                call.cancel();
            }
        });
       /* File f=new File(Environment.getExternalStorageDirectory()+"/Alarms/abcf.jpeg");
        if(f.exists())
        {
            Toast.makeText(getApplicationContext(),"Exists",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Not Exists",Toast.LENGTH_SHORT).show();
        }*/
    }



    public void uploadfiletoserver()
    {

        File sourcefile=new File(Environment.getExternalStorageDirectory()+"/Alarms/abc.jpg");
        MediaType contentType=MediaType.parse("text/plain; charset=utf-8");
        OkHttpClient client;
        OkHttpClient.Builder client1 = new OkHttpClient.Builder();
        client1.connectTimeout(5,TimeUnit.MINUTES);
        client1.readTimeout(5,TimeUnit.MINUTES);
        client1.writeTimeout(5,TimeUnit.MINUTES);

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("photo1", sourcefile.getName(),RequestBody.create(MediaType.parse("image/png"), sourcefile))
                .addFormDataPart("isedit", null,RequestBody.create(contentType, "vvvvvok"))
                .addFormDataPart("image1", null,RequestBody.create(contentType, "filled"))
                .build();

        RequestBody requestBody = ProgressHelper.withProgress(body, new ProgressUIListener() {

            //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
            @Override
            public void onUIProgressStart(long totalBytes) {
                super.onUIProgressStart(totalBytes);

            }

            @Override
            public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {

                progress.setText((int) (100 * percent)+"%");
                //uploadProgress.setProgress((int) (100 * percent));
                //progress.setText("numBytes:" + numBytes + " bytes" + "\ntotalBytes:" + totalBytes + " bytes" + "\npercent:" + percent * 100 + " %" + "\nspeed:" + speed * 1000 / 1024 / 1024 + "  MB/秒");
            }
            @Override
            public void onUIProgressFinish() {
                super.onUIProgressFinish();

            }

        });

        Request request = new Request.Builder()
                .url("http://apistatuschinthakal.in/fishapp/addnewadvtadminsample.php")
                .post(requestBody)
                .build();
        client = client1.build();
         call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.w("Resukt1",Log.getStackTraceString(e));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getApplicationContext(),"Please try later",Toast.LENGTH_SHORT).show();
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
                            Log.w("Resukt1",result);
                        }
                        catch (Exception a)
                        {

                        }
                    }
                });
            }
        });



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
