package cn.limw.summer.spring.hibernate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解在一个数据对象类或其一个属性上,作为它对应在数据库中的注释
 * @author li
 * @version 1 (2014年6月25日 上午9:01:11)
 * @since Java7
 */
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
//继承
@Inherited
//文档
@Documented
public @interface Comment {
    public String value() default "";

    public String forJoinTable() default "";

    public String[] forJoinColumns() default {};

    public String[] forInverseJoinColumns() default {};
}