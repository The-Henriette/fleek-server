package run.fleek.utils;

import java.util.Calendar;
import java.util.TimeZone;

public final class TimeUtil {

    private final static TimeZone timeZoneUtc = TimeZone.getTimeZone("UTC");
    public static long getCurrentTimeMillisUtc() {
        Calendar calendar = Calendar.getInstance(timeZoneUtc);
        long currentTimeMillisUtc = System.currentTimeMillis();

        calendar.setTimeInMillis(currentTimeMillisUtc);
        return calendar.getTimeInMillis();
    }
}
