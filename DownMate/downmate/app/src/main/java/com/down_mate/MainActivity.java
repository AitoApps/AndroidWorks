package com.down_mate;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.net.URLEncoder.encode;

public class MainActivity extends AppCompatActivity {
    int PERMISSION = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.ACCESS_WIFI_STATE
    };

    TextView text;
    RelativeLayout tiktok, sharechat, instagram, facebook, whatsappstatus, videosplitter, videotomp3, unknownchat;
    ImageView qulitysettings_fb;
    final BackEnd_DB db = new BackEnd_DB(this);
    Typeface face;
    NetworkConnections nc;
    public static long nexttime = System.currentTimeMillis();
    private MediaPlayer mplayer;
    String dwnldurl = "", wheretosave = "";
    public OkHttpClient client = new OkHttpClient();
    ImageView help, about, downloads, share;

    public AdView adView1;
    AdRequest adreq1;
    AdRequest adreq;
    private InterstitialAd intestrial;
    int count = 0;
    int intcount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        MobileAds.initialize(this, "ca-app-pub-2432830627480060~4959209996");
        face = Typeface.createFromAsset(getAssets(), "font/fonts.otf");
        text = findViewById(R.id.text);
        tiktok = findViewById(R.id.tiktok);
        nc = new NetworkConnections(this);
        share = findViewById(R.id.share);
        sharechat = findViewById(R.id.sharechat);
        instagram = findViewById(R.id.instagram);
        facebook = findViewById(R.id.facebook);
        whatsappstatus = findViewById(R.id.whatsappstatus);
        videosplitter = findViewById(R.id.videosplitter);
        videotomp3 = findViewById(R.id.videotomp3);
        unknownchat = findViewById(R.id.unknownchat);
        qulitysettings_fb = findViewById(R.id.qulitysettings_fb);
        adView1 = findViewById(R.id.adView1);
        intestrial = new InterstitialAd(MainActivity.this);
        intestrial.setAdUnitId("ca-app-pub-2432830627480060/3287035679");
        adreq = new AdRequest.Builder().build();
        adreq1 = new AdRequest.Builder().build();

        downloads = findViewById(R.id.downloads);
        about = findViewById(R.id.about);
        help = findViewById(R.id.help);

        try {
            adView1.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    try {
                        if (count <= 10) {
                            adView1.loadAd(adreq1);
                            count++;
                        }


                    } catch (Exception a) {

                    }

                }
            });

            intestrial.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {

                    if (intcount <= 10) {
                        intestrial.loadAd(adreq);
                        intcount++;
                    }

                }
            });

        } catch (Exception a) {

        }

        if (db.get_fbquality().equalsIgnoreCase("")) {
            db.add_fbquality("low");
        }
        if (nc.isConnectingToInternet()) {
            if (db.get_isupdate().equalsIgnoreCase("")) {
                new firstupdate().execute();
            }
        }
        downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), VideoDownloads.class);
                startActivity(i);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), About.class);
                startActivity(i);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showshare("Download Video/Image from Instagram , TikTok , Facebook , Sharechat without any watermark and logo .. Install Now : https://play.google.com/store/apps/details?id=com.down_mate");
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_howdownload();
            }
        });
        qulitysettings_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showformat();
            }
        });
        unknownchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showunknownunmber();
            }
        });
        whatsappstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), WP_StatusSaver.class);
                startActivity(i);
            }
        });
        videosplitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ThirtySecondSplitter.class);
                startActivity(i);
            }
        });
        videotomp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File tikfolder = new File(Environment.getExternalStorageDirectory() + "/MP3Converts");
                if (!tikfolder.exists()) {
                    tikfolder.mkdir();
                }
                Intent i = new Intent(getApplicationContext(), MP3_Converts.class);
                startActivity(i);
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File tikfolder = new File(Environment.getExternalStorageDirectory() + "/FB_Downloads");
                if (!tikfolder.exists()) {
                    tikfolder.mkdir();
                }
                if (!storagepermitted(MainActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION);
                } else {
                    if (db.get_fphelp().equalsIgnoreCase("")) {
                        howtodownload1(1, 4, "How to download from Facebook ?");
                    } else {
                        db.add_fb("1");
                        openfacebook();
                    }
                }
            }
        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File tikfolder = new File(Environment.getExternalStorageDirectory() + "/Insta_Downloads");
                if (!tikfolder.exists()) {
                    tikfolder.mkdir();
                }
                if (!storagepermitted(MainActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION);
                } else {
                    if (db.get_instahelp().equalsIgnoreCase("")) {
                        howtodownload1(1, 3, "How to download from Instagram ?");
                    } else {
                        db.add_insta("1");
                        openinstagram();
                    }
                }
            }
        });
        sharechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File tikfolder = new File(Environment.getExternalStorageDirectory() + "/ShareChat_Downloads");
                if (!tikfolder.exists()) {
                    tikfolder.mkdir();
                }
                if (!storagepermitted(MainActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION);
                } else {
                    if (db.get_sharehelp().equalsIgnoreCase("")) {
                        howtodownload1(1, 2, "How to download from ShareChat ?");
                    } else {
                        db.add_chatshare("1");
                        opensharechat();
                    }
                }
            }
        });
        tiktok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File tikfolder = new File(Environment.getExternalStorageDirectory() + "/TikTok_Downloads");
                if (!tikfolder.exists()) {
                    tikfolder.mkdir();
                }
                if (!storagepermitted(MainActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION);
                } else {

                    if (db.get_tikhelp().equalsIgnoreCase("")) {
                        howtodownload1(1, 1, "How to download from TikTok ?");
                    } else {
                        db.add_ticks("1");
                        opentitktok();
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (intestrial.isLoaded()) {
            intestrial.show();
        }

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {

            count = 0;
            intcount = 0;

            try {
                adView1.loadAd(adreq1);
                intestrial.loadAd(adreq);
            } catch (Exception a) {

            }

            final ClipboardManager cbm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cbm.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
                @Override
                public void onPrimaryClipChanged() {

                    if (nc.isConnectingToInternet()) {
                        String address = cbm.getPrimaryClip().getItemAt(0).coerceToText(getApplicationContext()).toString();
                        if (System.currentTimeMillis() > nexttime) {
                            if (address.contains("facebook.com")) {
                                if (!db.get_fb().equalsIgnoreCase("")) {
                                    Toasty.success(getApplicationContext(), "Download will start soon", Toast.LENGTH_SHORT, true).show();
                                    Facebook_parser(address);
                                }
                            }
                            if (address.contains("tiktok.com")) {
                                if (address.contains("vm.tiktok.com")) {
                                    Toasty.success(getApplicationContext(), "Download will start soon", Toast.LENGTH_SHORT, true).show();
                                    TikTok_Parser(address);
                                } else if (address.contains("\\>")) {
                                    String[] fields = address.split("");
                                    if (fields.length > 1) {
                                        Toasty.success(getApplicationContext(), "Download will start soon", Toast.LENGTH_SHORT, true).show();
                                        TikTok_Parser(fields[1]);
                                    }
                                } else {
                                    Toasty.success(getApplicationContext(), "Download will start soon", Toast.LENGTH_SHORT, true).show();
                                    TikTok_Parser(address);
                                }
                            }
                            if (address.contains("instagram.com/")) {
                                if (!db.get_insta().equalsIgnoreCase("")) {
                                    String[] fields = address.split("\\?");
                                    if (fields.length > 1) {
                                        Toasty.success(getApplicationContext(), "Download will start soon", Toast.LENGTH_SHORT, true).show();
                                        instagram_parser(fields[0] + "?__a=1");
                                    }
                                }
                            }
                            if (address.contains("b.sharechat.com") || address.contains("sharechat.com")) {
                                if (!db.get_chatshare().equalsIgnoreCase("")) {
                                    Toasty.success(getApplicationContext(), "Download will start soon", Toast.LENGTH_SHORT, true).show();
                                    sharechat_parser(address);
                                }
                            }

                        }
                        nexttime = System.currentTimeMillis() + 1000;
                    } else {
                        Toasty.error(getApplicationContext(), TempValues.nonetwork, Toast.LENGTH_LONG).show();
                    }


                }
            });
        } catch (Exception a) {

        }
    }

    public static boolean storagepermitted(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void howtodownload1(int isfirst, int type, String message) {
        try {
            //1-tiktok 2-sharechat 4-facebook 3-instagram
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.how_todownload_1);
            final TextView txt = dialog.findViewById(R.id.txt);
            final ImageView image = dialog.findViewById(R.id.img);
            final Button ok = dialog.findViewById(R.id.ok);
            ok.setTypeface(face);
            txt.setTypeface(face);
            txt.setText(message);
            if (type == 1) {
                image.setImageResource(R.drawable.sahayam_tiktok);
            } else if (type == 2) {
                image.setImageResource(R.drawable.sahayam_sc);
            } else if (type == 3) {
                image.setImageResource(R.drawable.sahayam_insta);
            } else if (type == 4) {
                image.setImageResource(R.drawable.sahayam_fb);
            }
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (type == 1) {

                        db.add_tickhelp("1");
                        db.add_ticks("1");


                        if (isfirst == 1) {
                            opentitktok();
                        }


                    } else if (type == 2) {

                        db.add_sharehelp("1");
                        db.add_chatshare("1");

                        if (isfirst == 1) {
                            opensharechat();
                        }


                    } else if (type == 3) {
                        db.add_instahelp("1");
                        db.add_insta("1");
                        if (isfirst == 1) {
                            openinstagram();
                        }
                    } else if (type == 4) {
                        db.add_fbhelp("1");
                        db.add_fb("1");
                        if (isfirst == 1) {
                            openfacebook();
                        }
                    }
                    dialog.dismiss();
                }
            });
            dialog.show();

        } catch (Exception a) {

        }

    }


    public void opentitktok() {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.zhiliaoapp.musically");
        if (intent != null) {
            startActivity(intent);
        } else {
            Intent intent1 = getPackageManager().getLaunchIntentForPackage("com.zhiliaoapp.musically.go");
            if (intent1 != null) {
                startActivity(intent1);
            } else {
                Toasty.info(getApplicationContext(), "Please install TikTok App", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void opensharechat() {

        Intent intent1 = getPackageManager().getLaunchIntentForPackage("in.mohalla.sharechat");
        if (intent1 != null) {
            startActivity(intent1);
        } else {
            Toasty.info(getApplicationContext(), "Please install Sharechat", Toast.LENGTH_SHORT).show();
        }

    }

    public void openinstagram() {

        Intent intent1 = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
        if (intent1 != null) {
            startActivity(intent1);
        } else {
            Toasty.info(getApplicationContext(), "Please install Instagram", Toast.LENGTH_SHORT).show();
        }

    }

    public void openfacebook() {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
        if (intent != null) {
            startActivity(intent);
        } else {
            Intent intent1 = getPackageManager().getLaunchIntentForPackage("com.facebook.lite");
            if (intent1 != null) {
                startActivity(intent1);
            } else {
                Toasty.info(getApplicationContext(), "Please install Facebook App", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class firstupdate extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {

            try {
                String link = TempValues.weblink + "downmate_install.php";
                String data = encode("item", "UTF-8")
                        + "=" + encode("", "UTF-8");
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
                while ((line = reader.readLine()) != null) {

                    sb.append(line);

                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.contains("ok")) {
                db.add_isupdate("ok");
            }


        }

    }

    public void showformat() {
        try {
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.facebook_settings);

            final RadioButton low = dialog.findViewById(R.id.qlty_low);
            final RadioButton high = dialog.findViewById(R.id.qlty_high);
            final Button save = dialog.findViewById(R.id.save);
            final TextView msg1 = dialog.findViewById(R.id.msg1);

            low.setTypeface(face);
            high.setTypeface(face);
            save.setTypeface(face);
            msg1.setTypeface(face, Typeface.BOLD);
            try {
                if (!db.get_fbquality().equalsIgnoreCase("")) {
                    if (db.get_fbquality().equalsIgnoreCase("low")) {
                        low.setChecked(true);
                    } else if (db.get_fbquality().equalsIgnoreCase("high")) {
                        high.setChecked(true);
                    }
                }
            } catch (Exception a) {

            }
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!low.isChecked() && !high.isChecked()) {
                        Toasty.error(getApplicationContext(), "Please select atleast one quality", Toast.LENGTH_SHORT, true).show();
                    } else {

                        if (low.isChecked()) {
                            db.add_fbquality("low");
                        } else if (high.isChecked()) {
                            db.add_fbquality("high");
                        }
                        dialog.dismiss();
                    }
                }
            });
            dialog.show();
        } catch (Exception a) {
            // Toast.makeText(getApplicationContext(),Log.getStackTraceString(a),Toast.LENGTH_LONG).show();
        }
    }

    public void instagram_parser(String link) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request.Builder builder = new Request.Builder();
                    builder.url(link);
                    Request request = builder.build();
                    Response response = client.newCall(request).execute();
                    String datas = response.body().string();
                    if (datas.contains("Page Not Found &bull; Instagram")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.info(getApplicationContext(), "Unable to Download ! This video / Image is Private", Toast.LENGTH_SHORT).show();

                            }
                        });
                    } else {
                        JSON json = new JSON(datas);
                        JSON js = json.key("graphql");
                        JSON json1 = js.key("shortcode_media");
                        String types = json1.key("__typename").toString();
                        String downname = "";
                        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                        String ctime = sdf.format(new Date());
                        if (types.equalsIgnoreCase("GraphImage")) {
                            dwnldurl = json1.key("display_url").toString();
                            downname = "INSTA_" + ctime + ".jpg";
                            wheretosave = "file://" + Environment.getExternalStorageDirectory() + "/InstaDownloads/" + downname;
                        } else if (types.equalsIgnoreCase("GraphVideo")) {
                            dwnldurl = json1.key("video_url").toString();
                            downname = "INSTA_" + ctime + ".mp4";
                            wheretosave = "file://" + Environment.getExternalStorageDirectory() + "/InstaDownloads/" + downname;
                        }
                        downloads_queu(wheretosave, dwnldurl, downname, "INSTA");

                    }


                } catch (Exception a) {
                    //Toasty.info(getApplicationContext(),"Unable to Download ! This video / Image is Private",Toast.LENGTH_SHORT).show();
                }


            }
        }).start();
    }


    public void downloads_queu(String path, String downurl, String downname1, String fname) {
        try {

            Uri downloadUri = Uri.parse(downurl);
            DownloadManager.Request req = new DownloadManager.Request(downloadUri);
            req.setDestinationUri(Uri.parse(path));
            req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            DownloadManager dm = (DownloadManager) getSystemService(getApplicationContext().DOWNLOAD_SERVICE);
            final long downloadId = dm.enqueue(req);
            IntentFilter filters = new IntentFilter();
            filters.addAction(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
            filters.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
            filters.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            registerReceiver(new Noti_Reciver(), new IntentFilter(filters));
            db.add_downlaodlist(downloadId + "", fname, downname1, path, downurl, "0");
            playnoti();
        } catch (Exception a) {

            // Log.w("itsok",Log.getStackTraceString(a));
        }
    }

    public void playnoti() {
        try {
            mplayer = MediaPlayer.create(getApplicationContext(), R.raw.quedown);
            mplayer.setVolume(0.1f, 0.1f);
            mplayer.start();
        } catch (Exception a) {

        }

    }


    public void showshare(final String text) {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.share_data);
            final RelativeLayout lytwhatsapp = dialog.findViewById(R.id.wp_layout);
            final RelativeLayout lytfacebook = dialog.findViewById(R.id.fb_layout);
            final RelativeLayout lytwhatsappbus = dialog.findViewById(R.id.wpbus_layout);
            final TextView txtwhatsapp = dialog.findViewById(R.id.wp_txt);
            final TextView txtfacebook = dialog.findViewById(R.id.fb_txt);
            final TextView txtwhatsappbus = dialog.findViewById(R.id.wpbus_txt);

            txtwhatsapp.setText("Whatsapp");
            txtfacebook.setText("Facebook");
            txtwhatsappbus.setText("Whatsapp Business");

            txtwhatsapp.setTypeface(face);
            txtfacebook.setTypeface(face);
            txtwhatsappbus.setTypeface(face);

            lytwhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickApp("com.whatsapp", text);
                    dialog.dismiss();
                }
            });

            lytfacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickApp("com.facebook.katana", text);
                    dialog.dismiss();
                }
            });

            lytwhatsappbus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickApp("com.whatsapp.w4b", text);
                    dialog.dismiss();
                }
            });


            dialog.show();
        } catch (Exception a) {

        }

    }

    public void onClickApp(String pack, String msg) {
        PackageManager pm = getPackageManager();
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            @SuppressWarnings("unused")
            PackageInfo info = pm.getPackageInfo(pack, PackageManager.GET_META_DATA);
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/html");
            waIntent.setPackage(pack);
            waIntent.putExtra(Intent.EXTRA_TEXT, msg);
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

    /// tick tok code here

    private void TikTok_Parser(String ticklink) {

        try {

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("url", ticklink)
                    .build();
            Request request = new Request.Builder()
                    .url("https://tiktokvideodownload.com/download")
                    .post(body)
                    .build();

            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toasty.info(getApplicationContext(), "Error on Bypassing" + Log.getStackTraceString(e), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {


                                String result = response.body().string();
                                Document doc = Jsoup.parse(result);
                                Element link = doc.select("video").first();
                                String relHref = link.attr("src");
                                SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                                String ctime = sdf.format(new Date());
                                String downname = "TIKS_" + ctime + ".mp4";
                                String path = "file://" + Environment.getExternalStorageDirectory() + "/TikTokDownloads/" + downname;
                                Log.w("ffdf", relHref);
                                downloads_queu(path, relHref, downname, "TIKS");
                            } catch (Exception a) {
                            }
                        }
                    });
                }
            });
        } catch (Exception a) {
            //Toasty.info(getApplicationContext(), "1"+Log.getStackTraceString(a), Toast.LENGTH_SHORT).show();

        }
    }

    /// tick tok code here


    private void Facebook_parser(String link1) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String hdlink = "NA";
                String sdlink = "NA";

                Request.Builder builder = new Request.Builder();
                builder.header("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                builder.url(link1);
                Request request = builder.build();

                try {
                    Response response = client.newCall(request).execute();
                    String responseString = response.body().string();
                    final String pattern = "\\\"?hd_src\\\"?:\\\"([^\\\"]+)";
                    final String pattern1 = "\\\"?sd_src_no_ratelimit\\\"?:\\\"([^\\\"]+)";
                    Pattern r = Pattern.compile(pattern);
                    Matcher m = r.matcher(responseString);

                    Pattern r1 = Pattern.compile(pattern1);
                    Matcher m1 = r1.matcher(responseString);

                    try {
                        if (m.find()) {
                            hdlink = m.group(1);
                        } else {
                            hdlink = "NA";
                        }
                    } catch (Exception a) {
                        hdlink = "NA";
                    }


                    try {
                        if (m1.find()) {
                            sdlink = m1.group(1);
                        } else {
                            sdlink = "NA";
                        }
                    } catch (Exception a) {
                        sdlink = "NA";
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                    String ctime = sdf.format(new Date());
                    String downname = "FB_" + ctime + ".mp4";
                    String path = "file://" + Environment.getExternalStorageDirectory() + "/FBDownloads/" + downname;

                    if (db.get_fbquality().equalsIgnoreCase("low")) {
                        if (sdlink != "NA") {
                            downloads_queu(path, sdlink, downname, "FB");

                        } else if (hdlink != "NA") {
                            downloads_queu(path, hdlink, downname, "FB");

                        }
                    } else if (db.get_fbquality().equalsIgnoreCase("high")) {
                        if (hdlink != "NA") {
                            downloads_queu(path, hdlink, downname, "FB");

                        } else if (sdlink != "NA") {
                            downloads_queu(path, sdlink, downname, "FB");

                        }
                    }


                } catch (Exception e) {


                }
            }
        }).start();
    }


    public void showunknownunmber() {
        try {
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.unknownchat);

            TextView txtcontact = dialog.findViewById(R.id.contact_txt);
            EditText contactnumber = dialog.findViewById(R.id.contactnumber);
            Button send = dialog.findViewById(R.id.chat);

            txtcontact.setTypeface(face);

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (contactnumber.getText().toString().equalsIgnoreCase("")) {
                        Toasty.error(getApplicationContext(), "Please enter contact number with countrycode", Toast.LENGTH_SHORT).show();
                        contactnumber.requestFocus();
                    } else if (!contactnumber.getText().toString().contains("+")) {
                        Toasty.info(getApplicationContext(), "Please add country code like +91,+1,+347 etc..", Toast.LENGTH_SHORT).show();
                        contactnumber.requestFocus();
                    } else {
                        openWhatsApp(contactnumber.getText().toString());
                        dialog.dismiss();
                    }
                }
            });


            dialog.show();
        } catch (Exception a) {

            //Toast.makeText(getApplicationContext(),Log.getStackTraceString(a),Toast.LENGTH_LONG).show();
        }

    }


    private void openWhatsApp(String number) {
        try {
            number = number.replace(" ", "").replace("+", "");
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number) + "@s.whatsapp.net");
            startActivity(sendIntent);
        } catch (Exception e) {
            Toasty.info(getApplicationContext(), "Please install whatsapp", Toast.LENGTH_LONG).show();
        }
    }


    public void sharechat_parser(String link) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                try {


                    URL url = new URL(link);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    url.openStream()));

                    String line = null;
                    while ((line = in.readLine()) != null) {
                        builder.append(line);
                    }

                    String datas = builder.toString();


                    if (datas.contains("compressedVideoUrl") && datas.contains("videoCompressedSize")) {
                        int index = datas.indexOf("compressedVideoUrl");
                        int index1 = datas.indexOf("videoCompressedSize");
                        String a1 = datas.substring(index + 21, index1 - 3);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                        String ctime = sdf.format(new Date());
                        String downname = "SC_" + ctime + ".mp4";
                        String path = "file://" + Environment.getExternalStorageDirectory() + "/SharechatDownloads/" + downname;
                        String newdownpath = URLEncoder.encode(a1.substring(a1.lastIndexOf('/') + 1), "UTF-8");
                        String newurl = a1.substring(0, a1.lastIndexOf('/') + 1) + newdownpath;
                        downloads_queu(path, newurl, downname, "SC");

                    } else if (datas.contains("og:video:url") && datas.contains("_c_v.mp4")) {
                        int index = datas.indexOf("og:video:url");
                        int index1 = datas.indexOf("_c_v.mp4");
                        String a1 = datas.substring(index + 23, index1) + "_c_v.mp4";
                        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                        String ctime = sdf.format(new Date());
                        String downname = "SC_" + ctime + ".mp4";
                        String path = "file://" + Environment.getExternalStorageDirectory() + "/SharechatDownloads/" + downname;
                        String newdownpath = URLEncoder.encode(a1.substring(a1.lastIndexOf('/') + 1), "UTF-8");
                        String newurl = a1.substring(0, a1.lastIndexOf('/') + 1) + newdownpath;
                        downloads_queu(path, newurl, downname, "SC");
                    } else if (datas.contains("og:video:url") && datas.contains("compressed_vat.mp4")) {
                        int index = datas.indexOf("og:video:url");
                        int index1 = datas.indexOf("compressed_vat.mp4");
                        String a1 = datas.substring(index + 23, index1) + "compressed_vat.mp4";
                        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                        String ctime = sdf.format(new Date());
                        String downname = "SC_" + ctime + ".mp4";
                        String path = "file://" + Environment.getExternalStorageDirectory() + "/SharechatDownloads/" + downname;
                        String newdownpath = URLEncoder.encode(a1.substring(a1.lastIndexOf('/') + 1), "UTF-8");
                        String newurl = a1.substring(0, a1.lastIndexOf('/') + 1) + newdownpath;
                        downloads_queu(path, newurl, downname, "SC");
                    } else if (datas.contains("og:video:url") && datas.contains("compressed.mp4")) {
                        int index = datas.indexOf("og:video:url");
                        int index1 = datas.indexOf("compressed.mp4");
                        String a1 = datas.substring(index + 23, index1) + "compressed.mp4";

                        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                        String ctime = sdf.format(new Date());
                        String downname = "SC_" + ctime + ".mp4";
                        String path = "file://" + Environment.getExternalStorageDirectory() + "/SharechatDownloads/" + downname;
                        String newdownpath = URLEncoder.encode(a1.substring(a1.lastIndexOf('/') + 1), "UTF-8");
                        String newurl = a1.substring(0, a1.lastIndexOf('/') + 1) + newdownpath;
                        downloads_queu(path, newurl, downname, "SC");
                    } else if (datas.contains("og:video:url") && datas.contains(".mp4")) {
                        int index = datas.indexOf("og:video:url");
                        int index1 = datas.indexOf(".mp4");
                        String a1 = datas.substring(index + 23, index1) + ".mp4";

                        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                        String ctime = sdf.format(new Date());
                        String downname = "SC_" + ctime + ".mp4";
                        String path = "file://" + Environment.getExternalStorageDirectory() + "/SharechatDownloads/" + downname;
                        String newdownpath = URLEncoder.encode(a1.substring(a1.lastIndexOf('/') + 1), "UTF-8");
                        String newurl = a1.substring(0, a1.lastIndexOf('/') + 1) + newdownpath;
                        downloads_queu(path, newurl, downname, "SC");
                    } else if (datas.contains("og:image") && !datas.contains("compressed_thumb.jpeg")) {
                        int index = datas.indexOf("og:image");
                        int index1 = datas.indexOf("da:req:type");
                        String a1 = datas.substring(index + 19, index1 - 34);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                        String ctime = sdf.format(new Date());
                        String downname = "SC_" + ctime + ".jpg";
                        String path = "file://" + Environment.getExternalStorageDirectory() + "/SharechatDownloads/" + downname;
                        String newdownpath = URLEncoder.encode(a1.substring(a1.lastIndexOf('/') + 1), "UTF-8");
                        String newurl = a1.substring(0, a1.lastIndexOf('/') + 1) + newdownpath;
                        downloads_queu(path, newurl, downname, "SC");
                    }

                } catch (Exception e) {
                }

            }
        }).start();
    }


    public void show_howdownload() {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.howtodown);
            final RelativeLayout lytfacebook = dialog.findViewById(R.id.fb_layout);
            final RelativeLayout lytinstagram = dialog.findViewById(R.id.insta_layout);
            final RelativeLayout lyttiktok = dialog.findViewById(R.id.tiktok_layout);
            final RelativeLayout lytsharechat = dialog.findViewById(R.id.sharechat_layout);

            final TextView txtfacebook = dialog.findViewById(R.id.fb_txt);
            final TextView txtinstagram = dialog.findViewById(R.id.insta_txt);
            final TextView txttiktok = dialog.findViewById(R.id.tiktok_txt);
            final TextView txtsharechat = dialog.findViewById(R.id.sharechat_txt);

            txtinstagram.setTypeface(face);
            txtfacebook.setTypeface(face);
            txttiktok.setTypeface(face);
            txtsharechat.setTypeface(face);

            lytfacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    howtodownload1(0, 4, "How to download from Facebook ?");

                }
            });

            lytinstagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    howtodownload1(0, 3, "How to download from Instagram ?");

                }
            });

            lyttiktok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    howtodownload1(0, 1, "How to download from TikTok ?");

                }
            });

            lytsharechat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    howtodownload1(0, 2, "How to download from Sharechat ?");

                }
            });


            dialog.show();
        } catch (Exception a) {
            // Toasty.info(getApplicationContext(), Log.getStackTraceString(a),Toast.LENGTH_LONG).show();
        }

    }

}