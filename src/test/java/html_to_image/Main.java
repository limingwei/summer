package html_to_image;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author li
 * @version 1 (2015年5月26日 上午9:57:42)
 * @since Java7
 */
public class Main {
    protected static void generateOutput() throws Exception {
        JEditorPane editorPane = new JEditorPane(new URL("http://www.google.com/"));
        editorPane.setSize(1000, 2000);

        BufferedImage bufferedImage = new BufferedImage(editorPane.getWidth(), editorPane.getHeight(), BufferedImage.TYPE_INT_ARGB);

        SwingUtilities.paintComponent(bufferedImage.createGraphics(), editorPane, new JPanel(), 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        ImageIO.write((RenderedImage) bufferedImage, "png", new File("D:/html.png"));
    }

    public static void main(String[] args) {
        try {
            generateOutput();
            System.err.println("done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}