package com.astir_trotter.atcustom.global;

import android.content.Context;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/11/17
 */

public class Cache {
    private static final String TAG = Cache.class.getSimpleName();

    private static Cache _instance = null;

    public static Cache getInstance() {
        if (_instance == null)
            _instance = new Cache();

        return _instance;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private Context mContext;
    private AppInfo mAppInfo;

    public Cache() {
        mAppInfo = new AppInfo();
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        if (mContext == null)
            throw new IllegalStateException("No registered context yet.");

        return mContext;
    }

    public AppInfo getAppInfo() {
        return mAppInfo;
    }
}
