package com.astir_trotter.atcustom.demoapp;

import android.support.annotation.NonNull;

import com.astir_trotter.atcustom.ATApplication;
import com.astir_trotter.atcustom.global.ATAppInfo;
import com.astir_trotter.atcustom.utils.ResourceUtils;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/11/17
 */

public class DemoApplication extends ATApplication {

    @NonNull
    @Override
    protected ATAppInfo getAppInfo() {
        ATAppInfo appInfo = super.getAppInfo();
        appInfo.appName = ResourceUtils.getString(this, R.string.app_name);

        return appInfo;
    }
}
