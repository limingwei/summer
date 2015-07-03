package cn.limw.summer.spring.aop.callercheck;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author li
 * @version 1 (2015年2月6日 下午1:28:10)
 * @since Java7
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CallerCheck {
    String type() default "";

    String method() default "";
}