package com.payu.custombrowser.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.ColorRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.view.View;
import android.widget.ArrayAdapter;
import com.payu.custombrowser.Bank;
import com.payu.custombrowser.R;
import com.payu.custombrowser.upiintent.Payment;
import com.payu.custombrowser.util.CBConstant;
import com.payu.custombrowser.util.CBUtil;
import com.payumoney.core.PayUmoneyConstants;
import java.util.HashMap;

public class CustomBrowserConfig implements Parcelable {
    public static final Creator<CustomBrowserConfig> CREATOR = new Creator<CustomBrowserConfig>() {
        /* renamed from: a */
        public CustomBrowserConfig createFromParcel(Parcel parcel) {
            return new CustomBrowserConfig(parcel);
        }

        /* renamed from: a */
        public CustomBrowserConfig[] newArray(int i) {
            return new CustomBrowserConfig[i];
        }
    };
    public static final int DISABLE = -1;
    public static final int ENABLE = 0;
    public static final int FAIL_MODE = 2;
    public static final int FALSE = -1;
    private static View P = null;
    public static final int STOREONECLICKHASH_MODE_NONE = 0;
    public static final int STOREONECLICKHASH_MODE_SERVER = 1;
    public static final int TRUE = 0;
    public static final int WARN_MODE = 1;
    private String A;
    private String B;
    private String C;
    private String D;
    private int E;
    private int F;
    private int G;
    private int H;
    private String I;
    private ArrayAdapter J;
    private int K;
    private String L;
    private int M;
    private transient ReviewOrderBundle N;
    private int O;
    private int Q;
    private int R;
    private int S;
    private String T;
    private String U;
    private int V;
    private String a;
    private int b;
    private int c;
    private int d;
    private String e;
    private String f;
    private String g;
    private int h;
    private int i;
    private int j;
    private int k = 1;
    private int l;
    private int m;
    private String n;
    private String o;
    private String p;
    private String q;
    private int r;
    private String s;
    private String t;
    private String u;
    private String v;
    private String w;
    private String x;
    private String y;
    private String z;

    public String getPayUOptionPaymentHash() {
        return this.a;
    }

    public void setPayUOptionPaymentHash(String payUOptionPaymentHash) {
        this.a = payUOptionPaymentHash;
    }

    public ArrayAdapter getCbMenuAdapter() {
        return this.J;
    }

    public void setCbMenuAdapter(ArrayAdapter cbMenuAdapter) {
        this.J = cbMenuAdapter;
    }

    public int getCbDrawerCustomMenu() {
        return this.Q;
    }

    public void setCbDrawerCustomMenu(int cbDrawerCustomMenu) {
        this.Q = cbDrawerCustomMenu;
    }

    public View getToolBarView() {
        return P;
    }

    public void setToolBarView(View toolBarView) {
        P = toolBarView;
    }

    private CustomBrowserConfig() {
    }

    protected CustomBrowserConfig(Parcel in) {
        this.b = in.readInt();
        this.c = in.readInt();
        this.d = in.readInt();
        this.e = in.readString();
        this.f = in.readString();
        this.g = in.readString();
        this.h = in.readInt();
        this.i = in.readInt();
        this.j = in.readInt();
        this.k = in.readInt();
        this.l = in.readInt();
        this.m = in.readInt();
        this.n = in.readString();
        this.o = in.readString();
        this.p = in.readString();
        this.q = in.readString();
        this.r = in.readInt();
        this.s = in.readString();
        this.t = in.readString();
        this.u = in.readString();
        this.v = in.readString();
        this.w = in.readString();
        this.x = in.readString();
        this.y = in.readString();
        this.z = in.readString();
        this.A = in.readString();
        this.B = in.readString();
        this.C = in.readString();
        this.D = in.readString();
        this.I = in.readString();
        this.E = in.readInt();
        this.F = in.readInt();
        this.G = in.readInt();
        this.O = in.readInt();
        this.K = in.readInt();
        this.L = in.readString();
        this.M = in.readInt();
        this.Q = in.readInt();
        this.R = in.readInt();
        this.S = in.readInt();
        this.T = in.readString();
        this.V = in.readInt();
        this.H = in.readInt();
    }

