package cn.limw.summer.spring.web.view.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

/**
 * @author li
 * @version 1 (2014年10月21日 下午3:03:33)
 * @since Java7
 */
public class TextToImageView implements View {
    private String text;

    public TextToImageView(String text) {
        this.text = text;
    }

    public String getContentType() {
        return "image/png";
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2d = bufferedImage.createGraphics();
        graphics2d.drawString(text, 20, 20);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, "jpg", outputStream);
        outputStream.flush();
        outputStream.close();
    }
}