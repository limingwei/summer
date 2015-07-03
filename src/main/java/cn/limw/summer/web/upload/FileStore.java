package cn.limw.summer.web.upload;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author li
 * @version 1 (2014年9月10日 上午11:49:03)
 * @since Java7
 */
public interface FileStore {
    /**
     * 上传文件
     * @param fileName 文件名
     * @param inputStream 文件内容
     */
    public Boolean upload(String fileName, InputStream inputStream);

    /**
     * 下载文件
     * @param fileName 文件名
     * @param request
     * @param response
     */
    public void download(String fileName, HttpServletRequest request, HttpServletResponse response);

    /**
     * 删除文件
     * @param fileName
     */
    public Boolean delete(String fileName);
}