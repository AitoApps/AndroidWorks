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
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import java.util.ArrayList;
import java.util.List;

public class ExpandableLinearLayout extends LinearLayout implements ExpandableLayout {
    private List<Integer> childSizeList;
    /* access modifiers changed from: private */
    public int closePosition;
    private int defaultChildIndex;
    private boolean defaultExpanded;
    private int defaultPosition;
    private int duration;
    private boolean inRecyclerView;
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
    private boolean recyclerExpanded;
    private ExpandableSavedState savedState;

    public ExpandableLinearLayout(Context context) {
        this(context, null);
    }

    public ExpandableLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.interpolator = new LinearInterpolator();
        this.closePosition = 0;
        this.layoutSize = 0;
        this.inRecyclerView = false;
        this.isArranged = false;
        this.isCalculatedSize = false;
        this.isAnimating = false;
        this.recyclerExpanded = false;
        this.childSizeList = new ArrayList();
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public ExpandableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.interpolator = new LinearInterpolator();
        this.closePosition = 0;
        this.layoutSize = 0;
        this.inRecyclerView = false;
        this.isArranged = false;
        this.isCalculatedSize = false;
        this.isAnimating = false;
        this.recyclerExpanded = false;
        this.childSizeList = new ArrayList();
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.expandableLayout, defStyleAttr, 0);
        this.duration = a.getInteger(R.styleable.expandableLayout_ael_duration, ExpandableLayout.DEFAULT_DURATION);
        this.defaultExpanded = a.getBoolean(R.styleable.expandableLayout_ael_expanded, false);
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
        int i3;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!this.isCalculatedSize) {
            this.childSizeList.clear();
            int childCount = getChildCount();
            if (childCount > 0) {
                int sumSize = 0;
                for (int i4 = 0; i4 < childCount; i4++) {
                    View view = getChildAt(i4);
                    LayoutParams params = (LayoutParams) view.getLayoutParams();
                    if (i4 > 0) {
                        sumSize = ((Integer) this.childSizeList.get(i4 - 1)).intValue();
                    }
                    List<Integer> list = this.childSizeList;
                    if (isVertical()) {
                        i3 = view.getMeasuredHeight() + params.topMargin;
                        i2 = params.bottomMargin;
                    } else {
                        i3 = view.getMeasuredWidth() + params.leftMargin;
                        i2 = params.rightMargin;
                    }
                    list.add(Integer.valueOf(i3 + i2 + sumSize));
                }
                int intValue = ((Integer) this.childSizeList.get(childCount - 1)).intValue();
                if (isVertical()) {
                    i = getPaddingTop() + getPaddingBottom();
                } else {
                    i = getPaddingLeft() + getPaddingRight();
                }
                this.layoutSize = intValue + i;
                this.isCalculatedSize = true;
            } else {
                throw new IllegalStateException("The expandableLinearLayout must have at least one child");
            }
        }
        if (!this.isArranged) {
            if (!this.defaultExpanded) {
                setLayoutSize(this.closePosition);
            }
            if (this.inRecyclerView) {
                setLayoutSize(this.recyclerExpanded ? this.layoutSize : this.closePosition);
            }
            int childNumbers = this.childSizeList.size();
            int i5 = this.defaultChildIndex;
            if (childNumbers > i5 && childNumbers > 0) {
                moveChild(i5, 0, null);
            }
            int i6 = this.defaultPosition;
            if (i6 > 0) {
                int i7 = this.layoutSize;
                if (i7 >= i6 && i7 > 0) {
                    move(i6, 0, null);
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
        if (this.inRecyclerView) {
            this.recyclerExpanded = expanded;
        }
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

    public void initLayout() {
        this.closePosition = 0;
        this.layoutSize = 0;
        this.isArranged = false;
        this.isCalculatedSize = false;
        this.savedState = null;
        if (isVertical()) {
            measure(MeasureSpec.makeMeasureSpec(getWidth(), 1073741824), MeasureSpec.makeMeasureSpec(getHeight(), 0));
        } else {
            measure(MeasureSpec.makeMeasureSpec(getWidth(), 0), MeasureSpec.makeMeasureSpec(getHeight(), 1073741824));
        }
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

    public int getChildPosition(int index) {
        if (index >= 0 && this.childSizeList.size() > index) {
            return ((Integer) this.childSizeList.get(index)).intValue();
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

    public void setInRecyclerView(boolean inRecyclerView2) {
        this.inRecyclerView = inRecyclerView2;
    }

    /* access modifiers changed from: private */
    public boolean isVertical() {
        return getOrientation() == 1;
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
                if (ExpandableLinearLayout.this.isVertical()) {
                    ExpandableLinearLayout.this.getLayoutParams().height = ((Integer) animator.getAnimatedValue()).intValue();
                } else {
                    ExpandableLinearLayout.this.getLayoutParams().width = ((Integer) animator.getAnimatedValue()).intValue();
                }
                ExpandableLinearLayout.this.requestLayout();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                ExpandableLinearLayout.this.isAnimating = true;
                if (ExpandableLinearLayout.this.listener != null) {
                    ExpandableLinearLayout.this.listener.onAnimationStart();
                    if (ExpandableLinearLayout.this.layoutSize == to) {
                        ExpandableLinearLayout.this.listener.onPreOpen();
                        return;
                    }
                    if (ExpandableLinearLayout.this.closePosition == to) {
                        ExpandableLinearLayout.this.listener.onPreClose();
                    }
                }
            }

            public void onAnimationEnd(Animator animator) {
                boolean z = false;
                ExpandableLinearLayout.this.isAnimating = false;
                ExpandableLinearLayout expandableLinearLayout = ExpandableLinearLayout.this;
                if (to > expandableLinearLayout.closePosition) {
                    z = true;
                }
                expandableLinearLayout.isExpanded = z;
                if (ExpandableLinearLayout.this.listener != null) {
                    ExpandableLinearLayout.this.listener.onAnimationEnd();
                    if (to == ExpandableLinearLayout.this.layoutSize) {
                        ExpandableLinearLayout.this.listener.onOpened();
                        return;
                    }
                    if (to == ExpandableLinearLayout.this.closePosition) {
                        ExpandableLinearLayout.this.listener.onClosed();
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
                        ExpandableLinearLayout.this.getViewTreeObserver().removeGlobalOnLayoutListener(ExpandableLinearLayout.this.mGlobalLayoutListener);
                    } else {
                        ExpandableLinearLayout.this.getViewTreeObserver().removeOnGlobalLayoutListener(ExpandableLinearLayout.this.mGlobalLayoutListener);
                    }
                    ExpandableLinearLayout.this.listener.onAnimationEnd();
                    if (ExpandableLinearLayout.this.isExpanded) {
                        ExpandableLinearLayout.this.listener.onOpened();
                    } else {
                        ExpandableLinearLayout.this.listener.onClosed();
                    }
                }
            };
            getViewTreeObserver().addOnGlobalLayoutListener(this.mGlobalLayoutListener);
        }
    }
}
