package com.astir_trotter.atcustom.singleton

import android.content.Context

import com.astir_trotter.atcustom.component.AppInfo
import com.astir_trotter.atcustom.singleton.lang.base.Language
import com.astir_trotter.atcustom.singleton.theme.base.Theme

/**
 * @author - Saori Sugiyama
 * *
 * @contact - sugiyama.saori.biz@gmail.com
 * *
 * @date - 4/11/17
 */

class Cache private constructor() {

    ////////////////////////////////////////////////////////////////////////////////////////////////

    var isDebug: Boolean = false
    var context: Context? = null
        get() {
            if (field == null)
                throw IllegalStateException("No context registered yet.")

            return field
        }
    var appInfo: AppInfo? = null
        get() {
            if (field == null)
                throw IllegalStateException("No app info registered yet.")

            return field
        }
    var language: Language = Language.English
    var theme: Theme = Theme.Light

    companion object {
        private val TAG = Cache::class.java.simpleName

        private var _instance: Cache? = null

        val instance: Cache
            get() {
                if (_instance == null)
                    _instance = Cache()

                return _instance!!
            }
    }
}
