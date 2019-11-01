package com.downly_app;

import Downly_Data.Feed_WP;
import Downly_adapter.Adapter_Whatsapplist;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Media;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import de.mateware.snacky.Snacky;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Status_WP extends AppCompatActivity {
    public static String DIRECTORY_TO_SAVE_MEDIA_NOW = "/WhatStatus/";
    private static final String WHATSAPPBUS_STATUSES_LOCATION = "/WhatsApp Business/Media/.Statuses";
    private static final String WHATSAPP_STATUSES_LOCATION = "/WhatsApp/Media/.Statuses";
    AdRequest adRequest;
    AdRequest adRequest1;
    public AdView adView1;
    ImageView back;
    InternetConncetivity cd;
    int count = 0;
    final DataBase db = new DataBase(this);
    Typeface face;
    private List<Feed_WP> feedItems;
    int intcount = 0;
    public InterstitialAd interstitial;
    ListView list;
    private Adapter_Whatsapplist listAdapter;
    TextView nodata;
    ProgressBar pb;
    ProgressDialog pd;
    TextView title;
    ImageView whatsapp;
    ImageView whatsappbus;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_status_wp);
        try {
            title = (TextView) findViewById(R.id.title);
            list = (ListView) findViewById(R.id.list);
            cd = new InternetConncetivity(this);
            pd = new ProgressDialog(this);
            whatsapp = (ImageView) findViewById(R.id.whatsapp);
            adView1 = (AdView) findViewById(R.id.adView1);
            back = (ImageView) findViewById(R.id.back);
            nodata = (TextView) findViewById(R.id.nodownloads);
            whatsappbus = (ImageView) findViewById(R.id.whatsappbus);
            pb = (ProgressBar) findViewById(R.id.pb);
            feedItems = new ArrayList();
            listAdapter = new Adapter_Whatsapplist(this, feedItems);
            list.setAdapter(listAdapter);
            face = Typeface.createFromAsset(getAssets(), "commonfont.otf");
            title.setTypeface(face);
            nodata.setTypeface(face);
            back.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            interstitial = new InterstitialAd(this);
            interstitial.setAdUnitId("ca-app-pub-8933294539595122/5629841256");
            adRequest = new Builder().build();
            adRequest1 = new Builder().build();
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


            if (db.get_WpWhich().equalsIgnoreCase("2")) {
                whatsappbus.setVisibility(View.INVISIBLE);
                whatsapp.setVisibility(View.VISIBLE);
                title.setText("Whatsapp Business Status");
            } else if (db.get_WpWhich().equalsIgnoreCase("1")) {
                whatsappbus.setVisibility(View.VISIBLE);
                whatsapp.setVisibility(View.INVISIBLE);
                title.setText("Whatsapp Status");
            }
            whatsappbus.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    db.add_WpWhich("2");
                    loaddata();
                    title.setText("Whatsapp Business Status");
                    whatsappbus.setVisibility(View.INVISIBLE);
                    whatsapp.setVisibility(View.VISIBLE);
                }
            });
            whatsapp.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    db.add_WpWhich("1");
                    title.setText("Whatsapp Status");
                    whatsappbus.setVisibility(View.VISIBLE);
                    whatsapp.setVisibility(View.INVISIBLE);
                    loaddata();
                }
            });
        } catch (Exception e2) {
        }
    }

    public void loaddata() {
        try {
            list.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
            feedItems.clear();
            File f = null;
            if (db.get_WpWhich().equalsIgnoreCase("1")) {
                f=new File(Environment.getExternalStorageDirectory().toString()+WHATSAPP_STATUSES_LOCATION);
            } else if (db.get_WpWhich().equalsIgnoreCase("2")) {

                f = new File(Environment.getExternalStorageDirectory().toString()+WHATSAPPBUS_STATUSES_LOCATION);

            }
            File[] files = f.listFiles();
            Arrays.sort(files, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return (f2.lastModified() > f1.lastModified() ? 1 : (f2.lastModified() == f1.lastModified() ? 0 : -1));
                }
            });
            boolean z = false;
            for (File file : files) {
                if (file.getName().endsWith(".jpg") || file.getName().endsWith(".gif") || file.getName().endsWith(".mp4")) {
                    z = true;
                    Feed_WP item = new Feed_WP();
                    item.setFilepath(file.getAbsolutePath());
                    feedItems.add(item);
                }

            }
            if (z) {
                pb.setVisibility(View.GONE);
                list.setVisibility(View.VISIBLE);
                listAdapter.notifyDataSetChanged();
            }
            else
            {
                pb.setVisibility(View.GONE);
                list.setVisibility(View.GONE);
                listAdapter.notifyDataSetChanged();
                nodata.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            list.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
        }
    }

    public void savevideo(final File sourceFile) {
        try {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != 0) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != 0) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            File file = sourceFile;
                            copyFile(file, new File(Environment.getExternalStorageDirectory().toString()+DIRECTORY_TO_SAVE_MEDIA_NOW+sourceFile.getName()));
                            Snacky.builder().setActivty(Status_WP.this).setText((CharSequence) "Saved to Gallery").success().show();
                        } catch (Exception e) {
                            Snacky.builder().setActivty(Status_WP.this).setText((CharSequence) "Unable to Save").error().show();
                        }
                    }
                });
            }
        } catch (Exception e) {
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        try {
            count = 0;
            intcount = 0;
            adView1.loadAd(adRequest);
            interstitial.loadAd(adRequest1);
        } catch (Exception e) {
        }
        try {
            if (db.get_WpWhich().equalsIgnoreCase("")) {
                db.add_WpWhich("1");
                title.setText("Whatsapp Status");
                whatsappbus.setVisibility(View.VISIBLE);
                whatsapp.setVisibility(View.INVISIBLE);
            }
            loaddata();
        } catch (Exception e2) {
        }
    }
   @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    public void copyFile(File sourceFile, File destFile) throws IOException {
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
            addImageGallery(destFile);
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    public void addImageGallery(File file) {
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

    public void share_to_WhatsApp(File f) {
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
            try
            {
                Uri uri = FileProvider.getUriForFile(
                        getApplicationContext(),
                        this.getApplicationContext()
                                .getPackageName() + ".provider", f);

                Intent videoshare = new Intent(Intent.ACTION_SEND);
                videoshare.setType("*/*");
                videoshare.setPackage("com.whatsapp.w4b");
                videoshare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                videoshare.putExtra(Intent.EXTRA_STREAM,uri);
                startActivity(videoshare);
            }
            catch (Exception r)
            {
                Snacky.builder().setActivty(Status_WP.this).setText((CharSequence) "Please Install Whatsapp").success().show();

            }
        }
    }
}
