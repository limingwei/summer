package cn.limw.summer.spring.beans.factory.mock;

import java.util.ArrayList;
import java.util.List;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Mirrors;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年11月25日 上午10:21:14)
 * @since Java7
 */
public class MockImplementationBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    private static final String $_MOCK_SUFFIX = ".$Mock";

    private static final Logger log = Logs.slf4j();

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
            } else if ((!Mirrors.hasClass(beanClassName)) && beanClassName.endsWith($_MOCK_SUFFIX)) {
                MutablePropertyValues mutablePropertyValues = beanDefinition.getPropertyValues();
                List<PropertyValue> propertyValues = new ArrayList<PropertyValue>(mutablePropertyValues.getPropertyValueList());
                for (PropertyValue propertyValue : propertyValues) {
                    mutablePropertyValues.removePropertyValue(propertyValue); // 移除引用
                }
                generateMockClass(beanClassName, propertyValues);
            }
        }
    }

    private void generateMockClass(String beanClassName, List<PropertyValue> propertyValues) {
        try {
            String interfaceName = getInterfaceName(beanClassName);

            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.makeClass(beanClassName);
            classPool.insertClassPath(new ClassClassPath(Mirrors.classForName(interfaceName))); //new ClassClassPath(AbstractDBModel.class)
            CtClass ctInterface = classPool.get(interfaceName);
            ctClass.setInterfaces(new CtClass[] { ctInterface });
            ctClass.addField(CtField.make(" private static final org.slf4j.Logger log = Logs.slf4j(); ", ctClass));

            CtMethod[] ctMethods = ctInterface.getMethods();
            for (CtMethod ctMethod : ctMethods) {
                if (interfaceName.equals(ctMethod.getDeclaringClass().getName())) {
                    ctClass.addMethod(CtNewMethod.make(MockImplementationUtil.methodCode(ctMethod, propertyValues), ctClass));
                }
            }

            ctClass.toClass(); // ctClass 的类型是 javassist.CtNewClass
            log.info("generateMockClass beanClassName={}, interfaceName={}", beanClassName, interfaceName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getInterfaceName(String beanClassName) {
        return beanClassName.substring(0, beanClassName.length() - $_MOCK_SUFFIX.length());
    }
}