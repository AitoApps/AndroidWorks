package com.footballstatus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import adapter.Video_List_Adapter;
import data.videoList_Feed;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    final UserDatabaseHandler udb=new UserDatabaseHandler(this);
    ConnectionDetecter cd;
    public UserDatabaseHandler db;
    Typeface face;
    public List<videoList_Feed> feedItems;
    boolean flag = false;
    RelativeLayout footerview;
    public int limit = 0;
    ListView list;
    public Video_List_Adapter listAdapter;
    ShimmerFrameLayout shimmer_view_container;
    ImageView nointernet;
    public InterstitialAd interstitial;
    AdRequest adRequest;
    int intcount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize((Context) this, "ca-app-pub-5517777745693327~3276930061");
        FirebaseApp.initializeApp(this);

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-5517777745693327/5778615103");
        adRequest = new AdRequest.Builder().build();

        if (udb.getscreenwidth().equalsIgnoreCase("")) {
            int width = getResources().getDisplayMetrics().widthPixels;
            udb.addscreenwidth(width+"");
        }
        if(udb.get_name().equalsIgnoreCase(""))
        {
            Intent i=new Intent(getApplicationContext(),Registration.class);
            startActivity(i);
            finish();
            return;
        }
        shimmer_view_container=findViewById(R.id.shimmer_view_container);
        interstitial.setAdListener(new AdListener() {
            public void onAdFailedToLoad(int errorCode) {
                if (intcount <= 30) {
                    interstitial.loadAd(adRequest);
                    intcount++;
                }
            }
        });

        try {
            db = new UserDatabaseHandler(this);
            face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
            nointernet = (ImageView)findViewById(R.id.nonet);
            list = (ListView) findViewById(R.id.listview);
            cd = new ConnectionDetecter(this);
            footerview = (RelativeLayout) getLayoutInflater().inflate(R.layout.bottomview, null);
            list.addFooterView(footerview);
            footerview. setVisibility(View.GONE);
            feedItems = new ArrayList();
            listAdapter = new Video_List_Adapter(this, feedItems);
            list.setAdapter(listAdapter);

            list.setOnScrollListener(new AbsListView.OnScrollListener() {
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (visibleItemCount == totalItemCount - firstVisibleItem && flag) {
                        flag = false;
                        if (!cd.isConnectingToInternet()) {
                            Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                        } else if (footerview.getVisibility() != View.VISIBLE) {
                            limit += 30;
                            new getyoutube1().execute(new String[0]);
                        }
                    }
                }

                public void onScrollStateChanged(AbsListView arg0, int arg1) {
                    if (arg1 == 2) {
                        flag = true;
                    }
                }
            });
            nointernet.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    if (cd.isConnectingToInternet()) {
                        nointernet. setVisibility(View.GONE);
                        limit = 0;
                        new getyoutube().execute(new String[0]);
                        return;
                    }
                    nointernet.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            });

            Temp.isvideo=0;
        } catch (Exception e) {


        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Temp.isvideo==0)
        {
            refresh();
        }
        else
        {
            Temp.isvideo=0;
        }

        intcount = 0;
        try {
            interstitial.loadAd(adRequest);
        } catch (Exception e) {

        }

    }

    public void refresh() {
        try {
            if (cd.isConnectingToInternet()) {
                nointernet. setVisibility(View.GONE);
                limit = 0;
                new getyoutube().execute(new String[0]);
                return;
            }
            nointernet.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
        } catch (Exception a) {
           Toasty.info(getApplicationContext(), (CharSequence) Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
        }
    }

    public class getyoutube extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            feedItems.clear();
            nointernet. setVisibility(View.GONE);
            list. setVisibility(View.GONE);
            shimmer_view_container.setVisibility(View.VISIBLE);
            shimmer_view_container.startShimmerAnimation();
            limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Temp.weblink +"getvideolist.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+"", "UTF-8");
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
                shimmer_view_container.setVisibility(View.GONE);
                if (result.contains("%:ok")) {
                    String[] got = result.split("%:");
                    int k = (got.length - 1) / 7;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        videoList_Feed item = new videoList_Feed();
                        m=m+1;
                        try {
                            Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                            c1.setTime(sdf.parse(got[m]));
                            item.setRegdate(getFormattedDate(c1.getTimeInMillis()));
                        } catch (Exception e) {
                            item.setRegdate(got[m]);
                        }
                        m=m+1;
                        item.setMediatype(got[m]);
                        m=m+1;
                        item.setTitle(got[m]);
                        m=m+1;
                        item.setFbid(got[m]);
                        m=m+1;
                        item.setDim(got[m]);
                        m=m+1;
                        item.setImgsrc(got[m]);
                        m=m+1;
                        item.setVideosrc(got[m]);
                        if(i%3==0)
                        {
                            item.setIsadshow(1);
                        }
                        else
                        {
                            item.setIsadshow(0);
                        }
                        feedItems.add(item);
                    }
                    listAdapter.notifyDataSetChanged();
                    list.setVisibility(View.VISIBLE);
                    shimmer_view_container.stopShimmerAnimation();
                    shimmer_view_container.setVisibility(View.GONE);
                    return;
                }
                shimmer_view_container.stopShimmerAnimation();
                shimmer_view_container.setVisibility(View.GONE);
                footerview. setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }

    public class getyoutube1 extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            footerview.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Temp.weblink +"getvideolist.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+"", "UTF-8");
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
                if (result.contains("%:ok")) {
                    String[] got = result.split("%:");
                    int k = (got.length - 1) / 7;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        videoList_Feed item = new videoList_Feed();
                        m=m+1;
                        try {
                            Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                            c1.setTime(sdf.parse(got[m]));
                            item.setRegdate(getFormattedDate(c1.getTimeInMillis()));
                        } catch (Exception e) {
                            item.setRegdate(got[m]);
                        }
                        m=m+1;
                        item.setMediatype(got[m]);
                        m=m+1;
                        item.setTitle(got[m]);
                        m=m+1;
                        item.setFbid(got[m]);
                        m=m+1;
                        item.setDim(got[m]);
                        m=m+1;
                        item.setImgsrc(got[m]);
                        m=m+1;
                        item.setVideosrc(got[m]);
                        if(i%3==0)
                        {
                            item.setIsadshow(1);
                        }
                        else
                        {
                            item.setIsadshow(0);
                        }
                        feedItems.add(item);
                    }
                    return;
                }
                shimmer_view_container.stopShimmerAnimation();
                shimmer_view_container.setVisibility(View.GONE);
                footerview. setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }

    public String getFormattedDate(long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);
        Calendar now = Calendar.getInstance();
        final String timeFormatString = "h:mm a";
        final String dateTimeFormatString = "MMM d h:mm a";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ) {
            return DateFormat.format(timeFormatString, smsTime)+"";
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1  ){
            return "Yesterday " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("MMM dd yyyy h:mm a", smsTime).toString();
        }

    }

    @Override
    public void onBackPressed() {
        if(interstitial.isLoaded())
        {
            interstitial.show();
        }
        super.onBackPressed();
    }
}
