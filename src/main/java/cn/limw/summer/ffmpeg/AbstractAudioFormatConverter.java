package cn.limw.summer.ffmpeg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import org.slf4j.Logger;

import cn.limw.summer.util.Files;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年3月30日 上午9:39:15)
 * @since Java7
 */
public abstract class AbstractAudioFormatConverter implements AudioFormatConverter {
    private static final Logger log = Logs.slf4j();

    public InputStream amrToMp3(InputStream source) {
        String tmpdir = System.getProperty("java.io.tmpdir") + "/ffmpeg_audio_format_converter_temp/";
        log.info("amrToMp3() tmpdir={}", tmpdir);

        String uuid = StringUtil.uuid();
        File sourceFile = new File(tmpdir, uuid + ".amr");
        File targetFile = new File(tmpdir, uuid + ".mp3");

        if (!sourceFile.exists()) {
            sourceFile.getParentFile().mkdirs();
            sourceFile.deleteOnExit();
        }

        Files.write(source, sourceFile); // 写临时原文件

        amrToMp3(sourceFile, targetFile); // 转换

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Files.write(Files.fileInputStream(targetFile), byteArrayOutputStream); // 读转换后文件

        // sourceFile.delete();
        // targetFile.delete(); //删除临时文件

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public abstract void amrToMp3(File source, File target);
}