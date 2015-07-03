package cn.limw.summer.yui.compressor;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.servlet.ServletContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;

import cn.limw.summer.time.Clock;
import cn.limw.summer.util.Files;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年6月25日 下午4:55:45)
 * @since Java7
 */
public class WroYuiUtil {
    private static final Logger log = Logs.slf4j();

    public static final String UTF_8 = "UTF-8";

    public static final String OUTPUT_PATH_IN_SERVLET_CONTEXT_KEY = "WRO_YUI_OUTPUT_PATH_IN_SERVLET_CONTEXT";

    public static void compressResources(ServletContext servletContext) {
        log.info("contextInitialized, servletContextEvent={}", servletContext);

        String realPathRoot = servletContext.getRealPath("/");
        long start = System.currentTimeMillis();

        String config = Files.toString(WroYuiUtil.class.getResourceAsStream("/wro.xml"), UTF_8);
        Document document = Jsoup.parse(config);
        Elements elementsGroup = document.select("group");

        long sourceSizeTotal = 0;
        long compressedSizeTotal = 0;

        for (Element elementGroup : elementsGroup) {
            String groupName = elementGroup.attr("name");

            String jsSourceHeader = "/* \n js source \n group: " + elementGroup.attr("name") + " \n time: " + Clock.now().toYYYY_MM_DD_HH_MM_SS() + " \n */\n";
            String cssSourceHeader = "/* \n css source \n group: " + elementGroup.attr("name") + " \n time: " + Clock.now().toYYYY_MM_DD_HH_MM_SS() + " \n */\n";
            String jsSource = jsSourceHeader;
            String cssSource = cssSourceHeader;

            // 合并JS
            Elements elementsJs = elementGroup.select("js");
            for (Element elementJs : elementsJs) {
                String fileName = elementJs.text();
                jsSource += "/* file: " + fileName + " start */\n";
                jsSource += Files.toString(Files.reader(servletContext.getRealPath("/") + "/" + fileName, UTF_8));
                jsSource += "/* file: " + fileName + " end */\n";
            }

            // 合并CSS
            Elements elementsCss = elementGroup.select("css");
            for (Element elementCss : elementsCss) {
                String fileName = elementCss.text();
                cssSource += "/* file: " + fileName + " start */\n";
                String bigFilePath = servletContext.getRealPath("/") + "/" + fileName;
                String cssFileSource = Files.toString(Files.fileInputStream(bigFilePath), UTF_8);
                cssSource += handleCssImport(cssFileSource, bigFilePath);
                cssSource += "/* file: " + fileName + " end */\n";
            }

            // 写完整 JS/CSS 到文件
            String fullJsFilePath = realPathRoot + getOutputPath(servletContext) + groupName + ".full.js";
            Files.write(new ByteArrayInputStream(jsSource.getBytes()), Files.fileOutputStream(fullJsFilePath));

            String fullCssFilePath = realPathRoot + getOutputPath(servletContext) + groupName + ".full.css";
            Files.write(new ByteArrayInputStream(cssSource.getBytes()), Files.fileOutputStream(fullCssFilePath));

            // 压缩JS
            if (jsSource.length() > jsSourceHeader.length()) {
                long _start = System.currentTimeMillis();

                String compressedJs = YuiUtil.compressJs(jsSource);
                String fileName = realPathRoot + getOutputPath(servletContext) + groupName + ".js";
                Files.write(new ByteArrayInputStream(compressedJs.getBytes()), Files.fileOutputStream(fileName));

                sourceSizeTotal += jsSource.length();
                compressedSizeTotal += compressedJs.length();
                log.info("compressed {} size: {}->{}, 耗时 {}ms", fileName, jsSource.length(), compressedJs.length(), (System.currentTimeMillis() - _start));
            }

            // 压缩CSS
            if (cssSource.length() > cssSourceHeader.length()) {
                long _start = System.currentTimeMillis();

                // 压缩, 处理 @import url(), 再压缩
                String compressedCss = YuiUtil.compressCss(cssSource);

                String fileName = realPathRoot + getOutputPath(servletContext) + groupName + ".css";
                Files.write(new ByteArrayInputStream(compressedCss.getBytes()), Files.fileOutputStream(fileName));

                sourceSizeTotal += cssSource.length();
                compressedSizeTotal += compressedCss.length();
                log.info("compressed {} size: {}->{}, 耗时 {}ms", fileName, cssSource.length(), compressedCss.length(), (System.currentTimeMillis() - _start));
            }
        }

        log.info("总计 size: {}->{}, 耗时 {}ms", sourceSizeTotal, compressedSizeTotal, (System.currentTimeMillis() - start));
    }

    public static String getOutputPath(ServletContext servletContext) {
        String outputPath = StringUtil.toString(servletContext.getAttribute(OUTPUT_PATH_IN_SERVLET_CONTEXT_KEY));
        if (StringUtil.isEmpty(outputPath)) {
            return "\\source\\wro\\";
        } else {
            return outputPath;
        }
    }

    /**
     * 处理Css import
     * @import url(common.import.css);
     */
    private static String handleCssImport(String source, String bigFilePath) {
        if (!StringUtil.isEmpty(source)) { // @charset "utf-8";@import url(common.import.css);@import url(font.import.css);
            int importIndex = 0;
            for (;;) {
                importIndex = source.indexOf("@import url(", importIndex);
                if (importIndex < 0) {
                    break;
                } else {
                    int endIndex = source.indexOf(");", importIndex);
                    if (endIndex > 0) {
                        String innerFilePath = source.substring(importIndex + 12, endIndex);
                        source = source.substring(0, importIndex) + readImportedCssFile(innerFilePath, bigFilePath) + source.substring(endIndex + 2);
                    }
                }
            }
        }
        return source;
    }

    private static String readImportedCssFile(String innerFilePath, String bigFilePath) {
        bigFilePath = bigFilePath.replace("\\", File.separator).replace("/", File.separator);
        return "/*" + bigFilePath + " import " + innerFilePath + " start*/\n" //
                + Files.read(new File(new File(bigFilePath).getParentFile(), innerFilePath)) //
                + "\n/*" + bigFilePath + " import " + innerFilePath + " end*/ ";
    }
}