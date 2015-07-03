package cn.limw.summer.web.wro4j;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import ro.isdc.wro.model.resource.support.naming.TimestampNamingStrategy;
import ro.isdc.wro.util.WroUtil;

/**
 * @author lgb
 * @version 1 (2014年9月26日下午4:19:34)
 * @since Java7
 */
public class CustomTimestampNamingStrategy extends TimestampNamingStrategy {
    public String rename(String originalName, InputStream inputStream) {
        String baseName = FilenameUtils.getBaseName(originalName);
        String extension = FilenameUtils.getExtension(originalName);
        File wroDirectory = new File(WroUtil.getWorkingDirectory() + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "wro");
        if (wroDirectory.exists() && wroDirectory.isDirectory()) {
            for (File file : wroDirectory.listFiles()) {
                String filename = file.getName();
                if (filename.startsWith(baseName) && extension.equals(FilenameUtils.getExtension(filename))) {
                    file.delete();
                }
            }
        }
        return superRename(originalName, inputStream);
    }

    public String superRename(String originalName, InputStream inputStream) {
        final String baseName = FilenameUtils.getBaseName(originalName);
        final String extension = FilenameUtils.getExtension(originalName);
        final long timestamp = getTimestamp();
        final StringBuilder sb = new StringBuilder(baseName);
        if (!StringUtils.isEmpty(extension)) {
            sb.append(".").append(extension);
        }
        // sb.append("?v=").append(timestamp);
        return sb.toString();
    }

    protected long getTimestamp() {
        return Long.valueOf(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
    }
}
