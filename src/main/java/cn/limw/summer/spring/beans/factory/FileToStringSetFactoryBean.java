package cn.limw.summer.spring.beans.factory;

import java.util.Set;

import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Files;

/**
 * 读取文件的每一行,转换成
 * @author li
 * @version 1 (2014年10月17日 下午1:42:16)
 * @since Java7
 */
public class FileToStringSetFactoryBean extends SingletonFactoryBean<Set<String>> {
    private String filePath;

    public Class<?> getObjectType() {
        return Set.class;
    }

    public Set<String> getObject() throws Exception {
        return Files.readFileToLines(Files.classPathRead(FileToStringSetFactoryBean.class, getFilePath()));
    }

    public String getFilePath() {
        return Asserts.noEmpty(filePath, "必须为 FileToStringSetFactoryBean 指定 filePath");
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}