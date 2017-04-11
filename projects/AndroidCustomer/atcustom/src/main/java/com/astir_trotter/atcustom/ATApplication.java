package com.astir_trotter.atcustom;

import android.app.Application;
import android.support.annotation.NonNull;

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

        Cache.getInstance().setContext(this);
        SplashActivity.setNextActivity(getNextActivity());
    }

    @NonNull
    protected abstract Class<?> getNextActivity();
}
