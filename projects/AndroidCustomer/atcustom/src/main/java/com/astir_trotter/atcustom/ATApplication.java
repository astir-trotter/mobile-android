package com.astir_trotter.atcustom;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.astir_trotter.atcustom.crashreport.AutoErrorReporter;
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

        AutoErrorReporter.get(this)
                .setEmailAddresses(getDeveloperEmailAddress())
                .setEmailSubject("Auto Crash Report")
                .start();

        Cache.getInstance().setContext(this);
        if (getNextActivity() == null)
            SplashActivity.setNeedToSplash(false);
        else
            SplashActivity.setNextActivity(getNextActivity());
    }

    @Nullable
    protected abstract Class<?> getNextActivity();

    @Nullable
    protected abstract String[] getDeveloperEmailAddress();
}
