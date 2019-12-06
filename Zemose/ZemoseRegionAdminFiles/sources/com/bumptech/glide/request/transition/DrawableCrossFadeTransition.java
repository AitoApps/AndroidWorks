package com.bumptech.glide.request.transition;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import com.bumptech.glide.request.transition.Transition.ViewAdapter;

public class DrawableCrossFadeTransition implements Transition<Drawable> {
    private final int duration;
    private final boolean isCrossFadeEnabled;

    public DrawableCrossFadeTransition(int duration2, boolean isCrossFadeEnabled2) {
        this.duration = duration2;
        this.isCrossFadeEnabled = isCrossFadeEnabled2;
    }

    public boolean transition(Drawable current, ViewAdapter adapter2) {
        Drawable previous = adapter2.getCurrentDrawable();
        if (previous == null) {
            previous = new ColorDrawable(0);
        }
        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{previous, current});
        transitionDrawable.setCrossFadeEnabled(this.isCrossFadeEnabled);
        transitionDrawable.startTransition(this.duration);
        adapter2.setDrawable(transitionDrawable);
        return true;
    }
}
