package com.suhi_chintha;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import chintha_adapter.ChinathakalAdapter;
import chintha_data.ChinthaFeeds;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class status_frag extends Fragment {
    public static int firstitem = 0;
    public static int limit = 0;
    public static String reporttype = "";
    public ImageView addimage;
    public ChinathakalAdapter apater;
    NetConnection cd;
    public Context context;
    public DataDb dataDb;
    public DataDB1 dataDb1;
    public DataDB4 dataDb4;
    public Dialog dialog;
    public ImageView emoji;
    Typeface face;
    public List<ChinthaFeeds> feedStatuses;
    boolean flag = false;
    public SwipeRefreshLayout layout;
    LottieAnimationView loadingicon;
    public MediaPlayer mediaPlayer;
    public String msg = "";
    ImageView nonet;
    ProgressDialog pd;
    RecyclerView recylerview;
    RotateAnimation rotateimg;
    ImageView statusadd;
    ImageView statussettings;
    public String txt_shotstatus = "";
    public String txtstatus = "";
    public String txtstatus1 = "";
    public User_DataDB userDataDB;
    EmojiEditText status;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chinthakal_fragment, container, false);
        try {
            context = getActivity();
            face = Typeface.createFromAsset(context.getAssets(), "asset_fonts/font_rachana.ttf");
            loadingicon = (LottieAnimationView) rootView.findViewById(R.id.lotty_loadin);
            dataDb = new DataDb(context);
            userDataDB = new User_DataDB(context);
            pd = new ProgressDialog(context);
            cd = new NetConnection(context);
            dataDb1 = new DataDB1(context);
            dataDb4 = new DataDB4(context);
            try
            {
                rotateimg = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateimg.setDuration(5000);
                rotateimg.setRepeatCount(Animation.INFINITE);
                rotateimg.setInterpolator(new LinearInterpolator());
            }
            catch(Exception a)
            {
            }
            statusadd = (ImageView) rootView.findViewById(R.id.addstatus);
            nonet = (ImageView) rootView.findViewById(R.id.nonets);
            statussettings = (ImageView) rootView.findViewById(R.id.statussettings);
            layout = (SwipeRefreshLayout) rootView.findViewById(R.id.layout);
            recylerview = (RecyclerView) rootView.findViewById(R.id.recylerview);
            emoji = (ImageView) rootView.findViewById(R.id.emoji);
            addimage = (ImageView) rootView.findViewById(R.id.addimage);
            status = (EmojiEditText) rootView.findViewById(R.id.chintha);
            final EmojiPopup emojiPopup = EmojiPopup.Builder.fromRootView(rootView).build(status);
            emoji.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    emojiPopup.toggle();
                }
            });


            layout.setEnabled(true);
            loadingicon.setAnimation("loading.json");
            loadingicon.playAnimation();
            loadingicon.loop(true);
            feedStatuses = new ArrayList();
            apater = new ChinathakalAdapter(getActivity(), feedStatuses, status_frag.this);
            recylerview.setLayoutManager(new LinearLayoutManager(context));
            recylerview.setAdapter(apater);
            layout.setOnRefreshListener(new OnRefreshListener() {
                public void onRefresh() {
                    layout.setRefreshing(true);
                    nonet. setVisibility(View.GONE);
                    limit = 0;
                    loadstatus2();
                }
            });

            status.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {

                }
            });
            statussettings.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    settings_status();
                }
            });
            nonet.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (cd.isConnectingToInternet()) {
                        nonet. setVisibility(View.GONE);
                        limit = 0;
                        loadstatus();
                        return;
                    }
                    nonet.setVisibility(View.VISIBLE);
                    Toasty.info(context, (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            });
            if (Static_Variable.viewd_pfle == 0) {
                if (cd.isConnectingToInternet()) {
                    nonet. setVisibility(View.GONE);
                    limit = 0;
                    try {
                        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(mHandleMessageReceiver, new IntentFilter("com.statusappkal.Message"));
                    } catch (Exception e2) {
                    }
                    loadstatus();
                } else {
                    nonet.setVisibility(View.VISIBLE);
                    Toasty.info(context, (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            }
            statusadd.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (status.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(context, (CharSequence) "Please enter status", Toast.LENGTH_SHORT).show();
                        status.requestFocus();
                        return;
                    }
                    if (checkcharector(status.getText().toString())) {
                        Toasty.info(context, (CharSequence) Static_Variable.isenglish, Toast.LENGTH_SHORT).show();
                        status.requestFocus();
                    } else if (status.getText().toString().length() <= 50) {
                        Toasty.info(context, (CharSequence) Static_Variable.length_chintha, Toast.LENGTH_SHORT).show();
                        status.requestFocus();
                    } else if (cd.isConnectingToInternet()) {
                        try {
                            txtstatus1 = status.getText().toString();
                            byte[] data = status.getText().toString().getBytes(StandardCharsets.UTF_8);
                            txtstatus = Base64.encodeToString(data, 0);
                            status_updating();
                        } catch (Exception e) {
                        }
                    } else {
                        Toasty.info(context, (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e3) {

        Toast.makeText(context,Log.getStackTraceString(e3),Toast.LENGTH_LONG).show();
        }


        return rootView;
    }

    public void status_updating() {
        statusadd.setEnabled(false);
        status.setEnabled(false);
        emoji.setEnabled(false);
        try {
            statusadd.startAnimation(rotateimg);
        } catch (Exception e) {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"addstatus4.php";
        httpClient.newCall(new Builder().url(url).post(new FormBody.Builder().add("item", userDataDB.get_userid()+"%:"+txtstatus+"%:"+userDataDB.get_usermobile()+"%:"+dataDb4.get_lockcomments()).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            if (pd.isShowing()) {
                                pd.dismiss();
                            }
                            statusadd.setEnabled(true);
                            status.setEnabled(true);
                            emoji.setEnabled(true);
                            statusadd.clearAnimation();
                            if (result.contains("ok:%")) {
                                String[] k = result.split(":%");
                                dataDb1.add_cmntdetails(k[1], status.getText().toString());
                                status.setText("");
                                ChinthaFeeds item = new ChinthaFeeds();
                                item.setId(k[1]);
                                item.setuserid(userDataDB.get_userid());
                                item.set_name(k[3]);
                                item.setMobile(k[4]);
                                item.setProfilePic(Static_Variable.entypoint1+"userphotosmall/"+userDataDB.get_userid()+".jpg");
                                item.setStatus(txtstatus1);
                                item.setlikes(k[5]);
                                item.setshowmobile(k[6]);
                                item.setverified(k[7]);
                                item.setcmntcount("0");
                                item.setshowads("0");
                                item.setpinned("0");
                                try {
                                    Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                    c1.setTime(sdf.parse(k[8]));
                                    item.set_postdate(getFormattedDate(c1.getTimeInMillis()));
                                } catch (Exception e) {
                                    item.set_postdate(k[8]);
                                }
                                item.setimgsig(k[9]);
                                item.setstatustype(k[10]);
                                item.setphotourl(k[11]);
                                item.setphotodimension(k[12]);
                                item.setshortstatus(k[13]);
                                item.set_iscmntlock(k[14]);
                                item.set_statuscount(k[15]);
                                feedStatuses.add(0, item);
                                apater.notifyItemInserted(0);
                                recylerview.getLayoutManager().scrollToPosition(0);
                                try {
                                    mediaPlayer = MediaPlayer.create(context, R.raw.chintha_send);
                                    mediaPlayer.start();
                                } catch (Exception e2) {
                                }
                            } else if (result.contains("::limitover::")) {
                                Toasty.info(context, (CharSequence) Static_Variable.limited_post, Toast.LENGTH_SHORT).show();
                            } else if (result.contains("::block::")) {
                                Toasty.info(context, (CharSequence) "Sorry ! You are blocked by Admin", Toast.LENGTH_SHORT).show();
                            } else if (result.contains("block:%")) {
                                String[] k=result.split(":%");
                                Toasty.info(context, Static_Variable.error1 +"  "+ Static_Variable.error2 +" "+ k[1]+" "+ Static_Variable.err3_posting,Toast.LENGTH_SHORT).show();
                            } else if (result.contains("exist")) {
                                Toasty.info(context, (CharSequence) Static_Variable.dupli_post, Toast.LENGTH_SHORT).show();
                            } else {
                                Toasty.info(context, (CharSequence) result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void refersh() {
        if (cd.isConnectingToInternet()) {
            nonet. setVisibility(View.GONE);
            limit = 0;
            try {
                LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(mHandleMessageReceiver, new IntentFilter("com.statusappkal.Message"));
            } catch (Exception e) {
            }
            loadstatus();
            return;
        }
        nonet.setVisibility(View.VISIBLE);
        Toasty.info(context, (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(mHandleMessageReceiver);
    }

    public void loadstatus() {
        recylerview. setVisibility(View.GONE);
        loadingicon.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient();
        String url =Static_Variable.entypoint1+"getstatus_new1.php";
        httpClient.newCall(new Builder().url(url).post(new FormBody.Builder().add("item", limit+"").build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            int ispinned;
                            layout.setRefreshing(false);
                            if (result.contains("%:ok")) {
                                try {
                                    feedStatuses.clear();
                                    String[] got = result.split("%:");
                                    int k = (got.length - 1) / 18;
                                    int m = -1;
                                    for (int i = 1; i <= k; i++) {
                                        m=m+1;
                                        ChinthaFeeds item = new ChinthaFeeds();
                                        item.setId(got[m]);
                                        m=m+1;
                                        item.setuserid(got[m]);
                                        item.setProfilePic(Static_Variable.entypoint1+"userphotosmall/"+got[m]+".jpg");
                                        m=m+1;
                                        item.set_name(got[m]);
                                        m=m+1;
                                        item.setMobile(got[m]);
                                        m=m+1;
                                        if (got[m].equalsIgnoreCase("0")) {
                                            ispinned = 0;
                                        } else {
                                            ispinned = 1;
                                        }
                                        item.setpinned(got[m]);
                                        m=m+1;
                                        if (ispinned == 1) {
                                            try {
                                                item.setStatus(got[m]);
                                            } catch (Exception e) {
                                            }
                                        } else {
                                            if(got[m].equalsIgnoreCase("0"))
                                            {
                                                item.setStatus(got[m]);
                                            }
                                            else
                                            {
                                                item.setStatus(new String(Base64.decode(got[m], 0), StandardCharsets.UTF_8));
                                            }
                                        }
                                        m=m+1;
                                        item.setlikes(got[m]);
                                        m=m+1;
                                        item.setshowmobile(got[m]);
                                        m=m+1;
                                        item.setverified(got[m]);
                                        m=m+1;
                                        item.setcmntcount(got[m]);
                                        m=m+1;
                                        try {
                                            Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                            c1.setTime(sdf.parse(got[m]));
                                            item.set_postdate(getFormattedDate(c1.getTimeInMillis()));
                                        } catch (Exception e2) {
                                            item.set_postdate(got[m]);
                                        }
                                        m=m+1;
                                        item.setimgsig(got[m]);
                                        m=m+1;
                                        item.setstatustype(got[m]);
                                        m=m+1;
                                        item.setphotourl(got[m]);
                                        m=m+1;
                                        item.setphotodimension(got[m]);
                                        m=m+1;
                                        item.setshortstatus(got[m]);
                                        m=m+1;
                                        item.set_iscmntlock(got[m]);
                                        m=m+1;
                                        item.set_statuscount(got[m]);
                                        if (i % 5 == 0) {
                                            item.setshowads("1");
                                        } else {
                                            item.setshowads("0");
                                        }
                                        feedStatuses.add(item);
                                    }
                                } catch (Exception e3) {

                                    Log.w("Result333",Log.getStackTraceString(e3));
                                }
                                loadingicon. setVisibility(View.GONE);
                                recylerview.setVisibility(View.VISIBLE);
                                apater.notifyDataSetChanged();
                                return;
                            }
                            loadingicon. setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    public void loadstatus2() {
        limit = 0;
        OkHttpClient httpClient = new OkHttpClient();
        String url =Static_Variable.entypoint1+"getstatus_new1.php";
        httpClient.newCall(new Builder().url(url).post(new FormBody.Builder().add("item", limit+"").build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            int ispinned;
                            layout.setRefreshing(false);
                            if (result.contains("%:ok")) {
                                try {
                                    feedStatuses.clear();
                                    String[] got = result.split("%:");
                                    int k = (got.length - 1) / 18;
                                    int m = -1;
                                    for (int i = 1; i <= k; i++) {
                                        m=m+1;
                                        ChinthaFeeds item = new ChinthaFeeds();
                                        item.setId(got[m]);
                                        m=m+1;
                                        item.setuserid(got[m]);
                                        item.setProfilePic(Static_Variable.entypoint1+"userphotosmall/"+got[m]+".jpg");
                                        m=m+1;
                                        item.set_name(got[m]);
                                        m=m+1;
                                        item.setMobile(got[m]);
                                        m=m+1;
                                        if (got[m].equalsIgnoreCase("0")) {
                                            ispinned = 0;
                                        } else {
                                            ispinned = 1;
                                        }
                                        item.setpinned(got[m]);
                                        m=m+1;
                                        if (ispinned == 1) {
                                            try {
                                                item.setStatus(got[m]);
                                            } catch (Exception e) {
                                            }
                                        } else {
                                            if(got[m].equalsIgnoreCase("0"))
                                            {
                                                item.setStatus(got[m]);
                                            }
                                            else
                                            {
                                                item.setStatus(new String(Base64.decode(got[m], 0), StandardCharsets.UTF_8));
                                            }
                                        }
                                        m=m+1;
                                        item.setlikes(got[m]);
                                        m=m+1;
                                        item.setshowmobile(got[m]);
                                        m=m+1;
                                        item.setverified(got[m]);
                                        m=m+1;
                                        item.setcmntcount(got[m]);
                                        m=m+1;
                                        try {
                                            Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                            c1.setTime(sdf.parse(got[m]));
                                            item.set_postdate(getFormattedDate(c1.getTimeInMillis()));
                                        } catch (Exception e2) {
                                            item.set_postdate(got[m]);
                                        }
                                        m=m+1;
                                        item.setimgsig(got[m]);
                                        m=m+1;
                                        item.setstatustype(got[m]);
                                        m=m+1;
                                        item.setphotourl(got[m]);
                                        m=m+1;
                                        item.setphotodimension(got[m]);
                                        m=m+1;
                                        item.setshortstatus(got[m]);
                                        m=m+1;
                                        item.set_iscmntlock(got[m]);
                                        m=m+1;
                                        item.set_statuscount(got[m]);
                                        if (i % 5 == 0) {
                                            item.setshowads("1");
                                        } else {
                                            item.setshowads("0");
                                        }
                                        feedStatuses.add(item);
                                    }
                                } catch (Exception e3) {
                                }
                                layout.setRefreshing(false);
                                loadingicon. setVisibility(View.GONE);
                                recylerview.setVisibility(View.VISIBLE);
                                apater.notifyDataSetChanged();
                                return;
                            }
                            loadingicon. setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    public void loadstatus1() {
        limit += 30;
        OkHttpClient httpClient = new OkHttpClient();
        String url =Static_Variable.entypoint1+"getstatus_new1.php";
        httpClient.newCall(new Builder().url(url).post(new FormBody.Builder().add("item", limit+"").build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.w("Result",result);
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            int ispinned;
                            if (result.contains("%:ok")) {
                                String[] got = result.split("%:");
                                int k = (got.length - 1) / 18;
                                Log.w("TotalSize",k+"");
                                int m = -1;
                                for (int i = 1; i <= k; i++) {
                                    m=m+1;
                                    ChinthaFeeds item = new ChinthaFeeds();
                                    item.setId(got[m]);
                                    m=m+1;
                                    item.setuserid(got[m]);
                                    item.setProfilePic(Static_Variable.entypoint1+"userphotosmall/"+got[m]+".jpg");
                                    m=m+1;
                                    item.set_name(got[m]);
                                    m=m+1;
                                    item.setMobile(got[m]);
                                    m=m+1;
                                    if (got[m].equalsIgnoreCase("0")) {
                                        ispinned = 0;
                                    } else {
                                        ispinned = 1;
                                    }
                                    item.setpinned(got[m]);
                                    m=m+1;
                                    if (ispinned == 1) {
                                        try {
                                            item.setStatus(got[m]);
                                        } catch (Exception e) {
                                        }
                                    } else {
                                        if(got[m].equalsIgnoreCase("0"))
                                        {
                                            item.setStatus(got[m]);
                                        }
                                        else
                                        {
                                            item.setStatus(new String(Base64.decode(got[m], 0), StandardCharsets.UTF_8));
                                        }
                                    }
                                    m=m+1;
                                    item.setlikes(got[m]);
                                    m=m+1;
                                    item.setshowmobile(got[m]);
                                    m=m+1;
                                    item.setverified(got[m]);
                                    m=m+1;
                                    item.setcmntcount(got[m]);
                                    m=m+1;
                                    try {
                                        Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                        c1.setTime(sdf.parse(got[m]));
                                        item.set_postdate(getFormattedDate(c1.getTimeInMillis()));
                                    } catch (Exception e2) {
                                        item.set_postdate(got[m]);
                                    }
                                    m=m+1;
                                    item.setimgsig(got[m]);
                                    m=m+1;
                                    item.setstatustype(got[m]);
                                    m=m+1;
                                    item.setphotourl(got[m]);
                                    m=m+1;
                                    item.setphotodimension(got[m]);
                                    m=m+1;
                                    item.setshortstatus(got[m]);
                                    m=m+1;
                                    item.set_iscmntlock(got[m]);
                                    m=m+1;
                                    item.set_statuscount(got[m]);
                                    if (i % 5 == 0) {
                                        item.setshowads("1");
                                    } else {
                                        item.setshowads("0");
                                    }
                                    feedStatuses.add(item);
                                }
                                apater.notifyDataSetChanged();
                                return;
                            }
                            loadingicon. setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
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

    public void removeitem(int position) {
        feedStatuses.remove(position);
        apater.notifyDataSetChanged();
    }

    public void showalert2(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        try {
            ((TextView) alert.findViewById(R.id.message)).setTypeface(face);
        } catch (Exception e) {
        }
    }

    public void status_report(AppCompatActivity actv) {
        Dialog dialog2 = new Dialog(actv);
        dialog2.requestWindowFeature(1);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.setCancelable(true);
        dialog2.setContentView(R.layout.report_chinthakal);
        View findViewById = dialog2.findViewById(R.id.rdgroup);
        TextView text = (TextView) dialog2.findViewById(R.id.text);
        TextView text1 = (TextView) dialog2.findViewById(R.id.text1);
        Button update = (Button) dialog2.findViewById(R.id.rpt_update);
        RadioButton chatting = (RadioButton) dialog2.findViewById(R.id.rpt_chatting);
        RadioButton thery = (RadioButton) dialog2.findViewById(R.id.rpt_thery);
        RadioButton parihasam = (RadioButton) dialog2.findViewById(R.id.parihasam);
        final RadioButton ashleelam = (RadioButton) dialog2.findViewById(R.id.rpt_ashleelam);
        RadioButton other = (RadioButton) dialog2.findViewById(R.id.other);
        text.setTypeface(face);
        text1.setTypeface(face);
        text.setText(Static_Variable.chinthas_rpt);
        text1.setText(Static_Variable.status_rpt1);
        chatting.setText(Static_Variable.r_chattings);
        thery.setText(Static_Variable.r_theri);
        parihasam.setText(Static_Variable.r_parihasakan);
        ashleelam.setText(Static_Variable.reason_ashleelam);
        other.setText(Static_Variable.other_reason);

        update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chatting.isChecked() || thery.isChecked() || parihasam.isChecked() || ashleelam.isChecked() || other.isChecked()) {
                    if (chatting.isChecked()) {
                        reporttype = "1";
                    } else if (thery.isChecked()) {
                        reporttype = "2";
                    } else if (parihasam.isChecked()) {
                        reporttype = "3";
                    } else if (ashleelam.isChecked()) {
                        reporttype = "4";
                    } else if (ashleelam.isChecked()) {
                        reporttype = "4";
                    } else if (other.isChecked()) {
                        reporttype = "5";
                    }
                    Static_Variable.typeofstatus = "0";
                    ((HeartOf_App) actv).status_report();
                    dialog2.dismiss();
                }
                else
                {
                    Toasty.info(context, (CharSequence) "Please select atleast one option", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog2.show();
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void editstatus() {
        final Dialog dialog2 = new Dialog(getActivity());
        dialog2.requestWindowFeature(1);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.chintha_editchintha_custom);
        final EditText status2 = (EditText) dialog2.findViewById(R.id.chintha);
        Button update = (Button) dialog2.findViewById(R.id.rpt_update);
        Button close = (Button) dialog2.findViewById(R.id.close);
        status2.setText(Static_Variable.chintha_text);
        TextView alert = (TextView) dialog2.findViewById(R.id.alert);
        alert.setTypeface(face);
        alert.setText(Static_Variable.chintha_msgalert);
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (status2.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(context, (CharSequence) "Please enter status", Toast.LENGTH_SHORT).show();
                    status2.requestFocus();
                } else if (checkcharector(status2.getText().toString())) {
                    Toasty.info(context, (CharSequence) Static_Variable.isenglish, Toast.LENGTH_SHORT).show();
                    status2.requestFocus();
                } else if (status2.getText().toString().length() <= 50) {
                    Toasty.info(context, (CharSequence) Static_Variable.length_chintha, Toast.LENGTH_SHORT).show();
                    status2.requestFocus();
                } else if (cd.isConnectingToInternet()) {
                    try {
                        byte[] data = status2.getText().toString().getBytes(StandardCharsets.UTF_8);
                        txtstatus = Base64.encodeToString(data, 0);
                    } catch (Exception e) {
                    }
                    statusedit();
                    dialog2.dismiss();
                } else {
                    Toasty.info(context, (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        dialog2.show();
    }

    public void statusedit() {
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();
        timerDelayRemoveDialog(50000, pd);
        OkHttpClient httpClient = new OkHttpClient();
        String url =Static_Variable.entypoint1+"editstatus.php";
        httpClient.newCall(new Builder().url(url).post(new FormBody.Builder().add("item", userDataDB.get_username()+"%:"+Static_Variable.chintha_Id+"%:"+txtstatus).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            if (result.contains("ok")) {
                                refersh();
                            } else if (result.contains("block:%")) {
                                String[] k = result.split(":%");
                                Toasty.info(context, Static_Variable.error1+" "+Static_Variable.error2+" "+k[1]+" "+Static_Variable.err3_posting, Toast.LENGTH_SHORT).show();
                            } else {
                                Toasty.info(context, (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public boolean checkcharector(String text) {
        boolean found = false;
        int i = 0;
        while (true) {
            if (i < text.length()) {
                if (text.toUpperCase().charAt(i) >= 'A' && text.toUpperCase().charAt(i) <= 'Z') {
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

    public void delete_alert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    delete_status();
                } else {
                    Toasty.info(context, (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void delete_status() {
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();
        timerDelayRemoveDialog(50000, pd);
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"deletestatus.php";
        httpClient.newCall(new Builder().url(url).post(new FormBody.Builder().add("item", Static_Variable.chintha_Id).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            if (pd != null || pd.isShowing()) {
                                pd.dismiss();
                                if (result.contains("ok")) {
                                    removeitem(Static_Variable.pos);
                                } else {
                                    Toasty.info(context, (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public void loadmore() {
        if (cd.isConnectingToInternet()) {
            loadstatus1();
        } else {
            Toasty.info(context, (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
        }
    }

    public void update_shortstatus() {
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();
        timerDelayRemoveDialog(50000, pd);
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"update_shortstatus.php";
        httpClient.newCall(new Builder().url(url).post(new FormBody.Builder().add("item", txt_shotstatus+":%"+userDataDB.get_userid()).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            if (pd != null || pd.isShowing()) {
                                pd.dismiss();
                                if (result.contains("ok")) {
                                    dataDb4.add_short_status(txt_shotstatus);
                                    dialog.dismiss();
                                    loadstatus();
                                    return;
                                }
                                Toasty.info(context, (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void settings_status() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.statussettings);
        final CheckBox commentblock = (CheckBox) dialog.findViewById(R.id.commentblock);
        final EditText shortstatus = (EditText) dialog.findViewById(R.id.shortstatus);
        TextView txtshortstatus = (TextView) dialog.findViewById(R.id.txtshortstatus);
        Button update = (Button) dialog.findViewById(R.id.rpt_update);
        txtshortstatus.setText("ഇപ്പോ എന്താ അവസ്ഥ ?");
        txtshortstatus.setTypeface(face);
        commentblock.setText("ഈ ചിന്തയുടെ കമന്റ് ബോക്‌സ് പൂട്ടണം ");
        commentblock.setTypeface(face);
        commentblock.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (commentblock.isChecked()) {
                    dataDb4.add_lockcomments("1");
                } else {
                    dataDb4.add_lockcomments("0");
                }
            }
        });
        if (dataDb4.get_lockcomments().equalsIgnoreCase("0")) {
            commentblock.setChecked(false);
        } else {
            commentblock.setChecked(true);
        }
        try {
            shortstatus.setText(dataDb4.get_short_status());
            shortstatus.setSelection(shortstatus.getText().length());
        } catch (Exception e) {
        }
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (shortstatus.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(context, (CharSequence) "ദയവായി ഒരു ചിന്ത എഴുതുക ", Toast.LENGTH_SHORT).show();
                    shortstatus.requestFocus();
                    return;
                }
                txt_shotstatus = shortstatus.getText().toString();
                if (cd.isConnectingToInternet()) {
                    update_shortstatus();
                } else {
                    Toasty.info(context, (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        public void onReceive(Context arg0, Intent arg1) {
            try {
                JSONObject json = new JSONObject(arg1.getExtras().getString("salmanstatus"));
                String _status = json.getString(NotificationCompat.CATEGORY_STATUS);
                if (!json.getString("notitype").equalsIgnoreCase(NotificationCompat.CATEGORY_STATUS)) {
                    return;
                }
                if (!msg.equalsIgnoreCase(_status)) {
                    if (dataDb.get_visible().equalsIgnoreCase("1") && !userDataDB.get_userid().equalsIgnoreCase(json.getString("userid"))) {
                        Collections.reverse(feedStatuses);
                        ChinthaFeeds item = new ChinthaFeeds();
                        item.setId(json.getString("statusid"));
                        item.setuserid(json.getString("userid"));
                        item.set_name(json.getString(ConditionalUserProperty.NAME));
                        item.setMobile(json.getString("mobile"));
                        item.setProfilePic(Static_Variable.entypoint1+"userphotosmall/"+json.getString("userid")+".jpg");
                        item.setStatus(json.getString(NotificationCompat.CATEGORY_STATUS));
                        item.setlikes("0");
                        item.setshowmobile(json.getString("showmobile"));
                        item.setverified(json.getString("verified"));
                        item.setimgsig(json.getString("imgsig"));
                        item.set_iscmntlock(json.getString("cmntlock"));
                        item.setshortstatus(json.getString("shortstatus"));
                        item.setcmntcount("0");
                        item.setshowads("1");
                        item.set_statuscount(json.getString("statuscount"));
                        item.setpinned("0");
                        try {
                            Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                            c1.setTime(sdf.parse(json.getString("postdate")));
                            item.set_postdate(getFormattedDate(c1.getTimeInMillis()));
                        } catch (Exception e) {
                            item.set_postdate(json.getString("postdate"));
                        }
                        try {
                            item.setstatustype("0");
                            item.setphotourl("0");
                            item.setphotodimension("0");
                        } catch (Exception e2) {
                        }
                        feedStatuses.add(item);
                        Collections.reverse(feedStatuses);
                        apater.notifyDataSetChanged();
                        msg = json.getString(NotificationCompat.CATEGORY_STATUS);
                        try {
                            if (firstitem != 0) {
                                mediaPlayer = MediaPlayer.create(context, R.raw.chintha_send);
                                mediaPlayer.start();
                            }
                        } catch (Exception e3) {
                        }
                    }
                }
            } catch (Exception e4) {
            }
        }
    };

}
