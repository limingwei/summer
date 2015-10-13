package summer.util;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author li
 * @version 1 (2015年10月13日 上午9:33:50)
 * @since Java7
 */
public class Files {
    /**
     * 返回项目的classes目录
     */
    public static File root() {
        try {
            return new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e + " ", e);
        }
    }

    /**
     * 根据文件名正则表达式搜索一个路径下的文件,返回文件路径的List
     * @param file 要搜索的目录
     * @param regex 要求文件路径要符合的正则表达式
     * @param increase 是否递进搜索
     * @param fileOrFloder 1 文件 2 文件夹
     * @return 文件绝对路径列表
     */
    public static List<String> list(File file, String regex, Boolean increase, Integer fileOrFloder) {
        List<String> list = new ArrayList<String>();
        if (((1 == fileOrFloder && file.isFile()) || (2 == fileOrFloder && file.isDirectory())) && regex(file.getPath(), regex)) {
            list.add(file.getPath());
        } else if (increase && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                list.addAll(list(f, regex, increase, fileOrFloder)); // 递归调用本方法
            }
        }
        return list;
    }

    private static boolean regex(String input, String regex) {
        return Pattern.compile(regex).matcher(input).find();
    }
}