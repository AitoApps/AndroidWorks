package com.payumoney.sdkui.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.entity.CardDetail;
import com.payumoney.core.response.BinDetail;
import com.payumoney.core.utils.SdkHelper;
import com.payumoney.graphics.AssetDownloadManager;
import com.payumoney.graphics.AssetsHelper;
import com.payumoney.graphics.BitmapCallBack;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.ui.utils.PPConstants;
import com.payumoney.sdkui.ui.utils.Utils;
import com.payumoney.sdkui.ui.widgets.CustomDrawableTextView;
import java.util.HashMap;
import java.util.List;

public class SavedCardsPagerAdapter extends PagerAdapter {
    private final List<CardDetail> a;
    private final int b;
    private LayoutInflater c;
    /* access modifiers changed from: private */
    public OnCardClickListener d;
    /* access modifiers changed from: private */
    public Context e;
    private HashMap<String, BinDetail> f;
    /* access modifiers changed from: private */
    public int g = -1;
    /* access modifiers changed from: private */
    public int h = -1;

    public interface OnCardClickListener {
        void onAddCardClick();

        void onSavedCardClick(int i, int i2);
    }

    public void setItemSelectedCurrentPosition(int newPosition) {
        this.g = newPosition;
        if (newPosition == 0) {
            this.d.onAddCardClick();
        } else {
            this.d.onSavedCardClick(this.g, this.h);
        }
    }

    public SavedCardsPagerAdapter(Context context, List<CardDetail> savedCardList, HashMap<String, BinDetail> binDetailsMap, OnCardClickListener onCardClickListener) {
        this.e = context;
        this.c = LayoutInflater.from(context);
        this.d = onCardClickListener;
        this.a = savedCardList;
        this.f = binDetailsMap;
        this.b = savedCardList.size() + 1;
    }

    public int getItemPosition(Object object) {
        return -2;
    }

