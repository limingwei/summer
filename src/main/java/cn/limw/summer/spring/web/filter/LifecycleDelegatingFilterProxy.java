package cn.limw.summer.spring.web.filter;

import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * @author li
 * @version 1 (2015年2月5日 上午9:16:20)
 * @since Java7
 */
public class LifecycleDelegatingFilterProxy extends DelegatingFilterProxy {
    public LifecycleDelegatingFilterProxy() {
        setTargetFilterLifecycle(true);
    }
}