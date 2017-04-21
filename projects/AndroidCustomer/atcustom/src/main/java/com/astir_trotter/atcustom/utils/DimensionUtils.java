/**
 * @author - Yonis Larsson
 * @contact - yonis.larsson.biz@gmail.com
 * @date - 12/6/16
 */
package com.astir_trotter.atcustom.utils;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.util.TypedValue;

public class DimensionUtils {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Distance

    public static float distance(@NonNull PointF point1, @NonNull PointF point2) {
        return distance(point1.x, point1.y, point2.x, point2.y);
    }

    public static float distance(@NonNull Point point1, @NonNull Point point2) {
        return distance(point1.x, point1.y, point2.x, point2.y);
    }

    public static float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Layout

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private final static TypedValue tv = new TypedValue();
    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
        } else if (context.getTheme().resolveAttribute( android.support.v7.appcompat.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
        }

        return actionBarHeight;
    }

}
