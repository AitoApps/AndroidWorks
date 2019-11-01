package com.sanji_admin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.RemoteViews;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class FcmMessagingService extends FirebaseMessagingService {
    public DatabaseHandler db;
    public long notitime = System.currentTimeMillis();
    public UserDatabaseHandler udb;

    public void onMessageReceived(RemoteMessage remoteMessage) {
        db = new DatabaseHandler(this);
        udb = new UserDatabaseHandler(this);
        if (remoteMessage.getData().size() > 0) {
            try {
                String message = new JSONObject((String) remoteMessage.getData().get("bigdeal")).getString("salman");
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
                if (new JSONObject(json1.toString()).getString("notitype").equalsIgnoreCase("newproduct")) {
                    try {
                        generatenewproduct("വെരിഫിക്കേഷന് വേണ്ടി പുതിയ പ്രൊഡക്ട് വന്നിട്ടുണ്ട്‌");
                    } catch (Exception e2) {
                    }
                }
            } catch (Exception e3) {
            }
        }
    }

    public void generatenewproduct(String msg) {
        Intent intent = new Intent(this, Product_verification.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, msg);
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        NotificationChannel channel = new NotificationChannel("BDChannel", "BigDeal", 3);
        channel.setDescription("BigDeal_Notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, "BDChannel").setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }
}
