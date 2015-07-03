package cn.limw.summer.time;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author li
 * @version 1 (2015年5月4日 下午5:42:18)
 * @since Java7
 */
public class NullClockTime extends ClockTime {
    private static final long serialVersionUID = 2047820306835212363L;

    public static final NullClockTime INSTANCE = new NullClockTime();

    public NullClockTime() {
        super(new Date());
    }

    public Timestamp toTimestamp() {
        return null;
    }

    public Long milliSeconds() {
        return null;
    }
}