package com.suhi_chintha;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Media;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;
import com.google.android.material.tabs.TabLayout.ViewPagerOnTabSelectedListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pkmmte.view.CircularImageView;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.mateware.snacky.Snacky;
import es.dmoral.toasty.Toasty;
import io.github.lizhangqu.coreprogress.ProgressHelper;
import io.github.lizhangqu.coreprogress.ProgressUIListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HeartOf_App extends AppCompatActivity implements OnNavigationItemSelectedListener {
    private static String Save_Media = "/Status_WhatStatus/";
    public static Uri uri;

    ImageView aboutus;
    ViewPagerAdapter adapter;
    Button addvideo;
    ImageView adminclose;
    ImageView adminpic;
    TextView admintitle;
    AdRequest adreq;
    ImageView ads;
    RelativeLayout alphalayout;
    Bitmap bitmap;
    NetConnection cd;
    Image_Compressing cmp;
    RelativeLayout content;
    public DataDb dataDb = new DataDb(this);
    public DataDB1 dataDb1 = new DataDB1(this);
    public DataDB2 dataDb2 = new DataDB2(this);
    public DataDB4 dataDb4 = new DataDB4(this);
    public TextView detelete_txt;
    RelativeLayout develpr;
    TextView devmsg;
    public Dialog dialog;
    ScrollView dvprmsg;
    RelativeLayout extrasettings;
    Typeface face;
    Typeface face1;
    Typeface face2;
    Typeface face3;
    RelativeLayout fifth;
    RelativeLayout first;
    public RelativeLayout footer;
    RelativeLayout fourth;
    Button stop;
    int intcount = 0;
    public InterstitialAd intestrial;
    public String mediapath = "";
    public String mediatype = "0";
    RelativeLayout navigationbar;
    public TextView noticount;
    ImageView notifications;
    ImageView pauseicon;
    public ProgressBar pb1;
    public ProgressDialog pd;
    public TextView persentage;
    ImageView playicon;
    ImageView popup;
    ImageView previewimage;
    VideoView previewvideo;
    RotateAnimation rotate;
    RelativeLayout second;
    RelativeLayout settingslayout;
    RelativeLayout sixth;
    public EditText status;

    public TabLayout tabLayout;
    TextView text;
    RelativeLayout third;
    TextView tv;
    public TextView txtaddtofvrt;
    public TextView txtcopy;
    public TextView txtedit;
    TextView txtlaoding;
    public TextView txtreportstatus;
    public TextView txtstatustoimage;
    public String uploadtitle = "";
    CircularImageView user;
    public User_DataDB userDataDB = new User_DataDB(this);
    private ViewPager viewPager;

    Call call;
    boolean requestgoing=true;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.homepage_actvty);
        MobileAds.initialize((Context) this, "ca-app-pub-2432830627480060~5524010724");
        FirebaseApp.initializeApp(this);
        EmojiManager.install(new IosEmojiProvider());

        try {
            rotate= new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(5000);
            rotate.setRepeatCount(Animation.INFINITE);
            rotate.setInterpolator(new LinearInterpolator());
            if (VERSION.SDK_INT > 9) {
                StrictMode.setThreadPolicy(new Builder().permitAll().build());
            }
        } catch (Exception e) {
        }
        try {
            intestrial = new InterstitialAd(this);
            intestrial.setAdUnitId("ca-app-pub-2432830627480060/8613208496");
            adreq = new AdRequest.Builder().build();
            navigationbar = (RelativeLayout) findViewById(R.id.navigationbar);
            cmp = new Image_Compressing();
            cd = new NetConnection(this);
            face = Typeface.createFromAsset(getAssets(), "asset_fonts/common.ttf");
            face1 = Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
            face2 = Typeface.createFromAsset(getAssets(), "asset_fonts/common_heading.otf");
            face3 = Typeface.createFromAsset(getAssets(), "asset_fonts/proxibold.otf");
            pd = new ProgressDialog(this);
            viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewpage_setup(viewPager);
            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
            change_tab();
            txtstatustoimage = (TextView) findViewById(R.id.txtstatustoimage);
            sixth = (RelativeLayout) findViewById(R.id.sixth);
            dvprmsg = (ScrollView) findViewById(R.id.developermsg);
            develpr = (RelativeLayout) findViewById(R.id.develpr);
            alphalayout = (RelativeLayout) findViewById(R.id.alphalayout);
            extrasettings = (RelativeLayout) findViewById(R.id.extrasettings);
            noticount = (TextView) findViewById(R.id.countofnoti);
            notifications = (ImageView) findViewById(R.id.noties);
            aboutus = (ImageView) findViewById(R.id.whoabout);
            content = (RelativeLayout) findViewById(R.id.content);
            footer = (RelativeLayout) findViewById(R.id.bottom_lyt);
            devmsg = (TextView) findViewById(R.id.devmsg);
            admintitle = (TextView) findViewById(R.id.admintitle);
            adminclose = (ImageView) findViewById(R.id.close_admin);
            ads = (ImageView) findViewById(R.id.advts);
            adminpic = (ImageView) findViewById(R.id.adminpic);
            tv = (TextView) findViewById(R.id.tv);
            user = (CircularImageView) findViewById(R.id.imgsuser);
            text = (TextView) findViewById(R.id.text);
            first = (RelativeLayout) findViewById(R.id.lyt_first);
            second = (RelativeLayout) findViewById(R.id.lyt_second);
            third = (RelativeLayout) findViewById(R.id.lyt_third);
            fourth = (RelativeLayout) findViewById(R.id.lyt_fourth);
            fifth = (RelativeLayout) findViewById(R.id.fifth);
            txtedit = (TextView) findViewById(R.id.txtedit);
            detelete_txt = (TextView) findViewById(R.id.txtdelete);
            txtcopy = (TextView) findViewById(R.id.txtcopy);
            txtaddtofvrt = (TextView) findViewById(R.id.txtaddtofvrt);
            txtreportstatus = (TextView) findViewById(R.id.txtreportstatus);
            popup = (ImageView) findViewById(R.id.options);
            settingslayout = (RelativeLayout) findViewById(R.id.settingslayout);
            txtedit.setText("എഡിറ്റ് ");
            detelete_txt.setText("ഡിലീറ്റ് ");
            txtcopy.setText("കോപ്പി ");
            txtaddtofvrt.setText("പ്രിയപ്പെട്ട ആളാക്കാം ");
            txtreportstatus.setText("റിപ്പോര്‍ട്ട്‌ ");
            txtstatustoimage.setText("ചിത്രമുണ്ടാക്കാം ");
            txtedit.setTypeface(face3);
            detelete_txt.setTypeface(face3);
            txtcopy.setTypeface(face3);
            txtaddtofvrt.setTypeface(face3);
            txtreportstatus.setTypeface(face3);
            txtstatustoimage.setTypeface(face3);
            ArrayList<String> id1 = userDataDB.get_user();
            String[] c = (String[]) id1.toArray(new String[id1.size()]);
            if (c.length <= 0) {
                startActivity(new Intent(getApplicationContext(), Primary_Registration.class));
                finish();
                return;
            }
            if (dataDb2.get_screenwidth().equalsIgnoreCase("")) {
                int width = getResources().getDisplayMetrics().widthPixels;

                dataDb2.add_screenwidth(width+"");
            }
            if (dataDb4.get_lockcomments().equalsIgnoreCase("")) {
                dataDb4.add_lockcomments("0");
            }
            text.setText(c[1]);
            text.setSelected(true);
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

            File folder=new File(Environment.getExternalStorageDirectory()+"/"+ Static_Variable.foldername);
            if(!folder.exists())
            {
                folder.mkdir();
                File f1 = new File(Environment.getExternalStorageDirectory() + "/"+ Static_Variable.foldername+"/" + ".nomedia");
                try {
                    f1.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            File folder2=new File(Environment.getExternalStorageDirectory()+"/"+ Static_Variable.foldername+"/bg");
            if(!folder2.exists())
            {
                folder2.mkdir();
                File f2 = new File(Environment.getExternalStorageDirectory() + "/"+ Static_Variable.foldername+"/bg/" + ".nomedia");
                try {
                    f2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            File folder3 = new File(Environment.getExternalStorageDirectory() + "/"+ Static_Variable.folder_img);
            if(!folder3.exists())
            {
                folder3.mkdir();
            }
            File f4 = new File(Environment.getExternalStorageDirectory() + "/" + Static_Variable.foldername + "/userphoto1.jpg");
            if (f4.exists()) {

                user.setVisibility(View.VISIBLE);
                tv.setVisibility(View.INVISIBLE);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bmp = BitmapFactory.decodeFile(f4.getAbsolutePath(),options);
                user.setImageBitmap(bmp);
            } else {
                user.setVisibility(View.INVISIBLE);
                tv.setVisibility(View.VISIBLE);

                try
                {
                    char chars = c[1].toUpperCase().charAt(0);
                    tv.setText(chars + "");
                }
                catch(Exception a)
                {

                }

            }
            NavigationView navigationView = (NavigationView) findViewById(R.id.navigview);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setItemIconTintList(null);
            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.txthelp)).setTypeface(face2);
            popup.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        ((DrawerLayout) findViewById(R.id.drawer_layout)).openDrawer((int) GravityCompat.END);
                    } catch (Exception e) {
                    }
                }
            });
            if (dataDb1.get_notistatus().equalsIgnoreCase("")) {
                dataDb1.drop_statusnoti();
                dataDb1.add_notistatus("1");
            }
            if (dataDb1.get_likenoti().equalsIgnoreCase("")) {
                dataDb1.drop_notilike();
                dataDb1.add_notilike("1");
            }
            if (dataDb1.get_cmntnoti().equalsIgnoreCase("")) {
                dataDb1.drop_cmntnoti();
                dataDb1.add_cmtnnoti("1");
            }
            try {
                if (dataDb1.get_cmntvisible().equalsIgnoreCase("")) {
                    dataDb1.deletecmntvisible();
                    dataDb1.add_cmntvisible("0");
                }
            } catch (Exception e6) {
            }
            tabLayout.addOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager) {
                public void onTabSelected(Tab tab) {
                    super.onTabSelected(tab);
                    int position = tabLayout.getSelectedTabPosition();
                    Fragment fragment = adapter.getItem(tab.getPosition());
                    if (fragment != null) {
                        switch (position) {
                            case 0:
                                if (Integer.parseInt(dataDb1.get_noticount()) <= 0) {
                                    noticount. setVisibility(View.GONE);
                                } else {
                                    noticount.setVisibility(View.VISIBLE);
                                    noticount.setText(dataDb1.get_noticount()+"");
                                }
                                ((status_frag) fragment).refersh();
                                return;
                            case 1:
                                if (Integer.parseInt(dataDb1.get_noticount()) <= 0) {
                                    noticount. setVisibility(View.GONE);
                                } else {
                                    noticount.setVisibility(View.VISIBLE);
                                    noticount.setText(dataDb1.get_noticount()+"");
                                }
                                notifications.setVisibility(View.VISIBLE);
                                aboutus.setVisibility(View.VISIBLE);
                                ((video_frag) fragment).refresh();
                                return;
                            case 2:
                                if (Integer.parseInt(dataDb1.get_noticount()) <= 0) {
                                    noticount. setVisibility(View.GONE);
                                } else {
                                    noticount.setVisibility(View.VISIBLE);
                                    noticount.setText(dataDb1.get_noticount()+"");
                                }
                                ((wpstatus) fragment).refresh();
                                return;
                            default:
                                return;
                        }
                    }
                }

                public void onTabUnselected(Tab tab) {
                    super.onTabUnselected(tab);
                }
            });
            alphalayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    settingslayout. setVisibility(View.GONE);
                }
            });
            extrasettings.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                }
            });
            txtcopy.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    settingslayout. setVisibility(View.GONE);
                    if (VERSION.SDK_INT < 11) {
                        ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setText(Static_Variable.chintha_text);
                    } else {
                        ((android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("salmansuhailamp", Static_Variable.chintha_text));
                    }
                    Toasty.info(getApplicationContext(), (CharSequence) "Copied", Toast.LENGTH_SHORT).show();
                }
            });
            third.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    settingslayout. setVisibility(View.GONE);
                    if (VERSION.SDK_INT < 11) {
                        ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setText(Static_Variable.chintha_text);
                    } else {
                        ((android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("salmansuhailamp", Static_Variable.chintha_text));
                    }
                    Toasty.info(getApplicationContext(), (CharSequence) "Copied", Toast.LENGTH_SHORT).show();
                }
            });
            txtaddtofvrt.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    settingslayout. setVisibility(View.GONE);
                    if (dataDb.getfvr_usr(Static_Variable.userid).equalsIgnoreCase("")) {
                        dataDb.add_fvrtuser(Static_Variable.userid, Static_Variable.username);
                        Toasty.info(getApplicationContext(), (CharSequence) "Added to favourite list", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toasty.info(getApplicationContext(), (CharSequence) "Sorry ! Already added to favourite list", Toast.LENGTH_SHORT).show();
                }
            });
            txtedit.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    try {
                        settingslayout. setVisibility(View.GONE);
                        ((status_frag) adapter.getItem(0)).editstatus();
                    } catch (Exception e) {
                        Log.w("Errror",Log.getStackTraceString(e));
                    }
                }
            });
            first.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    try {
                        settingslayout. setVisibility(View.GONE);
                        ((status_frag) adapter.getItem(0)).editstatus();
                    } catch (Exception e) {
                        Log.w("Errror",Log.getStackTraceString(e));
                    }
                }
            });
            detelete_txt.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    try {
                        settingslayout. setVisibility(View.GONE);
                        ((status_frag) adapter.getItem(0)).delete_alert("Are you sure want to drop this status");
                    } catch (Exception e) {
                    }
                }
            });
            second.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    try {
                        settingslayout. setVisibility(View.GONE);
                        ((status_frag) adapter.getItem(0)).delete_alert("Are you sure want to drop this status");
                    } catch (Exception e) {
                    }
                }
            });
            fourth.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    settingslayout. setVisibility(View.GONE);
                    if (dataDb.getfvr_usr(Static_Variable.userid).equalsIgnoreCase("")) {
                        dataDb.add_fvrtuser(Static_Variable.userid, Static_Variable.username);
                        Toasty.info(getApplicationContext(), (CharSequence) "Added to favourite list", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toasty.info(getApplicationContext(), (CharSequence) "Sorry ! Already added to favourite list", Toast.LENGTH_SHORT).show();
                }
            });
            txtreportstatus.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    try {
                        settingslayout. setVisibility(View.GONE);
                        ((status_frag) adapter.getItem(0)).status_report(HeartOf_App.this);
                    } catch (Exception e) {
                    }
                }
            });
            fifth.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    try {
                        settingslayout. setVisibility(View.GONE);
                        ((status_frag) adapter.getItem(0)).status_report(HeartOf_App.this);
                    } catch (Exception e) {
                    }
                }
            });
            sixth.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Status_To_Image.class));
                }
            });
            aboutus.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    Static_Variable.viewd_pfle = 1;
                    startActivity(new Intent(getApplicationContext(), Developer_Details.class));
                }
            });
            notifications.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    Static_Variable.viewd_pfle = 1;
                    startActivity(new Intent(getApplicationContext(), List_noti.class));
                }
            });
            noticount.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    Static_Variable.viewd_pfle = 1;
                    startActivity(new Intent(getApplicationContext(), List_noti.class));
                }
            });
            user.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    startActivity(new Intent(getApplicationContext(), My_info.class));
                }
            });
            tv.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    startActivity(new Intent(getApplicationContext(), My_info.class));
                }
            });
            adminclose.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    dvprmsg. setVisibility(View.GONE);
                    dataDb1.drop_message();
                }
            });
            ads.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    try {
                        ArrayList<String> id3 = dataDb1.get_message();
                        String[] k1 = ((String[]) id3.toArray(new String[id3.size()]))[0].split("::")[1].split("#");
                        if (k1[0].equalsIgnoreCase(NotificationCompat.CATEGORY_CALL)) {
                            call(k1[1]);
                        } else if (k1[0].equalsIgnoreCase("web")) {
                            web(k1[1]);
                        } else if (k1[0].equalsIgnoreCase("download")) {
                            download(k1[1]);
                        }
                    } catch (Exception e) {
                    }
                }
            });
            ArrayList<String> id3 = dataDb1.get_message();
            String[] c2 = (String[]) id3.toArray(new String[id3.size()]);
            if (c2.length > 0) {
                try {
                    dvprmsg.setVisibility(View.VISIBLE);
                    if (c2[1].equalsIgnoreCase("0")) {
                        adminpic.setVisibility(View.VISIBLE);
                        admintitle.setVisibility(View.VISIBLE);
                        devmsg.setVisibility(View.VISIBLE);
                        ads. setVisibility(View.GONE);
                        String[] k = c2[0].split("::");
                        admintitle.setText(k[0]);
                        devmsg.setText(k[1]);
                    } else if (c2[1].equalsIgnoreCase("1")) {
                        adminpic. setVisibility(View.GONE);
                        admintitle. setVisibility(View.GONE);
                        devmsg. setVisibility(View.GONE);
                        ads.setVisibility(View.VISIBLE);
                        if (cd.isConnectingToInternet()) {
                            String[] k2 = c2[0].split("::");
                            new imageLoading1().execute(new String[]{k2[0]});
                        }
                    }
                } catch (Exception e7) {
                }
            } else if (dvprmsg.getVisibility() != View.VISIBLE) {
                dvprmsg. setVisibility(View.GONE);
            }
        } catch (Exception e8) {
        }
    }

    public void show_popup() {
        settingslayout.setVisibility(View.VISIBLE);
        ArrayList<String> id1 = userDataDB.get_user();
        if (!Static_Variable.usermobile.equalsIgnoreCase(((String[]) id1.toArray(new String[id1.size()]))[2])) {
            first. setVisibility(View.GONE);
            second. setVisibility(View.GONE);
        } else if (Static_Variable.txtstatustype.equalsIgnoreCase("1")) {
            first. setVisibility(View.GONE);
            second.setVisibility(View.VISIBLE);
        } else {
            first.setVisibility(View.VISIBLE);
            second.setVisibility(View.VISIBLE);
        }
    }

    private void viewpage_setup(ViewPager viewPager2) {
        try {
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new status_frag(), "ചിന്ത ");
            adapter.addFragment(new video_frag(), "ഫീഡ്‌ ");
            adapter.addFragment(new wpstatus(), "സ്റ്റാറ്റസ്‌ ");
            viewPager2.setAdapter(adapter);
        } catch (Exception e) {
        }
    }

    public void change_tab() {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(face);
                }
            }
        }
    }

    public void onResume() {
        super.onResume();
        try {
            if (dataDb1.get_privacynoti().equalsIgnoreCase("")) {
                notice_legal();
            }
            ArrayList<String> id2 = userDataDB.get_user();
            String[] c1 = id2.toArray(new String[id2.size()]);
            if (c1.length > 0) {

                dataDb.del_visible();
                dataDb.add_visible("1");
                if (Static_Variable.picchanged == 1) {
                    File f4 = new File(Environment.getExternalStorageDirectory() + "/" + Static_Variable.foldername + "/userphoto1.jpg");
                    if (f4.exists()) {

                        user.setVisibility(View.VISIBLE);
                        tv.setVisibility(View.INVISIBLE);
                        Bitmap bmp = BitmapFactory.decodeFile(f4.getAbsolutePath());
                        user.setImageBitmap(bmp);
                    } else {
                        user.setVisibility(View.INVISIBLE);
                        tv.setVisibility(View.VISIBLE);

                        char chars = c1[1].toUpperCase().charAt(0);
                        tv.setText(chars + "");
                    }

                    Static_Variable.picchanged = 0;

                }
                if (Integer.parseInt(dataDb1.get_noticount()) <= 0) {
                    noticount.setVisibility(View.GONE);
                } else {

                    noticount.setVisibility(View.VISIBLE);
                    noticount.setText(dataDb1.get_noticount() + "");
                }

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


            intcount=0;

            try
            {

                intestrial.loadAd(adreq);
            }
            catch (Exception a)
            {

            }


        } catch (Exception a) {

        }
    }

    public void notice_legal() {
        final Dialog dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(1);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.legal_notice);
        TextView text2 = (TextView) dialog2.findViewById(R.id.text);
        Button agree = (Button) dialog2.findViewById(R.id.agree);
        text2.setTypeface(face);
        text2.setText(Static_Variable.privacy_noti);
        agree.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                dataDb1.add_privacynoti("1");
                dialog2.dismiss();
            }
        });
        dialog2.show();
    }

    public void notiserrings() {
        Dialog dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(1);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.setCancelable(true);
        dialog2.setContentView(R.layout.not_settings);
        TextView text2 = (TextView) dialog2.findViewById(R.id.text);
        final CheckBox chkstatus = (CheckBox) dialog2.findViewById(R.id.chkstatus);
        final CheckBox chklikes = (CheckBox) dialog2.findViewById(R.id.chklikes);
        final CheckBox chkcomments = (CheckBox) dialog2.findViewById(R.id.chkcomments);
        text2.setTypeface(face);
        text2.setText(Static_Variable.noti_setttngs);
        if (dataDb1.get_notistatus().equalsIgnoreCase("1")) {
            chkstatus.setChecked(true);
        } else {
            chkstatus.setChecked(false);
        }
        if (dataDb1.get_likenoti().equalsIgnoreCase("1")) {
            chklikes.setChecked(true);
        } else {
            chklikes.setChecked(false);
        }
        if (dataDb1.get_cmntnoti().equalsIgnoreCase("1")) {
            chkcomments.setChecked(true);
        } else {
            chkcomments.setChecked(false);
        }
        chkstatus.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (chkstatus.isChecked()) {
                    dataDb1.drop_statusnoti();
                    dataDb1.add_notistatus("1");
                    return;
                }
                dataDb1.drop_statusnoti();
                dataDb1.add_notistatus("0");
            }
        });
        chklikes.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (chklikes.isChecked()) {
                    dataDb1.drop_notilike();
                    dataDb1.add_notilike("1");
                    return;
                }
                dataDb1.drop_notilike();
                dataDb1.add_notilike("0");
            }
        });
        chkcomments.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (chkcomments.isChecked()) {
                    dataDb1.drop_cmntnoti();
                    dataDb1.add_cmtnnoti("1");
                    return;
                }
                dataDb1.drop_cmntnoti();
                dataDb1.add_cmtnnoti("0");
            }
        });
        dialog2.show();
    }

    public void statusshare(String status) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Status");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, status + "\n " + Static_Variable.appshare_link);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen((int) GravityCompat.END)) {
            drawer.closeDrawer((int) GravityCompat.END);
        } else if (settingslayout.getVisibility() == View.VISIBLE) {
            settingslayout. setVisibility(View.GONE);
        } else if (tabLayout.getSelectedTabPosition() == 2) {
            viewPager.setCurrentItem(1);
        } else if (tabLayout.getSelectedTabPosition() == 1) {
            viewPager.setCurrentItem(0);
        } else {
            if (intestrial.isLoaded()) {
                intestrial.show();
            }
            super.onBackPressed();
        }
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void status_report() {
        if (cd.isConnectingToInternet()) {
            new report_status().execute(new String[0]);
        } else {
            Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
        }
    }

    public void call(String mob)
    {
        try
        {
            if ( ContextCompat.checkSelfPermission( this, Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions( this, new String[] {Manifest.permission.CALL_PHONE },1);
            }
            else
            {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+mob));
                startActivity(callIntent);
            }
        }
        catch(Exception a)
        {
        }
    }

    public void web(String web)
    {
        Intent pagIntent = new Intent(Intent.ACTION_VIEW);
        pagIntent.setData(Uri.parse(web));
        try
        {
            startActivity(pagIntent);
        }
        catch(Exception a)
        {

        }
    }
    public void download(String link)
    {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + link)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + link)));
        }
    }

    public void update_fcm_alert(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();


                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        try
        {
            TextView textView = alert.findViewById(android.R.id.message);
            textView.setTypeface(face);
        }
        catch(Exception a)
        {

        }
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
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            Static_Variable.viewd_pfle = 1;
            startActivity(new Intent(getApplicationContext(), Search_Result.class));
        } else if (id == R.id.myprofile) {
            Static_Variable.viewd_pfle = 1;
            startActivity(new Intent(getApplicationContext(), My_info.class));
        } else if (id == R.id.fvrtupdates) {
            Static_Variable.viewd_pfle = 1;
            startActivity(new Intent(getApplicationContext(), Chintha_Fvrtusers.class));
        } else if (id == R.id.blockedlist) {
            Static_Variable.viewd_pfle = 1;
            startActivity(new Intent(getApplicationContext(), Chintha_bloked.class));
        } else if (id == R.id.mystatus) {
            Static_Variable.viewd_pfle = 1;
            startActivity(new Intent(getApplicationContext(), My_Chinthakal.class));
        } else if (id == R.id.myfvrtlist) {
            Static_Variable.viewd_pfle = 1;
            try {
                startActivity(new Intent(getApplicationContext(), FvrtChinthakal_List.class));
            } catch (Exception e) {

            }
        } else if (id == R.id.myfvrtusers) {
            Static_Variable.viewd_pfle = 1;
            startActivity(new Intent(getApplicationContext(), Favourite_UsersList.class));
        } else if (id == R.id.notification) {
            notiserrings();
        } else if (id == R.id.backgroundimg) {
            Static_Variable.viewd_pfle = 1;
            startActivity(new Intent(getApplicationContext(), Background_Image.class));
        } else if (id == R.id.clearmmry) {
            Static_Variable.viewd_pfle = 1;
            new Thread(new Runnable() {
                public void run() {
                    Glide.get(HeartOf_App.this).clearDiskCache();
                }
            }).start();
            try {
                dataDb2.deletefvrt();
            } catch (Exception e2) {
            }
            Toasty.info(getApplicationContext(), (CharSequence) "Memory Cleaned", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.notificationissue) {
            if (cd.isConnectingToInternet()) {
                FirebaseMessaging.getInstance().subscribeToTopic("status_updating");
                pd.setMessage("Please wait...");
                pd.setCancelable(false);
                pd.show();
                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            pd.dismiss();
                            Toasty.info(getApplicationContext(), (CharSequence) "താല്‍ക്കാലിക പ്രശ്‌നം ! ദയവായി 10 മിനിട്ടിന് ശേഷം ശ്രമിക്കുക ", Toast.LENGTH_LONG).show();
                            return;
                        }
                        dataDb2.addfcmid(((InstanceIdResult) task.getResult()).getToken());
                    }
                });
                new update_fcm().execute(new String[0]);
            } else {
                Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.rpt_update) {
            updating_checing();
        } else if (id == R.id.whoabout) {
            Static_Variable.viewd_pfle = 1;
            startActivity(new Intent(getApplicationContext(), Developer_Details.class));
        } else if (id == R.id.shareapp) {
            Static_Variable.viewd_pfle = 1;
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = Static_Variable.app_share;
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, Static_Variable.title_share);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer((int) GravityCompat.END);
        return true;
    }

    public void onPause() {
        super.onPause();
        dataDb.del_visible();
        dataDb.add_visible("0");
        Static_Variable.viewd_pfle = 0;
    }

    public void updating_checing()
    {
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public void videosave(final File sourceFile)
    {
        try
        {


            if (!storagepermission(getApplicationContext(), PERMISSIONS)) {
                ActivityCompat.requestPermissions(HeartOf_App.this, PERMISSIONS, PERMISSION_ALL);
            }

            else
            {
                try {
                    file_copy(sourceFile, new File(Environment.getExternalStorageDirectory().toString() + Save_Media + sourceFile.getName()));
                    Snacky.builder().
                            setActivty(HeartOf_App.this).
                            setText("Saved to Gallery").
                            success().
                            show();
                } catch (Exception e) {

                    Snacky.builder().
                            setActivty(HeartOf_App.this).
                            setText("Unable to Save").
                            error().
                            show();
                }
            }

        }
        catch (Exception a)

        {

        }




    }

    public static boolean storagepermission(Context context, String... permissions) {
        if (!(context == null || permissions == null)) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void file_copy(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
            addtogallery(destFile);
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    public void addtogallery(File file) {
        if (file.getName().endsWith(".mp4")) {
            ContentValues values = new ContentValues();
            values.put("_data", file.getAbsolutePath());
            values.put("mime_type", "video/mp4");
            getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
        } else if (file.getName().endsWith(".gif")) {
            ContentValues values2 = new ContentValues();
            values2.put("_data", file.getAbsolutePath());
            values2.put("mime_type", "image/gif");
            getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, values2);
        } else if (file.getName().endsWith(".jpg")) {
            ContentValues values3 = new ContentValues();
            values3.put("_data", file.getAbsolutePath());
            values3.put("mime_type", "image/jpg");
            getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, values3);
        }
    }

    public void wpshare(File f) {
        try{


            Uri uri = FileProvider.getUriForFile(
                    getApplicationContext(),
                    this.getApplicationContext()
                            .getPackageName() + ".provider", f);

            Intent videoshare = new Intent(Intent.ACTION_SEND);
            videoshare.setType("*/*");
            videoshare.setPackage("com.whatsapp");
            videoshare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            videoshare.putExtra(Intent.EXTRA_STREAM,uri);
            startActivity(videoshare);
        }
        catch (Exception a)
        {

        }
    }
    public void uploadvideo() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.uploadvideo);
        LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.width = -1;
        wmlp.height = -1;
        addvideo = (Button) dialog.findViewById(R.id.addvideo);
        previewimage = (ImageView) dialog.findViewById(R.id.previewimage);
        previewvideo = (VideoView) dialog.findViewById(R.id.previewvideo);
        playicon = (ImageView) dialog.findViewById(R.id.playicon);
        pauseicon = (ImageView) dialog.findViewById(R.id.pauseicon);
        pb1 = (ProgressBar) dialog.findViewById(R.id.pb1);
        txtlaoding = (TextView) dialog.findViewById(R.id.txtlaoding);
        persentage = (TextView) dialog.findViewById(R.id.persentage);
        ImageView pickvideo = (ImageView) dialog.findViewById(R.id.pickvideo);
        final EditText title = (EditText) dialog.findViewById(R.id.title);
        ((ImageView) dialog.findViewById(R.id.pickimage)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivityForResult(new Intent("android.intent.action.PICK", Images.Media.EXTERNAL_CONTENT_URI), 100);
            }
        });
        pickvideo.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), 101);
            }
        });
        pauseicon.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                playicon.setVisibility(View.VISIBLE);
                pauseicon.setVisibility(View.INVISIBLE);
                previewvideo.pause();
            }
        });
        playicon.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                playicon.setVisibility(View.INVISIBLE);
                pauseicon.setVisibility(View.VISIBLE);
                previewvideo.start();
            }
        });
        addvideo.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Integer.parseInt(String.valueOf(new File(mediapath).length() / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED)) > 10) {
                    showalert("ക്ഷമിക്കണം പരമാവധി 10 MB യുടെ ചിത്രം/വീഡിയോ മാത്രമേ താങ്കള്‍ക്ക് അപ്‌ലോഡ് ചെയ്യുവാന്‍ സാധിക്കുകയൊള്ളൂ");
                } else if (addvideo.getText().toString().equalsIgnoreCase("Upload")) {
                    uploadtitle = title.getText().toString();
                    addvideo.setText("Stop");
                    uploadingprogress();
                }
            }
        });
        dialog.show();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == -1) {
            mediapath = getRealPathFromURI_Image(data.getData());
            mediatype = "1";
            previewimage.setVisibility(View.VISIBLE);
            previewvideo. setVisibility(View.GONE);
            playicon.setVisibility(View.INVISIBLE);
            pauseicon.setVisibility(View.INVISIBLE);
            previewimage.setImageBitmap(BitmapFactory.decodeFile(new File(mediapath).getAbsolutePath()));
        } else if (requestCode == 101 && resultCode == -1) {
            mediapath = getRealPathFromURI_Video(data.getData());
            mediatype = "2";
            previewimage. setVisibility(View.GONE);
            previewvideo.setVisibility(View.VISIBLE);
            playicon.setVisibility(View.VISIBLE);
            pauseicon.setVisibility(View.INVISIBLE);
            previewvideo.setVideoPath(mediapath);
            previewvideo.seekTo(5000);
        }
    }

    public String getRealPathFromURI_Video(Uri contentUri) {
        Cursor cursor = managedQuery(contentUri, new String[]{"_data"}, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow("_data");
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String getRealPathFromURI_Image(Uri contentUri) {
        Cursor cursor = managedQuery(contentUri, new String[]{"_data"}, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow("_data");
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void showalert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    public void movetovideo() {
        tabLayout.getTabAt(1).select();
    }

    public void uploadingprogress() {
        try {
            dialog = new Dialog(this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialogupload);
            pb1 = (ProgressBar) dialog.findViewById(R.id.pb1);
            persentage = (TextView) dialog.findViewById(R.id.persentage);
            stop = (Button) dialog.findViewById(R.id.stop);
            uploadfiletoserver();
            stop.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        requestgoing=false;
                        call.cancel();
                        dialog.dismiss();
                    } catch (Exception e) {
                    }
                }
            });
            dialog.show();
        } catch (Exception e) {
        }
    }

    public void uploadfiletoserver()
    {
        pb1.setVisibility(View.VISIBLE);
        txtlaoding.setVisibility(View.VISIBLE);
        dialog.setCancelable(false);
        persentage.setVisibility(View.VISIBLE);

        MediaType contentType=MediaType.parse("text/plain; charset=utf-8");
        OkHttpClient client;
        OkHttpClient.Builder client1 = new OkHttpClient.Builder();
        client1.connectTimeout(5, TimeUnit.MINUTES);
        client1.readTimeout(5,TimeUnit.MINUTES);
        client1.writeTimeout(5,TimeUnit.MINUTES);

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);


        File sourceFile = new File(mediapath);
        bodyBuilder.addFormDataPart("file", sourceFile.getName(), RequestBody.create(null, sourceFile));
        bodyBuilder.addFormDataPart("mediatype", null,RequestBody.create(contentType, mediatype+""));
        bodyBuilder.addFormDataPart("id", null,RequestBody.create(contentType, userDataDB.get_userid()));
        bodyBuilder.addFormDataPart("title", null,RequestBody.create(contentType, uploadtitle));

        MultipartBody body = bodyBuilder.build();

        RequestBody requestBody = ProgressHelper.withProgress(body, new ProgressUIListener() {

            //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
            @Override
            public void onUIProgressStart(long totalBytes) {
                super.onUIProgressStart(totalBytes);

            }

            @Override
            public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                persentage.setText((int) (100 * percent)+"%");
                //progress.setText("numBytes:" + numBytes + " bytes" + "\ntotalBytes:" + totalBytes + " bytes" + "\npercent:" + percent * 100 + " %" + "\nspeed:" + speed * 1000 / 1024 / 1024 + "  MB/秒");
            }
            @Override
            public void onUIProgressFinish() {
                super.onUIProgressFinish();

            }

        });
        Request request = new Request.Builder()
                .url(Static_Variable.entypoint1+"upload_image_or_video.php")
                .post(requestBody)
                .build();
        client = client1.build();
        call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb1.setVisibility(View.GONE);
                        persentage.setVisibility(View.GONE);
                        dialog.dismiss();
                        pd.dismiss();
                        if(requestgoing==true)
                        {
                            Toast.makeText(getApplicationContext(),"Please try later",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
            @Override
            public void onResponse(Call call, final Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            String result=response.body().string();
                            if (dialog.isShowing() && dialog != null) {
                                txtlaoding. setVisibility(View.GONE);
                                pb1. setVisibility(View.GONE);
                                persentage. setVisibility(View.GONE);
                                dialog.setCancelable(true);
                                dialog.dismiss();
                                if (mediatype.equalsIgnoreCase("1")) {
                                    showalert("നന്ദി !! താങ്കള്‍ അപ്‌ലോഡ് ചെയ്ത ചിത്രം പരിശോധനക്ക് ശേഷം പോസ്റ്റ് ചെയ്യുന്നതായിരിക്കും ");
                                } else if (mediatype.equalsIgnoreCase("2")) {
                                    showalert("നന്ദി !! താങ്കള്‍ അപ്‌ലോഡ് ചെയ്ത വീഡിയ പരിശോധനക്ക് ശേഷം പോസ്റ്റ് ചെയ്യുന്നതായിരിക്കും ");
                                }
                            }

                        }
                        catch (Exception a)
                        {

                        }
                    }
                });
            }
        });
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public Fragment getItem(int position) {
            return (Fragment) mFragmentList.get(position);
        }

        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public CharSequence getPageTitle(int position) {
            return (CharSequence) mFragmentTitleList.get(position);
        }
    }

    private class imageLoading1 extends AsyncTask<String, String, Bitmap> {
        private imageLoading1() {
        }
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
                ads.setImageBitmap(image1);
                ads.setScaleType(ScaleType.FIT_XY);
                ads.setAdjustViewBounds(true);
            }
        }
    }

    public class report_status extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"reportstatus.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(userDataDB.get_userid()+":%"+Static_Variable.userid+":%"+Static_Variable.chintha_Id+":%"+status_frag.reporttype, "UTF-8");
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
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.reported, Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public class update_fcm extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"updatefcmid.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(dataDb2.getfcmid()+":%"+userDataDB.get_userid(), "UTF-8");
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
                pd.dismiss();
                if (result.contains("ok")) {
                    update_fcm_alert("താങ്കളുടെ നോട്ടിഫിക്കേഷന്‍ പ്രശ്‌നം പരിഹരിക്കാന്‍ ഞങ്ങള്‍ പരമാവധി ശ്രമിച്ചിട്ടുണ്ട്.ഇനിയും പരിഹാരമായില്ലെങ്കില്‍ ദയവായി ആപ്ലിക്കേഷന്‍ അണ്‍ ഇന്‍സ്റ്റാള്‍ ചെയ്ത് വീണ്ടും ഇന്‍സ്റ്റാള്‍ ചെയ്യുക. വളരെ നന്ദി !!!");
                } else {
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }
        }
    }
}
