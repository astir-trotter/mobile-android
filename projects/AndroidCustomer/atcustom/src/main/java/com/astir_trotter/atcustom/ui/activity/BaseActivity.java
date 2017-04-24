package com.astir_trotter.atcustom.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.astir_trotter.atcustom.util.ViewUtils;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/20/17
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    private Handler mHandler;

    public Handler getHandler() {
        if (mHandler == null)
            mHandler = new Handler();

        return mHandler;
    }

    public void transit(@NonNull Class<?> nextActivityClass,
                        boolean isCloseSelf) {
        transit(nextActivityClass, isCloseSelf, 0);
    }

    public void transit(@NonNull Class<?> nextActivityClass,
                        boolean isCloseSelf,
                        int flags) {
        Intent nextIntent = new Intent(this, nextActivityClass);
        nextIntent.setFlags(flags);
        startActivity(nextIntent);

        if (isCloseSelf)
            finish();
    }

    public void fullscreen() {
        ViewUtils.makeFullScreen(getWindow());
    }

    @Override
    protected void onResume() {
        super.onResume();

        representTheme();
        representLanguage();
    }

    protected abstract void representTheme();

    protected abstract void representLanguage();

}
