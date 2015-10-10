package summer.converter.impl;

import summer.converter.AbstractConverter;

/**
 * @author li
 * @version 1 (2015年10月10日 下午3:17:59)
 * @since Java7
 */
public class StringToLongConverter extends AbstractConverter<String, Long> {
    public Long convert(String source) {
        return Long.parseLong(source);
    }
}