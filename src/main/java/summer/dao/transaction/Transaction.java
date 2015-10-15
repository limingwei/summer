package summer.dao.transaction;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import summer.log.Logger;
import summer.util.Log;

/**
 * @author li
 * @version 1 (2015年10月15日 下午4:25:39)
 * @since Java7
 */
public abstract class Transaction {
    private static final Logger log = Log.slf4j();

    /**
     * 当前线程的事务,为空表示未在事务中
     */
    private static final ThreadLocal<Transaction> TRANS_LOCAL = new ThreadLocal<Transaction>();

    /**
     * 当前事务管理的链接,每DataSource一个,多DataSource支持
     */
    private Map<DataSource, Connection> connectionMap = new HashMap<DataSource, Connection>();

    /**
     * 事务隔离级别
     */
    private Integer level;

    /**
     * 是否只读事务
     */
    private Boolean readOnly;

    /**
     * 用于存放一些值,可用于Trans内外通信
     */
    private Map<Object, Object> map;

    /**
     * 返回map引用
     */
    public Map<Object, Object> map() {
        return this.map;
    }

    /**
     * 返回事务执行成功与否的标记
     */
    public Boolean success() {
        return (Boolean) this.map.get(hashCode() + "~!@#success");
    }

    /**
     * 获取当前线程的事务,没在事务中则返回空
     */
    public static Transaction current() {
        return TRANS_LOCAL.get();
    }

    /**
     * 在事务中获取数据库链接
     */
    public Connection getConnection(DataSource dataSource) throws Exception {
        Connection connection = this.connectionMap.get(dataSource); // 从connectionMap中得到为这个DataSource缓存的connection
        if (null == connection || connection.isClosed()) { // 没有缓存这个DataSource的connection或已被关闭
            connection = dataSource.getConnection(); // 获取一个新的connection
            connection.setAutoCommit(false); // 设置为不自动提交
            connection.setTransactionIsolation(null != this.level ? this.level : connection.getTransactionIsolation());// 设置事务级别
            connection.setReadOnly(null != this.readOnly ? this.readOnly : connection.isReadOnly());// 设置只读
            this.connectionMap.put(dataSource, connection); // 缓存connection
        }
        return connection; // 返回这个connection
    }

    /**
     * 定义一个事务,并执行run()中包裹的数据操作方法
     */
    public Transaction() {
        this(new HashMap<Object, Object>(), null, false);
    }

    /**
     * 定义并执行一个事务,传入一些参数,指定事务级别,指定是否只读
     */
    public Transaction(Map<Object, Object> map, Integer level, Boolean readOnly) {
        try {
            try {
                this.map = map;
                this.level = level;
                this.readOnly = readOnly;
                this.begin(); // 开始事务
                this.run(); // 执行事务内方法
                this.commit(); // 提交事务
                this.map.put(hashCode() + "~!@#success", true);
            } catch (Exception e) {// QueryRunner里面已经打印栈,事务管理中,也不需要抛出异常,这里就只管流程
                this.rollback(); // 回滚事务
                this.map.put(hashCode() + "~!@#success", false);
            } finally {
                this.end(); // 结束事务
            }
        } catch (Exception e) {
            throw new RuntimeException(e + " ", e);
        }
    }

    /**
     * 抽象方法,包裹需要事务控制的Dao方法
     */
    public abstract void run();

    /**
     * 开始事务,初始化CONNECTION_MAP,或者标记这个事务已被其他事务包裹融化
     */
    private void begin() {
        if (null == Transaction.current()) { // 未在另一个事务中
            TRANS_LOCAL.set(this);
            log.trace("Trans@? level=? readOnly=? beginning", hashCode(), null == level ? "default" : level, this.readOnly);
        } else {
            this.map.put(hashCode() + "~!@#in_trans", true);// 在另一个事务中
        }
    }

    /**
     * 结束事务,关闭当前事务中的所有Connection,如果这个事务未在其他事务中的话
     */
    private void end() throws Exception {
        if (null == this.map.get(hashCode() + "~!@#in_trans")) {// 未在另一个事务中
            for (Entry<DataSource, Connection> connection : this.connectionMap.entrySet()) {
                connection.getValue().close();
                log.trace("Closing Connection in Trans ?", connection.getValue());
            }
            TRANS_LOCAL.set(null);
            log.trace("Trans@? level=? readOnly=? ending", hashCode(), null == level ? "default" : level, this.readOnly);
        }
    }

    /**
     * 捆绑提交当前事务中所有Connection的事务,如果这个事务未在其他事务中的话
     */
    private void commit() throws Exception {
        if (null == this.map.get(hashCode() + "~!@#in_trans") && !this.readOnly) {// 未在另一个事务中且非只读
            for (Entry<DataSource, Connection> connection : this.connectionMap.entrySet()) {
                connection.getValue().commit();
                log.trace("Trans@? level=? readOnly=? commiting ?", hashCode(), null == level ? "default" : level, this.readOnly, connection.getValue());
            }
        }
    }

    /**
     * 捆绑回滚当前事务中所有Connection中的事务,如果这个事务
     */
    private void rollback() throws Exception {
        if (null == this.map.get(hashCode() + "~!@#in_trans") && !this.readOnly) {// 未在另一个事务中且非只读
            for (Entry<DataSource, Connection> connection : this.connectionMap.entrySet()) {
                connection.getValue().rollback();
                log.trace("Trans@?  level=? readOnly=?  rollingback ?", hashCode(), null == level ? "default" : level, this.readOnly, connection.getValue());
            }
        }
    }
}