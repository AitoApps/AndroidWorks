package com.payumoney.sdkui.ui.adapters;

import android.support.annotation.Keep;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.payumoney.sdkui.R;

public class IndexHeaderAdapter extends Adapter<ViewHolder> {
    private char[] a;
    private RowStyle b;

    private static class IndexViewHolder extends ViewHolder {
        TextView a;

        IndexViewHolder(View v) {
            super(v);
            this.a = (TextView) v.findViewById(R.id.sticky_row_index);
        }
    }

    @Keep
    public static class RowStyle {
        Float rowHeigh;
        Float stickyWidth;
        Integer textColor;
        Float textSize;
        Integer textStyle;

        public RowStyle(Float rHeight, Float sWidth, Integer tColor, Float tSize, Integer tStyle) {
            this.rowHeigh = rHeight;
            this.stickyWidth = sWidth;
            this.textColor = tColor;
            this.textSize = tSize;
            this.textStyle = tStyle;
        }

        public Float getStickyWidth() {
            return this.stickyWidth;
        }

        public void setStickyWidth(Float stickyWidth2) {
            this.stickyWidth = stickyWidth2;
        }

        public Float getRowHeigh() {
            return this.rowHeigh;
        }

        public void setRowHeigh(Float rowHeigh2) {
            this.rowHeigh = rowHeigh2;
        }

        public Integer getTextColor() {
            return this.textColor;
        }

        public void setTextColor(Integer textColor2) {
            this.textColor = textColor2;
        }

        public Float getTextSize() {
            return this.textSize;
        }

        public void setTextSize(Float textSize2) {
            this.textSize = textSize2;
        }

        public Integer getTextStyle() {
            return this.textStyle;
        }

        public void setTextStyle(Integer textStyle2) {
            this.textStyle = textStyle2;
        }
    }

    public IndexHeaderAdapter(char[] data2, RowStyle style) {
        this.a = data2;
        this.b = style;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.sticky_row_details, parent, false);
        RowStyle rowStyle = this.b;
        if (rowStyle != null) {
            if (rowStyle.getRowHeigh().floatValue() != -1.0f) {
                LayoutParams layoutParams = inflate.getLayoutParams();
                layoutParams.height = this.b.getRowHeigh().intValue();
                layoutParams.width = this.b.getStickyWidth().intValue();
                inflate.setLayoutParams(layoutParams);
            }
            TextView textView = (TextView) inflate.findViewById(R.id.sticky_row_index);
            if (this.b.getTextColor().intValue() != -1) {
                textView.setTextColor(this.b.getTextColor().intValue());
            }
            if (this.b.getTextSize().intValue() != -1) {
                textView.setTextSize(0, this.b.getTextSize().floatValue());
            }
            if (this.b.getTextStyle().intValue() != -1) {
                textView.setTypeface(null, this.b.getTextStyle().intValue());
            }
        }
        return new IndexViewHolder(inflate);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        IndexViewHolder indexViewHolder = (IndexViewHolder) holder;
        indexViewHolder.a.setText(Character.toString(this.a[position]));
        if (a(position).booleanValue()) {
            indexViewHolder.a.setVisibility(0);
        } else {
            indexViewHolder.a.setVisibility(4);
        }
    }

    private Boolean a(int i) {
        if (i == 0) {
            return Boolean.TRUE;
        }
        char[] cArr = this.a;
        if (a(cArr[i - 1], cArr[i]).booleanValue()) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private Boolean a(char c, char c2) {
        if (Character.toLowerCase(c) == Character.toLowerCase(c2)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public int getItemCount() {
        return this.a.length;
    }

    public void setDataSet(char[] dataSet) {
        this.a = dataSet;
    }
}
