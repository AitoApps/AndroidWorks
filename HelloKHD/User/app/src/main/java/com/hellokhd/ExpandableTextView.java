package com.hellokhd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView.BufferType;

import androidx.appcompat.widget.AppCompatTextView;

public class ExpandableTextView extends AppCompatTextView {
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
    public ExpandableTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.trim = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ExpandableTextView);
        this.trimLength = obtainStyledAttributes.getInt(0, 200);
        obtainStyledAttributes.recycle();
        setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                trim = !trim;
                setText();
                requestFocusFromTouch();
            }
        });
    }

    /* access modifiers changed from: private */
    public void setText() {
        super.setText(getDisplayableText(), this.bufferType);
    }

    private CharSequence getDisplayableText() {
        return this.trim ? this.trimmedText : this.originalText;
    }

    public void setText(CharSequence charSequence, BufferType bufferType2) {
        this.originalText = charSequence;
        this.trimmedText = getTrimmedText(charSequence);
        this.bufferType = bufferType2;
        setText();
    }

    private CharSequence getTrimmedText(CharSequence charSequence) {
        if (this.originalText == null || this.originalText.length() <= this.trimLength) {
            return this.originalText;
        }
        return new SpannableStringBuilder(this.originalText, 0, this.trimLength + 1).append(ELLIPSIS);
    }

    public CharSequence getOriginalText() {
        return this.originalText;
    }

    public void setTrimLength(int i) {
        this.trimLength = i;
        this.trimmedText = getTrimmedText(this.originalText);
        setText();
    }

    public int getTrimLength() {
        return this.trimLength;
    }
}
