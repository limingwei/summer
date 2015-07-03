package cn.limw.summer.spring.beans.factory.support.util;

import org.springframework.beans.factory.support.RootBeanDefinition;

import cn.limw.summer.util.Mirrors;

/**
 * @author li
 * @version 1 (2015年4月29日 下午2:07:22)
 * @since Java7
 */
public class RootBeanDefinitionUtil {
    public static Object getPostProcessingLock(RootBeanDefinition mbd) {
        return Mirrors.getFieldValue(mbd, "postProcessingLock");
    }

    public static Boolean getPostProcessed(RootBeanDefinition mbd) {
        return (Boolean) Mirrors.getFieldValue(mbd, "postProcessed");
    }

    public static void setPostProcessed(RootBeanDefinition mbd, Boolean value) {
        Mirrors.setFieldValue(mbd, "postProcessed", value);
    }
}