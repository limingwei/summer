package cn.limw.summer.spring.web.returnvalue.voidvalue;

import org.springframework.core.MethodParameter;

import cn.limw.summer.spring.web.returnvalue.AbstractHandlerMethodReturnValueHandler;

/**
 * @author li
 * @version 1 (2014年7月7日 上午9:26:04)
 * @since Java7
 */
public class VoidReturnValueHandler extends AbstractHandlerMethodReturnValueHandler {
    public boolean supportsReturnType(MethodParameter returnType) {
        return void.class.equals(returnType.getParameterType());
    }
}