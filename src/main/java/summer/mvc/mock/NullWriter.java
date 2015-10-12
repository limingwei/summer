package summer.mvc.mock;

import java.io.IOException;
import java.io.Writer;

/**
 * @author li
 * @version 1 (2015年10月12日 下午10:53:57)
 * @since Java7
 */
public class NullWriter extends Writer {
    public void write(char[] cbuf, int off, int len) throws IOException {}

    public void flush() throws IOException {}

    public void close() throws IOException {}
}