package summer.converter.impl;

import summer.converter.AbstractConverter;

/**
 * @author li
 * @version 1 (2015年10月10日 下午3:14:57)
 * @since Java7
 */
public class StringToIntegerConverter extends AbstractConverter<String, Integer> {
    public Integer convert(String source) {
        return (null == source || source.isEmpty()) ? null : Integer.parseInt(source);
    }
}