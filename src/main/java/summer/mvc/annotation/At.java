package summer.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:43:38)
 * @since Java7
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface At {
    public static final String GET = "GET";

    public static final String POST = "POST";

    String[] value();

    String[] method() default { GET };
}