package com.payumoney.sdkui.ui.widgets;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

interface PageIndicator extends OnPageChangeListener {
    void notifyDataSetChanged();

    void setCurrentItem(int i);

    void setOnPageChangeListener(OnPageChangeListener onPageChangeListener);

    void setViewPager(ViewPager viewPager);

    void setViewPager(ViewPager viewPager, int i);
}
