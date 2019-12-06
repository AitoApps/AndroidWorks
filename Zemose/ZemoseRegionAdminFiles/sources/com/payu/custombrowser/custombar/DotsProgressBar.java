package com.payu.custombrowser.custombar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import com.payu.custombrowser.R;

public class DotsProgressBar extends View {
    private final Paint a = new Paint(1);
    private final Paint b = new Paint(1);
    /* access modifiers changed from: private */
    public final Handler c = new Handler();
    private final int d = 10;
    private float e;
    private float f;
    /* access modifiers changed from: private */
    public int g = 0;
    private int h;
    private int i;
    /* access modifiers changed from: private */
    public int j = 5;
    /* access modifiers changed from: private */
    public boolean k;
    /* access modifiers changed from: private */
    public int l = 1;
    /* access modifiers changed from: private */
    public Runnable m;

    public DotsProgressBar(Context context) {
        super(context);
        a(context);
    }

    public DotsProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    public DotsProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        a(context);
    }

    private void a(Context context) {
        this.e = context.getResources().getDimension(R.dimen.cb_circle_indicator_radius);
        this.f = context.getResources().getDimension(R.dimen.cb_circle_indicator_outer_radius);
        this.a.setStyle(Style.FILL);
        this.a.setColor(context.getResources().getColor(R.color.cb_payu_blue));
        this.b.setStyle(Style.FILL);
        this.b.setColor(855638016);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.m = new Runnable() {
            public void run() {
                DotsProgressBar dotsProgressBar = DotsProgressBar.this;
                dotsProgressBar.g = dotsProgressBar.g + DotsProgressBar.this.l;
                if (DotsProgressBar.this.g < 0) {
                    DotsProgressBar.this.g = 1;
                    DotsProgressBar.this.l = 1;
                } else if (DotsProgressBar.this.g > DotsProgressBar.this.j - 1) {
                    DotsProgressBar.this.g = 0;
                    DotsProgressBar.this.l = 1;
                }
                if (!DotsProgressBar.this.k) {
                    DotsProgressBar.this.invalidate();
                    DotsProgressBar.this.c.postDelayed(DotsProgressBar.this.m, 400);
                }
            }
        };
        a();
    }

    public void setDotsCount(int count) {
        this.j = count;
    }

    public void a() {
        this.g = -1;
        this.k = false;
        this.c.removeCallbacks(this.m);
        this.c.post(this.m);
    }

    public void b() {
        Handler handler = this.c;
        if (handler != null) {
            Runnable runnable = this.m;
            if (runnable != null) {
                this.k = true;
                handler.removeCallbacks(runnable);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float f2 = this.e;
        float f3 = 2.0f * f2;
        int i2 = this.j;
        this.h = (int) ((f3 * ((float) i2)) + ((float) (i2 * 10)) + 10.0f + (this.f - f2));
        this.i = (((int) f2) * 2) + getPaddingBottom() + getPaddingTop();
        setMeasuredDimension(this.h, this.i);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float f2 = (float) this.h;
        int i2 = this.j;
        float f3 = ((f2 - ((((float) i2) * this.e) * 2.0f)) - ((float) ((i2 - 1) * 10))) / 2.0f;
        float f4 = (float) (this.i / 2);
        for (int i3 = 0; i3 < this.j; i3++) {
            if (i3 == this.g) {
                canvas.drawCircle(f3, f4, this.f, this.a);
            } else {
                canvas.drawCircle(f3, f4, this.e, this.b);
            }
            f3 += (this.e * 2.0f) + 10.0f;
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Handler handler = this.c;
        if (handler != null) {
            Runnable runnable = this.m;
            if (runnable != null) {
                handler.removeCallbacks(runnable);
                this.m = null;
            }
        }
    }
}
