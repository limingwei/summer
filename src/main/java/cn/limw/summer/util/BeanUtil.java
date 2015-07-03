package cn.limw.summer.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;

/**
 * bean工具类
 * @author jianglei
 */
public class BeanUtil {
    private static final Logger log = Logs.slf4j();

    /**
     * 将指定源对象中的简单属性的值复制到指定目标对象中，如果目标对象中无相应属性则忽略。
     * 简单属性包括：原始类型，字符串，数字，日期，URI，URL，Locale
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copySimpleProperties(Object source, Object target) {
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(source.getClass());
        Class<?> targetClass = target.getClass();
        for (PropertyDescriptor pd : propertyDescriptors) {
            try {
                if (BeanUtils.isSimpleValueType(pd.getPropertyType())) {
                    String name = pd.getDisplayName();
                    if (!"class".equals(name)) {
                        PropertyDescriptor writePd = BeanUtils.getPropertyDescriptor(targetClass, name);
                        if (writePd != null) {
                            Method writeMethod = writePd.getWriteMethod();
                            if (writeMethod != null) {
                                Object value = pd.getReadMethod().invoke(source);
                                writeMethod.invoke(target, value);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("copySimpleProperties error", e);
            }
        }
    }

    /**
     * 将指定源对象中的简单属性的值复制到指定目标对象中，如果目标对象中无相应属性或ID则忽略。
     * 简单属性包括：原始类型，字符串，数字，日期，URI，URL，Locale
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copySimplePropertiesExceptId(Object source, Object target) {
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(source.getClass());
        Class<?> targetClass = target.getClass();
        for (PropertyDescriptor pd : propertyDescriptors) {
            try {
                if (BeanUtils.isSimpleValueType(pd.getPropertyType())) {
                    String name = pd.getDisplayName();
                    if (!"class".equals(name) && !"id".equals(name)) {
                        PropertyDescriptor writePd = BeanUtils.getPropertyDescriptor(targetClass, name);
                        if (writePd != null) {
                            Method writeMethod = writePd.getWriteMethod();
                            if (writeMethod != null) {
                                Object value = pd.getReadMethod().invoke(source);
                                writeMethod.invoke(target, value);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("copySimplePropertiesExceptId error", e);
            }
        }
    }
}
