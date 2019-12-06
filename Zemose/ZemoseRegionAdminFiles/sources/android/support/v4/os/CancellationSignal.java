package android.support.v4.os;

import android.os.Build.VERSION;

public final class CancellationSignal {
    private boolean mCancelInProgress;
    private Object mCancellationSignalObj;
    private boolean mIsCanceled;
    private OnCancelListener mOnCancelListener;

    public interface OnCancelListener {
        void onCancel();
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this) {
            z = this.mIsCanceled;
        }
        return z;
    }

    public void throwIfCanceled() {
        if (isCanceled()) {
            throw new OperationCanceledException();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0014, code lost:
        if (r2 == null) goto L_0x001d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r2.onCancel();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x001a, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x001d, code lost:
        if (r0 == null) goto L_0x0038;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0023, code lost:
        if (android.os.Build.VERSION.SDK_INT < 16) goto L_0x0038;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0025, code lost:
        ((android.os.CancellationSignal) r0).cancel();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x002c, code lost:
        monitor-enter(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r5.mCancelInProgress = false;
        notifyAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0033, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0038, code lost:
        monitor-enter(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        r5.mCancelInProgress = false;
        notifyAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x003e, code lost:
        monitor-exit(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0040, code lost:
        return;
     */
    public void cancel() {
        synchronized (this) {
            try {
                if (!this.mIsCanceled) {
                    this.mIsCanceled = true;
                    this.mCancelInProgress = true;
                    OnCancelListener listener = this.mOnCancelListener;
                    try {
                        Object obj = this.mCancellationSignalObj;
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    public void setOnCancelListener(OnCancelListener listener) {
        synchronized (this) {
            waitForCancelFinishedLocked();
            if (this.mOnCancelListener != listener) {
                this.mOnCancelListener = listener;
                if (this.mIsCanceled) {
                    if (listener != null) {
                        listener.onCancel();
                    }
                }
            }
        }
    }

    public Object getCancellationSignalObject() {
        Object obj;
        if (VERSION.SDK_INT < 16) {
            return null;
        }
        synchronized (this) {
            if (this.mCancellationSignalObj == null) {
                this.mCancellationSignalObj = new android.os.CancellationSignal();
                if (this.mIsCanceled) {
                    ((android.os.CancellationSignal) this.mCancellationSignalObj).cancel();
                }
            }
            obj = this.mCancellationSignalObj;
        }
        return obj;
    }

    private void waitForCancelFinishedLocked() {
        while (this.mCancelInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }
}
