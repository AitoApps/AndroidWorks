package com.payumoney.sdkui.ui.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerViewOnItemClickListener implements OnItemTouchListener {
    private OnItemClickListener a;
    private GestureDetector b;

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    public RecyclerViewOnItemClickListener(Context context, OnItemClickListener listener) {
        this.a = listener;
        this.b = new GestureDetector(context, new SimpleOnGestureListener() {
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View findChildViewUnder = view.findChildViewUnder(e.getX(), e.getY());
        if (!(findChildViewUnder == null || this.a == null || !this.b.onTouchEvent(e))) {
            this.a.onItemClick(findChildViewUnder, view.getChildPosition(findChildViewUnder));
        }
        return false;
    }

    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}
