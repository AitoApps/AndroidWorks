package com.downly_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import Downly_Data.Feed_MP3;
import Downly_adapter.MP3ConvertedList_Adapter;
import es.dmoral.toasty.Toasty;

public class Video_to_Mp3 extends AppCompatActivity {
    private static final String MP3LOCATION = "/MP3Converts";

    int PERMISSION = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_NETWORK_STATE
    };
    public String mediapath="";
    ImageView back;
    TextView nodata;
    TextView title;
    ListView list;
    ProgressDialog pd;
    Typeface face;
    private MP3ConvertedList_Adapter listAdapter;
    private List<Feed_MP3> feedItems;
    ProgressBar pb;
    InternetConncetivity cd;
    Button convert;
    public InterstitialAd interstitial;
    AdRequest adRequest;
    AdRequest adRequest1;
    public AdView adView1;
    int count = 0;
    int intcount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_to__mp3);
        try
        {
            adView1 = (AdView) findViewById(R.id.adView1);
            convert=findViewById(R.id.convert);
            title=(TextView)findViewById(R.id.viewtitle);
            list=(ListView)findViewById(R.id.listview);
            cd=new InternetConncetivity(this);
            pd=new ProgressDialog(this);
            back=(ImageView)findViewById(R.id.back);
            nodata=findViewById(R.id.emptydownloads);
            pb=(ProgressBar)findViewById(R.id.pb);
            feedItems = new ArrayList<Feed_MP3>();
            listAdapter = new MP3ConvertedList_Adapter(this, feedItems);
            list.setAdapter(listAdapter);
            face= Typeface.createFromAsset(getAssets(), "commonfont.otf");
            title.setTypeface(face);
            nodata.setTypeface(face);

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


            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            convert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!storagepermitted(Video_to_Mp3.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Video_to_Mp3.this, PERMISSIONS, PERMISSION);
                    }
                    else
                    {
                        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pickIntent.setType("video/*");
                        startActivityForResult(pickIntent,1);
                    }
                }
            });
        }
        catch (Exception a)
        {
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==1 && resultCode == RESULT_OK) {
            try
            {
                Uri path = data.getData();
                mediapath = generatePath(path,getApplicationContext());
                convertvideo(mediapath);
            }
            catch (Exception a)
            {
            }
        }
    }
    public String generatePath(Uri uri, Context context) {
        String filePath = null;
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if(isKitKat){
            filePath = generateFromKitkat(uri,context);
        }

        if(filePath != null){
            return filePath;
        }

        Cursor cursor = context.getContentResolver().query(uri, new String[] { MediaStore.MediaColumns.DATA }, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return filePath == null ? uri.getPath() : filePath;
    }
    @TargetApi(19)
    private String generateFromKitkat(Uri uri,Context context){
        String filePath = null;
        if(DocumentsContract.isDocumentUri(context, uri)){
            String wholeID = DocumentsContract.getDocumentId(uri);
            String id = wholeID.split(":")[1];
            String[] column = { MediaStore.Video.Media.DATA };
            String sel = MediaStore.Video.Media._ID + "=?";
            Cursor cursor = context.getContentResolver().
                    query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{ id }, null);
            int columnIndex = cursor.getColumnIndex(column[0]);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return filePath;
    }


    @Override
    protected void onResume() {
        super.onResume();
        try
        {
            try {
                count = 0;
                intcount = 0;
                adView1.loadAd(adRequest);
                interstitial.loadAd(adRequest1);
            } catch (Exception e) {
            }

            loaddata();
        }
        catch(Exception a)
        {
        }
    }
    public void convertvideo(String path)
    {
        try
        {
            File tikfolder = new File(Environment.getExternalStorageDirectory() + "/MP3Converts");
            if (!tikfolder.exists()) {
                tikfolder.mkdir();
            }
            String filename=path.substring(path.lastIndexOf("/")+1);
            String fi=filename.substring(0, filename.lastIndexOf("."));
            File f = new File(path);
            File f1 = new File(Environment.getExternalStorageDirectory() + "/MP3Converts/"+fi+".mp3");

            pd.setMessage("Converting...");
            pd.setCancelable(false);
            pd.show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        new AudioExtractor().genVideoUsingMuxer(f.getAbsolutePath(), f1.getAbsolutePath(), -1, -1, true, false);
                        pd.dismiss();
                        loaddata();
                    }
                    catch (Exception a)
                    {
                        pd.dismiss();
                        Toasty.info(getApplicationContext(),"Sorry ! Unable to convert", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        catch (Exception a)
        {
            Toasty.info(getApplicationContext(),"Sorry ! Unable to convert",Toast.LENGTH_SHORT).show();
        }


    }

    public void loaddata()
    {
        try
        {

            list.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
            feedItems.clear();
            File f=new File(Environment.getExternalStorageDirectory().toString()+MP3LOCATION);

            File[] files= f.listFiles();
            Arrays.sort(files, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return Long.compare(f2.lastModified(),f1.lastModified());
                }
            });

            int i=0;
            for (File file : files) {

                if (file.getName().endsWith(".mp3")) {
                    i=1;
                    Feed_MP3 item=new Feed_MP3();
                    item.settitle(file.getName());
                    item.setfpath(file.getAbsolutePath());
                    feedItems.add(item);
                }
            }
            if(i==1)
            {
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
        }
        catch (Exception a)
        {

            list.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
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


    public void playmusic(String path)
    {
        try
        {
            File f=new File(path);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri apkURI = FileProvider.getUriForFile(
                    getApplicationContext(),
                    this.getApplicationContext()
                            .getPackageName() + ".provider", f);
            intent.setDataAndType(apkURI,"audio/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);

        }
        catch (Exception a)
        {
            // Toasty.info(getApplicationContext(),Log.getStackTraceString(a),Toast.LENGTH_LONG).show();
        }

    }

    public void audioshare(String path) {
        try{
            File f= new File(path);
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("audio/*");
            Uri uri = FileProvider.getUriForFile(this, "com.downly_app.provider",f);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(shareIntent, "Share"));

        }
        catch (Exception a)
        {
           // Toasty.info(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
        }
    }

}
