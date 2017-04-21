/**
 * @author - Nicklass Fransson
 * @contact - nicklass.fransson@gmail.com
 * @date - 12/2/16
 */
package com.astir_trotter.atcustom.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.BoolRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;


public class ResourceUtils {
    private static final String TAG = ResourceUtils.class.getSimpleName();

    public static String getString(Context context, @StringRes int resId) {
        return context.getResources().getString(resId);
    }

    public static int getInteger(Context context, @IntegerRes int resId) {
        return context.getResources().getInteger(resId);
    }

    public static boolean getBool(Context context, @BoolRes int resId) {
        return context.getResources().getBoolean(resId);
    }

    public static int getColor(Context context, @ColorRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return context.getColor(resId);
        else
            return context.getResources().getColor(resId);
    }

    public static int getDimension(Context context, @DimenRes int resId) {
        return context.getResources().getDimensionPixelSize(resId);
    }

    public static Drawable getDrawable(Context context, @DrawableRes int drawableId) {
        return context.getResources().getDrawable(drawableId);
    }
}
