package com.astir_trotter.atcustom.ui.layout.bubble;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.astir_trotter.atcustom.singleton.Cache;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 12/2/16
 */
public class BubblePopupHelper {

    public static PopupWindow create(@NonNull BubbleLayout bubbleLayout) {

        PopupWindow popupWindow = new PopupWindow(Cache.Companion.getInstance().getContext());

        popupWindow.setContentView(bubbleLayout);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);

        // change background color to transparent
//        Drawable drawable = ResourceUtils.getDrawable(Cache.getInstance().getContext(), R.drawable.popup_window_transparent);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return popupWindow;
    }

}
