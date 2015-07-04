package cn.limw.summer.mongo.driver.util;

import org.apache.log4j.Logger;

/**
 * Logs
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月5日 上午10:04:11)
 */
public class Logs {
    public static Logger get() {
        return Logger.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }
}