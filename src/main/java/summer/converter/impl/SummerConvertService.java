package summer.converter.impl;

import java.util.ArrayList;
import java.util.List;

import summer.converter.ConvertService;
import summer.converter.Converter;

/**
 * @author li
 * @version 1 (2015年10月10日 下午3:26:50)
 * @since Java7
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SummerConvertService implements ConvertService {
    private List<Converter> converters;

    public SummerConvertService() {
        converters = new ArrayList<Converter>();
        converters.add(new StringToBooleanConverter());
        converters.add(new StringToIntegerConverter());
        converters.add(new StringToLongConverter());
    }

    public <From, To> To convert(Class<From> fromType, Class<To> toType, From fromValue) {
        if (fromType.equals(toType)) {
            return (To) fromValue;
        } else {
            for (Converter converter : converters) {
                if (converter.getFromType().equals(fromType) && converter.getToType().equals(toType)) {
                    return (To) converter.convert(fromValue);
                }
            }
            throw new RuntimeException("未找到合适的Converter, fromType=" + fromType + ", toType=" + toType);
        }
    }
}