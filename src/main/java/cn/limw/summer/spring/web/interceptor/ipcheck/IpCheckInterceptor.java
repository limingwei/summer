package cn.limw.summer.spring.web.interceptor.ipcheck;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.spring.web.interceptor.ipcheck.impl.DefaultIpCheckFailedHandler;
import cn.limw.summer.spring.web.interceptor.ipcheck.impl.DefaultIpChecker;
import cn.limw.summer.spring.web.interceptor.ipcheck.util.IpCheckUtil;
import cn.limw.summer.spring.web.servlet.AbstractHandlerInterceptor;

/**
 * @author li
 * @version 1 (2015年3月18日 下午1:07:54)
 * @since Java7
 */
public class IpCheckInterceptor extends AbstractHandlerInterceptor {
    private IpChecker ipChecker = new DefaultIpChecker();

    private IpCheckFailedHandler ipCheckFailedHandler = new DefaultIpCheckFailedHandler();

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        IpCheck annotation = IpCheckUtil.getAnnotation(handler);
        if (null == annotation) { // 没有注解
            return true;
        } else {
            String ip = IpCheckUtil.getIp(request);
            if (getIpChecker().check(ip)) {
                return true;
            } else {
                return getIpCheckFailedHandler().handle(request, response, handler);
            }
        }
    }

    public IpChecker getIpChecker() {
        return ipChecker;
    }

    public void setIpChecker(IpChecker ipChecker) {
        this.ipChecker = ipChecker;
    }

    public IpCheckFailedHandler getIpCheckFailedHandler() {
        return ipCheckFailedHandler;
    }

    public void setIpCheckFailedHandler(IpCheckFailedHandler ipCheckFailedHandler) {
        this.ipCheckFailedHandler = ipCheckFailedHandler;
    }
}