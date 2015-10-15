package summer.mvc.loader;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import summer.ioc.BeanDefinition;
import summer.ioc.IocContext;
import summer.log.Logger;
import summer.mvc.ActionHandler;
import summer.mvc.ActionLoader;
import summer.mvc.annotation.At;
import summer.mvc.impl.MethodActionHandler;
import summer.util.Log;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月15日 下午6:50:59)
 * @since Java7
 */
public class AnnotationActionLoader implements ActionLoader {
    private static final Logger log = Log.slf4j();

    private IocContext iocContext;

    public AnnotationActionLoader(IocContext iocContext) {
        this.iocContext = iocContext;
    }

    public Map<String, ActionHandler> getActionHandlerMapping() {
        Map<String, ActionHandler> actionHandlerMapping = new HashMap<String, ActionHandler>();
        List<BeanDefinition> beanDefinitions = iocContext.getBeanDefinitions();
        for (BeanDefinition beanDefinition : beanDefinitions) {
            List<Method> methods = Reflect.getPublicMethods(beanDefinition.getBeanType());
            for (Method method : methods) {
                At atAnnotation = method.getAnnotation(At.class);
                if (null != atAnnotation) {
                    String[] atAnnotationValues = atAnnotation.value();
                    String[] atAnnotationMethods = atAnnotation.method();

                    for (String atAnnotationValue : atAnnotationValues) {
                        for (String atAnnotationMethod : atAnnotationMethods) {
                            String key = atAnnotationValue + "@" + atAnnotationMethod;

                            MethodActionHandler actionHandler = new MethodActionHandler();
                            actionHandler.setBeanDefinition(beanDefinition);
                            actionHandler.setMethod(method);

                            actionHandlerMapping.put(key, actionHandler);

                            log.info("添加 @At(value=\"" + atAnnotationValue + "\", method=\"" + atAnnotationMethod + "\"), method={}", atAnnotation, method);
                        }
                    }
                }
            }
        }
        return actionHandlerMapping;
    }
}