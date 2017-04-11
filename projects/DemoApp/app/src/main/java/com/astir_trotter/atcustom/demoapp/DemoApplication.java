package com.astir_trotter.atcustom.demoapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.astir_trotter.atcustom.ATApplication;
import com.astir_trotter.atcustom.demoapp.activity.MainActivity;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/11/17
 */

public class DemoApplication extends ATApplication {

    @Nullable
    @Override
    protected Class<?> getNextActivity() {
        return MainActivity.class;
    }

    @Nullable
    @Override
    protected String[] getDeveloperEmailAddress() {
        return new String[]{"yonis.larsson.biz@gmail.com", "sugiyama.saori.biz@gmail.com"};
    }
}
