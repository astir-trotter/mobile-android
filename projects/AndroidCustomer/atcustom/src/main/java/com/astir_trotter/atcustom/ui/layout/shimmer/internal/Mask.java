package com.astir_trotter.atcustom.ui.layout.shimmer.internal;

import android.graphics.Color;

import com.astir_trotter.atcustom.ui.layout.shimmer.MaskAngle;
import com.astir_trotter.atcustom.ui.layout.shimmer.MaskShape;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 *
 * Struct storing various mask related parameters, which are used to construct the mask bitmap.
 */

public class Mask {

    public MaskAngle angle;
    public float tilt;
    public float dropoff;
    public int fixedWidth;
    public int fixedHeight;
    public float intensity;
    public float relativeWidth;
    public float relativeHeight;
    public MaskShape shape;

    public int maskWidth(int width) {
        return fixedWidth > 0 ? fixedWidth : (int) (width * relativeWidth);
    }

    public int maskHeight(int height) {
        return fixedHeight > 0 ? fixedHeight : (int) (height * relativeHeight);
    }

    /**
     * Get the array of colors to be distributed along the gradient of the mask bitmap
     *
     * @return An array of black and transparent colors
     */
    public int[] getGradientColors() {
        switch (shape) {
            default:
            case LINEAR:
                return new int[]{Color.TRANSPARENT, Color.BLACK, Color.BLACK, Color.TRANSPARENT};
            case RADIAL:
                return new int[]{Color.BLACK, Color.BLACK, Color.TRANSPARENT};
        }
    }

    /**
     * Get the array of relative positions [0..1] of each corresponding color in the colors array
     *
     * @return A array of float values in the [0..1] range
     */
    public float[] getGradientPositions() {
        switch (shape) {
            default:
            case LINEAR:
                return new float[]{
                        Math.max((1.0f - intensity - dropoff) / 2, 0.0f),
                        Math.max((1.0f - intensity) / 2, 0.0f),
                        Math.min((1.0f + intensity) / 2, 1.0f),
                        Math.min((1.0f + intensity + dropoff) / 2, 1.0f)};
            case RADIAL:
                return new float[]{
                        0.0f,
                        Math.min(intensity, 1.0f),
                        Math.min(intensity + dropoff, 1.0f)};
        }
    }
}
