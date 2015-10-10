package summer.mvc.impl;

import java.lang.reflect.Method;

import summer.mvc.ActionHandler;

/**
 * @author li
 * @version 1 (2015年10月10日 上午11:23:43)
 * @since Java7
 */
public class MethodActionHandler implements ActionHandler {
    private String target;

    private Method method;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}