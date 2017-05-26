package com.astir_trotter.atcustom

import android.app.Application

import com.astir_trotter.atcustom.component.AppInfo
import com.astir_trotter.atcustom.singleton.Cache
import com.astir_trotter.atcustom.singleton.lang.MultiLangStringRes
import com.astir_trotter.atcustom.singleton.lang.base.Language
import com.astir_trotter.atcustom.ui.activity.base.AutoCrashReporter

/**
 * @author - Saori Sugiyama
 * *
 * @contact - sugiyama.saori.biz@gmail.com
 * *
 * @date - 4/11/17
 */

abstract class ATApplication : Application() {

    companion object {
        private val TAG = ATApplication::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()

        // set base app info
        Cache.instance.context = this
        Cache.instance.appInfo = appInfo

        // initializae language info - string res
        initMultiLangStringRes()

        // initializae language info - string res
        initMultiThemeColorRes()

        // if debug mode, start auto crash reporter.
        if (isDebug) {
            Cache.instance.isDebug = true
            AutoCrashReporter.get(this)
                    .setEmailAddresses(*developerEmailAddress)
                    .setEmailSubject(subjectForAutoCrashRepot)
                    .start()
        }

    }

    protected val isDebug: Boolean
        get() = BuildConfig.DEBUG

    protected val developerEmailAddress: Array<String>
        get() = arrayOf("yonis.larsson.biz@gmail.com")

    protected val subjectForAutoCrashRepot: String
        get() = MultiLangStringRes.instance.get(R.string.crashreport_title)

    protected val appInfo: AppInfo
        get() = AppInfo.getDefaultAppInfo(this)

    /**
     * Initialize language info.
     * Create languages and register string res according to its language.
     */
    protected open fun initMultiLangStringRes() {
        MultiLangStringRes.instance.getStringRes(Language.Korean)
                .putRepeat(android.R.string.ok, "확인")
                .putRepeat(android.R.string.cancel, "취소")
                .putRepeat(android.R.string.yes, "예")
                .putRepeat(android.R.string.no, "아니")
                .putRepeat(R.string.crashreport_title, "오류발생")
                .putRepeat(R.string.crashreport_description, "개발자에게로 오류내용을 전송하여 수정하겠습니까?")
    }

    /**
     * Initialize theme info.
     * Create themes and register color res according to its theme.
     */
    protected fun initMultiThemeColorRes() {

    }
}
