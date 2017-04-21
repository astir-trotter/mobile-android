package com.astir_trotter.atcustom.demoapp;

import android.support.annotation.NonNull;

import com.astir_trotter.atcustom.ATApplication;
import com.astir_trotter.atcustom.component.AppInfo;
import com.astir_trotter.atcustom.util.ResourceUtils;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/11/17
 */

public class DemoApplication extends ATApplication {

    @Override
    protected boolean isDebug() {
        return super.isDebug();
    }

    @NonNull
    @Override
    protected AppInfo getAppInfo() {
        AppInfo appInfo = super.getAppInfo();
        appInfo.appName = ResourceUtils.getString(this, R.string.app_name);

        return appInfo;
    }

    @NonNull
    @Override
    protected String[] getDeveloperEmailAddress() {
        return super.getDeveloperEmailAddress();
    }
}
