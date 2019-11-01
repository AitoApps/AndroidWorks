package com.down_mate;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.util.List;

import Adapter_Class.Whats_Adapter;
import Feed_Data.Whats_Feed;
import de.mateware.snacky.Snacky;

public class WP_StatusSaver extends AppCompatActivity {
    ImageView back;
    TextView emptydata;
    TextView title;
    ListView listview;
    ProgressDialog pd;
    Typeface face;
    private Whats_Adapter listAdapter;
    private List<Whats_Feed> feedItems;
    ProgressBar pb;
    ImageView whatsapp, whatsappB;
    private static final String WB_Location = "/WhatsApp Business/Media/.Statuses";
    private static final String W_Location = "/WhatsApp/Media/.Statuses";
    private static String Save_Media = "/DownMate_WhatStatus/";
    NetworkConnections cd;
    final BackEnd_DB db=new BackEnd_DB(this);
    String[] PERMISSIONS = {
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE
    };
    int PERMISSION_ALL = 1;
    public AdView adView1;
    AdRequest adreq1;
    AdRequest adreq;
    private InterstitialAd intestrial;
    int count=0;
    int intcount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wpstatus_activity);
        try
        {
            title=(TextView)findViewById(R.id.viewtitle);
            listview =(ListView)findViewById(R.id.listview);
            cd=new NetworkConnections(this);
            emptydata =findViewById(R.id.emptydownloads);
            whatsappB =findViewById(R.id.wpbus);
            pd=new ProgressDialog(this);
            whatsapp=findViewById(R.id.wp);
            back=(ImageView)findViewById(R.id.back);
            adView1=findViewById(R.id.adView1);
            pb=(ProgressBar)findViewById(R.id.pb);
            feedItems = new ArrayList<Whats_Feed>();
            listAdapter = new Whats_Adapter(this, feedItems);
            listview.setAdapter(listAdapter);
            face= Typeface.createFromAsset(getAssets(), "font/fonts.otf");
            title.setTypeface(face);
            emptydata.setTypeface(face);

            intestrial = new InterstitialAd(WP_StatusSaver.this);
            intestrial.setAdUnitId("ca-app-pub-2432830627480060/3287035679");
            adreq = new AdRequest.Builder().build();
            adreq1 = new AdRequest.Builder().build();


            try
            {
                adView1.setAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        try
                        {
                            if(count<=10)
                            {
                                adView1.loadAd(adreq1);
                                count++;
                            }


                        }
                        catch (Exception a)
                        {

                        }

                    }
                });

                intestrial.setAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {

                        if(intcount<=10) {
                            intestrial.loadAd(adreq);
                            intcount++;
                        }

                    }
                });

            }
            catch (Exception a)
            {

            }
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            File folder2=new File(Environment.getExternalStorageDirectory()+"/"+ Save_Media);
            if(!folder2.exists())
            {
                folder2.mkdir();

            }

            if(db.get_whichwp().equalsIgnoreCase("2"))
            {
            whatsappB.setVisibility(View.INVISIBLE);
            whatsapp.setVisibility(View.VISIBLE);
            title.setText("Whatsapp Business Status");
            }
            else if(db.get_whichwp().equalsIgnoreCase("1"))
            {
                whatsappB.setVisibility(View.VISIBLE);
                whatsapp.setVisibility(View.INVISIBLE);
                title.setText("Whatsapp Status");
            }

            whatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.add_whichwp("1");
                    title.setText("Whatsapp Status");
                    whatsappB.setVisibility(View.VISIBLE);
                    whatsapp.setVisibility(View.INVISIBLE);
                    data_loading();
                }
            });

            whatsappB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.add_whichwp("2");
                    data_loading();
                    title.setText("Whatsapp Business Status");
                    whatsappB.setVisibility(View.INVISIBLE);
                    whatsapp.setVisibility(View.VISIBLE);
                }
            });


        }
        catch (Exception a)
        {
            //Toasty.info(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(intestrial.isLoaded())
        {
            intestrial.show();
        }

        super.onBackPressed();
    }


    public void data_loading()
    {
        try
        {

            listview.setVisibility(View.GONE);
            emptydata.setVisibility(View.GONE);
            feedItems.clear();
            File f=null;
            if(db.get_whichwp().equalsIgnoreCase("1"))
            {
                f=new File(Environment.getExternalStorageDirectory().toString()+ W_Location);
            }
            else if(db.get_whichwp().equalsIgnoreCase("2"))
            {
                f=new File(Environment.getExternalStorageDirectory().toString()+ WB_Location);
            }

            File[] files= f.listFiles();
            Arrays.sort(files, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return Long.compare(f2.lastModified(),f1.lastModified());
                }
            });

            int i=0;
            for (File file : files) {

                if (file.getName().endsWith(".jpg") ||
                        file.getName().endsWith(".gif") ||
                        file.getName().endsWith(".mp4")) {
                    i=1;
                    Whats_Feed item=new Whats_Feed();
                    item.set_fpath(file.getAbsolutePath());

                    feedItems.add(item);
                }
            }

            if(i==1)
            {
                pb.setVisibility(View.GONE);
                listview.setVisibility(View.VISIBLE);
                listAdapter.notifyDataSetChanged();
            }
            else
            {
                pb.setVisibility(View.GONE);
                listview.setVisibility(View.GONE);
                listAdapter.notifyDataSetChanged();
                emptydata.setVisibility(View.VISIBLE);
            }



        }
        catch (Exception a)
        {

            listview.setVisibility(View.GONE);
            emptydata.setVisibility(View.VISIBLE);
        }


    }

    public void videosave(final File sourceFile)
    {
        try
        {

            if (!storagepermission(WP_StatusSaver.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(WP_StatusSaver.this, PERMISSIONS, PERMISSION_ALL);
            }

            else
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            file_copy(sourceFile, new File(Environment.getExternalStorageDirectory().toString() + Save_Media + sourceFile.getName()));
                            Snacky.builder().
                                    setActivty(WP_StatusSaver.this).
                                    setText("Saved to Gallery").
                                    success().
                                    show();
                        } catch (Exception e) {

                            Snacky.builder().
                                    setActivty(WP_StatusSaver.this).
                                    setText("Unable to Save").
                                    error().
                                    show();
                        }

                    }
                });
            }

        }
        catch (Exception a)

        {

        }




    }

    public static boolean storagepermission(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public void file_copy(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
        addtogallery(destFile);
    }

    public void addtogallery(File file ) {
        if(file.getName().endsWith(".mp4"))
        {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Video.Media.DATA, file.getAbsolutePath());
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4"); // or image/png
            getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        }
        else if(file.getName().endsWith(".gif"))
        {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/gif"); // or image/png
            getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
        else if(file.getName().endsWith(".jpg"))
        {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg"); // or image/png
            getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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
    @Override
    protected void onResume() {
        super.onResume();
        try
        {
            try
            {
                count=0;
                intcount=0;
                adView1.loadAd(adreq1);
                intestrial.loadAd(adreq);
            }
            catch (Exception a)
            {

            }
            if(db.get_whichwp().equalsIgnoreCase(""))
            {
                db.add_whichwp("1");
                title.setText("Whatsapp Status");
                whatsappB.setVisibility(View.VISIBLE);
                whatsapp.setVisibility(View.INVISIBLE);
            }
            data_loading();
        }
        catch(Exception a)
        {
        }
    }
}