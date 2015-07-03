package cn.limw.summer.spring.beans.factory.lazyinject;

import org.springframework.context.annotation.Bean;

/**
 * 继承主要功能实现类,@Bean注册两个辅助Bean
 * @author li
 * @version 1 (2015年6月19日 上午9:06:57)
 * @since Java7
 * @see org.mybatis.spring.mapper.MapperScannerConfigurer
 * @see cn.limw.summer.spring.beans.factory.mock.MockImplementationBeanFactoryPostProcessor
 */
public class LazyInjectBeanPostProcessor extends AbstractLazyInjectBeanPostProcessor {
    @Bean
    public LazyInjectApplicationContextAware lazyInjectApplicationContextAware() {
        return new LazyInjectApplicationContextAware();
    }

    @Bean
    public LazyInjectContextRefreshedEventApplicationListener contextRefreshedEventApplicationListener() {
        return new LazyInjectContextRefreshedEventApplicationListener();
    }
}