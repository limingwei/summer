package cn.limw.summer.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author li
 * @version 1 (2015年4月29日 下午4:43:43)
 * @since Java7
 */
public class DateFormatPool {
    public static final DateFormatPool INSTANCE = new DateFormatPool();

    public static final DateFormat YYYY_MM_DD = INSTANCE.getDateFormat("yyyy-MM-dd");

    public static final DateFormat HH_MM_SS = INSTANCE.getDateFormat("HH:mm:ss");

    public static final DateFormat YYYY_MM_DD_HH_MM_SS = INSTANCE.getDateFormat("yyyy-MM-dd HH:mm:ss");

    private Map<String, DateFormat> dateFormats = new HashMap<String, DateFormat>();

    public DateFormat getDateFormat(String format) {
        return getDateFormat(format, Locale.getDefault());
    }

    public synchronized DateFormat getDateFormat(String format, Locale locale) {
        DateFormat dateFormat = dateFormats.get(format);
        if (null == dateFormat) {
            dateFormats.put(format + "_" + locale, dateFormat = newDateFormat(format));
        }
        return dateFormat;
    }

    private DateFormat newDateFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }
}