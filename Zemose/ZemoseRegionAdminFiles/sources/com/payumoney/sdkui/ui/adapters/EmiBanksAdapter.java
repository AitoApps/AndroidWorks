package com.payumoney.sdkui.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.payumoney.core.entity.PaymentEntity;
import com.payumoney.graphics.AssetDownloadManager;
import com.payumoney.graphics.BitmapCallBack;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.ui.utils.Utils;
import java.util.List;

public class EmiBanksAdapter extends Adapter<ViewHolder> {
    /* access modifiers changed from: private */
    public final Context a;
    /* access modifiers changed from: private */
    public final List<PaymentEntity> b;
    /* access modifiers changed from: private */
    public final EmiBankItemOnSelectListener c;
    private final boolean d;
    /* access modifiers changed from: private */
    public int e = -1;

    public interface EmiBankItemOnSelectListener {
        void onEmiBankSelected(PaymentEntity paymentEntity);

        void onViewMoreEmiBanksClicked();
    }

    class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements OnClickListener {
        TextView a;
        TextView b;
        ImageView c;
        RelativeLayout d;

        ViewHolder(View itemView) {
            super(itemView);
            this.a = (TextView) itemView.findViewById(R.id.textview_recyclerview_item);
            this.c = (ImageView) itemView.findViewById(R.id.imageview_recyclerview_item);
            this.b = (TextView) itemView.findViewById(R.id.view_more_bank);
            this.d = (RelativeLayout) itemView.findViewById(R.id.static_bank_item_layout);
            this.d.setOnClickListener(this);
        }

        public void onClick(View v) {
            if (getAdapterPosition() < EmiBanksAdapter.this.b.size()) {
                EmiBanksAdapter.this.e = getAdapterPosition();
                if (EmiBanksAdapter.this.e != -1) {
                    EmiBanksAdapter.this.c.onEmiBankSelected((PaymentEntity) EmiBanksAdapter.this.b.get(EmiBanksAdapter.this.e));
                    EmiBanksAdapter.this.notifyDataSetChanged();
                }
            }
        }
    }

    public void setmSelectedItem(int mSelectedItem) {
        this.e = mSelectedItem;
    }

    public EmiBanksAdapter(Context context, List<PaymentEntity> bankCIDArrayList, EmiBankItemOnSelectListener emiBankItemOnSelectListener, boolean showMoreButton) {
        this.a = context;
        this.b = bankCIDArrayList;
        this.c = emiBankItemOnSelectListener;
        this.d = showMoreButton;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.static_bank_item, parent, false));
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        boolean z = false;
        if (position < this.b.size()) {
            holder.b.setVisibility(8);
            holder.a.setVisibility(0);
            holder.c.setVisibility(0);
            RelativeLayout relativeLayout = holder.d;
            Context context = this.a;
            relativeLayout.setBackgroundDrawable(Utils.makeSelectorStaticBankItem(context, ContextCompat.getColor(context, R.color.light_gray)));
            RelativeLayout relativeLayout2 = holder.d;
            if (position == this.e) {
                z = true;
            }
            relativeLayout2.setSelected(z);
            PaymentEntity paymentEntity = (PaymentEntity) this.b.get(holder.getAdapterPosition());
            if (paymentEntity != null) {
                holder.a.setText(paymentEntity.getCode());
                if (!TextUtils.isEmpty(paymentEntity.getCode())) {
                    AssetDownloadManager.getInstance().getBankBitmapByBankCode(paymentEntity.getCode(), new BitmapCallBack() {
                        public void onBitmapReceived(Bitmap bitmap) {
                            holder.c.setImageDrawable(new BitmapDrawable(EmiBanksAdapter.this.a.getResources(), bitmap));
                        }

                        public void onBitmapFailed(Bitmap bitmap) {
                            holder.c.setImageDrawable(new BitmapDrawable(EmiBanksAdapter.this.a.getResources(), bitmap));
                        }
                    });
                } else {
                    holder.c.setVisibility(8);
                }
            }
        } else if (this.d) {
            holder.b.setVisibility(0);
            holder.a.setVisibility(8);
            holder.c.setVisibility(8);
            holder.b.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    EmiBanksAdapter.this.c.onViewMoreEmiBanksClicked();
                }
            });
        }
    }

    public int getItemCount() {
        if (this.d) {
            return this.b.size() + 1;
        }
        return this.b.size();
    }
}
