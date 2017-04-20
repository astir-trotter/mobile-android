package com.astir_trotter.atcustom.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.astir_trotter.atcustom.R;
import com.astir_trotter.atcustom.global.ATAppInfo;
import com.astir_trotter.atcustom.global.ATCache;
import com.astir_trotter.atcustom.utils.ViewUtils;

import java.text.MessageFormat;

public class ATSplashActivity extends AppCompatActivity {
    private static final String TAG = ATSplashActivity.class.getSimpleName();
    private static final long DEFAULT_DELAY_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewUtils.makeFullScreen(getWindow());

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
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    transit();
                }
            }, getDelayDuration());
    }

    protected void transit() {
        finish();

        if (getNextActivity() != null) {
            Intent nextIntent = new Intent(ATSplashActivity.this, getNextActivity());
            startActivity(nextIntent);
        }
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
    protected Class<?> getNextActivity() {
        return null;
    }

    protected long getDelayDuration() {
        return DEFAULT_DELAY_DURATION;
    }
}
