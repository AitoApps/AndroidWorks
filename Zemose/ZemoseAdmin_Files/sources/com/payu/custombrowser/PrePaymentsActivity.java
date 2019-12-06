package com.payu.custombrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.payu.custombrowser.bean.b;

public class PrePaymentsActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            Intent intent = new Intent(this, PreLollipopPaymentsActivity.class);
            intent.putExtra("data", getIntent().getExtras());
            startActivity(intent);
            overridePendingTransition(0, 0);
            return;
        }
        finish();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (b.SINGLETON.getPayuCustomBrowserCallback() != null) {
            b.SINGLETON.setPayuCustomBrowserCallback(null);
        }
    }
}
