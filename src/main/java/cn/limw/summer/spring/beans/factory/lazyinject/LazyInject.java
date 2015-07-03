package cn.limw.summer.spring.beans.factory.lazyinject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 延迟注入, 注入一个代理, 使用时再获取对象
 * @author li
 * @version 1 (2015年6月19日 上午9:03:20)
 * @since Java7
 * @see cn.limw.summer.spring.beans.factory.lazyinject.LazyInjectApplicationContextAware
 * @see cn.limw.summer.spring.beans.factory.lazyinject.LazyInjectBeanPostProcessor
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface LazyInject {
    /**
     * Bean名称
     */
    String value() default "";

    /**
     * 默认框架初始化完成后检查
     */
    RequiredType required() default RequiredType.WHEN_INIT;

    public enum RequiredType {
        /**
         * 必须,不能为空,初始化时检查
         */
        TRUE,

        /**
         * 可以为空,调用时检查,为空时打日志
         */
        FALSE,

        /**
         * 框架初始化完成后检查, ContextRefreshedEvent
         */
        WHEN_INIT,

        /**
         * 调用时检查,为空抛异常
         */
        WHEN_CALL
    }
}