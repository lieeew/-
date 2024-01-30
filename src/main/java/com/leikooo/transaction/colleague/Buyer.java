package com.leikooo.transaction.colleague;

import com.leikooo.transaction.mediator.AbstractMediator;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/30
 * @description
 */
public class Buyer extends AbstractCustomer {

    public Buyer (AbstractMediator mediator, String orderId, String customerName) {
        super(mediator, orderId, customerName);
    }

    @Override
    public void messageTransfer(String orderId, String targetCustomer, String payType) {
        super.mediator.messageTransfer(orderId, targetCustomer, this, payType);
    }
}
