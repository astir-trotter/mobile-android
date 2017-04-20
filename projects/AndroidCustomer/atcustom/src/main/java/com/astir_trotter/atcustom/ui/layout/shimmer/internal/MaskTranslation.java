package com.astir_trotter.atcustom.ui.layout.shimmer.internal;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 *
 * Struct for storing the mask translation animation values.
 */

public class MaskTranslation {

    public int fromX;
    public int fromY;
    public int toX;
    public int toY;

    public void set(int fromX, int fromY, int toX, int toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }
}
