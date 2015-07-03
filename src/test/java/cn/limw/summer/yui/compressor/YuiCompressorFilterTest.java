package cn.limw.summer.yui.compressor;

import javax.servlet.ServletException;

import cn.limw.summer.yui.compressor.YuiCompressorFilter;

/**
 * @author li
 * @version 1 (2015年6月25日 下午2:09:36)
 * @since Java7
 */
public class YuiCompressorFilterTest {
    public static void main(String[] args) throws ServletException {
        new YuiCompressorFilter().init(null);
    }
}