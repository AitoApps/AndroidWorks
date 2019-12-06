package com.payumoney.sdkui.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Toast;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneySDK;
import com.payumoney.core.entity.CardDetail;
import com.payumoney.core.entity.TransactionResponse.TransactionStatus;
import com.payumoney.core.listener.OnFetchUserDetailsForNitroFlowListener;
import com.payumoney.core.listener.OnMultipleCardBinDetailsListener;
import com.payumoney.core.listener.OnNetBankingStatusListReceivedListener;
import com.payumoney.core.listener.OnPaymentOptionReceivedListener;
import com.payumoney.core.response.BinDetail;
import com.payumoney.core.response.ErrorResponse;
import com.payumoney.core.response.NetBankingStatusResponse;
import com.payumoney.core.response.PaymentOptionDetails;
import com.payumoney.core.response.UserDetail;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.presenter.fragmentPresenter.ILogoutListener;
import com.payumoney.sdkui.ui.fragments.PayUMoneyFragment;
import com.payumoney.sdkui.ui.fragments.ResultFragment;
import com.payumoney.sdkui.ui.utils.PPLogger;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.PreferenceManager;
import com.payumoney.sdkui.ui.utils.ResultModel;
import com.payumoney.sdkui.ui.utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PayUmoneyActivity extends BaseActivity implements OnFetchUserDetailsForNitroFlowListener, OnMultipleCardBinDetailsListener, OnNetBankingStatusListReceivedListener, OnPaymentOptionReceivedListener, ILogoutListener {
    boolean i;
    private Activity j;
    private ResultModel k;
    private boolean l;
    private int m;
    private PaymentOptionDetails n;
    private boolean o;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        this.g = getIntent().getIntExtra(PayUmoneyFlowManager.KEY_STYLE, -1);
        if (this.g != -1) {
            setTheme(this.g);
        } else {
            setTheme(R.style.AppTheme_default);
        }
        super.onCreate(savedInstanceState);
        this.i = false;
        PreferenceManager.initializeInstance(this);
        this.j = this;
        setUserDetails(getIntent().getStringExtra(PayUmoneyFlowManager.KEY_EMAIL), getIntent().getStringExtra(PayUmoneyFlowManager.KEY_MOBILE));
        this.e = getIntent().getStringExtra(PayUmoneyFlowManager.KEY_AMOUNT);
        if (this.e != null) {
            this.e = Utils.getProcessedDisplayAmount(Double.valueOf(this.e).doubleValue());
        }
        int intExtra = getIntent().getIntExtra(PayUmoneyFlowManager.KEY_FLOW, 0);
        this.l = getIntent().getBooleanExtra(PayUmoneyFlowManager.OVERRIDE_RESULT_SCREEN, false);
        this.j.getTheme().resolveAttribute(R.attr.colorPrimary, new TypedValue(), true);
        this.f = new ProgressDialog(this);
        this.f.setMessage("Getting payment details");
        this.f.setCanceledOnTouchOutside(false);
        this.f.setCancelable(false);
        TypedValue typedValue = new TypedValue();
        this.j.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        this.m = typedValue.data;
        String format = String.format(getString(R.string.color_string), new Object[]{Integer.valueOf(this.m)});
        TypedValue typedValue2 = new TypedValue();
        this.j.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue2, true);
        int i2 = typedValue2.data;
        String format2 = String.format(getString(R.string.color_string), new Object[]{Integer.valueOf(i2)});
        TypedValue typedValue3 = new TypedValue();
        this.j.getTheme().resolveAttribute(R.attr.actionMenuTextColor, typedValue3, true);
        int i3 = typedValue3.data;
        String format3 = String.format(getString(R.string.color_string), new Object[]{Integer.valueOf(i3)});
        PayUmoneyConfig instance = PayUmoneyConfig.getInstance();
        instance.setColorPrimary(format);
        instance.setColorPrimaryDark(format2);
        instance.setTextColorPrimary(format3);
        b();
        this.o = false;
        if (intExtra == 3) {
            if (this.f != null) {
                this.f.show();
            }
            if (isDataConnectionAvailable(this)) {
                PayUmoneySDK.getInstance().addPayment(this, "citrus_ui_activity");
                return;
            }
            Toast.makeText(this, getResources().getString(R.string.no_internet_connection), 0).show();
            finish();
        }
    }

    public int getPrimaryColor() {
        return this.m;
    }

    public void setUserDetails(String email, String mobile) {
        this.c = email;
        this.d = mobile;
    }

    /* access modifiers changed from: protected */
    public int a() {
        return R.layout.activity_citrus_ui;
    }

    private void a(Intent intent) {
        this.j.setResult(-1, intent);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data2) {
        super.onActivityResult(requestCode, resultCode, data2);
    }

    public void processAndShowResult(ResultModel resultModel, boolean isAddMoneyTxn) {
        this.k = resultModel;
        boolean z = true;
        this.h = true;
        c();
        if (!(((Integer) this.a.get(this.a.size() - 1)).intValue() == 0 && ((Integer) this.a.get(this.a.size() - 1)).intValue() == 5) && !this.a.isEmpty()) {
            if (this.a.size() > 1) {
                PPLogger instance = PPLogger.getInstance();
                StringBuilder sb = new StringBuilder();
                sb.append("CitrusActivity$ Screen Size = ");
                sb.append(this.a.size());
                instance.d(sb.toString(), new Object[0]);
                for (int size = this.a.size(); size > 1; size--) {
                    this.h = true;
                    onBackPressed();
                }
            } else {
                PPLogger instance2 = PPLogger.getInstance();
                StringBuilder sb2 = new StringBuilder();
                sb2.append("CitrusActivity$ Screen Size = ");
                sb2.append(this.a.size());
                instance2.d(sb2.toString(), new Object[0]);
            }
        }
        if (resultModel.getTransactionResponse() != null) {
            if (resultModel.getTransactionResponse().getTransactionStatus() != TransactionStatus.SUCCESSFUL) {
                z = false;
            }
            a(z, resultModel, isAddMoneyTxn);
        } else if (resultModel.getError() != null) {
            a(false, resultModel, isAddMoneyTxn);
        }
    }

    private void a(boolean z, ResultModel resultModel, boolean z2) {
        b();
        if (z) {
            this.e = "0";
        }
        if (!this.l || z2) {
            clearAllFragments();
            navigateWithReplace(ResultFragment.newInstance(resultModel), 2);
            return;
        }
        c();
        finish();
    }

    /* access modifiers changed from: protected */
    public void onResumeFragments() {
        super.onResumeFragments();
        try {
            c();
        } catch (Exception e) {
            PPLogger.getInstance().e("Exception", e);
        }
    }

    private void c() {
        try {
            if (this.h && this.k != null) {
                this.h = false;
                if (this.k.getTransactionResponse() != null) {
                    Intent intent = new Intent();
                    intent.putExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE, this.k.getTransactionResponse());
                    this.j.setResult(-1, intent);
                    return;
                }
                a(new Intent().putExtra("result", this.k));
            }
        } catch (Exception e) {
            PPLogger.getInstance().d("Exception", e);
        }
    }

    public boolean isStopEditing() {
        return this.i;
    }

    public void setStopEditing(boolean stopEditing) {
        this.i = stopEditing;
    }

    private void a(PaymentOptionDetails paymentOptionDetails, HashMap<String, BinDetail> hashMap) {
        this.o = true;
        navigateWithReplace(PayUMoneyFragment.newInstance(paymentOptionDetails, hashMap, this.g), 12);
    }

    public void onUserDetailsReceivedForNitroFlow(UserDetail userDetail, String tag) {
        if (!isFinishing()) {
            this.n.setUserDetails(userDetail);
            if (userDetail.getSaveCardList() == null || userDetail.getSaveCardList().size() <= 0) {
                if (this.f != null) {
                    this.f.dismiss();
                }
                a(this.n, null);
                return;
            }
            a(userDetail.getSaveCardList());
        }
    }

    private void a(ArrayList<CardDetail> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(((CardDetail) it.next()).getNumber());
        }
        PayUmoneySDK.getInstance().getMultipleCardBinDetails(this, arrayList2, "fetch_multiple_api_tag");
    }

    public void onMultipleCardBinDetailsReceived(HashMap<String, BinDetail> binDetailresponse, String tag) {
        if (!isFinishing()) {
            if (this.f != null) {
                this.f.dismiss();
            }
            a(this.n, binDetailresponse);
        }
    }

    public void onSuccess(String response, String tag) {
    }

    public void onPaymentOptionReceived(PaymentOptionDetails paymentDetails, String tag) {
        if (!isFinishing()) {
            this.n = paymentDetails;
            b();
            PayUmoneySDK.getInstance().getNetBankingStatusList(this, "get_net_banking_status_api_tag");
        }
    }

    public void onError(String response, String tag) {
        c(tag);
    }

    public void missingParam(String description, String tag) {
        if (this.j != null && !isFinishing()) {
            if (this.f != null) {
                this.f.dismiss();
            }
            Toast.makeText(this.j, description, 0).show();
            finish();
        }
    }

    public void onFailureResponse(ErrorResponse response, String tag) {
        c(tag);
    }

    private void c(String str) {
        if (isFinishing()) {
            return;
        }
        if (str.equals("fetch_nitro_payment_options_api_tag")) {
            if (this.n.getUserDetails() == null || this.n.getUserDetails().getSaveCardList() == null || this.n.getUserDetails().getSaveCardList().size() <= 0) {
                if (this.f != null) {
                    this.f.dismiss();
                }
                a(this.n, null);
                return;
            }
            a(this.n.getUserDetails().getSaveCardList());
        } else if (str.equals("fetch_nitro_payment_options_api_on_logout_tag")) {
            if (this.f != null) {
                this.f.dismiss();
            }
            this.n.setUserDetails(null);
            a(this.n, null);
        } else if (!str.equals("get_net_banking_status_api_tag")) {
            if (this.f != null) {
                this.f.dismiss();
            }
            Toast.makeText(this.j, "Some error occured", 0).show();
            finish();
        } else if (PayUmoneySDK.getInstance().isUserLoggedIn() || !this.n.isNitroEnabled()) {
            if (this.f != null) {
                this.f.dismiss();
            }
            a(this.n, null);
        } else {
            PayUmoneySDK.getInstance().fetchDetailsForNitroFlow(this, this.n.getPaymentID(), "fetch_nitro_payment_options_api_tag");
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.o = false;
        if (this.f != null) {
            this.f.dismiss();
            this.f = null;
        }
    }

    public void OnNetBankingListReceived(NetBankingStatusResponse response, String tag) {
        if (!isFinishing()) {
            this.n.setNetBankingStatusList(response.getNetBankList());
            if (PayUmoneySDK.getInstance().isUserLoggedIn() || !this.n.isNitroEnabled()) {
                if (this.f != null) {
                    this.f.dismiss();
                }
                if (this.n.getUserDetails() == null || this.n.getUserDetails().getSaveCardList() == null || this.n.getUserDetails().getSaveCardList().size() <= 0) {
                    a(this.n, null);
                } else {
                    a(this.n.getUserDetails().getSaveCardList());
                }
            } else {
                PayUmoneySDK.getInstance().fetchDetailsForNitroFlow(this, this.n.getPaymentID(), "fetch_nitro_payment_options_api_tag");
            }
        }
    }

    public void onLogout() {
        if (!isFinishing()) {
            clearAllFragments();
            PaymentOptionDetails paymentOptionDetails = this.n;
            if (paymentOptionDetails == null) {
                return;
            }
            if (paymentOptionDetails.isNitroEnabled()) {
                PayUmoneySDK.getInstance().fetchDetailsForNitroFlow(this, this.n.getPaymentID(), "fetch_nitro_payment_options_api_on_logout_tag");
                return;
            }
            if (this.f != null) {
                this.f.dismiss();
            }
            this.n.setUserDetails(null);
            a(this.n, null);
        }
    }
}
