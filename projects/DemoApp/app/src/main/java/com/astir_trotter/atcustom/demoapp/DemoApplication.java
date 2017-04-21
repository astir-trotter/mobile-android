package com.astir_trotter.atcustom.demoapp;

import android.support.annotation.NonNull;

import com.astir_trotter.atcustom.ATApplication;
import com.astir_trotter.atcustom.component.AppInfo;
import com.astir_trotter.atcustom.singleton.Cache;
import com.astir_trotter.atcustom.singleton.MultiLangStringRes;
import com.astir_trotter.atcustom.singleton.base.Language;
import com.astir_trotter.atcustom.util.ResourceUtils;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/11/17
 */

public class DemoApplication extends ATApplication {

    @Override
    protected boolean isDebug() {
        return false;//super.isDebug();
    }

    @NonNull
    @Override
    protected AppInfo getAppInfo() {
        AppInfo appInfo = super.getAppInfo();
        appInfo.appName = ResourceUtils.getString(R.string.app_name);

        return appInfo;
    }

    @NonNull
    @Override
    protected String[] getDeveloperEmailAddress() {
        return super.getDeveloperEmailAddress();
    }

    @Override
    protected void initMultiLangStringRes() {
        super.initMultiLangStringRes();

        Cache.getInstance().setLanguage(Language.Korean);

        MultiLangStringRes.getInstance().getStringRes(Language.Korean)
                .putRepeat(R.string.app_name, "데모앱");
    }
}
