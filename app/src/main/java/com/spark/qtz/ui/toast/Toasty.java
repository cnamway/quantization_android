package com.spark.qtz.ui.toast;

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

import com.spark.qtz.R;

import me.spark.mvvm.utils.Utils;


/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/3/18
 * 描    述：Toast工具类
 * 修订历史：
 * ================================================
 */

@SuppressLint("InflateParams")
public class Toasty {
    private static final
    @ColorInt
    int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");

    private static final
    @ColorInt
    int ERROR_COLOR = Color.parseColor("#D50000");
    private static final
    @ColorInt
    int INFO_COLOR = Color.parseColor("#A6000000");
    private static final
    @ColorInt
    int SUCCESS_COLOR = Color.parseColor("#388E3C");
    private static final
    @ColorInt
    int WARNING_COLOR = Color.parseColor("#FFA900");

    private static final String TOAST_TYPEFACE = "sans-serif-condensed";
    static Toast currentToast;
    static TextView toastTextView;
    static ImageView toastIcon;
    static View toastLayout;

    private Toasty() {
    }

    public static
    @CheckResult
    Toast normal(@NonNull Context context, @NonNull CharSequence message) {
        return normal(context, message, Toast.LENGTH_SHORT, null, false);
    }

    public static
    @CheckResult
    Toast normal(@NonNull Context context, @NonNull CharSequence message, Drawable icon) {
        return normal(context, message, Toast.LENGTH_SHORT, icon, true);
    }

    public static
    @CheckResult
    Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return normal(context, message, duration, null, false);
    }

    public static
    @CheckResult
    Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration,
                 Drawable icon) {
        return normal(context, message, duration, icon, true);
    }

    public static
    @CheckResult
    Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration,
                 Drawable icon, boolean withIcon) {
        return custom(context, message, icon, DEFAULT_TEXT_COLOR, duration, withIcon);
    }

    public static
    @CheckResult
    Toast warning(@NonNull Context context, @NonNull CharSequence message) {
        return warning(context, message, Toast.LENGTH_SHORT, true);
    }

    public static
    @CheckResult
    Toast warning(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return warning(context, message, duration, true);
    }

    public static
    @CheckResult
    Toast warning(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.mipmap.ic_error_outline_white_48dp),
                DEFAULT_TEXT_COLOR, WARNING_COLOR, duration, withIcon, true);
    }

    public static
    @CheckResult
    Toast info(@NonNull Context context, @NonNull CharSequence message) {
        return info(context, message, Toast.LENGTH_SHORT, true);
    }

    public static
    @CheckResult
    Toast info(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return info(context, message, duration, true);
    }

    public static
    @CheckResult
    Toast info(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.mipmap.ic_info_outline_white_48dp),
                DEFAULT_TEXT_COLOR, INFO_COLOR, duration, withIcon, true);
    }

    public static
    @CheckResult
    Toast success(@NonNull Context context, @NonNull CharSequence message) {
        return success(context, message, Toast.LENGTH_SHORT, true);
    }

    public static
    @CheckResult
    Toast success(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return success(context, message, duration, true);
    }

    public static
    @CheckResult
    Toast success(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.mipmap.ic_check_white_48dp),
                DEFAULT_TEXT_COLOR, SUCCESS_COLOR, duration, withIcon, true);
    }

    public static
    @CheckResult
    Toast error(@NonNull Context context, @NonNull CharSequence message) {
        return error(context, message, Toast.LENGTH_SHORT, true);
    }

    public static
    @CheckResult
    Toast error(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return error(context, message, duration, true);
    }

    public static
    @CheckResult
    Toast error(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.mipmap.ic_clear_white_48dp),
                DEFAULT_TEXT_COLOR, ERROR_COLOR, duration, withIcon, true);
    }

    public static
    @CheckResult
    Toast custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon,
                 @ColorInt int textColor, int duration, boolean withIcon) {
        return custom(context, message, icon, textColor, -1, duration, withIcon, false);
    }

    public static
    @CheckResult
    Toast custom(@NonNull Context context, @NonNull CharSequence message, @DrawableRes int iconRes,
                 @ColorInt int textColor, @ColorInt int tintColor, int duration,
                 boolean withIcon, boolean shouldTint) {
        return custom(context, message, ToastyUtils.getDrawable(context, iconRes), textColor,
                tintColor, duration, withIcon, shouldTint);
    }

    public static
    @CheckResult
    Toast custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon,
                 @ColorInt int textColor, @ColorInt int tintColor, int duration,
                 boolean withIcon, boolean shouldTint) {
        /**
         * 防止重复出现
         * by ccs
         */
        if (currentToast == null) {
            currentToast = new Toast(context);
            toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.toast_layout, null);
            toastIcon = (ImageView) toastLayout.findViewById(R.id.toast_icon);
            toastTextView = (TextView) toastLayout.findViewById(R.id.toast_text);
            Drawable drawableFrame;

            if (shouldTint)
                drawableFrame = ToastyUtils.tint9PatchDrawableFrame(context, tintColor);
            else
                drawableFrame = ToastyUtils.getDrawable(context, R.mipmap.toast_frame);
            ToastyUtils.setBackground(toastLayout, drawableFrame);

            if (withIcon) {
                if (icon == null)
                    throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
                ToastyUtils.setBackground(toastIcon, icon);
            } else
                toastIcon.setVisibility(View.GONE);

            toastTextView.setTextColor(textColor);
            toastTextView.setText(message);
            toastTextView.setTypeface(Typeface.create(TOAST_TYPEFACE, Typeface.NORMAL));

            currentToast.setView(toastLayout);
            currentToast.setDuration(duration);
        } else {
            Drawable drawableFrame;
            if (shouldTint)
                drawableFrame = ToastyUtils.tint9PatchDrawableFrame(context, tintColor);
            else
                drawableFrame = ToastyUtils.getDrawable(context, R.mipmap.toast_frame);
            ToastyUtils.setBackground(toastLayout, drawableFrame);

            if (withIcon) {
                if (icon == null)
                    throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
                ToastyUtils.setBackground(toastIcon, icon);
            } else
                toastIcon.setVisibility(View.GONE);

            toastTextView.setTextColor(textColor);
            toastTextView.setText(message);
            toastTextView.setTypeface(Typeface.create(TOAST_TYPEFACE, Typeface.NORMAL));

            currentToast.setView(toastLayout);
            currentToast.setDuration(duration);
        }

        return currentToast;
    }


    /**
     * toast INFO
     *
     * @param info
     */
    public static void showInfo(String info) {
        Toasty.info(Utils.getContext(), info, 3 * 1000, true).show();
    }

    /**
     * toast SUCCESS
     *
     * @param success
     */
    public static void showSuccess(String success) {
        Toasty.success(Utils.getContext(), success, 3 * 1000, true).show();
    }

    /**
     * toast ERROR
     *
     * @param error
     */
    public static void showError(String error) {
        Toasty.error(Utils.getContext(), error, 3 * 1000, true).show();

    }
}
