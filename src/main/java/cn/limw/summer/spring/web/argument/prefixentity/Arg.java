package cn.limw.summer.spring.web.argument.prefixentity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解在一个Action方法的对象类型参数上
 * @author li
 * @version 1 (2014年7月5日 上午10:29:17)
 * @since Java7
 * @see org.springframework.web.bind.annotation.RequestParam
 * @see cn.limw.summer.spring.web.argument.prefixentity.PrefixEntityArgumentResolver
 * @see cn.limw.summer.spring.web.argument.prefixentity.PrefixDataBinder
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Arg {
    public String value() default "";
}