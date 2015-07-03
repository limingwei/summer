package cn.limw.summer.spring.web.interceptor.ipcheck.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.method.HandlerMethod;

import cn.limw.summer.spring.web.interceptor.ipcheck.IpCheck;
import cn.limw.summer.spring.web.method.HandlerMethodUtil;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年3月18日 下午1:07:49)
 * @since Java7
 */
public class IpCheckUtil {
    public static IpCheck getAnnotation(Object handler) {
        if (!HandlerMethodUtil.isHandlerMethod(handler)) {
            return null;
        } else {
            IpCheck ipCheck = HandlerMethodUtil.getMethodAnnotation((HandlerMethod) handler, IpCheck.class);
            if (null == ipCheck) {
                ipCheck = HandlerMethodUtil.getBeanTypeAnnotation((HandlerMethod) handler, IpCheck.class);
            }
            return ipCheck;
        }
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        return StringUtil.isEmpty(ip) ? request.getRemoteAddr() : ip;
    }
}