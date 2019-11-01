package com.chintha_admin;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class FcmMessagingService extends FirebaseMessagingService {
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            try {
                String message = new JSONObject((String) remoteMessage.getData().get("chinthakal")).getString("salmanstatus");
                JSONObject json1 = new JSONObject();
                try {
                    String[] s = message.split(":%");
                    int i = 0;
                    while (i < s.length) {
                        int a = i;
                        int i2 = i + 1;
                        json1.put(s[a], s[i2]);
                        i = i2 + 1;
                    }
                } catch (JSONException e) {
                }
                JSONObject json = new JSONObject(json1.toString());
                if (json.getString("notitype").equalsIgnoreCase("status_report")) {
                    try {
                        generatereport();
                    } catch (Exception e2) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("status_video")) {
                    try {
                        generatenewvideo();
                    } catch (Exception e3) {
                    }
                } else if (json.getString("notitype").equalsIgnoreCase("instamedia")) {
                    try {
                        instavideo();
                    } catch (Exception e4) {
                    }
                } else if (json.getString("notitype").equalsIgnoreCase("newmediaforupload")) {
                    try {
                        generatemediaupload();
                    } catch (Exception e5) {
                    }
                } else if (!json.getString("notitype").equalsIgnoreCase("status_publike")) {
                    if (json.getString("notitype").equalsIgnoreCase("mk_recharge")) {
                        try {
                            generate_ecrecharge(json.getString("title"));
                        } catch (Exception e7) {
                        }
                    } else if (json.getString("notitype").equalsIgnoreCase("mk_rechargenew")) {
                        try {
                            generate_ecrecharge1(json.getString("title"));
                        } catch (Exception e8) {
                        }
                    }
                }
            } catch (Exception e11) {
            }
        }
    }

    public void generate_ecrecharge(String title){
        Intent intent = new Intent(this,MK_EASY_RECHARGE_NEW.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Tempvariable.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, title);


        if (Build.VERSION.SDK_INT < 26) {
            Notification notification = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(1, notification);

        }
        else
        {
            String str = "SAChannel";
            NotificationChannel channel = new NotificationChannel(str, "StatusAdmin", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("SA_Notification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new NotificationCompat.Builder(this, str).setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(1, notification2);
        }

    }

    public void generate_ecrecharge1(String title){
        Intent intent = new Intent(this,MK_EASY_RECHARGE_NEW.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Tempvariable.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, title);


        if (Build.VERSION.SDK_INT < 26) {
            Notification notification = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(6, notification);

        }
        else
        {
            String str = "SAChannel";
            NotificationChannel channel = new NotificationChannel(str, "StatusAdmin", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("SA_Notification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new NotificationCompat.Builder(this, str).setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(6, notification2);
        }

    }

    public void generatenewvideo(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Tempvariable.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "New Video For Uploading");


        if (Build.VERSION.SDK_INT < 26) {
            Notification notification = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(5, notification);

        }
        else
        {
            String str = "SAChannel";
            NotificationChannel channel = new NotificationChannel(str, "StatusAdmin", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("SA_Notification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new NotificationCompat.Builder(this, str).setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(5, notification2);
        }

    }


    public void generatemediaupload(){
        Intent intent = new Intent(this, Status_Media_Upload.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Tempvariable.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "New Media");


        if (Build.VERSION.SDK_INT < 26) {
            Notification notification = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(4, notification);

        }
        else
        {
            String str = "SAChannel";
            NotificationChannel channel = new NotificationChannel(str, "StatusAdmin", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("SA_Notification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new NotificationCompat.Builder(this, str).setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(4, notification2);
        }

    }


    public void instavideo(){
        Intent intent = new Intent(this, InstagramToFacebook.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Tempvariable.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "New Instagram Media");


        if (Build.VERSION.SDK_INT < 26) {
            Notification notification = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(3, notification);

        }
        else
        {
            String str = "SAChannel";
            NotificationChannel channel = new NotificationChannel(str, "StatusAdmin", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("SA_Notification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new NotificationCompat.Builder(this, str).setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(3, notification2);
        }

    }
    public void generatereport(){
        Intent intent = new Intent(this, Report_Status.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Tempvariable.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "New Report");


        if (Build.VERSION.SDK_INT < 26) {
            Notification notification = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(2, notification);

        }
        else
        {
            String str = "SAChannel";
            NotificationChannel channel = new NotificationChannel(str, "StatusAdmin", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("SA_Notification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new NotificationCompat.Builder(this, str).setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(2, notification2);
        }

    }



}
