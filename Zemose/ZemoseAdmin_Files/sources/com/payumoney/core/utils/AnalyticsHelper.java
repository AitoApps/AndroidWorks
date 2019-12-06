package com.payumoney.core.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.webkit.WebView;
import com.payumoney.core.PayUmoneyConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.UUID;

public class AnalyticsHelper {
    public String getDeviceID(Context applicationContext) {
        return Secure.getString(applicationContext.getContentResolver(), "android_id");
    }

    public String getUUID() {
        return UUID.randomUUID().toString();
    }

    public String getUserAgent(Context applicationContext) {
        return new WebView(applicationContext).getSettings().getUserAgentString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x005f A[Catch:{ Exception -> 0x0078 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0075 A[Catch:{ Exception -> 0x0078 }] */
    public String getLatitude(Context applicationContext) {
        Location location;
        Location location2;
        try {
            LocationManager locationManager = (LocationManager) applicationContext.getSystemService("location");
            boolean isProviderEnabled = locationManager.isProviderEnabled("gps");
            boolean isProviderEnabled2 = locationManager.isProviderEnabled("network");
            if (ActivityCompat.checkSelfPermission(applicationContext, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                if (ActivityCompat.checkSelfPermission(applicationContext, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                    return "";
                }
            }
            if (isProviderEnabled) {
                location = locationManager.getLastKnownLocation("gps");
            } else {
                location = null;
            }
            if (isProviderEnabled2) {
                location2 = locationManager.getLastKnownLocation("network");
            } else {
                location2 = null;
            }
            if (location == null || location2 == null) {
                if (location == null) {
                    if (location2 == null) {
                        location2 = null;
                    }
                    if (location2 == null) {
                        return "";
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(location2.getLatitude());
                    sb.append("");
                    return sb.toString();
                }
            } else if (location.getAccuracy() > location2.getAccuracy()) {
                if (location2 == null) {
                }
            }
            location2 = location;
            if (location2 == null) {
            }
        } catch (Exception e) {
            return "";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x005f A[Catch:{ Exception -> 0x0078 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0075 A[Catch:{ Exception -> 0x0078 }] */
    public String getLongitude(Context applicationContext) {
        Location location;
        Location location2;
        try {
            LocationManager locationManager = (LocationManager) applicationContext.getSystemService("location");
            boolean isProviderEnabled = locationManager.isProviderEnabled("gps");
            boolean isProviderEnabled2 = locationManager.isProviderEnabled("network");
            if (ActivityCompat.checkSelfPermission(applicationContext, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                if (ActivityCompat.checkSelfPermission(applicationContext, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                    return "";
                }
            }
            if (isProviderEnabled) {
                location = locationManager.getLastKnownLocation("gps");
            } else {
                location = null;
            }
            if (isProviderEnabled2) {
                location2 = locationManager.getLastKnownLocation("network");
            } else {
                location2 = null;
            }
            if (location == null || location2 == null) {
                if (location == null) {
                    if (location2 == null) {
                        location2 = null;
                    }
                    if (location2 == null) {
                        return "";
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(location2.getLongitude());
                    sb.append("");
                    return sb.toString();
                }
            } else if (location.getAccuracy() > location2.getAccuracy()) {
                if (location2 == null) {
                }
            }
            location2 = location;
            if (location2 == null) {
            }
        } catch (Exception e) {
            return "";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x005f A[Catch:{ Exception -> 0x0078 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0075 A[Catch:{ Exception -> 0x0078 }] */
    public String getAccuracy(Context applicationContext) {
        Location location;
        Location location2;
        try {
            LocationManager locationManager = (LocationManager) applicationContext.getSystemService("location");
            boolean isProviderEnabled = locationManager.isProviderEnabled("gps");
            boolean isProviderEnabled2 = locationManager.isProviderEnabled("network");
            if (ActivityCompat.checkSelfPermission(applicationContext, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                if (ActivityCompat.checkSelfPermission(applicationContext, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                    return "";
                }
            }
            if (isProviderEnabled) {
                location = locationManager.getLastKnownLocation("gps");
            } else {
                location = null;
            }
            if (isProviderEnabled2) {
                location2 = locationManager.getLastKnownLocation("network");
            } else {
                location2 = null;
            }
            if (location == null || location2 == null) {
                if (location == null) {
                    if (location2 == null) {
                        location2 = null;
                    }
                    if (location2 == null) {
                        return "";
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(location2.getAccuracy());
                    sb.append("");
                    return sb.toString();
                }
            } else if (location.getAccuracy() > location2.getAccuracy()) {
                if (location2 == null) {
                }
            }
            location2 = location;
            if (location2 == null) {
            }
        } catch (Exception e) {
            return "";
        }
    }

    public String getLocale() {
        return Locale.getDefault().getLanguage();
    }

    public String getLanguage() {
        return Locale.getDefault().getDisplayLanguage();
    }

    public String getCountryCode(Context context) {
        String networkCountryIso = ((TelephonyManager) context.getSystemService("phone")).getNetworkCountryIso();
        if (networkCountryIso.equals("")) {
            return "Unknown";
        }
        return networkCountryIso;
    }

    public static String getNetworkType(Context context) {
        if (context != null) {
            try {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    if (activeNetworkInfo.isConnected()) {
                        if (activeNetworkInfo.getType() == 1) {
                            return "WIFI";
                        }
                        if (activeNetworkInfo.getType() == 0) {
                            switch (activeNetworkInfo.getSubtype()) {
                                case 1:
                                    return "GPRS";
                                case 2:
                                    return "EDGE";
                                case 3:
                                case 5:
                                case 6:
                                case 8:
                                case 9:
                                case 10:
                                    return "HSPA";
                                case 4:
                                    return "CDMA";
                                case 7:
                                case 11:
                                    return "2G";
                                case 12:
                                case 14:
                                case 15:
                                    return "3G";
                                case 13:
                                    return "4G";
                                default:
                                    return "Mobile";
                            }
                        }
                    }
                }
                return "Not connected";
            } catch (Exception e) {
                return "NA";
            }
        }
        return "NA";
    }

    public static boolean isFingerPrintHardwareAvailable(Context context) {
        try {
            if (FingerprintManagerCompat.from(context).isHardwareDetected()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDeviceRooted() {
        return a() || b() || c();
    }

    private static boolean a() {
        String str = Build.TAGS;
        return str != null && str.contains("test-keys");
    }

    private static boolean b() {
        for (String file : new String[]{"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"}) {
            if (new File(file).exists()) {
                return true;
            }
        }
        return false;
    }

    private static boolean c() {
        Process process = null;
        try {
            Process exec = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            if (new BufferedReader(new InputStreamReader(exec.getInputStream())).readLine() != null) {
                if (exec != null) {
                    exec.destroy();
                }
                return true;
            }
            if (exec != null) {
                exec.destroy();
            }
            return false;
        } catch (Throwable th) {
            if (process != null) {
                process.destroy();
            }
            throw th;
        }
    }

    private int a(Context context, NetworkInfo networkInfo) {
        int i = 0;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (VERSION.SDK_INT >= 18) {
                int i2 = 0;
                for (CellInfo cellInfo : telephonyManager.getAllCellInfo()) {
                    if (cellInfo.isRegistered()) {
                        if (cellInfo instanceof CellInfoGsm) {
                            i2 = ((CellInfoGsm) cellInfo).getCellSignalStrength().getDbm();
                        } else if (cellInfo instanceof CellInfoCdma) {
                            i2 = ((CellInfoCdma) cellInfo).getCellSignalStrength().getDbm();
                        } else if (cellInfo instanceof CellInfoLte) {
                            i2 = ((CellInfoLte) cellInfo).getCellSignalStrength().getDbm();
                        } else if (cellInfo instanceof CellInfoWcdma) {
                            i2 = ((CellInfoWcdma) cellInfo).getCellSignalStrength().getDbm();
                        }
                    }
                }
                i = i2;
            }
            return i;
        } catch (Exception e) {
            return 0;
        }
    }

    public NetworkInfo getNetWorkInfo(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService("connectivity");
        int i = 0;
        NetworkInfo networkInfo = null;
        if (VERSION.SDK_INT >= 21) {
            Network[] allNetworks = connectivityManager.getAllNetworks();
            int length = allNetworks.length;
            while (i < length) {
                NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(allNetworks[i]);
                if (networkInfo2.getState().equals(State.CONNECTED)) {
                    networkInfo = networkInfo2;
                }
                i++;
            }
        } else {
            NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
            if (allNetworkInfo != null) {
                int length2 = allNetworkInfo.length;
                while (i < length2) {
                    NetworkInfo networkInfo3 = allNetworkInfo[i];
                    if (networkInfo3.getState() == State.CONNECTED) {
                        networkInfo = networkInfo3;
                    }
                    i++;
                }
            }
        }
        return networkInfo;
    }

    public int getNetworkStrength(Context context) {
        NetworkInfo netWorkInfo = getNetWorkInfo(context);
        if (netWorkInfo == null) {
            return 0;
        }
        if (netWorkInfo.getTypeName().equalsIgnoreCase("MOBILE")) {
            return a(context, netWorkInfo);
        }
        if (netWorkInfo.getTypeName().equalsIgnoreCase(PayUmoneyConstants.IS_WIFI) && a(context, "android.permission.ACCESS_WIFI_STATE")) {
            try {
                WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService(PayUmoneyConstants.IS_WIFI)).getConnectionInfo();
                if (connectionInfo != null) {
                    return WifiManager.calculateSignalLevel(connectionInfo.getRssi(), 5);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private boolean a(Context context, String str) {
        return context.checkCallingOrSelfPermission(str) == 0;
    }
}
