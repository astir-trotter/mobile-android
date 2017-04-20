package com.astir_trotter.atcustom;

import android.app.Application;
import android.support.annotation.NonNull;

import com.astir_trotter.atcustom.crashreport.AutoCrashReporter;
import com.astir_trotter.atcustom.global.ATAppInfo;
import com.astir_trotter.atcustom.global.ATCache;
import com.astir_trotter.atcustom.utils.ResourceUtils;

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

        if (isAutoCrashReportEnabled()) {
            AutoCrashReporter.get(this)
                    .setEmailAddresses(getDeveloperEmailAddress())
                    .setEmailSubject(getSubjectForAutoCrashRepot())
                    .start();
        }

        ATCache.getInstance().setContext(this);
        ATCache.getInstance().setAppInfo(getAppInfo());
    }

    protected boolean isAutoCrashReportEnabled() {
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
    protected ATAppInfo getAppInfo() {
        return ATAppInfo.getDefaultAppInfo(this);
    }
}
