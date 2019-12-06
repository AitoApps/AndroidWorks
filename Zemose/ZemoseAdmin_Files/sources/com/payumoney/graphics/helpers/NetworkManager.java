package com.payumoney.graphics.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BaseHttpStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.payumoney.graphics.AssetsHelper.BRANDING;
import com.payumoney.graphics.AssetsHelper.CARD;
import com.payumoney.graphics.AssetsHelper.LARGEBANK;
import com.payumoney.graphics.AssetsHelper.SDK_TYPE;
import com.payumoney.graphics.BitmapCallBack;
import com.payumoney.graphics.R;
import com.payumoney.graphics.classes.PGSettingIdentifier;
import com.payumoney.graphics.enums.ScreenDensity;
import com.payumoney.graphics.tls.HurlStackFactory;
import java.util.ArrayList;
import java.util.Iterator;

public class NetworkManager {
    private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
    private static int DISK_IMAGECACHE_QUALITY = 100;
    private static int DISK_IMAGECACHE_SIZE = 10485760;
    /* access modifiers changed from: private */
    public static Context mCtx;
    private static NetworkManager mInstance;
    static String sdkType;
    private String defaultICONURL;
    public boolean isCoreSDKResourcesDownloaded = false;
    public boolean isFlashResourcesDownloaded = false;
    public boolean isPlugNPlayResourcesDownloaded = false;
    private ArrayList<String> mBanks = null;
    private ArrayList<String> mCARDs = null;
    private ArrayList<String> mFlashBanks = null;
    private ArrayList<String> mFlashCARDs = null;
    private ImageCache mImageCache;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    ScreenDensity mScreenDensity = null;

    public void setDefaultICONURL(String defaultICONURL2) {
        this.defaultICONURL = defaultICONURL2;
    }

    private NetworkManager(Context context) {
        mCtx = context;
        this.mRequestQueue = getRequestQueue();
        Context context2 = mCtx;
        DiskLruImageCache diskLruImageCache = new DiskLruImageCache(context2, context2.getPackageCodePath(), DISK_IMAGECACHE_SIZE, DISK_IMAGECACHE_COMPRESS_FORMAT, DISK_IMAGECACHE_QUALITY);
        this.mImageCache = diskLruImageCache;
        this.mImageLoader = new ImageLoader(Volley.newRequestQueue(context, (BaseHttpStack) HurlStackFactory.getHurlStack()), this.mImageCache);
        this.mScreenDensity = Utils.getScreenDensity(mCtx);
    }

