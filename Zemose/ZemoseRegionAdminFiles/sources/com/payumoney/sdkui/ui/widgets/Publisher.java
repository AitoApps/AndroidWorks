package com.payumoney.sdkui.ui.widgets;

import android.support.v7.widget.RecyclerView;

interface Publisher {
    void notifySubscribers(RecyclerView recyclerView, int i, int i2);

    void register(Subscriber subscriber);

    void unregister(Subscriber subscriber);
}
