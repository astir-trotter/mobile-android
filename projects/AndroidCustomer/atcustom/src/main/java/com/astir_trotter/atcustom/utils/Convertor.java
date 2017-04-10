/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 12/2/16
 */
package com.astir_trotter.atcustom.utils;

import android.content.res.Resources;

public class Convertor {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // String -> Number

    public static float getFloat(String valString) {
        try {
            return Float.parseFloat(valString);
        } catch (NumberFormatException ignored) {
        }

        return 0;
    }

    public static int getInt(String valString) {
        return getInt(valString, 10);
    }
    public static int getInt(String valString, int radix) {
        try {
            return Integer.parseInt(valString, radix);
        } catch (NumberFormatException ignored) {
        }

        return 0;
    }

    public static boolean getBoolean(String valString) {
        return Boolean.parseBoolean(valString);
    }

    public static long getLong(String valString) {
        return getLong(valString, 10);
    }
    public static long getLong(String valString, int radix) {
        try {
            return Long.parseLong(valString, radix);
        } catch (NumberFormatException ignored) {
        }

        return 0;
    }

    public static float getPercent(String valString) {
        if (!valString.endsWith("%"))
            return 0;

        return getFloat(valString.substring(0, valString.length() - 1));
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Dimension

    public static float dp2px(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    public static float px2dp(float px) {
        return px / Resources.getSystem().getDisplayMetrics().density;
    }

    public static float sp2px(float sp) {
        return sp * Resources.getSystem().getDisplayMetrics().scaledDensity;
    }

    public static float px2sp(float px) {
        return px / Resources.getSystem().getDisplayMetrics().scaledDensity;
    }
}
