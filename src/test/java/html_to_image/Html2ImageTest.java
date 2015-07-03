package html_to_image;

import gui.ava.html.image.generator.HtmlImageGenerator;

/**
 * @author li
 * @version 1 (2015年5月26日 上午10:33:48)
 * @since Java7
 */
public class Html2ImageTest {
    public static void main(String[] args) {
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        imageGenerator.loadUrl("http://www.baidu.com");
        imageGenerator.saveAsImage("D:/hello-world.png");
        // imageGenerator.saveAsHtmlWithMap("hello-world.html", "d:/hello-world.png");
        System.err.println("done");
    }
}