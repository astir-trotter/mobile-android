package com.astir_trotter.atcustom.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.astir_trotter.atcustom.R;
import com.astir_trotter.atcustom.utils.ViewUtils;

public class SplashActivity extends AppCompatActivity {
    private static Class<?> sNextActivity = null;
    private static boolean  sNeedToSplash = true;
    private static long     sDelayDuration = 3000;      // ms
    private static View     sSplashContentView = null;
    private static int      sSplashContentLayout = 0;   // @LayoutRes

    public static void setNextActivity(Class<?> nextActivity) {
        SplashActivity.sNextActivity = nextActivity;
    }

    public static void setNeedToSplash(boolean isNeedToSplash) {
        SplashActivity.sNeedToSplash = isNeedToSplash;
    }

    public static void setDelayDuration(long delayDuration) {
        SplashActivity.sDelayDuration = delayDuration;
    }

    public static void setSplashContentView(View splashContentView) {
        SplashActivity.sSplashContentView = splashContentView;
        SplashActivity.sSplashContentLayout = 0;
    }

    public static void setSplashContentLayout(@LayoutRes int splashContentLayout) {
        SplashActivity.sSplashContentLayout = splashContentLayout;
        SplashActivity.sSplashContentView = null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewUtils.makeFullScreen(getWindow());

        if (sSplashContentView != null)
            setContentView(sSplashContentView);
        else if (sSplashContentLayout != 0)
            setContentView(sSplashContentLayout);
        else
            setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sNeedToSplash) {
            if (sNextActivity == null)
                throw new IllegalStateException("Not next activity set!");

            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    transit();
                }
            }, sDelayDuration);
        } else
            transit();
    }

    private void transit() {
        finish();
        Intent mainIntent = new Intent(SplashActivity.this, sNextActivity);
        startActivity(mainIntent);
    }
}
