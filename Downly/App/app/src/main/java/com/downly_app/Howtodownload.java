package com.downly_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

import Downly_Data.Feed_Howtodownload;
import Downly_Data.Feed_WP;
import Downly_adapter.Adapter_DownloadList;
import Downly_adapter.Adapter_Howtodownload;
import Downly_adapter.Adapter_Whatsapplist;

public class Howtodownload extends AppCompatActivity {
    ListView list;
    ImageView back;
    TextView title;
    private List<Feed_Howtodownload> feedItems;
    private Adapter_Howtodownload listAdapter;
    Typeface face;

    public InterstitialAd interstitial;
    AdRequest adRequest;
    AdRequest adRequest1;
    public AdView adView1;
    int count = 0;
    int intcount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtodownload);
        adView1 = (AdView) findViewById(R.id.adView1);
        face = Typeface.createFromAsset(getAssets(), "commonfont.otf");
        list=findViewById(R.id.list);
        back=findViewById(R.id.back);
        title=findViewById(R.id.title);
        feedItems = new ArrayList();
        listAdapter = new Adapter_Howtodownload(this, feedItems);
        list.setAdapter(listAdapter);

        title.setTypeface(face);
        title.setText(Temp.helptitle);

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-8933294539595122/5629841256");
        adRequest = new AdRequest.Builder().build();
        adRequest1 = new AdRequest.Builder().build();
        try {
            adView1.setAdListener(new AdListener() {
                public void onAdFailedToLoad(int errorCode) {
                    try {
                        if (count <= 10) {
                            adView1.loadAd(adRequest);
                            count++;
                        }
                    } catch (Exception e) {
                    }
                }
            });
            interstitial.setAdListener(new AdListener() {
                public void onAdFailedToLoad(int errorCode) {
                    if (intcount <= 10) {
                        interstitial.loadAd(adRequest1);
                        intcount++;
                    }
                }
            });
        } catch (Exception e) {
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(Temp.help.equalsIgnoreCase("1"))
        {
            String[] youtubetext={"Open youtube app by pressing this button","Click On Share Button","Click On Copy Link","Wait for some time","Download Your Video"};
            Drawable[] youtubeicon={getResources().getDrawable(R.drawable.new_yt1),
                    getResources().getDrawable(R.drawable.new_yt2),
                    getResources().getDrawable(R.drawable.new_yt3),
                    getResources().getDrawable(R.drawable.new_yt4),
                    getResources().getDrawable(R.drawable.new_yt5)};

            if(Temp.help.equalsIgnoreCase("1"))
            {
                for(int i=0;i<youtubetext.length;i++)
                {
                    Feed_Howtodownload item=new Feed_Howtodownload();
                    item.setTitle(youtubetext[i]);
                    item.setImage(youtubeicon[i]);
                    feedItems.add(item);
                }
                listAdapter.notifyDataSetChanged();
            }
        }

        else if(Temp.help.equalsIgnoreCase("2"))
        {
            String[] youtubetext={"Open facebook app by pressing this button","Click On Option Button","Click On Copy Link","Wait for some time","Download Your Video"};
            Drawable[] youtubeicon={getResources().getDrawable(R.drawable.new_fb1),
                    getResources().getDrawable(R.drawable.new_fb2),
                    getResources().getDrawable(R.drawable.new_fb3),
                    getResources().getDrawable(R.drawable.new_fb4),
                    getResources().getDrawable(R.drawable.new_fb5)};

                for(int i=0;i<youtubetext.length;i++)
                {
                    Feed_Howtodownload item=new Feed_Howtodownload();
                    item.setTitle(youtubetext[i]);
                    item.setImage(youtubeicon[i]);
                    feedItems.add(item);
                }
                listAdapter.notifyDataSetChanged();

        }
        else if(Temp.help.equalsIgnoreCase("3"))
        {
            String[] youtubetext={"Open instagram app by pressing this button","Click On Option Button","Click On Copy Link","Download will start soon , Please wait"};
            Drawable[] youtubeicon={getResources().getDrawable(R.drawable.new_insta1),
                    getResources().getDrawable(R.drawable.new_insta2),
                    getResources().getDrawable(R.drawable.new_insta3),
                    getResources().getDrawable(R.drawable.new_insta4)};

            for(int i=0;i<youtubetext.length;i++)
            {
                Feed_Howtodownload item=new Feed_Howtodownload();
                item.setTitle(youtubetext[i]);
                item.setImage(youtubeicon[i]);
                feedItems.add(item);
            }
            listAdapter.notifyDataSetChanged();

        }
        else if(Temp.help.equalsIgnoreCase("4"))
        {
            String[] youtubetext={"Open Tiktok app by pressing this button","Click On Share Button","Click On Copy Link","Wait for some time","Download Your Video"};
            Drawable[] youtubeicon={getResources().getDrawable(R.drawable.new_tik1),
                    getResources().getDrawable(R.drawable.new_tick2),
                    getResources().getDrawable(R.drawable.new_tick3),
                    getResources().getDrawable(R.drawable.new_tick4),
                    getResources().getDrawable(R.drawable.new_tick5)
                    };

            for(int i=0;i<youtubetext.length;i++)
            {
                Feed_Howtodownload item=new Feed_Howtodownload();
                item.setTitle(youtubetext[i]);
                item.setImage(youtubeicon[i]);
                feedItems.add(item);
            }
            listAdapter.notifyDataSetChanged();

        }
        else if(Temp.help.equalsIgnoreCase("5"))
        {
            String[] youtubetext={"Open Sharechat app by pressing this button","Click On Option Button","Click On Copy Link","Download will start soon , Please wait"};
            Drawable[] youtubeicon={getResources().getDrawable(R.drawable.new_sc1),
                    getResources().getDrawable(R.drawable.new_sc2),
                    getResources().getDrawable(R.drawable.new_sc3),
                    getResources().getDrawable(R.drawable.new_sc4)
            };

            for(int i=0;i<youtubetext.length;i++)
            {
                Feed_Howtodownload item=new Feed_Howtodownload();
                item.setTitle(youtubetext[i]);
                item.setImage(youtubeicon[i]);
                feedItems.add(item);
            }
            listAdapter.notifyDataSetChanged();

        }
        else if(Temp.help.equalsIgnoreCase("6"))
        {
            String[] youtubetext={"Open Twitter app by pressing this button","Click On Share Button","Click On Copy to clipboard","Wait for some time","Download Your Video"};
            Drawable[] youtubeicon={getResources().getDrawable(R.drawable.new_tw1),
                    getResources().getDrawable(R.drawable.new_tw2),
                    getResources().getDrawable(R.drawable.new_tw3),
                    getResources().getDrawable(R.drawable.new_tw4),
                    getResources().getDrawable(R.drawable.new_tw5)
            };

            for(int i=0;i<youtubetext.length;i++)
            {
                Feed_Howtodownload item=new Feed_Howtodownload();
                item.setTitle(youtubetext[i]);
                item.setImage(youtubeicon[i]);
                feedItems.add(item);
            }
            listAdapter.notifyDataSetChanged();

        }
        else if(Temp.help.equalsIgnoreCase("7"))
        {
            String[] youtubetext={"Open Pinterest app by pressing this button","Click On Share Button","Click On Copy Link","Download will start soon , Please wait"};
            Drawable[] youtubeicon={getResources().getDrawable(R.drawable.new_pin1),
                    getResources().getDrawable(R.drawable.new_pin2),
                    getResources().getDrawable(R.drawable.new_pin3),
                    getResources().getDrawable(R.drawable.new_pin4)
            };

            for(int i=0;i<youtubetext.length;i++)
            {
                Feed_Howtodownload item=new Feed_Howtodownload();
                item.setTitle(youtubetext[i]);
                item.setImage(youtubeicon[i]);
                feedItems.add(item);
            }
            listAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            count = 0;
            intcount = 0;
            adView1.loadAd(adRequest);
            interstitial.loadAd(adRequest1);
        } catch (Exception e) {
        }
    }
}
