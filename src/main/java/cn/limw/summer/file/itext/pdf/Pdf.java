package cn.limw.summer.file.itext.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author li
 * @version 1 (2014年10月27日 下午5:32:57)
 * @since Java7
 */
public class Pdf {
    public static void pdf(String[] args) throws DocumentException {
        Document document = new Document();
        document.open();
        PdfWriter.getInstance(document, System.err);
        document.add(new Paragraph("hello world"));
    }
}