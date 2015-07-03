package cn.limw.summer.spring.web.returnvalue;

import org.slf4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年1月9日 上午11:44:06)
 * @since Java7
 */
public class AbstractHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {
    private static final Logger log = Logs.slf4j();

    public boolean supportsReturnType(MethodParameter returnType) {
        log.info("supportsReturnType {}", returnType);
        return false;
    }

    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        log.info("handleReturnValue {}", returnValue);
    }
}