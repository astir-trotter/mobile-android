package com.astir_trotter.atcustom.singleton.theme;

import android.content.res.Resources;
import android.support.annotation.Nullable;

import com.astir_trotter.atcustom.singleton.Cache;
import com.astir_trotter.atcustom.singleton.theme.base.ColorRes;
import com.astir_trotter.atcustom.singleton.theme.base.Theme;
import com.astir_trotter.atcustom.util.LogHelper;
import com.astir_trotter.atcustom.util.ResourceUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/22/17
 */

public class MultiThemeColorRes {
    private static final String TAG = MultiThemeColorRes.class.getSimpleName();

    private static MultiThemeColorRes _instance = null;
    public static MultiThemeColorRes getInstance() {
        if (_instance == null)
            _instance = new MultiThemeColorRes();

        return _instance;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private Map<Theme, ColorRes> mMultiThemeColorRes;

    private MultiThemeColorRes() {
        mMultiThemeColorRes = new HashMap<>();
    }

    public ColorRes getColorRes(Theme theme) {
        ColorRes ColorRes = mMultiThemeColorRes.get(theme);
        if (ColorRes == null) {
            ColorRes = new ColorRes(theme);
            mMultiThemeColorRes.put(theme, ColorRes);
        }

        return ColorRes;
    }

    @Nullable
    public Integer get(Integer id) {
        Integer ret = getColorRes(Cache.Companion.getInstance().getTheme()).get(id);
        if (ret == null) {
            try {
                ret = ResourceUtils.getInteger(id);
            } catch (Resources.NotFoundException ignored) {
                LogHelper.log(TAG, "No resource registered. id = " + id);
            }
        }

        return ret;
    }

    @Nullable
    public Integer[] getArray(Integer id) {
        Integer[] ret = getColorRes(Cache.Companion.getInstance().getTheme()).getArray(id);
        if (ret == null) {
            try {
                ret = (Integer[]) Arrays.asList(ResourceUtils.getIntegers(id)).toArray();
            } catch (Resources.NotFoundException ignored) {
                LogHelper.log(TAG, "No resource registered. id = " + id);
            }
        }

        return ret;
    }
}
