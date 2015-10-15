package summer.basic.order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 越大越靠后
 * @author li
 * @version 1 (2015年10月14日 下午9:33:09)
 * @since Java7
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Inherited
public @interface Order {
    int value() default Integer.MAX_VALUE;
}