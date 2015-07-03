package cn.limw.summer.ffmpeg;

import java.io.File;
import java.io.InputStream;

/**
 * @author li
 * @version 1 (2015年2月2日 下午5:56:50)
 * @since Java7
 */
public interface AudioFormatConverter {
    public InputStream amrToMp3(InputStream source);

    public void amrToMp3(File source, File target);
}