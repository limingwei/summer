package cn.limw.summer.shiro.subject.wrapper;

import org.apache.shiro.subject.support.DelegatingSubject;

/**
 * @author li
 * @version 1 (2015年1月9日 下午4:49:53)
 * @since Java7
 */
public class DelegatingSubjectWrapper extends DelegatingSubject {
    public DelegatingSubjectWrapper(DelegatingSubject delegatingSubject) {
        super(delegatingSubject.getSecurityManager());
    }
}