package com.github.aakira.expandablelayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class ExpandableWeightLayout extends RelativeLayout implements ExpandableLayout {
    private boolean defaultExpanded;
    private int duration;
    private TimeInterpolator interpolator;
    /* access modifiers changed from: private */
    public boolean isAnimating;
    private boolean isArranged;
    private boolean isCalculatedSize;
    /* access modifiers changed from: private */
    public boolean isExpanded;
    /* access modifiers changed from: private */
    public float layoutWeight;
    /* access modifiers changed from: private */
    public ExpandableLayoutListener listener;
    /* access modifiers changed from: private */
    public OnGlobalLayoutListener mGlobalLayoutListener;
    private ExpandableSavedState savedState;

    public ExpandableWeightLayout(Context context) {
        this(context, null);
    }

    public ExpandableWeightLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableWeightLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.interpolator = new LinearInterpolator();
        this.layoutWeight = 0.0f;
        this.isArranged = false;
        this.isCalculatedSize = false;
        this.isAnimating = false;
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public ExpandableWeightLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.interpolator = new LinearInterpolator();
        this.layoutWeight = 0.0f;
        this.isArranged = false;
        this.isCalculatedSize = false;
        this.isAnimating = false;
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.expandableLayout, defStyleAttr, 0);
        this.duration = a.getInteger(R.styleable.expandableLayout_ael_duration, ExpandableLayout.DEFAULT_DURATION);
        this.defaultExpanded = a.getBoolean(R.styleable.expandableLayout_ael_expanded, false);
        int interpolatorType = a.getInteger(R.styleable.expandableLayout_ael_interpolator, 8);
        a.recycle();
        this.interpolator = Utils.createInterpolator(interpolatorType);
        this.isExpanded = this.defaultExpanded;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!(getLayoutParams() instanceof LayoutParams)) {
            throw new AssertionError("You must arrange in LinearLayout.");
        } else if (0.0f >= getCurrentWeight()) {
            throw new AssertionError("You must set a weight than 0.");
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!this.isCalculatedSize) {
            this.layoutWeight = getCurrentWeight();
            this.isCalculatedSize = true;
        }
        if (!this.isArranged) {
            setWeight(this.defaultExpanded ? this.layoutWeight : 0.0f);
            this.isArranged = true;
            ExpandableSavedState expandableSavedState = this.savedState;
            if (expandableSavedState != null) {
                setWeight(expandableSavedState.getWeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        ExpandableSavedState ss = new ExpandableSavedState(super.onSaveInstanceState());
        ss.setWeight(getCurrentWeight());
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof ExpandableSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        ExpandableSavedState ss = (ExpandableSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.savedState = ss;
    }

    public void setListener(@NonNull ExpandableLayoutListener listener2) {
        this.listener = listener2;
    }

    public void toggle() {
        toggle((long) this.duration, this.interpolator);
    }

    public void toggle(long duration2, @Nullable TimeInterpolator interpolator2) {
        if (0.0f < getCurrentWeight()) {
            collapse(duration2, interpolator2);
        } else {
            expand(duration2, interpolator2);
        }
    }

    public void expand() {
        if (!this.isAnimating) {
            createExpandAnimator(0.0f, this.layoutWeight, (long) this.duration, this.interpolator).start();
        }
    }

    public void expand(long duration2, @Nullable TimeInterpolator interpolator2) {
        if (!this.isAnimating) {
            if (duration2 <= 0) {
                this.isExpanded = true;
                setWeight(this.layoutWeight);
                requestLayout();
                notifyListeners();
                return;
            }
            createExpandAnimator(getCurrentWeight(), this.layoutWeight, duration2, interpolator2).start();
        }
    }

    public void collapse() {
        if (!this.isAnimating) {
            createExpandAnimator(getCurrentWeight(), 0.0f, (long) this.duration, this.interpolator).start();
        }
    }

    public void collapse(long duration2, @Nullable TimeInterpolator interpolator2) {
        if (!this.isAnimating) {
            if (duration2 <= 0) {
                this.isExpanded = false;
                setWeight(0.0f);
                requestLayout();
                notifyListeners();
                return;
            }
            createExpandAnimator(getCurrentWeight(), 0.0f, duration2, interpolator2).start();
        }
    }

    public void setDuration(@NonNull int duration2) {
        if (duration2 >= 0) {
            this.duration = duration2;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Animators cannot have negative duration: ");
        sb.append(duration2);
        throw new IllegalArgumentException(sb.toString());
    }

    public void setExpanded(boolean expanded) {
        float currentWeight = getCurrentWeight();
        if (!expanded || currentWeight != this.layoutWeight) {
            float f = 0.0f;
            if (expanded || currentWeight != 0.0f) {
                this.isExpanded = expanded;
                if (expanded) {
                    f = this.layoutWeight;
                }
                setWeight(f);
                requestLayout();
            }
        }
    }

    public boolean isExpanded() {
        return this.isExpanded;
    }

    public void setInterpolator(@NonNull TimeInterpolator interpolator2) {
        this.interpolator = interpolator2;
    }

    public void setExpandWeight(float expandWeight) {
        this.layoutWeight = expandWeight;
    }

    public float getCurrentWeight() {
        return ((LayoutParams) getLayoutParams()).weight;
    }

    public void move(float weight) {
        move(weight, (long) this.duration, this.interpolator);
    }

    public void move(float weight, long duration2, @Nullable TimeInterpolator interpolator2) {
        if (!this.isAnimating) {
            if (duration2 <= 0) {
                this.isExpanded = weight > 0.0f;
                setWeight(weight);
                requestLayout();
                notifyListeners();
                return;
            }
            createExpandAnimator(getCurrentWeight(), weight, duration2, interpolator2).start();
        }
    }

    private ValueAnimator createExpandAnimator(float from, final float to, long duration2, @Nullable TimeInterpolator interpolator2) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[]{from, to});
        valueAnimator.setDuration(duration2);
        valueAnimator.setInterpolator(interpolator2 == null ? this.interpolator : interpolator2);
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                ExpandableWeightLayout.this.setWeight(((Float) animation.getAnimatedValue()).floatValue());
                ExpandableWeightLayout.this.requestLayout();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                ExpandableWeightLayout.this.isAnimating = true;
                if (ExpandableWeightLayout.this.listener != null) {
                    ExpandableWeightLayout.this.listener.onAnimationStart();
                    float access$300 = ExpandableWeightLayout.this.layoutWeight;
                    float f = to;
                    if (access$300 == f) {
                        ExpandableWeightLayout.this.listener.onPreOpen();
                        return;
                    }
                    if (0.0f == f) {
                        ExpandableWeightLayout.this.listener.onPreClose();
                    }
                }
            }

            public void onAnimationEnd(Animator animation) {
                boolean z = false;
                ExpandableWeightLayout.this.isAnimating = false;
                ExpandableWeightLayout expandableWeightLayout = ExpandableWeightLayout.this;
                if (to > 0.0f) {
                    z = true;
                }
                expandableWeightLayout.isExpanded = z;
                if (ExpandableWeightLayout.this.listener != null) {
                    ExpandableWeightLayout.this.listener.onAnimationEnd();
                    if (to == ExpandableWeightLayout.this.layoutWeight) {
                        ExpandableWeightLayout.this.listener.onOpened();
                        return;
                    }
                    if (to == 0.0f) {
                        ExpandableWeightLayout.this.listener.onClosed();
                    }
                }
            }
        });
        return valueAnimator;
    }

    /* access modifiers changed from: private */
    public void setWeight(float weight) {
        ((LayoutParams) getLayoutParams()).weight = weight;
    }

    private void notifyListeners() {
        ExpandableLayoutListener expandableLayoutListener = this.listener;
        if (expandableLayoutListener != null) {
            expandableLayoutListener.onAnimationStart();
            if (this.isExpanded) {
                this.listener.onPreOpen();
            } else {
                this.listener.onPreClose();
            }
            this.mGlobalLayoutListener = new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    if (VERSION.SDK_INT < 16) {
                        ExpandableWeightLayout.this.getViewTreeObserver().removeGlobalOnLayoutListener(ExpandableWeightLayout.this.mGlobalLayoutListener);
                    } else {
                        ExpandableWeightLayout.this.getViewTreeObserver().removeOnGlobalLayoutListener(ExpandableWeightLayout.this.mGlobalLayoutListener);
                    }
                    ExpandableWeightLayout.this.listener.onAnimationEnd();
                    if (ExpandableWeightLayout.this.isExpanded) {
                        ExpandableWeightLayout.this.listener.onOpened();
                    } else {
                        ExpandableWeightLayout.this.listener.onClosed();
                    }
                }
            };
            getViewTreeObserver().addOnGlobalLayoutListener(this.mGlobalLayoutListener);
        }
    }
}
