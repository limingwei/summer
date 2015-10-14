package summer.mvc.impl;

import java.util.HashMap;
import java.util.Map;

import summer.ioc.IocContext;
import summer.mvc.ActionHandler;
import summer.mvc.ActionInvokeService;
import summer.mvc.ActionInvoker;

/**
 * @author li
 * @version 1 (2015年10月12日 下午3:41:45)
 * @since Java7
 */
public class SummerActionInvokeService implements ActionInvokeService {
    private Map<ActionHandler, ActionInvoker> actionInvokerMap = new HashMap<ActionHandler, ActionInvoker>();

    private IocContext iocContext;

    public SummerActionInvokeService(IocContext iocContext) {
        this.iocContext = iocContext;
    }

    public void invokeAction(ActionHandler actionHandler) {
        ActionInvoker actionInvoker = actionInvokerMap.get(actionHandler);
        if (null == actionInvoker) {
            actionInvokerMap.put(actionHandler, actionInvoker = new MethodActionInvoker(iocContext, actionHandler));
        }
        actionInvoker.invokeAction();
    }
}