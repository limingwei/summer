package cn.limw.summer.spring.web.multipart.commons;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.limw.summer.spring.web.multipart.MultipartFileUtil;
import cn.limw.summer.spring.web.servlet.Mvcs;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年2月3日 上午10:17:48)
 * @since Java7
 * @see org.springframework.web.method.annotation.RequestParamMethodArgumentResolver
 */
public class CommonsMultipartResolver extends AbstractCommonsMultipartResolver {
    private static final Logger log = Logs.slf4j();

    private static final String DEFAULT_ENCODING_UTF_8 = "UTF-8";

    private static final ThreadLocal<HttpServletRequest> REQUEST_THREAD_LOCAL = new ThreadLocal<HttpServletRequest>();

    public CommonsMultipartResolver() {
        setDefaultEncoding(DEFAULT_ENCODING_UTF_8);
    }

    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
        REQUEST_THREAD_LOCAL.set(request);
        return super.resolveMultipart(request);
    }

    protected MultipartParsingResult parseFileItems(List<FileItem> fileItems, String encoding) {
        MultipartParsingResult result = super.parseFileItems(fileItems, encoding);

        MultiValueMap<String, MultipartFile> multipartFiles = result.getMultipartFiles();
        Set<String> keys = multipartFiles.keySet();

        for (String key : keys) {
            MultipartFile multipartFile = multipartFiles.getFirst(key);
            String content_type = getContentType(multipartFile);

            String contentType = multipartFile.getContentType();

            log.info("parseFileItems name=" + multipartFile.getName() + ", originalFilename=" + multipartFile.getOriginalFilename() + ", contentType=" + contentType + ", ip=" + Mvcs.getRemoteAddr() //
                    + ", Accept=" + Mvcs.getRequestHeader("Accept") //
                    + ", Accept-Encoding=" + Mvcs.getRequestHeader("Accept-Encoding") //
                    + ", Accept-Language=" + Mvcs.getRequestHeader("Accept-Language") //
                    + ", User-Agent=" + Mvcs.getRequestHeader("User-Agent") //
                    + ", Referer=" + Mvcs.getRequestHeader("Referer"));

            if ((StringUtil.isEmpty(contentType) || "multipart/form-data".equalsIgnoreCase(contentType)) && !StringUtil.isEmpty(content_type)) {
                ((CommonsMultipartFile) multipartFile).setContentType(content_type);
            }
        }

        return result;
    }

    public static String getContentType(MultipartFile multipartFile) { // content_type 参数优先
        String contentType = REQUEST_THREAD_LOCAL.get().getParameter("content_type");
        contentType = StringUtil.isEmpty(contentType) ? REQUEST_THREAD_LOCAL.get().getHeader("content_type") : contentType;
        if (!StringUtil.isEmpty(contentType)) {
            return contentType;
        } else {
            return MultipartFileUtil.getContentType(multipartFile);
        }
    }
}