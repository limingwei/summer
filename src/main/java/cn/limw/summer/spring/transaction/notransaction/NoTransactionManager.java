package cn.limw.summer.spring.transaction.notransaction;

import org.slf4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年1月27日 下午2:41:33)
 * @since Java7
 */
public class NoTransactionManager implements PlatformTransactionManager {
    private static final Logger log = Logs.slf4j();

    public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
        NoTransactionStatus transactionStatus = new NoTransactionStatus(definition);
        log.trace("getTransaction {}", transactionStatus);
        return transactionStatus;
    }

    public void commit(TransactionStatus status) throws TransactionException {
        log.trace("commit {}", status);
    }

    public void rollback(TransactionStatus status) throws TransactionException {
        log.trace("rollback {}", status);
    }
}