    public CustomBrowserConfig(@Size(max = 6, min = 6) @NonNull String merchantKey, @NonNull String transactionID) {
        this.e = transactionID;
        this.f = merchantKey;
        this.r = R.drawable.surepay_logo;
        this.s = "Internet Restored";
        this.t = "You can now resume the transaction";
        this.v = "No Internet Found";
        this.w = "We could not detect internet on your device";
        this.y = "Transaction Verified";
        this.z = "The bank has verified this transaction and we are good to go.";
        this.B = "Transaction Status Unknown";
        this.C = "The bank could not verify the transaction at this time.";
        this.I = CBConstant.NOTIFICATION_CHANNEL_ID;
        this.m = 0;
        this.E = 1;
        this.F = 1800000;
        this.G = 5000;
        this.O = -1;
        this.K = -1;
        this.M = -1;
        this.R = 1;
        this.S = -1;
        this.V = 0;
        this.H = 5000;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.b);
        dest.writeInt(this.c);
        dest.writeInt(this.d);
        dest.writeString(this.e);
        dest.writeString(this.f);
        dest.writeString(this.g);
        dest.writeInt(this.h);
        dest.writeInt(this.i);
        dest.writeInt(this.j);
        dest.writeInt(this.k);
        dest.writeInt(this.l);
        dest.writeInt(this.m);
        dest.writeString(this.n);
        dest.writeString(this.o);
        dest.writeString(this.p);
        dest.writeString(this.q);
        dest.writeInt(this.r);
        dest.writeString(this.s);
        dest.writeString(this.t);
        dest.writeString(this.u);
        dest.writeString(this.v);
        dest.writeString(this.w);
        dest.writeString(this.x);
        dest.writeString(this.y);
        dest.writeString(this.z);
        dest.writeString(this.A);
        dest.writeString(this.B);
        dest.writeString(this.C);
        dest.writeString(this.D);
        dest.writeString(this.I);
        dest.writeInt(this.E);
        dest.writeInt(this.F);
        dest.writeInt(this.G);
        dest.writeInt(this.O);
        dest.writeInt(this.K);
        dest.writeString(this.L);
        dest.writeInt(this.M);
        dest.writeInt(this.Q);
        dest.writeInt(this.R);
        dest.writeInt(this.S);
        dest.writeString(this.T);
        dest.writeInt(this.V);
        dest.writeInt(this.H);
    }

    public int describeContents() {
        return 0;
    }

    public String getPostURL() {
        return this.o;
    }

    public void setPostURL(String postURL) {
        this.o = postURL;
    }

    public String getPayuPostData() {
        return this.p;
    }

    public void setPayuPostData(String payuPostData) {
        this.p = payuPostData;
        HashMap dataFromPostData = new CBUtil().getDataFromPostData(payuPostData);
        StringBuilder sb = new StringBuilder();
        sb.append("Product info: ");
        sb.append((String) dataFromPostData.get(PayUmoneyConstants.PRODUCT_INFO_STRING));
        sb.append("\nAmount: ");
        sb.append((String) dataFromPostData.get(PayUmoneyConstants.AMOUNT));
        String sb2 = sb.toString();
        if (this.u == null) {
            setSurePayNotificationGoodNetWorkBody(sb2);
        }
        if (this.x == null) {
            setSurePayNotificationPoorNetWorkBody(sb2);
        }
        if (this.A == null) {
            setSurePayNotificationTransactionVerifiedBody(sb2);
        }
        if (this.D == null) {
            setSurePayNotificationTransactionNotVerifiedBody(sb2);
        }
        if (dataFromPostData.get("key") != null) {
            setMerchantKey(Bank.keyAnalytics == null ? (String) dataFromPostData.get("key") : Bank.keyAnalytics);
        }
    }

    public int getEnableSurePay() {
        return this.m;
    }

    public void setEnableSurePay(@IntRange(from = 0, to = 3) int enableSurePay) {
        int i2 = 3;
        if (enableSurePay <= 3) {
            i2 = enableSurePay;
        }
        this.m = i2;
    }

    public int getMerchantSMSPermission() {
        return this.l;
    }

    public void setMerchantSMSPermission(boolean merchantSMSPermission) {
        this.l = merchantSMSPermission;
    }

    public int getEnableWebFlow() {
        return this.R;
    }

    public void setEnableWebFlow(Payment payment, boolean enableWebFlow) {
        payment.setWebFlowSupported(enableWebFlow);
    }

    public int getMagicretry() {
        return this.k;
    }

    public void setmagicRetry(boolean magicRetry) {
        this.k = magicRetry;
    }

    public int getStoreOneClickHash() {
        return this.j;
    }

    public void setStoreOneClickHash(int storeOneClickHash) {
        this.j = storeOneClickHash;
    }

    public String getMerchantCheckoutActivityPath() {
        return this.n;
    }

    public void setMerchantCheckoutActivityPath(String merchantCheckoutActivityPath) {
        this.n = merchantCheckoutActivityPath;
    }

    public int getDisableBackButtonDialog() {
        return this.i;
    }

    public void setDisableBackButtonDialog(boolean disableBackButtonDialog) {
        this.i = disableBackButtonDialog;
    }

    public int getViewPortWideEnable() {
        return this.b;
    }

    public void setViewPortWideEnable(boolean viewPortWideEnable) {
        this.b = viewPortWideEnable;
    }

    public int getAutoApprove() {
        return this.c;
    }

    public void setAutoApprove(boolean autoApprove) {
        this.c = autoApprove;
    }

    public String getTransactionID() {
        return this.e;
    }

    public int getAutoSelectOTP() {
        return this.d;
    }

    public void setAutoSelectOTP(boolean autoSelectOTP) {
        this.d = autoSelectOTP;
    }

    public String getMerchantKey() {
        return this.f;
    }

    public void setMerchantKey(String merchantKey) {
        this.f = Bank.keyAnalytics;
        String str = this.f;
        if (str == null || str.trim().length() < 1) {
            this.f = merchantKey;
            Bank.keyAnalytics = merchantKey;
        }
    }

    public String getSdkVersionName() {
        return this.g;
    }

    public void setSdkVersionName(String sdkVersionName) {
        this.g = sdkVersionName;
    }

    public int getShowCustombrowser() {
        return this.h;
    }

    public void setShowCustombrowser(boolean showCustombrowser) {
        this.h = showCustombrowser;
    }

    public String getSurePayNotificationGoodNetworkTitle() {
        return this.s;
    }

    public void setSurePayNotificationGoodNetworkTitle(String surePayNotificationGoodNetworkTitle) {
        this.s = surePayNotificationGoodNetworkTitle;
    }

    public String getSurePayNotificationGoodNetWorkHeader() {
        return this.t;
    }

    public void setSurePayNotificationGoodNetWorkHeader(String surePayNotificationGoodNetWorkHeader) {
        this.t = surePayNotificationGoodNetWorkHeader;
    }

    public String getSurePayNotificationGoodNetWorkBody() {
        return this.u;
    }

    public void setSurePayNotificationGoodNetWorkBody(String surePayNotificationGoodNetWorkBody) {
        this.u = surePayNotificationGoodNetWorkBody;
    }

    public String getSurePayNotificationPoorNetWorkTitle() {
        return this.v;
    }

    public void setSurePayNotificationPoorNetWorkTitle(String surePayNotificationPoorNetWorkTitle) {
        this.v = surePayNotificationPoorNetWorkTitle;
    }

    public String getSurePayNotificationPoorNetWorkHeader() {
        return this.w;
    }

    public void setSurePayNotificationPoorNetWorkHeader(String surePayNotificationPoorNetWorkHeader) {
        this.w = surePayNotificationPoorNetWorkHeader;
    }

    public String getSurePayNotificationPoorNetWorkBody() {
        return this.x;
    }

    public void setSurePayNotificationPoorNetWorkBody(String surePayNotificationPoorNetWorkBody) {
        this.x = surePayNotificationPoorNetWorkBody;
    }

    public String getSurePayNotificationTransactionVerifiedTitle() {
        return this.y;
    }

    public void setSurePayNotificationTransactionVerifiedTitle(String surePayNotificationTransactionVerifiedTitle) {
        this.y = surePayNotificationTransactionVerifiedTitle;
    }

    public String getSurePayNotificationTransactionVerifiedHeader() {
        return this.z;
    }

    public void setSurePayNotificationTransactionVerifiedHeader(String surePayNotificationTransactionVerifiedHeader) {
        this.z = surePayNotificationTransactionVerifiedHeader;
    }

    public String getSurePayNotificationTransactionVerifiedBody() {
        return this.A;
    }

    public void setSurePayNotificationTransactionVerifiedBody(String surePayNotificationTransactionVerifiedBody) {
        this.A = surePayNotificationTransactionVerifiedBody;
    }

    public String getSurePayNotificationTransactionNotVerifiedTitle() {
        return this.B;
    }

    public void setSurePayNotificationTransactionNotVerifiedTitle(String surePayNotificationTransactionNotVerifiedTitle) {
        this.B = surePayNotificationTransactionNotVerifiedTitle;
    }

    public String getSurePayNotificationTransactionNotVerifiedHeader() {
        return this.C;
    }

    public void setSurePayNotificationTransactionNotVerifiedHeader(String surePayNotificationTransactionNotVerifiedHeader) {
        this.C = surePayNotificationTransactionNotVerifiedHeader;
    }

    public String getSurePayNotificationTransactionNotVerifiedBody() {
        return this.D;
    }

    public void setSurePayNotificationTransactionNotVerifiedBody(String surePayNotificationTransactionNotVerifiedBody) {
        this.D = surePayNotificationTransactionNotVerifiedBody;
    }

    public int getSurePayNotificationIcon() {
        return this.r;
    }

    public void setSurePayNotificationIcon(int surePayNotificationIcon) {
        this.r = surePayNotificationIcon;
    }

    public int getSurePayMode() {
        return this.E;
    }

    public void setSurePayMode(int surePayMode) {
        this.E = surePayMode;
    }

    public int getInternetRestoredWindowTTL() {
        return this.G;
    }

    public void setInternetRestoredWindowTTL(int internetRestoredWindowTTL) {
        this.G = internetRestoredWindowTTL;
    }

    public String getReviewOrderButtonText() {
        return this.L;
    }

    public void setReviewOrderButtonText(@Size(max = 16) @NonNull String reviewOrderButtonText) {
        if (reviewOrderButtonText == null) {
            throw new RuntimeException("ReviewOrderButtonText cannot be null");
        } else if (reviewOrderButtonText.length() <= 16) {
            this.L = reviewOrderButtonText;
        } else {
            throw new RuntimeException("ReviewOrderButtonText size should be less than 16");
        }
    }

    public int getReviewOrderButtonTextColor() {
        return this.M;
    }

    public void setReviewOrderButtonTextColor(@ColorRes int reviewOrderButtonTextColor) {
        this.M = reviewOrderButtonTextColor;
    }

    public int getEnableReviewOrder() {
        return this.K;
    }

    public void setEnableReviewOrder(int enableReviewOrder) {
        this.K = enableReviewOrder;
    }

    public int getSurePayBackgroundTTL() {
        return this.F;
    }

    public void setSurePayBackgroundTTL(int surePayBackgroundTTL) {
        this.F = surePayBackgroundTTL;
    }

    public int getReviewOrderCustomView() {
        return this.O;
    }

    public void setReviewOrderCustomView(@LayoutRes int reviewOrderCustomView) {
        this.O = reviewOrderCustomView;
    }

    public ReviewOrderBundle getReviewOrderDefaultViewData() {
        return this.N;
    }

    public void setReviewOrderDefaultViewData(ReviewOrderBundle reviewOrderDefaultViewData) {
        this.N = reviewOrderDefaultViewData;
    }

    public int getGmsProviderUpdatedStatus() {
        return this.S;
    }

    public void setGmsProviderUpdatedStatus(int gmsProviderUpdatedStatus) {
        this.S = gmsProviderUpdatedStatus;
    }

    public String getHtmlData() {
        return this.q;
    }

    public void setHtmlData(String htmlData) {
        this.q = htmlData;
    }

    public String getSurePayNotificationChannelId() {
        return this.I;
    }

    public void setSurePayNotificationChannelId(String surePayNotificationChannelId) {
        this.I = surePayNotificationChannelId;
    }

    public String getSurepayS2Surl() {
        return this.T;
    }

    public void setSurepayS2Surl(String surepayS2Surl) {
        this.T = surepayS2Surl;
    }

    public void setReactVersion(String reactVersion) {
        this.U = reactVersion;
    }

    public String getReactVersion() {
        return this.U;
    }

    public int getIsPhonePeUserCacheEnabled() {
        return this.V;
    }

    public void setIsPhonePeUserCacheEnabled(int isPhonePeUserCacheEnabled) {
        this.V = isPhonePeUserCacheEnabled;
    }

    public int getMerchantResponseTimeout() {
        return this.H;
    }

    public void setMerchantResponseTimeout(int merchantResponseTimeout) {
        this.H = merchantResponseTimeout;
    }
}
