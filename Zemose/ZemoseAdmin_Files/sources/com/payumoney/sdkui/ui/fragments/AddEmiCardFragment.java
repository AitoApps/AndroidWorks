package com.payumoney.sdkui.ui.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySDK;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.analytics.LogAnalytics;
import com.payumoney.core.entity.EmiTenure;
import com.payumoney.core.entity.PaymentEntity;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.core.entity.TransactionResponse.TransactionStatus;
import com.payumoney.core.listener.OnCardBinDetailsReceived;
import com.payumoney.core.listener.OnPaymentStatusReceivedListener;
import com.payumoney.core.request.PaymentRequest;
import com.payumoney.core.response.BinDetail;
import com.payumoney.core.response.ErrorResponse;
import com.payumoney.core.response.PayumoneyError;
import com.payumoney.core.utils.AnalyticsConstant;
import com.payumoney.core.utils.SdkHelper;
import com.payumoney.core.widget.ExpiryDate;
import com.payumoney.graphics.AssetDownloadManager;
import com.payumoney.graphics.AssetsHelper;
import com.payumoney.graphics.AssetsHelper.CARD;
import com.payumoney.graphics.BitmapCallBack;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.ui.events.FragmentCallbacks;
import com.payumoney.sdkui.ui.utils.PPConstants;
import com.payumoney.sdkui.ui.utils.ResultModel;
import com.payumoney.sdkui.ui.utils.ToastUtils;
import com.payumoney.sdkui.ui.utils.Utils;
import com.payumoney.sdkui.ui.widgets.CustomDrawableTextView;
import com.payumoney.sdkui.ui.widgets.FlipImageView;
import java.util.Calendar;
import java.util.HashMap;
import org.json.JSONObject;

public class AddEmiCardFragment extends BaseFragment implements OnClickListener, OnCardBinDetailsReceived, OnPaymentStatusReceivedListener {
    public static final String ARG_CONVENIENCE_FEE = "conv_fee";
    public static final String ARG_EMI_SELECTED_BANK = "emi_bank";
    public static final String ARG_EMI_SELECTED_TENURE = "emi_tenure";
    public static final String ARG_PAYMENT_ID = "payment_id";
    public static final int CARD_NUMBER_CVV = 6;
    public static final int CARD_NUMBER_ET = 1;
    public static final int CARD_NUMBER_ET_SNACK_BAR = 5;
    public static final int SELECTED_MONTH_VAL = 3;
    public static final int SELECTED_YEAR_VAL = 4;
    /* access modifiers changed from: private */
    public boolean A;
    /* access modifiers changed from: private */
    public boolean B;
    /* access modifiers changed from: private */
    public ImageView C;
    /* access modifiers changed from: private */
    public ImageView D;
    private long E = 0;
    private long F = 0;
    private Calendar G = Calendar.getInstance();
    private PaymentRequest H;
    /* access modifiers changed from: private */
    public boolean I;
    /* access modifiers changed from: private */
    public String J = "";
    private String K = "";
    private String L = "";
    private CustomDrawableTextView M;
    /* access modifiers changed from: private */
    public String N;
    /* access modifiers changed from: private */
    public SwitchCompat O;
    private boolean P = false;
    private String Q;
    private PaymentEntity R;
    private EmiTenure S;
    private double T;
    private LinearLayout U;
    int a = 23;
    private FragmentCallbacks m;
    /* access modifiers changed from: private */
    public EditText n;
    private ExpiryDate o;
    /* access modifiers changed from: private */
    public EditText p;
    /* access modifiers changed from: private */
    public FlipImageView q;
    /* access modifiers changed from: private */
    public BitmapDrawable r;
    private TextView s;
    /* access modifiers changed from: private */
    public BitmapDrawable t;
    /* access modifiers changed from: private */
    public FlipImageView u;
    /* access modifiers changed from: private */
    public TextView v;
    /* access modifiers changed from: private */
    public TextView w;
    /* access modifiers changed from: private */
    public TextView x;
    private boolean y;
    private boolean z = true;

    /* renamed from: com.payumoney.sdkui.ui.fragments.AddEmiCardFragment$4 reason: invalid class name */
    class AnonymousClass4 implements BitmapCallBack {
        final /* synthetic */ AddEmiCardFragment a;

        public void onBitmapReceived(Bitmap bitmap) {
            if (this.a.getActivity() != null && this.a.isAdded() && !this.a.getActivity().isFinishing()) {
                this.a.u.setImageBitmap(bitmap);
            }
        }

        public void onBitmapFailed(Bitmap bitmap) {
            if (this.a.getActivity() != null && this.a.isAdded() && !this.a.getActivity().isFinishing()) {
                this.a.u.setImageBitmap(bitmap);
            }
        }
    }

    private class CardFieldTextWatcher implements TextWatcher {
        final /* synthetic */ AddEmiCardFragment a;

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            this.a.m();
        }

