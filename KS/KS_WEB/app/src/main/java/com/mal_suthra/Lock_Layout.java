package com.mal_suthra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.util.Timer;
import java.util.TimerTask;

public class Lock_Layout extends AppCompatActivity {

    ImageView moveback;
    TextView text,info;
    FrameLayout adplaceholder;
    TextView timeintervelll;
    Button payment;
    NetConnect nc;
    Typeface face;
    final DataBase db=new DataBase(this);
    public int islive=0;
    UnifiedNativeAd nativeAd;
    public InterstitialAd intestrial1,intestrial2;
    AdRequest adreq1,adreq2;
    int intcount = 0;
    int intcount1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock__layout);
        moveback=findViewById(R.id.moveback);
        text=findViewById(R.id.text);
        info=findViewById(R.id.info);
        adplaceholder=findViewById(R.id.adplaceholder);
        timeintervelll=findViewById(R.id.timeintervelll);
        payment=findViewById(R.id.payment);
        nc=new NetConnect(this);
        face = Typeface.createFromAsset(getAssets(), "app_fonts/heading.otf");

        moveback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        intestrial1 = new InterstitialAd(this);
        intestrial1.setAdUnitId("ca-app-pub-5452894935816879/2151077897");
        adreq1 = new AdRequest.Builder().build();

        try {
            intestrial1.setAdListener(new AdListener() {

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    if(islive==1)
                    {
                        intestrial1.show();
                    }

                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    if (intcount <= 40) {
                        intestrial1.loadAd(adreq1);
                        intcount++;
                    }
                }
            });
        } catch (Exception e2) {
        }

        intestrial2 = new InterstitialAd(this);
        intestrial2.setAdUnitId("ca-app-pub-5452894935816879/7897733752");
        adreq2= new AdRequest.Builder().build();

        try {
            intestrial2.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    if(islive==1)
                    {
                        intestrial2.show();
                    }
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    if (intcount1 <= 40) {
                        intestrial2.loadAd(adreq2);
                        intcount1++;
                    }
                }
            });
        } catch (Exception e2) {
        }


        text.setTypeface(face);
        info.setTypeface(face);
        timeintervelll.setTypeface(face);
        payment.setTypeface(face);


        info.setText("ക്ഷമിക്കണം ! 20 സെക്കന്റ് കാത്തിരിക്കുക.ശേഷം ബാക്കിയുള്ള പൊസിഷനുകള്\u200D കാണാവുന്നതാണ്.പേയ്\u200Cമെന്റ് ചെയ്താല്\u200D ഒരു തടസ്സവുമില്ലാതെ തന്നെ താങ്കള്\u200Dക്ക് എല്ലാ പൊസിഷനുകളും കാണാവുന്നതാണ്.");

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Static_Veriable.clickedmethod = 5;
                startActivity(new Intent(getApplicationContext(),Mobile_verification.class));
                finish();
                return;
            }
        });

        AdLoader.Builder builder = new AdLoader.Builder((this), "ca-app-pub-5452894935816879/7343694820");
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
            }

            public void onAdLoaded() {
                super.onAdLoaded();
                adplaceholder.setVisibility(View.VISIBLE);
            }
        }).build().loadAd(new AdRequest.Builder().build());

        try {
            new CountDownTimer(20000, 1000) {
                public void onTick(long millisUntilFinished) {
                    timeintervelll.setText((millisUntilFinished / 1000)+"");
                }
                public void onFinish() {

                    if(islive==1)
                    {
                        islive=0;
                        db.drop_shouldshow();
                        db.add_showedads("0");
                        finish();
                    }

                }
            }.start();
        }
        catch (Exception a)
        {

        }

        try
        {
            intcount=0;
            intcount1=0;
            try
            {
                intestrial1.loadAd(adreq1);
                intestrial2.loadAd(adreq2);
            }
            catch (Exception a)
            {

            }
        }
        catch (Exception a)
        {

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        islive=1;

    }

    @Override
    public void onBackPressed() {

        finish();
        super.onBackPressed();

    }

    @Override
    protected void onPause() {
        super.onPause();

        islive=0;

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
