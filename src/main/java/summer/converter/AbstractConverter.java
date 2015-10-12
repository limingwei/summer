package summer.converter;

import java.lang.reflect.Type;

import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月10日 下午3:32:12)
 * @param <From>
 * @param <To>
 * @since Java7
 */
@SuppressWarnings("unchecked")
public abstract class AbstractConverter<From, To> implements Converter<From, To> {
    public Class<From> getFromType() {
        Type[] actualTypeArguments = Reflect.getGenericSuperclassActualTypeArguments(getClass());
        Type type = actualTypeArguments[0];
        Class<From> typeFrom = (Class<From>) type;
        return typeFrom;
    }

    public Class<To> getToType() {
        Type[] actualTypeArguments = Reflect.getGenericSuperclassActualTypeArguments(getClass());
        Type type = actualTypeArguments[1];
        Class<To> typeTo = (Class<To>) type;
        return typeTo;
    }
}