    public int getCount() {
        return this.b;
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        String str;
        String str2;
        String str3;
        String string;
        ViewGroup viewGroup = container;
        final int i = position;
        View inflate = this.c.inflate(R.layout.saved_card_item, viewGroup, false);
        RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.saved_card_highlighter_layout);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.layout_saved_item_add_new_card);
        RelativeLayout relativeLayout2 = (RelativeLayout) inflate.findViewById(R.id.saved_card_inner_layout);
        View findViewById = inflate.findViewById(R.id.highlight_view_saved_card);
        TextView textView = (TextView) inflate.findViewById(R.id.card_cardNumber);
        TextView textView2 = (TextView) inflate.findViewById(R.id.card_bank_name);
        TextView textView3 = (TextView) inflate.findViewById(R.id.card_type);
        final ImageView imageView = (ImageView) inflate.findViewById(R.id.bank_logo);
        CustomDrawableTextView customDrawableTextView = (CustomDrawableTextView) inflate.findViewById(R.id.add_new_card);
        final ImageView imageView2 = (ImageView) inflate.findViewById(R.id.card_cardType_image);
        relativeLayout.setTag(Integer.valueOf(position));
        if (VERSION.SDK_INT >= 16) {
            Context context = this.e;
            findViewById.setBackground(Utils.getCustomDrawable(context, Utils.getPrimaryColor(context), true, 1, 10, true));
        } else {
            Context context2 = this.e;
            findViewById.setBackgroundDrawable(Utils.getCustomDrawable(context2, Utils.getPrimaryColor(context2), true, 1, 10, true));
        }
        if (i == 0) {
            inflate.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    SavedCardsPagerAdapter.this.d.onAddCardClick();
                }
            });
            customDrawableTextView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    SavedCardsPagerAdapter.this.d.onAddCardClick();
                }
            });
            textView2.setText(this.e.getString(R.string.default_bank_name));
            AssetDownloadManager.getInstance().getBankBitmapByBankCode(PPConstants.DEFAULT_BANK_NAME, new BitmapCallBack() {
                public void onBitmapReceived(Bitmap bitmap) {
                    if (!((Activity) SavedCardsPagerAdapter.this.e).isFinishing()) {
                        imageView.setImageDrawable(new BitmapDrawable(SavedCardsPagerAdapter.this.e.getResources(), bitmap));
                    }
                }

                public void onBitmapFailed(Bitmap bitmap) {
                    if (!((Activity) SavedCardsPagerAdapter.this.e).isFinishing()) {
                        imageView.setImageDrawable(new BitmapDrawable(SavedCardsPagerAdapter.this.e.getResources(), bitmap));
                    }
                }
            });
            if (this.a.size() != 0) {
                CardDetail cardDetail = (CardDetail) this.a.get(i);
                textView2.setText(this.e.getString(R.string.default_bank_name));
                textView.setText(Utils.getProcessedNumber(cardDetail.getNumber(), cardDetail.getType()));
                AssetDownloadManager.getInstance().getCardBitmap(AssetsHelper.getCard(SdkHelper.getCardType(cardDetail.getType())), new BitmapCallBack() {
                    public void onBitmapReceived(Bitmap bitmap) {
                        if (!((Activity) SavedCardsPagerAdapter.this.e).isFinishing()) {
                            imageView2.setImageDrawable(new BitmapDrawable(SavedCardsPagerAdapter.this.e.getResources(), bitmap));
                        }
                    }

                    public void onBitmapFailed(Bitmap bitmap) {
                        if (!((Activity) SavedCardsPagerAdapter.this.e).isFinishing()) {
                            imageView2.setImageDrawable(new BitmapDrawable(SavedCardsPagerAdapter.this.e.getResources(), bitmap));
                        }
                    }
                });
            }
            linearLayout.setVisibility(0);
            relativeLayout2.setVisibility(4);
        } else {
            if (this.g == i) {
                findViewById.setVisibility(0);
            }
            inflate.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    SavedCardsPagerAdapter savedCardsPagerAdapter = SavedCardsPagerAdapter.this;
                    savedCardsPagerAdapter.h = savedCardsPagerAdapter.g;
                    SavedCardsPagerAdapter.this.g = i;
                    SavedCardsPagerAdapter.this.d.onSavedCardClick(SavedCardsPagerAdapter.this.g, SavedCardsPagerAdapter.this.h);
                }
            });
            CardDetail cardDetail2 = (CardDetail) this.a.get(i - 1);
            String substring = cardDetail2.getNumber().substring(0, 6);
            HashMap<String, BinDetail> hashMap = this.f;
            if (hashMap == null || hashMap.get(substring) == null) {
                str2 = PPConstants.DEFAULT_BANK_NAME;
                textView2.setText(this.e.getString(R.string.default_bank_name));
                if (cardDetail2.getPg().equalsIgnoreCase("cc")) {
                    string = this.e.getResources().getString(R.string.payu_credit);
                } else {
                    string = this.e.getResources().getString(R.string.payu_debit);
                }
                str = cardDetail2.getType();
            } else {
                if (((BinDetail) this.f.get(substring)).getBankName() == null || ((BinDetail) this.f.get(substring)).getBankName().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING) || ((BinDetail) this.f.get(substring)).getBankName().isEmpty()) {
                    str2 = PPConstants.DEFAULT_BANK_NAME;
                    textView2.setText(this.e.getString(R.string.default_bank_name));
                } else {
                    str2 = ((BinDetail) this.f.get(substring)).getBankName();
                    textView2.setText(str2);
                }
                if (((BinDetail) this.f.get(substring)).getCategory().equalsIgnoreCase("cc")) {
                    str3 = this.e.getResources().getString(R.string.payu_credit);
                } else {
                    str3 = this.e.getResources().getString(R.string.payu_debit);
                }
                if (((BinDetail) this.f.get(substring)).getBinOwner() == null || ((BinDetail) this.f.get(substring)).getBinOwner().equalsIgnoreCase(PayUmoneyConstants.NULL_STRING) || ((BinDetail) this.f.get(substring)).getBinOwner().isEmpty()) {
                    str = cardDetail2.getType();
                } else {
                    str = ((BinDetail) this.f.get(substring)).getBinOwner();
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            sb.append(str3);
            sb.append(")");
            textView3.setText(sb.toString());
            textView.setText(Utils.getProcessedNumber(cardDetail2.getNumber(), str));
            AssetDownloadManager.getInstance().getBankBitmapByBankCode(str2, new BitmapCallBack() {
                public void onBitmapReceived(Bitmap bitmap) {
                    if (!((Activity) SavedCardsPagerAdapter.this.e).isFinishing()) {
                        imageView.setImageDrawable(new BitmapDrawable(SavedCardsPagerAdapter.this.e.getResources(), bitmap));
                    }
                }

                public void onBitmapFailed(Bitmap bitmap) {
                    if (!((Activity) SavedCardsPagerAdapter.this.e).isFinishing()) {
                        imageView.setImageDrawable(new BitmapDrawable(SavedCardsPagerAdapter.this.e.getResources(), bitmap));
                    }
                }
            });
            AssetDownloadManager.getInstance().getCardBitmap(AssetsHelper.getCard(SdkHelper.getCardType(str.toUpperCase())), new BitmapCallBack() {
                public void onBitmapReceived(Bitmap bitmap) {
                    if (!((Activity) SavedCardsPagerAdapter.this.e).isFinishing()) {
                        imageView2.setImageDrawable(new BitmapDrawable(SavedCardsPagerAdapter.this.e.getResources(), bitmap));
                    }
                }

                public void onBitmapFailed(Bitmap bitmap) {
                    if (!((Activity) SavedCardsPagerAdapter.this.e).isFinishing()) {
                        imageView2.setImageDrawable(new BitmapDrawable(SavedCardsPagerAdapter.this.e.getResources(), bitmap));
                    }
                }
            });
            linearLayout.setVisibility(4);
            relativeLayout2.setVisibility(0);
        }
        viewGroup.addView(inflate);
        return inflate;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
