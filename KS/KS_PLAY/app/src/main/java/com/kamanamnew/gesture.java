package com.kamanamnew;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class gesture extends SimpleOnGestureListener {
    private static final int ACTION_FAKE = -13;
    public static final int MODE_DYNAMIC = 2;
    public static final int MODE_SOLID = 1;
    public static final int MODE_TRANSPARENT = 0;
    public static final int SWIPE_DOWN = 2;
    public static final int SWIPE_LEFT = 3;
    public static final int SWIPE_RIGHT = 4;
    public static final int SWIPE_UP = 1;
    private Activity context;
    private GestureDetector detector;
    private SimpleGestureListener listener;
    private int mode = 2;
    private boolean running = true;
    private int swipe_Max_Distance = 350;
    private int swipe_Min_Distance = 100;
    private int swipe_Min_Velocity = 100;
    private boolean tapIndicator = false;

    interface SimpleGestureListener {
        void onDoubleTap();

        void onSwipe(int i);
    }

    public gesture(Activity context2, SimpleGestureListener sgl) {
        this.context = context2;
        this.detector = new GestureDetector(context2, this);
        this.listener = sgl;
    }

    public void onTouchEvent(MotionEvent event) {
        if (this.running) {
            boolean result = this.detector.onTouchEvent(event);
            int i = this.mode;
            if (i == 1) {
                event.setAction(3);
            } else if (i == 2) {
                if (event.getAction() == ACTION_FAKE) {
                    event.setAction(1);
                } else if (result) {
                    event.setAction(3);
                } else if (this.tapIndicator) {
                    event.setAction(0);
                    this.tapIndicator = false;
                }
            }
        }
    }

    public void setMode(int m) {
        this.mode = m;
    }

    public int getMode() {
        return this.mode;
    }

    public void setEnabled(boolean status) {
        this.running = status;
    }

    public void setSwipeMaxDistance(int distance) {
        this.swipe_Max_Distance = distance;
    }

    public void setSwipeMinDistance(int distance) {
        this.swipe_Min_Distance = distance;
    }

    public void setSwipeMinVelocity(int distance) {
        this.swipe_Min_Velocity = distance;
    }

    public int getSwipeMaxDistance() {
        return this.swipe_Max_Distance;
    }

    public int getSwipeMinDistance() {
        return this.swipe_Min_Distance;
    }

    public int getSwipeMinVelocity() {
        return this.swipe_Min_Velocity;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float xDistance = Math.abs(e1.getX() - e2.getX());
        float yDistance = Math.abs(e1.getY() - e2.getY());
        int i = this.swipe_Max_Distance;
        if (xDistance > ((float) i) || yDistance > ((float) i)) {
            return false;
        }
        float velocityX2 = Math.abs(velocityX);
        float velocityY2 = Math.abs(velocityY);
        boolean result = false;
        if (velocityX2 > ((float) this.swipe_Min_Velocity) && xDistance > ((float) this.swipe_Min_Distance)) {
            if (e1.getX() > e2.getX()) {
                this.listener.onSwipe(3);
            } else {
                this.listener.onSwipe(4);
            }
            result = true;
        } else if (velocityY2 > ((float) this.swipe_Min_Velocity) && yDistance > ((float) this.swipe_Min_Distance)) {
            if (e1.getY() > e2.getY()) {
                this.listener.onSwipe(1);
            } else {
                this.listener.onSwipe(2);
            }
            result = true;
        }
        return result;
    }

    public boolean onSingleTapUp(MotionEvent e) {
        this.tapIndicator = true;
        try {
            Position_View.back_move.setAlpha(1.0f);
            Position_View.next_move.setAlpha(1.0f);
            Position_View.animation_back();
            Position_View.animation_next();
        } catch (Exception e2) {
        }
        return false;
    }

    public boolean onDoubleTap(MotionEvent arg) {
        this.listener.onDoubleTap();
        return true;
    }

    public boolean onDoubleTapEvent(MotionEvent arg) {
        return true;
    }

    public boolean onSingleTapConfirmed(MotionEvent arg) {
        if (this.mode == 2) {
            arg.setAction(ACTION_FAKE);
            this.context.dispatchTouchEvent(arg);
        }
        return false;
    }
}
