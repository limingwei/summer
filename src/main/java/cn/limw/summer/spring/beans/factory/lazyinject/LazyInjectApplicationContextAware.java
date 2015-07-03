package cn.limw.summer.spring.beans.factory.lazyinject;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.limw.summer.spring.beans.factory.lazyinject.LazyInject.RequiredType;

/**
 * @author li
 * @version 1 (2015年6月19日 下午1:58:03)
 * @since Java7
 */
public class LazyInjectApplicationContextAware implements ApplicationContextAware {
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        for (LazyInjectSupport lazyInjectSupport : LazyInjectBeanPostProcessor.LAZY_INJECT_SUPPORTS) {
            LazyInjectDelegateHolder lazyInjectDelegateHolder = lazyInjectSupport._getLazyInjectDelegateHolder_();
            lazyInjectDelegateHolder.setApplicationContext(applicationContext);

            if (RequiredType.TRUE.equals(lazyInjectDelegateHolder.getLazyInject().required())) {
                lazyInjectDelegateHolder.getLazyInjectDelegateTarget();
            }
        }
    }
}