/**
 * @author - Nicklass Fransson
 * @contact - nicklass.fransson@gmail.com
 * @date - 12/2/16
 */
package com.astir_trotter.atcustom.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.BoolRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.astir_trotter.atcustom.singleton.Cache;


public class ResourceUtils {
    private static final String TAG = ResourceUtils.class.getSimpleName();

    public static String getString(@StringRes int resId) {
        return Cache.getInstance().getContext().getResources().getString(resId);
    }

    public static String[] getStrings(@ArrayRes int resId) {
        return Cache.getInstance().getContext().getResources().getStringArray(resId);
    }

    public static int getInteger(@IntegerRes int resId) {
        return Cache.getInstance().getContext().getResources().getInteger(resId);
    }

    public static int[] getIntegers(@ArrayRes int resId) {
        return Cache.getInstance().getContext().getResources().getIntArray(resId);
    }

    public static boolean getBool(@BoolRes int resId) {
        return Cache.getInstance().getContext().getResources().getBoolean(resId);
    }

    public static int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(Cache.getInstance().getContext(), resId);
    }

    public static int[] getColors(@ArrayRes int resId) {
        return getIntegers(resId);
    }

    public static int getDimension(Context context, @DimenRes int resId) {
        return context.getResources().getDimensionPixelSize(resId);
    }

    public static Drawable getDrawable(Context context, @DrawableRes int drawableId) {
        return context.getResources().getDrawable(drawableId);
    }
}
