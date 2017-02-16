package io.github.droidkaigi.confsched2017.util;

import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.temporal.ChronoUnit;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    private static final String FORMAT_MMDD = "MMMd";

    private static final String FORMAT_KKMM = "kk:mm";

    private static final String FORMAT_YYYYMMDDKKMM = "yyyyMMMdkkmm";

    private static final String FORMAT_PROGRAM_START_DATE = "MM/dd(E) kk:mm";

    private DateUtil() {
        throw new AssertionError("no instance!");
    }

    @NonNull
    private static Date convertZoneDateTime(ZonedDateTime date) {
        return DateTimeUtils.toDate(date.toInstant());
    }

    @NonNull
    public static String getMonthDate(ZonedDateTime zonedDateTime, Context context) {
        Date date = convertZoneDateTime(zonedDateTime);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            String pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), FORMAT_MMDD);
            return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
        } else {
            int flag = DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_NO_YEAR;
            return DateUtils.formatDateTime(context, date.getTime(), flag);
        }
    }

    @NonNull
    public static String getHourMinute(ZonedDateTime zonedDateTime) {
        Date date = convertZoneDateTime(zonedDateTime);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            String pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), FORMAT_KKMM);
            return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
        } else {
            return String.valueOf(DateFormat.format(FORMAT_KKMM, date));
        }
    }

    @NonNull
    public static String getLongFormatDate(@Nullable ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return "";
        }
        Date date = convertZoneDateTime(zonedDateTime);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            String pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), FORMAT_YYYYMMDDKKMM);
            return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
        } else {
            java.text.DateFormat dayOfWeekFormat =
                    java.text.DateFormat.getDateInstance(java.text.DateFormat.LONG);
            java.text.DateFormat shortTimeFormat =
                    java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT);
            dayOfWeekFormat.setTimeZone(TimeZone.getDefault());
            shortTimeFormat.setTimeZone(TimeZone.getDefault());
            return dayOfWeekFormat.format(date) + " " + shortTimeFormat.format(date);
        }
    }

    public static int getMinutes(ZonedDateTime stime, ZonedDateTime etime) {
        return (int) ChronoUnit.MINUTES.between(stime, etime);
    }
}
