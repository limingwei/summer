package cn.limw.summer.spring.web.returnvalue.string;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ViewNameMethodReturnValueHandler;

import cn.limw.summer.spring.web.returnvalue.AbstractHandlerMethodReturnValueHandler;

/**
 * @author li
 * @version 1 (2015年1月9日 上午11:43:00)
 * @since Java7
 */
public class StringReturnValueHandler extends AbstractHandlerMethodReturnValueHandler {
    private static final ViewNameMethodReturnValueHandler VIEW_NAME_METHOD_RETURN_VALUE_HANDLER = new ViewNameMethodReturnValueHandler();

    private static final RequestResponseBodyMethodProcessor REQUEST_RESPONSE_BODY_METHOD_PROCESSOR = new RequestResponseBodyMethodProcessor(null);

    public boolean supportsReturnType(MethodParameter returnType) {
        return String.class.equals(returnType.getParameterType());
    }

    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        VIEW_NAME_METHOD_RETURN_VALUE_HANDLER.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
        REQUEST_RESPONSE_BODY_METHOD_PROCESSOR.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
        super.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }
}