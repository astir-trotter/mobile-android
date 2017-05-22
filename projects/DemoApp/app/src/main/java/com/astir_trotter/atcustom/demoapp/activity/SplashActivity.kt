package com.astir_trotter.atcustom.demoapp.activity

import com.astir_trotter.atcustom.demoapp.activity.main.MainActivity

/**
 * @author - Saori Sugiyama
 * *
 * @contact - sugiyama.saori.biz@gmail.com
 * *
 * @date - 4/20/17
 */

class SplashActivity : com.astir_trotter.atcustom.ui.activity.SplashActivity() {

    override fun getNextActivityClass(): Class<*>? {
        return MainActivity::class.java
    }

    override fun getDelayDuration(): Long {
        return 1000 // TESTCODE
        //        return super.getDelayDuration();
    }
}
