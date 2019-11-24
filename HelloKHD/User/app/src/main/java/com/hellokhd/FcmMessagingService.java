package com.hellokhd;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build.VERSION;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat.Builder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class FcmMessagingService extends FirebaseMessagingService {
    public UserDatabaseHandler udb;

    public void onMessageReceived(RemoteMessage remoteMessage) {
        udb = new UserDatabaseHandler(this);
        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject jsonObject=new JSONObject(remoteMessage.getData().get("hellokhd"));
                String message=jsonObject.getString("salman");
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
                if (new JSONObject(json1.toString()).getString("notitype").equalsIgnoreCase("schoolresult")) {
                    try {
                       generate_SchoolWise(json1.getString("message"));
                    } catch (Exception e2) {
                    }
                }

                if (new JSONObject(json1.toString()).getString("notitype").equalsIgnoreCase("districresult")) {
                    try {
                        generate_DistricResult(json1.getString("message"));
                    } catch (Exception e2) {
                    }
                }

                if (new JSONObject(json1.toString()).getString("notitype").equalsIgnoreCase("news")) {
                    try {
                        generate_News(json1.getString("message"));
                    } catch (Exception e2) {
                    }
                }

                if (new JSONObject(json1.toString()).getString("notitype").equalsIgnoreCase("anouncment")) {
                    try {
                        generate_anouncement(json1.getString("message"));
                    } catch (Exception e2) {
                    }
                }

            } catch (Exception e3) {

            }

        }
    }

    public void generate_anouncement(String anouncement) {
        Intent intent = new Intent(this, Announcement_List.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, anouncement);
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NotificationID.getID(), notification);

        }
        else
        {
            String str = "HelloKHDChannel";
            NotificationChannel channel = new NotificationChannel(str, "HelloKHD_VER", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("HelloKHDNotification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NotificationID.getID(), notification2);
        }

    }

    public void generate_DistricResult(String anouncement) {
        Intent intent = new Intent(this, Distric_Wise_Result.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, anouncement);
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NotificationID.getID(), notification);

        }
        else
        {
            String str = "HelloKHDChannel";
            NotificationChannel channel = new NotificationChannel(str, "HelloKHD_VER", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("HelloKHDNotification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NotificationID.getID(), notification2);
        }

    }

    public void generate_SchoolWise(String anouncement) {
        Intent intent = new Intent(this, School_Wise_Result.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, anouncement);
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NotificationID.getID(), notification);

        }
        else
        {
            String str = "HelloKHDChannel";
            NotificationChannel channel = new NotificationChannel(str, "HelloKHD_VER", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("HelloKHDNotification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NotificationID.getID(), notification2);
        }

    }

    public void generate_News(String anouncement) {
        Intent intent = new Intent(this, News.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, anouncement);
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NotificationID.getID(), notification);

        }
        else
        {
            String str = "HelloKHDChannel";
            NotificationChannel channel = new NotificationChannel(str, "HelloKHD_VER", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("HelloKHDNotification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NotificationID.getID(), notification2);
        }

    }
}
