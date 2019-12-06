package es.dmoral.toasty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({"InflateParams"})
public class Toasty {
    /* access modifiers changed from: private */
    @ColorInt
    public static int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");
    /* access modifiers changed from: private */
    @ColorInt
    public static int ERROR_COLOR = Color.parseColor("#D50000");
    /* access modifiers changed from: private */
    @ColorInt
    public static int INFO_COLOR = Color.parseColor("#3F51B5");
    /* access modifiers changed from: private */
    public static final Typeface LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", 0);
    @ColorInt
    private static int NORMAL_COLOR = Color.parseColor("#353A3E");
    /* access modifiers changed from: private */
    @ColorInt
    public static int SUCCESS_COLOR = Color.parseColor("#388E3C");
    /* access modifiers changed from: private */
    @ColorInt
    public static int WARNING_COLOR = Color.parseColor("#FFA900");
    /* access modifiers changed from: private */
    public static Typeface currentTypeface = LOADED_TOAST_TYPEFACE;
    /* access modifiers changed from: private */
    public static int textSize = 16;
    /* access modifiers changed from: private */
    public static boolean tintIcon = true;

    public static class Config {
        @ColorInt
        private int DEFAULT_TEXT_COLOR = Toasty.DEFAULT_TEXT_COLOR;
        @ColorInt
        private int ERROR_COLOR = Toasty.ERROR_COLOR;
        @ColorInt
        private int INFO_COLOR = Toasty.INFO_COLOR;
        @ColorInt
        private int SUCCESS_COLOR = Toasty.SUCCESS_COLOR;
        @ColorInt
        private int WARNING_COLOR = Toasty.WARNING_COLOR;
        private int textSize = Toasty.textSize;
        private boolean tintIcon = Toasty.tintIcon;
        private Typeface typeface = Toasty.currentTypeface;

        private Config() {
        }

        @CheckResult
        public static Config getInstance() {
            return new Config();
        }

        public static void reset() {
            Toasty.DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");
            Toasty.ERROR_COLOR = Color.parseColor("#D50000");
            Toasty.INFO_COLOR = Color.parseColor("#3F51B5");
            Toasty.SUCCESS_COLOR = Color.parseColor("#388E3C");
            Toasty.WARNING_COLOR = Color.parseColor("#FFA900");
            Toasty.currentTypeface = Toasty.LOADED_TOAST_TYPEFACE;
            Toasty.textSize = 16;
            Toasty.tintIcon = true;
        }

        @CheckResult
        public Config setTextColor(@ColorInt int textColor) {
            this.DEFAULT_TEXT_COLOR = textColor;
            return this;
        }

        @CheckResult
        public Config setErrorColor(@ColorInt int errorColor) {
            this.ERROR_COLOR = errorColor;
            return this;
        }

        @CheckResult
        public Config setInfoColor(@ColorInt int infoColor) {
            this.INFO_COLOR = infoColor;
            return this;
        }

        @CheckResult
        public Config setSuccessColor(@ColorInt int successColor) {
            this.SUCCESS_COLOR = successColor;
            return this;
        }

        @CheckResult
        public Config setWarningColor(@ColorInt int warningColor) {
            this.WARNING_COLOR = warningColor;
            return this;
        }

        @CheckResult
        public Config setToastTypeface(@NonNull Typeface typeface2) {
            this.typeface = typeface2;
            return this;
        }

        @CheckResult
        public Config setTextSize(int sizeInSp) {
            this.textSize = sizeInSp;
            return this;
        }

        @CheckResult
        public Config tintIcon(boolean tintIcon2) {
            this.tintIcon = tintIcon2;
            return this;
        }

        public void apply() {
            Toasty.DEFAULT_TEXT_COLOR = this.DEFAULT_TEXT_COLOR;
            Toasty.ERROR_COLOR = this.ERROR_COLOR;
            Toasty.INFO_COLOR = this.INFO_COLOR;
            Toasty.SUCCESS_COLOR = this.SUCCESS_COLOR;
            Toasty.WARNING_COLOR = this.WARNING_COLOR;
            Toasty.currentTypeface = this.typeface;
            Toasty.textSize = this.textSize;
            Toasty.tintIcon = this.tintIcon;
        }
    }

    private Toasty() {
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message) {
        return normal(context, message, 0, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, Drawable icon) {
        return normal(context, message, 0, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return normal(context, message, duration, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration, Drawable icon) {
        return normal(context, message, duration, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration, Drawable icon, boolean withIcon) {
        return custom(context, message, icon, NORMAL_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message) {
        return warning(context, message, 0, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return warning(context, message, duration, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_error_outline_white_48dp), WARNING_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message) {
        return info(context, message, 0, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return info(context, message, duration, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_info_outline_white_48dp), INFO_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message) {
        return success(context, message, 0, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return success(context, message, duration, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_check_white_48dp), SUCCESS_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message) {
        return error(context, message, 0, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return error(context, message, duration, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_clear_white_48dp), ERROR_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon, int duration, boolean withIcon) {
        return custom(context, message, icon, -1, duration, withIcon, false);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, @DrawableRes int iconRes, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        return custom(context, message, ToastyUtils.getDrawable(context, iconRes), tintColor, duration, withIcon, shouldTint);
    }

    @SuppressLint({"ShowToast"})
    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        Drawable drawableFrame;
        Toast currentToast = Toast.makeText(context, "", duration);
        View toastLayout = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.toast_layout, null);
        ImageView toastIcon = (ImageView) toastLayout.findViewById(R.id.toast_icon);
        TextView toastTextView = (TextView) toastLayout.findViewById(R.id.toast_text);
        if (shouldTint) {
            drawableFrame = ToastyUtils.tint9PatchDrawableFrame(context, tintColor);
        } else {
            drawableFrame = ToastyUtils.getDrawable(context, R.drawable.toast_frame);
        }
        ToastyUtils.setBackground(toastLayout, drawableFrame);
        if (!withIcon) {
            toastIcon.setVisibility(8);
        } else if (icon != null) {
            if (tintIcon) {
                icon = ToastyUtils.tintIcon(icon, DEFAULT_TEXT_COLOR);
            }
            ToastyUtils.setBackground(toastIcon, icon);
        } else {
            throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
        }
        toastTextView.setText(message);
        toastTextView.setTextColor(DEFAULT_TEXT_COLOR);
        toastTextView.setTypeface(currentTypeface);
        toastTextView.setTextSize(2, (float) textSize);
        currentToast.setView(toastLayout);
        return currentToast;
    }
}
