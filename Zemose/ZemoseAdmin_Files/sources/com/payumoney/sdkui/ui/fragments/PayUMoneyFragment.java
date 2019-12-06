package com.payumoney.sdkui.ui.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySDK;
import com.payumoney.core.analytics.LogAnalytics;
import com.payumoney.core.entity.CardDetail;
import com.payumoney.core.entity.PaymentEntity;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.core.entity.TransactionResponse.TransactionStatus;
import com.payumoney.core.entity.Wallet;
import com.payumoney.core.listener.OnLoginErrorListener;
import com.payumoney.core.listener.OnMultipleCardBinDetailsListener;
import com.payumoney.core.listener.OnPaymentStatusReceivedListener;
import com.payumoney.core.listener.OnUserLoginListener;
import com.payumoney.core.listener.OnValidateVpaListener;
import com.payumoney.core.listener.onUserAccountReceivedListener;
import com.payumoney.core.request.PaymentRequest;
import com.payumoney.core.response.BinDetail;
import com.payumoney.core.response.ErrorResponse;
import com.payumoney.core.response.PayUMoneyLoginResponse;
import com.payumoney.core.response.PaymentOptionDetails;
import com.payumoney.core.response.PayumoneyError;
import com.payumoney.core.response.UserDetail;
import com.payumoney.core.utils.AnalyticsConstant;
import com.payumoney.core.utils.BankCID;
import com.payumoney.core.utils.SdkHelper;
import com.payumoney.core.utils.WalletsCID;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.presenter.fragmentPresenter.ILogoutListener;
import com.payumoney.sdkui.presenter.fragmentPresenter.IRecyclerViewOnItemClickListener;
import com.payumoney.sdkui.ui.activities.PayUmoneyActivity;
import com.payumoney.sdkui.ui.adapters.EmiBanksAdapter;
import com.payumoney.sdkui.ui.adapters.EmiBanksAdapter.EmiBankItemOnSelectListener;
import com.payumoney.sdkui.ui.adapters.QuickPayNetBankingAdapter;
import com.payumoney.sdkui.ui.adapters.QuickPayNetBankingAdapter.QuickPayStaticBankItemListener;
import com.payumoney.sdkui.ui.adapters.SavedCardsPagerAdapter;
import com.payumoney.sdkui.ui.adapters.SavedCardsPagerAdapter.OnCardClickListener;
import com.payumoney.sdkui.ui.adapters.ThirdPartyWalletsAdapter;
import com.payumoney.sdkui.ui.adapters.ThirdPartyWalletsAdapter.ThirdPartyStaticWalletListener;
import com.payumoney.sdkui.ui.adapters.ZoomOutTransformer;
import com.payumoney.sdkui.ui.events.FragmentCallbacks;
import com.payumoney.sdkui.ui.utils.CustomTextWatcher;
import com.payumoney.sdkui.ui.utils.CustomTextWatcherListener;
import com.payumoney.sdkui.ui.utils.PPConstants;
import com.payumoney.sdkui.ui.utils.PPLogger;
import com.payumoney.sdkui.ui.utils.ResultModel;
import com.payumoney.sdkui.ui.utils.ToastUtils;
import com.payumoney.sdkui.ui.utils.Utils;
import com.payumoney.sdkui.ui.widgets.AutoFitRecyclerView;
import com.payumoney.sdkui.ui.widgets.CirclePageIndicator;
import com.payumoney.sdkui.ui.widgets.CustomDrawableTextView;
import com.payumoney.sdkui.ui.widgets.RoundRectTextInputLayout;
import com.payumoney.sdkui.ui.widgets.WrapContentHeightViewPager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class PayUMoneyFragment extends BaseFragment implements OnClickListener, OnMultipleCardBinDetailsListener, OnPaymentStatusReceivedListener, OnUserLoginListener, onUserAccountReceivedListener, IRecyclerViewOnItemClickListener, EmiBankItemOnSelectListener, QuickPayStaticBankItemListener, OnCardClickListener, ThirdPartyStaticWalletListener, CustomTextWatcherListener {
    public static final String EMI_SECTION = "emi_section";
    public static final String LOGIN_DIALOG_TAG = "login_dialog";
    public static final String NET_BANK_SECTION = "saved_banks";
    public static final String SAVED_CARD_SECTION = "saved_Cards";
    public static final String THIRD_PARTY_WALLETS_SECTION = "third_party_wallets_section";
    public static final String UPI_SECTION = "upi_section";
    public static final String WALLET_SECTION = "wallet_section";
    public static PaymentEntity moreBankPaymentEntity = null;
    private LinearLayout A;
    private LinearLayout B;
    private CardView C;
    private TextView D;
    /* access modifiers changed from: private */
    public ExpandableLinearLayout E;
    /* access modifiers changed from: private */
    public ExpandableLinearLayout F;
    /* access modifiers changed from: private */
    public ExpandableLinearLayout G;
    /* access modifiers changed from: private */
    public ExpandableLinearLayout H;
    /* access modifiers changed from: private */
    public ExpandableLinearLayout I;
    private RelativeLayout J;
    private RelativeLayout K;
    private RelativeLayout L;
    private RelativeLayout M;
    private FragmentCallbacks N;
    /* access modifiers changed from: private */
    public TextView O;
    /* access modifiers changed from: private */
    public TextView P;
    private TextView Q;
    /* access modifiers changed from: private */
    public TextView R;
    /* access modifiers changed from: private */
    public TextView S;
    private ImageView T;
    private ImageView U;
    private ImageView V;
    private ImageView W;
    private ImageView X;
    private long Y = 0;
    /* access modifiers changed from: private */
    public Wallet Z;
    /* access modifiers changed from: private */
    public boolean a;
    private ArrayList<PaymentEntity> aA;
    /* access modifiers changed from: private */
    public TextView aB;
    private List<PaymentEntity> aC;
    private List<PaymentEntity> aD;
    private List<PaymentEntity> aE;
    /* access modifiers changed from: private */
    public int aF = 0;
    /* access modifiers changed from: private */
    public boolean aG = true;
    private DialogBankListFragment aH;
    private WalletListFragment aI;
    private OnLoginErrorListener aJ;
    private HashMap<String, BinDetail> aK;
    /* access modifiers changed from: private */
    public TextInputEditText aL;
    /* access modifiers changed from: private */
    public RoundRectTextInputLayout aM;
    private CustomDrawableTextView aN;
    /* access modifiers changed from: private */
    public boolean aO;
    private Comparator<PaymentEntity> aP = new Comparator<PaymentEntity>() {
        public int compare(PaymentEntity e1, PaymentEntity e2) {
            return e1.getTitle().compareTo(e2.getTitle());
        }
    };
    private Comparator<PaymentEntity> aQ = new Comparator<PaymentEntity>() {
        public int compare(PaymentEntity e1, PaymentEntity e2) {
            try {
                return e1.getCode().compareTo(e2.getCode());
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    };
    private TextView aa;
    private CirclePageIndicator ab;
    private WrapContentHeightViewPager ac;
    /* access modifiers changed from: private */
    public boolean ad;
    /* access modifiers changed from: private */
    public List<CardDetail> ae;
    /* access modifiers changed from: private */
    public CardDetail af;
    /* access modifiers changed from: private */
    public int ag = -1;
    /* access modifiers changed from: private */
    public Animation ah;
    /* access modifiers changed from: private */
    public Animation ai;
    /* access modifiers changed from: private */
    public QuickPayNetBankingAdapter aj;
    private EmiBanksAdapter ak;
    private ThirdPartyWalletsAdapter al;
    private int am;
    private RelativeLayout an;
    /* access modifiers changed from: private */
    public AppCompatCheckBox ao;
    private TextView ap;
    /* access modifiers changed from: private */
    public ExpandableRelativeLayout aq;
    private TextView ar;
    /* access modifiers changed from: private */
    public ILogoutListener as;
    /* access modifiers changed from: private */
    public boolean at;
    /* access modifiers changed from: private */
    public double au = 0.0d;
    private boolean av;
    private boolean aw;
    private ArrayList<PaymentEntity> ax;
    private ArrayList<PaymentEntity> ay;
    private ArrayList<PaymentEntity> az;
    /* access modifiers changed from: private */
    public boolean m;
    /* access modifiers changed from: private */
    public boolean n;
    private boolean o;
    private boolean p;
    private boolean q;
    /* access modifiers changed from: private */
    public PaymentOptionDetails r;
    private View s;
    /* access modifiers changed from: private */
    public PaymentEntity t;
    /* access modifiers changed from: private */
    public PaymentEntity u;
    /* access modifiers changed from: private */
    public PaymentEntity v;
    private LinearLayout w;
    private LinearLayout x;
    private LinearLayout y;
    private LinearLayout z;

    class DoneOnEditorActionListener implements OnEditorActionListener {
        DoneOnEditorActionListener() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 6) {
                return false;
            }
            ((InputMethodManager) v.getContext().getSystemService("input_method")).hideSoftInputFromWindow(v.getWindowToken(), 0);
            return true;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    class ToggleListener extends ExpandableLayoutListenerAdapter {
        private TextView b;
        private ImageView c;
        /* access modifiers changed from: private */
        public String d;

        ToggleListener(ImageView arrowImage, String sectionType) {
            this.c = arrowImage;
            this.d = sectionType;
        }

        ToggleListener(TextView tv_show_wallet_details, String sectionType) {
            this.b = tv_show_wallet_details;
            this.d = sectionType;
        }

        /* access modifiers changed from: private */
        public void a() {
            if (PayUMoneyFragment.this.getActivity() != null && !PayUMoneyFragment.this.getActivity().isFinishing()) {
                if (this.d.equals(PayUMoneyFragment.SAVED_CARD_SECTION)) {
                    PayUMoneyFragment.this.O.setText(PayUMoneyFragment.this.getString(R.string.label_netBanking_header));
                    PayUMoneyFragment payUMoneyFragment = PayUMoneyFragment.this;
                    payUMoneyFragment.a(payUMoneyFragment.j);
                } else if (this.d.equals(PayUMoneyFragment.NET_BANK_SECTION)) {
                    PayUMoneyFragment.this.x();
                    PayUMoneyFragment.this.O.setText(PayUMoneyFragment.this.getString(R.string.label_netBanking_rs, Utils.getProcessedDisplayAmount(Double.valueOf(PayUMoneyFragment.this.j).doubleValue())));
                } else if (this.d.equals(PayUMoneyFragment.THIRD_PARTY_WALLETS_SECTION)) {
                    PayUMoneyFragment.this.x();
                    PayUMoneyFragment.this.P.setText(PayUMoneyFragment.this.getString(R.string.label_third_party_wallets_rs, Utils.getProcessedDisplayAmount(Double.valueOf(PayUMoneyFragment.this.j).doubleValue())));
                }
            }
        }

        public void onOpened() {
            if (PayUMoneyFragment.this.isAdded()) {
                TextView textView = this.b;
                if (textView != null) {
                    textView.setText(PayUMoneyFragment.this.getString(R.string.label_hide_details));
                }
            }
            ImageView imageView = this.c;
            if (imageView != null) {
                imageView.startAnimation(PayUMoneyFragment.this.ah);
            }
            new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                public void run() {
                    double d;
                    if (ToggleListener.this.d != null) {
                        if (ToggleListener.this.d.equalsIgnoreCase(PayUMoneyFragment.SAVED_CARD_SECTION)) {
                            PayUMoneyFragment.this.s();
                            PayUMoneyFragment.this.setPaymentButtonEnable();
                            if (PayUMoneyFragment.this.af == null && PayUMoneyFragment.this.ae != null && PayUMoneyFragment.this.ae.size() > 0 && !PayUMoneyFragment.this.ad) {
                                PayUMoneyFragment.this.af = (CardDetail) PayUMoneyFragment.this.ae.get(0);
                            }
                            if (PayUMoneyFragment.this.af != null) {
                                String a2 = PayUMoneyFragment.this.a(PayUMoneyFragment.this.af);
                                if (PayUMoneyFragment.this.b(PayUMoneyFragment.this.af).equalsIgnoreCase("dc")) {
                                    d = SdkHelper.getDCConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), a2, PayUMoneyFragment.this.ao.isChecked(), null);
                                } else {
                                    d = SdkHelper.getCCConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), a2, PayUMoneyFragment.this.ao.isChecked(), null);
                                }
                                PayUMoneyFragment.this.updateConvenienceFee(Double.parseDouble(PayUMoneyFragment.this.b), d);
                                PayUMoneyFragment.this.showConvenienceFeeOption();
                            } else if (PayUMoneyFragment.this.ao.isChecked()) {
                                PayUMoneyFragment.this.updateConvenienceFee(Double.parseDouble(PayUMoneyFragment.this.b), SdkHelper.getWalletConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee()));
                            } else {
                                PayUMoneyFragment.this.hideConvenienceFeeOption();
                                PayUMoneyFragment.this.j = Double.parseDouble(PayUMoneyFragment.this.b);
                                PayUMoneyFragment.this.setDisplayAmount(PayUMoneyFragment.this.j);
                            }
                        } else if (ToggleListener.this.d.equalsIgnoreCase(PayUMoneyFragment.NET_BANK_SECTION)) {
                            PayUMoneyFragment.this.s();
                            PayUMoneyFragment.this.hideConvenieneceFee();
                            if (PayUMoneyFragment.this.t != null) {
                                PayUMoneyFragment.this.setPaymentButtonEnable();
                                PayUMoneyFragment.this.updateConvenienceFee(Double.parseDouble(PayUMoneyFragment.this.b), SdkHelper.getNBConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), PayUMoneyFragment.this.t.getCode(), PayUMoneyFragment.this.ao.isChecked()));
                                PayUMoneyFragment.this.showConvenienceFeeOption();
                            } else {
                                if (PayUMoneyFragment.this.ao.isChecked()) {
                                    PayUMoneyFragment.this.updateConvenienceFee(Double.parseDouble(PayUMoneyFragment.this.b), SdkHelper.getWalletConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee()));
                                } else {
                                    PayUMoneyFragment.this.hideConvenienceFeeOption();
                                    PayUMoneyFragment.this.j = Double.parseDouble(PayUMoneyFragment.this.b);
                                    PayUMoneyFragment.this.setDisplayAmount(PayUMoneyFragment.this.j);
                                }
                                PayUMoneyFragment.this.setPaymentButtonDisable();
                            }
                        } else if (ToggleListener.this.d.equalsIgnoreCase(PayUMoneyFragment.EMI_SECTION)) {
                            if (PayUMoneyFragment.this.ao.isChecked()) {
                                PayUMoneyFragment.this.ao.setChecked(false);
                            }
                            PayUMoneyFragment.this.r();
                            PayUMoneyFragment.this.hideConvenieneceFee();
                            if (PayUMoneyFragment.this.v != null) {
                                PayUMoneyFragment.this.setPaymentButtonEnable();
                                PayUMoneyFragment.this.updateConvenienceFee(Double.parseDouble(PayUMoneyFragment.this.b), SdkHelper.getEmiConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), PayUMoneyFragment.this.v.getCode()));
                                PayUMoneyFragment.this.showConvenienceFeeOption();
                            } else {
                                PayUMoneyFragment.this.hideConvenienceFeeOption();
                                PayUMoneyFragment.this.j = Double.parseDouble(PayUMoneyFragment.this.b);
                                PayUMoneyFragment.this.setDisplayAmount(PayUMoneyFragment.this.j);
                                PayUMoneyFragment.this.setPaymentButtonDisable();
                            }
                            PayUMoneyFragment.this.R.setText(PayUMoneyFragment.this.getString(R.string.label_emi_rs, Utils.getProcessedDisplayAmount(PayUMoneyFragment.this.j)));
                        } else if (ToggleListener.this.d.equalsIgnoreCase(PayUMoneyFragment.UPI_SECTION)) {
                            if (PayUMoneyFragment.this.ao.isChecked()) {
                                PayUMoneyFragment.this.ao.setChecked(false);
                            }
                            if (PayUMoneyFragment.this.aL == null || TextUtils.isEmpty(PayUMoneyFragment.this.aL.getText().toString().trim())) {
                                PayUMoneyFragment.this.setPaymentButtonDisable();
                            } else {
                                PayUMoneyFragment.this.setPaymentButtonEnable();
                            }
                            PayUMoneyFragment.this.r();
                            PayUMoneyFragment.this.updateConvenienceFee(Double.parseDouble(PayUMoneyFragment.this.b), SdkHelper.getUPIConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), PayUmoneyConstants.UPI));
                            PayUMoneyFragment.this.showConvenienceFeeOption();
                            PayUMoneyFragment.this.S.setText(PayUMoneyFragment.this.getString(R.string.label_upi_rs, Utils.getProcessedDisplayAmount(PayUMoneyFragment.this.j)));
                        } else if (ToggleListener.this.d.equalsIgnoreCase(PayUMoneyFragment.THIRD_PARTY_WALLETS_SECTION)) {
                            PayUMoneyFragment.this.hideConvenieneceFee();
                            PayUMoneyFragment.this.s();
                            if (PayUMoneyFragment.this.ao.isChecked()) {
                                PayUMoneyFragment.this.ao.setChecked(false);
                            }
                            if (PayUMoneyFragment.this.u != null) {
                                PayUMoneyFragment.this.setPaymentButtonEnable();
                                PayUMoneyFragment.this.updateConvenienceFee(Double.parseDouble(PayUMoneyFragment.this.b), SdkHelper.getThirdPartyWalletsConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), PayUMoneyFragment.this.u.getCode()));
                                PayUMoneyFragment.this.showConvenienceFeeOption();
                            } else {
                                PayUMoneyFragment.this.hideConvenienceFeeOption();
                                PayUMoneyFragment.this.j = Double.parseDouble(PayUMoneyFragment.this.b);
                                PayUMoneyFragment.this.setDisplayAmount(PayUMoneyFragment.this.j);
                                PayUMoneyFragment.this.setPaymentButtonDisable();
                            }
                        }
                        if (!PayUMoneyFragment.this.n) {
                            ToggleListener.this.a();
                        } else if (PayUMoneyFragment.this.ao.isChecked() && PayUMoneyFragment.this.isWalletSufficientBalance()) {
                            if (ToggleListener.this.d.equalsIgnoreCase(PayUMoneyFragment.SAVED_CARD_SECTION) || ToggleListener.this.d.equalsIgnoreCase(PayUMoneyFragment.NET_BANK_SECTION) || ToggleListener.this.d.equalsIgnoreCase(PayUMoneyFragment.THIRD_PARTY_WALLETS_SECTION) || ToggleListener.this.d.equalsIgnoreCase(PayUMoneyFragment.EMI_SECTION) || ToggleListener.this.d.equalsIgnoreCase(PayUMoneyFragment.UPI_SECTION)) {
                                PayUMoneyFragment.this.aq.collapse();
                                PayUMoneyFragment.this.ao.setChecked(false);
                            }
                            ToggleListener.this.a();
                        } else if (!PayUMoneyFragment.this.ao.isChecked()) {
                            ToggleListener.this.a();
                        } else if (Double.compare(PayUMoneyFragment.this.au, 0.0d) > 0) {
                            if (PayUMoneyFragment.this.Z.getAmount() != 0.0d) {
                                PayUMoneyFragment.this.au = PayUMoneyFragment.this.j - PayUMoneyFragment.this.Z.getAmount();
                                ToggleListener.this.b();
                            } else {
                                ToggleListener.this.a();
                            }
                        }
                    }
                }
            }, 200);
        }

        /* access modifiers changed from: private */
        public void b() {
            if (this.d.equals(PayUMoneyFragment.SAVED_CARD_SECTION)) {
                PayUMoneyFragment.this.O.setText(PayUMoneyFragment.this.getString(R.string.label_netBanking_header));
                PayUMoneyFragment payUMoneyFragment = PayUMoneyFragment.this;
                payUMoneyFragment.a(payUMoneyFragment.au);
            } else if (this.d.equals(PayUMoneyFragment.NET_BANK_SECTION)) {
                PayUMoneyFragment.this.x();
                PayUMoneyFragment.this.O.setText(PayUMoneyFragment.this.getString(R.string.label_netBanking_rs, Utils.getProcessedDisplayAmount(PayUMoneyFragment.this.au)));
            } else if (this.d.equals(PayUMoneyFragment.THIRD_PARTY_WALLETS_SECTION)) {
                PayUMoneyFragment.this.x();
                PayUMoneyFragment.this.P.setText(PayUMoneyFragment.this.getString(R.string.label_third_party_wallets_rs, Utils.getProcessedDisplayAmount(PayUMoneyFragment.this.au)));
            }
        }

        public void onClosed() {
            if (PayUMoneyFragment.this.isAdded()) {
                TextView textView = this.b;
                if (textView != null) {
                    textView.setText(PayUMoneyFragment.this.getString(R.string.label_view_details));
                }
            }
            ImageView imageView = this.c;
            if (imageView != null) {
                imageView.startAnimation(PayUMoneyFragment.this.ai);
            }
            new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                public void run() {
                    if (ToggleListener.this.d == null) {
                        return;
                    }
                    if (ToggleListener.this.d.equals(PayUMoneyFragment.SAVED_CARD_SECTION)) {
                        PayUMoneyFragment.this.x();
                        if (PayUMoneyFragment.this.u()) {
                            return;
                        }
                        if (PayUMoneyFragment.this.ao.isChecked()) {
                            PayUMoneyFragment.this.updateConvenienceFee(Double.parseDouble(PayUMoneyFragment.this.b), SdkHelper.getWalletConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee()));
                            PayUMoneyFragment.this.showConvenienceFeeOption();
                            if (PayUMoneyFragment.this.isWalletSufficientBalance()) {
                                PayUMoneyFragment.this.setPaymentButtonEnable();
                            } else {
                                PayUMoneyFragment.this.setPaymentButtonDisable();
                            }
                        } else {
                            PayUMoneyFragment.this.setPaymentButtonDisable();
                            PayUMoneyFragment.this.hideConvenienceFeeOption();
                        }
                    } else if (ToggleListener.this.d.equals(PayUMoneyFragment.NET_BANK_SECTION)) {
                        if (PayUMoneyFragment.this.aB.getVisibility() == 0) {
                            PayUMoneyFragment.this.aj.setmSelectedItem(-1);
                            PayUMoneyFragment.this.aj.notifyDataSetChanged();
                            PayUMoneyFragment.this.aB.setVisibility(8);
                        }
                        PayUMoneyFragment.this.q();
                        PayUMoneyFragment.this.O.setText(PayUMoneyFragment.this.getString(R.string.label_netBanking_header));
                    } else if (ToggleListener.this.d.equals(PayUMoneyFragment.THIRD_PARTY_WALLETS_SECTION)) {
                        PayUMoneyFragment.this.q();
                        PayUMoneyFragment.this.P.setText(PayUMoneyFragment.this.getString(R.string.label_third_party_wallets_header));
                    } else if (ToggleListener.this.d.equals(PayUMoneyFragment.EMI_SECTION)) {
                        if (!PayUMoneyFragment.this.E.isExpanded()) {
                            PayUMoneyFragment.this.s();
                            if (!PayUMoneyFragment.this.w()) {
                                if (PayUMoneyFragment.this.ao.isChecked()) {
                                    PayUMoneyFragment.this.updateConvenienceFee(Double.parseDouble(PayUMoneyFragment.this.b), SdkHelper.getWalletConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee()));
                                    PayUMoneyFragment.this.showConvenienceFeeOption();
                                    if (PayUMoneyFragment.this.isWalletSufficientBalance()) {
                                        PayUMoneyFragment.this.setPaymentButtonEnable();
                                    } else {
                                        PayUMoneyFragment.this.setPaymentButtonDisable();
                                    }
                                } else {
                                    PayUMoneyFragment.this.setPaymentButtonDisable();
                                    PayUMoneyFragment.this.hideConvenienceFeeOption();
                                }
                            }
                        }
                        PayUMoneyFragment.this.R.setText(PayUMoneyFragment.this.getString(R.string.label_emi_header));
                    } else if (ToggleListener.this.d.equals(PayUMoneyFragment.UPI_SECTION)) {
                        PayUMoneyFragment.this.s();
                        PayUMoneyFragment.this.q();
                        PayUMoneyFragment.this.S.setText(PayUMoneyFragment.this.getString(R.string.label_upi_header));
                    }
                }
            }, 200);
        }
    }

    public static Fragment newInstance(PaymentOptionDetails paymentOptionDetails, HashMap<String, BinDetail> binDetailsMap, int theme) {
        PayUMoneyFragment payUMoneyFragment = new PayUMoneyFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("paymentOption", paymentOptionDetails);
        bundle.putSerializable("binDetailsMap", binDetailsMap);
        bundle.putInt("theme", theme);
        payUMoneyFragment.setArguments(bundle);
        return payUMoneyFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.b = (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT);
            this.r = (PaymentOptionDetails) getArguments().getParcelable("paymentOption");
            this.aK = (HashMap) getArguments().getSerializable("binDetailsMap");
            this.am = getArguments().getInt("theme");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.s = inflater.inflate(R.layout.fragment_pay_umoney, container, false);
        ArrayList<PaymentEntity> netBankingList = this.r.getNetBankingList();
        if (netBankingList == null || netBankingList.isEmpty()) {
            this.m = false;
        } else {
            this.m = true;
            this.az = netBankingList;
            this.ax = this.r.getNetBankingStatusList();
        }
        if (SdkHelper.isUPIPaymentOptionAvailable(this.r)) {
            this.q = true;
        } else {
            this.q = false;
        }
        ArrayList<PaymentEntity> cashCardList = this.r.getCashCardList();
        if (cashCardList == null || cashCardList.isEmpty()) {
            this.o = false;
        } else {
            this.o = true;
            this.ay = cashCardList;
        }
        ArrayList<PaymentEntity> emiList = this.r.getEmiList();
        if (emiList == null || emiList.isEmpty()) {
            this.p = false;
        } else {
            this.aA = emiList;
            this.p = true;
        }
        initConvenieneceFee(this.s);
        initUI(this.s);
        setAmount(this.b);
        this.h.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (PayUMoneyFragment.this.h.getText().toString().equalsIgnoreCase("Details")) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.CHECKOUT);
                    hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                    LogAnalytics.logEvent(PayUMoneyFragment.this.getContext(), AnalyticsConstant.SHOW_PAYMENT_DETAILS_CLIKED, hashMap, AnalyticsConstant.CLEVERTAP);
                    PayUMoneyFragment.this.showConvenieneceFee();
                    return;
                }
                HashMap hashMap2 = new HashMap();
                hashMap2.put(AnalyticsConstant.PAGE, AnalyticsConstant.CHECKOUT);
                hashMap2.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                LogAnalytics.logEvent(PayUMoneyFragment.this.getContext(), AnalyticsConstant.HIDE_PAYMENT_DETAILS_CLICKED, hashMap2, AnalyticsConstant.CLEVERTAP);
                PayUMoneyFragment.this.hideConvenieneceFee();
            }
        });
        this.aN = (CustomDrawableTextView) this.s.findViewById(R.id.btn_pay_quick_pay);
        this.aN.setText(getString(R.string.quick_pay_amount_now));
        this.aN.setClickable(true);
        setPaymentButtonDisable();
        if (this.r.isWalletAvailable()) {
            this.n = true;
        }
        if (!(this.r.getCreditCardList() == null && this.r.getDebitCardList() == null)) {
            this.a = true;
        }
        if (this.a) {
            n();
            y();
            if (this.r.getUserDetails() == null || this.r.getUserDetails().getSaveCardList() == null || this.r.getUserDetails().getSaveCardList().size() <= 0) {
                g();
            } else {
                HashMap hashMap = new HashMap();
                hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                hashMap.put(AnalyticsConstant.NUMBER_OF_CARDS_DISPLAYED, Integer.valueOf(this.r.getUserDetails().getSaveCardList().size()));
                LogAnalytics.logEvent(getContext(), AnalyticsConstant.SAVED_CARD_DISPLAYED, hashMap, AnalyticsConstant.CLEVERTAP);
                this.ae = this.r.getUserDetails().getSaveCardList();
                setSaveCardUI();
            }
            TextView textView = (TextView) this.y.findViewById(R.id.header_credit_debit_section);
            this.av = this.r.getCreditCardList() != null;
            this.aw = this.r.getDebitCardList() != null;
            if (this.a) {
                this.y.setVisibility(0);
            }
            x();
        }
        if (this.m) {
            this.x.setVisibility(0);
        }
        if (this.o) {
            this.z.setVisibility(0);
        }
        if (this.p) {
            this.A.setVisibility(0);
        } else {
            this.A.setVisibility(8);
        }
        if (this.q) {
            this.B.setVisibility(0);
        } else {
            this.B.setVisibility(8);
        }
        if (this.r.getUserDetails() != null && this.r.getUserDetails().getWalletDetails() != null && this.n) {
            this.Z = this.r.getUserDetails().getWalletDetails();
            if (this.Z.getAmount() != 0.0d || Double.parseDouble(this.b) > 1.0d) {
                this.ao.setChecked(true);
                if (isWalletSufficientBalance()) {
                    setPaymentButtonEnable();
                    showConvenienceFeeOption();
                }
                updateConvenienceFee(Double.parseDouble(this.b), SdkHelper.getWalletConvenienceFee(this.r.getConvenienceFee()));
            } else {
                this.ao.setChecked(false);
            }
            p();
            e();
            this.w.setVisibility(0);
        } else if (!this.n) {
            this.w.setVisibility(8);
        } else if (this.r.getUserDetails() == null || this.r.getUserDetails().getWalletDetails() != null) {
            this.ao.setChecked(false);
            this.ar.setText(getResources().getString(R.string.please_login_to_use_your_wallet));
            this.w.setVisibility(0);
        } else {
            this.w.setVisibility(8);
        }
        this.aa.setOnClickListener(this);
        if (!this.m && !this.a && !this.p && this.n && !PayUmoneySDK.getInstance().isUserLoggedIn()) {
            if (!this.r.isNitroEnabled()) {
                this.aa.performClick();
            } else if (this.r.getUserDetails() == null) {
                this.aa.performClick();
            }
        }
        this.aN.setOnClickListener(this);
        initListener();
        if (this.a) {
            if (!this.n || this.Z == null) {
                a();
            } else if (!isWalletSufficientBalance() || !this.ao.isChecked()) {
                a();
            }
        } else if (this.m) {
            if (!this.n || this.Z == null) {
                b();
            } else if (!isWalletSufficientBalance() || !this.ao.isChecked()) {
                b();
            }
        } else if (this.o) {
            if (!this.n || this.Z == null) {
                c();
            } else if (!isWalletSufficientBalance() || !this.ao.isChecked()) {
                c();
            }
        } else if (this.p) {
            if (!this.n || this.Z == null) {
                d();
            } else if (!isWalletSufficientBalance() || !this.ao.isChecked()) {
                d();
            }
        }
        return this.s;
    }

    private void a() {
        this.E.initLayout();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (PayUMoneyFragment.this.getActivity() != null && !PayUMoneyFragment.this.getActivity().isFinishing() && !PayUMoneyFragment.this.aO && !PayUMoneyFragment.this.F.isExpanded() && !PayUMoneyFragment.this.G.isExpanded() && !PayUMoneyFragment.this.aq.isExpanded() && !PayUMoneyFragment.this.H.isExpanded()) {
                    PayUMoneyFragment.this.E.expand();
                }
            }
        }, 1500);
    }

    private void b() {
        this.F.initLayout();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (PayUMoneyFragment.this.getActivity() != null && !PayUMoneyFragment.this.getActivity().isFinishing() && !PayUMoneyFragment.this.aO && !PayUMoneyFragment.this.E.isExpanded() && !PayUMoneyFragment.this.G.isExpanded() && !PayUMoneyFragment.this.aq.isExpanded() && !PayUMoneyFragment.this.H.isExpanded()) {
                    PayUMoneyFragment.this.F.expand();
                }
            }
        }, 1500);
    }

    private void c() {
        this.G.initLayout();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (PayUMoneyFragment.this.getActivity() != null && !PayUMoneyFragment.this.getActivity().isFinishing() && !PayUMoneyFragment.this.aO && !PayUMoneyFragment.this.F.isExpanded() && !PayUMoneyFragment.this.E.isExpanded() && !PayUMoneyFragment.this.aq.isExpanded() && !PayUMoneyFragment.this.H.isExpanded()) {
                    PayUMoneyFragment.this.G.expand();
                }
            }
        }, 1500);
    }

    private void d() {
        this.H.initLayout();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (PayUMoneyFragment.this.getActivity() != null && !PayUMoneyFragment.this.getActivity().isFinishing() && !PayUMoneyFragment.this.aO && !PayUMoneyFragment.this.F.isExpanded() && !PayUMoneyFragment.this.G.isExpanded() && !PayUMoneyFragment.this.aq.isExpanded() && !PayUMoneyFragment.this.E.isExpanded()) {
                    PayUMoneyFragment.this.H.expand();
                }
            }
        }, 1500);
    }

    private void e() {
        TextView textView = this.ap;
        if (textView != null) {
            textView.setText(getString(R.string.label_view_details));
            this.ap.setClickable(true);
        }
        if (Double.parseDouble(this.b) >= 1.0d) {
            a(this.an, 1.0f);
            this.an.setClickable(true);
            this.ao.setClickable(true);
            return;
        }
        this.an.setClickable(false);
        this.ao.setClickable(false);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCallbacks) {
            this.N = (FragmentCallbacks) context;
            if (context instanceof ILogoutListener) {
                this.as = (ILogoutListener) context;
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(context.toString());
            sb.append(" must implement ILogoutListener");
            throw new RuntimeException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(context.toString());
        sb2.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb2.toString());
    }

    public void onDetach() {
        super.onDetach();
        this.N = null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0126, code lost:
        if (a(r1) != false) goto L_0x014c;
     */
    public void onClick(View view) {
        this.aO = true;
        if (view.getId() == R.id.tv_show_wallet_details) {
            if (this.aq.isExpanded()) {
                HashMap hashMap = new HashMap();
                hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                hashMap.put(AnalyticsConstant.PAGE, AnalyticsConstant.CHECKOUT);
                LogAnalytics.logEvent(getContext(), AnalyticsConstant.HIDE_WALLET_DETAILS_CLICKED, hashMap, AnalyticsConstant.CLEVERTAP);
            } else {
                HashMap hashMap2 = new HashMap();
                hashMap2.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                hashMap2.put(AnalyticsConstant.PAGE, AnalyticsConstant.CHECKOUT);
                LogAnalytics.logEvent(getContext(), AnalyticsConstant.SHOW_WALLET_DETAILS_CLICKED, hashMap2, AnalyticsConstant.CLEVERTAP);
            }
            this.aq.toggle();
        } else if (view.getId() == R.id.layout_wallet_view_user_balance_header) {
            this.aq.toggle();
        } else if (view.getId() == R.id.button_login) {
            if (PayUmoneySDK.getInstance().isUserLoggedIn()) {
                showLogoutDialog();
            } else {
                PayUmoneySDK.getInstance().launchLoginScreen(this, getFragmentManager(), this.am, LOGIN_DIALOG_TAG);
            }
        } else if (view.getId() == R.id.btn_pay_quick_pay) {
            if (SystemClock.elapsedRealtime() - this.Y >= 1000) {
                this.Y = SystemClock.elapsedRealtime();
                HashMap hashMap3 = new HashMap();
                hashMap3.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                hashMap3.put(AnalyticsConstant.PAGE, AnalyticsConstant.CHECKOUT);
                LogAnalytics.logEvent(getContext(), AnalyticsConstant.PAY_NOW_BUTTON_CLICKED, hashMap3, AnalyticsConstant.CLEVERTAP);
                if (!this.ao.isChecked() || PayUmoneySDK.getInstance().isUserLoggedIn() || !this.r.isNitroEnabled() || !PayUmoneySDK.getInstance().isUserAccountActive() || this.G.isExpanded() || this.u != null) {
                    if (this.F.isExpanded()) {
                        PaymentEntity paymentEntity = this.t;
                        if (paymentEntity != null) {
                            if (!a(paymentEntity)) {
                                paymentFailAndShowErrorScreen();
                            } else if (!SdkHelper.isCitiNetBankingSelected(this.t)) {
                                makeNetBankingPayment(this.t);
                            } else if (SdkHelper.isBankStatusIsUp(this.t, this.ax)) {
                                addNewCard();
                            }
                        }
                    }
                    if (!this.G.isExpanded() || this.u == null) {
                        if (this.E.isExpanded()) {
                            CardDetail cardDetail = this.af;
                            if (cardDetail != null) {
                                String a2 = a(cardDetail);
                                if (a2.equalsIgnoreCase(PayUmoneyConstants.SMAE)) {
                                    PaymentRequest paymentRequest = new PaymentRequest();
                                    paymentRequest.setPaymentID(this.r.getPaymentID());
                                    paymentRequest.setPg(this.af.getPg());
                                    if (this.ao.isChecked()) {
                                        paymentRequest.setSplitPayment(true);
                                    }
                                    paymentRequest.setConvenienceFee(getConvenieneceFee());
                                    paymentRequest.setCardtoken(this.af.getToken());
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(this.af.getId());
                                    sb.append("");
                                    paymentRequest.setStoreCardId(sb.toString());
                                    paymentRequest.setCardName(this.af.getName());
                                    if (this.af != null) {
                                        paymentRequest.setBankCode(a2);
                                    }
                                    paymentRequest.setPg(this.af.getPg());
                                    paymentRequest.setProcessor(a2);
                                    PayUmoneySDK.getInstance().makePayment(this, paymentRequest, true, getActivity(), GetCvvFragment.MAKE_PAYMENT_API_TAG);
                                } else {
                                    PaymentRequest paymentRequest2 = new PaymentRequest();
                                    paymentRequest2.setPaymentID(this.r.getPaymentID());
                                    paymentRequest2.setPg(this.af.getPg());
                                    if (this.ao.isChecked()) {
                                        paymentRequest2.setSplitPayment(true);
                                    }
                                    paymentRequest2.setConvenienceFee(getConvenieneceFee());
                                    paymentRequest2.setCardtoken(this.af.getToken());
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(this.af.getId());
                                    sb2.append("");
                                    paymentRequest2.setStoreCardId(sb2.toString());
                                    paymentRequest2.setCardName(this.af.getName());
                                    if (this.af != null) {
                                        paymentRequest2.setBankCode(a2);
                                    }
                                    paymentRequest2.setPg(this.af.getPg());
                                    paymentRequest2.setProcessor(a2);
                                    BinDetail binDetail = null;
                                    HashMap<String, BinDetail> hashMap4 = this.aK;
                                    if (hashMap4 != null) {
                                        binDetail = (BinDetail) hashMap4.get(this.af.getNumber().substring(0, 6));
                                    }
                                    GetCvvFragment newInstance = GetCvvFragment.newInstance(paymentRequest2, this.af, binDetail);
                                    newInstance.setConvenienceFee(getConvenieneceFee());
                                    newInstance.setmListener(this);
                                    this.N.navigateTo(newInstance, 11);
                                }
                            }
                        }
                        if (this.E.isExpanded() && (this.ad || this.C.getVisibility() == 0)) {
                            addNewCard();
                        } else if (this.I.isExpanded()) {
                            f();
                        } else if (w()) {
                            a(this.v, this.aA);
                        } else if (this.ao.isChecked()) {
                            if (!isWalletSufficientBalance()) {
                                Toast.makeText(getActivity(), getResources().getString(R.string.toast_insufficient_balance_in_wallet), 0).show();
                            } else if (isDataConnectionAvailable(getActivity())) {
                                PaymentRequest paymentRequest3 = new PaymentRequest();
                                paymentRequest3.setPaymentID(this.r.getPaymentID());
                                paymentRequest3.setPg(PayUmoneyConstants.WALLET);
                                paymentRequest3.setConvenienceFee(getConvenieneceFee());
                                PayUmoneySDK.getInstance().makePayment(this, paymentRequest3, true, getActivity(), "WALLET_PAYMENT_REQUEST_API_TAG");
                            } else {
                                showNoNetworkError();
                            }
                        }
                    } else if (this.ao.isChecked()) {
                        this.ao.setChecked(false);
                    } else {
                        makeThirdPartyWalletPayment(this.u);
                    }
                } else {
                    ValidateWalletFragment newInstance2 = ValidateWalletFragment.newInstance();
                    newInstance2.setConvenienceFee(getConvenieneceFee());
                    newInstance2.setListener(this);
                    this.aJ = newInstance2;
                    if (isWalletSufficientBalance()) {
                        this.N.navigateTo(newInstance2, 6);
                    } else {
                        if (this.F.isExpanded()) {
                            PaymentEntity paymentEntity2 = this.t;
                            if (paymentEntity2 != null) {
                            }
                        }
                        if ((!this.E.isExpanded() || this.af == null) && (!this.E.isExpanded() || (!this.ad && this.C.getVisibility() != 0))) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.toast_insufficient_balance_in_wallet), 0).show();
                        }
                        this.N.navigateTo(newInstance2, 6);
                    }
                }
            }
        } else if (view.getId() == R.id.layout_cardView_header || view.getId() == R.id.saved_card_option_enable || view.getId() == R.id.header_credit_debit_section) {
            if (this.F.isExpanded()) {
                h();
            } else if (this.G.isExpanded()) {
                i();
            } else if (this.H.isExpanded()) {
                k();
            } else if (this.I.isExpanded()) {
                l();
            }
            j();
        } else if (view.getId() == R.id.layout_netBankView_header || view.getId() == R.id.saved_bank_option_enable || view.getId() == R.id.header_net_banking_section) {
            if (this.E.isExpanded()) {
                j();
            } else if (this.G.isExpanded()) {
                i();
            } else if (this.H.isExpanded()) {
                k();
            } else if (this.I.isExpanded()) {
                l();
            }
            h();
        } else if (view.getId() == R.id.layout_emi_header || view.getId() == R.id.emi_option_enable || view.getId() == R.id.header_emi_section) {
            if (this.E.isExpanded()) {
                j();
            } else if (this.G.isExpanded()) {
                i();
            } else if (this.F.isExpanded()) {
                h();
            } else if (this.I.isExpanded()) {
                l();
            }
            k();
        } else if (view.getId() == R.id.layout_third_party_wallets_header || view.getId() == R.id.saved_third_party_wallets_option_enable || view.getId() == R.id.header_third_party_wallets_section) {
            if (this.E.isExpanded()) {
                j();
            } else if (this.F.isExpanded()) {
                h();
            } else if (this.H.isExpanded()) {
                k();
            } else if (this.I.isExpanded()) {
                l();
            }
            i();
        } else if (view.getId() == R.id.layout_upi_header || view.getId() == R.id.upi_option_enable || view.getId() == R.id.header_upi_section) {
            if (this.E.isExpanded()) {
                j();
            } else if (this.F.isExpanded()) {
                h();
            } else if (this.H.isExpanded()) {
                k();
            } else if (this.G.isExpanded()) {
                i();
            }
            l();
        } else if (view.getId() == R.id.add_new_card) {
            HashMap hashMap5 = new HashMap();
            hashMap5.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
            LogAnalytics.logEvent(getContext(), AnalyticsConstant.ADD_CARD_BUTTON_CLICKED, hashMap5, AnalyticsConstant.CLEVERTAP);
            if (!this.ao.isChecked() || PayUmoneySDK.getInstance().isUserLoggedIn() || !this.r.isNitroEnabled() || !PayUmoneySDK.getInstance().isUserAccountActive()) {
                addNewCard();
            } else {
                ValidateWalletFragment newInstance3 = ValidateWalletFragment.newInstance();
                newInstance3.setConvenienceFee(getConvenieneceFee());
                newInstance3.setListener(this);
                this.aJ = newInstance3;
                this.N.navigateTo(newInstance3, 6);
            }
        }
    }

    private void f() {
        TextInputEditText textInputEditText = this.aL;
        final String trim = textInputEditText != null ? textInputEditText.getText().toString().trim() : "";
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        PayUmoneySDK.getInstance().validateVPA(new OnValidateVpaListener() {
            public void onSuccess(boolean isValidVpa, String tag) {
                progressDialog.dismiss();
                if (isValidVpa) {
                    PaymentRequest paymentRequest = new PaymentRequest();
                    paymentRequest.setPaymentID(PayUMoneyFragment.this.r.getPaymentID());
                    paymentRequest.setPg(PayUmoneyConstants.PAYMENT_MODE_UPI);
                    paymentRequest.setBankCode(PayUmoneyConstants.UPI);
                    paymentRequest.setConvenienceFee(PayUMoneyFragment.this.getConvenieneceFee());
                    paymentRequest.setVpa(trim);
                    PayUmoneySDK instance = PayUmoneySDK.getInstance();
                    PayUMoneyFragment payUMoneyFragment = PayUMoneyFragment.this;
                    instance.makePayment(payUMoneyFragment, paymentRequest, false, payUMoneyFragment.getActivity(), "UPI_PAYMENT_REQUEST_API_TAG");
                    return;
                }
                PayUMoneyFragment.this.aM.setErrorEnabled(true);
                PayUMoneyFragment.this.aM.setError(PayUMoneyFragment.this.getResources().getString(R.string.error_incorrect_upi_id));
                PayUMoneyFragment.this.setPaymentButtonDisable();
            }

            public void onFailureResponse(ErrorResponse response, String tag) {
                progressDialog.dismiss();
                PayUMoneyFragment.this.setPaymentButtonDisable();
                PayUMoneyFragment.this.aM.setErrorEnabled(true);
                if (response == null || response.getMessage() == null) {
                    PayUMoneyFragment.this.aM.setError(PayUMoneyFragment.this.getResources().getString(R.string.error_incorrect_upi_id));
                } else {
                    PayUMoneyFragment.this.aM.setError(response.getMessage());
                }
            }
        }, trim, PayUmoneyConstants.PM_VALIDATE_VPA_API_TAG);
    }

    private void a(PaymentEntity paymentEntity, ArrayList<PaymentEntity> arrayList) {
        HashMap hashMap = new HashMap();
        hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        hashMap.put(AnalyticsConstant.BANK_NAME, paymentEntity.getCode());
        hashMap.put(AnalyticsConstant.AMOUNT, Double.valueOf(this.j));
        LogAnalytics.logEvent(getContext(), AnalyticsConstant.EMI_PAYMENT_INITIATED, hashMap, AnalyticsConstant.CLEVERTAP);
        this.N.navigateTo(EmiTenureSelectionFragment.newInstance(paymentEntity, arrayList, this.am, this.r.getConvenienceFee(), this.r.getPaymentID(), this.r.getEmiThresholds()), 14);
    }

    public void showLogoutDialog() {
        new Builder(getActivity()).setMessage((CharSequence) "Do you want to logout").setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                final ProgressDialog progressDialog = new ProgressDialog(PayUMoneyFragment.this.getActivity());
                progressDialog.setMessage("Logging out...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                        PayUmoneySDK.getInstance().logoutUser();
                        if (PayUMoneyFragment.this.as != null) {
                            PayUMoneyFragment.this.as.onLogout();
                        }
                        PayUMoneyFragment.this.getActivity().getSupportFragmentManager().popBackStackImmediate();
                    }
                }, 2000);
            }
        }).setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(false).create().show();
    }

    public void addNewCard() {
        if ((this.ao.isChecked() && isWalletSufficientBalance()) || getActivity() == null) {
            return;
        }
        if ((!isAdded() && this.C.getVisibility() != 0) || getActivity().isFinishing()) {
            return;
        }
        if (this.ao.isChecked()) {
            this.N.navigateTo(AddCardFragment.newInstance(this.r.getCreditCardList(), this.r.getDebitCardList(), true, this.r), 1);
        } else {
            this.N.navigateTo(AddCardFragment.newInstance(this.r.getCreditCardList(), this.r.getDebitCardList(), false, this.r), 1);
        }
    }

    public void initUI(View view) {
        boolean z2;
        boolean z3;
        int i;
        boolean z4;
        int i2;
        this.aa = (TextView) this.s.findViewById(R.id.button_login);
        TextView textView = this.aa;
        textView.setPaintFlags(textView.getPaintFlags() | 8);
        this.w = (LinearLayout) this.s.findViewById(R.id.wallet_layout);
        this.x = (LinearLayout) this.s.findViewById(R.id.net_banking_layout);
        this.z = (LinearLayout) this.s.findViewById(R.id.third_party_wallets_layout);
        this.y = (LinearLayout) this.s.findViewById(R.id.saved_card_layout);
        this.aB = (TextView) this.s.findViewById(R.id.payu_error_text);
        this.A = (LinearLayout) this.s.findViewById(R.id.emi_layout);
        this.B = (LinearLayout) this.s.findViewById(R.id.upi_layout);
        this.aM = (RoundRectTextInputLayout) this.s.findViewById(R.id.etEnterVpaLayout);
        this.aM.setDefaultDrawable(ContextCompat.getDrawable(getContext(), R.drawable.round_rect_grey_border));
        this.aM.setErrorDrawable(ContextCompat.getDrawable(getContext(), R.drawable.round_rect_red_border));
        TextView textView2 = (TextView) this.aM.findViewById(R.id.textinput_error);
        textView2.setLines(2);
        textView2.setPadding(textView2.getPaddingLeft(), Utils.dpToPixel(getContext(), 10.0f), textView2.getPaddingRight(), textView2.getPaddingBottom());
        this.aL = (TextInputEditText) this.s.findViewById(R.id.etEnterVpa);
        TextInputEditText textInputEditText = this.aL;
        textInputEditText.addTextChangedListener(new CustomTextWatcher(textInputEditText, this));
        this.aL.setOnEditorActionListener(new DoneOnEditorActionListener());
        this.aB.setTextColor(Color.parseColor(PayUmoneyConfig.getInstance().getColorPrimaryDark()));
        this.O = (TextView) view.findViewById(R.id.header_net_banking_section);
        this.O.setOnClickListener(this);
        this.P = (TextView) view.findViewById(R.id.header_third_party_wallets_section);
        this.P.setOnClickListener(this);
        this.Q = (TextView) view.findViewById(R.id.header_credit_debit_section);
        this.Q.setOnClickListener(this);
        this.R = (TextView) view.findViewById(R.id.header_emi_section);
        this.R.setOnClickListener(this);
        this.S = (TextView) view.findViewById(R.id.header_upi_section);
        this.S.setOnClickListener(this);
        this.ah = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
        this.ai = AnimationUtils.loadAnimation(getActivity(), R.anim.fadeout);
        this.ah.setFillAfter(true);
        this.ai.setFillAfter(true);
        this.ab = (CirclePageIndicator) view.findViewById(R.id.indicator_pager_saved_card);
        this.D = (TextView) view.findViewById(R.id.add_new_card);
        this.D.setOnClickListener(this);
        this.E = (ExpandableLinearLayout) view.findViewById(R.id.expandableLayout2);
        this.F = (ExpandableLinearLayout) view.findViewById(R.id.expandableLayout3);
        this.G = (ExpandableLinearLayout) view.findViewById(R.id.expandableLayout4);
        this.H = (ExpandableLinearLayout) view.findViewById(R.id.expandableLayout5);
        this.I = (ExpandableLinearLayout) view.findViewById(R.id.expandableLayout6);
        if (this.p) {
            this.H.setVisibility(0);
        } else {
            this.H.setVisibility(8);
        }
        this.J = (RelativeLayout) view.findViewById(R.id.layout_cardView_header);
        this.J.setOnClickListener(this);
        this.K = (RelativeLayout) this.s.findViewById(R.id.layout_netBankView_header);
        this.K.setOnClickListener(this);
        this.L = (RelativeLayout) this.s.findViewById(R.id.layout_third_party_wallets_header);
        this.L.setOnClickListener(this);
        this.M = (RelativeLayout) this.s.findViewById(R.id.layout_upi_header);
        this.M.setOnClickListener(this);
        this.s.findViewById(R.id.layout_emi_header).setOnClickListener(this);
        this.C = (CardView) view.findViewById(R.id.add_new_card_itemView);
        g();
        view.findViewById(R.id.saved_card_option_enable).setOnClickListener(this);
        this.T = (ImageView) view.findViewById(R.id.saved_bank_option_enable);
        this.T.setVisibility(4);
        this.T.setOnClickListener(this);
        this.V = (ImageView) view.findViewById(R.id.saved_third_party_wallets_option_enable);
        this.V.setVisibility(4);
        this.V.setOnClickListener(this);
        this.U = (ImageView) view.findViewById(R.id.saved_card_option_enable);
        this.U.setVisibility(4);
        this.U.setOnClickListener(this);
        this.W = (ImageView) view.findViewById(R.id.emi_option_enable);
        this.W.setVisibility(4);
        this.W.setOnClickListener(this);
        this.X = (ImageView) view.findViewById(R.id.upi_option_enable);
        this.X.setVisibility(4);
        this.X.setOnClickListener(this);
        AutoFitRecyclerView autoFitRecyclerView = (AutoFitRecyclerView) view.findViewById(R.id.recycler_view);
        autoFitRecyclerView.setHasFixedSize(true);
        AutoFitRecyclerView autoFitRecyclerView2 = (AutoFitRecyclerView) view.findViewById(R.id.third_party_wallets_recycler_view);
        autoFitRecyclerView2.setHasFixedSize(true);
        AutoFitRecyclerView autoFitRecyclerView3 = (AutoFitRecyclerView) view.findViewById(R.id.emi_recycler_view);
        autoFitRecyclerView3.setHasFixedSize(true);
        int i3 = 6;
        if (this.m) {
            ArrayList<PaymentEntity> arrayList = this.az;
            if (arrayList != null && arrayList.size() > 0) {
                this.aC = new ArrayList();
                a(BankCID.SBI_BANK, this.aC);
                a(BankCID.HDFC_BANK, this.aC);
                a(BankCID.ICICI_BANK, this.aC);
                a(BankCID.AXIS, this.aC);
                a(BankCID.PNB_RETAIL, this.aC);
                Collections.sort(this.az, this.aP);
                if (this.az.size() == 6) {
                    i2 = 6;
                    z4 = false;
                } else {
                    i2 = this.az.size() <= 5 ? this.az.size() : 5;
                    z4 = this.az.size() > 5;
                }
                if (this.aC.size() < i2) {
                    Iterator it = this.az.iterator();
                    while (it.hasNext()) {
                        PaymentEntity paymentEntity = (PaymentEntity) it.next();
                        if (this.aC.size() >= i2) {
                            break;
                        } else if (!this.aC.contains(paymentEntity)) {
                            if (BankCID.isBankCIDAvailable(paymentEntity.getCode())) {
                                paymentEntity.setCidCode(BankCID.getBankCIDByBankCode(paymentEntity.getCode()).getCID());
                            } else {
                                paymentEntity.setCidCode(null);
                            }
                            this.aC.add(paymentEntity);
                        }
                    }
                }
                this.aj = new QuickPayNetBankingAdapter(getActivity(), this.aC, this, z4);
                autoFitRecyclerView.setAdapter(this.aj);
            }
        }
        if (this.p) {
            ArrayList<PaymentEntity> arrayList2 = this.aA;
            if (arrayList2 != null && !arrayList2.isEmpty()) {
                this.aD = new ArrayList();
                Collections.sort(this.aA, this.aQ);
                if (this.aA.size() == 6) {
                    i = 6;
                    z3 = false;
                } else {
                    i = this.aA.size() <= 5 ? this.aA.size() : 5;
                    z3 = this.aA.size() > 5;
                }
                Iterator it2 = this.aA.iterator();
                while (it2.hasNext()) {
                    PaymentEntity paymentEntity2 = (PaymentEntity) it2.next();
                    if (this.aD.size() >= i) {
                        break;
                    } else if (!this.aD.contains(paymentEntity2)) {
                        this.aD.add(paymentEntity2);
                    }
                }
                this.ak = new EmiBanksAdapter(getActivity(), this.aD, this, z3);
                autoFitRecyclerView3.setAdapter(this.ak);
            }
        }
        if (this.o) {
            ArrayList<PaymentEntity> arrayList3 = this.ay;
            if (arrayList3 != null && arrayList3.size() > 0) {
                this.aE = new ArrayList();
                Collections.sort(this.ay, this.aP);
                if (this.ay.size() == 6) {
                    z2 = false;
                } else {
                    i3 = this.ay.size() <= 5 ? this.ay.size() : 5;
                    z2 = this.ay.size() > 5;
                }
                Iterator it3 = this.ay.iterator();
                while (it3.hasNext()) {
                    PaymentEntity paymentEntity3 = (PaymentEntity) it3.next();
                    if (this.aE.size() >= i3) {
                        break;
                    }
                    if (WalletsCID.isWalletCIDAvailable(paymentEntity3.getCode())) {
                        paymentEntity3.setCidCode(WalletsCID.getWalletCIDByBankCode(paymentEntity3.getCode()).getCID());
                    } else {
                        paymentEntity3.setCidCode(null);
                    }
                    this.aE.add(paymentEntity3);
                }
                this.al = new ThirdPartyWalletsAdapter(getActivity(), this.aE, this, z2);
                autoFitRecyclerView2.setAdapter(this.al);
            }
        }
        this.ac = (WrapContentHeightViewPager) view.findViewById(R.id.viewpager_saved_card);
        this.ac.setBackgroundColor(-1);
        this.ab = (CirclePageIndicator) view.findViewById(R.id.indicator_pager_saved_card);
        this.an = (RelativeLayout) view.findViewById(R.id.layout_wallet_view_user_balance_header);
        this.ao = (AppCompatCheckBox) view.findViewById(R.id.checkbox_citrus_wallet);
        this.ap = (TextView) view.findViewById(R.id.tv_show_wallet_details);
        this.ap.setOnClickListener(this);
        this.aq = (ExpandableRelativeLayout) view.findViewById(R.id.expandableLayout1);
        this.aq.toggle();
        this.ar = (TextView) view.findViewById(R.id.citrus_balance_label);
        this.ao.setSupportButtonTintList(new ColorStateList(new int[][]{new int[]{-16842910}, new int[]{16842910}}, new int[]{-3355444, ((PayUmoneyActivity) getActivity()).getPrimaryColor()}));
        hideConvenienceFeeOption();
    }

    public void setSaveCardUI() {
        SavedCardsPagerAdapter savedCardsPagerAdapter = new SavedCardsPagerAdapter(getActivity(), this.ae, this.aK, this);
        savedCardsPagerAdapter.setItemSelectedCurrentPosition(1);
        this.ac.setAdapter(savedCardsPagerAdapter);
        this.ac.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (PayUMoneyFragment.this.aF == position) {
                    return;
                }
                if (PayUMoneyFragment.this.aG) {
                    PayUMoneyFragment.this.aG = false;
                    return;
                }
                HashMap hashMap = new HashMap();
                hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                LogAnalytics.logEvent(PayUMoneyFragment.this.getContext(), AnalyticsConstant.SAVE_CARDS_SCROLLED, hashMap, AnalyticsConstant.CLEVERTAP);
                PayUMoneyFragment.this.aF = position;
            }

            public void onPageSelected(int position) {
                StringBuilder sb = new StringBuilder();
                sb.append("PUMF.onPageSelected(): ");
                sb.append(position);
                Log.v("PUMF", sb.toString());
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        this.ab.setViewPager(this.ac);
        this.ac.setCurrentItem(1);
        this.ab.setVisibility(0);
        this.y.setVisibility(0);
        this.ad = false;
    }

    private void a(BankCID bankCID, List<PaymentEntity> list) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setTitle(bankCID.getName());
        paymentEntity.setShortTitle(bankCID.getShortName());
        paymentEntity.setCode(bankCID.getBankCode());
        paymentEntity.setCidCode(bankCID.getCID());
        if (this.az.contains(paymentEntity)) {
            list.add(paymentEntity);
        }
    }

    public void initListener() {
        m();
        this.ao.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                PayUMoneyFragment.this.at = isChecked;
                if (!isChecked) {
                    PayUMoneyFragment.this.ao.setText(R.string.label_payumoney_wallet);
                    if (PayUMoneyFragment.this.t()) {
                        PayUMoneyFragment.this.showConvenienceFeeOption();
                        PayUMoneyFragment payUMoneyFragment = PayUMoneyFragment.this;
                        String a2 = payUMoneyFragment.a(payUMoneyFragment.af);
                        if (PayUMoneyFragment.this.af.getPg().equalsIgnoreCase(PayUmoneyConstants.PAYMENT_MODE_DC)) {
                            PayUMoneyFragment payUMoneyFragment2 = PayUMoneyFragment.this;
                            payUMoneyFragment2.updateConvenienceFee(Double.parseDouble(payUMoneyFragment2.b), SdkHelper.getDCConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), a2, false, null));
                            return;
                        }
                        PayUMoneyFragment payUMoneyFragment3 = PayUMoneyFragment.this;
                        payUMoneyFragment3.updateConvenienceFee(Double.parseDouble(payUMoneyFragment3.b), SdkHelper.getCCConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), a2, false, null));
                    } else if (PayUMoneyFragment.this.u()) {
                        PayUMoneyFragment.this.showConvenienceFeeOption();
                        PayUMoneyFragment payUMoneyFragment4 = PayUMoneyFragment.this;
                        payUMoneyFragment4.updateConvenienceFee(Double.parseDouble(payUMoneyFragment4.b), SdkHelper.getNBConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), PayUMoneyFragment.this.t.getCode(), false));
                    } else if (PayUMoneyFragment.this.v()) {
                        PayUMoneyFragment.this.showConvenienceFeeOption();
                        PayUMoneyFragment payUMoneyFragment5 = PayUMoneyFragment.this;
                        payUMoneyFragment5.updateConvenienceFee(Double.parseDouble(payUMoneyFragment5.b), SdkHelper.getThirdPartyWalletsConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), PayUMoneyFragment.this.u.getCode()));
                    } else if (PayUMoneyFragment.this.w()) {
                        PayUMoneyFragment.this.showConvenienceFeeOption();
                        PayUMoneyFragment payUMoneyFragment6 = PayUMoneyFragment.this;
                        payUMoneyFragment6.updateConvenienceFee(Double.parseDouble(payUMoneyFragment6.b), SdkHelper.getEmiConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), PayUMoneyFragment.this.v.getCode()));
                    } else {
                        PayUMoneyFragment.this.hideConvenienceFeeOption();
                    }
                } else if (PayUMoneyFragment.this.isWalletSufficientBalance()) {
                    PayUMoneyFragment.this.setPaymentButtonEnable();
                    if (PayUMoneyFragment.this.E.isExpanded()) {
                        PayUMoneyFragment.this.j();
                    }
                    if (PayUMoneyFragment.this.F.isExpanded()) {
                        PayUMoneyFragment.this.h();
                    }
                    if (PayUMoneyFragment.this.G.isExpanded()) {
                        PayUMoneyFragment.this.i();
                    }
                    if (PayUMoneyFragment.this.H.isExpanded()) {
                        PayUMoneyFragment.this.k();
                    }
                    if (PayUMoneyFragment.this.I.isExpanded()) {
                        PayUMoneyFragment.this.l();
                    }
                    PayUMoneyFragment.this.showConvenienceFeeOption();
                    PayUMoneyFragment payUMoneyFragment7 = PayUMoneyFragment.this;
                    payUMoneyFragment7.updateConvenienceFee(Double.parseDouble(payUMoneyFragment7.b), SdkHelper.getWalletConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee()));
                    PayUMoneyFragment.this.ao.setText(PayUMoneyFragment.this.getString(R.string.label_payu_money_wallet_rs500, Utils.getProcessedDisplayAmount(PayUMoneyFragment.this.j)));
                } else {
                    double walletConvenienceFee = SdkHelper.getWalletConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee());
                    PayUMoneyFragment.this.ao.setText(PayUMoneyFragment.this.getString(R.string.label_payu_money_wallet_rs500, Utils.getProcessedDisplayAmount(PayUMoneyFragment.this.Z.getAmount())));
                    if (PayUMoneyFragment.this.t()) {
                        PayUMoneyFragment.this.showConvenienceFeeOption();
                        PayUMoneyFragment payUMoneyFragment8 = PayUMoneyFragment.this;
                        String a3 = payUMoneyFragment8.a(payUMoneyFragment8.af);
                        if (PayUMoneyFragment.this.af.getPg().equalsIgnoreCase(PayUmoneyConstants.PAYMENT_MODE_DC)) {
                            if (walletConvenienceFee < SdkHelper.getDCConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), a3, true)) {
                                PayUMoneyFragment payUMoneyFragment9 = PayUMoneyFragment.this;
                                payUMoneyFragment9.updateConvenienceFee(Double.parseDouble(payUMoneyFragment9.b), SdkHelper.getDCConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), a3, true));
                                return;
                            }
                            PayUMoneyFragment payUMoneyFragment10 = PayUMoneyFragment.this;
                            payUMoneyFragment10.updateConvenienceFee(Double.parseDouble(payUMoneyFragment10.b), walletConvenienceFee);
                        } else if (walletConvenienceFee < SdkHelper.getCCConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), a3, true, null)) {
                            PayUMoneyFragment payUMoneyFragment11 = PayUMoneyFragment.this;
                            payUMoneyFragment11.updateConvenienceFee(Double.parseDouble(payUMoneyFragment11.b), SdkHelper.getCCConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), a3, true, null));
                        } else {
                            PayUMoneyFragment payUMoneyFragment12 = PayUMoneyFragment.this;
                            payUMoneyFragment12.updateConvenienceFee(Double.parseDouble(payUMoneyFragment12.b), walletConvenienceFee);
                        }
                    } else if (PayUMoneyFragment.this.u()) {
                        PayUMoneyFragment.this.showConvenienceFeeOption();
                        if (walletConvenienceFee < SdkHelper.getNBConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), PayUMoneyFragment.this.t.getCode(), true)) {
                            PayUMoneyFragment payUMoneyFragment13 = PayUMoneyFragment.this;
                            payUMoneyFragment13.updateConvenienceFee(Double.parseDouble(payUMoneyFragment13.b), SdkHelper.getNBConvenienceFee(PayUMoneyFragment.this.r.getConvenienceFee(), PayUMoneyFragment.this.t.getCode(), true));
                            return;
                        }
                        PayUMoneyFragment payUMoneyFragment14 = PayUMoneyFragment.this;
                        payUMoneyFragment14.updateConvenienceFee(Double.parseDouble(payUMoneyFragment14.b), walletConvenienceFee);
                    } else if (PayUMoneyFragment.this.H.isExpanded()) {
                        PayUMoneyFragment.this.k();
                        if (PayUMoneyFragment.this.a) {
                            PayUMoneyFragment.this.j();
                        } else if (PayUMoneyFragment.this.m) {
                            PayUMoneyFragment.this.h();
                        }
                    } else if (PayUMoneyFragment.this.I.isExpanded()) {
                        PayUMoneyFragment.this.l();
                        if (PayUMoneyFragment.this.a) {
                            PayUMoneyFragment.this.j();
                        } else if (PayUMoneyFragment.this.m) {
                            PayUMoneyFragment.this.h();
                        }
                    } else if (PayUMoneyFragment.this.G.isExpanded()) {
                        PayUMoneyFragment.this.i();
                        if (PayUMoneyFragment.this.a) {
                            PayUMoneyFragment.this.j();
                        } else if (PayUMoneyFragment.this.m) {
                            PayUMoneyFragment.this.h();
                        }
                    } else {
                        PayUMoneyFragment payUMoneyFragment15 = PayUMoneyFragment.this;
                        payUMoneyFragment15.updateConvenienceFee(Double.parseDouble(payUMoneyFragment15.b), walletConvenienceFee);
                    }
                }
            }
        });
        this.ao.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PayUMoneyFragment.this.a(((AppCompatCheckBox) view).isChecked());
            }
        });
        this.an.setOnClickListener(this);
        this.ac.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(final int position) {
                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                    public void run() {
                        PayUMoneyFragment.this.onSavedCardClick(position, PayUMoneyFragment.this.ag);
                        PayUMoneyFragment.this.ag = position;
                    }
                }, 200);
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void onSuccessfulLogin(PayUMoneyLoginResponse loginResponse, String tag) {
        if (tag.contains(LOGIN_DIALOG_TAG)) {
            this.aa.setVisibility(0);
            this.aa.setText(getResources().getString(R.string.payumoney_logout));
            if (getActivity() != null && !getActivity().isFinishing()) {
                Toast.makeText(getActivity(), "Successful Login", 1).show();
            }
            PayUmoneySDK.getInstance().getUserAccount(this, this.r.getPaymentID(), "USER_ACCOUNT_DETAILS_API_TAG");
        } else if (tag.contains(PayUmoneyConstants.VALIDATE_WALLET_FOR_NITRO_FLOW)) {
            PaymentEntity paymentEntity = moreBankPaymentEntity;
            if (paymentEntity == null) {
                if (this.F.isExpanded()) {
                    PaymentEntity paymentEntity2 = this.t;
                    if (paymentEntity2 != null) {
                        if (!a(paymentEntity2)) {
                            paymentFailAndShowErrorScreen();
                            return;
                        } else if (!SdkHelper.isCitiNetBankingSelected(this.t)) {
                            makeNetBankingPayment(this.t);
                            return;
                        } else if (SdkHelper.isBankStatusIsUp(this.t, this.ax)) {
                            addNewCard();
                            return;
                        } else {
                            return;
                        }
                    }
                }
                if (!w()) {
                    if (this.E.isExpanded()) {
                        CardDetail cardDetail = this.af;
                        if (cardDetail != null) {
                            String a2 = a(cardDetail);
                            if (a2.equalsIgnoreCase(PayUmoneyConstants.SMAE)) {
                                PaymentRequest paymentRequest = new PaymentRequest();
                                paymentRequest.setPaymentID(this.r.getPaymentID());
                                paymentRequest.setPg(this.af.getPg());
                                if (this.ao.isChecked()) {
                                    paymentRequest.setSplitPayment(true);
                                }
                                paymentRequest.setConvenienceFee(getConvenieneceFee());
                                paymentRequest.setCardtoken(this.af.getToken());
                                StringBuilder sb = new StringBuilder();
                                sb.append(this.af.getId());
                                sb.append("");
                                paymentRequest.setStoreCardId(sb.toString());
                                paymentRequest.setCardName(this.af.getName());
                                if (this.af != null) {
                                    paymentRequest.setBankCode(a2);
                                }
                                paymentRequest.setPg(this.af.getPg());
                                paymentRequest.setProcessor(a2);
                                PayUmoneySDK.getInstance().makePayment(this, paymentRequest, true, getActivity(), GetCvvFragment.MAKE_PAYMENT_API_TAG);
                                return;
                            }
                            PaymentRequest paymentRequest2 = new PaymentRequest();
                            paymentRequest2.setPaymentID(this.r.getPaymentID());
                            paymentRequest2.setPg(this.af.getPg());
                            if (this.ao.isChecked()) {
                                paymentRequest2.setSplitPayment(true);
                            }
                            paymentRequest2.setConvenienceFee(getConvenieneceFee());
                            paymentRequest2.setCardtoken(this.af.getToken());
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(this.af.getId());
                            sb2.append("");
                            paymentRequest2.setStoreCardId(sb2.toString());
                            paymentRequest2.setCardName(this.af.getName());
                            if (this.af != null) {
                                paymentRequest2.setBankCode(a2);
                            }
                            paymentRequest2.setPg(this.af.getPg());
                            paymentRequest2.setProcessor(a2);
                            BinDetail binDetail = null;
                            HashMap<String, BinDetail> hashMap = this.aK;
                            if (hashMap != null) {
                                binDetail = (BinDetail) hashMap.get(this.af.getNumber().substring(0, 6));
                            }
                            GetCvvFragment newInstance = GetCvvFragment.newInstance(paymentRequest2, this.af, binDetail);
                            newInstance.setConvenienceFee(getConvenieneceFee());
                            newInstance.setmListener(this);
                            this.N.navigateTo(newInstance, 11);
                            return;
                        }
                    }
                    if (this.E.isExpanded() && (this.ad || this.C.getVisibility() == 0)) {
                        addNewCard();
                    } else if (!this.ao.isChecked()) {
                    } else {
                        if (!isWalletSufficientBalance()) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.toast_insufficient_balance_in_wallet), 0).show();
                        } else if (isDataConnectionAvailable(getActivity())) {
                            PaymentRequest paymentRequest3 = new PaymentRequest();
                            paymentRequest3.setPaymentID(this.r.getPaymentID());
                            paymentRequest3.setPg(PayUmoneyConstants.WALLET);
                            paymentRequest3.setConvenienceFee(getConvenieneceFee());
                            PayUmoneySDK.getInstance().makePayment(this, paymentRequest3, true, getActivity(), "WALLET_PAYMENT_REQUEST_API_TAG");
                        } else {
                            showNoNetworkError();
                        }
                    }
                }
            } else if (!SdkHelper.isCitiNetBankingSelected(paymentEntity)) {
                showConvenienceFeeOption();
                updateConvenienceFeeNB(moreBankPaymentEntity);
                a(moreBankPaymentEntity, this.aC);
                setPaymentButtonEnable();
                if (getActivity() != null && !getActivity().isFinishing()) {
                    this.N.popBackStackTillTag(String.valueOf(6));
                }
            } else if (this.a) {
                addNewCard();
            } else {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.payu_citi_netbank_error), 0).show();
            }
        }
    }

    public void onDismissLoginDialog() {
    }

    public void onError(String response, String tag) {
        if (getActivity() != null && !getActivity().isFinishing() && tag.equalsIgnoreCase("fetch_multiple_api_tag")) {
            o();
        }
        if (getActivity() != null && !getActivity().isFinishing() && response != null && !response.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), response, 1).show();
        }
        if (getActivity() != null && !getActivity().isFinishing() && tag.contains("PAYMENT_REQUEST_API")) {
            getActivity().finish();
        }
    }

    public void missingParam(String description, String tag) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            if (tag.contains("LOGIN_DIALOG_TAG")) {
                Toast.makeText(getActivity(), "Invalid credentials", 1).show();
            } else if (tag.equals("NB_PAYMENT_REQUEST_API_TAG")) {
                Toast.makeText(getActivity(), description, 1).show();
            }
        }
    }

    public void onFailureResponse(ErrorResponse response, String tag) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            if (tag.contains("PAYMENT_REQUEST_API")) {
                TransactionResponse transactionResponse = new TransactionResponse(TransactionStatus.TRANSACTION_EXPIRY, response.getMessage(), (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get("txnid"));
                transactionResponse.setTransactionDetails("");
                transactionResponse.setPayuResponse("");
                this.N.processAndShowResult(new ResultModel(new PayumoneyError("Transaction Failure"), transactionResponse), false);
            } else if (tag.contains(PayUmoneyConstants.VALIDATE_WALLET_FOR_NITRO_FLOW)) {
                OnLoginErrorListener onLoginErrorListener = this.aJ;
                if (onLoginErrorListener != null) {
                    onLoginErrorListener.onLoginFailed(response.getMessage());
                }
            }
        }
    }

    public void onSuccess(String payUResponse, String merchantResponse, String tag) {
        TransactionResponse transactionResponse = new TransactionResponse(TransactionStatus.SUCCESSFUL, "Transaction Successful", (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get("txnid"));
        transactionResponse.setTransactionDetails(merchantResponse);
        transactionResponse.setPayuResponse(payUResponse);
        this.N.processAndShowResult(new ResultModel(new PayumoneyError("Transaction Successful"), transactionResponse), false);
    }

    public void onFailure(String payUResponse, String merchantResponse, String tag) {
        String str = "Transaction Failed";
        try {
            JSONObject jSONObject = new JSONObject(payUResponse);
            if (jSONObject.has("status") && jSONObject.getInt("status") == 0 && jSONObject.has("result")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("result");
                if (jSONObject2.has("status") && jSONObject2.getString("status").equalsIgnoreCase("failure") && jSONObject2.has("error_Message") && !jSONObject2.getString("error_Message").equalsIgnoreCase("no error")) {
                    str = jSONObject2.getString("error_Message");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TransactionResponse transactionResponse = new TransactionResponse(TransactionStatus.FAILED, str, (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get("txnid"));
        transactionResponse.setTransactionDetails(merchantResponse);
        transactionResponse.setPayuResponse(payUResponse);
        ResultModel resultModel = new ResultModel(new PayumoneyError(str), transactionResponse);
        FragmentCallbacks fragmentCallbacks = this.N;
        if (fragmentCallbacks != null) {
            fragmentCallbacks.processAndShowResult(resultModel, false);
        }
    }

    public void onCancelled(String payUResponse, String tag) {
        String str = "Transaction Canceled";
        try {
            JSONObject jSONObject = new JSONObject(payUResponse);
            if (jSONObject.has("status") && jSONObject.getInt("status") == 0 && jSONObject.has("result")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("result");
                if (jSONObject2.has("status") && jSONObject2.getString("status").equalsIgnoreCase("failure") && jSONObject2.has("error_Message") && !jSONObject2.getString("error_Message").equalsIgnoreCase("no error")) {
                    str = jSONObject2.getString("error_Message");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TransactionResponse transactionResponse = new TransactionResponse(TransactionStatus.CANCELLED, str, (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get("txnid"));
        transactionResponse.setTransactionDetails("");
        transactionResponse.setPayuResponse("");
        ResultModel resultModel = new ResultModel(new PayumoneyError(str), transactionResponse);
        FragmentCallbacks fragmentCallbacks = this.N;
        if (fragmentCallbacks != null) {
            fragmentCallbacks.processAndShowResult(resultModel, false);
        }
    }

    public void onItemClickListener(PaymentEntity entity, String tag) {
        if (tag.equalsIgnoreCase(PayUmoneyConstants.NET_BANKING_LIST_DIALOG)) {
            DialogBankListFragment dialogBankListFragment = this.aH;
            if (dialogBankListFragment != null) {
                dialogBankListFragment.dismissAllowingStateLoss();
            }
            if (SdkHelper.isBankStatusIsUp(entity, this.ax)) {
                this.aB.setVisibility(8);
            } else {
                HashMap hashMap = new HashMap();
                hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
                hashMap.put("bank", entity.getTitle());
                LogAnalytics.logEvent(getContext(), AnalyticsConstant.NB_UNREACHABLE, hashMap, AnalyticsConstant.CLEVERTAP);
                this.aB.setVisibility(0);
            }
            if (this.ao.isChecked() && !PayUmoneySDK.getInstance().isUserLoggedIn() && this.r.isNitroEnabled() && PayUmoneySDK.getInstance().isUserAccountActive()) {
                moreBankPaymentEntity = entity;
                ValidateWalletFragment newInstance = ValidateWalletFragment.newInstance();
                newInstance.setConvenienceFee(getConvenieneceFee());
                newInstance.setListener(this);
                this.aJ = newInstance;
                this.N.navigateTo(newInstance, 6);
            } else if (!SdkHelper.isCitiNetBankingSelected(entity)) {
                a(entity, this.aC);
                if (SdkHelper.isBankStatusIsUp(entity, this.ax)) {
                    this.t = entity;
                    setPaymentButtonEnable();
                    showConvenienceFeeOption();
                    updateConvenienceFeeNB(entity);
                    this.aB.setVisibility(8);
                    return;
                }
                this.t = null;
                setPaymentButtonDisable();
                hideConvenienceFeeOption();
                this.aB.setVisibility(0);
            } else if (this.a) {
                addNewCard();
            } else {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.payu_citi_netbank_error), 0).show();
            }
        } else if (tag.equalsIgnoreCase(PayUmoneyConstants.EMI_BANKING_LIST_DIALOG)) {
            this.v = entity;
            showConvenienceFeeOption();
            updateConvenienceFeeEmi(entity);
            b(entity, this.aD);
            setPaymentButtonEnable();
        } else if (tag.equalsIgnoreCase(PayUmoneyConstants.WALLET_LIST_DIALOG)) {
            WalletListFragment walletListFragment = this.aI;
            if (walletListFragment != null) {
                walletListFragment.dismissAllowingStateLoss();
            }
            this.u = entity;
            showConvenienceFeeOption();
            updateConvenienceFeeThirdPartyWallets(this.u);
            if (WalletsCID.isWalletCIDAvailable(this.u.getCode())) {
                PaymentEntity paymentEntity = this.u;
                paymentEntity.setCidCode(WalletsCID.getWalletCIDByBankCode(paymentEntity.getCode()).getCID());
            } else {
                this.u.setCidCode(null);
            }
            this.aE.add(0, this.u);
            List<PaymentEntity> list = this.aE;
            list.remove(list.size() - 1);
            this.al.setmSelectedItem(0);
            this.al.notifyDataSetChanged();
            setPaymentButtonEnable();
        }
    }

    private void a(PaymentEntity paymentEntity, List<PaymentEntity> list) {
        if (BankCID.isBankCIDAvailable(paymentEntity.getCode())) {
            paymentEntity.setCidCode(BankCID.getBankCIDByBankCode(paymentEntity.getCode()).getCID());
        } else {
            paymentEntity.setCidCode(null);
        }
        list.add(0, paymentEntity);
        list.remove(list.size() - 1);
        this.aj.setmSelectedItem(0);
        this.aj.notifyDataSetChanged();
    }

    private void b(PaymentEntity paymentEntity, List<PaymentEntity> list) {
        if (BankCID.isBankCIDAvailable(paymentEntity.getCode())) {
            paymentEntity.setCidCode(BankCID.getBankCIDByBankCode(paymentEntity.getCode()).getCID());
        } else {
            paymentEntity.setCidCode(null);
        }
        list.add(0, paymentEntity);
        list.remove(list.size() - 1);
        this.ak.setmSelectedItem(0);
        this.ak.notifyDataSetChanged();
    }

    private void g() {
        this.y.setVisibility(8);
        this.ab.setVisibility(4);
        this.C.setVisibility(0);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /* access modifiers changed from: private */
    public void h() {
        this.F.toggle();
    }

    /* access modifiers changed from: private */
    public void i() {
        this.G.toggle();
    }

    /* access modifiers changed from: private */
    public void j() {
        if (this.a) {
            this.E.toggle();
        } else if (this.m) {
            this.F.toggle();
        }
    }

    /* access modifiers changed from: private */
    public void k() {
        this.H.toggle();
    }

    /* access modifiers changed from: private */
    public void l() {
        this.I.toggle();
    }

    private void m() {
        this.aq.setListener(new ToggleListener(this.ap, WALLET_SECTION));
        this.E.setListener(new ToggleListener(this.U, SAVED_CARD_SECTION));
        this.F.setListener(new ToggleListener(this.T, NET_BANK_SECTION));
        this.G.setListener(new ToggleListener(this.V, THIRD_PARTY_WALLETS_SECTION));
        this.H.setListener(new ToggleListener(this.W, EMI_SECTION));
        this.I.setListener(new ToggleListener(this.X, UPI_SECTION));
    }

    public void onBankSelected(PaymentEntity bankCID) {
        if (SdkHelper.isBankStatusIsUp(bankCID, this.ax)) {
            if (this.ax != null) {
                for (int i = 0; i < this.ax.size(); i++) {
                    if (((PaymentEntity) this.ax.get(i)).getCode().equalsIgnoreCase(bankCID.getCode())) {
                        this.t = (PaymentEntity) this.ax.get(i);
                    }
                }
            } else {
                this.t = bankCID;
            }
            setPaymentButtonEnable();
            showConvenienceFeeOption();
            updateConvenienceFeeNB(this.t);
            a(this.ao.isChecked());
            this.aB.setVisibility(8);
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        hashMap.put("bank", bankCID.getTitle());
        LogAnalytics.logEvent(getContext(), AnalyticsConstant.NB_UNREACHABLE, hashMap, AnalyticsConstant.CLEVERTAP);
        setPaymentButtonDisable();
        if (this.ao.isChecked()) {
            updateConvenienceFee(Double.parseDouble(this.b), SdkHelper.getWalletConvenienceFee(this.r.getConvenienceFee()));
        } else {
            hideConvenienceFeeOption();
        }
        a(this.ao.isChecked());
        this.aB.setVisibility(0);
        this.t = null;
    }

    private boolean a(PaymentEntity paymentEntity) {
        for (int i = 0; i < this.az.size(); i++) {
            if (((PaymentEntity) this.az.get(i)).getCode().equalsIgnoreCase(paymentEntity.getCode())) {
                return true;
            }
        }
        return false;
    }

    public void onViewMoreBanksClick() {
        new HashMap().put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        LogAnalytics.logEvent(getContext(), AnalyticsConstant.MORE_NB_BANKS_CLICKED, null, AnalyticsConstant.CLEVERTAP);
        navigateToBankListFragment();
    }

    public void onEmiBankSelected(PaymentEntity emiBankEntity) {
        for (int i = 0; i < this.aA.size(); i++) {
            if (((PaymentEntity) this.aA.get(i)).getCode().equalsIgnoreCase(emiBankEntity.getCode())) {
                setPaymentButtonEnable();
                this.v = (PaymentEntity) this.aA.get(i);
                showConvenienceFeeOption();
                updateConvenienceFeeEmi(this.v);
                a(this.ao.isChecked());
                return;
            }
        }
    }

    public void onViewMoreEmiBanksClicked() {
        HashMap hashMap = new HashMap();
        hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        hashMap.put(AnalyticsConstant.AMOUNT, this.b);
        LogAnalytics.logEvent(getContext(), AnalyticsConstant.MORE_EMI_BANKS_CLICKED, hashMap, AnalyticsConstant.CLEVERTAP);
        navigateToEmiListFragment(false);
    }

    public void navigateToBankListFragment() {
        if (getActivity() != null && isAdded() && !getActivity().isFinishing()) {
            ArrayList arrayList = new ArrayList();
            for (PaymentEntity paymentEntity : this.aC) {
                Iterator it = this.az.iterator();
                while (it.hasNext()) {
                    PaymentEntity paymentEntity2 = (PaymentEntity) it.next();
                    if (paymentEntity.getCode().equalsIgnoreCase(paymentEntity2.getCode())) {
                        paymentEntity2.setCidCode(paymentEntity.getCidCode());
                        paymentEntity2.setShortTitle(paymentEntity.getShortTitle());
                        paymentEntity2.setTitle(paymentEntity.getTitle());
                        arrayList.add(paymentEntity2);
                    }
                }
            }
            this.aH = DialogBankListFragment.newInstance(PPConstants.TRANS_QUICK_PAY, (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT), false, b(this.az, (List<PaymentEntity>) arrayList));
            this.aH.setListener(this);
            this.aH.setTargetFragment(this, 4889);
            this.aH.show(getFragmentManager(), DialogBankListFragment.TAG);
            this.aH.setListener(this);
        }
    }

    public void navigateToEmiListFragment(boolean showAllEmiBanksList) {
        ArrayList<PaymentEntity> arrayList;
        if (getActivity() != null && isAdded() && !getActivity().isFinishing()) {
            if (showAllEmiBanksList) {
                arrayList = this.aA;
            } else {
                arrayList = a(this.aA, this.aD);
            }
            if (!arrayList.isEmpty()) {
                Collections.sort(arrayList, this.aQ);
                this.aH = DialogBankListFragment.newInstance(PPConstants.TRANS_QUICK_PAY, (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT), false, arrayList, 2);
                this.aH.setListener(this);
                this.aH.setTargetFragment(this, 4889);
                this.aH.show(getFragmentManager(), DialogBankListFragment.TAG);
                this.aH.setListener(this);
            }
        }
    }

    private ArrayList<PaymentEntity> a(ArrayList<PaymentEntity> arrayList, List<PaymentEntity> list) {
        ArrayList<PaymentEntity> arrayList2 = new ArrayList<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add((PaymentEntity) it.next());
        }
        for (int i = 0; i < list.size(); i++) {
            arrayList2.remove(list.get(i));
        }
        Collections.sort(arrayList2, this.aQ);
        return arrayList2;
    }

    private void n() {
        this.y.setVisibility(8);
        this.C.setVisibility(4);
        this.ab.setVisibility(8);
    }

    private void a(double d, boolean z2) {
        if (this.E.isExpanded()) {
            this.O.setText(getString(R.string.label_netBanking_header));
            a(d);
        } else if (this.F.isExpanded()) {
            x();
            this.O.setText(getString(R.string.label_netBanking_rs, Utils.getProcessedDisplayAmount(d)));
        } else if (this.G.isExpanded()) {
            x();
            this.P.setText(getString(R.string.label_third_party_wallets_rs, Utils.getProcessedDisplayAmount(d)));
        } else if (this.H.isExpanded()) {
            x();
            this.R.setText(getString(R.string.label_emi_rs, Utils.getProcessedDisplayAmount(d)));
        }
    }

    /* access modifiers changed from: private */
    public void a(boolean z2) {
        if (!z2) {
            if (Double.compare(Double.valueOf(this.b).doubleValue(), 0.0d) == 0) {
            }
            if (this.E.isExpanded()) {
                this.O.setText(getString(R.string.label_netBanking_header));
                a(this.j);
            } else if (this.F.isExpanded()) {
                x();
                this.O.setText(getString(R.string.label_netBanking_rs, Utils.getProcessedDisplayAmount(this.j)));
            } else if (this.G.isExpanded()) {
                x();
                this.P.setText(getString(R.string.label_third_party_wallets_rs, Utils.getProcessedDisplayAmount(this.j)));
            } else if (this.H.isExpanded()) {
                x();
                this.R.setText(getString(R.string.label_emi_rs, Utils.getProcessedDisplayAmount(this.j)));
            }
            if (this.aq.isExpanded()) {
                this.aq.toggle();
            }
        } else if (Double.compare(this.j, this.Z.getAmount()) > 0) {
            this.au = this.j - this.Z.getAmount();
            a(this.au, true);
        } else {
            this.O.setText(getString(R.string.label_netBanking_header));
            x();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x012f  */
    /* JADX WARNING: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    public void onSavedCardClick(int itemSelectedPosition, int itemSelectedOldPosition) {
        boolean z2;
        StringBuilder sb = new StringBuilder();
        sb.append("PUMF.onSavedCardClick(): ");
        sb.append(itemSelectedPosition);
        sb.append(", old = ");
        sb.append(itemSelectedOldPosition);
        sb.append(10);
        Log.d("PUMF", sb.toString());
        if (getActivity() != null && isAdded() && !getActivity().isFinishing()) {
            int i = itemSelectedPosition - 1;
            WrapContentHeightViewPager wrapContentHeightViewPager = this.ac;
            if (wrapContentHeightViewPager != null) {
                if (itemSelectedOldPosition != -1) {
                    RelativeLayout relativeLayout = (RelativeLayout) wrapContentHeightViewPager.findViewWithTag(Integer.valueOf(itemSelectedOldPosition));
                    if (relativeLayout != null) {
                        relativeLayout.findViewById(R.id.highlight_view_saved_card).setVisibility(4);
                    }
                }
                RelativeLayout relativeLayout2 = (RelativeLayout) this.ac.findViewWithTag(Integer.valueOf(itemSelectedPosition));
                if (itemSelectedPosition != 0) {
                    List<CardDetail> list = this.ae;
                    if (list != null && !list.isEmpty()) {
                        this.ad = false;
                        this.af = (CardDetail) this.ae.get(i);
                        String a2 = a(this.af);
                        showConvenienceFeeOption();
                        if (b(this.af).equalsIgnoreCase("dc")) {
                            z2 = false;
                        } else {
                            z2 = true;
                        }
                        if (!this.ao.isChecked()) {
                            if (!z2) {
                                updateConvenienceFee(Double.parseDouble(this.b), SdkHelper.getDCConvenienceFee(this.r.getConvenienceFee(), a2, false, null));
                            } else {
                                updateConvenienceFee(Double.parseDouble(this.b), SdkHelper.getCCConvenienceFee(this.r.getConvenienceFee(), a2, false, null));
                            }
                        } else if (!isWalletSufficientBalance()) {
                            if (!z2) {
                                updateConvenienceFee(Double.parseDouble(this.b), SdkHelper.getDCConvenienceFee(this.r.getConvenienceFee(), a2, true, null));
                            } else {
                                updateConvenienceFee(Double.parseDouble(this.b), SdkHelper.getCCConvenienceFee(this.r.getConvenienceFee(), a2, true, null));
                            }
                        }
                        a(this.ao.isChecked());
                        if (relativeLayout2 == null) {
                            relativeLayout2.findViewById(R.id.highlight_view_saved_card).setVisibility(0);
                            return;
                        }
                        return;
                    }
                }
                this.af = null;
                this.ad = true;
                if (this.ao.isChecked()) {
                    updateConvenienceFee(Double.parseDouble(this.b), SdkHelper.getWalletConvenienceFee(this.r.getConvenienceFee()));
                } else {
                    hideConvenienceFeeOption();
                }
                a(this.ao.isChecked());
                if (relativeLayout2 == null) {
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public String a(CardDetail cardDetail) {
        String substring = this.af.getNumber().substring(0, 6);
        HashMap<String, BinDetail> hashMap = this.aK;
        if (hashMap == null || !hashMap.containsKey(substring) || this.aK.get(substring) == null || ((BinDetail) this.aK.get(substring)).getBinOwner() == null || ((BinDetail) this.aK.get(substring)).getBinOwner().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING) || ((BinDetail) this.aK.get(substring)).getBinOwner().isEmpty()) {
            return this.af.getType();
        }
        return ((BinDetail) this.aK.get(substring)).getBinOwner();
    }

    /* access modifiers changed from: private */
    public String b(CardDetail cardDetail) {
        String str;
        String str2 = PayUmoneyConstants.PAYMENT_MODE_CC;
        try {
            String substring = cardDetail.getNumber().substring(0, 6);
            if (this.aK == null || !this.aK.containsKey(substring)) {
                return cardDetail.getPg();
            }
            BinDetail binDetail = (BinDetail) this.aK.get(substring);
            if (binDetail != null && !TextUtils.isEmpty(binDetail.getCategory())) {
                if (!binDetail.getCategory().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING)) {
                    str = binDetail.getCategory();
                    return str;
                }
            }
            str = cardDetail.getPg();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return str2;
        }
    }

    public void onAddCardClick() {
        if (!this.ao.isChecked() || PayUmoneySDK.getInstance().isUserLoggedIn() || !this.r.isNitroEnabled() || !PayUmoneySDK.getInstance().isUserAccountActive()) {
            HashMap hashMap = new HashMap();
            hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
            LogAnalytics.logEvent(getContext(), AnalyticsConstant.ADD_CARD_BUTTON_CLICKED, hashMap, AnalyticsConstant.CLEVERTAP);
            addNewCard();
            return;
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        LogAnalytics.logEvent(getContext(), AnalyticsConstant.ADD_CARD_BUTTON_CLICKED, hashMap2, AnalyticsConstant.CLEVERTAP);
        ValidateWalletFragment newInstance = ValidateWalletFragment.newInstance();
        newInstance.setConvenienceFee(getConvenieneceFee());
        newInstance.setListener(this);
        this.aJ = newInstance;
        this.N.navigateTo(newInstance, 6);
    }

    public void OnUserPaymentDetailsReceived(UserDetail userDetail, String tag) {
        if (!(userDetail == null || userDetail.getSaveCardList() == null || userDetail.getSaveCardList().isEmpty())) {
            this.ae = userDetail.getSaveCardList();
            this.aK = null;
            if (this.a) {
                List<CardDetail> list = this.ae;
                if (list == null || list.size() <= 0) {
                    o();
                } else {
                    ArrayList arrayList = new ArrayList();
                    for (CardDetail number : this.ae) {
                        arrayList.add(number.getNumber());
                    }
                    PayUmoneySDK.getInstance().getMultipleCardBinDetails(this, arrayList, "fetch_multiple_api_tag");
                }
            }
        }
        if (userDetail != null) {
            if ((userDetail.getWalletDetails() != null) && this.n) {
                this.Z = userDetail.getWalletDetails();
                if (this.n) {
                    this.w.setVisibility(0);
                    p();
                    e();
                    if (!isWalletSufficientBalance() && this.a && !this.E.isExpanded()) {
                        j();
                        return;
                    }
                    return;
                }
                return;
            }
        }
        this.w.setVisibility(8);
        if (this.a && !this.E.isExpanded()) {
            j();
        }
    }

    public void onMultipleCardBinDetailsReceived(HashMap<String, BinDetail> binDetailresponse, String tag) {
        if (!getActivity().isFinishing()) {
            this.aK = binDetailresponse;
            o();
        }
    }

    public void onSuccess(String response, String tag) {
    }

    private void o() {
        HashMap hashMap = new HashMap();
        hashMap.put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        hashMap.put(AnalyticsConstant.NUMBER_OF_CARDS_DISPLAYED, Integer.valueOf(this.ae.size()));
        LogAnalytics.logEvent(getContext(), AnalyticsConstant.SAVED_CARD_DISPLAYED, hashMap, AnalyticsConstant.CLEVERTAP);
        n();
        setSaveCardUI();
    }

    private void p() {
        this.an.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.payumoney_white));
        this.ao.setVisibility(0);
        if (Double.parseDouble(this.b) < 1.0d || this.Z.getAmount() < 1.0d) {
            this.ao.setText(getString(R.string.label_payumoney_wallet));
        } else if (isWalletSufficientBalance()) {
            this.ao.setText(getString(R.string.label_payu_money_wallet_rs500, Utils.getProcessedDisplayAmount(this.j)));
        } else {
            this.ao.setText(getString(R.string.label_payu_money_wallet_rs500, Utils.getProcessedDisplayAmount(this.Z.getAmount())));
        }
        this.ar.setText(getString(R.string.pay_u_money_inner_label, Utils.getProcessedDisplayAmount(this.Z.getAmount())));
        this.ap.setVisibility(0);
        this.ap.setClickable(true);
        if (Double.parseDouble(this.b) < 1.0d) {
            this.ao.setClickable(false);
            this.ao.setChecked(false);
            this.ao.setEnabled(false);
        } else if (this.Z.getAmount() >= 1.0d) {
            this.ao.setClickable(true);
            this.ao.setChecked(true);
            this.ao.setEnabled(true);
            a(true);
        } else {
            this.ao.setClickable(false);
            this.ao.setChecked(false);
            this.ao.setEnabled(false);
        }
        this.ao.setTextColor(ViewCompat.MEASURED_STATE_MASK);
    }

    public boolean isWalletSufficientBalance() {
        if (Double.valueOf(this.b).doubleValue() + SdkHelper.getWalletConvenienceFee(this.r.getConvenienceFee()) <= this.Z.getAmount()) {
            return true;
        }
        return false;
    }

    public void makeNetBankingPayment(PaymentEntity paymentEntity) {
        if (isDataConnectionAvailable(getActivity())) {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setPaymentID(this.r.getPaymentID());
            paymentRequest.setPg(PayUmoneyConstants.PAYMENT_MODE_NB);
            paymentRequest.setConvenienceFee(getConvenieneceFee());
            if (this.ao.isChecked()) {
                paymentRequest.setSplitPayment(true);
            }
            if (paymentEntity != null) {
                paymentRequest.setBankCode(paymentEntity.getCode());
            }
            paymentRequest.setPg(PayUmoneyConstants.PAYMENT_MODE_NB);
            PayUmoneySDK.getInstance().makePayment(this, paymentRequest, true, getActivity(), "NB_PAYMENT_REQUEST_API_TAG");
            return;
        }
        showNoNetworkError();
    }

    public void makeThirdPartyWalletPayment(PaymentEntity paymentEntity) {
        if (isDataConnectionAvailable(getActivity())) {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setPaymentID(this.r.getPaymentID());
            paymentRequest.setPg(PayUmoneyConstants.PAYMENT_MODE_CASH_CARD);
            paymentRequest.setConvenienceFee(getConvenieneceFee());
            paymentRequest.setSplitPayment(false);
            if (paymentEntity != null) {
                paymentRequest.setBankCode(paymentEntity.getCode());
            }
            PayUmoneySDK.getInstance().makePayment(this, paymentRequest, true, getActivity(), "NB_PAYMENT_REQUEST_API_TAG");
            return;
        }
        showNoNetworkError();
    }

    public void onStaticWalletSelected(PaymentEntity walletsCID) {
        Iterator it = this.ay.iterator();
        while (it.hasNext()) {
            PaymentEntity paymentEntity = (PaymentEntity) it.next();
            if (walletsCID.getCode().equalsIgnoreCase(paymentEntity.getCode())) {
                setPaymentButtonEnable();
                this.u = paymentEntity;
                showConvenienceFeeOption();
                updateConvenienceFeeThirdPartyWallets(this.u);
                return;
            }
        }
    }

    public void onMoreWalletsSelected() {
        new HashMap().put(AnalyticsConstant.EVENT_SOURCE, "Sdk");
        LogAnalytics.logEvent(getContext(), AnalyticsConstant.MORE_THIRD_PARTY_WALLETS, null, AnalyticsConstant.CLEVERTAP);
        navigateToWalletListFragment();
    }

    public void navigateToWalletListFragment() {
        if (getActivity() != null && isAdded() && !getActivity().isFinishing()) {
            this.aI = WalletListFragment.newInstance(PPConstants.TRANS_QUICK_PAY, (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get(PayUmoneyConstants.AMOUNT), false, b(this.r.getCashCardList(), this.aE));
            this.aI.setListener(this);
            this.aI.setTargetFragment(this, 4889);
            this.aI.show(getFragmentManager(), DialogBankListFragment.TAG);
        }
    }

    public void afterTextChanged(View view, String text) {
        if (view.getId() == R.id.etEnterVpa) {
            a(text);
        }
    }

    private void a(String str) {
        this.aM.setError(null);
        this.aM.setErrorEnabled(false);
        if (str == null || str.isEmpty()) {
            setPaymentButtonDisable();
        } else {
            setPaymentButtonEnable();
        }
    }

    /* access modifiers changed from: private */
    public void q() {
        if (t()) {
            return;
        }
        if (this.ao.isChecked()) {
            updateConvenienceFee(Double.parseDouble(this.b), SdkHelper.getWalletConvenienceFee(this.r.getConvenienceFee()));
            showConvenienceFeeOption();
            if (isWalletSufficientBalance()) {
                setPaymentButtonEnable();
            } else {
                setPaymentButtonDisable();
            }
        } else {
            setPaymentButtonDisable();
            hideConvenienceFeeOption();
        }
    }

    /* access modifiers changed from: private */
    public void r() {
        this.aN.setText(getString(R.string.btn_continue));
    }

    /* access modifiers changed from: private */
    public void s() {
        this.aN.setText(getString(R.string.quick_pay_amount_now));
    }

    /* access modifiers changed from: private */
    public boolean t() {
        if (this.af == null || !this.E.isExpanded()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean u() {
        if (this.t == null || !this.F.isExpanded()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean v() {
        if (this.u == null || !this.G.isExpanded()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean w() {
        if (this.v == null || !this.H.isExpanded()) {
            return false;
        }
        return true;
    }

    public void updateConvenienceFeeNB(PaymentEntity selectedPaymentEntity) {
        if (!this.ao.isChecked()) {
            updateConvenienceFee(Double.parseDouble(this.b), SdkHelper.getNBConvenienceFee(this.r.getConvenienceFee(), selectedPaymentEntity.getCode(), false));
        } else if (!isWalletSufficientBalance()) {
            updateConvenienceFee(Double.parseDouble(this.b), SdkHelper.getNBConvenienceFee(this.r.getConvenienceFee(), selectedPaymentEntity.getCode(), true));
        }
        a(this.ao.isChecked());
    }

    public void updateConvenienceFeeEmi(PaymentEntity selectedPaymentEntity) {
        updateConvenienceFee(Double.parseDouble(this.b), SdkHelper.getEmiConvenienceFee(this.r.getConvenienceFee(), selectedPaymentEntity.getCode()));
        a(this.ao.isChecked());
    }

    public void updateConvenienceFeeThirdPartyWallets(PaymentEntity selectedPaymentEntity) {
        updateConvenienceFee(Double.parseDouble(this.b), SdkHelper.getThirdPartyWalletsConvenienceFee(this.r.getConvenienceFee(), selectedPaymentEntity.getCode()));
        a(this.ao.isChecked());
    }

    /* access modifiers changed from: private */
    public void a(double d) {
        if (this.av && this.aw) {
            this.Q.setText(getString(R.string.label_credit_debit_rs, Utils.getProcessedDisplayAmount(d)));
        } else if (this.av) {
            this.Q.setText(getString(R.string.label_credit_rs, Utils.getProcessedDisplayAmount(d)));
        } else if (this.aw) {
            this.Q.setText(getString(R.string.label_debit_rs, Utils.getProcessedDisplayAmount(d)));
        }
    }

    /* access modifiers changed from: private */
    public void x() {
        if (this.av && this.aw) {
            this.Q.setText(getString(R.string.label_credit_debit_header));
        } else if (this.av) {
            this.Q.setText(getString(R.string.payu_credit_card));
        } else if (this.aw) {
            this.Q.setText(getString(R.string.payu_debit_card));
        } else {
            this.y.setVisibility(8);
        }
    }

    private void y() {
        try {
            this.ac.setOffscreenPageLimit(3);
            this.ac.setClipChildren(false);
            this.ac.setPageMargin(-5);
            this.ac.setPageTransformer(true, new ZoomOutTransformer());
        } catch (Exception e) {
            PPLogger.getInstance().e("Exception", e);
        }
    }

    public void paymentFailAndShowErrorScreen() {
        TransactionResponse transactionResponse;
        if (!this.t.getTitle().toLowerCase().contains("netbanking")) {
            TransactionStatus transactionStatus = TransactionStatus.TRANSACTION_EXPIRY;
            StringBuilder sb = new StringBuilder();
            sb.append("The merchant does not support ");
            sb.append(this.t.getTitle());
            sb.append(" Netbanking");
            transactionResponse = new TransactionResponse(transactionStatus, sb.toString(), (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get("txnid"));
        } else {
            TransactionStatus transactionStatus2 = TransactionStatus.TRANSACTION_EXPIRY;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("The merchant does not support ");
            sb2.append(this.t.getTitle());
            transactionResponse = new TransactionResponse(transactionStatus2, sb2.toString(), (String) PayUmoneySDK.getInstance().getPaymentParam().getParams().get("txnid"));
        }
        transactionResponse.setTransactionDetails("");
        transactionResponse.setPayuResponse("");
        this.N.processAndShowResult(new ResultModel(new PayumoneyError("Transaction Failure"), transactionResponse), false);
    }

    public boolean isDataConnectionAvailable(Context context) {
        boolean z2 = false;
        if (context == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            z2 = true;
        }
        return z2;
    }

    public void showNoNetworkError() {
        if (getActivity() != null && !getActivity().isFinishing() && isAdded()) {
            ToastUtils.showLong((Activity) getActivity(), getString(R.string.no_internet_connection), true);
        }
    }

    public void setPaymentButtonEnable() {
        this.aN.setEnabled(true);
        this.aN.getBackground().setAlpha(255);
    }

    public void setPaymentButtonDisable() {
        this.aN.setEnabled(false);
        this.aN.getBackground().setAlpha(120);
    }

    private ArrayList<PaymentEntity> b(ArrayList<PaymentEntity> arrayList, List<PaymentEntity> list) {
        ArrayList<PaymentEntity> arrayList2 = new ArrayList<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add((PaymentEntity) it.next());
        }
        for (int i = 0; i < list.size(); i++) {
            arrayList2.remove(list.get(i));
        }
        Collections.sort(arrayList2, this.aP);
        return arrayList2;
    }
}
