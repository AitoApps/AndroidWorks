package com.payumoney.sdkui.ui.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

public class RoundRectTextInputLayout extends TextInputLayout {
    private Drawable a;
    private Drawable b;

    public RoundRectTextInputLayout(Context context) {
        super(context);
    }

    public RoundRectTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundRectTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        a();
    }

    public void setError(@Nullable CharSequence error) {
        super.setError(error);
        a();
    }

    public void setDefaultDrawable(Drawable defaultDrawable) {
        this.a = defaultDrawable;
    }

    public void setErrorDrawable(Drawable errorDrawable) {
        this.b = errorDrawable;
    }

    private void a() {
        EditText editText = getEditText();
        if (editText != null) {
            editText.setBackground(b() ? this.b : this.a);
            Drawable background = editText.getBackground();
            if (background != null) {
                background.clearColorFilter();
            }
        }
    }

    private boolean b() {
        if (!isErrorEnabled() || TextUtils.isEmpty(getError())) {
            return false;
        }
        return true;
    }
}
