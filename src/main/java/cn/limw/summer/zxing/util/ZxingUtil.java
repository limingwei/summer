package cn.limw.summer.zxing.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import cn.limw.summer.java.io.exception.IORuntimeException;
import cn.limw.summer.javax.imageio.util.ImageIoUtil;
import cn.limw.summer.util.Files;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * @author li
 * @version 1 (2015年5月20日 上午11:46:45)
 * @since Java7
 */
public class ZxingUtil {
    private static final int BLACK = 0xFF000000;

    private static final int WHITE = 0xFFFFFFFF;

    public static BitMatrix encode(String content, BarcodeFormat format, Integer width, Integer height, Map<EncodeHintType, ?> hints) {
        try {
            return new MultiFormatWriter().encode(content, format, width, height, hints);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }

    public static BitMatrix encodeQrCode(String content, Integer width, Integer height) {
        return encode(content, BarcodeFormat.QR_CODE, width, height, HintsBuilder.UTF8_ERROR_CORRECTION_H_MARGIN_0);
    }

    public static void encodeQrCodeToJpg(String content, Integer width, Integer height, FileOutputStream fileOutputStream) {
        BitMatrix bitMatrix = encodeQrCode(content, width, height);
        writeToOutputStream(bitMatrix, "jpg", fileOutputStream);
    }

    public static void encodeQrCodeToJpg(String content, Integer width, Integer height, File file) {
        BitMatrix bitMatrix = encodeQrCode(content, width, height);
        writeToFile(bitMatrix, "jpg", file);
    }

    public static void writeToFile(BitMatrix matrix, String format, File file) {
        BufferedImage image = toBufferedImage(matrix);

        if (!ImageIoUtil.write(image, format, Files.fileOutputStream(file))) {
            throw new IORuntimeException("Could not write an image of format " + format + " to " + file);
        }
    }

    public static void writeToOutputStream(BitMatrix bitMatrix, String format, FileOutputStream fileOutputStream) {
        BufferedImage image = toBufferedImage(bitMatrix);

        if (!ImageIoUtil.write(image, format, fileOutputStream)) {
            throw new IORuntimeException("Could not write an image of format " + format + " to " + fileOutputStream);
        }
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
}