package com.payumoney.sdkui.ui.widgets;

import android.content.Context;
import android.support.annotation.Keep;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

public class OtpEditText extends EditText {
    DeletePress a;

    @Keep
    public interface DeletePress {
        void onEmptyDeletePress(String str);

        void onNonEmptyDeletePress(String str);
    }

    private class OtpEditInputConnection extends InputConnectionWrapper {
        public OtpEditInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getAction() == 0 && event.getKeyCode() == 67 && OtpEditText.this.a != null) {
                if (TextUtils.isEmpty(OtpEditText.this.getText())) {
                    if (OtpEditText.this.a != null) {
                        OtpEditText.this.a.onEmptyDeletePress(OtpEditText.this.getTag().toString());
                        return false;
                    }
                } else if (TextUtils.isEmpty(getTextBeforeCursor(1, 1))) {
                    OtpEditText.this.a.onNonEmptyDeletePress(OtpEditText.this.getTag().toString());
                    return false;
                }
            }
            return super.sendKeyEvent(event);
        }

        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            boolean z = true;
            if (beforeLength != 1 || afterLength != 0) {
                return super.deleteSurroundingText(beforeLength, afterLength);
            }
            if (!sendKeyEvent(new KeyEvent(0, 67)) || !sendKeyEvent(new KeyEvent(1, 67))) {
                z = false;
            }
            return z;
        }
    }

    public OtpEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public OtpEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OtpEditText(Context context) {
        super(context);
    }

    public void setDeletePressListener(DeletePress deletePress) {
        this.a = deletePress;
    }

    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new OtpEditInputConnection(super.onCreateInputConnection(outAttrs), true);
    }
}
