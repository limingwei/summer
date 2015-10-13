package summer.mvc.impl;

import java.lang.reflect.Method;

import summer.ioc.IocContext;
import summer.mvc.ActionHandler;
import summer.mvc.ActionInvokeService;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月12日 下午3:41:45)
 * @since Java7
 */
public class SummerActionInvokeService implements ActionInvokeService {
    private IocContext iocContext;

    public SummerActionInvokeService(IocContext iocContext) {
        this.iocContext = iocContext;
    }

    public IocContext getIocContext() {
        return iocContext;
    }

    /**
     * TODO 在生成代码里做参数适配
     */
    public void invokeAction(ActionHandler actionHandler) {
        if (actionHandler instanceof MethodActionHandler) {
            Method actionMethod = ((MethodActionHandler) actionHandler).getMethod();
            Object actionBean = getIocContext().getBean(actionMethod.getDeclaringClass());
            Class<?>[] parameterTypes = actionMethod.getParameterTypes();
            Object[] args = new Object[parameterTypes.length];

            Reflect.invokeMethod(actionBean, actionMethod, args); // TODO 改成生成代码执行
        } else {
            throw new RuntimeException("invokeAction error");
        }
    }
}