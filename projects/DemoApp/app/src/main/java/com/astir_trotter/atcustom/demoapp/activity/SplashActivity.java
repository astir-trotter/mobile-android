package com.astir_trotter.atcustom.demoapp.activity;

import android.support.annotation.Nullable;

import com.astir_trotter.atcustom.singleton.Cache;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/20/17
 */

public class SplashActivity extends com.astir_trotter.atcustom.ui.activity.SplashActivity {

    @Nullable
    @Override
    protected Class<?> getNextActivityClass() {
        return MainActivity.class;
    }

//    @Override
//    protected long getDelayDuration() {
//        return 1000;//super.getDelayDuration(); // TESTCODE
//    }
}
