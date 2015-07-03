package cn.limw.summer.javax.servlet.http.util;

import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年6月18日 下午3:39:43)
 * @since Java7
 */
public class HeaderUtil {
    /**
     * Content-disposition: attachment; filename="6PKLToWKRJfkdiEggo_IoRo4pI24vQzDk89VtxCqHOF59NkmEsEJpayrn4_BAirJ.jpg"
     */
    public static String getFilenameFromContentDisposition(String contentDisposition) {
        if (StringUtil.isEmpty(contentDisposition)) {
            return null;
        }
        String fromString = "filename=\"";
        int fromStringLength = fromString.length();
        int fromIndex = contentDisposition.indexOf(fromString);
        if (fromIndex > 0) {
            int fromStringEndIndex = fromIndex + fromStringLength;
            int engIndex = contentDisposition.indexOf("\"", fromStringEndIndex);
            if (engIndex >= 0) {
                return contentDisposition.substring(fromStringEndIndex, engIndex);
            } else {
                return contentDisposition.substring(fromStringEndIndex);
            }
        }
        return null;
    }
}