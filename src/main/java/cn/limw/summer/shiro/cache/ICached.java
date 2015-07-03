package cn.limw.summer.shiro.cache;

import java.util.List;
import java.util.Set;

/**
 * @author lgb
 * @version 1 (2014年9月10日下午4:40:39)
 * @since Java7
 */
public interface ICached {
    /**
     * 删除缓存
     * @param key
     * @throws Exception
     */
    public String deleteCached(byte[] key) throws Exception;

    /**
     * 更新缓存
     * @param key
     * @param value
     * @param expire
     * @throws Exception
     */
    public Object updateCached(byte[] key, byte[] value, Long expire) throws Exception;

    /**
     * 获取缓存
     * @param key
     * @throws Exception
     */
    public Object getCached(byte[] key) throws Exception;

    /**
     * 根据正则表达式key获取列表
     * @param keys
     * @throws Exception
     */
    public Set getKeys(byte[] keys) throws Exception;

    /**
     * 根据正则表达式key获取列表
     * @param key
     * @throws Exception
     */
    public Set getHashKeys(byte[] key) throws Exception;

    /**
     * 更新缓存
     * @param key
     * @param mapkey
     * @param value
     * @param expire
     * @throws Exception
     */
    public Boolean updateHashCached(byte[] key, byte[] mapkey, byte[] value, Long expire) throws Exception;

    /**
     * 获取缓存
     * @param key
     * @param mapkey
     * @throws Exception
     */
    public Object getHashCached(byte[] key, byte[] mapkey) throws Exception;

    /**
     * 删除缓存
     * @param key
     * @param mapkey
     * @throws Exception
     */
    public Long deleteHashCached(byte[] key, byte[] mapkey) throws Exception;

    /**
     * 获取map的长度
     * @param key
     * @throws Exception
     */
    public Long getHashSize(byte[] key) throws Exception;

    /**
     * 获取 map中的所有值
     * @param key
     * @throws Exception
     */
    public List getHashValues(byte[] key) throws Exception;

    /**
     * @throws Exception
     */
    public Long getDBSize() throws Exception;

    /**
     * @throws Exception
     */
    public void clearDB() throws Exception;
}