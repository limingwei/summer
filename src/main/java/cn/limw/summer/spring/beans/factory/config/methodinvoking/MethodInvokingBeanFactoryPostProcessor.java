package cn.limw.summer.spring.beans.factory.config.methodinvoking;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import cn.limw.summer.util.Mirrors;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年12月4日 下午3:08:45)
 * @since Java7
 */
public class MethodInvokingBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanNames = beanFactory.getBeanDefinitionNames();
        for (String curName : beanNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(curName);
            String beanClassName = beanDefinition.getBeanClassName();

            if (StringUtil.isEmpty(beanClassName)) {
                // @org.springframework.context.annotation.Bean
                // Root bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; 
                // factoryBeanName=cometDInitializer; factoryMethodName=bayeuxServer; initMethodName=start; destroyMethodName=stop; 
                // defined in class path resource [CometDInitializer.class]
            } else if ((!Mirrors.hasClass(beanClassName)) && isMethodInvoking(beanClassName)) {
                beanDefinition.setBeanClassName("org.springframework.beans.factory.config.MethodInvokingFactoryBean");
                beanDefinition.setAttribute("targetClass", beanClassName.substring(0, beanClassName.lastIndexOf('.')));
                beanDefinition.setAttribute("staticMethod", beanClassName.replace("()", ""));
            }
        }
    }

    private Boolean isMethodInvoking(String beanClassName) {
        return beanClassName.endsWith(")");
    }
}