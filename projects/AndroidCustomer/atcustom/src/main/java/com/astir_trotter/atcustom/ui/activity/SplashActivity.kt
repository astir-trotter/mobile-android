package com.astir_trotter.atcustom.ui.activity

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.util.Log
import android.view.View
import android.widget.TextView

import com.astir_trotter.atcustom.R
import com.astir_trotter.atcustom.component.AppInfo
import com.astir_trotter.atcustom.singleton.Cache

import java.text.MessageFormat

open class SplashActivity : BaseActivity() {

    private var mDelayedTransit: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate")

        if (contentView != null)
            setContentView(contentView)
        else if (contentLayout != 0)
            setContentView(contentLayout)
        else {
            setContentView(R.layout.activity_splash)

            val appInfo = Cache.instance.appInfo
            val title = MessageFormat.format("{0} {1}_{2}", appInfo.appName, appInfo.versionName, appInfo.buildNumber)
            (findViewById(R.id.app_name) as TextView).text = title
            (findViewById(R.id.app_description) as TextView).text = appInfo.appDescription
            (findViewById(R.id.copyright) as TextView).text = appInfo.copyright
            (findViewById(R.id.org_name) as TextView).text = appInfo.orgName
        }
    }

    override fun onPause() {
        super.onPause()

        if (mDelayedTransit != null) {
            handler.removeCallbacks(mDelayedTransit)
        }
    }

    override fun onResume() {
        super.onResume()

        fullscreen()

        if (nextActivityClass != null) {
            mDelayedTransit = Runnable { transit() }
            handler.postDelayed(mDelayedTransit, delayDuration)
        } else
            mDelayedTransit = null
    }

    private fun transit() {
        assert(nextActivityClass != null)
        transit(nextActivityClass!!, true)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // getter

    protected val contentView: View?
        get() = null

    protected val contentLayout: Int
        @LayoutRes
        get() = 0

    protected open val nextActivityClass: Class<*>?
        get() = null

    protected open val delayDuration: Long
        get() = DEFAULT_DELAY_DURATION

    companion object {
        private val TAG = SplashActivity::class.java.simpleName
        private val DEFAULT_DELAY_DURATION: Long = 5000
    }

}
