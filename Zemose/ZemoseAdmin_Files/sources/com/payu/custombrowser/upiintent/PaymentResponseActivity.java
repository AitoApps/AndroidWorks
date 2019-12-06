package com.payu.custombrowser.upiintent;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.payu.custombrowser.PackageListDialogFragment;
import com.payu.custombrowser.R;
import com.payu.custombrowser.a.a;
import com.payu.custombrowser.b.b;
import com.payu.custombrowser.bean.CustomBrowserConfig;
import com.payu.custombrowser.util.CBConstant;
import com.payu.custombrowser.util.CBUtil;
import com.payumoney.core.PayUmoneyConstants;
import java.util.ArrayList;
import java.util.Iterator;

public class PaymentResponseActivity extends FragmentActivity implements b {
    c a;
    String b;
    String c;
    Payment d;
    ArrayList<a> e;
    ArrayList<a> f;
    private CustomBrowserConfig g;
    private a h;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.h = a.a(getApplicationContext(), "local_cache_analytics");
        this.g = (CustomBrowserConfig) getIntent().getParcelableExtra(CBConstant.CB_CONFIG);
        this.b = this.g.getPayuPostData();
        this.c = this.g.getMerchantKey();
        this.d = (Payment) getIntent().getSerializableExtra(CBConstant.PAYMENT_TYPE);
        this.a = new c(this, this.b, com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback());
        if (this.d == Payment.TEZ) {
            this.a.a();
            return;
        }
        a();
        this.a.a();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data2) {
        super.onActivityResult(requestCode, resultCode, data2);
        if (requestCode != 101) {
            return;
        }
        if (resultCode != -1 || data2 == null) {
            this.a.a("cancel", (String) null);
            return;
        }
        if (PayUmoneyConstants.SUCCESS_STRING.equalsIgnoreCase(data2.getStringExtra("Status"))) {
            this.a.a(PayUmoneyConstants.SUCCESS_STRING, (String) null);
        } else {
            this.a.a("failure", (String) null);
        }
    }

    public void a(d dVar) {
        if (this.d == Payment.TEZ) {
            this.a.a(this.d.getPackageName());
            return;
        }
        this.f = new ArrayList<>();
        if (dVar.b() != null && dVar.b().size() > 0) {
            for (a aVar : dVar.b()) {
                Iterator it = this.e.iterator();
                while (it.hasNext()) {
                    a aVar2 = (a) it.next();
                    if (aVar2.equals(aVar)) {
                        aVar2.a(aVar.b());
                        this.f.add(aVar2);
                        it.remove();
                    }
                }
            }
        }
        this.f.addAll(this.e);
        PackageListDialogFragment newInstance = PackageListDialogFragment.newInstance(this.f, dVar, this.g);
        newInstance.setStyle(0, R.style.FullScreenDialogStyle);
        newInstance.show(getSupportFragmentManager(), "packageList");
    }

    private void a() {
        this.e = new ArrayList<>();
        Intent intent = new Intent();
        intent.setData(Uri.parse("upi://pay?"));
        for (ResolveInfo resolveInfo : getPackageManager().queryIntentActivities(intent, 65536)) {
            try {
                PackageInfo packageInfo = getPackageManager().getPackageInfo(resolveInfo.activityInfo.packageName, 0);
                String str = (String) getPackageManager().getApplicationLabel(packageInfo.applicationInfo);
                this.e.add(new a(str, getPackageManager().getApplicationIcon(packageInfo.applicationInfo), packageInfo.packageName));
            } catch (NameNotFoundException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void a(String str) {
        a aVar = this.h;
        Context applicationContext = getApplicationContext();
        StringBuilder sb = new StringBuilder();
        sb.append(this.d.toString().toLowerCase());
        sb.append("_payment_app");
        aVar.a(CBUtil.getLogMessage(applicationContext, sb.toString(), str, null, this.g.getMerchantKey(), this.g.getTransactionID(), null));
        this.a.a(str);
    }

    public void a(boolean z) {
        if (z) {
            this.h.a(CBUtil.getLogMessage(getApplicationContext(), this.d.toString().toLowerCase(), "back_button_cancel", null, this.g.getMerchantKey(), this.g.getTransactionID(), null));
            this.a.a("cancel", (String) null);
            return;
        }
        this.h.a(CBUtil.getLogMessage(getApplicationContext(), this.d.toString().toLowerCase(), "no_upi_apps_present", null, this.g.getMerchantKey(), this.g.getTransactionID(), null));
        this.a.a(CBConstant.FAIL, "No Upi apps present and collect disabled.");
    }
}
