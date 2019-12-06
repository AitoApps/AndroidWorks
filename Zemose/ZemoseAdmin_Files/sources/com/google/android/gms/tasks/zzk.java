package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

final class zzk<TResult> implements zzq<TResult> {
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private final Executor zzd;
    /* access modifiers changed from: private */
    @GuardedBy("mLock")
    public OnFailureListener zzn;

    public zzk(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzd = executor;
        this.zzn = onFailureListener;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0016, code lost:
        r2.zzd.execute(new com.google.android.gms.tasks.zzl(r2, r3));
     */
    public final void onComplete(@NonNull Task<TResult> task) {
        if (!task.isSuccessful() && !task.isCanceled()) {
            synchronized (this.mLock) {
                if (this.zzn == null) {
                }
            }
        }
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzn = null;
        }
    }
}
