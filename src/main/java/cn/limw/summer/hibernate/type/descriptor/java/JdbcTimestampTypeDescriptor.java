package cn.limw.summer.hibernate.type.descriptor.java;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.HibernateException;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;

import cn.limw.summer.time.Clock;
import cn.limw.summer.time.DateFormatPool;

/**
 * @author li
 * @version 1 (2014年8月12日 下午1:40:12)
 * @since Java7
 * @see cn.limw.summer.hibernate.type.TimestampType
 */
public class JdbcTimestampTypeDescriptor extends AbstractTypeDescriptor {
    private static final long serialVersionUID = -7768104170598643675L;

    public static final JdbcTimestampTypeDescriptor INSTANCE = new JdbcTimestampTypeDescriptor();

    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static class TimestampMutabilityPlan extends MutableMutabilityPlan<Date> {
        private static final long serialVersionUID = 6249719243940955214L;

        public static final TimestampMutabilityPlan INSTANCE = new TimestampMutabilityPlan();

        public Date deepCopyNotNull(Date value) {
            if (value instanceof Timestamp) {
                Timestamp orig = (Timestamp) value;
                Timestamp ts = new Timestamp(orig.getTime());
                ts.setNanos(orig.getNanos());
                return ts;
            } else {
                return new Date(value.getTime());
            }
        }
    }

    public JdbcTimestampTypeDescriptor() {
        super(Date.class, TimestampMutabilityPlan.INSTANCE);
    }

    public Date fromString(String string) {
        try {
            return new Timestamp(new SimpleDateFormat(TIMESTAMP_FORMAT).parse(string).getTime());
        } catch (ParseException pe) {
            throw new HibernateException("could not parse timestamp string" + string, pe);
        }
    }

    public boolean areEqual(Date one, Date another) {
        if (one == another) {
            return true;
        }
        if (one == null || another == null) {
            return false;
        }

        long t1 = one.getTime();
        long t2 = another.getTime();

        boolean oneIsTimestamp = Timestamp.class.isInstance(one);
        boolean anotherIsTimestamp = Timestamp.class.isInstance(another);

        int n1 = oneIsTimestamp ? ((Timestamp) one).getNanos() : 0;
        int n2 = anotherIsTimestamp ? ((Timestamp) another).getNanos() : 0;

        if (t1 != t2) {
            return false;
        }

        if (oneIsTimestamp && anotherIsTimestamp) {
            int nn1 = n1 % 1000000;
            int nn2 = n2 % 1000000;
            return nn1 == nn2;
        } else {
            return true;
        }
    }

    public int extractHashCode(Date value) {
        return Long.valueOf(value.getTime() / 1000).hashCode();
    }

    public <X> X unwrap2(Date value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (Timestamp.class.isAssignableFrom(type)) {
            final Timestamp rtn = Timestamp.class.isInstance(value) ? (Timestamp) value : new Timestamp(value.getTime());
            return (X) rtn;
        }
        if (java.sql.Date.class.isAssignableFrom(type)) {
            final java.sql.Date rtn = java.sql.Date.class.isInstance(value) ? (java.sql.Date) value : new java.sql.Date(value.getTime());
            return (X) rtn;
        }
        if (java.sql.Time.class.isAssignableFrom(type)) {
            final java.sql.Time rtn = java.sql.Time.class.isInstance(value) ? (java.sql.Time) value : new java.sql.Time(value.getTime());
            return (X) rtn;
        }
        if (Date.class.isAssignableFrom(type)) {
            return (X) value;
        }
        if (Calendar.class.isAssignableFrom(type)) {
            final GregorianCalendar cal = new GregorianCalendar();
            cal.setTimeInMillis(value.getTime());
            return (X) cal;
        }
        if (Long.class.isAssignableFrom(type)) {
            return (X) Long.valueOf(value.getTime());
        }
        throw unknownUnwrap(type);
    }

    public <X> Date wrap2(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (Timestamp.class.isInstance(value)) {
            return (Timestamp) value;
        }

        if (Long.class.isInstance(value)) {
            return new Timestamp((Long) value);
        }

        if (Calendar.class.isInstance(value)) {
            return new Timestamp(((Calendar) value).getTimeInMillis());
        }

        if (Date.class.isInstance(value)) {
            return (Date) value;
        }

        throw unknownWrap(value.getClass());
    }

    public String toString2(Date value) {
        return new SimpleDateFormat(TIMESTAMP_FORMAT).format(value);
    }

    public Object unwrap(Object value, Class type, WrapperOptions options) {
        if (null != value && value instanceof Date) {
            return unwrap2((Date) value, type, options);
        } else if (null != value) {
            return Clock.when("" + value, DateFormatPool.YYYY_MM_DD_HH_MM_SS).toType(type);
        } else {
            return null;
        }
    }

    public Object wrap(Object value, WrapperOptions options) {
        if (null != value && value instanceof Date) {
            return wrap2((Date) value, options);
        } else if (null != value) {
            throw new RuntimeException();
        } else {
            return null;
        }
    }

    public String toString(Object value) {
        if (null != value && value instanceof Date) {
            return toString2((Date) value);
        } else if (null != value) {
            throw new RuntimeException();
        } else {
            return null;
        }
    }
}