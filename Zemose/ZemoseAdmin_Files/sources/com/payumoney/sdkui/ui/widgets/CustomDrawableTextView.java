package com.payumoney.sdkui.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.ui.utils.Utils;

public class CustomDrawableTextView extends AppCompatTextView {
    public CustomDrawableTextView(Context context) {
        super(context);
    }

    public CustomDrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context, attrs);
    }

    public CustomDrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a(context, attrs);
    }

    private void a(Context context, AttributeSet attributeSet) {
        if (!isInEditMode()) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.customButton, 0, 0);
            try {
                boolean z = obtainStyledAttributes.getBoolean(R.styleable.customButton_borderOnly, false);
                int color = obtainStyledAttributes.getColor(R.styleable.customButton_bgColor, ContextCompat.getColor(context, R.color.payumoney_black));
                if (VERSION.SDK_INT >= 16) {
                    setBackground(Utils.getCustomDrawable(context, color, z, 2, 5, true));
                } else {
                    setBackgroundDrawable(Utils.getCustomDrawable(context, color, z, 2, 5, true));
                }
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
    }
}
