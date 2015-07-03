package cn.limw.summer.yui.compressor;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

import org.slf4j.Logger;

import cn.limw.summer.mozilla.javascript.SimpleErrorReporter;
import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Files;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/**
 * @author li
 * @version 1 (2015年6月25日 下午2:01:33)
 * @since Java7
 */
public class YuiUtil {
    private static final Logger log = Logs.slf4j();

    public static String compressJs(String jsSource) {
        try {
            Asserts.noEmpty(jsSource, "jsSource为空");

            StringReader stringReader = new StringReader(jsSource);
            JavaScriptCompressor javaScriptCompressor = new JavaScriptCompressor(stringReader, SimpleErrorReporter.INSTANCE);

            StringWriter stringWriter = new StringWriter();
            int linebreak = -1; // 指定单行宽度
            boolean munge = true; // 混淆
            boolean verbose = true; // 显示详细信息
            boolean preserveAllSemiColons = false; // 保留所有分号
            boolean disableOptimizations = false; // 禁用优化
            javaScriptCompressor.compress(stringWriter, linebreak, munge, verbose, preserveAllSemiColons, disableOptimizations);
            return stringWriter.toString();
        } catch (Throwable e) {
            log.error("compressJs error", e);
            throw new RuntimeException(e);
        }
    }

    public static String compressCss(String cssSource) {
        try {
            Asserts.noEmpty(cssSource, "cssSource为空");

            StringReader stringReader = new StringReader(cssSource);
            CssCompressor cssCompressor = new CssCompressor(stringReader);

            StringWriter stringWriter = new StringWriter();
            int linebreakpos = -1; // 指定单行宽度
            cssCompressor.compress(stringWriter, linebreakpos);
            return stringWriter.toString();
        } catch (Throwable e) {
            log.error("compressCss error", e);
            throw new RuntimeException(e);
        }
    }

    public static String compressJs(File file) {
        try {
            return compressJs(Files.read(file));
        } catch (Throwable e) {
            log.error("compressJs error " + Files.getCanonicalPath(file), e);
            throw new RuntimeException("error when compressJs " + Files.getCanonicalPath(file), e);
        }
    }

    public static String compressCss(File file) {
        try {
            return compressCss(Files.read(file));
        } catch (Throwable e) {
            log.error("compressCss error " + Files.getCanonicalPath(file), e);
            throw new RuntimeException("error when compressCss " + Files.getCanonicalPath(file), e);
        }
    }

    public static String compressJsIfPossible(File file) {
        String source = Files.read(file);
        try {
            return compressJs(source);
        } catch (Throwable e) {
            log.error("compressJsIfPossible error " + Files.getCanonicalPath(file), e);
            return source;
        }
    }

    public static String compressJsIfPossible(String jsSource) {
        try {
            return compressJs(jsSource);
        } catch (Throwable e) {
            log.error("compressJsIfPossible error " + StringUtil.cutLeft(jsSource, 25, ""), e);
            return jsSource;
        }
    }
}