package cn.limw.summer.spring.web.multipart;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.multipart.MultipartFile;

import cn.limw.summer.util.ArrayUtil;
import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Maps;
import cn.limw.summer.util.StringUtil;
import cn.limw.summer.web.ContentTypeUtil;

/**
 * @author li
 * @version 1 (2014年12月15日 下午2:07:05)
 * @since Java7
 */
public class MultipartFileUtil {
    private static final Map<String, String> CONTENT_TYPE_BY_FILE_NAME = Maps.toMap(".jpg", "image/jpeg", ".amr", "audio/amr");

    private static final String[] IMAGE_FILE_NAME_SUFFIX = { ".jpg" };

    public static String getContentType(MultipartFile multipartFile) {
        Asserts.noNull(multipartFile, "multipartFile 为空");
        String contentType = multipartFile.getContentType();
        if (StringUtil.isEmpty(contentType)) { // 为空或值不正确
            String originalFilename = multipartFile.getOriginalFilename();
            contentType = guessContentTypeByFileName(originalFilename);
        }
        return contentType;
    }

    public static String guessContentTypeByFileName(String originalFilename) {
        Asserts.noEmpty(originalFilename, "文件名不可以为空");
        for (Entry<String, String> entry : CONTENT_TYPE_BY_FILE_NAME.entrySet()) {
            if (StringUtil.endWith(originalFilename, entry.getKey())) {
                return entry.getValue();
            }
        }
        return "content-type-of-" + originalFilename;
    }

    public static Boolean isImage(MultipartFile multipartFile) {
        return ArrayUtil.contains(IMAGE_FILE_NAME_SUFFIX, guessContentTypeByFileName(multipartFile.getOriginalFilename()));
    }

    public static InputStream getInputStream(MultipartFile multipartFile) {
        try {
            return multipartFile.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean isAmr(MultipartFile file) {
        return ContentTypeUtil.isAmr(file.getContentType());
    }
}