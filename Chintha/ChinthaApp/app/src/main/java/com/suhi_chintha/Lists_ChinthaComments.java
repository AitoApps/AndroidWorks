package com.suhi_chintha;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.EmojiTextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import chintha_adapter.CommentsAdapter;
import chintha_data.CommentFeed;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Lists_ChinthaComments extends AppCompatActivity {
    public static String txtcommentid = "";
    public static String txtcomments = "";
    public static String txtcomments1 = "";
    public AdView adView1;
    ImageView addcomment;
    AdRequest adreq1;
    public CommentsAdapter apater;
    ImageView back;
    ImageView blk_comments;
    RelativeLayout bottom_lyt;
    NetConnection cd;
    int cmntcount = 0;
    public EditText comments;
    int count = 0;
    public DataDb dataDb;
    public DataDB1 dataDb1;
    final DataDB2 dataDb2 = new DataDB2(this);
    final DataDB4 dataDb4 = new DataDB4(this);
    public Dialog dialog;
    ImageView drop;
    ImageView emptydata;
    Typeface face;

    public List<CommentFeed> feed;
    boolean flag = false;
    RelativeLayout headerview;
    public RelativeLayout layout;
    RelativeLayout layoutloadmore;
    public int limit = 0;
    ListView list;
    LottieAnimationView load_cion;
    TextView loadmore;
    RelativeLayout mainlayout;
    public MediaPlayer mp;
    public String msg = "";
    TextView name;
    ImageView nonet;
    ProgressBar pb1;
    ProgressDialog pd;
    ProgressDialog pd1;
    public String photodim = "";
    ImageView photostatus;
    public String photourl = "";
    ImageView popup;
    public ImageView private1;
    int replaycount = 0;
    RotateAnimation rotate;
    EmojiTextView status;
    public String statusid = "";
    public String statusimgsig = "0";
    public String statustype = "";
    String statususerid = "";
    TextView text;
    final User_DataDB userDataDB = new User_DataDB(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.commentslist_actvty);
        try {
            getWindow().setFlags(8192, 8192);
        } catch (Exception e) {
        }
        bottom_lyt = (RelativeLayout) findViewById(R.id.bottom_lyt);
        blk_comments = (ImageView) findViewById(R.id.commentblock);
        load_cion = (LottieAnimationView) findViewById(R.id.lotty_loadin);
        private1 = (ImageView) findViewById(R.id.private1);
        pd1 = new ProgressDialog(this);
        pd1.setMessage("Please wait..loading ads");
        pd1.setCancelable(true);
        adView1 = (AdView) findViewById(R.id.adView1);
        adreq1 = new Builder().build();
        list = (ListView) findViewById(R.id.listview);
        text = (TextView) findViewById(R.id.text);
        mainlayout = (RelativeLayout) findViewById(R.id.mainlayout);
        popup = (ImageView) findViewById(R.id.options);
        face = Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
        text.setText(Static_Variable.text_cmnts);
        text.setTypeface(face);
        back = (ImageView) findViewById(R.id.moveback);
        addcomment = (ImageView) findViewById(R.id.comment_add);
        emptydata = (ImageView) findViewById(R.id.emptydata);
        drop = (ImageView) findViewById(R.id.del);
        nonet = (ImageView) findViewById(R.id.nonets);
        dataDb = new DataDb(this);
        dataDb1 = new DataDB1(this);
        cd = new NetConnection(this);
        pd = new ProgressDialog(this);
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
        } catch (Exception e3) {
        }
        try {
            headerview = (RelativeLayout) getLayoutInflater().inflate(R.layout.top_comments, null);

            layoutloadmore = (RelativeLayout) headerview.findViewById(R.id.layoutloadmore);
            loadmore = (TextView) headerview.findViewById(R.id.loadmore);
            pb1 = (ProgressBar) headerview.findViewById(R.id.progress_pb1);
            loadmore.setText(Static_Variable.comments_load);
            loadmore.setTypeface(face);
            list.addHeaderView(headerview);
            layout = (RelativeLayout) findViewById(R.id.layout);
            comments = (EditText) findViewById(R.id.commentslist);
            addcomment = (ImageView) findViewById(R.id.comment_add);
            ImageView emoji=findViewById(R.id.emoji);
            RelativeLayout layout=findViewById(R.id.layout);
            final EmojiPopup emojiPopup = EmojiPopup.Builder.fromRootView(layout).build(comments);
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


            comments.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    emojiPopup.dismiss();
                    emoji.setImageDrawable(getResources().getDrawable(R.drawable.emojies));

                }
            });




            drop.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    showalert("Are you sure ?");
                }
            });
            popup.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    settings_comments();
                }
            });
            blk_comments.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    showalert_commentblok();
                }
            });
            feed = new ArrayList();
            apater = new CommentsAdapter(this, feed);
            list.setAdapter(apater);
            back.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    onBackPressed();
                }
            });
            addcomment.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (comments.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), (CharSequence) "Please enter a comment", Toast.LENGTH_SHORT).show();
                    } else if (comments.getText().toString().length() < 2) {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.err_cmnt, Toast.LENGTH_SHORT).show();
                    } else if (cd.isConnectingToInternet()) {
                        try {
                            if (dataDb1.get_commntdtls(statusid.trim()).equalsIgnoreCase("")) {
                                dataDb1.add_cmntdetails(statusid.trim(), status.getText().toString());
                            }
                            txtcomments = Base64.encodeToString(comments.getText().toString().getBytes(StandardCharsets.UTF_8), 0);
                            if (cmntcount % 3 == 0) {
                                cmntcount = 0;
                            } else {
                                cmntcount++;
                            }
                            postcomment();
                        } catch (Exception e) {
                        }
                    } else {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            list.setOnScrollListener(new OnScrollListener() {
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (firstVisibleItem != 0) {
                        layoutloadmore. setVisibility(View.GONE);
                    } else if (apater.getCount() > 20) {
                        layoutloadmore.setVisibility(View.VISIBLE);
                        layoutloadmore.setEnabled(true);
                    } else {
                        layoutloadmore. setVisibility(View.GONE);
                    }
                }
            });
            layoutloadmore.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (cd.isConnectingToInternet()) {
                        limit += 30;
                        pb1.setVisibility(View.VISIBLE);
                        layoutloadmore.setEnabled(false);
                        commentload1();
                        return;
                    }
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet_scroll, Toast.LENGTH_SHORT).show();
                }
            });
            File f = new File(Environment.getExternalStorageDirectory()+File.separator+Static_Variable.foldername+"/bg/bg.png");
            if (f.exists()) {
                try {
                    Glide.with(getApplicationContext()).asBitmap().load(f).into(new SimpleTarget<Bitmap>() {
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            mainlayout.setBackground(new BitmapDrawable(getResources(), bitmap));
                        }

                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                        }
                    });
                } catch (Exception e4) {
                }
            }
            nonet.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (cd.isConnectingToInternet()) {
                        nonet. setVisibility(View.GONE);
                        limit = 0;
                        comment_load();
                        return;
                    }
                    nonet.setVisibility(View.VISIBLE);
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            });
            private1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (cd.isConnectingToInternet()) {
                        new fvrts1().execute(new String[0]);
                    } else {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e5) {
        }
    }
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        public void onReceive(Context arg0, Intent arg1) {
            try {
                JSONObject json = new JSONObject(arg1.getExtras().getString("salmanstatus"));
                if (json.getString("notitype").equalsIgnoreCase("comment")) {
                    if (!msg.equalsIgnoreCase(json.getString("comment"))) {
                        if (dataDb1.get_cmntvisible().equalsIgnoreCase("1") && !userDataDB.get_userid().equalsIgnoreCase(json.getString("userid")) && statusid.trim().equalsIgnoreCase(json.getString("statusid"))) {
                            emptydata. setVisibility(View.GONE);
                            CommentFeed item = new CommentFeed();
                            item.setsn(json.getString("commentid"));
                            item.setuserid(json.getString("userid"));
                            item.set_name(json.getString("name"));
                            txtcomments1 = Base64.encodeToString(json.getString("comment").getBytes(StandardCharsets.UTF_8), 0);
                            item.setcomments(txtcomments1);
                            item.set_dppic(Static_Variable.entypoint1+"userphotosmall/"+json.getString("userid")+".jpg");
                            item.setreplay("0");
                            try {
                                Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                c1.setTime(sdf.parse(json.getString("postdate")));
                                item.setpostdate(getFormattedDate(c1.getTimeInMillis()));
                            } catch (Exception e) {
                                item.setpostdate(json.getString("postdate"));
                            }
                            item.set_imgsig(json.getString("statusimgsig"));
                            feed.add(item);
                            apater.notifyDataSetChanged();
                            list.setSelection(apater.getCount() - 1);
                            msg = json.getString("comment");
                            try {
                                mp = MediaPlayer.create(getApplicationContext(), R.raw.chintha_send);
                                mp.start();
                            } catch (Exception e2) {
                            }
                        }
                    }
                }
            } catch (Exception e3) {
            }
        }
    };
    public void postcomment() {
        String txtname = name.getText().toString();
        addcomment.setEnabled(false);
        comments.setEnabled(false);
        emptydata. setVisibility(View.GONE);
        nonet. setVisibility(View.GONE);
        try {
            addcomment.startAnimation(rotate);
        } catch (Exception e) {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerUpdate(50000, pd);
        }
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"addcomments1.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", userDataDB.get_userid()+"%:"+statusid.trim()+"%:"+txtcomments+"%:"+userDataDB.get_username()+"%:"+statususerid+"%:"+txtname+"%:"+userDataDB.get_userid()+"%:"+statustype+"%:"+photourl+"%:"+photodim).build()).build()).enqueue(new Callback() {
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
                            addcomment.setEnabled(true);
                            comments.setEnabled(true);
                            comments.setText("");
                            addcomment.clearAnimation();
                            if (result.contains("::ok")) {
                                emptydata. setVisibility(View.GONE);
                                String[] k = result.split("::");
                                CommentFeed item = new CommentFeed();
                                item.setsn(k[0]);
                                item.setuserid(userDataDB.get_userid());
                                item.set_name(userDataDB.get_username());
                                item.setcomments(txtcomments);
                                item.set_dppic(Static_Variable.entypoint1+"userphotosmall/"+userDataDB.get_userid()+".jpg");
                                item.setreplay("0");
                                try {
                                    Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                    c1.setTime(sdf.parse(k[1]));
                                    item.setpostdate(getFormattedDate(c1.getTimeInMillis()));
                                } catch (Exception e) {
                                    item.setpostdate(k[1]);
                                }
                                item.set_imgsig(statusimgsig);
                                feed.add(item);
                                apater.notifyDataSetChanged();
                                list.setSelection(apater.getCount() - 1);
                                try {
                                    mp = MediaPlayer.create(getApplicationContext(), R.raw.chintha_send);
                                    mp.start();
                                } catch (Exception e2) {
                                }
                            } else if (result.contains("::blockuser:%")) {
                                String[] p = result.split(":%");
                                Context applicationContext = getApplicationContext();
                                Toasty.info(applicationContext, "ക്ഷമിക്കണം !! താങ്കളെ "+p[1]+" ബ്ലോക്ക് ചെയ്തത് കാരണം ഈ പോസ്റ്റിന് കമന്റ് ചെയ്യുവാന്‍ സാധിക്കില്ല", Toast.LENGTH_LONG).show();
                            } else if (result.contains("::commentblocked:%")) {
                                Toasty.info(getApplicationContext(), (CharSequence) "ക്ഷമിക്കണം !! ഈ കമന്റ് ബോക്‌സ് പൂട്ടിയിരിക്കുന്നു ", Toast.LENGTH_LONG).show();
                            } else if (result.contains("::block::")) {
                                Toasty.info(getApplicationContext(), (CharSequence) "Sorry ! You are blocked by Admin", Toast.LENGTH_SHORT).show();
                            } else {
                                Toasty.info(getApplicationContext(), "കമന്റ് കുറച്ച് കൂടിപ്പോയി", Toast.LENGTH_SHORT).show();
                                //Toasty.info(getApplicationContext(), (CharSequence) result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void onBackPressed() {
        if(isTaskRoot())
        {
            Intent intent = new Intent(getApplicationContext(), HeartOf_App.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        }
        else
        {
            super.onBackPressed();
        }
    }


    public void timerUpdate(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    private boolean listIsAtTop() {
        boolean z = true;
        if (list.getChildCount() == 0) {
            return true;
        }
        if (list.getChildAt(0).getTop() != 0) {
            z = false;
        }
        return z;
    }

    public void removeitem(int position) {
        feed.remove(position);
        apater.notifyDataSetChanged();
    }


    public void status_edit() {
        final Dialog dialog2 = new Dialog(this);
        dialog2.setContentView(R.layout.dialog_editstatus);
        dialog2.setTitle("Edit Comment");
        dialog2.setCancelable(false);
        final EditText status2 = (EditText) dialog2.findViewById(R.id.chintha);
        Button update = (Button) dialog2.findViewById(R.id.rpt_update);
        Button close = (Button) dialog2.findViewById(R.id.close);
        status2.setText(txtcomments);
        TextView alert = (TextView) dialog2.findViewById(R.id.alert);
        alert.setTypeface(face);
        alert.setText(Static_Variable.alert_cmnt);
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                int fount=0;
                for(int i=0;i<feed.size();i++)
                {
                    CommentFeed item = feed.get(i);
                    try
                    {
                        byte[] data=Base64.decode(item.getcomments(),Base64.DEFAULT);
                        String text1=new String(data, StandardCharsets.UTF_8);
                        if(text1.equalsIgnoreCase(status.getText().toString()))
                        {
                            fount=1;
                            break;
                        }
                    }
                    catch(Exception a)
                    {

                    }
                }
                if (status2.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(getApplicationContext(), (CharSequence) "Please enter a commant", Toast.LENGTH_SHORT).show();
                } else if (status2.getText().toString().length() < 2) {
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.err_cmnt, Toast.LENGTH_SHORT).show();
                } else if (cd.isConnectingToInternet()) {
                    try {
                        txtcomments = Base64.encodeToString(status2.getText().toString().getBytes(StandardCharsets.UTF_8), 0);
                    } catch (Exception e2) {
                    }
                    edit_status();
                    dialog2.dismiss();
                } else {
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
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

    public void edit_status() {
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();
        timerUpdate(50000, pd);
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"editcommants.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", Static_Variable.id_comment+"%:"+txtcomments).build()).build()).enqueue(new Callback() {
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
                                    ArrayList<String> id1 = dataDb1.getcmntdetails();
                                    String[] c = (String[]) id1.toArray(new String[id1.size()]);
                                    statusid = c[0].trim();
                                    refresh();
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

    public void refresh() {
        if (cd.isConnectingToInternet()) {
            nonet. setVisibility(View.GONE);
            limit = 0;
            comment_load();
            return;
        }
        nonet.setVisibility(View.VISIBLE);
        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
    }

    public void onResume() {
        try {
            LocalBroadcastManager.getInstance(this).registerReceiver(mHandleMessageReceiver, new IntentFilter("com.statusappkal.Message"));
            ImageView img = (ImageView) headerview.findViewById(R.id.img);
            name = (TextView) headerview.findViewById(R.id.name);
            status = (EmojiTextView) headerview.findViewById(R.id.chintha);
            photostatus = (ImageView) headerview.findViewById(R.id.photostatus);
            ArrayList<String> id1 = dataDb1.getcmntdetails();
            String[] c = (String[]) id1.toArray(new String[id1.size()]);
            statusid = c[0].trim();
            Static_Variable.chintha_Id = c[0].trim();
            statususerid = c[1];
            name.setText(c[2]);
            statusimgsig = c[4];
            statustype = c[5];
            photourl = c[6];
            photodim = c[7];
            if (c[5].equalsIgnoreCase("0")) {
                status.setVisibility(View.VISIBLE);
                photostatus. setVisibility(View.GONE);
                status.setText(c[3]);
            } else if (c[5].equalsIgnoreCase("1")) {
                try {
                    status.setVisibility(View.VISIBLE);
                    status.setText(c[3]);
                    photostatus.setVisibility(View.VISIBLE);
                    String[] k = c[7].split("x");
                    float calheight = (Float.valueOf(k[1]).floatValue() / Float.valueOf(k[0]).floatValue()) * (Float.valueOf(dataDb2.get_screenwidth()).floatValue() - 20.0f);
                    photostatus.getLayoutParams().height = Math.round(calheight);
                    Glide.with(this).load(c[6]).transition(DrawableTransitionOptions.withCrossFade()).into(photostatus);
                } catch (Exception e) {
                }
            } else {
                status. setVisibility(View.GONE);
                photostatus. setVisibility(View.GONE);
            }

            status.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(status.getMaxLines()==Integer.MAX_VALUE)
                    {
                        status.setMaxLines(8);
                    }
                    else
                    {
                        status.setMaxLines(Integer.MAX_VALUE);
                    }

                }
            });

            RequestOptions rep = new RequestOptions().signature(new ObjectKey(statusimgsig));
            Glide.with(this).load(Static_Variable.entypoint1+"userphotosmall/"+c[1]+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(img);
            dataDb1.deletecmntvisible();
            dataDb1.add_cmntvisible("1");
            if (userDataDB.get_userid().equalsIgnoreCase(statususerid)) {
                blk_comments.setVisibility(View.VISIBLE);
            } else {
                blk_comments.setVisibility(View.INVISIBLE);
            }
            if (cd.isConnectingToInternet()) {
                nonet. setVisibility(View.GONE);
                limit = 0;
                comment_load();
            } else {
                nonet.setVisibility(View.VISIBLE);
                Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
            }
            cmntcount = 0;
            replaycount = 0;
            count = 0;
            try {
                adView1.loadAd(adreq1);
            } catch (Exception e2) {
            }
        } catch (Exception e3) {
        }
        super.onResume();
    }

    public void comment_load() {
        bottom_lyt.setVisibility(View.INVISIBLE);
        list.setVisibility(View.INVISIBLE);
        addcomment.setVisibility(View.INVISIBLE);
        nonet. setVisibility(View.GONE);
        emptydata. setVisibility(View.GONE);
        load_cion.setVisibility(View.VISIBLE);
        limit = 0;
        feed.clear();
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"getcomments.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", limit+"%:"+statusid).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            addcomment.setVisibility(View.VISIBLE);
                            if (result.contains("%:ok")) {
                                String[] got = result.split("%:");
                                int k = (got.length - 1) / 7;
                                int m = -1;
                                Collections.reverse(feed);
                                for (int i = 1; i <= k; i++) {
                                    CommentFeed item = new CommentFeed();
                                    m=m+1;
                                    item.setsn(got[m]);
                                    m=m+1;
                                    item.setuserid(got[m]);
                                    item.set_dppic(Static_Variable.entypoint1+"userphotosmall/"+got[m]+".jpg");
                                    m=m+1;
                                    item.set_name(got[m]);
                                    m=m+1;
                                    item.setcomments(got[m]);
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
                                    item.setreplay(got[m]);
                                    m=m+1;
                                    item.set_imgsig(got[m]);
                                    feed.add(item);
                                }
                                Collections.reverse(feed);
                                list.setVisibility(View.VISIBLE);
                                bottom_lyt.setVisibility(View.VISIBLE);
                                load_cion. setVisibility(View.GONE);
                                apater.notifyDataSetChanged();
                                list.setSelection(apater.getCount() - 1);
                                return;
                            }
                            bottom_lyt.setVisibility(View.VISIBLE);
                            list.setVisibility(View.VISIBLE);
                            layoutloadmore. setVisibility(View.GONE);
                            emptydata.setVisibility(View.VISIBLE);
                            load_cion. setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHandleMessageReceiver);
        dataDb1.deletecmntvisible();
        dataDb1.add_cmntvisible("0");
    }

    public void commentload1() {
        pb1.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"getcomments.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", limit+"%:"+statusid).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (result.contains("%:ok")) {
                                String[] got = result.split("%:");
                                int k = (got.length - 1) / 7;
                                int m = -1;
                                Collections.reverse(feed);
                                for (int i = 1; i <= k; i++) {
                                    CommentFeed item = new CommentFeed();
                                    m=m+1;
                                    item.setsn(got[m]);
                                    m=m+1;
                                    item.setuserid(got[m]);
                                    item.set_dppic(Static_Variable.entypoint1+"userphotosmall/"+got[m]+".jpg");
                                    m=m+1;
                                    item.set_name(got[m]);
                                    m=m+1;
                                    item.setcomments(got[m]);
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
                                    item.setreplay(got[m]);
                                    m=m+1;
                                    item.set_imgsig(got[m]);
                                    feed.add(item);
                                }
                                Collections.reverse(feed);
                                apater.notifyDataSetChanged();
                                list.setSelection(0);
                                pb1. setVisibility(View.GONE);
                                return;
                            }
                            pb1. setVisibility(View.GONE);
                            layoutloadmore. setVisibility(View.GONE);
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

    public void settings_comments()
    {
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.commentnoti_disabling);
        final CheckBox check= dialog.findViewById(R.id.check);
        final CheckBox commentblock=dialog.findViewById(R.id.commentblock);

        check.setText(Static_Variable.no_comnetnoti);
        check.setTypeface(face);



        if(dataDb2.get_notidisabled(statusid).equalsIgnoreCase(""))
        {
            check.setChecked(false);
        }
        else
        {
            check.setChecked(true);
        }

        check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(check.isChecked())
                {
                    dataDb2.del_notidisabled(statusid);
                    dataDb2.add_disablenoti(statusid);

                    Toasty.info(getApplicationContext(), Static_Variable.reject_cmnt,Toast.LENGTH_SHORT).show();
                }
                else
                {

                    dataDb2.del_notidisabled(statusid);
                    Toasty.info(getApplicationContext(), Static_Variable.acpect_cmnts,Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.show();

    }
    public void showalert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new comments_delete().execute(new String[0]);
                } else {
                    Toasty.info(getApplicationContext(), (CharSequence) "Please make sure your internet connection is active", Toast.LENGTH_SHORT).show();
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

    public void showalert_commentblok() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("ഈ സ്റ്റാറ്റസിന്റെ കമന്റ് ബോക്‌സ് പൂട്ടാം അല്ലെ ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    cmnts_block();
                } else {
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        try {
            ((TextView) alert.findViewById(android.R.id.message)).setTypeface(face);
        } catch (Exception e) {
        }
    }

    public void cmnts_block() {
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();
        timerUpdate(50000, pd);
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"updatecommentblock.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", statusid).build()).build()).enqueue(new Callback() {
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
                                    Toasty.info(getApplicationContext(), (CharSequence) "കമന്റ് ബോക്‌സ് പൂട്ടിയിരിക്കുന്നു ", Toast.LENGTH_SHORT).show();
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
    public class comments_delete extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerUpdate(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"deleteallcomments.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(statusid, "UTF-8");
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
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(getApplicationContext(), (CharSequence) "Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(getApplicationContext(), (CharSequence) "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public class fvrts1 extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerUpdate(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"publiccomment.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(statusid, "UTF-8");
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
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(getApplicationContext(), (CharSequence) "Fvrt", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(getApplicationContext(), (CharSequence) "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
