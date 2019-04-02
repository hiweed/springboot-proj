package cn.hiweedwang.springbootproj;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ITransactionTests {
    /**
     * 必须开启新事务
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void beginNewTransation();
}
