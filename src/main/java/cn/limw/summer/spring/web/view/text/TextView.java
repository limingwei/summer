package cn.limw.summer.spring.web.view.text;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

/**
 * @author li
 * @version 1 (2014年7月25日 下午3:21:05)
 * @since Java7
 */
public class TextView implements View {
    private String text;

    private String contentType = "text/html;charset=UTF-8";

    private String characterEncoding = "UTF-8";

    public TextView() {}

    public TextView(String text) {
        this.text = text;
    }

    public TextView(String text, String contentType) {
        setText(text);
        setContentType(contentType);
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding(getCharacterEncoding());
        response.setContentType(getContentType());
        PrintWriter out = getWriter(response);
        out.print(text);
        out.flush();
        out.close();
    }

    /**
     * getWriter
     */
    private PrintWriter getWriter(HttpServletResponse response) throws IOException {
        try {
            return response.getWriter();
        } catch (Exception e) {
            return new PrintWriter(response.getOutputStream());
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toString() {
        return super.toString() + " text=" + getText() + ", contentType=" + getContentType() + ", characterEncoding=" + getCharacterEncoding();
    }
}