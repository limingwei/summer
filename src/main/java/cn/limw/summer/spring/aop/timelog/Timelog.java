package cn.limw.summer.spring.aop.timelog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author li
 * @version 1 (2015年3月19日 下午4:57:23)
 * @since Java7
 * @see cn.limw.summer.spring.aop.timelog.TimelogInterceptor
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Timelog {
    int min() default -1;
}