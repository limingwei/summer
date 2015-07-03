package cn.limw.summer.spring.web.argument.prefixentity;

import java.lang.annotation.Annotation;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import cn.limw.summer.spring.web.argument.keepinrequest.KeepInRequestArgumentResolver;
import cn.limw.summer.util.Logs;

/**
 * SpringMvc参数获取类,可以在参数上以@Arg定义表单域前缀,像struts
 * @author li
 * @version 1 (2014年7月5日 上午10:29:04)
 * @since Java7
 * @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#getDefaultArgumentResolvers
 */
public class PrefixEntityArgumentResolver extends KeepInRequestArgumentResolver {
    private static final Logger log = Logs.slf4j();

    public boolean supportsParameter(MethodParameter parameter) {
        log.debug("supportsParameter {}", parameter);
        return null != parameter.getParameterAnnotation(Arg.class);
    }

    /**
     * @see org.springframework.web.method.annotation.ModelAttributeMethodProcessor#resolveArgument(MethodParameter, ModelAndViewContainer, NativeWebRequest, WebDataBinderFactory)
     */
    public Object doResolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {
        log.debug("resolveArgument {} {} {} {}", parameter, mavContainer, request, binderFactory);

        String name = ModelFactory.getNameForParameter(parameter);
        Object attribute = (mavContainer.containsAttribute(name)) ? mavContainer.getModel().get(name) : createAttribute(name, parameter, binderFactory, request);

        WebDataBinder binder = binderFactory.createBinder(request, attribute, name);
        if (binder.getTarget() != null) {
            bindRequestParameters(binder, request, getParameterPrefix(parameter));//###
            validateIfApplicable(binder, parameter);
            if (binder.getBindingResult().hasErrors()) {
                if (isBindExceptionRequired(binder, parameter)) {
                    throw new BindException(binder.getBindingResult());
                }
            }
        }
        Map<String, Object> bindingResultModel = binder.getBindingResult().getModel();
        mavContainer.removeAttributes(bindingResultModel);
        mavContainer.addAllAttributes(bindingResultModel);
        return binder.getTarget();
    }

    private String getParameterPrefix(MethodParameter parameter) {
        String prefix = parameter.getParameterAnnotation(Arg.class).value();
        if (null == prefix || prefix.trim().isEmpty()) {
            prefix = parameter.getParameterName();
        }
        return prefix;
    }

    protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter parameter) {
        int i = parameter.getParameterIndex();
        Class<?>[] paramTypes = parameter.getMethod().getParameterTypes();
        boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
        return !hasBindingResult;
    }

    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        Annotation[] annotations = parameter.getParameterAnnotations();
        for (Annotation annot : annotations) {
            if (annot.annotationType().getSimpleName().startsWith("Valid")) {
                Object hints = AnnotationUtils.getValue(annot);
                binder.validate(hints instanceof Object[] ? (Object[]) hints : new Object[] { hints });
                break;
            }
        }
    }

    /**
     * @param prefix
     */
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request, String prefix) {
        PrefixDataBinder _binder = new PrefixDataBinder((ExtendedServletRequestDataBinder) binder, prefix);
        _binder.bind(request.getNativeRequest(HttpServletRequest.class));
    }

    protected Object createAttribute(String attributeName, MethodParameter parameter, WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {
        return BeanUtils.instantiateClass(parameter.getParameterType());
    }
}