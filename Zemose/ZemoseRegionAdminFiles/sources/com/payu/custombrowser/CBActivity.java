package com.payu.custombrowser;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.payu.custombrowser.bean.CustomBrowserConfig;
import com.payu.custombrowser.bean.b;
import com.payu.custombrowser.c.a;
import com.payu.custombrowser.util.CBConstant;
import com.payu.custombrowser.util.CBUtil;
import com.payu.magicretry.MagicRetryFragment.ActivityCallback;
import java.util.ArrayList;
import org.json.JSONException;

public class CBActivity extends AppCompatActivity implements a, ActivityCallback {
    protected static ArrayAdapter a;
    public static int b;
    protected static View e;
    CustomBrowserConfig c;
    CBUtil d;
    private Bank f;
    private AlertDialog g;
    private android.app.AlertDialog h;

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        b = 1;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        b = 2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.cb_payments);
        this.f = new Bank();
        this.d = new CBUtil();
        this.d.resetPayuID();
        Bundle bundle = new Bundle();
        this.c = (CustomBrowserConfig) getIntent().getParcelableExtra(CBConstant.CB_CONFIG);
        ArrayList parcelableArrayListExtra = getIntent().getParcelableArrayListExtra(CBConstant.ORDER_DETAILS);
        bundle.putParcelable(CBConstant.CB_CONFIG, this.c);
        if (parcelableArrayListExtra != null) {
            bundle.putParcelableArrayList(CBConstant.ORDER_DETAILS, parcelableArrayListExtra);
        }
        this.f.setArguments(bundle);
        cbSetToolBar(e);
        b();
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, (Fragment) this.f).commit();
    }

    public void onBackPressed() {
        CustomBrowserConfig customBrowserConfig = this.c;
        if (customBrowserConfig == null || customBrowserConfig.getDisableBackButtonDialog() == 1) {
            this.f.a("user_input", "m_back_button");
            if (b.SINGLETON.getPayuCustomBrowserCallback() != null) {
                b.SINGLETON.getPayuCustomBrowserCallback().onBackButton(null);
            }
            finish();
            return;
        }
        this.f.a("user_input", "payu_back_button".toLowerCase());
        this.f.showBackButtonDialog();
    }

    public void showMagicRetry() {
        this.f.showMagicRetry();
    }

    public void hideMagicRetry() {
        this.f.hideMagicRetry();
    }

    public void onDestroy() {
        android.app.AlertDialog alertDialog = this.h;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.h.dismiss();
            this.h.cancel();
        }
        AlertDialog alertDialog2 = this.g;
        if (alertDialog2 != null && alertDialog2.isShowing()) {
            this.g.dismiss();
            this.g.cancel();
        }
        b = 3;
        Bank bank = this.f;
        if (!(bank == null || bank.getSnoozeLoaderView() == null)) {
            this.f.getSnoozeLoaderView().b();
            this.f.setSnoozeLoaderView(null);
        }
        if (b.SINGLETON.getPayuCustomBrowserCallback() != null) {
            b.SINGLETON.getPayuCustomBrowserCallback().onPaymentTerminate();
            b.SINGLETON.setPayuCustomBrowserCallback(null);
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        notificationManager.cancel(CBConstant.SNOOZE_NOTIFICATION_ID);
        notificationManager.cancel(63);
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getStringExtra(CBConstant.SENDER).contentEquals(CBConstant.SNOOZE_SERVICE)) {
            Bank bank = this.f;
            if (bank != null) {
                bank.killSnoozeService();
                this.f.dismissSnoozeWindow();
                Bank bank2 = this.f;
                bank2.ad = null;
                bank2.ac = false;
                if (intent.getExtras().getBoolean(CBConstant.VERIFICATION_MSG_RECEIVED)) {
                    try {
                        if (this.d.getValueOfJSONKey(intent.getExtras().getString(CBConstant.PAYU_RESPONSE), getString(R.string.cb_snooze_verify_api_status)).equalsIgnoreCase("1")) {
                            this.f.a("transaction_verified_notification_click", "-1");
                        } else {
                            this.f.a("transaction_not_verified_notification_click", "-1");
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    this.f.showTransactionStatusDialog(intent.getExtras().getString(CBConstant.PAYU_RESPONSE), true);
                    return;
                }
                this.f.a("internet_restored_notification_click", "-1");
                this.f.resumeTransaction(intent);
            }
        }
    }

    public void a() {
        this.f.a("user_input", "review_order_close_click");
    }

    public void cbSetToolBar(View resView) {
        if (resView != null && getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setCustomView(resView, new LayoutParams(-1, -1));
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            ((Toolbar) resView.getParent()).setContentInsetsAbsolute(0, 0);
        } else if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void b() {
        if (a != null && this.c.getCbDrawerCustomMenu() != 0) {
            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            ListView listView = (ListView) getLayoutInflater().inflate(this.c.getCbDrawerCustomMenu(), null);
            DrawerLayout.LayoutParams layoutParams = new DrawerLayout.LayoutParams(-1, -1);
            layoutParams.gravity = GravityCompat.START;
            drawerLayout.addView(listView);
            listView.setLayoutParams(layoutParams);
            listView.setAdapter(a);
            b.SINGLETON.getPayuCustomBrowserCallback().getNavigationDrawerObject(drawerLayout);
        }
    }
}
