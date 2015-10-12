package summer.mvc.impl;

import java.lang.reflect.Method;
import java.util.List;

import summer.aop.AopChain;
import summer.aop.AopFilter;
import summer.log.Logger;
import summer.mvc.Mvc;
import summer.mvc.annotation.At;
import summer.util.Log;
import summer.util.MethodParamNamesScaner;

/**
 * @author li
 * @version 1 (2015年10月12日 下午4:05:01)
 * @since Java7
 */
public class ParameterAdapter implements AopFilter {
    private static final Logger log = Log.slf4j();

    public void doFilter(AopChain chain) {
        Method method = chain.getMethod();
        if (null != method.getAnnotation(At.class)) {
            log.info("ParameterAdapter 适配参数, chain=" + chain);
            List<String> paramNames = MethodParamNamesScaner.getParamNames(method);

            Object[] args = chain.getArgs();
            for (int i = 0; i < args.length; i++) {
                args[i] = Mvc.getRequest().getParameter(paramNames.get(i));
            }

            chain.doFilter();
        } else {
            chain.doFilter();
        }
    }
}