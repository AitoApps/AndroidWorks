package com.daydeal_shop;

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
    public DatabaseHandler db;
    public UserDatabaseHandler udb;

    public void onMessageReceived(RemoteMessage remoteMessage) {
        db = new DatabaseHandler(this);
        udb = new UserDatabaseHandler(this);

        if (remoteMessage.getData().size() > 0) {

            try {
                String message = new JSONObject((String) remoteMessage.getData().get("alamol")).getString("daydeal");
                JSONObject json1 = new JSONObject();
                String[] s = message.split(":%");
                int i = 0;
                while (i < s.length) {
                    int a = i;
                    int i2 = i + 1;
                    json1.put(s[a], s[i2]);
                    i = i2 + 1;
                }

            try {
                JSONObject json = new JSONObject(json1.toString());
                String str2 = "itemname";
                if (json.getString("notitype").equalsIgnoreCase("newproductdisapproved")) {
                    try {
                        generate_productunverify(json.getString(str2));
                    } catch (Exception e2) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("newproductapproved")) {
                    try {
                        generate_productverified(json.getString(str2));
                    } catch (Exception e3) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("productactived")) {
                    try {
                        generate_productactived(json.getString(str2));
                    } catch (Exception e4) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("productinactived")) {
                    try {
                        generate_productinactived(json.getString(str2));
                    } catch (Exception e5) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("productdeleted")) {
                    try {
                        generate_productdeleted(json.getString(str2));
                    } catch (Exception e6) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("expireextend")) {
                    try {
                        generate_extenddate();
                    } catch (Exception e7) {
                    }
                }
            } catch (JSONException e) {
            }
            } catch (Exception e8) {
            }
        }
    }
    public void generate_extenddate(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "വളരെ നന്ദി ! ഇനി താങ്കള്‍ക്ക് പുതിയ ഓഫറുകള്‍ ചേര്‍ക്കാവുന്നതാണ്‌ ");


        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NotificationID.getID(), notification);

        }
        else
        {
            String str = "DDChannel";
            NotificationChannel channel = new NotificationChannel(str, "DayDeal", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("DayDeal_Notification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NotificationID.getID(), notification2);
        }

    }

    public void generate_productactived(String msg){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "താങ്കളുടെ "+msg+" ആക്ടീവ് ചെയ്തിരിക്കുന്നു ");


        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NotificationID.getID(), notification);

        }
        else
        {
            String str = "DDChannel";
            NotificationChannel channel = new NotificationChannel(str, "DayDeal", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("DayDeal_Notification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NotificationID.getID(), notification2);
        }

    }

    public void generate_productinactived(String msg){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "താങ്കളുടെ "+msg+" ഡീ ആക്ടീവ് ചെയ്തിരിക്കുന്നു ");


        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NotificationID.getID(), notification);

        }
        else
        {
            String str = "DDChannel";
            NotificationChannel channel = new NotificationChannel(str, "DayDeal", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("DayDeal_Notification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NotificationID.getID(), notification2);
        }

    }

    public void generate_productdeleted(String msg){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "താങ്കളുടെ "+msg+" ഡിലീറ്റ് ചെയ്തിരിക്കുന്നു ");


        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NotificationID.getID(), notification);

        }
        else
        {
            String str = "DDChannel";
            NotificationChannel channel = new NotificationChannel(str, "DayDeal", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("DayDeal_Notification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NotificationID.getID(), notification2);
        }

    }

    public void generate_productunverify(String msg){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "താങ്കളുടെ "+msg+" അംഗീകരിക്കുവാന്‍ സാധിച്ചില്ല ");


        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NotificationID.getID(), notification);

        }
        else
        {
            String str = "DDChannel";
            NotificationChannel channel = new NotificationChannel(str, "DayDeal", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("DayDeal_Notification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NotificationID.getID(), notification2);
        }

    }
    public void generate_productverified(String msg){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "താങ്കളുടെ "+msg+" അംഗീകരിച്ചിരിക്കുന്നു ");


        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NotificationID.getID(), notification);

        }
        else
        {
            String str = "DDChannel";
            NotificationChannel channel = new NotificationChannel(str, "DayDeal", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("DayDeal_Notification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NotificationID.getID(), notification2);
        }

    }
}
