package com.astir_trotter.atcustom.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.astir_trotter.atcustom.utils.ViewUtils;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/20/17
 */

public abstract class ATBaseActivity extends AppCompatActivity {

    public void transit(@NonNull Class<?> nextActivity,
                           boolean isClose) {
        if (isClose)
            finish();

        Intent nextIntent = new Intent(this, nextActivity);
        startActivity(nextIntent);
    }

    public void fullscreen() {
        ViewUtils.makeFullScreen(getWindow());
    }
}
