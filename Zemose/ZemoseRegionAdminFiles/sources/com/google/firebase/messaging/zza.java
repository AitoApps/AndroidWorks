package com.google.firebase.messaging;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.firebase.iid.zzav;
import com.payu.custombrowser.util.CBConstant;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;

final class zza {
    private static final AtomicInteger zzdn = new AtomicInteger((int) SystemClock.elapsedRealtime());
    private Bundle zzdo;
    private final Context zzx;

    public zza(Context context) {
        this.zzx = context.getApplicationContext();
    }

    static boolean zzf(Bundle bundle) {
        return "1".equals(zza(bundle, "gcm.n.e")) || zza(bundle, "gcm.n.icon") != null;
    }

    static String zza(Bundle bundle, String str) {
        String string = bundle.getString(str);
        if (string == null) {
            return bundle.getString(str.replace("gcm.n.", "gcm.notification."));
        }
        return string;
    }

    static String zzb(Bundle bundle, String str) {
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf("_loc_key");
        return zza(bundle, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    static Object[] zzc(Bundle bundle, String str) {
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf("_loc_args");
        String zza = zza(bundle, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        if (TextUtils.isEmpty(zza)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(zza);
            Object[] objArr = new String[jSONArray.length()];
            for (int i = 0; i < objArr.length; i++) {
                objArr[i] = jSONArray.opt(i);
            }
            return objArr;
        } catch (JSONException e) {
            String str2 = "FirebaseMessaging";
            String valueOf3 = String.valueOf(str);
            String valueOf4 = String.valueOf("_loc_args");
            String substring = (valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3)).substring(6);
            StringBuilder sb = new StringBuilder(String.valueOf(substring).length() + 41 + String.valueOf(zza).length());
            sb.append("Malformed ");
            sb.append(substring);
            sb.append(": ");
            sb.append(zza);
            sb.append("  Default value will be used.");
            Log.w(str2, sb.toString());
            return null;
        }
    }

    @Nullable
    static Uri zzg(@NonNull Bundle bundle) {
        String zza = zza(bundle, "gcm.n.link_android");
        if (TextUtils.isEmpty(zza)) {
            zza = zza(bundle, "gcm.n.link");
        }
        if (!TextUtils.isEmpty(zza)) {
            return Uri.parse(zza);
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x0317  */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0347  */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x0352  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x0365  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0370  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x0377  */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x037e  */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x0385  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x039d  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x03b6  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x013c  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x013e  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x01a7  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01bb  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01f6  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x01f8  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0245  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0247  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0255  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x028c  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x02b8  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x02f8  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0308  */
    public final boolean zzh(Bundle bundle) {
        boolean z;
        int i;
        Integer zzl;
        String zzi;
        String str;
        Uri uri;
        String zza;
        Intent intent;
        PendingIntent pendingIntent;
        boolean z2;
        PendingIntent pendingIntent2;
        String zza2;
        String zza3;
        NotificationManager notificationManager;
        String string;
        Bundle bundle2 = bundle;
        if ("1".equals(zza(bundle2, "gcm.n.noui"))) {
            return true;
        }
        if (!((KeyguardManager) this.zzx.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            if (!PlatformVersion.isAtLeastLollipop()) {
                SystemClock.sleep(10);
            }
            int myPid = Process.myPid();
            List runningAppProcesses = ((ActivityManager) this.zzx.getSystemService("activity")).getRunningAppProcesses();
            if (runningAppProcesses != null) {
                Iterator it = runningAppProcesses.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    RunningAppProcessInfo runningAppProcessInfo = (RunningAppProcessInfo) it.next();
                    if (runningAppProcessInfo.pid == myPid) {
                        z = runningAppProcessInfo.importance == 100;
                    }
                }
            }
        }
        z = false;
        if (z) {
            return false;
        }
        CharSequence zzd = zzd(bundle2, "gcm.n.title");
        if (TextUtils.isEmpty(zzd)) {
            zzd = this.zzx.getApplicationInfo().loadLabel(this.zzx.getPackageManager());
        }
        String zzd2 = zzd(bundle2, "gcm.n.body");
        String zza4 = zza(bundle2, "gcm.n.icon");
        if (!TextUtils.isEmpty(zza4)) {
            Resources resources = this.zzx.getResources();
            i = resources.getIdentifier(zza4, "drawable", this.zzx.getPackageName());
            if (i == 0 || !zzb(i)) {
                i = resources.getIdentifier(zza4, "mipmap", this.zzx.getPackageName());
                if (i == 0 || !zzb(i)) {
                    StringBuilder sb = new StringBuilder(String.valueOf(zza4).length() + 61);
                    sb.append("Icon resource ");
                    sb.append(zza4);
                    sb.append(" not found. Notification will use default icon.");
                    Log.w("FirebaseMessaging", sb.toString());
                }
            }
            zzl = zzl(zza(bundle2, "gcm.n.color"));
            zzi = zzi(bundle);
            str = null;
            if (!TextUtils.isEmpty(zzi)) {
                uri = null;
            } else if (CBConstant.DEFAULT_VALUE.equals(zzi) || this.zzx.getResources().getIdentifier(zzi, "raw", this.zzx.getPackageName()) == 0) {
                uri = RingtoneManager.getDefaultUri(2);
            } else {
                String packageName = this.zzx.getPackageName();
                StringBuilder sb2 = new StringBuilder(String.valueOf(packageName).length() + 24 + String.valueOf(zzi).length());
                sb2.append("android.resource://");
                sb2.append(packageName);
                sb2.append("/raw/");
                sb2.append(zzi);
                uri = Uri.parse(sb2.toString());
            }
            zza = zza(bundle2, "gcm.n.click_action");
            if (TextUtils.isEmpty(zza)) {
                intent = new Intent(zza);
                intent.setPackage(this.zzx.getPackageName());
                intent.setFlags(268435456);
            } else {
                Uri zzg = zzg(bundle);
                if (zzg != null) {
                    intent = new Intent("android.intent.action.VIEW");
                    intent.setPackage(this.zzx.getPackageName());
                    intent.setData(zzg);
                } else {
                    intent = this.zzx.getPackageManager().getLaunchIntentForPackage(this.zzx.getPackageName());
                    if (intent == null) {
                        Log.w("FirebaseMessaging", "No activity found to launch app");
                    }
                }
            }
            if (intent != null) {
                pendingIntent = null;
            } else {
                intent.addFlags(67108864);
                Bundle bundle3 = new Bundle(bundle2);
                FirebaseMessagingService.zzj(bundle3);
                intent.putExtras(bundle3);
                for (String str2 : bundle3.keySet()) {
                    if (str2.startsWith("gcm.n.") || str2.startsWith("gcm.notification.")) {
                        intent.removeExtra(str2);
                    }
                }
                pendingIntent = PendingIntent.getActivity(this.zzx, zzdn.incrementAndGet(), intent, 1073741824);
            }
            if (bundle2 != null) {
                z2 = false;
            } else {
                z2 = "1".equals(bundle2.getString("google.c.a.e"));
            }
            if (!z2) {
                Intent intent2 = new Intent("com.google.firebase.messaging.NOTIFICATION_OPEN");
                zza(intent2, bundle2);
                intent2.putExtra("pending_intent", pendingIntent);
                pendingIntent = zzav.zza(this.zzx, zzdn.incrementAndGet(), intent2, 1073741824);
                Intent intent3 = new Intent("com.google.firebase.messaging.NOTIFICATION_DISMISS");
                zza(intent3, bundle2);
                pendingIntent2 = zzav.zza(this.zzx, zzdn.incrementAndGet(), intent3, 1073741824);
            } else {
                pendingIntent2 = null;
            }
            zza2 = zza(bundle2, "gcm.n.android_channel_id");
            if (PlatformVersion.isAtLeastO() && this.zzx.getApplicationInfo().targetSdkVersion >= 26) {
                notificationManager = (NotificationManager) this.zzx.getSystemService(NotificationManager.class);
                if (!TextUtils.isEmpty(zza2)) {
                    if (notificationManager.getNotificationChannel(zza2) != null) {
                        str = zza2;
                    } else {
                        StringBuilder sb3 = new StringBuilder(String.valueOf(zza2).length() + 122);
                        sb3.append("Notification Channel requested (");
                        sb3.append(zza2);
                        sb3.append(") has not been created by the app. Manifest configuration, or default, value will be used.");
                        Log.w("FirebaseMessaging", sb3.toString());
                    }
                }
                string = zzas().getString("com.google.firebase.messaging.default_notification_channel_id");
                if (!TextUtils.isEmpty(string)) {
                    Log.w("FirebaseMessaging", "Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
                } else if (notificationManager.getNotificationChannel(string) != null) {
                    str = string;
                } else {
                    Log.w("FirebaseMessaging", "Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
                }
                if (notificationManager.getNotificationChannel("fcm_fallback_notification_channel") == null) {
                    notificationManager.createNotificationChannel(new NotificationChannel("fcm_fallback_notification_channel", this.zzx.getString(R.string.fcm_fallback_notification_channel_label), 3));
                }
                str = "fcm_fallback_notification_channel";
            }
            Builder smallIcon = new Builder(this.zzx).setAutoCancel(true).setSmallIcon(i);
            if (!TextUtils.isEmpty(zzd)) {
                smallIcon.setContentTitle(zzd);
            }
            if (!TextUtils.isEmpty(zzd2)) {
                smallIcon.setContentText(zzd2);
                smallIcon.setStyle(new BigTextStyle().bigText(zzd2));
            }
            if (zzl != null) {
                smallIcon.setColor(zzl.intValue());
            }
            if (uri != null) {
                smallIcon.setSound(uri);
            }
            if (pendingIntent != null) {
                smallIcon.setContentIntent(pendingIntent);
            }
            if (pendingIntent2 != null) {
                smallIcon.setDeleteIntent(pendingIntent2);
            }
            if (str != null) {
                smallIcon.setChannelId(str);
            }
            Notification build = smallIcon.build();
            zza3 = zza(bundle2, "gcm.n.tag");
            if (Log.isLoggable("FirebaseMessaging", 3)) {
                Log.d("FirebaseMessaging", "Showing notification");
            }
            NotificationManager notificationManager2 = (NotificationManager) this.zzx.getSystemService("notification");
            if (TextUtils.isEmpty(zza3)) {
                long uptimeMillis = SystemClock.uptimeMillis();
                StringBuilder sb4 = new StringBuilder(37);
                sb4.append("FCM-Notification:");
                sb4.append(uptimeMillis);
                zza3 = sb4.toString();
            }
            notificationManager2.notify(zza3, 0, build);
            return true;
        }
        int i2 = zzas().getInt("com.google.firebase.messaging.default_notification_icon", 0);
        if (i2 == 0 || !zzb(i2)) {
            i2 = this.zzx.getApplicationInfo().icon;
        }
        if (i2 == 0 || !zzb(i2)) {
            i = 17301651;
        } else {
            i = i2;
        }
        zzl = zzl(zza(bundle2, "gcm.n.color"));
        zzi = zzi(bundle);
        str = null;
        if (!TextUtils.isEmpty(zzi)) {
        }
        zza = zza(bundle2, "gcm.n.click_action");
        if (TextUtils.isEmpty(zza)) {
        }
        if (intent != null) {
        }
        if (bundle2 != null) {
        }
        if (!z2) {
        }
        zza2 = zza(bundle2, "gcm.n.android_channel_id");
        notificationManager = (NotificationManager) this.zzx.getSystemService(NotificationManager.class);
        if (!TextUtils.isEmpty(zza2)) {
        }
        string = zzas().getString("com.google.firebase.messaging.default_notification_channel_id");
        if (!TextUtils.isEmpty(string)) {
        }
        if (notificationManager.getNotificationChannel("fcm_fallback_notification_channel") == null) {
        }
        str = "fcm_fallback_notification_channel";
        Builder smallIcon2 = new Builder(this.zzx).setAutoCancel(true).setSmallIcon(i);
        if (!TextUtils.isEmpty(zzd)) {
        }
        if (!TextUtils.isEmpty(zzd2)) {
        }
        if (zzl != null) {
        }
        if (uri != null) {
        }
        if (pendingIntent != null) {
        }
        if (pendingIntent2 != null) {
        }
        if (str != null) {
        }
        Notification build2 = smallIcon2.build();
        zza3 = zza(bundle2, "gcm.n.tag");
        if (Log.isLoggable("FirebaseMessaging", 3)) {
        }
        NotificationManager notificationManager22 = (NotificationManager) this.zzx.getSystemService("notification");
        if (TextUtils.isEmpty(zza3)) {
        }
        notificationManager22.notify(zza3, 0, build2);
        return true;
    }

    private final String zzd(Bundle bundle, String str) {
        String zza = zza(bundle, str);
        if (!TextUtils.isEmpty(zza)) {
            return zza;
        }
        String zzb = zzb(bundle, str);
        if (TextUtils.isEmpty(zzb)) {
            return null;
        }
        Resources resources = this.zzx.getResources();
        int identifier = resources.getIdentifier(zzb, "string", this.zzx.getPackageName());
        if (identifier == 0) {
            String str2 = "FirebaseMessaging";
            String valueOf = String.valueOf(str);
            String valueOf2 = String.valueOf("_loc_key");
            String substring = (valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)).substring(6);
            StringBuilder sb = new StringBuilder(String.valueOf(substring).length() + 49 + String.valueOf(zzb).length());
            sb.append(substring);
            sb.append(" resource not found: ");
            sb.append(zzb);
            sb.append(" Default value will be used.");
            Log.w(str2, sb.toString());
            return null;
        }
        Object[] zzc = zzc(bundle, str);
        if (zzc == null) {
            return resources.getString(identifier);
        }
        try {
            return resources.getString(identifier, zzc);
        } catch (MissingFormatArgumentException e) {
            String arrays = Arrays.toString(zzc);
            StringBuilder sb2 = new StringBuilder(String.valueOf(zzb).length() + 58 + String.valueOf(arrays).length());
            sb2.append("Missing format argument for ");
            sb2.append(zzb);
            sb2.append(": ");
            sb2.append(arrays);
            sb2.append(" Default value will be used.");
            Log.w("FirebaseMessaging", sb2.toString(), e);
            return null;
        }
    }

    @TargetApi(26)
    private final boolean zzb(int i) {
        if (VERSION.SDK_INT != 26) {
            return true;
        }
        try {
            if (!(this.zzx.getResources().getDrawable(i, null) instanceof AdaptiveIconDrawable)) {
                return true;
            }
            StringBuilder sb = new StringBuilder(77);
            sb.append("Adaptive icons cannot be used in notifications. Ignoring icon id: ");
            sb.append(i);
            Log.e("FirebaseMessaging", sb.toString());
            return false;
        } catch (NotFoundException e) {
            return false;
        }
    }

    private final Integer zzl(String str) {
        if (VERSION.SDK_INT < 21) {
            return null;
        }
        if (!TextUtils.isEmpty(str)) {
            try {
                return Integer.valueOf(Color.parseColor(str));
            } catch (IllegalArgumentException e) {
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 54);
                sb.append("Color ");
                sb.append(str);
                sb.append(" not valid. Notification will use default color.");
                Log.w("FirebaseMessaging", sb.toString());
            }
        }
        int i = zzas().getInt("com.google.firebase.messaging.default_notification_color", 0);
        if (i != 0) {
            try {
                return Integer.valueOf(ContextCompat.getColor(this.zzx, i));
            } catch (NotFoundException e2) {
                Log.w("FirebaseMessaging", "Cannot find the color resource referenced in AndroidManifest.");
            }
        }
        return null;
    }

    static String zzi(Bundle bundle) {
        String zza = zza(bundle, "gcm.n.sound2");
        if (TextUtils.isEmpty(zza)) {
            return zza(bundle, "gcm.n.sound");
        }
        return zza;
    }

    private static void zza(Intent intent, Bundle bundle) {
        for (String str : bundle.keySet()) {
            if (str.startsWith("google.c.a.") || str.equals("from")) {
                intent.putExtra(str, bundle.getString(str));
            }
        }
    }

    private final Bundle zzas() {
        Bundle bundle = this.zzdo;
        if (bundle != null) {
            return bundle;
        }
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = this.zzx.getPackageManager().getApplicationInfo(this.zzx.getPackageName(), 128);
        } catch (NameNotFoundException e) {
        }
        if (applicationInfo == null || applicationInfo.metaData == null) {
            return Bundle.EMPTY;
        }
        this.zzdo = applicationInfo.metaData;
        return this.zzdo;
    }
}
