package com.google.android.gms.stats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import android.os.WorkSource;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.providers.PooledExecutorsProvider;
import com.google.android.gms.common.stats.StatsUtils;
import com.google.android.gms.common.stats.WakeLockTracker;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.common.util.WorkSourceUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.ThreadSafe;

@ShowFirstParty
@ThreadSafe
@KeepForSdk
public class WakeLock {
    private static ScheduledExecutorService zzn;
    private static volatile zza zzo = new zza();
    private final Object zza;
    private final android.os.PowerManager.WakeLock zzb;
    private WorkSource zzc;
    private final int zzd;
    private final String zze;
    private final String zzf;
    private final String zzg;
    private final Context zzh;
    private boolean zzi;
    private final Map<String, Integer[]> zzj;
    private final Set<Future<?>> zzk;
    private int zzl;
    private AtomicInteger zzm;

    public interface zza {
    }

    @KeepForSdk
    public WakeLock(@NonNull Context context, int i, @NonNull String str) {
        this(context, i, str, null, context == null ? null : context.getPackageName());
    }

    private WakeLock(@NonNull Context context, int i, @NonNull String str, @Nullable String str2, @NonNull String str3) {
        this(context, i, str, null, str3, null);
    }

    @SuppressLint({"UnwrappedWakeLock"})
    private WakeLock(@NonNull Context context, int i, @NonNull String str, @Nullable String str2, @NonNull String str3, @Nullable String str4) {
        this.zza = this;
        this.zzi = true;
        this.zzj = new HashMap();
        this.zzk = Collections.synchronizedSet(new HashSet());
        this.zzm = new AtomicInteger(0);
        Preconditions.checkNotNull(context, "WakeLock: context must not be null");
        Preconditions.checkNotEmpty(str, "WakeLock: wakeLockName must not be empty");
        this.zzd = i;
        this.zzf = null;
        this.zzg = null;
        this.zzh = context.getApplicationContext();
        if (!"com.google.android.gms".equals(context.getPackageName())) {
            String valueOf = String.valueOf("*gcore*:");
            String valueOf2 = String.valueOf(str);
            this.zze = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        } else {
            this.zze = str;
        }
        this.zzb = ((PowerManager) context.getSystemService("power")).newWakeLock(i, str);
        if (WorkSourceUtil.hasWorkSourcePermission(context)) {
            if (Strings.isEmptyOrWhitespace(str3)) {
                str3 = context.getPackageName();
            }
            this.zzc = WorkSourceUtil.fromPackage(context, str3);
            WorkSource workSource = this.zzc;
            if (workSource != null && WorkSourceUtil.hasWorkSourcePermission(this.zzh)) {
                WorkSource workSource2 = this.zzc;
                if (workSource2 != null) {
                    workSource2.add(workSource);
                } else {
                    this.zzc = workSource;
                }
                try {
                    this.zzb.setWorkSource(this.zzc);
                } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                    Log.wtf("WakeLock", e.toString());
                }
            }
        }
        if (zzn == null) {
            zzn = PooledExecutorsProvider.getInstance().newSingleThreadScheduledExecutor();
        }
    }

    private final List<String> zza() {
        return WorkSourceUtil.getNames(this.zzc);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0064, code lost:
        if (r13.zzl == 0) goto L_0x0066;
     */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0062  */
    @KeepForSdk
    public void acquire(long j) {
        this.zzm.incrementAndGet();
        String zza2 = zza((String) null);
        synchronized (this.zza) {
            boolean z = false;
            if (this.zzj.isEmpty()) {
                if (this.zzl > 0) {
                }
                if (this.zzi) {
                    Integer[] numArr = (Integer[]) this.zzj.get(zza2);
                    if (numArr == null) {
                        this.zzj.put(zza2, new Integer[]{Integer.valueOf(1)});
                        z = true;
                    } else {
                        numArr[0] = Integer.valueOf(numArr[0].intValue() + 1);
                    }
                    if (!z) {
                    }
                    WakeLockTracker.getInstance().registerEvent(this.zzh, StatsUtils.getEventKey(this.zzb, zza2), 7, this.zze, zza2, null, this.zzd, zza(), j);
                    this.zzl++;
                }
                if (!this.zzi) {
                }
            }
            if (!this.zzb.isHeld()) {
                this.zzj.clear();
                this.zzl = 0;
                if (this.zzi) {
                }
                if (!this.zzi) {
                }
            }
            if (this.zzi) {
            }
            if (!this.zzi) {
            }
        }
        this.zzb.acquire();
        if (j > 0) {
            zzn.schedule(new zzb(this), j, TimeUnit.MILLISECONDS);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005f, code lost:
        if (r12.zzl == 1) goto L_0x0061;
     */
    @KeepForSdk
    public void release() {
        boolean z;
        if (this.zzm.decrementAndGet() < 0) {
            Log.e("WakeLock", String.valueOf(this.zze).concat(" release without a matched acquire!"));
        }
        String zza2 = zza((String) null);
        synchronized (this.zza) {
            if (this.zzi) {
                Integer[] numArr = (Integer[]) this.zzj.get(zza2);
                if (numArr == null) {
                    z = false;
                } else if (numArr[0].intValue() == 1) {
                    this.zzj.remove(zza2);
                    z = true;
                } else {
                    numArr[0] = Integer.valueOf(numArr[0].intValue() - 1);
                    z = false;
                }
                if (!z) {
                }
                WakeLockTracker.getInstance().registerEvent(this.zzh, StatsUtils.getEventKey(this.zzb, zza2), 8, this.zze, zza2, null, this.zzd, zza());
                this.zzl--;
            }
            if (!this.zzi) {
            }
        }
        zza(0);
    }

    /* access modifiers changed from: private */
    public final void zza(int i) {
        if (this.zzb.isHeld()) {
            try {
                this.zzb.release();
            } catch (RuntimeException e) {
                if (e.getClass().equals(RuntimeException.class)) {
                    Log.e("WakeLock", String.valueOf(this.zze).concat(" was already released!"), e);
                } else {
                    throw e;
                }
            }
            this.zzb.isHeld();
        }
    }

    private final String zza(String str) {
        return (!this.zzi || TextUtils.isEmpty(str)) ? this.zzf : str;
    }

    @KeepForSdk
    public void setReferenceCounted(boolean z) {
        this.zzb.setReferenceCounted(z);
        this.zzi = z;
    }

    @KeepForSdk
    public boolean isHeld() {
        return this.zzb.isHeld();
    }
}
