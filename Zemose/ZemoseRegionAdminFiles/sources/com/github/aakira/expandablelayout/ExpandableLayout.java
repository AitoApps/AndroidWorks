package com.github.aakira.expandablelayout;

import android.animation.TimeInterpolator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface ExpandableLayout {
    public static final int DEFAULT_DURATION = 300;
    public static final boolean DEFAULT_EXPANDED = false;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    void collapse();

    void collapse(long j, @Nullable TimeInterpolator timeInterpolator);

    void expand();

    void expand(long j, @Nullable TimeInterpolator timeInterpolator);

    boolean isExpanded();

    void setDuration(int i);

    void setExpanded(boolean z);

    void setInterpolator(@NonNull TimeInterpolator timeInterpolator);

    void setListener(@NonNull ExpandableLayoutListener expandableLayoutListener);

    void toggle();

    void toggle(long j, @Nullable TimeInterpolator timeInterpolator);
}
