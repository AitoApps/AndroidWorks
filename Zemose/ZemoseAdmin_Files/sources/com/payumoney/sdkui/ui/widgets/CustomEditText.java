package com.payumoney.sdkui.ui.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.util.AttributeSet;

public class CustomEditText extends AppCompatEditText {
    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onSelectionChanged(int start, int end) {
        Editable text = getText();
        if (text == null || (start == text.length() && end == text.length())) {
            super.onSelectionChanged(start, end);
        } else {
            setSelection(text.length(), text.length());
        }
    }
}
