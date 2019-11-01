package com.sanji;

import android.app.Notification;
import android.app.Notification.BigPictureStyle;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.widget.RemoteViews;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Builder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class FcmMessagingService extends FirebaseMessagingService {
    public DatabaseHandler db;
    public Notification_Databasehandler ndb;
    public long notitime = System.currentTimeMillis();
    public UserDatabaseHandler udb;

    public void onMessageReceived(RemoteMessage remoteMessage) {
        String str = "heading";
        String str2 = "";
        String str3 = "notitype";
        db = new DatabaseHandler(this);
        udb = new UserDatabaseHandler(this);
        ndb = new Notification_Databasehandler(this);
        if (remoteMessage.getData().size() > 0) {
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
            try {
                JSONObject json = new JSONObject(json1.toString());
                if (json.getString(str3).equalsIgnoreCase("removeuser")) {
                    try {
                        udb.deleteselecttown();
                        udb.deleteuser();
                    } catch (Exception e2) {
                    }
                }
                if (json.getString(str3).equalsIgnoreCase("ordercancelled")) {
                    try {
                        generate_ordercancelled();
                    } catch (Exception e3) {
                    }
                }
                if (json.getString(str3).equalsIgnoreCase("product_ordercanelled")) {
                    try {
                        generate_productordercancelled(json.getString("itemname"));
                    } catch (Exception e4) {
                    }
                }
                if (json.getString(str3).equalsIgnoreCase("orderconfimred")) {
                    try {
                        generate_orderconfirmed();
                    } catch (Exception e5) {
                    }
                }
                if (json.getString(str3).equalsIgnoreCase("outtodelivery")) {
                    try {
                        generate_orderoutdelivery();
                    } catch (Exception e6) {
                    }
                }
                if (json.getString(str3).equalsIgnoreCase("delivered")) {
                    try {
                        generate_delivered();
                    } catch (Exception e7) {
                    }
                }
                if (json.getString(str3).equalsIgnoreCase("deliveryreturn")) {
                    try {
                        generate_deliveryreturnd();
                    } catch (Exception e8) {
                    }
                }
                boolean equalsIgnoreCase = json.getString(str3).equalsIgnoreCase("newnotification");
                String str4 = NotificationCompat.CATEGORY_MESSAGE;
                if (equalsIgnoreCase) {
                    try {
                        ndb.addnoti(json.getString("type"), json.getString(str), json.getString(str4), json.getString("msgtime"));
                        if (ndb.get_count().equalsIgnoreCase(str2)) {
                            Notification_Databasehandler notification_Databasehandler = ndb;
                            StringBuilder sb = new StringBuilder();
                            sb.append(1);
                            sb.append(str2);
                            notification_Databasehandler.addcount(sb.toString());
                        } else {
                            int noticount = Integer.parseInt(ndb.get_count()) + 1;
                            Notification_Databasehandler notification_Databasehandler2 = ndb;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(noticount);
                            sb2.append(str2);
                            notification_Databasehandler2.update_count(sb2.toString());
                        }
                        generate_notifications(json.getString(str), json.getString("title"));
                    } catch (Exception e9) {
                    }
                }
                if (json.getString(str3).equalsIgnoreCase("newproductcat")) {
                    try {
                        db.deleteproductcatlist();
                    } catch (Exception e10) {
                    }
                }
                if (json.getString(str3).equalsIgnoreCase("offernoti")) {
                    try {
                        db.addoffernoti(json.getString("productid"), json.getString("producttitle"), json.getString("offermsg"));
                        generate_productadvt(json.getString("notititle"), json.getString("bigtitle"), json.getString("picurl"));
                    } catch (Exception e11) {
                    }
                }
                if (json.getString(str3).equalsIgnoreCase("newadsnoti")) {
                    try {
                        generate_offernoti(json.getString(str4));
                    } catch (Exception e12) {
                    }
                }
                if (json.getString(str3).equalsIgnoreCase("newtowns")) {
                    try {
                        String[] k = json.getString("towns").split(",");
                        int j = 0;
                        while (j < k.length) {
                            int a2 = j;
                            int j2 = j + 1;
                            int a1 = j2;
                            int j3 = j2 + 1;
                            udb.addtowns(k[a2], k[a1], k[j3]);
                            j = j3 + 1;
                        }
                    } catch (Exception e13) {
                    }
                }
            } catch (Exception e14) {
            }
        }
    }

    public void generate_deliveryreturnd() {
        Intent intent = new Intent(this, My_Orders.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "താങ്കളുടെ ഓര്‍ഡര്‍ തിരിച്ചെടുത്തിരിക്കുന്നു ");
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        String str = "BDChannel";
        NotificationChannel channel = new NotificationChannel(str, "BigDeal", 3);
        channel.setDescription("BigDeal_Notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_delivered() {
        Intent intent = new Intent(this, My_Orders.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "താങ്കളുടെ ഓര്‍ഡര്‍ ഡെലിവറി ചെയ്ത് കഴിഞ്ഞു ");
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        String str = "BDChannel";
        NotificationChannel channel = new NotificationChannel(str, "BigDeal", 3);
        channel.setDescription("BigDeal_Notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_orderoutdelivery() {
        Intent intent = new Intent(this, My_Orders.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "താങ്കളുടെ ഓര്‍ഡര്‍ ഡെലിവറി ചെയ്യുവാനായി വിട്ടിട്ടുണ്ട്‌ ");
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        String str = "BDChannel";
        NotificationChannel channel = new NotificationChannel(str, "BigDeal", 3);
        channel.setDescription("BigDeal_Notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_orderconfirmed() {
        Intent intent = new Intent(this, My_Orders.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "താങ്കളുടെ ഓര്‍ഡര്‍ അംഗീകരിച്ചിരിക്കുന്നു ");
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        String str = "BDChannel";
        NotificationChannel channel = new NotificationChannel(str, "BigDeal", 3);
        channel.setDescription("BigDeal_Notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_productordercancelled(String itemname) {
        Intent intent = new Intent(this, My_Orders.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        StringBuilder sb = new StringBuilder();
        sb.append("താങ്കളുടെ ");
        sb.append(itemname);
        sb.append(" ന്റെ ഓര്‍ഡര്‍ റദ്ദാക്കിയിരിക്കുന്നു ");
        contentView.setTextViewText(R.id.title, sb.toString());
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        String str = "BDChannel";
        NotificationChannel channel = new NotificationChannel(str, "BigDeal", 3);
        channel.setDescription("BigDeal_Notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_ordercancelled() {
        Intent intent = new Intent(this, My_Orders.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        contentView.setTextViewText(R.id.title, "താങ്കളുടെ ഓര്‍ഡര്‍ റദ്ദാക്കിയിരിക്കുന്നു ");
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        String str = "BDChannel";
        NotificationChannel channel = new NotificationChannel(str, "BigDeal", 3);
        channel.setDescription("BigDeal_Notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_notifications(String itemname, String title) {
        Intent intent = new Intent(this, Notifications.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customenoti_sub);
        contentView.setTextViewText(R.id.title, title);
        contentView.setTextViewText(R.id.subtitle, itemname);
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        String str = "BDChannel";
        NotificationChannel channel = new NotificationChannel(str, "BigDeal", 3);
        channel.setDescription("BigDeal_Notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_productdeliverd(String msg) {
        Intent intent = new Intent(this, My_Orders.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        StringBuilder sb = new StringBuilder();
        sb.append("താങ്കള്‍ ");
        sb.append(msg);
        sb.append(" ഡെലിവറി ചെയ്തു കഴിഞ്ഞു ");
        contentView.setTextViewText(R.id.title, sb.toString());
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        String str = "BDChannel";
        NotificationChannel channel = new NotificationChannel(str, "BigDeal", 3);
        channel.setDescription("BigDeal_Notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_productunverify(String msg) {
        Intent intent = new Intent(this, My_Orders.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        StringBuilder sb = new StringBuilder();
        sb.append("താങ്കള്‍ ");
        sb.append(msg);
        sb.append(" വാങ്ങിയിരിക്കുന്നു ");
        contentView.setTextViewText(R.id.title, sb.toString());
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        String str = "BDChannel";
        NotificationChannel channel = new NotificationChannel(str, "BigDeal", 3);
        channel.setDescription("BigDeal_Notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_productconfirmed(String msg) {
        Intent intent = new Intent(this, My_Orders.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        StringBuilder sb = new StringBuilder();
        sb.append("താങ്കള്‍ ");
        sb.append(msg);
        sb.append(" ഓര്‍ഡര്‍ ഉറപ്പ് വരുത്തിയിരിക്കുന്നു ");
        contentView.setTextViewText(R.id.title, sb.toString());
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        String str = "BDChannel";
        NotificationChannel channel = new NotificationChannel(str, "BigDeal", 3);
        channel.setDescription("BigDeal_Notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_productnotconfirmed(String msg) {
        Intent intent = new Intent(this, My_Orders.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp.packagename, R.layout.customnotification);
        StringBuilder sb = new StringBuilder();
        sb.append("താങ്കള്‍ ");
        sb.append(msg);
        sb.append(" ഓര്‍ഡര്‍ ക്യാന്‍സല്‍ ചെയ്തിരിക്കുന്നു ");
        contentView.setTextViewText(R.id.title, sb.toString());
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        String str = "BDChannel";
        NotificationChannel channel = new NotificationChannel(str, "BigDeal", 3);
        channel.setDescription("BigDeal_Notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_productadvt(String title, String bigtitle, String picurl) {
        try {
            Intent intent = new Intent(this, Offer_Notification.class);
            intent.addFlags(67108864);
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
            final Uri soundUri = RingtoneManager.getDefaultUri(2);
            Handler handler = new Handler(Looper.getMainLooper());
            final String str = picurl;
            final String str2 = title;
            final String str3 = bigtitle;
            AnonymousClass1 r3 = new Runnable() {
                public void run() {
                    Glide.with(FcmMessagingService.getApplicationContext()).asBitmap().load(str).into(new SimpleTarget<Bitmap>() {
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            ((NotificationManager) FcmMessagingService.getSystemService("notification")).notify(NotificationID.getID(), new Notification.Builder(FcmMessagingService.this).setContentTitle(str2).setContentIntent(pendingIntent).setAutoCancel(true).setSound(soundUri).setSmallIcon(R.drawable.smalllogo).setLargeIcon(BitmapFactory.decodeResource(FcmMessagingService.getResources(), R.drawable.smalllogo)).setStyle(new BigPictureStyle().bigPicture(bitmap).setBigContentTitle(str3)).build());
                        }

                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                        }
                    });
                }
            };
            handler.post(r3);
        } catch (Exception e) {
        }
    }

    public void generate_offernoti(String msg) {
        Intent intent = new Intent(this, MainActivity.class);
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
        String str = "BDChannel";
        NotificationChannel channel = new NotificationChannel(str, "BigDeal", 3);
        channel.setDescription("BigDeal_Notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, str).setSmallIcon(R.drawable.smalllogo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }
}
