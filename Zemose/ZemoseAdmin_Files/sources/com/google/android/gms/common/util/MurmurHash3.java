package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
public class MurmurHash3 {
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x009b, code lost:
        return r5 ^ (r5 >>> 16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x005f, code lost:
        r6 = r6 | ((r5[r0 + 1] & 255) << 8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x006b, code lost:
        r5 = ((r5[r0] & 255) | r6) * -862048943;
        r8 = r8 ^ (((r5 >>> 17) | (r5 << 15)) * 461845907);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x007f, code lost:
        r5 = r8 ^ r7;
        r5 = (r5 ^ (r5 >>> 16)) * -2048144789;
        r5 = (r5 ^ (r5 >>> 13)) * -1028477387;
     */
    @KeepForSdk
    public static int murmurhash3_x86_32(byte[] bArr, int i, int i2, int i3) {
        int i4 = (i2 & -4) + i;
        while (i < i4) {
            int i5 = ((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | (bArr[i + 3] << 24)) * -862048943;
            int i6 = i3 ^ (((i5 << 15) | (i5 >>> 17)) * 461845907);
            i3 = (((i6 >>> 19) | (i6 << 13)) * 5) - 430675100;
            i += 4;
        }
        int i7 = 0;
        switch (i2 & 3) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                i7 = (bArr[i4 + 2] & 255) << 16;
                break;
        }
    }

    private MurmurHash3() {
    }
}
