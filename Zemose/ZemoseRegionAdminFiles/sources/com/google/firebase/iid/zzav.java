package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import java.util.ArrayDeque;
import java.util.Queue;
import javax.annotation.concurrent.GuardedBy;

public final class zzav {
    private static zzav zzcx;
    @GuardedBy("serviceClassNames")
    private final SimpleArrayMap<String, String> zzcy = new SimpleArrayMap<>();
    private Boolean zzcz = null;
    @VisibleForTesting
    final Queue<Intent> zzda = new ArrayDeque();
    @VisibleForTesting
    private final Queue<Intent> zzdb = new ArrayDeque();

    public static synchronized zzav zzai() {
        zzav zzav;
        synchronized (zzav.class) {
            if (zzcx == null) {
                zzcx = new zzav();
            }
            zzav = zzcx;
        }
        return zzav;
    }

    private zzav() {
    }

    public static PendingIntent zza(Context context, int i, Intent intent, int i2) {
        return PendingIntent.getBroadcast(context, i, zza(context, "com.google.firebase.MESSAGING_EVENT", intent), 1073741824);
    }

    public static void zzb(Context context, Intent intent) {
        context.sendBroadcast(zza(context, "com.google.firebase.INSTANCE_ID_EVENT", intent));
    }

    public static void zzc(Context context, Intent intent) {
        context.sendBroadcast(zza(context, "com.google.firebase.MESSAGING_EVENT", intent));
    }

    private static Intent zza(Context context, String str, Intent intent) {
        Intent intent2 = new Intent(context, FirebaseInstanceIdReceiver.class);
        intent2.setAction(str);
        intent2.putExtra("wrapped_intent", intent);
        return intent2;
    }

    public final Intent zzaj() {
        return (Intent) this.zzdb.poll();
    }

    public final int zzb(Context context, String str, Intent intent) {
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String str2 = "FirebaseInstanceId";
            String str3 = "Starting service: ";
            String valueOf = String.valueOf(str);
            Log.d(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -842411455) {
            if (hashCode == 41532704 && str.equals("com.google.firebase.MESSAGING_EVENT")) {
                c = 1;
            }
        } else if (str.equals("com.google.firebase.INSTANCE_ID_EVENT")) {
            c = 0;
        }
        switch (c) {
            case 0:
                this.zzda.offer(intent);
                break;
            case 1:
                this.zzdb.offer(intent);
                break;
            default:
                String str4 = "FirebaseInstanceId";
                String str5 = "Unknown service action: ";
                String valueOf2 = String.valueOf(str);
                Log.w(str4, valueOf2.length() != 0 ? str5.concat(valueOf2) : new String(str5));
                return 500;
        }
        Intent intent2 = new Intent(str);
        intent2.setPackage(context.getPackageName());
        return zzd(context, intent2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x00e3 A[Catch:{ SecurityException -> 0x0143, IllegalStateException -> 0x011b }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00fd A[Catch:{ SecurityException -> 0x0143, IllegalStateException -> 0x011b }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0102 A[Catch:{ SecurityException -> 0x0143, IllegalStateException -> 0x011b }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x010f A[Catch:{ SecurityException -> 0x0143, IllegalStateException -> 0x011b }] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0119  */
    private final int zzd(Context context, Intent intent) {
        String str;
        ComponentName componentName;
        synchronized (this.zzcy) {
            str = (String) this.zzcy.get(intent.getAction());
        }
        boolean z = false;
        if (str == null) {
            ResolveInfo resolveService = context.getPackageManager().resolveService(intent, 0);
            if (resolveService == null || resolveService.serviceInfo == null) {
                Log.e("FirebaseInstanceId", "Failed to resolve target intent service, skipping classname enforcement");
                if (this.zzcz == null) {
                    if (context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0) {
                        z = true;
                    }
                    this.zzcz = Boolean.valueOf(z);
                }
                if (this.zzcz.booleanValue()) {
                    componentName = WakefulBroadcastReceiver.startWakefulService(context, intent);
                } else {
                    componentName = context.startService(intent);
                    Log.d("FirebaseInstanceId", "Missing wake lock permission, service start may be delayed");
                }
                if (componentName != null) {
                    return -1;
                }
                Log.e("FirebaseInstanceId", "Error while delivering the message: ServiceIntent not found.");
                return 404;
            }
            ServiceInfo serviceInfo = resolveService.serviceInfo;
            if (!context.getPackageName().equals(serviceInfo.packageName) || serviceInfo.name == null) {
                String str2 = serviceInfo.packageName;
                String str3 = serviceInfo.name;
                StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 94 + String.valueOf(str3).length());
                sb.append("Error resolving target intent service, skipping classname enforcement. Resolved service was: ");
                sb.append(str2);
                sb.append("/");
                sb.append(str3);
                Log.e("FirebaseInstanceId", sb.toString());
                if (this.zzcz == null) {
                }
                if (this.zzcz.booleanValue()) {
                }
                if (componentName != null) {
                }
            } else {
                str = serviceInfo.name;
                if (str.startsWith(".")) {
                    String valueOf = String.valueOf(context.getPackageName());
                    String valueOf2 = String.valueOf(str);
                    str = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
                }
                synchronized (this.zzcy) {
                    this.zzcy.put(intent.getAction(), str);
                }
            }
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String str4 = "FirebaseInstanceId";
            String str5 = "Restricting intent to a specific service: ";
            String valueOf3 = String.valueOf(str);
            Log.d(str4, valueOf3.length() != 0 ? str5.concat(valueOf3) : new String(str5));
        }
        intent.setClassName(context.getPackageName(), str);
        try {
            if (this.zzcz == null) {
            }
            if (this.zzcz.booleanValue()) {
            }
            if (componentName != null) {
            }
        } catch (SecurityException e) {
            Log.e("FirebaseInstanceId", "Error while delivering the message to the serviceIntent", e);
            return 401;
        } catch (IllegalStateException e2) {
            String valueOf4 = String.valueOf(e2);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf4).length() + 45);
            sb2.append("Failed to start service while in background: ");
            sb2.append(valueOf4);
            Log.e("FirebaseInstanceId", sb2.toString());
            return 402;
        }
    }
}
