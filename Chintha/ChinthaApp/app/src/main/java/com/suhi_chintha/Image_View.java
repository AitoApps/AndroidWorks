package com.suhi_chintha;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.InputStream;
import java.net.URL;

public class Image_View extends AppCompatActivity {
    NetConnection cd;
    Bitmap bitmap;
    Zoomable_image image;
    ImageView back;
    TextView text;
    final DataDB1 dataDb1 =new DataDB1(this);
    final DataDB4 dataDb4 =new DataDB4(this);
    LottieAnimationView loadingicon;
    public AdView adView1;
    AdRequest adreq1;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageview_actvty);

        adView1=findViewById(R.id.adView1);
        adreq1 = new AdRequest.Builder().build();

        loadingicon=findViewById(R.id.lotty_loadin);
        cd=new NetConnection(this);
        image= findViewById(R.id.image);
        back= findViewById(R.id.moveback);
        text= findViewById(R.id.text);
        text.setText(Static_Variable.username);

        try
        {
            adView1.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    try
                    {
                        if(count<=10)
                        {
                            adView1.loadAd(adreq1);
                            count++;
                        }


                    }
                    catch (Exception a)
                    {

                    }

                }
            });
        }
        catch (Exception a)
        {

        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });

        if(cd.isConnectingToInternet())
        {
            new laodinImage1().execute(Static_Variable.entypoint1 +"userphoto/"+ Static_Variable.userid+".jpg");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        count=0;
        try
        {
            adView1.loadAd(adreq1);

        }
        catch (Exception a)
        {

        }
    }

    private class laodinImage1 extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bitmap=null;
            loadingicon.setVisibility(View.VISIBLE);

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image1) {
            loadingicon.setVisibility(View.GONE);
            if(image1 != null){
                image.setVisibility(View.VISIBLE);
                image.setImageBitmap(image1);
                image.setScaleType(ScaleType.FIT_XY);
                image.setAdjustViewBounds(true);
            }else{

            }

        }
    }

}


