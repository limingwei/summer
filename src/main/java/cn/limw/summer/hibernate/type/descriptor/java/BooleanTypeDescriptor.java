package cn.limw.summer.hibernate.type.descriptor.java;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

import cn.limw.summer.util.BoolUtil;

/**
 * @author li
 * @version 1 (2015年1月28日 上午9:45:56)
 * @since Java7
 * @see org.hibernate.type.descriptor.java.BooleanTypeDescriptor
 */
public class BooleanTypeDescriptor extends AbstractTypeDescriptor {
    private static final long serialVersionUID = 7048376355145770731L;

    public static final BooleanTypeDescriptor INSTANCE = new BooleanTypeDescriptor();

    private final char characterValueTrue;

    private final char characterValueFalse;

    private final char characterValueTrueLC;

    private final String stringValueTrue;

    private final String stringValueFalse;

    public BooleanTypeDescriptor() {
        this('Y', 'N');
    }

    public BooleanTypeDescriptor(char characterValueTrue, char characterValueFalse) {
        super(Boolean.class);
        this.characterValueTrue = Character.toUpperCase(characterValueTrue);
        this.characterValueFalse = Character.toUpperCase(characterValueFalse);

        characterValueTrueLC = Character.toLowerCase(characterValueTrue);

        stringValueTrue = String.valueOf(characterValueTrue);
        stringValueFalse = String.valueOf(characterValueFalse);
    }

    public Boolean fromString(String string) {
        return Boolean.valueOf(string);
    }

    private boolean isTrue(char charValue) {
        return charValue == characterValueTrue || charValue == characterValueTrueLC;
    }

    public int toInt(Boolean value) {
        return value ? 1 : 0;
    }

    public Byte toByte(Boolean value) {
        return (byte) toInt(value);
    }

    public Short toShort(Boolean value) {
        return (short) toInt(value);
    }

    public Integer toInteger(Boolean value) {
        return toInt(value);
    }

    public Long toLong(Boolean value) {
        return (long) toInt(value);
    }

    public String toString(Object value) {
        return value == null ? null : value.toString();
    }

    public Object unwrap(Object value, Class type, WrapperOptions options) {
        return unwrap_2(BoolUtil.toBool(value), type, options);
    }

    public Object wrap(Object value, WrapperOptions options) {
        return wrap_2(value, options);
    }

    @SuppressWarnings({ "unchecked" })
    public <X> X unwrap_2(Boolean value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (Boolean.class.isAssignableFrom(type)) {
            return (X) value;
        }
        if (Byte.class.isAssignableFrom(type)) {
            return (X) toByte(value);
        }
        if (Short.class.isAssignableFrom(type)) {
            return (X) toShort(value);
        }
        if (Integer.class.isAssignableFrom(type)) {
            return (X) toInteger(value);
        }
        if (Long.class.isAssignableFrom(type)) {
            return (X) toInteger(value);
        }
        if (Character.class.isAssignableFrom(type)) {
            return (X) Character.valueOf(value ? characterValueTrue : characterValueFalse);
        }
        if (String.class.isAssignableFrom(type)) {
            return (X) (value ? stringValueTrue : stringValueFalse);
        }
        throw unknownUnwrap(type);
    }

    public <X> Boolean wrap_2(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (Boolean.class.isInstance(value)) {
            return (Boolean) value;
        }
        if (Number.class.isInstance(value)) {
            final int intValue = ((Number) value).intValue();
            return intValue == 0 ? FALSE : TRUE;
        }
        if (Character.class.isInstance(value)) {
            return isTrue((Character) value) ? TRUE : FALSE;
        }
        if (String.class.isInstance(value)) {
            return isTrue(((String) value).charAt(0)) ? TRUE : FALSE;
        }
        throw unknownWrap(value.getClass());
    }
}