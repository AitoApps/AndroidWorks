package com.payumoney.sdkui.ui.utils;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import com.payumoney.sdkui.ui.activities.PayUmoneyActivity;
import com.payumoney.sdkui.ui.widgets.CustomDrawableTextView;
import com.payumoney.sdkui.ui.widgets.OtpEditText;

public class OtpEditTextWatcher implements TextWatcher {
    OnTextInputFound a;
    private final OtpEditText b;
    private final Activity c;
    private final int d;
    private CustomDrawableTextView e;
    private boolean f = false;

    public interface OnTextInputFound {
        void hideLabelOtpError();
    }

    public OtpEditTextWatcher(OtpEditText appCompatEditText, CustomDrawableTextView payNowButton, Activity activity, int cvvLength, OnTextInputFound onTextInputFound) {
        this.b = appCompatEditText;
        this.e = payNowButton;
        this.c = activity;
        this.d = cvvLength;
        this.a = onTextInputFound;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        boolean z = true;
        if (!(start == 1 && after == 1)) {
            z = false;
        }
        this.f = z;
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s)) {
            this.a.hideLabelOtpError();
            if (this.d == 3 && this.b.getTag().toString().equals("3")) {
                a(this.b);
                Utils.hideKeyBoard(this.c, this.b.getWindowToken());
            } else if (this.d == 4 && this.b.getTag().toString().equals("4")) {
                a(this.b);
                Utils.hideKeyBoard(this.c, this.b.getWindowToken());
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0063, code lost:
        if (r0.equals("1") != false) goto L_0x0067;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009f, code lost:
        if (r0.equals("4") != false) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0188, code lost:
        if (r0.equals("1") != false) goto L_0x018c;
     */
    public void afterTextChanged(Editable s) {
        char c2 = 0;
        if (!((PayUmoneyActivity) this.c).isStopEditing()) {
            char c3 = 3;
            if (!TextUtils.isEmpty(s) && s.length() == 1) {
                this.e.setEnabled(true);
                this.e.getBackground().setAlpha(255);
                String obj = this.b.getTag().toString();
                switch (obj.hashCode()) {
                    case 49:
                        break;
                    case 50:
                        if (obj.equals("2")) {
                            c2 = 1;
                            break;
                        }
                    case 51:
                        if (obj.equals("3")) {
                            c2 = 2;
                            break;
                        }
                    case 52:
                        if (obj.equals("4")) {
                            c2 = 3;
                            break;
                        }
                    default:
                        c2 = 65535;
                        break;
                }
                switch (c2) {
                    case 0:
                    case 1:
                    case 2:
                        View focusSearch = this.b.focusSearch(66);
                        if (focusSearch != null) {
                            focusSearch.requestFocus();
                            return;
                        }
                        return;
                    default:
                        return;
                }
            } else if (!TextUtils.isEmpty(s) && s.length() == 2) {
                String obj2 = this.b.getTag().toString();
                switch (obj2.hashCode()) {
                    case 49:
                        if (obj2.equals("1")) {
                            c3 = 0;
                            break;
                        }
                    case 50:
                        if (obj2.equals("2")) {
                            c3 = 1;
                            break;
                        }
                    case 51:
                        if (obj2.equals("3")) {
                            c3 = 2;
                            break;
                        }
                    case 52:
                        break;
                    default:
                        c3 = 65535;
                        break;
                }
                switch (c3) {
                    case 0:
                    case 1:
                    case 2:
                        this.b.removeTextChangedListener(this);
                        if (this.f) {
                            this.b.setText(s.toString().substring(1));
                        } else {
                            this.b.setText(s.toString().substring(0, 1));
                        }
                        View focusSearch2 = this.b.focusSearch(66);
                        if (focusSearch2 != null && (focusSearch2 instanceof OtpEditText)) {
                            OtpEditText otpEditText = (OtpEditText) focusSearch2;
                            focusSearch2.setClickable(true);
                            focusSearch2.requestFocus();
                            otpEditText.setSelection(otpEditText.getText().length());
                        }
                        this.b.addTextChangedListener(this);
                        return;
                    case 3:
                        this.b.removeTextChangedListener(this);
                        if (!this.f) {
                            this.b.setText(s.toString().substring(0, 1));
                        } else {
                            this.b.setText(s.toString().substring(1));
                        }
                        OtpEditText otpEditText2 = this.b;
                        otpEditText2.setSelection(otpEditText2.getText().length());
                        this.b.addTextChangedListener(this);
                        return;
                    default:
                        return;
                }
            } else if (TextUtils.isEmpty(s)) {
                String obj3 = this.b.getTag().toString();
                switch (obj3.hashCode()) {
                    case 49:
                        break;
                    case 50:
                        if (obj3.equals("2")) {
                            c2 = 1;
                            break;
                        }
                    case 51:
                        if (obj3.equals("3")) {
                            c2 = 2;
                            break;
                        }
                    case 52:
                        if (obj3.equals("4")) {
                            c2 = 3;
                            break;
                        }
                    default:
                        c2 = 65535;
                        break;
                }
                switch (c2) {
                    case 1:
                    case 2:
                    case 3:
                        View focusSearch3 = this.b.focusSearch(17);
                        if (focusSearch3 != null && (focusSearch3 instanceof OtpEditText)) {
                            OtpEditText otpEditText3 = (OtpEditText) focusSearch3;
                            focusSearch3.requestFocus();
                            otpEditText3.setSelection(otpEditText3.getText().length());
                        }
                        this.b.setClickable(true);
                        return;
                    default:
                        return;
                }
            }
        } else {
            ((PayUmoneyActivity) this.c).setStopEditing(false);
        }
    }

    private void a(OtpEditText otpEditText) {
        otpEditText.setFocusableInTouchMode(false);
        otpEditText.setFocusable(false);
        otpEditText.setFocusableInTouchMode(true);
        otpEditText.setFocusable(true);
    }
}
