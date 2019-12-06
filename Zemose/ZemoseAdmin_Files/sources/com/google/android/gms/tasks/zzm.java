package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

final class zzm<TResult> implements zzq<TResult> {
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private final Executor zzd;
    /* access modifiers changed from: private */
    @GuardedBy("mLock")
    public OnSuccessListener<? super TResult> zzp;

    public zzm(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzd = executor;
        this.zzp = onSuccessListener;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0010, code lost:
        r2.zzd.execute(new com.google.android.gms.tasks.zzn(r2, r3));
     */
    public final void onComplete(@NonNull Task<TResult> task) {
        if (task.isSuccessful()) {
            synchronized (this.mLock) {
                if (this.zzp == null) {
                }
            }
        }
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzp = null;
        }
    }
}
