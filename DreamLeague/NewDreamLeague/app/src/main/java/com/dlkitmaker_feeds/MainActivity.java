package com.dlkitmaker_feeds;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.Adapter_KitHead;
import data.Feed_KitHeads;

public class MainActivity extends AppCompatActivity {

    List<Feed_KitHeads> items;
    RecyclerView recylerview;
    ImageView nointernet;
    ProgressDialog pd;
    final DB db=new DB(this);
    Dialog dialog;
    public AdView adView1;
    AdRequest adreq1;
    AdRequest adreq;
    private InterstitialAd intestrial;
    int count=0;
    int intcount=0;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pd=new ProgressDialog(this);


        items = new ArrayList<>();

        items.add(new Feed_KitHeads(R.drawable.kuchalana));
        items.add(new Feed_KitHeads(R.drawable.dlscenter));
        items.add(new Feed_KitHeads(R.drawable.dlsvn));
        items.add(new Feed_KitHeads(R.drawable.dlssoccer));
        items.add(new Feed_KitHeads(R.drawable.dlssoccer2));
        items.add(new Feed_KitHeads(R.drawable.dream11));
        items.add(new Feed_KitHeads(R.drawable.dls));

        back=findViewById(R.id.back);
        adView1=findViewById(R.id.adView1);
        intestrial = new InterstitialAd(MainActivity.this);
        intestrial.setAdUnitId("ca-app-pub-5517777745693327/8411597433");
        adreq = new AdRequest.Builder().build();
        adreq1 = new AdRequest.Builder().build();

        nointernet=findViewById(R.id.nointernet);
        recylerview=findViewById(R.id.recylerview);

        recylerview.setAdapter(new Adapter_KitHead(MainActivity.this,items, this));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recylerview.setLayoutManager(gridLayoutManager);


        try
        {
            adView1.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    try
                    {
                        if(count<=20)
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

            intestrial.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {

                    if(intcount<=20) {
                        intestrial.loadAd(adreq);
                        intcount++;
                    }

                }
            });

        }
        catch (Exception a)
        {

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ArrayList<String> id1=db.get_teamlist1();
        String[] c1=id1.toArray(new String[id1.size()]);

        if(c1.length<=0)
        {
            new teamlistload().execute();
        }



    }
    @Override
    protected void onResume() {
        super.onResume();
        if(dialog!=null && dialog.isShowing())
        {
           dialog.dismiss();

        }
        count=0;
        intcount=0;
        try
        {
            adView1.loadAd(adreq1);
            intestrial.loadAd(adreq);
        }
        catch (Exception a)
        {

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(intestrial.isLoaded())
        {
            intestrial.show();
        }
    }

    public class teamlistload extends AsyncTask<String,Void,String> {

        protected void onPreExecute(){
            pd.setMessage("Loading Team List...");
            pd.setCancelable(false);
            pd.show();

        }
        @Override
        protected String doInBackground(String... arg0) {

            try{

                for(int i = 0; i< Kit_Data.kitdata.length; i++)
                {
                    int a=i;
                    i++;
                    int a1=i;
                    i++;
                    int a2=i;

                    db.add_teamlist(Kit_Data.kitdata[a], Kit_Data.kitdata[a1], Kit_Data.kitdata[a2]);

                }

                return "ok";

            }

            catch(Exception e) {

                return new String("Exception: " + e.getMessage());



            }
        }

        @Override
        protected void onPostExecute(String result){

            ArrayList<String> id1=db.get_teamlist1();
            String[] c1=id1.toArray(new String[id1.size()]);

            pd.dismiss();

        }
    }


}
