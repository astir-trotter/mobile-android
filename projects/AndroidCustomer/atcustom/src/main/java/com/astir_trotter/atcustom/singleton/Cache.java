package com.astir_trotter.atcustom.singleton;

import android.content.Context;
import android.support.annotation.NonNull;

import com.astir_trotter.atcustom.component.AppInfo;
import com.astir_trotter.atcustom.singleton.lang.base.Language;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/11/17
 */

public class Cache {
    private static final String TAG = Cache.class.getSimpleName();
    public static Class<?> sCacheClass = Cache.class;

    private static Cache _instance = null;

    public static Cache getInstance() {
        if (_instance == null) {
            try {
                _instance = (Cache) sCacheClass.newInstance();
            } catch (InstantiationException e) {
                _instance = new Cache();
            } catch (IllegalAccessException e) {
                _instance = new Cache();
            }
        }

        return _instance;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean mDebug;
    private Context mContext;
    private AppInfo mAppInfo;
    private Language mLanguage;

    private Cache() {
        mLanguage = Language.English;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        if (mContext == null)
            throw new IllegalStateException("No context registered yet.");

        return mContext;
    }

    public void setAppInfo(@NonNull AppInfo appInfo) {
        mAppInfo = appInfo;
    }

    public AppInfo getAppInfo() {
        if (mAppInfo == null)
            throw new IllegalStateException("No app info registered yet.");

        return mAppInfo;
    }

    public boolean isDebug() {
        return mDebug;
    }

    public void setDebug(boolean debug) {
        this.mDebug = debug;
    }

    public Language getLanguage() {
        return mLanguage;
    }

    public void setLanguage(Language language) {
        this.mLanguage = language;
    }
}
