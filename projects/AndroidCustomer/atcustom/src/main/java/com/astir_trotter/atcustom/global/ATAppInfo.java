package com.astir_trotter.atcustom.global;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.astir_trotter.atcustom.R;
import com.astir_trotter.atcustom.utils.ResourceUtils;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/11/17
 */

public class ATAppInfo {
    private static final String TAG = ATAppInfo.class.getSimpleName();

    public String appName;
    public String orgName;
    public String versionName;
    public int    buildNumber;

    public String appDescription;
    public String copyright;

    public ATAppInfo(Context context, String appName, String orgName, String appDescription, String copyright) {
        this.appName = appName;
        this.orgName = orgName;
        this.appDescription = appDescription;
        this.copyright = copyright;

        calcVersion(context);
    }

    private void calcVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            this.versionName = packageInfo.versionName;
            this.buildNumber = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException ignored) {
            this.versionName = "Unknown";
            this.buildNumber = -1;
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static ATAppInfo getDefaultAppInfo(Context context) {
        return new ATAppInfo(
                context,
                ResourceUtils.getString(context, R.string.lib_name),        // app name
                ResourceUtils.getString(context, R.string.org_name),        // org name
                ResourceUtils.getString(context, R.string.app_description), // app description
                ResourceUtils.getString(context, R.string.copyright)        // copyright
        );
    }
}
