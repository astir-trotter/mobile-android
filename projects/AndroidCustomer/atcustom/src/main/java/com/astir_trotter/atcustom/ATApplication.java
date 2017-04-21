package com.astir_trotter.atcustom;

import android.app.Application;
import android.support.annotation.NonNull;

import com.astir_trotter.atcustom.singleton.Cache;
import com.astir_trotter.atcustom.ui.activity.base.AutoCrashReporter;
import com.astir_trotter.atcustom.component.AppInfo;
import com.astir_trotter.atcustom.util.ResourceUtils;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/11/17
 */

public abstract class ATApplication extends Application {
    private static final String TAG = ATApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        if (isDebug()) {
            Cache.getInstance().setDebug(true);
            AutoCrashReporter.get(this)
                    .setEmailAddresses(getDeveloperEmailAddress())
                    .setEmailSubject(getSubjectForAutoCrashRepot())
                    .start();
        }

        Cache.getInstance().setContext(this);
        Cache.getInstance().setAppInfo(getAppInfo());
    }

    protected boolean isDebug() {
        return true;
    }

    @NonNull
    protected String[] getDeveloperEmailAddress() {
        return new String[]{"yonis.larsson.biz@gmail.com", "sugiyama.saori.biz@gmail.com"};
    }

    @NonNull
    protected String getSubjectForAutoCrashRepot() {
        return ResourceUtils.getString(this, R.string.crashreport_title);
    }

    @NonNull
    protected AppInfo getAppInfo() {
        return AppInfo.getDefaultAppInfo(this);
    }
}
