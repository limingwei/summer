package cn.limw.summer.hibernate.type;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.LiteralType;
import org.hibernate.type.StringType;
import org.hibernate.type.VersionType;
import org.hibernate.type.descriptor.sql.TimestampTypeDescriptor;

import cn.limw.summer.hibernate.type.descriptor.java.JdbcTimestampTypeDescriptor;

/**
 * 扩展Hibernate数据类型,使传入的时间参数可以为string(本来要求为date)
 * @author li
 * @version 1 (2014年8月12日 下午1:24:53)
 * @since Java7
 * @see cn.limw.summer.hibernate.type.descriptor.java.JdbcTimestampTypeDescriptor
 */
public class TimestampType extends AbstractSingleColumnStandardBasicType<Date> implements VersionType<Date>, LiteralType<Date> {
    private static final long serialVersionUID = -4400494738901427215L;

    public static final TimestampType INSTANCE = new TimestampType();

    public TimestampType() {
        super(TimestampTypeDescriptor.INSTANCE, JdbcTimestampTypeDescriptor.INSTANCE);
    }

    public String getName() {
        return "timestamp";
    }

    public String[] getRegistrationKeys() {
        return new String[] { getName(), Timestamp.class.getName(), java.util.Date.class.getName() };
    }

    public Date next(Date current, SessionImplementor session) {
        return seed(session);
    }

    public Date seed(SessionImplementor session) {
        return new Timestamp(System.currentTimeMillis());
    }

    public Comparator<Date> getComparator() {
        return getJavaTypeDescriptor().getComparator();
    }

    public String objectToSQLString(Date value, Dialect dialect) throws Exception {
        final Timestamp ts = Timestamp.class.isInstance(value) ? (Timestamp) value : new Timestamp(value.getTime());
        return StringType.INSTANCE.objectToSQLString(ts.toString(), dialect);
    }

    public Date fromStringValue(String xml) throws HibernateException {
        return fromString(xml);
    }
}
