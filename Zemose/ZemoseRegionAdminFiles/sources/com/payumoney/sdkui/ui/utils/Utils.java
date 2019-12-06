package com.payumoney.sdkui.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.bumptech.glide.load.Key;
import com.payumoney.core.PayUmoneySDK;
import com.payumoney.sdkui.R;
import com.payumoney.sdkui.ui.widgets.OtpEditText;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class Utils {
    public static boolean isEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    public static String getProcessedNumber(String cardNumber, String cardType) {
        if (!cardType.equalsIgnoreCase("AMEX")) {
            return cardNumber.replaceAll("....(?!$)", "$0 ");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(cardNumber.substring(0, 4));
        sb.append(" ");
        sb.append(cardNumber.substring(4, 10));
        sb.append(" ");
        sb.append(cardNumber.substring(10, cardNumber.length()));
        return sb.toString();
    }

    public static void hideKeyBoard(Context context, IBinder windowToken) {
        if (context != null) {
            try {
                ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(windowToken, 0);
            } catch (Exception e) {
                PPLogger.getInstance().e("Exception", e);
            }
        }
    }

    public static void showKeyBoard(Context context) {
        if (context != null) {
            ((InputMethodManager) context.getSystemService("input_method")).toggleSoftInput(2, 1);
        }
    }

    public static String getProcessedDisplayAmount(double amount) {
        return new DecimalFormat("#.##").format(amount);
    }

    public static void setCustomBackground(int bgColorCode, View loaderView, Context context) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setColor(bgColorCode);
        gradientDrawable.setCornerRadius((float) getPixelSize(10, context));
        if (VERSION.SDK_INT >= 16) {
            loaderView.setBackground(gradientDrawable);
        } else {
            loaderView.setBackgroundDrawable(gradientDrawable);
        }
    }

    public static int getPixelSize(int dp, Context context) {
        return (int) ((((float) dp) * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void hideKeyBoard(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            View currentFocus = activity.getCurrentFocus();
            if (currentFocus != null) {
                ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 2);
            }
        }
    }

    public static String getTempCardName(String cardName, String cardNumber, Context context) {
        if (cardNumber == null) {
            return cardName;
        }
        String substring = cardNumber.substring(cardNumber.length() - 4);
        return context.getString(R.string.temp_card_name, new Object[]{cardName, substring});
    }

    public static void showKeyBoard(FragmentActivity activity, OtpEditText editText) {
        if (activity != null && !activity.isFinishing()) {
            editText.setFocusableInTouchMode(true);
            editText.setFocusable(true);
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            editText.requestFocus();
            inputMethodManager.showSoftInput(editText, 0);
        }
    }

    public static Drawable getCustomDrawable(Context context, int bgColorCode, boolean isBorderOnly, int strokeWidth, int cornerRadius, boolean isRounded) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        if (isBorderOnly) {
            gradientDrawable.setColor(0);
            gradientDrawable.setStroke(getPixelSize(strokeWidth, context), bgColorCode);
        } else {
            gradientDrawable.setColor(bgColorCode);
        }
        if (isRounded) {
            gradientDrawable.setCornerRadius((float) getPixelSize(cornerRadius, context));
        }
        return gradientDrawable;
    }

    public static StateListDrawable makeSelectorStaticBankItem(Context context, int normalColor) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842913}, getCustomDrawable(context, getPrimaryColor(context), true, 1, 0, false));
        stateListDrawable.addState(new int[]{16842912}, getCustomDrawable(context, getPrimaryColor(context), true, 1, 0, false));
        stateListDrawable.addState(new int[0], getCustomDrawable(context, normalColor, true, 1, 0, false));
        return stateListDrawable;
    }

    public static int getPrimaryColor(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    public static int getColor(Context activity, int colorCode) {
        return ContextCompat.getColor(activity, colorCode);
    }

    public static String loadJSONFromAsset(Context context) {
        try {
            InputStream open = context.getAssets().open("config-ifsc.json");
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return new String(bArr, Key.STRING_CHARSET_NAME);
        } catch (IOException e) {
            PPLogger.getInstance().e("JsonReadException", (Exception) e);
            return null;
        }
    }

    public static boolean checkIfLoggedInOrNitroLoggedIn() {
        if (PayUmoneySDK.getInstance().isUserLoggedIn() || (PayUmoneySDK.getInstance().isNitroEnabled() && PayUmoneySDK.getInstance().isUserAccountActive())) {
            return true;
        }
        return false;
    }

    public static int dpToPixel(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(1, dpValue, context.getResources().getDisplayMetrics());
    }
}
