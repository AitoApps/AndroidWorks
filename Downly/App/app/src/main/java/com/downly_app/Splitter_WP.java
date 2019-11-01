package com.downly_app;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaMuxer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Downly_Data.Feed_CutVideoList;
import Downly_adapter.Adapter_Cutvideo;
import es.dmoral.toasty.Toasty;

public class Splitter_WP extends AppCompatActivity {
    String[] PERMISSIONS = {
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE
    };
    int count;
    String sr_path;
    boolean isfinish;
    ProgressDialog pd;
    String tr_srcpath;
    String tr_dstpath;
    int tr_startMs;
    int tr_endMMS;
    long total_time =0;
    ListView listview;
    List<String> durationlist = new ArrayList<String>();
    ImageView back;
    Typeface face;
    TextView title;
    int PERMISSION_ALL = 1;
    List<Feed_CutVideoList> feedItems;
    Adapter_Cutvideo listAdapter;
    public String extension="";
    ImageView emptydata,save,share;
    RelativeLayout sharetools;
    Button addvideo;
    public AdView adView1;
    AdRequest adreq1;
    AdRequest adreq;
    private InterstitialAd intestrial;
    int adcount=0;
    int intcount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splitter_activity);
        pd=new ProgressDialog(this);
        face= Typeface.createFromAsset(getAssets(), "commonfont.otf");
        title=findViewById(R.id.viewtitle);
        listview =findViewById(R.id.listview);
        emptydata =findViewById(R.id.emptydownloads);
        back=findViewById(R.id.back);
        addvideo=findViewById(R.id.addvideo);
        sharetools=findViewById(R.id.sharetools);
        save=findViewById(R.id.save);
        share=findViewById(R.id.share);
        adView1=findViewById(R.id.adView1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        title.setTypeface(face);
        feedItems=new ArrayList<Feed_CutVideoList>();
        listAdapter=new Adapter_Cutvideo(this, feedItems);
        listview.setAdapter(listAdapter);

        intestrial = new InterstitialAd(Splitter_WP.this);
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
                        if(adcount<=10)
                        {
                            adView1.loadAd(adreq1);
                            adcount++;
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
        emptydata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!storagepermission(Splitter_WP.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(Splitter_WP.this, PERMISSIONS, PERMISSION_ALL);
                }
                else {
                    File folder2 = new File(Environment.getExternalStorageDirectory() + "/" + Temp.Defualtfolder);
                    if (!folder2.exists()) {
                        folder2.mkdir();
                    }
                    File folder3 = new File(Environment.getExternalStorageDirectory() + "/" + Temp.Defualtfolder + "/Splitted_Video");
                    if (!folder3.exists()) {
                        folder3.mkdir();
                        File f2 = new File(Environment.getExternalStorageDirectory() + "/" + Temp.Defualtfolder + "/Splitted_Video/" + ".nomedia");
                        try {
                            f2.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    File folder4 = new File(Environment.getExternalStorageDirectory() + "/" +Temp.Defualtfolder + "/Splitted_Gallery");
                    if (!folder4.exists()) {
                        folder4.mkdir();

                    }
                    total_time = 0;
                    extension = "";
                    emptydata.setVisibility(View.VISIBLE);
                    listview.setVisibility(View.GONE);
                    sharetools.setVisibility(View.GONE);
                    choosevideo();
                }
            }
        });


        addvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!storagepermission(Splitter_WP.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(Splitter_WP.this, PERMISSIONS, PERMISSION_ALL);
                }
                else
                {
                    File folder2=new File(Environment.getExternalStorageDirectory()+"/"+Temp.Defualtfolder);
                    if(!folder2.exists())
                    {
                        folder2.mkdir();


                    }

                    File folder3=new File(Environment.getExternalStorageDirectory()+"/"+ Temp.Defualtfolder +"/Splitted_Video");
                    if(!folder3.exists())
                    {
                        folder3.mkdir();
                        File f2 = new File(Environment.getExternalStorageDirectory() + "/"+ Temp.Defualtfolder +"/Splitted_Video/" + ".nomedia");
                        try {
                            f2.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    File folder4=new File(Environment.getExternalStorageDirectory()+"/"+ Temp.Defualtfolder+"/Splitted_Gallery");
                    if(!folder4.exists())
                    {
                        folder4.mkdir();

                    }
                    total_time =0;
                    extension="";
                    emptydata.setVisibility(View.VISIBLE);
                    listview.setVisibility(View.GONE);
                    sharetools.setVisibility(View.GONE);
                    choosevideo();
                }

            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    savevido();
                                    dialog.dismiss();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(Splitter_WP.this);
                    builder.setMessage("Are you sure want to save these videos ?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
                catch (Exception a)
                {
                    Toasty.error(getApplicationContext(), "Sorry ! Unable to Save", Toast.LENGTH_SHORT).show();
                }


            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<Uri> files = new ArrayList<Uri>();
                            for (int i = 0; i < listview.getCount(); i++) {
                                View v = listview.getChildAt(i);
                                CheckBox chk=v.findViewById(R.id.check);
                                TextView fp=v.findViewById(R.id.filepath);
                                if(chk.isChecked())
                                {
                                    try
                                    {
                                        File f=new File(fp.getText().toString());

                                        Uri uri = Uri.fromFile(f);
                                        files.add(uri);


                                    }
                                    catch (Exception a)
                                    {

                                    }


                                }
                            }
                            if(isPackageExisted("com.whatsapp")&& isPackageExisted("com.whatsapp.w4b")&& isPackageExisted("com.img_whatsapp_gb"))
                            {
                                showshare_wp(files,1);
                            }
                            else if(isPackageExisted("com.whatsapp")&& isPackageExisted("com.whatsapp.w4b"))
                            {
                                showshare_wp(files,0);
                            }
                            else if(isPackageExisted("com.whatsapp"))
                            {
                                onClickApp("com.whatsapp",files);
                            }
                            else if(isPackageExisted("com.whatsapp.w4b"))
                            {
                                onClickApp("com.whatsapp.w4b",files);
                            }
                            else if(isPackageExisted("com.img_whatsapp_gb"))
                            {
                                onClickApp("com.img_whatsapp_gb",files);
                            }
                            else
                            {
                                Toasty.error(getApplicationContext(), "Please install Whatsapp app", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                catch (Exception a)
                {
                    Toasty.error(getApplicationContext(), "Sorry ! Unable to Share", Toast.LENGTH_SHORT).show();

                }




            }
        });

    }

    @Override
    public void onBackPressed() {
        if(intestrial.isLoaded())
        {
            intestrial.show();
        }

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        try
        {
            adcount=0;
            intcount=0;
            adView1.loadAd(adreq1);
            intestrial.loadAd(adreq);
        }
        catch (Exception a)
        {

        }
        super.onResume();
    }

    public boolean isPackageExisted(String targetPackage){
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }

    public void showshare_wp(ArrayList<Uri> files, int fount)
    {
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.wpsharedata);

        final RelativeLayout lytwhatsapp=dialog.findViewById(R.id.wp_layout);
        final RelativeLayout lytwhatsappbus=dialog.findViewById(R.id.wpbus_layout);
        final TextView txtwhatsapp=dialog.findViewById(R.id.wp_txt);
        final TextView txtwhatsappbus=dialog.findViewById(R.id.wpbus_txt);

        final RelativeLayout lytgbwhatsappbus=dialog.findViewById(R.id.lytgbwhatsappbus);
        final TextView txtgbwhatsappbus=dialog.findViewById(R.id.txtgbwhatsappbus);

        txtwhatsapp.setText("Whatsapp");
        txtwhatsappbus.setText("Whatsapp Business");
        txtgbwhatsappbus.setText("GB Whatsapp");

        txtwhatsapp.setTypeface(face);
        txtwhatsappbus.setTypeface(face);
        txtgbwhatsappbus.setTypeface(face);


        if(fount==0)
        {
            lytgbwhatsappbus.setVisibility(View.GONE);
        }
        else if(fount==1){
            lytgbwhatsappbus.setVisibility(View.VISIBLE);
        }

        lytwhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickApp("com.whatsapp",files);
                dialog.dismiss();
            }
        });



        lytwhatsappbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickApp("com.whatsapp.w4b",files);
                dialog.dismiss();
            }
        });

        lytgbwhatsappbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickApp("com.img_whatsapp_gb",files);
                dialog.dismiss();
            }
        });


        dialog.show();
    }
    public void onClickApp(String pack, ArrayList<Uri> files) {

        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("video/*");
            waIntent.setPackage(pack);
            waIntent.putExtra(Intent.EXTRA_STREAM, files);
            startActivity(Intent.createChooser(waIntent, "Share with"));
        } catch (Exception e) {
            if(pack.equalsIgnoreCase("com.whatsapp"))
            {
                Toasty.error(getApplicationContext(), "Please install Whatsapp app", Toast.LENGTH_SHORT).show();
            }
            else if(pack.equalsIgnoreCase("com.whatsapp.w4b"))
            {
                Toasty.error(getApplicationContext(), "Please install Whatsapp Business app", Toast.LENGTH_SHORT).show();
            }
            else if(pack.equalsIgnoreCase("com.img_whatsapp_gb"))
            {
                Toasty.error(getApplicationContext(), "Please install GB Whatsapp", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void savevido()
    {
        try
        {
            pd.setMessage("Saving videos...");
            pd.setCancelable(false);
            pd.show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String paths="";
                    for (int i = 0; i < listview.getCount(); i++) {
                        View v = listview.getChildAt(i);
                        CheckBox chk=v.findViewById(R.id.check);
                        TextView fp=v.findViewById(R.id.filepath);
                        if(chk.isChecked())
                        {
                            try
                            {
                                File f=new File(fp.getText().toString());
                                File f1=new File(Environment.getExternalStorageDirectory()+"/"+ Temp.Defualtfolder +"/Splitted_Gallery/"+ System.currentTimeMillis()+"S"+(i+1)+extension);

                                copyfile(f,f1);
                            }
                            catch (Exception a)
                            {

                            }
                       }
                    }
                    pd.dismiss();
                    Toasty.success(getApplicationContext(),"Saved to gallery", Toast.LENGTH_SHORT).show();

                }
            });
        }
        catch (Exception a)
        {
            Toasty.success(getApplicationContext(),"Sorry ! Unable to Save", Toast.LENGTH_SHORT).show();

        }

    }
    public void copyfile(File src, File dst) throws IOException {
        FileInputStream inStream = new FileInputStream(src);
        FileOutputStream outStream = new FileOutputStream(dst);
        FileChannel outChannel = outStream.getChannel();
        FileChannel inChannel = inStream.getChannel();

        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
        addtogallery(dst);

    }

    public void addtogallery(File file)
    {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Video.Media.DATA, file.getAbsolutePath());
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/*"); // or image/png
        getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
    }

    public void choosevideo()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("video/*");
                startActivityForResult(pickIntent,1);
                pd.setMessage("Loading Video...");
                pd.setCancelable(true);
                pd.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            try
            {
                pd.dismiss();
                Uri path = data.getData();

                sr_path = generatePath(path,getApplicationContext());
                extension= sr_path.substring(sr_path.lastIndexOf("."));


                pd.setMessage("Splitting...Please wait");
                pd.setCancelable(false);
                pd.show();

                new splitvideos().execute();
            }
            catch (Exception a)
            {
                Toasty.error(getApplicationContext(), "Sorry ! Unable to Split", Toast.LENGTH_SHORT, true).show();
            }


        }
    }

    public String generatePath(Uri uri,Context context) {
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

    public class splitvideos extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {

        }
        @Override
        protected String doInBackground(String... arg0) {

            try {

                if(total_time ==0)
                {

                    File f=new File(sr_path);
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(getApplicationContext(), Uri.fromFile(f));
                    String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    long timeInMillisec = Long.parseLong(time);
                    retriever.release();
                    total_time =timeInMillisec;
                    count=1;
                    isfinish=false;
                    durationlist.clear();

                }

                int start=(count-1)*30000;
                int end=0;

                if(count*30000< total_time)
                {
                    end=count*30000;
                    isfinish = false;
                }
                else {
                    end = (int) total_time;
                    isfinish = true;

                }


                String destpath= Environment.getExternalStorageDirectory()+"/"+ Temp.Defualtfolder +"/Splitted_Video/"+count+extension;
                count=count+1;

                tr_srcpath = sr_path;
                tr_dstpath =destpath;
                tr_startMs =start;
                tr_endMMS =end;
                durationlist.add(millisecToTime(start)+" - "+millisecToTime(end));



                MediaExtractor extractor = new MediaExtractor();
                File file = new File(tr_srcpath);
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    FileDescriptor fd = fis.getFD();
                    extractor.setDataSource(fd);
                } catch (Exception e) {




                }
                int trackCount = extractor.getTrackCount();
                MediaMuxer muxer;
                muxer = new MediaMuxer(tr_dstpath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                HashMap<Integer, Integer> indexMap = new HashMap<>(trackCount);
                int bufferSize = -1;
                for (int i = 0; i < trackCount; i++) {
                    MediaFormat format = extractor.getTrackFormat(i);
                    String mime = format.getString(MediaFormat.KEY_MIME);
                    boolean selectCurrentTrack = false;
                    if (mime.startsWith("audio/") && true) {
                        selectCurrentTrack = true;
                    } else if (mime.startsWith("video/") && true) {
                        selectCurrentTrack = true;
                    }
                    if (selectCurrentTrack) {
                        extractor.selectTrack(i);
                        int dstIndex = muxer.addTrack(format);
                        indexMap.put(i, dstIndex);
                        if (format.containsKey(MediaFormat.KEY_MAX_INPUT_SIZE)) {
                            int newSize = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
                            bufferSize = newSize > bufferSize ? newSize : bufferSize;
                        }
                    }
                    else
                    {

                    }
                }
                if (bufferSize < 0) {
                    bufferSize = 1024;
                }
                MediaMetadataRetriever retrieverSrc = new MediaMetadataRetriever();
                retrieverSrc.setDataSource(tr_srcpath);
                String degreesString = retrieverSrc.extractMetadata(
                        MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
                if (degreesString != null) {
                    int degrees = Integer.parseInt(degreesString);
                    if (degrees >= 0) {
                        muxer.setOrientationHint(degrees);
                    }
                }
                if (tr_startMs > 0) {
                    extractor.seekTo(tr_startMs * 1000, MediaExtractor.SEEK_TO_CLOSEST_SYNC);
                }
                int offset = 0;
                int trackIndex = -1;
                ByteBuffer dstBuf = ByteBuffer.allocate(bufferSize);
                MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

                muxer.start();
                while (true) {
                    bufferInfo.offset = offset;
                    bufferInfo.size = extractor.readSampleData(dstBuf, offset);
                    if (bufferInfo.size < 0) {
                        bufferInfo.size = 0;
                        break;
                    } else {
                        bufferInfo.presentationTimeUs = extractor.getSampleTime();
                        if (tr_endMMS > 0 && bufferInfo.presentationTimeUs > (tr_endMMS * 1000)) {
                            break;
                        } else {
                            bufferInfo.flags = extractor.getSampleFlags();
                            trackIndex = extractor.getSampleTrackIndex();
                            muxer.writeSampleData(indexMap.get(trackIndex), dstBuf,
                                    bufferInfo);
                            extractor.advance();
                        }
                    }
                }
                muxer.stop();
                muxer.release();

                return null;
            } catch (Exception e) {
                return new String(Log.getStackTraceString(e));
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {


                if(!isfinish)
                {
                    new splitvideos().execute();
                }
                else {
                    pd.dismiss();
                    if(count>=1)
                    {
                        loadtrimvidlist(count);
                    }
                    else
                    {
                        emptydata.setVisibility(View.VISIBLE);
                        listview.setVisibility(View.GONE);
                        sharetools.setVisibility(View.GONE);
                    }

                }


            } catch (Exception a) {
                Toasty.error(getApplicationContext(), "Unable to Split", Toast.LENGTH_SHORT, true).show();

            }

        }

    }
    public void loadtrimvidlist(int count)
    {
        feedItems.clear();
        listAdapter.notifyDataSetChanged();

        for(int i=1;i<count;i++)
        {

            Feed_CutVideoList item=new Feed_CutVideoList();
            item.setTitle("Part-"+i);
            item.setFilepath(Environment.getExternalStorageDirectory()+"/"+ Temp.Defualtfolder +"/Splitted_Video/"+i+extension);
            item.setDuration(durationlist.get(i-1));
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(Environment.getExternalStorageDirectory() + "/"+ Temp.Defualtfolder +"/Splitted_Video/"+i+extension, MediaStore.Images.Thumbnails.MINI_KIND);
            item.setBmp(thumb);
            feedItems.add(item);


        }
        emptydata.setVisibility(View.GONE);
        listview.setVisibility(View.VISIBLE);
        sharetools.setVisibility(View.VISIBLE);
        listAdapter.notifyDataSetChanged();
    }

    String millisecToTime(int millisec) {
        int sec = millisec/1000;
        int second = sec % 60;
        int minute = sec / 60;
        if (minute >= 60) {
            int hour = minute / 60;
            minute %= 60;
            return hour + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second);
        }
        return minute + ":" + (second < 10 ? "0" + second : second);
    }

    public void openfile(String path)
    {
        try {

            File f=new File(path);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri apkURI = FileProvider.getUriForFile(
                    getApplicationContext(),
                    this.getApplicationContext()
                            .getPackageName() + ".provider", f);
            intent.setDataAndType(apkURI,"video/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (Exception e) {
            Toasty.error(getApplicationContext(), "Sorry ! Unable to play", Toast.LENGTH_SHORT, true).show();

        }
    }

    public void SharetoApp_1(String pack, String msg) {
        PackageManager pm = getPackageManager();
        try {
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
}

