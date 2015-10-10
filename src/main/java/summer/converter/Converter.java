package summer.converter;

/**
 * @author li
 * @version 1 (2015年10月10日 下午3:14:15)
 * @since Java7
 */
public interface Converter<From, To> {
    public Class<From> getFromType();

    public Class<To> getToType();

    public To convert(From source);
}