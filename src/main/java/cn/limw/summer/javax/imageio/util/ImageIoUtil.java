package cn.limw.summer.javax.imageio.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import cn.limw.summer.java.io.exception.IORuntimeException;

/**
 * @author li
 * @version 1 (2015年5月20日 下午12:05:33)
 * @since Java7
 */
public class ImageIoUtil {
    public static Boolean write(BufferedImage image, String format, OutputStream outputStream) {
        try {
            return ImageIO.write(image, format, outputStream);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }
}