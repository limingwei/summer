package cn.limw.summer.dao.hibernate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author li
 * @version 1 (2015年6月9日 上午10:38:39)
 * @since Java7
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Like {
    /**
     * 添加的Like属性值
     */
    String[] value() default {};

    /**
     * 是否要默认属性列表
     */
    boolean original() default true;
}