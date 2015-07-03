package cn.limw.summer.spring.beans.factory.lazyinject;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import cn.limw.summer.spring.beans.factory.lazyinject.LazyInject.RequiredType;

/**
 * @author li
 * @version 1 (2015年6月19日 下午1:58:03)
 * @since Java7
 */
public class LazyInjectContextRefreshedEventApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (LazyInjectSupport lazyInjectSupport : LazyInjectBeanPostProcessor.LAZY_INJECT_SUPPORTS) {
            LazyInjectDelegateHolder lazyInjectDelegateHolder = lazyInjectSupport._getLazyInjectDelegateHolder_();

            if (RequiredType.WHEN_INIT.equals(lazyInjectDelegateHolder.getLazyInject().required())) {
                lazyInjectDelegateHolder.getLazyInjectDelegateTarget();
            }
        }
    }
}