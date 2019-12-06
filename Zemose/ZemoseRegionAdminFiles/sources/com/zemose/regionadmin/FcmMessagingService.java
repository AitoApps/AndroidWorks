package com.zemose.regionadmin;

import android.app.Notification;
import android.app.Notification.BigTextStyle;
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
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.RemoteViews;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.utils.SharedPrefsUtils.Keys;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class FcmMessagingService extends FirebaseMessagingService {
    public ChatDB db;
    String displaytext = "";

    public void onMessageReceived(RemoteMessage remoteMessage) {
        this.db = new ChatDB(this);
        if (remoteMessage.getData().size() > 0) {
            try {
                Map<String, String> data2 = remoteMessage.getData();
                if (this.db.get_isopened().equalsIgnoreCase("1")) {
                    Intent intent = new Intent("com.zemosechat_user.Message");
                    intent.putExtra("zemosechatuser", data2.toString());
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                }
                if (((String) data2.get("notitype")).equalsIgnoreCase("newonlinePayment")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("New Payment With the amount of ");
                    sb.append((String) data2.get(PayUmoneyConstants.AMOUNT));
                    generate_newpayment(sb.toString());
                } else if (((String) data2.get("notitype")).equalsIgnoreCase("odr_transfer")) {
                    generate_transferorder("New Transfer Order");
                } else if (((String) data2.get("notitype")).equalsIgnoreCase("chatmsg")) {
                    String strDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").format(Calendar.getInstance().getTime());
                    if (((String) data2.get("usertype")).replaceAll("^\"|\"$", "").equalsIgnoreCase("s")) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("s_");
                        sb2.append(((String) data2.get(Keys.USER_ID)).replaceAll("^\"|\"$", ""));
                        this.db.add_chatmsg(strDate, ((String) data2.get(Keys.USER_ID)).replaceAll("^\"|\"$", "").toString(), ((String) data2.get("userName")).replaceAll("^\"|\"$", "").toString(), ((String) data2.get(NotificationCompat.CATEGORY_MESSAGE)).replaceAll("^\"|\"$", "").toString(), "0", "2", sb2.toString());
                    } else if (((String) data2.get("usertype")).replaceAll("^\"|\"$", "").equalsIgnoreCase("c")) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("c_");
                        sb3.append(((String) data2.get(Keys.USER_ID)).replaceAll("^\"|\"$", ""));
                        this.db.add_chatmsg(strDate, ((String) data2.get(Keys.USER_ID)).replaceAll("^\"|\"$", "").toString(), ((String) data2.get("userName")).replaceAll("^\"|\"$", "").toString(), ((String) data2.get(NotificationCompat.CATEGORY_MESSAGE)).replaceAll("^\"|\"$", "").toString(), "0", "2", sb3.toString());
                    }
                    if (this.db.get_isopened().equalsIgnoreCase("")) {
                        generate_newchat(((String) data2.get(NotificationCompat.CATEGORY_MESSAGE)).replaceAll("^\"|\"$", "").toString(), ((String) data2.get(Keys.USER_ID)).replaceAll("^\"|\"$", "").toString(), ((String) data2.get("userName")).replaceAll("^\"|\"$", "").toString());
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public void generate_newpayment(String title) {
        Intent intent = new Intent(this, Payment_Approve.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp_Variable.packagename, R.layout.customenotification);
        contentView.setTextViewText(R.id.title, title);
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        NotificationChannel channel = new NotificationChannel("ZemoseAdminChannel", "ZemoseAdmin", 3);
        channel.setDescription("ZemoseAdmin_Notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, "ZemoseAdminChannel").setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_transferorder(String title) {
        Intent intent = new Intent(this, Transfered_Report.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        RemoteViews contentView = new RemoteViews(Temp_Variable.packagename, R.layout.customenotification);
        contentView.setTextViewText(R.id.title, title);
        if (VERSION.SDK_INT < 26) {
            Notification notification = new Builder(this).setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).build();
            notification.flags |= 16;
            notification.defaults |= 1;
            notification.defaults |= 2;
            ((NotificationManager) getSystemService("notification")).notify(NotificationID.getID(), notification);
            return;
        }
        NotificationChannel channel = new NotificationChannel("ZemoseAdminChannel", "ZemoseAdmin", 3);
        channel.setDescription("ZemoseAdmin_Notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification2 = new Builder(this, "ZemoseAdminChannel").setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContent(contentView).setPriority(0).build();
        notification2.flags |= 16;
        notificationManager.notify(NotificationID.getID(), notification2);
    }

    public void generate_newchat(String msg, String userid, String username) {
        try {
            Intent intent = new Intent(this, Chatting.class);
            intent.addFlags(67108864);
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
            final Uri soundUri = RingtoneManager.getDefaultUri(2);
            if (msg.length() >= 150) {
                StringBuilder sb = new StringBuilder();
                sb.append(msg.substring(0, 150));
                sb.append("...");
                this.displaytext = sb.toString();
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(msg);
                sb2.append("...");
                this.displaytext = sb2.toString();
            }
            Handler handler = new Handler(Looper.getMainLooper());
            final String str = userid;
            final String str2 = username;
            AnonymousClass1 r3 = new Runnable() {
                public void run() {
                    RequestBuilder asBitmap = Glide.with(FcmMessagingService.this.getApplicationContext()).asBitmap();
                    StringBuilder sb = new StringBuilder();
                    sb.append(Temp_Variable.baseurl);
                    sb.append("images/customerImageThumb/");
                    sb.append(str);
                    sb.append(".jpg");
                    asBitmap.load(sb.toString()).into(new SimpleTarget<Bitmap>() {
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            if (VERSION.SDK_INT < 26) {
                                ((NotificationManager) FcmMessagingService.this.getSystemService("notification")).notify(0, new Notification.Builder(FcmMessagingService.this).setContentTitle(str2).setContentIntent(pendingIntent).setAutoCancel(true).setSound(soundUri).setSmallIcon(R.drawable.logo).setLargeIcon(bitmap).setStyle(new BigTextStyle().bigText(FcmMessagingService.this.displaytext)).build());
                                return;
                            }
                            NotificationChannel channel = new NotificationChannel("ZemoseAdminChannel", "ZemoseAdmin", 3);
                            channel.setDescription("ZemoseAdmin_Notification");
                            NotificationManager notificationManager = (NotificationManager) FcmMessagingService.this.getSystemService(NotificationManager.class);
                            notificationManager.createNotificationChannel(channel);
                            Notification notification = new Builder(FcmMessagingService.this, "ZemoseAdminChannel").setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContentTitle(str2).setContentIntent(pendingIntent).setLargeIcon(bitmap).setStyle(new NotificationCompat.BigTextStyle().bigText(FcmMessagingService.this.displaytext)).setContentText(FcmMessagingService.this.displaytext).setPriority(0).build();
                            notification.flags |= 16;
                            notificationManager.notify(NotificationID.getID(), notification);
                        }

                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            if (VERSION.SDK_INT < 26) {
                                ((NotificationManager) FcmMessagingService.this.getSystemService("notification")).notify(0, new Notification.Builder(FcmMessagingService.this).setContentTitle(str2).setContentIntent(pendingIntent).setAutoCancel(true).setSound(soundUri).setSmallIcon(R.drawable.logo).setLargeIcon(BitmapFactory.decodeResource(FcmMessagingService.this.getResources(), R.drawable.logo)).setStyle(new BigTextStyle().bigText(FcmMessagingService.this.displaytext)).build());
                                return;
                            }
                            NotificationChannel channel = new NotificationChannel("ZemoseAdminChannel", "ZemoseAdmin", 3);
                            channel.setDescription("ZemoseAdmin_Notification");
                            NotificationManager notificationManager = (NotificationManager) FcmMessagingService.this.getSystemService(NotificationManager.class);
                            notificationManager.createNotificationChannel(channel);
                            Notification notification = new Builder(FcmMessagingService.this, "ZemoseAdminChannel").setSmallIcon(R.drawable.logo).setContentIntent(pendingIntent).setContentTitle(str2).setContentIntent(pendingIntent).setLargeIcon(BitmapFactory.decodeResource(FcmMessagingService.this.getResources(), R.drawable.logo)).setStyle(new NotificationCompat.BigTextStyle().bigText(FcmMessagingService.this.displaytext)).setContentText(FcmMessagingService.this.displaytext).setPriority(0).build();
                            notification.flags |= 16;
                            notificationManager.notify(NotificationID.getID(), notification);
                        }
                    });
                }
            };
            handler.post(r3);
        } catch (Exception e) {
        }
    }
}
