package com.payumoney.sdkui.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.payumoney.core.entity.EmiTenure;
import com.payumoney.core.entity.PaymentEntity;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.ui.utils.Utils;
import java.util.List;

public class EmiTenureRecyclerViewAdapter extends Adapter<EmiTenureViewHolder> implements OnClickListener {
    /* access modifiers changed from: private */
    public final PaymentEntity a;
    private final Context b;
    /* access modifiers changed from: private */
    public final OnTenureSelectedListener c;
    /* access modifiers changed from: private */
    public int d = -1;

    public class EmiTenureViewHolder extends ViewHolder implements OnClickListener {
        CheckBox a;
        TextView b;
        TextView c;
        TextView d;
        View e;

        public EmiTenureViewHolder(View emiTenureRow) {
            super(emiTenureRow);
            this.a = (CheckBox) emiTenureRow.findViewById(R.id.cb_emi_tenure);
            this.b = (TextView) emiTenureRow.findViewById(R.id.tv_emi_tenure);
            this.c = (TextView) emiTenureRow.findViewById(R.id.tv_emi_amount);
            this.d = (TextView) emiTenureRow.findViewById(R.id.tv_emi_interest);
            this.e = emiTenureRow;
            emiTenureRow.setOnClickListener(this);
        }

        public void onClick(View v) {
            EmiTenureRecyclerViewAdapter.this.d = getAdapterPosition();
            EmiTenureRecyclerViewAdapter.this.notifyDataSetChanged();
            if (EmiTenureRecyclerViewAdapter.this.c != null) {
                EmiTenureRecyclerViewAdapter.this.c.onTenureSelected((EmiTenure) EmiTenureRecyclerViewAdapter.this.a.getEmiTenures().get(EmiTenureRecyclerViewAdapter.this.d));
            }
        }
    }

    public interface OnTenureSelectedListener {
        void onTenureSelected(EmiTenure emiTenure);
    }

    public EmiTenureRecyclerViewAdapter(PaymentEntity selectedEmiBank, Context context, OnTenureSelectedListener onTenureSelectedListener) {
        this.a = selectedEmiBank;
        this.b = context;
        this.c = onTenureSelectedListener;
    }

    public EmiTenureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EmiTenureViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.emi_tenure_layout, parent, false));
    }

    public void onBindViewHolder(EmiTenureViewHolder holder, int position) {
        EmiTenure emiTenure = (EmiTenure) this.a.getEmiTenures().get(position);
        boolean z = false;
        holder.a.setVisibility(0);
        holder.a.setChecked(this.d == position);
        holder.b.setText(String.format("%s@%s%%", new Object[]{emiTenure.getTitle(), Utils.getProcessedDisplayAmount(Double.valueOf(emiTenure.getEmiBankInterest()).doubleValue())}));
        holder.c.setText(this.b.getString(R.string.pnp_amount_text, new Object[]{Utils.getProcessedDisplayAmount(Double.valueOf(emiTenure.getEmiValue()).doubleValue())}));
        holder.d.setText(this.b.getString(R.string.pnp_amount_text, new Object[]{Utils.getProcessedDisplayAmount(Double.valueOf(emiTenure.getEmiInterestPaid()).doubleValue())}));
        View view = holder.e;
        if (this.d == position) {
            z = true;
        }
        view.setSelected(z);
    }

    public int getItemCount() {
        List emiTenures = this.a.getEmiTenures();
        if (emiTenures == null || emiTenures.isEmpty()) {
            return 0;
        }
        return emiTenures.size();
    }

    public void onClick(View v) {
    }
}
