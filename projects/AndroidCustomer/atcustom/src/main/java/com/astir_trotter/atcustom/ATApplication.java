package com.astir_trotter.atcustom;

import android.app.Application;
import android.support.annotation.NonNull;

import com.astir_trotter.atcustom.component.AppInfo;
import com.astir_trotter.atcustom.singleton.Cache;
import com.astir_trotter.atcustom.singleton.lang.MultiLangStringRes;
import com.astir_trotter.atcustom.singleton.lang.base.Language;
import com.astir_trotter.atcustom.ui.activity.base.AutoCrashReporter;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/11/17
 */

public abstract class ATApplication extends Application {
    private static final String TAG = ATApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        // set base app info
        Cache.getInstance().setContext(this);
        Cache.getInstance().setAppInfo(getAppInfo());

        // initializae language info - string res
        initMultiLangStringRes();

        // initializae language info - string res
        initMultiThemeColorRes();

        // if debug mode, start auto crash reporter.
        if (isDebug()) {
            Cache.getInstance().setDebug(true);
            AutoCrashReporter.get(this)
                    .setEmailAddresses(getDeveloperEmailAddress())
                    .setEmailSubject(getSubjectForAutoCrashRepot())
                    .start();
        }

    }

    protected boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @NonNull
    protected String[] getDeveloperEmailAddress() {
        return new String[]{"yonis.larsson.biz@gmail.com"};
    }

    protected String getSubjectForAutoCrashRepot() {
        return MultiLangStringRes.getInstance().get(R.string.crashreport_title);
    }

    @NonNull
    protected AppInfo getAppInfo() {
        return AppInfo.getDefaultAppInfo(this);
    }

    /**
     * Initialize language info.
     * Create languages and register string res according to its language.
     */
    protected void initMultiLangStringRes() {
        MultiLangStringRes.getInstance().getStringRes(Language.Korean)
                .putRepeat(android.R.string.ok, "확인")
                .putRepeat(android.R.string.cancel, "취소")
                .putRepeat(android.R.string.yes, "예")
                .putRepeat(android.R.string.no, "아니")
                .putRepeat(R.string.crashreport_title, "오류발생")
                .putRepeat(R.string.crashreport_description, "개발자에게로 오류내용을 전송하여 수정하겠습니까?");
    }

    /**
     * Initialize theme info.
     * Create themes and register color res according to its theme.
     */
    protected void initMultiThemeColorRes() {

    }

}
