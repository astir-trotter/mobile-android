package com.astir_trotter.atcustom.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.astir_trotter.atcustom.singleton.Cache;
import com.astir_trotter.atcustom.singleton.lang.base.Language;
import com.astir_trotter.atcustom.singleton.theme.base.Theme;
import com.astir_trotter.atcustom.util.ViewUtils;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/20/17
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    private boolean isInitialized;
    private Theme mCurTheme;
    private Language mCurLanguage;

    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isInitialized) {
            initTheme();
            initLanguage();
            isInitialized = true;
        }
    }

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
    protected void onPause() {
        super.onPause();

        mCurTheme = Cache.getInstance().getTheme();
        mCurLanguage = Cache.getInstance().getLanguage();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mCurTheme == null || !mCurTheme.equals(Cache.getInstance().getTheme())) {
            representTheme();
            mCurTheme = Cache.getInstance().getTheme();
        }

        if (mCurLanguage == null || !mCurLanguage.equals(Cache.getInstance().getLanguage())) {
            representLanguage();
            mCurLanguage = Cache.getInstance().getLanguage();
        }
    }

    protected void initTheme() { }

    protected void initLanguage() { }

    protected abstract void representTheme();

    protected abstract void representLanguage();

}
