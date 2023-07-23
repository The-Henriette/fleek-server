package run.fleek.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public final class TimeUtil {

    private final static TimeZone timeZoneUtc = TimeZone.getTimeZone("UTC");
    public static long getCurrentTimeMillisUtc() {
        Calendar calendar = Calendar.getInstance(timeZoneUtc);
        long currentTimeMillisUtc = System.currentTimeMillis();

        calendar.setTimeInMillis(currentTimeMillisUtc);
        return calendar.getTimeInMillis();
    }

    public static long convertToEpochTime(String dobString) {
        // Parse the input string using the specified format (ddMMyy)
        SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
        Date dob = null;
        try {
            dob = format.parse(dobString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // Set the time zone to UTC
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        // Convert the date to UTC-0 epoch time in milliseconds
        return dob.getTime();
    }

}
