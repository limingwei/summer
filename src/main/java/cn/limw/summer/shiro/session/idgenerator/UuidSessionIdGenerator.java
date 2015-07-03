package cn.limw.summer.shiro.session.idgenerator;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年7月2日 下午7:02:21)
 * @since Java7
 * @see org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator
 */
public class UuidSessionIdGenerator implements SessionIdGenerator {
    public Serializable generateId(Session session) {
        return StringUtil.uuid();
    }
}