package com.payumoney.sdkui.ui.widgets;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

public class WrapContentHeightViewPager extends ViewPager {
    private static final String a = WrapContentHeightViewPager.class.getSimpleName();
    /* access modifiers changed from: private */
    public int b = 0;
    private int c = 0;
    private int d;
    private boolean e;
    private int f;
    private int g;
    private int h = -1;

    private class PagerAdapterWrapper extends PagerAdapter {
        private final PagerAdapter b;
        private SparseArray<Object> c;

        PagerAdapterWrapper(PagerAdapter adapter2) {
            this.b = adapter2;
            this.c = new SparseArray<>(adapter2.getCount());
        }

        public void startUpdate(ViewGroup container) {
            this.b.startUpdate(container);
        }

        public Object instantiateItem(ViewGroup container, int position) {
            Object instantiateItem = this.b.instantiateItem(container, position);
            this.c.put(position, instantiateItem);
            return instantiateItem;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            this.b.destroyItem(container, position, object);
            this.c.remove(position);
        }

        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            this.b.setPrimaryItem(container, position, object);
        }

        public void finishUpdate(ViewGroup container) {
            this.b.finishUpdate(container);
        }

        public Parcelable saveState() {
            return this.b.saveState();
        }

        public void restoreState(Parcelable state, ClassLoader loader) {
            this.b.restoreState(state, loader);
        }

        public int getItemPosition(Object object) {
            return this.b.getItemPosition(object);
        }

        public void notifyDataSetChanged() {
            this.b.notifyDataSetChanged();
        }

        public void registerDataSetObserver(DataSetObserver observer) {
            this.b.registerDataSetObserver(observer);
        }

        public void unregisterDataSetObserver(DataSetObserver observer) {
            this.b.unregisterDataSetObserver(observer);
        }

        public float getPageWidth(int position) {
            return this.b.getPageWidth(position);
        }

        public CharSequence getPageTitle(int position) {
            return this.b.getPageTitle(position);
        }

        public int getCount() {
            return this.b.getCount();
        }

        public boolean isViewFromObject(View view, Object object) {
            return this.b.isViewFromObject(view, object);
        }

        /* access modifiers changed from: 0000 */
        public Object a(int i) {
            return this.c.get(i);
        }
    }

    public WrapContentHeightViewPager(Context context) {
        super(context);
        a();
    }

    public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        a();
    }

    private void a() {
        addOnPageChangeListener(new OnPageChangeListener() {
            int a;

            public void onPageScrolled(int position, float offset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if (this.a == 0) {
                    WrapContentHeightViewPager.this.b = 0;
                }
            }

            public void onPageScrollStateChanged(int state) {
                this.a = state;
            }
        });
    }

    public void setAdapter(PagerAdapter adapter2) {
        this.b = 0;
        super.setAdapter(new PagerAdapterWrapper(adapter2));
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.d = widthMeasureSpec;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        if (mode == 0 || mode == Integer.MIN_VALUE) {
            if (this.b == 0) {
                this.c = 0;
                for (int i = 0; i < getChildCount(); i++) {
                    View childAt = getChildAt(i);
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    if (layoutParams != null && layoutParams.isDecor) {
                        int i2 = layoutParams.gravity & 112;
                        if (i2 == 48 || i2 == 80) {
                            this.c += childAt.getMeasuredHeight();
                        }
                    }
                }
                View a2 = a(getCurrentItem());
                if (a2 != null) {
                    this.b = a(a2);
                }
            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(this.b + this.c + getPaddingBottom() + getPaddingTop(), 1073741824);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void onPageScrolled(int position, float offset, int positionOffsetPixels) {
        super.onPageScrolled(position, offset, positionOffsetPixels);
        if (this.h != position) {
            this.h = position;
            View a2 = a(position);
            View a3 = a(position + 1);
            if (a2 == null || a3 == null) {
                this.e = false;
            } else {
                this.g = a(a2);
                this.f = a(a3);
                this.e = true;
            }
        }
        if (this.e) {
            int i = (int) ((((float) this.g) * (1.0f - offset)) + (((float) this.f) * offset));
            if (this.b != i) {
                this.b = i;
                requestLayout();
                invalidate();
            }
        }
    }

    private int a(View view) {
        view.measure(getChildMeasureSpec(this.d, getPaddingLeft() + getPaddingRight(), view.getLayoutParams().width), MeasureSpec.makeMeasureSpec(0, 0));
        return view.getMeasuredHeight();
    }

    /* access modifiers changed from: protected */
    public View a(int i) {
        if (getAdapter() != null) {
            Object a2 = ((PagerAdapterWrapper) getAdapter()).a(i);
            if (a2 != null) {
                for (int i2 = 0; i2 < getChildCount(); i2++) {
                    View childAt = getChildAt(i2);
                    if (childAt != null && getAdapter().isViewFromObject(childAt, a2)) {
                        return childAt;
                    }
                }
            }
        }
        return null;
    }
}
