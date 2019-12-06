package com.payu.custombrowser.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.util.AttributeSet;
import android.view.View;
import com.payu.custombrowser.R;
import java.util.Timer;
import java.util.TimerTask;

public class SnoozeLoaderView extends View {
    Activity a;
    private Paint b;
    private Paint c;
    private Rect d;
    private Rect e;
    private Rect f;
    private Paint g;
    private Paint h;
    private Paint i;
    private int j = 40;
    private int k = 120;
    private int l = 70;
    private int m = (this.k / 3);
    /* access modifiers changed from: private */
    public boolean n = false;
    private int o = Color.parseColor("#00adf2");
    private int p = Color.parseColor("#b0eafc");
    private int q = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
    /* access modifiers changed from: private */
    public int r = 0;
    private Timer s;

    public SnoozeLoaderView(Context context) {
        super(context);
        this.a = (Activity) context;
        c();
    }

    public SnoozeLoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.a = (Activity) context;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SnoozeLoaderView, 0, 0);
        try {
            this.n = obtainStyledAttributes.getBoolean(R.styleable.SnoozeLoaderView_startAnimate, this.n);
            this.o = obtainStyledAttributes.getColor(R.styleable.SnoozeLoaderView_activeBarColor, this.o);
            this.p = obtainStyledAttributes.getColor(R.styleable.SnoozeLoaderView_inActiveBarColor, this.p);
            this.j = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SnoozeLoaderView_barWidth, this.j);
            this.k = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SnoozeLoaderView_barHeight, this.k);
            this.m = this.k / 3;
            this.l = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SnoozeLoaderView_barSpace, this.l);
            this.q = obtainStyledAttributes.getInt(R.styleable.SnoozeLoaderView_animationSpeed, this.q);
            c();
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public SnoozeLoaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.a = (Activity) context;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SnoozeLoaderView, 0, 0);
        try {
            this.n = obtainStyledAttributes.getBoolean(R.styleable.SnoozeLoaderView_startAnimate, this.n);
            this.o = obtainStyledAttributes.getColor(R.styleable.SnoozeLoaderView_activeBarColor, this.o);
            this.p = obtainStyledAttributes.getColor(R.styleable.SnoozeLoaderView_inActiveBarColor, this.p);
            this.j = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SnoozeLoaderView_barWidth, this.j);
            this.k = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SnoozeLoaderView_barHeight, this.k);
            this.m = this.k / 3;
            this.l = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SnoozeLoaderView_barSpace, this.l);
            c();
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(this.d, this.g);
        canvas.drawRect(this.e, this.h);
        canvas.drawRect(this.f, this.i);
    }

    public void a(int i2) {
        switch (i2) {
            case 0:
                Paint paint = this.c;
                this.g = paint;
                this.h = paint;
                this.i = paint;
                break;
            case 1:
                this.g = this.b;
                Paint paint2 = this.c;
                this.h = paint2;
                this.i = paint2;
                break;
            case 2:
                Paint paint3 = this.b;
                this.g = paint3;
                this.h = paint3;
                this.i = this.c;
                break;
            case 3:
                Paint paint4 = this.b;
                this.g = paint4;
                this.h = paint4;
                this.i = paint4;
                break;
            default:
                Paint paint5 = this.c;
                this.g = paint5;
                this.h = paint5;
                this.i = paint5;
                break;
        }
        Activity activity = this.a;
        if (activity != null && !activity.isFinishing()) {
            this.a.runOnUiThread(new Runnable() {
                public void run() {
                    SnoozeLoaderView.this.invalidate();
                }
            });
        }
    }

    private void c() {
        this.b = new Paint();
        this.b.setColor(this.o);
        this.b.setStyle(Style.FILL);
        this.c = new Paint();
        this.c.setColor(this.p);
        this.c.setStyle(Style.FILL);
        Paint paint = this.c;
        this.g = paint;
        this.h = paint;
        this.i = paint;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h2, int oldw, int oldh) {
        super.onSizeChanged(w, h2, oldw, oldh);
        int i2 = w / 2;
        int i3 = h2 / 2;
        int i4 = this.j;
        int i5 = i2 - (i4 / 2);
        int i6 = this.k;
        int i7 = i3 - (i6 / 2);
        int i8 = i5 + i4;
        int i9 = i7 + i6;
        int i10 = i2 - i4;
        int i11 = this.l;
        int i12 = (i10 - i11) - (i4 / 2);
        int i13 = i3 - (i6 / 2);
        int i14 = this.m;
        int i15 = i13 - i14;
        int i16 = i12 + i4;
        int i17 = i15 + i6 + i14 + i14;
        int i18 = i2 + (i4 / 2) + i11;
        int i19 = (i3 - (i6 / 2)) + i14;
        int i20 = i4 + i18;
        int i21 = ((i6 + i19) - i14) - i14;
        this.e = new Rect(i5, i7, i8, i9);
        this.d = new Rect(i12, i15, i16, i17);
        this.f = new Rect(i18, i19, i20, i21);
        if (this.n) {
            a();
        }
    }

    public void a() {
        this.n = true;
        this.s = new Timer();
        this.s.schedule(new TimerTask() {
            public void run() {
                if (SnoozeLoaderView.this.r == 4) {
                    SnoozeLoaderView.this.r = 0;
                } else {
                    SnoozeLoaderView snoozeLoaderView = SnoozeLoaderView.this;
                    snoozeLoaderView.r = snoozeLoaderView.r + 1;
                }
                if (SnoozeLoaderView.this.n) {
                    SnoozeLoaderView snoozeLoaderView2 = SnoozeLoaderView.this;
                    snoozeLoaderView2.a(snoozeLoaderView2.r);
                    return;
                }
                cancel();
            }
        }, 0, (long) this.q);
    }

    public void b() {
        this.n = false;
        Timer timer = this.s;
        if (timer != null) {
            timer.cancel();
            this.s.purge();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((this.j * 3) + (this.l * 2) + getPaddingLeft() + getPaddingRight(), this.k + (this.m * 2) + getPaddingTop() + getPaddingBottom());
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.n = false;
        b();
    }
}
