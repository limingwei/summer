package cn.limw.summer.spring.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

/**
 * @author li
 * @version 1 (2014年6月26日 下午2:28:54)
 * @since Java7
 */
public class CaptchaGenerator {
    /**
     * 图片高度
     */
    private Integer height;

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 文字数量
     */
    private Integer size = 4;

    /**
     * 文字范围
     */
    private String source = "1234567890"//
            + "abcdefg" + "hijklmn" + "opqrst" + "uvwxyz"//
            + "ABCDEFG" + "HIJKLMN" + "OPQRST" + "UVWXYZ";

    public Image create() {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics graphics = image.getGraphics();

        graphics.setColor(Color.WHITE);//背景色
        graphics.fillRect(0, 0, getWidth(), getHeight());//填充

        写干扰线(graphics);

        写文字(graphics);
        graphics.dispose();
        return image;
    }

    private void 写文字(Graphics graphics) {
        Random random = new Random();

        graphics.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.PLAIN, 20);
        graphics.setFont(font);
        String verifyCode = "";
        char tmp = 0;
        int distance = 15;
        int x = -distance;

        String _source = getSource();
        int _length = _source.length();

        for (int i = 0; i < getSize(); i++) {
            tmp = _source.charAt(random.nextInt(_length - 1));
            verifyCode = verifyCode + tmp;
            x = x + distance;
            graphics.setColor(new Color(random.nextInt(100) + 50, random.nextInt(100) + 50, random.nextInt(100) + 50));
            graphics.drawString(tmp + "", x, random.nextInt(height - (font.getSize())) + (font.getSize()));
        }
    }

    private void 写干扰线(Graphics graphics) {
        Random random = new Random();
        graphics.setColor(new Color(random.nextInt(100) + 100, random.nextInt(100) + 100, random.nextInt(100) + 100));
        for (int i = 0; i < 10; i++) {
            graphics.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));//添加干扰线
        }
    }

    /**
     * 工具方法
     */
    public static void show(final Image image) {
        JFrame frame = new JFrame() {
            private static final long serialVersionUID = -4484695137315466725L;

            public void paint(Graphics g) {
                g.drawImage(image, 50, 50, this);
            }
        };
        frame.setBounds(200, 200, 300, 200);
        frame.setVisible(true);
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getHeight() {
        return height;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getSize() {
        return size;
    }

    public String getSource() {
        return source;
    }

    public Integer getWidth() {
        return width;
    }
}