package com.dlkitmaker_feeds;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.io.IOException;

public class ControlPanel extends AppCompatActivity {
    int PERMISSION_ALL = 1;
    RelativeLayout create,mykits,feeds,webresource;
    Dialog dialog;
    final DB db=new DB(this);
    private InterstitialAd intestrial;
    AdRequest adreq;
    int intcount=0;
    UnifiedNativeAd nativeAd;
    FrameLayout adplaceholder;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);

        FirebaseApp.initializeApp(this);
        MobileAds.initialize(this, "ca-app-pub-5517777745693327~8323967529");
        String[] PERMISSIONS = {
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_NETWORK_STATE,

        };
        if (!ispermitted(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        if(db.getscreenwidth().equalsIgnoreCase(""))
        {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            db.addscreenwidth(width+"");
        }
        intestrial = new InterstitialAd(ControlPanel.this);
        intestrial.setAdUnitId("ca-app-pub-5517777745693327/8411597433");
        adreq = new AdRequest.Builder().build();
        adplaceholder=findViewById(R.id.adplaceholder);
        File folder=new File(Environment.getExternalStorageDirectory()+"/"+ Temp.foldername);
        if(!folder.exists())
        {
            folder.mkdir();
            File f1 = new File(Environment.getExternalStorageDirectory() + "/"+ Temp.foldername+"/" + ".nomedia");
            try {
                f1.createNewFile();
            } catch (IOException e) {

            }

        }
        create=findViewById(R.id.create);
        mykits=findViewById(R.id.mykits);
        feeds=findViewById(R.id.feeds);
        webresource=findViewById(R.id.webresource);


        intestrial.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {

                if(intcount<=20) {
                    intestrial.loadAd(adreq);
                    intcount++;
                }

            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                show_kitcat();

            }
        });

        mykits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),My_Kits.class));
            }
        });

        webresource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        feeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Feeds.class));
            }
        });

        if(db.getcommon().equalsIgnoreCase(""))
        {
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if (!task.isSuccessful()) {

                    }
                    else
                    {
                        db.addcommon(((InstanceIdResult) task.getResult()).getToken());
                        FirebaseMessaging.getInstance().subscribeToTopic(Temp.fcmtopic);
                    }

                }
            });
        }


        refreshad();
    }


    public void show_kitcat()
    {
        dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.catogerykits);
        final TextView txttitle=dialog.findViewById(R.id.txttitle);
        final Spinner spinner=dialog.findViewById(R.id.spinner);

        String[] p={"Choose","Normal","Goal Keeper"};
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, p){
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ((TextView)v).setTextColor(Color.BLACK);
                ((TextView)v).setTextSize(16);
                //((TextView)v).setTypeface(face);
                return v;
            }
            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);
                ((TextView)v).setTextColor(Color.BLACK);
                ((TextView)v).setTextSize(16);
                //  ((TextView)v).setTypeface(face);
                return v;
            }
        };
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(arg2==0)
                {

                }
                else
                {
                    try
                    {

                        db.add_kittheme(arg2+"");
                        exittotheme();
                        dialog.dismiss();
                    }
                    catch (Exception a)
                    {

                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dialog.show();

    }


    public void exittotheme()
    {


        Intent i=new Intent(getApplicationContext(), Theme_Selection.class);
        startActivity(i);

    }

    @Override
    protected void onResume() {
        super.onResume();
        intcount=0;
        count = 0;
        try
        {
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
    public static boolean ispermitted(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public void refreshad()
    {
        AdLoader.Builder builder = new AdLoader.Builder((this), "ca-app-pub-5517777745693327/7431594234");
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                adplaceholder.removeAllViews();
                adplaceholder.addView(adView);
            }
        });
        builder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build()).build());

        builder.withAdListener(new AdListener() {
            public void onAdFailedToLoad(int errorCode) {
                try {
                    if (count <= 15) {

                        refreshad();
                        count++;
                    }
                } catch (Exception e) {
                }
            }
            public void onAdLoaded() {
                super.onAdLoaded();
                adplaceholder.setVisibility(View.VISIBLE);
            }
        }).build().loadAd(new AdRequest.Builder().build());
    }

    public void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd2, UnifiedNativeAdView adView) {
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        ((TextView) adView.getHeadlineView()).setText(nativeAd2.getHeadline());
        if (nativeAd2.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd2.getBody());
        }
        if (nativeAd2.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd2.getCallToAction());
        }
        if (nativeAd2.getIcon() == null) {
            adView.getIconView(). setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd2.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
        if (nativeAd2.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd2.getPrice());
        }
        if (nativeAd2.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd2.getStore());
        }
        if (nativeAd2.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd2.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        if (nativeAd2.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd2.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd2);
        VideoController vc = nativeAd2.getVideoController();
        if (vc.hasVideoContent()) {
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });
        }
    }
}
