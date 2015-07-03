package cn.limw.summer.hibernate.type;

import java.io.Serializable;
import java.util.Comparator;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.DiscriminatorType;
import org.hibernate.type.PrimitiveType;
import org.hibernate.type.VersionType;

import cn.limw.summer.hibernate.type.descriptor.java.LongTypeDescriptor;
import cn.limw.summer.hibernate.type.descriptor.sql.BigIntTypeDescriptor;
import cn.limw.summer.util.Nums;

/**
 * @author li
 * @version 1 (2014年9月28日 上午10:17:38)
 * @since Java7
 * @see org.hibernate.type.LongType
 */
public class LongType extends AbstractSingleColumnStandardBasicType implements PrimitiveType, DiscriminatorType, VersionType {
    public static final LongType INSTANCE = new LongType();

    private static final Long ZERO = (long) 0;

    public LongType() {
        super(BigIntTypeDescriptor.INSTANCE, LongTypeDescriptor.INSTANCE);
    }

    public String getName() {
        return "long";
    }

    public String[] getRegistrationKeys() {
        return new String[] { getName(), long.class.getName(), Long.class.getName() };
    }

    public Serializable getDefaultValue() {
        return ZERO;
    }

    public Class getPrimitiveClass() {
        return long.class;
    }

    public Long stringToObject(String xml) throws Exception {
        return Long.valueOf(xml);
    }

    public Long seed(SessionImplementor session) {
        return ZERO;
    }

    public Comparator<Long> getComparator() {
        return getJavaTypeDescriptor().getComparator();
    }

    public String objectToSQLString(Object value, Dialect dialect) throws Exception {
        return value.toString();
    }

    public Object next(Object current, SessionImplementor session) {
        return Nums.toLong(current) + 1L;
    }
}