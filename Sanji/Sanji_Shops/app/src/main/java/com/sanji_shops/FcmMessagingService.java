package com.sanji_shops;

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
                JSONObject json = new JSONObject(json1.toString());
                if (json.getString("notitype").equalsIgnoreCase("newproductdisapproved")) {
                    try {
                        generate_productunverify(json.getString("itemname"));
                    } catch (Exception e2) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("newproductapproved")) {
                    try {
                        generate_productverified(json.getString("itemname"));
                    } catch (Exception e3) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("productactived")) {
                    try {
                        generate_productactived(json.getString("itemname"));
                    } catch (Exception e4) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("productinactived")) {
                    try {
                        generate_productinactived(json.getString("itemname"));
                    } catch (Exception e5) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("productdeleted")) {
                    try {
                        generate_productdeleted(json.getString("itemname"));
                    } catch (Exception e6) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("neworder")) {
                    try {
                        generate_homedelivery();
                    } catch (Exception e7) {
                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("expireextend")) {
                    try {
                        generate_extenddate();
                    } catch (Exception e8) {
                    }
                }
            } catch (Exception e9) {
            }
        }
    }

    public void generate_homedelivery() {
        Intent intent = new Intent(this, Order_Group.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "ഓര്‍ഡര്‍ വന്നിട്ടുണ്ട്‌ ");
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        NotificationChannel channel = new NotificationChannel("BDSChannel", "BigDeal_Shops", 3);
        channel.setDescription("BigDeal_ShopsNotification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, "BDSChannel").setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_extenddate() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "വളരെ നന്ദി ! ഇനി താങ്കള്‍ക്ക് പുതിയ ഓഫറുകള്‍ ചേര്‍ക്കാവുന്നതാണ്‌ ");
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        NotificationChannel channel = new NotificationChannel("BDSChannel", "BigDeal_Shops", 3);
        channel.setDescription("BigDeal_ShopsNotification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, "BDSChannel").setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_productactived(String msg) {
        Intent intent = new Intent(this, Product_Management.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        StringBuilder sb = new StringBuilder();
        sb.append("താങ്കളുടെ ");
        sb.append(msg);
        sb.append(" ആക്ടീവ് ചെയ്തിരിക്കുന്നു ");
        contentView.setTextViewText(R.id.title, sb.toString());
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        NotificationChannel channel = new NotificationChannel("BDSChannel", "BigDeal_Shops", 3);
        channel.setDescription("BigDeal_ShopsNotification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, "BDSChannel").setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_productinactived(String msg) {
        Intent intent = new Intent(this, Product_Management.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        StringBuilder sb = new StringBuilder();
        sb.append("താങ്കളുടെ ");
        sb.append(msg);
        sb.append(" ഡീ ആക്ടീവ് ചെയ്തിരിക്കുന്നു ");
        contentView.setTextViewText(R.id.title, sb.toString());
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        NotificationChannel channel = new NotificationChannel("BDSChannel", "BigDeal_Shops", 3);
        channel.setDescription("BigDeal_ShopsNotification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, "BDSChannel").setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_productdeleted(String msg) {
        Intent intent = new Intent(this, Product_Management.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        StringBuilder sb = new StringBuilder();
        sb.append("താങ്കളുടെ ");
        sb.append(msg);
        sb.append(" ഡിലീറ്റ് ചെയ്തിരിക്കുന്നു ");
        contentView.setTextViewText(R.id.title, sb.toString());
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        NotificationChannel channel = new NotificationChannel("BDSChannel", "BigDeal_Shops", 3);
        channel.setDescription("BigDeal_ShopsNotification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, "BDSChannel").setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_productunverify(String msg) {
        Intent intent = new Intent(this, Product_Management.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        StringBuilder sb = new StringBuilder();
        sb.append("താങ്കളുടെ ");
        sb.append(msg);
        sb.append(" അംഗീകരിക്കുവാന്‍ സാധിച്ചില്ല ");
        contentView.setTextViewText(R.id.title, sb.toString());
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        NotificationChannel channel = new NotificationChannel("BDSChannel", "BigDeal_Shops", 3);
        channel.setDescription("BigDeal_ShopsNotification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, "BDSChannel").setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_productverified(String msg) {
        Intent intent = new Intent(this, Product_Management.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        StringBuilder sb = new StringBuilder();
        sb.append("താങ്കളുടെ ");
        sb.append(msg);
        sb.append(" അംഗീകരിച്ചിരിക്കുന്നു ");
        contentView.setTextViewText(R.id.title, sb.toString());
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        NotificationChannel channel = new NotificationChannel("BDSChannel", "BigDeal_Shops", 3);
        channel.setDescription("BigDeal_ShopsNotification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, "BDSChannel").setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_newvoucher(String msg) {
        Intent intent = new Intent(this, Product_Management.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        StringBuilder sb = new StringBuilder();
        sb.append(" പുതിയ വൗച്ചര്‍ - ");
        sb.append(msg);
        contentView.setTextViewText(R.id.title, sb.toString());
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        NotificationChannel channel = new NotificationChannel("BDSChannel", "BigDeal_Shops", 3);
        channel.setDescription("BigDeal_ShopsNotification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, "BDSChannel").setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }
}
