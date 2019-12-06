package com.google.firebase.iid;

import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.tasks.Task;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

final class zzaq {
    private final Executor zzbj;
    @GuardedBy("this")
    private final Map<Pair<String, String>, Task<String>> zzco = new ArrayMap();

    zzaq(Executor executor) {
        this.zzbj = executor;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0040, code lost:
        return r4;
     */
    public final synchronized Task<String> zza(String str, String str2, zzas zzas) {
        Pair pair = new Pair(str, str2);
        Task<String> task = (Task) this.zzco.get(pair);
        if (task == null) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String valueOf = String.valueOf(pair);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
                sb.append("Making new request for: ");
                sb.append(valueOf);
                Log.d("FirebaseInstanceId", sb.toString());
            }
            Task<String> continueWithTask = zzas.zzs().continueWithTask(this.zzbj, new zzar(this, pair));
            this.zzco.put(pair, continueWithTask);
            return continueWithTask;
        } else if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String valueOf2 = String.valueOf(pair);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 29);
            sb2.append("Joining ongoing request for: ");
            sb2.append(valueOf2);
            Log.d("FirebaseInstanceId", sb2.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Task zza(Pair pair, Task task) throws Exception {
        synchronized (this) {
            this.zzco.remove(pair);
        }
        return task;
    }
}
