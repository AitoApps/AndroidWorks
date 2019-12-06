package com.payu.custombrowser;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.payu.custombrowser.bean.ReviewOrderData;
import java.util.ArrayList;
import java.util.Iterator;

public class c extends Fragment {
    private ArrayList<ReviewOrderData> a;
    private int b;
    /* access modifiers changed from: private */
    public a c;
    private View d;
    /* access modifiers changed from: private */
    public int e;

    public interface a {
        void a();
    }

    public static c a(ArrayList<ReviewOrderData> arrayList, @LayoutRes int i) {
        c cVar = new c();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("review_order_detail_list", arrayList);
        bundle.putInt("layout_res", i);
        cVar.setArguments(bundle);
        return cVar;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.a = getArguments().getParcelableArrayList("review_order_detail_list");
            this.b = getArguments().getInt("layout_res");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.d = inflater.inflate(R.layout.fragment_review_order, container, false);
        return this.d;
    }

    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.payu_review_order_list);
        if (this.b == -1) {
            ArrayList<ReviewOrderData> arrayList = this.a;
            if (arrayList != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ReviewOrderData reviewOrderData = (ReviewOrderData) it.next();
                    View inflate = getActivity().getLayoutInflater().inflate(R.layout.payu_review_order_list_row, null, false);
                    TextView textView = (TextView) inflate.findViewById(R.id.t_review_order_details_value);
                    ((TextView) inflate.findViewById(R.id.t_review_order_details_key)).setText(reviewOrderData.getKey());
                    textView.setText(reviewOrderData.getValue());
                    linearLayout.addView(inflate);
                }
            }
        } else {
            View inflate2 = getActivity().getLayoutInflater().inflate(this.b, null, false);
            RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.r_payu_review_order);
            relativeLayout.removeAllViews();
            relativeLayout.addView(inflate2);
        }
        a(view);
        view.findViewById(R.id.i_payu_close_review).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                v.setEnabled(false);
                v.setClickable(false);
                c.this.c.a();
                TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) c.this.e);
                translateAnimation.setDuration(500);
                translateAnimation.setFillBefore(false);
                translateAnimation.setFillEnabled(true);
                translateAnimation.setZAdjustment(1);
                view.startAnimation(translateAnimation);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (c.this.getActivity() != null && !c.this.getActivity().isFinishing()) {
                            view.setVisibility(8);
                            FragmentTransaction beginTransaction = c.this.getActivity().getSupportFragmentManager().beginTransaction();
                            beginTransaction.remove(c.this);
                            beginTransaction.commit();
                        }
                    }
                }, 450);
            }
        });
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof a) {
            this.c = (a) context;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnReviewOrderDetailCloseListener");
        throw new RuntimeException(sb.toString());
    }

    public void onDetach() {
        super.onDetach();
        this.c = null;
    }

    private void a(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.heightPixels;
        view.measure(0, 0);
        this.e = (int) (((double) i) * 0.45d);
        view.setLayoutParams(new LayoutParams(-1, this.e));
    }
}
