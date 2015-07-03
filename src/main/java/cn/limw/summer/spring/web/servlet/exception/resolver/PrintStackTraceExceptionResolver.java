package cn.limw.summer.spring.web.servlet.exception.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.limw.summer.spring.web.servlet.Mvcs;
import cn.limw.summer.spring.web.view.text.TextView;
import cn.limw.summer.util.Errors;
import cn.limw.summer.util.Logs;

import com.alibaba.dubbo.rpc.RpcException;

/**
 * @author li
 * @version 1 (2014年7月25日 下午3:09:25)
 * @since Java7
 */
public class PrintStackTraceExceptionResolver implements HandlerExceptionResolver {
    private static Logger log = Logs.slf4j();

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.error(ex.getClass() + ", " + ex.getMessage(), ex);

        TextView textView = new TextView();
        if (Mvcs.isLocalhost()) {
            textView.setText(Errors.stackTrace(ex));
        } else if (ex instanceof RpcException) {
            textView.setText("Rpc Error, 请尝试刷新页面. 如果多次出现, 请联系管理员(QQ:12345)");
        } else {
            textView.setText("server error");
        }
        return new ModelAndView(textView);
    }
}