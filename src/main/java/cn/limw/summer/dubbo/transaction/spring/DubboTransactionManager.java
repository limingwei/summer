package cn.limw.summer.dubbo.transaction.spring;

import org.slf4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import cn.limw.summer.spring.transaction.notransaction.NoTransactionStatus;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年1月27日 下午4:34:02)
 * @since Java7
 */
public class DubboTransactionManager implements PlatformTransactionManager {
    private static final Logger log = Logs.slf4j();

    public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
        NoTransactionStatus transactionStatus = new NoTransactionStatus(definition);
        log.warn("getTransaction {}", transactionStatus);
        return transactionStatus;
    }

    public void commit(TransactionStatus status) throws TransactionException {
        log.warn("commit {}", status);
    }

    public void rollback(TransactionStatus status) throws TransactionException {
        log.warn("rollback {}", status);
    }
}