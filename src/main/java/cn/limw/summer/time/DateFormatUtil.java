package cn.limw.summer.time;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.util.Date;

import cn.limw.summer.java.text.exception.ParseRuntimeException;

/**
 * @author li
 */
public class DateFormatUtil {
    /**
     * @see java.text.DateFormat#parse(String)
     */
    public static Date parse(DateFormat dateFormat, String source) {
        ParsePosition pos = new ParsePosition(0);
        Date result = dateFormat.parse(source, pos);
        if (pos.getIndex() == 0) {
            throw new ParseRuntimeException("Unparseable date: \"" + source + "\"", pos.getErrorIndex());
        }
        return result;
    }
}