package cn.limw.summer.dubbo.transaction.spring;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.SimpleTransactionStatus;

import cn.limw.summer.util.Jsons;

/**
 * @author li
 * @version 1 (2015年1月27日 下午4:35:35)
 * @since Java7
 */
public class DubboTransactionStatus extends SimpleTransactionStatus {
    private TransactionDefinition transactionDefinition;

    public DubboTransactionStatus(TransactionDefinition transactionDefinition) {
        this.transactionDefinition = transactionDefinition;
    }

    public TransactionDefinition getTransactionDefinition() {
        return transactionDefinition;
    }

    public String toString() {
        return super.toString() + ", " + Jsons.toJson(this);
    }
}
