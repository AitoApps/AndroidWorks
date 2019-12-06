package com.payumoney.sdkui.ui.adapters;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class PagerContainer extends FrameLayout implements OnPageChangeListener {
    boolean a = false;
    private ViewPager b;
    private Point c = new Point();
    private Point d = new Point();

    public PagerContainer(Context context) {
        super(context);
        a();
    }

    public PagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        a();
    }

    public PagerContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        a();
    }

    private void a() {
        setClipChildren(false);
        setLayerType(1, null);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        try {
            this.b = (ViewPager) getChildAt(0);
            this.b.setPageMargin(15);
            this.b.addOnPageChangeListener(this);
        } catch (Exception e) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }

    public ViewPager getViewPager() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            this.d.x = (int) ev.getX();
            this.d.y = (int) ev.getY();
        }
        ev.offsetLocation((float) (this.c.x - this.d.x), (float) (this.c.y - this.d.y));
        return this.b.dispatchTouchEvent(ev);
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (this.a) {
            invalidate();
        }
    }

    public void onPageSelected(int position) {
    }

    public void onPageScrollStateChanged(int state) {
        this.a = state != 0;
    }
}
