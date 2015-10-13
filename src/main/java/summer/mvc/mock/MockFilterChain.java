package summer.mvc.mock;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author li
 * @version 1 (2015年10月13日 下午1:22:39)
 * @since Java7
 */
public class MockFilterChain implements FilterChain {
    private int _index = 0;

    private List<Filter> filters = new ArrayList<Filter>();

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public MockFilterChain() {}

    public MockFilterChain(MockServletContext servletContext) {}

    /**
     * 执行AopChain,执行下一个AopFilter或者执行被代理方法
     */
    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        if (null == filters || _index >= filters.size()) {// 如果没有AopFilter或者已经经过全部AopFilter
            // 执行目标方法 
            PrintWriter printWriter = response.getWriter();
            printWriter.append("404404404404404404404404404404");
            printWriter.flush();
        } else {// 还有AopFilter 
            filters.get(_index++).doFilter(request, response, this);
        }
    }
}