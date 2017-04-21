package com.astir_trotter.atcustom.component;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.astir_trotter.atcustom.R;
import com.astir_trotter.atcustom.util.ResourceUtils;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/11/17
 */

public class AppInfo {
    private static final String TAG = AppInfo.class.getSimpleName();

    public String appName;
    public String orgName;
    public String versionName;
    public int    buildNumber;

    public String appDescription;
    public String copyright;

    public AppInfo(Context context, String appName, String orgName, String appDescription, String copyright) {
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

    public static AppInfo getDefaultAppInfo(Context context) {
        return new AppInfo(
                context,
                ResourceUtils.getString(R.string.lib_name),        // app name
                ResourceUtils.getString(R.string.org_name),        // org name
                ResourceUtils.getString(R.string.app_description), // app description
                ResourceUtils.getString(R.string.copyright)        // copyright
        );
    }
}
