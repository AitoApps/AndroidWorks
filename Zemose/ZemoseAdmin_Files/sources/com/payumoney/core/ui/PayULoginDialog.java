package com.payumoney.core.ui;

import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.internal.view.SupportMenu;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.payu.custombrowser.util.CBConstant;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySDK;
import com.payumoney.core.R;
import com.payumoney.core.SdkSession;
import com.payumoney.core.analytics.LogAnalytics;
import com.payumoney.core.listener.OnOTPRequestSendListener;
import com.payumoney.core.listener.OnUserLoginListener;
import com.payumoney.core.listener.PayULoginDialogListener;
import com.payumoney.core.response.ErrorResponse;
import com.payumoney.core.utils.AnalyticsConstant;
import com.payumoney.core.utils.SdkHelper;
import com.payumoney.core.utils.SharedPrefsUtils;
import com.payumoney.core.utils.SharedPrefsUtils.Keys;
import com.payumoney.sdkui.ui.fragments.PayUMoneyFragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class PayULoginDialog extends DialogFragment implements OnOTPRequestSendListener {
    AccountManager a = null;
    /* access modifiers changed from: private */
    public AutoCompleteTextView b = null;
    /* access modifiers changed from: private */
    public EditText c = null;
    /* access modifiers changed from: private */
    public Button d = null;
    /* access modifiers changed from: private */
    public TextView e;
    private FragmentActivity f = null;
    /* access modifiers changed from: private */
    public String g = "";
    private BroadcastReceiver h = null;
    private OnUserLoginListener i;
    private int j;
    private PayULoginDialogListener k;
    /* access modifiers changed from: private */
    public TextView l;
    private Handler m;
    private Runnable n;
    /* access modifiers changed from: private */
    public String o = "";
    private final int p = 123;
    /* access modifiers changed from: private */
    public BroadcastReceiver q;

    public class onFocusListener implements OnFocusChangeListener {
        public onFocusListener() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            String obj = PayULoginDialog.this.b.getText().toString();
            if (v.getId() == R.id.email) {
                if (hasFocus) {
                    return;
                }
                if (PayULoginDialog.this.b.getText() == null || PayULoginDialog.this.b.getText().equals("")) {
                    PayULoginDialog.this.b.setError("Please fill the details");
                } else if (SdkHelper.isPhoneNumber(obj)) {
                    if (!SdkHelper.isValidNumber(obj)) {
                        PayULoginDialog.this.o = "";
                        PayULoginDialog.this.b.setError(PayULoginDialog.this.getResources().getString(R.string.email_phone_invalid));
                        return;
                    }
                    PayULoginDialog.this.o = obj.substring(obj.length() - 10);
                } else if (!SdkHelper.isValidEmail(obj)) {
                    PayULoginDialog.this.o = "";
                    PayULoginDialog.this.b.setError(PayULoginDialog.this.getResources().getString(R.string.email_phone_invalid));
                } else {
                    PayULoginDialog.this.o = obj;
                }
            } else if (v.getId() == R.id.password && SdkHelper.isValidateUsername(PayULoginDialog.this.o) && PayULoginDialog.this.a()) {
                PayULoginDialog.this.c();
                PayUmoneySDK instance = PayUmoneySDK.getInstance();
                PayULoginDialog payULoginDialog = PayULoginDialog.this;
                instance.requestOTPForLogin(payULoginDialog, payULoginDialog.o, "otp_request_api_tag");
                PayULoginDialog.this.c.setOnFocusChangeListener(null);
            }
        }
    }

    public static PayULoginDialog newInstance(int theme) {
        PayULoginDialog payULoginDialog = new PayULoginDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("theme", theme);
        payULoginDialog.setArguments(bundle);
        return payULoginDialog;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.j = getArguments().getInt("theme");
        int i2 = this.j;
        if (i2 == -1) {
            setStyle(0, R.style.PayUAppThemedefault);
        } else {
            setStyle(0, i2);
        }
    }

    public void onResume() {
        super.onResume();
        getDialog().setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode != 4) {
                    return false;
                }
                HashMap hashMap = new HashMap();
                hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.LOGIN);
                LogAnalytics.logEvent(PayULoginDialog.this.getContext(), AnalyticsConstant.BACK_BUTTON_CLICKED, hashMap, AnalyticsConstant.CLEVERTAP);
                PayULoginDialog.this.dismiss();
                return true;
            }
        });
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.sdk_activity_login, container, false);
        HashMap hashMap = new HashMap();
        hashMap.put(AnalyticsConstant.MERCHANT_PASSED_EMAIL, PayUmoneySDK.getInstance().getPaymentParam().getParams().get("email"));
        hashMap.put(AnalyticsConstant.MERCHANT_PASSED_PHONE, PayUmoneySDK.getInstance().getPaymentParam().getParams().get("phone"));
        hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        LogAnalytics.logEvent(getContext(), AnalyticsConstant.LOGIN_ATTEMPTED, hashMap, AnalyticsConstant.CLEVERTAP);
        Toolbar toolbar = (Toolbar) inflate.findViewById(R.id.toolbar);
        if (toolbar != null) {
            String stringPreference = SharedPrefsUtils.getStringPreference(getContext(), Keys.MERCHANT_NAME);
            if (stringPreference == null || stringPreference.equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                stringPreference = PayUmoneyConstants.SP_SP_NAME;
            }
            toolbar.setTitle((CharSequence) stringPreference);
            try {
                if (!(PayUmoneyConfig.getInstance() == null || PayUmoneyConfig.getInstance().getTextColorPrimary() == null)) {
                    toolbar.setTitleTextColor(Color.parseColor(PayUmoneyConfig.getInstance().getTextColorPrimary()));
                }
            } catch (Exception e2) {
            }
            toolbar.setNavigationIcon(R.drawable.img_back_arrow);
            toolbar.setNavigationOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    PayULoginDialog.this.dismiss();
                }
            });
        }
        this.f = getActivity();
        this.a = AccountManager.get(getActivity().getApplicationContext());
        this.b = (AutoCompleteTextView) inflate.findViewById(R.id.email);
        this.c = (EditText) inflate.findViewById(R.id.password);
        this.d = (Button) inflate.findViewById(R.id.login);
        this.e = (TextView) inflate.findViewById(R.id.opt_message);
        GradientDrawable gradientDrawable = (GradientDrawable) this.d.getBackground();
        if (this.j != -1) {
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), this.j);
            TypedValue typedValue = new TypedValue();
            contextThemeWrapper.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            getActivity().getTheme();
            int i2 = typedValue.data;
            gradientDrawable.setColor(Color.parseColor(String.format(getString(R.string.payumoney_color_string), new Object[]{Integer.valueOf(i2)})));
        }
        this.d.setEnabled(false);
        this.d.getBackground().setAlpha(150);
        this.b.setAdapter(new ArrayAdapter(getActivity(), 17367050, new ArrayList(new HashSet())));
        this.b.setOnFocusChangeListener(new onFocusListener());
        this.c.setOnFocusChangeListener(new onFocusListener());
        this.g = CBConstant.DEFAULT_VALUE;
        this.b.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view1, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    PayULoginDialog.this.b.showDropDown();
                }
                if (!PayULoginDialog.this.g.equals(CBConstant.DEFAULT_VALUE) && inflate.findViewById(R.id.password).getVisibility() == 0) {
                    inflate.findViewById(R.id.password).setVisibility(8);
                }
                return false;
            }
        });
        this.d.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (!SdkHelper.checkNetwork(PayULoginDialog.this.getActivity())) {
                    Toast.makeText(PayULoginDialog.this.getActivity(), R.string.disconnected_from_internet, 0).show();
                    return;
                }
                PayULoginDialog payULoginDialog = PayULoginDialog.this;
                payULoginDialog.a(payULoginDialog.o, PayULoginDialog.this.c.getText().toString());
            }
        });
        this.b.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                PayULoginDialog payULoginDialog = PayULoginDialog.this;
                payULoginDialog.a((View) payULoginDialog.e);
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                PayULoginDialog.this.c.setOnFocusChangeListener(new onFocusListener());
            }
        });
        this.c.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                PayULoginDialog payULoginDialog = PayULoginDialog.this;
                payULoginDialog.a((View) payULoginDialog.e);
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() <= 0) {
                    PayULoginDialog.this.d.setEnabled(false);
                    PayULoginDialog.this.d.getBackground().setAlpha(150);
                } else if (SdkHelper.isValidateUsername(PayULoginDialog.this.o)) {
                    PayULoginDialog.this.d.setEnabled(true);
                    PayULoginDialog.this.d.getBackground().setAlpha(255);
                }
            }
        });
        this.c.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((keyEvent == null || keyEvent.getKeyCode() != 66) && i != 6) {
                    return false;
                }
                if (!SdkHelper.checkNetwork(PayULoginDialog.this.getActivity())) {
                    Toast.makeText(PayULoginDialog.this.getActivity(), R.string.disconnected_from_internet, 0).show();
                    return false;
                }
                PayULoginDialog payULoginDialog = PayULoginDialog.this;
                payULoginDialog.a(payULoginDialog.o, PayULoginDialog.this.c.getText().toString());
                return true;
            }
        });
        this.l = (TextView) inflate.findViewById(R.id.text_view_resend_otp);
        this.l.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SdkHelper.isValidateUsername(PayULoginDialog.this.o)) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                    hashMap.put(AnalyticsConstant.ID_VALUE, PayULoginDialog.this.o);
                    LogAnalytics.logEvent(PayULoginDialog.this.getContext(), AnalyticsConstant.LOGIN_OTP_RESENT, hashMap, AnalyticsConstant.CLEVERTAP);
                    if (PayULoginDialog.this.a()) {
                        PayULoginDialog.this.c();
                        PayUmoneySDK instance = PayUmoneySDK.getInstance();
                        PayULoginDialog payULoginDialog = PayULoginDialog.this;
                        instance.requestOTPForLogin(payULoginDialog, payULoginDialog.o, "otp_request_api_tag");
                    }
                    PayULoginDialog.this.b();
                    PayULoginDialog payULoginDialog2 = PayULoginDialog.this;
                    payULoginDialog2.a((View) payULoginDialog2.e);
                } else if (PayULoginDialog.this.o == null || PayULoginDialog.this.o.length() < 1) {
                    PayULoginDialog.this.b.setError("Please fill the details");
                } else if (!SdkHelper.isValidNumber(PayULoginDialog.this.o)) {
                    PayULoginDialog.this.b.setError("Invalid Phone number");
                } else if (!SdkHelper.isValidEmail(PayULoginDialog.this.o)) {
                    PayULoginDialog.this.b.setError("Invalid Email");
                }
            }
        });
        b();
        return inflate;
    }

    public void onStart() {
        super.onStart();
    }

    public void onDestroy() {
        if (!(this.q == null || getActivity() == null)) {
            getActivity().unregisterReceiver(this.q);
            this.q = null;
        }
        super.onDestroy();
    }

    public void onStop() {
        super.onStop();
        BroadcastReceiver broadcastReceiver = this.h;
        if (broadcastReceiver != null) {
            this.f.unregisterReceiver(broadcastReceiver);
        }
    }

    /* access modifiers changed from: private */
    public boolean a() {
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_SMS") == 0) {
            return true;
        }
        if (SdkHelper.isPhoneNumber(this.o)) {
            requestPermissions(new String[]{"android.permission.READ_SMS"}, 123);
        } else {
            PayUmoneySDK.getInstance().requestOTPForLogin(this, this.o, "otp_request_api_tag");
            this.c.setOnFocusChangeListener(null);
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2) {
        a(this.d, false, R.string.logging_in, 150);
        if (this.g.equals("guestLogin")) {
            SdkSession.getInstance(this.f.getApplicationContext()).setLoginMode(this.g);
            SdkSession.getInstance(this.f.getApplicationContext()).setGuestEmail(str);
            Intent intent = new Intent();
            intent.putExtra(PayUmoneyConstants.AMOUNT, this.f.getIntent().getStringExtra(PayUmoneyConstants.AMOUNT));
            intent.putExtra("merchantId", this.f.getIntent().getStringExtra("merchantId"));
            intent.putExtra(PayUmoneyConstants.PARAMS, this.f.getIntent().getSerializableExtra(PayUmoneyConstants.PARAMS));
            this.f.setResult(-1, intent);
            this.f.finish();
            return;
        }
        SdkSession.getInstance(this.f.getApplicationContext()).create(str, this.c.getText().toString(), this.i, this.k, PayUMoneyFragment.LOGIN_DIALOG_TAG);
    }

    public void onOTPRequestSend(String response, String tag) {
        if (SdkHelper.isPhoneNumber(this.o)) {
            b(this.e, "OTP sent to your mobile number");
        } else {
            try {
                JSONObject jSONObject = new JSONObject(response);
                if (jSONObject.has("result")) {
                    if (jSONObject.getJSONObject("result").has("phone") && jSONObject.getJSONObject("result").getString("phone") != null) {
                        if (!jSONObject.getJSONObject("result").getString("phone").equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                            TextView textView = this.e;
                            StringBuilder sb = new StringBuilder();
                            sb.append("OTP sent to your mobile number ");
                            sb.append(jSONObject.getJSONObject("result").getString("phone"));
                            b(textView, sb.toString());
                        }
                    }
                    b(this.e, "OTP sent to your mobile number");
                }
            } catch (Exception e2) {
            }
        }
        initiateDelayResendOTP();
    }

    public void onError(String response, String tag) {
        a((View) this.e, "OTP can't be send");
    }

    public void missingParam(String description, String tag) {
    }

    public void onFailureResponse(ErrorResponse response, String tag) {
        updateUiOnError(response.getMessage());
    }

    public void setLoginDialogListener(PayULoginDialogListener dialogDismissListener) {
        this.k = dialogDismissListener;
    }

    public void updateUiOnError(String message) {
        a(this.d, false, R.string.login, 150);
        a((View) this.e, message);
    }

    public void setLoginListener(OnUserLoginListener onUserLoginListener) {
        this.i = onUserLoginListener;
    }

    private void a(View view, boolean z, int i2, int i3) {
        view.setEnabled(z);
        view.getBackground().setAlpha(i3);
        if (view instanceof Button) {
            ((Button) view).setText(i2);
        }
    }

    private void a(View view, String str) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setText(str);
            textView.setTextColor(SupportMenu.CATEGORY_MASK);
        }
        view.setVisibility(0);
    }

    private void b(View view, String str) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setText(str);
            textView.setTextColor(getActivity().getResources().getColor(R.color.primary_green));
        }
        view.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void a(View view) {
        if (view instanceof TextView) {
            ((TextView) view).setText("");
        }
        view.setVisibility(4);
    }

    public void onDetach() {
        super.onDetach();
        this.i.onDismissLoginDialog();
    }

    /* access modifiers changed from: private */
    public void b() {
        this.l.setEnabled(false);
        this.l.setClickable(false);
        this.l.setTextColor(-7829368);
    }

    public void initiateDelayResendOTP() {
        Handler handler = this.m;
        if (handler != null) {
            Runnable runnable = this.n;
            if (runnable != null) {
                handler.removeCallbacks(runnable);
            }
        }
        this.m = new Handler();
        this.n = new Runnable() {
            public void run() {
                if (PayULoginDialog.this.getActivity() != null && !PayULoginDialog.this.getActivity().isFinishing()) {
                    PayULoginDialog.this.l.setEnabled(true);
                    PayULoginDialog.this.l.setClickable(true);
                    PayULoginDialog.this.l.setTextColor(PayULoginDialog.this.getActivity().getResources().getColor(R.color.primary_green));
                }
            }
        };
        this.m.postDelayed(this.n, 20000);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                PayUmoneySDK.getInstance().requestOTPForLogin(this, this.o, "otp_request_api_tag");
                this.c.setOnFocusChangeListener(null);
            } else {
                c();
                PayUmoneySDK.getInstance().requestOTPForLogin(this, this.o, "otp_request_api_tag");
                this.c.setOnFocusChangeListener(null);
            }
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        if (this.q == null) {
            this.q = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    String str;
                    try {
                        Bundle extras = intent.getExtras();
                        if (PayULoginDialog.this.getActivity() != null) {
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
                                if (!(str2.isEmpty() || PayULoginDialog.this.c == null || PayULoginDialog.this.c.getText() == null || PayULoginDialog.this.c.getText().toString() == null || !PayULoginDialog.this.c.getText().toString().isEmpty())) {
                                    PayULoginDialog.this.c.setText(str2);
                                    PayULoginDialog.this.c.setSelection(PayULoginDialog.this.c.getText().length());
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!PayULoginDialog.this.getActivity().isFinishing() && PayULoginDialog.this.q != null) {
                        PayULoginDialog.this.getActivity().unregisterReceiver(PayULoginDialog.this.q);
                        PayULoginDialog.this.q = null;
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.setPriority(9999999);
            intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
            getActivity().registerReceiver(this.q, intentFilter);
        }
    }
}
