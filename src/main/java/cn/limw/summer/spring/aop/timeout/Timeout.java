package cn.limw.summer.spring.aop.timeout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解在一个方法上,控制方法执行超时时间
 * @author li
 * @version 1 (2015年3月18日 下午6:58:35)
 * @since Java7
 * @see cn.limw.summer.spring.aop.timeout.TimeoutInterceptor
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Timeout {
    int value() default -1;
}