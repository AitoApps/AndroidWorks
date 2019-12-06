package com.payumoney.graphics.helpers;

import android.content.Context;
import com.google.gson.Gson;
import com.payumoney.graphics.AssetDownloadManager.Environment;
import com.payumoney.graphics.AssetsHelper;
import com.payumoney.graphics.AssetsHelper.MASTERPASS;
import com.payumoney.graphics.classes.PGSettingIdentifier;
import com.payumoney.graphics.enums.ScreenDensity;

class Utils {
    private static String environment = null;

    Utils() {
    }

    static ScreenDensity getScreenDensity(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        if (((double) density) >= 3.0d) {
            return ScreenDensity.XXHDPI;
        }
        if (((double) density) >= 2.0d) {
            return ScreenDensity.XHDPI;
        }
        return ScreenDensity.HDPI;
    }

    static void setEnvironment(String env) {
        environment = env;
    }

    static PGSettingIdentifier getPGSettingIdentifier(String json) {
        return (PGSettingIdentifier) new Gson().fromJson(json, PGSettingIdentifier.class);
    }

    static String getBankURL(String bankCode, ScreenDensity screenDensity) {
        String density = screenDensity.name().toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append(getBaseurl());
        sb.append("/bank/");
        sb.append(density);
        sb.append("/");
        sb.append(bankCode);
        sb.append(".png");
        return sb.toString();
    }

    static String getWalletURL(String bankCode, ScreenDensity screenDensity) {
        String density = screenDensity.name().toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append(getBaseurl());
        sb.append("/thirdparty/");
        sb.append(density);
        sb.append("/");
        sb.append(bankCode);
        sb.append(".png");
        return sb.toString();
    }

    static String getLargeWalletURL(String bankCode, ScreenDensity screenDensity) {
        String density = screenDensity.name().toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append(getBaseurl());
        sb.append("/thirdparty-large/");
        sb.append(density);
        sb.append("/");
        sb.append(bankCode);
        sb.append(".png");
        return sb.toString();
    }

    static String getLargeBankURL(String bankCode, ScreenDensity screenDensity) {
        String density = screenDensity.name().toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append(getBaseurl());
        sb.append("/bank-large/");
        sb.append(density);
        sb.append("/");
        sb.append(bankCode);
        sb.append(".png");
        return sb.toString();
    }

    static String getCardURL(String bankCode, ScreenDensity screenDensity) {
        String density = screenDensity.name().toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append(getBaseurl());
        sb.append("/card/");
        sb.append(density);
        sb.append("/");
        sb.append(bankCode);
        sb.append(".png");
        return sb.toString();
    }

    static String getLargeCardURL(String bankCode, ScreenDensity screenDensity) {
        String density = screenDensity.name().toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append(getBaseurl());
        sb.append("/card-large/");
        sb.append(density);
        sb.append("/");
        sb.append(bankCode);
        sb.append(".png");
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    static String getMasterPassURL(String resourceName, ScreenDensity screenDensity) {
        char c;
        int hashCode = resourceName.hashCode();
        if (hashCode == -2014622151) {
            if (resourceName.equals(MASTERPASS.BUTTON)) {
                c = 0;
                switch (c) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        } else if (hashCode == -842253695) {
            if (resourceName.equals(MASTERPASS.GRAYBG)) {
                c = 2;
                switch (c) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        } else if (hashCode == -380091977 && resourceName.equals(MASTERPASS.COLORBG)) {
            c = 1;
            switch (c) {
                case 0:
                    StringBuilder sb = new StringBuilder();
                    sb.append(getBaseurl());
                    sb.append("/card-btn/xxhdpi/");
                    sb.append(resourceName);
                    sb.append(".png");
                    return sb.toString();
                case 1:
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(getBaseurl());
                    sb2.append("/card/xxhdpi/MPASSW.png");
                    return sb2.toString();
                case 2:
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(getBaseurl());
                    sb3.append("/card-gs/xxhdpi/MPASSW.png");
                    return sb3.toString();
                default:
                    return null;
            }
        }
        c = 65535;
        switch (c) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }

    static String getBrandingURL(String branding, ScreenDensity screenDensity) {
        String density = screenDensity.name().toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append(getBaseurl());
        sb.append("/branding/");
        sb.append(density);
        sb.append("/");
        sb.append(branding);
        sb.append(".png");
        return sb.toString();
    }

    static String getDensity(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        if (((double) density) >= 3.0d) {
            return AssetsHelper.ScreenDensity.XXHDPI;
        }
        if (((double) density) >= 2.0d) {
            return AssetsHelper.ScreenDensity.XHDPI;
        }
        return AssetsHelper.ScreenDensity.HDPI;
    }

    static String getBaseurl() {
        if (Environment.SANDBOX.equalsIgnoreCase(environment)) {
            return "https://s3.ap-south-1.amazonaws.com/citrus-mobile-sandbox/assets";
        }
        return "https://s3-ap-south-1.amazonaws.com/mobilestatic";
    }
}
