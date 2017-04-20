package com.astir_trotter.atcustom.global;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/11/17
 */

public class ATCache {
    private static final String TAG = ATCache.class.getSimpleName();
    public static Class<?> sCacheClass = ATCache.class;

    private static ATCache _instance = null;

    public static ATCache getInstance() {
        if (_instance == null) {
            try {
                _instance = (ATCache) sCacheClass.newInstance();
            } catch (InstantiationException e) {
                _instance = new ATCache();
            } catch (IllegalAccessException e) {
                _instance = new ATCache();
            }
        }

        return _instance;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private Context mContext;
    private ATAppInfo mAppInfo;

    public void setContext(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        if (mContext == null)
            throw new IllegalStateException("No context registered yet.");

        return mContext;
    }

    public void setAppInfo(@NonNull ATAppInfo appInfo) {
        mAppInfo = appInfo;
    }

    public ATAppInfo getAppInfo() {
        if (mAppInfo == null)
            throw new IllegalStateException("No app info registered yet.");

        return mAppInfo;
    }
}
