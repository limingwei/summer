package summer.ioc.impl.util;

import java.util.ArrayList;
import java.util.List;

import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.log.Logger;
import summer.util.Log;

/**
 * @author li
 * @version 1 (2015年10月13日 上午9:23:21)
 * @since Java7
 */
public class SummerIocContextUtil {
    private static final Logger log = Log.slf4j();

    public static List<BeanDefinition> mergeBeanDefinitions(List<BeanDefinition> beanDefinitions) {
        log.info("mergeBeanDefinitions");

        List<BeanDefinition> toRemove = new ArrayList<BeanDefinition>();
        for (BeanDefinition beanDefinition1 : beanDefinitions) {
            for (BeanDefinition beanDefinition2 : beanDefinitions) {
                if (!beanDefinition1.equals(beanDefinition2) //
                        && (beanDefinition1.getBeanType().equals(beanDefinition2.getBeanType()) && beanDefinition1.getId().equals(beanDefinition2.getId()))) {
                    beanDefinition1.getBeanFields().addAll(beanDefinition2.getBeanFields()); // 2 合并到 1
                    toRemove.add(beanDefinition2); // 移除 2
                }
            }
        }

        for (BeanDefinition beanDefinition : beanDefinitions) {
            List<BeanField> beanFields = beanDefinition.getBeanFields();
            for (BeanField beanField1 : beanFields) {
                for (BeanField beanField2 : beanFields) {
                    if (!beanField1.equals(beanField2) //
                            && beanField1.getName().equals(beanField2.getName())) {
                        throw new RuntimeException("重复的属性配置 " + beanDefinition.getId() + ", " + beanDefinition.getBeanType().getName() + ", " + beanField1.getName());
                    } else {
                        System.err.println("beanField1=" + beanField1 + ", beanField2=" + beanField2 + ", beanField1.name=" + beanField1.getName() + ", beanField2.name=" + beanField2.getName());
                    }
                }
            }
        }

        beanDefinitions.removeAll(toRemove);

        return beanDefinitions;
    }
}