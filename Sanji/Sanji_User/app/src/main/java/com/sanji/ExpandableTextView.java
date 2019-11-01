package com.sanji;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class ExpandableTextView extends TextView {
    private static final int DEFAULT_TRIM_LENGTH = 200;
    private static final String ELLIPSIS = ".....";
    private BufferType bufferType;
    private CharSequence originalText;

    public boolean trim;
    private int trimLength;
    private CharSequence trimmedText;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    @SuppressLint({"Instantiatable"})
    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        trim = true;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        trimLength = typedArray.getInt(0, 200);
        typedArray.recycle();
        setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ExpandableTextView expandableTextView = ExpandableTextView.this;
                expandableTextView.trim = !expandableTextView.trim;
                ExpandableTextView.setText();
                ExpandableTextView.requestFocusFromTouch();
            }
        });
    }


    public void setText() {
        super.setText(getDisplayableText(), bufferType);
    }

    private CharSequence getDisplayableText() {
        return trim ? trimmedText : originalText;
    }

    public void setText(CharSequence text, BufferType type) {
        originalText = text;
        trimmedText = getTrimmedText(text);
        bufferType = type;
        setText();
    }

    private CharSequence getTrimmedText(CharSequence text) {
        CharSequence charSequence = originalText;
        if (charSequence != null) {
            int length = charSequence.length();
            int i = trimLength;
            if (length > i) {
                return new SpannableStringBuilder(originalText, 0, i + 1).append(ELLIPSIS);
            }
        }
        return originalText;
    }

    public CharSequence getOriginalText() {
        return originalText;
    }

    public void setTrimLength(int trimLength2) {
        trimLength = trimLength2;
        trimmedText = getTrimmedText(originalText);
        setText();
    }

    public int getTrimLength() {
        return trimLength;
    }
}
