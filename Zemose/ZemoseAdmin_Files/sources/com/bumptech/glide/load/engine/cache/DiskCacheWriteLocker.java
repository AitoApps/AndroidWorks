package com.bumptech.glide.load.engine.cache;

import com.bumptech.glide.util.Preconditions;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class DiskCacheWriteLocker {
    private final Map<String, WriteLock> locks = new HashMap();
    private final WriteLockPool writeLockPool = new WriteLockPool();

    private static class WriteLock {
        int interestedThreads;
        final Lock lock = new ReentrantLock();

        WriteLock() {
        }
    }

    private static class WriteLockPool {
        private static final int MAX_POOL_SIZE = 10;
        private final Queue<WriteLock> pool = new ArrayDeque();

        WriteLockPool() {
        }

        /* access modifiers changed from: 0000 */
        public WriteLock obtain() {
            WriteLock result;
            synchronized (this.pool) {
                result = (WriteLock) this.pool.poll();
            }
            if (result == null) {
                return new WriteLock();
            }
            return result;
        }

        /* access modifiers changed from: 0000 */
        public void offer(WriteLock writeLock) {
            synchronized (this.pool) {
                if (this.pool.size() < 10) {
                    this.pool.offer(writeLock);
                }
            }
        }
    }

    DiskCacheWriteLocker() {
    }

    /* access modifiers changed from: 0000 */
    public void acquire(String safeKey) {
        WriteLock writeLock;
        synchronized (this) {
            writeLock = (WriteLock) this.locks.get(safeKey);
            if (writeLock == null) {
                writeLock = this.writeLockPool.obtain();
                this.locks.put(safeKey, writeLock);
            }
            writeLock.interestedThreads++;
        }
        writeLock.lock.lock();
    }

    /* access modifiers changed from: 0000 */
    public void release(String safeKey) {
        WriteLock writeLock;
        synchronized (this) {
            writeLock = (WriteLock) Preconditions.checkNotNull(this.locks.get(safeKey));
            if (writeLock.interestedThreads >= 1) {
                writeLock.interestedThreads--;
                if (writeLock.interestedThreads == 0) {
                    WriteLock removed = (WriteLock) this.locks.remove(safeKey);
                    if (removed.equals(writeLock)) {
                        this.writeLockPool.offer(removed);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Removed the wrong lock, expected to remove: ");
                        sb.append(writeLock);
                        sb.append(", but actually removed: ");
                        sb.append(removed);
                        sb.append(", safeKey: ");
                        sb.append(safeKey);
                        throw new IllegalStateException(sb.toString());
                    }
                }
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Cannot release a lock that is not held, safeKey: ");
                sb2.append(safeKey);
                sb2.append(", interestedThreads: ");
                sb2.append(writeLock.interestedThreads);
                throw new IllegalStateException(sb2.toString());
            }
        }
        writeLock.lock.unlock();
    }
}
