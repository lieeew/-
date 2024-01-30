package com.leikooo.transaction.mediator;

import com.leikooo.transaction.colleague.AbstractCustomer;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/30
 * @description
 */
public abstract class AbstractMediator {
    public abstract void messageTransfer(String orderId, String targetCustomer, AbstractCustomer customer, String payResult);
}
