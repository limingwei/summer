package cn.limw.summer.spring.web.multipart.commons;

import org.apache.commons.fileupload.FileItem;

import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年2月3日 下午3:36:30)
 * @since Java7
 */
public class CommonsMultipartFile extends AbstractCommonsMultipartFile {
    private static final long serialVersionUID = 7491237995094535214L;

    private String contentType;

    public CommonsMultipartFile(FileItem fileItem) {
        super(fileItem);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return StringUtil.isEmpty(contentType) ? super.getContentType() : contentType;
    }
}