package com.payu.magicretry.WaitingDots;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.text.style.ReplacementSpan;

public class JumpingSpan extends ReplacementSpan {
    private float translationX = 0.0f;
    private float translationY = 0.0f;

    public int getSize(Paint paint, CharSequence text, int start, int end, FontMetricsInt fontMetricsInt) {
        return (int) paint.measureText(text, start, end);
    }

    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        canvas.drawText(text, start, end, x + this.translationX, ((float) y) + this.translationY, paint);
    }

    public void setTranslationX(float translationX2) {
        this.translationX = translationX2;
    }

    public void setTranslationY(float translationY2) {
        this.translationY = translationY2;
    }
}
