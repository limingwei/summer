package cn.limw.summer.web;

import org.slf4j.Logger;

import cn.limw.summer.util.ArrayUtil;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年1月20日 下午2:01:05)
 * @since Java7
 */
public class ContentTypeUtil {
    private static final Logger log = Logs.slf4j();

    private static final String[] IMAGE_CONTENT_TYPES = { "image/gif", "image/jpeg", "image/png" };

    private static final String[] AMR_CONTENT_TYPES = { "audio/amr" };

    private static final String[] MP4_CONTENT_TYPES = { "video/mp4" };

    public static Boolean isImage(String contentType) {
        return ArrayUtil.contains(IMAGE_CONTENT_TYPES, (null == contentType ? "" : contentType).toLowerCase());
    }

    public static Boolean isAmr(String contentType) {
        return ArrayUtil.contains(AMR_CONTENT_TYPES, (null == contentType ? "" : contentType).toLowerCase());
    }

    public static Boolean isMp4(String contentType) {
        return ArrayUtil.contains(MP4_CONTENT_TYPES, (null == contentType ? "" : contentType).toLowerCase());
    }

    public static String guessBySuffix(String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            return "guess_by_suffix_file_name_is_null";
        }
        log.error("guess_by_suffix_unknown fileName={}", fileName);
        return "guess_by_suffix_unknown";
    }

    public static String contentTypeToSuffix(String contentType) {
        if (StringUtil.equals(contentType, "image/jpeg")) {
            return "jpg";
        } else if (StringUtil.equals(contentType, "video/mp4")) {
            return "mp4";
        } else if (StringUtil.equals(contentType, "audio/amr")) {
            return "amr";
        } else if (StringUtil.equals(contentType, "audio/mp3")) {
            return "mp3";
        } else {
            return "";
        }
    }
}