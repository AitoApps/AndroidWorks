package com.payumoney.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Keep;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.payumoney.graphics.AssetsHelper.CARD;
import com.payumoney.graphics.classes.NetBanking;
import com.payumoney.graphics.enums.ScreenDensity;
import com.payumoney.graphics.helpers.NetworkManager;
import com.payumoney.sdkui.ui.utils.PPConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;

public class AssetDownloadManager {
    private static String DEFAULT = PPConstants.DEFAULT_BANK_NAME;
    private static AssetDownloadManager ourInstance = new AssetDownloadManager();
    String REQUEST_TAG = "AssetDownloadManager";
    private String configJSON = null;
    private Context context = null;
    HashSet<String> failedResourceList;
    private boolean isSDKInitialized = false;
    Bitmap mBitmap = null;
    private NetworkManager mNetworkManager = null;
    ScreenDensity mScreenDensity = null;
    private String sdkErrorMessage = "SDK not initialized.";
    String sdkType;

    @Keep
    @Retention(RetentionPolicy.SOURCE)
    public @interface Environment {
        public static final String PRODUCTION = "PRODUCTION";
        public static final String SANDBOX = "SANDBOX";
    }

    public static AssetDownloadManager getInstance() {
        return ourInstance;
    }

    private AssetDownloadManager() {
    }

    public void init(Context context2, String sdkType2, String environment) {
        this.context = context2;
        this.sdkType = sdkType2;
        this.mNetworkManager = NetworkManager.getInstance(this.context, sdkType2, environment);
        this.mScreenDensity = NetworkManager.getScreenDensity(this.context);
        this.failedResourceList = new HashSet<>();
        this.mNetworkManager.downloadPriorityResources();
        this.isSDKInitialized = true;
    }

    @Deprecated
    public void init(Context context2, String sdkType2) {
        this.context = context2;
        this.sdkType = sdkType2;
        this.mNetworkManager = NetworkManager.getInstance(this.context, sdkType2, Environment.PRODUCTION);
        this.mScreenDensity = NetworkManager.getScreenDensity(this.context);
        this.failedResourceList = new HashSet<>();
        this.mNetworkManager.downloadPriorityResources();
        this.isSDKInitialized = true;
    }

    public void setEnvironment(String environment) {
        if (!this.isSDKInitialized) {
            throw new IllegalArgumentException(this.sdkErrorMessage);
        }
    }

    public ImageLoader getImageLoader() {
        return this.mNetworkManager.getImageLoader();
    }

    public void getAllResources(String configJSON2) {
        if (this.isSDKInitialized) {
            this.configJSON = configJSON2;
            for (final NetBanking netBanking : NetworkManager.getPGSettingIdentifier(configJSON2).getNetBanking()) {
                if (!this.failedResourceList.contains(netBanking.getIssuerCode())) {
                    this.mNetworkManager.getImageLoader().get(NetworkManager.getBankURL(netBanking.getIssuerCode(), this.mScreenDensity), new ImageListener() {
                        public void onResponse(ImageContainer response, boolean isImmediate) {
                        }

                        public void onErrorResponse(VolleyError error) {
                            AssetDownloadManager.this.failedResourceList.add(netBanking.getIssuerCode());
                        }
                    });
                }
            }
            return;
        }
        throw new IllegalArgumentException(this.sdkErrorMessage);
    }

    public void getBankBitmap(final String CID, final BitmapCallBack bitmapCallBack) {
        if (!this.isSDKInitialized) {
            throw new IllegalArgumentException(this.sdkErrorMessage);
        } else if (!this.failedResourceList.contains(CID)) {
            BitmapCallBack bitmapCallBack1 = new BitmapCallBack() {
                public void onBitmapReceived(Bitmap bitmap) {
                    bitmapCallBack.onBitmapReceived(bitmap);
                }

                public void onBitmapFailed(Bitmap bitmap) {
                    bitmapCallBack.onBitmapFailed(bitmap);
                    AssetDownloadManager.this.failedResourceList.add(CID);
                }
            };
            if (DEFAULT.equalsIgnoreCase(CID)) {
                bitmapCallBack.onBitmapReceived(((BitmapDrawable) this.context.getResources().getDrawable(R.drawable.default_bank)).getBitmap());
            } else {
                this.mNetworkManager.getBankBitmap(NetworkManager.getBankURL(CID, this.mScreenDensity), bitmapCallBack1);
            }
        } else {
            bitmapCallBack.onBitmapFailed(((BitmapDrawable) this.context.getResources().getDrawable(R.drawable.default_bank)).getBitmap());
        }
    }

