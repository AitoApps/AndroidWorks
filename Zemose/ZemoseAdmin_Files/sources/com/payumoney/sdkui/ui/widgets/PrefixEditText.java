package com.payumoney.sdkui.ui.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextPaint;
import android.util.AttributeSet;
import com.payumoney.sdkui.R;

public class PrefixEditText extends AppCompatEditText {
    /* access modifiers changed from: private */
    public ColorStateList a;

    private class TextDrawable extends Drawable {
        private String b = "";

        public TextDrawable(String text) {
            this.b = text;
            setBounds(0, 0, ((int) PrefixEditText.this.getPaint().measureText(this.b)) + 2, (int) PrefixEditText.this.getTextSize());
        }

        public void draw(Canvas canvas) {
            TextPaint paint = PrefixEditText.this.getPaint();
            paint.setColor(PrefixEditText.this.a.getColorForState(PrefixEditText.this.getDrawableState(), 0));
            canvas.drawText(this.b, 0.0f, (float) (canvas.getClipBounds().top + PrefixEditText.this.getLineBounds(0, null)), paint);
        }

        public void setAlpha(int alpha) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }

        public int getOpacity() {
            return 0;
        }
    }

    public PrefixEditText(Context context) {
        super(context, null);
    }

    public PrefixEditText(Context context, AttributeSet attrs) {
        super(context, attrs, 16842862);
        a(context, attrs);
    }

    public PrefixEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        a(context, attrs);
        this.a = getTextColors();
    }

    private void a(Context context, AttributeSet attributeSet) {
        if (!isInEditMode()) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.prefixEditText, 0, 0);
            try {
                String string = obtainStyledAttributes.getString(R.styleable.prefixEditText_prefix_string);
                int color = obtainStyledAttributes.getColor(R.styleable.prefixEditText_prefix_color, ContextCompat.getColor(context, R.color.payumoney_black));
                setPrefix(string);
                setPrefixTextColor(color);
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
    }

    public void setPrefix(String prefix) {
        setCompoundDrawables(new TextDrawable(prefix), null, null, null);
    }

    public void setPrefixTextColor(int color) {
        this.a = ColorStateList.valueOf(color);
    }

    public void setPrefixTextColor(ColorStateList color) {
        this.a = color;
    }
}
