package com.payumoney.sdkui.ui.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySDK;
import com.payumoney.core.analytics.LogAnalytics;
import com.payumoney.core.entity.CardDetail;
import com.payumoney.core.listener.OnPaymentStatusReceivedListener;
import com.payumoney.core.request.PaymentRequest;
import com.payumoney.core.response.BinDetail;
import com.payumoney.core.utils.AnalyticsConstant;
import com.payumoney.core.utils.SdkHelper;
import com.payumoney.graphics.AssetDownloadManager;
import com.payumoney.graphics.AssetsHelper;
import com.payumoney.graphics.BitmapCallBack;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.ui.activities.BaseActivity;
import com.payumoney.sdkui.ui.activities.PayUmoneyActivity;
import com.payumoney.sdkui.ui.utils.OtpEditTextWatcher;
import com.payumoney.sdkui.ui.utils.OtpEditTextWatcher.OnTextInputFound;
import com.payumoney.sdkui.ui.utils.PPConstants;
import com.payumoney.sdkui.ui.utils.ToastUtils;
import com.payumoney.sdkui.ui.utils.Utils;
import com.payumoney.sdkui.ui.widgets.CustomDrawableTextView;
import com.payumoney.sdkui.ui.widgets.OtpEditText;
import com.payumoney.sdkui.ui.widgets.OtpEditText.DeletePress;
import java.util.HashMap;

public class GetCvvFragment extends BaseFragment implements OnTextInputFound, DeletePress {
    public static final String MAKE_PAYMENT_API_TAG = "CARD_PAYMENT_REQUEST_API_TAG";
    private TextView A;
    private boolean B;
    private boolean C;
    /* access modifiers changed from: private */
    public long D = 0;
    private double E;
    private BinDetail F;
    TextView a;
    ImageView m;
    OtpEditText n;
    OtpEditText o;
    OtpEditText p;
    OtpEditText q;
    /* access modifiers changed from: private */
    public PaymentRequest r;
    private CardDetail s;
    /* access modifiers changed from: private */
    public OnPaymentStatusReceivedListener t;
    private int u = 3;
    private CustomDrawableTextView v;
    private TextView w;
    private LinearLayout x;
    /* access modifiers changed from: private */
    public ImageView y;
    private TextView z;

    public static GetCvvFragment newInstance(PaymentRequest request, CardDetail cardDetail, BinDetail binDetail) {
        GetCvvFragment getCvvFragment = new GetCvvFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("payment_option", request);
        bundle.putParcelable("autoload_amount", cardDetail);
        bundle.putParcelable("bin_detail_object", binDetail);
        getCvvFragment.setArguments(bundle);
        return getCvvFragment;
    }

    public void setmListener(OnPaymentStatusReceivedListener mListener) {
        this.t = mListener;
    }

