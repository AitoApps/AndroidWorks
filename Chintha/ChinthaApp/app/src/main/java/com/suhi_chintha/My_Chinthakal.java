package com.suhi_chintha;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import chintha_adapter.MyChinthaAdapter;
import chintha_data.MyChinthakalFeed;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

public class My_Chinthakal extends AppCompatActivity {
    public AdView adView1;
    AdRequest adreq1;
    public MyChinthaAdapter apater;
    ImageView back;
    NetConnection cd;
    RelativeLayout content;
    int count = 0;
    final DataDB4 dataDb4 = new DataDB4(this);
    ImageView emptydata;
    Typeface face;
    public List<MyChinthakalFeed> feed;
    boolean flag = false;
    RelativeLayout foot;
    public int limit = 0;
    ListView list;
    LottieAnimationView loadicon;
    ImageView nonet;
    ProgressBar pb1;
    ProgressDialog pd;
    ProgressDialog pd1;
    TextView statuscount;
    TextView text;
    public String txtstatus = "";
    final User_DataDB userDataDB = new User_DataDB(this);
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.my_chintha_actvty);
        adView1 = (AdView) findViewById(R.id.adView1);
        adreq1 = new Builder().build();
        loadicon = (LottieAnimationView) findViewById(R.id.lotty_loadin);
        statuscount = (TextView) findViewById(R.id.statuscount);
        pb1 = (ProgressBar) findViewById(R.id.progress_pb1);
        back = (ImageView) findViewById(R.id.moveback);
        content = (RelativeLayout) findViewById(R.id.content);
        pd = new ProgressDialog(this);
        pd1 = new ProgressDialog(this);
        cd = new NetConnection(this);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        list = (ListView) findViewById(R.id.listview);
        emptydata = (ImageView) findViewById(R.id.emptydata);
        nonet = (ImageView) findViewById(R.id.nonets);
        foot = (RelativeLayout) getLayoutInflater().inflate(R.layout.bottomview, null);
        list.addFooterView(foot);
        foot. setVisibility(View.GONE);
        text = (TextView) findViewById(R.id.text);
        face = Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
        text.setText(Static_Variable.mychinthakal_text);
        text.setTypeface(face);
        text.setSelected(true);
        pd1.setMessage("Please wait");
        pd1.setCancelable(false);
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
        feed = new ArrayList();
        apater = new MyChinthaAdapter(this, feed);
        list.setAdapter(apater);
        list.setOnScrollListener(new OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount == totalItemCount - firstVisibleItem && flag) {
                    flag = false;
                    if (!cd.isConnectingToInternet()) {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet_scroll, Toast.LENGTH_SHORT).show();
                    } else if (foot.getVisibility() != View.VISIBLE) {
                        limit += 30;
                        load_status1();
                    }
                }
            }

            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                if (arg1 == 2) {
                    flag = true;
                }
            }
        });
        nonet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (cd.isConnectingToInternet()) {
                    nonet. setVisibility(View.GONE);
                    limit = 0;
                    load_status();
                    return;
                }
                nonet.setVisibility(View.VISIBLE);
                Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
            }
        });
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
            } catch (Exception e4) {
            }
        }
        if (cd.isConnectingToInternet()) {
            nonet. setVisibility(View.GONE);
            limit = 0;
            load_status();
        } else {
            nonet.setVisibility(View.VISIBLE);
            Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
        }
        if (cd.isConnectingToInternet()) {
            count_userchintha();
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

    public void count_userchintha() {
        statuscount. setVisibility(View.GONE);
        pb1.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"getuserscount.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", userDataDB.get_userid()).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (result.contains("%:ok")) {
                                String[] k = result.split("%:");
                                pb1. setVisibility(View.GONE);
                                statuscount.setVisibility(View.VISIBLE);
                                TextView textView = statuscount;
                                StringBuilder sb = new StringBuilder();
                                sb.append(k[0]);
                                sb.append("");
                                textView.setText(sb.toString());
                                return;
                            }
                            pb1. setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    public void timerUpdate(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void edit_status() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.chintha_editchintha_custom);
        final EditText status = (EditText) dialog.findViewById(R.id.chintha);
        Button update = (Button) dialog.findViewById(R.id.rpt_update);
        Button close = (Button) dialog.findViewById(R.id.close);
        status.setText(Static_Variable.chintha_text);
        TextView alert = (TextView) dialog.findViewById(R.id.alert);
        alert.setTypeface(face);
        alert.setText(Static_Variable.chintha_msgalert);
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (status.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(getApplicationContext(), (CharSequence) "Please enter status", Toast.LENGTH_SHORT).show();
                    status.requestFocus();
                } else if (checkcharector(status.getText().toString())) {
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.isenglish, Toast.LENGTH_SHORT).show();
                    status.requestFocus();
                } else if (status.getText().toString().length() <= 50) {
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.length_chintha, Toast.LENGTH_SHORT).show();
                    status.requestFocus();
                } else if (cd.isConnectingToInternet()) {
                    try {
                        byte[] data = status.getText().toString().getBytes(StandardCharsets.UTF_8);
                        txtstatus = Base64.encodeToString(data, 0);
                    } catch (Exception e) {
                    }
                    async_editstaus();
                    dialog.dismiss();
                } else {
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void async_editstaus() {
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();
        timerUpdate(50000, pd);
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.newCall(new Request.Builder().url(Static_Variable.entypoint1+"editstatus.php").post(new FormBody.Builder().add("item", userDataDB.get_usermobile()+"%:"+Static_Variable.chintha_Id+"%:"+txtstatus).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (pd != null || pd.isShowing()) {
                                pd.dismiss();
                                if (result.contains("ok")) {
                                    Toasty.info(getApplicationContext(), (CharSequence) "Updated", Toast.LENGTH_SHORT).show();
                                    reload();
                                } else if (result.contains("block:%")) {
                                    String[] k = result.split(":%");

                                    Toasty.info(getApplicationContext(), Static_Variable.error1+" "+Static_Variable.error2+" "+k[1]+" "+Static_Variable.err3_posting, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public void status_share(String status)
    {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Status");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, status +"\n "+ Static_Variable.appshare_link);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    public void reload() {
        if (cd.isConnectingToInternet()) {
            nonet. setVisibility(View.GONE);
            limit = 0;
            load_status();
            return;
        }
        nonet.setVisibility(View.VISIBLE);
        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
    }

    public boolean checkcharector(String text2) {
        boolean found = false;
        int i = 0;
        while (true) {
            if (i < text2.length()) {
                if (text2.toUpperCase().charAt(i) >= 'A' && text2.toUpperCase().charAt(i) <= 'Z') {
                    found = true;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        return found;
    }

    public void removeitem(int position) {
        try {
            feed.remove(position);
            apater.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void sharetoall(Bitmap bitmap) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Chinthakal_");
            sb.append(System.currentTimeMillis());
            sb.append(".png");
            File file = new File(getCacheDir(), sb.toString());
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 100, fOut);
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

    public void load_status() {
        nonet. setVisibility(View.GONE);
        emptydata. setVisibility(View.GONE);
        list. setVisibility(View.GONE);
        loadicon.setVisibility(View.VISIBLE);
        limit = 0;
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"getmystatus.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", limit+"%:"+userDataDB.get_userid()).build()).build()).enqueue(new Callback() {
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
                                    MyChinthakalFeed item = new MyChinthakalFeed();
                                    m++;
                                    item.setstatusid(got[m]);
                                    m++;
                                    item.setstatus(got[m]);
                                    m++;
                                    item.setfvrt(got[m]);
                                    m++;
                                    item.set_countcomment(got[m]);
                                    m++;
                                    try
                                    {
                                        Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                        c1.setTime(sdf.parse(got[m]));
                                        item.setpostdate(getFormattedDate(c1.getTimeInMillis()));

                                    }
                                    catch(Exception a)
                                    {
                                        item.setpostdate(got[m]);
                                    }
                                    m++;
                                    item.set_imgsig(got[m]);
                                    m++;
                                    item.setstatustype(got[m]);
                                    m++;
                                    item.setphotourl(got[m]);
                                    m++;
                                    item.set_photodim(got[m]);
                                    m++;
                                    item.set_lockedcomments(got[m]);
                                    if(i%3==0)
                                    {
                                        item.setshowads("1");

                                    }
                                    else
                                    {
                                        item.setshowads("0");

                                    }
                                    feed.add(item);
                                }
                                loadicon. setVisibility(View.GONE);
                                list.setVisibility(View.VISIBLE);
                                apater.notifyDataSetChanged();
                                return;
                            }
                            emptydata.setVisibility(View.VISIBLE);
                            loadicon. setVisibility(View.GONE);
                            foot. setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    public void changeitem(int position, String statusid1, String status1, String fvrt1, String cmntcount1, String postdate1, String imgsig1, String statustype1, String photourl1, String photodimension1, String commentlock1, String showads1) {
        try {
            feed.remove(position);
            apater.notifyDataSetChanged();
            MyChinthakalFeed item = new MyChinthakalFeed();
            item.setstatusid(statusid1);
            item.setstatus(status1);
            item.setfvrt(fvrt1);
            item.set_countcomment(cmntcount1);
            item.setpostdate(postdate1);
            item.set_imgsig(imgsig1);
            item.setstatustype(statustype1);
            item.setphotourl(photourl1);
            item.set_photodim(photodimension1);
            item.set_lockedcomments(commentlock1);
            item.setshowads(showads1);
            feed.add(position, item);
        } catch (Exception e) {
        }
    }

    public void load_status1() {
        foot.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"getmystatus.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item",limit+"%:"+userDataDB.get_userid()).build()).build()).enqueue(new Callback() {
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
                                    MyChinthakalFeed item = new MyChinthakalFeed();
                                    m++;
                                    item.setstatusid(got[m]);
                                    m++;
                                    item.setstatus(got[m]);
                                    m++;
                                    item.setfvrt(got[m]);
                                    m++;
                                    item.set_countcomment(got[m]);
                                    m++;
                                    try
                                    {
                                        Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                        c1.setTime(sdf.parse(got[m]));
                                        item.setpostdate(getFormattedDate(c1.getTimeInMillis()));

                                    }
                                    catch(Exception a)
                                    {
                                        item.setpostdate(got[m]);
                                    }
                                    m++;
                                    item.set_imgsig(got[m]);
                                    m++;
                                    item.setstatustype(got[m]);
                                    m++;
                                    item.setphotourl(got[m]);
                                    m++;
                                    item.set_photodim(got[m]);
                                    m++;
                                    item.set_lockedcomments(got[m]);
                                    if(i%3==0)
                                    {
                                        item.setshowads("1");

                                    }
                                    else
                                    {
                                        item.setshowads("0");

                                    }
                                    feed.add(item);
                                }
                                apater.notifyDataSetChanged();
                                foot. setVisibility(View.GONE);
                                return;
                            }
                            loadicon. setVisibility(View.GONE);
                            foot. setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }
}
