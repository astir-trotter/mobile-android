package com.astir_trotter.atcustom.util;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * @author - Yonis Larsson
 * @contact - yonis.larsson.biz@gmail.com
 * @date - 3/28/17
 */

public class ViewUtils {
    private static final String TAG = ViewUtils.class.getSimpleName();

    public static final float DEFAULT_BACKGROUND_DIM = 0.3f;            // 1.0f -> black
    public static final float DEFAULT_TRANSPARENT_OPAQUE = 0.3f;        // 0f -> transparent

    public static void applyDim(@NonNull PopupWindow popupWindow, float dim) {
        View view = popupWindow.getContentView().getRootView();
        WindowManager wm = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) view.getLayoutParams();
        if (dim == 0)
            p.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        else
            p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = dim;
        wm.updateViewLayout(view, p);
    }

    public static void applyDim(@NonNull View view, float dim) {
        view.setBackgroundColor(getDimColor(dim));
    }

    public static void applyAlpha(@NonNull View view, float alpha) {
        view.setAlpha(alpha);
    }

    public static int getDimColor() {
        return getDimColor(DEFAULT_BACKGROUND_DIM);
    }

    public static int getDimColor(float dim) {
        return (int) (0xff * dim) << 24;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Layout

    private static int position[] = new int[2];
    public static Rect getViewRectOnScreen(@NonNull View view, @NonNull Rect retRect) {
        view.getLocationOnScreen(position);
        retRect.set(position[0],
                position[1],
                position[0] + view.getMeasuredWidth(),
                position[1] + view.getMeasuredHeight());
        return retRect;
    }

    private static Rect tmpViewScreenRect = null;
    public static Rect getViewRectOnScreen(@NonNull View view) {
        if (tmpViewScreenRect == null)
            tmpViewScreenRect = new Rect();
        return getViewRectOnScreen(view, tmpViewScreenRect);
    }

    public static Point getScreenPosition(@NonNull View view, float x, float y, @NonNull Point retPoint) {
        if (tmpViewScreenRect == null)
            tmpViewScreenRect = new Rect();
        getViewRectOnScreen(view, tmpViewScreenRect);
        retPoint.set(tmpViewScreenRect.left + (int) x, tmpViewScreenRect.top + (int) y);
        return retPoint;
    }

    private static Point screenPos = null;
    public static Point getScreenPosition(@NonNull View view, float x, float y) {
        if (screenPos == null)
            screenPos = new Point();
        return getScreenPosition(view, x, y, screenPos);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Window

    public static void makeFullScreen(Window window) {
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
