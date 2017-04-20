package com.astir_trotter.atcustom.demoapp.activity;

import android.support.annotation.Nullable;

import com.astir_trotter.atcustom.ui.activity.ATSplashActivity;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/20/17
 */

public class SplashActivity extends ATSplashActivity {

    @Nullable
    @Override
    protected Class<?> getNextActivity() {
        return MainActivity.class;
    }
}
