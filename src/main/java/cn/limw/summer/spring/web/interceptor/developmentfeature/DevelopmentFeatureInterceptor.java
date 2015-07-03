package cn.limw.summer.spring.web.interceptor.developmentfeature;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import cn.limw.summer.spring.web.servlet.AbstractHandlerInterceptor;
import cn.limw.summer.spring.web.servlet.Mvcs;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年3月6日 下午1:29:01)
 * @since Java7
 */
public class DevelopmentFeatureInterceptor extends AbstractHandlerInterceptor {
    private static final Logger log = Logs.slf4j();

    private String developerIpFilePath = "/home/*/conf/developer-ips.txt";

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        DevelopmentFeature annotation = DevelopmentFeatureUtil.getAnnotation(handler);
        if (null == annotation) { // 没有注解
            return true;
        } else if (mayAccess(request)) {
            return true;
        } else {
            Mvcs.write("DevelopmentFeature ... ");
            return false;
        }
    }

    public Boolean mayAccess(HttpServletRequest request) throws FileNotFoundException, IOException {
        return mayAccessByIp(request);
    }

    public Boolean mayAccessByIp(HttpServletRequest request) throws IOException, FileNotFoundException {
        if ("localhost".equals(Mvcs.getServerName())) {
            return true;
        }
        String ip = request.getRemoteAddr();
        File file = new File(getDeveloperIpFilePath());
        if (file.exists() && file.isFile()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));) {
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.equals(ip)) {
                        return true;
                    }
                }
            }
        }

        log.info("trying to access developmentFeature ip={}, uri=", ip, request.getRequestURI());
        return false;
    }

    public String getDeveloperIpFilePath() {
        return developerIpFilePath;
    }

    public void setDeveloperIpFilePath(String developerIpFilePath) {
        this.developerIpFilePath = developerIpFilePath;
    }
}