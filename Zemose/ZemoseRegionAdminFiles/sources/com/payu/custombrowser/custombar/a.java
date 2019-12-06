package com.payu.custombrowser.custombar;

import android.view.View;
import com.payu.custombrowser.R;

public class a {
    public void a(View view) {
        view.setVisibility(0);
        d(view);
    }

    private void d(View view) {
        DotsProgressBar dotsProgressBar = (DotsProgressBar) view.findViewById(R.id.dotsProgressBar);
        dotsProgressBar.setDotsCount(5);
        dotsProgressBar.a();
    }

    public void b(View view) {
        DotsProgressBar dotsProgressBar = (DotsProgressBar) view.findViewById(R.id.dotsProgressBar);
        view.setVisibility(8);
        dotsProgressBar.b();
    }

    public void c(View view) {
        view.setVisibility(8);
    }
}
