package cn.limw.summer.log4j.appender.file.rolling.daily;

import java.util.Properties;

import cn.limw.summer.java.lang.SystemUtil;
import cn.limw.summer.time.Clock;

/**
 * @author li
 * @version 1 (2015年3月3日 上午9:53:26)
 * @since Java7
 */
public class DailyRollingFileAppenderUtil {
    /**
     * @param file #{logFileRoot}/log/*-web/*-web.all.log
     */
    public static String fileWithExpression(String file) {
        Properties properties = SystemUtil.getEnvAndProperties();
        String logFile = merge(file, properties);
        System.err.println("log4j:INFO " + Clock.now().toYYYY_MM_DD_HH_MM_SS() + " DailyRollingFileAppender.setFile() file=" + file + " logFile=" + logFile);
        return logFile;
    }

    public static String merge(String fileName, Properties properties) {
        int a = fileName.indexOf("#{");
        int b = fileName.indexOf('}', a);
        String placeholder = fileName.substring(a + 2, b);
        String replacement = properties.getProperty(placeholder, "");
        return fileName.replace("#{" + placeholder + "}", replacement);
    }
}