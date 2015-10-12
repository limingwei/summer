package summer.mvc.impl;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import summer.aop.AopChain;
import summer.aop.AopFilter;
import summer.log.Logger;
import summer.mvc.Mvc;
import summer.mvc.ParameterAdapter;
import summer.mvc.annotation.At;
import summer.util.Log;
import summer.util.MethodParamNamesScaner;

/**
 * @author li
 * @version 1 (2015年10月12日 下午5:37:13)
 * @since Java7
 */
public class ParameterAdapterAopFilter implements AopFilter {
    private static final Logger log = Log.slf4j();

    private ParameterAdapter parameterAdapter;

    public ParameterAdapterAopFilter(ParameterAdapter parameterAdapter) {
        this.parameterAdapter = parameterAdapter;
    }

    public void doFilter(AopChain chain) {
        Method method = chain.getMethod();
        if (null != method.getAnnotation(At.class)) {
            log.info("ParameterAdapter 适配参数, chain=" + chain);

            List<String> parameterNameList = MethodParamNamesScaner.getParamNames(method);
            String[] parameterNames = parameterNameList.toArray(new String[parameterNameList.size()]);
            Class<?>[] parameterTypes = method.getParameterTypes();

            HttpServletRequest request = Mvc.getRequest();

            Object[] args = parameterAdapter.adapt(request, parameterNames, parameterTypes);

            chain.setArgs(args);

            chain.doFilter();
        } else {
            chain.doFilter();
        }
    }
}