package com.payumoney.sdkui.ui.widgets;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.payumoney.sdkui.ui.utils.PPLogger;
import java.lang.reflect.Field;

public class CustomTextInputLayout extends TextInputLayout {
    private static final String a = CustomTextInputLayout.class.getSimpleName();
    private static final Field b;

    static {
        Field field = null;
        try {
            field = TextInputLayout.class.getDeclaredField("mIndicatorArea");
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            PPLogger.getInstance().w(a, e);
        }
        b = field;
    }

    public CustomTextInputLayout(Context context) {
        super(context);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setErrorEnabled(boolean enabled) {
        super.setErrorEnabled(enabled);
        if (!enabled) {
            a();
        }
    }

    public void setCounterEnabled(boolean enabled) {
        super.setCounterEnabled(enabled);
        if (!enabled) {
            a();
        }
    }

    private void a() {
        Field field = b;
        if (field != null) {
            try {
                Object obj = field.get(this);
                if (obj instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) obj;
                    if (viewGroup.getChildCount() <= 1) {
                        viewGroup.setVisibility(8);
                    }
                }
            } catch (IllegalAccessException e) {
                PPLogger.getInstance().w(a, e);
            }
        }
    }
}
