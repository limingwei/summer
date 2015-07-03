package cn.limw.summer.upload.local;

import java.io.File;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import cn.limw.summer.spring.web.servlet.Mvcs;
import cn.limw.summer.util.Files;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;
import cn.limw.summer.web.upload.AbstractFileStore;

/**
 * 上传文件到本地文件系统
 * @author li
 * @version 1 (2014年9月10日 下午12:06:26)
 * @since Java7
 */
public class LocalFileStore extends AbstractFileStore {
    private static final Logger log = Logs.slf4j();

    private String rootPath = "web:upload";

    public Boolean upload(String fileName, InputStream inputStream) {
        File file = new File(getRoot(), fileName);
        file.getParentFile().mkdirs();
        Files.write(inputStream, file);
        log.info("uploaded fileName={}, filePath={}", fileName, Files.getCanonicalPath(file));
        return true;
    }

    public void download(String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            Files.write(new File(getRoot(), fileName), Mvcs.getOutputStream());
        } catch (Exception e) {
            Files.write(e.getMessage().getBytes(), Mvcs.getOutputStream());//返回 默认图片? 返回 404?
        }
    }

    public Boolean delete(String fileName) {
        return new File(getRoot(), fileName).delete();
    }

    private File getRoot() {
        if (StringUtil.startWith(getRootPath(), "web:")) {
            return new File(Mvcs.getRealPath(getRootPath().substring(4)));
        } else {
            return new File(getRootPath());
        }
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String toString() {
        return super.toString() + ", rootPath=" + getRootPath() + ", " + Files.getCanonicalPath(getRoot());
    }
}