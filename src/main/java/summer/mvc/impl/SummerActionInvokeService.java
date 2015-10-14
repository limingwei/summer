package summer.mvc.impl;

import java.lang.reflect.Method;

import summer.aop.AopType;
import summer.ioc.IocContext;
import summer.ioc.compiler.JavassistSummerCompilerUtil;
import summer.log.Logger;
import summer.mvc.ActionHandler;
import summer.mvc.ActionInvokeService;
import summer.util.Log;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月12日 下午3:41:45)
 * @since Java7
 */
public class SummerActionInvokeService implements ActionInvokeService {
    private static final Logger log = Log.slf4j();

    private IocContext iocContext;

    public SummerActionInvokeService(IocContext iocContext) {
        this.iocContext = iocContext;
    }

    public IocContext getIocContext() {
        return iocContext;
    }

    public void invokeAction(ActionHandler actionHandler) {
        if (actionHandler instanceof MethodActionHandler) {
            Method actionMethod = ((MethodActionHandler) actionHandler).getMethod();
            Object actionBean = getIocContext().getBean(actionMethod.getDeclaringClass());
            Class<?>[] parameterTypes = actionMethod.getParameterTypes();
            Object[] args = new Object[parameterTypes.length];

            if (actionBean instanceof AopType) {
                String methodSignature = JavassistSummerCompilerUtil.getMethodSignature(((MethodActionHandler) actionHandler).getMethod());
                ((AopType) actionBean).call(methodSignature, args);
                log.info("AopType invokeMethod, actionBean={}, actionMethod={}", actionBean, actionMethod);
            } else {
                Reflect.invokeMethod(actionBean, actionMethod, args); // TODO 改成生成代码执行
                log.info("Reflect invokeMethod, actionBean={}, actionMethod={}", actionBean, actionMethod);
            }
        } else {
            throw new RuntimeException("invokeAction error");
        }
    }
}