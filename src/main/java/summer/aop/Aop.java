package summer.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author li
 * @version 1 (2015年10月11日 上午9:46:24)
 * @since Java7
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Aop {
    /**
     * AopBeanName
     */
    String[] value();
}