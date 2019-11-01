package com.suhi_chintha;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
public class FCM_MESSAGES extends FirebaseMessagingService {
    public DataDb dataDb;
    public DataDB4 dataDb4;
    public DataDB1 dataDb1;
    public DataDB2 dataDb2;
    public User_DataDB userDataDB;
    public long notitime = System.currentTimeMillis();
    public String txtstatusid = "",txtreplayid="";
    public String msg="";
    public String displaytext="";
    //suhisalman@gmail.com
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        dataDb = new DataDb(this);
        dataDb1 = new DataDB1(this);
        dataDb2 = new DataDB2(this);
        dataDb4 =new DataDB4(this);
        userDataDB =new User_DataDB(this);
        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject jsonObject=new JSONObject(remoteMessage.getData().get("chinthakal"));
                String message=jsonObject.getString("salmanstatus");
                JSONObject json1 = new JSONObject();
                try {
                    String[] s=message.split(":%");
                    for(int i=0;i<s.length;i++)
                    {
                        int a=i;
                        i++;
                        int a1=i;
                        json1.put(s[a],s[a1]);
                    }

                } catch (JSONException e) {

                }
                final JSONObject json = new JSONObject(json1.toString());
                Intent intent = new Intent("com.statusappkal.Message");
                intent.putExtra("salmanstatus", json1.toString());
                LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
                lbm.sendBroadcast(intent);
                if (json.getString("notitype").equalsIgnoreCase("allmsg")) {
                    try {
                        gen_allmsg(json.getString("title"),json.getString("msg"));
                    } catch (Exception a) {

                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("commonmsg")) {

                    try {
                        dataDb2.add_publicmsg(json.getString("stype"),json.getString("url"),json.getString("dim"),json.getString("msg"));
                    } catch (Exception a) {

                    }
                }
                if (json.getString("notitype").equalsIgnoreCase("hacking")) {

                    try {
                        dataDb.deletevada();
                        dataDb.add_vada(json.getString("hacktype"));
                    } catch (Exception a) {

                    }
                }



                if (json.getString("notitype").equalsIgnoreCase("adminmessage")) {

                    try {
                        String[] p =json.getString("msg").split("##");
                        dataDb1.drop_message();
                        dataDb1.add_message(p[0], p[1]);
                    } catch (Exception a) {

                    }
                }


                if (json.getString("notitype").equalsIgnoreCase("update")) {

                    gen_update(json.getString("title"),json.getString("content"));
                }

                if (json.getString("notitype").equalsIgnoreCase("like")) {

                    if(!userDataDB.get_userid().equalsIgnoreCase(json.getString("userid")))
                    {
                        if (dataDb1.get_likenoti().equalsIgnoreCase("1")) {
                            if (System.currentTimeMillis() > notitime) {

                                noti_likes(json.getString("status"),json.getString("userid"),json.getString("username"),json.getString("imgsig"));

                            }
                        }
                        if (dataDb1.getlikes(json.getString("statusid")).equalsIgnoreCase("")) {

                            dataDb1.add_notilist(json.getString("statusid"), "0",json.getString("username"), "1", json.getString("status"), json.getString("userid"), "0", "0", "0", json.getString("imgsig"), "0", "0", "0");

                        } else {

                            dataDb1.update_likes(json.getString("statusid"), json.getString("username"), json.getString("userid"), json.getString("imgsig"));

                        }

                        notitime = System.currentTimeMillis() + 3000;
                    }

                }

                if(json.getString("notitype").equalsIgnoreCase("status"))
                {

                    if(dataDb.get_visible().equalsIgnoreCase("0"))
                    {

                        if(userDataDB.get_userid().equalsIgnoreCase(json.getString("userid")))
                        {

                        }
                        else
                        {
                            if(System.currentTimeMillis()>notitime)
                            {
                                if(dataDb1.get_notistatus().equalsIgnoreCase("1"))
                                {
                                    not_statusnoti(json.getString("status"),json.getString("userid"),json.getString("name"),json.getString("imgsig"));
                                }
                            }
                            notitime=System.currentTimeMillis()+5000;
                        }

                    }
                }

                if (json.getString("notitype").equalsIgnoreCase("comment")) {


                    if (dataDb1.get_commntdtls(json.getString("statusid")).equalsIgnoreCase("")) {

                        msg =json1.toString();
                        try {
                            txtstatusid =json.getString("statusid");

                            loadstatus();


                        } catch (Exception a) {

                        }


                    } else {


                        ArrayList<String> id2 = dataDb1.getcommentdetails(json.getString("statusid"));
                        String[] c2 = id2.toArray(new String[id2.size()]);


                        String text1 = c2[1];

                        if (userDataDB.get_userid().equalsIgnoreCase(json.getString("userid"))) {

                        } else {
                            if (dataDb1.get_cmntvisible().equalsIgnoreCase("0")) {

                                if (dataDb1.get_cmntnoti().equalsIgnoreCase("1")) {
                                    if (System.currentTimeMillis() > notitime) {

                                        if (dataDb2.get_notidisabled(json.getString("statusid")).equalsIgnoreCase("")) {

                                            dataDb1.deletecmntdetails();
                                            dataDb1.add_cmntdtails(json.getString("statusid"),json.getString("statususerid"),json.getString("statusname"),text1,json.getString("statusimgsig"),json.getString("statustype"),json.getString("photourl"),json.getString("photodim"));
                                            noti_cmnts(text1,json.getString("userid"),json.getString("name"),json.getString("statusimgsig"));
                                        }

                                    }

                                }
                            }
                        }

                        if (!json.getString("userid").equalsIgnoreCase(userDataDB.get_userid())) {

                            if (dataDb1.getcomments(json.getString("statusid")).equalsIgnoreCase("")) {

                                if (dataDb2.get_notidisabled(json.getString("statusid")).equalsIgnoreCase("")) {

                                    dataDb1.add_notilist(json.getString("statusid"), "1", json.getString("name"), "1", text1,json.getString("userid"), "0",json.getString("statususerid"),json.getString("statusname"),json.getString("statusimgsig"),json.getString("statustype"),json.getString("photourl"),"");

                                }

                            } else {
                                if (dataDb1.get_comments1(json.getString("userid")).equalsIgnoreCase("")) {

                                    if (dataDb2.get_notidisabled(json.getString("statusid")).equalsIgnoreCase("")) {

                                        dataDb1.update_cmnts(json.getString("statusid"), json.getString("name"), text1,json.getString("userid"), json.getString("statususerid"),json.getString("statusname"),json.getString("statusimgsig"));
                                    }
                                } else {
                                    if (dataDb2.get_notidisabled(json.getString("statusid")).equalsIgnoreCase("")) {

                                        dataDb1.updatecomments1(json.getString("statusid"), json.getString("name"), text1, json.getString("statususerid"), json.getString("statusname"),json.getString("statusimgsig"));
                                    }
                                }


                            }

                        }

                        notitime = System.currentTimeMillis() + 3000;
                    }


                }
                if (json.getString("notitype").equalsIgnoreCase("replay")) {
                    if (dataDb2.getcommentstore1(json.getString("cmntid")).equalsIgnoreCase("")) {
                        msg = json1.toString();
                        try {
                            txtreplayid= json.getString("cmntid");
                            loadreply();
                        } catch (Exception a) {

                        }
                    } else {

                        ArrayList<String> id2 = dataDb2.get_storecmnt(json.getString("cmntid"));
                        String[] c2 = id2.toArray(new String[id2.size()]);

                        String text1 = c2[1];

                        if (userDataDB.get_userid().equalsIgnoreCase(json.getString("userid"))) {

                        } else {
                            if (dataDb2.get_rplyvisible().equalsIgnoreCase("")) {
                                if (dataDb1.get_cmntnoti().equalsIgnoreCase("1")) {

                                    if (System.currentTimeMillis() > notitime) {

                                        if (dataDb2.get_disablerply(json.getString("cmntid")).equalsIgnoreCase("")) {

                                            dataDb2.add_replycmnt(json.getString("cmntid"),json.getString("rplyuserid"),json.getString("rplyname"), Static_Variable.entypoint1 +"userphotosmall/"+json.getString("userid")+".jpg",text1,json.getString("replyimgsig"));

                                            gen_reply(text1,json.getString("userid"),json.getString("name"),json.getString("replyimgsig"));

                                        }

                                    }
                                }
                            }
                        }


                        if (!json.getString("userid").equalsIgnoreCase(userDataDB.get_userid())) {

                            if (dataDb1.get_reply(json.getString("cmntid")).equalsIgnoreCase("")) {

                                //String notiogid1,String noti_type1,String noti_lastname1,String noti_count1,String noti_text1,String noti_userid1,String isread1
                                if (dataDb2.get_disablerply(json.getString("cmntid")).equalsIgnoreCase("")) {
                                    dataDb1.add_notilist(json.getString("cmntid"), "2", json.getString("name"), "1", text1, json.getString("userid"), "0", json.getString("rplyuserid"),json.getString("rplyname"),json.getString("replyimgsig"),"0","0","0");

                                }
                            } else {
                                if (dataDb1.getreply1(json.getString("userid")).equalsIgnoreCase("")) {


                                    if (dataDb2.get_disablerply(json.getString("cmntid")).equalsIgnoreCase("")) {
                                        dataDb1.updatereplay(json.getString("cmntid"),json.getString("name"), text1,json.getString("userid"),json.getString("rplyuserid"),json.getString("rplyname"),json.getString("replyimgsig"));

                                    }
                                } else {
                                    if (dataDb2.get_disablerply(json.getString("cmntid")).equalsIgnoreCase("")) {

                                        dataDb1.updatereplay1(json.getString("cmntid"), json.getString("name"), text1, json.getString("rplyuserid"),json.getString("rplyname"),json.getString("replyimgsig"));

                                    }
                                }


                            }

                        }
                        notitime = System.currentTimeMillis() + 3000;
                    }

                }

            } catch (Exception a) {
            }
        }
        else
        {
        }

    }

    public void gen_allmsg(String title, String message) {
        try {

            Intent intent = new Intent(this, HeartOf_App.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            if (Build.VERSION.SDK_INT < 26) {
                Notification builder = new Notification.Builder(this)
                        .setContentTitle(title)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setSound(soundUri)
                        .setSmallIcon(R.drawable.img_notilogo)
                        .setStyle(new Notification.BigTextStyle().bigText(message))
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img_notiadmin))
                        .build();
                final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(2, builder);
            }
            else{

                CharSequence name = "StatusApp";
                String description = "StatusApp_Disc";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("StatusAppChannel", name, importance);
                channel.setDescription(description);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);

                NotificationCompat.Builder builder1 = new NotificationCompat.Builder(FCM_MESSAGES.this,"StatusAppChannel")
                        .setContentTitle(title)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setSound(soundUri)
                        .setSmallIcon(R.drawable.img_notilogo)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img_notiadmin))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                Notification notification = builder1.build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(2, notification);
            }


        } catch (Exception ignored) {
        }
    }






    private void gen_update(String title, String content) {
        try {


            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.mozhi"));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            //sound for notification
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            if (Build.VERSION.SDK_INT < 26) {
                Notification builder = new Notification.Builder(this)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setSound(soundUri)
                        .setSmallIcon(R.drawable.img_notilogo)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img_notilogo))
                        .build();
                final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, builder);
            }
            else
            {
                CharSequence name = "StatusApp";
                String description = "StatusApp_Disc";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("StatusAppChannel", name, importance);
                channel.setDescription(description);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);

                NotificationCompat.Builder builder1 = new NotificationCompat.Builder(FCM_MESSAGES.this,"StatusAppChannel")
                        .setContentTitle(title)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setSound(soundUri)
                        .setSmallIcon(R.drawable.img_notilogo)
                        .setContentText(content)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img_notilogo))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                Notification notification = builder1.build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(0, notification);



            }




        } catch (Exception ignored) {
        }
    }




    public void noti_likes(String status, final String userid, final String username, final String imgsig) {
        try {


            final Intent intent = new Intent(this, List_noti.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


            final Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            if(status.length()>=150)
            {
                displaytext= status.substring(0,150)+"...";
            }
            else
            {
                displaytext=status+"...";
            }


            if (Build.VERSION.SDK_INT < 26) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        RequestOptions rep=new RequestOptions().signature(new ObjectKey(imgsig));
                        Glide.with(getApplicationContext()).asBitmap().apply(rep).load(Static_Variable.entypoint1 + "userphotosmall/" + userid + ".jpg").into(new SimpleTarget<Bitmap>() {

                            @Override
                            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {

                                Notification builder1 = new Notification.Builder(FCM_MESSAGES.this)
                                        .setContentTitle(username+ " Likes your status")
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setSmallIcon(R.drawable.img_notilogo)
                                        .setLargeIcon(bitmap)
                                        .setStyle(new Notification.BigTextStyle().bigText(displaytext))
                                        .build();
                                final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.notify(0, builder1);

                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                Notification builder1 = new Notification.Builder(FCM_MESSAGES.this)
                                        .setContentTitle(username+ " Likes your status")
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setSmallIcon(R.drawable.img_notilogo)
                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img_notilogo))
                                        .setStyle(new Notification.BigTextStyle().bigText(displaytext))
                                        .build();
                                final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.notify(0, builder1);
                            }
                        });
                    }
                });
            }
            else{

                CharSequence name = "StatusApp";
                String description = "StatusApp_Disc";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("StatusAppChannel", name, importance);
                channel.setDescription(description);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        RequestOptions rep=new RequestOptions().signature(new ObjectKey(imgsig));
                        Glide.with(getApplicationContext()).asBitmap().apply(rep).load(Static_Variable.entypoint1 + "userphotosmall/" + userid + ".jpg").into(new SimpleTarget<Bitmap>() {

                            @Override
                            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {

                                NotificationCompat.Builder builder1 = new NotificationCompat.Builder(FCM_MESSAGES.this,"StatusAppChannel")
                                        .setContentTitle(username+ " Likes your status")
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setSmallIcon(R.drawable.img_notilogo)
                                        .setLargeIcon(bitmap)
                                        .setStyle(new NotificationCompat.BigTextStyle().bigText(displaytext))
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                                Notification notification = builder1.build();
                                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                                notificationManager.notify(0, notification);

                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                NotificationCompat.Builder builder1 = new NotificationCompat.Builder(FCM_MESSAGES.this,"StatusAppChannel")
                                        .setContentTitle(username+ " Likes your status")
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setSmallIcon(R.drawable.img_notilogo)
                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img_notilogo))
                                        .setStyle(new NotificationCompat.BigTextStyle().bigText(displaytext))
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                                Notification notification = builder1.build();
                                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                                notificationManager.notify(0, notification);
                            }
                        });
                    }
                });
            }




        } catch (Exception ignored) {
        }
    }

    public void not_statusnoti(String status, final String userid, final String username, final String imgsig) {
        try {


            final Intent intent = new Intent(this, HeartOf_App.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            //sound for notification
            final Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            if(status.length()>=150)
            {
                displaytext=status.substring(0,150)+"...";
            }
            else
            {
                displaytext=status+"...";
            }

            if (Build.VERSION.SDK_INT < 26) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        RequestOptions rep=new RequestOptions().signature(new ObjectKey(imgsig));
                        Glide.with(getApplicationContext()).asBitmap().apply(rep).load(Static_Variable.entypoint1 + "userphotosmall/" + userid+ ".jpg").into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {

                                Notification builder1 = new Notification.Builder(FCM_MESSAGES.this)
                                        .setContentTitle(username)
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setSmallIcon(R.drawable.img_notilogo)
                                        .setLargeIcon(bitmap)
                                        .setStyle(new Notification.BigTextStyle().bigText(displaytext))
                                        .build();
                                final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.notify(0, builder1);

                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                Notification builder1 = new Notification.Builder(FCM_MESSAGES.this)
                                        .setContentTitle(username)
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setSmallIcon(R.drawable.img_notilogo)
                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img_notilogo))
                                        .setStyle(new Notification.BigTextStyle().bigText(displaytext))
                                        .build();
                                final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.notify(0, builder1);
                            }
                        });
                    }
                });
            }
            else

            {

                CharSequence name = "StatusApp";
                String description = "StatusApp_Disc";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("StatusAppChannel", name, importance);
                channel.setDescription(description);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        RequestOptions rep=new RequestOptions().signature(new ObjectKey(imgsig));
                        Glide.with(getApplicationContext()).asBitmap().apply(rep).load(Static_Variable.entypoint1 + "userphotosmall/" + userid+ ".jpg").into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {

                                NotificationCompat.Builder builder1 = new NotificationCompat.Builder(FCM_MESSAGES.this,"StatusAppChannel")
                                        .setContentTitle(username)
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setSmallIcon(R.drawable.img_notilogo)
                                        .setLargeIcon(bitmap)
                                        .setStyle(new NotificationCompat.BigTextStyle().bigText(displaytext))
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                Notification notification = builder1.build();
                                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                                notificationManager.notify(0, notification);
                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                NotificationCompat.Builder builder1 = new NotificationCompat.Builder(FCM_MESSAGES.this,"StatusAppChannel")
                                        .setContentTitle(username)
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setSmallIcon(R.drawable.img_notilogo)
                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img_notilogo))
                                        .setStyle(new NotificationCompat.BigTextStyle().bigText(displaytext))
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                Notification notification = builder1.build();
                                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                                notificationManager.notify(0, notification);
                            }
                        });
                    }
                });
            }





        } catch (Exception a) {

        }
    }

    public void gen_reply(final String comment, final String userid, final String username, final String imgsig) {
        try {

            final Intent intent = new Intent(this, List_noti.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            final Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            if(comment.length()>=151)
            {
                displaytext=comment.substring(0,150)+"...";
            }
            else
            {
                displaytext=comment+"...";
            }

            if (Build.VERSION.SDK_INT < 26) {


                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        RequestOptions rep=new RequestOptions().signature(new ObjectKey(imgsig));
                        Glide.with(getApplicationContext()).asBitmap().apply(rep).load(Static_Variable.entypoint1 + "userphotosmall/" + userid + ".jpg").into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                                Notification builder1 = new Notification.Builder(FCM_MESSAGES.this)
                                        .setContentTitle(username+ " Replayed on")
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setSmallIcon(R.drawable.img_notilogo)
                                        .setLargeIcon(bitmap)
                                        .setStyle(new Notification.BigTextStyle().bigText(displaytext))
                                        .build();
                                final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.notify(0, builder1);

                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                Notification builder1 = new Notification.Builder(FCM_MESSAGES.this)
                                        .setContentTitle(username+" Replayed on")
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setSmallIcon(R.drawable.img_notilogo)
                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img_notilogo))
                                        .setStyle(new Notification.BigTextStyle().bigText(displaytext))
                                        .build();
                                final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.notify(0, builder1);
                            }
                        });
                    }
                });
            }
            else{

                CharSequence name = "StatusApp";
                String description = "StatusApp_Disc";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("StatusAppChannel", name, importance);
                channel.setDescription(description);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        RequestOptions rep=new RequestOptions().signature(new ObjectKey(imgsig));
                        Glide.with(getApplicationContext()).asBitmap().apply(rep).load(Static_Variable.entypoint1 + "userphotosmall/" + userid + ".jpg").into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                                NotificationCompat.Builder builder1 = new NotificationCompat.Builder(FCM_MESSAGES.this,"StatusAppChannel")
                                        .setContentTitle(username+ " Replayed on")
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setSmallIcon(R.drawable.img_notilogo)
                                        .setLargeIcon(bitmap)
                                        .setStyle(new NotificationCompat.BigTextStyle().bigText(displaytext))
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                                Notification notification = builder1.build();
                                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                                notificationManager.notify(0, notification);

                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                NotificationCompat.Builder builder1 = new NotificationCompat.Builder(FCM_MESSAGES.this,"StatusAppChannel")
                                        .setContentTitle(username+" Replayed on")
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setSmallIcon(R.drawable.img_notilogo)
                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img_notilogo))
                                        .setStyle(new NotificationCompat.BigTextStyle().bigText(displaytext))
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                Notification notification = builder1.build();
                                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                                notificationManager.notify(0, notification);
                            }
                        });
                    }
                });
            }









        } catch (Exception ignored) {
        }
    }

    public void noti_cmnts(final String comment, final String userid, final String username, final String imgsig) {
        try {


            final Intent intent = new Intent(this, Lists_ChinthaComments.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            //sound for notification

            final Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if(comment.length()>=151)
            {
                displaytext=comment.substring(0,150)+"...";
            }
            else
            {
                displaytext=comment+"...";
            }

            if (Build.VERSION.SDK_INT < 26) {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        RequestOptions rep=new RequestOptions().signature(new ObjectKey(imgsig));
                        Glide.with(getApplicationContext()).asBitmap().apply(rep).load(Static_Variable.entypoint1 + "userphotosmall/" + userid + ".jpg").into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                                Notification builder1 = new Notification.Builder(FCM_MESSAGES.this)
                                        .setContentTitle(username+ " Commented on")
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setSmallIcon(R.drawable.img_notilogo)
                                        .setLargeIcon(bitmap)
                                        .setStyle(new Notification.BigTextStyle().bigText(displaytext))
                                        .setContentIntent(pendingIntent)
                                        .build();
                                final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.notify(0, builder1);

                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                Notification builder1 = new Notification.Builder(FCM_MESSAGES.this)
                                        .setContentTitle(username+" Commented on")
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setSmallIcon(R.drawable.img_notilogo)
                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img_notilogo))
                                        .setStyle(new Notification.BigTextStyle().bigText(displaytext))
                                        .build();
                                final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.notify(0, builder1);
                            }
                        });
                    }
                });
            }
            else
            {

                CharSequence name = "StatusApp";
                String description = "StatusApp_Disc";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("StatusAppChannel", name, importance);
                channel.setDescription(description);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        RequestOptions rep=new RequestOptions().signature(new ObjectKey(imgsig));
                        Glide.with(getApplicationContext()).asBitmap().apply(rep).load(Static_Variable.entypoint1 + "userphotosmall/" + userid + ".jpg").into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                                NotificationCompat.Builder builder1 = new NotificationCompat.Builder(FCM_MESSAGES.this,"StatusAppChannel")
                                        .setContentTitle(username+ " Commented on")
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setSmallIcon(R.drawable.img_notilogo)
                                        .setLargeIcon(bitmap)
                                        .setStyle(new NotificationCompat.BigTextStyle().bigText(displaytext))
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                                Notification notification = builder1.build();
                                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                                notificationManager.notify(0, notification);

                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                NotificationCompat.Builder builder1 = new NotificationCompat.Builder(FCM_MESSAGES.this,"StatusAppChannel")
                                        .setContentTitle(username+" Commented on")
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true)
                                        .setSound(soundUri)
                                        .setSmallIcon(R.drawable.img_notilogo)
                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img_notilogo))
                                        .setStyle(new NotificationCompat.BigTextStyle().bigText(displaytext))
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                                Notification notification = builder1.build();
                                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                                notificationManager.notify(0, notification);
                            }
                        });
                    }
                });
            }




        } catch (Exception ignored) {

            // Log.e("error",ignored.toString());
        }
    }


    public void loadstatus()
    {
        new status_load().execute();
    }

    public void loadreply()
    {
        new replay_load().execute();
    }


    public class status_load extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {


        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                String link = Static_Variable.entypoint1 + "getstatusnotification.php";
                String data = URLEncoder.encode("statusid", "UTF-8")
                        + "=" + URLEncoder.encode(txtstatusid + "", "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {

                    sb.append(line);

                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {

                if (result.contains(":%ok")) {
                    try {
                        String[] m = result.split(":%");

                        byte[] data = Base64.decode(m[0], Base64.DEFAULT);
                        String text2 = new String(data, StandardCharsets.UTF_8);

                        dataDb1.add_cmntdetails(txtstatusid, text2);

                        final JSONObject json = new JSONObject(msg);

                        ArrayList<String> id2 = dataDb1.getcommentdetails(json.getString("statusid"));
                        String[] c2 = id2.toArray(new String[id2.size()]);

                        String text1 = c2[1];

                        if (userDataDB.get_userid().equalsIgnoreCase(json.getString("userid"))) {

                        } else {


                            if (dataDb1.get_cmntvisible().equalsIgnoreCase("0")) {


                                if (dataDb1.get_cmntnoti().equalsIgnoreCase("1")) {


                                    if (System.currentTimeMillis() > notitime) {

                                        if (dataDb2.get_notidisabled(json.getString("statusid")).equalsIgnoreCase("")) {
                                            dataDb1.deletecmntdetails();
                                            dataDb1.add_cmntdtails(json.getString("statusid"),json.getString("statususerid"),json.getString("statusname"),text1,json.getString("statusimgsig"),json.getString("statustype"),json.getString("photourl"),json.getString("photodim"));
                                            noti_cmnts(text1,json.getString("userid"),json.getString("name"),json.getString("statusimgsig"));
                                        }

                                    }

                                }
                            }
                        }
                        if (!json.getString("userid").equalsIgnoreCase(userDataDB.get_userid())) {
                            if (dataDb1.getcomments(json.getString("statusid")).equalsIgnoreCase("")) {

                                if (dataDb2.get_notidisabled(json.getString("statusid")).equalsIgnoreCase("")) {
                                    dataDb1.add_notilist(json.getString("statusid"), "1", json.getString("name"), "1", text1,json.getString("userid"), "0",json.getString("statususerid"),json.getString("statusname"),json.getString("statusimgsig"),json.getString("statustype"),json.getString("photourl"),"");

                                }

                            } else {
                                if (dataDb1.get_comments1(json.getString("userid")).equalsIgnoreCase("")) {
                                    if (dataDb2.get_notidisabled(json.getString("statusid")).equalsIgnoreCase("")) {
                                        dataDb1.update_cmnts(json.getString("statusid"), json.getString("name"), text1,json.getString("userid"), json.getString("statususerid"),json.getString("statusname"),json.getString("statusimgsig"));
                                    }
                                } else {
                                    if (dataDb2.get_notidisabled(json.getString("statusid")).equalsIgnoreCase("")) {
                                        dataDb1.updatecomments1(json.getString("statusid"), json.getString("name"), text1, json.getString("statususerid"), json.getString("statusname"),json.getString("statusimgsig"));
                                    }
                                }


                            }

                        }

                        notitime = System.currentTimeMillis() + 3000;

                    } catch (Exception a) {

                    }


                }

            } catch (Exception a) {

            }

        }

    }


    public class replay_load extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {

        }
        @Override
        protected String doInBackground(String... arg0) {

            try {

                String link = Static_Variable.entypoint1 + "getcommentnotification.php";
                String data = URLEncoder.encode("statusid", "UTF-8")
                        + "=" + URLEncoder.encode(txtreplayid + "", "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        @Override
        protected void onPostExecute(String result) {
            try {
                if (result.contains(":%ok")) {
                    try {
                        String[] m = result.split(":%");
                        byte[] data = Base64.decode(m[0], Base64.DEFAULT);
                        String text2 = new String(data, StandardCharsets.UTF_8);
                        dataDb2.add_storecmnt(txtreplayid,text2);
                        final JSONObject json = new JSONObject(msg);
                        ArrayList<String> id2 = dataDb2.get_storecmnt(json.getString("cmntid"));
                        String[] c2 = id2.toArray(new String[id2.size()]);
                        String text1 = c2[1];
                        if (userDataDB.get_userid().equalsIgnoreCase(json.getString("userid"))) {

                        } else {
                            if (dataDb2.get_rplyvisible().equalsIgnoreCase("")) {
                                if (dataDb1.get_cmntnoti().equalsIgnoreCase("1")) {

                                    if (System.currentTimeMillis() > notitime) {

                                        if (dataDb2.get_disablerply(json.getString("cmntid")).equalsIgnoreCase("")) {

                                            dataDb2.add_replycmnt(json.getString("cmntid"),json.getString("rplyuserid"),json.getString("rplyname"), Static_Variable.entypoint1 +"userphotosmall/"+json.getString("userid")+".jpg",text1,json.getString("replyimgsig"));

                                            gen_reply(text1,json.getString("userid"),json.getString("name"),json.getString("replyimgsig"));

                                        }

                                    }
                                }
                            }
                        }

                        if (!json.getString("userid").equalsIgnoreCase(userDataDB.get_userid())) {
                            if (dataDb1.get_reply(json.getString("cmntid")).equalsIgnoreCase("")) {
                                //String notiogid1,String noti_type1,String noti_lastname1,String noti_count1,String noti_text1,String noti_userid1,String isread1
                                if (dataDb2.get_disablerply(json.getString("cmntid")).equalsIgnoreCase("")) {
                                    dataDb1.add_notilist(json.getString("cmntid"), "2", json.getString("name"), "1", text1, json.getString("userid"), "0", json.getString("rplyuserid"),json.getString("rplyname"),json.getString("replyimgsig"),"0","0","0");

                                }
                            } else {
                                if (dataDb1.getreply1(json.getString("userid")).equalsIgnoreCase("")) {
                                    if (dataDb2.get_disablerply(json.getString("cmntid")).equalsIgnoreCase("")) {
                                        dataDb1.updatereplay(json.getString("cmntid"),json.getString("name"), text1,json.getString("userid"),json.getString("rplyuserid"),json.getString("rplyname"),json.getString("replyimgsig"));

                                    }
                                } else {
                                    if (dataDb2.get_disablerply(json.getString("cmntid")).equalsIgnoreCase("")) {
                                        dataDb1.updatereplay1(json.getString("cmntid"), json.getString("name"), text1, json.getString("rplyuserid"),json.getString("rplyname"),json.getString("replyimgsig"));
                                    }
                                }
                            }
                        }
                        notitime = System.currentTimeMillis() + 3000;

                    } catch (Exception a) {

                    }
                }

            } catch (Exception a) {
            }
        }
    }
}
