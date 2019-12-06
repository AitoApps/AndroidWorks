package com.google.firebase.iid;

import android.support.annotation.GuardedBy;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.io.IOException;
import java.util.Map;

final class zzba {
    @GuardedBy("itself")
    private final zzaw zzaj;
    @GuardedBy("this")
    private int zzdl = 0;
    @GuardedBy("this")
    private final Map<Integer, TaskCompletionSource<Void>> zzdm = new ArrayMap();

    zzba(zzaw zzaw) {
        this.zzaj = zzaw;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized Task<Void> zza(String str) {
        String zzak;
        TaskCompletionSource taskCompletionSource;
        int i;
        synchronized (this.zzaj) {
            zzak = this.zzaj.zzak();
            zzaw zzaw = this.zzaj;
            StringBuilder sb = new StringBuilder(String.valueOf(zzak).length() + 1 + String.valueOf(str).length());
            sb.append(zzak);
            sb.append(",");
            sb.append(str);
            zzaw.zzf(sb.toString());
        }
        taskCompletionSource = new TaskCompletionSource();
        Map<Integer, TaskCompletionSource<Void>> map = this.zzdm;
        if (TextUtils.isEmpty(zzak)) {
            i = 0;
        } else {
            i = zzak.split(",").length - 1;
        }
        map.put(Integer.valueOf(this.zzdl + i), taskCompletionSource);
        return taskCompletionSource.getTask();
    }

    /* access modifiers changed from: 0000 */
    public final synchronized boolean zzaq() {
        return zzar() != null;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
        if (zza(r5, r0) != false) goto L_0x0022;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0021, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0022, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r2 = (com.google.android.gms.tasks.TaskCompletionSource) r4.zzdm.remove(java.lang.Integer.valueOf(r4.zzdl));
        zzk(r0);
        r4.zzdl++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0039, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003a, code lost:
        if (r2 == null) goto L_0x0000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003c, code lost:
        r2.setResult(null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0018, code lost:
        return true;
     */
    @WorkerThread
    public final boolean zzc(FirebaseInstanceId firebaseInstanceId) {
        while (true) {
            synchronized (this) {
                String zzar = zzar();
                if (zzar == null) {
                    if (FirebaseInstanceId.zzl()) {
                        Log.d("FirebaseInstanceId", "topic sync succeeded");
                    }
                }
            }
        }
        while (true) {
        }
    }

    @Nullable
    @GuardedBy("this")
    private final String zzar() {
        String zzak;
        synchronized (this.zzaj) {
            zzak = this.zzaj.zzak();
        }
        if (!TextUtils.isEmpty(zzak)) {
            String[] split = zzak.split(",");
            if (split.length > 1 && !TextUtils.isEmpty(split[1])) {
                return split[1];
            }
        }
        return null;
    }

    private final synchronized boolean zzk(String str) {
        synchronized (this.zzaj) {
            String zzak = this.zzaj.zzak();
            String valueOf = String.valueOf(",");
            String valueOf2 = String.valueOf(str);
            if (!zzak.startsWith(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf))) {
                return false;
            }
            String valueOf3 = String.valueOf(",");
            String valueOf4 = String.valueOf(str);
            this.zzaj.zzf(zzak.substring((valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3)).length()));
            return true;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0034 A[Catch:{ IOException -> 0x005a }] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0035 A[Catch:{ IOException -> 0x005a }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0047 A[Catch:{ IOException -> 0x005a }] */
    @WorkerThread
    private static boolean zza(FirebaseInstanceId firebaseInstanceId, String str) {
        String[] split = str.split("!");
        if (split.length == 2) {
            String str2 = split[0];
            String str3 = split[1];
            char c = 65535;
            try {
                int hashCode = str2.hashCode();
                if (hashCode == 83) {
                    if (str2.equals("S")) {
                        c = 0;
                        switch (c) {
                            case 0:
                                break;
                            case 1:
                                break;
                        }
                    }
                } else if (hashCode == 85 && str2.equals("U")) {
                    c = 1;
                    switch (c) {
                        case 0:
                            firebaseInstanceId.zzb(str3);
                            if (FirebaseInstanceId.zzl()) {
                                Log.d("FirebaseInstanceId", "subscribe operation succeeded");
                                break;
                            }
                            break;
                        case 1:
                            firebaseInstanceId.zzc(str3);
                            if (!FirebaseInstanceId.zzl()) {
                                break;
                            } else {
                                Log.d("FirebaseInstanceId", "unsubscribe operation succeeded");
                                break;
                            }
                    }
                }
                switch (c) {
                    case 0:
                        break;
                    case 1:
                        break;
                }
            } catch (IOException e) {
                String str4 = "FirebaseInstanceId";
                String str5 = "Topic sync failed: ";
                String valueOf = String.valueOf(e.getMessage());
                Log.e(str4, valueOf.length() != 0 ? str5.concat(valueOf) : new String(str5));
                return false;
            }
        }
        return true;
    }
}
