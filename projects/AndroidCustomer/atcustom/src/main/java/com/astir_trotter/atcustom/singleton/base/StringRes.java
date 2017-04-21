package com.astir_trotter.atcustom.singleton.base;

import com.astir_trotter.atcustom.util.Constants;

import java.util.HashMap;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 */

public class StringRes extends HashMap<Integer, String> {
    private static final String TAG = StringRes.class.getSimpleName();

    private Language language;

    public StringRes(Language language) {
        this.language = language;
    }

    public Language getLanguage() {
        return language;
    }

    @Override
    public String get(Object key) {
        String ret = super.get(key);
        if (ret == null)
            return Constants.EMPTY_STRING;

        return ret;
    }
}