    public void getBankBitmapByBankCode(final String bankCode, final BitmapCallBack bitmapCallBack) {
        if (!this.isSDKInitialized) {
            throw new IllegalArgumentException(this.sdkErrorMessage);
        } else if (!this.failedResourceList.contains(bankCode)) {
            this.mNetworkManager.getBankBitmap(NetworkManager.getBankURL(bankCode, this.mScreenDensity), new BitmapCallBack() {
                public void onBitmapReceived(Bitmap bitmap) {
                    bitmapCallBack.onBitmapReceived(bitmap);
                }

                public void onBitmapFailed(Bitmap bitmap) {
                    bitmapCallBack.onBitmapFailed(bitmap);
                    AssetDownloadManager.this.failedResourceList.add(bankCode);
                }
            });
        } else {
            bitmapCallBack.onBitmapFailed(((BitmapDrawable) this.context.getResources().getDrawable(R.drawable.default_bank)).getBitmap());
        }
    }

    public void getWalletBitmap(final String bankCode, final BitmapCallBack bitmapCallBack) {
        if (!this.isSDKInitialized) {
            throw new IllegalArgumentException(this.sdkErrorMessage);
        } else if (!this.failedResourceList.contains(bankCode)) {
            this.mNetworkManager.getWalletBitmap(NetworkManager.getWalletURL(bankCode, this.mScreenDensity), new BitmapCallBack() {
                public void onBitmapReceived(Bitmap bitmap) {
                    bitmapCallBack.onBitmapReceived(bitmap);
                }

                public void onBitmapFailed(Bitmap bitmap) {
                    bitmapCallBack.onBitmapFailed(bitmap);
                    AssetDownloadManager.this.failedResourceList.add(bankCode);
                }
            });
        } else {
            bitmapCallBack.onBitmapFailed(((BitmapDrawable) this.context.getResources().getDrawable(R.drawable.default_bank)).getBitmap());
        }
    }

    public void getLargeWalletBitmap(String bankCode, BitmapCallBack bitmapCallBack) {
        if (this.isSDKInitialized) {
            this.mNetworkManager.getLargeWalletBitmap(NetworkManager.getLargeWalletURL(bankCode, this.mScreenDensity), bitmapCallBack);
            return;
        }
        throw new IllegalArgumentException(this.sdkErrorMessage);
    }

    public void getCardBitmap(String card, BitmapCallBack bitmapCallBack) {
        if (!this.isSDKInitialized) {
            throw new IllegalArgumentException(this.sdkErrorMessage);
        } else if (card.equalsIgnoreCase(CARD.DEFAULT)) {
            bitmapCallBack.onBitmapReceived(((BitmapDrawable) this.context.getResources().getDrawable(R.drawable.default_card)).getBitmap());
        } else {
            this.mNetworkManager.getCardBitmap(NetworkManager.getCardURL(card, this.mScreenDensity), bitmapCallBack);
        }
    }

    public void getBrandingBitmap(String branding, BitmapCallBack bitmapCallBack) {
        if (this.isSDKInitialized) {
            this.mNetworkManager.getBrandingBitmap(NetworkManager.getBrandingURL(branding, this.mScreenDensity), bitmapCallBack);
            return;
        }
        throw new IllegalArgumentException(this.sdkErrorMessage);
    }

    public void getLargeBankBitmapByBankCode(String bankCode, BitmapCallBack bitmapCallBack) {
        if (this.isSDKInitialized) {
            this.mNetworkManager.getLargeBankBitmap(NetworkManager.getLargeBankURL(bankCode, this.mScreenDensity), bitmapCallBack);
            return;
        }
        throw new IllegalArgumentException(this.sdkErrorMessage);
    }

    public void getLargeBankBitmap(String bank, BitmapCallBack bitmapCallBack) {
        if (this.isSDKInitialized) {
            this.mNetworkManager.getLargeBankBitmap(NetworkManager.getLargeBankURL(bank, this.mScreenDensity), bitmapCallBack);
            return;
        }
        throw new IllegalArgumentException(this.sdkErrorMessage);
    }

    public void getLargeCardBitmap(String card, BitmapCallBack bitmapCallBack) {
        if (this.isSDKInitialized) {
            this.mNetworkManager.getLargeCardBitmap(NetworkManager.getLargeCardURL(card, this.mScreenDensity), bitmapCallBack);
            return;
        }
        throw new IllegalArgumentException(this.sdkErrorMessage);
    }

    public void getMasterPassBitmap(String resource, BitmapCallBack bitmapCallBack) {
        if (this.isSDKInitialized) {
            this.mNetworkManager.getMasterPassBitmap(NetworkManager.getMasterPassURL(resource, this.mScreenDensity), bitmapCallBack);
            return;
        }
        throw new IllegalArgumentException(this.sdkErrorMessage);
    }

    public void clearCache() {
        if (this.isSDKInitialized) {
            this.mNetworkManager.clearCache();
            return;
        }
        throw new IllegalArgumentException(this.sdkErrorMessage);
    }
}
