package com.payumoney.sdkui.ui.fragments;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.payumoney.core.utils.SharedPrefsUtils;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.ui.utils.Utils;

public abstract class BaseFragment extends Fragment {
    protected String b;
    TextView c;
    TextView d;
    TextView e;
    LinearLayout f;
    TextView g;
    TextView h;
    TextView i;
    protected double j;
    double k;
    LinearLayout l;

    public double getConvenieneceFee() {
        return this.k;
    }

    public void setConvenieneceFee(double convenienceFee) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            StringBuilder sb = new StringBuilder();
            sb.append(Double.parseDouble(this.b) + convenienceFee);
            sb.append("");
            SharedPrefsUtils.setStringPreference(getActivity().getBaseContext(), "netamount", sb.toString());
            this.k = convenienceFee;
        }
    }

    public void initConvenieneceFee(View layout) {
        this.g = (TextView) layout.findViewById(R.id.quick_pay_balance);
        this.l = (LinearLayout) layout.findViewById(R.id.r_amount_layout);
        this.f = (LinearLayout) layout.findViewById(R.id.l_convenience_fee);
        this.c = (TextView) layout.findViewById(R.id.subtotal_amount);
        this.d = (TextView) layout.findViewById(R.id.convenience_fee_amount);
        this.e = (TextView) layout.findViewById(R.id.total_amount);
        this.h = (TextView) layout.findViewById(R.id.show_button);
        this.i = (TextView) layout.findViewById(R.id.hide_button);
        setUpUIConvenienceFee();
    }

    public void setUpUIConvenienceFee() {
        this.f.setVisibility(8);
    }

    public void hideConvenieneceFee() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            this.h.setText("Details");
            this.f.setVisibility(8);
            this.l.setBackground(null);
            this.h.setVisibility(0);
        }
    }

    public void showConvenieneceFee() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            this.h.setText("Hide Details");
            this.f.setVisibility(0);
            this.l.setBackgroundColor(getActivity().getResources().getColor(R.color.payumoney_white));
            this.h.setVisibility(0);
        }
    }

    public void setAmount(String mAmount) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            this.b = mAmount;
            this.g.setText(getString(R.string.quick_pay_amount, Utils.getProcessedDisplayAmount(Double.valueOf(mAmount).doubleValue())));
            this.g.setVisibility(0);
        }
    }

    public void hideConvenienceFeeOption() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            this.j = Double.parseDouble(this.b);
            setDisplayAmount(Double.parseDouble(this.b));
            hideConvenieneceFee();
            updateConvenienceFee(this.j, 0.0d);
        }
    }

    public void showConvenienceFeeOption() {
        if (getActivity() != null && !getActivity().isFinishing() && this.f.getVisibility() != 0) {
            this.h.setVisibility(0);
        }
    }

    public void setDisplayAmount(double mAmount) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            String processedDisplayAmount = Utils.getProcessedDisplayAmount(mAmount);
            this.g.setText(getString(R.string.quick_pay_amount, processedDisplayAmount));
        }
    }

    public void updateConvenienceFee(double mAmount, double mConvenienceFee) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            setConvenieneceFee(mConvenienceFee);
            this.j = mAmount + mConvenienceFee;
            setDisplayAmount(this.j);
            String processedDisplayAmount = Utils.getProcessedDisplayAmount(this.j);
            this.e.setText(getString(R.string.pnp_amount_text, processedDisplayAmount));
            String processedDisplayAmount2 = Utils.getProcessedDisplayAmount(mConvenienceFee);
            this.d.setText(getString(R.string.pnp_amount_text, processedDisplayAmount2));
            String processedDisplayAmount3 = Utils.getProcessedDisplayAmount(mAmount);
            this.c.setText(getString(R.string.pnp_amount_text, processedDisplayAmount3));
        }
    }

    /* access modifiers changed from: protected */
    public void a(View view, float f2) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            view.setAlpha(f2);
        }
    }
}
