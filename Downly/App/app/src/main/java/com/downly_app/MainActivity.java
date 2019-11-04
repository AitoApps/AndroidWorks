package com.downly_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.ChunkOffsetBox;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.HandlerBox;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.StaticChunkOffsetBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.FileDataSourceViaHeapImpl;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.boxes.apple.AppleArtist2Box;
import com.googlecode.mp4parser.boxes.apple.AppleArtistBox;
import com.googlecode.mp4parser.boxes.apple.AppleNameBox;
import com.googlecode.mp4parser.util.Path;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import Downly_Data.Feed_Formats;
import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    LabeledSwitch downloadon;
    TextView text;
    RelativeLayout lytyoutube,lytfacebook,lytinstagram,lyttiktok,lyttwitter,lytsharechat,lytpintrest,lytwhatsapp,lytspliiter,lytvideotomp3,lythelp,lytabout;
    InternetConncetivity ic;
    TextView txtyoutube,txtfacebook,txtinstagram,txttiktok,txttwitter,txtsharechat,txtpintrest,txtwhatsapp,txtspliiter,txtvideotomp3,txthelp,txtabout;
    String[] PERMISSIONS = {
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.ACCESS_WIFI_STATE
    };
    int PERMISSION_ALL = 1;
    public static long notitime = System.currentTimeMillis();
    final DataBase db=new DataBase(this);
    private MediaPlayer mp;
    public OkHttpClient client = new OkHttpClient();
    Typeface face;
    RelativeLayout lythowtodown;
    ImageView share;
    ImageView downloads;

    public InterstitialAd interstitial;
    AdRequest adRequest;
    AdRequest adRequest1;
    public AdView adView1;
    int count = 0;
    int intcount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        MobileAds.initialize(this, "ca-app-pub-5517777745693327~5594950145");
        FirebaseApp.initializeApp(this);
        face = Typeface.createFromAsset(getAssets(), "commonfont.otf");
        adView1 = (AdView) findViewById(R.id.adView1);
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-5517777745693327/1464133446");
        adRequest = new AdRequest.Builder().build();
        adRequest1 = new AdRequest.Builder().build();


        try {
            adView1.setAdListener(new AdListener() {
                public void onAdFailedToLoad(int errorCode) {
                    try {
                        if (count <= 10) {
                            adView1.loadAd(adRequest);
                            count++;
                        }
                    } catch (Exception e) {
                    }
                }
            });
            interstitial.setAdListener(new AdListener() {
                public void onAdFailedToLoad(int errorCode) {
                    if (intcount <= 10) {
                        interstitial.loadAd(adRequest1);
                        intcount++;
                    }
                }
            });
        } catch (Exception e) {
        }

        downloadon=(LabeledSwitch)findViewById(R.id.download);
        share=findViewById(R.id.share);
        downloads=findViewById(R.id.downloads);
        text=findViewById(R.id.text);
        lytyoutube=findViewById(R.id.lytyoutube);
        lytfacebook=findViewById(R.id.lytfacebook);
        lytinstagram=findViewById(R.id.lytinstagram);
        lyttiktok=findViewById(R.id.lyttiktok);
        lyttwitter=findViewById(R.id.lyttwitter);
        lytsharechat=findViewById(R.id.lytsharechat);
        lytpintrest=findViewById(R.id.lytpintrest);
        lythowtodown=findViewById(R.id.lythowtodown);


        lytwhatsapp=findViewById(R.id.lytwhatsapp);
        lytspliiter=findViewById(R.id.lytspliiter);
        lytvideotomp3=findViewById(R.id.lytvideotomp3);
        lythelp=findViewById(R.id.lythelp);
        lytabout=findViewById(R.id.lytabout);

        ic=new InternetConncetivity(this);
        txtyoutube=findViewById(R.id.txtyoutube);
        txtfacebook=findViewById(R.id.txtfacebook);
        txtinstagram=findViewById(R.id.txtinstagram);
        txttiktok=findViewById(R.id.txttiktok);
        txttwitter=findViewById(R.id.txttwitter);
        txtsharechat=findViewById(R.id.txtsharechat);
        txtpintrest=findViewById(R.id.txtpintrest);

        txtwhatsapp=findViewById(R.id.txtwhatsapp);
        txtspliiter=findViewById(R.id.txtspliiter);
        txtvideotomp3=findViewById(R.id.txtvideotomp3);
        txthelp=findViewById(R.id.txthelp);
        txtabout=findViewById(R.id.txtabout);

        text.setTypeface(face);

        downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),DownloadsList.class);
                startActivity(i);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showshare("The Simple , Super Cool Video Downloader App. Just copy the link from TikTok , Instagram , Facebook , Sharechat , Twitter , Pinterest , Youtube.. The Rest App will take !! Install Now : http://downly.in");
            }
        });

        lythowtodown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showhowtodownload();
            }
        });

        downloadon.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {

                if(isOn)
                {
                    db.add_downloadon("1");
                }
                else
                {
                    db.add_downloadon("0");
                }
            }
        });
        lytwhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Status_WP.class);
                startActivity(i);

            }
        });

        lytspliiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Splitter_WP.class);
                startActivity(i);
            }
        });

        lytvideotomp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Video_to_Mp3.class);
                startActivity(i);
            }
        });

        lytabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),About_Me.class);
                startActivity(i);
            }
        });

        lythelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openWhatsApp("+91 9037631786");

            }
        });
        lyttwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!hasPermissions(MainActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivity.this,PERMISSIONS, PERMISSION_ALL);

                }
                else
                {
                    File folder = new File(Environment.getExternalStorageDirectory()+"/TwitterDownloads");
                    if (!folder.exists()) {
                        folder.mkdir();
                    }

                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.twitter.android");
                    if (launchIntent != null) {
                        startActivity(launchIntent);
                    } else {

                        Intent launchIntent1 = getPackageManager().getLaunchIntentForPackage("com.twitter.android.lite");
                        if (launchIntent1 != null) {
                            startActivity(launchIntent1);
                        }
                        else
                        {
                            laodweb("https://twitter.com");
                            Toast.makeText(getApplicationContext(), "Please install Twitter App for best perfomance", Toast.LENGTH_SHORT).show();
                        }

                    }
                }


            }
        });

        lytpintrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!hasPermissions(MainActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivity.this,PERMISSIONS, PERMISSION_ALL);

                }
                else
                {
                    File folder = new File(Environment.getExternalStorageDirectory()+"/PinterestDownloads");
                    if (!folder.exists()) {
                        folder.mkdir();
                    }

                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.pinterest");
                    if (launchIntent != null) {
                        startActivity(launchIntent);
                    } else {

                        laodweb("https://pinterest.com");
                        Toast.makeText(getApplicationContext(), "Please install Pinterest App for best perfomance", Toast.LENGTH_SHORT).show();

                    }
                }


            }
        });

        lytyoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!hasPermissions(MainActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivity.this,PERMISSIONS, PERMISSION_ALL);

                }
                else
                {
                    File folder = new File(Environment.getExternalStorageDirectory()+"/YoutubeDownloads");
                    if (!folder.exists()) {
                        folder.mkdir();
                    }

                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                    if (launchIntent != null) {
                        startActivity(launchIntent);
                    } else {
                        laodweb("https://www.youtube.com");
                        Toast.makeText(getApplicationContext(), "Please install Youtube app for better performance", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        lytfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!hasPermissions(MainActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivity.this,PERMISSIONS, PERMISSION_ALL);

                }
                else
                {
                    File folder = new File(Environment.getExternalStorageDirectory()+"/FBDownloads");
                    if (!folder.exists()) {
                        folder.mkdir();
                    }


                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
                    if (launchIntent != null) {
                        startActivity(launchIntent);
                        return;
                    }
                    else {
                        Intent launchIntent1 = getPackageManager().getLaunchIntentForPackage("com.facebook.lite");
                        if (launchIntent1 != null) {
                            startActivity(launchIntent1);
                        } else {
                            laodweb("https://www.facebook.com");
                            Toast.makeText(getApplicationContext(), "Please install Facebook app for better performance", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


            }
        });

        lytinstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!hasPermissions(MainActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivity.this,PERMISSIONS, PERMISSION_ALL);

                }
                else
                {
                    File folder = new File(Environment.getExternalStorageDirectory()+"/InstaDownloads");
                    if (!folder.exists()) {
                        folder.mkdir();
                    }


                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                    if (launchIntent != null) {
                        startActivity(launchIntent);
                    }
                    else {

                        laodweb("https://www.instagram.com/");
                        Toasty.info(getApplicationContext(), "Please install Instagram app for better performance", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        lytsharechat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!hasPermissions(MainActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivity.this,PERMISSIONS, PERMISSION_ALL);
                    return;
                }
                File folder = new File(Environment.getExternalStorageDirectory()+"/SharechatDownloads");
                if (!folder.exists()) {
                    folder.mkdir();
                }

                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("in.mohalla.sharechat");
                if (launchIntent != null) {
                    startActivity(launchIntent);
                } else {
                    laodweb("https://sharechat.com/");
                    Toasty.info(getApplicationContext(), "Please install Sharechat app for better performance", Toast.LENGTH_SHORT).show();
                }
            }
        });
        lyttiktok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!hasPermissions(MainActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivity.this,PERMISSIONS, PERMISSION_ALL);
                    return;
                }
                File folder = new File(Environment.getExternalStorageDirectory()+"/TikTokDownloads");
                if (!folder.exists()) {
                    folder.mkdir();
                }

                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.zhiliaoapp.musically");
                if (launchIntent != null) {
                    startActivity(launchIntent);
                }
                else
                {
                    Intent launchIntent1 = getPackageManager().getLaunchIntentForPackage("com.zhiliaoapp.musically.go");
                    if (launchIntent1 != null) {
                        startActivity(launchIntent1);
                    } else {
                        laodweb("https://www.tiktok.com");
                        Toasty.info(getApplicationContext(), "Please install TikTok App for better performance", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        if(db.get_isupdate().equalsIgnoreCase(""))
        {
            new isupdate().execute();
        }

        if(db.get_isfcmupdate().equalsIgnoreCase(""))
        {
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if (!task.isSuccessful()) {

                        return;
                    }
                    FirebaseMessaging.getInstance().subscribeToTopic("downlyapp");
                   db.add_isfcmupdate("ok");
                }
            });
        }


        if(!db.get_notimsg().equalsIgnoreCase(""))
        {
             shownotimsg();
        }


    }

    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
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

    public void onBackPressed() {
        super.onBackPressed();
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            count = 0;
            intcount = 0;
            try {
                adView1.loadAd(adRequest);
                interstitial.loadAd(adRequest1);
            } catch (Exception e) {

            }

            try
            {
                if(db.get_downloadon().equalsIgnoreCase(""))
                {
                    db.add_downloadon("1");
                }

                if(db.get_downloadon().equalsIgnoreCase("1"))
                {
                    downloadon.setOn(true);
                }
                else
                {
                    downloadon.setOn(false);
                }
            }
            catch (Exception a)
            {

            }
            final ClipboardManager cbm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cbm.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
                @Override
                public void onPrimaryClipChanged() {

                    if (System.currentTimeMillis() > notitime) {
                        String links = cbm.getPrimaryClip().getItemAt(0).coerceToText(getApplicationContext()).toString();
                        if (links.contains("facebook.com") || links.contains("tiktok.com") || links.contains("https://twitter.com/") || links.contains("https://pin.it/") || links.contains("instagram.com/") || links.contains("b.sharechat.com") || links.contains("sharechat.com") || links.contains("youtube.com") || links.contains("youtu.be")) {

                            if (db.get_downloadon().equalsIgnoreCase("1")) {
                                if (links.contains("facebook.com")) {
                                    Temp.videosource = "2";
                                    Temp.youtubeurl = links;
                                    Intent i = new Intent(getApplicationContext(), YoutubeFormat_Window.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                }
                                if (links.contains("tiktok.com")) {
                                    if (links.contains("vm.tiktok.com")) {
                                        Temp.videosource = "3";
                                        Temp.youtubeurl = links;
                                        Intent i = new Intent(getApplicationContext(), YoutubeFormat_Window.class);
                                        startActivity(i);
                                    } else if (links.contains("\\>")) {
                                        String[] fields = links.split("");
                                        if (fields.length > 1) {

                                            Temp.videosource = "3";
                                            Temp.youtubeurl = fields[1];
                                            Intent i = new Intent(getApplicationContext(), YoutubeFormat_Window.class);
                                            startActivity(i);
                                        }
                                    } else {
                                        Temp.videosource = "3";
                                        Temp.youtubeurl = links;
                                        Intent i = new Intent(getApplicationContext(), YoutubeFormat_Window.class);
                                        startActivity(i);
                                    }
                                }
                                if (links.contains("https://twitter.com/")) {
                                    Temp.videosource = "4";
                                    Temp.youtubeurl = links;
                                    Intent i = new Intent(getApplicationContext(), YoutubeFormat_Window.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                }
                                if (links.contains("https://pin.it/")) {
                                    Temp.videosource = "7";
                                    Temp.youtubeurl = links;
                                    Intent i = new Intent(getApplicationContext(), YoutubeFormat_Window.class);
                                    startActivity(i);
                                }
                                if (links.contains("instagram.com/")) {

                                    String[] fields = links.split("\\?");
                                    if (fields.length > 1) {

                                        Temp.videosource = "6";
                                        Temp.youtubeurl = fields[0] + "?__a=1";
                                        Intent i = new Intent(getApplicationContext(), YoutubeFormat_Window.class);
                                        startActivity(i);
                                    }
                                }
                                if (links.contains("b.sharechat.com") || links.contains("sharechat.com")) {

                                    Temp.videosource = "5";
                                    Temp.youtubeurl = links;
                                    Intent i = new Intent(getApplicationContext(), YoutubeFormat_Window.class);
                                    startActivity(i);

                                }
                                if ((links.contains("youtube.com") || links.contains("youtu.be"))) {

                                    Temp.videosource = "1";
                                    Temp.youtubeurl = links;
                                    Intent i = new Intent(getApplicationContext(), YoutubeFormat_Window.class);
                                    startActivity(i);


                                }
                            }
                        }
                    }
                    notitime = System.currentTimeMillis() + 1000;


                }
            });

        } catch (Exception e2) {

            Log.w("Errororr",Log.getStackTraceString(e2));
        }
    }



    public static boolean hasPermissions(Context context, String... permissions) {
        if (!(context == null || permissions == null)) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void download(String path, String downurl, String downname1,String appid,String ctime) {
        try {
            DownloadManager.Request req = new DownloadManager.Request(Uri.parse(downurl));
            req.setDestinationUri(Uri.parse(path));
            req.setNotificationVisibility(0);
            long downloadId = ((DownloadManager) getSystemService(getApplicationContext().DOWNLOAD_SERVICE)).enqueue(req);
            IntentFilter filters = new IntentFilter();
            filters.addAction("extra_click_download_ids");
            filters.addAction("android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED");
            filters.addAction("android.intent.action.DOWNLOAD_COMPLETE");
            registerReceiver(new NotiHandler(), new IntentFilter(filters));
            try {
                db.add_downloadList(downloadId+"", appid, downname1, path, downurl, "0","no",ctime,"1");
                playmusic();
            } catch (Exception e) {
            }
        } catch (Exception e2) {

        }
    }

    public void playmusic() {
        mp = MediaPlayer.create(getApplicationContext(), R.raw.downtone);
        mp.setVolume(0.1f, 0.1f);
        mp.start();
    }


    private void openWhatsApp(String number) {
        try {
            number = number.replace(" ", "").replace("+", "");
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number)+"@s.whatsapp.net");
            startActivity(sendIntent);

        } catch(Exception e) {

            Toast.makeText(getApplicationContext(),"Please Whatsapp to this number "+number,Toast.LENGTH_LONG).show();
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

            title.setText(db.get_notimsg());

            if(db.get_notitype().equalsIgnoreCase("2"))
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

                    laodweb("https://downly.in");
                    db.delete_notimsg();
                    dialog.dismiss();


                }
            });

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db.delete_notimsg();
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
        }
    }

    public void showhowtodownload() {
        try {
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.download_how);
            RelativeLayout lytfacebook = (RelativeLayout) dialog.findViewById(R.id.lytfacebook);
            RelativeLayout lytinstagram = (RelativeLayout) dialog.findViewById(R.id.lytinstagram);
            RelativeLayout lyttiktok = (RelativeLayout) dialog.findViewById(R.id.lyttiktok);
            RelativeLayout lytsharechat = (RelativeLayout) dialog.findViewById(R.id.lytsharechat);
            RelativeLayout lytyoutube = (RelativeLayout) dialog.findViewById(R.id.lytyoutube);
            RelativeLayout lyttwitter=dialog.findViewById(R.id.lyttwitter);
            RelativeLayout lytpinterest=dialog.findViewById(R.id.lytpintrest);

            TextView txtfacebook = (TextView) dialog.findViewById(R.id.txtfacebook);
            TextView txttiktok = (TextView) dialog.findViewById(R.id.txttiktok);
            TextView txtsharechat = (TextView) dialog.findViewById(R.id.txtsharechat);
            TextView txtyoutube = (TextView) dialog.findViewById(R.id.txtyoutube);
            TextView txtinstagram=dialog.findViewById(R.id.txtinstagram);
            TextView txttwitter=dialog.findViewById(R.id.txttwitter);
            TextView txtpinterest=dialog.findViewById(R.id.txtpintrest);

            txtinstagram.setTypeface(face);
            txtfacebook.setTypeface(face);
            txttiktok.setTypeface(face);
            txtsharechat.setTypeface(face);
            txtyoutube.setTypeface(face);
            txttwitter.setTypeface(face);
            txtpinterest.setTypeface(face);

            lytyoutube.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                   Temp.help="1";
                   Temp.helptitle="Youtube Help";
                   Intent i=new Intent(getApplicationContext(),Howtodownload.class);
                   startActivity(i);
                   dialog.dismiss();
                }
            });
            lytfacebook.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Temp.help="2";
                    Temp.helptitle="Facebook Help";
                    Intent i=new Intent(getApplicationContext(),Howtodownload.class);
                    startActivity(i);
                    dialog.dismiss();
                }
            });
            lytinstagram.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Temp.help="3";
                    Temp.helptitle="Instagram Help";
                    Intent i=new Intent(getApplicationContext(),Howtodownload.class);
                    startActivity(i);
                    dialog.dismiss();
                }
            });
            lyttiktok.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Temp.help="4";
                    Temp.helptitle="TikTok Help";
                    Intent i=new Intent(getApplicationContext(),Howtodownload.class);
                    startActivity(i);
                    dialog.dismiss();
                }
            });
            lytsharechat.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Temp.help="5";
                    Temp.helptitle="Sharechat Help";
                    Intent i=new Intent(getApplicationContext(),Howtodownload.class);
                    startActivity(i);
                    dialog.dismiss();
                }
            });
            lyttwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Temp.help="6";
                    Temp.helptitle="Twitter Help";
                    Intent i=new Intent(getApplicationContext(),Howtodownload.class);
                    startActivity(i);
                    dialog.dismiss();
                }
            });

            lytpinterest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Temp.help="7";
                    Temp.helptitle="Pinterest Help";
                    Intent i=new Intent(getApplicationContext(),Howtodownload.class);
                    startActivity(i);
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
        }
    }
    public void showshare(final String text2) {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.news_share);
            RelativeLayout lytwhatsapp = (RelativeLayout) dialog.findViewById(R.id.lytwhatsapp);
            RelativeLayout lytfacebook = (RelativeLayout) dialog.findViewById(R.id.lytfacebook);
            RelativeLayout lytwhatsappbus = (RelativeLayout) dialog.findViewById(R.id.lytwhatsappbus);
            TextView txtwhatsapp = (TextView) dialog.findViewById(R.id.txtwhatsapp);
            TextView txtfacebook = (TextView) dialog.findViewById(R.id.txtfacebook);
            TextView txtwhatsappbus = (TextView) dialog.findViewById(R.id.txtwhatsappbus);
            txtwhatsapp.setText("Whatsapp");
            txtfacebook.setText("Facebook");
            txtwhatsappbus.setText("Whatsapp Business");
            txtwhatsapp.setTypeface(face);
            txtfacebook.setTypeface(face);
            txtwhatsappbus.setTypeface(face);
            lytwhatsapp.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onClickApp("com.whatsapp", text2);
                    dialog.dismiss();
                }
            });
            lytfacebook.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onClickApp("com.facebook.katana", text2);
                    dialog.dismiss();
                }
            });
            lytwhatsappbus.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onClickApp("com.whatsapp.w4b", text2);
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception e) {
        }
    }

    public void onClickApp(String pack, String msg) {
        try {
            Intent waIntent = new Intent("android.intent.action.SEND");
            waIntent.setType("text/html");
            waIntent.setPackage(pack);
            waIntent.putExtra("android.intent.extra.TEXT", msg);
            startActivity(Intent.createChooser(waIntent, "Share with"));
        } catch (Exception e) {
            if (pack.equalsIgnoreCase("com.whatsapp")) {
                Toast.makeText(getApplicationContext(), "Please install Whatsapp app", Toast.LENGTH_SHORT).show();
            } else if (pack.equalsIgnoreCase("com.facebook.katana")) {
                Toast.makeText(getApplicationContext(), "Please install Facebook app", Toast.LENGTH_SHORT).show();
            } else if (pack.equalsIgnoreCase("com.whatsapp.w4b")) {
                Toast.makeText(getApplicationContext(), "Please install Whatsapp Business app", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class isupdate extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
        }
        public String doInBackground(String... arg0) {
            try {
                String link="http://apistatuschinthakal.in/appinstall/downly_installnew.php";
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
            if (result.contains("ok")) {
                db.add_isupdate("ok");
            }
        }
    }

}
