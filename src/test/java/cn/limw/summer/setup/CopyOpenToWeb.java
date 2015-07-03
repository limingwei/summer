package cn.limw.summer.setup;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;

import cn.limw.summer.util.Files;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年5月12日 下午4:35:23)
 * @since Java7
 */
public class CopyOpenToWeb {
    private static final Logger log = Logs.slf4j();

    @Test
    public void copyOpenToWeb() {
        log.info("CopyOpenToWeb start");

        copyDir("/home/*/source-code/*-open/target/classes", "/home/*/www/*-web-full/WEB-INF/classes");

        log.info("CopyOpenToWeb end");
    }

    private void copyDir(String from, String to) {
        File fromFile = new File(from);
        if (fromFile.exists() && !fromFile.isDirectory()) {
            throw new RuntimeException("fromFile 是文件");
        }
        File toFile = new File(to);
        if (toFile.exists() && !toFile.isDirectory()) {
            throw new RuntimeException("toFile 是文件");
        }
        copyDir(fromFile, toFile);
    }

    private void copyDir(File from, File to) {
        log.info("复制目录, " + Files.getCanonicalPath(from) + ", " + Files.getCanonicalPath(to));

        File[] files = from.listFiles();
        for (File file : files) {
            File toFile = new File(Files.getCanonicalPath(to) + File.separator + file.getName());
            if (file.isDirectory()) {
                copyDir(file, toFile);
            } else if (file.getName().endsWith(".class")) {
                copyFile(file, toFile);
            } else {
                log.info("不复制 " + Files.getCanonicalPath(file));
            }
        }
    }

    private void copyFile(File from, File to) {
        log.info("复制文件, " + Files.getCanonicalPath(from) + ", " + Files.getCanonicalPath(to));
        to.getParentFile().mkdirs();
        if (!to.exists()) {
            Files.createNewFile(to);
        }
        Files.write(from, to);
    }
}