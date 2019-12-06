package com.payumoney.sdkui.ui.widgets;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.payumoney.sdkui.R;

public class IndexHeaderLayoutManager implements Subscriber {
    private TextView a;
    private RecyclerView b;

    IndexHeaderLayoutManager(RelativeLayout rl) {
        this.a = (TextView) rl.findViewById(R.id.sticky_index);
    }

    private Boolean a(TextView textView, TextView textView2) {
        if (a(textView.getText().charAt(0), textView2.getText().charAt(0)).booleanValue()) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private Boolean a(char c, char c2) {
        if (Character.toLowerCase(c) == Character.toLowerCase(c2)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private void b(RecyclerView recyclerView) {
        View childAt = recyclerView.getChildAt(0);
        ((LinearLayoutManager) this.b.getLayoutManager()).scrollToPositionWithOffset(recyclerView.getChildPosition(childAt), childAt.getTop());
    }

    public void update(RecyclerView referenceList, float dx, float dy) {
        RecyclerView recyclerView = this.b;
        if (recyclerView != null) {
            if (recyclerView.getChildCount() < 2) {
                this.a.setVisibility(4);
                return;
            }
            b(referenceList);
            View childAt = this.b.getChildAt(0);
            TextView textView = (TextView) childAt.findViewById(R.id.sticky_row_index);
            TextView textView2 = (TextView) this.b.getChildAt(1).findViewById(R.id.sticky_row_index);
            int childCount = this.b.getChildCount();
            int childPosition = this.b.getChildPosition(childAt);
            int i = childPosition + 1;
            int i2 = childPosition + childCount;
            this.a.setText(String.valueOf(a(textView)).toUpperCase());
            this.a.setVisibility(0);
            textView.setAlpha(1.0f);
            if (dy > 0.0f) {
                if (i <= i2) {
                    if (a(textView, textView2).booleanValue()) {
                        this.a.setVisibility(4);
                        textView.setVisibility(0);
                        textView2.setVisibility(0);
                    } else {
                        textView.setVisibility(4);
                        this.a.setVisibility(0);
                    }
                }
            } else if (i <= i2) {
                textView.setVisibility(4);
                if ((a(textView, textView2).booleanValue() || a(textView) != a(textView2)) && a(textView, textView2).booleanValue()) {
                    this.a.setVisibility(4);
                    textView.setVisibility(0);
                    textView2.setVisibility(0);
                } else {
                    textView2.setVisibility(4);
                }
            }
            if (this.a.getVisibility() == 0) {
                textView.setVisibility(4);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(RecyclerView recyclerView) {
        this.b = recyclerView;
    }

    private char a(TextView textView) {
        return textView.getText().charAt(0);
    }

    public TextView getStickyIndex() {
        return this.a;
    }
}