    public void setConvenienceFee(double convenienceFee) {
        this.E = convenienceFee;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle arguments = getArguments();
            this.b = (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT);
            this.r = (PaymentRequest) arguments.getParcelable("payment_option");
            this.s = (CardDetail) arguments.getParcelable("autoload_amount");
            this.F = (BinDetail) arguments.getParcelable("bin_detail_object");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_get_cvv_fragment_new, container, false);
        a(inflate);
        a();
        initConvenieneceFee(inflate);
        setAmount(this.b);
        setPaymentButtonDisable();
        inflate.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        this.h.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (GetCvvFragment.this.h.getText().toString().equalsIgnoreCase("Details")) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.CVV_ENTRY);
                    hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                    LogAnalytics.logEvent(GetCvvFragment.this.getContext(), AnalyticsConstant.SHOW_PAYMENT_DETAILS_CLIKED, hashMap, AnalyticsConstant.CLEVERTAP);
                    GetCvvFragment.this.showConvenieneceFee();
                    return;
                }
                HashMap hashMap2 = new HashMap();
                hashMap2.put(AnalyticsConstant.PAGE, AnalyticsConstant.CVV_ENTRY);
                hashMap2.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                LogAnalytics.logEvent(GetCvvFragment.this.getContext(), AnalyticsConstant.HIDE_PAYMENT_DETAILS_CLICKED, hashMap2, AnalyticsConstant.CLEVERTAP);
                GetCvvFragment.this.hideConvenieneceFee();
            }
        });
        updateConvenienceFee(Double.parseDouble(this.b), this.E);
        a(this.s);
        return inflate;
    }

    private void a() {
        this.v.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - GetCvvFragment.this.D >= 1000) {
                    GetCvvFragment.this.D = SystemClock.elapsedRealtime();
                    HashMap hashMap = new HashMap();
                    hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                    hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.CVV_ENTRY);
                    LogAnalytics.logEvent(GetCvvFragment.this.getContext(), AnalyticsConstant.PAY_NOW_BUTTON_CLICKED, hashMap, AnalyticsConstant.CLEVERTAP);
                    if (!((BaseActivity) GetCvvFragment.this.getActivity()).isDataConnectionAvailable(GetCvvFragment.this.getActivity())) {
                        GetCvvFragment.this.setPaymentButtonDisable();
                        GetCvvFragment getCvvFragment = GetCvvFragment.this;
                        getCvvFragment.showError(getCvvFragment.getString(R.string.no_internet_connection));
                    } else if (SdkHelper.isValidCvv(GetCvvFragment.this.getCvv(), GetCvvFragment.this.r.getBankCode())) {
                        HashMap hashMap2 = new HashMap();
                        hashMap2.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                        LogAnalytics.logEvent(GetCvvFragment.this.getContext(), AnalyticsConstant.SAVED_CARD_CVV_ENTERED, hashMap2, AnalyticsConstant.CLEVERTAP);
                        GetCvvFragment.this.r.setCvv(GetCvvFragment.this.getCvv());
                        PayUmoneySDK.getInstance().makePayment(GetCvvFragment.this.t, GetCvvFragment.this.r, true, GetCvvFragment.this.getActivity(), GetCvvFragment.MAKE_PAYMENT_API_TAG);
                    } else {
                        GetCvvFragment.this.setPaymentButtonDisable();
                        GetCvvFragment getCvvFragment2 = GetCvvFragment.this;
                        getCvvFragment2.showError(getCvvFragment2.getString(R.string.payu_invalid_cvv));
                    }
                }
            }
        });
    }

    public void onPause() {
        super.onPause();
        this.B = true;
        this.C = false;
    }

    public void onResume() {
        super.onResume();
        this.C = true;
        this.B = false;
        if (isAdded() && this.n != null) {
            Utils.showKeyBoard(getActivity(), this.n);
        }
    }

    private void a(CardDetail cardDetail) {
        String str;
        String str2;
        String str3;
        String string;
        BinDetail binDetail = this.F;
        if (binDetail != null) {
            if (binDetail.toString().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING) || this.F.getBankName() == null || this.F.getBankName().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING) || this.F.getBankName().isEmpty()) {
                str3 = PPConstants.DEFAULT_BANK_NAME;
                this.A.setText(getActivity().getString(R.string.default_bank_name));
            } else {
                str3 = this.F.getBankName();
                this.A.setText(str3);
            }
            if (this.F.getCategory().equalsIgnoreCase("cc")) {
                str2 = getActivity().getResources().getString(R.string.payu_credit);
            } else {
                str2 = getActivity().getResources().getString(R.string.payu_debit);
            }
            if (this.F.getBinOwner() == null || this.F.getBinOwner().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING) || this.F.getBinOwner().isEmpty()) {
                str = this.s.getType();
            } else {
                str = this.F.getBinOwner();
            }
        } else {
            str3 = PPConstants.DEFAULT_BANK_NAME;
            this.A.setText(getActivity().getString(R.string.default_bank_name));
            if (this.s.getPg().equalsIgnoreCase("cc")) {
                string = getActivity().getResources().getString(R.string.payu_credit);
            } else {
                string = getActivity().getResources().getString(R.string.payu_debit);
            }
            str = this.s.getType();
        }
        TextView textView = this.z;
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(str2);
        sb.append(")");
        textView.setText(sb.toString());
        this.a.setText(Utils.getProcessedNumber(cardDetail.getNumber(), str));
        AssetDownloadManager.getInstance().getCardBitmap(AssetsHelper.getCard(SdkHelper.getCardType(str.toUpperCase())), new BitmapCallBack() {
            public void onBitmapReceived(Bitmap bitmap) {
                if (GetCvvFragment.this.getActivity() != null && GetCvvFragment.this.isAdded() && !GetCvvFragment.this.getActivity().isFinishing()) {
                    GetCvvFragment.this.m.setImageDrawable(new BitmapDrawable(GetCvvFragment.this.getActivity().getResources(), bitmap));
                }
            }

            public void onBitmapFailed(Bitmap bitmap) {
                if (GetCvvFragment.this.getActivity() != null && GetCvvFragment.this.isAdded() && !GetCvvFragment.this.getActivity().isFinishing()) {
                    GetCvvFragment.this.m.setImageDrawable(new BitmapDrawable(GetCvvFragment.this.getActivity().getResources(), bitmap));
                }
            }
        });
        AssetDownloadManager.getInstance().getBankBitmapByBankCode(str3, new BitmapCallBack() {
            public void onBitmapReceived(Bitmap bitmap) {
                if (GetCvvFragment.this.getActivity() != null && GetCvvFragment.this.isAdded() && !GetCvvFragment.this.getActivity().isFinishing()) {
                    GetCvvFragment.this.y.setImageDrawable(new BitmapDrawable(GetCvvFragment.this.getActivity().getResources(), bitmap));
                }
            }

            public void onBitmapFailed(Bitmap bitmap) {
                if (GetCvvFragment.this.getActivity() != null && GetCvvFragment.this.isAdded() && !GetCvvFragment.this.getActivity().isFinishing()) {
                    GetCvvFragment.this.y.setImageDrawable(new BitmapDrawable(GetCvvFragment.this.getActivity().getResources(), bitmap));
                }
            }
        });
        a(this.x, cardDetail.getType().equalsIgnoreCase("AMEX") ? 4 : 3);
    }

    private void a(View view) {
        this.v = (CustomDrawableTextView) view.findViewById(R.id.payButton);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.cvvContainer);
        this.a = (TextView) view.findViewById(R.id.card_cardNumber);
        this.m = (ImageView) view.findViewById(R.id.card_cardType_image);
        this.y = (ImageView) view.findViewById(R.id.bank_logo);
        this.A = (TextView) view.findViewById(R.id.card_bank_name);
        this.z = (TextView) view.findViewById(R.id.textview_card_type);
        this.w = (TextView) view.findViewById(R.id.otp_error);
        this.x = (LinearLayout) View.inflate(getActivity(), R.layout.cvv_boxes_layout, null);
        this.x.setLayoutParams(new LayoutParams(-1, -2));
        linearLayout.addView(this.x);
    }

    private void a(LinearLayout linearLayout, int i) {
        this.u = i;
        this.n = (OtpEditText) linearLayout.findViewById(R.id.otpEdit_box1);
        this.o = (OtpEditText) linearLayout.findViewById(R.id.otpEdit_box2);
        this.p = (OtpEditText) linearLayout.findViewById(R.id.otpEdit_box3);
        this.q = (OtpEditText) linearLayout.findViewById(R.id.otpEdit_box4);
        OtpEditText otpEditText = this.n;
        OtpEditTextWatcher otpEditTextWatcher = new OtpEditTextWatcher(otpEditText, this.v, getActivity(), i, this);
        otpEditText.addTextChangedListener(otpEditTextWatcher);
        OtpEditText otpEditText2 = this.o;
        OtpEditTextWatcher otpEditTextWatcher2 = new OtpEditTextWatcher(otpEditText2, this.v, getActivity(), i, this);
        otpEditText2.addTextChangedListener(otpEditTextWatcher2);
        OtpEditText otpEditText3 = this.p;
        OtpEditTextWatcher otpEditTextWatcher3 = new OtpEditTextWatcher(otpEditText3, this.v, getActivity(), i, this);
        otpEditText3.addTextChangedListener(otpEditTextWatcher3);
        this.n.setDeletePressListener(this);
        this.o.setDeletePressListener(this);
        this.p.setDeletePressListener(this);
        if (i == 4) {
            OtpEditText otpEditText4 = this.q;
            OtpEditTextWatcher otpEditTextWatcher4 = new OtpEditTextWatcher(otpEditText4, this.v, getActivity(), i, this);
            otpEditText4.addTextChangedListener(otpEditTextWatcher4);
            this.q.setDeletePressListener(this);
            return;
        }
        this.q.setVisibility(8);
        this.p.setImeOptions(6);
    }

    public String getCvv() {
        if (this.u == 4) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(this.n.getText()));
            sb.append(this.o.getText());
            sb.append(this.p.getText());
            sb.append(this.q.getText());
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(String.valueOf(this.n.getText()));
        sb2.append(this.o.getText());
        sb2.append(this.p.getText());
        return sb2.toString();
    }

    public void onDetach() {
        super.onDetach();
        this.t = null;
    }

    public void onEmptyDeletePress(String tag) {
        char c;
        switch (tag.hashCode()) {
            case 49:
                if (tag.equals("1")) {
                    c = 0;
                    break;
                }
            case 50:
                if (tag.equals("2")) {
                    c = 1;
                    break;
                }
            case 51:
                if (tag.equals("3")) {
                    c = 2;
                    break;
                }
            case 52:
                if (tag.equals("4")) {
                    c = 3;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 1:
                ((PayUmoneyActivity) getActivity()).setStopEditing(true);
                this.n.requestFocus();
                OtpEditText otpEditText = this.n;
                otpEditText.setSelection(otpEditText.getText().length());
                ((PayUmoneyActivity) getActivity()).setStopEditing(false);
                return;
            case 2:
                ((PayUmoneyActivity) getActivity()).setStopEditing(true);
                this.o.requestFocus();
                OtpEditText otpEditText2 = this.o;
                otpEditText2.setSelection(otpEditText2.getText().length());
                ((PayUmoneyActivity) getActivity()).setStopEditing(false);
                return;
            case 3:
                ((PayUmoneyActivity) getActivity()).setStopEditing(true);
                this.p.requestFocus();
                OtpEditText otpEditText3 = this.p;
                otpEditText3.setSelection(otpEditText3.getText().length());
                ((PayUmoneyActivity) getActivity()).setStopEditing(false);
                return;
            default:
                return;
        }
    }

    public void onNonEmptyDeletePress(String tag) {
        char c;
        switch (tag.hashCode()) {
            case 49:
                if (tag.equals("1")) {
                    c = 0;
                    break;
                }
            case 50:
                if (tag.equals("2")) {
                    c = 1;
                    break;
                }
            case 51:
                if (tag.equals("3")) {
                    c = 2;
                    break;
                }
            case 52:
                if (tag.equals("4")) {
                    c = 3;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 1:
                ((PayUmoneyActivity) getActivity()).setStopEditing(true);
                this.n.requestFocus();
                OtpEditText otpEditText = this.n;
                otpEditText.setSelection(otpEditText.getText().length());
                return;
            case 2:
                ((PayUmoneyActivity) getActivity()).setStopEditing(true);
                this.o.requestFocus();
                OtpEditText otpEditText2 = this.o;
                otpEditText2.setSelection(otpEditText2.getText().length());
                return;
            case 3:
                ((PayUmoneyActivity) getActivity()).setStopEditing(true);
                this.p.requestFocus();
                OtpEditText otpEditText3 = this.p;
                otpEditText3.setSelection(otpEditText3.getText().length());
                return;
            default:
                return;
        }
    }

    public void hideKeyBoard() {
        Utils.hideKeyBoard(getActivity(), this.n.getWindowToken());
    }

    public void hideLabelOtpError() {
        if (getActivity() != null && isAdded() && !getActivity().isFinishing()) {
            TextView textView = this.w;
            if (textView != null && textView.getVisibility() == 0) {
                this.w.setVisibility(4);
            }
        }
    }

    public void showError(String text) {
        if (getActivity() != null && !getActivity().isFinishing() && isAdded()) {
            ToastUtils.showLong((Activity) getActivity(), text, true);
        }
    }

    public void setPaymentButtonDisable() {
        this.v.setEnabled(false);
        this.v.getBackground().setAlpha(120);
    }
}
