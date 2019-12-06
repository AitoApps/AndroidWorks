package com.payumoney.sdkui.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.annotation.Keep;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import com.payumoney.sdkui.R;

public class FlipImageView extends ImageView implements OnClickListener, AnimationListener {
    private static final Interpolator a = new DecelerateInterpolator();
    private OnFlipListener b;
    private boolean c;
    private boolean d;
    private Drawable e;
    private Drawable f;
    private FlipAnimator g;
    /* access modifiers changed from: private */
    public boolean h;
    /* access modifiers changed from: private */
    public boolean i;
    /* access modifiers changed from: private */
    public boolean j;
    private boolean k;
    /* access modifiers changed from: private */
    public boolean l;

    public class FlipAnimator extends Animation {
        private Camera b;
        private Drawable c;
        private float d;
        private float e;
        private boolean f;

        public FlipAnimator() {
            setFillAfter(true);
        }

        public void setToDrawable(Drawable to) {
            this.c = to;
            this.f = false;
        }

        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            this.b = new Camera();
            this.d = (float) (width / 2);
            this.e = (float) (height / 2);
        }

        /* access modifiers changed from: protected */
        public void applyTransformation(float interpolatedTime, Transformation t) {
            float f2;
            double d2 = ((double) interpolatedTime) * 3.141592653589793d;
            float f3 = (float) ((180.0d * d2) / 3.141592653589793d);
            if (FlipImageView.this.l) {
                f3 = -f3;
            }
            if (interpolatedTime >= 0.5f) {
                if (FlipImageView.this.l) {
                    f2 += 180.0f;
                } else {
                    f2 -= 180.0f;
                }
                if (!this.f) {
                    FlipImageView.this.setImageDrawable(this.c);
                    this.f = true;
                }
            }
            Matrix matrix = t.getMatrix();
            this.b.save();
            float f4 = 0.0f;
            this.b.translate(0.0f, 0.0f, (float) (Math.sin(d2) * 150.0d));
            this.b.rotateX(FlipImageView.this.h ? f2 : 0.0f);
            this.b.rotateY(FlipImageView.this.i ? f2 : 0.0f);
            Camera camera = this.b;
            if (FlipImageView.this.j) {
                f4 = f2;
            }
            camera.rotateZ(f4);
            this.b.getMatrix(matrix);
            this.b.restore();
            matrix.preTranslate(-this.d, -this.e);
            matrix.postTranslate(this.d, this.e);
        }
    }

    @Keep
    public interface OnFlipListener {
        void onClick(FlipImageView flipImageView);

        void onFlipEnd(FlipImageView flipImageView);

        void onFlipStart(FlipImageView flipImageView);
    }

    public FlipImageView(Context context) {
        super(context);
        a(context, null, 0);
    }

    public FlipImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context, attrs, 0);
    }

    public FlipImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        a(context, attrs, defStyle);
    }

    private void a(Context context, AttributeSet attributeSet, int i2) {
        int integer = context.getResources().getInteger(R.integer.default_fiv_duration);
        int integer2 = context.getResources().getInteger(R.integer.default_fiv_rotations);
        boolean z = context.getResources().getBoolean(R.bool.default_fiv_isAnimated);
        boolean z2 = context.getResources().getBoolean(R.bool.default_fiv_isFlipped);
        boolean z3 = context.getResources().getBoolean(R.bool.default_fiv_isRotationReversed);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FlipImageView, i2, 0);
        this.d = obtainStyledAttributes.getBoolean(R.styleable.FlipImageView_isAnimated, z);
        this.c = obtainStyledAttributes.getBoolean(R.styleable.FlipImageView_isFlipped, z2);
        this.f = obtainStyledAttributes.getDrawable(R.styleable.FlipImageView_flipDrawable);
        int i3 = obtainStyledAttributes.getInt(R.styleable.FlipImageView_flipDuration, integer);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.FlipImageView_flipInterpolator, 0);
        Interpolator loadInterpolator = resourceId > 0 ? AnimationUtils.loadInterpolator(context, resourceId) : a;
        int integer3 = obtainStyledAttributes.getInteger(R.styleable.FlipImageView_flipRotations, integer2);
        boolean z4 = true;
        this.h = (integer3 & 1) != 0;
        this.i = (integer3 & 2) != 0;
        if ((integer3 & 4) == 0) {
            z4 = false;
        }
        this.j = z4;
        this.e = getDrawable();
        this.l = obtainStyledAttributes.getBoolean(R.styleable.FlipImageView_reverseRotation, z3);
        this.g = new FlipAnimator();
        this.g.setAnimationListener(this);
        this.g.setInterpolator(loadInterpolator);
        this.g.setDuration((long) i3);
        setOnClickListener(this);
        setImageDrawable(this.c ? this.f : this.e);
        this.k = false;
        obtainStyledAttributes.recycle();
    }

    public void setFlippedDrawable(Drawable flippedDrawable) {
        this.f = flippedDrawable;
        if (this.c) {
            setImageDrawable(this.f);
        }
    }

    public void setDrawable(Drawable drawable) {
        this.e = drawable;
        if (!this.c) {
            setImageDrawable(this.e);
        }
    }

    public boolean isRotationXEnabled() {
        return this.h;
    }

    public void setRotationXEnabled(boolean enabled) {
        this.h = enabled;
    }

    public boolean isRotationYEnabled() {
        return this.i;
    }

    public void setRotationYEnabled(boolean enabled) {
        this.i = enabled;
    }

    public boolean isRotationZEnabled() {
        return this.j;
    }

    public void setRotationZEnabled(boolean enabled) {
        this.j = enabled;
    }

    public FlipAnimator getFlipAnimation() {
        return this.g;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.g.setInterpolator(interpolator);
    }

    public void setDuration(int duration) {
        this.g.setDuration((long) duration);
    }

    public boolean isFlipped() {
        return this.c;
    }

    public void setFlipped(boolean flipped) {
        setFlipped(flipped, this.d);
    }

    public boolean isFlipping() {
        return this.k;
    }

    public boolean isRotationReversed() {
        return this.l;
    }

    public void setRotationReversed(boolean rotationReversed) {
        this.l = rotationReversed;
    }

    public boolean isAnimated() {
        return this.d;
    }

    public void setAnimated(boolean animated) {
        this.d = animated;
    }

    public void setFlipped(boolean flipped, boolean animated) {
        if (flipped != this.c) {
            toggleFlip(animated);
        }
    }

    public void toggleFlip() {
        toggleFlip(this.d);
    }

    public void toggleFlip(boolean animated) {
        if (animated) {
            this.g.setToDrawable(this.c ? this.e : this.f);
            startAnimation(this.g);
        } else {
            setImageDrawable(this.c ? this.e : this.f);
        }
        this.c = !this.c;
    }

    public void setOnFlipListener(OnFlipListener listener) {
        this.b = listener;
    }

    public void onClick(View v) {
        toggleFlip();
        OnFlipListener onFlipListener = this.b;
        if (onFlipListener != null) {
            onFlipListener.onClick(this);
        }
    }

    public void onAnimationStart(Animation animation) {
        OnFlipListener onFlipListener = this.b;
        if (onFlipListener != null) {
            onFlipListener.onFlipStart(this);
        }
        this.k = true;
    }

    public void onAnimationEnd(Animation animation) {
        OnFlipListener onFlipListener = this.b;
        if (onFlipListener != null) {
            onFlipListener.onFlipEnd(this);
        }
        this.k = false;
    }

    public void onAnimationRepeat(Animation animation) {
    }
}
