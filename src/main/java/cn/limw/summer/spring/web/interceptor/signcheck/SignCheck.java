package cn.limw.summer.spring.web.interceptor.signcheck;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author li
 * @version 1 (2015年1月8日 下午4:44:32)
 * @since Java7
 * @see cn.limw.summer.spring.web.interceptor.signcheck.SignCheckInterceptor
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SignCheck {
    String[] value() default {};

    boolean check() default true;
}