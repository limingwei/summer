package summer.converter;

/**
 * @author li
 * @version 1 (2015年10月10日 下午3:26:26)
 * @since Java7
 */
public interface ConvertService {
    public <From, To> To convert(Class<From> fromType, Class<To> toType, From fromValue);
}