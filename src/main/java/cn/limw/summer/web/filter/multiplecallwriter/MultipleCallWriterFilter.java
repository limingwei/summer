package cn.limw.summer.web.filter.multiplecallwriter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.web.filter.AbstractFilter;

/**
 * TODO 过程中不能关闭流, 结束时统一关闭
 * @author li
 * @version 1 (2015年6月5日 下午6:18:29)
 * @since Java7
 */
public class MultipleCallWriterFilter extends AbstractFilter {
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        MultipleCallWriterResponse multipleCallWriterResponse = new MultipleCallWriterResponse(response, request);
        super.doFilter(request, multipleCallWriterResponse, chain);
        multipleCallWriterResponse.closeWriter();
    }
}