package com.payumoney.sdkui.ui.events;

import android.text.InputFilter;

public interface OnSchemeChangeListener {
    void callBinService();

    void onTextChanged(CharSequence charSequence, int i, int i2);

    void resetBankToDefault();

    void setInputFilterForEditText(InputFilter[] inputFilterArr);
}
