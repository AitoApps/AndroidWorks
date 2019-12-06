package com.payumoney.sdkui.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.entity.Amount;
import com.payumoney.core.entity.TransactionResponse.TransactionStatus;
import com.payumoney.core.utils.SharedPrefsUtils;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.ui.events.FragmentCallbacks;
import com.payumoney.sdkui.ui.utils.PPLogger;
import com.payumoney.sdkui.ui.utils.ResultModel;
import com.payumoney.sdkui.ui.widgets.CustomDrawableTextView;

public class ResultFragment extends Fragment {
    private ResultModel a;
    private FragmentCallbacks b;

    public static ResultFragment newInstance(ResultModel resultModel) {
        ResultFragment resultFragment = new ResultFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("result", resultModel);
        resultFragment.setArguments(bundle);
        return resultFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.a = (ResultModel) getArguments().getParcelable("result");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_result_new, container, false);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.payment_result_image);
        TextView textView = (TextView) inflate.findViewById(R.id.payment_result_text);
        CustomDrawableTextView customDrawableTextView = (CustomDrawableTextView) inflate.findViewById(R.id.btn_continue_shopping);
        String doneButtonText = PayUmoneyConfig.getInstance().getDoneButtonText();
        TextView textView2 = (TextView) inflate.findViewById(R.id.transaction_response_msg);
        if (doneButtonText == null || TextUtils.isEmpty(doneButtonText)) {
            customDrawableTextView.setText(getString(R.string.text_return_to_app_shopping));
        } else {
            customDrawableTextView.setText(PayUmoneyConfig.getInstance().getDoneButtonText());
        }
        if (this.a.getTransactionResponse() == null) {
            imageView.setImageResource(R.drawable.ic_txn_fail);
            textView.setText(getString(R.string.text_payment_failure));
            if (this.a.getError() != null) {
                textView2.setText(this.a.getError().getMessage());
                PPLogger instance = PPLogger.getInstance();
                StringBuilder sb = new StringBuilder();
                sb.append("ResultFragment$ Transaction Error ");
                sb.append(this.a.getError().getMessage());
                instance.d(sb.toString(), new Object[0]);
            }
        } else if (this.a.getTransactionResponse().getTransactionStatus().equals(TransactionStatus.SUCCESSFUL)) {
            imageView.setImageResource(R.drawable.ic_txn_done);
            textView.setText(getString(R.string.text_payment_success));
            Amount amount = new Amount(SharedPrefsUtils.getStringPreference(getActivity().getBaseContext(), "netamount"), "INR");
            textView2.setText(getString(R.string.transaction_response_msg, amount.getValueAsFormattedDouble("#.##")));
        } else if (this.a.getTransactionResponse().getTransactionStatus().equals(TransactionStatus.TRANSACTION_EXPIRY)) {
            imageView.setImageResource(R.drawable.ic_txn_fail);
            textView.setText(getString(R.string.text_payment_failure));
            textView2.setText(this.a.getTransactionResponse().getMessage());
            PPLogger instance2 = PPLogger.getInstance();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("ResultFragment$ Transaction Error ");
            sb2.append(this.a.getTransactionResponse().getMessage());
            instance2.d(sb2.toString(), new Object[0]);
        } else {
            imageView.setImageResource(R.drawable.ic_txn_fail);
            textView.setText(getString(R.string.text_payment_failure));
            textView2.setText(this.a.getError().getMessage());
            PPLogger instance3 = PPLogger.getInstance();
            StringBuilder sb3 = new StringBuilder();
            sb3.append("ResultFragment$ Transaction Error ");
            sb3.append(this.a.getTransactionResponse().getMessage());
            instance3.d(sb3.toString(), new Object[0]);
        }
        customDrawableTextView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ResultFragment.this.getActivity().finish();
            }
        });
        return inflate;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.b = (FragmentCallbacks) context;
        } catch (ClassCastException e) {
            StringBuilder sb = new StringBuilder();
            sb.append(context.toString());
            sb.append(" must implement FragmentCallbacks");
            throw new ClassCastException(sb.toString());
        }
    }

    public void onDetach() {
        super.onDetach();
        this.b = null;
    }
}
