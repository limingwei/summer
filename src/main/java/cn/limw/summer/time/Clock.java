package cn.limw.summer.time;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

/**
 * 时钟
 * @author li
 * @version 1 (2015年4月16日 上午9:25:30)
 * @since Java7
 */
public class Clock {
    /**
     * 当前时间
     */
    public static ClockTime now() {
        return when(new Date());
    }

    public static Timestamp nowTimestamp() {
        return now().toTimestamp();
    }

    /**
     * new ClockTime()
     */
    public static ClockTime when(Date date) {
        return null == date ? NullClockTime.INSTANCE : new ClockTime(date);
    }

    public static ClockTime when(Number time) {
        return null == time ? NullClockTime.INSTANCE : when(new Date(time.longValue()));
    }

    public static ClockTime when(String value, DateFormat dateFormat) {
        return null == value ? NullClockTime.INSTANCE : when(DateFormatUtil.parse(dateFormat, value));
    }

    public static ClockTime when(String value, String dateFormat) {
        return when(value, DateFormatPool.INSTANCE.getDateFormat(dateFormat));
    }

    /**
     * 今日指定时间
     * @param hour
     * @param minute
     * @param second
     */
    public static ClockTime today(Integer hour, Integer minute, Integer second) {
        return now().setTime(hour, minute, second);
    }
}