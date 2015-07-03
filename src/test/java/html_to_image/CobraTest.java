//package html_to_image;
//
//import javax.swing.JFrame;
//
///**
// * @author li
// * @version 1 (2015年5月26日 上午10:39:43)
// * @since Java7
// */
//public class CobraTest {
//    public static void main(String[] args) throws Exception {
//        JFrame window = new JFrame();
//        HtmlPanel panel = new HtmlPanel();
//        window.getContentPane().add(panel);
//        window.setSize(600, 400);
//        window.setVisible(true);
//        new SimpleHtmlRendererContext(panel, new SimpleUserAgentContext()).navigate("http://www.hefeipet.com/client/chongwuzhishi/shenghuozatan/2012/0220/95.html");
//        BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
//
//        // paint the editor onto the image  
//        SwingUtilities.paintComponent(image.createGraphics(), panel, new JPanel(), 0, 0, image.getWidth(), image.getHeight());
//        // save the image to file  
//        ImageIO.write((RenderedImage) image, "png", new File("html.png"));
//    }
//}