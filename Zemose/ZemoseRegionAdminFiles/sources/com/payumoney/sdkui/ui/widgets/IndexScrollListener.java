package com.payumoney.sdkui.ui.widgets;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import java.util.ArrayList;
import java.util.List;

public class IndexScrollListener extends OnScrollListener implements Publisher {
    private List<Subscriber> a = new ArrayList();

    IndexScrollListener() {
    }

    public void onScrolled(RecyclerView rv, int dx, int dy) {
        notifySubscribers(rv, dx, dy);
    }

    public void register(Subscriber newObserver) {
        this.a.add(newObserver);
    }

    public void unregister(Subscriber existentObserver) {
        this.a.remove(existentObserver);
    }

    public void notifySubscribers(RecyclerView rv, int dx, int dy) {
        for (Subscriber update : this.a) {
            update.update(rv, (float) dx, (float) dy);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(RecyclerView recyclerView) {
        recyclerView.setOnScrollListener(this);
    }
}
