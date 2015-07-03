package cn.limw.summer.hibernate.type.descriptor.java;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

import cn.limw.summer.util.Nums;

/**
 * @author li
 * @version 1 (2014年9月28日 上午10:11:18)
 * @since Java7
 */
public class LongTypeDescriptor extends AbstractTypeDescriptor {
    public static final LongTypeDescriptor INSTANCE = new LongTypeDescriptor();

    public LongTypeDescriptor() {
        super(Long.class);
    }

    public Long fromString(String string) {
        return Long.valueOf(string);
    }

    public Object unwrap(Object value, Class type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (Long.class.isAssignableFrom(type)) {
            return Nums.toLong(value);
        }
        if (Byte.class.isAssignableFrom(type)) {
            return Byte.valueOf(Nums.toLong(value).byteValue());
        }
        if (Short.class.isAssignableFrom(type)) {
            return Short.valueOf(Nums.toLong(value).shortValue());
        }
        if (Integer.class.isAssignableFrom(type)) {
            return Integer.valueOf(Nums.toLong(value).intValue());
        }
        if (Double.class.isAssignableFrom(type)) {
            return Double.valueOf(Nums.toLong(value).doubleValue());
        }
        if (Float.class.isAssignableFrom(type)) {
            return Float.valueOf(Nums.toLong(value).floatValue());
        }
        if (BigInteger.class.isAssignableFrom(type)) {
            return BigInteger.valueOf(Nums.toLong(value));
        }
        if (BigDecimal.class.isAssignableFrom(type)) {
            return BigDecimal.valueOf(Nums.toLong(value));
        }
        if (String.class.isAssignableFrom(type)) {
            return value.toString();
        }
        throw unknownUnwrap(type);
    }

    public Object wrap(Object value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (Long.class.isInstance(value)) {
            return (Long) value;
        }
        if (Number.class.isInstance(value)) {
            return ((Number) value).longValue();
        } else if (String.class.isInstance(value)) {
            return Long.valueOf(((String) value));
        }
        throw unknownWrap(value.getClass());
    }

    public String toString(Object value) {
        return value == null ? null : value.toString();
    }
}
