package summer.converter.impl;

import summer.converter.AbstractConverter;

/**
 * @author li
 * @version 1 (2015年10月10日 下午3:18:10)
 * @since Java7
 */
public class StringToBooleanConverter extends AbstractConverter<String, Boolean> {
    public Boolean convert(String source) {
        return Boolean.parseBoolean(source);
    }
}