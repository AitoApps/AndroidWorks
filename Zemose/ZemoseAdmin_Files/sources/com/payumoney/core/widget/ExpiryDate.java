package com.payumoney.core.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import com.payumoney.core.utils.Month;
import com.payumoney.core.utils.Year;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ExpiryDate extends AppCompatEditText {
    /* access modifiers changed from: private */
    public String a = "";
    private TextWatcher b = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            String obj = s.toString();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy", Locale.GERMANY);
            try {
                Calendar.getInstance().setTime(simpleDateFormat.parse(obj));
            } catch (ParseException e) {
                if (s.length() != 2 || ExpiryDate.this.a.endsWith("/")) {
                    if (s.length() == 2 && ExpiryDate.this.a.endsWith("/")) {
                        int parseInt = Integer.parseInt(obj.replace("/", ""));
                        if (parseInt > 12 || parseInt <= 0) {
                            ExpiryDate.this.setText("");
                            ExpiryDate expiryDate = ExpiryDate.this;
                            expiryDate.setSelection(expiryDate.getText().toString().length());
                        } else {
                            ExpiryDate expiryDate2 = ExpiryDate.this;
                            expiryDate2.setText(expiryDate2.getText().toString().substring(0, 1));
                            ExpiryDate expiryDate3 = ExpiryDate.this;
                            expiryDate3.setSelection(expiryDate3.getText().toString().length());
                        }
                    } else if (s.length() == 1 && !obj.replace("/", "").isEmpty() && Integer.parseInt(obj.replace("/", "")) > 1) {
                        ExpiryDate expiryDate4 = ExpiryDate.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("0");
                        sb.append(ExpiryDate.this.getText().toString());
                        sb.append("/");
                        expiryDate4.setText(sb.toString());
                        ExpiryDate expiryDate5 = ExpiryDate.this;
                        expiryDate5.setSelection(expiryDate5.getText().toString().length());
                    }
                } else if (!ExpiryDate.this.a.contains("/")) {
                    int parseInt2 = Integer.parseInt(obj.replace("/", ""));
                    if (parseInt2 > 12 || parseInt2 <= 0) {
                        ExpiryDate.this.setText("");
                        ExpiryDate expiryDate6 = ExpiryDate.this;
                        expiryDate6.setSelection(expiryDate6.getText().toString().length());
                    } else {
                        ExpiryDate expiryDate7 = ExpiryDate.this;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(ExpiryDate.this.getText().toString());
                        sb2.append("/");
                        expiryDate7.setText(sb2.toString());
                        ExpiryDate expiryDate8 = ExpiryDate.this;
                        expiryDate8.setSelection(expiryDate8.getText().toString().length());
                    }
                }
                ExpiryDate expiryDate9 = ExpiryDate.this;
                expiryDate9.a = expiryDate9.getText().toString();
            }
        }
    };

    public ExpiryDate(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        a();
    }

    public ExpiryDate(Context context, AttributeSet attrs) {
        super(context, attrs);
        a();
    }

    public ExpiryDate(Context context) {
        super(context);
        a();
    }

    public Month getMonth() {
        String obj = getText().toString();
        if (!TextUtils.isEmpty(obj)) {
            String[] split = obj.split("/");
            if (split.length == 2) {
                return Month.getMonth(split[0]);
            }
        }
        return null;
    }

    public Year getYear() {
        String obj = getText().toString();
        if (!TextUtils.isEmpty(obj)) {
            String[] split = obj.split("/");
            if (split.length == 2) {
                return Year.getYear(split[1]);
            }
        }
        return null;
    }

    private void a() {
        addTextChangedListener(this.b);
    }
}
