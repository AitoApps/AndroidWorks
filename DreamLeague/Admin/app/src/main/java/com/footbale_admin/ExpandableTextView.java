package com.footbale_admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

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
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExtendTextView);
        trimLength = typedArray.getInt(0, 200);
        typedArray.recycle();
        setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                trim = !trim;
                setText();
                requestFocusFromTouch();
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
        if (originalText == null || originalText.length() <= trimLength) {
            return originalText;
        }
        return new SpannableStringBuilder(originalText, 0, trimLength + 1).append(ELLIPSIS);
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
