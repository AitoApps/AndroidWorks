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
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import java.util.ArrayList;
import java.util.List;

public class ExpandableRelativeLayout extends RelativeLayout implements ExpandableLayout {
    private List<Integer> childPositionList;
    private List<Integer> childSizeList;
    /* access modifiers changed from: private */
    public int closePosition;
    private int defaultChildIndex;
    private boolean defaultExpanded;
    private int defaultPosition;
    private int duration;
    private TimeInterpolator interpolator;
    /* access modifiers changed from: private */
    public boolean isAnimating;
    private boolean isArranged;
    private boolean isCalculatedSize;
    /* access modifiers changed from: private */
    public boolean isExpanded;
    /* access modifiers changed from: private */
    public int layoutSize;
    /* access modifiers changed from: private */
    public ExpandableLayoutListener listener;
    /* access modifiers changed from: private */
    public OnGlobalLayoutListener mGlobalLayoutListener;
    private int orientation;
    private ExpandableSavedState savedState;

    public ExpandableRelativeLayout(Context context) {
        this(context, null);
    }

    public ExpandableRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.interpolator = new LinearInterpolator();
        this.closePosition = 0;
        this.layoutSize = 0;
        this.isArranged = false;
        this.isCalculatedSize = false;
        this.isAnimating = false;
        this.childSizeList = new ArrayList();
        this.childPositionList = new ArrayList();
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public ExpandableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.interpolator = new LinearInterpolator();
        this.closePosition = 0;
        this.layoutSize = 0;
        this.isArranged = false;
        this.isCalculatedSize = false;
        this.isAnimating = false;
        this.childSizeList = new ArrayList();
        this.childPositionList = new ArrayList();
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.expandableLayout, defStyleAttr, 0);
        this.duration = a.getInteger(R.styleable.expandableLayout_ael_duration, ExpandableLayout.DEFAULT_DURATION);
        this.defaultExpanded = a.getBoolean(R.styleable.expandableLayout_ael_expanded, false);
        this.orientation = a.getInteger(R.styleable.expandableLayout_ael_orientation, 1);
        this.defaultChildIndex = a.getInteger(R.styleable.expandableLayout_ael_defaultChildIndex, Integer.MAX_VALUE);
        this.defaultPosition = a.getDimensionPixelSize(R.styleable.expandableLayout_ael_defaultPosition, Integer.MIN_VALUE);
        int interpolatorType = a.getInteger(R.styleable.expandableLayout_ael_interpolator, 8);
        a.recycle();
        this.interpolator = Utils.createInterpolator(interpolatorType);
        this.isExpanded = this.defaultExpanded;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        int i2;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!this.isCalculatedSize) {
            int measureSpec = MeasureSpec.makeMeasureSpec(0, 0);
            if (isVertical()) {
                int measuredHeight = getMeasuredHeight();
                super.onMeasure(widthMeasureSpec, measureSpec);
                this.layoutSize = getMeasuredHeight();
                setMeasuredDimension(getMeasuredWidth(), measuredHeight);
            } else {
                int measuredWidth = getMeasuredWidth();
                super.onMeasure(measureSpec, heightMeasureSpec);
                this.layoutSize = getMeasuredWidth();
                setMeasuredDimension(measuredWidth, getMeasuredHeight());
            }
            this.childSizeList.clear();
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                View view = getChildAt(i3);
                LayoutParams params = (LayoutParams) view.getLayoutParams();
                List<Integer> list = this.childSizeList;
                if (isVertical()) {
                    i2 = view.getMeasuredHeight() + params.topMargin;
                    i = params.bottomMargin;
                } else {
                    i2 = view.getMeasuredWidth() + params.leftMargin;
                    i = params.rightMargin;
                }
                list.add(Integer.valueOf(i2 + i));
            }
            this.isCalculatedSize = true;
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!this.isArranged) {
            this.childPositionList.clear();
            for (int i = 0; i < getChildCount(); i++) {
                this.childPositionList.add(Integer.valueOf((int) (isVertical() ? getChildAt(i).getY() : getChildAt(i).getX())));
            }
            if (this.defaultExpanded == 0) {
                setLayoutSize(this.closePosition);
            }
            int childNumbers = this.childSizeList.size();
            int i2 = this.defaultChildIndex;
            if (childNumbers > i2 && childNumbers > 0) {
                moveChild(i2, 0, null);
            }
            int i3 = this.defaultPosition;
            if (i3 > 0) {
                int i4 = this.layoutSize;
                if (i4 >= i3 && i4 > 0) {
                    move(i3, 0, null);
                }
            }
            this.isArranged = true;
            ExpandableSavedState expandableSavedState = this.savedState;
            if (expandableSavedState != null) {
                setLayoutSize(expandableSavedState.getSize());
            }
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        ExpandableSavedState ss = new ExpandableSavedState(super.onSaveInstanceState());
        ss.setSize(getCurrentPosition());
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
        if (this.closePosition < getCurrentPosition()) {
            collapse(duration2, interpolator2);
        } else {
            expand(duration2, interpolator2);
        }
    }

    public void expand() {
        if (!this.isAnimating) {
            createExpandAnimator(getCurrentPosition(), this.layoutSize, (long) this.duration, this.interpolator).start();
        }
    }

    public void expand(long duration2, @Nullable TimeInterpolator interpolator2) {
        if (!this.isAnimating) {
            if (duration2 <= 0) {
                move(this.layoutSize, duration2, interpolator2);
                return;
            }
            createExpandAnimator(getCurrentPosition(), this.layoutSize, duration2, interpolator2).start();
        }
    }

    public void collapse() {
        if (!this.isAnimating) {
            createExpandAnimator(getCurrentPosition(), this.closePosition, (long) this.duration, this.interpolator).start();
        }
    }

    public void collapse(long duration2, @Nullable TimeInterpolator interpolator2) {
        if (!this.isAnimating) {
            if (duration2 <= 0) {
                move(this.closePosition, duration2, interpolator2);
                return;
            }
            createExpandAnimator(getCurrentPosition(), this.closePosition, duration2, interpolator2).start();
        }
    }

    public void setDuration(int duration2) {
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
        int currentPosition = getCurrentPosition();
        if ((!expanded || currentPosition != this.layoutSize) && (expanded || currentPosition != this.closePosition)) {
            this.isExpanded = expanded;
            setLayoutSize(expanded ? this.layoutSize : this.closePosition);
            requestLayout();
        }
    }

    public boolean isExpanded() {
        return this.isExpanded;
    }

    public void setInterpolator(@NonNull TimeInterpolator interpolator2) {
        this.interpolator = interpolator2;
    }

    public void move(int position) {
        move(position, (long) this.duration, this.interpolator);
    }

    public void move(int position, long duration2, @Nullable TimeInterpolator interpolator2) {
        if (!this.isAnimating && position >= 0 && this.layoutSize >= position) {
            if (duration2 <= 0) {
                this.isExpanded = position > this.closePosition;
                setLayoutSize(position);
                requestLayout();
                notifyListeners();
                return;
            }
            createExpandAnimator(getCurrentPosition(), position, duration2, interpolator2 == null ? this.interpolator : interpolator2).start();
        }
    }

    public void moveChild(int index) {
        moveChild(index, (long) this.duration, this.interpolator);
    }

    public void moveChild(int index, long duration2, @Nullable TimeInterpolator interpolator2) {
        if (!this.isAnimating) {
            int destination = getChildPosition(index) + (isVertical() ? getPaddingBottom() : getPaddingRight());
            if (duration2 <= 0) {
                this.isExpanded = destination > this.closePosition;
                setLayoutSize(destination);
                requestLayout();
                notifyListeners();
                return;
            }
            createExpandAnimator(getCurrentPosition(), destination, duration2, interpolator2 == null ? this.interpolator : interpolator2).start();
        }
    }

    public void setOrientation(int orientation2) {
        this.orientation = orientation2;
    }

    public int getChildPosition(int index) {
        if (index >= 0 && this.childSizeList.size() > index) {
            return ((Integer) this.childPositionList.get(index)).intValue() + ((Integer) this.childSizeList.get(index)).intValue();
        }
        throw new IllegalArgumentException("There aren't the view having this index.");
    }

    public int getClosePosition() {
        return this.closePosition;
    }

    public void setClosePosition(int position) {
        this.closePosition = position;
    }

    public int getCurrentPosition() {
        return isVertical() ? getMeasuredHeight() : getMeasuredWidth();
    }

    public void setClosePositionIndex(int childIndex) {
        this.closePosition = getChildPosition(childIndex);
    }

    /* access modifiers changed from: private */
    public boolean isVertical() {
        return this.orientation == 1;
    }

    private void setLayoutSize(int size) {
        if (isVertical()) {
            getLayoutParams().height = size;
        } else {
            getLayoutParams().width = size;
        }
    }

    private ValueAnimator createExpandAnimator(int from, final int to, long duration2, TimeInterpolator interpolator2) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(new int[]{from, to});
        valueAnimator.setDuration(duration2);
        valueAnimator.setInterpolator(interpolator2);
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animator) {
                if (ExpandableRelativeLayout.this.isVertical()) {
                    ExpandableRelativeLayout.this.getLayoutParams().height = ((Integer) animator.getAnimatedValue()).intValue();
                } else {
                    ExpandableRelativeLayout.this.getLayoutParams().width = ((Integer) animator.getAnimatedValue()).intValue();
                }
                ExpandableRelativeLayout.this.requestLayout();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                ExpandableRelativeLayout.this.isAnimating = true;
                if (ExpandableRelativeLayout.this.listener != null) {
                    ExpandableRelativeLayout.this.listener.onAnimationStart();
                    if (ExpandableRelativeLayout.this.layoutSize == to) {
                        ExpandableRelativeLayout.this.listener.onPreOpen();
                        return;
                    }
                    if (ExpandableRelativeLayout.this.closePosition == to) {
                        ExpandableRelativeLayout.this.listener.onPreClose();
                    }
                }
            }

            public void onAnimationEnd(Animator animator) {
                boolean z = false;
                ExpandableRelativeLayout.this.isAnimating = false;
                ExpandableRelativeLayout expandableRelativeLayout = ExpandableRelativeLayout.this;
                if (to > expandableRelativeLayout.closePosition) {
                    z = true;
                }
                expandableRelativeLayout.isExpanded = z;
                if (ExpandableRelativeLayout.this.listener != null) {
                    ExpandableRelativeLayout.this.listener.onAnimationEnd();
                    if (to == ExpandableRelativeLayout.this.layoutSize) {
                        ExpandableRelativeLayout.this.listener.onOpened();
                        return;
                    }
                    if (to == ExpandableRelativeLayout.this.closePosition) {
                        ExpandableRelativeLayout.this.listener.onClosed();
                    }
                }
            }
        });
        return valueAnimator;
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
                        ExpandableRelativeLayout.this.getViewTreeObserver().removeGlobalOnLayoutListener(ExpandableRelativeLayout.this.mGlobalLayoutListener);
                    } else {
                        ExpandableRelativeLayout.this.getViewTreeObserver().removeOnGlobalLayoutListener(ExpandableRelativeLayout.this.mGlobalLayoutListener);
                    }
                    ExpandableRelativeLayout.this.listener.onAnimationEnd();
                    if (ExpandableRelativeLayout.this.isExpanded) {
                        ExpandableRelativeLayout.this.listener.onOpened();
                    } else {
                        ExpandableRelativeLayout.this.listener.onClosed();
                    }
                }
            };
            getViewTreeObserver().addOnGlobalLayoutListener(this.mGlobalLayoutListener);
        }
    }
}
