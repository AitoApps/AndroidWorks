package com.payumoney.sdkui.ui.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySDK;
import com.payumoney.core.analytics.LogAnalytics;
import com.payumoney.core.listener.ReviewOrderImpl;
import com.payumoney.core.utils.AnalyticsConstant;
import com.payumoney.core.utils.SharedPrefsUtils;
import com.payumoney.core.utils.SharedPrefsUtils.Keys;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.ui.events.FragmentCallbacks;
import com.payumoney.sdkui.ui.utils.PPConstants;
import com.payumoney.sdkui.ui.utils.PPLogger;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseActivity extends AppCompatActivity implements FragmentCallbacks {
    final ArrayList<Integer> a = new ArrayList<>();
    public boolean b = false;
    String c = "";
    String d = "";
    String e = "0";
    ProgressDialog f = null;
    int g = -1;
    public boolean h;
    private Toolbar i;
    private NetworkChangeReceiver j;
    private IntentFilter k;
    private boolean l;

    public class NetworkChangeReceiver extends BroadcastReceiver {
        public NetworkChangeReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            BaseActivity.this.isDataConnectionAvailable(context);
        }
    }

    private class TermCondWebViewClient extends WebViewClient {
        private TermCondWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public abstract int a();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(a());
        this.i = (Toolbar) findViewById(R.id.custom_toolbar);
        this.j = new NetworkChangeReceiver();
        this.k = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        Toolbar toolbar = this.i;
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        registerReceiver(this.j, this.k);
        this.l = false;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        NetworkChangeReceiver networkChangeReceiver = this.j;
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
            this.j = null;
        }
        super.onPause();
        this.l = true;
    }

    private void c() {
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.img_back_arrow);
        drawable.setColorFilter(Color.parseColor(PayUmoneyConfig.getInstance().getTextColorPrimary()), Mode.SRC_ATOP);
        if (this.i != null) {
            getSupportActionBar().setHomeAsUpIndicator(drawable);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        int i2;
        if (PayUmoneyConfig.getInstance().isEnableReviewOrder()) {
            getMenuInflater().inflate(R.menu.review_order_menu, menu);
            String string = getString(R.string.order_details_btn_text);
            menu.findItem(R.id.review_order_menu).setTitle(string);
            PayUmoneyConfig.getInstance().setReviewOrderMenuText(string);
            TypedValue typedValue = new TypedValue();
            if (getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true)) {
                i2 = typedValue.data;
            } else {
                i2 = -1;
            }
            PayUmoneyConfig.getInstance().setReviewOrderToolbarBgColor(i2);
            PayUmoneyConfig.getInstance().setReviewOrderToolbarTextColor(-1);
            PayUmoneyConfig.getInstance().setReviewOrderImpl(new ReviewOrderImpl() {
                public Intent getReviewOrderScreenIntent(Context context) {
                    Intent intent = new Intent(context, ReviewOrderActivity.class);
                    intent.putExtra(PayUmoneyFlowManager.KEY_STYLE, BaseActivity.this.g);
                    return intent;
                }
            });
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
            return true;
        }
        if (itemId == R.id.review_order_menu) {
            Intent intent = new Intent(this, ReviewOrderActivity.class);
            intent.putExtra(PayUmoneyFlowManager.KEY_STYLE, this.g);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /* access modifiers changed from: 0000 */
    public void a(String str) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(PayUmoneyConfig.getInstance().getTextColorPrimary())), 0, spannableString.length(), 33);
        getSupportActionBar().setTitle((CharSequence) spannableString);
    }

    public void navigateTo(Fragment fragment, int screenName) {
        if (getBaseContext() != null && !isFinishing()) {
            this.a.add(Integer.valueOf(screenName));
            b();
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.add(R.id.container, fragment, String.valueOf(screenName));
            beginTransaction.addToBackStack(String.valueOf(screenName));
            beginTransaction.commit();
        }
    }

    public void navigateWithReplace(Fragment fragment, int screenName) {
        if (getBaseContext() != null && !isFinishing()) {
            this.a.add(Integer.valueOf(screenName));
            b();
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(R.id.container, fragment, String.valueOf(screenName));
            beginTransaction.commitAllowingStateLoss();
        }
    }

    public void navigateWithReplaceBackStack(Fragment fragment, int screenName) {
        this.a.add(Integer.valueOf(screenName));
        b();
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.container, fragment, String.valueOf(screenName));
        beginTransaction.addToBackStack(String.valueOf(screenName));
        beginTransaction.commit();
    }

    public void clearAllFragments() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                try {
                    FragmentManager supportFragmentManager = BaseActivity.this.getSupportFragmentManager();
                    if (supportFragmentManager.getBackStackEntryCount() > 0 && !BaseActivity.this.isFinishing()) {
                        supportFragmentManager.popBackStackImmediate((String) null, 1);
                    }
                    BaseActivity.this.a.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 100);
    }

    public String getEmail() {
        if (!TextUtils.isEmpty(this.c)) {
            return this.c;
        }
        return "";
    }

    public String getMobile() {
        if (!TextUtils.isEmpty(this.d)) {
            return this.d;
        }
        return "";
    }

    public void popBackStackTillTag(String tag) {
        if (!isFinishing()) {
            getSupportFragmentManager().popBackStackImmediate(tag, 1);
            int size = this.a.size() - 1;
            while (size >= 0) {
                if (((Integer) this.a.get(size)).equals(Integer.valueOf(tag))) {
                    this.a.remove(size);
                    return;
                } else {
                    this.a.remove(size);
                    size--;
                }
            }
        }
    }

    public String getAmount() {
        if (!TextUtils.isEmpty(this.e)) {
            return this.e;
        }
        return "";
    }

    public void showProgressDialog(boolean cancelable, String message) {
        ProgressDialog progressDialog = this.f;
        if (progressDialog == null) {
            return;
        }
        if (!progressDialog.isShowing()) {
            this.f.setCanceledOnTouchOutside(false);
            this.f.setCancelable(cancelable);
            this.f.setMessage(message);
            this.f.show();
            return;
        }
        this.f.setMessage(message);
    }

    public void updateProgressMsg(String message) {
        try {
            if (!isFinishing() && this.f != null && this.f.isShowing()) {
                this.f.setMessage(message);
            }
        } catch (IllegalArgumentException e2) {
            PPLogger.getInstance().e("IllegalArgumentException", (Exception) e2);
        }
    }

    public void dismissProgressDialog() {
        ProgressDialog progressDialog = this.f;
        if (progressDialog != null && progressDialog.isShowing()) {
            this.f.dismiss();
        }
    }

    public void displayTerms(Activity activity) {
        String uri = Uri.parse(PPConstants.TERMS_COND_URL).toString();
        Dialog dialog = new Dialog(activity, R.style.Local_Base_Theme_AppCompat_Dialog);
        dialog.setContentView(R.layout.layout_terms_condition);
        WebView webView = (WebView) dialog.findViewById(R.id.web_view);
        webView.loadUrl(uri);
        webView.setWebViewClient(new TermCondWebViewClient());
        dialog.setCancelable(true);
        dialog.show();
    }

    public int getStyle() {
        return this.g;
    }

    public void onBackPressed() {
        PPLogger.getInstance().d("BaseActivity$ onBackPressed", new Object[0]);
        if (this.a.size() > 1) {
            if (!this.h) {
                ArrayList<Integer> arrayList = this.a;
                if (((Integer) arrayList.get(arrayList.size() - 1)).intValue() != 12) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                    ArrayList<Integer> arrayList2 = this.a;
                    if (((Integer) arrayList2.get(arrayList2.size() - 1)).intValue() == 1) {
                        hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.ADD_CARD);
                    } else {
                        ArrayList<Integer> arrayList3 = this.a;
                        if (((Integer) arrayList3.get(arrayList3.size() - 1)).intValue() == 11) {
                            hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.CVV_ENTRY);
                        } else {
                            ArrayList<Integer> arrayList4 = this.a;
                            if (((Integer) arrayList4.get(arrayList4.size() - 1)).intValue() == 6) {
                                hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.VERIFY_OTP);
                            } else {
                                ArrayList<Integer> arrayList5 = this.a;
                                if (((Integer) arrayList5.get(arrayList5.size() - 1)).intValue() == 14) {
                                    hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.EMI_TENURE_PAGE);
                                } else {
                                    ArrayList<Integer> arrayList6 = this.a;
                                    if (((Integer) arrayList6.get(arrayList6.size() - 1)).intValue() == 13) {
                                        hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.ADD_EMI_CARD);
                                    }
                                }
                            }
                        }
                    }
                    LogAnalytics.logEvent(getApplicationContext(), AnalyticsConstant.BACK_BUTTON_CLICKED, hashMap, AnalyticsConstant.CLEVERTAP);
                }
            }
            PPLogger instance = PPLogger.getInstance();
            StringBuilder sb = new StringBuilder();
            sb.append("BaseActivity$stack top = ");
            ArrayList<Integer> arrayList7 = this.a;
            sb.append(arrayList7.get(arrayList7.size() - 1));
            instance.d(sb.toString(), new Object[0]);
            PPLogger instance2 = PPLogger.getInstance();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("BaseActivity$stack bot = ");
            sb2.append(this.a.get(0));
            instance2.d(sb2.toString(), new Object[0]);
            ArrayList<Integer> arrayList8 = this.a;
            if (((Integer) arrayList8.get(arrayList8.size() - 1)).intValue() != 2 || this.b || ((Integer) this.a.get(0)).intValue() != 0) {
                if (!this.h) {
                    ArrayList<Integer> arrayList9 = this.a;
                    if (((Integer) arrayList9.get(arrayList9.size() - 2)).intValue() == 6) {
                        popBackStackTillTag(String.valueOf(6));
                    }
                }
                try {
                    this.a.remove(this.a.size() - 1);
                    b();
                } catch (IndexOutOfBoundsException e2) {
                    PPLogger.getInstance().e("IndexOutOfBoundsException", (Exception) e2);
                }
                if (!isFinishing() && !this.l) {
                    super.onBackPressed();
                }
            } else if (!PayUmoneyConfig.getInstance().isExitConfirmationDisabled()) {
                showCancelTransactionMessage();
            } else {
                PayUmoneySDK.getInstance().userCancelledTransaction();
                d();
                e();
                finish();
            }
        } else {
            if (this.a.size() == 1) {
                if (((Integer) this.a.get(0)).intValue() != 12) {
                    HashMap hashMap2 = new HashMap();
                    hashMap2.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                    if (((Integer) this.a.get(0)).intValue() == 1) {
                        hashMap2.put(AnalyticsConstant.PAGE, AnalyticsConstant.ADD_CARD);
                    } else if (((Integer) this.a.get(0)).intValue() == 11) {
                        hashMap2.put(AnalyticsConstant.PAGE, AnalyticsConstant.CVV_ENTRY);
                    } else if (((Integer) this.a.get(0)).intValue() == 6) {
                        hashMap2.put(AnalyticsConstant.PAGE, AnalyticsConstant.VERIFY_OTP);
                    } else if (((Integer) this.a.get(0)).intValue() == 14) {
                        hashMap2.put(AnalyticsConstant.PAGE, AnalyticsConstant.EMI_TENURE_PAGE);
                    } else if (((Integer) this.a.get(0)).intValue() == 13) {
                        hashMap2.put(AnalyticsConstant.PAGE, AnalyticsConstant.ADD_EMI_CARD);
                    }
                    LogAnalytics.logEvent(getApplicationContext(), AnalyticsConstant.BACK_BUTTON_CLICKED, hashMap2, AnalyticsConstant.CLEVERTAP);
                } else if (!PayUmoneyConfig.getInstance().isExitConfirmationDisabled()) {
                    showCancelTransactionMessage();
                    return;
                }
            }
            PayUmoneySDK.getInstance().userCancelledTransaction();
            d();
            e();
            finish();
        }
    }

    public void showCancelTransactionMessage() {
        final HashMap hashMap = new HashMap();
        hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        Builder builder = new Builder(this);
        builder.setMessage((CharSequence) getResources().getString(R.string.are_you_sure_you_want_to_cancel_transaction));
        builder.setPositiveButton((CharSequence) "Yes", (OnClickListener) new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                PayUmoneySDK.getInstance().userCancelledTransaction();
                hashMap.put(AnalyticsConstant.TXN_CANCELLED, "true");
                LogAnalytics.logEvent(BaseActivity.this.getApplicationContext(), AnalyticsConstant.TXN_CANCEL_ATTEMPT, hashMap, AnalyticsConstant.CLEVERTAP);
                BaseActivity.this.d();
                BaseActivity.this.e();
                BaseActivity.this.finish();
            }
        });
        builder.setNegativeButton((CharSequence) "No", (OnClickListener) new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                hashMap.put(AnalyticsConstant.TXN_CANCELLED, "false");
                LogAnalytics.logEvent(BaseActivity.this.getApplicationContext(), AnalyticsConstant.TXN_CANCEL_ATTEMPT, hashMap, AnalyticsConstant.CLEVERTAP);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /* access modifiers changed from: private */
    public void d() {
        HashMap hashMap = new HashMap();
        hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        hashMap.put(AnalyticsConstant.REASON, PayUmoneyConstants.USER_CANCELLED_TRANSACTION);
        LogAnalytics.logEvent(getApplicationContext(), AnalyticsConstant.PAYMENT_ABANDONED, hashMap, AnalyticsConstant.CLEVERTAP);
    }

    /* access modifiers changed from: private */
    public void e() {
        LogAnalytics.pushAllPendingEvents(getApplicationContext(), AnalyticsConstant.CLEVERTAP);
    }

    public boolean isDataConnectionAvailable(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        boolean z = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (!z) {
            a(context);
        }
        return z;
    }

    private void a(Context context) {
        if (context != null) {
            Activity activity = (Activity) context;
            if (!activity.isFinishing()) {
                Snackbar.make(activity.findViewById(16908290), (CharSequence) context.getString(R.string.no_internet_connection), 0).show();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void b() {
        b((String) null);
    }

    /* access modifiers changed from: protected */
    public void b(String str) {
        if (str == null) {
            str = SharedPrefsUtils.getStringPreference(this, Keys.MERCHANT_NAME);
            if (str == null || str.equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                str = PayUmoneyConstants.SP_SP_NAME;
            }
        }
        a(str);
        c();
    }
}
