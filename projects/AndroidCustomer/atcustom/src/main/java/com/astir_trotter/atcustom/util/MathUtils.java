package com.astir_trotter.atcustom.util;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 5/3/17
 */

public class MathUtils {

    public static float mix(float x, float y, float a) {
        return x * (1 - a) + y * a;
    }

    public static double constraint(float x, float f, double c) {
        return Math.max(f, Math.min(x, c));
    }

}
