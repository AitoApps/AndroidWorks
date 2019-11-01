package com.suhi_chintha;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import chintha_adapter.UserChinthaAdapter;
import chintha_data.ChinthakarStatusFeed;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.pkmmte.view.CircularImageView;
import es.dmoral.toasty.Toasty;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Users_Chinthakal extends AppCompatActivity {
    public AdView adView1;
    AdRequest adreq1;

    public UserChinthaAdapter apater;
    Bitmap bitmap;
    NetConnection cd;
    RelativeLayout content;
    int count = 0;
    final DataDb dataDb = new DataDb(this);
    final DataDB1 dataDb1 = new DataDB1(this);
    final DataDB4 dataDb4 = new DataDB4(this);
    Typeface face;

    public List<ChinthakarStatusFeed> feed;
    boolean flag = false;
    RelativeLayout footerview;
    public int limit = 0;
    ListView listview;
    LottieAnimationView load_icon;
    ImageView moveback;
    ImageView nonet;
    ProgressBar progressPB1;
    TextView statuscount;
    TextView text;
    TextView tv;
    CircularImageView user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.users_chintha_actvty);
        try {
            tv = (TextView) findViewById(R.id.tv);
            text = (TextView) findViewById(R.id.text);
            cd = new NetConnection(this);
            content = (RelativeLayout) findViewById(R.id.content);
            adView1 = (AdView) findViewById(R.id.adView1);
            adreq1 = new Builder().build();
            text.setText(Static_Variable.username);
            text.setSelected(true);
            text.setTypeface(face);
            moveback = (ImageView) findViewById(R.id.moveback);
            load_icon = (LottieAnimationView) findViewById(R.id.lotty_loadin);
            statuscount = (TextView) findViewById(R.id.statuscount);
            face = Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
            user = (CircularImageView) findViewById(R.id.imgsuser);
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
            listview = (ListView) findViewById(R.id.listview);
            progressPB1 = (ProgressBar) findViewById(R.id.progress_pb1);
            nonet = (ImageView) findViewById(R.id.nonets);
            footerview = (RelativeLayout) getLayoutInflater().inflate(R.layout.bottomview, null);
            listview.addFooterView(footerview);
            footerview. setVisibility(View.GONE);
            feed = new ArrayList();
            apater = new UserChinthaAdapter(this, feed);
            listview.setAdapter(apater);
            listview.setOnScrollListener(new OnScrollListener() {
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (visibleItemCount == totalItemCount - firstVisibleItem &&flag) {
                       flag = false;
                        if (!cd.isConnectingToInternet()) {
                            Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet_scroll, Toast.LENGTH_SHORT).show();
                        } else if (footerview.getVisibility() != View.VISIBLE) {
                           limit += 30;
                           userchintha_history1();
                        }
                    }
                }

                public void onScrollStateChanged(AbsListView arg0, int arg1) {
                    if (arg1 == 2) {
                       flag = true;
                    }
                }
            });
            moveback.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                   onBackPressed();
                }
            });
            user.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                   startActivity(new Intent(getApplicationContext(), Image_View.class));
                }
            });
            nonet.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (cd.isConnectingToInternet()) {
                       nonet. setVisibility(View.GONE);
                       limit = 0;
                       history_user();
                        return;
                    }
                   nonet.setVisibility(View.VISIBLE);
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            });
            if (cd.isConnectingToInternet()) {
                nonet. setVisibility(View.GONE);
                limit = 0;
                history_user();
            } else {
                nonet.setVisibility(View.VISIBLE);
                Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
            }
            if (cd.isConnectingToInternet()) {
                new loading_image().execute(Static_Variable.entypoint1+"userphotosmall/"+Static_Variable.userid+".jpg");
            }
            if (cd.isConnectingToInternet()) {
                statuscount_foruser();
            }
            File f = new File(Environment.getExternalStorageDirectory() + File.separator + Static_Variable.foldername+"/bg/bg.png");
            if(f.exists())
            {
                try
                {
                    Glide.with(getApplicationContext()).asBitmap().load(f).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                            content.setBackground(drawable);
                        }
                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);

                        }
                    });
                }
                catch(Exception a)
                {
                }
            }
        } catch (Exception e3) {
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

    private class loading_image extends AsyncTask<String, String, Bitmap> {
        public void onPreExecute() {
            super.onPreExecute();
            bitmap = null;
        }
        public Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        public void onPostExecute(Bitmap image1) {
            if (image1 != null) {
                tv.setVisibility(View.INVISIBLE);
                user.setVisibility(View.VISIBLE);
                user.setImageBitmap(image1);
                return;
            }
            user.setVisibility(View.INVISIBLE);
            tv.setVisibility(View.VISIBLE);
            try {
                char chars = Static_Variable.username.toUpperCase().charAt(0);
                tv.setText(chars+"");
            } catch (Exception e) {
            }
        }
    }

    public void status_share(String status)
    {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Status");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, status +"\n "+ Static_Variable.appshare_link);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

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

    public void statuscount_foruser() {
        statuscount. setVisibility(View.GONE);
        progressPB1.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"getuserscount.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", Static_Variable.userid).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                   runOnUiThread(new Runnable() {
                        public void run() {
                            if (result.contains("%:ok")) {
                                String[] k = result.split("%:");
                               progressPB1. setVisibility(View.GONE);
                               statuscount.setVisibility(View.VISIBLE);
                               statuscount.setText(k[0]+"");
                                return;
                            }
                           progressPB1. setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    public void history_user() {
        listview. setVisibility(View.GONE);
        load_icon.setVisibility(View.VISIBLE);
        nonet. setVisibility(View.GONE);
        limit = 0;
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"getuserstatus.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", limit+"%:"+Static_Variable.userid).build()).build()).enqueue(new Callback() {
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
                                int k = (got.length - 1) / 10;
                                int m = -1;
                                for (int i = 1; i <= k; i++) {
                                    m=m+1;
                                    ChinthakarStatusFeed item = new ChinthakarStatusFeed();
                                    item.setstatusid(got[m]);
                                    m=m+1;
                                    item.setstatus(got[m]);
                                    m=m+1;
                                    item.set_likes(got[m]);
                                    m=m+1;
                                    item.setcmntcount(got[m]);
                                    m=m+1;
                                    try {
                                        Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                        c1.setTime(sdf.parse(got[m]));
                                        item.setpostdate(getFormattedDate(c1.getTimeInMillis()));
                                    } catch (Exception e) {
                                        item.setpostdate(got[m]);
                                    }
                                    m=m+1;
                                    item.set_imgsig(got[m]);
                                    m=m+1;
                                    item.setstatustype(got[m]);
                                    m=m+1;
                                    item.setphotourl(got[m]);
                                    m=m+1;
                                    item.set_photodim(got[m]);
                                    m=m+1;
                                    item.set_iscommentlock(got[m]);
                                    if (i % 3 == 0) {
                                        item.setshowads("1");
                                    } else {
                                        item.setshowads("0");
                                    }
                                   feed.add(item);
                                }
                               load_icon. setVisibility(View.GONE);
                               listview.setVisibility(View.VISIBLE);
                               apater.notifyDataSetChanged();
                                return;
                            }
                           load_icon. setVisibility(View.GONE);
                           footerview. setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    public void sharetoall(Bitmap bitmap2) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Chinthakal_");
            sb.append(System.currentTimeMillis());
            sb.append(".png");
            File file = new File(getCacheDir(), sb.toString());
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap2.compress(CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
            intent.setType("image/*");
            startActivity(intent);
        } catch (Exception e) {
            Toasty.info(getApplicationContext(), (CharSequence) "Unable to img_share", Toast.LENGTH_SHORT).show();
        }
    }

    public void userchintha_history1() {
        footerview.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"getuserstatus.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", limit+"%:"+Static_Variable.userid).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                   runOnUiThread(new Runnable() {
                        public void run() {
                            if (result.contains("%:ok")) {
                                String[] got = result.split("%:");
                                int k = (got.length - 1) / 10;
                                int m = -1;
                                for (int i = 1; i <= k; i++) {
                                    m=m+1;
                                    ChinthakarStatusFeed item = new ChinthakarStatusFeed();
                                    item.setstatusid(got[m]);
                                    m=m+1;
                                    item.setstatus(got[m]);
                                    m=m+1;
                                    item.set_likes(got[m]);
                                    m=m+1;
                                    item.setcmntcount(got[m]);
                                    m=m+1;
                                    try {
                                        Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                        c1.setTime(sdf.parse(got[m]));
                                        item.setpostdate(getFormattedDate(c1.getTimeInMillis()));
                                    } catch (Exception e) {
                                        item.setpostdate(got[m]);
                                    }
                                    m=m+1;
                                    item.set_imgsig(got[m]);
                                    m=m+1;
                                    item.setstatustype(got[m]);
                                    m=m+1;
                                    item.setphotourl(got[m]);
                                    m=m+1;
                                    item.set_photodim(got[m]);
                                    m=m+1;
                                    item.set_iscommentlock(got[m]);
                                    if (i % 3 == 0) {
                                        item.setshowads("1");
                                    } else {
                                        item.setshowads("0");
                                    }
                                    feed.add(item);
                                }
                               apater.notifyDataSetChanged();
                               footerview. setVisibility(View.GONE);
                                return;
                            }
                           footerview. setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }
}
