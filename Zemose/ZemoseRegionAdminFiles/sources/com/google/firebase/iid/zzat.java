package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.iid.zzl.zza;
import com.payumoney.core.utils.AnalyticsConstant;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.concurrent.GuardedBy;

final class zzat {
    private static int zzcf = 0;
    private static PendingIntent zzcr;
    private final zzan zzan;
    @GuardedBy("responseCallbacks")
    private final SimpleArrayMap<String, TaskCompletionSource<Bundle>> zzcs = new SimpleArrayMap<>();
    private Messenger zzct;
    private Messenger zzcu;
    private zzl zzcv;
    private final Context zzx;

    public zzat(Context context, zzan zzan2) {
        this.zzx = context;
        this.zzan = zzan2;
        this.zzct = new Messenger(new zzau(this, Looper.getMainLooper()));
    }

    /* access modifiers changed from: private */
    public final void zzb(Message message) {
        if (message == null || !(message.obj instanceof Intent)) {
            Log.w("FirebaseInstanceId", "Dropping invalid message");
        } else {
            Intent intent = (Intent) message.obj;
            intent.setExtrasClassLoader(new zza());
            if (intent.hasExtra("google.messenger")) {
                Parcelable parcelableExtra = intent.getParcelableExtra("google.messenger");
                if (parcelableExtra instanceof zzl) {
                    this.zzcv = (zzl) parcelableExtra;
                }
                if (parcelableExtra instanceof Messenger) {
                    this.zzcu = (Messenger) parcelableExtra;
                }
            }
            Intent intent2 = (Intent) message.obj;
            String action = intent2.getAction();
            if (!"com.google.android.c2dm.intent.REGISTRATION".equals(action)) {
                if (Log.isLoggable("FirebaseInstanceId", 3)) {
                    String str = "FirebaseInstanceId";
                    String str2 = "Unexpected response action: ";
                    String valueOf = String.valueOf(action);
                    Log.d(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                }
                return;
            }
            String stringExtra = intent2.getStringExtra("registration_id");
            if (stringExtra == null) {
                stringExtra = intent2.getStringExtra("unregistered");
            }
            if (stringExtra == null) {
                String stringExtra2 = intent2.getStringExtra(AnalyticsConstant.ERROR);
                if (stringExtra2 == null) {
                    String valueOf2 = String.valueOf(intent2.getExtras());
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf2).length() + 49);
                    sb.append("Unexpected response, no error or registration id ");
                    sb.append(valueOf2);
                    Log.w("FirebaseInstanceId", sb.toString());
                } else {
                    if (Log.isLoggable("FirebaseInstanceId", 3)) {
                        String str3 = "FirebaseInstanceId";
                        String str4 = "Received InstanceID error ";
                        String valueOf3 = String.valueOf(stringExtra2);
                        Log.d(str3, valueOf3.length() != 0 ? str4.concat(valueOf3) : new String(str4));
                    }
                    if (stringExtra2.startsWith("|")) {
                        String[] split = stringExtra2.split("\\|");
                        if (split.length <= 2 || !"ID".equals(split[1])) {
                            String str5 = "FirebaseInstanceId";
                            String str6 = "Unexpected structured response ";
                            String valueOf4 = String.valueOf(stringExtra2);
                            Log.w(str5, valueOf4.length() != 0 ? str6.concat(valueOf4) : new String(str6));
                        } else {
                            String str7 = split[2];
                            String str8 = split[3];
                            if (str8.startsWith(":")) {
                                str8 = str8.substring(1);
                            }
                            zza(str7, intent2.putExtra(AnalyticsConstant.ERROR, str8).getExtras());
                        }
                    } else {
                        synchronized (this.zzcs) {
                            for (int i = 0; i < this.zzcs.size(); i++) {
                                zza((String) this.zzcs.keyAt(i), intent2.getExtras());
                            }
                        }
                    }
                }
            } else {
                Matcher matcher = Pattern.compile("\\|ID\\|([^|]+)\\|:?+(.*)").matcher(stringExtra);
                if (!matcher.matches()) {
                    if (Log.isLoggable("FirebaseInstanceId", 3)) {
                        String str9 = "FirebaseInstanceId";
                        String str10 = "Unexpected response string: ";
                        String valueOf5 = String.valueOf(stringExtra);
                        Log.d(str9, valueOf5.length() != 0 ? str10.concat(valueOf5) : new String(str10));
                    }
                    return;
                }
                String group = matcher.group(1);
                String group2 = matcher.group(2);
                Bundle extras = intent2.getExtras();
                extras.putString("registration_id", group2);
                zza(group, extras);
            }
        }
    }

    private static synchronized void zza(Context context, Intent intent) {
        synchronized (zzat.class) {
            if (zzcr == null) {
                Intent intent2 = new Intent();
                intent2.setPackage("com.google.example.invalidpackage");
                zzcr = PendingIntent.getBroadcast(context, 0, intent2, 0);
            }
            intent.putExtra("app", zzcr);
        }
    }

    private final void zza(String str, Bundle bundle) {
        synchronized (this.zzcs) {
            TaskCompletionSource taskCompletionSource = (TaskCompletionSource) this.zzcs.remove(str);
            if (taskCompletionSource == null) {
                String str2 = "FirebaseInstanceId";
                String str3 = "Missing callback for ";
                String valueOf = String.valueOf(str);
                Log.w(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                return;
            }
            taskCompletionSource.setResult(bundle);
        }
    }

    /* access modifiers changed from: 0000 */
    public final Bundle zzc(Bundle bundle) throws IOException {
        if (this.zzan.zzaf() < 12000000) {
            return zzd(bundle);
        }
        try {
            return (Bundle) Tasks.await(zzab.zzc(this.zzx).zzb(1, bundle));
        } catch (InterruptedException | ExecutionException e) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String valueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 22);
                sb.append("Error making request: ");
                sb.append(valueOf);
                Log.d("FirebaseInstanceId", sb.toString());
            }
            if (!(e.getCause() instanceof zzal) || ((zzal) e.getCause()).getErrorCode() != 4) {
                return null;
            }
            return zzd(bundle);
        }
    }

    private final Bundle zzd(Bundle bundle) throws IOException {
        Bundle zze = zze(bundle);
        if (zze == null || !zze.containsKey("google.messenger")) {
            return zze;
        }
        Bundle zze2 = zze(bundle);
        if (zze2 == null || !zze2.containsKey("google.messenger")) {
            return zze2;
        }
        return null;
    }

    private static synchronized String zzah() {
        String num;
        synchronized (zzat.class) {
            int i = zzcf;
            zzcf = i + 1;
            num = Integer.toString(i);
        }
        return num;
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x00f7 A[SYNTHETIC] */
    private final Bundle zze(Bundle bundle) throws IOException {
        String zzah = zzah();
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        synchronized (this.zzcs) {
            this.zzcs.put(zzah, taskCompletionSource);
        }
        if (this.zzan.zzac() != 0) {
            Intent intent = new Intent();
            intent.setPackage("com.google.android.gms");
            if (this.zzan.zzac() == 2) {
                intent.setAction("com.google.iid.TOKEN_REQUEST");
            } else {
                intent.setAction("com.google.android.c2dm.intent.REGISTER");
            }
            intent.putExtras(bundle);
            zza(this.zzx, intent);
            StringBuilder sb = new StringBuilder(String.valueOf(zzah).length() + 5);
            sb.append("|ID|");
            sb.append(zzah);
            sb.append("|");
            intent.putExtra("kid", sb.toString());
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String valueOf = String.valueOf(intent.getExtras());
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 8);
                sb2.append("Sending ");
                sb2.append(valueOf);
                Log.d("FirebaseInstanceId", sb2.toString());
            }
            intent.putExtra("google.messenger", this.zzct);
            if (!(this.zzcu == null && this.zzcv == null)) {
                Message obtain = Message.obtain();
                obtain.obj = intent;
                try {
                    if (this.zzcu != null) {
                        this.zzcu.send(obtain);
                    } else {
                        this.zzcv.send(obtain);
                    }
                } catch (RemoteException e) {
                    if (Log.isLoggable("FirebaseInstanceId", 3)) {
                        Log.d("FirebaseInstanceId", "Messenger failed, fallback to startService");
                    }
                }
                Bundle bundle2 = (Bundle) Tasks.await(taskCompletionSource.getTask(), 30000, TimeUnit.MILLISECONDS);
                synchronized (this.zzcs) {
                    this.zzcs.remove(zzah);
                }
                return bundle2;
            }
            if (this.zzan.zzac() == 2) {
                this.zzx.sendBroadcast(intent);
            } else {
                this.zzx.startService(intent);
            }
            try {
                Bundle bundle22 = (Bundle) Tasks.await(taskCompletionSource.getTask(), 30000, TimeUnit.MILLISECONDS);
                synchronized (this.zzcs) {
                }
                return bundle22;
            } catch (InterruptedException e2) {
                Log.w("FirebaseInstanceId", "No response");
                throw new IOException("TIMEOUT");
            } catch (TimeoutException e3) {
                Log.w("FirebaseInstanceId", "No response");
                throw new IOException("TIMEOUT");
            } catch (ExecutionException e4) {
                throw new IOException(e4);
            } catch (Throwable th) {
                synchronized (this.zzcs) {
                    this.zzcs.remove(zzah);
                    throw th;
                }
            }
        } else {
            throw new IOException("MISSING_INSTANCEID_SERVICE");
        }
    }
}
