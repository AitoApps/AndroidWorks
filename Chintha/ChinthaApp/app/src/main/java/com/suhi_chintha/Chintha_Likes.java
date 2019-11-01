package com.suhi_chintha;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import chintha_adapter.ChinthaLikesAdapter;
import chintha_data.LikeFeed;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import es.dmoral.toasty.Toasty;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Chintha_Likes extends AppCompatActivity {
    public AdView adView1;
    AdRequest adreq1;

    public ChinthaLikesAdapter apater;
    ImageView back;
    NetConnection cd;
    RelativeLayout content;
    int count = 0;
    final DataDB1 dataDb1 = new DataDB1(this);
    final DataDB4 dataDb4 = new DataDB4(this);
    Typeface face;

    public List<LikeFeed> feed;
    boolean flag = false;
    RelativeLayout footerview;
    public int limit = 0;
    ListView list;
    LottieAnimationView loadingicon;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    TextView text;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.likes_actvty);
        try {
            adView1 = (AdView) findViewById(R.id.adView1);
            adreq1 = new Builder().build();
            loadingicon = (LottieAnimationView) findViewById(R.id.lotty_loadin);
            text = (TextView) findViewById(R.id.text);
            pd = new ProgressDialog(this);
            cd = new NetConnection(this);
            content = (RelativeLayout) findViewById(R.id.content);
            back = (ImageView) findViewById(R.id.moveback);
            try {
                adView1.setAdListener(new AdListener() {
                    public void onAdFailedToLoad(int errorCode) {
                        try {
                            if (count <= 10) {
                                adView1.loadAd(adreq1);
                                count++;
                            }
                        } catch (Exception e) {
                        }
                    }
                });
            } catch (Exception e) {
            }
            back.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    onBackPressed();
                }
            });
            face = Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
            text.setText(Static_Variable.text_liked);
            text.setTypeface(face);
            text.setSelected(true);
            list = (ListView) findViewById(R.id.listview);
            nodata = (ImageView) findViewById(R.id.emptydata);
            nointernet = (ImageView) findViewById(R.id.nonets);
            footerview = (RelativeLayout) getLayoutInflater().inflate(R.layout.bottomview, null);
            list.addFooterView(footerview);
            footerview. setVisibility(View.GONE);
            feed = new ArrayList();
            apater = new ChinthaLikesAdapter(this, feed);
            list.setAdapter(apater);
            nointernet.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (cd.isConnectingToInternet()) {
                        nointernet. setVisibility(View.GONE);
                        limit = 0;
                        loadlikes();
                        return;
                    }
                    nointernet.setVisibility(View.VISIBLE);
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            });
            if (cd.isConnectingToInternet()) {
                nointernet. setVisibility(View.GONE);
                loadlikes();
            } else {
                nointernet.setVisibility(View.VISIBLE);
                Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
            }
            File f = new File(Environment.getExternalStorageDirectory()+File.separator+Static_Variable.foldername+"/bg/bg.png");
            if (f.exists()) {
                try {
                    Glide.with(getApplicationContext()).asBitmap().load(f).into(new SimpleTarget<Bitmap>() {
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            content.setBackground(new BitmapDrawable(getResources(), bitmap));
                        }

                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                        }
                    });
                } catch (Exception e2) {
                }
            }
        } catch (Exception e3) {
        }
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void loadlikes() {
        list. setVisibility(View.GONE);
        loadingicon.setVisibility(View.VISIBLE);
        nointernet. setVisibility(View.GONE);
        nodata. setVisibility(View.GONE);
        limit = 0;
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"getlikes.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", Static_Variable.userid).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (result.contains("%:ok")) {
                                feed.clear();
                                String[] got = result.split("%:");
                                int k = (got.length - 1) / 3;
                                int m = -1;
                                for (int i = 1; i <= k; i++) {
                                    LikeFeed item = new LikeFeed();
                                    m = m + 1;
                                    item.set_userid(got[m]);
                                    item.set_dppic(Static_Variable.entypoint1+"userphotosmall/"+got[m]+".jpg");
                                    m = m + 1;
                                    item.setName(got[m]);
                                    m = m + 1;
                                    item.set_imgsig(got[m]);
                                    feed.add(item);
                                }
                                Collections.reverse(feed);
                                loadingicon. setVisibility(View.GONE);
                                list.setVisibility(View.VISIBLE);
                                apater.notifyDataSetChanged();
                                return;
                            }
                            nodata.setVisibility(View.VISIBLE);
                            loadingicon. setVisibility(View.GONE);
                            footerview. setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        try
        {

            ActivityManager mngr = (ActivityManager) getSystemService( ACTIVITY_SERVICE );
            List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10); if(taskList.get(0).numActivities == 1 && taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {

            Intent i=new Intent(getApplicationContext(), HeartOf_App.class);
            startActivity(i);
        }
        else
        {
            super.onBackPressed();
        }
        }
        catch(Exception a)
        {

        }


    }
    public void onResume() {
        super.onResume();
        count = 0;
        try {
            adView1.loadAd(adreq1);
        } catch (Exception e) {
        }
    }
}
