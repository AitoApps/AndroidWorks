package com.payumoney.sdkui.ui.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import com.payumoney.core.analytics.LogAnalytics;
import com.payumoney.core.entity.PaymentEntity;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.core.entity.TransactionResponse.TransactionStatus;
import com.payumoney.core.listener.OnCardBinDetailsReceived;
import com.payumoney.core.listener.OnPaymentStatusReceivedListener;
import com.payumoney.core.request.PaymentRequest;
import com.payumoney.core.response.BinDetail;
import com.payumoney.core.response.ErrorResponse;
import com.payumoney.core.response.PaymentOptionDetails;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

public class AddCardFragment extends BaseFragment implements OnClickListener, OnCardBinDetailsReceived, OnPaymentStatusReceivedListener {
    public static final int CARD_NUMBER_CVV = 6;
    public static final int CARD_NUMBER_ET = 1;
    public static final int CARD_NUMBER_ET_SNACK_BAR = 5;
    public static final String CREDIT_CARD_LIST = "credit_card_list";
    public static final String DEBIT_CARD_LIST = "debit_card_list";
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
    private PaymentOptionDetails G;
    /* access modifiers changed from: private */
    public Calendar H = Calendar.getInstance();
    private PaymentRequest I;
    private double J;
    /* access modifiers changed from: private */
    public boolean K;
    /* access modifiers changed from: private */
    public String L = "";
    private ArrayList<PaymentEntity> M;
    private ArrayList<PaymentEntity> N;
    private String O = "";
    private CustomDrawableTextView P;
    private String Q;
    /* access modifiers changed from: private */
    public SwitchCompat R;
    private boolean S = false;
    private LinearLayout T;
    int a = 23;
    private FragmentCallbacks m;
    /* access modifiers changed from: private */
    public EditText n;
    /* access modifiers changed from: private */
    public ExpiryDate o;
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
            AddCardFragment.this.v.setVisibility(4);
            if (charSequence.toString().replace(" ", "").length() >= 6) {
                if (this.c == null || !this.b.equalsIgnoreCase(charSequence.toString().replace(" ", "").subSequence(0, 6).toString())) {
                    this.c = SdkHelper.getIssuer(charSequence.toString().replace(" ", "").subSequence(0, 6).toString());
                }
                String str = this.c;
                if (str == null || str.length() <= 1) {
                    AddCardFragment.this.n.setFilters(new InputFilter[]{new LengthFilter(23)});
                    return;
                }
                if (this.c == "AMEX") {
                    AddCardFragment.this.p.setFilters(new InputFilter[]{new LengthFilter(4)});
                } else {
                    AddCardFragment.this.p.setFilters(new InputFilter[]{new LengthFilter(3)});
                }
                String str2 = this.c;
                if (str2 == "AMEX") {
                    AddCardFragment.this.n.setFilters(new InputFilter[]{new LengthFilter(17)});
                } else if (str2 == PayUmoneyConstants.MASTER || str2 == PayUmoneyConstants.DINR) {
                    AddCardFragment.this.n.setFilters(new InputFilter[]{new LengthFilter(19)});
                } else {
                    AddCardFragment.this.n.setFilters(new InputFilter[]{new LengthFilter(23)});
                }
            } else {
                this.c = null;
                if (AddCardFragment.this.R != null && AddCardFragment.this.D != null) {
                    AddCardFragment.this.R.setChecked(true);
                    AddCardFragment.this.R.setEnabled(true);
                    AddCardFragment.this.D.setVisibility(8);
                }
            }
        }

        public void afterTextChanged(Editable s) {
            int i = 0;
            if (s.toString().replace(" ", "").length() < 6) {
                AddCardFragment.this.C.setVisibility(8);
                AddCardFragment addCardFragment = AddCardFragment.this;
                addCardFragment.a((Drawable) addCardFragment.r);
                AddCardFragment.this.hideConvinienceFeeForCard();
            } else if (!this.b.equalsIgnoreCase(s.toString().replace(" ", "").subSequence(0, 6).toString())) {
                PayUmoneySDK instance = PayUmoneySDK.getInstance();
                AddCardFragment addCardFragment2 = AddCardFragment.this;
                String replace = s.toString().replace(" ", "");
                StringBuilder sb = new StringBuilder();
                sb.append("card_bin_api_tag");
                sb.append(s.toString().replace(" ", "").subSequence(0, 6).toString());
                instance.getCardBinDetails(addCardFragment2, replace, sb.toString());
                AddCardFragment.this.K = false;
                AddCardFragment addCardFragment3 = AddCardFragment.this;
                addCardFragment3.a((Drawable) addCardFragment3.r);
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
            if (AddCardFragment.this.n.getSelectionStart() > 0 && s.charAt(AddCardFragment.this.n.getSelectionStart() - 1) == ' ') {
                AddCardFragment.this.n.setSelection(AddCardFragment.this.n.getSelectionStart() - 1);
            }
        }
    }

    public class FocusListener implements OnFocusChangeListener {
        public FocusListener() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            int i;
            if (AddCardFragment.this.L == null) {
                AddCardFragment.this.L = "";
            }
            if (v.getId() == R.id.add_card_cardNumber) {
                if (!hasFocus) {
                    String replace = AddCardFragment.this.n.getText().toString().replace(" ", "");
                    if (replace == null || replace.trim() == null || replace.trim().length() == 0) {
                        AddCardFragment.this.showValidationError(R.string.err_invalid_card, 5);
                    } else if (replace.toString().length() < 12 || replace.toString().length() > 19) {
                        AddCardFragment.this.showValidationError(R.string.err_invalid_card, 5);
                    } else if (!SdkHelper.validateCardNumber(replace, AddCardFragment.this.L)) {
                        AddCardFragment.this.showValidationError(R.string.err_invalid_card, 5);
                    } else {
                        AddCardFragment.this.setPaymentButtonEnable();
                    }
                }
            } else if (v.getId() == R.id.add_card_cardExpiry) {
                if (!hasFocus) {
                    int i2 = -1;
                    if (AddCardFragment.this.o.getText() == null || AddCardFragment.this.o.getMonth() == null) {
                        i = -1;
                    } else {
                        i = Integer.parseInt(AddCardFragment.this.o.getMonth().toString());
                    }
                    if (!(AddCardFragment.this.o.getText() == null || AddCardFragment.this.o.getYear() == null)) {
                        i2 = Integer.parseInt(AddCardFragment.this.o.getYear().toString());
                    }
                    if ((i2 < AddCardFragment.this.H.get(1) || (i - 1 < AddCardFragment.this.H.get(2) && i2 == AddCardFragment.this.H.get(1))) && !AddCardFragment.this.L.equalsIgnoreCase(PayUmoneyConstants.SMAE)) {
                        AddCardFragment.this.showValidationError(R.string.err_invalid_expiry_date, 4);
                    } else if ((AddCardFragment.this.o.getMonth() == null || AddCardFragment.this.o.getYear() == null) && !AddCardFragment.this.L.equalsIgnoreCase(PayUmoneyConstants.SMAE)) {
                        AddCardFragment.this.showValidationError(R.string.err_invalid_expiry_date, 4);
                    } else {
                        AddCardFragment.this.setPaymentButtonEnable();
                    }
                }
            } else if (v.getId() == R.id.add_card_cardCvv && !hasFocus) {
                if (!SdkHelper.isValidCvv(AddCardFragment.this.p.getText().toString(), AddCardFragment.this.L)) {
                    AddCardFragment.this.showValidationError(R.string.err_invalid_cvv, 6);
                } else {
                    AddCardFragment.this.setPaymentButtonEnable();
                }
            }
            if (!hasFocus && AddCardFragment.this.getActivity() != null && !AddCardFragment.this.getActivity().isFinishing()) {
                if (AddCardFragment.this.g()) {
                    AddCardFragment.this.setPaymentButtonDisable();
                } else {
                    AddCardFragment.this.setPaymentButtonEnable();
                }
            }
        }
    }

    public static AddCardFragment newInstance(ArrayList creditCardList, ArrayList debitCardList, boolean isSplitPay, PaymentOptionDetails paymentOptionDetails) {
        AddCardFragment addCardFragment = new AddCardFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(PPConstants.ARG_IS_SPLIT_PAY, isSplitPay);
        bundle.putParcelableArrayList(CREDIT_CARD_LIST, creditCardList);
        bundle.putParcelableArrayList(DEBIT_CARD_LIST, debitCardList);
        bundle.putParcelable(PayUmoneyConstants.PAYMENT_DETAILS_OBJECT, paymentOptionDetails);
        addCardFragment.setArguments(bundle);
        return addCardFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.y = getArguments().getBoolean(PPConstants.ARG_IS_SPLIT_PAY);
            this.M = getArguments().getParcelableArrayList(CREDIT_CARD_LIST);
            this.N = getArguments().getParcelableArrayList(DEBIT_CARD_LIST);
            this.G = (PaymentOptionDetails) getArguments().getParcelable(PayUmoneyConstants.PAYMENT_DETAILS_OBJECT);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_add_card_fragment_new, container, false);
        this.T = (LinearLayout) inflate.findViewById(R.id.ll_switch_compat);
        if (Utils.checkIfLoggedInOrNitroLoggedIn()) {
            this.T.setVisibility(0);
        } else if (PayUmoneySDK.getInstance().isUserSignUpDisabled()) {
            this.T.setVisibility(8);
        }
        this.R = (SwitchCompat) inflate.findViewById(R.id.switch_save_card);
        this.R.setChecked(true);
        getActivity().getWindow().setSoftInputMode(16);
        a(inflate);
        initConvenieneceFee(inflate);
        setAmount((String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT));
        inflate.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        this.h.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (AddCardFragment.this.h.getText().toString().equalsIgnoreCase("Details")) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.ADD_CARD);
                    hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                    LogAnalytics.logEvent(AddCardFragment.this.getContext(), AnalyticsConstant.SHOW_PAYMENT_DETAILS_CLIKED, hashMap, AnalyticsConstant.CLEVERTAP);
                    AddCardFragment.this.showConvenieneceFee();
                    return;
                }
                HashMap hashMap2 = new HashMap();
                hashMap2.put(AnalyticsConstant.PAGE, AnalyticsConstant.ADD_CARD);
                hashMap2.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                LogAnalytics.logEvent(AddCardFragment.this.getContext(), AnalyticsConstant.HIDE_PAYMENT_DETAILS_CLICKED, hashMap2, AnalyticsConstant.CLEVERTAP);
                AddCardFragment.this.hideConvenieneceFee();
            }
        });
        hideConvinienceFeeForCard();
        AssetDownloadManager.getInstance().getCardBitmap(CARD.DEFAULT, new BitmapCallBack() {
            public void onBitmapReceived(Bitmap bitmap) {
                if (AddCardFragment.this.getActivity() != null && AddCardFragment.this.isAdded() && !AddCardFragment.this.getActivity().isFinishing()) {
                    AddCardFragment addCardFragment = AddCardFragment.this;
                    addCardFragment.r = new BitmapDrawable(addCardFragment.getActivity().getResources(), bitmap);
                    AddCardFragment.this.q.setDrawable(AddCardFragment.this.r);
                }
            }

            public void onBitmapFailed(Bitmap bitmap) {
                if (AddCardFragment.this.getActivity() != null && AddCardFragment.this.isAdded() && !AddCardFragment.this.getActivity().isFinishing()) {
                    AddCardFragment addCardFragment = AddCardFragment.this;
                    addCardFragment.r = new BitmapDrawable(addCardFragment.getActivity().getResources(), bitmap);
                    AddCardFragment.this.q.setDrawable(AddCardFragment.this.r);
                }
            }
        });
        a();
        return inflate;
    }

    private void a() {
        BitmapDrawable bitmapDrawable = this.t;
        if (bitmapDrawable != null) {
            this.u.setDrawable(bitmapDrawable);
        } else {
            AssetDownloadManager.getInstance().getBankBitmapByBankCode(PPConstants.DEFAULT_BANK_NAME, new BitmapCallBack() {
                public void onBitmapReceived(Bitmap bitmap) {
                    if (AddCardFragment.this.getActivity() != null && AddCardFragment.this.isAdded() && !AddCardFragment.this.getActivity().isFinishing()) {
                        AddCardFragment addCardFragment = AddCardFragment.this;
                        addCardFragment.t = new BitmapDrawable(addCardFragment.getActivity().getResources(), bitmap);
                        AddCardFragment.this.u.setDrawable(AddCardFragment.this.t);
                    }
                }

                public void onBitmapFailed(Bitmap bitmap) {
                    if (AddCardFragment.this.getActivity() != null && AddCardFragment.this.isAdded() && !AddCardFragment.this.getActivity().isFinishing()) {
                        AddCardFragment addCardFragment = AddCardFragment.this;
                        addCardFragment.t = new BitmapDrawable(addCardFragment.getActivity().getResources(), bitmap);
                        AddCardFragment.this.u.setDrawable(AddCardFragment.this.t);
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
                if (!TextUtils.isEmpty(s) && AddCardFragment.this.w.getVisibility() == 0) {
                    AddCardFragment.this.w.setVisibility(4);
                }
            }

            public void afterTextChanged(Editable s) {
            }
        });
        this.o.setOnFocusChangeListener(new FocusListener());
        this.p = (EditText) view.findViewById(R.id.add_card_cardCvv);
        this.p.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && AddCardFragment.this.x.getVisibility() == 0) {
                    AddCardFragment.this.x.setVisibility(4);
                }
            }

            public void afterTextChanged(Editable s) {
                if (!SdkHelper.isValidCvv(s.toString(), AddCardFragment.this.L)) {
                    AddCardFragment.this.setPaymentButtonDisable();
                } else {
                    AddCardFragment.this.setPaymentButtonEnable();
                }
                if (AddCardFragment.this.getActivity() != null && !AddCardFragment.this.getActivity().isFinishing()) {
                    if (AddCardFragment.this.g()) {
                        AddCardFragment.this.setPaymentButtonDisable();
                    } else {
                        AddCardFragment.this.setPaymentButtonEnable();
                    }
                }
            }
        });
        this.p.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 6) {
                    Utils.hideKeyBoard(AddCardFragment.this.getActivity(), AddCardFragment.this.p.getWindowToken());
                    if (AddCardFragment.this.getActivity() != null && !AddCardFragment.this.getActivity().isFinishing()) {
                        if (AddCardFragment.this.g()) {
                            AddCardFragment.this.setPaymentButtonDisable();
                            return false;
                        }
                        AddCardFragment.this.setPaymentButtonEnable();
                        AddCardFragment.this.d();
                        return true;
                    }
                }
                return false;
            }
        });
        this.p.setOnFocusChangeListener(new FocusListener());
        this.q = (FlipImageView) view.findViewById(R.id.add_card_cardType_image);
        this.P = (CustomDrawableTextView) view.findViewById(R.id.btn_pay_quick_pay);
        this.v = (TextView) view.findViewById(R.id.tv_error_card_number);
        this.w = (TextView) view.findViewById(R.id.tv_error_expiry_date);
        this.x = (TextView) view.findViewById(R.id.tv_error_cvv);
        this.P.setText(getString(R.string.quick_pay_amount_now));
        this.P.setOnClickListener(this);
        this.q.setClickable(false);
        this.u.setClickable(false);
    }

    private void b() {
        this.q.setInterpolator(new AccelerateDecelerateInterpolator());
        this.q.setDuration(0);
        this.q.setAnimated(true);
        this.q.setRotationYEnabled(true);
        this.q.setRotationXEnabled(false);
        this.q.setRotationZEnabled(false);
    }

    /* access modifiers changed from: private */
    public void c() {
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
            b();
        } else if (this.z) {
            this.z = false;
            this.q.setDrawable(this.r);
            b();
        }
    }

    private void b(final Drawable drawable) {
        if (!drawable.equals(this.t)) {
            if (!this.B) {
                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                    public void run() {
                        AddCardFragment.this.u.setFlippedDrawable(drawable);
                        AddCardFragment.this.u.toggleFlip();
                        AddCardFragment.this.c();
                        AddCardFragment.this.B = true;
                    }
                }, 100);
            }
        } else if (!this.A) {
            new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                public void run() {
                    AddCardFragment.this.u.toggleFlip();
                    AddCardFragment.this.c();
                    AddCardFragment.this.A = true;
                }
            }, 100);
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        if (!SdkHelper.checkNetwork(getContext())) {
            showNoNetworkError();
        } else if (!validateCardDetails(this.L) || !this.K) {
            setPaymentButtonDisable();
        } else if (h()) {
            String replace = this.n.getText().toString().trim().replace(" ", "");
            this.I = new PaymentRequest();
            this.I.setPaymentID(this.G.getPaymentID());
            this.I.setPg(this.O);
            this.I.setCardNumber(replace);
            this.I.setConvenienceFee(this.J);
            this.I.setCountryCode(this.Q);
            if (Utils.checkIfLoggedInOrNitroLoggedIn() || !PayUmoneySDK.getInstance().isUserSignUpDisabled()) {
                this.I.setStoreCard(this.R.isChecked());
            } else {
                this.I.setStoreCard(false);
            }
            this.I.setBankCode(this.L);
            this.I.setSplitPayment(this.y);
            if (this.o.getMonth() == null || this.o.getYear() == null || this.p.getText().toString().trim().equalsIgnoreCase("")) {
                if (this.o.getMonth() == null) {
                    this.I.setExpiryMonth("02");
                } else {
                    this.I.setExpiryMonth(this.o.getMonth().toString());
                }
                if (this.o.getYear() == null) {
                    this.I.setExpiryYear("2030");
                } else {
                    this.I.setExpiryYear(this.o.getYear().toString());
                }
                if (this.p.getText() == null || this.p.getText().toString().trim().equalsIgnoreCase("")) {
                    this.I.setCvv("123");
                } else {
                    this.I.setCvv(this.p.getText().toString().trim());
                }
            } else {
                this.I.setCvv(this.p.getText().toString().trim());
                this.I.setExpiryMonth(this.o.getMonth().toString());
                this.I.setExpiryYear(this.o.getYear().toString());
            }
            this.I.setProcessor(this.L);
            PayUmoneySDK.getInstance().makePayment(this, this.I, true, getActivity(), "add_card_api_tag");
        } else {
            paymentFailAndShowErrorScreen();
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_pay_quick_pay) {
            HashMap hashMap = new HashMap();
            hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
            hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.ADD_CARD);
            LogAnalytics.logEvent(getContext(), AnalyticsConstant.PAY_NOW_BUTTON_CLICKED, hashMap, AnalyticsConstant.CLEVERTAP);
            if (SystemClock.elapsedRealtime() - this.E >= 1000) {
                this.E = SystemClock.elapsedRealtime();
                Utils.hideKeyBoard(getActivity());
                d();
            }
        } else if (id == R.id.img_info_about_card) {
            if (SystemClock.elapsedRealtime() - this.F >= 1000) {
                this.F = SystemClock.elapsedRealtime();
                f();
            }
        } else if (id == R.id.img_info_save_card && SystemClock.elapsedRealtime() - this.F >= 1000) {
            this.F = SystemClock.elapsedRealtime();
            e();
        }
    }

    private void e() {
        if (this.S) {
            a(getString(R.string.we_are_unable_to_identify_the_exact_card_being_used));
            return;
        }
        String str = this.Q;
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

    private void f() {
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
        } else if ((i2 < this.H.get(1) || (i - 1 < this.H.get(2) && i2 == this.H.get(1))) && !issuer.equalsIgnoreCase(PayUmoneyConstants.SMAE)) {
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
                    this.R.setChecked(false);
                    this.R.setEnabled(false);
                    this.D.setVisibility(0);
                    this.S = true;
                    this.L = SdkHelper.getIssuer(this.n.getText().toString().trim().replace(" ", "").substring(0, 6));
                    this.Q = "IN";
                    if (this.G.getCreditCardList() == null || this.G.getCreditCardList().size() <= 0) {
                        this.O = PayUmoneyConstants.PAYMENT_MODE_DC;
                        this.J = SdkHelper.getDCConvenienceFee(this.G.getConvenienceFee(), this.L, this.y, this.Q);
                    } else {
                        this.O = PayUmoneyConstants.PAYMENT_MODE_CC;
                        this.J = SdkHelper.getCCConvenienceFee(this.G.getConvenienceFee(), this.L, this.y, this.Q);
                    }
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
            boolean z2 = false;
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
                    this.C.setVisibility(8);
                    this.R.setChecked(false);
                    this.R.setEnabled(false);
                    this.D.setVisibility(0);
                    this.S = true;
                    this.L = SdkHelper.getIssuer(this.n.getText().toString().trim().replace(" ", "").substring(0, 6));
                    this.Q = "IN";
                    if ((TextUtils.isEmpty(this.L) || !this.L.equalsIgnoreCase(PayUmoneyConstants.SMAE)) && this.G.getCreditCardList() != null && !this.G.getCreditCardList().isEmpty()) {
                        z2 = true;
                    }
                    if (z2) {
                        this.O = PayUmoneyConstants.PAYMENT_MODE_CC;
                        this.J = SdkHelper.getCCConvenienceFee(this.G.getConvenienceFee(), this.L, this.y, this.Q);
                    } else {
                        this.O = PayUmoneyConstants.PAYMENT_MODE_DC;
                        this.J = SdkHelper.getDCConvenienceFee(this.G.getConvenienceFee(), this.L, this.y, this.Q);
                    }
                    b(tag);
                }
            }
        }
    }

    public void onCardBinDetailReceived(BinDetail binDetailresponse, String tag) {
        this.O = binDetailresponse.getCategory();
        this.Q = binDetailresponse.getCountryCode();
        this.L = binDetailresponse.getBinOwner();
        this.S = false;
        if (this.Q.equalsIgnoreCase("IN")) {
            this.R.setChecked(true);
            this.R.setEnabled(true);
            this.D.setVisibility(8);
        }
        if (this.O.equalsIgnoreCase("cc")) {
            this.J = SdkHelper.getCCConvenienceFee(this.G.getConvenienceFee(), binDetailresponse.getBinOwner(), this.y, this.Q);
        } else {
            this.J = SdkHelper.getDCConvenienceFee(this.G.getConvenienceFee(), binDetailresponse.getBinOwner(), this.y, this.Q);
        }
        b(tag);
    }

    public void onSuccess(String response, String tag) {
    }

    private void b(String str) {
        String str2 = this.Q;
        if (str2 != null && !str2.isEmpty() && !this.Q.equalsIgnoreCase("IN")) {
            this.R.setChecked(false);
            this.R.setEnabled(false);
            this.D.setVisibility(0);
        }
        if (this.n.getText().toString().replace(" ", "").length() >= 6) {
            StringBuilder sb = new StringBuilder();
            sb.append("card_bin_api_tag");
            sb.append(this.n.getText().toString().replace(" ", "").substring(0, 6));
            if (str.equalsIgnoreCase(sb.toString())) {
                this.K = true;
                setPaymentButtonEnable();
                showConvenienceFeeOption();
                updateConvenienceFee(Double.parseDouble((String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT)), this.J);
            }
        }
        if (this.L == null) {
            this.L = "";
        }
        String str3 = this.L;
        if (str3 == null || str3.equalsIgnoreCase("")) {
            a((Drawable) this.r);
            return;
        }
        if (this.L.equalsIgnoreCase("AMEX")) {
            this.p.setFilters(new InputFilter[]{new LengthFilter(4)});
        } else if (this.L.equalsIgnoreCase(PayUmoneyConstants.SMAE)) {
            this.C.setVisibility(0);
        } else {
            this.C.setVisibility(8);
            this.p.setFilters(new InputFilter[]{new LengthFilter(3)});
        }
        try {
            AssetDownloadManager.getInstance().getCardBitmap(AssetsHelper.getCard(SdkHelper.getCardType(this.L.toUpperCase())), new BitmapCallBack() {
                public void onBitmapReceived(Bitmap bitmap) {
                    if (AddCardFragment.this.getActivity() != null && AddCardFragment.this.isAdded() && !AddCardFragment.this.getActivity().isFinishing()) {
                        AddCardFragment.this.a((Drawable) new BitmapDrawable(AddCardFragment.this.getActivity().getResources(), bitmap));
                    }
                }

                public void onBitmapFailed(Bitmap bitmap) {
                    if (AddCardFragment.this.getActivity() != null && AddCardFragment.this.isAdded() && !AddCardFragment.this.getActivity().isFinishing()) {
                        AddCardFragment.this.a((Drawable) new BitmapDrawable(AddCardFragment.this.getActivity().getResources(), bitmap));
                    }
                }
            });
        } catch (Exception e) {
            this.n.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        if (!g()) {
            setPaymentButtonEnable();
        } else if (getContext() != null && !((Activity) getContext()).isFinishing()) {
            a(getContext().getString(R.string.international_card_type_not_supported_message));
            setPaymentButtonDisable();
        }
    }

    /* access modifiers changed from: private */
    public boolean g() {
        String str = this.Q;
        if (str == null || str.isEmpty() || this.Q.equalsIgnoreCase("IN") || this.L.equalsIgnoreCase("AMEX") || this.L.equalsIgnoreCase("VISA") || this.L.equalsIgnoreCase(PayUmoneyConstants.MASTER) || this.L.equalsIgnoreCase(PayUmoneyConstants.MASTER_CARD)) {
            return false;
        }
        return true;
    }

    private boolean h() {
        ArrayList<PaymentEntity> arrayList;
        String str;
        String str2 = this.L;
        if (str2 == null || str2.equalsIgnoreCase("")) {
            return true;
        }
        if ((this.M == null && this.O.equalsIgnoreCase("cc")) || (this.N == null && this.O.equalsIgnoreCase("dc"))) {
            return false;
        }
        if (this.O.equalsIgnoreCase("cc")) {
            arrayList = this.M;
        } else {
            arrayList = this.N;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (((PaymentEntity) it.next()).getCode().equalsIgnoreCase(this.L)) {
                return true;
            }
        }
        if (this.L.equalsIgnoreCase(PayUmoneyConstants.DINR) || this.L.equalsIgnoreCase("AMEX")) {
            str = this.L;
        } else {
            str = PayUmoneyConstants.CC;
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            if (((PaymentEntity) it2.next()).getCode().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public void paymentFailAndShowErrorScreen() {
        StringBuilder sb = new StringBuilder();
        sb.append("The merchant does not support ");
        sb.append(getIssuer(SdkHelper.getCardType(this.L)));
        sb.append(" ");
        sb.append(getCardType(this.O));
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

    public void hideConvinienceFeeForCard() {
        if (this.y) {
            updateConvenienceFee(Double.parseDouble(this.b), SdkHelper.getWalletConvenienceFee(this.G.getConvenienceFee()));
            return;
        }
        hideConvenienceFeeOption();
        setDisplayAmount(Double.parseDouble(this.b));
    }

    public void setPaymentButtonEnable() {
        this.P.setEnabled(true);
        this.P.getBackground().setAlpha(255);
    }

    public void setPaymentButtonDisable() {
        this.P.setEnabled(false);
        this.P.getBackground().setAlpha(120);
    }
}
