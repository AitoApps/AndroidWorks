package com.google.firebase.messaging;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.iid.zzab;
import com.google.firebase.iid.zzav;
import com.google.firebase.iid.zzb;
import com.payumoney.core.utils.AnalyticsConstant;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FirebaseMessagingService extends zzb {
    private static final Queue<String> zzdr = new ArrayDeque(10);

    @WorkerThread
    public void onMessageReceived(RemoteMessage remoteMessage) {
    }

    @WorkerThread
    public void onDeletedMessages() {
    }

    @WorkerThread
    public void onMessageSent(String str) {
    }

    @WorkerThread
    public void onSendError(String str, Exception exc) {
    }

    @WorkerThread
    public void onNewToken(String str) {
    }

    /* access modifiers changed from: protected */
    public final Intent zzb(Intent intent) {
        return zzav.zzai().zzaj();
    }

    public final boolean zzc(Intent intent) {
        if (!"com.google.firebase.messaging.NOTIFICATION_OPEN".equals(intent.getAction())) {
            return false;
        }
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("pending_intent");
        if (pendingIntent != null) {
            try {
                pendingIntent.send();
            } catch (CanceledException e) {
                Log.e("FirebaseMessaging", "Notification pending intent canceled");
            }
        }
        if (MessagingAnalytics.shouldUploadMetrics(intent)) {
            MessagingAnalytics.logNotificationOpen(intent);
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0104, code lost:
        if (r0.equals("send_event") == false) goto L_0x0125;
     */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0129  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x013d  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x015f  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0169  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x016d  */
    public final void zzd(Intent intent) {
        Task task;
        boolean z;
        String str;
        String action = intent.getAction();
        if ("com.google.android.c2dm.intent.RECEIVE".equals(action) || "com.google.firebase.messaging.RECEIVE_DIRECT_BOOT".equals(action)) {
            String stringExtra = intent.getStringExtra("google.message_id");
            char c = 2;
            if (TextUtils.isEmpty(stringExtra)) {
                task = Tasks.forResult(null);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("google.message_id", stringExtra);
                task = zzab.zzc(this).zza(2, bundle);
            }
            if (TextUtils.isEmpty(stringExtra)) {
                z = false;
            } else if (zzdr.contains(stringExtra)) {
                if (Log.isLoggable("FirebaseMessaging", 3)) {
                    String str2 = "FirebaseMessaging";
                    String str3 = "Received duplicate message: ";
                    String valueOf = String.valueOf(stringExtra);
                    Log.d(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                }
                z = true;
            } else {
                if (zzdr.size() >= 10) {
                    zzdr.remove();
                }
                zzdr.add(stringExtra);
                z = false;
            }
            if (!z) {
                String stringExtra2 = intent.getStringExtra("message_type");
                if (stringExtra2 == null) {
                    stringExtra2 = "gcm";
                }
                int hashCode = stringExtra2.hashCode();
                if (hashCode == -2062414158) {
                    if (stringExtra2.equals("deleted_messages")) {
                        c = 1;
                        switch (c) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                        }
                    }
                } else if (hashCode == 102161) {
                    if (stringExtra2.equals("gcm")) {
                        c = 0;
                        switch (c) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                        }
                    }
                } else if (hashCode == 814694033) {
                    if (stringExtra2.equals("send_error")) {
                        c = 3;
                        switch (c) {
                            case 0:
                                if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                    MessagingAnalytics.logNotificationReceived(intent);
                                }
                                Bundle extras = intent.getExtras();
                                if (extras == null) {
                                    extras = new Bundle();
                                }
                                extras.remove("android.support.content.wakelockid");
                                if (zza.zzf(extras)) {
                                    if (!new zza(this).zzh(extras)) {
                                        if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                            MessagingAnalytics.logNotificationForeground(intent);
                                        }
                                    }
                                }
                                onMessageReceived(new RemoteMessage(extras));
                                break;
                            case 1:
                                onDeletedMessages();
                                break;
                            case 2:
                                onMessageSent(intent.getStringExtra("google.message_id"));
                                break;
                            case 3:
                                String stringExtra3 = intent.getStringExtra("google.message_id");
                                if (stringExtra3 == null) {
                                    stringExtra3 = intent.getStringExtra("message_id");
                                }
                                onSendError(stringExtra3, new SendException(intent.getStringExtra(AnalyticsConstant.ERROR)));
                                break;
                            default:
                                String str4 = "FirebaseMessaging";
                                String str5 = "Received message with unknown type: ";
                                String valueOf2 = String.valueOf(stringExtra2);
                                if (valueOf2.length() != 0) {
                                    str = str5.concat(valueOf2);
                                } else {
                                    str = new String(str5);
                                }
                                Log.w(str4, str);
                                break;
                        }
                    }
                } else if (hashCode == 814800675) {
                }
                c = 65535;
                switch (c) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }
            try {
                Tasks.await(task, 1, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                String valueOf3 = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf3).length() + 20);
                sb.append("Message ack failed: ");
                sb.append(valueOf3);
                Log.w("FirebaseMessaging", sb.toString());
            }
        } else {
            if ("com.google.firebase.messaging.NOTIFICATION_DISMISS".equals(action)) {
                if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                    MessagingAnalytics.logNotificationDismiss(intent);
                }
            } else if ("com.google.firebase.messaging.NEW_TOKEN".equals(action)) {
                onNewToken(intent.getStringExtra("token"));
            } else {
                String str6 = "FirebaseMessaging";
                String str7 = "Unknown intent action: ";
                String valueOf4 = String.valueOf(intent.getAction());
                Log.d(str6, valueOf4.length() != 0 ? str7.concat(valueOf4) : new String(str7));
            }
        }
    }

    static void zzj(Bundle bundle) {
        Iterator it = bundle.keySet().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str != null && str.startsWith("google.c.")) {
                it.remove();
            }
        }
    }
}