        public void afterTextChanged(Editable s) {
        }
    }

    private class CardNumberTextWatcher implements TextWatcher {
        private String b;
        private String c;

        private CardNumberTextWatcher() {
            this.b = "";
            this.c = null;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.toString().replace(" ", "").length() >= 6) {
                this.b = charSequence.toString().replace(" ", "").subSequence(0, 6).toString();
            } else {
                this.b = "";
            }
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            AddEmiCardFragment.this.v.setVisibility(4);
            if (charSequence.toString().replace(" ", "").length() >= 6) {
                if (this.c == null || !this.b.equalsIgnoreCase(charSequence.toString().replace(" ", "").subSequence(0, 6).toString())) {
                    this.c = SdkHelper.getIssuer(charSequence.toString().replace(" ", "").subSequence(0, 6).toString());
                }
                String str = this.c;
                if (str == null || str.length() <= 1) {
                    AddEmiCardFragment.this.n.setFilters(new InputFilter[]{new LengthFilter(23)});
                } else {
                    if (this.c == "AMEX") {
                        AddEmiCardFragment.this.p.setFilters(new InputFilter[]{new LengthFilter(4)});
                    } else {
                        AddEmiCardFragment.this.p.setFilters(new InputFilter[]{new LengthFilter(3)});
                    }
                    String str2 = this.c;
                    if (str2 == "AMEX") {
                        AddEmiCardFragment.this.n.setFilters(new InputFilter[]{new LengthFilter(17)});
                    } else if (str2 == PayUmoneyConstants.MASTER || str2 == PayUmoneyConstants.DINR) {
                        AddEmiCardFragment.this.n.setFilters(new InputFilter[]{new LengthFilter(19)});
                    } else {
                        AddEmiCardFragment.this.n.setFilters(new InputFilter[]{new LengthFilter(23)});
                    }
                }
            } else {
                this.c = null;
                AddEmiCardFragment.this.N = null;
                if (!(AddEmiCardFragment.this.O == null || AddEmiCardFragment.this.D == null)) {
                    AddEmiCardFragment.this.resetBankToDefault();
                    AddEmiCardFragment.this.O.setChecked(true);
                    AddEmiCardFragment.this.O.setEnabled(true);
                    AddEmiCardFragment.this.D.setVisibility(8);
                }
            }
            AddEmiCardFragment.this.m();
        }

        public void afterTextChanged(Editable s) {
            int i = 0;
            if (s.toString().replace(" ", "").length() < 6) {
                AddEmiCardFragment.this.C.setVisibility(8);
                AddEmiCardFragment addEmiCardFragment = AddEmiCardFragment.this;
                addEmiCardFragment.a((Drawable) addEmiCardFragment.r);
            } else if (!this.b.equalsIgnoreCase(s.toString().replace(" ", "").subSequence(0, 6).toString())) {
                PayUmoneySDK instance = PayUmoneySDK.getInstance();
                AddEmiCardFragment addEmiCardFragment2 = AddEmiCardFragment.this;
                String replace = s.toString().replace(" ", "");
                StringBuilder sb = new StringBuilder();
                sb.append("card_bin_api_tag");
                sb.append(s.toString().replace(" ", "").subSequence(0, 6).toString());
                instance.getCardBinDetails(addEmiCardFragment2, replace, sb.toString());
                AddEmiCardFragment.this.I = false;
                AddEmiCardFragment addEmiCardFragment3 = AddEmiCardFragment.this;
                addEmiCardFragment3.a((Drawable) addEmiCardFragment3.r);
            }
            String str = this.c;
            int i2 = 4;
            if (str == null || str.length() <= 1 || !this.c.equalsIgnoreCase("AMEX")) {
                while (i < s.length()) {
                    if (' ' == s.charAt(i)) {
                        int i3 = i + 1;
                        if (i3 % 5 != 0 || i3 == s.length()) {
                            s.delete(i, i3);
                        }
                    }
                    i++;
                }
                while (i2 < s.length()) {
                    if ("0123456789".indexOf(s.charAt(i2)) >= 0) {
                        s.insert(i2, " ");
                    }
                    i2 += 5;
                }
            } else {
                while (i < s.length()) {
                    if (' ' == s.charAt(i)) {
                        int i4 = i + 1;
                        if (!(i4 == 5 || i4 == 12) || i4 == s.length()) {
                            s.delete(i, i4);
                        }
                    }
                    i++;
                }
                while (i2 < s.length()) {
                    if ("0123456789".indexOf(s.charAt(i2)) >= 0) {
                        s.insert(i2, " ");
                    }
                    i2 += 7;
                }
            }
            if (AddEmiCardFragment.this.n.getSelectionStart() > 0 && s.charAt(AddEmiCardFragment.this.n.getSelectionStart() - 1) == ' ') {
                AddEmiCardFragment.this.n.setSelection(AddEmiCardFragment.this.n.getSelectionStart() - 1);
            }
        }
    }

    public class FocusListener implements OnFocusChangeListener {
        public FocusListener() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (AddEmiCardFragment.this.J == null) {
                AddEmiCardFragment.this.J = "";
            }
            if (v.getId() == R.id.add_card_cardNumber) {
                if (!hasFocus) {
                    AddEmiCardFragment.this.j();
                }
            } else if (v.getId() == R.id.add_card_cardExpiry) {
                if (!hasFocus) {
                    AddEmiCardFragment.this.k();
                }
            } else if (v.getId() == R.id.add_card_cardCvv && !hasFocus) {
                AddEmiCardFragment.this.l();
            }
            if (!hasFocus) {
                AddEmiCardFragment.this.m();
            }
        }
    }

    public static AddEmiCardFragment newInstance(String paymentId, PaymentEntity selectedEmiBank, EmiTenure selectedEmiTenure, double convenienceFee) {
        AddEmiCardFragment addEmiCardFragment = new AddEmiCardFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PAYMENT_ID, paymentId);
        bundle.putParcelable(ARG_EMI_SELECTED_BANK, selectedEmiBank);
        bundle.putParcelable(ARG_EMI_SELECTED_TENURE, selectedEmiTenure);
        bundle.putDouble(ARG_CONVENIENCE_FEE, convenienceFee);
        addEmiCardFragment.setArguments(bundle);
        return addEmiCardFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.y = false;
            this.Q = getArguments().getString(ARG_PAYMENT_ID);
            this.R = (PaymentEntity) getArguments().getParcelable(ARG_EMI_SELECTED_BANK);
            this.S = (EmiTenure) getArguments().getParcelable(ARG_EMI_SELECTED_TENURE);
            this.T = getArguments().getDouble(ARG_CONVENIENCE_FEE);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_add_card_fragment_emi, container, false);
        this.U = (LinearLayout) inflate.findViewById(R.id.ll_switch_compat);
        if (Utils.checkIfLoggedInOrNitroLoggedIn()) {
            this.U.setVisibility(0);
        } else if (PayUmoneySDK.getInstance().isUserSignUpDisabled()) {
            this.U.setVisibility(8);
        }
        this.O = (SwitchCompat) inflate.findViewById(R.id.switch_save_card);
        this.O.setChecked(true);
        getActivity().getWindow().setSoftInputMode(16);
        a(inflate);
        initConvenieneceFee(inflate);
        a(inflate, this.S);
        String str = (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT);
        setAmount(str);
        updateConvenienceFee(Double.parseDouble(str), this.T);
        inflate.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        this.h.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (AddEmiCardFragment.this.h.getText().toString().equalsIgnoreCase("Details")) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.ADD_EMI_CARD);
                    hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                    LogAnalytics.logEvent(AddEmiCardFragment.this.getContext(), AnalyticsConstant.SHOW_PAYMENT_DETAILS_CLIKED, hashMap, AnalyticsConstant.CLEVERTAP);
                    AddEmiCardFragment.this.showConvenieneceFee();
                    return;
                }
                HashMap hashMap2 = new HashMap();
                hashMap2.put(AnalyticsConstant.PAGE, AnalyticsConstant.ADD_EMI_CARD);
                hashMap2.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                LogAnalytics.logEvent(AddEmiCardFragment.this.getContext(), AnalyticsConstant.HIDE_PAYMENT_DETAILS_CLICKED, hashMap2, AnalyticsConstant.CLEVERTAP);
                AddEmiCardFragment.this.hideConvenieneceFee();
            }
        });
        hideConvenieneceFee();
        a();
        b();
        return inflate;
    }

    private void a(View view, EmiTenure emiTenure) {
        String format = String.format(" | %s", new Object[]{String.format("%s@%s%%", new Object[]{emiTenure.getTitle(), Utils.getProcessedDisplayAmount(Double.valueOf(emiTenure.getEmiBankInterest()).doubleValue())})});
        String format2 = String.format("EMI - %s | Interest - %s", new Object[]{getString(R.string.pnp_amount_text, Utils.getProcessedDisplayAmount(Double.valueOf(emiTenure.getEmiValue()).doubleValue())), getString(R.string.pnp_amount_text, Utils.getProcessedDisplayAmount(Double.valueOf(emiTenure.getEmiInterestPaid()).doubleValue()))});
        View findViewById = view.findViewById(R.id.emi_add_card_details);
        TextView textView = (TextView) findViewById.findViewById(R.id.tv_emi_add_card_emi_bank_name);
        TextView textView2 = (TextView) findViewById.findViewById(R.id.tv_emi_add_card_emi_bank_tenure);
        TextView textView3 = (TextView) findViewById.findViewById(R.id.tv_emi_add_card_emi_details);
        textView.setText(this.R.getTitle());
        textView2.setText(format);
        textView3.setText(format2);
        view.findViewById(R.id.btn_emi_tenure_change).setOnClickListener(this);
        view.findViewById(R.id.tv_emi_add_card_tnc).setOnClickListener(this);
    }

    private void a() {
        AssetDownloadManager.getInstance().getCardBitmap(CARD.DEFAULT, new BitmapCallBack() {
            public void onBitmapReceived(Bitmap bitmap) {
                if (AddEmiCardFragment.this.getActivity() != null && AddEmiCardFragment.this.isAdded() && !AddEmiCardFragment.this.getActivity().isFinishing()) {
                    AddEmiCardFragment addEmiCardFragment = AddEmiCardFragment.this;
                    addEmiCardFragment.r = new BitmapDrawable(addEmiCardFragment.getActivity().getResources(), bitmap);
                    AddEmiCardFragment.this.q.setDrawable(AddEmiCardFragment.this.r);
                }
            }

            public void onBitmapFailed(Bitmap bitmap) {
                if (AddEmiCardFragment.this.getActivity() != null && AddEmiCardFragment.this.isAdded() && !AddEmiCardFragment.this.getActivity().isFinishing()) {
                    AddEmiCardFragment addEmiCardFragment = AddEmiCardFragment.this;
                    addEmiCardFragment.r = new BitmapDrawable(addEmiCardFragment.getActivity().getResources(), bitmap);
                    AddEmiCardFragment.this.q.setDrawable(AddEmiCardFragment.this.r);
                }
            }
        });
    }

    private void b() {
        BitmapDrawable bitmapDrawable = this.t;
        if (bitmapDrawable != null) {
            this.u.setDrawable(bitmapDrawable);
        } else {
            AssetDownloadManager.getInstance().getBankBitmapByBankCode(PPConstants.DEFAULT_BANK_NAME, new BitmapCallBack() {
                public void onBitmapReceived(Bitmap bitmap) {
                    if (AddEmiCardFragment.this.getActivity() != null && AddEmiCardFragment.this.isAdded() && !AddEmiCardFragment.this.getActivity().isFinishing()) {
                        AddEmiCardFragment addEmiCardFragment = AddEmiCardFragment.this;
                        addEmiCardFragment.t = new BitmapDrawable(addEmiCardFragment.getActivity().getResources(), bitmap);
                        AddEmiCardFragment.this.u.setDrawable(AddEmiCardFragment.this.t);
                    }
                }

                public void onBitmapFailed(Bitmap bitmap) {
                    if (AddEmiCardFragment.this.getActivity() != null && AddEmiCardFragment.this.isAdded() && !AddEmiCardFragment.this.getActivity().isFinishing()) {
                        AddEmiCardFragment addEmiCardFragment = AddEmiCardFragment.this;
                        addEmiCardFragment.t = new BitmapDrawable(addEmiCardFragment.getActivity().getResources(), bitmap);
                        AddEmiCardFragment.this.u.setDrawable(AddEmiCardFragment.this.t);
                    }
                }
            });
        }
    }

    private void a(View view) {
        this.n = (EditText) view.findViewById(R.id.add_card_cardNumber);
        this.n.setFilters(new InputFilter[]{new LengthFilter(this.a)});
        this.n.addTextChangedListener(new CardNumberTextWatcher());
        this.n.setOnFocusChangeListener(new FocusListener());
        this.C = (ImageView) view.findViewById(R.id.img_info_about_card);
        this.C.setOnClickListener(this);
        this.C.setColorFilter(Utils.getPrimaryColor(getActivity()));
        this.D = (ImageView) view.findViewById(R.id.img_info_save_card);
        this.D.setOnClickListener(this);
        this.D.setColorFilter(Utils.getPrimaryColor(getActivity()));
        this.s = (TextView) view.findViewById(R.id.add_card_bankname);
        this.u = (FlipImageView) view.findViewById(R.id.add_bank_logo);
        this.o = (ExpiryDate) view.findViewById(R.id.add_card_cardExpiry);
        this.o.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && AddEmiCardFragment.this.w.getVisibility() == 0) {
                    AddEmiCardFragment.this.w.setVisibility(4);
                }
            }

            public void afterTextChanged(Editable s) {
                AddEmiCardFragment.this.m();
            }
        });
        this.o.setOnFocusChangeListener(new FocusListener());
        this.p = (EditText) view.findViewById(R.id.add_card_cardCvv);
        this.p.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && AddEmiCardFragment.this.x.getVisibility() == 0) {
                    AddEmiCardFragment.this.x.setVisibility(4);
                }
            }

            public void afterTextChanged(Editable s) {
                if (!SdkHelper.isValidCvv(s.toString(), AddEmiCardFragment.this.J)) {
                    AddEmiCardFragment.this.setPaymentButtonDisable();
                } else {
                    AddEmiCardFragment.this.m();
                }
            }
        });
        this.p.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 6) {
                    Utils.hideKeyBoard(AddEmiCardFragment.this.getActivity(), AddEmiCardFragment.this.p.getWindowToken());
                    if (AddEmiCardFragment.this.getActivity() != null && !AddEmiCardFragment.this.getActivity().isFinishing()) {
                        if (AddEmiCardFragment.this.m()) {
                            AddEmiCardFragment.this.setPaymentButtonEnable();
                            AddEmiCardFragment.this.e();
                            return true;
                        }
                        AddEmiCardFragment.this.setPaymentButtonDisable();
                        return false;
                    }
                }
                return false;
            }
        });
        this.p.setOnFocusChangeListener(new FocusListener());
        this.q = (FlipImageView) view.findViewById(R.id.add_card_cardType_image);
        this.M = (CustomDrawableTextView) view.findViewById(R.id.btn_pay_quick_pay_emi);
        this.v = (TextView) view.findViewById(R.id.tv_error_card_number);
        this.w = (TextView) view.findViewById(R.id.tv_error_expiry_date);
        this.x = (TextView) view.findViewById(R.id.tv_error_cvv);
        this.M.setOnClickListener(this);
        this.q.setClickable(false);
        this.u.setClickable(false);
        setPaymentButtonDisable();
    }

    private void c() {
        this.q.setInterpolator(new AccelerateDecelerateInterpolator());
        this.q.setDuration(0);
        this.q.setAnimated(true);
        this.q.setRotationYEnabled(true);
        this.q.setRotationXEnabled(false);
        this.q.setRotationZEnabled(false);
    }

    /* access modifiers changed from: private */
    public void d() {
        this.u.setInterpolator(new AccelerateDecelerateInterpolator());
        this.u.setDuration(500);
        this.u.setAnimated(true);
        this.u.setRotationYEnabled(true);
        this.u.setRotationXEnabled(false);
        this.u.setRotationZEnabled(false);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.m = (FragmentCallbacks) context;
        } catch (ClassCastException e) {
            StringBuilder sb = new StringBuilder();
            sb.append(context.toString());
            sb.append(" must implement FragmentCallbacks");
            throw new ClassCastException(sb.toString());
        }
    }

    public void onDetach() {
        super.onDetach();
        this.m = null;
    }

    public void onResume() {
        super.onResume();
    }

    public void resetBankToDefault() {
        this.s.setText(getString(R.string.default_bank_name));
        if (!this.u.getDrawable().equals(this.t)) {
            this.B = false;
            b((Drawable) this.t);
        }
    }

    /* access modifiers changed from: private */
    public void a(Drawable drawable) {
        if (!drawable.equals(this.r)) {
            this.z = true;
            this.q.setDrawable(drawable);
            c();
        } else if (this.z) {
            this.z = false;
            this.q.setDrawable(this.r);
            c();
        }
    }

    private void b(final Drawable drawable) {
        if (!drawable.equals(this.t)) {
            if (!this.B) {
                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                    public void run() {
                        AddEmiCardFragment.this.u.setFlippedDrawable(drawable);
                        AddEmiCardFragment.this.u.toggleFlip();
                        AddEmiCardFragment.this.d();
                        AddEmiCardFragment.this.B = true;
                    }
                }, 100);
            }
        } else if (!this.A) {
            new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                public void run() {
                    AddEmiCardFragment.this.u.toggleFlip();
                    AddEmiCardFragment.this.d();
                    AddEmiCardFragment.this.A = true;
                }
            }, 100);
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        if (!SdkHelper.checkNetwork(getContext())) {
            showNoNetworkError();
        } else if (!validateCardDetails(this.J)) {
            setPaymentButtonDisable();
        } else if (i()) {
            HashMap hashMap = new HashMap();
            hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
            hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.ADD_EMI_CARD);
            LogAnalytics.logEvent(getContext(), AnalyticsConstant.PAY_NOW_BUTTON_CLICKED, hashMap, AnalyticsConstant.CLEVERTAP);
            HashMap hashMap2 = new HashMap();
            hashMap2.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
            hashMap2.put(AnalyticsConstant.BANK_NAME, this.R.getCode());
            if (!TextUtils.isEmpty(this.J)) {
                hashMap2.put(AnalyticsConstant.CARD_SCHEME, this.J);
            }
            hashMap2.put(AnalyticsConstant.AMOUNT, Double.valueOf(this.j));
            LogAnalytics.logEvent(getContext(), AnalyticsConstant.EMI_CARD_ADDED, hashMap2, AnalyticsConstant.CLEVERTAP);
            String replace = this.n.getText().toString().trim().replace(" ", "");
            this.H = new PaymentRequest();
            this.H.setEmiPayment(true);
            this.H.setPaymentID(this.Q);
            this.H.setPg("EMI");
            this.H.setCardNumber(replace);
            this.H.setConvenienceFee(this.T);
            this.H.setCountryCode(this.N);
            if (Utils.checkIfLoggedInOrNitroLoggedIn() || !PayUmoneySDK.getInstance().isUserSignUpDisabled()) {
                this.H.setStoreCard(this.O.isChecked());
            } else {
                this.H.setStoreCard(false);
            }
            this.H.setBankCode(this.S.getTenureId());
            this.H.setSplitPayment(this.y);
            if (this.o.getMonth() == null || this.o.getYear() == null || this.p.getText().toString().trim().equalsIgnoreCase("")) {
                if (this.o.getMonth() == null) {
                    this.H.setExpiryMonth("02");
                } else {
                    this.H.setExpiryMonth(this.o.getMonth().toString());
                }
                if (this.o.getYear() == null) {
                    this.H.setExpiryYear("2030");
                } else {
                    this.H.setExpiryYear(this.o.getYear().toString());
                }
                if (this.p.getText() == null || this.p.getText().toString().trim().equalsIgnoreCase("")) {
                    this.H.setCvv("123");
                } else {
                    this.H.setCvv(this.p.getText().toString().trim());
                }
            } else {
                this.H.setCvv(this.p.getText().toString().trim());
                this.H.setExpiryMonth(this.o.getMonth().toString());
                this.H.setExpiryYear(this.o.getYear().toString());
            }
            this.H.setProcessor(this.J);
            PayUmoneySDK.getInstance().makePayment(this, this.H, true, getActivity(), "add_card_api_tag");
        } else {
            paymentFailAndShowErrorScreen();
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_pay_quick_pay_emi) {
            if (SystemClock.elapsedRealtime() - this.E >= 1000) {
                this.E = SystemClock.elapsedRealtime();
                Utils.hideKeyBoard(getActivity());
                e();
            }
        } else if (id == R.id.img_info_about_card) {
            if (SystemClock.elapsedRealtime() - this.F >= 1000) {
                this.F = SystemClock.elapsedRealtime();
                g();
            }
        } else if (id == R.id.img_info_save_card) {
            if (SystemClock.elapsedRealtime() - this.F >= 1000) {
                this.F = SystemClock.elapsedRealtime();
                f();
            }
        } else if (id == R.id.btn_emi_tenure_change) {
            HashMap hashMap = new HashMap();
            hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.ADD_EMI_CARD);
            hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
            hashMap.put(AnalyticsConstant.AMOUNT, Double.valueOf(this.j));
            hashMap.put(AnalyticsConstant.BANK_NAME, this.R.getCode());
            hashMap.put(AnalyticsConstant.SAVED_CARD_USED, "No");
            LogAnalytics.logEvent(getContext(), AnalyticsConstant.EMI_BANK_CHANGED, hashMap, AnalyticsConstant.CLEVERTAP);
            this.m.popBackStackTillTag("13");
        } else if (id == R.id.tv_emi_add_card_tnc) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(PayUmoneySdkInitializer.getTermsAndConditionsUrl()));
            startActivity(intent);
        }
    }

    private void f() {
        if (this.P) {
            a(getString(R.string.we_are_unable_to_identify_the_exact_card_being_used));
            return;
        }
        String str = this.N;
        if (str != null && !str.equalsIgnoreCase("IN")) {
            a(getString(R.string.you_are_attempting_to_use_a_card_issued_outside_india));
        }
    }

    private void a(String str) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(1);
        View inflate = View.inflate(getActivity(), R.layout.dialog_signle_button_layout, null);
        TextView textView = (TextView) inflate.findViewById(R.id.single_btn_dialog_message);
        TextView textView2 = (TextView) inflate.findViewById(R.id.single_btn_dialog_btn);
        ((TextView) inflate.findViewById(R.id.single_btn_dialog_header)).setVisibility(8);
        textView.setText(str);
        textView2.setText(getString(R.string.btn_ok));
        textView2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Utils.setCustomBackground(Utils.getColor(getActivity(), R.color.payumoney_white), inflate, getActivity());
        dialog.setContentView(inflate);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void g() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(1);
        View inflate = View.inflate(getActivity(), R.layout.dialog_signle_button_layout, null);
        TextView textView = (TextView) inflate.findViewById(R.id.single_btn_dialog_message);
        TextView textView2 = (TextView) inflate.findViewById(R.id.single_btn_dialog_btn);
        ((TextView) inflate.findViewById(R.id.single_btn_dialog_header)).setVisibility(8);
        textView.setText(getString(R.string.msg_maestro_card_input_detail));
        textView2.setText(getString(R.string.btn_ok));
        textView2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().getAttributes().windowAnimations = R.style.AlertDialogAnimation;
        Utils.setCustomBackground(Utils.getColor(getActivity(), R.color.payumoney_white), inflate, getActivity());
        dialog.setContentView(inflate);
        dialog.show();
    }

    public boolean validateCardDetails(String issuer) {
        int i;
        if (issuer == null) {
            issuer = "";
        }
        String replace = this.n.getText().toString().replace(" ", "");
        String obj = this.p.getText().toString();
        int i2 = -1;
        if (this.o.getText() == null || this.o.getMonth() == null) {
            i = -1;
        } else {
            i = Integer.parseInt(this.o.getMonth().toString());
        }
        if (!(this.o.getText() == null || this.o.getYear() == null)) {
            i2 = Integer.parseInt(this.o.getYear().toString());
        }
        if (replace == null || replace.trim() == null || replace.trim().length() == 0) {
            showValidationError(R.string.err_invalid_card, 5);
            return false;
        } else if (replace.toString().length() < 12 || replace.toString().length() > 19) {
            showValidationError(R.string.err_invalid_card, 5);
            return false;
        } else if (!SdkHelper.validateCardNumber(replace, issuer)) {
            showValidationError(R.string.err_invalid_card, 5);
            return false;
        } else if ((i2 < this.G.get(1) || (i - 1 < this.G.get(2) && i2 == this.G.get(1))) && !issuer.equalsIgnoreCase(PayUmoneyConstants.SMAE)) {
            showValidationError(R.string.err_invalid_expiry_date, 4);
            return false;
        } else if ((this.o.getMonth() == null || this.o.getYear() == null) && !issuer.equalsIgnoreCase(PayUmoneyConstants.SMAE)) {
            showValidationError(R.string.err_invalid_expiry_date, 4);
            return false;
        } else if (SdkHelper.isValidCvv(obj, issuer)) {
            return true;
        } else {
            showValidationError(R.string.err_invalid_cvv, 6);
            return false;
        }
    }

    public void showValidationError(int error_message, int errorView) {
        setPaymentButtonDisable();
        if (errorView != 1) {
            switch (errorView) {
                case 3:
                case 4:
                    String string = getString(error_message);
                    this.w.setVisibility(0);
                    this.w.setText(string);
                    return;
                case 5:
                    break;
                case 6:
                    String string2 = getString(error_message);
                    this.x.setVisibility(0);
                    this.x.setText(string2);
                    return;
                default:
                    return;
            }
        }
        String string3 = getString(error_message);
        this.v.setVisibility(0);
        this.v.setText(string3);
    }

    public void showErrorToEditText(PayumoneyError error) {
        if (error != null && error.getMessage() != null) {
            String message = error.getMessage();
            if (message.toLowerCase().contains("invalid card number")) {
                showValidationError(R.string.err_invalid_card, 1);
            } else if (message.toLowerCase().contains("invalid expiry date")) {
                showValidationError(R.string.err_invalid_expiry_date, 4);
            } else if (message.toLowerCase().contains("invalid cvv")) {
                showValidationError(R.string.err_invalid_cvv, 6);
            } else {
                ToastUtils.showLong((Activity) getActivity(), error.getMessage(), true);
            }
        }
    }

    public void onPause() {
        super.onPause();
    }

    public void showProgress(int msgString) {
        if (getActivity() != null && !getActivity().isFinishing() && isAdded()) {
            this.m.showProgressDialog(false, getString(msgString));
        }
    }

    public void hideProgress() {
        if (getActivity() != null && !getActivity().isFinishing() && isAdded()) {
            this.m.dismissProgressDialog();
        }
    }

    public void showNoNetworkError() {
        if (getActivity() != null && !getActivity().isFinishing() && isAdded()) {
            ToastUtils.showLong((Activity) getActivity(), getString(R.string.no_internet_connection), true);
        }
    }

    public void onSuccess(String payUResponse, String merchantResponse, String tag) {
        TransactionResponse transactionResponse = new TransactionResponse(TransactionStatus.SUCCESSFUL, "Transaction Successful", (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get("txnid"));
        transactionResponse.setTransactionDetails(merchantResponse);
        transactionResponse.setPayuResponse(payUResponse);
        this.m.processAndShowResult(new ResultModel(new PayumoneyError("Transaction Successful"), transactionResponse), false);
    }

    public void onFailure(String payUResponse, String merchantResponse, String tag) {
        String str = "Transaction Failed";
        try {
            JSONObject jSONObject = new JSONObject(payUResponse);
            if (jSONObject.has("status") && jSONObject.getInt("status") == 0 && jSONObject.has("result")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("result");
                if (jSONObject2.has("status") && jSONObject2.getString("status").equalsIgnoreCase("failure") && jSONObject2.has("error_Message") && !jSONObject2.getString("error_Message").equalsIgnoreCase("no error")) {
                    str = jSONObject2.getString("error_Message");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TransactionResponse transactionResponse = new TransactionResponse(TransactionStatus.FAILED, str, (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get("txnid"));
        transactionResponse.setTransactionDetails(merchantResponse);
        transactionResponse.setPayuResponse(payUResponse);
        ResultModel resultModel = new ResultModel(new PayumoneyError(str), transactionResponse);
        FragmentCallbacks fragmentCallbacks = this.m;
        if (fragmentCallbacks != null) {
            fragmentCallbacks.processAndShowResult(resultModel, false);
        }
    }

    public void onCancelled(String payUResponse, String tag) {
        String str = "Transaction Canceled";
        try {
            JSONObject jSONObject = new JSONObject(payUResponse);
            if (jSONObject.has("status") && jSONObject.getInt("status") == 0 && jSONObject.has("result")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("result");
                if (jSONObject2.has("status") && jSONObject2.getString("status").equalsIgnoreCase("failure") && jSONObject2.has("error_Message") && !jSONObject2.getString("error_Message").equalsIgnoreCase("no error")) {
                    str = jSONObject2.getString("error_Message");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TransactionResponse transactionResponse = new TransactionResponse(TransactionStatus.CANCELLED, str, (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get("txnid"));
        transactionResponse.setTransactionDetails("");
        transactionResponse.setPayuResponse("");
        ResultModel resultModel = new ResultModel(new PayumoneyError(str), transactionResponse);
        FragmentCallbacks fragmentCallbacks = this.m;
        if (fragmentCallbacks != null) {
            fragmentCallbacks.processAndShowResult(resultModel, false);
        }
    }

    public void onError(String response, String tag) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            if (tag.contains("add_card_api_tag")) {
                if (response != null && !response.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), response, 1).show();
                }
                getActivity().finish();
            } else if (this.n.getText().toString().replace(" ", "").length() >= 6) {
                StringBuilder sb = new StringBuilder();
                sb.append("card_bin_api_tag");
                sb.append(this.n.getText().toString().replace(" ", "").substring(0, 6));
                if (tag.equalsIgnoreCase(sb.toString())) {
                    this.C.setVisibility(8);
                    this.O.setChecked(false);
                    this.O.setEnabled(false);
                    this.D.setVisibility(0);
                    this.P = true;
                    this.J = SdkHelper.getIssuer(this.n.getText().toString().trim().replace(" ", "").substring(0, 6));
                    this.N = "IN";
                    this.K = PayUmoneyConstants.PAYMENT_MODE_CC;
                    b(tag);
                }
            }
        }
    }

    public void missingParam(String description, String tag) {
        Toast.makeText(getActivity(), description, 0).show();
    }

    public void onFailureResponse(ErrorResponse response, String tag) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            if (tag.contains("add_card_api_tag")) {
                TransactionResponse transactionResponse = new TransactionResponse(TransactionStatus.TRANSACTION_EXPIRY, response.getMessage(), (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get("txnid"));
                transactionResponse.setTransactionDetails("");
                transactionResponse.setPayuResponse("");
                this.m.processAndShowResult(new ResultModel(new PayumoneyError("Transaction Failure"), transactionResponse), false);
            } else if (this.n.getText().toString().replace(" ", "").length() >= 6) {
                StringBuilder sb = new StringBuilder();
                sb.append("card_bin_api_tag");
                sb.append(this.n.getText().toString().replace(" ", "").substring(0, 6));
                if (tag.equalsIgnoreCase(sb.toString())) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("onFailureResponse(): ");
                    sb2.append(response);
                    sb2.append(", ");
                    sb2.append(tag);
                    Log.w("AECF", sb2.toString());
                    this.C.setVisibility(8);
                    this.O.setChecked(false);
                    this.O.setEnabled(false);
                    this.D.setVisibility(0);
                    this.P = true;
                    this.J = SdkHelper.getIssuer(this.n.getText().toString().trim().replace(" ", "").substring(0, 6));
                    this.N = "IN";
                    if (TextUtils.isEmpty(this.J) || !this.J.equalsIgnoreCase(PayUmoneyConstants.SMAE)) {
                        this.K = PayUmoneyConstants.PAYMENT_MODE_CC;
                    } else {
                        this.K = PayUmoneyConstants.PAYMENT_MODE_DC;
                    }
                    b(tag);
                }
            }
        }
    }

    public void onCardBinDetailReceived(BinDetail binDetailresponse, String tag) {
        StringBuilder sb = new StringBuilder();
        sb.append("onCardBinDetailReceived(): ");
        sb.append(binDetailresponse);
        sb.append(", ");
        sb.append(tag);
        Log.d("AECF", sb.toString());
        this.K = binDetailresponse.getCategory();
        this.N = binDetailresponse.getCountryCode();
        this.J = binDetailresponse.getBinOwner();
        this.L = binDetailresponse.getBankCode();
        this.P = false;
        if (this.N.equalsIgnoreCase("IN")) {
            this.O.setChecked(true);
            this.O.setEnabled(true);
            this.D.setVisibility(8);
        }
        a(tag, binDetailresponse.getBankCode(), binDetailresponse.getBankName());
    }

    public void onSuccess(String response, String tag) {
    }

    private void b(String str) {
        a(str, null, null);
    }

    private void a(String str, String str2, String str3) {
        String str4 = this.N;
        if (str4 != null && !str4.isEmpty() && !this.N.equalsIgnoreCase("IN")) {
            this.O.setChecked(false);
            this.O.setEnabled(false);
            this.D.setVisibility(0);
        }
        if (this.n.getText().toString().replace(" ", "").length() >= 6) {
            StringBuilder sb = new StringBuilder();
            sb.append("card_bin_api_tag");
            sb.append(this.n.getText().toString().replace(" ", "").substring(0, 6));
            str.equalsIgnoreCase(sb.toString());
        }
        if (this.J == null) {
            this.J = "";
        }
        if (TextUtils.isEmpty(this.J)) {
            a((Drawable) this.r);
            return;
        }
        if (this.J.equalsIgnoreCase("AMEX")) {
            this.p.setFilters(new InputFilter[]{new LengthFilter(4)});
        } else if (this.J.equalsIgnoreCase(PayUmoneyConstants.SMAE)) {
            this.C.setVisibility(0);
        } else {
            this.C.setVisibility(8);
            this.p.setFilters(new InputFilter[]{new LengthFilter(3)});
        }
        try {
            AssetDownloadManager.getInstance().getCardBitmap(AssetsHelper.getCard(SdkHelper.getCardType(this.J.toUpperCase())), new BitmapCallBack() {
                public void onBitmapReceived(Bitmap bitmap) {
                    if (AddEmiCardFragment.this.getActivity() != null && AddEmiCardFragment.this.isAdded() && !AddEmiCardFragment.this.getActivity().isFinishing()) {
                        AddEmiCardFragment.this.a((Drawable) new BitmapDrawable(AddEmiCardFragment.this.getActivity().getResources(), bitmap));
                    }
                }

                public void onBitmapFailed(Bitmap bitmap) {
                    if (AddEmiCardFragment.this.getActivity() != null && AddEmiCardFragment.this.isAdded() && !AddEmiCardFragment.this.getActivity().isFinishing()) {
                        AddEmiCardFragment.this.a((Drawable) new BitmapDrawable(AddEmiCardFragment.this.getActivity().getResources(), bitmap));
                    }
                }
            });
        } catch (Exception e) {
            this.n.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        if (!TextUtils.isEmpty(str3)) {
            this.s.setText(str3);
        }
    }

    private boolean h() {
        if (TextUtils.isEmpty(this.N) || this.N.equalsIgnoreCase("IN")) {
            return false;
        }
        return true;
    }

    private boolean c(String str) {
        if (!TextUtils.isEmpty(str) && !str.equalsIgnoreCase("cc")) {
            return false;
        }
        return true;
    }

    private boolean i() {
        String str = this.J;
        if (str == null || str.equalsIgnoreCase("") || this.K.equalsIgnoreCase("cc")) {
            return true;
        }
        return false;
    }

    public void paymentFailAndShowErrorScreen() {
        StringBuilder sb = new StringBuilder();
        sb.append("The merchant does not support ");
        sb.append(getIssuer(SdkHelper.getCardType(this.J)));
        sb.append(" ");
        sb.append(getCardType(this.K));
        String sb2 = sb.toString();
        TransactionResponse transactionResponse = new TransactionResponse(TransactionStatus.FAILED, sb2, (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get("txnid"));
        transactionResponse.setTransactionDetails("");
        transactionResponse.setPayuResponse("");
        this.m.processAndShowResult(new ResultModel(new PayumoneyError(sb2), transactionResponse), false);
    }

    public String getIssuer(String issuer) {
        char c;
        String upperCase = issuer.toUpperCase();
        switch (upperCase.hashCode()) {
            case -891831603:
                if (upperCase.equals(PayUmoneyConstants.MASTER_CARD)) {
                    c = 0;
                    break;
                }
            case 73257:
                if (upperCase.equals("JCB")) {
                    c = 7;
                    break;
                }
            case 2012639:
                if (upperCase.equals("AMEX")) {
                    c = 5;
                    break;
                }
            case 2358594:
                if (upperCase.equals(PayUmoneyConstants.MAESTRO)) {
                    c = 4;
                    break;
                }
            case 2521846:
                if (upperCase.equals("RPAY")) {
                    c = 2;
                    break;
                }
            case 2548734:
                if (upperCase.equals(PayUmoneyConstants.SMAE)) {
                    c = 8;
                    break;
                }
            case 2634817:
                if (upperCase.equals("VISA")) {
                    c = 1;
                    break;
                }
            case 72205995:
                if (upperCase.equals(PayUmoneyConstants.LASER)) {
                    c = 3;
                    break;
                }
            case 2016591933:
                if (upperCase.equals(PayUmoneyConstants.DINERS)) {
                    c = 6;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return "Master";
            case 1:
                return "VISA";
            case 2:
                return "Rupay";
            case 3:
                return "Laser";
            case 4:
                return "Maestro";
            case 5:
                return "AMEX";
            case 6:
                return "Diners";
            case 7:
                return "JCB";
            case 8:
                return "State Bank Maestro";
            default:
                return "";
        }
    }

    public String getCardType(String cardType) {
        if (cardType.equalsIgnoreCase("cc")) {
            return "Credit Cards";
        }
        return "Debit Cards";
    }

    public void setPaymentButtonEnable() {
        this.M.setEnabled(true);
        this.M.getBackground().setAlpha(255);
    }

    public void setPaymentButtonDisable() {
        this.M.setEnabled(false);
        this.M.getBackground().setAlpha(120);
    }

    /* access modifiers changed from: private */
    public boolean j() {
        return a(true);
    }

    private boolean a(boolean z2) {
        String replace = this.n.getText().toString().replace(" ", "");
        if (replace == null || replace.trim() == null || replace.trim().length() == 0) {
            if (z2) {
                showValidationError(R.string.err_invalid_card, 5);
            }
            return false;
        } else if (replace.toString().length() < 12 || replace.toString().length() > 19) {
            if (z2) {
                showValidationError(R.string.err_invalid_card, 5);
            }
            return false;
        } else if (!SdkHelper.validateCardNumber(replace, this.J)) {
            if (z2) {
                showValidationError(R.string.err_invalid_card, 5);
            }
            return false;
        } else if (replace.length() >= 6) {
            return b(z2);
        } else {
            return true;
        }
    }

    private boolean b(boolean z2) {
        if (this.P) {
            if (this.J.equalsIgnoreCase(PayUmoneyConstants.SMAE)) {
                if (z2 && getContext() != null && !((Activity) getContext()).isFinishing()) {
                    a(getContext().getString(R.string.emi_only_credit_card_supported));
                }
                this.O.setChecked(false);
                this.O.setEnabled(false);
                return false;
            }
            resetBankToDefault();
            this.O.setChecked(false);
            this.O.setEnabled(false);
            return true;
        } else if (h()) {
            if (z2 && getContext() != null && !((Activity) getContext()).isFinishing()) {
                a(getContext().getString(R.string.emi_international_card_type_not_supported_message));
            }
            this.O.setChecked(false);
            this.O.setEnabled(false);
            setPaymentButtonDisable();
            return false;
        } else if (!c(this.K)) {
            if (z2 && getContext() != null && !((Activity) getContext()).isFinishing()) {
                a(getContext().getString(R.string.emi_only_credit_card_supported));
            }
            this.O.setChecked(false);
            this.O.setEnabled(false);
            return false;
        } else {
            this.O.setChecked(true);
            this.O.setEnabled(true);
            return true;
        }
    }

    /* access modifiers changed from: private */
    public boolean k() {
        return c(true);
    }

    private boolean c(boolean z2) {
        int i;
        int i2 = -1;
        if (this.o.getText() == null || this.o.getMonth() == null) {
            i = -1;
        } else {
            i = Integer.parseInt(this.o.getMonth().toString());
        }
        if (!(this.o.getText() == null || this.o.getYear() == null)) {
            i2 = Integer.parseInt(this.o.getYear().toString());
        }
        if ((i2 < this.G.get(1) || (i - 1 < this.G.get(2) && i2 == this.G.get(1))) && !this.J.equalsIgnoreCase(PayUmoneyConstants.SMAE)) {
            if (z2) {
                showValidationError(R.string.err_invalid_expiry_date, 4);
            }
            return false;
        } else if ((this.o.getMonth() != null && this.o.getYear() != null) || this.J.equalsIgnoreCase(PayUmoneyConstants.SMAE)) {
            return true;
        } else {
            if (z2) {
                showValidationError(R.string.err_invalid_expiry_date, 4);
            }
            return false;
        }
    }

    /* access modifiers changed from: private */
    public boolean l() {
        return d(true);
    }

    private boolean d(boolean z2) {
        if (SdkHelper.isValidCvv(this.p.getText().toString(), this.J)) {
            return true;
        }
        if (z2) {
            showValidationError(R.string.err_invalid_cvv, 6);
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean m() {
        if (!a(false) || !c(false) || !d(false)) {
            setPaymentButtonDisable();
            return false;
        }
        setPaymentButtonEnable();
        return true;
    }
}
