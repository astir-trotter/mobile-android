package com.astir_trotter.atcustom.utils;

import android.graphics.Bitmap;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 */

public class ImageUtils {
    private static final String TAG = ImageUtils.class.getSimpleName();

    /**
     * Creates a bitmap with the given width and height.
     * <p/>
     * If it fails with an OutOfMemory error, it will force a GC and then try to create the bitmap
     * one more time.
     *
     * @param width  width of the bitmap
     * @param height height of the bitmap
     */
    public static Bitmap createBitmapAndGcIfNecessary(int width, int height) {
        try {
            return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError e) {
            System.gc();
            return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
    }
}
