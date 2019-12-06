package com.github.aakira.expandablelayout;

public interface ExpandableLayoutListener {
    void onAnimationEnd();

    void onAnimationStart();

    void onClosed();

    void onOpened();

    void onPreClose();

    void onPreOpen();
}
