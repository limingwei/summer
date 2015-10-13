package summer.mvc.mock;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.ServletException;

import summer.mvc.SummerFilter;

/**
 * @author li
 * @version 1 (2015年10月13日 下午5:12:00)
 * @since Java7
 */
public class Mock {
    public static MockHttpServletRequest request(String url, String method) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath(url);
        request.setMethod(method);
        return request;
    }

    public static MockHttpServletResponse response(PrintWriter printWriter) {
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setWritter(printWriter);
        return response;
    }

    public static MockFilterChain filterChain(String summerConfig) {
        try {
            MockFilterChain filterChain = new MockFilterChain();

            SummerFilter summerFilter = getSummerFilter(summerConfig);

            filterChain.setFilters(new ArrayList<Filter>(Arrays.asList(summerFilter)));
            return filterChain;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private static SummerFilter summerFilter = null;

    private synchronized static SummerFilter getSummerFilter(String summerConfig) throws ServletException {
        if (null == summerFilter) {
            SummerFilter summerFilter = new SummerFilter();
            MockFilterConfig filterConfig = new MockFilterConfig();
            filterConfig.setInitParameter("summerConfig", summerConfig);
            summerFilter.init(filterConfig);
            return Mock.summerFilter = summerFilter;
        }
        return summerFilter;
    }
}