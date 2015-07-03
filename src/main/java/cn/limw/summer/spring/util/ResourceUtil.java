package cn.limw.summer.spring.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;

import cn.limw.summer.util.Files;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2014年9月30日 下午5:17:41)
 * @since Java7
 */
public class ResourceUtil {
    private static final Logger log = Logs.slf4j();

    public static ClassPathResource classPathResource(String path) {
        return new ClassPathResource(path);
    }

    public static InputStream classPathRead(String path) {
        try {
            return classPathResource(path).getInputStream();
        } catch (FileNotFoundException e) {
            log.error("classPathRead throws FileNotFoundException path={}", path);

            String url = ResourceUtil.class.getProtectionDomain().getCodeSource().getLocation().toString();
            if (url.endsWith("jar")) {
                return Files.classPathRead(ResourceUtil.class, path);
            } else {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}