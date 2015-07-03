package cn.limw.summer.ffmpeg;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;

import cn.limw.summer.java.io.util.BufferedReaderUtil;
import cn.limw.summer.java.lang.thread.ProcessKiller;
import cn.limw.summer.util.Files;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年3月19日 下午5:31:35)
 * @since Java7
 */
public class SimpleAudioFormatConverter extends AbstractAudioFormatConverter {
    private static final Logger log = Logs.slf4j();

    public static final SimpleAudioFormatConverter INSTANCE = new SimpleAudioFormatConverter();

    public void amrToMp3(File source, File target) {
        try {
            String command = "ffmpeg -i " + Files.getCanonicalPath(source) + " " + Files.getCanonicalPath(target);

            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(command);
            runtime.addShutdownHook(new ProcessKiller(process));
            logStream("InputStream", process.getInputStream());
            logStream("ErrorStream", process.getErrorStream());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void logStream(String prefix, InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = BufferedReaderUtil.readLine(bufferedReader)) != null) {
            log.info(prefix + ": " + line);
        }
    }
}

//FFmpeg version 0.6.5, Copyright (c) 2000-2010 the FFmpeg developers
//built on Jan 29 2012 17:52:15 with gcc 4.4.5 20110214 (Red Hat 4.4.5-6)
//configuration: --prefix=/usr --libdir=/usr/lib64 --shlibdir=/usr/lib64 --mandir=/usr/share/man --incdir=/usr/include --disable-avisynth --extra-cflags='-O2 -g -pipe -Wall -Wp,-D_FORTIFY_SOURCE=2 -fexceptions -fstack-protector --param=ssp-buffer-size=4 -m64 -mtune=generic -fPIC' --enable-avfilter --enable-avfilter-lavf --enable-libdc1394 --enable-libdirac --enable-libfaac --enable-libfaad --enable-libfaadbin --enable-libgsm --enable-libmp3lame --enable-libopencore-amrnb --enable-libopencore-amrwb --enable-librtmp --enable-libschroedinger --enable-libspeex --enable-libtheora --enable-libx264 --enable-gpl --enable-nonfree --enable-postproc --enable-pthreads --enable-shared --enable-swscale --enable-vdpau --enable-version3 --enable-x11grab
//libavutil     50.15. 1 / 50.15. 1
//libavcodec    52.72. 2 / 52.72. 2
//libavformat   52.64. 2 / 52.64. 2
//libavdevice   52. 2. 0 / 52. 2. 0
//libavfilter    1.19. 0 /  1.19. 0
//libswscale     0.11. 0 /  0.11. 0
//libpostproc   51. 2. 0 / 51. 2. 0
//[amr @ 0x6bc670]Estimating duration from bitrate, this may be inaccurate
//Input #0, amr, from '9a90284766e846778c71c9d0b09a107a.amr':
//Duration: N/A, bitrate: N/A
//  Stream #0.0: Audio: amrnb, 8000 Hz, 1 channels, flt
//Output #0, mp3, to '2.mp3':
//Metadata:
//  TSSE            : Lavf52.64.2
//  Stream #0.0: Audio: libmp3lame, 8000 Hz, 1 channels, s16, 64 kb/s
//Stream mapping:
//Stream #0.0 -> #0.0
//Press [q] to stop encoding
//size=      37kB time=4.68 bitrate=  64.1kbits/s    
//video:0kB audio:37kB global headers:0kB muxing overhead 0.088141%