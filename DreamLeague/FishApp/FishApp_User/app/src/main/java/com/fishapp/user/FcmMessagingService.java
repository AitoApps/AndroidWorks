package com.fishapp.user;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build.VERSION;
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

                String message = new JSONObject(remoteMessage.getData().get("fishapp")).getString("igglue");
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
                if (json.getString("notitype").equalsIgnoreCase("changearea")) {
                    try {
                        udb.addarea_update(json.getString("areaid").trim(), json.getString("areaname").trim(), json.getString("delitime").trim());
                    } catch (Exception e2) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("removeuser")) {
                    try {
                        udb.deleteuser();
                    } catch (Exception e3) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("ordercancelled")) {
                    try {
                        generate_ordercancelled();
                    } catch (Exception e4) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("ordercancelled_product")) {
                    try {
                        generate_ordercancelled_product(json.getString("msg"));
                    } catch (Exception e5) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("orderconfimred")) {
                    try {
                        generate_orderconfirmed();
                    } catch (Exception e6) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("outtodelivery")) {
                    try {
                        generate_orderoutdelivery();
                    } catch (Exception e7) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("delivered")) {
                    try {
                        generate_delivered();
                    } catch (Exception e8) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("deliveryreturn")) {
                    try {
                        generate_deliveryreturnd();
                    } catch (Exception e9) {
                    }
                }
            } catch (Exception e10) {
            }
            } catch (JSONException e) {
            }
        }
    }

    public void generate_deliveryreturnd(){
        Intent intent = new Intent(this, My_Order.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "Order Returned ");


        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NotificationID.getID(), notification);

        }
        else
        {
            String str = "FISHESChannels";
            NotificationChannel channel = new NotificationChannel(str, "Igglue_Fishs", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Igglue_FishesNoti");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NotificationID.getID(), notification2);
        }

    }

    public void generate_delivered(){
        Intent intent = new Intent(this, My_Order.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "Order Delivered ");


        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NotificationID.getID(), notification);

        }
        else
        {
            String str = "FISHESChannels";
            NotificationChannel channel = new NotificationChannel(str, "Igglue_Fishs", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Igglue_FishesNoti");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NotificationID.getID(), notification2);
        }

    }

    public void generate_orderoutdelivery(){
        Intent intent = new Intent(this, My_Order.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "Your order is out to delivery ");


        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NotificationID.getID(), notification);

        }
        else
        {
            String str = "FISHESChannels";
            NotificationChannel channel = new NotificationChannel(str, "Igglue_Fishs", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Igglue_FishesNoti");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NotificationID.getID(), notification2);
        }

    }


    public void generate_orderconfirmed(){
        Intent intent = new Intent(this, My_Order.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "Your order is Confirmed ");


        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NotificationID.getID(), notification);

        }
        else
        {
            String str = "FISHESChannels";
            NotificationChannel channel = new NotificationChannel(str, "Igglue_Fishs", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Igglue_FishesNoti");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NotificationID.getID(), notification2);
        }

    }

    public void generate_ordercancelled(){
        Intent intent = new Intent(this, My_Order.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "Your order is Cancelled ");


        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NotificationID.getID(), notification);

        }
        else
        {
            String str = "FISHESChannels";
            NotificationChannel channel = new NotificationChannel(str, "Igglue_Fishs", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Igglue_FishesNoti");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NotificationID.getID(), notification2);
        }

    }
    public void generate_ordercancelled_product(String msg){
        Intent intent = new Intent(this, My_Order.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "Order of  "+msg+" is Cancelled");


        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NotificationID.getID(), notification);

        }
        else
        {
            String str = "FISHESChannels";
            NotificationChannel channel = new NotificationChannel(str, "Igglue_Fishs", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Igglue_FishesNoti");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
            notification2.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NotificationID.getID(), notification2);
        }

    }

}
