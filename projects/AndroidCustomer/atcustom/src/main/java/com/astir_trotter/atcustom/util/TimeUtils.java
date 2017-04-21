/**
 * @author - Yonis Larsson
 * @contact - yonis.larsson.biz@gmail.com
 * @date - 2/24/17
 */
package com.astir_trotter.atcustom.util;

import java.util.concurrent.TimeUnit;

public class TimeUtils {
    private static final String TAG = TimeUtils.class.getSimpleName();

    public static long curTime() {
        return System.currentTimeMillis();
    }

    public static long distance(long oldT) {
        return distance(oldT, curTime());
    }

    public static long distance(long oldT, long newT) {
        return Math.max(0, newT - oldT);
    }

    public static long changeUnitToMin(long duration) {
        return changeUnit(duration, TimeUnit.MINUTES);
    }

    public static long changeUnit(long duration, TimeUnit timeUnit) {
        duration = (duration + 999) / 1000;
        if (timeUnit == TimeUnit.SECONDS)
            return duration;

        duration = (duration + 59) / 60;
        if (timeUnit == TimeUnit.MINUTES)
            return duration;

        duration = (duration + 59) / 60;
        if (timeUnit == TimeUnit.HOURS)
            return duration;

        duration = (duration + 23) / 24;
        if (timeUnit == TimeUnit.DAYS)
            return duration;

        throw new IllegalArgumentException();
    }
}
