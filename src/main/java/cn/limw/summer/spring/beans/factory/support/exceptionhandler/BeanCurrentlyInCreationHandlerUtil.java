package cn.limw.summer.spring.beans.factory.support.exceptionhandler;

import java.util.Set;

import org.springframework.util.StringUtils;

/**
 * @author li
 * @version 1 (2015年4月29日 下午2:40:53)
 * @since Java7
 */
public class BeanCurrentlyInCreationHandlerUtil {
    public static String beanCurrentlyInCreationExceptionMessage(String beanName, Set<String> actualDependentBeans) {
        return "Bean with name '" + beanName + "' has been injected into other beans [" + StringUtils.collectionToCommaDelimitedString(actualDependentBeans) +
                "] in its raw version as part of a circular reference, but has eventually been " + "wrapped. This means that said other beans do not use the final version of the " +
                "bean. This is often the result of over-eager type matching - consider using " + "'getBeanNamesOfType' with the 'allowEagerInit' flag turned off, for example.";
    }
}