package cn.limw.summer.spring.web.session.redis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;

import cn.limw.summer.spring.web.servlet.Mvcs;
import cn.limw.summer.spring.web.session.SessionProvider;
import cn.limw.summer.util.Errors;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2014年9月12日 上午10:01:18)
 * @since Java7
 */
public class RedisSessionProvider extends SessionProvider {
    private static final Logger log = Logs.slf4j();

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object handler) throws Exception {
        HttpServletRequestWrapper request = new HttpServletRequestWrapper(httpServletRequest) {
            public HttpSession getSession() {
                throw Errors.notImplemented();
            }

            public HttpSession getSession(boolean create) {
                throw Errors.notImplemented();
            }
        };

        log.info("binding {}, {}", request, response);
        Mvcs.bind(request, response);
        return super.preHandle(request, response, handler);
    }
}