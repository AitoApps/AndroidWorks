package com.appsbag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import adapter.AppCatogery_Adapter;
import data.AppCatogery_FeedItem;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    ConnectionDetecter cd;

    public AppCatogery_Adapter adapter;
    HeaderAndFooterRecyclerView recyclerview;
    Typeface face;
    public List<AppCatogery_FeedItem> feeditem;
    ImageView heart;
    public int limit = 0;
    ImageView nodata;
    ImageView nointernet;
    TextView text;
    View footerview;
    ImageView help;
    UnifiedNativeAd nativeAd;
    FrameLayout adplaceholder;
    public InterstitialAd intestrial;
    AdRequest adreq;
    int intcount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            MobileAds.initialize((Context) this, "ca-app-pub-5517777745693327~6877555307");
            cd = new ConnectionDetecter(this);
            if (udb.get_userid().equalsIgnoreCase("")) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
                finish();
                return;
            }
            if (udb.getscreenwidth().equalsIgnoreCase("")) {
                int width = getResources().getDisplayMetrics().widthPixels;
                udb.addscreenwidth(width + "");
            }

        } catch (Exception a) {

        }
        heart = (ImageView) findViewById(R.id.heart);
        nodata = (ImageView) findViewById(R.id.nodata);
        cd = new ConnectionDetecter(this);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        text = (TextView) findViewById(R.id.text);
        recyclerview = findViewById(R.id.recyclerview);
        intestrial = new InterstitialAd(this);
        intestrial.setAdUnitId("ca-app-pub-5517777745693327/4798186873");
        adreq = new AdRequest.Builder().build();

        try {
            intestrial.setAdListener(new AdListener() {
                public void onAdFailedToLoad(int errorCode) {
                    if (intcount <= 40) {
                        intestrial.loadAd(adreq);
                        intcount++;
                    }
                }
            });
        } catch (Exception e2) {
        }

        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        text.setTypeface(face);
        feeditem = new ArrayList();
        adapter = new AppCatogery_Adapter(this, feeditem);



        recyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerview.setAdapter(adapter);

        footerview = LayoutInflater.from(this).inflate(R.layout.helpview, recyclerview.getFooterContainer(), false);
        recyclerview.addFooterView(footerview);

        help=footerview.findViewById(R.id.image);

        adplaceholder = (FrameLayout) footerview.findViewById(R.id.adplaceholder);


        float calheight = 0.50f * Float.valueOf(udb.getscreenwidth()).floatValue();
        help.getLayoutParams().height = Math.round(calheight);

        help.setImageDrawable(getResources().getDrawable(R.drawable.help));

        AdLoader.Builder builder = new AdLoader.Builder((this), "ca-app-pub-5517777745693327/5919696855");
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

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp("+91 9037631786");
            }
        });

        nointernet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (cd.isConnectingToInternet()) {
                    nointernet.setVisibility(View.GONE);
                    limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                nointernet.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            limit = 0;
            new loadstatus().execute(new String[0]);
        }
        else
        {
            nointernet.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
        }

        if(!udb.get_notimsg().equalsIgnoreCase(""))
        {
            shownotimsg();
        }



    }

    public void onBackPressed() {

            if (intestrial.isLoaded()) {
                intestrial.show();
            }
            super.onBackPressed();
    }
    @Override
    protected void onResume() {
        super.onResume();

        try
        {
            intcount=0;
            try
            {
                intestrial.loadAd(adreq);
            }
            catch (Exception a)
            {

            }
        }
        catch (Exception a)
        {

        }
    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            heart.setVisibility(View.VISIBLE);
            footerview.setVisibility(View.INVISIBLE);
            limit = 0;
        }
        public String doInBackground(String... arg0) {

            try {

                String link= Temp.weblink +"getappcatogery_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode("", "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            try {
                if (result.contains(":%ok")) {
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 4;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        AppCatogery_FeedItem item = new AppCatogery_FeedItem();
                        m=m+1;
                        item.setSn(got[m]);
                        m=m+1;
                        item.setAppid(got[m]);
                        m=m+1;
                        item.setCatogeryname(got[m]);
                        m=m+1;
                        item.setImgurl(got[m]);
                        feeditem.add(item);
                    }
                    footerview.setVisibility(View.VISIBLE);
                    heart.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    return;
                }
                heart.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }

    private void openWhatsApp(String number) {
        try {
            number = number.replace(" ", "").replace("+", "");
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number)+"@s.whatsapp.net");
            startActivity(sendIntent);

        } catch(Exception e) {

            Toasty.success(getApplicationContext(), "Please Whatsapp to this number " + number, Toast.LENGTH_LONG).show();
        }
    }


    public void shownotimsg() {
        try {
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.custome_notidialog);
            TextView title = dialog.findViewById(R.id.title);
            Button update=dialog.findViewById(R.id.update);
            Button ok=dialog.findViewById(R.id.ok);

            title.setTypeface(face);
            update.setTypeface(face);

            title.setText(udb.get_notimsg());

            if(udb.get_notitype().equalsIgnoreCase("2"))
            {
                update.setVisibility(View.VISIBLE);
                ok.setVisibility(View.GONE);
            }
            else{
                update.setVisibility(View.GONE);
                ok.setVisibility(View.VISIBLE);
            }

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    laodweb("https://play.google.com/store/apps/details?id=com.appsbag");
                    udb.delete_notimsg();
                    dialog.dismiss();


                }
            });

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    udb.delete_notimsg();
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
        }
    }
    public void laodweb(String url)
    {
        try
        {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        catch (Exception a)
        {

        }

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

