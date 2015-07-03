package cn.limw.summer.spring.web.servlet.mvc.handler.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author li
 * @version 1 (2015年1月22日 下午2:20:41)
 * @since Java7
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @see cn.limw.summer.spring.web.servlet.mvc.handler.mapping.ExtendableHandlerMapping
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface At {
    String[] value() default {};

    RequestMethod[] method() default {};

    String[] params() default {};

    String[] headers() default {};

    String[] consumes() default {};

    String[] produces() default {};
}