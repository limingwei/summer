package cn.limw.summer.time;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.limw.summer.util.Asserts;

/**
 * 时间
 * @author li
 * @version 1 (2015年4月16日 上午9:25:51)
 * @since Java7
 */
public class ClockTime implements Serializable {
    private static final long serialVersionUID = 9173240684927424044L;

    private Date date;

    public ClockTime(Date date) {
        this.date = Asserts.noNull(date, "传入时间 date 不可以为空");
    }

    public Date getDate() {
        return date;
    }

    public Long milliSeconds() {
        return getDate().getTime();
    }

    public Date toDate() {
        return new Date(milliSeconds());
    }

    public Time toSqlTime() {
        return new Time(milliSeconds());
    }

    public java.sql.Date toSqlDate() {
        return new java.sql.Date(milliSeconds());
    }

    public Timestamp toTimestamp() {
        return new Timestamp(milliSeconds());
    }

    public Calendar toCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate());
        return calendar;
    }

    /**
     * @param field 常量 如 Calendar.SECOND
     * @param amount
     */
    public ClockTime plus(Integer field, Integer amount) {
        Calendar calendar = toCalendar();
        calendar.add(field, amount);
        return new ClockTime(calendar.getTime());
    }

    /**
     * @param field 常量 如 Calendar.SECOND
     * @param value
     */
    public ClockTime set(Integer field, Integer value) {
        Calendar calendar = toCalendar();
        calendar.set(field, value);
        return new ClockTime(calendar.getTime());
    }

    public ClockTime setMilliSecond(Integer value) {
        return set(Calendar.MILLISECOND, value);
    }

    public ClockTime setSecond(Integer value) {
        return set(Calendar.SECOND, value);
    }

    public ClockTime setMinute(Integer value) {
        return set(Calendar.MINUTE, value);
    }

    public ClockTime setHour(Integer value) {
        return set(Calendar.HOUR_OF_DAY, value);
    }

    public ClockTime setDay(Integer value) {
        return set(Calendar.DATE, value);
    }

    public ClockTime setMonth(Integer value) {
        return set(Calendar.MONTH, value);
    }

    public ClockTime setYear(Integer value) {
        return set(Calendar.YEAR, value);
    }

    public ClockTime setDate(Integer year, Integer month, Integer day) {
        return setYear(year).setMonth(month).setDay(day);
    }

    public ClockTime setTime(Integer hour, Integer minute, Integer second) {
        return setHour(hour).setMinute(minute).setSecond(second);
    }

    public ClockTime setTime(Integer hour, Integer minute, Integer second, Integer milliSecond) {
        return setHour(hour).setMinute(minute).setSecond(second).setMilliSecond(milliSecond);
    }

    public ClockTime plusSecond(Integer second) {
        return plus(Calendar.SECOND, second);
    }

    public ClockTime plusMilliSecond(Integer milliSecond) {
        return plus(Calendar.MILLISECOND, milliSecond);
    }

    public ClockTime minusSecond(Integer second) {
        return plusSecond(-second);
    }

    public ClockTime plusMinute(Integer minutes) {
        return plus(Calendar.MINUTE, minutes);
    }

    public ClockTime minusMinute(Integer minutes) {
        return plusMinute(-minutes);
    }

    public ClockTime plusHour(Integer amount) {
        return plus(Calendar.HOUR_OF_DAY, amount);
    }

    public ClockTime minusHour(Integer amount) {
        return plusHour(-amount);
    }

    public ClockTime plusDay(Integer days) {
        return plus(Calendar.DATE, days);
    }

    public ClockTime minusDay(Integer days) {
        return plusDay(-days);
    }

    public ClockTime plusMonth(Integer month) {
        return plus(Calendar.MONTH, month);
    }

    public ClockTime minusMonth(Integer month) {
        return plusMonth(-month);
    }

    public ClockTime plusYear(Integer year) {
        return plus(Calendar.YEAR, year);
    }

    public ClockTime minusYear(Integer year) {
        return plusYear(-year);
    }

    public String toString(String format, Locale locale) {
        return DateFormatPool.INSTANCE.getDateFormat(format, locale).format(getDate());
    }

    public String toString(String format) {
        return toString(format, Locale.getDefault());
    }

    public String toString() {
        return super.toString() + " " + toString("yyyy-MM-dd HH:mm:ss SSS");
    }

    /**
     * 格式化为 yyyy-MM-dd HH:mm:ss 格式
     */
    public String toYYYY_MM_DD_HH_MM_SS() {
        return toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化为 yyyy-MM-dd 格式
     */
    public String toYYYY_MM_DD() {
        return toString("yyyy-MM-dd");
    }

    /**
     * 格式化为 HH:mm:ss 格式
     */
    public String toHH_MM_SS() {
        return toString("HH:mm:ss");
    }

    /**
     * 格式化为 HH:mm:ss 格式
     */
    public String toHH_MM() {
        return toString("HH:mm");
    }

    @SuppressWarnings("unchecked")
    public <T> T toType(Class<T> type) {
        if (type.equals(Date.class)) {
            return (T) toDate();
        } else if (type.equals(java.sql.Date.class)) {
            return (T) toSqlDate();
        } else if (type.equals(Timestamp.class)) {
            return (T) toTimestamp();
        } else if (type.equals(Time.class)) {
            return (T) toSqlTime();
        } else if (type.equals(Calendar.class)) {
            return (T) toCalendar();
        } else {
            throw new IllegalArgumentException("Unsupported Time Type " + type.getName());
        }
    }

    public Boolean before(ClockTime clockTime) {
        return milliSeconds() < clockTime.milliSeconds();
    }

    public Boolean after(ClockTime clockTime) {
        return milliSeconds() > clockTime.milliSeconds();
    }

    /**
     * 完整比较日期时间
     */
    public Boolean between(ClockTime from, ClockTime to) {
        return from.before(this) && this.before(to);
    }

    /**
     * 只比较日期部分
     */
    public Boolean dateBetween(ClockTime from, ClockTime to) {
        ClockTime now = setTime(1, 1, 1); // 统一时间
        from = from.setTime(1, 1, 1);
        to = to.setTime(1, 1, 1);

        return from.before(now) && now.before(to);
    }

    /**
     * 只比较时间部分, 忽略日期部分, 如to小则为次日
     */
    public Boolean timeBetween(ClockTime from, ClockTime to) {
        ClockTime now = setDate(1999, 9, 9); // 统一日期
        from = from.setDate(1999, 9, 9);
        to = to.setDate(1999, 9, 9);

        if (to.after(from)) { // to大 to为当日
            return from.before(now) && now.before(to);
        } else { // from大 to为次日
            return from.before(now) && now.before(to.plusDay(1));
        }
    }

    /**
     * 计算指定两个时间之间的相差分钟数。如果 this 晚于clockTime，则返回负值
     * @param clockTime 较晚时间
     * @return 分钟差
     */
    public Long minutesBetween(ClockTime clockTime) {
        return secondsBetween(clockTime) / 60;
    }

    public Long secondsBetween(ClockTime clockTime) {
        return milliSecondsBetween(clockTime) / 1000;
    }

    /**
     * 计算指定两个时间之间的相差毫秒数。如果 this 晚于clockTime，则返回负值
     * @param clockTime 较晚时间
     * @return 毫秒差
     */
    public Long milliSecondsBetween(ClockTime clockTime) {
        return clockTime.milliSeconds() - milliSeconds();
    }

    public Integer getDayOfWeek() {
        return toCalendar().get(Calendar.DAY_OF_WEEK);
    }
}