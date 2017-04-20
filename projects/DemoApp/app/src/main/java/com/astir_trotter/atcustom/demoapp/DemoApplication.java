package com.astir_trotter.atcustom.demoapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.astir_trotter.atcustom.ATApplication;
import com.astir_trotter.atcustom.demoapp.activity.MainActivity;
import com.astir_trotter.atcustom.utils.ResourceUtils;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/11/17
 */

public class DemoApplication extends ATApplication {

    @Nullable
    @Override
    protected String[] getDeveloperEmailAddress() {
        return new String[]{"yonis.larsson.biz@gmail.com", "sugiyama.saori.biz@gmail.com"};
    }

    @NonNull
    @Override
    protected String getAppName() {
        return ResourceUtils.getString(this, R.string.app_name);
    }

    @NonNull
    @Override
    protected String getAppDescription() {
        return ResourceUtils.getString(this, R.string.app_description);
    }

    @NonNull
    @Override
    protected String getOrgName() {
        return ResourceUtils.getString(this, R.string.org_name);
    }

    @NonNull
    @Override
    protected String getCopyright() {
        return ResourceUtils.getString(this, R.string.copyright);
    }
}
