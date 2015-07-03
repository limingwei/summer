package cn.limw.summer.web.upload.ftp;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.ftp.apache.Ftp;
import cn.limw.summer.spring.web.servlet.Mvcs;
import cn.limw.summer.web.upload.AbstractFileStore;

/**
 * 上传文件到Ftp文件服务器
 * @author li
 * @version 1 (2014年10月14日 上午9:37:02)
 * @since Java7
 */
public class FtpFileStore extends AbstractFileStore {
    private String hostname;

    private Integer port;

    private String username;

    private String password;

    private Ftp ftp;

    public Ftp getFtp() {
        if (null == ftp) {
            ftp = new Ftp(getHostname(), getPort(), getUsername(), getPassword());
        }
        return ftp;
    }

    public Boolean upload(String fileName, InputStream inputStream) {
        return getFtp().storeFile(fileName, inputStream);
    }

    public void download(String fileName, HttpServletRequest request, HttpServletResponse response) {
        getFtp().retrieveFile(fileName, Mvcs.getOutputStream(response));
    }

    public Boolean delete(String fileName) {
        return getFtp().deleteFile(fileName);
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return super.toString() + ", hostname=" + getHostname() + ", port=" + getPort() + ", username=" + getUsername();
    }
}