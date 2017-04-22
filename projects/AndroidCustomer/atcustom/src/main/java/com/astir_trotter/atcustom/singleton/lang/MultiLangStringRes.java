package com.astir_trotter.atcustom.singleton.lang;

import android.content.res.Resources;
import android.support.annotation.Nullable;

import com.astir_trotter.atcustom.singleton.Cache;
import com.astir_trotter.atcustom.singleton.lang.base.Language;
import com.astir_trotter.atcustom.singleton.lang.base.StringRes;
import com.astir_trotter.atcustom.util.ResourceUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 */

public class MultiLangStringRes {
    private static final String TAG = MultiLangStringRes.class.getSimpleName();

    private static MultiLangStringRes _instance = null;
    public static MultiLangStringRes getInstance() {
        if (_instance == null)
            _instance = new MultiLangStringRes();

        return _instance;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private Map<Language, StringRes> multiLangStringRes;

    private MultiLangStringRes() {
        multiLangStringRes = new HashMap<>();
    }

    public StringRes getStringRes(Language language) {
        StringRes stringRes = multiLangStringRes.get(language);
        if (stringRes == null) {
            stringRes = new StringRes(language);
            multiLangStringRes.put(language, stringRes);
        }

        return stringRes;
    }

    @Nullable
    public String get(Integer id) {
        String ret = getStringRes(Cache.getInstance().getLanguage()).get(id);
        if (ret == null) {
            try {
                ret = ResourceUtils.getString(id);
            } catch (Resources.NotFoundException ignored) {
            }
        }

        return ret;
    }

    @Nullable
    public String[] getArray(Integer id) {
        String[] ret = getStringRes(Cache.getInstance().getLanguage()).getArray(id);
        if (ret == null) {
            try {
                ret = ResourceUtils.getStrings(id);
            } catch (Resources.NotFoundException ignored) {
            }
        }

        return ret;
    }
}
