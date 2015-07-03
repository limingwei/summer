package cn.limw.summer.hibernate.type.descriptor.java;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

import cn.limw.summer.util.Nums;

/**
 * @author li
 * @version 1 (2015年6月9日 下午2:16:40)
 * @since Java7
 */
public class IntegerTypeDescriptor extends AbstractTypeDescriptor {
    private static final long serialVersionUID = -7361567503643844035L;

    public static final IntegerTypeDescriptor INSTANCE = new IntegerTypeDescriptor();

    public IntegerTypeDescriptor() {
        super(Integer.class);
    }

    public Integer fromString(String string) {
        return string == null ? null : Integer.valueOf(string);
    }

    public Object unwrap(Object value, Class type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        Integer intValue = Nums.toInt(value);

        if (Integer.class.isAssignableFrom(type)) {
            return intValue;
        }
        if (Byte.class.isAssignableFrom(type)) {
            return Byte.valueOf(intValue.byteValue());
        }
        if (Short.class.isAssignableFrom(type)) {
            return Short.valueOf(intValue.shortValue());
        }
        if (Long.class.isAssignableFrom(type)) {
            return Long.valueOf(intValue.longValue());
        }
        if (Double.class.isAssignableFrom(type)) {
            return Double.valueOf(intValue.doubleValue());
        }
        if (Float.class.isAssignableFrom(type)) {
            return Float.valueOf(intValue.floatValue());
        }
        if (BigInteger.class.isAssignableFrom(type)) {
            return BigInteger.valueOf(intValue);
        }
        if (BigDecimal.class.isAssignableFrom(type)) {
            return BigDecimal.valueOf(intValue);
        }
        if (String.class.isAssignableFrom(type)) {
            return intValue.toString();
        }
        throw unknownUnwrap(type);
    }

    public String toString(Object value) {
        return value == null ? null : value.toString();
    }

    public Object wrap(Object value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (Integer.class.isInstance(value)) {
            return (Integer) value;
        }
        if (Number.class.isInstance(value)) {
            return ((Number) value).intValue();
        }
        if (String.class.isInstance(value)) {
            return Integer.valueOf(((String) value));
        }
        throw unknownWrap(value.getClass());
    }
}
