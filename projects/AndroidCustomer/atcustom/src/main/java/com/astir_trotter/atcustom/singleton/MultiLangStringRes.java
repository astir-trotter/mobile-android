package com.astir_trotter.atcustom.singleton;

import android.content.res.Resources;

import com.astir_trotter.atcustom.singleton.base.Language;
import com.astir_trotter.atcustom.singleton.base.StringRes;
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
        multiLangStringRes = new HashMap<>(Language.values().length);


    }

    public StringRes getStringRes(Language language) {
        StringRes stringRes = multiLangStringRes.get(language);
        if (stringRes == null) {
            stringRes = new StringRes(language);
            multiLangStringRes.put(language, stringRes);
        }

        return stringRes;
    }

    public String get(Integer id) {
        String ret = getStringRes(Cache.getInstance().getLanguage()).get(id);
        if (ret.isEmpty()) {
            try {
                ret = ResourceUtils.getString(Cache.getInstance().getContext(), id);
            } catch (Resources.NotFoundException ignored) {
            }
        }

        return ret;
    }
}
