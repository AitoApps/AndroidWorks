package com.payumoney.sdkui.ui.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import com.payumoney.sdkui.R;

public class CirclePageIndicator extends View implements PageIndicator {
    private final Paint a;
    private final Paint b;
    private final Paint c;
    private float d;
    private ViewPager e;
    private OnPageChangeListener f;
    private int g;
    private int h;
    private float i;
    private int j;
    private int k;
    private boolean l;
    private boolean m;
    private int n;
    private float o;
    private int p;
    private boolean q;

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int a;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.a = in.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.a);
        }
    }

    public CirclePageIndicator(Context context) {
        this(context, null);
    }

    public CirclePageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.vpiCirclePageIndicatorStyle);
    }

    public CirclePageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.a = new Paint(1);
        this.b = new Paint(1);
        this.c = new Paint(1);
        this.o = -1.0f;
        this.p = -1;
        if (!isInEditMode()) {
            Resources resources = getResources();
            int color = ContextCompat.getColor(context, R.color.default_circle_indicator_page_color);
            int color2 = ContextCompat.getColor(context, R.color.default_circle_indicator_fill_color);
            int integer = resources.getInteger(R.integer.default_circle_indicator_orientation);
            int color3 = ContextCompat.getColor(context, R.color.default_circle_indicator_stroke_color);
            float dimension = resources.getDimension(R.dimen.default_circle_indicator_stroke_width);
            float dimension2 = resources.getDimension(R.dimen.default_circle_indicator_radius);
            boolean z = resources.getBoolean(R.bool.default_circle_indicator_centered);
            boolean z2 = resources.getBoolean(R.bool.default_circle_indicator_snap);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.CirclePageIndicator, defStyle, 0);
            this.l = obtainStyledAttributes.getBoolean(R.styleable.CirclePageIndicator_centered, z);
            this.k = obtainStyledAttributes.getInt(R.styleable.CirclePageIndicator_android_orientation, integer);
            this.a.setStyle(Style.FILL);
            this.a.setColor(obtainStyledAttributes.getColor(R.styleable.CirclePageIndicator_pageColor, color));
            this.b.setStyle(Style.STROKE);
            this.b.setColor(obtainStyledAttributes.getColor(R.styleable.CirclePageIndicator_strokeColor, color3));
            this.b.setStrokeWidth(obtainStyledAttributes.getDimension(R.styleable.CirclePageIndicator_strokeWidth, dimension));
            this.c.setStyle(Style.FILL);
            this.c.setColor(obtainStyledAttributes.getColor(R.styleable.CirclePageIndicator_fillColor, color2));
            this.d = obtainStyledAttributes.getDimension(R.styleable.CirclePageIndicator_radius, dimension2);
            this.m = obtainStyledAttributes.getBoolean(R.styleable.CirclePageIndicator_snap, z2);
            Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.CirclePageIndicator_android_background);
            if (drawable != null) {
                setBackgroundDrawable(drawable);
            }
            obtainStyledAttributes.recycle();
            this.n = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
        }
    }

    public boolean isCentered() {
        return this.l;
    }

    public void setCentered(boolean centered) {
        this.l = centered;
        invalidate();
    }

    public int getPageColor() {
        return this.a.getColor();
    }

    public void setPageColor(int pageColor) {
        this.a.setColor(pageColor);
        invalidate();
    }

    public int getFillColor() {
        return this.c.getColor();
    }

    public void setFillColor(int fillColor) {
        this.c.setColor(fillColor);
        invalidate();
    }

    public int getOrientation() {
        return this.k;
    }

    public void setOrientation(int orientation) {
        switch (orientation) {
            case 0:
            case 1:
                this.k = orientation;
                requestLayout();
                return;
            default:
                throw new IllegalArgumentException("Orientation must be either HORIZONTAL or VERTICAL.");
        }
    }

    public int getStrokeColor() {
        return this.b.getColor();
    }

    public void setStrokeColor(int strokeColor) {
        this.b.setColor(strokeColor);
        invalidate();
    }

    public float getStrokeWidth() {
        return this.b.getStrokeWidth();
    }

    public void setStrokeWidth(float strokeWidth) {
        this.b.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public float getRadius() {
        return this.d;
    }

    public void setRadius(float radius) {
        this.d = radius;
        invalidate();
    }

    public boolean isSnap() {
        return this.m;
    }

    public void setSnap(boolean snap) {
        this.m = snap;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i2;
        int i3;
        int i4;
        int i5;
        float f2;
        float f3;
        super.onDraw(canvas);
        ViewPager viewPager = this.e;
        if (viewPager != null) {
            int count = viewPager.getAdapter().getCount();
            if (count != 0) {
                if (this.g >= count) {
                    setCurrentItem(count - 1);
                    return;
                }
                if (this.k == 0) {
                    i5 = getWidth();
                    i4 = getPaddingLeft();
                    i3 = getPaddingRight();
                    i2 = getPaddingTop();
                } else {
                    i5 = getHeight();
                    i4 = getPaddingTop();
                    i3 = getPaddingBottom();
                    i2 = getPaddingLeft();
                }
                float f4 = this.d;
                float f5 = 3.0f * f4;
                float f6 = ((float) i2) + f4;
                float f7 = ((float) i4) + f4;
                if (this.l) {
                    f7 += (((float) ((i5 - i4) - i3)) / 2.0f) - ((((float) count) * f5) / 2.0f);
                }
                float f8 = this.d;
                if (this.b.getStrokeWidth() > 0.0f) {
                    f8 -= this.b.getStrokeWidth() / 2.0f;
                }
                for (int i6 = 0; i6 < count; i6++) {
                    float f9 = (((float) i6) * f5) + f7;
                    if (this.k == 0) {
                        f3 = f6;
                    } else {
                        f3 = f9;
                        f9 = f6;
                    }
                    if (this.a.getAlpha() > 0) {
                        canvas.drawCircle(f9, f3, f8, this.a);
                    }
                    float f10 = this.d;
                    if (f8 != f10) {
                        canvas.drawCircle(f9, f3, f10, this.b);
                    }
                }
                float f11 = ((float) (this.m ? this.h : this.g)) * f5;
                if (!this.m) {
                    f11 += this.i * f5;
                }
                if (this.k == 0) {
                    f2 = f11 + f7;
                } else {
                    float f12 = f6;
                    f6 = f11 + f7;
                    f2 = f12;
                }
                canvas.drawCircle(f2, f6, this.d, this.c);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (super.onTouchEvent(ev)) {
            return true;
        }
        ViewPager viewPager = this.e;
        int i2 = 0;
        if (viewPager == null || viewPager.getAdapter().getCount() == 0) {
            return false;
        }
        int action = ev.getAction() & 255;
        switch (action) {
            case 0:
                this.p = MotionEventCompat.getPointerId(ev, 0);
                this.o = ev.getX();
                break;
            case 1:
            case 3:
                if (!this.q) {
                    int count = this.e.getAdapter().getCount();
                    float width = (float) getWidth();
                    float f2 = width / 2.0f;
                    float f3 = width / 6.0f;
                    if (this.g > 0 && ev.getX() < f2 - f3) {
                        if (action != 3) {
                            this.e.setCurrentItem(this.g - 1);
                        }
                        return true;
                    } else if (this.g < count - 1 && ev.getX() > f2 + f3) {
                        if (action != 3) {
                            this.e.setCurrentItem(this.g + 1);
                        }
                        return true;
                    }
                }
                this.q = false;
                this.p = -1;
                if (this.e.isFakeDragging()) {
                    this.e.endFakeDrag();
                    break;
                }
                break;
            case 2:
                float x = MotionEventCompat.getX(ev, MotionEventCompat.findPointerIndex(ev, this.p));
                float f4 = x - this.o;
                if (!this.q && Math.abs(f4) > ((float) this.n)) {
                    this.q = true;
                }
                if (this.q) {
                    this.o = x;
                    if (this.e.isFakeDragging() || this.e.beginFakeDrag()) {
                        this.e.fakeDragBy(f4);
                        break;
                    }
                }
                break;
            case 5:
                int actionIndex = MotionEventCompat.getActionIndex(ev);
                this.o = MotionEventCompat.getX(ev, actionIndex);
                this.p = MotionEventCompat.getPointerId(ev, actionIndex);
                break;
            case 6:
                int actionIndex2 = MotionEventCompat.getActionIndex(ev);
                if (MotionEventCompat.getPointerId(ev, actionIndex2) == this.p) {
                    if (actionIndex2 == 0) {
                        i2 = 1;
                    }
                    this.p = MotionEventCompat.getPointerId(ev, i2);
                }
                this.o = MotionEventCompat.getX(ev, MotionEventCompat.findPointerIndex(ev, this.p));
                break;
        }
        return true;
    }

    public void setViewPager(ViewPager view) {
        ViewPager viewPager = this.e;
        if (viewPager != view) {
            if (viewPager != null) {
                viewPager.setOnPageChangeListener(null);
            }
            if (view.getAdapter() != null) {
                this.e = view;
                this.e.setOnPageChangeListener(this);
                invalidate();
                return;
            }
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
    }

    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    public void setCurrentItem(int item) {
        ViewPager viewPager = this.e;
        if (viewPager != null) {
            viewPager.setCurrentItem(item);
            this.g = item;
            invalidate();
            return;
        }
        throw new IllegalStateException("ViewPager has not been bound.");
    }

    public void notifyDataSetChanged() {
        invalidate();
    }

    public void onPageScrollStateChanged(int state) {
        this.j = state;
        OnPageChangeListener onPageChangeListener = this.f;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.g = position;
        this.i = positionOffset;
        invalidate();
        OnPageChangeListener onPageChangeListener = this.f;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void onPageSelected(int position) {
        if (this.m || this.j == 0) {
            this.g = position;
            this.h = position;
            invalidate();
        }
        OnPageChangeListener onPageChangeListener = this.f;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(position);
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.f = listener;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.k == 0) {
            setMeasuredDimension(a(widthMeasureSpec), b(heightMeasureSpec));
        } else {
            setMeasuredDimension(b(widthMeasureSpec), a(heightMeasureSpec));
        }
    }

    private int a(int i2) {
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        if (mode == 1073741824) {
            return size;
        }
        ViewPager viewPager = this.e;
        if (viewPager == null) {
            return size;
        }
        int count = viewPager.getAdapter().getCount();
        float paddingLeft = (float) (getPaddingLeft() + getPaddingRight());
        float f2 = (float) (count * 2);
        float f3 = this.d;
        int i3 = (int) (paddingLeft + (f2 * f3) + (((float) (count - 1)) * f3) + 1.0f);
        if (mode == Integer.MIN_VALUE) {
            return Math.min(i3, size);
        }
        return i3;
    }

    private int b(int i2) {
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        if (mode == 1073741824) {
            return size;
        }
        int paddingTop = (int) ((this.d * 2.0f) + ((float) getPaddingTop()) + ((float) getPaddingBottom()) + 1.0f);
        if (mode == Integer.MIN_VALUE) {
            return Math.min(paddingTop, size);
        }
        return paddingTop;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.g = savedState.a;
        this.h = savedState.a;
        requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.a = this.g;
        return savedState;
    }
}
