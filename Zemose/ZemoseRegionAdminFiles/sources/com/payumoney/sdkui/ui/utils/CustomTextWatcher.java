package com.payumoney.sdkui.ui.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

public class CustomTextWatcher implements TextWatcher {
    private View a;
    private CustomTextWatcherListener b = null;

    public CustomTextWatcher(View view, CustomTextWatcherListener customTextWatcherListener) {
        this.a = view;
        this.b = customTextWatcherListener;
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void afterTextChanged(Editable editable) {
        this.b.afterTextChanged(this.a, editable.toString());
    }
}
