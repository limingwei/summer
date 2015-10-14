package summer.ioc.impl.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.ioc.FactoryBean;
import summer.ioc.IocContext;
import summer.log.Logger;
import summer.util.Assert;
import summer.util.Log;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月13日 上午9:23:21)
 * @since Java7
 */
public class SummerIocContextUtil {
    private static final Logger log = Log.slf4j();

    @SuppressWarnings("rawtypes")
    public static Object unwrapFactoryBean(Object beanInstance) {
        if (beanInstance instanceof FactoryBean) {
            return ((FactoryBean) beanInstance).getObject();
        } else {
            return beanInstance;
        }
    }

    private static boolean isBeanTypeMatch(Class<?> type, BeanDefinition beanDefinition) {
        Class<?> beanType = beanDefinition.getBeanType();
        return type.isAssignableFrom(beanType) || //
                (FactoryBean.class.isAssignableFrom(beanType) && type.isAssignableFrom((Class<?>) Reflect.getGenericInterfacesActualTypeArguments(beanType, FactoryBean.class)[0]));
    }

    public static List<BeanDefinition> mergeBeanDefinitions(List<BeanDefinition> beanDefinitions) {
        Map<String, List<BeanDefinition>> beanIdToBeanDefinitionMap = listToMap(beanDefinitions);

        List<BeanDefinition> mergedBeanDefinitions = doMergeBeanDefinitions(beanIdToBeanDefinitionMap);

        checkRepeatedInjection(mergedBeanDefinitions);

        log.info("mergeBeanDefinitions done, {} merged beanDefinitions", mergedBeanDefinitions.size());
        return mergedBeanDefinitions;
    }

    private static List<BeanDefinition> doMergeBeanDefinitions(Map<String, List<BeanDefinition>> beanIdToBeanDefinitionMap) {
        List<BeanDefinition> mergedBeanDefinitions = new ArrayList<BeanDefinition>();
        Set<Entry<String, List<BeanDefinition>>> entrySet = beanIdToBeanDefinitionMap.entrySet();
        for (Entry<String, List<BeanDefinition>> entry : entrySet) {
            List<BeanDefinition> list = entry.getValue();
            BeanDefinition beanDefinition = list.get(0);
            int size = list.size();
            if (size > 1) {
                for (int i = 1; i < size; i++) {
                    BeanDefinition each = list.get(i);
                    beanDefinition.getBeanFields().addAll(each.getBeanFields());
                    log.info("mergeBeanDefinitions, {}->{}", each, beanDefinition);
                }
            }
            mergedBeanDefinitions.add(beanDefinition);
        }
        return mergedBeanDefinitions;
    }

    private static void checkRepeatedInjection(List<BeanDefinition> mergedBeanDefinitions) {
        for (BeanDefinition beanDefinition : mergedBeanDefinitions) {
            List<BeanField> beanFields = beanDefinition.getBeanFields();
            for (BeanField beanField1 : beanFields) {
                for (BeanField beanField2 : beanFields) {
                    if (!beanField1.equals(beanField2) // 
                            && beanField1.getName().equals(beanField2.getName())) {
                        throw new RuntimeException("同一属性多次注入, beanId=" + beanDefinition.getId() + ", beanType=" + beanDefinition.getBeanType() + ", fieldName=" + beanField1.getName());
                    }
                }
            }
        }
    }

    private static Map<String, List<BeanDefinition>> listToMap(List<BeanDefinition> beanDefinitions) {
        Map<String, List<BeanDefinition>> beanIdToBeanDefinitionMap = new HashMap<String, List<BeanDefinition>>();
        for (BeanDefinition beanDefinition : beanDefinitions) {
            String beanId = beanDefinition.getId();
            List<BeanDefinition> beanDefinitionList = beanIdToBeanDefinitionMap.get(beanId);
            if (null == beanDefinitionList) {
                beanIdToBeanDefinitionMap.put(beanId, new ArrayList<BeanDefinition>(Arrays.asList(beanDefinition)));
            } else {
                beanDefinitionList.add(beanDefinition);
            }
        }
        return beanIdToBeanDefinitionMap;
    }

    public static BeanDefinition findMatchBeanDefinition(List<BeanDefinition> beanDefinitions, Class<?> type, String id) {
        Assert.noEmpty(id, "id 不可以为空");

        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (isBeanTypeMatch(type, beanDefinition) && (id.equals(beanDefinition.getId()) || IocContext.BEAN_HAS_NO_ID.equals(id))) {
                return beanDefinition;
            }
        }
        return null;
    }

    public static Boolean containsBean(List<BeanDefinition> beanDefinitions, Class<?> type, String id) {
        return null != findMatchBeanDefinition(beanDefinitions, type, id);
    }
}