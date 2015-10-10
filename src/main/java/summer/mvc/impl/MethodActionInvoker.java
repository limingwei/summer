package summer.mvc.impl;

import summer.mvc.ActionInvoker;

/**
 * @author li
 * @version 1 (2015年10月10日 上午11:23:43)
 * @since Java7
 */
public class MethodActionInvoker implements ActionInvoker<MethodActionHandler> {
    public Object invokeAction(MethodActionHandler methodActionHandler) {
        Object[] args = new Object[] {};
        try {
            return methodActionHandler.getMethod().invoke(methodActionHandler.getTarget(), args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}