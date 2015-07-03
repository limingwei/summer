package cn.limw.summer.spring.web.interceptor.sessioncheck;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author li
 * @version 1 (2015年1月8日 下午4:44:32)
 * @since Java7
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionCheck {
    String name() default "";

    String redirect() default "";

    boolean check() default true;
}