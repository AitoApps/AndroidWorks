package com.down_mate;

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
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.File;

public class Noti_Reciver extends BroadcastReceiver {
    public static long limittime = System.currentTimeMillis();
    public BackEnd_DB db;
    DownloadManager downmanager;
    public Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        try
        {
            this.context =context;
            db=new BackEnd_DB(context);
           downmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            if (System.currentTimeMillis() > limittime) {
                 if(intent.getAction().equalsIgnoreCase("extra_click_download_ids"))
                 {
                     Intent i=new Intent(context, VideoDownloads.class);
                     i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     context.startActivity(i);
                 }
                 else if(intent.getAction().equalsIgnoreCase("android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED"))
                 {
                    Intent i=new Intent(context, VideoDownloads.class);
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
                             String path=db.get_Downid(downloaded_id+"");
                             String downname=db.get_downname(downloaded_id+"");
                             File f=new File(path.replace("file://",""));
                             String path1=path.replace(".","VG.");
                             File f1=new File(path1.replace("file://",""));
                             if(!path1.contains("VGVG."))
                             {
                                 f.renameTo(f1);
                                 addtoGallery(f1);
                                 db.update_downid(downloaded_id+"",path1);

                                 final RemoteViews contentView = new RemoteViews("com.down_mate", R.layout.downloads_list_custom);
                                 contentView.setTextViewText(R.id.viewtitle, "Download Complete");
                                 contentView.setTextViewText(R.id.title1, downname);
                                 if(downname.contains("FB"))
                                 {
                                     contentView.setImageViewResource(R.id.img,R.drawable.img_fbdown);
                                 }
                                 else if(downname.contains("INSTA"))
                                 {
                                     contentView.setImageViewResource(R.id.img,R.drawable.img_instadown);
                                 }
                                 else if(downname.contains("TIKS"))
                                 {
                                     contentView.setImageViewResource(R.id.img,R.drawable.img_tikdown);
                                 }
                                 else if(downname.contains("SC"))
                                 {
                                     contentView.setImageViewResource(R.id.img,R.drawable.img_sharechatdown);
                                 }
                                 else
                                 {
                                     contentView.setImageViewResource(R.id.img,R.drawable.img_applogo);
                                 }
                                 Intent intent1 = new Intent();
                                 intent1.setAction(Intent.ACTION_VIEW);
                                 intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                 Uri apkURI = FileProvider.getUriForFile(
                                         context.getApplicationContext(),
                                         "com.down_mate.provider", f1);
                                 String mimtype="";
                                 if(f1.getName().endsWith(".mp4"))
                                 {
                                     mimtype="video/*";
                                 }
                                 else if(f1.getName().endsWith(".jpg"))
                                 {
                                     mimtype="image/*";
                                 }
                                  intent1.setDataAndType(apkURI, mimtype);
                                 //intent1.setDataAndType(Uri.fromFile(f1),mimtype);
                                 intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                 final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_ONE_SHOT);


                                 if (Build.VERSION.SDK_INT < 26) {
                                     NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                                             .setSmallIcon(R.drawable.img_logosmall)
                                             .setContentIntent(pendingIntent)
                                             .setContent(contentView);

                                     Notification notification = mBuilder.build();
                                     notification.flags |= Notification.FLAG_AUTO_CANCEL;
                                     notification.defaults |= Notification.DEFAULT_SOUND;
                                     notification.defaults |= Notification.DEFAULT_VIBRATE;
                                     final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                     notificationManager.notify(Noti_Count.getID(), notification);
                                 }
                                 else
                                 {

                                     CharSequence name = "DownMate";
                                     String description = "DownMateNoti";
                                     int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                     NotificationChannel channel = new NotificationChannel("DownMatechannel", name, importance);
                                     channel.setDescription(description);
                                     NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                                     notificationManager.createNotificationChannel(channel);
                                     NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "DownMatechannel")
                                             .setSmallIcon(R.drawable.img_logosmall)
                                             .setContentIntent(pendingIntent)
                                             .setContent(contentView)
                                             .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                                     Notification notification = mBuilder.build();
                                     notification.flags |= Notification.FLAG_AUTO_CANCEL;
                                     notificationManager.notify(Noti_Count.getID(), notification);
                                 }

                             }

                         }
                     }
                     catch (Exception a)
                     {
                       Log.w("Errr",Log.getStackTraceString(a));
                     }
                 }
            }
            limittime = System.currentTimeMillis() + 1000;
        }
        catch (Exception a)
        {

        }
    }

    //This function is save image to gallery

    public void addtoGallery(File file ) {
        if(file.getName().endsWith(".jpg"))
        {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg"); // or image/png
            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
        else if(file.getName().endsWith(".mp4"))
        {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Video.Media.DATA, file.getAbsolutePath());
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4"); // or image/png
            context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        }
    }
}