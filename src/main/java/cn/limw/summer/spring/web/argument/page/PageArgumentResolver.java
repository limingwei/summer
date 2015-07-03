package cn.limw.summer.spring.web.argument.page;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import cn.limw.summer.dao.Page;
import cn.limw.summer.spring.web.argument.keepinrequest.KeepInRequestArgumentResolver;
import cn.limw.summer.util.Nums;

/**
 * @author li
 * @version 1 (2015年1月8日 下午6:03:57)
 * @since Java7
 */
public class PageArgumentResolver extends KeepInRequestArgumentResolver {
    private Integer defaultPageSize = 10;

    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Page.class);
    }

    public Object doResolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Page page = new Page();
        page.setPageSize(Nums.toInt(webRequest.getParameter("size"), getDefaultPageSize()));
        page.setPageNumber(Nums.toInt(webRequest.getParameter("page"), 1));
        return page;
    }

    public Integer getDefaultPageSize() {
        return defaultPageSize;
    }

    public void setDefaultPageSize(Integer defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }
}