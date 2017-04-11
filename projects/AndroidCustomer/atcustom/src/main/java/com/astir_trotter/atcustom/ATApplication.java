package com.astir_trotter.atcustom;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.astir_trotter.atcustom.crashreport.AutoCrashReporter;
import com.astir_trotter.atcustom.global.Cache;
import com.astir_trotter.atcustom.ui.activity.SplashActivity;

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

        String[] devEmailAddresses = getDeveloperEmailAddress();
        if (devEmailAddresses != null) {
            AutoCrashReporter.get(this)
                    .setEmailAddresses(devEmailAddresses)
                    .setEmailSubject("Auto Crash Report")
                    .start();
        }

        calcAppInfo();
        Cache.getInstance().setContext(this);

        if (getNextActivity() != null)
            SplashActivity.setNextActivity(getNextActivity());

    }

    private void calcAppInfo() {
        Cache.getInstance().getAppInfo().appName = getAppName();
        Cache.getInstance().getAppInfo().orgName = getOrgName();
        Cache.getInstance().getAppInfo().appDescription = getAppDescription();
        Cache.getInstance().getAppInfo().copyright = getCopyright();

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            Cache.getInstance().getAppInfo().versionName = packageInfo.versionName;
            Cache.getInstance().getAppInfo().buildNumber = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException ignored) {
            Cache.getInstance().getAppInfo().versionName = "Unknown";
            Cache.getInstance().getAppInfo().buildNumber = -1;
        }
    }

    @Nullable
    protected abstract Class<?> getNextActivity();

    @Nullable
    protected abstract String[] getDeveloperEmailAddress();

    @NonNull
    protected abstract String getAppName();

    @NonNull
    protected abstract String getAppDescription();

    @NonNull
    protected abstract String getOrgName();

    @NonNull
    protected abstract String getCopyright();
}
