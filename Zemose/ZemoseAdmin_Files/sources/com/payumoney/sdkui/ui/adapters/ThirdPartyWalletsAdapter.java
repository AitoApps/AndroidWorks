package com.payumoney.sdkui.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.entity.PaymentEntity;
import com.payumoney.graphics.AssetDownloadManager;
import com.payumoney.graphics.BitmapCallBack;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.ui.utils.Utils;
import java.util.List;

public class ThirdPartyWalletsAdapter extends Adapter<ViewHolder> {
    /* access modifiers changed from: private */
    public final Context a;
    /* access modifiers changed from: private */
    public final List<PaymentEntity> b;
    /* access modifiers changed from: private */
    public final ThirdPartyStaticWalletListener c;
    private boolean d = false;
    /* access modifiers changed from: private */
    public int e = -1;

    public interface ThirdPartyStaticWalletListener {
        void onMoreWalletsSelected();

        void onStaticWalletSelected(PaymentEntity paymentEntity);
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
            if (getAdapterPosition() < ThirdPartyWalletsAdapter.this.b.size()) {
                ThirdPartyWalletsAdapter.this.e = getAdapterPosition();
                if (ThirdPartyWalletsAdapter.this.e != -1) {
                    ThirdPartyWalletsAdapter.this.c.onStaticWalletSelected((PaymentEntity) ThirdPartyWalletsAdapter.this.b.get(ThirdPartyWalletsAdapter.this.e));
                    ThirdPartyWalletsAdapter.this.notifyDataSetChanged();
                }
            }
        }
    }

    public void setmSelectedItem(int mSelectedItem) {
        this.e = mSelectedItem;
    }

    public ThirdPartyWalletsAdapter(Context context, List<PaymentEntity> walletsCIDList, ThirdPartyStaticWalletListener thirdPartyStaticWalletListener, boolean shouldShowMoreButton) {
        this.a = context;
        this.b = walletsCIDList;
        this.c = thirdPartyStaticWalletListener;
        this.d = shouldShowMoreButton;
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
            if (((PaymentEntity) this.b.get(holder.getAdapterPosition())).getShortTitle() == null || ((PaymentEntity) this.b.get(holder.getAdapterPosition())).getShortTitle().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING) || ((PaymentEntity) this.b.get(holder.getAdapterPosition())).getShortTitle().isEmpty()) {
                holder.a.setText(((PaymentEntity) this.b.get(holder.getAdapterPosition())).getTitle());
            } else {
                holder.a.setText(((PaymentEntity) this.b.get(holder.getAdapterPosition())).getShortTitle());
            }
            AssetDownloadManager.getInstance().getWalletBitmap(((PaymentEntity) this.b.get(holder.getAdapterPosition())).getCode(), new BitmapCallBack() {
                public void onBitmapReceived(Bitmap bitmap) {
                    holder.c.setImageDrawable(new BitmapDrawable(ThirdPartyWalletsAdapter.this.a.getResources(), bitmap));
                }

                public void onBitmapFailed(Bitmap bitmap) {
                    holder.c.setImageDrawable(new BitmapDrawable(ThirdPartyWalletsAdapter.this.a.getResources(), bitmap));
                }
            });
        } else if (this.d) {
            holder.b.setVisibility(0);
            holder.a.setVisibility(8);
            holder.c.setVisibility(8);
            holder.b.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ThirdPartyWalletsAdapter.this.c.onMoreWalletsSelected();
                }
            });
        } else {
            holder.b.setVisibility(8);
            holder.a.setVisibility(8);
        }
    }

    public int getItemCount() {
        if (this.d) {
            return this.b.size() + 1;
        }
        return this.b.size();
    }
}
