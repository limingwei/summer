package cn.limw.summer.ftp.apache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import cn.limw.summer.util.Files;

/**
 * 封装 Apache FTPClient
 * @author li
 * @version 1 (2014年10月14日 下午5:11:00)
 * @since Java7
 */
public class Ftp {
    private FTPClient ftpClient;

    public Ftp() {}

    public Ftp(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public Ftp(String hostname, Integer port, String username, String password) {
        this(connect(hostname, port, username, password));
    }

    public Ftp(String hostname, String username, String password) {
        this(connect(hostname, username, password));
    }

    public Boolean deleteFile(String pathname) {
        try {
            return getFtpClient().deleteFile(pathname);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean retrieveFile(String remote, OutputStream local) {
        try {
            return getFtpClient().retrieveFile(remote, local);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean storeFile(String fileName, InputStream inputStream) {
        try {
            Boolean success = getFtpClient().storeFile(fileName, inputStream);
            Files.close(inputStream);
            return success;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public static FTPClient connect(String hostname, Integer port, String username, String password) {
        try {
            FTPClient ftpClient = new FTPClient();
            if (null == port) {
                ftpClient.connect(hostname);
            } else {
                ftpClient.connect(hostname, port);
            }
            Integer reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
            } else {
                ftpClient.login(username, password);
                ftpClient.pasv();
                ftpClient.enterLocalPassiveMode();
            }
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // 以BINARY格式传送文件
            return ftpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static FTPClient connect(String hostname, String username, String password) {
        return connect(hostname, null, username, password);
    }
}