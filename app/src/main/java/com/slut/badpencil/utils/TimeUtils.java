package com.slut.badpencil.utils;

import com.slut.badpencil.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by shijianan on 2017/1/6.
 */

public class TimeUtils {

    private static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";

    public static String stamp2Str(long stamp,String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINESE);
        Date date = new Date(stamp);
        return simpleDateFormat.format(date);
    }

    public static String calInterval(long startStamp, long endStamp) {
        if (endStamp < startStamp) {
            return ResUtils.getString(R.string.error_calculate_interval);
        }
        long interval = endStamp - startStamp;
        long oneSecond = 1000;
        long oneMinute = 60 * oneSecond;
        long oneHour = 60 * oneMinute;
        long oneDay = 24 * oneHour;
        if (interval < oneSecond) {
            return ResUtils.getString(R.string.interval_just_now);
        } else if (interval < oneMinute) {
            return interval / oneSecond + ResUtils.getString(R.string.interval_second);
        } else if (interval < oneHour) {
            return interval / oneMinute + ResUtils.getString(R.string.interval_minute);
        } else if (interval < oneDay) {
            return interval / oneHour + ResUtils.getString(R.string.interval_hour);
        } else {
            return stamp2Str(startStamp,FORMAT_DATE_TIME);
        }
    }

}
