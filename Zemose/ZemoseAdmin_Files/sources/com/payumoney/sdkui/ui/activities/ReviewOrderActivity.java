package com.payumoney.sdkui.ui.activities;

import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.payu.custombrowser.bean.ReviewOrderBundle;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.ui.adapters.ReviewOrderRecyclerViewAdapter;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import java.util.ArrayList;

public class ReviewOrderActivity extends AppCompatActivity {
    private Toolbar a;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        int intExtra = getIntent().getIntExtra(PayUmoneyFlowManager.KEY_STYLE, -1);
        if (intExtra != -1) {
            setTheme(intExtra);
        } else {
            setTheme(R.style.AppTheme_default);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);
        ReviewOrderBundle reviewOrderBundle = PayUmoneyConfig.getInstance().getReviewOrderBundle();
        if (reviewOrderBundle == null) {
            finish();
            return;
        }
        ArrayList reviewOrderDatas = reviewOrderBundle.getReviewOrderDatas();
        if (reviewOrderDatas == null || reviewOrderDatas.isEmpty()) {
            finish();
            return;
        }
        this.a = (Toolbar) findViewById(R.id.toolbar_review_order_screen);
        Toolbar toolbar = this.a;
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            a();
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            View inflate = LayoutInflater.from(this).inflate(R.layout.review_order_toolbar, null);
            getSupportActionBar().setCustomView(inflate);
            ((TextView) inflate.findViewById(R.id.title)).setText(R.string.review_order_details);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_review_orders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ReviewOrderRecyclerViewAdapter(reviewOrderDatas, this));
        findViewById(R.id.btn_review_order_go_back).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ReviewOrderActivity.this.finish();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        onBackPressed();
        return true;
    }

    private void a() {
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.close);
        drawable.setColorFilter(Color.parseColor(PayUmoneyConfig.getInstance().getTextColorPrimary()), Mode.SRC_ATOP);
        if (this.a != null) {
            getSupportActionBar().setHomeAsUpIndicator(drawable);
        }
    }
}
