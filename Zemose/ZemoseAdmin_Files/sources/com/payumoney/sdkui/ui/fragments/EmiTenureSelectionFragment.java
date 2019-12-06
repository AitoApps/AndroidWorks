package com.payumoney.sdkui.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySDK;
import com.payumoney.core.analytics.LogAnalytics;
import com.payumoney.core.entity.EmiTenure;
import com.payumoney.core.entity.EmiThreshold;
import com.payumoney.core.entity.PaymentEntity;
import com.payumoney.core.entity.PayumoneyConvenienceFee;
import com.payumoney.core.listener.OnEmiInterestReceivedListener;
import com.payumoney.core.utils.AnalyticsConstant;
import com.payumoney.core.utils.SdkHelper;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.presenter.fragmentPresenter.IRecyclerViewOnItemClickListener;
import com.payumoney.sdkui.ui.adapters.EmiTenureRecyclerViewAdapter;
import com.payumoney.sdkui.ui.adapters.EmiTenureRecyclerViewAdapter.OnTenureSelectedListener;
import com.payumoney.sdkui.ui.events.FragmentCallbacks;
import com.payumoney.sdkui.ui.utils.PPConstants;
import com.payumoney.sdkui.ui.utils.ToastUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class EmiTenureSelectionFragment extends BaseFragment implements OnClickListener, OnEmiInterestReceivedListener, IRecyclerViewOnItemClickListener, OnTenureSelectedListener {
    private ArrayList<PaymentEntity> a;
    private ArrayList<EmiThreshold> m;
    private PaymentEntity n;
    private PayumoneyConvenienceFee o;
    private View p;
    private FragmentCallbacks q;
    private EmiTenure r;
    private String s;
    private View t;
    private RecyclerView u;
    private Adapter v;
    private LayoutManager w;

    public static EmiTenureSelectionFragment newInstance(PaymentEntity selectedEmiBank, ArrayList<PaymentEntity> emiBankList, int theme, PayumoneyConvenienceFee convenienceFee, String paymentId, ArrayList<EmiThreshold> emiThresholds) {
        EmiTenureSelectionFragment emiTenureSelectionFragment = new EmiTenureSelectionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("emi_bank_entities", emiBankList);
        bundle.putParcelable("emi_selected_bank", selectedEmiBank);
        bundle.putInt("theme", theme);
        bundle.putParcelable("emi_conv_fee", convenienceFee);
        bundle.putString(AddEmiCardFragment.ARG_PAYMENT_ID, paymentId);
        bundle.putParcelableArrayList("emi_thresholds", emiThresholds);
        emiTenureSelectionFragment.setArguments(bundle);
        return emiTenureSelectionFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.b = (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT);
            this.n = (PaymentEntity) getArguments().getParcelable("emi_selected_bank");
            this.a = getArguments().getParcelableArrayList("emi_bank_entities");
            this.o = (PayumoneyConvenienceFee) getArguments().getParcelable("emi_conv_fee");
            this.s = getArguments().getString(AddEmiCardFragment.ARG_PAYMENT_ID);
            this.m = getArguments().getParcelableArrayList("emi_thresholds");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.t = inflater.inflate(R.layout.emi_tenure_screen, container, false);
        this.u = (RecyclerView) this.t.findViewById(R.id.emi_tenures_recycler_view);
        this.u.setHasFixedSize(true);
        this.p = this.t.findViewById(R.id.btn_emi_tenure_continue);
        this.p.setOnClickListener(this);
        this.w = new LinearLayoutManager(getActivity());
        this.u.setLayoutManager(this.w);
        initConvenieneceFee(this.t);
        setAmount(this.b);
        this.h.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (EmiTenureSelectionFragment.this.h.getText().toString().equalsIgnoreCase("Details")) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.EMI_TENURE_PAGE);
                    hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                    LogAnalytics.logEvent(EmiTenureSelectionFragment.this.getContext(), AnalyticsConstant.SHOW_PAYMENT_DETAILS_CLIKED, hashMap, AnalyticsConstant.CLEVERTAP);
                    EmiTenureSelectionFragment.this.showConvenieneceFee();
                    return;
                }
                HashMap hashMap2 = new HashMap();
                hashMap2.put(AnalyticsConstant.PAGE, AnalyticsConstant.EMI_TENURE_PAGE);
                hashMap2.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                LogAnalytics.logEvent(EmiTenureSelectionFragment.this.getContext(), AnalyticsConstant.HIDE_PAYMENT_DETAILS_CLICKED, hashMap2, AnalyticsConstant.CLEVERTAP);
                EmiTenureSelectionFragment.this.hideConvenieneceFee();
            }
        });
        a();
        return this.t;
    }

    private void a(PaymentEntity paymentEntity) {
        if (paymentEntity != null) {
            ArrayList<PaymentEntity> arrayList = this.a;
            if (arrayList != null && !arrayList.isEmpty()) {
                View findViewById = this.t.findViewById(R.id.emi_selected_bank_name);
                findViewById.setOnClickListener(this);
                ((TextView) findViewById.findViewById(R.id.tv_emi_selected_bank_name)).setText(paymentEntity.getTitle());
                this.v = new EmiTenureRecyclerViewAdapter(paymentEntity, getActivity(), this);
                this.u.setAdapter(this.v);
                updateConvenienceFee(Double.parseDouble(this.b), SdkHelper.getEmiConvenienceFee(this.o, paymentEntity.getCode()));
                this.u.setVisibility(0);
                this.r = null;
                setContinueButtonDisable();
            }
        }
        this.u.setVisibility(8);
        hideConvenienceFeeOption();
        this.r = null;
        setContinueButtonDisable();
    }

    private void a() {
        if (SdkHelper.checkNetwork(getContext())) {
            updateConvenienceFee(Double.parseDouble(this.b), SdkHelper.getEmiConvenienceFee(this.o, this.n.getCode()));
            double d = this.j;
            showProgress(R.string.emi_getting_interests);
            PayUmoneySDK.getInstance().getEmiInterestForBank(this, this.s, d, this.m, "get_emi_interest_for_bank_tag");
            return;
        }
        showNoNetworkError();
        this.u.setVisibility(8);
        setContinueButtonDisable();
    }

    public void showNoNetworkError() {
        if (getActivity() != null && !getActivity().isFinishing() && isAdded()) {
            ToastUtils.showLong((Activity) getActivity(), getString(R.string.no_internet_connection), true);
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCallbacks) {
            this.q = (FragmentCallbacks) context;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void onDetach() {
        super.onDetach();
        this.q = null;
    }

    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.emi_selected_bank_name) {
            navigateToEmiListFragment();
        } else if (id == R.id.btn_emi_tenure_continue) {
            HashMap hashMap = new HashMap();
            hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
            hashMap.put(AnalyticsConstant.BANK_NAME, this.n.getCode());
            hashMap.put(AnalyticsConstant.TENURE, this.r.getTitle());
            hashMap.put(AnalyticsConstant.AMOUNT, Double.valueOf(this.j));
            LogAnalytics.logEvent(getContext(), AnalyticsConstant.EMI_TENURE_SELECTED, hashMap, AnalyticsConstant.CLEVERTAP);
            this.q.navigateTo(AddEmiCardFragment.newInstance(this.s, this.n, this.r, getConvenieneceFee()), 13);
        }
    }

    public void navigateToEmiListFragment() {
        if (getActivity() != null && isAdded() && !getActivity().isFinishing()) {
            ArrayList<PaymentEntity> arrayList = this.a;
            if (!arrayList.isEmpty()) {
                DialogBankListFragment newInstance = DialogBankListFragment.newInstance(PPConstants.TRANS_QUICK_PAY, (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT), false, arrayList, 2);
                newInstance.setListener(this);
                newInstance.show(getFragmentManager(), DialogBankListFragment.TAG);
            }
        }
    }

    public void onItemClickListener(PaymentEntity entity, String tag) {
        if (entity != null && entity.getCode() != null && this.n.getCode() != null && !this.n.getCode().equalsIgnoreCase(entity.getCode())) {
            HashMap hashMap = new HashMap();
            hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.EMI_TENURE_PAGE);
            hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
            hashMap.put(AnalyticsConstant.AMOUNT, Double.valueOf(this.j));
            hashMap.put(AnalyticsConstant.BANK_NAME_PREVIOUS, this.n.getCode());
            hashMap.put(AnalyticsConstant.BANK_NAME, entity.getCode());
            hashMap.put(AnalyticsConstant.SAVED_CARD_USED, "No");
            LogAnalytics.logEvent(getContext(), AnalyticsConstant.EMI_BANK_CHANGED, hashMap, AnalyticsConstant.CLEVERTAP);
            this.n = entity;
            a();
        }
    }

    public void onTenureSelected(EmiTenure tenure) {
        this.r = tenure;
        setContinueButtonEnable();
        StringBuilder sb = new StringBuilder();
        sb.append("onTenureSelected(): ");
        sb.append(tenure);
        Log.d("ETSF", sb.toString());
    }

    public void setContinueButtonDisable() {
        this.p.setEnabled(false);
        this.p.getBackground().setAlpha(120);
    }

    public void setContinueButtonEnable() {
        this.p.setEnabled(true);
        this.p.getBackground().setAlpha(255);
    }

    public void onUpdatedEmiInterestReceived(ArrayList<PaymentEntity> emiBankOptions, String tag) {
        hideProgress();
        if (emiBankOptions != null && !emiBankOptions.isEmpty()) {
            this.a = emiBankOptions;
            Iterator it = emiBankOptions.iterator();
            while (it.hasNext()) {
                PaymentEntity paymentEntity = (PaymentEntity) it.next();
                if (paymentEntity.getCode().equals(this.n.getCode())) {
                    this.n = paymentEntity;
                    a(this.n);
                    return;
                }
            }
        }
    }

    public void onUpdatedEmiInterestFailed(String errorMessage, String tag) {
        hideProgress();
        if (getActivity() != null && !getActivity().isFinishing() && isAdded()) {
            ToastUtils.showLong((Activity) getActivity(), getString(R.string.emi_tenure_failed_to_load), true);
        }
        this.u.setVisibility(8);
        setContinueButtonDisable();
        this.q.popBackStackTillTag("14");
    }

    public void showProgress(int msgString) {
        if (getActivity() != null && !getActivity().isFinishing() && isAdded()) {
            this.q.showProgressDialog(false, getString(msgString));
        }
    }

    public void hideProgress() {
        if (getActivity() != null && !getActivity().isFinishing() && isAdded()) {
            this.q.dismissProgressDialog();
        }
    }
}
