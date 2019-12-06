package com.payumoney.sdkui.ui.adapters;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.TextView;
import com.payumoney.core.entity.PaymentEntity;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.presenter.fragmentPresenter.IRecyclerViewOnItemClickListener;
import com.payumoney.sdkui.ui.events.TextGetter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends Adapter<NetBankingOptionsViewHolder> implements Filterable, TextGetter {
    private final CustomFilter a = new CustomFilter(this, this.e);
    /* access modifiers changed from: private */
    public List<PaymentEntity> b;
    /* access modifiers changed from: private */
    public List<PaymentEntity> c;
    /* access modifiers changed from: private */
    public String d;
    private SearchContentChangeListener e;
    /* access modifiers changed from: private */
    public IRecyclerViewOnItemClickListener f;

    private class CustomFilter extends Filter {
        SearchContentChangeListener a;
        private RecyclerViewAdapter c;

        private CustomFilter(RecyclerViewAdapter mAdapter, SearchContentChangeListener searchContentChangeListener) {
            this.c = mAdapter;
            this.a = searchContentChangeListener;
        }

        /* access modifiers changed from: protected */
        public FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint.length() > 0) {
                ArrayList arrayList = new ArrayList();
                synchronized (this) {
                    String trim = constraint.toString().toLowerCase().trim();
                    for (PaymentEntity paymentEntity : RecyclerViewAdapter.this.c) {
                        if (paymentEntity.getTitle().toLowerCase().startsWith(trim)) {
                            arrayList.add(paymentEntity);
                        }
                    }
                }
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("Count Number ");
                sb.append(arrayList.size());
                printStream.println(sb.toString());
                filterResults.values = arrayList;
                filterResults.count = arrayList.size();
            } else {
                filterResults.count = RecyclerViewAdapter.this.c.size();
                filterResults.values = RecyclerViewAdapter.this.c;
            }
            return filterResults;
        }

        /* access modifiers changed from: protected */
        public void publishResults(CharSequence constraint, FilterResults results) {
            PrintStream printStream = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append("Count Number 2 ");
            sb.append(((List) results.values).size());
            printStream.println(sb.toString());
            RecyclerViewAdapter.this.b = (List) results.values;
            this.a.publishSearchResult((List) results.values);
            RecyclerViewAdapter.this.notifyDataSetChanged();
        }
    }

    static class NetBankingOptionsViewHolder extends ViewHolder {
        TextView a;

        NetBankingOptionsViewHolder(View v) {
            super(v);
            this.a = (TextView) v.findViewById(R.id.contact_name);
        }
    }

    public interface SearchContentChangeListener {
        void publishSearchResult(List<PaymentEntity> list);
    }

    public RecyclerViewAdapter(IRecyclerViewOnItemClickListener listener, List<PaymentEntity> netBankingOptions, SearchContentChangeListener searchContentChangeListener, String tag) {
        this.b = netBankingOptions;
        this.f = listener;
        this.e = searchContentChangeListener;
        this.c = netBankingOptions;
        this.d = tag;
    }

    public NetBankingOptionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NetBankingOptionsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_row_details, parent, false));
    }

    public Filter getFilter() {
        return this.a;
    }

    public void onBindViewHolder(NetBankingOptionsViewHolder holder, int position) {
        final PaymentEntity paymentEntity = (PaymentEntity) this.b.get(position);
        holder.a.setText(paymentEntity.getTitle());
        holder.a.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (RecyclerViewAdapter.this.f != null) {
                    RecyclerViewAdapter.this.f.onItemClickListener(paymentEntity, RecyclerViewAdapter.this.d);
                }
                RecyclerViewAdapter.this.f = null;
            }
        });
    }

    public PaymentEntity getContactByName(String name) {
        for (PaymentEntity paymentEntity : this.b) {
            if (name.equals(paymentEntity.getTitle())) {
                return paymentEntity;
            }
        }
        return null;
    }

    public int getItemCount() {
        return this.b.size();
    }

    public PaymentEntity getNetBankingOption(int pos) {
        return (PaymentEntity) this.b.get(pos);
    }

    public PaymentEntity getNetbankingOptionByName(String name) {
        for (PaymentEntity paymentEntity : this.b) {
            if (name.equals(paymentEntity.getTitle())) {
                return paymentEntity;
            }
        }
        return null;
    }

    public List<PaymentEntity> getNetBankingOptionList() {
        return this.b;
    }

    public String getTextFromAdapter(int pos) {
        return String.valueOf(((PaymentEntity) this.b.get(pos)).getTitle().charAt(0)).toUpperCase();
    }
}
