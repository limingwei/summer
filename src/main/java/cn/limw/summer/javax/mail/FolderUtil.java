package cn.limw.summer.javax.mail;

import javax.mail.Folder;

/**
 * @author li
 * @version 1 (2014年12月9日 下午5:38:10)
 * @since Java7
 */
public class FolderUtil {
    public static String modeToString(int mode) {
        if (mode == Folder.READ_ONLY) {
            return "READ_ONLY(" + mode + ")";
        } else if (mode == Folder.READ_WRITE) {
            return "READ_WRITE(" + mode + ")";
        } else {
            throw new RuntimeException("mode 只可能为 " + modeToString(Folder.READ_ONLY) + " 或 " + modeToString(Folder.READ_WRITE));
        }
    }
}