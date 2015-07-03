package cn.limw.summer.spring.web.interceptor.developmentfeature;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记一个仅在开发调试阶段使用的Action
 * @author li
 * @version 1 (2015年3月6日 下午1:30:02)
 * @since Java7
 * @see cn.limw.summer.spring.web.interceptor.developmentfeature.DevelopmentFeatureInterceptor
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DevelopmentFeature {}