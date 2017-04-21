package com.astir_trotter.atcustom.util;

import android.util.Log;

import com.astir_trotter.atcustom.singleton.Cache;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 */

public class LogHelper {

    public static void log(String tag, String content) {
        if (!Cache.getInstance().isDebug())
            return;

        Log.d(tag, content);
    }

}
