package com.github.aakira.expandablelayout;

import android.animation.TimeInterpolator;
import android.support.annotation.IntRange;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

public class Utils {
    public static final int ACCELERATE_DECELERATE_INTERPOLATOR = 0;
    public static final int ACCELERATE_INTERPOLATOR = 1;
    public static final int ANTICIPATE_INTERPOLATOR = 2;
    public static final int ANTICIPATE_OVERSHOOT_INTERPOLATOR = 3;
    public static final int BOUNCE_INTERPOLATOR = 4;
    public static final int DECELERATE_INTERPOLATOR = 5;
    public static final int FAST_OUT_LINEAR_IN_INTERPOLATOR = 6;
    public static final int FAST_OUT_SLOW_IN_INTERPOLATOR = 7;
    public static final int LINEAR_INTERPOLATOR = 8;
    public static final int LINEAR_OUT_SLOW_IN_INTERPOLATOR = 9;
    public static final int OVERSHOOT_INTERPOLATOR = 10;

    public static TimeInterpolator createInterpolator(@IntRange(from = 0, to = 10) int interpolatorType) {
        switch (interpolatorType) {
            case 0:
                return new AccelerateDecelerateInterpolator();
            case 1:
                return new AccelerateInterpolator();
            case 2:
                return new AnticipateInterpolator();
            case 3:
                return new AnticipateOvershootInterpolator();
            case 4:
                return new BounceInterpolator();
            case 5:
                return new DecelerateInterpolator();
            case 6:
                return new FastOutLinearInInterpolator();
            case 7:
                return new FastOutSlowInInterpolator();
            case 8:
                return new LinearInterpolator();
            case 9:
                return new LinearOutSlowInInterpolator();
            case 10:
                return new OvershootInterpolator();
            default:
                return new LinearInterpolator();
        }
    }
}
