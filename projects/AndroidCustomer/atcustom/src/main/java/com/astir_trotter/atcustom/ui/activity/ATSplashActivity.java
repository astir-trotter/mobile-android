package com.astir_trotter.atcustom.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.astir_trotter.atcustom.R;
import com.astir_trotter.atcustom.global.ATAppInfo;
import com.astir_trotter.atcustom.global.ATCache;

import java.text.MessageFormat;

public class ATSplashActivity extends ATBaseActivity {
    private static final String TAG = ATSplashActivity.class.getSimpleName();
    private static final long DEFAULT_DELAY_DURATION = 3000;

    private Runnable mDelayedTransit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fullscreen();

        if (getContentView() != null)
            setContentView(getContentView());
        else if (getContentLayout() != 0)
            setContentView(getContentLayout());
        else {
            setContentView(R.layout.activity_splash);

            ATAppInfo appInfo = ATCache.getInstance().getAppInfo();
            String title = MessageFormat.format("{0} {1}_{2}", appInfo.appName, appInfo.versionName, appInfo.buildNumber);
            ((TextView) findViewById(R.id.app_name)).setText(title);
            ((TextView) findViewById(R.id.app_description)).setText(appInfo.appDescription);
            ((TextView) findViewById(R.id.copyright)).setText(appInfo.copyright);
            ((TextView) findViewById(R.id.org_name)).setText(appInfo.orgName);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mDelayedTransit != null) {
            getHandler().removeCallbacks(mDelayedTransit);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getNextActivityClass() != null) {
            mDelayedTransit = new Runnable() {
                @Override
                public void run() {
                    transit(getNextActivityClass(), true);
                }
            };
            getHandler().postDelayed(mDelayedTransit, getDelayDuration());
        } else
            mDelayedTransit = null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // getter

    @Nullable
    protected View getContentView() {
        return null;
    }

    @LayoutRes
    protected int getContentLayout() {
        return 0;
    }

    @Nullable
    protected Class<?> getNextActivityClass() {
        return null;
    }

    protected long getDelayDuration() {
        return DEFAULT_DELAY_DURATION;
    }
}