    public static synchronized NetworkManager getInstance(Context context, String sdkType2, String environment) {
        NetworkManager networkManager;
        synchronized (NetworkManager.class) {
            if (mInstance == null) {
                mInstance = new NetworkManager(context);
                sdkType = sdkType2;
                Utils.setEnvironment(environment);
            }
            networkManager = mInstance;
        }
        return networkManager;
    }

    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext(), (BaseHttpStack) HurlStackFactory.getHurlStack());
        }
        return this.mRequestQueue;
    }

    public void getBankBitmap(String bankURL, final BitmapCallBack bitmapCallBack) {
        this.mImageLoader.get(bankURL, new ImageListener() {
            public void onResponse(ImageContainer response, boolean isImmediate) {
                bitmapCallBack.onBitmapReceived(response.getBitmap());
            }

            public void onErrorResponse(VolleyError error) {
                bitmapCallBack.onBitmapFailed(((BitmapDrawable) NetworkManager.mCtx.getResources().getDrawable(R.drawable.default_bank)).getBitmap());
            }
        });
    }

    public void getWalletBitmap(String bankURL, final BitmapCallBack bitmapCallBack) {
        this.mImageLoader.get(bankURL, new ImageListener() {
            public void onResponse(ImageContainer response, boolean isImmediate) {
                bitmapCallBack.onBitmapReceived(response.getBitmap());
            }

            public void onErrorResponse(VolleyError error) {
                bitmapCallBack.onBitmapFailed(((BitmapDrawable) NetworkManager.mCtx.getResources().getDrawable(R.drawable.default_bank)).getBitmap());
            }
        });
    }

    public void getLargeWalletBitmap(String bankURL, final BitmapCallBack bitmapCallBack) {
        this.mImageLoader.get(bankURL, new ImageListener() {
            public void onResponse(ImageContainer response, boolean isImmediate) {
                bitmapCallBack.onBitmapReceived(response.getBitmap());
            }

            public void onErrorResponse(VolleyError error) {
                bitmapCallBack.onBitmapFailed(((BitmapDrawable) NetworkManager.mCtx.getResources().getDrawable(R.drawable.default_bank)).getBitmap());
            }
        });
    }

    public void getLargeBankBitmap(String bankURL, final BitmapCallBack bitmapCallBack) {
        this.mImageLoader.get(bankURL, new ImageListener() {
            public void onResponse(ImageContainer response, boolean isImmediate) {
                bitmapCallBack.onBitmapReceived(response.getBitmap());
            }

            public void onErrorResponse(VolleyError error) {
                bitmapCallBack.onBitmapFailed(((BitmapDrawable) NetworkManager.mCtx.getResources().getDrawable(R.drawable.default_bank)).getBitmap());
            }
        });
    }

    public void getCardBitmap(String cardURL, final BitmapCallBack bitmapCallBack) {
        this.mImageLoader.get(cardURL, new ImageListener() {
            public void onResponse(ImageContainer response, boolean isImmediate) {
                bitmapCallBack.onBitmapReceived(response.getBitmap());
            }

            public void onErrorResponse(VolleyError error) {
                bitmapCallBack.onBitmapFailed(((BitmapDrawable) NetworkManager.mCtx.getResources().getDrawable(R.drawable.default_card)).getBitmap());
            }
        });
    }

    public void getLargeCardBitmap(String cardURL, final BitmapCallBack bitmapCallBack) {
        this.mImageLoader.get(cardURL, new ImageListener() {
            public void onResponse(ImageContainer response, boolean isImmediate) {
                bitmapCallBack.onBitmapReceived(response.getBitmap());
            }

            public void onErrorResponse(VolleyError error) {
                bitmapCallBack.onBitmapFailed(((BitmapDrawable) NetworkManager.mCtx.getResources().getDrawable(R.drawable.default_card)).getBitmap());
            }
        });
    }

    public void getBrandingBitmap(String brandingURL, final BitmapCallBack bitmapCallBack) {
        this.mImageLoader.get(brandingURL, new ImageListener() {
            public void onResponse(ImageContainer response, boolean isImmediate) {
                bitmapCallBack.onBitmapReceived(response.getBitmap());
            }

            public void onErrorResponse(VolleyError error) {
                bitmapCallBack.onBitmapFailed(((BitmapDrawable) NetworkManager.mCtx.getResources().getDrawable(R.drawable.default_card)).getBitmap());
            }
        });
    }

    public void getMasterPassBitmap(String masterPassURL, final BitmapCallBack bitmapCallBack) {
        this.mImageLoader.get(masterPassURL, new ImageListener() {
            public void onResponse(ImageContainer response, boolean isImmediate) {
                bitmapCallBack.onBitmapReceived(response.getBitmap());
            }

            public void onErrorResponse(VolleyError error) {
                bitmapCallBack.onBitmapFailed(((BitmapDrawable) NetworkManager.mCtx.getResources().getDrawable(R.drawable.default_card)).getBitmap());
            }
        });
    }

    public void putBitmap(String url, Bitmap bitmap) {
        try {
            this.mImageCache.putBitmap(createKey(url), bitmap);
        } catch (NullPointerException e) {
            throw new IllegalStateException("Disk Cache Not initialized");
        }
    }

    public void clearCache() {
        ImageCache imageCache = this.mImageCache;
        if (imageCache != null) {
            ((DiskLruImageCache) imageCache).clearCache();
        }
    }

    public void downloadPriorityResources() {
        String str = sdkType;
        if (str == SDK_TYPE.CORE_SDK) {
            downloadCoreSDKPriorityResources();
        } else if (str == SDK_TYPE.FLASH_SDK) {
            downloadflashPriorityResources();
        } else if (str == SDK_TYPE.PLUG_N_PLAY) {
            downloadPlugNPlayPriorityResources();
        }
    }

    public void downloadCoreSDKPriorityResources() {
        if (!this.isCoreSDKResourcesDownloaded) {
            if (this.mCARDs == null) {
                this.mCARDs = new ArrayList<>();
                this.mCARDs.add("VISA");
                this.mCARDs.add("MCRD");
                this.mCARDs.add("RPAY");
                this.mCARDs.add(CARD.MAESTRO);
                this.mCARDs.add(CARD.DINERCLUB);
                this.mCARDs.add("AMEX");
                this.mCARDs.add(CARD.DISCOVER);
                this.mCARDs.add(CARD.CIRRUS);
            }
            if (this.mBanks == null) {
                this.mBanks = new ArrayList<>();
                this.mBanks.add(LARGEBANK.SBI);
                this.mBanks.add(LARGEBANK.ICICI);
                this.mBanks.add(LARGEBANK.HDFC);
                this.mBanks.add(LARGEBANK.AXIS);
                this.mBanks.add(LARGEBANK.KOTAK);
                this.mBanks.add("CID044");
            }
            Iterator it = this.mCARDs.iterator();
            while (it.hasNext()) {
                this.mImageLoader.get(Utils.getCardURL((String) it.next(), this.mScreenDensity), new ImageListener() {
                    public void onResponse(ImageContainer response, boolean isImmediate) {
                    }

                    public void onErrorResponse(VolleyError error) {
                    }
                });
            }
            Iterator it2 = this.mBanks.iterator();
            while (it2.hasNext()) {
                this.mImageLoader.get(Utils.getBankURL((String) it2.next(), this.mScreenDensity), new ImageListener() {
                    public void onResponse(ImageContainer response, boolean isImmediate) {
                    }

                    public void onErrorResponse(VolleyError error) {
                    }
                });
            }
            this.mImageLoader.get(Utils.getBrandingURL(BRANDING.BRANDING_FOOTER, this.mScreenDensity), new ImageListener() {
                public void onResponse(ImageContainer response, boolean isImmediate) {
                }

                public void onErrorResponse(VolleyError error) {
                }
            });
            this.isCoreSDKResourcesDownloaded = true;
        }
    }

    public void downloadflashPriorityResources() {
        if (!this.isFlashResourcesDownloaded) {
            if (this.mFlashCARDs == null) {
                this.mFlashCARDs = new ArrayList<>();
                this.mFlashCARDs.add("VISA");
                this.mFlashCARDs.add("MCRD");
            }
            if (this.mFlashBanks == null) {
                this.mFlashBanks = new ArrayList<>();
                this.mFlashBanks.add(LARGEBANK.AXIS);
                this.mFlashBanks.add(LARGEBANK.SBI);
                this.mFlashBanks.add(LARGEBANK.HDFC);
                this.mFlashBanks.add(LARGEBANK.ICICI);
                this.mFlashBanks.add(LARGEBANK.KOTAK);
                this.mFlashBanks.add(LARGEBANK.CITI);
            }
            Iterator it = this.mFlashCARDs.iterator();
            while (it.hasNext()) {
                this.mImageLoader.get(Utils.getLargeCardURL((String) it.next(), this.mScreenDensity), new ImageListener() {
                    public void onResponse(ImageContainer response, boolean isImmediate) {
                    }

                    public void onErrorResponse(VolleyError error) {
                    }
                });
            }
            Iterator it2 = this.mFlashBanks.iterator();
            while (it2.hasNext()) {
                this.mImageLoader.get(Utils.getLargeBankURL((String) it2.next(), this.mScreenDensity), new ImageListener() {
                    public void onResponse(ImageContainer response, boolean isImmediate) {
                    }

                    public void onErrorResponse(VolleyError error) {
                    }
                });
            }
            this.isFlashResourcesDownloaded = true;
        }
    }

    public void downloadPlugNPlayPriorityResources() {
        if (!this.isPlugNPlayResourcesDownloaded) {
            if (this.mCARDs == null) {
                this.mCARDs = new ArrayList<>();
                this.mCARDs.add("VISA");
                this.mCARDs.add("MCRD");
                this.mCARDs.add("RPAY");
                this.mCARDs.add(CARD.MAESTRO);
                this.mCARDs.add(CARD.DINERCLUB);
                this.mCARDs.add("AMEX");
                this.mCARDs.add(CARD.DISCOVER);
            }
            if (this.mBanks == null) {
                this.mBanks = new ArrayList<>();
                this.mBanks.add(LARGEBANK.SBI);
                this.mBanks.add(LARGEBANK.ICICI);
                this.mBanks.add(LARGEBANK.HDFC);
                this.mBanks.add(LARGEBANK.AXIS);
                this.mBanks.add(LARGEBANK.KOTAK);
                this.mBanks.add("CID007");
                this.mBanks.add("CID009");
            }
            Iterator it = this.mCARDs.iterator();
            while (it.hasNext()) {
                this.mImageLoader.get(Utils.getCardURL((String) it.next(), this.mScreenDensity), new ImageListener() {
                    public void onResponse(ImageContainer response, boolean isImmediate) {
                    }

                    public void onErrorResponse(VolleyError error) {
                    }
                });
            }
            Iterator it2 = this.mBanks.iterator();
            while (it2.hasNext()) {
                this.mImageLoader.get(Utils.getBankURL((String) it2.next(), this.mScreenDensity), new ImageListener() {
                    public void onResponse(ImageContainer response, boolean isImmediate) {
                    }

                    public void onErrorResponse(VolleyError error) {
                    }
                });
            }
            this.mImageLoader.get(Utils.getBrandingURL(BRANDING.BRANDING_FOOTER, this.mScreenDensity), new ImageListener() {
                public void onResponse(ImageContainer response, boolean isImmediate) {
                }

                public void onErrorResponse(VolleyError error) {
                }
            });
            this.isPlugNPlayResourcesDownloaded = true;
        }
    }

    private String createKey(String url) {
        return String.valueOf(url.hashCode());
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }

    public static ScreenDensity getScreenDensity(Context context) {
        return Utils.getScreenDensity(context);
    }

    public static PGSettingIdentifier getPGSettingIdentifier(String configJSON) {
        return Utils.getPGSettingIdentifier(configJSON);
    }

    public static String getBankURL(String cid, ScreenDensity screenDensity) {
        return Utils.getBankURL(cid, screenDensity);
    }

    public static String getWalletURL(String bankCode, ScreenDensity screenDensity) {
        return Utils.getWalletURL(bankCode, screenDensity);
    }

    public static String getLargeWalletURL(String bank, ScreenDensity screenDensity) {
        return Utils.getLargeWalletURL(bank, screenDensity);
    }

    public static String getMasterPassURL(String resource, ScreenDensity screenDensity) {
        return Utils.getMasterPassURL(resource, screenDensity);
    }

    public static String getLargeCardURL(String card, ScreenDensity screenDensity) {
        return Utils.getLargeCardURL(card, screenDensity);
    }

    public static String getLargeBankURL(String bank, ScreenDensity screenDensity) {
        return Utils.getLargeBankURL(bank, screenDensity);
    }

    public static String getBrandingURL(String branding, ScreenDensity screenDensity) {
        return Utils.getBrandingURL(branding, screenDensity);
    }

    public static String getCardURL(String card, ScreenDensity screenDensity) {
        return Utils.getCardURL(card, screenDensity);
    }
}
