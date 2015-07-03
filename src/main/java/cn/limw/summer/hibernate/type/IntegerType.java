package cn.limw.summer.hibernate.type;

import java.io.Serializable;
import java.util.Comparator;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.DiscriminatorType;
import org.hibernate.type.PrimitiveType;
import org.hibernate.type.VersionType;

import cn.limw.summer.hibernate.type.descriptor.java.IntegerTypeDescriptor;

/**
 * @author li
 * @version 1 (2015年6月9日 下午2:05:50)
 * @since Java7
 */
public class IntegerType extends AbstractSingleColumnStandardBasicType<Integer> implements PrimitiveType<Integer>, DiscriminatorType<Integer>, VersionType<Integer> {
    private static final long serialVersionUID = 7969091679684780744L;

    public static final IntegerType INSTANCE = new IntegerType();

    public static final Integer ZERO = 0;

    public IntegerType() {
        super(cn.limw.summer.hibernate.type.descriptor.sql.IntegerTypeDescriptor.INSTANCE, IntegerTypeDescriptor.INSTANCE);
    }

    public String getName() {
        return "integer";
    }

    public String[] getRegistrationKeys() {
        return new String[] { getName(), int.class.getName(), Integer.class.getName() };
    }

    public Serializable getDefaultValue() {
        return ZERO;
    }

    public Class getPrimitiveClass() {
        return int.class;
    }

    public String objectToSQLString(Integer value, Dialect dialect) throws Exception {
        return toString(value);
    }

    public Integer stringToObject(String xml) {
        return fromString(xml);
    }

    public Integer seed(SessionImplementor session) {
        return ZERO;
    }

    public Integer next(Integer current, SessionImplementor session) {
        return current + 1;
    }

    public Comparator<Integer> getComparator() {
        return getJavaTypeDescriptor().getComparator();
    }
}
