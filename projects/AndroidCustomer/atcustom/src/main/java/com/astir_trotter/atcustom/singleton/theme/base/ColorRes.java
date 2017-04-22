package com.astir_trotter.atcustom.singleton.theme.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/22/17
 */

public class ColorRes extends SparseArrayCompat<Integer> {
    private static final String TAG = ColorRes.class.getSimpleName();

    private Theme theme;

    private SparseArrayCompat<Integer[]> arrayRes;

    public ColorRes(Theme theme) {
        this.theme = theme;
        this.arrayRes = new SparseArrayCompat<>();
    }

    public Theme getTheme() {
        return theme;
    }

    @Nullable
    public Integer[] getArray(int id) {
        return arrayRes.get(id);
    }

    public ColorRes putArrayRepeat(int key, Integer[] values) {
        arrayRes.put(key, values);
        return this;
    }

    public ColorRes putAllRepeat(int key, Integer... values) {
        return putArrayRepeat(key, values);
    }

    @NonNull
    public ColorRes putRepeat(int key, Integer value) {
        put(key, value);
        return this;
    }
    
}
