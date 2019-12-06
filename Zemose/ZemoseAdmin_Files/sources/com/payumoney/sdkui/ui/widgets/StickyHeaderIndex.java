package com.payumoney.sdkui.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.ui.adapters.IndexHeaderAdapter;
import com.payumoney.sdkui.ui.adapters.IndexHeaderAdapter.RowStyle;

public class StickyHeaderIndex extends RelativeLayout {
    private IndexScrollListener a;
    private IndexHeaderAdapter b;
    private IndexHeaderLayoutManager c;

    public StickyHeaderIndex(Context context) {
        super(context);
        a(context, null);
    }

    public StickyHeaderIndex(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context, attrs);
    }

    public StickyHeaderIndex(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a(context, attrs);
    }

    public StickyHeaderIndex(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        a(context, attrs);
    }

    private void a(Context context, AttributeSet attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.sticky_index, this, true);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.index_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == 2;
            }
        });
        char[] cArr = new char[0];
        RowStyle b2 = b(context, attributeSet);
        View findViewById = findViewById(R.id.sticky_index_wrapper);
        LayoutParams layoutParams = findViewById.getLayoutParams();
        layoutParams.width = b2.getStickyWidth().intValue();
        findViewById.setLayoutParams(layoutParams);
        invalidate();
        this.b = new IndexHeaderAdapter(cArr, b2);
        recyclerView.setAdapter(this.b);
        this.a = new IndexScrollListener();
        this.a.a(recyclerView);
        this.c = new IndexHeaderLayoutManager(this);
        this.c.a(recyclerView);
        this.a.register(this.c);
        setStickyHeaderIndexStyle(b2);
    }

    private RowStyle b(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return null;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.StickyIndex);
        float dimension = obtainStyledAttributes.getDimension(R.styleable.StickyIndex_android_textSize, -1.0f);
        int color = obtainStyledAttributes.getColor(R.styleable.StickyIndex_stickyViewTextColor, -1);
        if (dimension == -1.0f) {
            dimension = 26.0f;
        }
        if (color == -1) {
            color = ContextCompat.getColor(context, R.color.index_text_color);
        }
        RowStyle rowStyle = new RowStyle(Float.valueOf(obtainStyledAttributes.getDimension(R.styleable.StickyIndex_rowHeight, -1.0f)), Float.valueOf(obtainStyledAttributes.getDimension(R.styleable.StickyIndex_stickyWidth, -1.0f)), Integer.valueOf(color), Float.valueOf(dimension), Integer.valueOf(obtainStyledAttributes.getInt(R.styleable.StickyIndex_android_textStyle, -1)));
        obtainStyledAttributes.recycle();
        return rowStyle;
    }

    private void setStickyHeaderIndexStyle(RowStyle styles) {
        if (styles.getRowHeigh().floatValue() != -1.0f) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.sticky_index_wrapper);
            LayoutParams layoutParams = linearLayout.getLayoutParams();
            layoutParams.height = styles.getRowHeigh().intValue();
            linearLayout.setLayoutParams(layoutParams);
        }
        if (styles.getTextSize().floatValue() != -1.0f) {
            this.c.getStickyIndex().setTextSize(0, styles.getTextSize().floatValue());
        }
        if (styles.getTextColor() != null) {
            this.c.getStickyIndex().setTextColor(styles.getTextColor().intValue());
        }
        if (styles.getTextStyle().intValue() != -1) {
            this.c.getStickyIndex().setTypeface(null, styles.getTextStyle().intValue());
        }
    }

    public void subscribeForScrollListener(Subscriber nexSubscriber) {
        this.a.register(nexSubscriber);
    }

    public void removeScrollListenerSubscription(Subscriber oldSubscriber) {
        this.a.unregister(oldSubscriber);
    }

    public void setDataSet(char[] dataSet) {
        this.b.setDataSet(dataSet);
        this.b.notifyDataSetChanged();
    }

    public IndexHeaderLayoutManager getStickyHeaderIndex() {
        return this.c;
    }

    public void setOnScrollListener(RecyclerView rl) {
        this.a.a(rl);
    }
}
