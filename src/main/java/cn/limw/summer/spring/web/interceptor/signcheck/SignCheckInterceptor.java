package cn.limw.summer.spring.web.interceptor.signcheck;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import cn.limw.summer.spring.web.servlet.AbstractHandlerInterceptor;
import cn.limw.summer.spring.web.servlet.Mvcs;
import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.BoolUtil;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年1月12日 上午10:21:02)
 * @since Java7
 */
public class SignCheckInterceptor extends AbstractHandlerInterceptor {
    private static final Logger log = Logs.slf4j();

    private String signCheckSecret;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SignCheck signCheck = SignCheckUtil.getAnnotation(handler);
        if (null == signCheck || !signCheck.check()) { // 没有注解或注解不需检查
            return true;
        } else { // 需要检查
            if (checkSign(request, signCheck.value())) {
                return true;
            } else {
                Mvcs.write("sign_check_error");
                return false;
            }
        }
    }

    private boolean checkSign(HttpServletRequest request, String[] args) {
        Map parameters = new HashMap();
        for (String arg : args) {
            parameters.put(arg, getParameter(request, arg));
        }
        String signLocal = SignCheckUtil.sign(parameters, getSignCheckSecret());

        String signParam = getParameter(request, "_sign");
        //"81f0f5c9bd12ee2c9d2400852cfadbfe".equalsIgnoreCase(signParam)这个是为了解决远程协助组件的一次发布失误
        Boolean checkSuccess = "81f0f5c9bd12ee2c9d2400852cfadbfe".equalsIgnoreCase(signParam) || StringUtil.equalsIgnoreCase(signParam, signLocal);

        if (BoolUtil.isFalse(checkSuccess)) {
            log.info("SignCheckInterceptor SignCheckFailed signParam={}, signLocal={}, signCheckSecret={}, parameters={}", signParam, signLocal, getSignCheckSecret(), parameters);
        }
        return checkSuccess;
    }

    private String getParameter(HttpServletRequest request, String key) {
        String value = request.getParameter(key);
        if (StringUtil.isEmpty(value)) {
            value = request.getHeader(key);
        }
        return value;
    }

    public String getSignCheckSecret() {
        return Asserts.noNull(signCheckSecret, "signCheckSecret 不可以为空");
    }

    public void setSignCheckSecret(String signCheckSecret) {
        this.signCheckSecret = signCheckSecret;
    }
}