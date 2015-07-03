package cn.limw.summer.spring.web.view;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.RedirectView;

import cn.limw.summer.spring.web.servlet.Mvcs;
import cn.limw.summer.spring.web.view.text.TextView;
import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年11月12日 下午3:00:54)
 * @since Java7
 */
public class Views {
    public static String render(View view, Map<String, ?> model) {
        return render(view, model, Mvcs.getRequest());
    }

    public static String render(View view, Map<String, ?> model, HttpServletRequest request) {
        try {
            Asserts.noNull(view, "not found view for " + Mvcs.getRequestURI(request));

            MockHttpServletResponse response = new MockHttpServletResponse();
            view.render(model, request, response);
            return new String(response.getContentAsByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static View resolve(ViewResolver viewResolver) {
        return resolve(viewResolver, "view-name-unknown", Locale.getDefault());
    }

    public static View resolve(ViewResolver viewResolver, String viewName, Locale locale) {
        try {
            return viewResolver.resolveViewName(viewName, locale);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static View redirect(String url) {
        return new RedirectView(url);
    }

    public static View text(String text) {
        return new TextView(text);
    }
}