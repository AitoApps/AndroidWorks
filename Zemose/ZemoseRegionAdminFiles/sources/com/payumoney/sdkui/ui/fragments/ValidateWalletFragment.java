package com.payumoney.sdkui.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.internal.view.SupportMenu;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySDK;
import com.payumoney.core.analytics.LogAnalytics;
import com.payumoney.core.listener.OnLoginErrorListener;
import com.payumoney.core.listener.OnOTPRequestSendListener;
import com.payumoney.core.listener.OnUserLoginListener;
import com.payumoney.core.response.ErrorResponse;
import com.payumoney.core.utils.AnalyticsConstant;
import com.payumoney.core.utils.SdkHelper;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.ui.widgets.CustomDrawableTextView;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class ValidateWalletFragment extends BaseFragment implements OnClickListener, OnLoginErrorListener, OnOTPRequestSendListener {
    private View a;
    /* access modifiers changed from: private */
    public CustomDrawableTextView m;
    /* access modifiers changed from: private */
    public EditText n;
    private final String o = "ValidateWalletFragment";
    private OnUserLoginListener p;
    /* access modifiers changed from: private */
    public String q;
    /* access modifiers changed from: private */
    public TextView r;
    /* access modifiers changed from: private */
    public TextView s;
    private Handler t;
    private Runnable u;
    private double v;
    private final int w = 123;
    /* access modifiers changed from: private */
    public BroadcastReceiver x;

    public static ValidateWalletFragment newInstance() {
        ValidateWalletFragment validateWalletFragment = new ValidateWalletFragment();
        validateWalletFragment.setArguments(new Bundle());
        return validateWalletFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.b = (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT);
        this.q = PayUmoneySDK.getInstance().getUserName();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.a = inflater.inflate(R.layout.fragment_validate_wallet, container, false);
        this.n = (EditText) this.a.findViewById(R.id.edittext_password);
        this.n.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ValidateWalletFragment validateWalletFragment = ValidateWalletFragment.this;
                validateWalletFragment.a((View) validateWalletFragment.r);
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    ValidateWalletFragment validateWalletFragment = ValidateWalletFragment.this;
                    validateWalletFragment.a(validateWalletFragment.m, true, R.string.quick_pay_amount_now, 255);
                    return;
                }
                ValidateWalletFragment validateWalletFragment2 = ValidateWalletFragment.this;
                validateWalletFragment2.a(validateWalletFragment2.m, false, R.string.quick_pay_amount_now, 150);
            }

            public void afterTextChanged(Editable s) {
            }
        });
        this.n.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((keyEvent == null || keyEvent.getKeyCode() != 66) && i != 6) {
                    return false;
                }
                if (!SdkHelper.checkNetwork(ValidateWalletFragment.this.getActivity())) {
                    Toast.makeText(ValidateWalletFragment.this.getActivity(), com.payumoney.core.R.string.disconnected_from_internet, 0).show();
                    return false;
                }
                ValidateWalletFragment validateWalletFragment = ValidateWalletFragment.this;
                validateWalletFragment.a(validateWalletFragment.q, ValidateWalletFragment.this.n.getText().toString().trim());
                ValidateWalletFragment.this.hideKeyboardIfShown();
                return true;
            }
        });
        this.r = (TextView) this.a.findViewById(R.id.otp_message);
        this.s = (TextView) this.a.findViewById(R.id.text_view_resend_otp);
        this.m = (CustomDrawableTextView) this.a.findViewById(R.id.btn_pay_now_otp_screen);
        this.m.setText(getString(R.string.quick_pay_amount_now));
        this.m.setOnClickListener(this);
        this.m.setClickable(true);
        b();
        a(this.m, false, R.string.quick_pay_amount_now, 150);
        initConvenieneceFee(this.a);
        setAmount(this.b);
        this.a.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        this.h.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (ValidateWalletFragment.this.h.getText().toString().equalsIgnoreCase("Details")) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                    hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.VERIFY_OTP);
                    LogAnalytics.logEvent(ValidateWalletFragment.this.getContext(), AnalyticsConstant.SHOW_PAYMENT_DETAILS_CLIKED, hashMap, AnalyticsConstant.CLEVERTAP);
                    ValidateWalletFragment.this.showConvenieneceFee();
                    return;
                }
                HashMap hashMap2 = new HashMap();
                hashMap2.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                hashMap2.put(AnalyticsConstant.PAGE, AnalyticsConstant.VERIFY_OTP);
                LogAnalytics.logEvent(ValidateWalletFragment.this.getContext(), AnalyticsConstant.HIDE_PAYMENT_DETAILS_CLICKED, hashMap2, AnalyticsConstant.CLEVERTAP);
                ValidateWalletFragment.this.hideConvenieneceFee();
            }
        });
        updateConvenienceFee(Double.parseDouble(this.b), this.v);
        if (PayUmoneySDK.getInstance().isMobileNumberRegistered()) {
            this.r.setVisibility(0);
            this.s.setVisibility(0);
            this.s.setOnClickListener(this);
            if (a()) {
                c();
            }
            PayUmoneySDK.getInstance().requestOTPForLogin(this, this.q, "otp_request_api_tag");
        } else {
            this.r.setVisibility(4);
            this.s.setVisibility(4);
        }
        return this.a;
    }

    private boolean a() {
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_SMS") == 0) {
            return true;
        }
        requestPermissions(new String[]{"android.permission.READ_SMS"}, 123);
        return false;
    }

    public void hideKeyboardIfShown() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService("input_method");
        View currentFocus = getActivity().getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(getActivity());
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    public void setListener(OnUserLoginListener listener) {
        this.p = listener;
    }

    public void setConvenienceFee(double convenienceFee) {
        this.v = convenienceFee;
    }

    public void initConvenieneceFee(View layout) {
        this.g = (TextView) layout.findViewById(R.id.quick_pay_balance);
        this.l = (LinearLayout) layout.findViewById(R.id.r_amount_layout);
        this.f = (LinearLayout) layout.findViewById(R.id.l_convenience_fee);
        this.c = (TextView) layout.findViewById(R.id.subtotal_amount);
        this.d = (TextView) layout.findViewById(R.id.convenience_fee_amount);
        this.e = (TextView) layout.findViewById(R.id.total_amount);
        this.h = (TextView) layout.findViewById(R.id.show_button);
        this.i = (TextView) layout.findViewById(R.id.hide_button);
        setUpUIConvenienceFee();
    }

    public void setUpUIConvenienceFee() {
        this.f.setVisibility(8);
        this.h.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ValidateWalletFragment.this.f.setVisibility(0);
                ValidateWalletFragment.this.l.setBackgroundColor(ValidateWalletFragment.this.getActivity().getResources().getColor(R.color.payumoney_white));
                ValidateWalletFragment.this.h.setVisibility(8);
            }
        });
        this.i.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ValidateWalletFragment.this.hideConvenieneceFee();
            }
        });
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_pay_now_otp_screen) {
            if (!SdkHelper.checkNetwork(getActivity())) {
                Toast.makeText(getActivity(), com.payumoney.core.R.string.disconnected_from_internet, 0).show();
                return;
            }
            a(this.q, this.n.getText().toString().trim());
        } else if (view.getId() == R.id.text_view_resend_otp) {
            Log.d("ValidateWalletFragment", "resendOTPClicked()");
            PayUmoneySDK.getInstance().requestOTPForLogin(this, this.q, "otp_request_api_tag");
            b();
            a((View) this.r);
        }
    }

    public void onError(String response, String tag) {
        if (getActivity() != null && !getActivity().isFinishing() && response != null && !response.equalsIgnoreCase("")) {
            a(this.m, true, R.string.quick_pay_amount_now, 255);
            Toast.makeText(getActivity(), response, 1).show();
        }
    }

    public void missingParam(String description, String tag) {
    }

    public void onFailureResponse(ErrorResponse response, String tag) {
        if (getActivity() != null && !getActivity().isFinishing() && response != null && response.getMessage() != null) {
            a(this.m, true, R.string.quick_pay_amount_now, 255);
            Toast.makeText(getActivity(), response.getMessage(), 1).show();
        }
    }

    public void onOTPRequestSend(String response, String tag) {
        try {
            JSONObject jSONObject = new JSONObject(response);
            if (jSONObject.has("result")) {
                if (jSONObject.getJSONObject("result").has("phone") && jSONObject.getJSONObject("result").getString("phone") != null) {
                    if (!jSONObject.getJSONObject("result").getString("phone").equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                        TextView textView = this.r;
                        StringBuilder sb = new StringBuilder();
                        sb.append("OTP sent to your mobile number ");
                        sb.append(jSONObject.getJSONObject("result").getString("phone"));
                        a((View) textView, sb.toString());
                    }
                }
                a((View) this.r, "OTP sent to your mobile number");
            }
        } catch (Exception e) {
        }
        initiateDelayResendOTP();
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2) {
        a(this.m, false, R.string.quick_pay_amount_now, 150);
        PayUmoneySDK.getInstance().validateAccount(str, str2, this.p, PayUmoneyConstants.VALIDATE_WALLET_FOR_NITRO_FLOW);
    }

    /* access modifiers changed from: private */
    public void a(View view, boolean z, int i, int i2) {
        view.setEnabled(z);
        view.getBackground().setAlpha(i2);
        if (view instanceof CustomDrawableTextView) {
            ((CustomDrawableTextView) view).setText(i);
        }
    }

    private void b() {
        this.s.setEnabled(false);
        this.s.setClickable(false);
        this.s.setTextColor(-7829368);
    }

    public void initiateDelayResendOTP() {
        Handler handler = this.t;
        if (handler != null) {
            Runnable runnable = this.u;
            if (runnable != null) {
                handler.removeCallbacks(runnable);
            }
        }
        this.t = new Handler();
        this.u = new Runnable() {
            public void run() {
                if (ValidateWalletFragment.this.getActivity() != null && !ValidateWalletFragment.this.getActivity().isFinishing()) {
                    ValidateWalletFragment.this.s.setEnabled(true);
                    ValidateWalletFragment.this.s.setClickable(true);
                    ValidateWalletFragment.this.s.setTextColor(ValidateWalletFragment.this.getActivity().getResources().getColor(com.payumoney.core.R.color.primary_green));
                }
            }
        };
        this.t.postDelayed(this.u, 20000);
    }

    public void onDetach() {
        PayUMoneyFragment.moreBankPaymentEntity = null;
        super.onDetach();
    }

    public void onDestroy() {
        if (!(this.x == null || getActivity() == null)) {
            getActivity().unregisterReceiver(this.x);
            this.x = null;
        }
        super.onDestroy();
    }

    /* access modifiers changed from: private */
    public void a(View view) {
        if (view instanceof TextView) {
            ((TextView) view).setText("");
        }
        view.setVisibility(4);
    }

    private void a(View view, String str) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setText(str);
            textView.setTextColor(getActivity().getResources().getColor(com.payumoney.core.R.color.primary_green));
        }
        view.setVisibility(0);
    }

    private void b(View view, String str) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setText(str);
            textView.setTextColor(SupportMenu.CATEGORY_MASK);
        }
        view.setVisibility(0);
    }

    public void onLoginFailed(String errorMessage) {
        b(this.r, errorMessage);
    }

    private void c() {
        if (this.x == null) {
            this.x = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    String str;
                    try {
                        Bundle extras = intent.getExtras();
                        if (ValidateWalletFragment.this.getActivity() != null) {
                            Bundle extras2 = intent.getExtras();
                            if (extras2 != null) {
                                Object[] objArr = (Object[]) extras2.get("pdus");
                                if (objArr != null) {
                                    SmsMessage[] smsMessageArr = new SmsMessage[objArr.length];
                                    str = null;
                                    for (int i = 0; i < smsMessageArr.length; i++) {
                                        if (VERSION.SDK_INT >= 23) {
                                            smsMessageArr[i] = SmsMessage.createFromPdu((byte[]) objArr[i], extras.getString("format"));
                                        } else {
                                            smsMessageArr[i] = SmsMessage.createFromPdu((byte[]) objArr[i]);
                                        }
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(str);
                                        sb.append(smsMessageArr[i].getMessageBody());
                                        str = sb.toString();
                                        smsMessageArr[i].getDisplayOriginatingAddress();
                                    }
                                } else {
                                    str = null;
                                }
                                Matcher matcher = Pattern.compile("(|^)\\d{6}").matcher(str);
                                String str2 = "";
                                while (matcher.find()) {
                                    str2 = matcher.group();
                                }
                                if (!str2.isEmpty() && ValidateWalletFragment.this.n != null && ValidateWalletFragment.this.n.getText() != null && ValidateWalletFragment.this.n.getText().toString().isEmpty()) {
                                    ValidateWalletFragment.this.n.setText(str2);
                                    ValidateWalletFragment.this.n.setSelection(ValidateWalletFragment.this.n.getText().length());
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!ValidateWalletFragment.this.getActivity().isFinishing() && ValidateWalletFragment.this.x != null) {
                        ValidateWalletFragment.this.getActivity().unregisterReceiver(ValidateWalletFragment.this.x);
                        ValidateWalletFragment.this.x = null;
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.setPriority(9999999);
            intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
            getActivity().registerReceiver(this.x, intentFilter);
        }
    }
}
