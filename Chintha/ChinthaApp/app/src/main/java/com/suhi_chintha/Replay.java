package com.suhi_chintha;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import chintha_adapter.Replay_notiAdapter;
import chintha_data.Replay_Feed;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;

import es.dmoral.toasty.Toasty;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class Replay extends AppCompatActivity {
    public static String comments_txt = "";
    public AdView adView1;
    public AdView adView2;
    public ImageView add_rply;
    AdRequest adreq1;
    public Replay_notiAdapter apater1;
    NetConnection cd;
    int count1 = 0;
    final DataDB2 dataDb2 = new DataDB2(this);
    public ImageView emptydata1;
    Typeface face;
    public List<Replay_Feed> feed1;
    public ImageView img1;
    public RelativeLayout layout;
    RelativeLayout layoutloadmore1;
    public int limit = 0;
    public ListView listview1;
    LottieAnimationView load_cion;
    TextView loadmore1;
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        public void onReceive(Context arg0, Intent arg1) {
            try {
                JSONObject json = new JSONObject(arg1.getExtras().getString("salmanstatus"));
                if (json.getString("notitype").equalsIgnoreCase("replay")) {
                    if (!msg.equalsIgnoreCase(json.getString("replay"))) {
                        if (dataDb2.get_rplyvisible().equalsIgnoreCase("1") && !userDataDB.get_userid().equalsIgnoreCase(json.getString("userid"))) {
                            if (reply_commentid.trim().equalsIgnoreCase(json.getString("cmntid"))) {
                                Replay_Feed item = new Replay_Feed();
                                item.set_sn(json.getString("replayid"));
                                item.setuserid(json.getString("userid"));
                                item.set_name(json.getString("name"));
                                item.set_cmnts(Base64.encodeToString(json.getString("replay").getBytes(StandardCharsets.UTF_8), 0));
                                item.set_dppic(Static_Variable.entypoint1+"userphotosmall/"+json.getString("userid")+".jpg");
                                try {
                                    Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                    c1.setTime(sdf.parse(json.getString("postdate")));
                                    item.set_postdate(getFormattedDate(c1.getTimeInMillis()));
                                } catch (Exception e) {
                                    item.set_postdate(json.getString("postdate"));
                                }
                                item.set_imgsig(json.getString("replyimgsig"));
                                feed1.add(item);
                                apater1.notifyDataSetChanged();
                                listview1.setSelection(apater1.getCount() - 1);
                                msg = json.getString("replay");
                                try {
                                    mp = MediaPlayer.create(getApplicationContext(), R.raw.chintha_send);
                                    mp.start();
                                } catch (Exception a) {
                                    Toasty.info(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            } catch (Exception e4) {
            }
        }
    };
    ImageView moveback;
    public MediaPlayer mp;
    String msg = "";
    public TextView name1;
    public ImageView nonet1;
    ProgressBar pb2;
    ProgressDialog pd;
    public ImageView popup1;
    String replay_imgsig = "0";
    int replaycount = 0;
    String replayuserid = "";
    public EmojiEditText reply;
    public String reply_commentid;
    RotateAnimation rotate;
    public TextView status1;
    TextView text;
    String txtreplay = "";
    final User_DataDB userDataDB = new User_DataDB(this);
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_replay);
        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        } catch (Exception e) {
        }
        try {
            rotate= new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(5000);
            rotate.setRepeatCount(Animation.INFINITE);
            rotate.setInterpolator(new LinearInterpolator());
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
            }
        } catch (Exception e) {
        }
        try {
            adreq1 = new Builder().build();
            cd = new NetConnection(this);
            moveback = (ImageView) findViewById(R.id.moveback);
            text = (TextView) findViewById(R.id.text);
            pd = new ProgressDialog(this);
            face = Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
            img1 = (ImageView) findViewById(R.id.img);
            name1 = (TextView) findViewById(R.id.name);
            popup1 = (ImageView) findViewById(R.id.options);
            status1 = (TextView) findViewById(R.id.chintha);
            emptydata1 = (ImageView) findViewById(R.id.emptydata);
            nonet1 = (ImageView) findViewById(R.id.nonets);
            load_cion = (LottieAnimationView) findViewById(R.id.lotty_loadin);
            listview1 = (ListView) findViewById(R.id.listview);
            layoutloadmore1 = (RelativeLayout) findViewById(R.id.layoutloadmore);
            loadmore1 = (TextView) findViewById(R.id.loadmore);
            pb2 = (ProgressBar) findViewById(R.id.progress_pb1);
            loadmore1.setText(Static_Variable.morereplays);
            loadmore1.setTypeface(face);
            adView2 = (AdView) findViewById(R.id.adView2);
            text.setTypeface(face);
            text.setText("റീപ്ലേ");
            moveback.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            try {
                adView2.setAdListener(new AdListener() {
                    public void onAdFailedToLoad(int errorCode) {
                        try {
                            if (count1 <= 10) {
                                adView2.loadAd(adreq1);
                                count1++;
                            }
                        } catch (Exception e) {
                        }
                    }
                });
            } catch (Exception e3) {
            }
            feed1 = new ArrayList();
            apater1 = new Replay_notiAdapter(this, feed1);
            count1 = 0;
            adView2.loadAd(adreq1);
            listview1.setOnScrollListener(new OnScrollListener() {
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (firstVisibleItem != 0) {
                        layoutloadmore1. setVisibility(View.GONE);
                    } else if (apater1.getCount() > 20) {
                        layoutloadmore1.setVisibility(View.VISIBLE);
                        layoutloadmore1.setEnabled(true);
                    } else {
                        layoutloadmore1. setVisibility(View.GONE);
                    }
                }
            });
            layoutloadmore1.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (cd.isConnectingToInternet()) {
                        limit += 30;
                        pb2.setVisibility(View.VISIBLE);
                        layoutloadmore1.setEnabled(false);
                        loadreplay1();
                        return;
                    }
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet_scroll, Toast.LENGTH_SHORT).show();
                }
            });
            nonet1.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (cd.isConnectingToInternet()) {
                        nonet1. setVisibility(View.GONE);
                        limit = 0;
                        loadreplay();
                        return;
                    }
                    nonet1.setVisibility(View.VISIBLE);
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            });
            add_rply = (ImageView) findViewById(R.id.addreplay);
            reply = (EmojiEditText) findViewById(R.id.reply);
            layout = (RelativeLayout) findViewById(R.id.layout);
            listview1.setAdapter(apater1);
            ImageView emoji=findViewById(R.id.emoji);
            RelativeLayout layout=findViewById(R.id.layout);
            final EmojiPopup emojiPopup = EmojiPopup.Builder.fromRootView(layout).build(reply);
            emoji.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(emojiPopup.isShowing())
                    {
                        emoji.setImageDrawable(getResources().getDrawable(R.drawable.emojies));
                    }
                    else
                    {
                        emoji.setImageDrawable(getResources().getDrawable(R.drawable.emojikeyboard));
                    }
                    emojiPopup.toggle();

                }
            });

            reply.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    emojiPopup.dismiss();
                    emoji.setImageDrawable(getResources().getDrawable(R.drawable.emojies));
                }
            });
            popup1.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    settings_replay();
                }
            });
            ArrayList<String> id1 = dataDb2.get_rplycmnt();
            String[] c = (String[]) id1.toArray(new String[id1.size()]);
            try {
                reply_commentid = c[0].trim();
                Static_Variable.sn_comments = reply_commentid;
                replayuserid = c[1];
                name1.setText(c[2]);
                status1.setText(c[4]);
            } catch (Exception e4) {
            }
            replay_imgsig = c[5];
            Glide.with(getApplicationContext()).load(c[3]).apply(new RequestOptions().signature(new ObjectKey(replay_imgsig))).transition(DrawableTransitionOptions.withCrossFade()).into(img1);
            add_rply.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (reply.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), (CharSequence) "Please enter a reply", Toast.LENGTH_SHORT).show();
                    } else if (reply.getText().toString().length() < 2) {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.error_replay, Toast.LENGTH_SHORT).show();
                    } else if (cd.isConnectingToInternet()) {
                        if (dataDb2.getcommentstore1(reply_commentid.trim()).equalsIgnoreCase("")) {
                            dataDb2.add_storecmnt(reply_commentid.trim(), status1.getText().toString());
                        }
                        try {
                            byte[] data = reply.getText().toString().getBytes(StandardCharsets.UTF_8);
                            txtreplay = Base64.encodeToString(data, 0);
                            if (replaycount % 3 == 0) {
                                replaycount = 0;
                            } else {
                                replaycount++;
                            }
                            post_repaly();
                        } catch (Exception e) {
                        }
                    } else {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            if (cd.isConnectingToInternet()) {
                nonet1. setVisibility(View.GONE);
                limit = 0;
                loadreplay();
                return;
            }
            nonet1.setVisibility(View.VISIBLE);
            Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
        } catch (Exception a) {
           // Toasty.info(getApplicationContext(), (CharSequence) Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        dataDb2.drop_rplyvisible();
    }

    public void post_repaly() {
        String str = "";
        String txtname = name1.getText().toString();
        add_rply.setEnabled(false);
        reply.setEnabled(false);
        emptydata1. setVisibility(View.GONE);
        nonet1. setVisibility(View.GONE);
        try {
            add_rply.startAnimation(rotate);
        } catch (Exception e) {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerupdate(50000, pd);
        }
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"addreply6.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", userDataDB.get_userid()+"%:"+reply_commentid.trim()+"%:"+txtreplay+"%:"+userDataDB.get_username()+"%:"+replayuserid+"%:"+txtname+"%:"+userDataDB.get_imgsig()).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (pd.isShowing()) {
                                pd.dismiss();
                            }
                            add_rply.setEnabled(true);
                            reply.setEnabled(true);
                            reply.setText("");
                            add_rply.clearAnimation();
                            if (result.contains("::ok")) {
                                String[] k = result.split("::");
                                Replay_Feed item = new Replay_Feed();
                                item.set_sn(k[0]);
                                item.setuserid(userDataDB.get_userid());
                                item.set_name(userDataDB.get_username());
                                item.set_cmnts(txtreplay);
                                item.set_dppic(Static_Variable.entypoint1+"userphotosmall/"+userDataDB.get_userid()+".jpg");
                                try {
                                    Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                    c1.setTime(sdf.parse(k[1]));
                                    item.set_postdate(getFormattedDate(c1.getTimeInMillis()));
                                } catch (Exception e) {
                                    item.set_postdate(k[1]);
                                }
                                item.set_imgsig(replay_imgsig);
                                feed1.add(item);
                                apater1.notifyDataSetChanged();
                                listview1.setSelection(apater1.getCount() - 1);
                                try {
                                    mp = MediaPlayer.create(getApplicationContext(), R.raw.chintha_send);
                                    mp.start();
                                } catch (Exception e2) {
                                }
                            } else if (result.contains("::blockuser:%")) {
                                String[] p = result.split(":%");
                                Toasty.info(getApplicationContext(), "ക്ഷമിക്കണം !! താങ്കളെ "+p[1]+" ബ്ലോക്ക് ചെയ്തത് കാരണം ഈ കമന്റിന് റിപ്ലേ ചെയ്യുവാന്‍ സാധിക്കില്ല", Toast.LENGTH_LONG).show();
                            } else if (result.contains("::block::")) {
                                Toasty.info(getApplicationContext(), "Sorry ! You are blocked by Admin", Toast.LENGTH_SHORT).show();
                            } else {
                                Toasty.info(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void loadreplay() {
        add_rply.setVisibility(View.INVISIBLE);
        nonet1. setVisibility(View.GONE);
        emptydata1. setVisibility(View.GONE);
        load_cion.setVisibility(View.VISIBLE);
        limit = 0;
        feed1.clear();
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"getreplay.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item",limit+"%:"+reply_commentid).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            add_rply.setVisibility(View.VISIBLE);
                            if (result.contains("%:ok")) {
                                String[] got = result.split("%:");
                                int k = (got.length - 1) / 6;
                                int m = -1;
                                Collections.reverse(feed1);
                                for (int i = 1; i <= k; i++) {
                                    Replay_Feed item = new Replay_Feed();
                                    m=m+1;
                                    item.set_sn(got[m]);
                                    m=m+1;
                                    item.setuserid(got[m]);
                                    item.set_dppic(Static_Variable.entypoint1+"userphotosmall/"+got[m]+".jpg");
                                    m=m+1;
                                    item.set_name(got[m]);
                                    m=m+1;
                                    item.set_cmnts(got[m]);
                                    m=m+1;
                                    try {
                                        Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                        c1.setTime(sdf.parse(got[m]));
                                        item.set_postdate(getFormattedDate(c1.getTimeInMillis()));
                                    } catch (Exception e) {
                                        item.set_postdate(got[m]);
                                    }
                                    m=m+1;
                                    item.set_imgsig(got[m]);
                                    feed1.add(item);
                                }
                                Collections.reverse(feed1);
                                load_cion. setVisibility(View.GONE);
                                apater1.notifyDataSetChanged();
                                listview1.setSelection(apater1.getCount() - 1);
                                return;
                            }
                            layoutloadmore1. setVisibility(View.GONE);
                            emptydata1.setVisibility(View.VISIBLE);
                            load_cion. setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    public void loadreplay1() {
        pb2.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"getreplay.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item",limit+"%:"+reply_commentid).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (result.contains("%:ok")) {
                                String[] got = result.split("%:");
                                int k = (got.length - 1) / 6;
                                int m = -1;
                                Collections.reverse(feed1);
                                for (int i = 1; i <= k; i++) {
                                    Replay_Feed item = new Replay_Feed();
                                    m=m+1;
                                    item.set_sn(got[m]);
                                    m=m+1;
                                    item.setuserid(got[m]);
                                    item.set_dppic(Static_Variable.entypoint1+"userphotosmall/"+got[m]+".jpg");
                                    m=m+1;
                                    item.set_name(got[m]);
                                    m=m+1;
                                    item.set_cmnts(got[m]);
                                    m=m+1;
                                    try {
                                        Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                        c1.setTime(sdf.parse(got[m]));
                                        item.set_postdate(getFormattedDate(c1.getTimeInMillis()));
                                    } catch (Exception e) {
                                        item.set_postdate(got[m]);
                                    }
                                    m=m+1;
                                    item.set_imgsig(got[m]);
                                    feed1.add(item);
                                }
                                Collections.reverse(feed1);
                                apater1.notifyDataSetChanged();
                                listview1.setSelection(0);
                                pb2. setVisibility(View.GONE);
                                return;
                            }
                            pb2. setVisibility(View.GONE);
                            layoutloadmore1. setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    public void editreply() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_editstatus);
        dialog.setTitle("Edit Reply");
        dialog.setCancelable(false);
        final EditText status = (EditText) dialog.findViewById(R.id.chintha);
        Button update = (Button) dialog.findViewById(R.id.rpt_update);
        Button close = (Button) dialog.findViewById(R.id.close);
        status.setText(List_noti.comments_txt);
        TextView alert = (TextView) dialog.findViewById(R.id.alert);
        alert.setTypeface(face);
        alert.setText(Static_Variable.addtext_replay);
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (status.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(getApplicationContext(), (CharSequence) "Please enter a commant", Toast.LENGTH_SHORT).show();
                } else if (status.getText().toString().length() < 2) {
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.err_cmnt, Toast.LENGTH_SHORT).show();
                } else if (cd.isConnectingToInternet()) {
                    try {
                        byte[] data = status.getText().toString().getBytes(StandardCharsets.UTF_8);
                        txtreplay = Base64.encodeToString(data, 0);
                    } catch (Exception e) {
                    }
                    edit_replay();
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

    public void edit_replay() {
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();
        timerupdate(50000, pd);
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"editreply.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", Static_Variable.id_comment+"%:"+txtreplay).build()).build()).enqueue(new Callback() {
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
                                    refresh1();
                                } else if (result.contains("block:%")) {
                                    String[] k = result.split(":%");
                                    Toasty.info(getApplicationContext(), Static_Variable.error1_cmntpost+" "+Static_Variable.error2_cmntpost+" "+k[1]+" "+Static_Variable.error3_cmntpost, Toast.LENGTH_SHORT).show();
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

    public void timerupdate(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHandleMessageReceiver);
        dataDb2.drop_rplyvisible();
    }

    public void refresh1() {
        if (cd.isConnectingToInternet()) {
            nonet1. setVisibility(View.GONE);
            limit = 0;
            loadreplay();
            return;
        }
        nonet1.setVisibility(View.VISIBLE);
        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
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
    public void removeitem1(int position) {
        feed1.remove(position);
        apater1.notifyDataSetChanged();
    }
    public void onResume() {
        super.onResume();
        try {
            LocalBroadcastManager.getInstance(this).registerReceiver(mHandleMessageReceiver, new IntentFilter("com.statusappkal.Message"));
            dataDb2.add_rplyvisible("1");
        } catch (Exception e) {
        }
        count1 = 0;
    }

    public void settings_replay() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.replaynoti_disabling);
        final CheckBox check = (CheckBox) dialog.findViewById(R.id.check);
        check.setText(Static_Variable.noti_replay);
        check.setTypeface(face);
        if (dataDb2.get_disablerply(reply_commentid).equalsIgnoreCase("")) {
            check.setChecked(false);
        } else {
            check.setChecked(true);
        }
        check.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (check.isChecked()) {
                    dataDb2.del_disablerply(reply_commentid);
                    dataDb2.add_rplydisable(reply_commentid);
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.reject_replay, Toast.LENGTH_SHORT).show();
                    return;
                }
                dataDb2.del_disablerply(reply_commentid);
                Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.accpet_replay, Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}
