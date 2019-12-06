package com.bumptech.glide.request.transition;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import com.bumptech.glide.request.transition.Transition.ViewAdapter;

public class ViewTransition<R> implements Transition<R> {
    private final ViewTransitionAnimationFactory viewTransitionAnimationFactory;

    interface ViewTransitionAnimationFactory {
        Animation build(Context context);
    }

    ViewTransition(ViewTransitionAnimationFactory viewTransitionAnimationFactory2) {
        this.viewTransitionAnimationFactory = viewTransitionAnimationFactory2;
    }

    public boolean transition(R r, ViewAdapter adapter2) {
        View view = adapter2.getView();
        if (view != null) {
            view.clearAnimation();
            view.startAnimation(this.viewTransitionAnimationFactory.build(view.getContext()));
        }
        return false;
    }
}
