package com.astir_trotter.atcustom.singleton.lang

import android.content.res.Resources

import com.astir_trotter.atcustom.singleton.Cache
import com.astir_trotter.atcustom.singleton.lang.base.Language
import com.astir_trotter.atcustom.singleton.lang.base.StringRes
import com.astir_trotter.atcustom.util.LogHelper
import com.astir_trotter.atcustom.util.ResourceUtils

import java.util.HashMap

/**
 * @author - Saori Sugiyama
 * *
 * @contact - sugiyama.saori.biz@gmail.com
 * *
 * @date - 4/21/17
 */

class MultiLangStringRes private constructor() {

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private val mMultiLangStringRes: MutableMap<Language, StringRes>

    init {
        mMultiLangStringRes = HashMap<Language, StringRes>()
    }

    fun getStringRes(language: Language): StringRes {
        var stringRes: StringRes? = mMultiLangStringRes[language]
        if (stringRes == null) {
            stringRes = StringRes(language)
            mMultiLangStringRes.put(language, stringRes)
        }

        return stringRes
    }

    operator fun get(id: Int?): String? {
        var ret: String? = getStringRes(Cache.instance.language).get(id!!)
        if (ret == null) {
            try {
                ret = ResourceUtils.getString(id)
            } catch (ignored: Resources.NotFoundException) {
                LogHelper.log(TAG, "No resource registered. id = " + id)
            }

        }

        return ret
    }

    fun getArray(id: Int?): Array<String>? {
        var ret = getStringRes(Cache.instance.language).getArray(id!!)
        if (ret == null) {
            try {
                ret = ResourceUtils.getStrings(id)
            } catch (ignored: Resources.NotFoundException) {
                LogHelper.log(TAG, "No resource registered. id = " + id)
            }

        }

        return ret
    }

    companion object {
        private val TAG = MultiLangStringRes::class.java.simpleName

        private var _instance: MultiLangStringRes? = null
        val instance: MultiLangStringRes
            get() {
                if (_instance == null)
                    _instance = MultiLangStringRes()

                return _instance
            }
    }
}
