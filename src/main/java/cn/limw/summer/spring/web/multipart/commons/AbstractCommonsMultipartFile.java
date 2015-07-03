package cn.limw.summer.spring.web.multipart.commons;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author li
 * @version 1 (2015年6月3日 下午2:20:15)
 * @since Java7
 */
public class AbstractCommonsMultipartFile extends CommonsMultipartFile {
    private static final long serialVersionUID = -4035669110707677875L;

    public AbstractCommonsMultipartFile(FileItem fileItem) {
        super(fileItem);
    }
}