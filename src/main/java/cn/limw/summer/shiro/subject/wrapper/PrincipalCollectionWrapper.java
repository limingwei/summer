package cn.limw.summer.shiro.subject.wrapper;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.shiro.subject.PrincipalCollection;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2015年1月15日 下午2:56:05)
 * @since Java7
 */
public class PrincipalCollectionWrapper implements PrincipalCollection {
    private static final long serialVersionUID = 3898351913182609486L;

    private PrincipalCollection principalCollection;

    public PrincipalCollectionWrapper() {}

    public PrincipalCollectionWrapper(PrincipalCollection principalCollection) {
        setPrincipalCollection(principalCollection);
    }

    public PrincipalCollection getPrincipalCollection() {
        return Asserts.noNull(principalCollection);
    }

    public PrincipalCollectionWrapper setPrincipalCollection(PrincipalCollection principalCollection) {
        this.principalCollection = principalCollection;
        return this;
    }

    public Iterator iterator() {
        return getPrincipalCollection().iterator();
    }

    public Object getPrimaryPrincipal() {
        return getPrincipalCollection().getPrimaryPrincipal();
    }

    public <T> T oneByType(Class<T> type) {
        return getPrincipalCollection().oneByType(type);
    }

    public <T> Collection<T> byType(Class<T> type) {
        return getPrincipalCollection().byType(type);
    }

    public List asList() {
        return getPrincipalCollection().asList();
    }

    public Set asSet() {
        return getPrincipalCollection().asSet();
    }

    public Collection fromRealm(String realmName) {
        return getPrincipalCollection().fromRealm(realmName);
    }

    public Set<String> getRealmNames() {
        return getPrincipalCollection().getRealmNames();
    }

    public boolean isEmpty() {
        return getPrincipalCollection().isEmpty();
    }
}