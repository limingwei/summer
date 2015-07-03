package cn.limw.summer.http;

import java.io.Serializable;

/**
 * @author li
 * @version 1 (2015年7月2日 上午10:45:43)
 * @since Java7
 */
public class MultipartEntity implements Serializable {
    private static final long serialVersionUID = -7762385251598474248L;

    private String contentType;

    private String fileName;

    private Object multipart;

    public MultipartEntity(Object multipart, String contentType, String fileName) {
        this.multipart = multipart;
        this.contentType = contentType;
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] multipartByteArray() {
        return Util.multipartFieldToByteArray(multipart);
    }

    public String toString() {
        return super.toString() + ", multipart=" + multipart + ", contentType=" + contentType + ", fileName=" + fileName;
    }
}