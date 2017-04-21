package com.astir_trotter.atcustom.singleton.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;

import com.astir_trotter.atcustom.util.Constants;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 */

public class StringRes extends SparseArrayCompat<String> {
    private static final String TAG = StringRes.class.getSimpleName();

    private Language language;

    private SparseArrayCompat<String[]> arrayRes;

    public StringRes(Language language) {
        this.language = language;
        this.arrayRes = new SparseArrayCompat<>();
    }

    public Language getLanguage() {
        return language;
    }

    @Nullable
    public String[] getArray(int id) {
        return arrayRes.get(id);
    }

    public StringRes putArrayRepeat(int key, String[] values) {
        arrayRes.put(key, values);
        return this;
    }

    public StringRes putAllRepeat(int key, String... values) {
        return putArrayRepeat(key, values);
    }

    @NonNull
    public StringRes putRepeat(int key, String value) {
        put(key, value);
        return this;
    }
}
