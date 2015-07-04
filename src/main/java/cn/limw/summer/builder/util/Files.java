package cn.limw.summer.builder.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

/**
 * Files
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年1月8日 下午5:24:22)
 */
public class Files {
    private static final Logger log = Logger.getLogger(Files.class);

    private static final Map<String, String> SOURCE_CACHE = new ConcurrentHashMap<String, String>();

    /**
     * 根据路径判断通过网络或本地文件方式读取内容
     */
    public static String read(String path) {
        try {
            String source = SOURCE_CACHE.get(path);
            if (null == source) {
                log.info("读取资源 " + path);
                InputStream inputStream = (path.startsWith("http://") || path.startsWith("https://")) ? new URL(path).openStream() : new FileInputStream(path);
                source = read(new InputStreamReader(inputStream));
                SOURCE_CACHE.put(path, source);
            }
            return source;
        } catch (Exception e) {
            throw Errors.wrap((path.startsWith("http://") || path.startsWith("https://")) ? e.getMessage() + getCanonicalPath(new File(path)) : "找不到文件 " + getCanonicalPath(new File(path)), e);
        }
    }

    /**
     * 从一个字符输入流读取类容并返回
     */
    public static String read(Reader reader) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw Errors.wrap(e);
        }
        return stringBuffer.toString();
    }

    /**
     * 返回文件完整路径
     */
    public static String getCanonicalPath(File file) {
        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            throw Errors.wrap(e);
        }
    }

    /**
     * 重命名文件后返回原路径文件
     */
    public static File backup(File oldFile) {
        String oldPath = getCanonicalPath(oldFile);
        String suffix = getSuffix(oldFile);
        for (int i = 2;; i++) {
            File fileBackup = new File(oldPath.replace("." + suffix, "_backup_" + i + "." + suffix));
            if (!fileBackup.exists()) {
                oldFile.renameTo(fileBackup);// 旧文件重命名备份
                return new File(oldPath);// 返回旧文件同名的新文件
            }
        }
    }

    /**
     * 返回重命名后的文件
     */
    public static File rename(File oldFile) {
        String oldPath = getCanonicalPath(oldFile);
        String suffix = getSuffix(oldFile);
        for (int i = 2;; i++) {
            File fileNew = new File(oldPath.replace("." + suffix, "_rename_" + i + "." + suffix));
            if (!fileNew.exists()) {
                return fileNew;
            }
        }
    }

    /**
     * 返回文件后后缀,文件名最后一个点号后的字符串
     */
    public static String getSuffix(File file) {
        return file.getName().substring(file.getName().lastIndexOf(".") + 1);
    }

    /**
     * 返回FileWriter,创建上级文件夹
     */
    public static Writer newWriter(File file) {
        try {
            file.getParentFile().mkdirs();
            return new FileWriter(file);
        } catch (IOException e) {
            throw Errors.wrap(e);
        }
    }

    /**
     * 返回空Writer
     */
    public static Writer nullWriter() {
        return new Writer() {
            public void write(char[] cbuf, int off, int len) throws IOException {}

            public void flush() throws IOException {}

            public void close() throws IOException {}
        };
    }
}