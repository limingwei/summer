package cn.limw.summer.spring.web.servlet.mvc.handler.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author li
 * @version 1 (2015年1月22日 下午3:26:26)
 * @since Java7
 * @see cn.limw.summer.spring.web.servlet.mvc.handler.mapping.ExtendableHandlerMapping
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Map {
    String value() default "{}";
}