package com.payumoney.sdkui.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Keep;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

@Keep
public class AutoFitRecyclerView extends RecyclerView {
    private int columnWidth = -1;
    private GridLayoutManager manager;

    public AutoFitRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public AutoFitRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AutoFitRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, new int[]{16843031});
            this.columnWidth = obtainStyledAttributes.getDimensionPixelSize(0, -1);
            obtainStyledAttributes.recycle();
        }
        this.manager = new GridLayoutManager(getContext(), 3);
        setLayoutManager(this.manager);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
    }
}
