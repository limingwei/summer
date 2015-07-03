package count_code;

import java.io.File;

import cn.limw.summer.code.Code;
import cn.limw.summer.util.Files;

/**
 * @author li
 * @since Java7 [2015年7月3日下午8:27:19]
 */
public class CountCode {
    public static void main(String[] args) {
        File src = new File("./src/main");
        System.err.println(Files.getCanonicalPath(src));
        System.err.println(Code.countingCode(src, "java", true, null));
    }
}
