package summer.converter;

/**
 * @author li
 * @version 1 (2015年10月10日 下午3:32:12)
 * @param <From>
 * @param <To>
 * @since Java7
 */
public abstract class AbstractConverter<From, To> implements Converter<From, To> {
    public Class<From> getFromType() {
        return null;
    }

    public Class<To> getToType() {
        return null;
    }
}