package com.payumoney.sdkui.ui.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.entity.PaymentEntity;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.presenter.fragmentPresenter.IRecyclerViewOnItemClickListener;
import com.payumoney.sdkui.ui.adapters.RecyclerViewAdapter;
import com.payumoney.sdkui.ui.adapters.RecyclerViewAdapter.SearchContentChangeListener;
import com.payumoney.sdkui.ui.utils.PPConstants;
import com.payumoney.sdkui.ui.utils.PPLogger;
import com.payumoney.sdkui.ui.utils.RecyclerViewOnItemClickListener;
import com.payumoney.sdkui.ui.utils.RecyclerViewOnItemClickListener.OnItemClickListener;
import com.payumoney.sdkui.ui.utils.Utils;
import com.payumoney.sdkui.ui.widgets.StickyHeaderIndex;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WalletListFragment extends DialogFragment implements SearchContentChangeListener {
    public static final String TAG = "WalletListFragment $ ";
    /* access modifiers changed from: private */
    public RecyclerView a;
    private String b = PPConstants.TRANS_QUICK_PAY;
    private List<PaymentEntity> c;
    /* access modifiers changed from: private */
    public IRecyclerViewOnItemClickListener d;
    private boolean e;
    private StickyHeaderIndex f;
    /* access modifiers changed from: private */
    public SearchView g;
    /* access modifiers changed from: private */
    public RecyclerViewAdapter h;
    private ImageView i;
    private RelativeLayout j;
    /* access modifiers changed from: private */
    public RecyclerViewOnItemClickListener k;

    private class CustomComparator implements Comparator<PaymentEntity> {
        private CustomComparator() {
        }

        public int compare(PaymentEntity o1, PaymentEntity o2) {
            return o1.getTitle().compareToIgnoreCase(o2.getTitle());
        }
    }

    public static WalletListFragment newInstance(String transactionType, String addMoneyAmount, boolean isSplitPay, ArrayList<PaymentEntity> walletList) {
        WalletListFragment walletListFragment = new WalletListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PPConstants.ARG_TRANSACTION_TYPE, transactionType);
        bundle.putString(PPConstants.ARG_ADD_MONEY_AMOUNT, addMoneyAmount);
        bundle.putBoolean(PPConstants.ARG_IS_SPLIT_PAY, isSplitPay);
        bundle.putParcelableArrayList(PPConstants.ARG_NET_BANKING_LIST, walletList);
        walletListFragment.setArguments(bundle);
        return walletListFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.b = getArguments().getString(PPConstants.ARG_TRANSACTION_TYPE);
            this.e = getArguments().getBoolean(PPConstants.ARG_IS_SPLIT_PAY);
            this.c = getArguments().getParcelableArrayList(PPConstants.ARG_NET_BANKING_LIST);
        }
    }

    public void setListener(IRecyclerViewOnItemClickListener listener) {
        this.d = listener;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_bank_list, container, false);
        try {
            getDialog().getWindow().requestFeature(1);
        } catch (Exception e2) {
            PPLogger.getInstance().e("DialogLayoutException", e2);
        }
        this.a = (RecyclerView) inflate.findViewById(R.id.enabled_bank_recycler_view);
        this.a.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.g = (SearchView) inflate.findViewById(R.id.bank_filter_search_view);
        this.i = (ImageView) inflate.findViewById(R.id.img_dismiss_dialog);
        this.f = (StickyHeaderIndex) inflate.findViewById(R.id.sticky_index_container);
        this.j = (RelativeLayout) inflate.findViewById(R.id.layout_get_bank_list);
        ((TextView) inflate.findViewById(R.id.textview_dialogfragment_title)).setText("Select Wallet");
        b();
        a();
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            public void run() {
                WalletListFragment.this.g.setIconifiedByDefault(false);
                Utils.showKeyBoard(WalletListFragment.this.getActivity());
            }
        }, 300);
        return inflate;
    }

    private void a() {
        c();
        this.i.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Utils.hideKeyBoard(WalletListFragment.this.getActivity(), WalletListFragment.this.g.getWindowToken());
                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                    public void run() {
                        WalletListFragment.this.dismiss();
                    }
                }, 200);
            }
        });
        this.g.setOnQueryTextListener(new OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                try {
                    if (WalletListFragment.this.h != null) {
                        WalletListFragment.this.h.getFilter().filter(query);
                    }
                } catch (NullPointerException e) {
                    PPLogger.getInstance().e("NullPointerException", (Exception) e);
                }
                return true;
            }

            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    WalletListFragment.this.g.post(new Runnable() {
                        public void run() {
                            WalletListFragment.this.g.clearFocus();
                        }
                    });
                }
                try {
                    if (WalletListFragment.this.h != null) {
                        WalletListFragment.this.h.getFilter().filter(newText);
                    }
                } catch (NullPointerException e) {
                    PPLogger.getInstance().e("NullPointerException", (Exception) e);
                }
                return true;
            }
        });
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void onDetach() {
        super.onDetach();
        this.d = null;
    }

    private void b() {
        this.h = new RecyclerViewAdapter(this.d, this.c, this, PayUmoneyConstants.WALLET_LIST_DIALOG);
        this.a.setAdapter(this.h);
        Collections.sort(this.c, new CustomComparator());
        this.f.setDataSet(a(this.c));
        this.f.setOnScrollListener(this.a);
        this.j.setVisibility(0);
    }

    private char[] a(List<PaymentEntity> list) {
        char[] cArr = new char[list.size()];
        int i2 = 0;
        for (PaymentEntity title : list) {
            cArr[i2] = Character.toUpperCase(title.getTitle().charAt(0));
            i2++;
        }
        return cArr;
    }

    public void onResume() {
        super.onResume();
        try {
            getDialog().getWindow().setLayout(-1, -1);
            getDialog().setOnKeyListener(new OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode != 4) {
                        return false;
                    }
                    WalletListFragment.this.dismiss();
                    return true;
                }
            });
        } catch (Exception e2) {
            PPLogger.getInstance().e("DialogException", e2);
        }
    }

    public void publishSearchResult(List<PaymentEntity> netBankingOptionList) {
        Collections.sort(netBankingOptionList, new CustomComparator());
        this.f.setDataSet(a(netBankingOptionList));
        this.f.getStickyHeaderIndex().getStickyIndex().setVisibility(4);
    }

    private void c() {
        this.k = new RecyclerViewOnItemClickListener(getActivity(), new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                Utils.hideKeyBoard(WalletListFragment.this.getActivity(), WalletListFragment.this.g.getWindowToken());
                PaymentEntity netBankingOption = ((RecyclerViewAdapter) WalletListFragment.this.a.getAdapter()).getNetBankingOption(position);
                if (WalletListFragment.this.d != null) {
                    WalletListFragment.this.d.onItemClickListener(netBankingOption, PayUmoneyConstants.WALLET_LIST_DIALOG);
                }
                WalletListFragment.this.d = null;
                WalletListFragment.this.a.removeOnItemTouchListener(WalletListFragment.this.k);
                WalletListFragment.this.dismiss();
            }
        });
        this.a.addOnItemTouchListener(this.k);
    }
}
