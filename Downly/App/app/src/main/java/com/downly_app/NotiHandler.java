package com.downly_app;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.authoring.Movie;
import com.downly_app.DefaultMp4Builder1;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class NotiHandler extends BroadcastReceiver {
    public static long nanotime = System.currentTimeMillis();
    public DataBase db;
    DownloadManager downmanager;
    public Context context;
    long finalVideoFileSize=0;
    public void onReceive(Context context, Intent intent) {
        Context context2 = context;
        try {
            this.context =context;
            db = new DataBase(context2);
            downmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            if (System.currentTimeMillis() > nanotime) {
                if(intent.getAction().equalsIgnoreCase("extra_click_download_ids"))
                {

                    Intent i=new Intent(context, DownloadsList.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
                else if(intent.getAction().equalsIgnoreCase("android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED"))
                {

                    Intent i=new Intent(context,DownloadsList.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
                else if(intent.getAction().equalsIgnoreCase("android.intent.action.DOWNLOAD_COMPLETE"))
                {
                    try
                    {
                    Bundle extras = intent.getExtras();
                    Long downloaded_id = extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID);
                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(downloaded_id);
                    Cursor cursor = downmanager.query(q);

                        if(cursor.moveToFirst())
                        {
                            String path=db.getdownid(downloaded_id+"").replace("file://","");
                            String downname=db.getdownname(downloaded_id+"");

                            File f=new File(path);

                            if(db.getaudioreq(downloaded_id+"").equalsIgnoreCase("yes"))
                            {
                                String ctime=db.getctime(downloaded_id+"");
                                if(downname.contains("m4a"))
                                {
                                    String tpath=path.replace("YT","YTB");
                                    File tf=new File(tpath);
                                    f.renameTo(tf);

                                    String downname1= Environment.getExternalStorageDirectory() + "/YoutubeDownloads/"+"YTB_" + ctime + ".mp4";
                                    File f1=new File( downname1);
                                    if(f1.exists())
                                    {
                                        mergeMp4(tpath,downname1,ctime);

                                    }
                                }
                                else if(downname.contains("mp4"))
                                {
                                    String tpath=path.replace("YT","YTB");
                                    File tf=new File(tpath);
                                    f.renameTo(tf);

                                    String downname1= Environment.getExternalStorageDirectory() + "/YoutubeDownloads/"+"YTB_" + ctime + ".m4a";
                                    File f1=new File( downname1);

                                    if(f1.exists())
                                    {
                                        mergeMp4(downname1,tpath,ctime);

                                    }
                                }
                            }
                            else
                            {

                                String path1=path.replace(".","VG.");
                                File f1=new File(path1.replace("file://",""));
                                if(!path1.contains("VGVG."))
                                {
                                    f.renameTo(f1);
                                    addtoGallery(f1);
                                    db.updatedownid(downloaded_id+"",path1);

                                    final RemoteViews contentView = new RemoteViews("com.downly_app", R.layout.custom_list_notidownloads);
                                    contentView.setTextViewText(R.id.title, "Download Complete");
                                    contentView.setTextViewText(R.id.title1, downname);

                                    if(downname.contains("FB"))
                                    {
                                        contentView.setImageViewResource(R.id.image1,R.drawable.new_fblogo);
                                    }
                                    else if(downname.contains("INSTA"))
                                    {
                                        contentView.setImageViewResource(R.id.image1,R.drawable.new_instagram);
                                    }
                                    else if(downname.contains("TIKS"))
                                    {
                                        contentView.setImageViewResource(R.id.image1,R.drawable.new_tiktok);
                                    }
                                    else if(downname.contains("SC"))
                                    {
                                        contentView.setImageViewResource(R.id.image1,R.drawable.new_sharechat);
                                    }
                                    else if(downname.contains("TW"))
                                    {
                                        contentView.setImageViewResource(R.id.image1,R.drawable.new_twitter);
                                    }
                                    else if(downname.contains("PIN"))
                                    {
                                        contentView.setImageViewResource(R.id.image1,R.drawable.new_pintrest);
                                    }
                                    else if(downname.contains("YT"))
                                    {
                                        contentView.setImageViewResource(R.id.image1,R.drawable.new_youtube);
                                    }
                                    else
                                    {
                                        contentView.setImageViewResource(R.id.image1,R.drawable.applogo);
                                    }

                                    Intent intent1 = new Intent();
                                    intent1.setAction(Intent.ACTION_VIEW);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Uri apkURI = FileProvider.getUriForFile(
                                            context.getApplicationContext(),
                                            "com.downly_app.provider", f1);
                                    String mimtype="";
                                    if(f1.getName().endsWith(".mp4") || f1.getName().endsWith(".MP4"))
                                    {
                                        mimtype="video/*";
                                    }
                                    else if(f1.getName().endsWith(".jpg") || f1.getName().endsWith(".jpeg") || f1.getName().endsWith(".png") || f1.getName().endsWith(".gif") || f1.getName().endsWith(".JPG") || f1.getName().endsWith(".JPEG") || f1.getName().endsWith(".PNG") || f1.getName().endsWith(".GIF"))
                                    {
                                        mimtype="image/*";
                                    }
                                    else if(f1.getName().endsWith(".mp3") || f1.getName().endsWith(".MP3"))
                                    {
                                        mimtype="audio/*";
                                    }
                                    intent1.setDataAndType(apkURI, mimtype);
                                    intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);


                                    if (Build.VERSION.SDK_INT < 26) {
                                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                                                .setSmallIcon(R.drawable.logo_small)
                                                .setContentIntent(pendingIntent)
                                                .setContent(contentView);

                                        Notification notification = mBuilder.build();
                                        notification.flags |= Notification.FLAG_AUTO_CANCEL;
                                        notification.defaults |= Notification.DEFAULT_SOUND;
                                        notification.defaults |= Notification.DEFAULT_VIBRATE;
                                        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                        notificationManager.notify(Notification_ID.getID(), notification);
                                    }
                                    else
                                    {

                                        CharSequence name = "Downly";
                                        String description = "DownlyNoti";
                                        int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                        NotificationChannel channel = new NotificationChannel("Downlychannel", name, importance);
                                        channel.setDescription(description);
                                        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                                        notificationManager.createNotificationChannel(channel);
                                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "Downlychannel")
                                                .setSmallIcon(R.drawable.logo_small)
                                                .setContentIntent(pendingIntent)
                                                .setContent(contentView)
                                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                                        Notification notification = mBuilder.build();
                                        notification.flags |= Notification.FLAG_AUTO_CANCEL;
                                        notificationManager.notify(Notification_ID.getID(), notification);
                                    }

                                }

                            }


                        }
                        else
                        {
                            try
                            {
                                String ctime=db.getctime(downloaded_id+"");
                                ArrayList<String> id1 = db.get_all_ctime(ctime);
                                String[] k = (String[]) id1.toArray(new String[id1.size()]);
                                if(k.length>0)
                                {
                                    for(int i=0;i<k.length;i++)
                                    {
                                        DownloadManager dm= (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
                                        dm.remove(Long.parseLong(k[i]));
                                        db.delete_downid(k[i]);

                                    }
                                }
                            }
                            catch (Exception a)
                            {

                                Log.w("Removed",Log.getStackTraceString(a));
                            }

                            Log.w("Removed",downloaded_id+"");
                        }
                    }
                    catch (Exception a)
                    {
                        //Log.w("Errrrr",Log.getStackTraceString(a));
                    }
                }
            }
            nanotime = System.currentTimeMillis() + 1000;
        } catch (Exception e2) {


        }
    }
    public void addtoGallery(File file ) {
        if(file.getName().endsWith(".jpg") || file.getName().endsWith(".JPG"))
        {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg"); // or image/png
            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
        else if(file.getName().endsWith(".jpeg") || file.getName().endsWith(".JPEG"))
        {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg"); // or image/png
            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
        else if(file.getName().endsWith(".png") || file.getName().endsWith(".PNG"))
        {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png"); // or image/png
            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
        else if(file.getName().endsWith(".mp4") || file.getName().endsWith(".MP4"))
        {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Video.Media.DATA, file.getAbsolutePath());
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4"); // or image/png
            context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        }
    }

    private void mergeMp4(String inFilePathAudio, String inFilePathVideo,String ctime) {

        shownotification("0","0","0");

        String path = inFilePathVideo.substring(0, inFilePathVideo.lastIndexOf("/"));
        try {
            Movie audio = MovieCreator.build(new FileDataSourceImpl(inFilePathAudio));
            Movie video = MovieCreator.build(new FileDataSourceImpl(inFilePathVideo));
            video.addTrack(audio.getTracks().get(0));

            Container out = new DefaultMp4Builder1(context,inFilePathVideo,ctime).build(video);

            long currentMillis = System.currentTimeMillis();
            FileOutputStream fos = new FileOutputStream(new File(path + "temp" + currentMillis + ".mp4"));
            out.writeContainer(fos.getChannel());
            fos.close();
            File inAudioFile = new File(inFilePathAudio);
            inAudioFile.delete();
            File inVideoFile = new File(inFilePathVideo);
            if (inVideoFile.delete()) {
                File tempOutFile = new File(path + "temp" + currentMillis + ".mp4");
                tempOutFile.renameTo(inVideoFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shownotification(String currentsize,String totalsize,String persentage)
    {
        final RemoteViews contentView = new RemoteViews("com.downly_app", R.layout.custom_list_notidownloads_special);
        contentView.setTextViewText(R.id.title, "Merging , Please wait...");
        if(currentsize.equalsIgnoreCase("0"))
        {
            contentView.setTextViewText(R.id.title1, "");
        }
        else
        {
            contentView.setTextViewText(R.id.title1, currentsize+" / "+totalsize+" , "+persentage);
        }

        Intent intent1 = new Intent();
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);

        if (Build.VERSION.SDK_INT < 26) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.logo_small)
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{0L})
                    .setSound(null)
                    .setContent(contentView);
            Notification notification = mBuilder.build();
            notification.flags = notification.flags
                    | Notification.FLAG_ONGOING_EVENT;
            final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1237, notification);
        }
        else
        {

            CharSequence name = "DownlyNew";
            String description = "DownlyNotiNew";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("DownlychannelNew", name, importance);
            channel.setDescription(description);
            channel.setVibrationPattern(new long[]{ 0 });
            channel.enableVibration(false);
            channel.setSound(null, null);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "DownlychannelNew")
                    .setSmallIcon(R.drawable.logo_small)
                    .setContentIntent(pendingIntent)
                    .setContent(contentView)
                    .setPriority(NotificationCompat.PRIORITY_LOW);

            Notification notification = mBuilder.build();
            notification.flags = notification.flags
                    | Notification.FLAG_ONGOING_EVENT;
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(1237, notification);
        }

    }
}
