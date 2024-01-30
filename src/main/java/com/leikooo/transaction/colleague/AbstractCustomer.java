package com.leikooo.transaction.colleague;

import com.leikooo.transaction.mediator.AbstractMediator;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/30
 * @description 抽象同事类
 */
public abstract class AbstractCustomer {
    /**
     * 关联的中介对象
     */
    public AbstractMediator mediator;

    /**
     * 订单 ID
     */
    public String orderId;

    /**
     * 当前消费这者的名字
     */
    public String customerName;

    AbstractCustomer(AbstractMediator mediator, String orderId, String customerName) {
        this.mediator = mediator;
        this.orderId = orderId;
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    /**
     * 定义与中介信息的交互方法，提供之类实现
     *
     * @param orderId
     * @param targetCustomer
     * @param payType
     */
    public abstract void messageTransfer(String orderId, String targetCustomer, String payType);
}
