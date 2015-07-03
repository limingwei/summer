package cn.limw.summer.spring.transaction.notransaction;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.SimpleTransactionStatus;

import cn.limw.summer.util.Jsons;

/**
 * @author li
 * @version 1 (2015年1月27日 下午2:47:40)
 * @since Java7
 */
public class NoTransactionStatus extends SimpleTransactionStatus {
    private TransactionDefinition transactionDefinition;

    public NoTransactionStatus(TransactionDefinition transactionDefinition) {
        this.transactionDefinition = transactionDefinition;
    }

    public TransactionDefinition getTransactionDefinition() {
        return transactionDefinition;
    }

    public String toString() {
        return super.toString() + ", " + Jsons.toJson(this);
    }
}