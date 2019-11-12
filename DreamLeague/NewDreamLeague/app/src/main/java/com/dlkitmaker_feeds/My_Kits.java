package com.dlkitmaker_feeds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

import adapter.Adapter_MyKitview;
import data.Feed_MyKits;

public class My_Kits extends AppCompatActivity {
    private Adapter_MyKitview listAdapter;
    private List<Feed_MyKits> feedItems;
    RecyclerView recylerview;
    final DB db=new DB(this);
    public AdView adView1;
    AdRequest adreq1;
    AdRequest adreq;
    private InterstitialAd intestrial;
    int count=0;
    int intcount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvty_mykits);
        recylerview=(RecyclerView)findViewById(R.id.recylerview);
        feedItems = new ArrayList<Feed_MyKits>();
        listAdapter = new Adapter_MyKitview(this,feedItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerview.setLayoutManager(linearLayoutManager);
        recylerview.setAdapter(listAdapter);

        adView1=findViewById(R.id.adView1);
        intestrial = new InterstitialAd(My_Kits.this);
        intestrial.setAdUnitId("ca-app-pub-5517777745693327/8411597433");
        adreq = new AdRequest.Builder().build();
        adreq1 = new AdRequest.Builder().build();

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

        loaddata();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    public void loaddata()
    {

        feedItems.clear();
        ArrayList<String> id1=db.get_mykits();
        String[] got=id1.toArray(new String[id1.size()]);

        for(int i=0;i<got.length;i++)
        {
            Feed_MyKits item = new Feed_MyKits();
            item.setimgurl(got[i]);
            feedItems.add(item);
        }
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(intestrial.isLoaded())
        {
            intestrial.show();
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
