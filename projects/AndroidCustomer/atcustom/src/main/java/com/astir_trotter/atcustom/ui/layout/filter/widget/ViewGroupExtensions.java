package com.astir_trotter.atcustom.ui.layout.filter.widget;

import android.view.View;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 */

public class ViewGroupExtensions {

    public static int calculateSize(int measureSpec, int desiredSize) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);

        int actualSize;
        switch (mode) {
            case View.MeasureSpec.EXACTLY:
                actualSize = size;
                break;
            case View.MeasureSpec.AT_MOST:
                actualSize = Math.min(desiredSize, size);
                break;
            default:
                actualSize = desiredSize;
        }

        return actualSize;
    }

    public static int calculateX(int position, int size, int minMargin, int itemSize) {
        int realMargin = calculateMargin(size, itemSize, minMargin);
        return position * itemSize + position * realMargin + realMargin;
    }

    public static int calculateMargin(int width, int itemWidth, int margin) {
        int count = calculateCount(width, itemWidth, margin);
        if (count > 0)
            return (width - count * itemWidth) / count;

        return 0;
    }

    public static int calculateCount(int width, int itemWidth, int margin) {
        return width / (itemWidth + margin);
    }

    public static boolean isClick(float startX, float startY, float x, float y) {
        return Math.abs(x - startX) < 20 && Math.abs(y - startY) < 20;
    }
}
