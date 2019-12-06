package com.payu.custombrowser.custombar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.AnimatorSet.Builder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import com.payu.custombrowser.R;

public class CircularProgressView extends View {
    private Paint a;
    private int b = 0;
    private RectF c;
    private boolean d;
    private boolean e;
    private float f;
    private float g;
    /* access modifiers changed from: private */
    public float h;
    /* access modifiers changed from: private */
    public float i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    /* access modifiers changed from: private */
    public float p;
    /* access modifiers changed from: private */
    public float q;
    private ValueAnimator r;
    private ValueAnimator s;
    private AnimatorSet t;
    private float u;

    public CircularProgressView(Context context) {
        super(context);
        a((AttributeSet) null, 0);
    }

    public CircularProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(attrs, 0);
    }

    public CircularProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        a(attrs, defStyle);
    }

    /* access modifiers changed from: protected */
    public void a(AttributeSet attributeSet, int i2) {
        b(attributeSet, i2);
        this.a = new Paint(1);
        e();
        this.c = new RectF();
    }

    private void b(AttributeSet attributeSet, int i2) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.CircularProgressView, i2, 0);
        Resources resources = getResources();
        this.f = obtainStyledAttributes.getFloat(R.styleable.CircularProgressView_cpv_progress, (float) resources.getInteger(R.integer.cpv_default_progress));
        this.g = obtainStyledAttributes.getFloat(R.styleable.CircularProgressView_cpv_maxProgress, (float) resources.getInteger(R.integer.cpv_default_max_progress));
        this.j = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CircularProgressView_cpv_thickness, resources.getDimensionPixelSize(R.dimen.cpv_default_thickness));
        this.d = obtainStyledAttributes.getBoolean(R.styleable.CircularProgressView_cpv_indeterminate, resources.getBoolean(R.bool.cpv_default_is_indeterminate));
        this.e = obtainStyledAttributes.getBoolean(R.styleable.CircularProgressView_cpv_animAutostart, resources.getBoolean(R.bool.cpv_default_anim_autostart));
        this.u = obtainStyledAttributes.getFloat(R.styleable.CircularProgressView_cpv_startAngle, (float) resources.getInteger(R.integer.cpv_default_start_angle));
        this.p = this.u;
        int identifier = getContext().getResources().getIdentifier("colorAccent", "attr", getContext().getPackageName());
        if (obtainStyledAttributes.hasValue(R.styleable.CircularProgressView_cpv_color)) {
            this.k = obtainStyledAttributes.getColor(R.styleable.CircularProgressView_cpv_color, resources.getColor(R.color.cpv_default_color));
        } else if (identifier != 0) {
            TypedValue typedValue = new TypedValue();
            getContext().getTheme().resolveAttribute(identifier, typedValue, true);
            this.k = typedValue.data;
        } else if (VERSION.SDK_INT >= 21) {
            this.k = getContext().obtainStyledAttributes(new int[]{16843829}).getColor(0, resources.getColor(R.color.cpv_default_color));
        } else {
            this.k = resources.getColor(R.color.cpv_default_color);
        }
        this.l = obtainStyledAttributes.getInteger(R.styleable.CircularProgressView_cpv_animDuration, resources.getInteger(R.integer.cpv_default_anim_duration));
        this.m = obtainStyledAttributes.getInteger(R.styleable.CircularProgressView_cpv_animSwoopDuration, resources.getInteger(R.integer.cpv_default_anim_swoop_duration));
        this.n = obtainStyledAttributes.getInteger(R.styleable.CircularProgressView_cpv_animSyncDuration, resources.getInteger(R.integer.cpv_default_anim_sync_duration));
        this.o = obtainStyledAttributes.getInteger(R.styleable.CircularProgressView_cpv_animSteps, resources.getInteger(R.integer.cpv_default_anim_steps));
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int measuredWidth = getMeasuredWidth() - paddingLeft;
        int measuredHeight = getMeasuredHeight() - paddingTop;
        if (measuredWidth >= measuredHeight) {
            measuredWidth = measuredHeight;
        }
        this.b = measuredWidth;
        int i2 = this.b;
        setMeasuredDimension(paddingLeft + i2, i2 + paddingTop);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h2, int oldw, int oldh) {
        super.onSizeChanged(w, h2, oldw, oldh);
        this.b = w < h2 ? w : h2;
        d();
    }

    private void d() {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        RectF rectF = this.c;
        int i2 = this.j;
        float f2 = (float) (paddingLeft + i2);
        float f3 = (float) (paddingTop + i2);
        int i3 = this.b;
        rectF.set(f2, f3, (float) ((i3 - paddingLeft) - i2), (float) ((i3 - paddingTop) - i2));
    }

    private void e() {
        this.a.setColor(this.k);
        this.a.setStyle(Style.STROKE);
        this.a.setStrokeWidth((float) this.j);
        this.a.setStrokeCap(Cap.BUTT);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float f2 = ((isInEditMode() ? this.f : this.q) / this.g) * 360.0f;
        if (!this.d) {
            canvas.drawArc(this.c, this.p, f2, false, this.a);
        } else {
            canvas.drawArc(this.c, this.p + this.i, this.h, false, this.a);
        }
    }

    public void setIndeterminate(boolean isIndeterminate) {
        boolean z = this.d != isIndeterminate;
        this.d = isIndeterminate;
        if (z) {
            b();
        }
    }

    public int getThickness() {
        return this.j;
    }

    public void setThickness(int thickness) {
        this.j = thickness;
        e();
        d();
        invalidate();
    }

    public int getColor() {
        return this.k;
    }

    public void setColor(int color) {
        this.k = color;
        e();
        invalidate();
    }

    public float getMaxProgress() {
        return this.g;
    }

    public void setMaxProgress(float maxProgress) {
        this.g = maxProgress;
        invalidate();
    }

    public float getProgress() {
        return this.f;
    }

    public void setProgress(float currentProgress) {
        this.f = currentProgress;
        if (!this.d) {
            ValueAnimator valueAnimator = this.s;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.s.cancel();
            }
            this.s = ValueAnimator.ofFloat(new float[]{this.q, currentProgress});
            this.s.setDuration((long) this.n);
            this.s.setInterpolator(new LinearInterpolator());
            this.s.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    CircularProgressView.this.q = ((Float) animation.getAnimatedValue()).floatValue();
                    CircularProgressView.this.invalidate();
                }
            });
            this.s.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                }
            });
            this.s.start();
        }
        invalidate();
    }

    public void a() {
        b();
    }

    public void b() {
        ValueAnimator valueAnimator = this.r;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.r.cancel();
        }
        ValueAnimator valueAnimator2 = this.s;
        if (valueAnimator2 != null && valueAnimator2.isRunning()) {
            this.s.cancel();
        }
        AnimatorSet animatorSet = this.t;
        if (animatorSet != null && animatorSet.isRunning()) {
            this.t.cancel();
        }
        int i2 = 0;
        if (!this.d) {
            this.p = this.u;
            float f2 = this.p;
            this.r = ValueAnimator.ofFloat(new float[]{f2, f2 + 360.0f});
            this.r.setDuration((long) this.m);
            this.r.setInterpolator(new DecelerateInterpolator(2.0f));
            this.r.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    CircularProgressView.this.p = ((Float) animation.getAnimatedValue()).floatValue();
                    CircularProgressView.this.invalidate();
                }
            });
            this.r.start();
            this.q = 0.0f;
            this.s = ValueAnimator.ofFloat(new float[]{this.q, this.f});
            this.s.setDuration((long) this.n);
            this.s.setInterpolator(new LinearInterpolator());
            this.s.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    CircularProgressView.this.q = ((Float) animation.getAnimatedValue()).floatValue();
                    CircularProgressView.this.invalidate();
                }
            });
            this.s.start();
            return;
        }
        this.h = 15.0f;
        this.t = new AnimatorSet();
        Animator animator = null;
        while (i2 < this.o) {
            AnimatorSet a2 = a((float) i2);
            Builder play = this.t.play(a2);
            if (animator != null) {
                play.after(animator);
            }
            i2++;
            animator = a2;
        }
        this.t.addListener(new AnimatorListenerAdapter() {
            boolean a = false;

            public void onAnimationCancel(Animator animation) {
                this.a = true;
            }

            public void onAnimationEnd(Animator animation) {
                if (!this.a) {
                    CircularProgressView.this.b();
                }
            }
        });
        this.t.start();
    }

    public void c() {
        ValueAnimator valueAnimator = this.r;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.r = null;
        }
        ValueAnimator valueAnimator2 = this.s;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
            this.s = null;
        }
        AnimatorSet animatorSet = this.t;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.t = null;
        }
    }

    private AnimatorSet a(float f2) {
        int i2 = this.o;
        final float f3 = ((((float) (i2 - 1)) * 360.0f) / ((float) i2)) + 15.0f;
        final float f4 = ((f3 - 15.0f) * f2) - 0.049804688f;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{15.0f, f3});
        ofFloat.setDuration((long) ((this.l / this.o) / 2));
        ofFloat.setInterpolator(new DecelerateInterpolator(1.0f));
        ofFloat.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                CircularProgressView.this.h = ((Float) animation.getAnimatedValue()).floatValue();
                CircularProgressView.this.invalidate();
            }
        });
        float f5 = f2 * 720.0f;
        int i3 = this.o;
        float f6 = (0.5f + f2) * 720.0f;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{f5 / ((float) i3), f6 / ((float) i3)});
        ofFloat2.setDuration((long) ((this.l / this.o) / 2));
        ofFloat2.setInterpolator(new LinearInterpolator());
        ofFloat2.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                CircularProgressView.this.i = ((Float) animation.getAnimatedValue()).floatValue();
            }
        });
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(new float[]{f4, (f4 + f3) - 15.0f});
        ofFloat3.setDuration((long) ((this.l / this.o) / 2));
        ofFloat3.setInterpolator(new DecelerateInterpolator(1.0f));
        ofFloat3.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                CircularProgressView.this.p = ((Float) animation.getAnimatedValue()).floatValue();
                CircularProgressView circularProgressView = CircularProgressView.this;
                circularProgressView.h = (f3 - circularProgressView.p) + f4;
                CircularProgressView.this.invalidate();
            }
        });
        int i4 = this.o;
        ValueAnimator ofFloat4 = ValueAnimator.ofFloat(new float[]{f6 / ((float) i4), ((f2 + 1.0f) * 720.0f) / ((float) i4)});
        ofFloat4.setDuration((long) ((this.l / this.o) / 2));
        ofFloat4.setInterpolator(new LinearInterpolator());
        ofFloat4.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                CircularProgressView.this.i = ((Float) animation.getAnimatedValue()).floatValue();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(ofFloat).with(ofFloat2);
        animatorSet.play(ofFloat3).with(ofFloat4).after(ofFloat2);
        return animatorSet;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.e) {
            a();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        c();
    }

    public void setVisibility(int visibility) {
        int visibility2 = getVisibility();
        super.setVisibility(visibility);
        if (visibility == visibility2) {
            return;
        }
        if (visibility == 0) {
            b();
        } else if (visibility == 8 || visibility == 4) {
            c();
        }
    }
}
