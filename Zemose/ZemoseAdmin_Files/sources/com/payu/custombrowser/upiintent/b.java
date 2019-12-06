package com.payu.custombrowser.upiintent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.payu.custombrowser.PackageListDialogFragment;
import com.payu.custombrowser.R;
import java.util.List;

public class b extends Adapter<a> {
    private List<a> a;
    private Context b;
    /* access modifiers changed from: private */
    public com.payu.custombrowser.b.b c;
    /* access modifiers changed from: private */
    public PackageListDialogFragment d;

    class a extends ViewHolder {
        ImageView a;
        TextView b;
        LinearLayout c;

        public a(View view) {
            super(view);
            this.c = (LinearLayout) view;
            this.a = (ImageView) view.findViewById(R.id.image);
            this.b = (TextView) view.findViewById(R.id.text);
        }
    }

    public b(List<a> list, Context context, PackageListDialogFragment packageListDialogFragment) {
        this.a = list;
        this.b = context;
        this.c = (com.payu.custombrowser.b.b) context;
        this.d = packageListDialogFragment;
    }

    @NonNull
    /* renamed from: a */
    public a onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new a(LayoutInflater.from(this.b).inflate(R.layout.cb_layout_package_list, viewGroup, false));
    }

    /* renamed from: a */
    public void onBindViewHolder(@NonNull a aVar, int i) {
        final a aVar2 = (a) this.a.get(aVar.getAdapterPosition());
        aVar.b.setText(aVar2.b());
        aVar.a.setImageDrawable(aVar2.c());
        aVar.c.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                b.this.d.dismiss();
                b.this.c.a(aVar2.a());
            }
        });
    }

    public int getItemCount() {
        return this.a.size();
    }
}
