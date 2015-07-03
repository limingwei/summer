package cn.limw.summer.open.weixin.message.handler;

import cn.limw.summer.open.weixin.event.ScanEvent;
import cn.limw.summer.open.weixin.message.Message;

/**
 * @author li
 * @version 1 (2015年6月18日 上午10:24:50)
 * @since Java7
 */
public class ScanEventHandler extends EventMessageHandler {
    public Boolean support(Message message) {
        return message instanceof ScanEvent;
    }
}