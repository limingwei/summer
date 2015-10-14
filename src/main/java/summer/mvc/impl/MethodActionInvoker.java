package summer.mvc.impl;

import java.lang.reflect.Method;

import summer.aop.AopType;
import summer.ioc.BeanDefinition;
import summer.ioc.IocContext;
import summer.ioc.compiler.util.JavassistSummerCompilerUtil;
import summer.log.Logger;
import summer.mvc.ActionHandler;
import summer.mvc.ActionInvoker;
import summer.util.Log;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月14日 下午4:47:38)
 * @since Java7
 */
public class MethodActionInvoker implements ActionInvoker {
    private static final Logger log = Log.slf4j();

    private Object actionBean;

    private Method actionMethod;

    private String methodSignature;

    private Integer actionMethodParameterLength;

    public MethodActionInvoker(IocContext iocContext, ActionHandler actionHandler) {
        MethodActionHandler methodActionHandler = (MethodActionHandler) actionHandler;
        Method method = methodActionHandler.getMethod();
        BeanDefinition beanDefinition = methodActionHandler.getBeanDefinition();
        String beanId = beanDefinition.getId();
        beanId = (null == beanId || beanId.isEmpty()) ? IocContext.BEAN_HAS_NO_ID : beanId;

        this.actionMethod = method;
        this.actionBean = iocContext.getBean(beanDefinition.getBeanType(), beanId);
        this.methodSignature = JavassistSummerCompilerUtil.getMethodSignature(method);
        this.actionMethodParameterLength = actionMethod.getParameterTypes().length;
    }

    public Object invokeAction() {
        Object[] args = new Object[actionMethodParameterLength];

        if (actionBean instanceof AopType) {
            ((AopType) actionBean).call(methodSignature, args);
            log.info("AopType invokeMethod, actionMethod={}, actionBean={}, actionInvoker={}", actionMethod, actionBean, this);
        } else {
            Reflect.invokeMethod(actionBean, actionMethod, args);
            log.info("Reflect invokeMethod, actionBean={}, actionMethod={}", actionBean, actionMethod);
        }
        return null;
    }
}