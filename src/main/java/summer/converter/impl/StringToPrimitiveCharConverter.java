package summer.converter.impl;

import summer.converter.AbstractConverter;

/**
 * @author li
 * @version 1 (2015年10月14日 下午6:52:56)
 * @since Java7
 */
@SuppressWarnings("rawtypes")
public class StringToPrimitiveCharConverter extends AbstractConverter {
    public Class getFromType() {
        return String.class;
    }

    public Class getToType() {
        return char.class;
    }

    public Object convert(Object source) {
        return (null == source || ((String) source).isEmpty()) ? 0 : ((String) source).charAt(0);
    }
}