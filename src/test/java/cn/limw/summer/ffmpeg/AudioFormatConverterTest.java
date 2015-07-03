package cn.limw.summer.ffmpeg;

import java.io.File;

import cn.limw.summer.ffmpeg.SimpleAudioFormatConverter;
import cn.limw.summer.util.Files;

/**
 * @author li
 * @version 1 (2015年2月2日 下午6:36:03)
 * @since Java7
 */
public class AudioFormatConverterTest {
    public static void main(String[] args) {
        Files.write(new SimpleAudioFormatConverter().amrToMp3(Files.fileInputStream("C:/Users/li/Desktop/1.amr")), new File("C:/Users/li/Desktop/22222222.mp3"));
    }
}