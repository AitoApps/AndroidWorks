package com.footbale_admin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
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
                String message = new JSONObject((String) remoteMessage.getData().get("football")).getString("salman");
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
                if (json.getString("notitype").equalsIgnoreCase("instamediafoot")) {
                    try {
                        generatemediaupload();
                    } catch (Exception e5) {
                    }
                }
            } catch (Exception e11) {
            }
        }
    }


    public void generatemediaupload(){
        Intent intent = new Intent(this, InstagramToFacebook.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Tempvariable.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "New Media For Upload");


        if (Build.VERSION.SDK_INT < 26) {
            Notification notification = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(4, notification);

        }
        else
        {
            String str = "FBAChannel";
            NotificationChannel channel = new NotificationChannel(str, "FBAAdmin", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("FBA_Notification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new NotificationCompat.Builder(this, str).setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(4, notification2);
        }

    }

}
