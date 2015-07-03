package cn.limw.summer.http;

import java.io.File;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import cn.limw.summer.java.net.util.NetUrlUtil;
import cn.limw.summer.util.Files;
import cn.limw.summer.util.Logs;

/**
 * @author 明伟
 */
public class Util {
    private static final Logger log = Logs.slf4j();

    public static byte[] multipartFieldToByteArray(Object each) {
        if (each instanceof MultipartEntity) {
            return ((MultipartEntity) each).multipartByteArray();
        } else if (each instanceof File) {
            return Files.toBytes(Files.fileInputStream((File) each));
        } else if (each instanceof URL) {
            return Files.toBytes(NetUrlUtil.openStream((URL) each));
        } else if (each instanceof InputStream) {
            return Files.toBytes((InputStream) each);
        } else if (each instanceof byte[]) {
            return (byte[]) each;
        } else {
            throw new IllegalArgumentException("未能处理参数类型 " + each + ", type=" + each.getClass().getName());
        }
    }

    public static Boolean isMultipartField(Object each) {
        return each instanceof File || each instanceof URL || each instanceof InputStream || each instanceof byte[] || each instanceof MultipartEntity;
    }

    /**
     * 文件名
     */
    public static String fileName(Object file) {
        String filename = "unknown.unknown";
        if (file instanceof MultipartEntity) {
            return ((MultipartEntity) file).getFileName();
        } else if (file instanceof File) {
            filename = ((File) file).getName();
        } else if (file instanceof URL) {
            filename = ((URL) file).getFile();
            int i = filename.lastIndexOf("/");
            if (i > -1) {
                filename = filename.substring(i + 1);
            }
        }
        return filename;
    }

    /**
     * contentType of File or URL
     */
    public static String contentType(Object file) {
        if (file instanceof MultipartEntity) {
            return ((MultipartEntity) file).getContentType();
        }

        // 根据Filename判断
        String fileName = fileName(file);
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex < 0) { // 无后缀, 默认
            return "application/octet-stream";
        }
        String postfix = fileName.substring(lastDotIndex, fileName.length()).toUpperCase();
        if (postfix.equals(".XLS") || postfix.equals(".XLT") || postfix.equals(".XLW") || postfix.equals(".CSV")) {
            return "application/vnd.ms-excel";
        } else if (postfix.equals(".PPT") || postfix.equals(".PPS")) {
            return "application/vnd.ms-powerpoint";
        } else if (postfix.equals(".DOC")) {
            return "application/msword";
        } else if (postfix.equals(".PDF")) {
            return "application/pdf";
        } else if (postfix.equals(".RTF")) {
            return "application/rtf";
        } else if (postfix.equals(".TXT")) {
            return "text/plain";
        } else if (postfix.equals(".XML")) {
            return "text/xml";
        } else if (postfix.equals(".JPG") || postfix.equals(".JPEG")) {
            return "image/jpeg";
        } else if (postfix.equals(".BMP")) {
            return "image/bmp";
        } else if (postfix.equals(".GIF")) {
            return "image/gif";
        } else if (postfix.equals(".MP3")) {
            return "audio/mpeg";
        } else if (postfix.equals(".AVI")) {
            return "video/x-msvideo";
        } else if (postfix.equals(".MPA") || postfix.equals(".MPE") || postfix.equals(".MPEG") || postfix.equals(".MPG")) {
            return "video/mpeg";
        } else if (postfix.equals(".ZIP") || postfix.equals(".RAR")) {
            return "application/zip";
        } else if (postfix.equals(".TXT")) {
            return "text/plain";
        } else {
            return "application/octet-stream"; // 默认
        }
    }

    /**
     * cookieToString
     */
    public static String cookieToString(List<HttpCookie> cookies) {
        String cookieString = "";
        if (null == cookies) {
            return cookieString;
        }
        for (HttpCookie cookie : cookies) {
            cookieString += cookie.getName() + "=" + cookie.getValue() + "; ";
            if (null != cookie.getPath() && !cookie.getPath().isEmpty()) {
                cookieString += "PATH=" + cookie.getPath() + "; ";
            }
            if (null != cookie.getDomain() && !cookie.getDomain().isEmpty()) {
                cookieString += "DOMAIN=" + cookie.getDomain() + "; ";
            }
            if (-1 != cookie.getMaxAge()) {
                cookieString += "EXPIRES=" + cookie.getMaxAge() + "; ";
            }
        }
        return cookieString;
    }

    /**
     * equals
     */
    public static Boolean equals(String str1, String str2) {
        if (null != str1) {
            return str1.equals(str2);
        } else {
            return null == str2;
        }
    }

    /**
     * sameExceptValue
     */
    public static Boolean sameExceptValue(HttpCookie cookie1, HttpCookie cookie2) {
        return equals(cookie1.getName(), cookie2.getName()) //
                && equals(cookie1.getPath(), cookie2.getPath())//
                && equals(cookie1.getDomain(), cookie2.getDomain());
    }

    /**
     * stringToCookie
     */
    public static List<HttpCookie> stringToCookie(String cookieString) {
        List<HttpCookie> cookies = new ArrayList<HttpCookie>();
        String[] strs = cookieString.split(";");
        for (int i = 0; i < strs.length; i++) {
            String path = null, domain = null, expires = "-1", name = null, value = null;
            String[] pair = strs[i].split("=");
            if ("PATH".equalsIgnoreCase(pair[0].trim())) {
                path = pair[1].trim();
            } else if ("DOMAIN".equalsIgnoreCase(pair[0].trim())) {
                domain = pair[1].trim();
            } else if ("EXPIRES".equalsIgnoreCase(pair[0].trim())) {
                expires = pair[1].trim();
            } else {
                name = pair[0];
                value = pair.length > 1 ? pair[1] : "";
            }
            if (null != name && !name.trim().isEmpty()) {
                try {
                    HttpCookie cookie = new HttpCookie(name.trim(), value);
                    cookie.setPath(path);
                    cookie.setDomain(domain);
                    cookie.setMaxAge(Long.parseLong(expires));
                    cookies.add(cookie);
                } catch (Exception e) {
                    log.error("stringToCookie error " + e + ", " + cookieString, e);
                }
            }
        }
        return cookies;
    }

    public static String toString(Object value) {
        if (value instanceof char[]) {
            return new String((char[]) value);
        } else {
            return value + "";
        }
    }
}