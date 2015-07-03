package cn.limw.summer.log4j.appender.file.rolling.daily;

import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

import cn.limw.summer.util.Errors;

/**
 * 扩展 DailyRollingFileAppender 可以通过环境变量配置文件路径前缀
 * @author li
 * @version 1 (2014年8月5日 上午10:21:30)
 * @since Java7
 * @see org.apache.log4j.RollingFileAppender
 * @see org.apache.log4j.DailyRollingFileAppender
 */
public class DailyRollingFileAppender extends AbstractDailyRollingFileAppender {
    /**
     * The default maximum file size is 1GB.
     * @see org.apache.log4j.helpers.OptionConverter.toFileSize(String, long)
     */
    private long maxFileSize = 1024 * 1024 * 1024;

    private long nextRollover = 0;

    public void setFile(String file) {
        if (file.contains("#{") && file.contains("}")) {
            super.setFile(DailyRollingFileAppenderUtil.fileWithExpression(file));
        } else {
            super.setFile(file);
        }
    }

    protected void setQWForFiles(Writer writer) {
        this.qw = new CountingQuietWriter(writer, errorHandler);
    }

    protected void subAppend(LoggingEvent event) {
        super.subAppend(event);

        roll_over_by_size();
    }

    public void rollOver() {
        try {
            update_next_roll_over();

            super.rollOver();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void roll_over_by_size() {
        try {
            if (fileName != null && qw != null) {
                if (qw instanceof CountingQuietWriter) {
                    long size = ((CountingQuietWriter) qw).getCount();
                    if (size >= maxFileSize && size >= nextRollover) {
                        rollOver();
                    }
                }
            }
        } catch (Throwable e) {
            System.err.println("roll_over_by_size 失败, " + Errors.stackTrace(e));
        }
    }

    private void update_next_roll_over() {
        try {
            if (qw != null) {
                if (qw instanceof CountingQuietWriter) {
                    long size = ((CountingQuietWriter) qw).getCount();
                    LogLog.debug("rolling over count=" + size);
                    nextRollover = size + maxFileSize;
                }
            }
        } catch (Throwable e) {
            System.err.println("update_next_roll_over 失败, " + Errors.stackTrace(e));
        }
    }
}