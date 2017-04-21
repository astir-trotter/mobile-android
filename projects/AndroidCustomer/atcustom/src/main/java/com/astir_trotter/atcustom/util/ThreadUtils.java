/**
 * @author - Yonis Larsson
 * @contact - yonis.larsson.biz@gmail.com
 * @date - 3/3/17
 */
package com.astir_trotter.atcustom.util;

import android.os.Looper;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

public class ThreadUtils {
    private static final String TAG = ThreadUtils.class.getSimpleName();


    @WorkerThread
    @UiThread
    public static boolean isUIThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
