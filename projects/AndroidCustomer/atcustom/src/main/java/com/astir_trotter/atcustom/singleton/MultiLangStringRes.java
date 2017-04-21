package com.astir_trotter.atcustom.singleton;

import android.content.res.Resources;
import android.util.Log;

import com.astir_trotter.atcustom.R;
import com.astir_trotter.atcustom.singleton.base.Language;
import com.astir_trotter.atcustom.singleton.base.StringRes;
import com.astir_trotter.atcustom.util.AssetsHelper;
import com.astir_trotter.atcustom.util.Constants;
import com.astir_trotter.atcustom.util.LogHelper;
import com.astir_trotter.atcustom.util.ResourceUtils;
import com.astir_trotter.atcustom.util.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        getStringRes(Language.Korean)
                .putRepeat(android.R.string.ok, "확인")
                .putRepeat(android.R.string.cancel, "취소")
                .putRepeat(android.R.string.yes, "예")
                .putRepeat(android.R.string.no, "아니")
                .putRepeat(R.string.crashreport_title, "오류발생")
                .putRepeat(R.string.crashreport_description, "개발자에게로 오류내용을 전송하여 수정하겠습니까?");
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
