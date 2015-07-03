package cn.limw.summer.java.io.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/**
 * @author li
 * @version 1 (2015年1月27日 上午9:14:03)
 * @since Java7
 */
public class ReInsertBufferedReader extends BufferedReader {
    private ArrayList lines = new ArrayList();

    public ReInsertBufferedReader(Reader in) {
        super(in);
    }

    public String readLine() throws IOException {
        if (lines.size() > 0) {
            return (String) lines.remove(0);
        } else {
            return super.readLine();
        }
    }

    public void reinsertLine(String line) {
        lines.add(0, line);
    }
}