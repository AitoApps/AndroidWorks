package com.payumoney.sdkui.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.payu.custombrowser.bean.ReviewOrderData;
import com.payumoney.sdkui.R;
import java.util.List;

public class ReviewOrderRecyclerViewAdapter extends Adapter<ReviewOrderRecyclerViewHolder> {
    private final List<ReviewOrderData> a;
    private final Context b;

    public class ReviewOrderRecyclerViewHolder extends ViewHolder {
        TextView a;
        TextView b;

        public ReviewOrderRecyclerViewHolder(View itemView) {
            super(itemView);
            this.a = (TextView) itemView.findViewById(R.id.review_order_item_key);
            this.b = (TextView) itemView.findViewById(R.id.review_order_item_value);
        }
    }

    public ReviewOrderRecyclerViewAdapter(List<ReviewOrderData> reviewOrderItems, Context context) {
        this.a = reviewOrderItems;
        this.b = context;
    }

    public ReviewOrderRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewOrderRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.review_order_row_layout, parent, false));
    }

    public void onBindViewHolder(ReviewOrderRecyclerViewHolder holder, int position) {
        ReviewOrderData reviewOrderData = (ReviewOrderData) this.a.get(position);
        holder.a.setText(reviewOrderData.getKey());
        holder.b.setText(reviewOrderData.getValue());
    }

    public int getItemCount() {
        List<ReviewOrderData> list = this.a;
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}
