package com.astir_trotter.atcustom.demoapp

import com.astir_trotter.atcustom.ATApplication
import com.astir_trotter.atcustom.component.AppInfo
import com.astir_trotter.atcustom.singleton.lang.MultiLangStringRes
import com.astir_trotter.atcustom.singleton.lang.base.Language
import com.astir_trotter.atcustom.util.ResourceUtils

/**
 * @author - Saori Sugiyama
 * *
 * @contact - sugiyama.saori.biz@gmail.com
 * *
 * @date - 4/11/17
 */

class DemoApplication : ATApplication() {

    override fun isDebug(): Boolean {
        return false // TESTCODE
    }

    override fun getAppInfo(): AppInfo {
        val appInfo = super.getAppInfo()
        appInfo.appName = ResourceUtils.getString(R.string.app_name)

        return appInfo
    }

    override fun getDeveloperEmailAddress(): Array<String> {
        return super.getDeveloperEmailAddress()
    }

    override fun initMultiLangStringRes() {
        super.initMultiLangStringRes()

        //        Cache.getInstance().setLanguage(Language.Korean);

        MultiLangStringRes.getInstance().getStringRes(Language.Korean)
                .putRepeat(R.string.app_name, "데모앱")
    }
}
