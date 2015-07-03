package cn.limw.summer.web.filter.multiplecallwriter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;

import cn.limw.summer.java.io.writer.NullPrintWriter;
import cn.limw.summer.javax.servlet.NullServletOutputStream;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年6月5日 下午6:19:53)
 * @since Java7
 */
public class MultipleCallWriterResponse extends HttpServletResponseWrapper {
    private static final Logger log = Logs.slf4j();

    private HttpServletRequest request;

    public MultipleCallWriterResponse(HttpServletResponse response, HttpServletRequest request) {
        super(response);
        this.request = request;
    }

    /**
     * 静态文件不打日志
     */
    public PrintWriter getWriter() throws IOException {
        try {
            PrintWriter printWriter = super.getWriter();
            return printWriter;
        } catch (Exception e) {
            if (!(e instanceof IllegalStateException && isStaticFileRequest(request))) { // IllegalStateException且静态文件请求就不打日志
                log.error("getWriter error " + e + ", remoteAddr=" + request.getRemoteAddr() + ", requestURI=" + request.getRequestURI());
            }
            return new NullPrintWriter();
        }
    }

    /**
     * 是否静态文件请求
     */
    private Boolean isStaticFileRequest(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        if (StringUtil.startWith(requestUri, "/source/")) {
            if (StringUtil.endWith(requestUri, ".js") ||
                    StringUtil.endWith(requestUri, ".css") ||
                    StringUtil.endWith(requestUri, ".ttf")) {
                return true;
            }
        }
        return false;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        try {
            ServletOutputStream servletOutputStream = super.getOutputStream();
            return servletOutputStream;
        } catch (Exception e) {
            log.error("getOutputStream error " + e + ", remoteAddr=" + request.getRemoteAddr() + ", requestURI=" + request.getRequestURI());
            return new NullServletOutputStream();
        }
    }

    public void closeWriter() {
        // 返回不可关闭的writer和outputStream 这里再统一关闭
    }
}