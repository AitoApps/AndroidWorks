package com.payumoney.sdkui.ui.adapters;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class ZoomOutTransformer implements PageTransformer {
    private static float b = 0.9f;
    private static float c = 0.5f;
    public boolean a = true;

    public void transformPage(View page, float position) {
        float max = Math.max(b, 1.0f - Math.abs(position));
        page.setScaleX(max);
        page.setScaleY(max);
    }
}
