package com.payu.custombrowser;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.internal.view.SupportMenu;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.payu.custombrowser.b.a;
import com.payu.custombrowser.bean.CustomBrowserConfig;
import com.payu.custombrowser.custombar.CircularProgressView;
import com.payu.custombrowser.upiintent.b;
import com.payu.custombrowser.upiintent.d;
import com.payu.custombrowser.upiintent.e;
import com.payu.custombrowser.util.CBConstant;
import com.payumoney.core.PayUmoneyConstants;
import es.dmoral.toasty.BuildConfig;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class PackageListDialogFragment extends DialogFragment implements TextWatcher, OnClickListener, a {
    RecyclerView a;
    ArrayList<com.payu.custombrowser.upiintent.a> b;
    LinearLayout c;
    LinearLayout d;
    LinearLayout e;
    LinearLayout f;
    RelativeLayout g;
    TextView h;
    TextView i;
    Activity j;
    d k;
    boolean l;
    /* access modifiers changed from: private */
    public EditText m;
    private CustomBrowserConfig n;
    private TextView o;
    private TextView p;
    private ImageView q;
    private CircularProgressView r;
    /* access modifiers changed from: private */
    public boolean s = false;
    /* access modifiers changed from: private */
    public ScrollView t;
    private boolean u = true;
    /* access modifiers changed from: private */
    public boolean v = false;
    private StringBuilder w;

    public static PackageListDialogFragment newInstance(ArrayList<com.payu.custombrowser.upiintent.a> list, d paymentResponse, CustomBrowserConfig customBrowserConfig) {
        PackageListDialogFragment packageListDialogFragment = new PackageListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", list);
        bundle.putParcelable(CBConstant.CB_CONFIG, customBrowserConfig);
        bundle.putParcelable("paymentResponse", paymentResponse);
        packageListDialogFragment.setArguments(bundle);
        return packageListDialogFragment;
    }

    public PackageListDialogFragment() {
        setRetainInstance(true);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.cb_layout_generic_upi, container, false);
        this.t = (ScrollView) inflate;
        this.a = (RecyclerView) inflate.findViewById(R.id.rvApps);
        this.c = (LinearLayout) inflate.findViewById(R.id.ll_vpa);
        this.d = (LinearLayout) inflate.findViewById(R.id.ll_app_selector);
        this.g = (RelativeLayout) inflate.findViewById(R.id.rlInputVpa);
        this.e = (LinearLayout) inflate.findViewById(R.id.llPayment);
        this.f = (LinearLayout) inflate.findViewById(R.id.ll_separator);
        this.m = (EditText) inflate.findViewById(R.id.edit_vpa);
        this.h = (TextView) inflate.findViewById(R.id.tv_vpa_submit);
        this.i = (TextView) inflate.findViewById(R.id.tvHeading);
        this.o = (TextView) inflate.findViewById(R.id.tvVerifyVpa);
        this.p = (TextView) inflate.findViewById(R.id.tvVpaName);
        this.r = (CircularProgressView) inflate.findViewById(R.id.progressBar);
        this.q = (ImageView) inflate.findViewById(R.id.ivVpaSuccess);
        this.b = getArguments().getParcelableArrayList("list");
        this.k = (d) getArguments().getParcelable("paymentResponse");
        this.n = (CustomBrowserConfig) getArguments().getParcelable(CBConstant.CB_CONFIG);
        b();
        return inflate;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.j = activity;
    }

    public void onStart() {
        super.onStart();
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(-1, -2);
            getDialog().getWindow().setGravity(80);
            getDialog().setCanceledOnTouchOutside(false);
            getDialog().getWindow().setWindowAnimations(R.style.dialog_slide_animation);
            Activity activity = this.j;
            if (activity != null) {
                final View rootView = activity.getWindow().getDecorView().getRootView();
                rootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        rootView.getWindowVisibleDisplayFrame(rect);
                        if (rootView.getRootView().getHeight() - (rect.bottom - rect.top) >= rootView.getRootView().getHeight() / 4) {
                            PackageListDialogFragment.this.s = true;
                            PackageListDialogFragment.this.t.post(new Runnable() {
                                public void run() {
                                    PackageListDialogFragment.this.t.fullScroll(BuildConfig.VERSION_CODE);
                                    PackageListDialogFragment.this.m.requestFocus();
                                }
                            });
                        } else if (PackageListDialogFragment.this.s && PackageListDialogFragment.this.m.getText().toString().trim().isEmpty() && !PackageListDialogFragment.this.v) {
                            PackageListDialogFragment.this.s = false;
                            PackageListDialogFragment.this.b();
                        }
                    }
                });
            }
        }
        ArrayList<com.payu.custombrowser.upiintent.a> arrayList = this.b;
        if (arrayList != null && arrayList.size() > 0) {
            this.d.setVisibility(0);
            this.a.setLayoutManager(new GridLayoutManager(this.j, 3));
            this.a.setAdapter(new b(this.b, this.j, this));
        } else if (this.k.c() == null || !this.k.c().equalsIgnoreCase("1")) {
            c();
            this.v = true;
            this.d.setVisibility(8);
            this.f.setVisibility(4);
        } else {
            this.u = false;
            getDialog().cancel();
        }
        if (this.k.c() == null || !this.k.c().equalsIgnoreCase("1")) {
            this.c.setVisibility(0);
            this.h.setEnabled(false);
            this.h.setOnClickListener(this);
        } else {
            this.c.setVisibility(8);
            this.f.setVisibility(4);
        }
        if (this.k.d() == null || !this.k.d().equalsIgnoreCase("1")) {
            this.o.setVisibility(8);
            this.o.setOnClickListener(this);
            this.h.setEnabled(false);
            this.h.setAlpha(0.35f);
        } else {
            this.o.setVisibility(8);
            g();
        }
        this.w = new StringBuilder();
        if (!TextUtils.isEmpty(this.k.f())) {
            this.w.append(this.k.f());
            if (this.w.charAt(0) == '/') {
                this.w.deleteCharAt(0);
            }
            StringBuilder sb = this.w;
            if (sb.charAt(sb.length() - 1) == '/') {
                StringBuilder sb2 = this.w;
                sb2.deleteCharAt(sb2.length() - 1);
            }
        } else {
            this.w.append("^[^@]+@[^@]+$");
        }
        this.m.addTextChangedListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.tv_vpa_submit) {
            if (this.k.d().equalsIgnoreCase("1")) {
                d();
            } else {
                e();
            }
        } else if (view.getId() == R.id.tvVerifyVpa) {
            d();
        } else if (view.getId() == R.id.tvHeading) {
            c();
        }
    }

    public void verifyVpa(String verifyVpaHash) {
        StringBuilder sb = new StringBuilder();
        sb.append("key=");
        sb.append(this.n.getMerchantKey());
        sb.append("&var1=");
        sb.append(this.m.getText().toString().trim());
        sb.append("&command=validateVPA&hash=");
        sb.append(verifyVpaHash);
        String sb2 = sb.toString();
        com.payu.custombrowser.bean.a aVar = new com.payu.custombrowser.bean.a();
        aVar.a("POST");
        aVar.b(CBConstant.PRODUCTION_FETCH_DATA_URL);
        aVar.c(sb2);
        new com.payu.custombrowser.util.b(this).execute(new com.payu.custombrowser.bean.a[]{aVar});
    }

    public void onCustomBrowserAsyncTaskResponse(String cbAsynTaskResponse) {
        this.m.setEnabled(true);
        this.h.setVisibility(0);
        this.r.c();
        this.r.setVisibility(8);
        if (!a(cbAsynTaskResponse)) {
            i();
            if (this.k.d() != null && !this.k.d().equalsIgnoreCase("1")) {
                this.o.setVisibility(0);
                this.o.setBackgroundResource(17170445);
                this.o.setText(getResources().getString(R.string.cb_verify));
            }
        } else if (this.k.d() == null || !this.k.d().equalsIgnoreCase("1")) {
            h();
            this.o.setVisibility(8);
            String b2 = b(cbAsynTaskResponse);
            if (b2 == null || b2.equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                this.p.setVisibility(8);
            } else {
                this.p.setVisibility(0);
                this.p.setTextColor(getResources().getColor(R.color.cb_item_color));
                this.p.setText(b2);
            }
            this.q.setVisibility(0);
            this.l = true;
        } else {
            a();
            dismiss();
            if (getActivity() != null) {
                getActivity().finish();
            }
        }
    }

    private void a() {
        CBActivity.a = this.n.getCbMenuAdapter();
        CBActivity.e = this.n.getToolBarView();
        StringBuilder sb = new StringBuilder();
        sb.append("token=");
        sb.append(this.k.e());
        sb.append("&action=sdkFallback&customerVpa=");
        sb.append(this.m.getText().toString().trim());
        String sb2 = sb.toString();
        ArrayList<com.payu.custombrowser.upiintent.a> arrayList = this.b;
        if (arrayList != null && arrayList.isEmpty()) {
            sb2 = sb2.concat("&fallbackReasonCode=E1902");
        }
        this.n.setPayuPostData(sb2);
        this.n.setPostURL(this.k.a());
        Intent intent = new Intent(getActivity(), CBActivity.class);
        intent.putExtra(CBConstant.CB_CONFIG, this.n);
        if (!(this.n.getReviewOrderDefaultViewData() == null || this.n.getReviewOrderDefaultViewData().getReviewOrderDatas() == null)) {
            intent.putExtra(CBConstant.ORDER_DETAILS, this.n.getReviewOrderDefaultViewData().getReviewOrderDatas());
        }
        this.j.startActivity(intent);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        ((com.payu.custombrowser.b.b) this.j).a(this.u);
    }

    /* access modifiers changed from: private */
    public void b() {
        int i2 = (int) ((getResources().getDisplayMetrics().density * 10.0f) + 0.5f);
        this.i.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cb_ic_edit_black, 0, 0, 0);
        this.i.setPadding(0, 0, 0, i2);
        this.i.setOnClickListener(this);
        this.i.setCompoundDrawablePadding(20);
        this.g.setVisibility(8);
        this.p.setVisibility(8);
        this.e.setVisibility(8);
        this.q.setVisibility(8);
    }

    private void c() {
        this.i.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        this.i.setOnClickListener(null);
        this.i.setCompoundDrawablePadding(0);
        this.g.setVisibility(0);
        this.e.setVisibility(0);
        this.s = true;
        this.m.requestFocus();
        showSoftKeyboard();
    }

    private boolean a(String str) {
        boolean z = false;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("isVPAValid") && jSONObject.getInt("isVPAValid") == 1) {
                z = true;
            }
            return z;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private String b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("payerAccountName")) {
                return jSONObject.getString("payerAccountName");
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    private void d() {
        if (e.b(this.m.getText().toString(), this.w.toString())) {
            f();
            this.m.setEnabled(false);
            this.o.setVisibility(8);
            com.payu.custombrowser.bean.b.SINGLETON.getPayuCustomBrowserCallback().onVpaEntered(this.m.getText().toString(), this);
            return;
        }
        i();
    }

    private void e() {
        a();
        dismiss();
        this.j.finish();
    }

    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void beforeTextChanged(CharSequence charSequence, int i2, int i1, int i22) {
    }

    public void onTextChanged(CharSequence charSequence, int i2, int i1, int i22) {
    }

    public void afterTextChanged(Editable editable) {
        this.p.setVisibility(8);
        if (!e.b(this.m.getText().toString(), this.w.toString())) {
            this.h.setEnabled(false);
            this.h.setAlpha(0.35f);
            if (this.k.d() != null && this.k.d().equalsIgnoreCase("0")) {
                this.o.setVisibility(8);
            }
        } else if (this.k.d() == null || !this.k.d().equalsIgnoreCase("0")) {
            this.h.setEnabled(true);
            this.h.setAlpha(1.0f);
        } else {
            this.o.setVisibility(0);
            this.h.setEnabled(false);
            this.h.setAlpha(0.35f);
            this.q.setVisibility(4);
        }
    }

    private void f() {
        this.r.setVisibility(0);
        this.r.setIndeterminate(true);
        this.r.setColor(getResources().getColor(R.color.cb_progress_bar_color));
        this.r.a();
    }

    private void g() {
        this.h.setText(getResources().getString(R.string.cb_verify_and_proceed));
        this.h.setEnabled(false);
        this.h.setTextColor(getResources().getColor(17170443));
        this.h.setAlpha(0.35f);
        this.h.setOnClickListener(this);
    }

    private void h() {
        this.h.setTextColor(getResources().getColor(17170443));
        this.h.setText(getResources().getText(R.string.proceed_to_pay));
        this.h.setEnabled(true);
        this.h.setAlpha(1.0f);
    }

    private void i() {
        this.p.setVisibility(0);
        this.p.setText(getResources().getString(R.string.cb_invalid_vpa));
        this.p.setTextColor(SupportMenu.CATEGORY_MASK);
    }

    public void onPause() {
        super.onPause();
        hideSoftKeyboard(this.m);
    }

    /* access modifiers changed from: protected */
    public void hideSoftKeyboard(EditText input) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.j.getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(input.getWindowToken(), 0);
        }
    }

    /* access modifiers changed from: protected */
    public void showSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.j.getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(2, 0);
        }
    }
}
