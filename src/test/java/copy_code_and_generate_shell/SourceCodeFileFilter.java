package copy_code_and_generate_shell;

import java.io.File;
import java.io.FileFilter;

import cn.limw.summer.util.Files;

/**
 * @author li
 * @version 1 (2015年3月17日 下午12:56:21)
 * @since Java7
 */
public class SourceCodeFileFilter implements FileFilter {
    private String from;

    private String project;

    public SourceCodeFileFilter(String from, String project) {
        this.from = from;
        this.project = project;
    }

    public boolean accept(File file) {
        if (file.getName().endsWith(".svn")) {
            return false;
        }
        if (Files.getCanonicalPath(file).contains((from + project + "/src").replace('/', File.separatorChar))) {
            if (Files.getCanonicalPath(file).contains((from + project + "/src/main/webapp/wro/").replace('/', File.separatorChar))) {
                return false; // wro 不复制
            } else if (Files.getCanonicalPath(file).contains((from + project + "/src/main/webapp/source/wro/").replace('/', File.separatorChar))) {
                return false; // wro 不复制
            } else {
                return true; // 其他代码复制
            }
        } else if (Files.getCanonicalPath(file).contains((from + project + "/pom.xml").replace('/', File.separatorChar))) {
            return true;
        } else if (Files.getCanonicalPath(file).contains((from + project + "/bin").replace('/', File.separatorChar))) {
            return true;
        } else if (Files.getCanonicalPath(file).contains((from + project + "/about.txt").replace('/', File.separatorChar))) {
            return true;
        } else if (Files.getCanonicalPath(file).contains((from + project + "/about").replace('/', File.separatorChar))) {
            if (Files.getCanonicalPath(file).contains("/svn_revision/")) {
                return false;
            } else {
                return true;
            }
        } else if (Files.getCanonicalPath(file).contains((from + project + "/webdefault.xml").replace('/', File.separatorChar))) {
            return true;
        } else {
            return false; // 其他不复制
        }
    }
}